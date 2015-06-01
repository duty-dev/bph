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
<head>
<title>扁平化指挥系统</title>
<%@ include file="../../emulateIE.jsp" %>	

</head>


<body>
	<div id="wrapper">
		<div id='main-nav-bg'></div>
		<nav class="" id="main-nav">
			<div class='navigation'>
				<%@ include file="../../leftWithCheckBox.jsp"%>
			</div>
		</nav>
		<section id='content'>
			<div class="container-fluid">
				<div id="content-wrapper" class="row-fluid">
					<div class='span12'>

						<div class="row-fluid">
							<!----功能模块---->
							<div class="set">

								<h1>备勤统计</h1>

								<div class="clear box">
									<%@ include file="dutyReportToolbar.jsp"%>
								</div>
							</div>
						</div>
						<!----功能模块结束---->

						<div class="row-fluid">
							<!----信息显示区---->
							<div class="span8 box">
								<!----表格---->
								<%@ include file="dutyReportInfo.jsp"%>
							</div>
							<!----表格结束---->
						</div>
						<!----信息显示区结束---->

					</div>
				</div>
			</div>
		</section>
	</div>
 	<!-- 引用 js  -->
	<script type="text/javascript" charset="utf-8" src="<%=basePath%>dutydata/common.js"></script>
	<script type="text/javascript" charset="utf-8" src="<%=basePath%>dutydata/dutyreport/js/dutyReport.js"></script>
	<!----底部结束---->
</body>
</html>

