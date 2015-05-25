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
import com.tianyi.bph.domain.duty.GpsGroup;
import com.tianyi.bph.domain.duty.GpsGroupMember;
import com.tianyi.bph.domain.duty.GpsGroupOrg; 
import com.tianyi.bph.domain.duty.PoliceGroupMember;
import com.tianyi.bph.query.duty.GpsGroupMemberVM;
import com.tianyi.bph.query.duty.GpsGroupVM;
import com.tianyi.bph.service.duty.GpsGroupService;
import com.tianyi.bph.service.system.OrganService;

@Controller
@RequestMapping("/gpsGroupWeb")
public class GpsGroupController {

	@Autowired
	protected GpsGroupService gpsGroupService;
	@Autowired
	private OrganService organService;

	/**
	 * 获取左右翼分组列表
	 * 
	 * @param query
	 *            组织结构信息
	 * @param page
	 *            当前页
	 * @param rows
	 *            每页条数
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "list.do")
	public @ResponseBody
	PageReturn List(
			@RequestParam(value = "gpsGroup_Query", required = false) String query, 
			HttpServletRequest request) {
		try {
			JSONObject joQuery = JSONObject.fromObject(query);

			int orgId = joQuery.getInt("orgId"); 
			Map<String, Object> map = new HashMap<String, Object>(); 
			int pageSize = 10;
			int page = joQuery.getInt("page"); 
			int pageBegin = pageSize * (page > 0 ? (page - 1) : 0);
			map.put("pageStart", pageBegin);
			map.put("pageSize", pageSize); 
			map.put("orgId", orgId);

			map.put("inSubOrg", 0);

			int total = gpsGroupService.loadVMCountByOrgId(map);

			List<GpsGroupVM> pgvms = gpsGroupService.loadVMListByOrgId(map);

			return PageReturn.MESSAGE(MessageCode.STATUS_SUCESS,
					MessageCode.SELECT_SUCCESS, total, pgvms);
		} catch (Exception ex) {
			return PageReturn.MESSAGE(MessageCode.STATUS_FAIL, "", 0, null);
		}
	}

	/**
	 * 获取当前组织结构以及下级机构
	 * 
	 * @param gpsGroupId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "getShareOrgs.do")
	public @ResponseBody
	String getShareOrgs(
			@RequestParam(value = "gpsGroupId", required = false) Integer gpsGroupId,
			HttpServletRequest request) {

		List<GpsGroupOrg> ls = gpsGroupService.loadShareOrgList(gpsGroupId);

		String rs = JSONArray.fromObject(ls).toString();

		return rs;

	}

	/**
	 * 根据分组id获取组成员
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
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("pageStart", 0);
			map.put("pageSize", 10000);
			map.put("groupId", groupId);

			int total = gpsGroupService.countMemberByGroupId(groupId);

			List<GpsGroupMemberVM> ms = gpsGroupService
					.loadMemberByGroupId(map);

			return ReturnResult.MESSAGE(MessageCode.STATUS_SUCESS, "", total,
					ms);
		} catch (Exception ex) {
			return ReturnResult.MESSAGE(MessageCode.STATUS_FAIL, "", 0,
					null);
		}
	}

	/**
	 * 添加组成员
	 * @param members 组成员列表
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
					new GpsGroupMember(), new JsonConfig());// 参数1为要转换的JSONArray数据，参数2为要转换的目标数据，即List盛装的数据

			List<GpsGroupMember> ls = new ArrayList<GpsGroupMember>();

			for (Object o : list2) {
				GpsGroupMember pgm = (GpsGroupMember) o;
				ls.add(pgm);
			}
			gpsGroupService.appendMemeber(ls);
			return ReturnResult.MESSAGE(MessageCode.STATUS_SUCESS, "删除成功", 0,
					null);

		} catch (Exception ex) {
			return ReturnResult.MESSAGE(MessageCode.STATUS_FAIL, "查询数据出错", 0,
					null);
		}
		
		 
	}

	/**
	 * 保存定位设备分组
	 * @param gpsGroup
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "saveGpsGroup.do")
	public @ResponseBody
	ReturnResult saveGpsGroup(
			@RequestParam(value = "gpsGroup", required = false) String gpsGroup,
			HttpServletRequest request) {

		try {
			JSONObject joPG = JSONObject.fromObject(gpsGroup);

			GpsGroup pg = new GpsGroup();
			pg.setId(joPG.getInt("id"));
			pg.setName(joPG.getString("name"));
			pg.setShareType(joPG.getInt("shareType"));
			pg.setOrgId(joPG.getInt("orgId"));
			pg.setPlatformId(1);
			pg.setSyncState(true);
			JSONArray jaOrgIds = joPG.getJSONArray("shareOrgIds");

			Object[] orgIds = jaOrgIds.toArray();

			gpsGroupService.saveGpsGroup(pg, orgIds);
			return ReturnResult.MESSAGE(MessageCode.STATUS_SUCESS, "", 0, null);
		} catch (Exception ex) {
			return ReturnResult.MESSAGE(MessageCode.STATUS_FAIL, "", 0, null);
		} 
	}

	/**
	 * 删除定位设备分组
	 * 
	 * @param GpsGroupId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "deleteGpsGroup.do")
	public @ResponseBody
	ReturnResult deleteGpsGroup(
			@RequestParam(value = "gpsGroupId", required = false) Integer gpsGroupId,
			HttpServletRequest request) {
		try {
			if (gpsGroupId > 0) {
				gpsGroupService.deleteById(gpsGroupId);
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
				gpsGroupService.delMemberById(memberId);
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
	 * @param gpsGroupId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "cleanMemberByGroupId.do")
	public @ResponseBody
	ReturnResult cleanMemberByGroupId(
			@RequestParam(value = "gpsGroupId", required = false) Integer gpsGroupId,
			HttpServletRequest request) {
		try {
			if (gpsGroupId > 0) {
				gpsGroupService.cleanMember(gpsGroupId);
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
			List<GpsGroup> vehicleGroup = gpsGroupService
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
