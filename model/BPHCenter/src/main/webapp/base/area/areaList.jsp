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
<%@ include file="../../emulateIE.jsp"%>
</head>
<body>
	<div id="wrapper">
		<div id='main-nav-bg'></div>
		<!--机构树 -->
		<nav class="" id="main-nav">
			<div class='navigation'>
				<%@ include file="../../left.jsp"%>
			</div>
		</nav>
		<!--机构树 结束-->

		<section id='content'>
			<div class="container-fluid">
				<div id="content-wrapper" class="row-fluid">
					<div class='span12'>

						<div class="row-fluid">
							<!----功能模块---->
							<div class="set">
								<h1>巡区管理</h1>
								<div class="clear box">
									<%@ include file="areaSearch.jsp"%>
								</div>
							</div>
						</div>
						<!--地图显示的div-->
						<div id="map"
							style="position:absolute;left:260px;right:0px;width: 82%;height:85%;">
							<div id="drawDialog"></div>
						</div>
						<div class="temp">
							<div class="al-panel" id="alPanel">
								<div id="areaGrid"></div>
								<div id="page"></div>

								<!-- 必达点展开层 -->
								<div class="ty-box-main" id="tyBoxMain">
									<div class="ty-box-title" id="tyBoxTitle">222</div>
									<table class="ty-box-table" id="tyBoxTable">
										<tr>
											<td width="8"><div class="ty-box-top-left"></div></td>
											<td><div class="ty-box-top"></div></td>
											<td width="8"><div class="ty-box-top-right"></div></td>
										</tr>
										<tr>
											<td colspan="3">
												<div class="ty-box-info" id="tyBoxInfo"></div>
											</td>
										</tr>
										<tr>
											<td><div class="ty-box-bottom-left"></div></td>
											<td><div class="ty-box-bottom"></div></td>
											<td><div class="ty-box-bottom-right"></div></td>
										</tr>
									</table>
								</div>
								<!-- 必达点展开层 -->
							</div>
							<div class="al-sf" id="alBtn">
								<i class="al-line"></i> <i class="al-icon" onclick="alsf(this);"></i>
							</div>
							<div id="mkMenu" style="position:absolute;visibility:hidden;">
								<ul
									style="margin-top: 0px; margin-bottom: 0px;list-style-type:none;width:55px;">
									<li onclick="gotoMove('move')">移动</li>
									<li onclick="gotoMove('save')">锁定</li>
									<li onclick="cancelMenu('init')">取消</li>
								</ul>
							</div>
						</div>
						<!----信息显示区结束---->
					</div>
					<div id="drawDialog"></div>
				</div>
			</div>
		</section>

	</div>
	<style type="text/css">
/**areaList页面样式**/
.al-panel {
	width: 450px;
	height: 550px;
	position: absolute;
	top: 0;
	left: 0;
	background-color: #233742;
}

.al-sf {
	float: left;
	width: 24px;
	height: 70px;
	position: absolute;
	top: 0;
	left: 450px;
}

.al-line {
	float: left;
	width: 3px;
	height: 63px;
	background: url(<%=basePath%>Skin/Default/images/button4.png) no-repeat;
	display: block;
}

.al-icon {
	float: right;
	width: 19px;
	height: 19px;
	background: url(<%=basePath%>Skin/Default/images/button3.png) no-repeat;
	display: block;
}

.al-icon-s {
	float: right;
	width: 19px;
	height: 19px;
	background: url(<%=basePath%>Skin/Default/images/button3-1.png)
		no-repeat;
	display: block;
}

#mkMenu {
	background-color: #233742;
	color: #81C9F0;
}

#mkMenu ul {
	margin: 0;
	padding: 0;
}

#mkMenu li {
	float: left;
	width: 98%;
	height: 30px;
	text-align: center;
	font-size: 15px;
	line-height: 30px;
	border: 1px solid #4A748A;
	cursor: pointer;
}

#mkMenu li:hover {
	color: #00b0ff;
}

.ty-box-main {
	float: left;
	width: 100%;
	height: auto;
	margin: 0 atuo;
	background-color: #121E24;
	clear: both;
	position: absolute;
	display: none;
}

.ty-box-title {
	float: left;
	width: 72px;
	height: 22px;
	line-height: 22px;
	padding: 0px 4px;
	margin-left: 8px;
	font-size: 13px;
	color: #81C9F0;
	background-color: #121E24;
	text-align: center;
	top: 14px;
	left: 0px;
}

.ty-box-table {
	float: left;
	width: 100%;
	height: auto;
	border: 0;
	border-collapse: collapse;
	border-spacing: 0;
	table-layout: fixed;
	color: #81C9F0;
}

.ty-box-table td {
	padding: 0;
}

.ty-box-top-left {
	float: left;
	width: 8px;
	height: 8px;
	background: url(images/b1.png) no-repeat;
	font-size: 1px;
}

.ty-box-top {
	float: left;
	width: 100%;
	height: 6px;
	border-top: 1px solid #4A748B;
}

.ty-box-top-right {
	float: left;
	width: 8px;
	height: 8px;
	background: url(images/b2.png) no-repeat;
	font-size: 1px;
}

.ty-box-bottom-left {
	float: left;
	width: 8px;
	height: 8px;
	background: url(images/b3.png) no-repeat;
	font-size: 1px;
}

.ty-box-bottom {
	float: left;
	width: 100%;
	height: 6px;
	border-bottom: 1px solid #4A748B;
}

.ty-box-bottom-right {
	float: left;
	width: 8px;
	height: 8px;
	background: url(images/b4.png) no-repeat;
	font-size: 1px;
}

.ty-box-info {
	float: left;
	width: 100%;
	height: auto;
	border-left: 1px solid #4A748B;
	border-right: 1px solid #4A748B;
	display: none;
}
</style>
	<script type="text/javascript"
		src="<%=basePath%>supermap/libs/SuperMap.Include.js"></script>
	<script type="text/javascript" src="<%=basePath%>supermap/libs/map.js"></script>
	<script type="text/javascript"
		src="<%=basePath%>supermap/libs/toobar.js"></script>
	<script type="text/javascript">
	/**
	*获取标注状态
	*/
	function isMark(obj){
		var value="<image src='<%=basePath%>images/images/";
		if(obj.mapProperty!=null &&obj.mapProperty!=undefined &&obj.mapProperty!='[]'){
			value+="areaMarked.png'/>";
		}else{
			value+="areaMark.png'/>";
		}
		return value;
	}
	var isdrag = null;var curMk = null;
		var marker, drawPoint, drawPolygon, map,mymodifyFeature,polygon_feature,polygon_final;
		var vector, organId,areaPoint,mk,markerlayer,mkMenu;
		$(document).ready(function() {
					organId = $("#organId").val();
					//加载巡区数据
					AreaManage.loadAreaLit(1);
					//加载地图信息
					MapManager.setMapZoom(1);
					initMap();
					map = MapManager.getMap();
					vector = new SuperMap.Layer.Vector("vectorLayer");
					markerlayer = new SuperMap.Layer.Markers("markerLayer");
					map.addLayers([markerlayer,vector]);
					drawPolygon = MapToobar.initDrawFeature(map, vector,
							SuperMap.Handler.Polygon, null, false,
							drawPolygonCompleted);
					mymodifyFeature = MapManager.createModifyFeature(map,
							vector, false,drawPolygonCompleted);
					
					//去点对象
					drawPoint = MapToobar.initDrawFeature(map, vector,
							SuperMap.Handler.Point, null, false,
							drawPointCompleted);
					
					 //必达点
				    areaPoint = MapManager.createDrawFeature(map,vector, false,SuperMap.Handler.Point, { multi: true});
					areaPoint.events.on({"featureadded": drawAreaPointCompleted} );
					mkMenu = document.getElementById("mkMenu");
				});

		/**
		 *  注销控件
		 */
		function deactiveAll() {
			vector.removeAllFeatures();
			markerlayer.clearMarkers();
			MapManager.clearPopupsById(map);
		}
		
		/**
		 *  绘制巡区
		 */
		function draw_polygon() {
			if(AreaManage.areaId==0){
				alert("请选择要绘制的区域！");
				return;
			}
			if(polygon_final!=undefined){
				//如果已经存在 就激活修改
				mymodifyFeature.activate();
			}else{
				drawPolygon.activate();
			}
		}

		/**
		*区域描绘完成后调用事件
		*/
		function drawPolygonCompleted(eventArgs) {
			drawPolygon.deactivate();
			mymodifyFeature.activate();
			polygon_final =eventArgs.feature.geometry;
		 	polygon_feature = eventArgs.feature;
		 	/*point_features.push(polygon_feature);
			vector.addFeatures(point_features); */
			gotoDrawArea();
		}

		/**
		控制
		div缩放或者扩展
		 */
		function alsf(obj) {
			if ($(obj).hasClass("al-icon")) {
				$(obj).removeClass("al-icon").addClass("al-icon-s");
				$("#alPanel").animate({
					"width" : 0
				}, 'slow');
				$("#alBtn").animate({
					"left" : 1
				}, 'slow');
			} else {
				$(obj).removeClass("al-icon-s").addClass("al-icon");
				$("#alPanel").animate({
					"width" : 450
				}, 'slow');
				$("#alBtn").animate({
					"left" : 450
				}, 'slow');
			}
		}

		/**
		*左边菜单栏改变时调用
		*/
		function loadData() {
			AreaManage.loadAreaLit(1);
		}
		var AreaManage = {
			pageNo : 1,
			areaId:0,
			currentObj:null,
			loadAreaLit : function(pageNo) {
				$.ajax({
					url : "<%=basePath%>area/queryAreaList.do",
					type : "post",
					data : {
						"organIds" : $("#organId").val(),
						"areaType" : 1,
						"pageStart" : pageNo,
						"pageSize" : 10
					},
					dataType : "json",
					success : function(req) {
						if (req.code == 200) {
							if (req.data == undefined) {
								req.data=[];
							}
							var total = 20;
							$("#areaGrid").kendoGrid(
									{
										selectable : "multiple",
										dataSource : {
											data : req.data
										},
										columns : [ {
											title : 'Id',
											field : 'id',
											hidden : true
										}, 
										{field: "操作", template: 
		                        			"<button class='ty-edit-btn' title='编辑' id='openCardPoint' type='button' onclick='gotoModifiyArea(#:id#);'>编辑</button>"+
		                        			"<button class='ty-delete-btn' title='删除' id='deleteCardPoint' type='button' onclick='deleteArea(#:id#)'>删除</button>",
		                        		},{
											title : '巡区名称',
											field : 'areaName'
										},{
											title : '巡区负责人',
											field : 'areaName'
										}  
		                        		, {
											title : '联系电话',
											field : 'tel'
										}, {
											title : '巡区人数',
											field : 'nnt'
										}, {
											field: "取点状态", 
											template:isMark
										}],
										selectable : "row",
										change : function(e) {
											var caseGrid = $("#areaGrid")
													.data("kendoGrid");
											var row = caseGrid.dataItem(caseGrid
															.select());
											if (row != null) {
												AreaManage.areaId=row.id;
												AreaManage.currentObj=row;
												//清空所有pop
												MapManager.clearPopupsById(map);
												loadPolygon(row.displayProperty,row.mapProperty);
											}
											//
											tyBoxMainShow(row);
										}
									});
						/* 	var myGrid = $("#areaGrid").data("kendoGrid");
							myGrid.element.on("dblclick", "tbody>tr","dblclick", function(e) {
										var id = $(this).find("td").first().text();
									}); */
							//$("#page").html(pagination(pageNo,total,'loadData',10));
						}
					}
				});
			},
			editAreaAction : function() {
				var caseGrid = $("#areaGrid").data("kendoGrid");
				var row = caseGrid.dataItem(caseGrid.select());
				if (row != null) {
					AreaManage.editWarningAction(row.id);
				} else {
					$("body").popjs({"title" : "提示","content" : "请选择要操作的数据"});
				}
			},
			onClose : function() {
				loadData(AreaManage.pageNo);
			}
		};

		/**
		 * 点击某个巡区是触发加载
		 * @param obj
		 * @param ver
		 */
		function loadPolygon(displayData,mappropertyData) {
			vector.removeAllFeatures();
			if(mappropertyData!=undefined){
				//面数据
				var points = [], point_features = [];
				 $.each(mappropertyData, function(i, value) {  
					var point = new SuperMap.Geometry.Point(value.x,value.y);
					points.push(point);
				}); 
					var linearRing = new SuperMap.Geometry.LinearRing(points);
					polygon_final = new SuperMap.Geometry.Polygon([ linearRing ]);
					if(displayData!=undefined){
						if(displayData.name!=undefined||displayData.name!=''){
							displayData.label=displayData.name;
						}
					}
					polygon_feature = new SuperMap.Feature.Vector(polygon_final, null,displayData);
					point_features.push(polygon_feature);
					vector.addFeatures(point_features);
			}else{
				polygon_final=null;
			}
		
		}
		/**添加区域
		*/
		function toaAddAreaInfo(){
			if($("#organId").val() == null ||$("#organId").val() == ""){
				alert("请选择机构");
			}else{
				var sessionId = $("#token").val();
				$("#dialog").tyWindow({
					width: "520px",
					height: "448px",
				    title: "新增区域",
				    position: {
				        top: "100px"
				      },
					content: "<%=path%>/web/Area/gotoAddArea.do?organId="+$("#organId").val()+"&sessionId="+sessionId,
					iframe : true,
				});
			}
		}	 
		
		/**修改区域
		*/
		function gotoModifiyArea(areaId){
			if(areaId ==0){
				$("body").popjs({"title":"提示","content":"请选择修改的巡区！"});
			}else{
				$("#dialog").tyWindow({
					width: "520px",
					height: "448px",
				    title: "修改区域",
				    position: {
				        top: "100px"
				      },
					content: "<%=path%>/web/Area/gotoModifiyArea.do?areaId="+areaId + "&random="+ Math.random(),
					iframe : true,
				});
			}
		}	 
		
		
	/**
	调用颜色属性
	*/
	   function gotoDrawArea(){
    	$("#drawDialog").tyWindow({
    		width: "480px",
    		height: "480px",
    	    title: "图形属性设置",
    	    position: {
    	        top: "100px",
    	        left:  document.body.clientWidth-480
    	      },
    		content: "<%=path%>/web/Area/gotoDrawArea.do?areaId="
								+ AreaManage.areaId + "&random="
								+ Math.random(),
						iframe : true,
						init : function() {
							//alert($("#tyIframeContent").prop("id"));
							//alert($("#tyIframeContent #centerPointX").val());
						}
					});
			mymodifyFeature.deactivate();
			mymodifyFeature.activate();
		}

		/**
		 * 设置圈层的各种属性
		 */
		function changePloy() {
			//mymodifyFeature.deactivate();
			var iobj = document.getElementById("tyIframeContent").contentWindow;
			if (polygon_feature) {
				mystyle = new Object();
				if (iobj.document.getElementById("borderColor").value != "") {
					mystyle.strokeColor = iobj.document
							.getElementById("borderColor").value;
				}
				if (iobj.document.getElementById("borderOpacity").value != "") {
					mystyle.strokeOpacity = iobj.document
							.getElementById("borderOpacity").value;
				}
				if (iobj.document.getElementById("fColor").value != "") {
					mystyle.fillColor = iobj.document.getElementById("fColor").value;
				}
				if (iobj.document.getElementById("fOpacity").value != "") {
					mystyle.fillOpacity = iobj.document
							.getElementById("fOpacity").value;
				}
				mystyle.strokeWidth = 3;
				mystyle.pointRadius = 6;
				polygon_feature = new SuperMap.Feature.Vector(polygon_final,
						null, mystyle);
				var point_features = [];
				point_features.push(polygon_feature);
				vector.addFeatures(point_features);
			}
		}

		/**
		 * 取点
		 */
		function getPoint() {
			if (drawPoint) {
				drawPoint.activate();
			}
		}
		/**
		 * 取点end
		 */
		/**
		 * 描点完成后 的回调函数
		 * @param eventArgs
		 */
		function drawPointCompleted(eventArgs) {
			var iobj = document.getElementById("tyIframeContent").contentWindow;
			var mypoint = eventArgs.feature.geometry;
			var mx = (mypoint.x).toFixed(7), my = (mypoint.y).toFixed(7);
			$(iobj.document.getElementById("longitude")).val(mx);
			$(iobj.document.getElementById("latitude")).val(my);
			map.setCenter(new SuperMap.LonLat(mx, my), 1);
			drawPoint.deactivate();
			vector.removeFeatures(eventArgs.feature);
		}

		/**
		属性修改成功
		*/
		function updateSuccess() {
			$("body").popjs({"title":"提示","content":"地图属性修改成功！"});
			$("#drawDialog").tyWindow.close();
		}
		
		function addAreaInfoSuccess() {
			$("body").popjs({"title":"提示","content":"添加成功！"});
			$("#drawDialog").tyWindow.close();
		}
		
		/**删除巡区
		*/
		function deleteArea(areaId) {
	    	$("body").tyWindow({"content":"确定是否删除该巡区?","center":true,"ok":true,"no":true,"okCallback":function(){
	    		$("#table1 tbody").remove();
	    		$.ajax({
	    			url:"<%=basePath%>/area/deleteAreaDetailById.do",
						type : "post",
						dataType : "json",
						data : {
							id : areaId
						},
						success : function(msg) {
							if (msg.code == 200) {
								$("body").popjs({"title" : "提示","content" : msg.description
								});
								AreaManage.loadAreaLit(1);
							}
						}
					});
				}
			});
		}

		/**		
		 * 绘制必达点
		 */
		function draw_areaPoint(){
			if(AreaManage.areaId ==0){
				$("body").popjs({"title":"提示","content":"请选择修改的巡区！"});
				return;
			}
			areaPoint.activate();
		}
		 
		/**
		*画必达点
		*/
		
		function drawAreaPointCompleted(eventArgs){
			var geometryB = eventArgs.feature.geometry;
			var mypoint = geometryB.components[0];
			var mx = mypoint.x, my = mypoint.y;
			var size = new SuperMap.Size(32,37);
			var offset = new SuperMap.Pixel(-(size.w/2), -size.h);
			marker = new SuperMap.Marker(new SuperMap.LonLat( mx, my), new SuperMap.Icon("<%=basePath%>supermap/theme/images/MapIcos/XunPoliceIco.png", size, offset));
			marker.attributes = {source : marker};
			marker.name="新增必达点";
			marker.id=0;
			marker.events.on({ "rightclick": mkMenuDiv,"click":openAreaPointInfo});
		 	markerlayer.removeMarker(marker);
		 	markerlayer.addMarker(marker);
		 	areaPoint.deactivate();
		 	vector.removeFeatures(eventArgs.feature);
		}
		function mkMenuDiv(){
			curMk = this;
			var p= map.getPixelFromLonLat(this.lonlat);
			mkMenu.style.left= p.x+"px";
			mkMenu.style.top= p.y+"px";
			mkMenu.style.visibility="visible";
		}
		 
		/**
		 * 关闭下拉菜单
		 */
		function cancelMenu(type){
			if(type == "init"){
				var sourceMk = curMk.attributes.source;
				markerlayer.removeMarker(curMk);
				curMk.destroy();
				curMk = new SuperMap.Marker(sourceMk.lonlat, sourceMk.icon);
				curMk.attributes = {source: sourceMk};
				 //注册事件
				 curMk.events.on({ "rightclick": mkMenuDiv,"click":openAreaPointInfo});
				 curMk.events.register("mousedown",curMk,function(e){isdrag=false;});
			 	 markerlayer.addMarker(curMk);
			}
			mkMenu.style.visibility="hidden";
		}
		
		/**
		 * 移动marker
		 * @param type
		 */
		function gotoMove(type){
			// 关闭下拉菜单
			cancelMenu();
			//注册事件
			curMk.events.register("mousedown",curMk,function(e){
				if(type == 'save'){
					curMk.attributes = {
							source: curMk.attributes.source
					};
					isdrag=false;
					return;
				}
				isdrag = true;
				var lon = curMk.lonlat;
				var pixel = map.getPixelFromLonLat(lon);
				tx = parseInt(pixel.x);
				ty = parseInt(pixel.y);
				x = curMk ? e.clientX : event.clientX;
				y = curMk ? e.clientY : event.clientY;
			});
			
		/* var sourceObj=curMk.attributes.source;
			if(type == 'save'){
				alert(sourceObj.id+""+sourceObj.name);
			} */
		}


		/**
		 * marker移动方法
		 * @param e
		 */
		function movemouse(e)
		{
			//鼠标的实时位置
			var x2,y2;
			if (isdrag)
			{
				//获取鼠标的实时位置
				x2 = curMk ? tx + e.clientX - x : tx + event.clientX - x;
				y2 = curMk ? ty + e.clientY - y : ty + event.clientY - y;
				//转为屏幕坐标
				var pix = new SuperMap.Pixel(x2,y2);
				//屏幕坐标转为经纬度坐标
				var lon1 = map.getLonLatFromPixel(pix);
				var sourceMk = curMk.attributes.source;
				//将此前的marker坐标清理
				//markerlayer.clearMarkers();
				markerlayer.removeMarker(curMk);
				curMk.destroy();
				//var offset = new SuperMap.Pixel(x2,y2);
				var size = new SuperMap.Size(32,37);
				var offset = new SuperMap.Pixel(-(size.w/2), -size.h);
				var icon = new SuperMap.Icon("<%=basePath%>supermap/theme/images/MapIcos/XunPoliceIco.png",
						size, offset);
				curMk = new SuperMap.Marker(lon1, icon);
				curMk.attributes = {
					source : sourceMk
				};
				//注册事件
				curMk.events.register("mousedown", curMk, function(e) {
					isdrag = true;
					var lon = curMk.lonlat;
					var pixel = map.getPixelFromLonLat(lon);
					tx = parseInt(pixel.x);
					ty = parseInt(pixel.y);
					x = curMk ? e.clientX : event.clientX;
					y = curMk ? e.clientY : event.clientY;
				});
				curMk.events.on({ "rightclick": mkMenuDiv,"click":openAreaPointInfo});
				markerlayer.addMarker(curMk);
			}
		}
		document.onmousemove = movemouse;
		document.onmouseup = new Function("isdrag=false");

		/**
		 *必达点添加单机时间
		 */
		function openAreaPointInfo() {
			curMk = this;
			var lonlat = curMk.getLonLat();
			var sourceObj = curMk.attributes.source;
			if(sourceObj !=null){
			MapManager.clearPopupsById(map,sourceObj.id);
			var contentHTML = "<div style='font-size:.8em; opacity: 0.8; overflow-y:hidden;'>";
			contentHTML += "<div><input type='text' class='k-textbox'  onblur='saveOrUpdatePoint(this)' name='pointName' id='pointName'"+sourceObj.id +" value= "+sourceObj.name+" /></div>";
			var popup = new SuperMap.Popup.FramedCloud("popwin"+sourceObj.id,lonlat, null,
					contentHTML, null, true);
			popup.id = sourceObj.id;
			map.addPopup(popup);
			}
		}
	/**
	*加载必达点
	*/	
	
	function loadAreaPoint(mypoint){
		var mx = mypoint.x, my = mypoint.y;
		var size = new SuperMap.Size(28,33);
		var offset = new SuperMap.Pixel(-(size.w/2), -size.h);
		marker = new SuperMap.Marker(new SuperMap.LonLat( mx, my), new SuperMap.Icon("<%=basePath%>supermap/theme/images/MapIcos/XunPoliceIco.png",
							size, offset));
		marker.name = mypoint.name;
		marker.id = mypoint.id;
			marker.attributes = {
				source : marker
			};
			marker.events.on({"rightclick" : mkMenuDiv,"click" : openAreaPointInfo});
			markerlayer.removeMarker(marker);
			markerlayer.addMarker(marker);
			areaPoint.deactivate();
		}

		
		/**
		* 更新或新增必达点
		*/
		function saveOrUpdatePoint(obj) {
			if(AreaManage.areaId ==0){
				$("body").popjs({"title":"提示","content":"请选择修改的巡区！"});
				return;
			}
			var sourceObj = curMk.attributes.source;
			var lonlat = curMk.getLonLat();
			var  areaPOint={};
			areaPOint.areaId=AreaManage.areaId;
			areaPOint.x=lonlat.lon;
			areaPOint.y=lonlat.lat;
			areaPOint.name=obj.value;
			var url="<%=basePath%>area/addAreaPoint.do";
			if(sourceObj.id!=0){//修改
				areaPOint.id=sourceObj.id
				url="<%=basePath%>area/updateAreaPoint.do";
			}
			alert(JSON.stringify(areaPOint));
			$.ajax({
				url:url,
				type:"post",
				dataType:"json",
			    contentType:"application/json",  
				data:JSON.stringify(areaPOint),
				 success:function(msg){
					if(msg.code==200){
						addAreaInfoSuccess();
					}else{
						$("body").popjs({"title":"提示","content":msg.description});
					}
				}
			});	
			
		}
		
	/**
	*展开巡区节点
	*/
	function tyBoxMainShow(row){
		$("#tyBoxMain").show(function(){
			$("#tyBoxTitle").html(row.areaName);
			$.ajax({
    			url:"<%=basePath%>area/queryAreaPointByAreaId.do",
					type : "post",
					dataType : "json",
					data : {
						areaId : row.id
					},
					success : function(msg) {
						if (msg.code == 200) {
							markerlayer.clearMarkers();
							$("#tyBoxInfo").kendoGrid({
										selectable : "multiple",
										dataSource : {data : msg.data},
										width:"200px",
										columns : [{
													title : '必达点',
													field : 'name'
												}],
										selectable : "row",
										change : function(e) {
									var pointGrid = $("#tyBoxInfo").data("kendoGrid");
									var  pointRow = pointGrid.dataItem(pointGrid.select());
									focusAreaPoint(pointRow);
									}
									});
								$("#tyBoxMain th[class='k-header']").hide();
							 $.each(msg.data, function(i, value) { 
								 //必达点上图
								 loadAreaPoint(value);
								}); 
							$("#tyBoxInfo").css("height",300).slideDown();
						}
					}
				});
		});
	}
	
	
	/**
	*必达点获得焦点
	*/
	function focusAreaPoint(row){
		 $.each(markerlayer.markers, function(i, value) { 
              if(row.id==value.id){
            		MapManager.clearPopupsById(map,value.id);
            	    var lonlat=value.getLonLat();
                    var sourceObj = value.attributes.source;
                    map.setCenter(lonlat);
          			var contentHTML = "<div style='font-size:.8em; opacity: 0.8; overflow-y:hidden;'>";
          			contentHTML += "<div>"+sourceObj.name+"</div></div>";
          			var popup = new SuperMap.Popup.FramedCloud("popwin",lonlat, null,contentHTML, null, true);
          			popup.id=value.id;
          			popup.name=sourceObj.name;
          			map.addPopup(popup);
          		    return;
              }
			}); 
		
	}
	</script>
</body>
</html>
