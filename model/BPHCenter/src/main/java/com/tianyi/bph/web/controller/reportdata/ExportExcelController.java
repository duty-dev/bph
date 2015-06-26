package com.tianyi.bph.web.controller.reportdata;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianyi.bph.common.MessageCode;
import com.tianyi.bph.common.ReturnResult;
import com.tianyi.bph.domain.alarm.AlarmType;
import com.tianyi.bph.domain.report.CaseHourAGGR;
import com.tianyi.bph.domain.report.CaseOrgAGGR;
import com.tianyi.bph.domain.report.CasePeriodAGGR;
import com.tianyi.bph.domain.report.CaseReportResult;
import com.tianyi.bph.domain.report.CaseTypeAGGR;
import com.tianyi.bph.domain.report.ReportPeriod;
import com.tianyi.bph.domain.system.Organ;
import com.tianyi.bph.domain.system.User; 
import com.tianyi.bph.query.report.QueryCondition;
import com.tianyi.bph.service.alarm.AlarmDispatchService;
import com.tianyi.bph.service.report.CaseReportService;
import com.tianyi.bph.service.report.WarningCfgService;
import com.tianyi.bph.service.system.OrganService;

@Controller
@RequestMapping("/exportExcelWeb")
public class ExportExcelController {

	@Autowired
	private OrganService organService;
	@Autowired
	private CaseReportService caseReportService;
	@Autowired
	WarningCfgService warningService;
	@Autowired
	AlarmDispatchService alarmDispatchService;

	/**
	 * 警情分类数据导出Excel
	 * 
	 * @param police_Query
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/exportAlarmTypeDataToExcle.do")
	@ResponseBody
	public ReturnResult exportAlarmTypeDataToExcle(
			@RequestParam(value = "query", required = false) String query,
			@RequestParam(value = "data", required = false) String data,
			HttpServletRequest request) throws Exception {
		try {
			JSONObject jobj = JSONObject.fromObject(query);
			Map<String, Class<?>> classMap = new HashMap<String, Class<?>>();

			classMap.put("caseType", String.class);
			classMap.put("caseTimaSpan", Integer.class);
			classMap.put("caseLevels", Integer.class);
			QueryCondition queryCondition = (QueryCondition) JSONObject.toBean(
					jobj, QueryCondition.class, classMap);
 
			// 组织机构id
			int organId = queryCondition.getOrganId();
			User user = (User) request.getAttribute("User");
			if (organId == 0) {
				organId = user.getOrgId();
			} 
			List<String> caseTypes = queryCondition.getCaseType(); 
			data = data.substring(3,data.length());
			data = data.substring(0,data.length()-3);
			
			String serverPath = getClass().getResource("/").getFile()
					.toString();
			data =  data.replace("}\",\"{", "|");
			String[] a  =data.split("\\|");
			int s = a.length;
			serverPath = serverPath.substring(0, (serverPath.length() - 16));
			String filepath = "0";

			filepath = createAlarmTypeExcel(a, serverPath,organId,caseTypes);


			String retMsg = "";
			int retCode = 0;
			if (filepath.equals("1")) {
				retMsg = "文件创建出错";
				retCode = MessageCode.STATUS_FAIL;
			} else if (filepath.equals("2")) {
				retMsg = "创建Excel组件出错";
				retCode = MessageCode.STATUS_FAIL;
			} else if (filepath.equals("0")) {
				retMsg = "无查询结果数据，导出失败";
				retCode = MessageCode.STATUS_FAIL;
			} else {
				retMsg = filepath;
				retCode = MessageCode.STATUS_SUCESS;
			}
			return ReturnResult.MESSAGE(retCode, retMsg, 0, null);
		} catch (Exception ex) {
			return ReturnResult.MESSAGE(MessageCode.STATUS_FAIL, "获取警情分类统计导出数据出错",
					0, null);
		}
	}
 

	/**
	 * 机构统计数据导出Excel
	 * 
	 * @param police_Query
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/exportOrganDataToExcle.do")
	@ResponseBody
	public ReturnResult exportOrganDataToExcle(
			@RequestParam(value = "query", required = false) String query,
			@RequestParam(value = "data", required = false) String data,
			HttpServletRequest request) throws Exception {
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
			ls = getSelectCaseTypeOrgList(ls, type2Codes);
			cResult.setBeginYmd(rp.getBeginYmd());
			cResult.setEndYmd(rp.getEndYmd());
			cResult.setData(ls);
			results.add(cResult);
			String serverPath = getClass().getResource("/").getFile()
					.toString();
			serverPath = serverPath.substring(0, (serverPath.length() - 16));
			String filepath = "0";
			if (results.size() > 0) {
				filepath ="";// createOrganExcel(results, serverPath,organId);
			}

			String retMsg = "";
			int retCode = 0;
			if (filepath.equals("1")) {
				retMsg = "文件创建出错";
				retCode = MessageCode.STATUS_FAIL;
			} else if (filepath.equals("2")) {
				retMsg = "创建Excel组件出错";
				retCode = MessageCode.STATUS_FAIL;
			} else if (filepath.equals("0")) {
				retMsg = "无查询结果数据，导出失败";
				retCode = MessageCode.STATUS_FAIL;
			} else {
				retMsg = filepath;
				retCode = MessageCode.STATUS_SUCESS;
			}
			return ReturnResult.MESSAGE(retCode, retMsg, 0, null);
		} catch (Exception ex) {
			return ReturnResult.MESSAGE(MessageCode.STATUS_FAIL, "获取机构统计导出数据出错",
					0, null);
		}
	}

	private List<CaseOrgAGGR> getSelectCaseTypeOrgList(List<CaseOrgAGGR> ls,
			List<String> caseTypes) {
		// TODO Auto-generated method stub
		List<CaseOrgAGGR> list = new ArrayList<CaseOrgAGGR>();
		for (String code : caseTypes) {
			for (CaseOrgAGGR cta : ls) {
				if (cta.getTypeCode() == null) {
					list.add(cta);
				} else if (cta.getTypeCode().equals(code)) {
					list.add(cta);
				}
			}
		}
		return list;
	}

	/**
	 * 周期统计导出Excel
	 * 
	 * @param police_Query
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/exportPeriodDataToExcle.do")
	@ResponseBody
	public ReturnResult exportPeriodDataToExcle(
			@RequestParam(value = "query", required = false) String query,
			@RequestParam(value = "data", required = false) String data,
			HttpServletRequest request) throws Exception {
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
			String serverPath = getClass().getResource("/").getFile()
					.toString();
			serverPath = serverPath.substring(0, (serverPath.length() - 16));
			String filepath = "0";
			if (results.size() > 0) {
				filepath = createPeriodExcel(results, serverPath,organId);
			}

			String retMsg = "";
			int retCode = 0;
			if (filepath.equals("1")) {
				retMsg = "文件创建出错";
				retCode = MessageCode.STATUS_FAIL;
			} else if (filepath.equals("2")) {
				retMsg = "创建Excel组件出错";
				retCode = MessageCode.STATUS_FAIL;
			} else if (filepath.equals("0")) {
				retMsg = "无查询结果数据，导出失败";
				retCode = MessageCode.STATUS_FAIL;
			} else {
				retMsg = filepath;
				retCode = MessageCode.STATUS_SUCESS;
			}
			return ReturnResult.MESSAGE(retCode, retMsg, 0, null);
		} catch (Exception ex) {
			return ReturnResult.MESSAGE(MessageCode.STATUS_FAIL, "获取周期统计导出数据出错",
					0, null);
		}
	}

	/**
	 * 时间段统计数据导出Excel
	 * 
	 * @param police_Query
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/exportTiemSpanDataToExcle.do")
	@ResponseBody
	public ReturnResult exportTiemSpanDataToExcle(
			@RequestParam(value = "query", required = false) String query,
			@RequestParam(value = "data", required = false) String data,
			HttpServletRequest request) throws Exception {
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
			String serverPath = getClass().getResource("/").getFile()
					.toString();
			serverPath = serverPath.substring(0, (serverPath.length() - 16));
			String filepath = "0";
			if (results.size() > 0) {
				filepath = createTimeSpanExcel(results, serverPath,organId);
			}

			String retMsg = "";
			int retCode = 0;
			if (filepath.equals("1")) {
				retMsg = "文件创建出错";
				retCode = MessageCode.STATUS_FAIL;
			} else if (filepath.equals("2")) {
				retMsg = "创建Excel组件出错";
				retCode = MessageCode.STATUS_FAIL;
			} else if (filepath.equals("0")) {
				retMsg = "无查询结果数据，导出失败";
				retCode = MessageCode.STATUS_FAIL;
			} else {
				retMsg = filepath;
				retCode = MessageCode.STATUS_SUCESS;
			}
			return ReturnResult.MESSAGE(retCode, retMsg, 0, null);
		} catch (Exception ex) {
			return ReturnResult.MESSAGE(MessageCode.STATUS_FAIL, "获取时间段统计导出数据出错",
					0, null);
		}
	}

	/**
	 * 创建警情分类Excel到临时文件夹
	 * @param results
	 * @param serverPath
	 * @return
	 */
	private String createAlarmTypeExcel(
			String[] results, String serverPath,Integer organId,List<String> caseTypes) {
		String filePath = "";
		String orgName = getOrganName(organId);
		List<String> caseTypeName = getCaseTypeName(caseTypes); 
		if (results.length > 0) {
			Workbook workbook = null;
			try {
				workbook = new HSSFWorkbook();
				if (workbook != null) {
					Sheet sheet = workbook.createSheet("警情分类统计数据");
					Row row0 = sheet.createRow(0);
					String title = "警情分类统计数据"; 
					title = orgName + title;

					Cell cell_0 = row0.createCell(0, Cell.CELL_TYPE_STRING);
					cell_0.setCellValue(title);
					int clomnSize = caseTypeName.size();
					sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, clomnSize-1)); 
					Row row1 = sheet.createRow(1);
					Cell cell1 = row1.createCell(0, Cell.CELL_TYPE_STRING);
					cell1.setCellValue("统计时间/警情分类");
					sheet.autoSizeColumn(0);
					for(int j = 1;j<= clomnSize;j++){ 
						Cell cell = row1.createCell(j, Cell.CELL_TYPE_STRING);
						cell.setCellValue(caseTypeName.get(j-1));
						sheet.autoSizeColumn(j);
					}
					
					for (int rowNum = 2; rowNum <= 133 + 1; rowNum++) {
						Row row = sheet.createRow(rowNum); 
					}
				}
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
				java.util.Date date = new java.util.Date();
				String str = sdf.format(date);
				filePath = "excelModel/tempfile/" + str + "_AlarmTypeData.xls";
				String realPath = serverPath + filePath;
				try {
					FileOutputStream outputStream = new FileOutputStream(
							realPath);
					workbook.write(outputStream);
					outputStream.flush();
					outputStream.close();
				} catch (Exception e) {
					return "1";
				}
			} catch (Exception ex) {
				return "2";
			}
		}
		return filePath;
	}

	/**
	 * 创建组织机构Excel文件到临时文件夹
	 * @param results
	 * @param serverPath
	 * @return
	 */
	private String createOrganExcel(
			List<String> results, String serverPath,Integer organId) {
		// TODO Auto-generated method stub
		String filePath = "";
		String orgName = getOrganName(organId); 
		if (results.size() > 0) {
			Workbook workbook = null;
			try {
				workbook = new HSSFWorkbook();// HSSFWorkbook();//WorkbookFactory.create(inputStream);
				if (workbook != null) {
					Sheet sheet = workbook.createSheet("警情分类统计数据");
					Row row0 = sheet.createRow(0);
					String title = "机构统计数据"; 
					title = orgName + title;

					Cell cell_0 = row0.createCell(0, Cell.CELL_TYPE_STRING);
					cell_0.setCellValue(title); 
					sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5)); 
					Row row1 = sheet.createRow(1);
					Cell cell1 = row1.createCell(0, Cell.CELL_TYPE_STRING);
					cell1.setCellValue("统计时间/警情分类");
					sheet.autoSizeColumn(0);
					 
					
//					for (int rowNum = 2; rowNum <= results.size() + 1; rowNum++) {
//						Row row = sheet.createRow(rowNum); 
//					}
				}
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
				java.util.Date date = new java.util.Date();
				String str = sdf.format(date);
				filePath = "excelModel/tempfile/" + str + "_OrganData.xls";
				String realPath = serverPath + filePath;
				try {
					FileOutputStream outputStream = new FileOutputStream(
							realPath);
					workbook.write(outputStream);
					outputStream.flush();
					outputStream.close();
				} catch (Exception e) {
					return "1";
				}
			} catch (Exception ex) {
				return "2";
			}
		}
		return filePath;
	}
	/**
	 * 创建周期Excel文件到临时文件夹
	 * @param results
	 * @param serverPath
	 * @return
	 */
	private String createPeriodExcel(
			List<CaseReportResult<CasePeriodAGGR>> results, String serverPath,Integer organId) {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * 创建时间段Excel文件到临时文件夹
	 * @param results
	 * @param serverPath
	 * @return
	 */
	private String createTimeSpanExcel(
			List<CaseReportResult<CaseHourAGGR>> results, String serverPath,Integer organId) {
		// TODO Auto-generated method stub
		return null;
	}

	private String getOrganName(Integer organId){
		Organ organ = organService.getOrganByPrimaryKey(organId);
		if(organ!=null){
			return organ.getShortName();
		}else{ 
			return "";
		}
	}	
	private List<String> getCaseTypeName(List<String> caseTypes) {
		// TODO Auto-generated method stub

		List<AlarmType> list = new ArrayList<AlarmType>();
		list = alarmDispatchService.getAlarmTypeList(null);
		List<String> ls = new ArrayList<String>();
		for(String s : caseTypes){
			for(AlarmType ap : list){
				if(s.equals(ap.getTypeCode())){
					ls.add(ap.getTypeName());
				}
			}
		}
		return ls;
	}

}
