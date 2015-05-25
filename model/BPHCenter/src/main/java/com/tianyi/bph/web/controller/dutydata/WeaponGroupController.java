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
import com.tianyi.bph.domain.duty.PoliceGroupMember;
import com.tianyi.bph.domain.duty.WeaponGroup;
import com.tianyi.bph.domain.duty.WeaponGroupMember; 
import com.tianyi.bph.query.duty.WeaponGroupMemberVM;
import com.tianyi.bph.query.duty.WeaponGroupVM;
import com.tianyi.bph.service.duty.WeaponGroupService;
import com.tianyi.bph.service.system.OrganService;

@Controller
@RequestMapping("/weaponGroupWeb")
public class WeaponGroupController {

	@Autowired
	protected WeaponGroupService weaponGroupService;
	@Autowired
	private OrganService organService;

	/**
	 * 获取武器分组列表
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
			@RequestParam(value = "weaponGroup_Query", required = false) String query,
			HttpServletRequest request) {
		try {
		JSONObject joQuery = JSONObject.fromObject(query);

		int orgId = joQuery.getInt("orgId");
		int page = joQuery.getInt("page");
		int pageSize = 10;

		Map<String, Object> map = new HashMap<String, Object>(); 
		int pageBegin = pageSize * (page > 0 ? (page - 1) : 0);
		map.put("pageStart", pageBegin);
		map.put("pageSize", pageSize);
		map.put("orgId", orgId);

		map.put("inSubOrg", 0);

		int total = weaponGroupService.loadVMCountByOrgId(map);
		List<WeaponGroupVM> pgvms = weaponGroupService.loadVMListByOrgId(map);
		return PageReturn.MESSAGE(MessageCode.STATUS_SUCESS, "", total, pgvms);
	} catch (Exception ex) {
		return PageReturn.MESSAGE(MessageCode.STATUS_FAIL, "", 0, null);
	}
	}

	/**
	 * 根据分组id，获取组成员列表
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
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "rows", required = false) Integer rows,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageStart", 0);
		map.put("pageSize", 10000);
		map.put("groupId", groupId);

		int total = weaponGroupService.countMemberByGroupId(groupId);

		List<WeaponGroupMemberVM> ms = weaponGroupService
				.loadMemberByGroupId(map);
		return ReturnResult.MESSAGE(200, "", total, ms);
	}

	/**
	 * 添加组成员
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
					new WeaponGroupMember(), new JsonConfig());// 参数1为要转换的JSONArray数据，参数2为要转换的目标数据，即List盛装的数据

			List<WeaponGroupMember> ls = new ArrayList<WeaponGroupMember>();

			for (Object o : list2) {
				WeaponGroupMember pgm = (WeaponGroupMember) o;
				ls.add(pgm);
			}
			weaponGroupService.appendMemeber(ls);
			return ReturnResult.MESSAGE(MessageCode.STATUS_SUCESS, "删除成功", 0,
					null);

		} catch (Exception ex) {
			return ReturnResult.MESSAGE(MessageCode.STATUS_FAIL, "查询数据出错", 0,
					null);
		}
		 
	}

	/**
	 * 保存武器分组信息
	 * 
	 * @param weaponGroup
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "saveWeaponGroup.do")
	public @ResponseBody
	ReturnResult saveWeaponGroup(
			@RequestParam(value = "weaponGroup", required = false) String weaponGroup,
			HttpServletRequest request) {

		try {
			JSONObject joPG = JSONObject.fromObject(weaponGroup);

			WeaponGroup pg = new WeaponGroup();
			pg.setId(joPG.getInt("id"));
			pg.setName(joPG.getString("name"));
			pg.setShareType(joPG.getInt("shareType"));
			pg.setOrgId(joPG.getInt("orgId"));
			pg.setPlatformId(1);
			pg.setSyncState(true);
			JSONArray jaOrgIds = joPG.getJSONArray("shareOrgIds");

			Object[] orgIds = jaOrgIds.toArray();

			weaponGroupService.saveWeaponGroup(pg, orgIds);
			return ReturnResult.MESSAGE(MessageCode.STATUS_SUCESS, "", 0, null);
		} catch (Exception ex) {
			return ReturnResult.MESSAGE(MessageCode.STATUS_FAIL, "", 0, null);
		}
	}

	/**
	 * 删除武器分组
	 * 
	 * @param weaponGroupId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "deleteWeaponGroup.do")
	public @ResponseBody
	ReturnResult deleteWeaponGroup(
			@RequestParam(value = "weaponGroupId", required = false) Integer weaponGroupId,
			HttpServletRequest request) {
		try {
			if (weaponGroupId > 0) {
				weaponGroupService.deleteById(weaponGroupId);
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
				weaponGroupService.delMemberById(memberId);
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
	 * @param weaponGroupId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "cleanMemberByGroupId.do")
	public @ResponseBody
	ReturnResult cleanMemberByGroupId(
			@RequestParam(value = "weaponGroupId", required = false) Integer weaponGroupId,
			HttpServletRequest request) {
		try {
			if (weaponGroupId > 0) {
				weaponGroupService.cleanMember(weaponGroupId);
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
			List<WeaponGroup> vehicleGroup = weaponGroupService
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
