/**
 * @author fantedan@tieserv.com
 *
 * @date  2015-1-13 上午10:44:50
 */
package com.tianyi.bph.rest.action.system;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.ehcache.CacheManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianyi.bph.common.JsonUtils;
import com.tianyi.bph.common.Md5Encrypt;
import com.tianyi.bph.common.MessageCode;
import com.tianyi.bph.common.Pager;
import com.tianyi.bph.common.ReturnResult;
import com.tianyi.bph.common.SystemConfig;
import com.tianyi.bph.common.ehcache.CacheUtils;
import com.tianyi.bph.domain.system.ModuleJson;
import com.tianyi.bph.domain.system.Organ;
import com.tianyi.bph.domain.system.User;
import com.tianyi.bph.query.system.OrganQuery;
import com.tianyi.bph.query.system.UserQuery;
import com.tianyi.bph.rest.CommonUtils;
import com.tianyi.bph.rest.PrivilegeCache;
import com.tianyi.bph.rest.PrivilegeCache.PrivilegeUser;
import com.tianyi.bph.service.ServiceSetService;
import com.tianyi.bph.service.system.LogService;
import com.tianyi.bph.service.system.OrganService;
import com.tianyi.bph.service.system.RoleService;
import com.tianyi.bph.service.system.UserAddModuleService;
import com.tianyi.bph.service.system.UserRoleService;
import com.tianyi.bph.service.system.UserService;

/**
 * 用户管理接口
 * @author fantedan@tieserv.com
 * @date 2015-1-13 上午10:44:50
 */
@Controller
@RequestMapping("/user")
public class UserAction {

	static final Logger log = LoggerFactory.getLogger(UserAction.class);

	@Autowired UserService userService;
	
	@Autowired UserRoleService userRoleService;
	
	@Autowired UserAddModuleService userAddModuleService;
	
	@Autowired ServiceSetService serviceConfService;
	
	@Autowired OrganService organService;
	
	@Autowired LogService logService;
	
	@Autowired private RoleService roleService;
	
	private CacheManager manager;
	
	@Autowired
	public void setManager(@Qualifier("ehCacheManager") CacheManager manager) {
		this.manager = manager;
	}
	

	/**
	 * 用户登录
	 * @param request
	 * @param username 用户名
	 * @param password 用户密码
	 * @return
	 */
	@RequestMapping(value = "/login.do")
	@ResponseBody
	public ReturnResult login(                       
			HttpServletRequest request,
			@RequestParam(value = "username", required =true) String username,
			@RequestParam(value = "password", required = true) String password) {
		ReturnResult result = userService.userLogin(username, password);
		if (MessageCode.STATUS_SUCESS == result.getCode()) {
			User user = (User) result.getData();
			/**
			 * 用户权限
			 */
			List<String> funListStr=userRoleService.getFunctionsByUserId(user.getUserId());
			user.setFunctionList(funListStr);
			
			List<ModuleJson> moduleList=roleService.getModuleList();
			user.setModules(moduleList);
			/**
			 * 服务配置信息
			 */
			user=serviceConfService.getServcieList(user);
			
			/**
			 * session 缓存管理
			 */
			HttpSession session = request.getSession();
			session.setAttribute("User", user);
			
			OrganQuery query=new OrganQuery();
			query.setId(user.getOrgId());
			query.setPath(user.getOrganPath());
			CacheUtils.updateValue(manager, CacheUtils.ORGAN_DATASOURCE, user.getUserId()+"",
					organService.getOrganTree(query, SystemConfig.DATABASE));
			Organ o=(Organ) CacheUtils.getObjectValue(manager, CacheUtils.ORGAN_DATASOURCE, user.getUserId()+"");
			/**
			 * 添加日志记录
			 */
			logService.insert(CommonUtils.getRemoteIp(request),user.getUserId()+"",
					user.getUserName(),"用户登录系统成功",1);
			
			request.getSession().setAttribute("SESSIN_USERNAME",user.getUserName());
			return result;
		} else {
			return result;
			}
	}
	
	/**
	 * 用户退出客户端
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/loginOut.do")
	@ResponseBody
	public ReturnResult loginOut(@RequestParam(value="sessionId",required=true)String sessionId,
			HttpServletRequest request) {
		HttpSession session=(HttpSession) request.getSession();
		
		User user=(User) session.getAttribute("User");
		logService.insert(CommonUtils.getRemoteIp(request),user.getUserId()+"",
				user.getUserName(),"退出系统成功",1);
		session.invalidate();
		CacheUtils.invalidateValue(manager, CacheUtils.ORGAN_DATASOURCE, user.getUserId()+"");
		return ReturnResult.SUCCESS("成功退出！");
	}

	/**
	 * 更新用户密码
	 * @param request
	 * @param oldPwd 旧密码
	 * @param newPwd 新密码
	 * @param cfmPwd 确认密码
	 * @return
	 */
	@RequestMapping(value = "/updatePassword.do")
	@ResponseBody
	public ReturnResult updatePassword(HttpServletRequest request,
			@RequestParam(value = "newPwd", required = true) String newPwd,
			@RequestParam(value = "oldPwd", required = true) String oldPwd,
			@RequestParam(value = "cfmPwd", required = true) String cfmPwd) {
		ReturnResult result = new ReturnResult();
		try{
			PrivilegeUser pu = PrivilegeCache.currentUser();
			result = userService.updateUserPassword(pu.getID(), oldPwd,newPwd,cfmPwd);
			//logService.AddOperateLog(pu.getShowName(), CommonUtils.getRemoteIp(request),
					//"用户【" + pu.getShowName() + "】修改密码成功! ");
			pu.setPassword(Md5Encrypt.md5(newPwd));
			return result;
		} catch(Exception e){
			log.error(e.getMessage(), e);
			return result.FAILUER(e.getMessage());
		}
		
	}
	
	/**
	 * 添加用户
	 * @param user 用户信息
	 * @param rolesId 角色ID
	 * @param modulesId 模块ID
	 * @return
	 */
	@RequestMapping(value = "/addUser.do")
	@ResponseBody
	public ReturnResult addUser(User user,String rolesId,String modulesId) {
		ReturnResult result = new ReturnResult();
		try{
			Integer[] rolesIds = JsonUtils.toObj(rolesId, Integer[].class);
			Integer[] modulesIds = JsonUtils.toObj(modulesId, Integer[].class);		
			result = userService.addUser(user, rolesIds, modulesIds);
			return result;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return result.FAILUER(e.getMessage());
		}		
	}
	
	/**
	 * 删除用户
	 * @param 用户id
	 * @return
	 */
	@RequestMapping(value = "/deleteUser.do")
	@ResponseBody
	public ReturnResult deleteUser(
			@RequestParam(value = "userId", required =true) Long userId) {
		ReturnResult result = new ReturnResult();
		try{
			result = userService.deleteUser(userId);
			return result;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return result.FAILUER(e.getMessage());
		}
	}
	
	/**
	 * 批量删除多个用户
	 * @param userIds 用户ID号
	 * @return
	 */
	@RequestMapping(value = "/deleteUsers.do")
	@ResponseBody
	public ReturnResult deleteUsers(
			@RequestParam(value = "userIds", required =true) String userIds) {
		//json转换
		Long[] ids = JsonUtils.toObj(userIds, Long[].class);
		ReturnResult result = new ReturnResult();
		try{
			result = userService.deleteUsers(ids);
			return result;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return result.FAILUER(e.getMessage());
		}
	}
	
	/**
	 * 修改用户信息
	 * @param organ
	 * @return
	 */
	@RequestMapping(value = "/modifyUser.do")
	@ResponseBody
	public ReturnResult modifyUser(User user,String rolesId,String modulesId) {		
		ReturnResult result = new ReturnResult();
		try{		
		Integer[] rolesIds = JsonUtils.toObj(rolesId, Integer[].class);
		Integer[] modulesIds = JsonUtils.toObj(modulesId, Integer[].class);		
		result = userService.updateUser(user, rolesIds, modulesIds);
		return result;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return result.FAILUER(e.getMessage());
		}
	}
	
	/**
	 * 根据用户ID查询用户信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/queryUserDetail.do")
	@ResponseBody
	public ReturnResult queryUserDetail(
			@RequestParam(value = "id", required =true) Long id) {		
		User user = userService.getUserById(id);
		return ReturnResult.SUCCESS(user);
	}
	/**
	 * 根据条件分页查询用户列表
	 * @param query
	 * @return
	 */
	@RequestMapping(value = "/queryUserPageList.do")
	@ResponseBody
	public ReturnResult queryUserPageList(UserQuery query) {		
		Pager<User> page = userService.getPageList(query);
		return ReturnResult.SUCCESS(page);
	}
	
	/**
	 * 条件查询
	 * @param query
	 * @return
	 */
	@RequestMapping(value = "/queryUserList.do")
	@ResponseBody
	public ReturnResult queryUserList(UserQuery query) {		
		List<User> users = userService.getQueryList(query);
		return ReturnResult.SUCCESS(users);
	}

	/**
	 * 用户角色分配
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "/allotUserRoles.do")
	@ResponseBody
	public ReturnResult allotUserRoles(@RequestParam(value = "userId", required =true)Long userId, String rolesId) {
		try{
		//json转换
		Integer[] rolesIds = JsonUtils.toObj(rolesId, Integer[].class);
		userRoleService.allotUserRoles(userId, rolesIds);
		return ReturnResult.SUCCESS("成功");
	} catch (Exception e) {
		log.error(e.getMessage(), e);
		return ReturnResult.FAILUER(e.getMessage());
	}
	}
	
	/**
	 * 用户权限分配
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "/allotUserModules.do")
	@ResponseBody
	public ReturnResult allotUserModules(@RequestParam(value = "userId", required =true)Long userId, String modulesId) {
		try{
		Integer[] modulesIds = JsonUtils.toObj(modulesId, Integer[].class);
		userAddModuleService.allotUserModules(userId, modulesIds);
		return ReturnResult.SUCCESS("成功");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ReturnResult.FAILUER(e.getMessage());
		}
	}
	
	/**
	 * 修改状态信息
	 * @param organ
	 * @return
	 */
	@RequestMapping(value = "/modifyUserState.do")
	@ResponseBody
	public ReturnResult modifyUserState(@RequestParam(value = "userId", required =true)Long userId,
			@RequestParam(value = "state", required =true)Integer state) {	
		ReturnResult result = new ReturnResult();
		try{
		User user = new User();
		user.setState(state);
		user.setUserId(userId);
		userService.updateUserByMySelect(user);		
		return ReturnResult.SUCCESS();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return result.FAILUER(e.getMessage());
		}
	}
}
