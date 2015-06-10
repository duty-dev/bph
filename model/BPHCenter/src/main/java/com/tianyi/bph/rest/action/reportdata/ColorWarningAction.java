package com.tianyi.bph.rest.action.reportdata;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianyi.bph.common.ReturnResult;
import com.tianyi.bph.domain.basicdata.Gps;
import com.tianyi.bph.query.report.ColorWarningList;
import com.tianyi.bph.query.report.ColorWarningResultList;
import com.tianyi.bph.query.report.WarningCfgVM;
import com.tianyi.bph.service.report.CaseReportService;
import com.tianyi.bph.service.report.WarningCfgService;

@Controller
@RequestMapping("/colorWarning")
public class ColorWarningAction {

	@Autowired
	WarningCfgService warningService;

	@Autowired
	private CaseReportService caseReportService;
	/**
	 * 获取预警列表信息
	 * 
	 * @param request 
	 * @param organId
	 *            机构Id
	 * @return
	 */
	@RequestMapping(value = "/getWarningCfgList.do")
	@ResponseBody
	public ReturnResult getWarningCfgList(
			@RequestParam(value = "organId", required = true) Integer organId) {
		try {
			List<ColorWarningList> cwcfgList = warningService.getWarningCfgList(organId);
			return ReturnResult.SUCCESS("预警列表信息", cwcfgList);
		} catch (Exception ex) {
			return ReturnResult.SUCCESS("调用接口方法，传入参数为空", null);
		}
	}
	/**
	 * 获取GPS设备信息
	 * 
	 * @param request
	 * @param userId
	 *            用户Id
	 * @param organId
	 *            机构Id
	 * @return
	 */
	@RequestMapping(value = "/getWarningReport.do")
	@ResponseBody
	public ReturnResult getWarningReport(
			@RequestParam(value = "query", required = true) String query) {
		try {
			List<ColorWarningResultList> cwcfgList = caseReportService.getWarningReport(query);
			return ReturnResult.SUCCESS("预警列表信息", cwcfgList);
		} catch (Exception ex) {
			return ReturnResult.SUCCESS("调用接口方法，传入参数为错误", null);
		}
	}

}
