package com.tianyi.bph.web.controller.dutydata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tianyi.bph.common.MessageCode;
import com.tianyi.bph.common.ReturnResult;
import com.tianyi.bph.domain.duty.DutyType;
import com.tianyi.bph.query.basicdata.VehicleVM;
import com.tianyi.bph.query.duty.DutyTypeListVM;
import com.tianyi.bph.query.duty.DutyTypePropertyVM;
import com.tianyi.bph.query.duty.DutyTypeVM;
import com.tianyi.bph.service.duty.DutyTypeService;
import com.tianyi.bph.web.controller.ListResult;

/*
 * 警员管理逻辑控制器
 * 
 */

@Controller
@RequestMapping("/dutyTypeWeb")
public class DutyTypeController {

	@Autowired
	DutyTypeService dutyTypeService;// =

	/**
	 * web跳转到勤务类型列表
	 * 
	 * @param request
	 * @param
	 * 
	 * @return
	 */
	@RequestMapping({ "/gotoDutyTypeList.do", "/gotoDutyTypeList.action" })
	@ResponseBody
	public ModelAndView gotoDutyTypeList(HttpServletRequest request) {

		ModelAndView mv = new ModelAndView(
				"/dutydata/dutytype/dutyTypeList.jsp");
		mv.addObject("num", "300");
		return mv;
	}

	@RequestMapping({ "/gotoDutyTypeAdd.do", "/gotoDutyTypeAdd.action" })
	@ResponseBody
	public ModelAndView gotoPoliceAdd( 
			HttpServletRequest request) throws Exception {
		try {
			ModelAndView mv = new ModelAndView(
					"/dutydata/dutytype/dutyTypeAdd.jsp");
			return mv;
		} catch (Exception ex) {
			return new ModelAndView("/dutydata/dutytype/dutyTypeAdd.jsp");
		}
	}

	@RequestMapping({ "/gotoDutyTypeEdit.do", "/gotoDutyTypeEdit.action" })
	@ResponseBody
	public ModelAndView gotoPoliceEdit(HttpServletRequest request)
			throws Exception {
		try {
			ModelAndView mv = new ModelAndView(
					"/dutydata/dutytype/dutyTypeEdit.jsp");
			return mv;
		} catch (Exception ex) {
			return new ModelAndView("/dutydata/dutytype/dutyTypeEdit.jsp");
		}
	}

	/**
	 * 获取勤务类型列表，非模板，并且是启用状态的模板
	 * 
	 * @param isUsed
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getDutyTypelist.do")
	@ResponseBody
	public ReturnResult getDutyTypelist(
			@RequestParam(value = "isUsed", required = false) Boolean isUsed,
			HttpServletRequest request) throws Exception {
		try {
			List<DutyTypeVM> list = dutyTypeService.loadDutyTypeVM(isUsed);

			List<DutyTypeListVM> lists = new ArrayList<DutyTypeListVM>();

			if (list.size() > 0) {
				for (DutyTypeVM dt : list) {
					DutyTypeListVM dtvm = new DutyTypeListVM();
					dtvm.setArmamentType(dt.getArmamentType() == 0 ? "非武装"
							: "武装");
					dtvm.setAssoTaskType(dt.getAssoTaskType() == null ? "" : dt
							.getAssoTaskType() == 2 ? "社区" : dt
							.getAssoTaskType() == 1 ? "巡区" : dt
							.getAssoTaskType() == 3 ? "卡点" : "");
					dtvm.setAttireType(dt.getAttireType() == 0 ? "制服" : "便衣");
					dtvm.setFullpath(dt.getFullpath());
					dtvm.setId(dt.getId());
					dtvm.setIsLeaf(dt.getIsLeaf());
					dtvm.setIsShowname(dt.getIsShowname() ? "名称" : "人数");
					dtvm.setIsUsed(dt.getIsUsed() ? "启用" : "停用");
					dtvm.setLevel(dt.getLevel());
					dtvm.setMaxPolice(dt.getMaxPolice() == null ? "不限" : dt
							.getMaxPolice() == 0 ? "不限" : dt.getMaxPolice()
							.toString());
					dtvm.setName(dt.getName());
					dtvm.setParentFullPath(dt.getParentFullPath());
					dtvm.setParentId(dt.getParentId());
					dtvm.setParentName(dt.getParentName());
					String properties = "";
					if (dt.getProperties().size() > 0) {
						for (DutyTypePropertyVM dtp : dt.getProperties()) {
							properties += dtp.getName() + ",";
						}
						if (properties.length() > 1) {
							properties = properties.substring(0,
									properties.length() - 1);
						}
					}
					dtvm.setProperties(properties);

					lists.add(dtvm);
				}
			}
			return ReturnResult.MESSAGE(MessageCode.STATUS_SUCESS,
					MessageCode.SELECT_SUCCESS, 0, lists);
		} catch (Exception ex) {
			return ReturnResult.MESSAGE(MessageCode.STATUS_FAIL,
					MessageCode.SELECT_SUCCESS, 0, null);
		}
	}

	@RequestMapping(value = "/loadProperties.do",produces = "application/json;charset=UTF-8")
	public @ResponseBody
	String loadProperties(HttpServletRequest request) {

		List<DutyTypePropertyVM> ls = dutyTypeService.loadProperties();

		ListResult<DutyTypePropertyVM> rs = new ListResult<DutyTypePropertyVM>(
				ls.size(), ls);

		String result = rs.toJson();
		return result;
	}

	/**
	 * 保存勤务类型；
	 * 
	 * @param dutyType
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/saveDutyType.do")
	public @ResponseBody
	ReturnResult saveDutyType(
			@RequestParam(value = "dutyType", required = false) String dutyType,
			HttpServletRequest request) {
		try{
			JSONObject jsObj = JSONObject.fromObject(dutyType);
			Map<String, Class<?>> classMap = new HashMap<String, Class<?>>();
	
			classMap.put("properties", DutyTypePropertyVM.class);
			DutyTypeVM dtvm = (DutyTypeVM) JSONObject.toBean(jsObj,
					DutyTypeVM.class, classMap);
	
			dutyTypeService.save(dtvm);
			return ReturnResult.MESSAGE(MessageCode.STATUS_SUCESS,
					MessageCode.SELECT_SUCCESS, 0, null);
		} catch (Exception ex) {
			return ReturnResult.MESSAGE(MessageCode.STATUS_FAIL,
					MessageCode.SELECT_SUCCESS, 0, null);
		}
	}
	/**
	 * 修改勤务类型的启用与锁定状态
	 * 
	 * @param id
	 * @param isUsed
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/changeDutyTypeUseState.do")
	public @ResponseBody
	ReturnResult changeDutyTypeUseState(
			@RequestParam(value = "id", required = false) String id,
			@RequestParam(value = "isUsed", required = false) String isUsed,
			HttpServletRequest request) {
		try{
			Integer did = Integer.parseInt(id);
			Boolean dIsUsed = Boolean.parseBoolean(isUsed); 
			dutyTypeService.updateUseStateByFullPath(did, dIsUsed);
			return ReturnResult.MESSAGE(MessageCode.STATUS_SUCESS,MessageCode.SELECT_SUCCESS, 0,
					null);
		} catch (Exception ex) {
			return ReturnResult.MESSAGE(MessageCode.STATUS_FAIL,
					MessageCode.SELECT_SUCCESS, 0, null);
		}
	}
	/**
	 * 修改勤务类型的启用与锁定状态
	 * 
	 * @param id
	 * @param isUsed
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/isExistDutyType.do")
	public @ResponseBody
	ReturnResult isExistDutyType(
			@RequestParam(value = "id", required = false) Integer id,
			@RequestParam(value = "typeName", required = false) String typeName,
			@RequestParam(value = "optType", required = false) Integer optType,
			HttpServletRequest request) {
		try{ 
			List<DutyType> dType = new ArrayList<DutyType>();  
			
			if (optType > 0) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("typeName", typeName);
				map.put("id", id);
				dType = dutyTypeService.findByNameAndId(map);
			}else{
				dType = dutyTypeService.findByName(typeName);
			}
			
			if (dType.size() > 0) {
				return ReturnResult.MESSAGE(MessageCode.STATUS_FAIL,"" ,
						0, null);
			} else {
				return ReturnResult.MESSAGE(MessageCode.STATUS_SUCESS, "UnExits",
						0, null);
			}
		} catch (Exception ex) {
			return ReturnResult.MESSAGE(MessageCode.STATUS_FAIL,
					MessageCode.SELECT_SUCCESS, 0, null);
		}
	}

	/**
	 * 删除勤务类型
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/deleteDutyType.do")
	public @ResponseBody
	ReturnResult deleteDutyType(
			@RequestParam(value = "id", required = false) String id,
			@RequestParam(value = "parentid", required = false) Integer pid,
			HttpServletRequest request) {
		try {
			Integer did = Integer.valueOf(id);
			boolean rm = dutyTypeService.deleteNode(did);
			Integer retCode = MessageCode.STATUS_SUCESS;
			if (!rm) {
				retCode = MessageCode.STATUS_FAIL;
			}
			if (pid != null) {
				List<DutyType> list = new ArrayList<DutyType>();
				if (pid > 0) {
					list = dutyTypeService.loadDutyTypeByParentId(pid);
					if (list.size() == 0) {
						DutyType dt = new DutyType();
						dt = dutyTypeService.selectByPrimaryKey(pid);
						if (dt != null) {
							dt.setIsLeaf(true);
							dutyTypeService.updateByPrimaryKey(dt);
						}
					}
				}

			}
			return ReturnResult.MESSAGE(retCode, MessageCode.SELECT_SUCCESS, 0,
					null);
		} catch (Exception ex) {
			return ReturnResult.MESSAGE(MessageCode.STATUS_FAIL,
					MessageCode.SELECT_SUCCESS, 0, null);
		}
	}
}
