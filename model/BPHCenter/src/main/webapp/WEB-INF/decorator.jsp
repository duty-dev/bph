<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.Map"%>
<%@ page import="com.tianyi.bph.common.Pager"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
 
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>扁平化指挥系统</title>
<meta content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no' name='viewport' />
	
<link rel="stylesheet" type="text/css" href="<%=basePath %>Skin/Default/css/light-theme.css">
<link rel="stylesheet" type="text/css" href="<%=basePath %>Skin/Default/css/bootstrap/bootstrap-responsive.css">
<link rel="stylesheet" type="text/css" href="<%=basePath %>Skin/Default/css/bootstrap/bootstrap.css">
<link rel="stylesheet" type="text/css" href="<%=basePath %>Skin/Default/css/base.css">
<link rel="stylesheet" type="text/css" href="<%=basePath %>Skin/Default/css/global.css">
<!-- / kendo -->
<link href="<%=basePath %>resources/css/dataviz/kendo.common-material.min.css" media='all' rel='stylesheet' type='text/css' />	
<link href="<%=basePath %>resources/css/dataviz/kendo.dataviz.material.min.css" media='all' rel='stylesheet' type='text/css' />	
<link href="<%=basePath %>resources/css/dataviz/kendo.dataviz.min.css" media='all' rel='stylesheet' type='text/css' />	
<link href="<%=basePath %>resources/css/dataviz/kendo.materialblack.min.css" media='all' rel='stylesheet' type='text/css' />	
<link href="<%=basePath %>resources/css/dataviz/kendo.material.mobile.min.css" media='all' rel='stylesheet' type='text/css' />	
<link href="<%=basePath %>resources/css/dataviz/kendo.rtl.min.css" media='all' rel='stylesheet' type='text/css' />
<link href="<%=basePath %>JS/extend/jquery.mCustomScrollbar.css" rel='stylesheet' type='text/css' />	
<script src="<%=basePath %>resources/js/jquery.min.js" type='text/javascript'></script>
<script src="<%=basePath %>resources/js/kendo.all.min.js" type='text/javascript'></script>
<script src="<%=basePath %>JS/json2.js" type='text/javascript'></script>
<script src="<%=basePath %>JS/extend/kendo.messages.zh-CN.js" type='text/javascript'></script>
<script src="<%=basePath %>JS/extend/jquery.mCustomScrollbar.concat.min.js" type='text/javascript'></script>
<script src="<%=basePath %>JS/extend/pop.js" type='text/javascript'></script>
<script src="<%=basePath %>JS/extend/ty-window.js" type='text/javascript'></script>
<script src="<%=basePath %>JS/extend/checkException.js" type='text/javascript'></script>
<script src="<%=basePath %>JS/user/pager.js" type='text/javascript'></script>
<!--[if lte IE 9]>
<script src="<%=basePath %>JS/extend/html5shiv.js" type='text/javascript'></script>
<script src="<%=basePath %>JS/extend/respond.js" type='text/javascript'></script>
<![endif]-->
<input id="token" type="hidden" value="${requestScope.sessionId}"/>
<input id="expandeds" type="hidden" value="${requestScope.expandeds}"/>
<input  id="organId" type="hidden" value="${requestScope.organId}"/>
<input  id="organPath" type="hidden" value="${requestScope.organPath}"/>
</head>

<body>
   <header>
    <%@ include file="../header.jsp" %>
  </header>
  
  <div class="clear"></div>
  
  <sitemesh:write property='body'/>

<div class="clear"></div>
  <div><!----底部---->
    <%@ include file="../../footer.jsp" %>
  </div><!----底部结束---->
  <div class="clear"></div>
</body>

</html>

