package com.tianyi.bph.web.controller.dutydata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianyi.bph.common.MessageCode;
import com.tianyi.bph.common.PageReturn;
import com.tianyi.bph.common.ReturnResult;
import com.tianyi.bph.domain.duty.PoliceGroup;
import com.tianyi.bph.domain.duty.PoliceGroupMember;
import com.tianyi.bph.domain.duty.VehicleGroup;
import com.tianyi.bph.domain.duty.VehicleGroupMember;
import com.tianyi.bph.query.duty.PoliceGroupMemberVM;
import com.tianyi.bph.query.duty.PoliceGroupVM;
import com.tianyi.bph.service.duty.PoliceGroupService;
import com.tianyi.bph.service.system.OrganService;

@Controller
@RequestMapping("/policeGroupWeb")
public class PoliceGroupController {

	@Autowired
	protected PoliceGroupService policeGroupService;
	@Autowired
	private OrganService organService;

	/**
	 * 获取警员分组列表
	 * 
	 * @param query
	 * @param page
	 * @param rows
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "list.do")
	public @ResponseBody
	PageReturn List(
			@RequestParam(value = "policeGroup_Query", required = false) String query,
			HttpServletRequest request) {
		try{
			JSONObject joQuery = JSONObject.fromObject(query);
	
			int orgId = joQuery.getInt("orgId");
			int page = joQuery.getInt("page");
			int pageSize = 10;
	
			Map<String, Object> map = new HashMap<String, Object>(); 
			int pageBegin = pageSize * (page > 0 ? (page - 1) : 0);
			map.put("pageStart", pageBegin);
			map.put("pageSize", pageSize);
			map.put("orgId", orgId);
	
			int total = policeGroupService.loadVMCountByOrgId(map);
	
			List<PoliceGroupVM> pgvms = policeGroupService.loadVMListByOrgId(map);
	
			return PageReturn
					.MESSAGE(MessageCode.STATUS_SUCESS, "", total, pgvms);
		}catch(Exception ex){
			return PageReturn
					.MESSAGE(MessageCode.STATUS_FAIL, "", 0, null);
		}

	}

	/**
	 * 保存车辆分组信息
	 * 
	 * @param vehicleGroup
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "savePoliceGroup.do")
	public @ResponseBody
	ReturnResult savePoliceGroup(
			@RequestParam(value = "policeGroup", required = false) String policeGroup,
			HttpServletRequest request) {
		try {
			JSONObject joPG = JSONObject.fromObject(policeGroup);

			PoliceGroup pg = new PoliceGroup();
			pg.setId(joPG.getInt("id"));
			pg.setName(joPG.getString("name"));
			pg.setShareType(joPG.getInt("shareType"));
			pg.setOrgId(joPG.getInt("orgId"));
			pg.setPlatformId(1);
			pg.setSyncState(true);
			JSONArray jaOrgIds = joPG.getJSONArray("shareOrgIds");

			Object[] orgIds = jaOrgIds.toArray();

			policeGroupService.savePoliceGroup(pg, orgIds);
			return ReturnResult.MESSAGE(MessageCode.STATUS_SUCESS, "", 0, null);
		} catch (Exception ex) {
			return ReturnResult.MESSAGE(MessageCode.STATUS_FAIL, "", 0, null);
		}
	}

	/**
	 * 根据分组id，加载组成员
	 * 
	 * @param query
	 * @param page
	 * @param rows
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "loadMemberByGroupId.do")
	public @ResponseBody
	ReturnResult loadMemberByGroupId(
			@RequestParam(value = "groupId", required = false) Integer groupId,
			HttpServletRequest request) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageStart", 0);
		map.put("pageSize", 10000);
		map.put("groupId", groupId);

		int total = policeGroupService.countMemberByGroupId(groupId);

		List<PoliceGroupMemberVM> ms = policeGroupService
				.loadMemberByGroupId(map);

		return ReturnResult.MESSAGE(MessageCode.STATUS_SUCESS, "", total, ms);
	}

	/**
	 * 增加组成员
	 * 
	 * @param members
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "appendMember.do")
	public @ResponseBody
	ReturnResult appendMember(
			@RequestParam(value = "members", required = false) String members,
			HttpServletRequest request) {
		try {
			JSONArray jaMembers = JSONArray.fromObject(members); 
			List<?> list2 = JSONArray.toList(jaMembers,
					new PoliceGroupMember(), new JsonConfig());// 参数1为要转换的JSONArray数据，参数2为要转换的目标数据，即List盛装的数据

			List<PoliceGroupMember> ls = new ArrayList<PoliceGroupMember>();

			for (Object o : list2) {
				PoliceGroupMember pgm = (PoliceGroupMember) o;
				ls.add(pgm);
			}
			policeGroupService.appendMemeber(ls);
			return ReturnResult.MESSAGE(MessageCode.STATUS_SUCESS, "删除成功", 0,
					null);

		} catch (Exception ex) {
			return ReturnResult.MESSAGE(MessageCode.STATUS_FAIL, "查询数据出错", 0,
					null);
		}

	}

	/**
	 * 删除组成员
	 * 
	 * @param memberId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "delMemberById.do")
	public @ResponseBody
	ReturnResult delMemberById(
			@RequestParam(value = "memberId", required = false) Integer memberId,
			HttpServletRequest request) {
		try {
			if (memberId > 0) {
				policeGroupService.delMemberById(memberId);
				return ReturnResult.MESSAGE(MessageCode.STATUS_SUCESS, "删除成功",
						0, null);
			} else {
				return ReturnResult.MESSAGE(MessageCode.STATUS_FAIL, "删除成功", 0,
						null);
			}
		} catch (Exception ex) {
			return ReturnResult.MESSAGE(MessageCode.STATUS_FAIL, "查询数据出错", 0,
					null);
		}
	}

	/**
	 * 清除组成员
	 * 
	 * @param policeGroupId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "cleanMemberByGroupId.do")
	public @ResponseBody
	ReturnResult cleanMemberByGroupId(
			@RequestParam(value = "policeGroupId", required = false) Integer policeGroupId,
			HttpServletRequest request) {
		try {
			if (policeGroupId > 0) {
				policeGroupService.cleanMember(policeGroupId);
				return ReturnResult.MESSAGE(MessageCode.STATUS_SUCESS, "删除成功",
						0, null);
			} else {
				return ReturnResult.MESSAGE(MessageCode.STATUS_FAIL, "删除成功", 0,
						null);
			}
		} catch (Exception ex) {
			return ReturnResult.MESSAGE(MessageCode.STATUS_FAIL, "查询数据出错", 0,
					null);
		}
	}

	/**
	 * 删除车辆组
	 * 
	 * @param vehicleGroupId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "deletePoliceGroup.do")
	public @ResponseBody
	ReturnResult deletePoliceGroup(
			@RequestParam(value = "policeGroupId", required = false) Integer policeGroupId,
			HttpServletRequest request) {
		try {
			if (policeGroupId > 0) {
				policeGroupService.deleteById(policeGroupId);
				return ReturnResult.MESSAGE(MessageCode.STATUS_SUCESS, "删除成功",
						0, null);
			} else {
				return ReturnResult.MESSAGE(MessageCode.STATUS_FAIL, "删除成功", 0,
						null);
			}
		} catch (Exception ex) {
			return ReturnResult.MESSAGE(MessageCode.STATUS_FAIL, "查询数据出错", 0,
					null);
		}
	}

	/**
	 * 判断是否有有分组存在
	 * 
	 * 判断是否分组名称重复；
	 */
	@RequestMapping(value = "isExistGroup.do")
	public @ResponseBody
	ReturnResult isExistGroup(
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "optType", required = false) Integer optType,
			@RequestParam(value = "groupId", required = false) Integer groupId,
			@RequestParam(value = "orgId", required = false) Integer orgId)
			throws Exception {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("name", name);
			map.put("groupId", groupId);
			map.put("optType", optType);
			map.put("orgId", orgId);
			List<PoliceGroup> vehicleGroup = policeGroupService
					.findByNameAndOrg(map);
			if (vehicleGroup.size() > 0) {
				return ReturnResult.MESSAGE(MessageCode.STATUS_FAIL, "", 0,
						null);
			} else {
				return ReturnResult.MESSAGE(MessageCode.STATUS_SUCESS, "", 0,
						null);
			}
		} catch (Exception ex) {
			return ReturnResult.MESSAGE(MessageCode.STATUS_FAIL, "查询数据出错", 0,
					null);
		}
	}

}
