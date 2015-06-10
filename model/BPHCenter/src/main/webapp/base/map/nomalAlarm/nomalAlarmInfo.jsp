<%@ page language="java" pageEncoding="UTF-8"%>
<!--引用需要的脚本-->
<style type="text/css">
#map{
    position: relative;
    height: 553px;
    border:1px solid #3473b7;
}

#mapToolBar{
    position: relative;
    height: 33px;
    padding-top:5;
}
#mousePositionDiv{
    position: absolute;
    z-index: 99;
    left:510px;
    top:35px;
    font-family: Arial;
    font-size: smaller;
}

#vertical {
	height: 660px;
	width: 400px;
	margin:0 auto;
}
</style>
<script  type="text/javascript" charset="utf-8"  src="<%=basePath %>supermap/libs/SuperMap.Include.js"></script>
<script  type="text/javascript" charset="utf-8"  src="<%=basePath %>supermap/libs/map.js"></script>
<!-- <script  type="text/javascript" charset="utf-8"  src="<%=basePath %>supermap/common/nomalAlarmMap.js"></script> -->
<script type="text/javascript">

$("#mapdiv").height($(window).height()-280);
$("#mapdiv").width($(window).width()-35);

var basePath = '<%= basePath%>';
   $(document).ready(function(){
	   MapManager.setOverviewmap(false);
	   MapManager.setLayerSwitcher(true);
	   MapManager.setMapZoom(1);
	   MapManager.setLayerName("baseLayer"); 
   	   initMap();
   	   var map = MapManager.getMap();
   	   var vector = MapManager.createVectorLayer("vectorLayer");
   		MapManager.addLayer(map,vector);
   		
   	 //点数据
   	 var point =[104.06662 , 30.73399];
       var point_data=[[104.06662 , 30.71189],[104.08662 , 30.73399],[104.06662 , 30.75559]];
        //MapManager.createPointFeature(point, vector);
   		//MapManager.createLineFeature(point_data, vector);
       MapManager.createPolygonFeature(point_data, vector);
   		
   		var drawLineBounds = MapManager.toobar.initDrawFeature(map,
   				vector, 
   			 SuperMap.Handler.Path,  
   			 { multi: true},
   			 false,
   			 drawBoundsCompleted);
   		
   		function drawBoundsCompleted(eventArgs){
   			var geometryB = eventArgs.feature.geometry;
   			alert(1)
   		 } 
});
</script>
<!--地图显示的div-->
<div id='mousePositionDiv' class='smCustomControlMousePosition'></div>
<div id="map" style="width: 100%;height:100%">
<div id="drawDialog"></div>

<div id="vertical"  style="display: none">
	<div id="horizontal" style="height:100%; width:100%;">
				<div id="left-pane">
			<div class="pane-content">
				<!-- 左开始 -->
				<div class="demo-section k-header">
					<form id="employeeForm" data-role="validator"
						novalidate="novalidate">
						<input value="${user.userId}" id="userId" name="userId" type="hidden"/>
						<input value="${circleLayer.id}" id="circleLayerId" name="circleLayerd" type="hidden"/>
						<ul>
							<li>&nbsp;</li>
							<li><span>名&nbsp;&nbsp;&nbsp;称:</span> <span id="circleLayerName">${circleLayer.name }</span></li>
							<%-- <li><label>&nbsp; 姓&nbsp;&nbsp;&nbsp;&nbsp;名:</label> ${user.userName}</li> --%>
							<li><span class="fl" style="line-height:30px;">边框色:</span> 
							<input type="hidden"	class="k-textbox fl"  onchange="changePloy()"  value="${displayProperty.borderColor }" name="borderColor" id="borderColor"/>
							<div id="myborderColordiv" style="float:left;width: 20px; background-color:  ${displayProperty.borderColor };margin-top:5px;margin-right:5px;margin-left:8px;" >&nbsp;&nbsp;&nbsp;</div>	 
								 <img src='<%=basePath %>supermap/theme/images/others/colorpicker.png' id="cp1" style="cursor:pointer"/>
								 <span style="margin-left: 10px;">透明度:</span>
								 <select class="w176"  style="width: 70px;"id="borderOpacity"  onchange="changePloy()" >
								 	<option value="0"  ${displayProperty.borderOpacity ==0 ? 'selected' : '' }>0%</option>
								 	<option value="0.1"  ${displayProperty.borderOpacity ==0.1 ? 'selected' : '' }>10%</option>
								 	<option value="0.2"  ${displayProperty.borderOpacity ==0.2 ? 'selected' : '' }>20%</option>
								 	<option value="0.3" ${displayProperty.borderOpacity ==0.3 ? 'selected' : '' }>30%</option>
								 	<option value="0.4"  ${displayProperty.borderOpacity ==0.4 ? 'selected' : '' }>40%</option>
								 	<option value="0.5"  ${displayProperty.borderOpacity ==0.5 ? 'selected' : '' }>50%</option>
								 	<option value="0.6" ${displayProperty.borderOpacity ==0.6 ? 'selected' : '' }>60%</option>
								 	<option value="0.7"  ${displayProperty.borderOpacity ==0.7 ? 'selected' : '' }>70%</option>
								 	<option value="0.8"  ${displayProperty.borderOpacity ==0.8 ? 'selected' : '' }>80%</option>
								 	<option value="0.9" ${displayProperty.borderOpacity ==0.9 ? 'selected' : '' }>90%</option>
								 	<option value="1" ${displayProperty.borderOpacity ==1 ? 'selected' : '' }>100%</option>
								 </select>
						    </li>
							<li><span class="fl" style="line-height:30px;">背景色:</span> 
							<input type="hidden" class="k-textbox"  onchange="changePloy()" value="${displayProperty.fillColor }" name="fColor" id="fColor" />
							<div id="fColordiv" style="float:left;width: 20px; background-color:  ${displayProperty.fillColor };margin-top:5px;margin-right:5px;margin-left:8px;"">&nbsp;&nbsp;&nbsp;</div>	
								<img src='<%=basePath %>supermap/theme/images/others/colorpicker.png' id="cp2" style="cursor:pointer"/>
								<span style="margin-left: 10px;">透明度:</span> 
								<select class="w176" style="width: 70px;" id="fOpacity" placeholder="" onchange="changePloy()" >
								 	<option value="0"  ${displayProperty.fillOpacity ==0 ? 'selected' : '' }>0%</option>
								 	<option value="0.1"  ${displayProperty.fillOpacity ==0.1 ? 'selected' : '' }>10%</option>
								 	<option value="0.2"  ${displayProperty.fillOpacity ==0.2 ? 'selected' : '' }>20%</option>
								 	<option value="0.3" ${displayProperty.fillOpacity ==0.3 ? 'selected' : '' }>30%</option>
								 	<option value="0.4"  ${displayProperty.fillOpacity ==0.4 ? 'selected' : '' }>40%</option>
								 	<option value="0.5"  ${displayProperty.fillOpacity ==0.5 ? 'selected' : '' }>50%</option>
								 	<option value="0.6" ${displayProperty.fillOpacity ==0.6 ? 'selected' : '' }>60%</option>
								 	<option value="0.7"  ${displayProperty.fillOpacity ==0.7 ? 'selected' : '' }>70%</option>
								 	<option value="0.8"  ${displayProperty.fillOpacity ==0.8 ? 'selected' : '' }>80%</option>
								 	<option value="0.9" ${displayProperty.fillOpacity ==0.9 ? 'selected' : '' }>90%</option>
								 	<option value="1" ${displayProperty.fillOpacity ==1 ? 'selected' : '' }>100%</option>
								 </select>
							</li>
							<li><span>中心点:</span> 
								<input type="text" class="k-textbox" style="margin-left: 5px;width: 110px;" name="latitude" id="latitude"  value = "${displayProperty.y }"/>
								<button type="button" data-role="button" data-sprite-css-class="k-icon k-i-tick"  onclick="getPoint()">取点</button>	 
							</li>
							<li>
								<input type="text" class="k-textbox"  style="margin-left: 52px;width: 110px;" name="longitude" id="longitude" value = "${displayProperty.x }"/></li>
							<li class="actions">
								<button type="button"  onclick="save()">保存</button>
								<button type="button" data-role="button" class="closeButton" style="margin-left: 20px;"
									data-sprite-css-class="k-icon k-i-tick" onclick="uclose()">取消</button>
							</li>
						</ul>
					</form>
				</div>
				<!-- 左结束-->
		</div>
		</div>
		</div>
	</div>
	
</div>


