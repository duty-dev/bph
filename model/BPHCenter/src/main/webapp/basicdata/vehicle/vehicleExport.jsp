<%@ page language="java" pageEncoding="UTF-8"%>
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
<script type="text/javascript">
var sessionId = $("#token").val();
var fileUrl = "";
$(function() {
	var retMsg = $.trim($("#retMsgList").html());
	if(retMsg.length>4){
		$("body").popjs({"title":"提示","content":retMsg,"callback":function(){
			window.parent.window.parent.VehicleManage.onClose();
			window.parent.$("#dialog").tyWindow.close();
		}});   
	}  
});
var vehicleImportManage = {
		downLoadModel:function(){
			var urlStr = "<%=basePath %>excelModel/VehicleInfo.xls"; 
			window.location.href = urlStr;
		} ,
		fileFormSubmit:function(){ 
			if(fileUrl == undefined ||fileUrl ==""){
			$("body").popjs({"title":"提示","content":"请选择文件进行上传","callback":function(){ 
								return; 
							}});  
				return; 
			}
			$('#vehicleimportForm').submit();
		}
};

function checkType(e){
	 var src=e.target || window.event.srcElement;

	var filepath=src.value;
	fileUrl = filepath;
	var files =src.files;
	
	$("#fileUpload").val(fileUrl);
	filepath=filepath.substring(filepath.lastIndexOf('.')+1,filepath.length);
	if(filepath != 'xls'){
		$("body").popjs({"title":"提示","content":"只能上传97--2003版本格式文件，后缀名为.xls","callback":function(){
								src.value="";
								fileUrl = "";
								return;
							}});   
							return;
	}
	//var filesize = files[0].size;
	//var sizeKb =Math.round(filesize/1024);
	//if(sizeKb>1024){
	//	$("body").popjs({"title":"提示","content":"文件尺寸过大，只能选择1Mb以内的文件","callback":function(){
	//							src.value=""; 
	//							fileUrl = "";
	//							return;
	//						}});  
	//				return;
	//}

}
</script>
</head>
<body class="ty-body">
	<form id="vehicleimportForm" method="post" enctype="multipart/form-data"
		action="<%=basePath %>servlet/ExcelImportServlet">
		<div id="vertical">
			<div id="horizontal" style="height: 300px; width: 400px;">
				<div class="pane-content">
					<!-- 左开始 -->
					<div class="demo-section k-header">
						<p>
							<input id="orgId" name="orgId" type="hidden" value="${organ.id}" />
							<input id="dataType" name="dataType" value="vehicleData" type="hidden"/>
							<input type="file" onchange="checkType(event)"  id="fileName"   name="fileName" class="ty-file" text="选择文件上传" /> 
							<input type="text" class="k-textbox" id="fileUpload" readonly="readonly"/><button class="ty-upfile" onmousemove="document.getElementById('fileName').style.top=(event.clientY-10)+'px';document.getElementById('fileName').style.left= (event.clientX)+'px';">选择</button>
							<a href="<%=basePath %>excelModel/VehicleInfo.xls" style="font-size:12px;color:#819f0;" onclick="">[点击下载excel模板]</a>
						</p> <label style="font-size:12px;color:#819f0;">上传文件时，请先下载模板文件填写
							；</label><br /> <label style="font-size:12px;color:#819f0;">文件对象为：97—03版本的excel文件，后缀格式为xls；</label>
							<br /> <label style="font-size:12px;color:#819f0;">文件大小为：1Mb以内；</label>
					 <p>  	
					 	<span id="submit" class="k-button"  onClick="vehicleImportManage.fileFormSubmit();" >开始导入</span> 
					</p> 
					 <p id="retMsgList" style="display:none">
						${requestScope.uploadError}  <br />
					 	 <c:forEach var="item" items="${requestScope.uploadlist}">
					 	 	${item} <br />
					 	 </c:forEach> 
					 </p>
					</div>
				</div>
			</div>
		</div>
	</form>
</body>
</html>
