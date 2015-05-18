package com.tianyi.bph.web.controller.map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tianyi.bph.BaseLogController;
import com.tianyi.bph.domain.system.Organ;
import com.tianyi.bph.service.system.OrganService;

/**
 * 地图控制类
 * @author Mr A
 *
 */
@Controller
@RequestMapping("/map")
public class MapController extends BaseLogController{

	@Autowired
	private OrganService organService;
	
	@RequestMapping(value = {"/initMap.do", "/initMap.action"})
	@ResponseBody
	public ModelAndView initMap(
			@RequestParam(value = "organId", required = true, defaultValue = "1") Integer organId,
			HttpServletRequest request){
		ModelAndView mv = new ModelAndView("/base/map/map.jsp");
		Organ organ = new Organ();
		if (organId != null) {
			organ = organService.getOrganByPrimaryKey(organId);
		}
		mv.addObject("organ", organ);
		mv.addObject("num", "500");
		return mv ;
	}
}
