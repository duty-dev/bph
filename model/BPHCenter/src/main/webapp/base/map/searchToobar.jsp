<%@ page language="java" pageEncoding="UTF-8"%>
<!--引用需要的脚本-->
<style type="text/css">
#toobarDiv{
    position: absolute;
    z-index: 99;
    left:600px;
    bottom:35px;
    font-family: Arial;
    font-size: smaller;
}
img{
	cursor:pointer;
	}
</style>
<script  type="text/javascript" charset="utf-8"  src="<%=basePath %>supermap/libs/toobar.js"></script>
<script type="text/javascript">
var basePath = '<%= basePath%>';
   $(document).ready(function(){
	   
	   $("#searchResultDialog").kendoWindow({
			width: "380px",
			height: "650px",
			title: "搜索结果",
			position: {
				top: "230px",
				left: "100px"
			},
			visible: false
		});
});
   
 /**
	* typeNum 区域收藏类型
	*1：点周边
	*2：线周边
	*3：矩形
	*4：多边形
*/
 function selectSearch(obj,searchType, typeNum){
	 url2 =  MapManager.getAnalyUrl();
	 var dataInfos = [];
	 dataInfos.push(typeNum); // 搜索类型
	 dataInfos.push(ResourceDatas.datas);
	  
	 //---------------清理地图区，准备绘制--begin------------------
	 try{
		 MapToobar.clearSearchInfo();
	 }catch(e){}
	//---------------清理地图区，准备绘制--end------------------
	 var iconNum = 0, handle = SuperMap.Handler.Point;
	 var searchToobar = [["point",128,SuperMap.Handler.Point],["path",130,SuperMap.Handler.Path],["RegularPolygon",132,SuperMap.Handler.RegularPolygon],["Polygon",134,SuperMap.Handler.Polygon],["cance",136,null]];
	 for(var i = 0; i < searchToobar.length; i++){
		 if(searchType == searchToobar[i][0]){
			 iconNum = searchToobar[i][1];
			 handle = searchToobar[i][2];
		 }else{
			 $("#"+searchToobar[i][0]+"BySearch").attr("src","<%= basePath%>supermap/theme/images/toobar/buttons_"+searchToobar[i][1]+".png");
		 }
	 }
	 if(iconNum == 0) return;
	 var iconSrc = $(obj).attr("src"); 
	 if( iconSrc.indexOf(iconNum+"") > -1){
		 iconNum++;
		 if(searchType != "cance"){
			 var searchLayer = map.getLayersByName("searchLayer")[0];
			 if(!searchLayer) {
				 searchLayer = MapManager.createVectorLayer("searchLayer");
				 MapManager.addLayer(map,searchLayer);
			 }
			 var searchMarkerLayer = map.getLayersByName("searchMarkerLayer")[0];
			 if(!searchMarkerLayer){
				 var searchMarkerLayer = MapManager.createMarkerLayer("searchMarkerLayer");
			   	 MapManager.addLayer(map,searchMarkerLayer);
			 }
			 if(!map) map = MapManager.getMap();
			 // 值为1：天网 ；2： 卡口; 3：卡点   注意：11及以上表示需通过地图方式查询地图原始资源
			 var queryResType = [1,2,3,4,5];
			 searchDrawFea = MapToobar.initSearchFeature(dataInfos,map,
				 	searchLayer, url2,null,null,
					handle,  queryResType,
					{ multi: true},
					true); 
			 searchDrawFea.objInfo = "searchFeature";
		 }
	 }
	 $(obj).attr("src","<%= basePath%>supermap/theme/images/toobar/buttons_"+iconNum+".png");
}
</script>
<!--地图显示的div-->
<div id='toobarDiv' >
	<img id="pointBySearch" src="<%= basePath%>supermap/theme/images/toobar/buttons_128.png"  onclick="selectSearch(this,'point',1)">
	<img id="pathBySearch"  src="<%= basePath%>supermap/theme/images/toobar/buttons_130.png"  onclick="selectSearch(this,'path',2)">
	<img id="RegularPolygonBySearch"  src="<%= basePath%>supermap/theme/images/toobar/buttons_132.png"  onclick="selectSearch(this,'RegularPolygon',3)">
	<img id="PolygonBySearch"  src="<%= basePath%>supermap/theme/images/toobar/buttons_134.png"  onclick="selectSearch(this,'Polygon',4)">
	<img id="canceBySearch"  src="<%= basePath%>supermap/theme/images/toobar/buttons_136.png"  onclick="selectSearch(this,'cance',0)">
</div>
<div id="searchResultDialog"></div>
<div id="addGroupDialog"></div>
<div style="display:none;">
	<input id="userId" type="text"  value="${User.userId}">
	<input id="orgId" type="text"  value=" ${User.orgId}">
</div>


	



