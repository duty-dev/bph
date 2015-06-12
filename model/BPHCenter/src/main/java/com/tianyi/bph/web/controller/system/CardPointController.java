package com.tianyi.bph.web.controller.system;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tianyi.bph.common.Constants;
import com.tianyi.bph.common.MessageCode;
import com.tianyi.bph.common.PageReturn;
import com.tianyi.bph.common.Pager;
import com.tianyi.bph.common.ReturnResult;
import com.tianyi.bph.common.SystemConfig;
import com.tianyi.bph.common.utils.PatternUtils;
import com.tianyi.bph.dao.basicdata.PoliceMapper;
import com.tianyi.bph.domain.basicdata.Police;
import com.tianyi.bph.domain.system.CardPoint;
import com.tianyi.bph.domain.system.CardPointBean;
import com.tianyi.bph.domain.system.CircleLayer;
import com.tianyi.bph.domain.system.GBDevice;
import com.tianyi.bph.domain.system.TCardPoint;
import com.tianyi.bph.query.basicdata.PoliceJJVM;
import com.tianyi.bph.query.system.CardPointQuery;
import com.tianyi.bph.query.system.TCardPointExample;
import com.tianyi.bph.service.basicdata.PoliceService;
import com.tianyi.bph.service.system.CardPointService;
import com.tianyi.bph.service.system.CircleLayerService;
import com.tianyi.bph.service.system.GBPlatFormService;

/**
 * 卡点 管理
 * 
 * @author Administrator
 * 
 */
@Controller
@RequestMapping("/web/cardPoint")
public class CardPointController {

	static final Logger log = LoggerFactory
			.getLogger(CardPointController.class);

	@Autowired
	CardPointService cardPointService;
	@Autowired
	GBPlatFormService gBPlatFormService;
	@Autowired
	PoliceService policeService;
	@Autowired
	PoliceMapper policeMapper;
	@Autowired
	CircleLayerService circleService;

	/**
	 * 添加卡点
	 * 
	 * @param cardPoint
	 * @return
	 */
	@RequestMapping({ "/addCardPoints.do", "/addCardPoints.action" })
	@ResponseBody
	public ModelAndView addCardPoints() {
		// mv.addObject("organ", organ);
		return new ModelAndView("/base/cardPoint/addCardPoints.jsp");
	}

	/**
	 * 卡点查询
	 * 
	 * @param query
	 * @return
	 */
	@RequestMapping(value = "/queryCardPointList.do")
	@ResponseBody
	public PageReturn queryCardPointList(
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "organOrNext", required = true, defaultValue = "1") Integer organOrNext,
			@RequestParam(value = "organId", required = true) Integer organId,
			@RequestParam(value = "params", required = false) String params,
			@RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
			@RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
			@RequestParam(value = "sessionId", required = false) String sessionId,
			@RequestParam(value = "totalRows", required = false) Integer totalRows,
			HttpServletRequest request) {
		TCardPointExample example = new TCardPointExample();
		TCardPointExample.Criteria criteria = example.createCriteria();
		if (StringUtils.hasLength(name)) {
			criteria.andNameLike("%" + name + "%");
		}
		if (organOrNext == 1) {
			criteria.andOrganIdEqualTo(organId);
		} else {
			// criteria.andOrganIdIn(values);
		}
		if (totalRows == null || totalRows == 0) {
			totalRows = cardPointService.getCardPointListCount(example);
		}
		example.setBegin(pageNo * pageSize - pageSize);
		example.setEnd(pageNo * pageSize);
		example.setOrderByClause("create_time");
		List<TCardPoint> listCardPoints = cardPointService
				.getCardPointList(example);
		List<CardPointBean> cardPointList = new ArrayList<CardPointBean>();
		if (!listCardPoints.isEmpty()) {
			CardPointBean bean = null;
			for (TCardPoint cardPoint : listCardPoints) {
				bean = new CardPointBean();
				bean.setCardPointId(cardPoint.getId());
				bean.setName(cardPoint.getName());
				bean.setCircleLayerId(cardPoint.getLayersId());
				if (StringUtils.hasText(cardPoint.getCommunityGroupNum())) {
					bean.setIntercomGroupId(Integer.parseInt(cardPoint
							.getCommunityGroupNum()));
				}
				bean.setAssignment(cardPoint.getAssignment());
				CircleLayer c = circleService.getCircleLayerById(cardPoint
						.getLayersId());
				if (c != null) {
					bean.setCircleLayerName(c.getName());
				}
				// 添加摄像头名字
				List<Integer> cameras = cardPointService
						.getCardPointCamera(cardPoint.getId());
				if (cameras != null && cameras.size() > 0) {
					StringBuffer device = new StringBuffer();
					for (Integer cameraId : cameras) {
						GBDevice d = gBPlatFormService
								.getGBDeviceById(cameraId);
						if (d != null && d.getName() != null) {
							device.append(d.getName() + ",");
						}
					}
					bean.setCamera(device.toString());
				}
				cardPointList.add(bean);
			}

		}
		return PageReturn.MESSAGE(MessageCode.STATUS_SUCESS,
				MessageCode.SELECT_SUCCESS, totalRows, cardPointList);
	}

	/**
	 * 添加卡点
	 * 
	 * @param cardPoint
	 * @return 2015-04-18 update BY chen
	 */
	@RequestMapping({ "/addCardPoint.do", "/addCardPoint.action" })
	@ResponseBody
	public ReturnResult addCardPoint(
			HttpServletRequest request,
			@RequestParam(value = "organId", required = true) Integer organId, // 机构id
			@RequestParam(value = "name", required = true) String name, // 卡点名称
			@RequestParam(value = "manager", required = false) String manager, // 卡点负责人
			@RequestParam(value = "telephone", required = false) String telephone, // 联系电话
			@RequestParam(value = "intercomGroupId", required = true) String intercomGroupId, // 对讲机组号
			@RequestParam(value = "equipment", required = false) String equipment, // 携行装备
			@RequestParam(value = "policeNote", required = false) String policeNote, // 警力描述
			@RequestParam(value = "assignment", required = false) String assignment, // 职责
			@RequestParam(value = "camera", required = false) String camera, // 关联天网
			@RequestParam(value = "latitude", required = false) Double latitude, // 经度
			@RequestParam(value = "longitude", required = false) Double longitude, // 纬度
			@RequestParam(value = "circleLayer", required = true) Integer circleLayer,// 所属圈层
			@RequestParam(value = "iconId", required = false) Integer iconId, // 图标编号
			@RequestParam(value = "cardType", required = false) Integer cardType, // 卡点类型
			@RequestParam(value = "sessionId", required = false) String sessionId) {
		TCardPoint cardPoint;
		try {
			if (!StringUtils.hasLength(name)) {
				return ReturnResult.FAILUER("卡点名称不能为空");
			}
			if(!StringUtils.isEmpty(telephone)){
				if(!PatternUtils.instance.matchesTel(telephone) && !PatternUtils.instance.matchesPhone(telephone)){
					return ReturnResult.FAILUER("请输入正确的电话号码");
				}
			}
			cardPoint = new TCardPoint();
			cardPoint.setOrganId(organId);
			cardPoint.setName(name);
			cardPoint.setTel(telephone);
			cardPoint.setCommunityGroupNum(intercomGroupId);
			cardPoint.setCardType(cardType);
			cardPoint.setEquip(equipment);
			cardPoint.setAssignment(assignment);
			cardPoint.setLayersId(circleLayer);
			if (iconId != null) {
				cardPoint.setIconId(iconId);
			}
			Set<Integer> set = null;
			if (StringUtils.hasLength(manager)) {
				String[] users = manager.split(",");
				set = new HashSet<Integer>(users.length);
				for (String str : users) {
					set.add(Integer.parseInt(str));
				}
				cardPoint.setPoliceUsers(set);
			}
			if (StringUtils.hasLength(camera)) {
				String[] cameras = camera.split(",");
				set = new HashSet<Integer>(cameras.length);
				for (String str : cameras) {
					set.add(Integer.parseInt(str));
				}
				cardPoint.setCameras(set);
			}
			cardPointService.insertCardPoint(cardPoint);
		} catch (Exception e) {
			e.printStackTrace();
			return ReturnResult.FAILUER(e.getMessage());
		}
		return ReturnResult.SUCCESS();
	}

	/**
	 * 删除卡点
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping({ "/deleteCardPoint.do", "/deleteCardPoint.action" })
	@ResponseBody
	public ReturnResult deleteCardPoint(
			@RequestParam(value = "sessionId", required = false) String sessionId,
			@RequestParam(value = "id", required = true) Integer id) {
		return ReturnResult.SUCCESS(cardPointService.deleteCardPointById(id));
	}

	/**
	 * 客户端 修改卡点信息
	 * 
	 * @param CardPoint
	 * @return
	 */
	@RequestMapping(value = "/modifyCardPoints.do")
	@ResponseBody
	public ReturnResult modifyCardPoint(CardPoint cardPoint) {
		CardPoint c = cardPointService.updateCardPoint(cardPoint);
		return ReturnResult.SUCCESS("成功", c);
	}

	/**
	 * 修改卡点
	 * 
	 * @param CardPoint
	 * @return
	 */
	@RequestMapping({ "/modifyCardPoint.do", "/modifyCardPoint.action" })
	@ResponseBody
	public ReturnResult modifyCardPoint(
			@RequestParam(value = "cardPointId", required = true) Integer cardPointId,
			@RequestParam(value = "organId", required = true) Integer organId, // 机构id
			@RequestParam(value = "name", required = true) String name, // 卡点名称
			@RequestParam(value = "manager", required = false) String manager, // 卡点负责人
			@RequestParam(value = "telephone", required = false) String telephone, // 联系电话
			@RequestParam(value = "intercomGroupId", required = true) String intercomGroupId, // 对讲机组号
			@RequestParam(value = "equipment", required = false) String equipment, // 携行装备
			@RequestParam(value = "assignment", required = false) String assignment, // 职责
			@RequestParam(value = "camera", required = false) String camera, // 关联天网
			@RequestParam(value = "circleLayer", required = true) Integer circleLayer,// 所属圈层
			@RequestParam(value = "iconId", required = false) Integer iconId, // 图标编号
			@RequestParam(value = "cardType", required = true) Integer cardType, // 卡点类型
			HttpServletRequest request) {
		TCardPoint cardPoint;
		try {
			if (!StringUtils.hasLength(name)) {
				return ReturnResult.FAILUER("卡点名称不能为空");
			}
			if(!StringUtils.isEmpty(telephone)){
				if(!PatternUtils.instance.matchesTel(telephone) && !PatternUtils.instance.matchesPhone(telephone)){
					return ReturnResult.FAILUER("请输入正确的电话号码");
				}
			}
			cardPoint = new TCardPoint();
			cardPoint.setId(cardPointId);
			cardPoint.setOrganId(organId);
			cardPoint.setName(name);
			cardPoint.setTel(telephone);
			cardPoint.setCommunityGroupNum(intercomGroupId);
			cardPoint.setCardType(cardType);
			cardPoint.setEquip(equipment);
			cardPoint.setAssignment(assignment);
			cardPoint.setLayersId(circleLayer);
			if (!StringUtils.isEmpty(iconId)) {
				cardPoint.setIconId(iconId);
			}

			Set<Integer> set = null;
			if (StringUtils.hasText(manager)) {
				String[] users = manager.split(",");
				set = new HashSet<Integer>(users.length);
				for (String str : users) {
					set.add(Integer.parseInt(str));
				}
				cardPoint.setPoliceUsers(set);
			}
			if (StringUtils.hasText(camera)) {
				String[] cameras = camera.split(",");
				set = new HashSet<Integer>(cameras.length);
				for (String str : cameras) {
					set.add(Integer.parseInt(str));
				}
				cardPoint.setCameras(set);
			}
			cardPointService.updateCardPoint(cardPoint);
		} catch (Exception e) {
			log.error(e.getMessage());
			return ReturnResult.MESSAGE(MessageCode.STATUS_FAIL,
					MessageCode.UPDATE_CARDPOINT_FAIL);
		}
		return ReturnResult.MESSAGE(MessageCode.STATUS_SUCESS,
				MessageCode.UPDATE_CARDPOINT_SUCCESS);
	}
	/**
	 * 根据ID查询
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping({ "/queryCardPointDetail.do",
			"/queryCardPointDetail.action" })
	@ResponseBody
	public ModelAndView queryCardPointDetail(
			@RequestParam(value = "sessionId", required = false) String sessionId,
			@RequestParam(value = "id", required = true) Integer id) {
		ModelAndView mv = new ModelAndView(
				"/base/cardPoint/cardPointDetail.jsp");
		CardPoint cardPoint = cardPointService.getCardPointById(id);
		mv.addObject("cardPoint", cardPoint);
		return mv;
	}

	/**
	 * 根据ID查询
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping({ "/queryCardPointDetailById.do" })
	@ResponseBody
	public ReturnResult queryCardPointDetailById(
			@RequestParam(value = "sessionId", required = false) String sessionId,
			@RequestParam(value = "id", required = true) Integer id) {
		CardPoint cardPoint = cardPointService.getCardPointById(id);
		return ReturnResult.MESSAGE(MessageCode.STATUS_SUCESS, "查询成功",
				cardPoint);
	}

	/**
	 * 卡点列表展示(分页查询)
	 * 
	 * @param cardPointQuery
	 * @return
	 */
	@RequestMapping({ "/queryCardPointPageList.do",
			"/queryCardPointPageList.action" })
	@ResponseBody
	public ModelAndView queryCardPointPageList(
			@RequestParam(value = "id", required = false) Integer id,
			@RequestParam(value = "code", required = false) String code,
			@RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
			@RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
			@RequestParam(value = "sessionId", required = false) String sessionId,
			HttpServletRequest request) {// CardPointQuery cardPointQuery

		CardPointQuery cardPointQuery = new CardPointQuery();
		cardPointQuery.setId(id);
		cardPointQuery.setPageSize(pageSize);
		cardPointQuery.setPageNo(pageNo);

		ModelAndView mv = new ModelAndView("/base/cardPoint/cardPointList.jsp");
		//List<CardPoint> pager = cardPointService.getQueryList(cardPointQuery);
		//mv.addObject("pager", pager);
		mv.addObject("num", SystemConfig.BASE_MANAGER);
		return mv;
	}

	/**
	 * 卡点列表展示(分页查询)
	 * 
	 * @param cardPointQuery
	 * @return
	 */
	@RequestMapping({ "/queryCardPointPageList2.do",
			"/queryCardPointPageList2.action" })
	@ResponseBody
	public ReturnResult queryCardPointPageList2(
			@RequestParam(value = "path", required = false) String path,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "code", required = false) String code,
			@RequestParam(value = "organOrNext", required = true, defaultValue = "1") Integer organOrNext,
			@RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
			@RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
			@RequestParam(value = "sessionId", required = false) String sessionId,
			HttpServletRequest request) {

		CardPointQuery cardPointQuery = new CardPointQuery();
		cardPointQuery.setPath(path);
		cardPointQuery.setName(name);
		cardPointQuery.setOrganOrNext(organOrNext);
		cardPointQuery.setPageSize(pageSize);
		cardPointQuery.setPageNo(pageNo);

		Pager<CardPoint> cardPointList = cardPointService
				.getPageList(cardPointQuery);
		return ReturnResult.MESSAGE(MessageCode.STATUS_SUCESS, "查询成功",
				cardPointList);
	}

	@RequestMapping({ "/queryCardPointTotal.do", "/queryCardPointTotal.action" })
	@ResponseBody
	public ReturnResult queryCardPointTotal() {
		int total = cardPointService.queryCardPointTotal();
		return ReturnResult.SUCCESS("", total);
	}

	/**
	 * 跳转编辑页面
	 * 
	 * @param request
	 * @param cardPointId
	 * @return
	 */
	@RequestMapping({ "/gotoCardPointEdit.do", "/gotoCardPointEdit.action" })
	public ModelAndView gotoCardPointEdit(
			HttpServletRequest request,
			@RequestParam(value = "sessionId", required = false) String sessionId,
			@RequestParam(value = "cardPointId", required = true) Integer cardPointId) {
		ModelAndView mv = null;
		TCardPoint cardPoint = cardPointService
				.getCardPointByparamKey(cardPointId);
		if (cardPoint != null) {
			CardPointBean bean = new CardPointBean();
			bean = new CardPointBean();
			bean.setCardPointId(cardPoint.getId());
			bean.setName(cardPoint.getName());
			bean.setCircleLayerId(cardPoint.getLayersId());
			if (StringUtils.hasText(cardPoint.getCommunityGroupNum())) {
				bean.setIntercomGroupId(Integer.parseInt(cardPoint
						.getCommunityGroupNum()));
			}
			bean.setAssignment(cardPoint.getAssignment());
			CircleLayer c = circleService.getCircleLayerById(cardPoint
					.getLayersId());
			if (c != null) {
				bean.setCircleLayerName(c.getName());
			}
			if (cardPoint.getCardType() != null) {
				bean.setType(cardPoint.getCardType());
			}
			if (cardPoint.getTel() != null) {
				bean.setTelephone(cardPoint.getTel());
			}
			if (cardPoint.getEquip() != null) {
				bean.setEquipment(cardPoint.getEquip());
			}
			// 添加摄像头名字
			List<Integer> cameras = cardPointService
					.getCardPointCamera(cardPoint.getId());
			if (cameras != null && cameras.size() > 0) {
				StringBuffer device = new StringBuffer();
				StringBuffer cameraIds = new StringBuffer();
				for (Integer cameraId : cameras) {
					GBDevice d = gBPlatFormService.getGBDeviceById(cameraId);
					if (d != null && d.getName() != null) {
						device.append(d.getName() + ",");
					}
					cameraIds.append(cameraId+",");
				}
				bean.setCamera(cameraIds.toString());
				bean.setCameraName(device.toString());
			}
			
			//查看该卡点关联的警员信息
			List<Integer> police=cardPointService.getCardPointUser(cardPoint.getId());
			if(police != null && police.size()>0){
				StringBuffer policeStr = new StringBuffer();
				StringBuffer policeIdStr = new StringBuffer();
				for (Integer policeId : police) {
					Police p=policeService.selectByPrimaryKey(policeId);
					policeStr.append(p.getName()+",");
					policeIdStr.append(policeId+",");
				}
				bean.setManager(policeIdStr+"");
				bean.setManagerName(policeStr+"");
			}

			List<PoliceJJVM> policeList = policeService.getPoliceInfo(
					cardPoint.getOrganId(), Constants.SEARCH_TYPE_NO_CHILD);
			mv = new ModelAndView("/base/cardPoint/modifyCardPoint.jsp");
			mv.addObject("policeList", policeList);
			mv.addObject("cardPoint", bean);
			List<GBDevice> cameraList = gBPlatFormService
					.getGBDeviceListByOrganId(cardPoint.getOrganId());
			if (cameraList != null && cameraList.size() > 1000) {
				mv.addObject("cameraList", cameraList.subList(0, 1000)); // 天网
			} else {
				mv.addObject("cameraList", cameraList); // 天网
			}
			List<CircleLayer> circleList = circleService.getCircleLayerList();
			mv.addObject("circleList", circleList);
		} else {

			// 卡点不存在
			// mv = new ModelAndView("/base/cardPoint/modifyCardPoint.jsp");
		}
		return mv;
	}

	/**
	 * 跳转添加页面
	 * 
	 * @param request
	 * @param organId
	 * @return
	 */
	@RequestMapping({ "/gotoCardPointAdd.do", "/gotoCardPointAdd.action" })
	@ResponseBody
	public ModelAndView gotoCardPointAdd(
			HttpServletRequest request,
			@RequestParam(value = "sessionId", required = false) String sessionId,
			@RequestParam(value = "organId", required = false) Integer organId) {

		ModelAndView mv = new ModelAndView("/base/cardPoint/addCardPoints.jsp");
		List<PoliceJJVM> policeList = policeService.getPoliceInfo(organId,
				Constants.SEARCH_TYPE_NO_CHILD);
		/*List<GBDevice> cameraList = gBPlatFormService
				.getGBDeviceListByOrganId(organId);*/
		/*if (cameraList != null && cameraList.size() > 1000) {
			mv.addObject("cameraList", cameraList.subList(0, 1000)); // 天网
		} else {
			mv.addObject("cameraList", cameraList); // 天网
		}*/
		mv.addObject("organId", organId);
		mv.addObject("policeList", policeList); // 警员
		//mv.addObject("cameraList", cameraList); // 天网

		List<CircleLayer> circleList = circleService.getCircleLayerList();
		mv.addObject("circleList", circleList);
		return mv;
	}

	@RequestMapping(value="/getCameraList.do")
	@ResponseBody
	public ReturnResult getCameraList(@RequestParam(value="organId",required=true)Integer organId){
		
		List<GBDevice> cameraList = gBPlatFormService.getGBDeviceListByOrganId(organId);
		
		return ReturnResult.MESSAGE(200,"获取成功",cameraList);
	}
	
	
	/**
	 * @ goto圈层设置页面
	 * 
	 * @return
	 */
	@RequestMapping({ "/gotoCircleLayer.do", "/gotoCircleLayer.action" })
	@ResponseBody
	public ModelAndView gotoCircleLayer(
			@RequestParam(value = "organId", required = false) String organId,
			@RequestParam(value = "sessionId", required = false) String sessionId,
			HttpServletRequest request) {

		ModelAndView mv = new ModelAndView("/base/cardPoint/circleLayer.jsp");
		List<CircleLayer> circleList = circleService.getCircleLayerList();
		mv.addObject("circleList", circleList);
		return mv;
	}

	/**
	 * 添加圈层
	 * 
	 * @param cardPoint
	 * @return
	 */
	@RequestMapping({ "/addCircleLayer.do", "/addCircleLayer.action" })
	@ResponseBody
	public ReturnResult addCircleLayer(
			@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "sessionId", required = false) String sessionId,
			HttpServletRequest request) {
		CircleLayer circle = new CircleLayer();
		circle.setName(name);
		circleService.addCircleLayer(circle);
		return ReturnResult.SUCCESS();
	}

	/**
	 * 圈层查询
	 * 
	 * @param query
	 * @return
	 */
	@RequestMapping(value = "/queryCircleList.do")
	@ResponseBody
	public ReturnResult queryCircleList(
			@RequestParam(value = "sessionId", required = false) String sessionId,
			HttpServletRequest request) {
		List<CircleLayer> circleList = circleService.getCircleLayerList();
		return ReturnResult.MESSAGE(MessageCode.STATUS_SUCESS,
				MessageCode.SELECT_SUCCESS, circleList);
	}

	/**
	 * 删除圈层
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping({ "/deleteCircle.do", "/deleteCircle.action" })
	@ResponseBody
	public ReturnResult deleteCircle(
			@RequestParam(value = "id", required = true) Integer id) {
		try {
			circleService.deleteCircleLayer(id);
		} catch (Exception e) {
			// TODO: handle exception
			return ReturnResult.FAILUER(e.getMessage());
		}
		return ReturnResult.SUCCESS("删除成功!");
	}

	/**
	 * 修改卡点坐标
	 * 
	 * @param CardPoint
	 * @return
	 */
	@RequestMapping({ "/modifyCardPointCoordinate.do",
			"/modifyCardPointCoordinate.action" })
	@ResponseBody
	public ReturnResult modifyCardPointCoordinate(
			@RequestParam(value = "cardPointId", required = true) Integer cardPointId,
			@RequestParam(value = "latitude", required = true) String latitude, // 经度
			@RequestParam(value = "longitude", required = true) String longitude, // 纬度
			HttpServletRequest request) {

		try {
			TCardPoint c = new TCardPoint();
			c.setId(cardPointId);
			c.setLatitude(Double.parseDouble(latitude));
			c.setLongitude(Double.parseDouble(longitude));
			c = cardPointService.updateCardPointKeySelective(c);
			return ReturnResult.MESSAGE(MessageCode.STATUS_SUCESS,
					MessageCode.UPDATE_CARDPOINT_SUCCESS);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return ReturnResult.MESSAGE(MessageCode.STATUS_FAIL,
					MessageCode.UPDATE_CARDPOINT_FAIL);
		}
	}

	public static void main(String[] args) {
		System.out.println(StringUtils.hasLength(""));
	}
}
