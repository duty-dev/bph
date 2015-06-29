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
	    <div id='main-nav-bg'></div><!--机构树 -->
	    <nav class="" id="main-nav">
	      <div class='navigation'> 
	        <%@ include file="../../left.jsp" %>
	      </div>
	    </nav><!--机构树 结束-->
	    
	    <section id='content'>
	     <div class="container-fluid">
	         <div id="content-wrapper" class="row-fluid">
	           <div class='span12'>
	           
	             <div class="row-fluid"><!----功能模块---->
	               <div class="set">
	                 <h1>社区管理</h1>
	                 <div class="clear box">
	                  <%@ include file="areaSearch.jsp" %>
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
				</div>
				<div class="al-sf" id="alBtn">
					<i class="al-line"></i> <i class="al-icon" onclick="alsf(this);"></i>
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
	width:  450px;
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
</style>
	<script type="text/javascript"
		src="<%=basePath%>supermap/libs/SuperMap.Include.js"></script>
	<script type="text/javascript" src="<%=basePath%>supermap/libs/map.js"></script>
	<script type="text/javascript"
		src="<%=basePath%>supermap/libs/toobar.js"></script>
	<script type="text/javascript">
		var marker, drawPoint, drawPolygon, map,mymodifyFeature,polygon_feature,polygon_final;
		var vector, organId;
		$(document).ready(function() {
					organId = $("#organId").val();
					//加载巡区数据
					AreaManage.loadAreaLit(1);

					//加载地图信息
					MapManager.setMapZoom(1);
					initMap();
					map = MapManager.getMap();
					vector = new SuperMap.Layer.Vector("vectorLayer");
					map.addLayer(vector);
					drawPolygon = MapToobar.initDrawFeature(map, vector,
							SuperMap.Handler.Polygon, null, false,
							drawPolygonCompleted);
					mymodifyFeature = MapManager.createModifyFeature(map,
							vector, false,drawPolygonCompleted);
					
					//去点对象
					drawPoint = MapToobar.initDrawFeature(map, vector,
							SuperMap.Handler.Point, null, false,
							drawPointCompleted);
				});

		/**
		 *  注销控件
		 */
		function deactiveAll() {
			vector.removeAllFeatures();
		}
		
		/**
		 *  绘制巡区
		 */
		function draw_polygon() {
			if(AreaManage.areaId==0){
				alert("请选择要绘制的社区！");
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
						"areaType" : 2,
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
											title : '社区名称',
											field : 'areaName'
										},{
											title : '社区负责人',
											field : 'areaName'
										}  
		                        		, {
											title : '联系电话',
											field : 'tel'
										}, {
											title : '社区人数',
											field : 'nnt'
										}, {
											field: "取点状态", 
											template:"<image src='<%=basePath%>Skin/Default/images/2.png'/>"
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
												loadPolygon(row.displayProperty,row.mapProperty);
											}
										}
									});
							var myGrid = $("#areaGrid").data("kendoGrid");
							myGrid.element.on("dblclick", "tbody>tr","dblclick", function(e) {
										var id = $(this).find("td").first().text();
									});
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
		 * 加载巡区图形
		 */

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
						displayData.label=displayData.name;
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
				    title: "新增社区",
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
				$("body").popjs({"title":"提示","content":"请选择修改的社区！"});
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
	    			type:"post",
	    			dataType:"json",
	    			data:{
	    				id: areaId
	    			},
	    			success:function(msg){
	    				 if(msg.code==200){
	    					 $("body").popjs({"title":"提示","content":msg.description});
	    					 AreaManage.loadAreaLit(1);
	    				}
	    			}
	    		});
	    	}});
		}
		
	</script>
</body>
</html>
