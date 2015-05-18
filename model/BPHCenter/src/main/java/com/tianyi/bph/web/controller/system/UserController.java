/**
 * @author fantedan@tieserv.com
 *
 * @date  2015-1-21 上午11:25:48
 */
package com.tianyi.bph.web.controller.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.ehcache.CacheManager;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.tianyi.bph.BaseLogController;
import com.tianyi.bph.common.Constants;
import com.tianyi.bph.common.MessageCode;
import com.tianyi.bph.common.PageReturn;
import com.tianyi.bph.common.ReturnResult;
import com.tianyi.bph.common.SystemConfig; 
import com.tianyi.bph.common.ehcache.CacheUtils;
import com.tianyi.bph.domain.system.Organ;
import com.tianyi.bph.domain.system.Role;
import com.tianyi.bph.domain.system.User;
import com.tianyi.bph.query.basicdata.PoliceJJVM;
import com.tianyi.bph.query.system.OrganQuery;
import com.tianyi.bph.query.system.RoleQuery;
import com.tianyi.bph.query.system.UserQuery;
import com.tianyi.bph.rest.CommonUtils;
import com.tianyi.bph.service.basicdata.PoliceService;
import com.tianyi.bph.service.system.LogService;
import com.tianyi.bph.service.system.ModuleService;
import com.tianyi.bph.service.system.OrganService;
import com.tianyi.bph.service.system.RoleService;
import com.tianyi.bph.service.system.UserOtherOrganService;
import com.tianyi.bph.service.system.UserRoleService;
import com.tianyi.bph.service.system.UserService;
import com.tianyi.bph.web.cache.UserCache;

/**
 * web用户管理
 * @author fantedan@tieserv.com
 * @date 2015-1-13 上午10:44:50
 */
@Controller
@RequestMapping("/admin")
public class UserController extends BaseLogController {
	
	private static final Logger logger=LoggerFactory.getLogger(UserController.class);
	
    @Autowired UserService userService;
    @Autowired private OrganService organService;
    @Autowired public UserRoleService userRoleService;
    @Autowired PoliceService policeService;

    @Autowired LogService logService;
    @Autowired RoleService roleService;
    @Autowired ModuleService moduleService;
    @Autowired UserOtherOrganService userOtherService;
    private CacheManager manager;

	@Autowired
	public void setManager(@Qualifier("ehCacheManager") CacheManager manager) {
		this.manager = manager;
	}
   	
	/**
	 * web用户登录
	 * @param request
	 * @param username 用户名
	 * @param password 用户密码
	 * @return
	 */
	@RequestMapping(value="/login.action")
	@ResponseBody
	public ReturnResult login(
			HttpServletRequest request,
			@RequestParam(value = "username", required =true) String username,
			@RequestParam(value = "password", required = true) String password) {
		ReturnResult result = new ReturnResult();
		String reqIP=request.getRemoteAddr();
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		logger.debug("收到来自【"+reqIP+"】请求！");
		result = userService.userLogin(username, password);	
		if (MessageCode.STATUS_SUCESS == result.getCode()) {
			User user = (User) result.getData();
			HttpSession session=request.getSession();
			session.setAttribute("User", user);
			session.setAttribute("SESSIN_USERNAME",user.getUserName());
			//CacheUtils.updateValue(manager, CacheUtils.USER_BASE_DATA, request.getSession().getId(), session);
			//添加日志记录
			//logService.insert(CommonUtils.getRemoteIp(request),user.getUserId()+"",user.getUserName(),"用户登录系统成功",1);
			
			OrganQuery query =new OrganQuery();
			query.setId(user.getOrgId());
			query.setPath(user.getOrganPath());
			CacheUtils.updateValue(manager, CacheUtils.ORGAN_DATASOURCE, user.getUserId()+"", organService.getOrganTree(query, SystemConfig.DATABASE));
			
			getModule(user,request);
			map.put("msg","登录成功");
			map.put("id",user.getOrgId());
			return ReturnResult.MESSAGE(MessageCode.STATUS_SUCESS,MessageCode.SELECT_SUCCESS,map);
		} else {
			map.put("msg","用户名或密码不匹配！");
			return ReturnResult.MESSAGE(MessageCode.STATUS_FAIL,"登录失败",map);
		}
	}
	
	/**
	 * web用户登录
	 * @param request
	 * @param username 用户名
	 * @param password 用户密码
	 * @return
	 */
	@RequestMapping(value="/clientLogin.do")
	@ResponseBody
	public ModelAndView clientLogin(
			HttpServletRequest request,
			@RequestParam(value = "username", required =true) String username,
			@RequestParam(value = "password", required = true) String password) {
		ReturnResult result = new ReturnResult();
		String reqIP=request.getRemoteAddr();
		
		logger.debug("收到来自【"+reqIP+"】请求！");
		result = userService.userLogin(username, password);	
		if (MessageCode.STATUS_SUCESS == result.getCode()) {
			User user = (User) result.getData();			
			HttpSession session=request.getSession();
			session.setAttribute("User", user);
			session.setAttribute("SESSIN_USERNAME",user.getUserName());
			UserCache.addSession(request.getSession().getId(), session);
			//添加日志记录
			//logService.AddOperateLog(username, CommonUtils.getRemoteIp(request),"用户登录系统成功！");
			getModule(user,request);//获取功能权限集合
			
			return new ModelAndView(new RedirectView(request.getContextPath()+"/admin/gotoUserList.do?organId="+user.getOrgId()));
		} else {
			return new ModelAndView("/index.jsp").addObject("errMsg", "用户名或密码不匹配！");
			}
	}
	
	
	public void getModule(User user,HttpServletRequest request){
		List<String> functionList=userRoleService.getFunctionsByUserId(user.getUserId());
		//系统管理功能集合
		List<String> conductArray=moduleService.findModuleByParentId(Integer.parseInt(SystemConfig.SYSTEM_MANAGER));
		//获取基础数据集合
		List<String> baseArray=moduleService.findModuleByParentId(Integer.parseInt(SystemConfig.BASE_MANAGER));
		//勤务报备
		List<String> dutyArray=moduleService.findModuleByParentId(Integer.parseInt(SystemConfig.DUTY_MANAGER));
		//报备分组
		List<String> dutyReportGroup=moduleService.findModuleByParentId(Integer.parseInt(SystemConfig.REPORT_GROUP));
		 
		 request.getSession().setAttribute("funList", functionList);
		 request.getSession().setAttribute("baseArray", baseArray);
		 request.getSession().setAttribute("conductArray", conductArray);
		 request.getSession().setAttribute("dutyArray", dutyArray);
		 request.getSession().setAttribute("dutyReportGroup", dutyReportGroup);
		 
	}
	
	
	/**
	 * WEB用户退出客户端
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/logout.do")
	public ModelAndView logout(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("/index.jsp");
		//String sessionId=request.getSession().getId();
		/*HttpSession session=(HttpSession) CacheUtils.getObjectValue(manager,
				CacheUtils.USER_BASE_DATA,sessionId);*/
		HttpSession session=(HttpSession) request.getSession();
		User user=(User) session.getAttribute("User");

		logService.insert(CommonUtils.getRemoteIp(request),user.getUserId()+"",
				user.getUserName(),"退出系统成功",1);
		CacheUtils.invalidateValue(manager, CacheUtils.ORGAN_DATASOURCE, user.getUserId()+"");
		session.invalidate();
		//CacheUtils.invalidateValue(manager, CacheUtils.USER_BASE_DATA, sessionId);
		return mv;
	}
	/**
	 * web跳转到用户列表
	 * @param request
	 * @param username 用户名
	 * @return
	 */
	@RequestMapping({"/gotoUserList.do","/gotoUserList.action"})
	@ResponseBody
	public ModelAndView gotoList(
			HttpServletRequest request,
			@RequestParam(value="userName",required=false)String userName,
			@RequestParam(value="organId",required=false)Integer organId,
			@RequestParam(value="organPath",required=false)String organPath,
			@RequestParam(value="sessionId",required=false)String sessionId,
			@RequestParam(value="searchType",required=true,defaultValue="1")Integer searchType,
			@RequestParam(value="pageSize",required=false,defaultValue="10")Integer pageSize,
			@RequestParam(value="pageNo",required=false,defaultValue="1")Integer pageNo){
		UserQuery query=new UserQuery();
		User user=(User) request.getAttribute("User");
		if(!StringUtils.isEmpty(userName)){query.setUserName(userName);}
		if(organId!=null){
			query.setOrganId(organId);
		}else{
			query.setOrganId(user.getOrgId());
		}		
		query.setPageNo(pageNo);
		query.setPageSize(pageSize);
		ModelAndView  mv=new ModelAndView("/base/user/userList.jsp");
		Organ organ= new Organ();
		organ = organService.getOrganByPrimaryKey(organId);
		List<Role> roleList = roleService.getQueryList(null);
		query.setOrganId(organId);
		mv.addObject("query",query);
		mv.addObject("organ",organ);
		mv.addObject("num",SystemConfig.SYSTEM_MANAGER);
		mv.addObject("roleList",roleList);
		return mv;
	}
	
	/**
	 * 获取机构列表
	 * @param request
	 * @param username 用户名
	 * @return
	 */
	@RequestMapping(value = "/getUserList.do")
	@ResponseBody
	public PageReturn getUserList(
			HttpServletRequest request,
			@RequestParam(value="userName",required=false)String userName,
			@RequestParam(value="organPath",required=false)String organPath,
			@RequestParam(value="organId",required=true)Integer organId,
			@RequestParam(value="sessionId",required=false)String sessionId,
			@RequestParam(value="expandeds",required=false)String expandeds,
			@RequestParam(value="searchType",required=true,defaultValue="1")int searchType,
			@RequestParam(value="pageSize",required=false,defaultValue="10")Integer pageSize,
			@RequestParam(value="pageNo",required=false,defaultValue="1")Integer pageNo){
		UserQuery query=new UserQuery();
		if(!StringUtils.isEmpty(userName) || !"".equals(userName)){
			System.out.println("*****************userName="+userName);
			query.setUserName(userName);
			}
		if(Constants.SEARCH_TYPE_NO_CHILD == searchType ){
			query.setOrganId(organId);
		}
		if(Constants.SEARCH_TYPE_CHILD == searchType ){
			query.setOrganPath(organPath);
		}
		query.setPageNo(pageNo);
		query.setPageSize(pageSize);
		List<User> userList =(List<User>) userService.getUserPageList(query);
		int totalRows = 0;
			totalRows = userService.getUserCount(query);
		@SuppressWarnings("unused")
		HttpSession session=request.getSession();
		if(!StringUtils.isEmpty(sessionId)){//客户端
			session=UserCache.getSession(sessionId);
		}
		return PageReturn.MESSAGE(MessageCode.STATUS_SUCESS,MessageCode.SELECT_SUCCESS,totalRows,userList);
	}
	/**
	 * web跳转到用户新增页面
	 * @param request
	 * @param id 主键id
	 * @return
	 */
	@RequestMapping({"/gotoUserAdd.do","/gotoUserAdd.action"})
	@ResponseBody
	public ModelAndView gotoUserAdd(
			HttpServletRequest request,
			@RequestParam(value = "organId", required =false) Integer organId,
			@RequestParam(value = "userId", required =false) Long userId){
		Organ organ= new Organ();
		if(organId!=null){
			organ = organService.getOrganByPrimaryKey(organId);
		}
		ModelAndView  mv=new ModelAndView("/base/user/userAdd.jsp");
		User user=(User) request.getAttribute("User");
		RoleQuery roleQuery=new RoleQuery();
		roleQuery.setUserId(user.getUserId());
		List<Role> roleList = roleService.getQueryList(roleQuery);
		List<PoliceJJVM> policeList = policeService.getPoliceInfo(organId,Constants.SEARCH_TYPE_NO_CHILD);
		mv.addObject("user",user);
		mv.addObject("organId",organId);
		mv.addObject("organ",organ);
		mv.addObject("roleList",roleList);
		mv.addObject("policeList",policeList);
		return mv;
	}
	/**
	 * web跳转到用户信息修改页面
	 * @param request
	 * @param id 主键id
	 * @return
	 */
	@RequestMapping({"/gotoUserEdit.do","/gotoUserEdit.action"})
	@ResponseBody
	public ModelAndView gotoUserEdit(
			HttpServletRequest request,
			@RequestParam(value = "organId", required =false) Integer organId,
			@RequestParam(value = "userId", required =false) Long userId){
		User user = new User();
		Organ organ= new Organ();
		if(userId != null){
			user = userService.getUserById(userId);
			}
		User opUser=(User) request.getAttribute("User");
		RoleQuery roleQuery=new RoleQuery();
		roleQuery.setUserId(opUser.getUserId());
		if(organId!=null){
			organ = organService.getOrganByPrimaryKey(organId);
		}
		ModelAndView  mv=new ModelAndView("/base/user/userEdit.jsp");
		List<Role> roleList = roleService.getQueryList(roleQuery);
		List<Role> userRoleList = roleService.getRolesByUserId(userId);
		List<PoliceJJVM> policeList = policeService.getPoliceInfo(organId,Constants.SEARCH_TYPE_NO_CHILD);
		Role role = new Role();
		Role userRole = new Role();
		for (int i = 0; i < roleList.size(); i++) {
			role = roleList.get(i);
			role.setIsCheck("nochecked");
			for (int j = 0; j < userRoleList.size(); j++) {
				userRole = userRoleList.get(j);
				if(role.getId() == userRole.getId()){
					role.setIsCheck("checked");
					break;
				}
			}	
	    }
		mv.addObject("user",user);
		mv.addObject("organId",organId);
		mv.addObject("organ",organ);
		mv.addObject("roleList",roleList);
		mv.addObject("policeList",policeList);
		return mv;
	}
	/**
	 * web用户信息新增
	 * @param request
	 * @param id 主键id
	 * @return
	 */
	@RequestMapping({"/userAdd.do","/userAdd.action"})
	@ResponseBody
	public ReturnResult userAdd(
			HttpServletRequest request,
			@RequestParam(value="loginName",required=true)String loginName,
			@RequestParam(value="userName",required=true)String userName,
			@RequestParam(value="password",required=true)String password,
			@RequestParam(value="rolesId",required=true)String rolesId,
			@RequestParam(value="userOtherOrgans",required=false)String userOtherOrgans,
			@RequestParam(value="note",required=false)String note,
			@RequestParam(value="state",required=true,defaultValue="0")Integer state,
			@RequestParam(value="policeId",required=false)Integer policeId,
			@RequestParam(value="orgId",required=true)Integer orgId){
		User user = new User();
		if(!StringUtils.isEmpty(loginName)){user.setLoginName(loginName);}
		if(!StringUtils.isEmpty(userName)){user.setUserName(userName);}
		if(!StringUtils.isEmpty(password)){user.setPassword(password);}
		if(!StringUtils.isEmpty(note)){user.setNote(note);}
		if(!StringUtils.isEmpty(String.valueOf(orgId))){user.setOrgId(orgId);}
		if(!StringUtils.isEmpty(String.valueOf(policeId))){user.setPoliceUserId(policeId);}
		ReturnResult result = new ReturnResult();		
		Integer[] rolesIds = null;
		if(StringUtils.isEmpty(userName)){
			user.setUserName(loginName);
		}
		try{
			if(!StringUtils.isEmpty(rolesId)){
				String[] strr=rolesId.split(",");
				rolesIds=new Integer[strr.length];
				for(int i=0;i<strr.length;i++){
					String str=strr[i];
					Integer intr=Integer.parseInt(str);
					rolesIds[i]=intr;
				}
			}
			Integer[] userOrganIds = null;
			if(!StringUtils.isEmpty(userOtherOrgans)){
				String[] stru=userOtherOrgans.split(",");
				userOrganIds=new Integer[stru.length];
				for(int i=0;i<stru.length;i++){
					String str=stru[i];
					Integer intu=Integer.parseInt(str);
					userOrganIds[i]=intu;
				}
			}
				result = userService.addUser(user, rolesIds, userOrganIds);
				addLog(request, "添加用户成功", 2);
			return result;
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				return ReturnResult.FAILUER(e.getMessage());
			}
	}
	
	/**
	 * web用户信息修改
	 * @param request
	 * @param id 主键id
	 * @return
	 */
	@RequestMapping(value="/userModify.do")
	@ResponseBody
	public ReturnResult userModify(
			HttpServletRequest request,
			@RequestParam(value ="userId", required =true) Long userId,
			@RequestParam(value="loginName",required=true)String loginName,
			@RequestParam(value="userName",required=true)String userName,
			@RequestParam(value="password",required=false)String password,
			@RequestParam(value="rolesId",required=false)String rolesId,
			@RequestParam(value="userOtherOrgans",required=false)String userOtherOrgans,
			@RequestParam(value="modulesId",required=false)String modulesId,
			@RequestParam(value="note",required=false)String note,
			@RequestParam(value="policeId",required=false)Integer policeId,
			@RequestParam(value="state",required=true,defaultValue="0")Integer state){
		User user = new User();
		//if(!StringUtils.isEmpty(loginName)){user.setLoginName(loginName);}
		if(!StringUtils.isEmpty(userName)){user.setUserName(userName);}
		//if(!StringUtils.isEmpty(password)){user.setPassword(password);}
		if(!StringUtils.isEmpty(String.valueOf(policeId))){user.setPoliceUserId(policeId);}
		if(!StringUtils.isEmpty(note)){user.setNote(note);}
		user.setUserId(userId);
		ReturnResult result = new ReturnResult();		
		Integer[] rolesIds = null;
		try{
			if(!StringUtils.isEmpty(rolesId)){
				String[] strr=rolesId.split(",");
				rolesIds=new Integer[strr.length];
				for(int i=0;i<strr.length;i++){
					String str=strr[i];
					Integer intr=Integer.parseInt(str);
					rolesIds[i]=intr;
				}
			}
			Integer[] userOrganIds = null;
			if(!StringUtils.isEmpty(userOtherOrgans)){
				String[] stru=userOtherOrgans.split(",");
				userOrganIds=new Integer[stru.length];
				for(int i=0;i<stru.length;i++){
					String str=stru[i];
					Integer intu=Integer.parseInt(str);
					userOrganIds[i]=intu;
				}
			}
			result = userService.updateUser(user, rolesIds, userOrganIds);
			addLog(request, "修改用户成功", 2);
			return result;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ReturnResult.FAILUER(e.getMessage());
		}
	}
	
	/**
	 * web用户信息删除
	 * @param request
	 * @param id 主键id
	 * @return
	 */
	@RequestMapping(value = "/userDelete.do")
	@ResponseBody
	public ReturnResult userDelete(
			HttpServletRequest request,
			@RequestParam(value = "userId", required =true) Long userId){
		//User user = new User();	
		ReturnResult result = new ReturnResult();
		try{
			result = userService.deleteUser(userId);
			addLog(request, "删除用户成功", 2);
			return result;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ReturnResult.FAILUER(e.getMessage());
		}
	}
	
	@RequestMapping(value="/testTree.do")
	public ReturnResult testTree(
			@RequestParam(value="userId",required=true) String userId){
		
		List<Organ> organList=new ArrayList<Organ>();
		List<String> strList=userOtherService.getOrganIdByUserId(new Long(userId));
		for (String string : strList) {
			Organ o=organService.getOrganByPrimaryKey(Integer.parseInt(string));
			organList.add(o);
		}
		return null;
	}

	/**
	 * 修改密码界面 跳转
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = {"/gotoUpdatePassword.do", "/gotoUpdatePassword.action"})
	@ResponseBody
	public ModelAndView gotoUpdatePassword(
			@RequestParam(value = "userId") Long userId){
		User user = new User();
		if(userId != null){
			user = userService.getUserById(userId);
		}
		ModelAndView mv = new ModelAndView("/base/user/userUpdatePwd.jsp");
		mv.addObject("user", user);
		return mv;
	}
	/**
	 * web端修改密码功能
	 * 2015-5-6
	 * @param userId
	 * @param newPwd
	 * @param oldPwd
	 * @param cfmPwd
	 * @return
	 */
	@RequestMapping(value = {"/updatePassword.do", "/updatePassword.action"})
	@ResponseBody
	public ReturnResult updatePassword(
			@RequestParam(value = "userId") Long userId,
			@RequestParam(value = "newPwd", required = true) String newPwd,
			@RequestParam(value = "oldPwd", required = true) String oldPwd,
			@RequestParam(value = "cfmPwd", required = true) String cfmPwd){
		ReturnResult result = new ReturnResult();
		try{
			//PrivilegeUser pu = PrivilegeCache.currentUser();
			result = userService.updatePassword(userId, oldPwd, newPwd, cfmPwd);
			//pu.setPassword(Md5Encrypt.md5(newPwd));
			return result;
		}catch(Exception e){
			return result.FAILUER(e.getMessage());
		}
	}
	
	/**
	 * 重置密码
	 * <br> 2015-5-8
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = {"/resetPassword.do", "/resetPassword.action"})
	@ResponseBody
	public ReturnResult resetPassword(
			@RequestParam(value = "userId") Long userId){
		if(userId == null){
			return ReturnResult.FAILUER("重置密码失败，失败原因：userId == null");
		}
		return userService.resetPassword(userId);
	}
}
