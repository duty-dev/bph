<%@ page language="java" pageEncoding="UTF-8"%>
<!--引用需要的脚本-->
<style type="text/css">
#map{
    position: relative;
    height: 553px;
    border:1px solid #3473b7;
}
</style>
<script  type="text/javascript" charset="utf-8"  src="<%=basePath %>supermap/libs/SuperMap.Include.js"></script>
<script  type="text/javascript" charset="utf-8"  src="<%=basePath %>supermap/libs/map.js"></script>
<script type="text/javascript">

$("#mapdiv").height($(window).height()-280);
$("#mapdiv").width($(window).width()-35);

var cardPoints = [], bayonets =[], gBDevices = [];
var basePath = '<%= basePath%>';
//url = "http://25.30.9.42:8090/iserver/services/map-changchun/rest/maps/长春市区图"; //长春市范围
var url2 = "http://25.30.9.42:8090/iserver/services/spatialanalyst-changchun/restjsr/spatialanalyst";//长春市范围(缓冲区)
var markerLayer;
var vector;
   $(document).ready(function(){
	   MapManager.setLayerSwitcher(false);
	   MapManager.setLayerName("baseLayer"); 
	   /* MapManager.setUrl(url);
	   MapManager.setMapCenLat(-3375);
	   MapManager.setMapCenLon(5105);
	   MapManager.setMapZoom(5); */
	   
   	   initMap();
   	   
   	   var map = MapManager.getMap();
   		map.events.on({"click":  function(){
   			MapManager.clearPopups(map,["search"]);
   		},
    		"scope": map});
   		
   	   vector = MapManager.createVectorLayer("vectorLayer");
   		MapManager.addLayer(map,vector);
   		markerLayer = MapManager.createMarkerLayer("markerLayer");
   		MapManager.addLayer(map,markerLayer);
   		
});
  
</script>
<div id="map" style="width: 100%;height:100%"></div>

<%@include file="/base/map/overviewToobar.jsp" %>
<%@include file="/base/map/searchToobar.jsp" %>

	



