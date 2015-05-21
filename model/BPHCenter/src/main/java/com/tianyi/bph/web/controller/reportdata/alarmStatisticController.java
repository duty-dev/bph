package com.tianyi.bph.web.controller.reportdata;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianyi.bph.common.MessageCode;
import com.tianyi.bph.common.ReturnResult;
import com.tianyi.bph.domain.alarm.AlarmType;
import com.tianyi.bph.query.report.AlarmTypeVM;
import com.tianyi.bph.service.alarm.AlarmDispatchService;

@Controller
@RequestMapping("/alarmStatisticWeb")
public class alarmStatisticController {
	// getAlarmTypeList

	@Autowired
	AlarmDispatchService alarmDispatchService;

	@RequestMapping(value = "/getAlarmTypeList.do")
	public @ResponseBody
	ReturnResult getAlarmTypeList(
			@RequestParam(value = "parentCode", required = false) String parentCode,
			HttpServletRequest request) {
		try {
			List<AlarmType> list = new ArrayList<AlarmType>();
			list = alarmDispatchService.getAlarmTypeList(parentCode);

			List<AlarmTypeVM> vmList = new ArrayList<AlarmTypeVM>();
			vmList = createVMList(list);
			return ReturnResult.MESSAGE(MessageCode.STATUS_SUCESS,
					MessageCode.SELECT_SUCCESS, 0, vmList);

		} catch (Exception ex) {
			return ReturnResult.MESSAGE(MessageCode.STATUS_FAIL,
					MessageCode.SELECT_ORGAN_FAIL, 0, null);
		}
	}

	private List<AlarmTypeVM> createVMList(List<AlarmType> list) {
		// TODO Auto-generated method stub
		List<AlarmTypeVM> atList = new ArrayList<AlarmTypeVM>();
		for (AlarmType at : list) {
			if (at.getParentTypeCode().equals("caseType110")) {
				AlarmTypeVM arvm = new AlarmTypeVM();
				arvm.setAlarmType(at);
				List<AlarmType> lists = new ArrayList<AlarmType>();
				for (AlarmType att : list) {
					String str = at.getTypeCode().substring(0,
							at.getTypeCode().length() - 4);
					String substr = att.getTypeCode().substring(0,
							att.getTypeCode().length() - 4);
					if (substr.equals(str)
							&& !at.getTypeCode().equals(att.getTypeCode())) {
						lists.add(att);
					}
				}
				arvm.setAlarmTypeList(lists);
				atList.add(arvm);
			}
		}
		return atList;
	}
	
	
	@RequestMapping(value = "/getReportDataByQuery.do")
	public @ResponseBody
	ReturnResult getReportDataByQuery(
			@RequestParam(value = "QueryString", required = false) String QueryString,
			HttpServletRequest request) {
		try {
			 
			return ReturnResult.MESSAGE(MessageCode.STATUS_SUCESS,
					MessageCode.SELECT_SUCCESS, 0, null);

		} catch (Exception ex) {
			return ReturnResult.MESSAGE(MessageCode.STATUS_FAIL,
					MessageCode.SELECT_ORGAN_FAIL, 0, null);
		}
	}
}
