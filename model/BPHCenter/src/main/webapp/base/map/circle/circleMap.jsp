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
    <div id='main-nav-bg'></div>
    <nav class="" id="main-nav">
      <div class='navigation'> 
        <%@ include file="left.jsp" %>
      </div>
    </nav>
    
    <section id='content'>
       <div class="container-fluid">
         <div id="content-wrapper" class="row-fluid">
           <div class='span12'>
             <div class="row-fluid"><!----信息显示区---->
          <%--      <%@ include file="mapInfoOfClound.jsp"%>  --%>
          <%@include file="circleInfo.jsp" %>
             </div><!----信息显示区结束---->
             <div class="clear"></div>
           </div>
         </div>
       </div>
     </section>
    
  </div>
<!-- / jquery -->
</body>
</html>
