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
<%@ include file="../../../../emulateIE.jsp"%>
</head>
<body>
	<div id="wrapper">
		<div id='main-nav-bg'></div>
		<!--机构树 -->
		<nav class="" id="main-nav">
			<div class='navigation'>
				<%@ include file="../../../../left.jsp"%>
			</div>
		</nav>
		<!--机构树 结束-->

		<section id='content'>
			<div class="container-fluid">
				<div id="content-wrapper" class="row-fluid">
					<div class='span12'>

						<!--地图显示的div-->
						<div id="map" style="position:absolute;left:260px;right:0px;width: 83%;height:95%;"></div>
						<div class="temp">
							<div class="al-panel" id="alPanel">
								<div id="bayonetGrid"></div>
								<div id="page"></div>

								<!-- 必达点展开层 -->
								<div class="ty-box-main" id="tyBoxMain">
									<div class="ty-box-title" id="tyBoxTitle"></div>
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
									style="margin-top: 0px; margin-bottom: 0px;list-style-type:none;width:65px;">
									<li onclick="gotoMove('move')">移动</li>
									<li onclick="gotoMove('save')">保存</li>
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
/**页面样式**/
.ty-button-edit{width:6.875em;height:32px;line-height:24px;background-color:#FF7744;color:#FFF;font-size:14px;border:0;border-radius:2px;}
.ty-button-add{width:32px;height:32px;line-height:24px;background-color:#7FC7ED;color:#FFF;font-size:14px;border:0;border-radius:2px;}
.al-panel {
	width: 350px;
	height: 700px;
	position: absolute;
	top: 0;
	left: 0;
	background-color: #233742;
	OVERFLOW-Y: auto; 
	OVERFLOW-X:hidden;
}

.al-sf {
	float: left;
	width: 24px;
	height: 70px;
	position: absolute;
	top: 0;
	left: 350px;
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


</style>
	<script type="text/javascript"
		src="<%=basePath%>supermap/libs/SuperMap.Include.js"></script>
	<script type="text/javascript" src="<%=basePath%>supermap/libs/map.js"></script>
	<script type="text/javascript"
		src="<%=basePath%>supermap/libs/toobar.js"></script>
	<script type="text/javascript">
	var isdrag = null;var curMk = null;
		var marker, drawPoint;
		var vector, organId,markerlayer;
		$(document).ready(function() {
					organId = $("#organId").val();
					//加载地图信息
					MapManager.setMapZoom(1);
					initMap();
					map = MapManager.getMap();
					vector = new SuperMap.Layer.Vector("vectorLayer");
					markerlayer = new SuperMap.Layer.Markers("markerLayer");
					map.addLayers([markerlayer,vector]);
					map.events.on({"click":  function(){
			   			MapManager.clearPopups(map,["search"]);
			   		},"scope": map});
					
					//去点对象
					drawPoint = MapToobar.initDrawFeature(map, vector,
							SuperMap.Handler.Point, null, false,
							drawPointCompleted);
					//BayonetManage.loadBayonetList($("#organId").val());
				});

		/**
		 *  注销控件
		 */
		function deactiveAll() {
			vector.removeAllFeatures();
			markerlayer.clearMarkers();
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
					"width" : 350
				}, 'slow');
				$("#alBtn").animate({
					"left" : 350
				}, 'slow');
			}
		}

		/**
		*左边菜单栏改变时调用
		*/
		function loadData() {
			BayonetManage.loadBayonetList($("#organId").val());
		}
		var BayonetManage = {
			bayDatas: [],
			currentObj:null,
			loadBayonetList : function(orgId) {
				$.ajax({
					url : "<%=basePath%>/client/bayonet/getBayonetList.do",
					type : "post",
					data : {
						"organId" : orgId
					},
					dataType : "json",
					success : function(req) {
						if (req.code == 200) {
							if (req.data == undefined) {
								req.data=[];
							}
							bayDatas = req.data;
							$("#bayonetGrid").kendoGrid(
									{
										selectable : "multiple",
										dataSource : {
											data : req.data
										},
										columns : [ {
											title : 'Id',
											field : 'bayonetId',
											hidden : true
										},{
											title : '名称',
											field : 'name',
											width: '260px'
										}, 
										{field: "操作", template: 
		                        			"<button class='#= (longitude != null && longitude != 0 ? \"ty-button-edit\" : \"ty-button-add\") #' title='编辑' id='modifyCardPoint' style='width: 32px;height:28px;line-height:20px;' type='button' onclick='modifyCardPoint(#:bayonetId#);'>#= (longitude != null && longitude != 0 ? \"编辑\" : \"绘制\") #</button>",
		                        		}],
										selectable : "row",
										change : function(e) {
											var caseGrid = $("#bayonetGrid")
													.data("kendoGrid");
											var row = caseGrid.dataItem(caseGrid
															.select());
											if (row != null) {
												drawPoint.deactivate();
												BayonetManage.Id=row.bayonetId;
												BayonetManage.currentObj=row;
												// 设置被选中的卡口为地图中心位置
												MapManager.setCenter(row.longitude, row.latitude,MapManager.getMap().getZoom());
												doBayonetChart(bayDatas);
												var selectedMak = MapManager.clearMarkerById(markerlayer, row.bayonetId);
												if(selectedMak) selectedMak.icon.url = "<%=basePath%>supermap/theme/images/MapIcos/GisGunCameraSelected.png";
												markerlayer.addMarker(selectedMak);
											}
										}
									});
							doBayonetChart(bayDatas);
						}
					}
				});
			},
			editAreaAction : function() {
				var caseGrid = $("#bayonetGrid").data("kendoGrid");
				var row = caseGrid.dataItem(caseGrid.select());
				if (row != null) {
					BayonetManage.editWarningAction(row.id);
				} else {
					$("body").popjs({"title" : "提示","content" : "请选择要操作的数据"});
				}
			}
		};
		function doBayonetChart(datas){
			markerlayer.clearMarkers();
			for(var i in datas){
				var bayonet = datas[i];
				var entity = new Entity();
				entity.id = bayonet.bayonetId;
				entity.name = bayonet.name;
				entity.type = 2;
				entity.longitude = bayonet.longitude;
				entity.latitude = bayonet.latitude;
				entity.detailInfo = bayonet;
				if(bayonet.longitude && bayonet.latitude)
					MapToobar.doResourceChartOfPoint(entity, markerlayer, 30, 30);
			}
		}
		
		/**
		 * 点击某个巡区是触发加载
		 * @param obj
		 * @param ver
		 */
		function loadPolygon(displayData,mappropertyData) {
			vector.removeAllFeatures();
		
		}
	
		/**
		 * 取点
		 */
		function modifyCardPoint(id) {
			if(!id) return;
			if(!BayonetManage.Id || BayonetManage.Id != id) {
				$("body").popjs({"title" : "提示","content" : "请选择要编辑的卡口数据！"});
				return;
			}
			if (drawPoint) {
				drawPoint.activate();
			}
		}
		/**
		 * 描点完成后 的回调函数
		 * @param eventArgs
		 */
		function drawPointCompleted(eventArgs) {
			var mypoint = eventArgs.feature.geometry;
			var mx = (mypoint.x).toFixed(7), my = (mypoint.y).toFixed(7);
			drawPoint.deactivate();
			vector.removeFeatures(eventArgs.feature);
			
			if(!BayonetManage.currentObj) return;
			var bayonet = BayonetManage.currentObj;
			var entity = new Entity();
			entity.id = bayonet.bayonetId;
			entity.name = bayonet.name;
			entity.type = 2;
			entity.longitude = mypoint.x;
			entity.latitude = mypoint.y;
			entity.detailInfo = bayonet;
			if(entity.longitude && entity.latitude){
				$("body").tyWindow({"content":"确定要在该处绘制卡口?","center":true,"ok":true,"okCallback":function(){
					MapManager.clearMarkerById(markerlayer, entity.id);
					MapToobar.doResourceChartOfPoint(entity, markerlayer, 30, 30);
					// 更新页面变量的数据
					BayonetManage.currentObj.longitude = mypoint.x;
					BayonetManage.currentObj.latitude = mypoint.y;
					for(var i in bayDatas){
						var bayData = bayDatas[i];
						if(bayData && bayData.bayonetId == entity.id){
							bayDatas[i].longitude = mypoint.x;
							bayDatas[i].latitude = mypoint.y;
							break;
						}
					}
					
					try{
						modifyBayonetCoordinate(bayonet.bayonetId, bayonet);
					}catch(e){}
	        	},"no":true});
			}
		}
		
		/**
		* 保存卡口的新坐标
		*/
		function modifyBayonetCoordinate(bayonetId, point){
			$.ajax({
				url: basePath + MapResourceUrl.modifyBayonetCoordinate,
				type: "post",
				dataType: "json",
				data: {
					bayonetId: bayonetId,
					latitude: point.latitude,
					longitude: point.longitude
				},
				success: function(res){
					//$("#"+bayonetId).attr("src",basePath+"supermap/theme/images/others/CaseMarkPressed.png");
					$("body").popjs({"title":"提示","content":"成功绘制卡口！"});
				}
			});
		}

	</script>
<%@include file="/base/map/overviewToobar.jsp" %>
</body>
</html>
