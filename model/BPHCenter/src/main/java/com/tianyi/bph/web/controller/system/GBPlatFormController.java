package com.tianyi.bph.web.controller.system;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tianyi.bph.common.ReturnResult;
import com.tianyi.bph.common.SystemConfig;
import com.tianyi.bph.domain.system.GBOrgan;
import com.tianyi.bph.domain.system.OrganGBOrganKey;
import com.tianyi.bph.service.system.GBPlatFormService;

@Controller
@RequestMapping("/web/GBPlatForm")
public class GBPlatFormController {

	@Resource
	private GBPlatFormService service;

	// @RequestMapping("/toGBPlatForm.action")
	// public ModelAndView toGBPlatForm(HttpServletRequest request,
	// HttpSession session, @CookieValue("JSESSIONID") String sessionid) {
	// String username = (String) session.getAttribute("username");
	// System.out.println(username);
	// System.out.println(sessionid);
	//
	// MappingJacksonJsonView view = new MappingJacksonJsonView();
	// // return new ModelAndView(new RedirectView(request.getContextPath()
	// // + "/index.jsp"));
	//
	//
	//
	// Map attributes = new HashMap();
	// attributes.put("success", Boolean.TRUE);
	// attributes.put("contractNo", "contractNo");
	// view.setAttributesMap(attributes);
	// ModelAndView mv = new ModelAndView();
	// mv.setView(view);
	// return mv;
	// }

	/**
	 * 进入gb授权页面
	 * 
	 * @param request
	 * @param session
	 * @param organId
	 * @return
	 */
	@RequestMapping({ "/toGBPlatForm.action", "/toGBPlatForm.do" })
	public ModelAndView toGBPlatForm(HttpServletRequest request,
			HttpSession session,
			@RequestParam(value = "organId", required = false) Integer organId) {
		// 用户验证
		// String username = (String) session.getAttribute("username");
		// service.getOrganTree(organId);
		ModelAndView view = new ModelAndView("/base/gb/gbAuthorization.jsp");
		if (organId != null && organId != 0) {
			view.addObject("organId", organId);
		}
		view.addObject("num", SystemConfig.BASE_MANAGER);
		return view;
	}

	/**
	 * 获取国标机构数信息
	 * 
	 * @param request
	 * @param organId
	 * @return
	 */
	@RequestMapping("/getGBOrganList.do")
	@ResponseBody
	public ReturnResult getGBOrganList(HttpServletRequest request,
			@RequestParam(value = "organId") Integer organId) {
		try {
			GBOrgan organ = null;
			if (organId != null) {
				organ = service.getOrganTree(organId);
			}
			return ReturnResult.SUCCESS(organ);
		} catch (Exception e) {
			e.printStackTrace();
			return ReturnResult.FAILUER(e.getMessage());
		}
	}

	/**
	 * 机构 授权 国标机构
	 * 
	 * @param request
	 * @param organId
	 * @param GBOrganIds
	 * @return
	 */
	@RequestMapping("/addGBPermission.do")
	@ResponseBody
	public ReturnResult addGBPermission(
			HttpServletRequest request,
			@RequestParam(value = "organId", required = true) Integer organId,
			@RequestParam(value = "gbOrganIds", required = false) String GBOrganIds) {
		try {
			List<Integer> list = null;
			if(!StringUtils.isEmpty(GBOrganIds)){
				String[] ids = GBOrganIds.split(",");
				if (ids.length > 0) {
					list = new ArrayList<Integer>(ids.length);
					OrganGBOrganKey key = null;
					for (String id : ids) {
						list.add(Integer.valueOf(id));
					}
				}
			}
			service.addGBPermission(organId, list);
		} catch (Exception e) {
			e.printStackTrace();
			return ReturnResult.FAILUER(e.getMessage());
		}
		request.setAttribute("organId", organId);
		return ReturnResult.SUCCESS();
	}

	@RequestMapping("/searchGBOrganList.do")
	@ResponseBody
	public ReturnResult searchGBOrganList(HttpServletRequest request,
			@RequestParam(value = "organId", required = true) Integer organId,
			@RequestParam(value = "gbOrganName") String gbOrganName) {
		try {
			GBOrgan organ = service.getOrganTree(organId);
			if (StringUtils.hasLength(gbOrganName)) {
				process(organ.getItems(), gbOrganName);
			}
			return ReturnResult.SUCCESS(organ);
		} catch (Exception e) {
			e.printStackTrace();
			return ReturnResult.FAILUER(e.getMessage());
		}
	}

	private void process(final List<GBOrgan> list, String name) {
		Iterator<GBOrgan> it = list.listIterator();
		while (it.hasNext()) {
			GBOrgan organ = it.next();
			if (organ.getItems() != null) {
				process(organ.getItems(), name);
			}
			if (!organ.getName().contains(name)
					&& (organ.getItems() == null || organ.getItems().size() == 0)) {
				it.remove();
			}
		}
	}

	@RequestMapping("/getGBOrganListByOrganId.do")
	@ResponseBody
	public ReturnResult getGBOrganListByOrganId(HttpServletRequest request,
			@RequestParam(value = "organId", required = true) Integer organId) {
		try {
			GBOrgan organ = service.getOrganTree(organId);
			processByChecked(organ.getItems());
			return ReturnResult.SUCCESS(organ);
		} catch (Exception e) {
			e.printStackTrace();
			return ReturnResult.FAILUER(e.getMessage());
		}
	}

	private void processByChecked(final List<GBOrgan> list) {
		Iterator<GBOrgan> it = list.listIterator();
		while (it.hasNext()) {
			GBOrgan organ = it.next();
			organ.setExpanded(false);
			if (organ.getItems() != null) {
				processByChecked(organ.getItems());
			}
			if (!organ.isChecked()
					&& (organ.getItems() == null || organ.getItems().size() == 0)) {
				it.remove();
			}
		}
	}

	@RequestMapping(value = "/lazyGBOrganList.do", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String lazyGBOrganList(
			@RequestParam(value = "organId", required = true) Integer organId,
			@RequestParam(value = "parentId", required = false) Integer parentId,
			@RequestParam(value = "id", required = false) Integer hybrid_id,
			HttpServletRequest request) {
		List<GBOrgan> list = null;
		try {
			if (hybrid_id != null) {
				list = service.loadGbOrgan(organId, hybrid_id);
			} else {
				list = service.loadGbOrgan(organId, parentId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JSONArray.fromObject(list).toString();
	}

	
	
	@RequestMapping({ "/toMQ.action", "/toMQ.do" })
	public ModelAndView toMQ(HttpServletRequest request,
			HttpSession session,
			@RequestParam(value = "organId", required = false) Integer organId) {
		ModelAndView view = new ModelAndView("/base/gb/rabbit.jsp");
		if (organId != null && organId != 0) {
			view.addObject("organId", organId);
		}
		view.addObject("num", SystemConfig.BASE_MANAGER);
		return view;
	}
	
}
