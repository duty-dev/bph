package com.tianyi.bph.web.controller.system;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tianyi.bph.common.MessageCode;
import com.tianyi.bph.common.PageReturn;
import com.tianyi.bph.common.Pager;
import com.tianyi.bph.common.ReturnResult;
import com.tianyi.bph.common.SystemConfig;
import com.tianyi.bph.domain.system.Module;
import com.tianyi.bph.domain.system.Role;
import com.tianyi.bph.domain.system.RoleModuleFuctionKey;
import com.tianyi.bph.domain.system.User;
import com.tianyi.bph.query.system.ModuleQuery;
import com.tianyi.bph.query.system.RoleQuery;
import com.tianyi.bph.service.system.ModuleService;
import com.tianyi.bph.service.system.RoleModuleFuctionService;
import com.tianyi.bph.service.system.RoleService;
import com.tianyi.bph.service.system.UserRoleService;

/**
 * web用户管理
 * @author fantedan@tieserv.com
 * @date 2015-1-27 上午10:44:50
 */
@Controller
@RequestMapping("/role")
public class RoleController {
	
	private static final Logger logger=LoggerFactory.getLogger(UserController.class);
	
	@Autowired RoleService roleService;
	@Autowired UserRoleService userRoleService;
	@Autowired RoleModuleFuctionService roleModuleFuctionService;
	@Autowired ModuleService moduleService;
	
	/**
	 * goto 角色管理界面
	 * @param name
	 * @param pageSize
	 * @param pageNo
	 * @return
	 */
	@RequestMapping({"/gotoRoleList.do","/gotoRoleList.action"})
	public ModelAndView gotoRoleList(@RequestParam(value="name",required=false)String name,
			@RequestParam(value="nameSort",required=false,defaultValue="0")Integer nameSort, 
			@RequestParam(value="sessionId",required=false)String sessionId, 
			@RequestParam(value="timeSort",required=false,defaultValue="0")Integer timeSort, 
			@RequestParam(value="pageSize",required=false,defaultValue="10")Integer pageSize,
			@RequestParam(value="pageNo",required=false,defaultValue="1")Integer pageNo,
			HttpServletRequest request){
		ModelAndView mv=new ModelAndView("/base/role/roleList.jsp");
		RoleQuery query=new RoleQuery();
		if(!StringUtils.isEmpty(name)){query.setName(name);}
		query.setPageNo(pageNo);
		query.setPageSize(pageSize);
		query.setNameSort(nameSort);
		query.setTimeSort(timeSort);
		
		//User user=(User) request.getAttribute("User");
		//query.setUserId(user.getUserId());
		Pager<Role> roleList=roleService.getPageList(query);
		if(!StringUtils.isEmpty(sessionId)){
			request.getSession().setAttribute("sessionId", sessionId);
		}
		mv.addObject("query",query);
		mv.addObject("roleList",roleList);
		mv.addObject("num",SystemConfig.SYSTEM_MANAGER);
		return mv;
	}
	
	/**
	 * ajax請求
	 * @param name
	 * @param nameSort
	 * @param timeSort
	 * @param pageSize
	 * @param pageNo
	 * @return
	 */
	@RequestMapping({"/getRoleList.do","/getRoleList.action"})
	@ResponseBody
	public PageReturn getRoleList(HttpServletRequest request,
			@RequestParam(value="name",required=false)String name,
			@RequestParam(value="nameSort",required=false,defaultValue="0")Integer nameSort, 
			@RequestParam(value="timeSort",required=false,defaultValue="0")Integer timeSort, 
			@RequestParam(value="pageSize",required=false,defaultValue="10")Integer pageSize,
			@RequestParam(value="pageNo",required=false,defaultValue="1")Integer pageNo
			){
		RoleQuery query=new RoleQuery();
		User user=(User)request.getAttribute("User");
		
		int totalRows = 0;
		if(!StringUtils.isEmpty(name)){query.setName(name);}
		query.setPageNo(pageNo);
		query.setPageSize(pageSize);
		query.setUserId(user.getUserId());
		
		Pager<Role> roleList=roleService.getPageList(query);
		totalRows=roleList.getTotalRows();
		return PageReturn.MESSAGE(MessageCode.STATUS_SUCESS,"查詢成功",totalRows,roleList);
	}
	
	
	/**
	 * 跳转到添加角色权限界面
	 * @param name
	 * @param pageSize
	 * @param pageNo
	 * @return
	 */
	@RequestMapping({"/gotoAddRole.do","/gotoAddRole.action"})
	public ModelAndView gotoAddRole(@RequestParam(value="name",required=false)String name,
			@RequestParam(value="pageSize",required=false,defaultValue="10")Integer pageSize,
			@RequestParam(value="pageNo",required=false,defaultValue="1")Integer pageNo){
		ModelAndView mv=new ModelAndView("/base/role/roleAdd.jsp");
		return mv;
	}
	/**
	 * 跳转到角色详情界面
	 * @param roleId
	 * @return
	 */
	@RequestMapping(value="/gotoRoleDetail.do")
	public ModelAndView gotoRoleDetail(@RequestParam(value="roleId",required=true)String roleId){
		ModelAndView mv=new ModelAndView("/base/role/roleEdit.jsp");
		Role role=roleService.getRoleById(Integer.parseInt(roleId));
		mv.addObject("role",role);
		return mv;
	}
	
	/**
	 * 通过角色id去获取功能模块信息
	 * @param roleId
	 * @return
	 */
	@RequestMapping(value="/getModuleFunction.do")
	@ResponseBody
	public ReturnResult getModuleFunction(@RequestParam(value="roleId",required=true)String roleId){
		
		List<RoleModuleFuctionKey> funList=roleModuleFuctionService.getModuleFunListByRoleId(Integer.parseInt(roleId));
		
		return ReturnResult.MESSAGE(MessageCode.STATUS_SUCESS,"获取功能模块成功",funList);
	}
	
	/**
	 * 修改角色权限
	 * web分配角色权限    
	 * @param request
	 * @param roleId 角色ID
	 * @param modulesId 模块ID集
	 * @return
	 */
	public ReturnResult allotRoleModules(
			HttpServletRequest request,
			@RequestParam(value="roleId",required=true)Integer roleId,
			@RequestParam(value="modulesId",required=false)Integer[] modulesId){
		List<Integer> midList = new ArrayList<Integer>();
		if(modulesId!=null){  
			midList = (Arrays.asList(modulesId));
		}
		roleModuleFuctionService.allotRoleModules(roleId, midList);
		return null;
	}
	/**
	 * 添加角色权限
	 * @param roleName
	 * @param roleNote
	 * @param modulesId
	 * @return
	 */
	@RequestMapping(value="/insertRole.do")
	@ResponseBody
	public ReturnResult insertRole(HttpServletRequest request,
		@RequestParam(value="roleName",required=true) String roleName,
		@RequestParam(value="roleNote",required=false) String roleNote,
		@RequestParam(value="modulesId",required=false) String modulesId
		){
		try{
			Role role= new Role();
			role.setName(roleName);
			if(!StringUtils.isEmpty(roleNote)){
				role.setNote(roleNote);
			}
			List<String> midList = null;
			if(!StringUtils.isEmpty(modulesId)){
				String[] strr=modulesId.split(",");
				midList = (Arrays.asList(strr));
			}
			User user=(User) request.getAttribute("User");
			role.setUserId(user.getUserId());
			role.setModuleIds(midList);
			int i=roleService.addRole(role);
			if(i==0){
				return ReturnResult.MESSAGE(MessageCode.STATUS_FAIL,"添加角色失败或角色名称已存在");
			}
		}catch(Exception e){
			e.printStackTrace();
			return ReturnResult.MESSAGE(MessageCode.STATUS_FAIL,"添加角色失败或角色名称已存在");
		}
		return ReturnResult.MESSAGE(MessageCode.STATUS_SUCESS,"添加角色成功");
	}
	
	
	/**
	 * 修改角色权限
	 * @param roleName
	 * @param roleNote
	 * @param modulesId
	 * @return
	 */
	@RequestMapping(value="/updateRole.do")
	@ResponseBody
	public ReturnResult updateRole(
		@RequestParam(value="roleName",required=true) String roleName,
		@RequestParam(value="roleId",required=true) String roleId,
		@RequestParam(value="roleNote",required=false) String roleNote,
		@RequestParam(value="modulesId",required=false) String modulesId
		){
		try{
			Role role= new Role();
			role.setId(Integer.parseInt(roleId));
			role.setName(roleName);
			role.setNote(roleNote);
			List<String> midList = null;
			if(!StringUtils.isEmpty(modulesId)){
				String[] strr=modulesId.split(",");
				midList = (Arrays.asList(strr));
			}
			role.setModuleIds(midList);
			role.setId(Integer.parseInt(roleId));
			int i=roleService.updateRole(role);
			if(i==0){
				return ReturnResult.MESSAGE(MessageCode.STATUS_FAIL,"角色名已存在");
			}
		}catch(Exception e){
			e.printStackTrace();
			return ReturnResult.MESSAGE(MessageCode.STATUS_FAIL,"修改角色信息失败");
		}
		return ReturnResult.MESSAGE(MessageCode.STATUS_SUCESS,"修改角色信息成功");
	}
	

	/**
	 * 获取功能模块的列表
	 * @param name
	 * @return
	 */
	@RequestMapping(value="/getModuleTree.do")
	@ResponseBody
	public ReturnResult getModuleTree(
			@RequestParam(value="roleId",required=false)Integer roleId){
		
		ModuleQuery query =new ModuleQuery();
		if(roleId !=null){
			query.setRoleId(roleId);
		}
		Module module =moduleService.getTree(query);
		return ReturnResult.MESSAGE(MessageCode.STATUS_SUCESS,"查询成功",module);
	}
	/**
	 * 根据角色ID删除角色信息
	 * @param roleId
	 * @return
	 */
	@RequestMapping(value="/delete.do")
	@ResponseBody
	public ReturnResult delete(HttpServletRequest request,
			@RequestParam(value="roleId",required=true)String roleId){
		try{
			User user=(User) request.getAttribute("User");
			Role role=new Role();
			role.setId(Integer.parseInt(roleId));
			role.setUserId(user.getUserId());
			int i=roleService.deleteRole(role);
			if(i==0){
				return ReturnResult.MESSAGE(MessageCode.STATUS_FAIL,"删除失败,该角色还有绑定用户");
			}
		}catch(Exception e){
			logger.debug(e.getMessage());
			return ReturnResult.MESSAGE(MessageCode.STATUS_FAIL,e.getMessage());
		}
		return ReturnResult.MESSAGE(MessageCode.STATUS_SUCESS,"删除成功");
	}
	/**
	 * 通过角色ids 或者 用户Id 来获取功能模块信息
	 * @param roleIds
	 * @param userId
	 * @return
	 */
	@RequestMapping(value="/getModuleTreeByRoles.do")
	@ResponseBody
	public ReturnResult getModuleTreeByRoles(
			@RequestParam(value="roleIds",required=false)String roleIds,
			@RequestParam(value="userId",required=false)String userId){
		ModuleQuery moduleQuery =new ModuleQuery();
		if(!StringUtils.isEmpty(roleIds)){
			moduleQuery.setRoleIds(roleIds);
		}
		if(!StringUtils.isEmpty(userId)){
			moduleQuery.setUserId(new Long(userId));
		}
		Module module=moduleService.getTreeByUserIdOrRolesId(moduleQuery);
		return ReturnResult.MESSAGE(MessageCode.STATUS_SUCESS,"获取成功",module);	
	}
}
