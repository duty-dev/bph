package com.tianyi.bph.web.controller.basicdata;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import org.springframework.web.context.ContextLoader;

import com.alibaba.druid.pool.DruidDataSource;

@SuppressWarnings("serial")
public class ExcelImportServlet extends HttpServlet {

	public void init(ServletConfig config) throws ServletException {

		super.init(config);

	}

	private final com.alibaba.druid.pool.DruidDataSource dt = ContextLoader
			.getCurrentWebApplicationContext().getBean(DruidDataSource.class);
	// private Connection conn = null;
	private Connection conn = null;
	private final static String SEPARATOR = "|";
	private PreparedStatement ps = null;
	private PreparedStatement pes = null;

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
			return;
		} catch (FileUploadBase.InvalidContentTypeException e) {
			// 无效的请求类型,即请求类型enctype != "multipart/form-data"
			request.setAttribute("uploadError",
					"请求类型enctype != multipart/form-data");
			return;
		} catch (FileUploadException e) {
			// 如果都不是以上子异常,则抛出此总的异常,出现此异常原因无法说明.
			request.setAttribute("uploadError", "上传过程异常，导致其原因可能是磁盘已满或者其它原因");
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
						return;
					}
					boolean isSuccess = false;
					List<String> retMsgList = new ArrayList<String>();
					if (dataType.equals("policeData")) {
						try {
							isSuccess = operationPoliceDataToDb(orgId, path,
									retMsgList);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if (isSuccess) {
							if (retMsgList.size() > 0) {
								request.setAttribute("uploadError",
										"部分数据导入成功,详细信息:");
							} else {
								request.setAttribute("uploadError", "导入成功\n\r");
							}
							request.setAttribute("uploadlist", retMsgList);
						} else {
							request.setAttribute("uploadError",
									"导入失败，写入文件内容格式不是要求文件内容格式，请参考模板文件上传");
							if (retMsgList.size() > 0) {
								retMsgList = new ArrayList<String>();
								request.setAttribute("uploadlist", retMsgList);
							}
						}
						request.getRequestDispatcher(
								"/basicdata/police/policeExport.jsp").forward(
								request, response);
					} else if (dataType.equals("vehicleData")) {
						try {
							isSuccess = operationVehicleDataToDb(orgId, path,
									retMsgList);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if (isSuccess) {
							if (retMsgList.size() > 0) {
								request.setAttribute("uploadError",
										"部分数据导入成功,详细信息:");
							} else {
								request.setAttribute("uploadError", "导入成功\n\r");
							}
							request.setAttribute("uploadlist", retMsgList);
						} else {
							request.setAttribute("uploadError",
									"导入失败，写入文件内容格式不是要求文件内容格式，请参考模板文件上传");
							if (retMsgList.size() > 0) {
								retMsgList = new ArrayList<String>();
								request.setAttribute("uploadlist", retMsgList);
							}
						}
						request.getRequestDispatcher(
								"/basicdata/vehicle/vehicleExport.jsp")
								.forward(request, response);
					} else if (dataType.equals("gpsData")) {
						try {
							isSuccess = operationGpsDataToDb(orgId, path,
									retMsgList);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if (isSuccess) {
							if (retMsgList.size() > 0) {
								request.setAttribute("uploadError",
										"部分数据导入成功,详细信息:");
							} else {
								request.setAttribute("uploadError", "导入成功\n\r");
							}
							request.setAttribute("uploadlist", retMsgList);
						} else {
							request.setAttribute("uploadError",
									"导入失败，写入文件内容格式不是要求文件内容格式，请参考模板文件上传");
							if (retMsgList.size() > 0) {
								retMsgList = new ArrayList<String>();
								request.setAttribute("uploadlist", retMsgList);
							}
						}
						request.getRequestDispatcher(
								"/basicdata/gps/gpsExport.jsp").forward(
								request, response);
					} else if (dataType.equals("weaponData")) {
						try {
							isSuccess = operationWeaponDataToDb(orgId, path,
									retMsgList);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if (isSuccess) {
							if (retMsgList.size() > 0) {
								request.setAttribute("uploadError",
										"部分数据导入成功,详细信息:");
							} else {
								request.setAttribute("uploadError", "导入成功\n\r");
							}
							request.setAttribute("uploadlist", retMsgList);
						} else {
							request.setAttribute("uploadError",
									"导入失败，写入文件内容格式不是要求文件内容格式，请参考模板文件上传");
							if (retMsgList.size() > 0) {
								retMsgList = new ArrayList<String>();
								request.setAttribute("uploadlist", retMsgList);
							}
						}
						request.getRequestDispatcher(
								"/basicdata/weapon/weaponExport.jsp").forward(
								request, response);
					}
				}
			}
		}
	}

	private boolean operationWeaponDataToDb(int orgId, String path,
			List<String> retMsgList) throws SQLException {
		// TODO Auto-generated method stub
		boolean isSuccess = false;
		File pc = new File(path);
		// conn = DBClassMysql.getMysql();
		conn = dt.getConnection();
		if (pc.exists()) {
			try {
				HSSFWorkbook wookbook = new HSSFWorkbook(new FileInputStream(
						path));
				// HSSFSheet sheet = wookbook.getSheet("WeaponInfo");
				HSSFSheet sheet = wookbook.getSheetAt(0);
				// 获取到Excel文件中的所有行数
				// int rows = sheet.getPhysicalNumberOfRows();

				int minRowIx = sheet.getFirstRowNum();
				int maxRowIx = sheet.getPhysicalNumberOfRows();
				int rows = 0;
				if ((maxRowIx - minRowIx) >= 2) {
					rows = maxRowIx - minRowIx;
				}
				try {
					// 只有行大于2的时候，才有数据，前面两行为title栏位
					if (rows > 0 && rows >= 2) {
						// 遍历行
						for (int i = 2; i <= rows; i++) {
							// 读取左上端单元格?
							HSSFRow row = sheet.getRow(i);
							// 行不为空
							if (row != null) {
								// 获取到Excel文件中的所有的列?
								// int cells = row.getPhysicalNumberOfCells();
								String value = "";
								for (int j = 0; j < 3; j++) {
									// 获取到列的值?
									HSSFCell cell = row.getCell(j);
									if (cell != null) {

										switch (cell.getCellType()) {
										case Cell.CELL_TYPE_BOOLEAN:
											// sb.append(SEPARATOR +
											// cellValue.getBooleanValue());
											value += (cell
													.getBooleanCellValue() + SEPARATOR);
											break;
										case Cell.CELL_TYPE_NUMERIC:
											// 这里的日期类型会被转换为数字类型，需要判别后区分处理
											if (DateUtil
													.isCellDateFormatted(cell)) {
												value += (cell
														.getDateCellValue() + SEPARATOR);
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
											value += ("0.0" + SEPARATOR);
											break;
										default:
											value += ("0.0" + SEPARATOR);
											break;
										}
									} else {
										value += ("0.0" + SEPARATOR);
									}
								}
								String[] val = value.split("\\|");

								String regMsg = "";
								String number = val[0] == null ? "" : val[0]
										.trim().equals("0.0") ? "" : val[0];
								if (number.equals("")) {
									regMsg += "第" + (i - 1)
											+ "行数据错误，详细：武器编号为空;";
								} else {
									try {
										String existSql = "select count(*) from t_weapon where number = '"
												+ number + "' ";
										pes = conn.prepareStatement(existSql);
										ResultSet rs = pes.executeQuery();
										int count = 0;
										while (rs.next()) {
											count = rs.getInt(1);
										}
										if (count == 0) {
											String sql = "insert into t_weapon (type_id,org_id,number,standard,sync_state, platform_id) values (?,?,?,?,?,?)";
											try {
												// ps.clearParameters();
												ps = conn.prepareStatement(sql);
												ps.setInt(1,
														getWeaponTypeId(val[1]));
												ps.setInt(2, orgId);
												ps.setString(3, number);
												String standards = val[2] == null ? ""
														: val[2].trim().equals(
																"0.0") ? ""
																: val[2];
												ps.setString(4, standards);
												ps.setBoolean(5, true);
												ps.setInt(6, 1);

												ps.executeUpdate();
												isSuccess = true;
											} catch (SQLException e1) {
												// TODO Auto-generated catch
												// block
												return isSuccess;
											}
										} else {
											regMsg += "第"
													+ (i - 1)
													+ "行数据导入失败，详细：数据库已经存在该编号的武器数据;";
										}

									} catch (SQLException e1) {
										// TODO Auto-generated catch block
										return isSuccess;
									}
								}

								if (regMsg.length() > 0) {
									retMsgList.add(regMsg);
								}
								isSuccess = true;
							}

						}
					}
				} catch (Exception ex) {
					return isSuccess;
				}
				try {
					if (pes != null) {
						pes.cancel();
					}
					if (ps != null) {
						ps.cancel();
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					return isSuccess;
				}
				if (dt != null) {
					dt.discardConnection(conn);
				}
				// conn.close();

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				return isSuccess;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				return isSuccess;
			}
			pc.delete();
		}
		return isSuccess;
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

	private boolean operationGpsDataToDb(int orgId, String path,
			List<String> retMsgList) throws SQLException {// TODO
		// method stub
		boolean isSuccess = false;
		File pc = new File(path);
		// conn = DBClassMysql.getMysql();
		conn = dt.getConnection();
		if (pc.exists()) {
			try {
				HSSFWorkbook wookbook = new HSSFWorkbook(new FileInputStream(
						path));
				// HSSFSheet sheet = wookbook.getSheet("GpsInfo");
				HSSFSheet sheet = wookbook.getSheetAt(0);
				// 获取到Excel文件中的所有行数
				// int rows = sheet.getPhysicalNumberOfRows();

				int minRowIx = sheet.getFirstRowNum();
				int maxRowIx = sheet.getPhysicalNumberOfRows();
				int rows = 0;
				if ((maxRowIx - minRowIx) >= 2) {
					rows = maxRowIx - minRowIx;
				}
				// 只有行大于2的时候，才有数据，前面两行为title栏位
				try {
					if (rows > 0 && rows >= 2) {
						// 遍历行
						for (int i = 2; i <= rows; i++) {
							// 读取左上端单元格?
							HSSFRow row = sheet.getRow(i);
							// 行不为空
							if (row != null) {
								// 获取到Excel文件中的所有的列?
								// int cells = row.getPhysicalNumberOfCells();
								String value = "";
								for (int j = 0; j < 3; j++) {
									// 获取到列的值?
									HSSFCell cell = row.getCell(j);
									if (cell != null) {

										switch (cell.getCellType()) {
										case Cell.CELL_TYPE_BOOLEAN:
											// sb.append(SEPARATOR +
											// cellValue.getBooleanValue());
											value += (cell
													.getBooleanCellValue() + SEPARATOR);
											break;
										case Cell.CELL_TYPE_NUMERIC:
											// 这里的日期类型会被转换为数字类型，需要判别后区分处理
											if (DateUtil
													.isCellDateFormatted(cell)) {
												value += (cell
														.getDateCellValue() + SEPARATOR);
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
											value += ("0.0" + SEPARATOR);
											break;
										default:
											value += ("0.0" + SEPARATOR);
											break;
										}
									} else {
										value += ("0.0" + SEPARATOR);
									}
								}
								String[] val = value.split("\\|");

								String regMsg = "";
								String gpsNumber = val[2] == null ? "" : val[2]
										.trim().equals("0.0") ? "" : val[2];
								if (gpsNumber.equals("")) {
									regMsg += "第" + (i - 1)
											+ "行数据错误，详细：定位设备编号为空;";
								} else {
									try {
										String existSql = "select count(*) from t_gps where number = '"
												+ gpsNumber + "' ";
										pes = conn.prepareStatement(existSql);
										ResultSet rs = pes.executeQuery();
										int count = 0;
										while (rs.next()) {
											count = rs.getInt(1);
										}
										if (count == 0) {
											String sql = "insert into t_gps (type_id,org_id,number,gps_name,icon_url,sync_state, platform_id) values (?,?,?,?,?,?,?)";
											try {
												// ps.clearParameters();
												ps = conn.prepareStatement(sql);
												ps.setInt(1,
														getGpsTypeId(val[0]));
												ps.setInt(2, orgId);
												ps.setString(
														3,
														val[2] == null ? ""
																: val[2].trim()
																		.equals("0.0") ? ""
																		: val[2]);
												ps.setString(
														4,
														val[1] == null ? ""
																: val[1].trim()
																		.equals("0.0") ? ""
																		: val[1]);
												ps.setString(5, "");
												ps.setBoolean(6, true);
												ps.setInt(7, 1);

												ps.executeUpdate();
											} catch (SQLException e1) {
												// TODO Auto-generated catch
												// block
												return isSuccess;
											}

										} else {
											regMsg += "第"
													+ (i - 1)
													+ "行数据导入失败，详细：数据库已经存在该编号的定位设备数据;";
										}

									} catch (SQLException e1) {
										// TODO Auto-generated catch block
										return isSuccess;
									}
								}

								if (regMsg.length() > 0) {
									retMsgList.add(regMsg);
								}
								isSuccess = true;
							}

						}
					}
				} catch (Exception ex) {
					return isSuccess;
				}
				try {
					if (pes != null) {
						pes.cancel();
					}
					if (ps != null) {
						ps.cancel();
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					return isSuccess;
				}
				if (dt != null) {
					dt.discardConnection(conn);
				}

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				return isSuccess;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				return isSuccess;
			}
			pc.delete();
		}
		return isSuccess;

	}

	private boolean operationVehicleDataToDb(int orgId, String path,
			List<String> retMsgList) throws SQLException {
		// TODO Auto-generated method stub
		boolean isSuccess = false;
		File pc = new File(path);
		// conn = DBClassMysql.getMysql();
		conn = dt.getConnection();
		if (pc.exists()) {
			try {
				HSSFWorkbook wookbook = new HSSFWorkbook(new FileInputStream(
						path));
				// HSSFSheet sheet = wookbook.getSheet("VehicleInfo");
				HSSFSheet sheet = wookbook.getSheetAt(0);
				// 获取到Excel文件中的所有行数
				// int rows = sheet.getPhysicalNumberOfRows();

				int minRowIx = sheet.getFirstRowNum();
				int maxRowIx = sheet.getPhysicalNumberOfRows();
				int rows = 0;
				if ((maxRowIx - minRowIx) >= 2) {
					rows = maxRowIx - minRowIx;
				}
				// 只有行大于2的时候，才有数据，前面两行为title栏位
				try {
					if (rows > 0 && rows >= 2) {
						// 遍历行
						for (int i = 2; i <= rows; i++) {
							// 读取左上端单元格?
							HSSFRow row = sheet.getRow(i);
							// 行不为空
							if (row != null) {
								// 获取到Excel文件中的所有的列?
								// int cells = row.getPhysicalNumberOfCells();
								String value = "";
								for (int j = 0; j < 7; j++) {
									// 获取到列的值?
									HSSFCell cell = row.getCell(j);
									if (cell != null) {

										switch (cell.getCellType()) {
										case Cell.CELL_TYPE_BOOLEAN:
											// sb.append(SEPARATOR +
											// cellValue.getBooleanValue());
											value += (cell
													.getBooleanCellValue() + SEPARATOR);
											break;
										case Cell.CELL_TYPE_NUMERIC:
											// 这里的日期类型会被转换为数字类型，需要判别后区分处理
											if (DateUtil
													.isCellDateFormatted(cell)) {
												value += (cell
														.getDateCellValue() + SEPARATOR);
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
											value += ("0.0" + SEPARATOR);
											break;
										default:
											value += ("0.0" + SEPARATOR);
											break;
										}
									} else {
										value += ("0.0" + SEPARATOR);
									}
								}
								String[] val = value.split("\\|");

								String regMsg = "";
								String vehicleNumber = val[1] == null ? ""
										: val[1].trim().equals("0.0") ? ""
												: val[1];
								if (vehicleNumber.equals("")) {
									regMsg += "第" + (i - 1)
											+ "行数据错误，详细：车牌号码为空;";
								} else {
									try {
										String existSql = "select count(*) from t_vehicle where number = '"
												+ vehicleNumber + "' ";
										pes = conn.prepareStatement(existSql);
										ResultSet rs = pes.executeQuery();
										int count = 0;
										while (rs.next()) {
											count = rs.getInt(1);
										}
										if (count == 0) {
											String sql = "insert into t_vehicle (vehicle_type_id,org_id,number,intercom_group,intercom_person,purpose,brand,site_qty,sync_state, platform_id) values (?,?,?,?,?,?,?,?,?,?)";
											try {
												// ps.clearParameters();
												ps = conn.prepareStatement(sql);
												ps.setInt(
														1,
														getVehicleTypeId(val[0]));
												ps.setInt(2, orgId);
												ps.setString(
														3,
														val[1] == null ? ""
																: val[1].trim()
																		.equals("0.0") ? ""
																		: val[1]);
												ps.setString(
														4,
														val[5] == null ? ""
																: val[5].trim()
																		.equals("0.0") ? ""
																		: val[5]);
												ps.setString(
														5,
														val[6] == null ? ""
																: val[6].trim()
																		.equals("0.0") ? ""
																		: val[6]);
												ps.setString(
														6,
														val[2] == null ? ""
																: val[2].trim()
																		.equals("0.0") ? ""
																		: val[2]);
												ps.setString(
														7,
														val[3] == null ? ""
																: val[3].trim()
																		.equals("0.0") ? ""
																		: val[3]);
												ps.setString(
														8,
														val[4] == null ? ""
																: val[4].trim()
																		.equals("0.0") ? ""
																		: val[4]);
												ps.setBoolean(9, true);
												ps.setInt(10, 1);

												ps.executeUpdate();
											} catch (SQLException e1) {
												// TODO Auto-generated catch
												// block
												return isSuccess;
											}
										} else {
											regMsg += "第"
													+ (i - 1)
													+ "行数据导入失败，详细：数据库已经存在车牌号的车辆数据;";
										}

									} catch (SQLException e1) {
										// TODO Auto-generated catch block
										return isSuccess;
									}
								}

								if (regMsg.length() > 0) {
									retMsgList.add(regMsg);
								}
								isSuccess = true;
							}

						}
					}
				} catch (Exception ex) {
					return isSuccess;
				}
				try {
					if (pes != null) {
						pes.cancel();
					}
					if (ps != null) {
						ps.cancel();
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					return isSuccess;
				}
				if (dt != null) {
					dt.discardConnection(conn);
				}
				// conn.close();

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				return isSuccess;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				return isSuccess;
			}
			pc.delete();
		}
		return isSuccess;
	}

	private boolean operationPoliceDataToDb(Integer orgId, String path,
			List<String> retMsgList) throws SQLException {
		// TODO Auto-generated method stub
		boolean isSuccess = false;
		File pc = new File(path);
		// conn = DBClassMysql.getMysql();
		conn = dt.getConnection();
		if (pc.exists()) {
			try {
				HSSFWorkbook wookbook = new HSSFWorkbook(new FileInputStream(
						path));
				// HSSFSheet sheet = wookbook.getSheet("PoliceInfo");
				HSSFSheet sheet = wookbook.getSheetAt(0);
				// 获取到Excel文件中的所有行数
				// int rows = sheet.getPhysicalNumberOfRows();

				int minRowIx = sheet.getFirstRowNum();
				int maxRowIx = sheet.getPhysicalNumberOfRows();
				int rows = 0;
				if ((maxRowIx - minRowIx) >= 2) {
					rows = maxRowIx - minRowIx;
				}
				// 只有行大于2的时候，才有数据，前面两行为title栏位
				try {
					if (rows > 0 && rows >= 2) {
						// 遍历行
						for (int i = 2; i <= rows; i++) {
							// 读取左上端单元格?
							HSSFRow row = sheet.getRow(i);
							// 行不为空
							if (row != null) {
								// 获取到Excel文件中的所有的列?
								// int cells = row.getLastCellNum();
								String value = "";
								for (int j = 0; j < 9; j++) {
									// 获取到列的值?
									HSSFCell cell = row.getCell(j);
									if (cell != null) {

										switch (cell.getCellType()) {
										case Cell.CELL_TYPE_BOOLEAN:
											// sb.append(SEPARATOR +
											// cellValue.getBooleanValue());
											value += (cell
													.getBooleanCellValue() + SEPARATOR);
											break;
										case Cell.CELL_TYPE_NUMERIC:
											// 这里的日期类型会被转换为数字类型，需要判别后区分处理
											if (DateUtil
													.isCellDateFormatted(cell)) {
												value += (cell
														.getDateCellValue() + SEPARATOR);
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
											value += ("0.0" + SEPARATOR);
											break;
										default:
											value += ("0.0" + SEPARATOR);
											break;
										}
									} else {
										value += ("0.0" + SEPARATOR);
									}
								}
								String[] val = value.split("\\|");
								String regMsg = "";
								String policeName = val[0] == null ? ""
										: val[0].trim().equals("0.0") ? ""
												: val[0];
								String idCardNo = (val[3] == null) ? ""
										: (val[3].trim().equals("0.0")) ? ""
												: val[3];
								String policeCode = val[2] == null ? ""
										: val[2].trim().equals("0.0") ? ""
												: val[2];
								String policeMobile = val[5] == null ? ""
										: val[5].trim()
										.equals("0.0") ? ""
										: val[5];
								
								Pattern pattern = Pattern.compile("[0-9]*");  
								if (policeName.equals("")) { 
									regMsg += "第" + (i - 1) + "行数据错误，详细：姓名为空;";
								} else if (idCardNo.equals("")) { 
									regMsg += "第" + (i - 1) + "行数据错误，详细：身份证号码为空";
								} else if(idCardNo.length()!=15&&idCardNo.length()!=18){
									regMsg += "第" + (i - 1) + "行数据错误，详细：身份证号码长度错误";
								} else if (policeCode.equals("")) {
									regMsg += "第" + (i - 1) + "行数据错误，详细：警号为空";
								} else if(policeCode.length()>8){
									regMsg += "第" + (i - 1) + "行数据错误，详细：警号长度错误，超过长度";
								}else if(policeMobile.trim().length()>13){
										regMsg += "第" + (i - 1) + "行数据错误，详细：电话号码格式错误，长度过长;";
								}else if(policeMobile.trim().length() > 0 && !pattern.matcher(policeMobile.trim()).matches()){ 
										regMsg += "第" + (i - 1) + "行数据错误，详细：电话号码格式错误，只能为数字;"; 
								}else if(!pattern.matcher(policeCode.trim()).matches()){
										regMsg += "第" + (i - 1) + "行数据错误，详细：警号格式错误，只能为数字";
								}else if(!pattern.matcher(idCardNo.trim().substring(0, idCardNo.trim().length()-1)).matches()){
										regMsg += "第" + (i - 1) + "行数据错误，详细：身份证格式错误，只有最后一位可为非数字";
								}else {
									try {
										String existSql = "select count(*) from t_police where idcardNo = '"
												+ idCardNo
												+ "' or number = '"
												+ policeCode + "' ";
										pes = conn.prepareStatement(existSql);
										ResultSet rs = pes.executeQuery();
										int count = 0;
										while (rs.next()) {
											count = rs.getInt(1);
										}
										if (count == 0) {
											String sql = "insert into t_police (type_id,name,org_id,idcardNo,number,title,mobile,mobile_short,intercom_group,intercom_person,gps_name,isUsed,sync_state, platform_id) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
											try {
												// ps.clearParameters();
												ps = conn.prepareStatement(sql);
												ps.setInt(1, 1);
												ps.setString(2, policeName);
												ps.setInt(3, orgId);
												ps.setString(4, idCardNo);
												ps.setString(5, policeCode);
												ps.setString(
														6,
														val[4] == null ? ""
																: val[4].trim()
																		.equals("0.0") ? ""
																		: val[4]);
												ps.setString(
														7,
														val[5] == null ? ""
																: val[5].trim()
																		.equals("0.0") ? ""
																		: val[5]);
												ps.setString(
														8,
														val[6] == null ? ""
																: val[6].trim()
																		.equals("0.0") ? ""
																		: val[6]);
												ps.setString(
														9,
														val[7] == null ? ""
																: val[7].trim()
																		.equals("0.0") ? ""
																		: val[7]);
												ps.setString(
														10,
														val[8] == null ? ""
																: val[8].trim()
																		.equals("0.0") ? ""
																		: val[8]);
												ps.setString(11, null);
												ps.setBoolean(12, true);
												ps.setBoolean(13, true);
												ps.setInt(14, 1);

												ps.executeUpdate();
											} catch (SQLException e1) {
												// TODO Auto-generated catch
												// block
												return isSuccess;
											}
										} else {
											regMsg += "第"
													+ (i - 1)
													+ "行数据导入失败，详细：数据库已经存在身份证号或者警号的警员数据;";
										}

									} catch (SQLException e1) {
										// TODO Auto-generated catch block
										return isSuccess;
									}
									 
								}
								if (regMsg.length() > 0) {
									retMsgList.add(regMsg);
								}
								isSuccess = true;
							}

						}
					}
				} catch (Exception ex) {
					return isSuccess;
				}
				try {
					if (ps != null) {
						ps.cancel();
					}
					if (pes != null) {
						pes.cancel();
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					return isSuccess;
				}
				if (dt != null) {
					dt.discardConnection(conn);
				}
				// conn.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				return isSuccess;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				return isSuccess;
			}
			pc.delete();
		}
		return isSuccess;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doPost(req, resp);
	}
}
