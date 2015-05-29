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
$(function() {
	$("#dutyTaskgrid").empty();
	DutyTaskManage.loadData();
});
var DutyTaskManage ={
	loadData:function(){
		alert("加载关联任务列表");
	},
	saveDutyTask:function(){
		alert("关联任务成功");
	}
};
</script>
</head>
<body> 
	<div id="dutyTaskgrid" style="width:150%"></div>  
	<p style="float:left;width:100%;margin-top:10px;"> 
		<button id="undo" class="ty-button" onclick="DutyTaskManage.saveDutyTask()">确定</button>
	</p>  
</body> 
</html>