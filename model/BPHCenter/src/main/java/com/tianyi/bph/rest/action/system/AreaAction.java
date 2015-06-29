package com.tianyi.bph.rest.action.system;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianyi.bph.common.JsonUtils;
import com.tianyi.bph.common.ReturnResult;
import com.tianyi.bph.domain.system.Area;
import com.tianyi.bph.domain.system.AreaPoint;
import com.tianyi.bph.query.system.AreaExample;
import com.tianyi.bph.service.system.AreaService;
import com.tianyi.bph.vo.AreaVO;

/**
 * 巡区 社区 辖区
 * 
 * @author Administrator
 * 
 */
@Controller
@RequestMapping("/area")
public class AreaAction {
	static final Logger log = LoggerFactory.getLogger(AreaAction.class);
	@Autowired
	private AreaService service;

	/**
	 * 添加 区域 基本信息
	 * 
	 * @param area
	 * @return
	 */
	@RequestMapping(value = "/addArea.do")
	@ResponseBody
	public ReturnResult addArea(
			@RequestParam(value = "areaName", required = true) String areaName,
			@RequestParam(value = "areaType", required = true) Integer areaType,
			@RequestParam(value = "organId", required = true) Integer organId,
			@RequestParam(value = "createUserId", required = true) Integer createUserId,
			@RequestParam(value = "tel", required = false) String tel,
			@RequestParam(value = "nnt", required = false, defaultValue = "0") Integer nnt,
			@RequestParam(value = "relationUserIds", required = false) String relationUserIds,
			HttpServletRequest request) {
		try {
			if (!StringUtils.hasLength(areaName)) {
				ReturnResult.FAILUER("用户名不能为空");
			}
			Area area = new Area();
			if (!"UTF-8".equals(request.getCharacterEncoding())) {
				area.setAreaName(new String(areaName.getBytes("ISO-8859-1"),
						"UTF-8"));
			} else {
				area.setAreaName(areaName);
			}
			area.setAreaType(areaType);
			area.setChangeRange(false);// 基础添加 没 绘制
			area.setCreateTime(new Date());
			area.setCreateUserId(createUserId);
			area.setOrganId(organId);
			area.setTel(tel);
			area.setNnt(nnt);
			if (StringUtils.hasLength(relationUserIds)) {
				String[] ids = relationUserIds.split(",");
				List<Integer> relationUserKeys = null;
				if (ids.length > 0) {
					relationUserKeys = new ArrayList<Integer>(ids.length);
					for (String userId : ids) {
						relationUserKeys.add(Integer.parseInt(userId));
					}
				}
				area.setRelationUserKeys(relationUserKeys);
			}
			area.setFlag(AreaExample.flag_normal);// 默认正常
			return ReturnResult.SUCCESS("成功", service.addArea(area).getId());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ReturnResult.FAILUER(e.getMessage());
		}
	}

	/**
	 * 修改 巡区 社区 辖区
	 * 
	 * @param areaId
	 * @param areaType
	 * @param areaName
	 * @param lineColor
	 * @param lineTransparence
	 * @param backGroundColor
	 * @param backGroundTransparence
	 * @param coordinates
	 * @param shape
	 * @param centre
	 * @param tel
	 * @param nnt
	 * @return
	 */
	@RequestMapping(value = "/updateArea.do", method = RequestMethod.POST)
	@ResponseBody
	public ReturnResult updateArea(@RequestBody String body) {
		try {
			AreaVO areavo = JsonUtils.toObj(body, AreaVO.class);
			service.updateByPrimaryKey(areavo.ctrate());
			return ReturnResult.SUCCESS("成功",
					service.updateByPrimaryKey(areavo.ctrate()).getId());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ReturnResult.FAILUER(e.getMessage());
		}
	}

	@RequestMapping(value = "/updateAreaByWeb.do", method = RequestMethod.POST)
	@ResponseBody
	public ReturnResult updateAreaByWeb(
			@RequestParam(value = "AreaVo") String body,
			HttpServletRequest request) {
		try {
			AreaVO areavo = JsonUtils.toObj(body, AreaVO.class);
			return ReturnResult.SUCCESS("成功",
					service.updateByPrimaryKey(areavo.ctrate()).getId());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ReturnResult.FAILUER(e.getMessage());
		}
	}

	/**
	 * 查询巡区社区辖区，集合
	 * 
	 * @param organIds
	 * @param areaName
	 * @return
	 */
	@RequestMapping(value = "/queryAreaList.do")
	@ResponseBody
	public ReturnResult queryAreaList(
			@RequestParam(value = "organIds", required = false) String organIds,
			@RequestParam(value = "areaName", required = false) String areaName,
			@RequestParam(value = "areaType", required = false) Integer areaType,
			HttpServletRequest request) {
		try {
			AreaExample areaExample = new AreaExample();
			AreaExample.Criteria criteria = areaExample.createCriteria();
			List<Integer> listIds = null;
			if (StringUtils.hasLength(organIds)) {
				String[] ids = organIds.split(",");
				listIds = new ArrayList<Integer>(ids.length);
				for (String id : ids) {
					listIds.add(Integer.parseInt(id));
				}
			}
			if (listIds != null && !organIds.isEmpty()) {
				if (listIds.size() == 1) {
					criteria.andOrganIdEqualTo(listIds.get(0));

				} else {
					criteria.andOrganIdIn(listIds);
				}
			}
			if (StringUtils.hasLength(areaName)) {
				if (!"UTF-8".equals(request.getCharacterEncoding())) {
					areaName = new String(areaName.getBytes("ISO-8859-1"),
							"UTF-8");
				}
				criteria.andAreaNameLike("%" + areaName + "%");
			}
			if (areaType != null) {
				criteria.andAreaTypeEqualTo(areaType);
			}
			criteria.andFlagLessThanOrEqualTo(AreaExample.flag_normal);
			areaExample.setOrderByClause("area_name  desc");
			List<Area> list = service.selectByExample(areaExample);
			List<AreaVO> reList = null;
			if (!list.isEmpty()) {
				reList = new ArrayList<AreaVO>(list.size());
				for (Area area : list) {
					reList.add(new AreaVO(area));
				}
			}
			return ReturnResult.SUCCESS("成功！", reList);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return ReturnResult.FAILUER("失败！", e.getMessage());
		}
	}

	/**
	 * 查询巡区社区辖区，集合
	 * 
	 * @param organIds
	 * @param areaName
	 * @return
	 */
	@RequestMapping(value = "/queryAreaDetailById.do")
	@ResponseBody
	public ReturnResult queryAreaDetailById(
			@RequestParam(value = "id", required = true) Integer id) {
		try {
			return ReturnResult.SUCCESS("成功！",
					new AreaVO(service.queryByPrimaryKey(id)));
		} catch (Exception e) {
			log.error(e.getMessage());
			return ReturnResult.FAILUER("失败！", e.getMessage());
		}
	}

	@RequestMapping(value = "/deleteAreaDetailById.do")
	@ResponseBody
	public ReturnResult deleteAreaDetailById(
			@RequestParam(value = "id", required = true) Integer id) {
		try {
			return ReturnResult.SUCCESS("成功！", service.deleteByPrimaryKey(id));
		} catch (Exception e) {
			log.error(e.getMessage());
			return ReturnResult.FAILUER("失败！", e.getMessage());
		}
	}

	/**
	 * 查询巡区社区辖区，集合 包含子机构
	 * 
	 * @param organIds
	 * @param areaName
	 * @return
	 */
	@RequestMapping(value = "/queryAreaListByOrganId.do")
	@ResponseBody
	public ReturnResult queryAreaListByOrganId(
			@RequestParam(value = "organId", required = true) Integer organId) {
		try {
			List<Area> list = service.selectByOrganId(organId);
			List<AreaVO> reList = null;
			if (!list.isEmpty()) {
				reList = new ArrayList<AreaVO>(list.size());
				for (Area area : list) {
					reList.add(new AreaVO(area));
				}
			}
			return ReturnResult.SUCCESS("成功！", reList);
		} catch (Exception e) {
			log.error(e.getMessage());
			return ReturnResult.FAILUER("失败！", e.getMessage());
		}
	}

	@RequestMapping(value = "/add.do", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnResult add(@RequestBody String body, HttpServletRequest request) {
		try {
			return ReturnResult.SUCCESS("成功！", body);
		} catch (Exception e) {
			log.error(e.getMessage());
			return ReturnResult.FAILUER("失败！", e.getMessage());
		}
	}

	@RequestMapping(value = "/queryAreaPointByAreaId.do")
	@ResponseBody
	public ReturnResult queryAreaPointByAreaId(
			@RequestParam(value = "areaId", required = false) Integer areaId) {
		try {
			return ReturnResult.SUCCESS("成功！",
					service.queryAreaPointList(areaId));
		} catch (Exception e) {
			log.error(e.getMessage());
			return ReturnResult.FAILUER("失败！", e.getMessage());
		}
	}

	@RequestMapping(value = "/addAreaPoint.do", method = RequestMethod.POST)
	@ResponseBody
	public ReturnResult addAreaPoint(@RequestBody String body) {
		try {
			AreaPoint point = JsonUtils.toObj(body, AreaPoint.class);
			if (!StringUtils.hasLength(point.getName())) {
				return ReturnResult.FAILUER("失败！", "必达点名字不存在");
			}
			service.addAreaPoint(point);
			return ReturnResult.SUCCESS("成功！", point);
		} catch (Exception e) {
			log.error(e.getMessage());
			return ReturnResult.FAILUER("失败！", e.getMessage());
		}
	}

	@RequestMapping(value = "/updateAreaPoint.do", method = RequestMethod.POST)
	@ResponseBody
	public ReturnResult updateAreaPoint(@RequestBody String body) {
		try {
			AreaPoint point = JsonUtils.toObj(body, AreaPoint.class);
			service.updateAreaPoint(point);
			return ReturnResult.SUCCESS("成功！", point);
		} catch (Exception e) {
			log.error(e.getMessage());
			return ReturnResult.FAILUER("失败！", e.getMessage());
		}
	}

	@RequestMapping(value = "/deleteAreaPoint.do")
	@ResponseBody
	public ReturnResult deleteAreaPoint(
			@RequestParam(value = "areaPointId", required = false) Integer areaPointId) {
		try {
			return ReturnResult.SUCCESS("成功！",
					service.deleteAreaPoint(areaPointId));
		} catch (Exception e) {
			log.error(e.getMessage());
			return ReturnResult.FAILUER("失败！", e.getMessage());
		}
	}

	/**
	 * 修改 巡区 社区 辖区
	 * 
	 * @param areaId
	 * @param areaType
	 * @param areaName
	 * @param lineColor
	 * @param lineTransparence
	 * @param backGroundColor
	 * @param backGroundTransparence
	 * @param coordinates
	 * @param shape
	 * @param centre
	 * @param tel
	 * @param nnt
	 * @return
	 */
	@RequestMapping(value = "/updateAreabasic.do", method = RequestMethod.POST)
	@ResponseBody
	public ReturnResult updateAreabasic(
			@RequestParam(value = "areaId", required = true) Integer areaId,
			@RequestParam(value = "areaName", required = true) String areaName,
			@RequestParam(value = "createUserId", required = true) Integer createUserId,
			@RequestParam(value = "tel", required = false) String tel,
			@RequestParam(value = "nnt", required = false, defaultValue = "0") Integer nnt,
			@RequestParam(value = "relationUserIds", required = false) String relationUserIds,
			HttpServletRequest request) {
		try {
			AreaVO areavo = new AreaVO();
			areavo.setId(areaId);
			areavo.setAreaName(areaName);
			areavo.setTel(tel);
			areavo.setNnt(nnt);
			if (StringUtils.hasLength(relationUserIds)) {
				String[] ids = relationUserIds.split(",");
				List<Integer> relationUserKeys = null;
				if (ids.length > 0) {
					relationUserKeys = new ArrayList<Integer>(ids.length);
					for (String userId : ids) {
						relationUserKeys.add(Integer.parseInt(userId));
					}
				}
				areavo.setRelationUserIds(relationUserKeys);
			}
			return ReturnResult.SUCCESS("成功", service.updateByPrimaryKey(areavo.ctrate()).getId());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ReturnResult.FAILUER(e.getMessage());
		}
	}

	public static void main(String[] args) {
		// AreaVO area = new AreaVO();
		//
		// area.setAreaName("ssss");
		// DisplayProperty p = new DisplayProperty();
		// p.setBorderColor("closs");
		// p.setBorderOpacity(123.21);
		// area.setDisplayProperty(p);
		// System.out.println(JsonUtils.toJson(area));
		// AreaVO v = JsonUtils.toObj(JsonUtils.toJson(area), AreaVO.class);

		// System.out.println(v.getDisplayProperty());

		// Area
		// area=JsonUtils.toObj("{"id":26,"organId":1,"OwnerOrganName":null,"areaName":"陈丹巡区1","relationUserIds":null,"ResponsiblePersonName":null,"tel":"15865985645","nnt":20,"areaType":1,"displayProperty":{"name":"陈丹巡区1","borderColor":"#FFFF0000","borderOpacity":0.9,"fillColor":"#FF00FFFF","fillOpacity":0.3,"x":104.1103715425205,"y":30.744970327663665},"mapProperty":[{"x":3232.1501405226777,"y":1680.1538158922267},{"x":3232.1462342726777,"y":1679.8920971422267},{"x":3232.3493592726777,"y":1679.8374096422267},{"x":3232.4118592726777,"y":1679.7710033922267},{"x":3232.4665467726777,"y":1679.6616283922267},{"x":3232.6892030226777,"y":1679.6186596422267},{"x":3232.8493592726777,"y":1679.6889721422267},{"x":3232.9431092726777,"y":1679.7475658922267},{"x":3232.9977967726777,"y":1679.9350658922267},{"x":3233.0095155226777,"y":1680.0835033922267},{"x":3232.9860780226777,"y":1680.4077221422267},{"x":3232.8142030226777,"y":1680.4194408922267},{"x":3232.7985780226777,"y":1680.4467846422267},{"x":3232.7126405226777,"y":1680.4663158922267},{"x":3232.3532655226777,"y":1680.4077221422267},{"x":3232.1501405226777,"y":1680.3491283922267},{"x":3232.1462342726777,"y":1680.3491283922267}]}",
		// Area.class);
	}
}
