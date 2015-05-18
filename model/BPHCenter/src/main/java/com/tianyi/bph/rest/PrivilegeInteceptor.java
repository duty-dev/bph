/**
 * copy right 2012 sctiyi all rights reserved
 * create time:10:45:38 AM
 * author:ftd
 */
package com.tianyi.bph.rest;

import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;




import net.sf.json.JSONArray;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.tianyi.bph.CookieUtil;
import com.tianyi.bph.common.JsonUtils;
import com.tianyi.bph.common.ReturnResult;
import com.tianyi.bph.common.utils.DateUtils;
import com.tianyi.bph.domain.system.User;

/**
 * @author he
 * 系统拦截器
 */
public class PrivilegeInteceptor implements HandlerInterceptor {
	private final static Logger log=LoggerFactory.getLogger(PrivilegeInteceptor.class);
	
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
	}

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object handler) throws Exception {
		HttpSession session=request.getSession();
		
        String uri = request.getRequestURI();
        log.debug("收到请求："+uri+",会话["+session.getId()+","+DateUtils.format(new Date(session.getCreationTime()),DateUtils.YYYY_MM_DD_HH_MM_SS)+"]");
        
        //后台session控制
        if (uri.contains("login.action")||uri.contains("login.do")
        		||uri.contains("getModuleTreeByRoles.do")||uri.contains("getModuleTree.do")
        		||uri.contains("index.do")||uri.contains("install.do")||uri.contains("serviceSet")) {  
        	return true; 
        }else{
        	//String sessionId=request.getParameter("sessionId");
        	String organId=request.getParameter("organId");
        	String organPath=request.getParameter("organPath");
        	String expandeds=request.getParameter("expandeds");
        	String searchOrganName=request.getParameter("searchName");
        	String selectName=request.getParameter("selectName");
        	/*if(!StringUtils.isEmpty(sessionId)){//客户端
        		//session=UserCache.getSession(sessionId);
        		session=(HttpSession) CacheUtils.getObjectValue(manager,CacheUtils.USER_BASE_DATA,sessionId);
    			request.setAttribute("sessionId", sessionId);
        	}*/
        	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
        	User user=(User)session.getAttribute("User");
        	if(user==null){
        		response.setStatus(401);
        		ReturnResult rst=ReturnResult.FAILUER("用户未登陆！");
        		response.setContentType("text/html;charset=UTF-8");           
				response.setHeader("Cache-Control", "no-cache");
        		PrintWriter  writer=response.getWriter();
        		writer.write(JsonUtils.toJson(rst));
        		response.sendRedirect(basePath);//跳转到登陆界面
        		log.debug("会话["+session.getId()+","+DateUtils.format(new Date(session.getCreationTime()),DateUtils.YYYY_MM_DD_HH_MM_SS)+"]未登录！跳转登录页面。");
        		return false;
        	}
        	if(organId!= null && !StringUtils.isEmpty(String.valueOf(organId))){
    			session.setAttribute("organId", organId);
    		}
    		if(!StringUtils.isEmpty(organPath)){
    			session.setAttribute("organPath", organPath);
    		}
    		if(!StringUtils.isEmpty(expandeds)){
    			session.setAttribute("expandeds", expandeds);
    		}
    		if(searchOrganName !=null){
    			session.setAttribute("searchOrganName", searchOrganName);
    		}
    		if(!StringUtils.isEmpty(selectName)){
    			session.setAttribute("selectName", selectName);
    		}
        	request.setAttribute("organPath", session.getAttribute("organPath"));
			request.setAttribute("organId", session.getAttribute("organId"));
			request.setAttribute("expandeds", session.getAttribute("expandeds"));
			request.setAttribute("searchOrganName", session.getAttribute("searchOrganName"));
			request.setAttribute("selectName", session.getAttribute("selectName"));
			request.setAttribute("User",user);
        	return true;
        }
	}
}
