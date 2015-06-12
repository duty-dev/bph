package com.tianyi.bph.web.controller.basicdata;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
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
import org.springframework.web.context.ContextLoader;

import com.alibaba.druid.pool.DruidDataSource;
import com.tianyi.bph.domain.basicdata.Icons;
import com.tianyi.bph.domain.system.IconGroup;

@SuppressWarnings("serial") 
public class FileUploadServlet extends HttpServlet {

	 
	public void init(ServletConfig config) throws ServletException {

		super.init(config);

	}
	private final com.alibaba.druid.pool.DruidDataSource dt = ContextLoader
			.getCurrentWebApplicationContext().getBean(DruidDataSource.class);
	// private Connection conn = null;
	private Connection conn = null;

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {


		PreparedStatement ps = null;
		int iconId = 0;
		Integer icomTemp = 0;
		String nomalUrl = null;
	    String selectedUrl = null;
	    String intoEnclosureUrl = null;
	    String dispatchUrl = null;
	    String arriveUrl = null;
	    
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
			request.getRequestDispatcher("/admin/icons/iconsAdd.jsp?optType=0").forward(request,
					response);
			return;
		} catch (FileUploadBase.InvalidContentTypeException e) {
			// 无效的请求类型,即请求类型enctype != "multipart/form-data"
			request.setAttribute("uploadError",
					"请求类型enctype != multipart/form-data");
			request.getRequestDispatcher("/admin/icons/iconsAdd.jsp?optType=1").forward(request,
					response);
			return;
		} catch (FileUploadException e) {
			// 如果都不是以上子异常,则抛出此总的异常,出现此异常原因无法说明.
			request.setAttribute("uploadError", "上传过程异常，导致其原因可能是磁盘已满或者其它原因");
			request.getRequestDispatcher("/admin/icons/iconsAdd.jsp?optType=2").forward(request,
					response);
			return;
		}
		if (itemList != null) {
			Iterator it = itemList.iterator();
			IconGroup iconGroup = new IconGroup();
			while (it.hasNext()) {
				FileItem item = (FileItem) it.next();
				if (item.isFormField()) {
					// 非文件流
					String value = item.getString();
					//value = new String(value.getBytes("ISO-8859-1"), "UTF-8");

					if (item.getFieldName().equals("iconType")) {
						if (value.isEmpty() || value.equals("")
								|| value.equals("0")) {
							request.setAttribute("uploadError", "请选择图片类型");
							request.getRequestDispatcher

							("/admin/icons/iconsAdd.jsp?optType=3").forward(request, response);
							return;
						} else {
							iconGroup.setIconType(Integer.parseInt(value));
						}
					}

					if (item.getFieldName().equals("groupName")) {
						if (value.isEmpty() || value.equals("")) {
							request.setAttribute("uploadError", "请输入图标名称");
							request.getRequestDispatcher

							("/admin/icons/iconsAdd.jsp?optType=4").forward(request, response);
							return;
						} else {
							iconGroup.setGroupName(value);
						}
					}

//					if (item.getFieldName().equals("iconsId")) {
//						if (value.equals("0")) {
//							icons.setId(0);
//						} else {
//							icons.setId(Integer.parseInt(value));
//						}
//					}
					// System.out.println(value);
					// System.out.println(value);
				} else {
					String uploadPath = request.getRealPath("uploadIcon");
					String totalName = item.getName();
					System.out.println("totalName="+totalName);
					if (totalName != "") {
//						int index = totalName.lastIndexOf("\\");
//						name = totalName.substring(index + 1);
//						name = new String(name.getBytes("ISO-8859-1"), "UTF-8");	
						if(icomTemp == 0){
							nomalUrl = totalName;
							System.out.println(nomalUrl);
						}
						if(icomTemp == 1){
							selectedUrl = totalName;
							System.out.println(selectedUrl);
						}
						if(icomTemp == 2){
							intoEnclosureUrl = totalName;
							System.out.println(intoEnclosureUrl);
						}
						if(icomTemp == 3){
							dispatchUrl = totalName;
							System.out.println(dispatchUrl);
						}
						if(icomTemp == 4){
							arriveUrl = totalName;
							System.out.println(arriveUrl);
						}
					} else {
						totalName = "temp";
					}

					String path = uploadPath + "/" + totalName; 
					//String sql = "insert into t_icon (type_id,name,icon_url,sync_state, platform_id) values (?,?,?,?,?)";	
					
					try {
						item.write(new File(path));
						BufferedImage bi = ImageIO.read(new File(path));
						if(bi == null){ 
							request.setAttribute("uploadError", "上传文件格式出错，不是png格式的图片文件，请重新选择文件上传");
							request.getRequestDispatcher

							("/admin/icons/iconsAdd.jsp?optType=5").forward(request, response);
							return;
						}else{

//							List<String> s = new ArrayList<String>();
//							s.add("zhangsan");
//							s.add("zhangsan");
//							s.add("zhangsan");
//							s.add("zhangsan");
							request.setAttribute("uploadError", "图片上传成功"); 
							icomTemp = icomTemp+1;	
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						request.setAttribute("uploadError", "写入磁盘错误");
						request.getRequestDispatcher

						("/admin/icons/iconsAdd.jsp?optType=5").forward(request, response);
						return;
					}	
				}
			}
			try {
				System.out.println("");
				String sql = "insert into t_icon_group ( GROUP_ID, GROUP_NAME, ICON_TYPE, NOMAL_URL, SELECTED_URL, INTO_ENCLOSURE_URL, DISPATCH_URL, ARRIVE_URL) values (?,?,?,?,?,?,?,?)";	
				conn = dt.getConnection();
				ps = conn.prepareStatement(sql);
				ps.setInt(1, 0);
				ps.setString(2, iconGroup.getGroupName());
				ps.setInt(3, iconGroup.getIconType());
				ps.setString(4, "uploadIcon/" + nomalUrl);
				ps.setString(5, "uploadIcon/" + selectedUrl);
				ps.setString(6, "uploadIcon/" + intoEnclosureUrl);
				ps.setString(7, "uploadIcon/" + dispatchUrl);
				ps.setString(8, "uploadIcon/" + arriveUrl);
				iconId = ps.executeUpdate();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				request.setAttribute("uploadError", "写入数据库出错");
				request.getRequestDispatcher

				("/admin/icons/iconsAdd.jsp?optType=5").forward(request, response);
				return;
			}
			if (dt != null) {
				dt.discardConnection(conn);
			}
		} else {
			request.setAttribute("uploadError", "请选择要上传的图标！");
			request.getRequestDispatcher("/admin/icons/iconsAdd.jsp?optType=6").forward(request,
					response);
			return;
		} 
		request.getRequestDispatcher("/admin/icons/iconsAdd.jsp").forward(request,
				response);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doPost(req, resp);
	}
}
