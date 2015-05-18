//声明变量map、layer、markerlayer、marker、url、panzoombar、overviewmap、
   // scaleline
  var map,layer, markerlayer,panzoombar,overviewmap,scaleline;
    var  marker, markCenter;
   var drawPoint, drawLine,drawPolygon,pointLayer,lineLayer,polygonLayer;
   var drawLineMath, drawPolygonMath,lineMathLayer,lineMathLayer;
   var gmarker,menu;
   var dragFeature;
   //var url="http://support.supermap.com.cn:8090/iserver/services/map-china400/rest/maps/China";  //中国范围
 //url = "http://support.supermap.com.cn:8090/iserver/services/map-world/rest/maps/World";	//世界范围
 
var url =  "http://25.30.9.42:8090/iserver/services/map-ChengDu/rest/maps/chengduMap"; //成都市区范围

//创建地图控件
function initMap()
{
    map = new SuperMap.Map("map",{controls:[
   //    new SuperMap.Control.Zoom() ,
        new SuperMap.Control.Navigation({
        	dragPanOptions: {
        		enableKinetic: true
        		}
        		}) ,
        new SuperMap.Control.LayerSwitcher(),
        new SuperMap.Control.MousePosition()
    ]});
    map.events.on({"click": callbackFunction,
    	"mousemove":getMousePositionPx,
    	"scope": map});
    
    initCompents();
    addData();
}

function initCompents(){
	 //初始化复杂缩放控件类
	panzoombar=new SuperMap.Control.PanZoomBar();
	// 是否固定缩放级别为[0,16]之间的整数，默认为false
	panzoombar.forceFixedZoomLevel=true;
	//是否显示滑动条，默认值为false
	panzoombar.showSlider=true;
	/*点击箭头移动地图时，所移动的距离占总距离（上下移动的总距离为高度，左右移动的总距离为宽度）
	的百分比，默认为null。 例如：如果slideRatio 设为0.5, 则垂直上移地图半个地图高度.*/
	panzoombar.slideRatio=0.5; 
	//设置缩放条滑块的高度，默认为11
	panzoombar.zoomStopHeight=5;
	//设置缩放条滑块的宽度，默认为13
	panzoombar.zoomStopWidth=9;
	
	//初始化鹰眼控件类
	overviewmap = new SuperMap.Control.OverviewMap();
	//属性minRectSize：鹰眼范围矩形边框的最小的宽度和高度。默认为8pixels
	overviewmap.minRectSize = 15;
	
	//初始化比例尺控件类
	scaleline = new SuperMap.Control.ScaleLine();
	//是否使用依地量算，默认为false。推荐地图投影为EPSG:4326时设置为false；使用EPSG:900913时设置为true。为true时，比例值按照当前视图中心的水平线计算。
	scaleline.geodesic = true;
	
	// 新建点矢量图层
	pointLayer = new SuperMap.Layer.Vector("pointLayer");
	// 新建线矢量图层
	lineLayer = new SuperMap.Layer.Vector("lineLayer");
	// 新建面矢量图层
	polygonLayer = new SuperMap.Layer.Vector("polygonLayer");
	// DrawFeature--鼠标拖拽要素类
    drawPoint = new SuperMap.Control.DrawFeature(pointLayer, SuperMap.Handler.Point, { multi: true});
	drawLine = new SuperMap.Control.DrawFeature(lineLayer, SuperMap.Handler.Path, { multi: true});
	drawPolygon = new SuperMap.Control.DrawFeature(polygonLayer, SuperMap.Handler.Polygon);
   
	// 新建线矢量图层(距离量算)
	lineMathLayer = new SuperMap.Layer.Vector("lineMathLayer");
	// 新建面矢量图层(面积量算)
	polygonMathLayer = new SuperMap.Layer.Vector("polygonMathLayer"); 
	drawLineMath = new SuperMap.Control.DrawFeature(lineMathLayer, SuperMap.Handler.Path, { multi: true});
	/*
	注册featureadded事件,触发drawCompleted()方法
	例如注册"loadstart"事件的单独监听
	events.on({ "loadstart": loadStartListener });
	*/
	drawLineMath.events.on({"featureadded": drawCompleted});
	drawPolygonMath = new SuperMap.Control.DrawFeature(polygonMathLayer, SuperMap.Handler.Polygon);
	drawPolygonMath.events.on({"featureadded": drawCompletedArea});
    layer= new SuperMap.Layer.TiledDynamicRESTLayer("chengduMap", url, null,{maxResolution:"auto"});
	layer.events.on({"layerInitialized":addLayer});
	markerlayer = new SuperMap.Layer.Markers("markerLayer");
	
	//实例化 DragFeature 控件 
	dragFeature = new SuperMap.Control.DragFeature(polygonLayer); 
	
}

function addLayer(){

    map.addLayers([layer,markerlayer, pointLayer, lineLayer, polygonLayer, lineMathLayer, polygonMathLayer]);
    map.addControl(panzoombar);
    map.addControl(overviewmap);
    map.addControl(scaleline);
    
    map.addControl(drawPoint);
    map.addControl(drawLine);
    map.addControl(drawPolygon);
    
    map.addControl(drawLineMath);
    map.addControl(drawPolygonMath);
    
    //map上添加DragFeature控件 
	map.addControl(dragFeature); 
	//激活控件 
	dragFeature.activate();
	
    //显示地图范围
    map.setCenter(new SuperMap.LonLat(104.08 , 30.71), 1);

}
  //添加数据
 function addData()
 { 
    	  //数据1
	markerlayer.removeMarker(marker);
	var size = new SuperMap.Size(44,33);
	var offset = new SuperMap.Pixel(-(size.w/2), -size.h);
	var icon = new SuperMap.Icon(basePath+"supermap/theme/images/marker.png", size, offset);
	    marker =new SuperMap.Marker(new SuperMap.LonLat(104.06662 , 30.70079),icon) ;
	   marker.events.on({
	        "click": openInfoWin,
	    "mouseover": mouseover,
	    "mouseout": mouseout,
	    "scope": marker
	});
	// 数据2
	 markerlayer.removeMarker(markCenter);
	 markCenter = new SuperMap.Marker(new SuperMap.LonLat(104.08 , 30.71), new SuperMap.Icon(basePath+"supermap/theme/images/marker-gold.png", size, offset));
	markCenter.events.on({
		"rightclick":menuDiv,
		"scope": markCenter
	});	 
	 markerlayer.addMarker(markCenter);
	 markerlayer.addMarker(marker);
}
   
// 打开信息框
var infowin = null;
function openInfoWin(){
	closeInfoWin();
    var marker = this;
    var lonlat = marker.getLonLat();
    var size = new SuperMap.Size(0, 23);
    var offset = new SuperMap.Pixel(0, -size.h);
    var icon = new SuperMap.Icon(basePath+"supermap/theme/images/marker.png", 
		size, offset);

var  c = "点击弹出框信息<br>";
c += "<table border='0'><tr>";
c += "<td>name: </td><td><input type='text' style='width: 120px;'/></td></tr>";
c += "<tr><td>password: </td><td><input type='password' style='width: 120px;'/></td></tr>";

c += "<tr><td colspan='2'>&nbsp;</td></tr>"
c += "<tr><td align='center'  colspan='2'><input type= 'button' value='保存' style='width: 60px;'/>  <input type= 'button' onclick='closeInfoWin()' value='取消' style='width: 60px;'/></td></tr>";
c += "</table";
var popup = new SuperMap.Popup.FramedCloud("popwin",
    		new SuperMap.LonLat(lonlat.lon, lonlat.lat), null,
    		c, icon, true);
    infowin = popup;
    map.addPopup(popup);
}

var anchoredWin = null;
function openWin(obj, content){
	var marker = obj;
	var lonlat = marker.getLonLat();
	var size = new SuperMap.Size(0, 23);
	var offset = new SuperMap.Pixel(0, -size.h);
	var popup = new SuperMap.Popup.Anchored(
			"anPopWin", new SuperMap.LonLat(lonlat.lon, lonlat.lat),null,
			content, null, true);
	anchoredWin = popup;
	map.addPopup(popup);
}
// 关闭信息框
function closeInfoWin(){
	if(infowin){
		try{
			infowin.hide();
			infowin.destroy();
		}catch(e){}
	}
}
// 当鼠标移进maker时触发此事件
function mouseover(){
	//openWin(this, "当鼠标移进maker时触发此事件");
}
//当鼠标移出maker时触发此事件。
function mouseout(){
	if(anchoredWin){
		try{
			anchoredWin.hide();
			anchoredWin.destroy();
		}catch(e){}
	}
}
    
// 注销控件
function deactiveAll(){
	drawPoint.deactivate();
	drawLine.deactivate();
	drawPolygon.deactivate();
	drawLineMath.deactivate();
	drawPolygonMath.deactivate();
}
// 绘点，激活控件
function draw_point(){
	deactiveAll();
	drawPoint.activate();
}
// 绘线，激活控件
 function draw_line(){
	   deactiveAll();
	   drawLine.activate();
}
 // 绘面，激活控件
   function draw_polygon(){
	   deactiveAll();
	   drawPolygon.activate();
   }
   
// 清除，注销所有绘制控件
   function clearFeatures(){
	   deactiveAll();
	   pointLayer.removeAllFeatures();
	   lineLayer.removeAllFeatures();
	   polygonLayer.removeAllFeatures();
	   
		lineMathLayer.removeAllFeatures();
		polygonMathLayer.removeAllFeatures();
   }
   
   
 //绘完触发事件（距离）
function drawCompleted(drawGeometryArgs) {
   //停止画面控制
	drawLineMath.deactivate();
   //获得图层几何对象
   var geometry = drawGeometryArgs.feature.geometry,
   measureParam = new SuperMap.REST.MeasureParameters(geometry), /* MeasureParameters：量算参数类。 客户端要量算的地物间的距离或某个区域的面积*/
   myMeasuerService = new SuperMap.REST.MeasureService(url); //量算服务类，该类负责将量算参数传递到服务端，并获取服务端返回的量算结果
   myMeasuerService.events.on({ "processCompleted": measureCompleted });

   //对MeasureService类型进行判断和赋值，当判断出是LineString时设置MeasureMode.DISTANCE，否则是MeasureMode.AREA
   myMeasuerService.measureMode = SuperMap.REST.MeasureMode.DISTANCE;

   myMeasuerService.processAsync(measureParam); //processAsync负责将客户端的量算参数传递到服务端。
}

//绘完触发事件(面积)
function drawCompletedArea(drawGeometryArgs) {
   //停止画面控制
	drawPolygonMath.deactivate();
   //获得图层几何对象
   var geometry = drawGeometryArgs.feature.geometry,
   measureParam = new SuperMap.REST.MeasureParameters(geometry), /* MeasureParameters：量算参数类。 客户端要量算的地物间的距离或某个区域的面积*/
   myMeasuerService = new SuperMap.REST.MeasureService(url); //量算服务类，该类负责将量算参数传递到服务端，并获取服务端返回的量算结果
   myMeasuerService.events.on({ "processCompleted": measureCompletedArea });

   //对MeasureService类型进行判断和赋值，当判断出是LineString时设置MeasureMode.DISTANCE，否则是MeasureMode.AREA
   myMeasuerService.measureMode = SuperMap.REST.MeasureMode.AREA;

   myMeasuerService.processAsync(measureParam); //processAsync负责将客户端的量算参数传递到服务端。
}

//测量结束调用事件(距离)
function measureCompleted(measureEventArgs) {
	var distance = measureEventArgs.result.distance;
	var unit = measureEventArgs.result.unit;
	alert("量算结果:"+distance + "米");
}

//测量结束调用事件(面积)
function measureCompletedArea(measureEventArgs) {
var area = measureEventArgs.result.area,
unit = measureEventArgs.result.unit;
alert("量算结果:"+ area + "平方米");
}
// 距离测量
function distanceMeasure(){
	clearFeatures();
	drawLineMath.activate();
}
// 面积测量
function areaMeasure(){
	clearFeatures();
	drawPolygonMath.activate();
}

// -------------下面是点击右键弹出菜单的功能 验证未通过--start-----------
function menuDiv(){
	var p= map.getPixelFromLonLat(this.lonlat);
	menu.style.left= p.x+"px";
	menu.style.top= p.y+29+"px";
	menu.style.visibility="visible";
	gmarker= this;
}

//创建EventUtil对象
var EventUtil={
	addHandler:function(element,type,handler){
		if(element.addEventListener){
			element.addEventListener(type,handler,false);
		}
		else if(element.attachEvent){
			element.attachEvent("on"+type,handler);
		}
	},
	getEvent:function(event){
		return event?event:window.event;
	},
	//取消事件的默认行为
	preventDefault:function(event){
		if(event.preventDefault){
			event.preventDefault();
		}else{
			event.returnValue= false;
		}
	},
	stopPropagation:function(event){
		if(event.stopPropagation){
			event.stopPropagation();
		}else{
			event.cancelBubble=true;
		}
	}
};
EventUtil.addHandler(window,"load",function(event){
	menu= document.getElementById("myMenu");
	EventUtil.addHandler(myMenu,"contextmenu",function(event){
		event= EventUtil.getEvent(event);
		EventUtil.preventDefault(event);
		menu.style.visibility="visible";
	});
	EventUtil.addHandler(document,"click",function(event){
		menu.style.visibility="hidden";
	});
});

/**
 * 将对应的点作为中心
 */
function setCenter(){
	//menu.style.visibility="hidden";
	//map.setCenter(gmarker.lonlat,2);
	map.setCenter(marker.lonlat,2);
}

//-------------下面是点击右键弹出菜单的功能 验证未通过--start-----------

function callbackFunction(){ 
	
}

// 移动marker
var ix = 0.001;
function moveMarker(){
	markerlayer.removeMarker(marker);
	var size = new SuperMap.Size(44,33);
	var offset = new SuperMap.Pixel(-(size.w/2), -size.h);
	var icon = new SuperMap.Icon(basePath+"supermap/theme/images/marker.png", size, offset);
	    marker =new SuperMap.Marker(new SuperMap.LonLat(104.06862 + ix , 30.70079 +ix),icon) ;
	markerlayer.addMarker(marker);
	ix += 0.001;
	
	setCenter();
}

// 获取鼠标坐标
function getMousePositionPx(e){
	var lonlat= map.getLonLatFromPixel(new SuperMap.Pixel(e.clientX,e.clientY));
	var newHtml="地图坐标转换：像素坐标与地理位置坐标转换<br> 鼠标像素坐标：" + "x="+Math.floor(e.clientX)+"，"+"y="+Math.floor(e.clientY) +
	"<br>位置坐标："+ "lon="+ lonlat.lon.toFixed(5) + "，" + "lat="+
	lonlat.lat.toFixed(5);
	document.getElementById("mousePositionDiv").innerHTML=newHtml;
}