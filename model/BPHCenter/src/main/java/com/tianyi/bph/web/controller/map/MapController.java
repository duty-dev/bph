package com.tianyi.bph.web.controller.map;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tianyi.bph.BaseLogController;
import com.tianyi.bph.common.JsonUtils;
import com.tianyi.bph.common.ReturnResult;
import com.tianyi.bph.domain.system.CircleLayer;
import com.tianyi.bph.domain.system.Organ;
import com.tianyi.bph.domain.system.User;
import com.tianyi.bph.service.system.AsyncSendMessage;
import com.tianyi.bph.service.system.CardPointService;
import com.tianyi.bph.service.system.CircleLayerService;
import com.tianyi.bph.service.system.OrganService;
import com.tianyi.bph.vo.CircleLayerVo;
import com.tianyi.bph.vo.DisplayProperty;

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
	
	@Autowired CardPointService cardPointService;
	
	@Autowired CircleLayerService circleLayerService;
	
	@Autowired
	private AsyncSendMessage sendMessage;
	
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
	
	@RequestMapping(value = {"/initCircleMap.do", "/initCircleMap.action"})
	@ResponseBody
	public ModelAndView initCircleMap(
			@RequestParam(value = "organId", required = true, defaultValue = "1") Integer organId,
			HttpServletRequest request){
		ModelAndView mv = new ModelAndView("/base/map/circle/circleMap.jsp");
		Organ organ = new Organ();
		if (organId != null) {
			organ = organService.getOrganByPrimaryKey(organId);
		}
		mv.addObject("organ", organ);
		mv.addObject("num", "500");
		return mv ;
	}
	/**
	 * 绘制界面 跳转
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = {"/gotoDrawCircle.do", "/gotoDrawCircle.action"})
	@ResponseBody
	public ModelAndView gotoDrawCircle(
			@RequestParam(value = "circleId") Integer circleId){
		User user = new User();
		//CardPoint c = cardPointService.getCardPointById(44);
		CircleLayer c  = circleLayerService.getCircleLayerById(circleId);
		ModelAndView mv = new ModelAndView("/base/map/circle/circleDetailInfo.jsp");
		mv.addObject("user", user);
		mv.addObject("circleLayer", c);
		String s = c.getDisplayProperty();
		JSONObject jo = JSONObject.fromObject(s);
		DisplayProperty pro = (DisplayProperty) JSONObject.toBean(jo, DisplayProperty.class);
		// 验证RabbitMQ在Web端的使用
		try {
			JSONObject o = new JSONObject();
			o.accumulate("x", 104.06662);
			o.accumulate("y", 30.78559);
			sendMessage.asyncJsonData("routeData.H.51000.51001", o.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		mv.addObject("displayProperty", pro == null ?  new DisplayProperty() : pro);
		return mv;
	}
	
	/**
	 * 圈层查询
	 * @param query
	 * @return
	 */
	@RequestMapping(value="/queryCircleLayerList.do" ,produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String getCircleLayerList(HttpServletRequest request){
		
		List<CircleLayer> list = circleLayerService.getCircleLayerList();
		
		List<CircleLayerVo> reList = null;
		if (!list.isEmpty()) {
			reList = new ArrayList<CircleLayerVo>(list.size());
			for (CircleLayer circleLayer : list) {
				reList.add(new CircleLayerVo(circleLayer));
			}
		} 
		String s = JSONArray.fromObject(reList).toString();
		return s;
	}
	
	/**
	 * 修改圈层信息
	 * @param CircleLayer
	 * @return
	 */
	@RequestMapping(value = "/modifyCircleLayer.do", method = RequestMethod.POST)
	@ResponseBody
	public ReturnResult modifyCircleLayer(
			@RequestParam(value = "circleLayerVo") String circleLayerVo,
			HttpServletRequest request) {
		try {
			CircleLayerVo circleLayer = JsonUtils.toObj(circleLayerVo, CircleLayerVo.class);
			CircleLayer c = circleLayer.ctrate();
			c=circleLayerService.updateCircleLayer(c);
			addLog(request, "修改圈层成功", 2);
			return ReturnResult.SUCCESS("成功", c);
		} catch (Exception e) {
			return ReturnResult.FAILUER(e.getMessage());
		}
	}
	
	/**
	 * 常规警情界面 跳转
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = {"/initAlarmMap.do", "/initAlarmMap.action"})
	@ResponseBody
	public ModelAndView initAlarmMap(
			@RequestParam(value = "organId")Integer organId,
			HttpServletRequest request){
		ModelAndView mv = new ModelAndView("/base/map/nomalAlarm/nomalAlarmMap.jsp");
		Organ organ = new Organ();
		if (organId != null) {
			organ = organService.getOrganByPrimaryKey(organId);
		}
		mv.addObject("organ", organ);
		mv.addObject("num", "500");
		
		return mv;
	}
}
