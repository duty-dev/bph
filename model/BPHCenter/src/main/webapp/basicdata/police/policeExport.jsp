﻿<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>


<!DOCTYPE html>
<html>
<head>
<title>扁平化指挥系统</title>
<meta
	content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no'
	name='viewport' />

<%@ include file="../../emulateIE.jsp" %>
<script  type="text/javascript">
var fileUrl ="";
var policeImportManage = {
		downLoadModel:function(){
			var urlStr = "<%=basePath %>excelModel/PoliceInfo.xls"; 
			window.location.href = urlStr;
		}  ,
		fileFormSubmit:function(){ 
			if(fileUrl == undefined ||fileUrl==""){
			$("body").popjs({"title":"提示","content":"请选择文件进行上传","callback":function(){ 
								return; 
							}});  
				return; 
			}
			$('#policeimportForm').submit();
		}
};

function checkType(e){
	 var src=e.target || window.event.srcElement;

	var filepath=src.value;
	fileUrl = filepath;
	var files =src.files; 
	
	filepath=filepath.substring(filepath.lastIndexOf('.')+1,filepath.length)
	if(filepath != 'xls'){
		$("body").popjs({"title":"提示","content":"只能上传97--2003版本格式文件，后缀名为.xls","callback":function(){
								src.value="";
								fileUrl = "";
								return;
							}}); 
			return;  
	} 

}
</script>
</head>
<body>
	<form id="policeimportForm" method="post" enctype="multipart/form-data"
		action="<%=basePath %>servlet/ExcelImportServlet">
		<div id="vertical">
			<div id="horizontal" style="height: 300px; width: 400px;">
				<div class="pane-content">
					<!-- 左开始 -->
					<div class="demo-section k-header">
						<p>
							<input id="orgId" name="orgId" type="hidden" value="${organ.id}" />
							<input id="dataType" name="dataType" value="policeData" type="hidden"/>
							<input id="sessionId" value="${requestScope.sessionId}"  name="sessionId" type="hidden" />
							<input type="file"  onchange="checkType(event)"  id="fileName"  style="width:180px" name="fileName" text="选择文件上传" /> <a
								href="<%=basePath %>excelModel/PoliceInfo.xls" style="font-size:12px;color:#819f0;"
								onclick="">[点击下载excel模板]</a>
						</p> <label style="font-size:12px;color:#819f0;">上传文件时，请先下载模板文件填写
							；</label><br /> <label style="font-size:12px;color:#819f0;">文件对象为：97—03版本的excel文件，后缀格式为xls；</label>
							<br /> <label style="font-size:12px;color:#819f0;">文件大小为：1Mb；</label>
					 <p>  	
					 	<span id="submit" class="k-button"  onClick="policeImportManage.fileFormSubmit();" >开始导入</span> 
					</p> 
					 <p style="color: red">${requestScope.uploadError}</p> 
					 
					</div>
				</div>
			</div>
		</div>
	</form>
</body>
</html>