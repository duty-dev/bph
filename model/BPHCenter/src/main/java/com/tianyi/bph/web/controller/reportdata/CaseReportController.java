package com.tianyi.bph.web.controller.reportdata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianyi.bph.common.MessageCode;
import com.tianyi.bph.common.ReturnResult;
import com.tianyi.bph.domain.alarm.AlarmType;
import com.tianyi.bph.domain.report.CaseTypeAGGR;
import com.tianyi.bph.query.report.AlarmTypeVM;
import com.tianyi.bph.service.report.CaseReportService;

@Controller
@RequestMapping("/caseReportWeb")
public class CaseReportController {
	
	@Autowired
	private CaseReportService caseReportService;
	
	@RequestMapping(value = "/loadCaseTypeReport.do")
	public @ResponseBody ReturnResult loadCaseTypeReport(
			@RequestParam(value = "query", required = false) String query,
			HttpServletRequest request) {
		try {
			
			Map<String,Object> map=new HashMap<String,Object>();
			List<CaseTypeAGGR>  ls=null;
			
			map.put("orgFullPath", "/510000000000");
			map.put("beginYMD", 20140901);
			map.put("endYMD", 20140930);
			List<Integer>  levels=new ArrayList<Integer>();
			levels.add(0);
			levels.add(1);
			levels.add(2);
			map.put("levels",levels );
			map.put("beginHour", 0);
			map.put("endHour", 23);
			
			ls=caseReportService.loadCaseTypeReport(map);
			
			return ReturnResult.MESSAGE(MessageCode.STATUS_SUCESS,
					MessageCode.SELECT_SUCCESS, 0, ls);

		} catch (Exception ex) {
			return ReturnResult.MESSAGE(MessageCode.STATUS_FAIL,
					MessageCode.SELECT_ORGAN_FAIL, 0, null);
		}
	}
	
	
}
