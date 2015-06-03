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
import com.tianyi.bph.domain.system.Organ;
import com.tianyi.bph.domain.system.User;
import com.tianyi.bph.service.report.CaseReportService;
import com.tianyi.bph.service.system.OrganService;

@Controller
@RequestMapping("/caseReportWeb")
public class CaseReportController {

	@Autowired
	private OrganService organService;
	@Autowired
	private CaseReportService caseReportService;
	
	@RequestMapping(value = "/loadCaseTypeReport.do")
	public @ResponseBody ReturnResult loadCaseTypeReport(
			@RequestParam(value = "query", required = false) String query,
			HttpServletRequest request) {
		try {
			JSONObject joQuery = JSONObject.fromObject(query); 
			Map<String,Object> map=new HashMap<String,Object>();
			List<CaseReportResult<CaseTypeAGGR>>  results=new ArrayList<CaseReportResult<CaseTypeAGGR>>();
			List<CaseTypeAGGR>  ls=null;
			
			
			//组织机构id
			int organId = Integer.parseInt(joQuery.getString("orgId"));
			User user = (User) request.getAttribute("User");
			if (organId == 0) {
				organId = user.getOrgId();
			}
			Organ organ = organService.getOrganByPrimaryKey(organId);
			map.put("orgFullPath", organ.getPath());
			  
			//查询时间
			
			//查询时间方式，1、月  2、日
			
			//警情级别
			
			//警情类型    父级节点
			
			//警情级别    二级节点
			
			//时间区间    时间节点
			
			
			
			
			Integer bd=20140701;
			Integer ed=20140731;
			
			ReportPeriod  rp=new ReportPeriod(bd,ed,1);
			
			
			List<Integer>  levels=new ArrayList<Integer>();
			levels.add(0);
			levels.add(1);
			levels.add(2);
			map.put("levels",levels );
			map.put("beginHour", 0);
			map.put("endHour", 23);
			
			//当期
			CaseReportResult<CaseTypeAGGR> cResult=new CaseReportResult<CaseTypeAGGR>();
			map.put("beginYMD", rp.getBeginYmd());
			map.put("endYMD", rp.getEndYmd());
			ls=caseReportService.loadCaseTypeReport(map);
			cResult.setBeginYmd(rp.getBeginYmd());
			cResult.setEndYmd(rp.getEndYmd());
			cResult.setData(ls);
			results.add(cResult);
			
			//同比
			CaseReportResult<CaseTypeAGGR> yResult=new CaseReportResult<CaseTypeAGGR>();
			map.put("beginYMD", rp.getYOYBeginYmd());
			map.put("endYMD", rp.getYOYEndYmd());
			ls=caseReportService.loadCaseTypeReport(map);
			yResult.setBeginYmd(rp.getYOYBeginYmd());
			yResult.setEndYmd(rp.getYOYEndYmd());
			yResult.setData(ls);
			results.add(yResult);;
			
			//环比
			CaseReportResult<CaseTypeAGGR> mResult=new CaseReportResult<CaseTypeAGGR>();
			map.put("beginYMD", rp.getMOMBeginYmd());
			map.put("endYMD", rp.getMOMEndYmd());
			ls=caseReportService.loadCaseTypeReport(map);
			mResult.setBeginYmd(rp.getMOMBeginYmd());
			mResult.setEndYmd(rp.getMOMEndYmd());
			mResult.setData(ls);
			results.add(mResult);;
			
			return ReturnResult.MESSAGE(MessageCode.STATUS_SUCESS,
					MessageCode.SELECT_SUCCESS, 0, results);

		} catch (Exception ex) {
			return ReturnResult.MESSAGE(MessageCode.STATUS_FAIL,
					MessageCode.SELECT_ORGAN_FAIL, 0, null);
		}
		
	}
	@RequestMapping(value = "/loadCasePeriodReport.do")
	public @ResponseBody ReturnResult loadCasePeriodReport(
			@RequestParam(value = "query", required = false) String query,
			HttpServletRequest request) {
		try {
			Map<String,Object> map=new HashMap<String,Object>();
			List<CaseReportResult<CasePeriodAGGR>>  results=new ArrayList<CaseReportResult<CasePeriodAGGR>>();
			List<CasePeriodAGGR>  ls=null;
			
			Integer bd=20140101;
			Integer ed=20141231;
			
			ReportPeriod  rp=new ReportPeriod(bd,ed,1);
			
			map.put("orgFullPath", "/510000000000");
			
			List<Integer>  levels=new ArrayList<Integer>();
			levels.add(0);
			levels.add(1);
			levels.add(2);
			
			List<String>  type2Codes=new ArrayList<String>();
			type2Codes.add("caseType110011000");
			
			map.put("periodType", 1);
			
			map.put("type2Codes",type2Codes);
			map.put("levels",levels );
			map.put("beginHour", 0);
			map.put("endHour", 23);
			
			//当期
			CaseReportResult<CasePeriodAGGR> cResult=new CaseReportResult<CasePeriodAGGR>();
			map.put("beginYMD", rp.getBeginYmd());
			map.put("endYMD", rp.getEndYmd());
			ls=caseReportService.loadCasePeriodReport(map);
			cResult.setBeginYmd(rp.getBeginYmd());
			cResult.setEndYmd(rp.getEndYmd());
			cResult.setData(ls);
			results.add(cResult);
			
			//同比
			CaseReportResult<CasePeriodAGGR> yResult=new CaseReportResult<CasePeriodAGGR>();
			map.put("beginYMD", rp.getYOYBeginYmd());
			map.put("endYMD", rp.getYOYEndYmd());
			ls=caseReportService.loadCasePeriodReport(map);
			yResult.setBeginYmd(rp.getYOYBeginYmd());
			yResult.setEndYmd(rp.getYOYEndYmd());
			yResult.setData(ls);
			results.add(yResult);;
			
			//环比
			CaseReportResult<CasePeriodAGGR> mResult=new CaseReportResult<CasePeriodAGGR>();
			map.put("beginYMD", rp.getMOMBeginYmd());
			map.put("endYMD", rp.getMOMEndYmd());
			ls=caseReportService.loadCasePeriodReport(map);
			mResult.setBeginYmd(rp.getMOMBeginYmd());
			mResult.setEndYmd(rp.getMOMEndYmd());
			mResult.setData(ls);
			results.add(mResult);;
			
			return ReturnResult.MESSAGE(MessageCode.STATUS_SUCESS,
					MessageCode.SELECT_SUCCESS, 0, results);

		} catch (Exception ex) {
			return ReturnResult.MESSAGE(MessageCode.STATUS_FAIL,
					MessageCode.SELECT_ORGAN_FAIL, 0, null);
		}
	}
	
	@RequestMapping(value = "/loadCaseHourReport.do")
	public @ResponseBody ReturnResult loadCaseHourReport(
			@RequestParam(value = "query", required = false) String query,
			HttpServletRequest request) {
		try {
			Map<String,Object> map=new HashMap<String,Object>();
			List<CaseReportResult<CaseHourAGGR>>  results=new ArrayList<CaseReportResult<CaseHourAGGR>>();
			List<CaseHourAGGR>  ls=null;
			
			Integer bd=20140101;
			Integer ed=20141231;
			
			ReportPeriod  rp=new ReportPeriod(bd,ed,1);
			
			map.put("orgFullPath", "/510000000000");
			
			List<Integer>  levels=new ArrayList<Integer>();
			levels.add(0);
			levels.add(1);
			levels.add(2);
			
			List<String>  type2Codes=new ArrayList<String>();
			type2Codes.add("caseType110011000");
			map.put("type2Codes",type2Codes);
			
			map.put("periodType", 1);
			map.put("levels",levels );
			map.put("beginHour", 0);
			map.put("endHour", 23);
			
			//当期
			CaseReportResult<CaseHourAGGR> cResult=new CaseReportResult<CaseHourAGGR>();
			map.put("beginYMD", rp.getBeginYmd());
			map.put("endYMD", rp.getEndYmd());
			ls=caseReportService.loadCaseHourReport(map);
			cResult.setBeginYmd(rp.getBeginYmd());
			cResult.setEndYmd(rp.getEndYmd());
			cResult.setData(ls);
			results.add(cResult);
			
			//同比
			CaseReportResult<CaseHourAGGR> yResult=new CaseReportResult<CaseHourAGGR>();
			map.put("beginYMD", rp.getYOYBeginYmd());
			map.put("endYMD", rp.getYOYEndYmd());
			ls=caseReportService.loadCaseHourReport(map);
			yResult.setBeginYmd(rp.getYOYBeginYmd());
			yResult.setEndYmd(rp.getYOYEndYmd());
			yResult.setData(ls);
			results.add(yResult);;
			
			//环比
			CaseReportResult<CaseHourAGGR> mResult=new CaseReportResult<CaseHourAGGR>();
			map.put("beginYMD", rp.getMOMBeginYmd());
			map.put("endYMD", rp.getMOMEndYmd());
			ls=caseReportService.loadCaseHourReport(map);
			mResult.setBeginYmd(rp.getMOMBeginYmd());
			mResult.setEndYmd(rp.getMOMEndYmd());
			mResult.setData(ls);
			results.add(mResult);;
			
			return ReturnResult.MESSAGE(MessageCode.STATUS_SUCESS,
					MessageCode.SELECT_SUCCESS, 0, results);

		} catch (Exception ex) {
			return ReturnResult.MESSAGE(MessageCode.STATUS_FAIL,
					MessageCode.SELECT_ORGAN_FAIL, 0, null);
		}
			
		
	}
	
	@RequestMapping(value = "/loadCaseOrgReport.do")
	public @ResponseBody ReturnResult loadCaseOrgReport(
			@RequestParam(value = "query", required = false) String query,
			HttpServletRequest request) {
		try {
			Map<String,Object> map=new HashMap<String,Object>();
			List<CaseReportResult<CaseOrgAGGR>>  results=new ArrayList<CaseReportResult<CaseOrgAGGR>>();
			List<CaseOrgAGGR>  ls=null;
			
			Integer bd=20140101;
			Integer ed=20141231;
			
			ReportPeriod  rp=new ReportPeriod(bd,ed,1);
			
			map.put("orgFullPath", "/510000000000");
			
			List<Integer>  levels=new ArrayList<Integer>();
			levels.add(0);
			levels.add(1);
			levels.add(2);
			
			List<String>  type2Codes=new ArrayList<String>();
			type2Codes.add("caseType110011000");
			map.put("type2Codes",type2Codes);
			
			map.put("periodType", 1);
			map.put("levels",levels );
			map.put("beginHour", 0);
			map.put("endHour", 23);
			
			//当期
			CaseReportResult<CaseOrgAGGR> cResult=new CaseReportResult<CaseOrgAGGR>();
			map.put("beginYMD", rp.getBeginYmd());
			map.put("endYMD", rp.getEndYmd());
			ls=caseReportService.loadCaseOrgReport(map);
			cResult.setBeginYmd(rp.getBeginYmd());
			cResult.setEndYmd(rp.getEndYmd());
			cResult.setData(ls);
			results.add(cResult);
			
			return ReturnResult.MESSAGE(MessageCode.STATUS_SUCESS,
					MessageCode.SELECT_SUCCESS, 0, results);

		} catch (Exception ex) {
			return ReturnResult.MESSAGE(MessageCode.STATUS_FAIL,
					MessageCode.SELECT_ORGAN_FAIL, 0, null);
		}	
	}
	
	@RequestMapping(value = "/loadWarningReport.do")
	public @ResponseBody ReturnResult loadWarningReport(
			@RequestParam(value = "query", required = false) String query,
			HttpServletRequest request) { 
		
		try{
			Map<String,Object> map= new HashMap<String,Object>();
			ReportPeriod rp=new ReportPeriod(20150101,20150430,1);
			
			
			map.put("orgId", 72);
			map.put("orgPath", "/510000000000/510100000000");
			List<Integer> caseLevels=new ArrayList<Integer>();
			caseLevels.add(1);
			caseLevels.add(2);
			caseLevels.add(3);
			
			List<String> caseTypes=new ArrayList<String>();
			caseTypes.add("caseType110010901");
			caseTypes.add("caseType110011600");
			map.put("type2Codes", caseTypes);
			
			map.put("beginYmd", rp.getBeginYmd());
			map.put("endYmd", rp.getEndYmd());
			
			
			
		}catch(Exception ex){
			
		}
		
		
		
		
		return null;
	}
}
