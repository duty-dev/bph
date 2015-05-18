//package com.tianyi.bph.rest.action.alarm;
//
//import java.text.ParseException;
//import java.util.Date;
//import java.util.List;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.tianyi.bph.common.DateUtil;
//import com.tianyi.bph.common.JsonUtils;
//import com.tianyi.bph.common.ReturnResult;
//import com.tianyi.bph.domain.alarm.AlarmCommunication;
//import com.tianyi.bph.domain.alarm.AlarmType;
//import com.tianyi.bph.domain.alarm.JJDBState;
//import com.tianyi.bph.domain.alarm.Jjdb110;
//import com.tianyi.bph.domain.alarm.PJPolice;
//import com.tianyi.bph.query.alarm.JJDBView;
//import com.tianyi.bph.service.alarm.AlarmDispatchService;
//import com.tianyi.bph.vo.ALarmVO;
//
///**
// * 警情接口
// * @author fantedan@tieserv.com
// * @date 2015-4-9 上午10:44:50
// */
//@Controller
//@RequestMapping("/alarm")
//public class AlarmAction {
//	
//	@Autowired AlarmDispatchService alarmDispatchService;
//	
//	/**
//	 * 获取警情简要列表
//	 * @param request
//	 * @param 
//	 * @return
//	 * @throws ParseException 
//	 */
//	//@RequestMapping(value = "/getAlarmList.do", method ={ RequestMethod.GET, RequestMethod.POST })
//	@RequestMapping(value = "/getAlarmList.do")
//	@ResponseBody
//	public ReturnResult getAlarmList(
////				@RequestParam(value = "alarmState", required = false) String alarmState,
////				@RequestParam(value = "organCodes", required = false) String organCodes,
////				@RequestParam(value = "alarmPhone", required = false) String alarmPhone,
////				@RequestParam(value = "alarmLocation", required = false) String alarmLocation,
////				@RequestParam(value = "keyWord", required = false) String keyWord,
////				@RequestParam(value = "alarmAddress", required = false) String alarmAddress,
////				@RequestParam(value = "alarmLevel", required = false) Integer alarmLevel,
////				@RequestParam(value = "alarmTypeOne", required = false) String alarmTypeOne,
////				@RequestParam(value = "alarmTypeTwo", required = false) String alarmTypeTwo,
////				@RequestParam(value = "startTime", required = false) String startTime,
////				@RequestParam(value = "endTime", required = false) String endTime,
////				@RequestParam(value = "alarmFine", required = false) String alarmFine){
//			@RequestBody String body,HttpServletRequest request) throws ParseException {
//		System.out.println("***body="+body);
//		ALarmVO alarmvo = JsonUtils.toObj(body, ALarmVO.class);
//		System.out.println("***alarmLocation="+alarmvo.getAlarmLocation());
//		List<JJDBView> alarmList = alarmDispatchService.getJjdbListByQuery(alarmvo.ctrate());
//		return ReturnResult.SUCCESS("警情详情", alarmList);
//	}
//	
//	/**
//	 * 获取警情状态列表
//	 * @param request
//	 * @param 
//	 * @return
//	 */
//	@RequestMapping(value = "/getAlarmState.do")
//	@ResponseBody
//	public ReturnResult getAlarmState() {
//		List<JJDBState> stateList = alarmDispatchService.getJjdbStateList();
//		return ReturnResult.SUCCESS("警情状态列表", stateList);
//	}
//	
//	/**
//	 * 获取警情详情
//	 * @param request
//	 * @param 
//	 * @return
//	 */
//	@RequestMapping(value = "/getAlarmInfo.do")
//	@ResponseBody
//	public ReturnResult getAlarmInfo(HttpServletRequest request,
//			@RequestParam(value = "jjdbh", required = false) String jjdbh) {
//		Jjdb110 alarmInfo = alarmDispatchService.getAlarmInfo(jjdbh);
//		return ReturnResult.SUCCESS("警情详情", alarmInfo);
//	}
//	
//	/**
//	 * 获取警情类型列表
//	 * @param parentType
//	 * @param 
//	 * @return
//	 */
//	@RequestMapping(value = "/getAlarmTypeList.do")
//	@ResponseBody
//	public ReturnResult getAlarmTypeList(HttpServletRequest request,
//			@RequestParam(value = "parentType", required = false) String parentType) {
//		List<AlarmType> typeList = alarmDispatchService.getAlarmTypeList(parentType);
//		return ReturnResult.SUCCESS("警情类型列表", typeList);
//	}
//	
//	/**
//	 * 更新警情详情
//	 * @param request
//	 * @param 
//	 * @return
//	 * @throws ParseException 
//	 */
//	@RequestMapping(value = "/updateAlarmInfo.do")
//	@ResponseBody
//	public ReturnResult updateAlarmInfo(HttpServletRequest request,
//			@RequestParam(value = "jjdbh", required = false) String jjdbh,
//			@RequestParam(value = "mark", required = false) boolean mark,
//			@RequestParam(value = "markUserId", required = false) Integer markUserId,
//			@RequestParam(value = "markTime", required = false) String markTime,
//			@RequestParam(value = "gpsConfig", required = false) String gpsConfig) throws ParseException {
//		Jjdb110 alarmInfo = new Jjdb110();
//		try{
//			if(!StringUtils.isEmpty(jjdbh)){alarmInfo.setJjdbh(jjdbh);}	
//			if(markUserId != null){alarmInfo.setMarkUserId(markUserId);}
//			if(!StringUtils.isEmpty(markTime)){alarmInfo.setMarkTime(DateUtil.parse(markTime, "yyyyMMddHHmmss"));}
//			if(!StringUtils.isEmpty(gpsConfig)){alarmInfo.setGpsConfig(gpsConfig);}
//			alarmInfo.setMark(mark);
//			alarmDispatchService.updateJjdbInfo(alarmInfo);
//			return ReturnResult.SUCCESS("警情更新");
//		}catch(Exception e){
//			e.printStackTrace();
//			return ReturnResult.FAILUER("警情更新失败");
//		}
//	}
//	
//	/**
//	 * 警情派警
//	 * @param request
//	 * @param 
//	 * @return
//	 * @throws ParseException 
//	 */
//	@RequestMapping(value = "/dispatchPolice.do")
//	@ResponseBody
//	public ReturnResult dispatchPolice(HttpServletRequest request,
//			@RequestParam(value = "jjdbh", required = false) String jjdbh,
//			@RequestParam(value = "pjPoliceId", required = false) Integer pjPoliceId,
//			@RequestParam(value = "pjVehicleId", required = false) Integer pjVehicleId,
//			@RequestParam(value = "reportType", required = false) Integer reportType,
//			@RequestParam(value = "startTime", required = false) String startTime,
//			@RequestParam(value = "endTime", required = false) String endTime,
//			@RequestParam(value = "arriveTime", required = false) String arriveTime,
//			@RequestParam(value = "leaveTime", required = false) String leaveTime,
//			@RequestParam(value = "pjUserId", required = false) Integer pjUserId,
//			@RequestParam(value = "pjTime", required = false) String pjTime) throws ParseException {
//		PJPolice pjPolice = new PJPolice();		
//		try{
//			if(!StringUtils.isEmpty(jjdbh)){pjPolice.setJjdbh(jjdbh);}
//			if(pjPoliceId != null){pjPolice.setPjPoliceId(pjPoliceId);}
//			if(reportType != null){pjPolice.setReportType(reportType);}
//			if(!StringUtils.isEmpty(startTime)){pjPolice.setStartTime((DateUtil.parse(startTime, "yyyyMMddHHmmss")));}
//			if(!StringUtils.isEmpty(endTime)){pjPolice.setEndTime((DateUtil.parse(endTime, "yyyyMMddHHmmss")));}
//			if(!StringUtils.isEmpty(arriveTime)){pjPolice.setArriveTime((DateUtil.parse(arriveTime, "yyyyMMddHHmmss")));}
//			if(!StringUtils.isEmpty(leaveTime)){pjPolice.setLeaveTime((DateUtil.parse(leaveTime, "yyyyMMddHHmmss")));}
//			if(pjUserId != null){pjPolice.setPjUserId(pjUserId);}
//			alarmDispatchService.dispatchPolice(pjPolice);
//			return ReturnResult.SUCCESS("警情派警");
//		}catch(Exception e){
//			e.printStackTrace();
//			return ReturnResult.FAILUER("警情派警失败");
//		}
//	}
//	
//	/**
//	 * 警情消息推送
//	 * @param request
//	 * @param 
//	 * @return
//	 * @throws ParseException 
//	 */
//	@RequestMapping(value = "/pushAlarmMsg.do")
//	@ResponseBody
//	public ReturnResult pushAlarmMsg(HttpServletRequest request,
//			@RequestParam(value = "jjdbh", required = false) String jjdbh,
//			@RequestParam(value = "policeId", required = false) Integer policeId,
//			@RequestParam(value = "thetime", required = false) String thetime,
//			@RequestParam(value = "source", required = false) Integer source,
//			@RequestParam(value = "infoType", required = false) Integer infoType,
//			@RequestParam(value = "textInfo", required = false) String textInfo,
//			@RequestParam(value = "fileCode", required = false) String fileCode,
//			@RequestParam(value = "userId", required = false) Integer userId,
//			@RequestParam(value = "isByMyself", required = false) Integer isByMyself) throws ParseException {
//		AlarmCommunication alarmCommunication = new AlarmCommunication();
//		try{
//			if(!StringUtils.isEmpty(jjdbh)){alarmCommunication.setJjdbh(jjdbh);}
//			if(!StringUtils.isEmpty(thetime)){alarmCommunication.setThetime((DateUtil.parse(thetime, "yyyyMMddHHmmss")));}
//			if(!StringUtils.isEmpty(fileCode)){alarmCommunication.setFileCode(fileCode);}
//			if(!StringUtils.isEmpty(textInfo)){
//				String infoString = new String(textInfo.getBytes("ISO-8859-1"), "UTF-8");
//				alarmCommunication.setTextInfo(infoString);
//				}
//			if(policeId != null){alarmCommunication.setPoliceId(policeId);}
//			if(source != null){alarmCommunication.setSource(source);}
//			if(infoType != null){alarmCommunication.setInfoType(infoType);}
//			if(userId != null){alarmCommunication.setUserId(userId);}
//			if(isByMyself != null){alarmCommunication.setIsByMyself(isByMyself);}
//			alarmDispatchService.pushAlarmMsg(alarmCommunication);
//			return ReturnResult.SUCCESS("警情消息推送");
//		}catch (Exception e) {
//			e.printStackTrace();
//			return ReturnResult.FAILUER("警情消息推送失败");
//		}
//	}
//	
//	/**
//	 * 警情派警
//	 * @param request
//	 * @param 
//	 * @return
//	 * @throws ParseException 
//	 */
//	@RequestMapping(value = "/deleteDispatchPolice.do")
//	@ResponseBody
//	public ReturnResult deleteDispatchPolice(HttpServletRequest request,
//			@RequestParam(value = "jjdbh", required = false) String jjdbh,
//			@RequestParam(value = "pjPoliceId", required = false) Integer pjPoliceId){
//		PJPolice pjPolice = new PJPolice();		
//		try{
//			if(!StringUtils.isEmpty(jjdbh)){pjPolice.setJjdbh(jjdbh);}
//			if(pjPoliceId != null){pjPolice.setPjPoliceId(pjPoliceId);}
//			alarmDispatchService.deletePJByPrimaryKey(pjPolice);
//			return ReturnResult.SUCCESS("删除派警");
//		}catch(Exception e){
//			e.printStackTrace();
//			return ReturnResult.FAILUER("删除派警失败");
//		}
//	}
//	
//
//}
