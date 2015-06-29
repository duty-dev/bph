package com.tianyi.bph.rest.action.reportdata;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianyi.bph.common.ReturnResult; 
import com.tianyi.bph.domain.report.CaseGpsInfo;
import com.tianyi.bph.domain.system.Organ;
import com.tianyi.bph.domain.system.User;
import com.tianyi.bph.query.report.ColorWarningList;
import com.tianyi.bph.query.report.ColorWarningResultList;
import com.tianyi.bph.query.report.QueryCondition; 
import com.tianyi.bph.service.report.CaseReportService;
import com.tianyi.bph.service.report.WarningCfgService;
import com.tianyi.bph.service.system.OrganService;

@Controller
@RequestMapping("/colorWarning")
public class ColorWarningAction {

	@Autowired
	WarningCfgService warningService;

	@Autowired
	private CaseReportService caseReportService;
	@Autowired
	private OrganService organService;
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
			HttpServletRequest request) {
		try {
			User user = (User) request.getAttribute("User"); 
			//int	organId = Integer.parseInt(user.getUserId().toString()); 
			int	organId = user.getOrgId();
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
			HttpServletRequest request,
			@RequestParam(value = "organId", required = false) Integer organId,
			@RequestParam(value = "warningcfgId", required = true) Integer warningcfgId,
			@RequestParam(value = "periodType", required = true) Integer periodType,
			@RequestParam(value = "startDate", required = true) String startDate,
			@RequestParam(value = "endDate", required = true) String endDate) {
		try {
			User user = (User) request.getAttribute("User");
			if (organId == null) {
				organId = user.getOrgId();
			}
			QueryCondition queryCondition = new QueryCondition();
			queryCondition.setOrganId(organId);
			queryCondition.setPeriodType(periodType);
			queryCondition.setWarningCfgId(warningcfgId);
			queryCondition.setStartDate(startDate);
			queryCondition.setEndDate(endDate);
			List<ColorWarningResultList> cwcfgList = caseReportService.getWarningReport(queryCondition);
			return ReturnResult.SUCCESS("预警列表信息", cwcfgList);
		} catch (Exception ex) {
			return ReturnResult.SUCCESS("调用接口方法，传入参数为错误", null);
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
	@RequestMapping(value = "/getWarningMapReport.do")
	@ResponseBody
	public ReturnResult getWarningMapReport(
			HttpServletRequest request,
			@RequestParam(value = "organId", required = false) Integer organId,
			@RequestParam(value = "startDate", required = true) String startDate,
			@RequestParam(value = "endDate", required = true) String endDate,
			@RequestParam(value = "caseLevel", required = true) String caseLevel,
			@RequestParam(value = "timeSpan", required = true) String timeSpan,
			@RequestParam(value = "caseType", required = true) String caseType) {
		try {
			User user = (User) request.getAttribute("User"); 
			if (organId == null) {
				organId = user.getOrgId();
			}  
			Organ organ = new Organ();
			//organ = organService.getOrganById(organId);
			organ=organService.getOrganByPrimaryKey(organId);
			QueryCondition queryCondition = new QueryCondition(); 
			queryCondition.setOrganId(organId);
			queryCondition.setOrganPath(organ.getPath());
			List<Integer> clel = new ArrayList<Integer>();
			String[] levels = caseLevel.split(",");
			for(String s :levels){
				clel.add(Integer.parseInt(s));
			}
			queryCondition.setCaseLevels(clel);
			String[] typecodes = caseType.split(",");
			List<String> codes = new ArrayList<String>();
			for(String m :typecodes){
				codes.add(m);
			} 
			queryCondition.setCaseType(codes);
			List<Integer> hours = new ArrayList<Integer>();
			String[] times = timeSpan.split(",");
			for(String x :times){
				hours.add(Integer.parseInt(x));
			}  
			queryCondition.setCaseTimaSpan(hours);
			queryCondition.setStartDate(startDate);
			queryCondition.setEndDate(endDate);  
			List<CaseGpsInfo> list = caseReportService.loadCaseGpsList(queryCondition);
			return ReturnResult.SUCCESS("预警地图数据信息", list);
		} catch (Exception ex) {
			return ReturnResult.SUCCESS("调用接口方法，传入参数为错误", null);
		}
	}
}
