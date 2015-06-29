<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>


<!DOCTYPE html>
<html>
<head>
<title>扁平化指挥系统</title>
<%@ include file="../../../emulateIE.jsp" %>	
</head>

<body>
<div id="wrapper">
    	<input id="token" type="hidden" value="${requestScope.sessionId}" style="width:80px;"/><br>
       <div class="container-fluid">
         <div id="content-wrapper" class="row-fluid">
           <div class='span12'>
             <div class="row-fluid"><!----功能模块---->
               <div class="set">
                 <div class="clear box">
                 <%@ include file="alarmFunction.jsp" %> 
                 </div>
               </div>
             </div><!----功能模块结束---->
           	
             <div class="row-fluid"><!----信息显示区---->
               <div class="span8 box"  id="mapdiv"><!----表格---->
                <%@ include file="nomalAlarmInfo.jsp" %>
               </div><!----表格结束---->
             </div><!----信息显示区结束---->
             
           </div>
         </div>
       </div>
  </div>
</body>
</html>
