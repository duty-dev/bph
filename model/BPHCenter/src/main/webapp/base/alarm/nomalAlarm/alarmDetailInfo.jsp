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

<%@ include file="../../../emulateIE.jsp" %>
</head>
<body class="ty-body">
	<div id="vertical" style="overflow-x:hidden;">
		<div id="horizontal" style="height: 300px; width: 610px;">
			<div class="pane-content">
				<!-- 左开始 -->
				<div class="demo-section k-header"> 
					<ul>
					    <li class="ty-input">
							<span class="ty-input-warn"></span><label class="ty-input-label" style="width:88px;text-align:right;">报警单号:</label><input type="text" class="k-textbox" value="${alarmInfo.jjdbh }"/>
						</li>
						<li class="ty-input">
							<span class="ty-input-warn"></span><label class="ty-input-label">报警单位:</label><input type="text" class="k-textbox" value="${alarmInfo.jjdbh }"/>
						</li>
						<li class="ty-input">
							<span class="ty-input-warn"></span><label class="ty-input-label" style="width:88px;text-align:right;">接警人:</label><input type="text" class="k-textbox" value="${alarmInfo.jjyxm }"/>
						</li>
						<li class="ty-input">
							<span class="ty-input-warn"></span><label class="ty-input-label">报警时间:</label><input type="text" class="k-textbox" value="${alarmInfo.bjsj }" />
						</li>
						<li class="ty-input">
							<span class="ty-input-warn"></span><label class="ty-input-label" style="width:88px;text-align:right;">报警电话:</label><input type="text" class="k-textbox" value="${alarmInfo.bjdh }"/>
						</li>
						<li class="ty-input">
							<span class="ty-input-warn"></span><label class="ty-input-label" style="width:88px;text-align:right;">报警方式:</label><input type="text" class="k-textbox" value="${alarmInfo.bjfsbh }"/>
						</li>
						<li class="ty-input">
							<span class="ty-input-warn"></span><label class="ty-input-label">用户姓名:</label><input type="text" class="k-textbox" value="${alarmInfo.bjdhyhxm }"/>
						</li>
						<li class="ty-input">
							<span class="ty-input-warn"></span><label class="ty-input-label" style="width:88px;text-align:right;">用户地址:</label><input type="text" class="k-textbox" value="${alarmInfo.bjdhyhdz }"/>
						</li>
						<li class="ty-input">
							<span class="ty-input-warn"></span><label class="ty-input-label">报警人:</label><input type="text" class="k-textbox" value="${alarmInfo.bjrxm }"/>
						</li>
						<li class="ty-input">
							<span class="ty-input-warn"></span><label class="ty-input-label" style="width:88px;text-align:right;">性别:</label><input type="text" class="k-textbox" value="${alarmInfo.bjrxb }"/>
						</li>
						<li class="ty-input">
							<span class="ty-input-warn"></span><label class="ty-input-label">联系电话:</label><input type="text" class="k-textbox" value="${alarmInfo.lxdh }"/>
						</li>
						<li class="ty-input">
							<span class="ty-input-warn"></span><label class="ty-input-label">事发地址:</label><input type="text" class="k-textbox" value="${alarmInfo.sfdz }"/>
						</li>
						<li class="ty-input">
							<span class="ty-input-warn"></span><label class="ty-input-label" style="width:88px;text-align:right;">管辖单位:</label><input type="text" class="k-textbox" value="${alarmInfo.gxdwbh }"/>
						</li>
						<li class="ty-input">
							<span class="ty-input-warn"></span><label class="ty-input-label">报警类别:</label><input type="text" class="k-textbox" value="${alarmInfo.bjlbmc }"/>
						</li>
						<li class="ty-input">
							<span class="ty-input-warn"></span><label class="ty-input-label" style="width:88px;text-align:right;">报警类型:</label><input type="text" class="k-textbox" value="${alarmInfo.bjlxmc }"/>
						</li>
						<li class="ty-input">
							<span class="ty-input-warn"></span><label class="ty-input-label">报警细类:</label><input type="text" class="k-textbox" value="${alarmInfo.bjxlmc }"/>
						</li>
						<li class="ty-input">
							<span class="ty-input-warn"></span><label class="ty-input-label" style="width:88px;text-align:right;">报警内容:</label><input type="text" class="k-textbox" value="${alarmInfo.bjnr }"/>
						</li>
						<li class="ty-input">
							<span class="ty-input-warn"></span><label class="ty-input-label">结案人:</label><input type="text" class="k-textbox" value="${alarmInfo.jar }"/>
						</li>
						<li class="ty-input">
							<span class="ty-input-warn"></span><label class="ty-input-label" style="width:88px;text-align:right;">结案时间:</label><input type="text" class="k-textbox" value="${alarmInfo.jasj }"/>
						</li>
					</ul>
					<p class="ty-input-row">
						<!--<span id="undo" class="k-button" onclick="policeAddManage.savePoliceNotOut()">保存并继续</span>-->
						<button id="undo" class="ty-button" onclick="policeAddManage.savePoliceWithOut()">确定</button>
					</p>


				</div>
			</div>
		</div>
	</div>
</body> 
</html>
