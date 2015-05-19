package com.tianyi.bph.rest.action.system;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianyi.bph.BaseLogController;
import com.tianyi.bph.common.Pager;
import com.tianyi.bph.domain.system.CardPoint;
import com.tianyi.bph.domain.system.TCardPoint;
import com.tianyi.bph.query.system.CardPointQuery;
import com.tianyi.bph.query.system.TCardPointExample;
import com.tianyi.bph.service.system.CardPointService;
import com.tianyi.bph.common.ReturnResult;

/**
 * 卡点
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/cardPoint")
public class CardPointAction extends BaseLogController {

	static final Logger log = LoggerFactory.getLogger(CardPointAction.class);

	@Autowired CardPointService cardPointService;

	/**
	 * 添加卡点
	 * @param cardPoint
	 * @return
	 */
	@RequestMapping(value = "/addCardPoint.do")
	@ResponseBody
	public ReturnResult addCardPoint(CardPoint cardPoint) {
		
//		cardPoint = new CardPoint();
//		cardPoint.setAngle("angle");
//		cardPoint.setArmsPoliceCount(1);
//		cardPoint.setCircleLayer(2);
//		cardPoint.setCreateTime(new Date());
//		cardPoint.setIntercomGroupId(2);
//		cardPoint.setIsDisplayPatlabor(2);
//		cardPoint.setIsstation(3);
//		cardPoint.setLatitude("latitude");
//		cardPoint.setLongitude("longitude");
//		cardPoint.setName("测试");
//		cardPoint.setOrganId(5);
//		cardPoint.setPeoplePoliceCount(3);
//		cardPoint.setPoliceTotal(33);
//		cardPoint.setRespPoliceId(2);
//		cardPoint.setTrafficPoliceCount(3);
//		cardPoint.setType(3);
//		cardPoint.setAssignment("assignment");
//		
//		CardPoint c = cardPointService.addCardPoint(cardPoint);
//		if(c == null){
//			return ReturnResult.FAILUER("卡点名称重复");
//		}
		return ReturnResult.SUCCESS();
	}
	
	/**
	 * 删除卡点
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/deleteCardPoint.do")
	@ResponseBody
	public ReturnResult deleteCardPoint(@RequestParam(value = "id", required =true) Integer id
			,HttpServletRequest request) {
		int i = cardPointService.deleteCardPoint(id);
		addLog(request, "删除卡点成功", 2);
		return ReturnResult.SUCCESS("", i);
	}
	
	/**
	 * 修改卡点信息
	 * @param CardPoint
	 * @return
	 */
	@RequestMapping(value = "/modifyCardPoint.do")
	@ResponseBody
	public ReturnResult modifyCardPoint(CardPoint cardPoint,HttpServletRequest request) {
		CardPoint c = cardPointService.updateCardPoint(cardPoint);
		addLog(request, "修改卡点信息成功", 2);
		return ReturnResult.SUCCESS("成功", c);
	}
	
	/**
	 * 根据ID查询
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/queryCardPointDetail.do")
	@ResponseBody
	public ReturnResult queryCardPointDetail(@RequestParam(value = "id", required =true) Integer id) {
		CardPoint cardPoint = cardPointService.getCardPointById(id);
		return ReturnResult.SUCCESS("成功", cardPoint);
	}
	
	/**
	 * 分页查询
	 * @param query
	 * @return
	 */
	@RequestMapping(value = "/queryCardPointPageList.do")
	@ResponseBody
	public ReturnResult queryCardPointPageList(
			@RequestParam(value="sessionId",required=false)String sessionId,
			CardPointQuery query) {
		Pager<CardPoint> page = cardPointService.getPageList(query);
		return ReturnResult.SUCCESS("", page);
	}
	
	/**
	 * 条件查询
	 * @param query
	 * @return
	 */
	@RequestMapping(value = "/queryCardPointList.do")
	@ResponseBody
	public ReturnResult queryCardPointList(int organId) {
		TCardPointExample example = new TCardPointExample();
		TCardPointExample.Criteria criteria = example.createCriteria();
		criteria.andOrganIdEqualTo(organId);
		List<TCardPoint> listCardPoints = cardPointService
				.getCardPointList(example);
		//List<CardPoint> cardPoints = cardPointService.queryCardPointList(organId);
		return ReturnResult.SUCCESS("", listCardPoints);
	}
	
}
