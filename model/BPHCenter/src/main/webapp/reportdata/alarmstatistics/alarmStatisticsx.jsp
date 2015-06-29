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
<%@ include file="../../../emulateIE.jsp" %>	
<meta
	content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no'
	name='viewport' />

</head>

<body>
	<div id="wrapper">
		<div class="ty-statistics-top set">
			<h1 id="h_title"></h1>
			<div class="clear box">
				<%@ include file="alarmStatisticsxToolbar.jsp"%>
			</div>
		</div>
		<nav class="leftpart" id="main-nav" style="width:338px;padding-left:2px;">
			<div class='navigation'>
				<%@ include file="../../leftForReportx.jsp"%>
			</div>
		</nav>
		<div class="temp">
       		<div class="ty-btn-zoom" onclick="leftZoomx(this);"></div>
       		<div class="ty-left-zoom-line"></div>
       	</div>
		<section id='content' style="margin-left:340px;">
			<div class="container-fluid">
				<div id="content-wrapper" class="row-fluid">
					<div class='span12'>

						<div class="row-fluid">
							<!----功能模块---->
							<div class="set">
								
							</div>
						</div>
						<!----功能模块结束---->

						<div class="row-fluid">
							<!----信息显示区---->
							<div class="span8 box">
								<!----表格---->
								<%@ include file="alarmStatisticsInfo.jsp"%>
							</div>
							<!----表格结束---->
						</div>
						<!----信息显示区结束---->

					</div>
				</div>
			</div>
		</section>
 	</div>
	<!----底部结束---->
</body>
</html>
