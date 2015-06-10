<%@ page language="java"  import="java.util.*"  pageEncoding="UTF-8"%>
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
<base href="<%=basePath%>">  
<title>扁平化指挥系统</title>
<meta
	content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no'
	name='viewport' />

<%@ include file="../../emulateIE.jsp" %>
<script type="text/javascript">
var sessionId = $("#token").val();

var fileUrl ="";
$(function() {
	var retMsg = $("#retMsg").html(); 
	if(retMsg.length>0){ 
		$("body").popjs({"title":"提示","content":retMsg,"callback":function(){
			window.parent.window.parent.IconManage.onClose();
			window.parent.$("#dialog").tyWindow.close();
		}});   
	}
	$("#iconsType").kendoComboBox({
		dataTextField : "name",
		dataValueField : "id",
		dataSource : {
			serverFiltering : true,
			transport : {
				read : {
					url : "<%=basePath%>iconsWeb/getIconType.do?sessionId="+sessionId,
					dataType : "json"
				}
			}
		},
		filter : "contains",
		suggest : true,
		index : 0
	}).prev().find(".k-input").attr("readonly",true);
});
function getUrlArgs() {
	var url = decodeURI(location.search);
	// var url = location.search; //获取url中"?"符后的字串
	var theRequest = new Object();
	if (url.indexOf("?") != -1) {
		var str = url.substr(1);
		strs = str.split("&");
		for ( var i = 0; i < strs.length; i++) {
			theRequest[strs[i].split("=")[0]] = unescape(strs[i].split("=")[1]);

		}
	}
	return theRequest;
}

function iconFormSubmit(){
	var iconTypeId=$("#iconsType").val();
	if(iconTypeId==0){
		$("body").popjs({"title":"提示","content":"请选择图片类型"}); 
		return;
	}
	var iconname = $("#iconsName").val();
	if(iconname==""||$.trim(iconname)==""){
		$("body").popjs({"title":"提示","content":"请输入图片名称","callback":function(){
								$("#iconsName").focus();
								return; 
							}});  
		return; 
	}
	if(fileUrl == undefined ||fileUrl ==""){
		$("body").popjs({"title":"提示","content":"请选择图标进行上传","callback":function(){ 
								return; 
							}});  
		return; 
	}
	
	$('#iconSubForm').submit();
}
function checkType(e){
	 var src=e.target || window.event.srcElement;

	var filepath=src.value;
	fileUrl = filepath;
	var files =src.files; 
	
	//$("#upfile").val(fileUrl);
	$("#fileUpload").val(fileUrl);
	filepath=filepath.substring(filepath.lastIndexOf('.')+1,filepath.length);
	if(filepath != 'png'){
		$("body").popjs({"title":"提示","content":"只能上传png格式图标","callback":function(){
								src = null;
								fileUrl = "";
								return ;
							}});   
								return ;
	} 

}
</script>
</head>

<body class="ty-body">
<form id="iconSubForm" method="post" enctype="multipart/form-data"  action="<%=basePath %>servlet/FileUploadServlet" >  
     <div id="horizontal" style="height: 220px;">
			<div class="pane-content">
				<!-- 左开始 -->
				<div class="demo-section k-header"> 
					<ul>
						<li><span class="ty-input-warn">*</span><label for="iconsType">图片类型:</label><input id="iconsType"  name="iconsType"
							placeholder="请选择图片类型..." /><input id="iconsId" value="0"  name="iconsId" type="hidden" />
							<input id="sessionId" value="${requestScope.sessionId}"  name="sessionId" type="hidden" />
							</li>
						<li><span class="ty-input-warn">*</span><label for="iconsName">图标名称:</label><input
							type="text" class="k-textbox" name="iconsName"
							id="iconsName"  /></li>
						<li><span class="ty-input-warn">*</span><label for="iconUrl">图标选择:</label>
							<input type="file" class="ty-file" onchange="checkType(event);" id="fileName" name="fileName" />
							<input type="text" class="k-textbox" id="fileUpload" readonly="readonly"/><button class="ty-upfile" onmousemove="document.getElementById('fileName').style.top=(event.clientY-10)+'px';document.getElementById('fileName').style.left= (event.clientX)+'px';">选择</button>
						</li>
					</ul>
					<p style="font-size:10px;">请选择png格式图片进行上传，图标尺寸限制1Mb；</p>
					<p id="retMsg" style="display:none">${requestScope.uploadError}</p> 
					<p class="ty-input-row">  	
					 <button id="submit" class="ty-button" onClick="iconFormSubmit();" >确定</button>  
					</p>
				</div>
				
			</div>
		</div> 
 </form>   
</body>
</html>
