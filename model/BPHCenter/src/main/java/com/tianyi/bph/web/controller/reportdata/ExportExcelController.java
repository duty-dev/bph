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
 
			// 组织机构id
			int organId = queryCondition.getOrganId();
			User user = (User) request.getAttribute("User");
			if (organId == 0) {
				organId = user.getOrgId();
			}
  
			// 警情类型 父级节点
			List<String> type2Codes = queryCondition.getCaseType();
			data = data.substring(3,data.length());
			data = data.substring(0,data.length()-3);
			
			String serverPath = getClass().getResource("/").getFile()
					.toString();
			data =  data.replace("}\",\"{", "|");
			String[] a  =data.split("\\|");
			serverPath = serverPath.substring(0, (serverPath.length() - 16));
			String filepath = "0"; 
				filepath = createOrganExcel(a, serverPath,organId,type2Codes);
			 
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

			// 组织机构id
			int organId = queryCondition.getOrganId();
			User user = (User) request.getAttribute("User");
			if (organId == 0) {
				organId = user.getOrgId();
			} 
			
			data = data.substring(3,data.length());
			data = data.substring(0,data.length()-3);
						
			String serverPath = getClass().getResource("/").getFile()
								.toString();
			data =  data.replace("}\",\"{", "|");
			String[] a  =data.split("\\|");
			serverPath = serverPath.substring(0, (serverPath.length() - 16));
			String filepath = "0";

			filepath = createPeriodExcel(a, serverPath,organId);

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

			// 组织机构id
			int organId = queryCondition.getOrganId();
			User user = (User) request.getAttribute("User");
			if (organId == 0) {
				organId = user.getOrgId();
			}
			data = data.substring(3,data.length());
			data = data.substring(0,data.length()-3);
									
			String serverPath = getClass().getResource("/").getFile()
											.toString();
			data =  data.replace("}\",\"{", "|");
			String[] a  =data.split("\\|");
			serverPath = serverPath.substring(0, (serverPath.length() - 16));
			String filepath = "0";

			filepath = createTimeSpanExcel(a,serverPath,organId);


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
					Sheet sheet = workbook.createSheet("警情统计_警情分类统计数据");
					Row row0 = sheet.createRow(0);
					String title = "警情统计_警情分类统计数据"; 
					title = orgName + title;

					Cell cell_0 = row0.createCell(0, Cell.CELL_TYPE_STRING);
					cell_0.setCellValue(title);
					int clomnSize = caseTypeName.size();
					sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, clomnSize+1)); 
					//创建表头
					Row row1 = sheet.createRow(1);
					Cell cell1 = row1.createCell(0, Cell.CELL_TYPE_STRING);
					cell1.setCellValue("统计时间/警情分类");
					sheet.autoSizeColumn(0);
					for(int j = 1;j<= clomnSize;j++){ 
						Cell cell = row1.createCell(j, Cell.CELL_TYPE_STRING);
						cell.setCellValue(caseTypeName.get(j-1));
						sheet.autoSizeColumn(j);
					}
					
					Cell celll = row1.createCell(clomnSize+1, Cell.CELL_TYPE_STRING);
					celll.setCellValue("合计");
					sheet.autoSizeColumn(clomnSize+1);
					
					//从格式化的数据中获取想要的数据
					String[][] rowData = GetRowsData(results,clomnSize+2);
					//将除最后一行的行的最后一个元素放到该行的最前面
					for(int i = 0;i<rowData.length-1;i++){
						String temp = rowData[i][rowData[i].length-1];
						for(int j = rowData[i].length-2;j>=0;j--){
							rowData[i][j+1] =rowData[i][j];
						}
						rowData[i][0] = temp;
					}
					//添加行数据
					for (int rowNum = 2; rowNum <= rowData.length+1; rowNum++) {
						Row row = sheet.createRow(rowNum); 
						for(int cellCount = 0;cellCount<rowData[rowNum-2].length;cellCount++){
							Cell cel = row.createCell(cellCount, Cell.CELL_TYPE_STRING);
							cel.setCellValue(rowData[rowNum-2][cellCount].replace("\\\"", ""));
							sheet.autoSizeColumn(cellCount);
						}
					}
				}
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
				java.util.Date date = new java.util.Date();
				String str = sdf.format(date);
				filePath = "excelModel/tempfile/" + str + "_AlarmTypeData.xls";
				String realPath ="C:\\Users\\hzf\\Workspaces\\"+ str + "_AlarmTypeData.xls";// serverPath + filePath;
				//String realPath = serverPath+filePath;
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

	private String[][] GetRowsData(String[] results,Integer countOfColumns) {
		String[][] rows =null;
		if(results.length>0){
			rows =new String[results.length][countOfColumns];
			for(int i= 0;i< results.length;i++){
				//一行字符串处理
				rows[i] = GetRowData(results[i]);
			}
		}
		return rows;
	}


	private String[] GetRowData(String result) {
		String[] row = result.split("\\,");
		String[] score = new String[row.length];
		for(int i = 0;i<row.length;i++){
			String[] s = row[i].split("\\:");
			score[i] = s[1];
		}
		return score;
	}


	/**
	 * 创建组织机构Excel文件到临时文件夹
	 * @param results
	 * @param serverPath
	 * @return
	 */
	private String createOrganExcel(
			String[] results, String serverPath,Integer organId,List<String> caseTypes) {
		String filePath = "";
		String orgName = getOrganName(organId);
		List<String> caseTypeName = getCaseTypeName(caseTypes); 
		if (results.length > 0) {
			Workbook workbook = null;
			try {
				workbook = new HSSFWorkbook();
				if (workbook != null) {
					Sheet sheet = workbook.createSheet("警情统计_机构统计数据");
					Row row0 = sheet.createRow(0);
					String title = "警情统计_机构统计数据"; 
					title = orgName + title;

					Cell cell_0 = row0.createCell(0, Cell.CELL_TYPE_STRING);
					cell_0.setCellValue(title);
					int clomnSize = caseTypeName.size();
					sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, clomnSize+1)); 
					//创建表头
					Row row1 = sheet.createRow(1);
					Cell cell1 = row1.createCell(0, Cell.CELL_TYPE_STRING);
					cell1.setCellValue("警情分类");
					sheet.autoSizeColumn(0);
					for(int j = 1;j<= clomnSize;j++){ 
						Cell cell = row1.createCell(j, Cell.CELL_TYPE_STRING);
						cell.setCellValue(caseTypeName.get(j-1));
						sheet.autoSizeColumn(j);
					}
					
					Cell celll = row1.createCell(clomnSize+1, Cell.CELL_TYPE_STRING);
					celll.setCellValue("合计");
					sheet.autoSizeColumn(clomnSize+1);
					
					//从格式化的数据中获取想要的数据
					String[][] rowData = GetRowsData(results,clomnSize+2);
					//添加行数据
					for (int rowNum = 2; rowNum <= rowData.length+1; rowNum++) {
						Row row = sheet.createRow(rowNum); 
						for(int cellCount = 0;cellCount<rowData[rowNum-2].length;cellCount++){
							Cell cel = row.createCell(cellCount, Cell.CELL_TYPE_STRING);
							cel.setCellValue(rowData[rowNum-2][cellCount]=="null"?"0":rowData[rowNum-2][cellCount]==""?"0":rowData[rowNum-2][cellCount].replace("\\\"", ""));
							sheet.autoSizeColumn(cellCount);
						}
					}
				}
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
				java.util.Date date = new java.util.Date();
				String str = sdf.format(date);
				filePath = "excelModel/tempfile/" + str + "_OrganData.xls";
				String realPath ="C:\\Users\\hzf\\Workspaces\\"+ str + "_OrganData.xls";// serverPath + filePath;
				//String realPath = serverPath + filePath;
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
			String[] results, String serverPath,Integer organId) {
		// TODO Auto-generated method stub
		String filePath = "";
		String orgName = getOrganName(organId);
		if (results.length > 0) {
			Workbook workbook = null;
			try {
				workbook = new HSSFWorkbook();
				if (workbook != null) {
					Sheet sheet = workbook.createSheet("警情统计_周期统计数据");
					Row row0 = sheet.createRow(0);
					String title = "警情统计_周期统计数据"; 
					title = orgName + title;

					Cell cell_0 = row0.createCell(0, Cell.CELL_TYPE_STRING);
					cell_0.setCellValue(title);
					sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 25)); 

					String[][] rowData = GetRowsData(results,25);
					//添加行数据
					for (int rowNum = 1; rowNum <= rowData.length; rowNum++) {
						Row row = sheet.createRow(rowNum); 
						for(int cellCount = 0;cellCount<rowData[rowNum-1].length;cellCount++){
							Cell cel = row.createCell(cellCount, Cell.CELL_TYPE_STRING);
							cel.setCellValue(rowData[rowNum-1][cellCount].replace("\\\"", ""));
							sheet.autoSizeColumn(cellCount);
						}
					}
				}
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
				java.util.Date date = new java.util.Date();
				String str = sdf.format(date);
				filePath = "excelModel/tempfile/" + str + "_PeriodData.xls";
				//String realPath ="C:\\Users\\hzf\\Workspaces\\"+ str + "_Period.xls";// serverPath + filePath;
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
	 * 创建时间段Excel文件到临时文件夹
	 * @param results
	 * @param serverPath
	 * @return
	 */
	private String createTimeSpanExcel(
			String[] results, String serverPath,Integer organId) {
		// TODO Auto-generated method stub
		String filePath = "";
		String orgName = getOrganName(organId);
		if (results.length > 0) {
			Workbook workbook = null;
			try {
				workbook = new HSSFWorkbook();
				if (workbook != null) {
					Sheet sheet = workbook.createSheet("警情统计_时间段统计数据");
					Row row0 = sheet.createRow(0);
					String title = "警情统计_时间段统计数据"; 
					title = orgName + title;

					Cell cell_0 = row0.createCell(0, Cell.CELL_TYPE_STRING);
					cell_0.setCellValue(title);
					sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 24)); 
					//创建表头
					Row row1 = sheet.createRow(1);
					Cell cell1 = row1.createCell(0, Cell.CELL_TYPE_STRING);
					cell1.setCellValue("周期/时间段");
					sheet.autoSizeColumn(0);
					for(int j = 1;j<= 24;j++){ 
						Cell cell = row1.createCell(j, Cell.CELL_TYPE_STRING);
						cell.setCellValue(j);
						sheet.autoSizeColumn(j);
					}
					
					//从格式化的数据中获取想要的数据
					String[][] rowData = GetRowsData(results,25);
					//添加行数据
					for (int rowNum = 2; rowNum <= rowData.length+1; rowNum++) {
						Row row = sheet.createRow(rowNum); 
						for(int cellCount = 0;cellCount<rowData[rowNum-2].length;cellCount++){
							Cell cel = row.createCell(cellCount, Cell.CELL_TYPE_STRING);
							cel.setCellValue(rowData[rowNum-2][cellCount].replace("\\\"", ""));
							sheet.autoSizeColumn(cellCount);
						}
					}
				}
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
				java.util.Date date = new java.util.Date();
				String str = sdf.format(date);
				filePath = "excelModel/tempfile/" + str + "_TimeSpanData.xls";
				//String realPath ="C:\\Users\\hzf\\Workspaces\\"+ str + "_TimeSpan.xls";// serverPath + filePath;
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
