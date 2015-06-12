package com.tianyi.bph.web.controller.basicdata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tianyi.bph.common.MessageCode;
import com.tianyi.bph.common.PageReturn;
import com.tianyi.bph.common.ReturnResult;
import com.tianyi.bph.domain.basicdata.IntercomGroup;
import com.tianyi.bph.domain.basicdata.Vehicle;
import com.tianyi.bph.domain.basicdata.VehicleType;
import com.tianyi.bph.domain.system.Organ;
import com.tianyi.bph.domain.system.User;
import com.tianyi.bph.query.basicdata.GpsBaseVM;
import com.tianyi.bph.query.basicdata.VehicleVM;
import com.tianyi.bph.query.system.UserQuery;
import com.tianyi.bph.service.basicdata.VehicleService;
import com.tianyi.bph.service.system.OrganService;

/*
 * 车辆管理逻辑控制器；
 */

@Controller
@RequestMapping("/vehicleWeb")
public class VehicleController {


	@Autowired VehicleService vehicleService;

	@Autowired OrganService organService;

	/**
	 * web跳转到警员列表
	 * 
	 * @param request
	 * @param username
	 *            用户名
	 * @return
	 */
	@RequestMapping({ "/gotoVehicleList.do", "/gotoVehicleList.action" })
	@ResponseBody
	public ModelAndView gotoVehicleList(
			HttpServletRequest request,
			@RequestParam(value = "vehicleNumber", required = false) String number,
			@RequestParam(value = "organId", required = false) Integer organId,
			@RequestParam(value = "searchType", required = true, defaultValue = "1") Integer searchType,
			@RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
			@RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo) {
		UserQuery query = new UserQuery();
		User user = (User) request.getAttribute("User");
		if (organId == null) {
			organId = user.getOrgId();
		}
		if (!StringUtils.isEmpty(number)) {
			query.setUserName(number);
		}
		query.setOrganId(organId);
		query.setPageNo(pageNo);
		query.setPageSize(pageSize);
		ModelAndView mv = new ModelAndView("/basicdata/vehicle/vehicleList.jsp");
		Organ organ = new Organ();
		if (organId != null) {
			organ = organService.getOrganByPrimaryKey(organId);
		}
		query.setOrganId(organId);
		mv.addObject("query", query);
		mv.addObject("organ", organ);
		mv.addObject("num","200");
		return mv;
	}

	@RequestMapping({ "gotoVehicleAdd.do", "/gotoVehicleAdd.action" })
	@ResponseBody
	public ModelAndView gotoVehicleAdd(
			@RequestParam(value = "organId", required = false) Integer organId,
			HttpServletRequest request) throws Exception {
		try {
			Organ organ = new Organ();

			if (organId != null) {
				organ = organService.getOrganByPrimaryKey(organId);
			}
			ModelAndView mv = new ModelAndView(
					"/basicdata/vehicle/vehicleAdd.jsp");

			mv.addObject("organId", organId);
			mv.addObject("organ", organ);

			return mv;
		} catch (Exception ex) {
			return new ModelAndView("/basicdata/vehicle/vehicleAdd.jsp");
		}
	}

	@RequestMapping({ "gotoVehicleEdit.do", "/gotoVehicleEdit.action" })
	@ResponseBody
	public ModelAndView gotoVehicleEdit(
			@RequestParam(value = "organId", required = false) Integer organId,
			@RequestParam(value = "vehicleId", required = false) Integer vehicleId,
			HttpServletRequest request) throws Exception {
		try {
			Organ organ = new Organ();
			Vehicle vehicle = new Vehicle();
			if (vehicleId != null) {
				vehicle = vehicleService.selectByPrimaryKey(vehicleId);
			}
			if (organId != null) {
				organ = organService.getOrganByPrimaryKey(organId);
			}
			ModelAndView mv = new ModelAndView(
					"/basicdata/vehicle/vehicleEdit.jsp");
			mv.addObject("vehicle", vehicle);
			mv.addObject("organId", organId);
			mv.addObject("organ", organ);

			return mv;
		} catch (Exception ex) {
			return new ModelAndView("/basicdata/vehicle/vehicleEdit.jsp");
		}
	}

	@RequestMapping({ "gotoVehicleImport.do", "/gotoVehicleImport.action" })
	@ResponseBody
	public ModelAndView gotoVehicleImport(
			@RequestParam(value = "organId", required = false) Integer organId,
			HttpServletRequest request) throws Exception {
		try {
			Organ organ = new Organ();

			if (organId != null) {
				organ = organService.getOrganByPrimaryKey(organId);
			}
			ModelAndView mv = new ModelAndView(
					"/basicdata/vehicle/vehicleExport.jsp");

			mv.addObject("organId", organId);
			mv.addObject("organ", organ);

			return mv;
		} catch (Exception ex) {
			return new ModelAndView("/basicdata/vehicle/vehicleExport.jsp");
		}
	}

	/*
	 * 获取警员列表信息
	 * 
	 * police_Query：查询条件包 sort：排序列 order：排序方式 page：当前页 rows：每页条数
	 */
	@RequestMapping(value = "/getVehicleList.do")
	@ResponseBody
	public PageReturn getVehicleList(
			@RequestParam(value = "vehicle_Query", required = false) String vehicle_Query,
			HttpServletRequest request) throws Exception {
		try {
			JSONObject joQuery = JSONObject.fromObject(vehicle_Query);
			int orgId = Integer.parseInt(joQuery.getString("orgId"));
			
			int isSubOrg = Integer.parseInt(joQuery.getString("isSubOrg"));
			String number = joQuery.getString("number");
			String orgPath = joQuery.getString("orgPath");
			int pageStart = Integer.parseInt(joQuery.getString("pageStart"));
			int pageSize = Integer.parseInt(joQuery.getString("pageSize"));
			Map<String, Object> map = new HashMap<String, Object>();

			int pageBegin = pageSize * (pageStart > 0 ? (pageStart - 1) : 0);
			//int pageEnd = pageSize + pageBegin;
			map.put("isSubOrg", isSubOrg);
			map.put("number", number);
			map.put("sort", "v.number");
			map.put("order", "asc");
			map.put("orgId", orgId);
			map.put("orgPath", orgPath);
			map.put("pageStart", pageBegin);
			map.put("pageSize", pageSize);

			List<VehicleVM> list = new ArrayList<VehicleVM>();

			int total = vehicleService.loadVMCount(map);

			list = vehicleService.loadVMList(map);

			if(list.size()==0){
				if(pageStart>1){
					pageBegin = pageSize * ((pageStart-1) > 0 ? (pageStart - 2) : 0);
					map.put("pageStart", pageBegin);
					list =vehicleService.loadVMList(map);
				}
			}
			return PageReturn.MESSAGE(MessageCode.STATUS_SUCESS,
					MessageCode.SELECT_SUCCESS, total, list);
		} catch (Exception ex) {
			return PageReturn.MESSAGE(MessageCode.STATUS_FAIL,
					MessageCode.SELECT_ORGAN_FAIL, 0, null);
		}
	}

	/*
	 * 
	 * 获取车辆类型列表，以下拉框的形式展现；
	 */
	@RequestMapping(value = "getVehicleType.do", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String getVehicleType() throws Exception {
		try {
			List<VehicleType> list = vehicleService.selectVehicleType();
			JSONArray result = JSONArray.fromObject(list);
			return result.toString();
		} catch (Exception ex) {
			return "";
		}
	}

	/*
	 * 获取警员分组列表，以数据列表的形式展现
	 * 
	 * 用于报备类型警员过滤条件筛选；
	 */
	@RequestMapping(value = "getintercomGroup.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	String getintercomGroup() throws Exception {
		try {
			List<IntercomGroup> list = vehicleService.selectIntercomGroup();
			JSONArray result = JSONArray.fromObject(list);
			return result.toString();
		} catch (Exception ex) {
			return "";
		}
	}

	/*
	 * 获取当前组织机构下面所属的所有GPS列表
	 * 
	 * 以下拉列表的形式展现；
	 */

	@RequestMapping(value = "getGpsId.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	String getGpsId(int orgId) throws Exception {
		try {
			List<GpsBaseVM> list = vehicleService.selectGpsId(orgId);
			JSONArray result = JSONArray.fromObject(list);
			return result.toString();
		} catch (Exception ex) {
			return "";
		}
	}

	/*
	 * 保存警员信息逻辑
	 */
	@RequestMapping(value = "saveVehicle.do")
	@ResponseBody
	public ReturnResult saveVehicle(Vehicle vehicle) throws Exception {
		try {
			vehicle.setPlatformId(1);
			vehicle.setSyncState(true);
			if (vehicle.getId() > 0) {
				int vid = vehicle.getId();

				vehicle.setId(vid);
				vehicleService.updateByPrimaryKey(vehicle);
			} else {
				vehicleService.insert(vehicle);

			}
			return ReturnResult.MESSAGE(MessageCode.STATUS_SUCESS,
					MessageCode.SELECT_SUCCESS, 0, null);
		} catch (Exception ex) {
			return ReturnResult.MESSAGE(MessageCode.STATUS_FAIL,
					MessageCode.SELECT_ORGAN_FAIL, 0, null);
		}
	}

	/*
	 * 保存警员信息逻辑
	 */
	@RequestMapping(value = "deleteVehicleById.do")
	@ResponseBody
	public ReturnResult deleteVehicleById(
			@RequestParam(value = "vehicleId", required = true) String vehicleId)
			throws Exception {
		try {

			List<Vehicle> list = vehicleService.findByIdAndDtyId(vehicleId);
			if (list.size() > 0) {
				return ReturnResult.MESSAGE(MessageCode.STATUS_FAIL,
						"该警员存在关联报备数据，不能删除", 0, null);
			} else {
				vehicleService.deleteByPrimaryKey(Integer.parseInt(vehicleId));

				return ReturnResult.MESSAGE(MessageCode.STATUS_SUCESS,
						MessageCode.SELECT_SUCCESS, 0, null);
			}
		} catch (Exception ex) {
			return ReturnResult.MESSAGE(MessageCode.STATUS_FAIL, "删除车辆数据出错", 0,
					null);
		}
	}

	/**
	 * 判断是否有有车辆存在
	 * 
	 * 判断是否车牌号码重复；
	 */
	@RequestMapping(value = "isExistVehicle.do")
	@ResponseBody
	public ReturnResult isExistVehicle(
			@RequestParam(value = "param", required = false) String param,
			@RequestParam(value = "type", required = false) Integer type,
			@RequestParam(value = "id", required = false) Integer id)
			throws Exception {
		try {
			List<VehicleVM> vehicle = new ArrayList<VehicleVM>();  
			
			if (type > 0) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("number", param);
				map.put("id", id);
				vehicle = vehicleService.findByNumberAndId(map);
			}else{
				vehicle = vehicleService.findByNumber(param);
			}
			
			if (vehicle.size() > 0) {
				return ReturnResult.MESSAGE(MessageCode.STATUS_FAIL,vehicle.get(0).getOrgName() ,
						0, null);
			} else {
				return ReturnResult.MESSAGE(MessageCode.STATUS_SUCESS, "UnExits",
						0, null);
			}

		} catch (Exception ex) {
			return ReturnResult.MESSAGE(MessageCode.STATUS_FAIL, " ", 0,
					null);
		}
	}

}
