package com.tianyi.bph;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {
	
	public final int maxAge = 60 * 60 * 24 * 14;
	/** 
	 * 获取cookie的值 
	 * @param request 
	 * @param name cookie的名称 
	 * @return 
	 */  
	public static String getCookieByName(HttpServletRequest request, String name) {  
	    Map<String, Cookie> cookieMap = readCookieMap(request);  
	    if(cookieMap.containsKey(name)){  
	        Cookie cookie = (Cookie)cookieMap.get(name);  
	        String value = null;  
	        try {  
	            value=URLDecoder.decode(cookie.getValue(),"UTF-8");  
	        } catch (UnsupportedEncodingException e) {  
	            e.printStackTrace();  
	        }  
	        return value;  
	    }else{  
	        return null;  
	    }  
	}  
	  
	protected static Map<String, Cookie> readCookieMap(HttpServletRequest request) {  
	    Map<String, Cookie>  cookieMap= new HashMap<String, Cookie>(); 
	  if (null!=request) {
		  Cookie[] cookies = request.getCookies();  
		    if (null != cookies) {  
		        for (int i = 0; i < cookies.length; i++) {  
		            cookieMap.put(cookies[i].getName(), cookies[i]);  
		        }  
		    }  
	}
	    return cookieMap;  
	}  
	  

	/**
	 * 添加cookie
	 * 
	 * @param response
	 * @param name
	 *            cookie的名称
	 * @param value
	 *            cookie的值
	 * @param maxAge
	 *            cookie存放的时间(以秒为单位,假如存放三天,即3*24*60*60; 如果值为0,cookie将随浏览器关闭而清除)
	 */
	public static void addCookie(HttpServletResponse response, String name,
			String value, int maxAge) {
		try {
			// 意此处的 URLEncoder
			value = URLEncoder.encode(value, "UTF-8");
			Cookie cookie = new Cookie(name, value);
			cookie.setPath("/");
			if (maxAge > 0)
				cookie.setMaxAge(maxAge);
			response.addCookie(cookie);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除cookie
	 * @param request
	 * @param response
	 * @param cookiename
	 */
	
	public static void deleteCookie(HttpServletRequest request,HttpServletResponse response, String cookiename){
		Cookie[] cookies = request.getCookies();
		if (null != cookies) {  
		        for (int i = 0; i < cookies.length; i++) {  
		        	if (cookiename.equals(cookies[i].getName())) {
		        		Cookie cookie = new Cookie(cookies[i].getName(), null);
		        		cookie.setMaxAge(0);
		        		cookie.setPath("/");
						response.addCookie(cookie);
						System.out.println("删除的cookiename为："+cookies[i].getName());
						System.out.println("删除cookie成功。。。。");
					}
		        }  
		    }  
	}

}
