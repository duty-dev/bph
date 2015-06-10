<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<META NAME="Generator" CONTENT="EditPlus">
  <META NAME="Author" CONTENT="">
  <META NAME="Keywords" CONTENT="">
  <META NAME="Description" CONTENT="">
  
<link rel="stylesheet" type="text/css" href="<%=path %>/Skin/Default/css/global.css"/>
<link rel="stylesheet" type="text/css" href="<%=path %>/Skin/Default/css/base.css"/>
<script src="<%=basePath %>resources/js/jquery.min.js" type='text/javascript'></script>

<!-- / kendo -->
<link href="<%=basePath %>resources/css/web/kendo.common.min.css" media='all' rel='stylesheet' type='text/css' />
<link href="<%=basePath %>resources/css/web/kendo.default.min.css" media='all' rel='stylesheet' type='text/css' />
<link href="<%=basePath %>resources/css/dataviz/kendo.dataviz.min.css" media='all' rel='stylesheet' type='text/css' />
<link href="<%=basePath %>resources/css/dataviz/kendo.dataviz.default.min.css" media='all' rel='stylesheet' type='text/css' />		
<script src="<%=basePath %>resources/js/jquery.min.js" type='text/javascript'></script>
<script src="<%=basePath %>resources/js/kendo.all.min.js" type='text/javascript'></script>
<script src="<%=basePath %>JS/extend/pop.js" type='text/javascript'></script>
<title>系统后台管理登录认证</title>
<script type='text/javascript'>
var rootPath = '<%=path %>';
</script>
<script src="<%=basePath %>JS/admin/adminLogin.js" type='text/javascript'></script>
</head>

<body class="login">
  <div class="logo1"></div>
  
  <div class="close-area"><!----右上关闭样式组---->
    <div>
      <div class="login-close2"></div>
      <div class="login-close" onclick="closeWin()"></div>
      <div class="login-close1"></div>
    </div>
    <div>
      <div class="login-close3"></div>
      <div class="login-close4"></div>
    </div>
  </div><!----右上关闭样式组结束---->
  
   <div class="box"><!----登录样式---->
     <div class="h114">
       <div class="login-line1 pull-left"></div>
       <div class="login-line1 pull-right"></div>
     </div>
     <div class="logo2"></div>
     <div class="login-title"></div>
     <form id="loginForm" action="<%=path %>/adminUser/login.action" method="post">
       <input type="text" class="login-input" name="username" id="username">
       <input type="password" class="login-input" name="password" id="password">
       <button type="button" class="button-red" id="subBtn" onclick="submitBtn(this,1);">登录认证</button>
     </form>
     <input type="hidden" name="errMsg" id="errMsg" value="${errMsg}">
     <div id="alarmdialog"></div>
     <c:if test="${errMsg != null}">
     <script>
    //showMsg();
    $("#alarmdialog").tyWindow({
    	draggable: false,
    	title: "告警提示",
    	height: "300px",
    	width: "400px",
    	position: {
    	    top: "30%"
    	  }
    	});
    var dialog = $("#alarmdialog").tyWindow();
    var errMsg = $("#errMsg").val();
    dialog.content(errMsg);
    </script>
     </c:if>
  </div><!----登录样式结束---->
</body>
</html>

