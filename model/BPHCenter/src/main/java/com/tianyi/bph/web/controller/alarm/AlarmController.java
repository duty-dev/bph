package com.tianyi.bph.web.controller.alarm;

import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tianyi.bph.common.DateUtil;
import com.tianyi.bph.common.JsonUtils;
import com.tianyi.bph.common.PageReturn;
import com.tianyi.bph.common.ReturnResult;
import com.tianyi.bph.domain.alarm.Jjdb110;
import com.tianyi.bph.domain.system.Organ;
import com.tianyi.bph.query.alarm.JJDBQuery;
import com.tianyi.bph.query.alarm.JJDBView;
import com.tianyi.bph.service.alarm.AlarmDispatchService;
import com.tianyi.bph.vo.ALarmVO;

/**
 * 后台管理
 * @author fantedan@tieserv.com
 * @date 2015-6-16 上午10:44:50
 */
@Controller
@RequestMapping("/alarmWeb")
public class AlarmController {
	
	@Autowired AlarmDispatchService alarmDispatchService;
	
	/**
	 * 常规警情界面 跳转
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = {"/gotoAlarmIndex.do", "/gotoAlarmIndex.action"})
	@ResponseBody
	public ModelAndView gotoAlarmIndex(
			HttpServletRequest request){
		ModelAndView mv = new ModelAndView("/base/alarm/nomalAlarm/nomalAlarmIndex.jsp");
		mv.addObject("num", "400");		
		return mv;
	}
	
	/**
	 * 获取警情简要列表
	 * @param request
	 * @param 
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/getAlarmList.do")
	@ResponseBody
	public PageReturn getAlarmList(
				@RequestParam(value = "alarmState", required = false) String alarmState,
				@RequestParam(value = "organCodes", required = false) String organCodes,
				@RequestParam(value = "alarmPhone", required = false) String alarmPhone,
				@RequestParam(value = "alarmLocation", required = false) Integer alarmLocation,
				@RequestParam(value = "keyWord", required = false) String keyWord,
				@RequestParam(value = "alarmAddress", required = false) String alarmAddress,
				@RequestParam(value = "alarmLevel", required = false) Integer alarmLevel,
				@RequestParam(value = "alarmTypeOne", required = false) String[] alarmTypeOne,
				@RequestParam(value = "alarmTypeTwo", required = false) String alarmTypeTwo,
				@RequestParam(value = "startTime", required = false) String startTime,
				@RequestParam(value = "endTime", required = false) String endTime,
				@RequestParam(value = "alarmFine", required = false) String[] alarmFine,
				HttpServletRequest request) throws ParseException {
		//List<JJDBView> alarmList = alarmDispatchService.getJjdbListByQuery(alarmvo.ctrate());
		JJDBQuery query = new JJDBQuery();
		if(!StringUtils.isEmpty(alarmState)){
			int[] state = null;
			String[] strs=alarmState.split(",");
			state=new int[strs.length];
			for(int i=0;i<strs.length;i++){
				String str=strs[i];
				Integer intr=Integer.parseInt(str);
				state[i]=intr;
			}
			query.setAjzt(state);
		}
		if(alarmLocation != null){query.setAlarmLocation(alarmLocation);}
		if(!StringUtils.isEmpty(alarmPhone)){query.setBjdh(alarmPhone);}
		//if(this.alarmTypeOne != null){query.setBjlb(this.alarmTypeOne);}
		if(!StringUtils.isEmpty(alarmTypeTwo)){
			System.out.println("********alarmTypeTwo="+alarmTypeTwo.toString());
			String[] bjlx = null;
			String[] strt=alarmTypeTwo.split(",");
			bjlx=new String[strt.length];
			for(int i=0;i<strt.length;i++){
				String str=strt[i];
				bjlx[i]=str;
			}
			query.setBjlx(bjlx);
			}		
		//if(this.alarmFine != null){query.setBjxl(this.alarmFine);}
		if(alarmLevel != null){query.setCaseLevel(alarmLevel);}
		if(!StringUtils.isEmpty(keyWord)){query.setKeyWord(keyWord);}
		if(!StringUtils.isEmpty(organCodes)){
		System.out.println("********alarmTypeTwo="+alarmTypeTwo.toString());
		String[] codes = null;
		String[] strc=organCodes.split(",");
		codes=new String[strc.length];
		for(int i=0;i<strc.length;i++){
			String str=strc[i];
			codes[i]=str;
		}
		query.setOrganCodes(codes);
		}
		if(!StringUtils.isEmpty(alarmAddress)){query.setSfdz(alarmAddress);}
		if(!StringUtils.isEmpty(startTime)){query.setStartTime(DateUtil.parse(startTime, "yyyyMMddHHmmss"));}
		if(!StringUtils.isEmpty(endTime)){query.setEndTime(DateUtil.parse(endTime, "yyyyMMddHHmmss"));}
		List<JJDBView> alarmList = alarmDispatchService.getJjdbListByQuery(query);
		System.out.println("*************list.size="+alarmList.size());
		return PageReturn.SUCCESS("警情简要信息列表", alarmList);
	}
	
	/**
	 * 获取警情详情
	 * @param request
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "/getAlarmInfo.do")
	@ResponseBody
	public ReturnResult getAlarmInfo(HttpServletRequest request,
			@RequestParam(value = "jjdbh", required = false) String jjdbh) {
		Jjdb110 alarmInfo = alarmDispatchService.getAlarmInfo(jjdbh);
		return ReturnResult.SUCCESS("警情详情", alarmInfo);
	}
	
	/**
	 * WEB获取警情详情
	 * @param request
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "/gotoAlarmInfo.do")
	@ResponseBody
	public ModelAndView gotoAlarmInfo(HttpServletRequest request,
			@RequestParam(value = "jjdbh", required = false) String jjdbh) {
		Jjdb110 alarmInfo = alarmDispatchService.getAlarmDetail(jjdbh);
		ModelAndView mv = new ModelAndView("/base/alarm/nomalAlarm/alarmDetailInfo.jsp");
		mv.addObject("alarmInfo", alarmInfo);
		return mv;
	}

}
