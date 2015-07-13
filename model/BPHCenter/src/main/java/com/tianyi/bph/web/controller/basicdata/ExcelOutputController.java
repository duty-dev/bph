package com.tianyi.bph.web.controller.basicdata;
 
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
import com.tianyi.bph.query.basicdata.GpsVM;
import com.tianyi.bph.query.basicdata.PoliceVM;
import com.tianyi.bph.query.basicdata.VehicleVM;
import com.tianyi.bph.query.basicdata.WeaponVM;
import com.tianyi.bph.service.basicdata.GpsService;
import com.tianyi.bph.service.basicdata.PoliceService;
import com.tianyi.bph.service.basicdata.VehicleService;  
import com.tianyi.bph.service.basicdata.WeaponService; 
 
@Controller
@RequestMapping("/excelOutWeb")
public class ExcelOutputController {

	@Autowired PoliceService policeService;


	@Autowired VehicleService vehicleService;

	@Autowired GpsService gpsService;
	
	@Autowired WeaponService weaponService;
	/*
	 * 获取警员列表信息
	 * 
	 * police_Query：查询条件包 sort：排序列 order：排序方式 page：当前页 rows：每页条数
	 */
	@RequestMapping(value = "/exportPoliceDataToExcle.do")
	@ResponseBody
	public ReturnResult exportPoliceDataToExcle(
			@RequestParam(value = "police_Query", required = false) String police_Query,
			HttpServletRequest request) throws Exception {
		try {
			JSONObject joQuery = JSONObject.fromObject(police_Query);
			int orgId = Integer.parseInt(joQuery.getString("orgId"));
			int isSubOrg = Integer.parseInt(joQuery.getString("isSubOrg"));
			String name = joQuery.getString("name");
			String orgPath = joQuery.getString("orgPath");
			Map<String, Object> map = new HashMap<String, Object>();

			map.put("isSubOrg", isSubOrg);
			map.put("name", name);
			map.put("orgId", orgId);
			map.put("orgPath", orgPath);

			List<PoliceVM> list = new ArrayList<PoliceVM>();
			list = policeService.loadVMList(map); 
			String serverPath = getClass().getResource("/").getFile()
					.toString();
			serverPath = serverPath.substring(0, (serverPath.length() - 16));
			String filepath ="0";
			if(list.size()>0){
				filepath = createPoliceExcel(list, serverPath);
			}

			String retMsg = "";
			int retCode = 0;
			if (filepath.equals("1")) {
				retMsg = "文件创建出错";
				retCode = MessageCode.STATUS_FAIL;
			} else if (filepath.equals("2")){
				retMsg = "创建Excel组件出错";
				retCode = MessageCode.STATUS_FAIL;
			}else if(filepath.equals("0")){
				retMsg = "无查询结果数据，导出失败";
				retCode = MessageCode.STATUS_FAIL;
			}else{
				retMsg = filepath;
				retCode = MessageCode.STATUS_SUCESS;
			}
			return ReturnResult.MESSAGE(retCode, retMsg, 0, null);
		} catch (Exception ex) {
			return ReturnResult.MESSAGE(MessageCode.STATUS_FAIL, "获取警员导出数据出错", 0,
					null);
		}
	}

	
	@RequestMapping(value = "/exportVehicleDataToExcle.do")
	@ResponseBody
	public ReturnResult exportVehicleDataToExcle(
			@RequestParam(value = "vehicle_Query", required = false) String vehicle_Query,
			HttpServletRequest request) throws Exception {
		try {
			JSONObject joQuery = JSONObject.fromObject(vehicle_Query);
			int orgId = Integer.parseInt(joQuery.getString("orgId"));
			int isSubOrg = Integer.parseInt(joQuery.getString("isSubOrg"));
			String number = joQuery.getString("number");
			String orgPath = joQuery.getString("orgPath"); 
			Map<String, Object> map = new HashMap<String, Object>();

			map.put("isSubOrg", isSubOrg);
			map.put("number", number);
			map.put("sort", "v.id");
			map.put("order", "desc");
			map.put("orgId", orgId);
			map.put("orgPath", orgPath); 

			List<VehicleVM> list = new ArrayList<VehicleVM>(); 
			list = vehicleService.loadVMList(map);
			String serverPath = getClass().getResource("/").getFile()
					.toString();
			serverPath = serverPath.substring(0, (serverPath.length() - 16)); 
			String filepath ="0";
			if(list.size()>0){
				filepath = createVehicleExcel(list, serverPath);
			}

			String retMsg = "";
			int retCode = 0;
			if (filepath.equals("1")) {
				retMsg = "文件创建出错";
				retCode = MessageCode.STATUS_FAIL;
			} else if (filepath.equals("2")){
				retMsg = "创建Excel组件出错";
				retCode = MessageCode.STATUS_FAIL;
			}else if(filepath.equals("0")){
				retMsg = "无查询结果数据，导出失败";
				retCode = MessageCode.STATUS_FAIL;
			}else{
				retMsg = filepath;
				retCode = MessageCode.STATUS_SUCESS;
			}
			return ReturnResult.MESSAGE(retCode, retMsg, 0, null);
		} catch (Exception ex) {
			return ReturnResult.MESSAGE(MessageCode.STATUS_FAIL, "获取车辆导出数据出错", 0,
					null);
		}
	}

	
	@RequestMapping(value = "/exportGpsDataToExcle.do")
	@ResponseBody
	public ReturnResult exportGpsDataToExcle(
			@RequestParam(value = "gps_Query", required = false) String gps_Query,
			HttpServletRequest request) throws Exception {
		try {
			JSONObject joQuery = JSONObject.fromObject(gps_Query);
			int orgId = Integer.parseInt(joQuery.getString("orgId"));
			int isSubOrg = Integer.parseInt(joQuery.getString("isSubOrg"));
			String name = joQuery.getString("name");
			String number = joQuery.getString("number");
			String orgPath = joQuery.getString("orgPath");
			Map<String, Object> map = new HashMap<String, Object>();

			map.put("isSubOrg", isSubOrg);
			map.put("number", number);
			map.put("name", name);
			map.put("orgId", orgId);
			map.put("orgPath", orgPath);

			List<GpsVM> list = new ArrayList<GpsVM>(); 
			list = gpsService.loadVMList(map);
			String serverPath = getClass().getResource("/").getFile()
					.toString();
			serverPath = serverPath.substring(0, (serverPath.length() - 16)); 
			String filepath ="0";
			if(list.size()>0){
				filepath = createGpsExcel(list, serverPath);
			}

			String retMsg = "";
			int retCode = 0;
			if (filepath.equals("1")) {
				retMsg = "文件创建出错";
				retCode = MessageCode.STATUS_FAIL;
			} else if (filepath.equals("2")){
				retMsg = "创建Excel组件出错";
				retCode = MessageCode.STATUS_FAIL;
			}else if(filepath.equals("0")){
				retMsg = "无查询结果数据，导出失败";
				retCode = MessageCode.STATUS_FAIL;
			}else{
				retMsg = filepath;
				retCode = MessageCode.STATUS_SUCESS;
			}
			return ReturnResult.MESSAGE(retCode, retMsg, 0, null);
		} catch (Exception ex) {
			return ReturnResult.MESSAGE(MessageCode.STATUS_FAIL, "获取警员数据出错", 0,
					null);
		}
	}
	

	@RequestMapping(value = "/exportWeaponDataToExcle.do")
	@ResponseBody
	public ReturnResult exportWeaponDataToExcle(
			@RequestParam(value = "weapon_Query", required = false) String weapon_Query,
			HttpServletRequest request) throws Exception {
		try {
			JSONObject joQuery = JSONObject.fromObject(weapon_Query);
			int orgId = Integer.parseInt(joQuery.getString("orgId"));
			int isSubOrg = Integer.parseInt(joQuery.getString("isSubOrg"));
			String number = joQuery.getString("number");
			String orgPath = joQuery.getString("orgPath");
			Map<String, Object> map = new HashMap<String, Object>();

			map.put("isSubOrg", isSubOrg);
			map.put("number", number); 
			map.put("orgId", orgId);
			map.put("orgPath", orgPath);

			List<WeaponVM> list = new ArrayList<WeaponVM>(); 
			list = weaponService.loadVMList(map);

			String serverPath = getClass().getResource("/").getFile()
					.toString();
			serverPath = serverPath.substring(0, (serverPath.length() - 16)); 
			String filepath ="0";
			if(list.size()>0){
				filepath = createWeaponExcel(list, serverPath);
			}

			String retMsg = "";
			int retCode = 0;
			if (filepath.equals("1")) {
				retMsg = "文件创建出错";
				retCode = MessageCode.STATUS_FAIL;
			} else if (filepath.equals("2")){
				retMsg = "创建Excel组件出错";
				retCode = MessageCode.STATUS_FAIL;
			}else if(filepath.equals("0")){
				retMsg = "无查询结果数据，导出失败";
				retCode = MessageCode.STATUS_FAIL;
			}else{
				retMsg = filepath;
				retCode = MessageCode.STATUS_SUCESS;
			}
			return ReturnResult.MESSAGE(retCode, retMsg, 0, null);
		} catch (Exception ex) {
			return ReturnResult.MESSAGE(MessageCode.STATUS_FAIL, "获取警员数据出错", 0,
					null);
		}
	}
	
	
	private String createWeaponExcel(List<WeaponVM> list, String serverPath) {
		// TODO Auto-generated method stub
		String filePath = "";
		String orgName = "";
		if (list.size() > 0) {
			Workbook workbook = null;
			try {
				workbook = new HSSFWorkbook();// HSSFWorkbook();//WorkbookFactory.create(inputStream);
				if (workbook != null) {
					Sheet sheet = workbook.createSheet("武器基础数据");
					Row row0 = sheet.createRow(0);
					String title = "武器基础数据汇总信息";
					orgName = list.get(0).getOrgName();
					title = orgName + title;
					
					Cell cell_0 = row0.createCell(0, Cell.CELL_TYPE_STRING);  
					cell_0.setCellValue(title);
					sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 2));

					Row row1 = sheet.createRow(1);

					Cell cell_1 = row1.createCell(0, Cell.CELL_TYPE_STRING); 
					cell_1.setCellValue("武器类型");
					sheet.autoSizeColumn(0);

					Cell cell_2 = row1.createCell(1, Cell.CELL_TYPE_STRING); 
					cell_2.setCellValue("武器编号");
					sheet.autoSizeColumn(1);

					Cell cell_3 = row1.createCell(2, Cell.CELL_TYPE_STRING); 
					cell_3.setCellValue("规格标准");
					sheet.autoSizeColumn(2);

					for (int rowNum = 2; rowNum <= list.size()+1; rowNum++) {
						Row row = sheet.createRow(rowNum);
						WeaponVM weapon = new WeaponVM();
						weapon = list.get(rowNum - 2);
						Cell cella = row.createCell(0, Cell.CELL_TYPE_STRING);
						cella.setCellValue(weapon.getTypeName() == null ? ""
								: weapon.getTypeName());
						Cell cellb = row.createCell(1, Cell.CELL_TYPE_STRING);
						cellb.setCellValue(weapon.getNumber() == null ? "" : weapon
								.getNumber());
						Cell cellc = row.createCell(2, Cell.CELL_TYPE_STRING);
						cellc.setCellValue(weapon.getStandard() == null ? ""
								: weapon.getStandard());

					}
				}
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
				java.util.Date date = new java.util.Date();
				String str = sdf.format(date);
				filePath = "excelModel/tempfile/" + str + "_weaponData.xls";
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


	private String createGpsExcel(List<GpsVM> list, String serverPath) {
		// TODO Auto-generated method stub
		String filePath = "";
		String orgName = "";
		if (list.size() > 0) {
			Workbook workbook = null;
			try {
				workbook = new HSSFWorkbook();// HSSFWorkbook();//WorkbookFactory.create(inputStream);
				if (workbook != null) {
					Sheet sheet = workbook.createSheet("定位设备基础数据");
					Row row0 = sheet.createRow(0);
					String title = "定位设备基础数据汇总信息";
					orgName = list.get(0).getOrgName();
					title = orgName + title;
					
					Cell cell_0 = row0.createCell(0, Cell.CELL_TYPE_STRING);  
					cell_0.setCellValue(title);
					sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 2)); 
					Row row1 = sheet.createRow(1);

					Cell cell_1 = row1.createCell(0, Cell.CELL_TYPE_STRING); 
					cell_1.setCellValue("定位设备类型");
					sheet.autoSizeColumn(0);

					Cell cell_2 = row1.createCell(1, Cell.CELL_TYPE_STRING); 
					cell_2.setCellValue("显示名称");
					sheet.autoSizeColumn(1);

					Cell cell_3 = row1.createCell(2, Cell.CELL_TYPE_STRING); 
					cell_3.setCellValue("设备编号");
					sheet.autoSizeColumn(2);

					Cell cell_4 = row1.createCell(3, Cell.CELL_TYPE_STRING); 
					cell_4.setCellValue("图片链接地址");
					sheet.autoSizeColumn(3);

					for (int rowNum = 2; rowNum <= list.size()+1; rowNum++) {
						Row row = sheet.createRow(rowNum);
						GpsVM gps = new GpsVM();
						gps = list.get(rowNum - 2);
						Cell cella = row.createCell(0, Cell.CELL_TYPE_STRING);
						cella.setCellValue(gps.getTypeName() == null ? "" : gps
								.getTypeName());
						Cell cellb = row.createCell(1, Cell.CELL_TYPE_STRING);
						cellb.setCellValue(gps.getGpsName() == null ? "" : gps
								.getGpsName());
						Cell cellc = row.createCell(2, Cell.CELL_TYPE_STRING);
						cellc.setCellValue(gps.getNumber() == null ? "" : gps
								.getNumber());
						Cell celle = row.createCell(3, Cell.CELL_TYPE_STRING);
						celle.setCellValue(gps.getIconUrl() == null ? "" : gps
								.getIconUrl());
					}
				}
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
				java.util.Date date = new java.util.Date();
				String str = sdf.format(date);
				filePath = "excelModel/tempfile/" + str + "_gpsData.xls";
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


	private String createVehicleExcel(List<VehicleVM> list, String serverPath) {
		// TODO Auto-generated method stub
		String filePath = "";
		String orgName = "";
		if (list.size() > 0) {
			Workbook workbook = null;
			try {
				workbook = new HSSFWorkbook();// HSSFWorkbook();//WorkbookFactory.create(inputStream);
				if (workbook != null) {
					Sheet sheet = workbook.createSheet("车辆基础数据");
					Row row0 = sheet.createRow(0);
					String title = "车辆基础数据汇总信息";
					orgName = list.get(0).getOrgName();
					title = orgName + title;
					
					Cell cell_0 = row0.createCell(0, Cell.CELL_TYPE_STRING);  

					cell_0.setCellValue(title);
					sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 8));

					Row row1 = sheet.createRow(1);
					Cell cell_1 = row1.createCell(0, Cell.CELL_TYPE_STRING);  
					cell_1.setCellValue("车辆类型");
					sheet.autoSizeColumn(0);

					Cell cell_2 = row1.createCell(1, Cell.CELL_TYPE_STRING); 
					cell_2.setCellValue("车牌号码");
					sheet.autoSizeColumn(1);

					Cell cell_3 = row1.createCell(2, Cell.CELL_TYPE_STRING); 
					cell_3.setCellValue("车辆用途");
					sheet.autoSizeColumn(2);

					Cell cell_4 = row1.createCell(3, Cell.CELL_TYPE_STRING); 
					cell_4.setCellValue("车辆品牌");
					sheet.autoSizeColumn(3);

					Cell cell_5 = row1.createCell(4, Cell.CELL_TYPE_STRING); 
					cell_5.setCellValue("座位数");
					sheet.autoSizeColumn(4);

					Cell cell_6 = row1.createCell(5, Cell.CELL_TYPE_STRING); 
					cell_6.setCellValue("GPS设备ID");
					sheet.autoSizeColumn(5);

					Cell cell_7 = row1.createCell(6, Cell.CELL_TYPE_STRING); 
					cell_7.setCellValue("GPS名称");
					sheet.autoSizeColumn(6);

					Cell cell_8 = row1.createCell(7, Cell.CELL_TYPE_STRING); 
					cell_8.setCellValue("组呼号");
					sheet.autoSizeColumn(7);

					Cell cell_9 = row1.createCell(8, Cell.CELL_TYPE_STRING); 
					cell_9.setCellValue("个呼号");
					sheet.autoSizeColumn(8);

					for (int rowNum = 2; rowNum <= list.size()+1; rowNum++) {
						Row row = sheet.createRow(rowNum);
						VehicleVM vehicle = new VehicleVM();
						vehicle = list.get(rowNum - 2);
						Cell cella = row.createCell(0, Cell.CELL_TYPE_STRING);
						cella.setCellValue(vehicle.getTypeName() == null ? ""
								: vehicle.getTypeName());
						Cell cellb = row.createCell(1, Cell.CELL_TYPE_STRING);
						cellb.setCellValue(vehicle.getNumber() == null ? ""
								: vehicle.getNumber());
						Cell cellc = row.createCell(2, Cell.CELL_TYPE_STRING);
						cellc.setCellValue(vehicle.getPurposeName() == null ? ""
								: vehicle.getPurposeName());
						Cell celle = row.createCell(3, Cell.CELL_TYPE_STRING);
						celle.setCellValue(vehicle.getBrand() == null ? ""
								: vehicle.getBrand());
						Cell celld = row.createCell(4, Cell.CELL_TYPE_STRING);
						celld.setCellValue(vehicle.getSiteQty() == null ? ""
								: vehicle.getSiteQty());
						Cell cellf = row.createCell(5, Cell.CELL_TYPE_STRING);
						cellf.setCellValue(vehicle.getGpsNumber() == null ? ""
								: vehicle.getGpsNumber());

						Cell cellg = row.createCell(6, Cell.CELL_TYPE_STRING);
						cellg.setCellValue(vehicle.getGpsName() == null ? ""
								: vehicle.getGpsName());
						Cell cellh = row.createCell(7, Cell.CELL_TYPE_STRING);
						cellh.setCellValue(vehicle.getIntercomGroup() == null ? ""
								: vehicle.getIntercomGroup());
						Cell celli = row.createCell(8, Cell.CELL_TYPE_STRING);
						celli.setCellValue(vehicle.getIntercomPerson() == null ? ""
								: vehicle.getIntercomPerson());

					}
				}
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
				java.util.Date date = new java.util.Date();
				String str = sdf.format(date);
				filePath = "excelModel/tempfile/" + str + "_vehicleData.xls";
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


	private String createPoliceExcel(List<PoliceVM> list, String serverPath) {
		// TODO Auto-generated method stub
		String filePath = "";
		String orgName = "";
		if (list.size() > 0) {
			Workbook workbook = null;
			try {
				workbook = new HSSFWorkbook();// HSSFWorkbook();//WorkbookFactory.create(inputStream);
				if (workbook != null) {
					Sheet sheet = workbook.createSheet("警员基础数据");
					Row row0 = sheet.createRow(0);
					String title = "警员基础数据汇总信息";
					orgName = list.get(0).getOrgName();
					title = orgName + title;

					Cell cell_0 = row0.createCell(0, Cell.CELL_TYPE_STRING);
					cell_0.setCellValue(title);
					sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 9));
					Row row1 = sheet.createRow(1);
					Cell cell_1 = row1.createCell(0, Cell.CELL_TYPE_STRING);
					cell_1.setCellValue("职务");
					sheet.autoSizeColumn(0);

					Cell cell_2 = row1.createCell(1, Cell.CELL_TYPE_STRING);
					cell_2.setCellValue("姓名");
					sheet.autoSizeColumn(1);

					Cell cell_3 = row1.createCell(2, Cell.CELL_TYPE_STRING);
					cell_3.setCellValue("警号");
					sheet.autoSizeColumn(2);

					Cell cell_4 = row1.createCell(3, Cell.CELL_TYPE_STRING);
					cell_4.setCellValue("GPS名称");
					sheet.autoSizeColumn(3);

					Cell cell_5 = row1.createCell(4, Cell.CELL_TYPE_STRING);
					cell_5.setCellValue("手机号");
					sheet.autoSizeColumn(4);

					Cell cell_6 = row1.createCell(5, Cell.CELL_TYPE_STRING);
					cell_6.setCellValue("公安短号");
					sheet.autoSizeColumn(5);

					Cell cell_7 = row1.createCell(6, Cell.CELL_TYPE_STRING);
					cell_7.setCellValue("身份证号码");
					sheet.autoSizeColumn(6);

					Cell cell_8 = row1.createCell(7, Cell.CELL_TYPE_STRING);
					cell_8.setCellValue("警员类别");
					sheet.autoSizeColumn(7);

					Cell cell_9 = row1.createCell(8, Cell.CELL_TYPE_STRING);
					cell_9.setCellValue("组呼号");
					sheet.autoSizeColumn(8);

					Cell cell_t = row1.createCell(9, Cell.CELL_TYPE_STRING);
					cell_t.setCellValue("个呼号");
					sheet.autoSizeColumn(9);

					for (int rowNum = 2; rowNum <= list.size() + 1; rowNum++) {
						Row row = sheet.createRow(rowNum);
						PoliceVM police = new PoliceVM();
						police = list.get(rowNum - 2);
						Cell cella = row.createCell(0, Cell.CELL_TYPE_STRING);
						cella.setCellValue(police.getTitleName() == null ? ""
								: police.getTitleName());
						Cell cellb = row.createCell(1, Cell.CELL_TYPE_STRING);
						cellb.setCellValue(police.getName() == null ? ""
								: police.getName());
						Cell cellc = row.createCell(2, Cell.CELL_TYPE_STRING);
						cellc.setCellValue(police.getNumber() == null ? ""
								: police.getNumber());
						Cell celle = row.createCell(3, Cell.CELL_TYPE_STRING);
						celle.setCellValue(police.getGpsName() == null ? ""
								: police.getGpsName());
						Cell celld = row.createCell(4, Cell.CELL_TYPE_STRING);
						celld.setCellValue(police.getMobile() == null ? ""
								: police.getMobile());
						Cell cellf = row.createCell(5, Cell.CELL_TYPE_STRING);
						cellf.setCellValue(police.getMobileShort() == null ? ""
								: police.getMobileShort());

						Cell cellg = row.createCell(6, Cell.CELL_TYPE_STRING);
						cellg.setCellValue(police.getIdcardno() == null ? ""
								: police.getIdcardno());
						Cell cellh = row.createCell(7, Cell.CELL_TYPE_STRING);
						cellh.setCellValue(police.getTypeName() == null ? ""
								: police.getTypeName());
						Cell celli = row.createCell(8, Cell.CELL_TYPE_STRING);
						celli.setCellValue(police.getIntercomGroup() == null ? ""
								: police.getIntercomGroup());
						Cell cellj = row.createCell(9, Cell.CELL_TYPE_STRING);
						cellj.setCellValue(police.getIntercomPerson() == null ? ""
								: police.getIntercomPerson());
					}
				}
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
				java.util.Date date = new java.util.Date();
				String str = sdf.format(date);
				filePath = "excelModel/tempfile/" + str + "_policeData.xls";
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
 

}
