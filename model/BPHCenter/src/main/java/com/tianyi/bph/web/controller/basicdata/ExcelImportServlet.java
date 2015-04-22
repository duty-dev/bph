package com.tianyi.bph.web.controller.basicdata;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;

import com.tianyi.bph.domain.basicdata.Icons;

@SuppressWarnings("serial")
public class ExcelImportServlet extends HttpServlet {

	public void init(ServletConfig config) throws ServletException {

		super.init(config);

	}

	private Connection conn = null;
	private final static String SEPARATOR = "|";
	private PreparedStatement ps = null;

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		Connection conn = null;
		PreparedStatement ps = null;
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(4 * 1024);
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setSizeMax(1000 * 1024 * 1024);
		List itemList = null;
		try {
			itemList = upload.parseRequest(request);
		} catch (FileUploadBase.SizeLimitExceededException e) {
			// 请求数据的size超出了规定的大小.
			e.printStackTrace();
			request.setAttribute("uploadError", "请求数据的size超出了规定的大小");
			request.getRequestDispatcher("/basicdata/police/policeExport.jsp")
					.forward(request, response);
			return;
		} catch (FileUploadBase.InvalidContentTypeException e) {
			// 无效的请求类型,即请求类型enctype != "multipart/form-data"
			request.setAttribute("uploadError",
					"请求类型enctype != multipart/form-data");
			request.getRequestDispatcher("/basicdata/police/policeExport.jsp")
					.forward(request, response);
			return;
		} catch (FileUploadException e) {
			// 如果都不是以上子异常,则抛出此总的异常,出现此异常原因无法说明.
			request.setAttribute("uploadError", "上传过程异常，导致其原因可能是磁盘已满或者其它原因");
			request.getRequestDispatcher("/basicdata/police/policeExport.jsp")
					.forward(request, response);
			return;
		}
		String dataType = "";
		int orgId = 0;
		if (itemList != null) {
			Iterator it = itemList.iterator();
			while (it.hasNext()) {
				FileItem item = (FileItem) it.next();
				if (item.isFormField()) {
					// 非文件流
					String value = item.getString();
					value = new String(value.getBytes("ISO-8859-1"), "UTF-8");
					if (item.getFieldName().equals("dataType")) {
						dataType = value;
					}
					if (item.getFieldName().equals("orgId")) {
						orgId = Integer.parseInt(value);
					}
				} else {
					String uploadPath = request
							.getRealPath("excelModel/tempfile");
					String totalName = item.getName();
					String name = "temp";
					if (totalName != "") {
						int index = totalName.lastIndexOf("\\");
						name = totalName.substring(index + 1);
						name = new String(name.getBytes("ISO-8859-1"), "UTF-8");

					} else {
						name = "temp";
					}
					UUID uuid = UUID.randomUUID();
					String path = uploadPath + "/" + uuid + name;
					try {
						item.write(new File(path));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						request.setAttribute("uploadError", "写入磁盘错误");
						request.getRequestDispatcher

						("/basicdata/police/policeExport.jsp").forward(request,
								response);
						return;
					}
					if (dataType.equals("policeData")) {
						operationPoliceDataToDb(orgId, path);
					} else if (dataType.equals("vehicleData")) {
						operationVehicleDataToDb(orgId, path);
					} else if (dataType.equals("gpsData")) {
						operationGpsDataToDb(orgId, path);
					} else if (dataType.equals("weaponData")) {
						operationWeaponDataToDb(orgId, path);
					}

				}
			}
		} else {
			request.setAttribute("uploadError", "请选择要上传的Excel文件！");
			request.getRequestDispatcher("/basicdata/police/policeExport.jsp")
					.forward(request, response);
			return;
		}
		request.setAttribute("uploadError", "导入成功");
		request.getRequestDispatcher("/basicdata/police/policeExport.jsp")
				.forward(request, response);
	}

	private void operationWeaponDataToDb(int orgId, String path) {
		// TODO Auto-generated method stub
		File pc = new File(path);
		conn = DBClassMysql.getMysql();
		if (pc.exists()) {
			try {
				HSSFWorkbook wookbook = new HSSFWorkbook(new FileInputStream(
						path));
				// HSSFSheet sheet = wookbook.getSheet("WeaponInfo");
				HSSFSheet sheet = wookbook.getSheetAt(0);
				// 获取到Excel文件中的所有行数
				// int rows = sheet.getPhysicalNumberOfRows();

				int minRowIx = sheet.getFirstRowNum();
				int maxRowIx = sheet.getLastRowNum();
				int rows = 0;
				if ((maxRowIx - minRowIx) > 2) {
					rows = maxRowIx - minRowIx;
				}
				// 只有行大于2的时候，才有数据，前面两行为title栏位
				if (rows > 0 && rows > 2) {
					// 遍历行
					for (int i = 2; i <= rows; i++) {
						// 读取左上端单元格?
						HSSFRow row = sheet.getRow(i);
						// 行不为空
						if (row != null) {
							// 获取到Excel文件中的所有的列?
							int cells = row.getPhysicalNumberOfCells();
							String value = "";
							for (int j = 0; j < cells; j++) {
								// 获取到列的值?
								HSSFCell cell = row.getCell(j);
								if (cell != null) {

									switch (cell.getCellType()) {
									case Cell.CELL_TYPE_BOOLEAN:
										// sb.append(SEPARATOR +
										// cellValue.getBooleanValue());
										value += (cell.getBooleanCellValue() + SEPARATOR);
										break;
									case Cell.CELL_TYPE_NUMERIC:
										// 这里的日期类型会被转换为数字类型，需要判别后区分处理
										if (DateUtil.isCellDateFormatted(cell)) {
											value += (cell.getDateCellValue() + SEPARATOR);
										} else {
											value += (cell
													.getNumericCellValue() + SEPARATOR);
										}
										// sb.append(cellValue.getBooleanValue());
										break;
									case Cell.CELL_TYPE_STRING:
										// sb.append(SEPARATOR +
										// cellValue.getStringValue());
										value += (cell.getStringCellValue() + SEPARATOR);
										break;
									case Cell.CELL_TYPE_FORMULA:
										value += (cell.getStringCellValue()+ SEPARATOR);
										break;
									case Cell.CELL_TYPE_BLANK:
										value += ("0.0" + SEPARATOR);
										break;
									case Cell.CELL_TYPE_ERROR:
										value += (cell.getStringCellValue() + SEPARATOR);
										break;
									default:
										value += (cell.getStringCellValue() + SEPARATOR);
										break;
									}
								}
							}
							String[] val = value.split("\\|");
							String sql = "insert into t_weapon (type_id,org_id,number,standard,sync_state, platform_id) values (?,?,?,?,?,?)";
							try {
								// ps.clearParameters();
								ps = conn.prepareStatement(sql);
								ps.setInt(1, getWeaponTypeId(val[1]));
								ps.setInt(2, orgId);
								String number = val[0] == null ? "" : val[0]
										.trim().equals("0.0") ? ""
										: val[0];
								ps.setString(3, number);
								String standards = val[2] == null ? "" : val[2]
										.trim().equals("0.0") ? "" : val[2];
								ps.setString(4, standards);
								ps.setBoolean(5, true);
								ps.setInt(6, 1);

								ps.executeUpdate();
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}

					}
				}
				try {
					ps.cancel();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			pc.delete();
		}
	}

	private Integer getGpsTypeId(String typename) {
		int tId = 3;
		if (typename.equals("800M") || typename.equals("800m")) {
			tId = 1;
		} else if (typename.equals("350M") || typename.equals("350m")) {
			tId = 2;
		} else if (typename.equals("Qchat")) {
			tId = 3;
		} else if (typename.equals("其他") || typename.equals("其它")) {
			tId = 4;
		}
		return tId;
	}

	private int getVehicleTypeId(String typename) {
		int tId = 1;
		if (typename.equals("汽车") || typename.contains("汽")) {
			tId = 1;
		} else if (typename.equals("警摩") || typename.contains("摩")) {
			tId = 2;
		} else if (typename.equals("自行车") || typename.contains("电")) {
			tId = 3;
		}
		return tId;
	}

	private Integer getWeaponTypeId(String typename) {
		int tId = 1;
		if (typename.equals("65式手枪") || typename.contains("65式")) {
			tId = 1;
		} else if (typename.equals("77式手枪") || typename.contains("77式")) {
			tId = 2;
		} else if (typename.equals("97式突击步枪") || typename.contains("97式")) {
			tId = 3;
		} else if (typename.equals("79式冲锋枪") || typename.contains("79式")) {
			tId = 4;
		}
		return tId;
	}

	private void operationGpsDataToDb(int orgId, String path) {// TODO
																// Auto-generated
																// method stub
		File pc = new File(path);
		conn = DBClassMysql.getMysql();
		if (pc.exists()) {
			try {
				HSSFWorkbook wookbook = new HSSFWorkbook(new FileInputStream(
						path));
				// HSSFSheet sheet = wookbook.getSheet("GpsInfo");
				HSSFSheet sheet = wookbook.getSheetAt(0);
				// 获取到Excel文件中的所有行数
				// int rows = sheet.getPhysicalNumberOfRows();

				int minRowIx = sheet.getFirstRowNum();
				int maxRowIx = sheet.getLastRowNum();
				int rows = 0;
				if ((maxRowIx - minRowIx) > 2) {
					rows = maxRowIx - minRowIx;
				}
				// 只有行大于2的时候，才有数据，前面两行为title栏位
				if (rows > 0 && rows > 2) {
					// 遍历行
					for (int i = 2; i <= rows; i++) {
						// 读取左上端单元格?
						HSSFRow row = sheet.getRow(i);
						// 行不为空
						if (row != null) {
							// 获取到Excel文件中的所有的列?
							int cells = row.getPhysicalNumberOfCells();
							String value = "";
							for (int j = 0; j < cells; j++) {
								// 获取到列的值?
								HSSFCell cell = row.getCell(j);
								if (cell != null) {

									switch (cell.getCellType()) {
									case Cell.CELL_TYPE_BOOLEAN:
										// sb.append(SEPARATOR +
										// cellValue.getBooleanValue());
										value += (cell.getBooleanCellValue() + SEPARATOR);
										break;
									case Cell.CELL_TYPE_NUMERIC:
										// 这里的日期类型会被转换为数字类型，需要判别后区分处理
										if (DateUtil.isCellDateFormatted(cell)) {
											value += (cell.getDateCellValue() + SEPARATOR);
										} else {
											value += (cell
													.getNumericCellValue() + SEPARATOR);
										}
										// sb.append(cellValue.getBooleanValue());
										break;
									case Cell.CELL_TYPE_STRING:
										// sb.append(SEPARATOR +
										// cellValue.getStringValue());
										value += (cell.getStringCellValue() + SEPARATOR);
										break;
									case Cell.CELL_TYPE_FORMULA:
										value += (cell.getStringCellValue() + SEPARATOR);
										break;
									case Cell.CELL_TYPE_BLANK:
										value += ("0.0" + SEPARATOR);
										break;
									case Cell.CELL_TYPE_ERROR:
										value += (cell.getStringCellValue() + SEPARATOR);
										break;
									default:
										value += (cell.getStringCellValue() + SEPARATOR);
										break;
									}
								}
							}
							String[] val = value.split("\\|");
							String sql = "insert into t_gps (type_id,org_id,number,gps_name,icon_url,sync_state, platform_id) values (?,?,?,?,?,?,?)";
							try {
								// ps.clearParameters();
								ps = conn.prepareStatement(sql);
								ps.setInt(1, getGpsTypeId(val[0]));
								ps.setInt(2, orgId);
								ps.setString(3, val[2] == null ? "" : val[2]
										.trim().equals("0.0") ? "" : val[2]);
								ps.setString(4, val[1] == null ? "" : val[1]
										.trim().equals("0.0") ? "" :val[1]);
								ps.setString(5, "images/images/gpsDemo.png");
								ps.setBoolean(6, true);
								ps.setInt(7, 1);

								ps.executeUpdate();
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}

					}
				}
				try {
					ps.cancel();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			pc.delete();
		}

	}

	private void operationVehicleDataToDb(int orgId, String path) {
		// TODO Auto-generated method stub
		File pc = new File(path);
		conn = DBClassMysql.getMysql();
		if (pc.exists()) {
			try {
				HSSFWorkbook wookbook = new HSSFWorkbook(new FileInputStream(
						path));
				// HSSFSheet sheet = wookbook.getSheet("VehicleInfo");
				HSSFSheet sheet = wookbook.getSheetAt(0);
				// 获取到Excel文件中的所有行数
				// int rows = sheet.getPhysicalNumberOfRows();

				int minRowIx = sheet.getFirstRowNum();
				int maxRowIx = sheet.getLastRowNum();
				int rows = 0;
				if ((maxRowIx - minRowIx) > 2) {
					rows = maxRowIx - minRowIx;
				}
				// 只有行大于2的时候，才有数据，前面两行为title栏位
				if (rows > 0 && rows > 2) {
					// 遍历行
					for (int i = 2; i <= rows; i++) {
						// 读取左上端单元格?
						HSSFRow row = sheet.getRow(i);
						// 行不为空
						if (row != null) {
							// 获取到Excel文件中的所有的列?
							int cells = row.getPhysicalNumberOfCells();
							String value = "";
							for (int j = 0; j < cells; j++) {
								// 获取到列的值?
								HSSFCell cell = row.getCell(j);
								if (cell != null) {

									switch (cell.getCellType()) {
									case Cell.CELL_TYPE_BOOLEAN:
										// sb.append(SEPARATOR +
										// cellValue.getBooleanValue());
										value += (cell.getBooleanCellValue() + SEPARATOR);
										break;
									case Cell.CELL_TYPE_NUMERIC:
										// 这里的日期类型会被转换为数字类型，需要判别后区分处理
										if (DateUtil.isCellDateFormatted(cell)) {
											value += (cell.getDateCellValue() + SEPARATOR);
										} else {
											value += (cell
													.getNumericCellValue() + SEPARATOR);
										}
										// sb.append(cellValue.getBooleanValue());
										break;
									case Cell.CELL_TYPE_STRING:
										// sb.append(SEPARATOR +
										// cellValue.getStringValue());
										value += (cell.getStringCellValue() + SEPARATOR);
										break;
									case Cell.CELL_TYPE_FORMULA:
										value += (cell.getStringCellValue() + SEPARATOR);
										break;
									case Cell.CELL_TYPE_BLANK:
										value += ("0.0" + SEPARATOR);
										break;
									case Cell.CELL_TYPE_ERROR:
										value += (cell.getStringCellValue() + SEPARATOR);
										break;
									default:
										value += (cell.getStringCellValue() + SEPARATOR);
										break;
									}
								}
							}
							String[] val = value.split("\\|");
							String sql = "insert into t_vehicle (vehicle_type_id,org_id,number,intercom_group,intercom_person,purpose,brand,site_qty,sync_state, platform_id) values (?,?,?,?,?,?,?,?,?,?)";
							try {
								// ps.clearParameters();
								ps = conn.prepareStatement(sql);
								ps.setInt(1, getVehicleTypeId(val[0]));
								ps.setInt(2, orgId);
								ps.setString(3, val[1] == null ? "" : val[1]
										.trim().equals("0.0") ? "" : val[1]);
								ps.setString(4, val[5] == null ? "" : val[5]
										.trim().equals("0.0") ? "" : val[5]);
								ps.setString(5, val[6] == null ? "" : val[6]
										.trim().equals("0.0") ? "" :val[6]);
								ps.setString(6, val[2] == null ? "" : val[2]
										.trim().equals("0.0") ? "" : val[2]);
								ps.setString(7, val[3] == null ? "" : val[3]
										.trim().equals("0.0") ? "" : val[3]);
								ps.setString(8, val[4] == null ? "" : val[4]
										.trim().equals("0.0") ? "" :val[4]);
								ps.setBoolean(9, true);
								ps.setInt(10, 1);

								ps.executeUpdate();
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}

					}
				}
				try {
					ps.cancel();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			pc.delete();
		}
	}

	private void operationPoliceDataToDb(Integer orgId, String path) {
		// TODO Auto-generated method stub
		File pc = new File(path);
		conn = DBClassMysql.getMysql();
		if (pc.exists()) {
			try {
				HSSFWorkbook wookbook = new HSSFWorkbook(new FileInputStream(
						path));
				// HSSFSheet sheet = wookbook.getSheet("PoliceInfo");
				HSSFSheet sheet = wookbook.getSheetAt(0);
				// 获取到Excel文件中的所有行数
				// int rows = sheet.getPhysicalNumberOfRows();

				int minRowIx = sheet.getFirstRowNum();
				int maxRowIx = sheet.getLastRowNum();
				int rows = 0;
				if ((maxRowIx - minRowIx) > 2) {
					rows = maxRowIx - minRowIx;
				}
				// 只有行大于2的时候，才有数据，前面两行为title栏位
				if (rows > 0 && rows > 2) {
					// 遍历行
					for (int i = 2; i <= rows; i++) {
						// 读取左上端单元格?
						HSSFRow row = sheet.getRow(i);
						// 行不为空
						if (row != null) {
							// 获取到Excel文件中的所有的列?
							int cells = row.getPhysicalNumberOfCells();
							String value = "";
							for (int j = 0; j < cells; j++) {
								// 获取到列的值?
								HSSFCell cell = row.getCell(j);
								if (cell != null) {

									switch (cell.getCellType()) {
									case Cell.CELL_TYPE_BOOLEAN:
										// sb.append(SEPARATOR +
										// cellValue.getBooleanValue());
										value += (cell.getBooleanCellValue() + SEPARATOR);
										break;
									case Cell.CELL_TYPE_NUMERIC:
										// 这里的日期类型会被转换为数字类型，需要判别后区分处理
										if (DateUtil.isCellDateFormatted(cell)) {
											value += (cell.getDateCellValue() + SEPARATOR);
										} else {
											value += (cell
													.getNumericCellValue() + SEPARATOR);
										}
										// sb.append(cellValue.getBooleanValue());
										break;
									case Cell.CELL_TYPE_STRING:
										// sb.append(SEPARATOR +
										// cellValue.getStringValue());
										value += (	cell.getStringCellValue()+ SEPARATOR);
										break;
									case Cell.CELL_TYPE_FORMULA:
										value += (cell.getStringCellValue() + SEPARATOR);
										break;
									case Cell.CELL_TYPE_BLANK:
										value += ("0.0"+ SEPARATOR);
										break;
									case Cell.CELL_TYPE_ERROR:
										value += (cell.getStringCellValue() + SEPARATOR);
										break;
									default:
										value += (cell.getStringCellValue() + SEPARATOR);
										break;
									}
								}
							}
							String[] val = value.split("\\|");
							String sql = "insert into t_police (type_id,name,org_id,idcardNo,number,title,mobile,mobile_short,intercom_group,intercom_person,gps_name,isUsed,sync_state, platform_id) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
							try {
								// ps.clearParameters();
								ps = conn.prepareStatement(sql);
								ps.setInt(1, 1);
								ps.setString(2, val[0] == null ? "" : val[0]
										.trim().equals("0.0") ? "" : val[0]);
								ps.setInt(3, orgId);
								ps.setString(4, val[3] == null ? "" : val[3]
										.trim().equals("0.0") ? "" : val[3]);
								ps.setString(5, val[2] == null ? "" : val[2]
										.trim().equals("0.0") ? "" : 
												val[2]);
								ps.setString(6, val[4] == null ? "" : val[4]
										.trim().equals("0.0") ? "" :
												val[4]);
								ps.setString(7, val[5] == null ? "" : val[5]
										.trim().equals("0.0") ? "" : 
												val[5]);
								ps.setString(8, val[6] == null ? "" : val[6]
										.trim().equals("0.0") ? "" : 
												val[6]);
								ps.setString(9, val[7] == null ? "" : val[7]
										.trim().equals("0.0") ? "" :
												val[7]);
								ps.setString(10, val[8] == null ? "" : val[8]
										.trim().equals("0.0") ? "" : 
												val[8]);
								ps.setString(11, null);
								ps.setBoolean(12, true);
								ps.setBoolean(13, true);
								ps.setInt(14, 1);

								ps.executeUpdate();
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}

					}
				}
				try {
					ps.cancel();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			pc.delete();
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doPost(req, resp);
	}
}
