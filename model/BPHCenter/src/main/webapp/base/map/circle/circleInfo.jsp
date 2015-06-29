<%@ page language="java" pageEncoding="UTF-8"%>
 	<!--引用需要的脚本-->
<style type="text/css">
#circleBar{
	position: absolute;
    z-index: 99;
    right:15px;
    top:2px;
    font-family: Arial;
    font-size: smaller;
}
#mkMenu{
            position: absolute;
            visibility: hidden;
            z-index: 9999;
        }
</style>
    <script  type="text/javascript" charset="utf-8"  src="<%=basePath %>supermap/libs/SuperMap.Include.js"></script>
    <script  type="text/javascript" charset="utf-8"  src="<%=basePath %>supermap/libs/map.js"></script>
     <script  type="text/javascript" charset="utf-8"  src="<%=basePath %>supermap/common/circleMap.js"></script>
    <script type="text/javascript">
    $("#map").height($(window).height()-100);
    $("#map").width($(window).width()-35);
    var basePath = '<%= basePath%>';
    $(document).ready(function(){
    	$(".tree-box").css("height", $(window).height()-200);
    	MapManager.setMapZoom(1);
    	
    	initMap();
    	
    	initCompents();
    	map.addControl(drawPoint);
        map.addControl(drawPolygon);
        map.addControl(mymodifyFeature);
   		map.events.on({"click":  function(){
   			MapManager.clearPopups(map,["search"]);
   		},"scope": map});
	});
	
    </script>
    <input type="hidden"  id="circleId">
    <!--地图显示的div-->
    <div id='mousePositionDiv' class='smCustomControlMousePosition'></div>
    <div id="map" style="position:absolute;left:260px;right:0px;width: 83%;height:95%;"></div>
    <div id="drawDialog"></div>
    <div id="circleBar">
	    <!-- <input type="button" value="绘面" onclick="draw_polygon()" style="width:50px; margin-bottom: 5px;height:32px;line-height:24px;background-color:#E9967A;color:#000000;font-size:14px;border:0;border-radius:2px;"> -->
	    <input id="drawButtom" type="button" value="绘制" onclick="gotoDrawKpoint()" style="display: none; width:50px; margin-bottom: 5px;height:32px;line-height:24px;background-color:#E9967A;color:#000000;font-size:14px;border:0;border-radius:2px;"/>
	    <input type="button" value="返回" onclick="javascript: history.go(-1);" style="width:50px; margin-bottom: 5px;height:32px;line-height:24px;background-color:#B7202A;color:#FFF;font-size:14px;border:0;border-radius:2px;"/>
    </div>
<%@include file="/base/map/overviewToobar.jsp" %>


