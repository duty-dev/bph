package com.tianyi.bph.web.controller.admin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile; 
import org.springframework.web.servlet.ModelAndView;
 
import com.tianyi.bph.common.MessageCode;
import com.tianyi.bph.common.PageReturn;
import com.tianyi.bph.common.ReturnResult;
import com.tianyi.bph.domain.basicdata.Icons; 
import com.tianyi.bph.domain.basicdata.IconsType;  
import com.tianyi.bph.domain.system.IconGroup;
import com.tianyi.bph.domain.system.IconTypeDetail;
import com.tianyi.bph.query.admin.IconsQuery;
import com.tianyi.bph.query.basicdata.IconsVM;
import com.tianyi.bph.query.system.UserQuery;
import com.tianyi.bph.service.admin.IconGroupService;
import com.tianyi.bph.service.admin.IconsService;
import com.tianyi.bph.service.admin.TypeService;
 

/*
 * 
 * 图标管理；
 */

@Controller
@RequestMapping("/iconsWeb")
public class IconController {


	@Autowired IconsService iconsService;
	@Autowired IconGroupService iconGroupService;
	@Autowired TypeService typeService;

	/**
	 * web跳转到图标列表
	 * 
	 * @param request
	 * @param username
	 *            用户名
	 * @return
	 */
	@RequestMapping({ "/gotoIconsList.do", "/gotoIconsList.action" })
	@ResponseBody
	public ModelAndView gotoIconsList(
			HttpServletRequest request,  
			@RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
			@RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo) {
		UserQuery query = new UserQuery(); 
		query.setPageNo(pageNo);
		query.setPageSize(pageSize);
		ModelAndView mv = new ModelAndView("/admin/icons/iconsList.jsp");
		List<IconsType> iconTypeList = new ArrayList();
		iconTypeList = iconsService.selectIconType();
		  
		mv.addObject("query", query); 
		mv.addObject("iconTypeList", iconTypeList);
		mv.addObject("num","200");
		return mv;
	}

	@RequestMapping({ "gotoIconAdd.do", "/gotoIconAdd.action" })
	@ResponseBody
	public ModelAndView gotoIconAdd(
			@RequestParam(value="typeName",required=false)String typeName,
			@RequestParam(value="iconType",required=false)String iconType,
			HttpServletRequest request) throws Exception {
		try { 
			ModelAndView mv = new ModelAndView(
					"/admin/icons/iconsAdd.jsp"); 
			if(!StringUtils.isEmpty(typeName)){
				String typeNameString = new String(typeName.getBytes("ISO-8859-1"), "UTF-8");
				mv.addObject("typeName", typeNameString);
				}
			if(iconType != null){
				mv.addObject("iconType", iconType);
			}
			return mv;
		} catch (Exception ex) {
			return new ModelAndView("/admin/icons/iconsList.jsp");
		}
	} 
	/*
	 * 获取图标列表信息
	 * 
	 * param：query查询条件包
	 * 
	 * page：当前页 rows：每页条数
	 */
	@RequestMapping(value = "getIconsList.do")
	 @ResponseBody
	public
	PageReturn getIconsList(
			@RequestParam(value="pageSize",required=false,defaultValue="10")Integer pageSize,
			@RequestParam(value="pageNo",required=false,defaultValue="1")Integer pageNo,
			@RequestParam(value="iconType",required=false,defaultValue="1")Integer iconType,
			HttpServletRequest request) throws Exception {
		try { 
			//List<IconsVM> list = new ArrayList<IconsVM>();
			List<IconGroup> list = new ArrayList<IconGroup>();
			Map<String, Object> map = new HashMap<String, Object>();
			IconsQuery iconsQuery = new IconsQuery();
			if(iconType != null){
				iconsQuery.setIconType(iconType);
			}
			iconsQuery.setPageNo(pageNo);
			iconsQuery.setPageSize(pageSize);
			int total = iconsService.loadCount(iconsQuery);
			//list = iconsService.loadList(iconsQuery); 
			list = iconGroupService.getIconGroupList(iconsQuery);
			return PageReturn.MESSAGE(MessageCode.STATUS_SUCESS,
					MessageCode.SELECT_SUCCESS, total, list);
		} catch (Exception ex) {
			return PageReturn.MESSAGE(MessageCode.STATUS_FAIL,
					"获取图标列表失败", 0, null);
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
	 * 删除图标
	 */
	@RequestMapping(value = "deleteIconsById.do")
	@ResponseBody
	public ReturnResult deleteIconsById(
			@RequestParam(value = "groupId", required = true) Integer groupId,
			HttpServletRequest request)
			throws Exception {
		try {
			List<IconTypeDetail> tempList = typeService.getTypeListByGoupId(groupId);
			if(tempList!=null && tempList.size()>0){
				return ReturnResult.MESSAGE(MessageCode.STATUS_FAIL, "图标已绑定资源，删除失败", 0,
						null);
			}
			iconGroupService.deleteIconGroupById(groupId);
				return ReturnResult.MESSAGE(MessageCode.STATUS_SUCESS,
						"删除成功", 0, null);
		} catch (Exception ex) {
			return ReturnResult.MESSAGE(MessageCode.STATUS_FAIL, "删除图标数据出错", 0,
					null);
		}
	}
 

	/*
	 * 删除图标
	 */
//	@RequestMapping(value = "deleteIconsById.do")
//	@ResponseBody
//	public ReturnResult deleteIconsById(
//			@RequestParam(value = "iconId", required = true) String iconId,
//			HttpServletRequest request)
//			throws Exception {
//		try {
//
//			List<Icons> list = iconsService.findByIdAndGpsId(iconId);
//			if (list.size() > 0) {
//				return ReturnResult.MESSAGE(MessageCode.STATUS_FAIL,
//						"该图标已关联Gps设备，不能删除", 0, null);
//			} else {
//				Icons icons = new Icons();
//				icons = iconsService.selectByPrimaryKey(Integer.parseInt(iconId));
//				if(icons==null){
//					return ReturnResult.MESSAGE(MessageCode.STATUS_FAIL,
//							"获取图标对象失败，删除失败", 0, null);
//				}
//				String iconUrl = icons.getIconUrl();
//				iconUrl = iconUrl.substring(10,iconUrl.length());
//				String uploadPath = request.getRealPath("uploadIcon")+iconUrl;
//				File ficon = new File(uploadPath);
//				if(ficon.exists()){
//					ficon.delete();
//				}
//				iconsService.updateIconsInfoById(Integer.parseInt(iconId));
//
//				return ReturnResult.MESSAGE(MessageCode.STATUS_SUCESS,
//						MessageCode.SELECT_SUCCESS, 0, null);
//			}
//		} catch (Exception ex) {
//			return ReturnResult.MESSAGE(MessageCode.STATUS_FAIL, "删除图标数据出错", 0,
//					null);
//		}
//	}

}
