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
<title>角色管理</title>
<%@ include file="../../emulateIE.jsp" %>	
</head>

<body>
  <div id="wrapper">
    	<input id="token" type="hidden" value="${requestScope.sessionId}" style="width:80px;"/>
       <div class="container-fluid">
         <div id="content-wrapper" class="row-fluid">
           <div class='span12'>
           
             <div class="row-fluid"><!----功能模块---->
               <div class="set">
                 <h1>角色管理</h1>
                 <div class="clear box">
                 <%@ include file="roleSearch.jsp" %> 
                 </div>
               </div>
             </div><!----功能模块结束---->
           	
             <div class="row-fluid"><!----信息显示区---->
               <div class="span8 box"><!----表格---->
               <%@ include file="roleListInfo.jsp" %> 
               </div><!----表格结束---->
               <div class="span4 box"><!----权限显示---->
                  <%@ include file="roleModule.jsp" %>
               </div><!----权限显示结束---->
             </div><!----信息显示区结束---->
             
           </div>
         </div>
       </div>
  </div>
  

</body>
</html>
