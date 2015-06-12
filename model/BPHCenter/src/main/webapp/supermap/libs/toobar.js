var searchHandler = null; //搜索类型
var searchPop = null; //搜索提示框对象
var searchObj = null; // 搜索绘制控件对象
var searchGeo = null; // 缓冲区搜索源（要素）
var searchFea = null; // 搜索缓冲区要素
var searchLayer = null; // 搜索缓冲区显示的图层
var searchUrl = ""; //分析缓冲区的url
var searchDistance = 120 // 缓冲区半径大小
var searchLineSegment = 25;  // 缓冲区指定点的数量
var flag = true; 
/**
 * 专题部分
 */
var MapToobar = {
	/**
	 *  绘制控件专题
	 * @param map  地图对象
	 * @param layer 执行绘制要素的图层
	 * @param handler 要素绘制事件处理器，指定当前绘制的要素类型和操作方法
	 * @param options 设置该类及其父类开放的属性
	 * @param isActivate 是否立即生效
	 * @param drawPointCompleted 绘制完成后的回调方法
	 * @returns
	 */
	initDrawFeature: function(map,layer,handler, options, isActivate, drawCompleted){
		var drawPoint = MapManager.createDrawFeature(map, layer, isActivate, handler, options);
		drawPoint.events.on({"featureadded": drawCompleted} );
		return drawPoint;
	},
	
	initSearchFeature: function(map,layer,sUrl,distance,lineSegment,handler, options, isActivate){
		var searchFeature = MapManager.createDrawFeature(map, layer, isActivate, handler, options);
		searchHandler = handler;
		searchObj = searchFeature; 
		searchLayer = layer;
		searchUrl = sUrl;
		if(distance) searchDistance = distance; 
		if(lineSegment) searchLineSegment = lineSegment;
		searchFeature.events.on({"featureadded": MapToobar.drawSearchCompleted} );
		return searchFeature;
	},
	drawSearchCompleted: function(eventArgs){
		if(searchObj) MapManager.setControlIsActivate(searchObj, false);
		var geometry = eventArgs.feature.geometry.components[0];
		searchGeo = geometry;
		if(searchHandler != SuperMap.Handler.Polygon && searchHandler != SuperMap.Handler.RegularPolygon){
			MapManager.bufferAnalystProcess(searchUrl, geometry, searchDistance, searchLineSegment, MapToobar.bufferAnalystCompleted, null);
		}
	 } ,
	/**
	 * 分析成功后的回调函数
	 * @param BufferAnalystEventArgs
	 */
	bufferAnalystCompleted: function (BufferAnalystEventArgs) {
		if(searchFea) searchLayer.removeFeatures(searchFea);
	    var feature = new SuperMap.Feature.Vector();
	    var bufferResultGeometry = BufferAnalystEventArgs.result.resultGeometry;
	    feature.geometry = bufferResultGeometry;
	    searchFea = feature;
	    searchLayer.addFeatures(feature);
	    if(!flag){
	    	return;
	    }
	    flag = false;
		var la = bufferResultGeometry.bounds;
    	var lonlat = new Object();
    	lonlat.lon = (la.right + la.left)/2 + (la.right - la.left)/4;
    	lonlat.lat = (la.top + la.bottom)/2;
    	var content = "<div style='height: 70px;'><div style=‘position: absolute; left: 1px; top;10px;’>";
    	content += "<span>半径</span>";
    	content += "<input id='slider' style='width: 200px;' />";
    	content += "<span id='distance'>500</span>";
    	content += "米</div>";
    	content += "<div style='position: absolute;left: 105px;top; 10px;'><input type='button' value='搜索' onclick='MapToobar.clearSearchInfo()' style='width: 50px;background: red'></div>";
    	content += "</div>";
    	var popup = MapManager.openFramedCloud(null, lonlat, content,null, false);
    	map.addPopup(popup);
    	searchPop = popup;
    	$("#slider").kendoSlider({
 			min:10,
 			max:500,
 			change:function(e){
 				$("#distance").html(e.value);
 				MapManager.bufferAnalystProcess(searchUrl, searchGeo, Number(e.value), searchLineSegment, MapToobar.bufferAnalystCompleted, null);
 			}
 		});
    	var slider = $("#slider").data("kendoSlider");
    	slider.value(searchDistance);
	},
	clearSearchInfo: function(){
		searchHandler = null; //搜索类型
		map.removePopup(searchPop);
		searchPop = null; //搜索提示框对象
		searchObj = null; // 搜索绘制控件对象
		searchGeo = null; // 缓冲区搜索源（要素）
		searchFea = null; // 搜索缓冲区要素
		searchLayer.removeAllFeatures();
		map.removeLayer(searchLayer);
		searchLayer = null; // 搜索缓冲区显示的图层
		searchUrl = ""; //分析缓冲区的url
		searchDistance = 120 // 缓冲区半径大小
		searchLineSegment = 25;  // 缓冲区指定点的数量
		flag = true; 
	}
}