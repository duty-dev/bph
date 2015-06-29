package com.tianyi.bph.web.controller.admin;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tianyi.bph.common.MessageCode;
import com.tianyi.bph.common.PageReturn;
import com.tianyi.bph.common.ReturnResult;
import com.tianyi.bph.domain.basicdata.Icons;
import com.tianyi.bph.domain.basicdata.IconsType;
import com.tianyi.bph.domain.system.IconGroup;
import com.tianyi.bph.domain.system.IconTypeDetail;
import com.tianyi.bph.query.admin.IconsQuery;
import com.tianyi.bph.query.admin.TypeQuery;
import com.tianyi.bph.query.system.UserQuery;
import com.tianyi.bph.service.admin.IconGroupService;
import com.tianyi.bph.service.admin.IconsService;
import com.tianyi.bph.service.admin.TypeService;

/*
 * 
 * 类型管理；
 */

@Controller
@RequestMapping("/typeWeb")
public class TypeWebController {
	@Autowired IconsService iconsService;
	@Autowired IconGroupService iconGroupService;
	@Autowired TypeService typeService;

	/**
	 * web跳转到类型列表
	 * 
	 * @param request
	 * @param username
	 *            用户名
	 * @return
	 */
	@RequestMapping({ "/gotoTypeList.do", "/gotoTypeList.action" })
	@ResponseBody
	public ModelAndView gotoTypeList(
			HttpServletRequest request,  
			@RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
			@RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo) {
		UserQuery query = new UserQuery(); 
		query.setPageNo(pageNo);
		query.setPageSize(pageSize);
		ModelAndView mv = new ModelAndView("/admin/type/typeList.jsp");
		List<IconsType> iconTypeList = new ArrayList();
		iconTypeList = iconsService.selectIconType();
		  
		mv.addObject("query", query); 
		mv.addObject("iconTypeList", iconTypeList);
		mv.addObject("num","200");
		return mv;
	}

	@RequestMapping({ "gotoTypeAdd.do", "/gotoTypeAdd.action" })
	@ResponseBody
	public ModelAndView gotoTypeAdd(
			@RequestParam(value="iconType",required=false)Integer iconType,
			@RequestParam(value = "pageSize", required = false, defaultValue = "100") Integer pageSize,
			@RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
			HttpServletRequest request) throws Exception {
		try { 
			IconsQuery iconsQuery = new IconsQuery();
			ModelAndView mv = new ModelAndView(
					"/admin/type/typeAdd.jsp"); 
			if(iconType != null){
				iconsQuery.setIconType(iconType);
				mv.addObject("iconType", iconType);
			}
			iconsQuery.setPageNo(pageNo);
			iconsQuery.setPageSize(pageSize);
			List<IconGroup> iconGroupList = iconGroupService.getIconGroupList(iconsQuery);
			mv.addObject("iconGroupList", iconGroupList);
			return mv;
		} catch (Exception ex) {
			return new ModelAndView("/admin/type/typeList.jsp");
		}
	} 
	/*
	 * 获取类型列表信息
	 * 
	 * param：query查询条件包
	 * 
	 */
	@RequestMapping(value = "getTypeList.do")
	@ResponseBody
	public PageReturn getTypeList(
			@RequestParam(value="pageSize",required=false,defaultValue="10")Integer pageSize,
			@RequestParam(value="pageNo",required=false,defaultValue="1")Integer pageNo,
			@RequestParam(value="iconType",required=false,defaultValue="1")Integer iconType,
			HttpServletRequest request) throws Exception {
		try { 
			//List<IconsVM> list = new ArrayList<IconsVM>();
			List<IconTypeDetail> list = new ArrayList<IconTypeDetail>();
			Map<String, Object> map = new HashMap<String, Object>();
			TypeQuery typeQuery = new TypeQuery();
			typeQuery.setPageSize(pageSize);
			typeQuery.setPageNo(pageNo);
			if(iconType != null){
				typeQuery.setIconType(iconType);
			}
			list = typeService.getTypeList(typeQuery);
			int total = list.size();
			//list = iconsService.loadList(iconsQuery); 
			
			return PageReturn.MESSAGE(MessageCode.STATUS_SUCESS,
					MessageCode.SELECT_SUCCESS, total, list);
		} catch (Exception ex) {
			return PageReturn.MESSAGE(MessageCode.STATUS_FAIL,
					"获取类列表失败", 0, null);
		}
	}
	/*
	 * 
	 * 获取图标类型列表；
	 */
	@RequestMapping(value = "getIconType.do", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String getIconType() throws Exception {
		try {
			List<IconsType> list = iconsService.selectIconType();
			JSONArray result = JSONArray.fromObject(list);
			return result.toString();
		} catch (Exception ex) {
			return "";
		}
	}
 

	/*
	 * 删除类型
	 */
	@RequestMapping(value = "deleteTypeById.do")
	@ResponseBody
	public ReturnResult deleteIconsById(
			@RequestParam(value = "typeId", required = true) Integer typeId,
			HttpServletRequest request)
			throws Exception {
		try {
			typeService.deleteTypeById(typeId);
				return ReturnResult.MESSAGE(MessageCode.STATUS_SUCESS,
						MessageCode.SELECT_SUCCESS, 0, null);
		} catch (Exception ex) {
			return ReturnResult.MESSAGE(MessageCode.STATUS_FAIL, "删除类型数据出错", 0,
					null);
		}
	}
	
	/*
	 * 新增类型
	 */
	@RequestMapping(value = "typeAdd.do")
	@ResponseBody
	public ReturnResult typeAdd(
			@RequestParam(value = "groupId", required = true) Integer groupId,
			@RequestParam(value = "iconType", required = true) Integer iconType,
			@RequestParam(value = "typeName", required = true) String typeName,
			HttpServletRequest request)
			throws Exception {
		try {
			IconTypeDetail iconTypeDetail = new IconTypeDetail();
			iconTypeDetail.setParentIconType(iconType);
			iconTypeDetail.setTypeName(typeName);
			iconTypeDetail.setIconGroupId(groupId);
			typeService.addType(iconTypeDetail);
				return ReturnResult.MESSAGE(MessageCode.STATUS_SUCESS,
						"新增类型成功", 0, null);
		} catch (Exception ex) {
			return ReturnResult.MESSAGE(MessageCode.STATUS_FAIL, "新增类型数据出错", 0,
					null);
		}
	}
	
	@RequestMapping({ "gotoTypeUpdate.do", "/gotoTypeUpdate.action" })
	@ResponseBody
	public ModelAndView gotoTypeUpdate(
			@RequestParam(value="id",required=false) Integer id,
			@RequestParam(value = "pageSize", required = false, defaultValue = "100") Integer pageSize,
			@RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
			HttpServletRequest request) throws Exception {
		try { 
			IconTypeDetail iconTypeDetail = new IconTypeDetail();
			iconTypeDetail = typeService.selectByPrimaryKey(id);
			IconsQuery iconsQuery = new IconsQuery();
			ModelAndView mv = new ModelAndView(
					"/admin/type/typeEdit.jsp"); 
			if(iconTypeDetail.getParentIconType() != null){
				iconsQuery.setIconType(iconTypeDetail.getParentIconType());
				mv.addObject("iconType", iconTypeDetail.getParentIconType());
			}
			iconsQuery.setPageNo(pageNo);
			iconsQuery.setPageSize(pageSize);
			List<IconGroup> iconGroupList = iconGroupService.getIconGroupList(iconsQuery);
			mv.addObject("iconGroupList", iconGroupList);
			mv.addObject("iconTypeDetail",iconTypeDetail);
			return mv;
		} catch (Exception ex) {
			return new ModelAndView("/admin/type/typeList.jsp");
		}
	}
	
	/*
	 * 更新类型
	 */
	@RequestMapping(value = "typeUpdate.do")
	@ResponseBody
	public ReturnResult typeUpdate(
			@RequestParam(value = "id", required = true) Integer id,
			@RequestParam(value = "groupId", required = true) Integer groupId,
			@RequestParam(value = "iconType", required = true) Integer iconType,
			@RequestParam(value = "typeName", required = true) String typeName,
			HttpServletRequest request)
			throws Exception {
		try {
			IconTypeDetail iconTypeDetail = new IconTypeDetail();
			iconTypeDetail.setParentIconType(iconType);
			iconTypeDetail.setTypeName(typeName);
			iconTypeDetail.setIconGroupId(groupId);
			iconTypeDetail.setId(id);
			typeService.updateType(iconTypeDetail);
				return ReturnResult.MESSAGE(MessageCode.STATUS_SUCESS,
						"更新类型成功", 0, null);
		} catch (Exception ex) {
			return ReturnResult.MESSAGE(MessageCode.STATUS_FAIL, "新增类型数据出错", 0,
					null);
		}
	}


}
