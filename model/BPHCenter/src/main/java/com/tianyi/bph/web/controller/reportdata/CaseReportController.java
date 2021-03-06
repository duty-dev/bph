package com.tianyi.bph.web.controller.reportdata;
 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianyi.bph.common.MessageCode;
import com.tianyi.bph.common.ReturnResult;
import com.tianyi.bph.domain.report.CaseHourAGGR;
import com.tianyi.bph.domain.report.CaseOrgAGGR;
import com.tianyi.bph.domain.report.CasePeriodAGGR;
import com.tianyi.bph.domain.report.CaseReportResult;
import com.tianyi.bph.domain.report.CaseTypeAGGR; 
import com.tianyi.bph.domain.report.ReportPeriod;

import com.tianyi.bph.domain.report.WarningOrgAGGR; 

import com.tianyi.bph.domain.system.Organ;
import com.tianyi.bph.domain.system.User;
import com.tianyi.bph.query.report.ColorWarningResultList;
import com.tianyi.bph.query.report.QueryCondition; 
import com.tianyi.bph.service.report.CaseReportService;
import com.tianyi.bph.service.report.WarningCfgService;
import com.tianyi.bph.service.system.OrganService;

@Controller
@RequestMapping("/caseReportWeb")
public class CaseReportController {

	@Autowired
	private OrganService organService;
	@Autowired
	private CaseReportService caseReportService;
	@Autowired
	WarningCfgService warningService;

	@RequestMapping(value = "/loadCaseTypeReport.do")
	public @ResponseBody
	ReturnResult loadCaseTypeReport(
			@RequestParam(value = "query", required = false) String query,
			HttpServletRequest request) {
		try {			
			JSONObject jobj = JSONObject.fromObject(query);
			Map<String, Class<?>> classMap = new HashMap<String, Class<?>>();

			classMap.put("caseType", String.class);
			classMap.put("caseTimaSpan", Integer.class);
			classMap.put("caseLevels", Integer.class);
			QueryCondition queryCondition = (QueryCondition) JSONObject.toBean(
					jobj, QueryCondition.class, classMap);

			Map<String, Object> map = new HashMap<String, Object>();
			List<CaseReportResult<CaseTypeAGGR>> results = new ArrayList<CaseReportResult<CaseTypeAGGR>>();
			List<CaseTypeAGGR> ls = null;
			// 组织机构id
			int organId = queryCondition.getOrganId();
			User user = (User) request.getAttribute("User");
			if (organId == 0) {
				organId = user.getOrgId();
			}
			Organ organ = organService.getOrganByPrimaryKey(organId); 
			map.put("orgPath", organ.getPath());  
			 
			//当期
			CaseReportResult<CaseTypeAGGR> cResult=new CaseReportResult<CaseTypeAGGR>();  
			// 查询时间
			// 查询时间方式，1、月 2、日
			String startDate = queryCondition.getStartDate();
			String sDate = startDate.replace("-", "").trim();
			String endDate = queryCondition.getEndDate();
			String eDate = endDate.replace("-", "").trim();
			Integer bd = Integer.parseInt(sDate);
			Integer ed = Integer.parseInt(eDate);
			ReportPeriod rp = new ReportPeriod(bd, ed,
					queryCondition.getPeriodType());
			// 警情类型 父级节点
			List<String> caseTypes = queryCondition.getCaseType();
			// 警情级别 二级节点
			List<Integer> levels = queryCondition.getCaseLevels();
			// 时间区间 时间节点
			List<Integer> hours = queryCondition.getCaseTimaSpan();
			map.put("levels", levels);
			map.put("hours", hours); 
			map.put("beginYMD", rp.getBeginYmd());
			map.put("endYMD", rp.getEndYmd());
			map.put("type2Codes",caseTypes);
			ls = caseReportService.loadCaseTypeReport(map);
			ls = getSelectCaseTypeList(ls, caseTypes);
			cResult.setBeginYmd(rp.getBeginYmd());
			cResult.setEndYmd(rp.getEndYmd());
			cResult.setData(ls);
			results.add(cResult);

			// 同比
			CaseReportResult<CaseTypeAGGR> yResult = new CaseReportResult<CaseTypeAGGR>();
			map.put("beginYMD", rp.getYOYBeginYmd());
			map.put("endYMD", rp.getYOYEndYmd());
			ls = caseReportService.loadCaseTypeReport(map);
			ls = getSelectCaseTypeList(ls, caseTypes);
			yResult.setBeginYmd(rp.getYOYBeginYmd());
			yResult.setEndYmd(rp.getYOYEndYmd());
			yResult.setData(ls);
			results.add(yResult);

			// 环比
			CaseReportResult<CaseTypeAGGR> mResult = new CaseReportResult<CaseTypeAGGR>();
			map.put("beginYMD", rp.getMOMBeginYmd());
			map.put("endYMD", rp.getMOMEndYmd());
			ls = caseReportService.loadCaseTypeReport(map);
			ls = getSelectCaseTypeList(ls, caseTypes);
			mResult.setBeginYmd(rp.getMOMBeginYmd());
			mResult.setEndYmd(rp.getMOMEndYmd()); 
			mResult.setData(ls);
			results.add(mResult);
			//保存筛选条件
			saveUserQuery(user.getUserId(),query);
			
			return ReturnResult.MESSAGE(MessageCode.STATUS_SUCESS,
					MessageCode.SELECT_SUCCESS, 0, results);

		} catch (Exception ex) {
			return ReturnResult.MESSAGE(MessageCode.STATUS_FAIL,
					MessageCode.SELECT_ORGAN_FAIL, 0, null);
		}
	}
	private void saveUserQuery(Long userId,String query){
		caseReportService.insertQuery(userId,query);
	}
	private List<CaseTypeAGGR> getSelectCaseTypeList(List<CaseTypeAGGR> ls,
			List<String> caseTypes) {
		// TODO Auto-generated method stub
		List<CaseTypeAGGR> list = new ArrayList<CaseTypeAGGR>();
		for (String code : caseTypes) {
			for (CaseTypeAGGR cta : ls) {
				if (cta.getTypeCode().equals(code)) {
					list.add(cta);
				}
			} 
		}
		return list; 
	}

	@RequestMapping(value = "/loadCasePeriodReport.do")  
	public @ResponseBody
	ReturnResult loadCasePeriodReport( 
			@RequestParam(value = "query", required = false) String query,
			HttpServletRequest request) {
		try { 
		 
			JSONObject jobj = JSONObject.fromObject(query);
			Map<String, Class<?>> classMap = new HashMap<String, Class<?>>();

			classMap.put("caseType", String.class);
			classMap.put("caseTimaSpan", Integer.class);
			classMap.put("caseLevels", Integer.class);
			QueryCondition queryCondition = (QueryCondition) JSONObject.toBean(
					jobj, QueryCondition.class, classMap);

			Map<String, Object> map = new HashMap<String, Object>();
			List<CaseReportResult<CasePeriodAGGR>> results = new ArrayList<CaseReportResult<CasePeriodAGGR>>();
			List<CasePeriodAGGR> ls = null;
			// 组织机构id
			int organId = queryCondition.getOrganId();
			User user = (User) request.getAttribute("User");
			if (organId == 0) {
				organId = user.getOrgId();
			}
			Organ organ = organService.getOrganByPrimaryKey(organId);
			map.put("orgPath", organ.getPath());

			// 查询时间
			// 查询时间方式，1、月 2、日
			String startDate = queryCondition.getStartDate();
			String sDate = startDate.replace("-", "").trim();
			String endDate = queryCondition.getEndDate();
			String eDate = endDate.replace("-", "").trim();
			Integer bd = Integer.parseInt(sDate);
			Integer ed = Integer.parseInt(eDate);
			ReportPeriod rp = new ReportPeriod(bd, ed,
					queryCondition.getPeriodType());
			// 警情级别 二级节点
			List<Integer> levels = queryCondition.getCaseLevels();
			// 时间区间 时间节点
			List<Integer> hours = queryCondition.getCaseTimaSpan();
			map.put("levels", levels);
			map.put("hours", hours);

			map.put("periodType", queryCondition.getPeriodType());
			// 警情类型 父级节点
			List<String> type2Codes = queryCondition.getCaseType();
			map.put("type2Codes", type2Codes);

			// 当期
			CaseReportResult<CasePeriodAGGR> cResult = new CaseReportResult<CasePeriodAGGR>(); 
			map.put("beginYMD", rp.getBeginYmd());
			map.put("endYMD", rp.getEndYmd());
			
			ls = caseReportService.loadCasePeriodReport(map);
			cResult.setBeginYmd(rp.getBeginYmd());
			cResult.setEndYmd(rp.getEndYmd());
			
			cResult.setData(ls);
			results.add(cResult);

			// 同比
			CaseReportResult<CasePeriodAGGR> yResult = new CaseReportResult<CasePeriodAGGR>();
			map.put("beginYMD", rp.getYOYBeginYmd());
			map.put("endYMD", rp.getYOYEndYmd());
			ls = caseReportService.loadCasePeriodReport(map);
			yResult.setBeginYmd(rp.getYOYBeginYmd());
			yResult.setEndYmd(rp.getYOYEndYmd());
			
			yResult.setData(ls);
			results.add(yResult);

			// 环比
			CaseReportResult<CasePeriodAGGR> mResult = new CaseReportResult<CasePeriodAGGR>();
			map.put("beginYMD", rp.getMOMBeginYmd());
			map.put("endYMD", rp.getMOMEndYmd());
			ls = caseReportService.loadCasePeriodReport(map);
			mResult.setBeginYmd(rp.getMOMBeginYmd());
			mResult.setEndYmd(rp.getMOMEndYmd());
			
			mResult.setData(ls);
			results.add(mResult);

			return ReturnResult.MESSAGE(MessageCode.STATUS_SUCESS,
					MessageCode.SELECT_SUCCESS, 0, results);

		} catch (Exception ex) {
			return ReturnResult.MESSAGE(MessageCode.STATUS_FAIL,
					MessageCode.SELECT_ORGAN_FAIL, 0, null);
		}
	}

	@RequestMapping(value = "/loadCaseHourReport.do")
	public @ResponseBody
	ReturnResult loadCaseHourReport(
			@RequestParam(value = "query", required = false) String query,
			HttpServletRequest request) {
		try {  
			JSONObject jobj = JSONObject.fromObject(query);
			Map<String, Class<?>> classMap = new HashMap<String, Class<?>>();

			classMap.put("caseType", String.class);
			classMap.put("caseTimaSpan", Integer.class);
			classMap.put("caseLevels", Integer.class);
			QueryCondition queryCondition = (QueryCondition) JSONObject.toBean(
					jobj, QueryCondition.class, classMap);

			Map<String, Object> map = new HashMap<String, Object>();
			List<CaseReportResult<CaseHourAGGR>> results = new ArrayList<CaseReportResult<CaseHourAGGR>>();
			List<CaseHourAGGR> ls = null;
			// 组织机构id
			int organId = queryCondition.getOrganId();
			User user = (User) request.getAttribute("User");
			if (organId == 0) {
				organId = user.getOrgId();
			}
			Organ organ = organService.getOrganByPrimaryKey(organId);
			map.put("orgPath", organ.getPath());

			// 查询时间
			// 查询时间方式，1、月 2、日
			String startDate = queryCondition.getStartDate();
			String sDate = startDate.replace("-", "").trim();
			String endDate = queryCondition.getEndDate();
			String eDate = endDate.replace("-", "").trim();
			Integer bd = Integer.parseInt(sDate);
			Integer ed = Integer.parseInt(eDate);
			ReportPeriod rp = new ReportPeriod(bd, ed,
					queryCondition.getPeriodType());
			// 警情级别 二级节点
			List<Integer> levels = queryCondition.getCaseLevels();
			// 时间区间 时间节点
			List<Integer> hours = queryCondition.getCaseTimaSpan();
			map.put("levels", levels);
			map.put("hours", hours);

			map.put("periodType", queryCondition.getPeriodType());
			// 警情类型 父级节点
			List<String> type2Codes = queryCondition.getCaseType();
			map.put("type2Codes", type2Codes);

			// 当期
			CaseReportResult<CaseHourAGGR> cResult = new CaseReportResult<CaseHourAGGR>();  
			map.put("beginYMD", rp.getBeginYmd());
			map.put("endYMD", rp.getEndYmd());
			ls = caseReportService.loadCaseHourReport(map);
			cResult.setBeginYmd(rp.getBeginYmd());
			cResult.setEndYmd(rp.getEndYmd());
			cResult.setData(ls);
			results.add(cResult);

			// 同比
			CaseReportResult<CaseHourAGGR> yResult = new CaseReportResult<CaseHourAGGR>();
			map.put("beginYMD", rp.getYOYBeginYmd());
			map.put("endYMD", rp.getYOYEndYmd());
			ls = caseReportService.loadCaseHourReport(map);
			yResult.setBeginYmd(rp.getYOYBeginYmd());
			yResult.setEndYmd(rp.getYOYEndYmd());
			yResult.setData(ls);
			results.add(yResult);

			// 环比
			CaseReportResult<CaseHourAGGR> mResult = new CaseReportResult<CaseHourAGGR>();
			map.put("beginYMD", rp.getMOMBeginYmd());
			map.put("endYMD", rp.getMOMEndYmd());
			ls = caseReportService.loadCaseHourReport(map);
			mResult.setBeginYmd(rp.getMOMBeginYmd());
			mResult.setEndYmd(rp.getMOMEndYmd()); 
			mResult.setData(ls); 
			results.add(mResult);

			return ReturnResult.MESSAGE(MessageCode.STATUS_SUCESS,
					MessageCode.SELECT_SUCCESS, 0, results);

		} catch (Exception ex) {
			return ReturnResult.MESSAGE(MessageCode.STATUS_FAIL,
					MessageCode.SELECT_ORGAN_FAIL, 0, null);
		}

	}

	@RequestMapping(value = "/loadCaseOrgReport.do")
	public @ResponseBody
	ReturnResult loadCaseOrgReport(
			@RequestParam(value = "query", required = false) String query,
			HttpServletRequest request) {
		try { 
			JSONObject jobj = JSONObject.fromObject(query);
			Map<String, Class<?>> classMap = new HashMap<String, Class<?>>();

			classMap.put("caseType", String.class);
			classMap.put("caseTimaSpan", Integer.class);
			classMap.put("caseLevels", Integer.class);
			QueryCondition queryCondition = (QueryCondition) JSONObject.toBean(
					jobj, QueryCondition.class, classMap);

			Map<String, Object> map = new HashMap<String, Object>();
			List<CaseReportResult<CaseOrgAGGR>> results = new ArrayList<CaseReportResult<CaseOrgAGGR>>();
			List<CaseOrgAGGR> ls = null;
			// 组织机构id
			int organId = queryCondition.getOrganId();
			User user = (User) request.getAttribute("User");
			if (organId == 0) {
				organId = user.getOrgId();
			}
			Organ organ = organService.getOrganByPrimaryKey(organId);
			map.put("orgPath", organ.getPath());
			map.put("orgParentId", organId);

			// 查询时间
			// 查询时间方式，1、月 2、日
			String startDate = queryCondition.getStartDate();
			String sDate = startDate.replace("-", "").trim();
			String endDate = queryCondition.getEndDate();
			String eDate = endDate.replace("-", "").trim();
			Integer bd = Integer.parseInt(sDate);
			Integer ed = Integer.parseInt(eDate);
			ReportPeriod rp = new ReportPeriod(bd, ed,
					queryCondition.getPeriodType());
			// 警情级别 二级节点
			List<Integer> levels = queryCondition.getCaseLevels();
			// 时间区间 时间节点
			List<Integer> hours = queryCondition.getCaseTimaSpan();
			map.put("levels", levels);
			map.put("hours", hours);

			map.put("periodType", queryCondition.getPeriodType());
			// 警情类型 父级节点
			List<String> type2Codes = queryCondition.getCaseType();
			map.put("type2Codes", type2Codes);
			// 当期
			CaseReportResult<CaseOrgAGGR> cResult = new CaseReportResult<CaseOrgAGGR>(); 
			map.put("beginYMD", rp.getBeginYmd());
			map.put("endYMD", rp.getEndYmd());
			ls = caseReportService.loadCaseOrgReport(map);
			//ls = getSelectCaseTypeOrgList(ls,type2Codes);
			cResult.setBeginYmd(rp.getBeginYmd());
			cResult.setEndYmd(rp.getEndYmd());
			cResult.setData(ls);
			results.add(cResult);			
			//保存筛选条件
			saveUserQuery(user.getUserId(),query);
			
			return ReturnResult.MESSAGE(MessageCode.STATUS_SUCESS,
					MessageCode.SELECT_SUCCESS, 0, results);

		} catch (Exception ex) {
			return ReturnResult.MESSAGE(MessageCode.STATUS_FAIL,
					MessageCode.SELECT_ORGAN_FAIL, 0, null);
		}
	}

	private List<CaseOrgAGGR> getSelectCaseTypeOrgList(List<CaseOrgAGGR> ls,
			List<String> caseTypes) {
		// TODO Auto-generated method stub
		List<CaseOrgAGGR> list = new ArrayList<CaseOrgAGGR>();
		for(String code :caseTypes){
			for(CaseOrgAGGR cta:ls){
				if(cta.getTypeCode()==null){
					list.add(cta);
				}else if(cta.getTypeCode().equals(code)){
					list.add(cta);
				}
			}
		} 
		return list; 
	}
	@RequestMapping(value = "/loadWarningReport.do")
	public @ResponseBody
	ReturnResult loadWarningReport(
			@RequestParam(value = "query", required = false) String query, 
			HttpServletRequest request) {
	 
		try{
			JSONObject jobj = JSONObject.fromObject(query);
			Map<String, Class<?>> classMap = new HashMap<String, Class<?>>();

			classMap.put("caseType", String.class);
			classMap.put("caseTimaSpan", Integer.class);
			classMap.put("caseLevels", Integer.class);
			QueryCondition queryCondition = (QueryCondition) JSONObject.toBean(
					jobj, QueryCondition.class, classMap);

			Map<String, Object> map = new HashMap<String, Object>();

			int organId = queryCondition.getOrganId();
			User user = (User) request.getAttribute("User");
			if (organId == 0) {
				organId = user.getOrgId(); 
			}
			  
			return ReturnResult.MESSAGE(MessageCode.STATUS_SUCESS,
					MessageCode.SELECT_SUCCESS, 0, null);
			
		}catch(Exception ex){ 
			return ReturnResult.MESSAGE(MessageCode.STATUS_FAIL,
					MessageCode.SELECT_ORGAN_FAIL, 0, null); 
		} 
	}
	
	private int  getOrgLevel(String orgPath){

		String[] a=orgPath.split("/");
		if(a==null){
			return 0;
		}else{
			return a.length -1; 
		}
	} 
}
