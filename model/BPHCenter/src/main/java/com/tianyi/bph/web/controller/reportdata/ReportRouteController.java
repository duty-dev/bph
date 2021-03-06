package com.tianyi.bph.web.controller.reportdata;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tianyi.bph.domain.system.Organ;
import com.tianyi.bph.domain.system.User;
import com.tianyi.bph.query.system.OrganQuery;
import com.tianyi.bph.service.system.OrganService;

@Controller
@RequestMapping("/reportRouteWeb")
public class ReportRouteController {
	@Autowired
	private OrganService organService;

	/**
	 * web跳转到警情统计
	 * 
	 * @param request
	 * @param
	 * 
	 * @return
	 */
	@RequestMapping({ "/gotoAlarmStatistics.do", "/gotoAlarmStatistics.action" })
	@ResponseBody
	public ModelAndView gotoAlarmStatistics(
			@RequestParam(value = "organId", required = false) Integer organId,
			@RequestParam(value = "pageType", required = false) Integer pageType,
			HttpServletRequest request) {
		ModelAndView mv = new ModelAndView(
				"/reportdata/alarmstatistics/alarmStatistics.jsp");
		if(pageType==null){
			pageType = 1;
		}
		if(pageType==3||pageType==4){
			 mv = new ModelAndView("/reportdata/alarmstatistics/alarmStatisticsx.jsp");
		}
		User user = (User) request.getAttribute("User");
		//if (organId == null) {
			organId = user.getOrgId();
		//}  
		Organ organ = new Organ();
		organ = organService.getOrganByPrimaryKey(organId);
		mv.addObject("organId", organId);   
		mv.addObject("organName", organ.getShortName());   
		mv.addObject("pageType",pageType);   
		mv.addObject("num", "1000");
		return mv;
	}  
 

	/**
	 * web跳转到四色预警
	 * 
	 * @param request
	 * @param
	 * 
	 * @return
	 */
	@RequestMapping({ "/gotoFourColorWarning.do",
			"/gotoFourColorWarning.action" })
	@ResponseBody
	public ModelAndView gotoFourColorWarning(
			@RequestParam(value = "organId", required = false) Integer organId,
			HttpServletRequest request) {
		ModelAndView mv = new ModelAndView(
				"/reportdata/fourcolorwarning/fourColorwarning.jsp");
		User user = (User) request.getAttribute("User");
		//if (organId == null) {
			organId = user.getOrgId();
		//}
		Organ organ = new Organ();
		organ = organService.getOrganByPrimaryKey(organId);   
		
		mv.addObject("organ", organ); 
		mv.addObject("organName", organ.getShortName()); 
		mv.addObject("organCode", organ.getCode()); 
		mv.addObject("organPath", organ.getPath());  
		mv.addObject("num", "1000");
		return mv;
	}
	/**
	 * web跳转到四色预警
	 * 
	 * @param request
	 * @param
	 * 
	 * @return
	 */
	@RequestMapping({ "/gotoFourColorWarningAdd.do",
			"/gotoFourColorWarningAdd.action" })
	@ResponseBody
	public ModelAndView gotoFourColorWarningAdd(
			@RequestParam(value = "organId", required = false) Integer organId,
			HttpServletRequest request) {
		ModelAndView mv = new ModelAndView(
				"/reportdata/fourcolorwarning/fourColorwarningAdd.jsp");
		User user = (User) request.getAttribute("User");
		if (organId == null) {
			organId = user.getOrgId();
		} 
		mv.addObject("organId", organId);   
		mv.addObject("num", "1000");
		return mv;
	}
	/**
	 * web跳转到四色预警
	 * 
	 * @param request
	 * @param
	 * 
	 * @return
	 */
	@RequestMapping({ "/gotoFourColorWarningEdit.do",
			"/gotoFourColorWarningEdit.action" })
	@ResponseBody
	public ModelAndView gotoFourColorWarningEdit( 
			@RequestParam(value = "organId", required = false) Integer organId,
			@RequestParam(value = "caseId", required = false) Integer caseId,
			HttpServletRequest request) {
		ModelAndView mv = new ModelAndView(
				"/reportdata/fourcolorwarning/fourColorwarningEdit.jsp");
		User user = (User) request.getAttribute("User");
		if (organId == null) {
			organId = user.getOrgId();
		} 
		mv.addObject("organId", organId);  
		mv.addObject("caseId", caseId);  
		mv.addObject("num", "1000");
		return mv;
	}
}
