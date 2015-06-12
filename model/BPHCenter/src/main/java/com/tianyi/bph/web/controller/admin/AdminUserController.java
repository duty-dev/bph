/**
 * @author fantedan@tieserv.com
 *
 * @date  2015-1-21 上午11:25:48
 */
package com.tianyi.bph.web.controller.admin;

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
 * 后台管理
 * @author fantedan@tieserv.com
 * @date 2015-1-13 上午10:44:50
 */
@Controller
@RequestMapping("/adminUser")
public class AdminUserController extends BaseLogController {
	
	private static final Logger logger=LoggerFactory.getLogger(AdminUserController.class);
	
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
	 * 后台管理用户登录
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
		if(!"admin".equalsIgnoreCase(username)){
			map.put("msg","管理用户名不正确！");
			return ReturnResult.MESSAGE(MessageCode.STATUS_FAIL,"登录失败",map);
		}
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
	
	public void getModule(User user,HttpServletRequest request){
		List<String> functionList=userRoleService.getFunctionsByUserId(user.getUserId());
		//系统管理功能集合
		List<String> conductArray=moduleService.findModuleByParentId(Integer.parseInt(SystemConfig.SYSTEM_MANAGER));
		//获取基础数据集合
		List<String> baseArray=moduleService.findModuleByParentId(Integer.parseInt(SystemConfig.BASE_MANAGER));
		//勤务报备
		List<String> dutyArray=moduleService.findModuleByParentId(Integer.parseInt(SystemConfig.DUTY_MANAGER));
		//研判分析
		List<String> analyzingArray=moduleService.findModuleByParentId(Integer.parseInt(SystemConfig.ANALYZING_MANAGER));
		 
		 request.getSession().setAttribute("funList", functionList);
		 request.getSession().setAttribute("baseArray", baseArray);
		 request.getSession().setAttribute("conductArray", conductArray);
		 request.getSession().setAttribute("dutyArray", dutyArray);
		 request.getSession().setAttribute("analyzingArray", analyzingArray);
		 
	}
}
