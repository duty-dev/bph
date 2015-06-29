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
<%@ include file="../../emulateIE.jsp" %>	 
</head>  
  <body id="bd_prepare">
    <div id="wrapper">
		
			<div class="container-fluid">
				<div id="content-wrapper" class="row-fluid">
					<div class='span12'>

						<div class="row-fluid">
							<!----功能模块---->
							<div class="set">
								<h1>勤务报备</h1> 
								<div class="clear box">
									<%@ include file="dutyToolbar.jsp"%>
								</div>
							</div>
						</div>
						<!----功能模块结束---->

						<div class="row-fluid">
							<!----信息显示区---->
							<div class="span12 box">
								<!----表格---->
								<%@ include file="dutyPrepareInfo.jsp"%>
								
							</div>
							<!----表格结束---->
						</div>
						<!----信息显示区结束---->

					</div>
				</div>
			</div>
		<div class="clear"></div>
	</div>
	<!-- 引用 js  -->
	<script type="text/javascript" charset="utf-8" src="<%=basePath%>dutydata/common.js"></script>
	<script type="text/javascript" charset="utf-8" src="<%=basePath%>dutydata/dutyprepare/js/dutyPrepare.js"></script>
  </body>
</html>
