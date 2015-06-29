package com.tianyi.bph.web.controller.system;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tianyi.bph.common.Constants;
import com.tianyi.bph.common.JsonUtils;
import com.tianyi.bph.common.SystemConfig;
import com.tianyi.bph.domain.basicdata.Police;
import com.tianyi.bph.domain.system.Area;
import com.tianyi.bph.domain.system.Organ;
import com.tianyi.bph.query.basicdata.PoliceJJVM;
import com.tianyi.bph.service.basicdata.PoliceService;
import com.tianyi.bph.service.system.AreaService;
import com.tianyi.bph.service.system.OrganService;
import com.tianyi.bph.vo.DisplayProperty;

/**
 * 巡区 社区 辖区 web控制层
 * 
 * @author joe_chen
 * 
 */
@Controller
@RequestMapping("/web/Area")
public class AreaController {

	@Autowired
	private OrganService organService;
	@Autowired
	PoliceService policeService;
	@Autowired
	private AreaService areaService;

	@RequestMapping({ "/toArealist.action", "/toArealist.do" })
	public ModelAndView toGBPlatForm(HttpServletRequest request,
			HttpSession session,
			@RequestParam(value = "organId", required = false) Integer organId,
			@RequestParam(value = "areaType", required = true) Integer areaType) {
		// 用户验证
		// String username = (String) session.getAttribute("username");
		// service.getOrganTree(organId);
		ModelAndView view = null;
		if (areaType == 1) {
			view = new ModelAndView("/base/area/areaList.jsp");
		} else if (areaType == 2) {
			view = new ModelAndView("/base/area/communityAreaList.jsp");
		} else if (areaType == 3) {
			view = new ModelAndView("/base/area/mapAreaList.jsp");
		}
		if (organId != null && organId != 0) {
			view.addObject("organId", organId);
		}
		view.addObject("num", SystemConfig.BASE_MANAGER);
		return view;
	}

	@RequestMapping({ "/gotoAddArea.do", "/gotoAddArea.action" })
	@ResponseBody
	public ModelAndView gotoCardPointAdd(
			HttpServletRequest request,
			@RequestParam(value = "sessionId", required = false) String sessionId,
			@RequestParam(value = "organId", required = true) Integer organId) {

		Organ organ = organService.getOrganByPrimaryKey(organId);
		List<PoliceJJVM> policeList = policeService.getPoliceInfo(organId,
				Constants.SEARCH_TYPE_NO_CHILD);
		ModelAndView mv = new ModelAndView("/base/area/addArea.jsp");
		mv.addObject("organId", organId);
		mv.addObject(
				"organName",
				organ.getShortName() == null ? organ.getName() : organ
						.getShortName());
		mv.addObject("policeList", policeList); // 警员
		return mv;
	}

	@RequestMapping(value = { "/gotoDrawArea.do", "/gotoDrawArea.action" })
	@ResponseBody
	public ModelAndView gotoDrawArea(
			@RequestParam(value = "areaId") Integer areaId) {
		Area area = areaService.queryByPrimaryKey(areaId);
		ModelAndView mv = new ModelAndView("/base/area/areaDetailInfo.jsp");
		mv.addObject("area", area);
		String s = area.getDisplayProperty();
		DisplayProperty pro = null;
		if (StringUtils.hasLength(s)) {
			pro = (DisplayProperty) JsonUtils.toObj(s, DisplayProperty.class);
		}
		mv.addObject("displayProperty", pro == null ? new DisplayProperty(): pro);
		return mv;
	}

	@RequestMapping(value = { "/gotoModifiyArea.do", "/gotoModifiyArea.action" })
	@ResponseBody
	public ModelAndView gotoModifiyArea(
			@RequestParam(value = "areaId") Integer areaId) {
		Area area = areaService.queryByPrimaryKey(areaId);

		// 查看该卡点关联的警员信息
		List<Integer> policeIds = area.getRelationUserKeys();
		if (policeIds != null && policeIds.size() > 0) {
			StringBuffer policeStr = new StringBuffer();
			StringBuffer policeIdStr = new StringBuffer();
			for (Integer policeId : policeIds) {
				Police p = policeService.selectByPrimaryKey(policeId);
				if (p != null) {
					policeStr.append(p.getName() + ",");
					policeIdStr.append(policeId + ",");
				}
			}
			area.setManager(policeIdStr + "");
			area.setManagerName(policeStr + "");
		}
		ModelAndView mv = new ModelAndView("/base/area/modifiyArea.jsp");
		mv.addObject("area", area);
		return mv;
	}

}
