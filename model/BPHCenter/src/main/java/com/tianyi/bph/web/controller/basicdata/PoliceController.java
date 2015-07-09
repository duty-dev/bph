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
import com.tianyi.bph.domain.basicdata.Police;
import com.tianyi.bph.domain.basicdata.PoliceTitle;
import com.tianyi.bph.domain.basicdata.PoliceType;
import com.tianyi.bph.domain.system.Organ;
import com.tianyi.bph.domain.system.User;
import com.tianyi.bph.query.basicdata.GpsBaseVM;
import com.tianyi.bph.query.basicdata.GroupMemberVM;
import com.tianyi.bph.query.basicdata.PoliceExtItem;
import com.tianyi.bph.query.basicdata.PoliceVM;
import com.tianyi.bph.query.system.UserQuery;
import com.tianyi.bph.service.basicdata.PoliceService;
import com.tianyi.bph.service.system.OrganService;

/*
 * 警员管理逻辑控制器
 * 
 */

@Controller
@RequestMapping("/policeWeb")
public class PoliceController {

	@Autowired PoliceService policeService;// =
											// ServiceFactory.getPoliceServiceInit();

	// @Resource(name="exportService")
	// protected ExportService exportService;

	@RequestMapping("index")
	public String index() {
		return "index";
	}

	@Autowired
	private OrganService organService;

	/**
	 * web跳转到警员列表
	 * 
	 * @param request
	 * @param username
	 *            用户名
	 * @return
	 */
	@RequestMapping({ "/gotoPoliceList.do", "/gotoPoliceList.action" })
	@ResponseBody
	public ModelAndView gotoPoliceList(
			HttpServletRequest request,
			@RequestParam(value = "userName", required = false) String userName,
			@RequestParam(value = "organId", required = false) Integer organId,
			@RequestParam(value = "searchType", required = true, defaultValue = "1") Integer searchType,
			@RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
			@RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo) {
		UserQuery query = new UserQuery();
		User user = (User) request.getAttribute("User");
		if (organId == null) {
			organId = user.getOrgId();
		}
		if (!StringUtils.isEmpty(userName)) {
			query.setUserName(userName);
		}
		query.setOrganId(organId);
		query.setPageNo(pageNo);
		query.setPageSize(pageSize);
		ModelAndView mv = new ModelAndView("/basicdata/police/policeList.jsp");
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

	/*
	 * 获取警员列表信息
	 * 
	 * police_Query：查询条件包 sort：排序列 order：排序方式 page：当前页 rows：每页条数
	 */
	@RequestMapping(value = "/getPoliceList.do")
	@ResponseBody
	public PageReturn getPoliceList(
			@RequestParam(value = "police_Query", required = false) String police_Query,
			HttpServletRequest request) throws Exception {
		try {
			JSONObject joQuery = JSONObject.fromObject(police_Query);
			int orgId = Integer.parseInt(joQuery.getString("orgId"));
			int isSubOrg = Integer.parseInt(joQuery.getString("isSubOrg"));
			String name = joQuery.getString("name");
			String orgPath = joQuery.getString("orgPath");
			int pageStart = Integer.parseInt(joQuery.getString("pageStart"));
			int pageSize = Integer.parseInt(joQuery.getString("pageSize"));
			Map<String, Object> map = new HashMap<String, Object>();

			int pageBegin = pageSize * (pageStart > 0 ? (pageStart - 1) : 0);
			//int pageEnd = pageSize + pageBegin;
			map.put("isSubOrg", isSubOrg);
			map.put("name", name);
			map.put("orgId", orgId);
			map.put("orgPath", orgPath);
			map.put("pageStart", pageBegin);
			map.put("pageSize", pageSize);

			List<PoliceVM> list = new ArrayList<PoliceVM>();

			int total = policeService.loadVMCount(map);

			list = policeService.loadVMList(map);

			if(list.size()==0){
				if(pageStart>1){
					pageBegin = pageSize * ((pageStart-1) > 0 ? (pageStart - 2) : 0);
					map.put("pageStart", pageBegin);
					list = policeService.loadVMList(map);
				}
			}
			
			return PageReturn.MESSAGE(MessageCode.STATUS_SUCESS,
					MessageCode.SELECT_SUCCESS, total, list);
		} catch (Exception ex) {
			return PageReturn.MESSAGE(MessageCode.STATUS_FAIL,
					MessageCode.SELECT_ORGAN_FAIL, 0, null);
		}
	}

	@RequestMapping({ "gotoPoliceAdd.do", "/gotoPoliceAdd.action" })
	@ResponseBody
	public ModelAndView gotoPoliceAdd(
			@RequestParam(value = "organId", required = false) Integer organId,
			HttpServletRequest request) throws Exception {
		try {
			Organ organ = new Organ();

			if (organId != null) {
				organ = organService.getOrganByPrimaryKey(organId);
			}
			ModelAndView mv = new ModelAndView(
					"/basicdata/police/policeAdd.jsp");

			mv.addObject("organId", organId);
			mv.addObject("organ", organ);

			return mv;
		} catch (Exception ex) {
			return new ModelAndView("/basicdata/police/policeAdd.jsp");
		}
	}

	@RequestMapping({ "gotoPoliceImport.do", "/gotoPoliceImport.action" })
	@ResponseBody
	public ModelAndView gotoPoliceImport(
			@RequestParam(value = "organId", required = false) Integer organId,
			HttpServletRequest request) throws Exception {
		try {
			Organ organ = new Organ();

			if (organId != null) {
				organ = organService.getOrganByPrimaryKey(organId);
			}
			ModelAndView mv = new ModelAndView(
					"/basicdata/police/policeExport.jsp");

			mv.addObject("organId", organId);
			mv.addObject("organ", organ);

			return mv;
		} catch (Exception ex) {
			return new ModelAndView("/basicdata/police/policeList.jsp");
		}
	}

	@RequestMapping({ "gotoPoliceEdit.do", "/gotoPoliceEdit.action" })
	@ResponseBody
	public ModelAndView gotoPoliceEdit(
			@RequestParam(value = "organId", required = false) Integer organId,
			@RequestParam(value = "userId", required = false) Integer userId,
			HttpServletRequest request) throws Exception {
		try {
			Organ organ = new Organ();
			Police police = new Police();
			if (userId != null) {
				police = policeService.selectByPrimaryKey(userId);
			}
			if (organId != null) {
				organ = organService.getOrganByPrimaryKey(organId);
			}
			ModelAndView mv = new ModelAndView(
					"/basicdata/police/policeEdit.jsp");
			mv.addObject("police", police);
			mv.addObject("organId", organId);
			mv.addObject("organ", organ);

			return mv;
		} catch (Exception ex) {
			return new ModelAndView("/basicdata/police/policeList.jsp");
		}
	}

	/*
	 * 获取警员类型列表，以下拉列表形式呈现；
	 */
	@RequestMapping(value = "getPoliceType.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	String getPoliceType() throws Exception {
		try {
			List<PoliceType> list = policeService.selectPoliceType();
			JSONArray result = JSONArray.fromObject(list);
			return result.toString();
		} catch (Exception ex) {
			return "";
		}
	}
	
	/*
	 * 获取警员职务列表，以下拉列表形式呈现；
	 */
	@RequestMapping(value = "getPoliceTitle.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	String getPoliceTitle() throws Exception {
		try {
			List<PoliceTitle> list = policeService.selectPoliceTitle();
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
			List<IntercomGroup> list = policeService.selectIntercomGroup();
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
			List<GpsBaseVM> list = policeService.selectGpsId(orgId);
			JSONArray result = JSONArray.fromObject(list);
			return result.toString();
		} catch (Exception ex) {
			return "";
		}
	}

	/*
	 * 保存警员信息逻辑
	 */
	@RequestMapping(value = "savePolice.do")
	@ResponseBody
	public ReturnResult savePolice(Police police,
			@RequestParam(value = "groupId", required = false) Integer groupId
			) throws Exception {
		try {
			police.setPlatformId(1);
			police.setSyncState(true);
			int policeId = 0;
			if (police.getId() > 0) {
				//Edit
				int pid = police.getId();
				Police pol = new Police();
				pol = policeService.selectByPrimaryKey(pid);
				if (pol != null) {
					police.setIsused(pol.getIsused());
				} else {
				police.setIsused(true);
				}
				police.setId(pid);
				policeService.updateByPrimaryKey(police);
				policeId = pid;
			} else {
				//Add
				police.setIsused(true);
				policeService.insert(police);
				policeId = police.getId();
			}
			//保存分组信息
			if(groupId>0){ 
				List<GroupMemberVM> gm = policeService.findByGroupIDAndPoliceID(policeId,groupId);
				if(gm.size()==0){
					policeService.addMemberToGroup(policeId,groupId);
				} 
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
	@RequestMapping(value = "deletePoliceById.do")
	@ResponseBody
	public ReturnResult deletePoliceById(
			@RequestParam(value = "policeId", required = true) String policeId)
			throws Exception {
		try {

			List<Police> list = policeService.findByIdAndDtyId(policeId);
			if (list.size() > 0) {
				return ReturnResult.MESSAGE(MessageCode.STATUS_FAIL,
						"该警员存在关联报备数据，不能删除", 0, null);
			} else {
				policeService.deleteByPrimaryKey(Integer.parseInt(policeId));

				return ReturnResult.MESSAGE(MessageCode.STATUS_SUCESS,
						MessageCode.SELECT_SUCCESS, 0, null);
			}
		} catch (Exception ex) {
			return ReturnResult.MESSAGE(MessageCode.STATUS_FAIL, "删除警员数据出错", 0,
					null);
		}
	}

	/*
	 * 判断是否有有警员存在
	 * 
	 * 判断是否身份证号重复；
	 * 
	 * 判断是否警号重复；
	 */
	@RequestMapping(value = "isExistPolice.do")
	@ResponseBody
	public ReturnResult isExistPolice(
			@RequestParam(value = "param", required = false) String param,
			@RequestParam(value = "paramType", required = false) String paramType,
			@RequestParam(value = "optType", required = false) Integer optType,
			@RequestParam(value = "id", required = false) Integer id)
			throws Exception {
		try {
			List<PoliceVM> police = new ArrayList<PoliceVM>();

			if (paramType.equals("idCard")) {
				if (optType > 0) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("param", param);
					map.put("id", id);
					police = policeService.findByidCardAndId(map);
				} else {
					police = policeService.findByidCard(param);
				}
				if (police.size() > 0) {
					return ReturnResult.MESSAGE(MessageCode.STATUS_FAIL,
							police.get(0).getOrgName(), 0, null);
				} else {
					return ReturnResult.MESSAGE(MessageCode.STATUS_SUCESS,
							"UnExits", 0, null);
				}
			} else if (paramType.equals("number")) {
				if (optType > 0) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("param", param);
					map.put("id", id);
					police = policeService.findBycodeAndId(map);
				} else {
					police = policeService.findBycode(param);
				}
				if (police.size() > 0) {
					return ReturnResult.MESSAGE(MessageCode.STATUS_FAIL,
							police.get(0).getOrgName(), 0, null);
				} else {
					return ReturnResult.MESSAGE(MessageCode.STATUS_SUCESS,
							"UnExits", 0, null);
				}

			} else if (paramType.equals("intercomPerson")) {
				if (optType > 0) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("param", param);
					map.put("id", id);
					police = policeService.findByintercomPersonAndId(map);
				} else {
					police = policeService.findByintercomPerson(param);
				}
				if (police.size() > 0) {
					return ReturnResult.MESSAGE(MessageCode.STATUS_FAIL,
							police.get(0).getOrgName(), 0, null);
				} else {
					return ReturnResult.MESSAGE(MessageCode.STATUS_SUCESS,
							"UnExits", 0, null);
				}

			} else {
				return ReturnResult.MESSAGE(MessageCode.STATUS_SUCESS,
						"UnExits", 0, null);
			}
		} catch (Exception ex) {
			return ReturnResult.MESSAGE(MessageCode.STATUS_FAIL, " ", 0,
					null);
		}
	}

	/*
	 * 保存警员信息逻辑
	 */
	@RequestMapping(value = "changePoliceStates.do")
	@ResponseBody
	public ReturnResult changePoliceStates(
			@RequestParam(value = "policeId", required = true) Integer policeId,
			@RequestParam(value = "state", required = true) Integer state)
			throws Exception {
		try {

			Police police = policeService.selectByPrimaryKey(policeId);
			if (police != null) {
				if (state == 1) {
					police.setIsused(true);
				} else if (state == 0) {
					police.setIsused(false);
				}
				policeService.updateByPrimaryKey(police);
				return ReturnResult.MESSAGE(MessageCode.STATUS_SUCESS,
						"警员状态修改成功", 0, null);
			} else {
				return ReturnResult.MESSAGE(MessageCode.STATUS_FAIL,
						"警员状态修改失败，查询对象失败", 0, null);
			}
		} catch (Exception ex) {
			return ReturnResult.MESSAGE(MessageCode.STATUS_FAIL, "修改警员状态信息出错",
					0, null);
		}
	}

}
