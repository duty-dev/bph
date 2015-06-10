//声明变量map、layer、markerlayer、marker、url、panzoombar、overviewmap、
   // scaleline
var map,layer, markerlayer,panzoombar,overviewmap,scaleline;
var  marker,drawPoint,drawPolygon, drawRectangle;
var  snap01,mymodifyFeature,vector,snapState=false;
var vector_style;
var url =  "http://25.30.9.42:8090/iserver/services/map-ChengDu/rest/maps/chengduMap"; //成都市区范围

// 图层名称对象
var anchoredWin = null;
// 描绘的图层对象
var polygonFinal = null;
// 圈层
var polygonfeatureFinal = null;
//创建地图控件
function initMap()
{
	initCompents();
	
    map = new SuperMap.Map("map",{controls:[
   //    new SuperMap.Control.Zoom() ,
        new SuperMap.Control.Navigation({
        	dragPanOptions: {
        		enableKinetic: true
        		}
        		}) ,
        new SuperMap.Control.LayerSwitcher(),
        new SuperMap.Control.MousePosition(),mymodifyFeature
    ]});
    map.events.on({"click": callbackFunction,
    	"mousemove":getMousePositionPx,
    	"scope": map});

    addData();
}

/**
 * 初始化各种控件、图层
 */
function initCompents(){
	vector_style = new SuperMap.Style({
		strokeColor: "#00FF00",
		strokeOpacity: 1,
		strokeWidth: 3,
		fillColor: "#FF5500",
		fillOpacity: 0.5,
		pointRadius: 6,
		pointerEvents: "visiblePainted",
		graphicZIndex: 1
    });
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
	vector = new SuperMap.Layer.Vector("vectorLayer",{
			styleMap:new SuperMap.StyleMap({
				'default':vector_style
			}),
		rendererOptions: {zIndexing: true}
	});
	// DrawFeature--鼠标拖拽要素类
	drawPoint = new SuperMap.Control.DrawFeature(vector, SuperMap.Handler.Point, { multi: true});
	drawPoint.events.on({"featureadded": drawPointCompleted} );
	drawPolygon = new SuperMap.Control.DrawFeature(vector, SuperMap.Handler.Polygon);
	drawPolygon.events.on({"featureadded": drawPolygonCompleted});
	
	drawRectangle = new SuperMap.Control.DrawFeature(vector, SuperMap.Handler.RegularPolygon);
	drawRectangle.events.on({"featureadded": drawPolygonCompleted});
	
	snap01=new SuperMap.Snap([vector],4,2,{actived:true});
    //矢量要素编辑控件
	mymodifyFeature = new SuperMap.Control.ModifyFeature(vector);
	mymodifyFeature.snap=snap01;
	
    layer= new SuperMap.Layer.TiledDynamicRESTLayer("chengduMap", url, { transparent: true, cacheEnabled: true }, { maxResolution: "auto" });
	layer.events.on({"layerInitialized":addLayer});
	// 当图层上的要素编辑完成时，触发该事件
	vector.events.on({"afterfeaturemodified": editFeatureCompleted});
}
/**
 * 添加各种图层及控件
 */
function addLayer(){
	// 增加图层
    map.addLayers([layer,vector]);
    // 增加各种控件
    map.addControl(panzoombar);
    map.addControl(overviewmap);
    map.addControl(scaleline);

    map.addControl(drawPoint);
    map.addControl(drawPolygon);
    map.addControl(drawRectangle);
    //显示地图范围
    map.setCenter(new SuperMap.LonLat(104.08 , 30.71), 1);
}
/**
 * 添加数据
 */
 function addData()
 { 
     
}
/**
 * 要素编辑完成的回调函数
 * @param event
 */
function editFeatureCompleted(eventArgs){
	createCenterPoint(eventArgs);
}

/**
 * 绘制圈层后的回调函数
 * @param eventArgs
 */
function drawPolygonCompleted(eventArgs){
	createCenterPoint(eventArgs);
}

/**
 * 生成绘制圈层的中心点，并记录对应的坐标
 * @param eventArgs
 */
function createCenterPoint(eventArgs){
	// 先销毁中心点提示框
	destroyWin();
	deactiveAll();	
	var iobj  = document.getElementById("tyIframeContent").contentWindow;
	var geometryB = eventArgs.feature.geometry;
	var points = geometryB.components[0].components; 
	var x = 0,y = 0;
	for(var i = 0; i < points.length; i++){
		var point = points[i];
		x += point.x;
		y += point.y;
	}
	var obj = new Object();
	obj.x = x = (x/points.length).toFixed(7); // 平均值x
	obj.y = y = (y/points.length).toFixed(7);// 平均值y
	//iobj.document.getElementById("centerPointX").value="111";
	$(iobj.document.getElementById("longitude")).val(x);
	$(iobj.document.getElementById("latitude")).val(y);
	map.setCenter(new SuperMap.LonLat(x, y), 1);
	// 生成对应圈层的标签
	var circleLName= $(iobj.document.getElementById("circleLayerName")).html();
	var content = "<span style='border: 1px; border-style: solid;border-color: red;color: red; padding: 4px 3px 4px 3px; margin-left: -4px;'>"+circleLName;
	content += "</span>";
	var size = new Object();
	size.w = circleLName.length * 14; size.h = 20;
	anchoredWin = openWin(obj,content, size);
	var ve = eventArgs.feature;
	var polygon = ve.geometry;
	polygonFinal = ve.geometry;
	polygonfeatureFinal = ve;
}

/**
 * 描点完成后 的回调函数
 * @param eventArgs
 */
function drawPointCompleted(eventArgs){
	var iobj  = document.getElementById("tyIframeContent").contentWindow;
	var geometryB = eventArgs.feature.geometry;
	var mypoint = geometryB.components[0];
	var mx = (mypoint.x).toFixed(7), my = (mypoint.y).toFixed(7);
	$(iobj.document.getElementById("longitude")).val(mx);
	$(iobj.document.getElementById("latitude")).val(my);
	destroyWin();
	map.setCenter(new SuperMap.LonLat(mx, my), 1);
	var circleLName= $(iobj.document.getElementById("circleLayerName")).html();
	var content = "<span style='border: 1px; border-style: solid;border-color: red;color: red; padding: 4px 3px 4px 3px; margin-left: -4px;'>"+circleLName;
	content += "</span>";
	var size = new Object();
	size.w = circleLName.length * 14; size.h = 20;
	anchoredWin = openWin(mypoint,content, size);
	drawPoint.deactivate();
	vector.removeFeatures(eventArgs.feature);
}

/**
 * 设置圈层的各种属性
 */
function changePloy(){
	//mymodifyFeature.deactivate();
	var iobj  = document.getElementById("tyIframeContent").contentWindow;
	if(polygonfeatureFinal){
		/*var mystyle = {
			    strokeColor: iobj.document.getElementById("borderColor").value,
			    strokeOpacity: iobj.document.getElementById("borderOpacity").value,
			    strokeWidth:3,
			    pointRadius:6,
			    fillColor:  iobj.document.getElementById("fColor").value,
			    fillOpacity: iobj.document.getElementById("fOpacity").value
			}*/
		var mystyle = new Object();
		if(iobj.document.getElementById("borderColor").value !=  ""){
			mystyle. strokeColor = iobj.document.getElementById("borderColor").value;
		}
		if(iobj.document.getElementById("borderOpacity").value != ""){
			mystyle. strokeOpacity = iobj.document.getElementById("borderOpacity").value;
		}
		if(iobj.document.getElementById("fColor").value != ""){
			mystyle. fillColor = iobj.document.getElementById("fColor").value;
		}
		if(iobj.document.getElementById("fOpacity").value != ""){
			mystyle. fillOpacity = iobj.document.getElementById("fOpacity").value;
		}
		mystyle.strokeWidth = 3;
		mystyle.pointRadius = 6;
		var myPolygonFeature = new SuperMap.Feature.Vector(polygonFinal,null,mystyle);
		
		/*var lineString = polygonFinal.components[0];
		var points = lineString.getVertices();
		var point = points[0];
		point.x = point.x+0.3;
		point[0] = point;*/
		 var point_features=[];
		 point_features.push(myPolygonFeature);
		 vector.addFeatures(point_features);
	}
}
   
// 打开信息框
var infowin = null;
function openInfoWin(obj){
	closeInfoWin();
    var size = new SuperMap.Size(0, 23);
    var offset = new SuperMap.Pixel(0, -size.h);
    var icon = new SuperMap.Icon(basePath+"supermap/theme/images/marker.png", 
		size, offset);
var  c = "test";
var popup = new SuperMap.Popup.FramedCloud("popwin",
    		new SuperMap.LonLat(obj.x, obj.y), null,
    		c, icon, true);
    infowin = popup;
    map.addPopup(popup);
}
/**
 * 生成消息框
 * @param obj
 * @param content
 */
function openWin(obj, content, size){
	if(!size || size.w < 60){
		size = new Object();
		size.w = 60; size.h = 20;
	}
/*	var popup = new SuperMap.Popup.Anchored(
			"anPopWin", new SuperMap.LonLat(obj.x, obj.y),
			new SuperMap.Size(size.w,size.h),
			content, null, false);*/
	var popup = new SuperMap.Popup(
			"anPopWin", new SuperMap.LonLat(obj.x, obj.y),
			new SuperMap.Size(size.w,size.h),
			content, false);
	// 设置背景为透明
	popup.setBackgroundColor("");
	map.addPopup(popup);
	return popup;
}
/**
 * 关闭信息框
 */ 
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
/**
 * 当鼠标移出maker时触发此事件。
 */
function destroyWin(){
	if(anchoredWin){
		try{
			anchoredWin.hide();
			anchoredWin.destroy();
		}catch(e){}
	}
}
    
/**
 *  注销控件
 */
function deactiveAll(){
	drawRectangle.deactivate();
	drawPolygon.deactivate();
}
/**
	* 取点
	*/
function getPoint(){
	if(polygonFinal){
		drawPoint.activate();
	}
}
 /**
  *  绘面，激活控件
  */
  function draw_polygon(){
	   deactiveAll();
	   drawPolygon.activate();
	   mymodifyFeature.activate();
   }
  /**
   *  绘矩形，激活控件
   */
  function draw_Rectangle(){
	  deactiveAll();
	  drawRectangle.activate();
	   mymodifyFeature.activate();
  }
  
 /**
  * 激活 编辑
  */
  function acModifyFeature(){
	  mymodifyFeature.deactivate();
	  mymodifyFeature.activate();
  }
   
/**
 * 清除，注销所有绘制控件
 */
   function clearFeatures(){
	   deactiveAll();
	   
	   vector.removeAllFeatures();
   }
 
 /**
  * 地图点击事件
  */
function callbackFunction(){ 
	
}

/**
 * 获取鼠标坐标
 */
function getMousePositionPx(e){
	var lonlat= map.getLonLatFromPixel(new SuperMap.Pixel(e.clientX,e.clientY));
	var nlon = (lonlat == null ? "" : lonlat.lon.toFixed(5));
	var nlat = (lonlat == null ? "" : lonlat.lat.toFixed(5));
	var newHtml="<span style='color: #000;'><h4>经度："+ nlon + "   " + "纬度："+nlat;
	newHtml += "   层数：" + map.getNumLayers();
	newHtml += "   比例尺：" + (map.getScale() * 1000000).toFixed(6) + "万 : 1";
	newHtml += "</h4></span>";
	document.getElementById("mousePositionDiv").innerHTML=newHtml;
}

/**
 * 将对应的点作为中心
 */
function setCenter(){
	//menu.style.visibility="hidden";
	//map.setCenter(gmarker.lonlat,2);
	map.setCenter(marker.lonlat,2);
}

/**
 * 初始化圈层
 * @param obj
 * @param ver
 */
function createPolygon(obj, vec){
	if(polygonfeatureFinal){
		//vec.removeFeatures(polygonfeatureFinal);
		vec.removeAllFeatures();
		polygonFinal = null;
	}
	if(anchoredWin){
		destroyWin();
	}
	var name = obj.name;
	var displayProperty = obj.displayProperty;
	var mapProperty = obj.mapProperty;
	// 组装圈层各个顶点
    var points=[], createfeatures =[];
    for(var i= 0,len=mapProperty.length;i<len;i++){
        var point = new SuperMap.Geometry.Point(mapProperty[i].x,mapProperty[i].y);
        points.push(point);
    }
    var linearRing=new SuperMap.Geometry.LinearRing(points);
    var polygon=new SuperMap.Geometry.Polygon([linearRing]);
    
    // 组装圈层样式
    var mystyle = new Object();
	if(displayProperty.borderColor !=  ""){
		mystyle. strokeColor = displayProperty.borderColor;
	}
	if(displayProperty.borderOpacity != null){
		mystyle. strokeOpacity = displayProperty.borderOpacity;
	}
	if(displayProperty.fillColor != ""){
		mystyle. fillColor = displayProperty.fillColor;
	}
	if(displayProperty.fillOpacity != null){
		mystyle. fillOpacity = displayProperty.fillOpacity;
	}
	mystyle.strokeWidth = 3;
	mystyle.pointRadius = 6;
    // 生成圈层
    var polygon_feature=new SuperMap.Feature.Vector(polygon,null, mystyle);
    createfeatures.push(polygon_feature);
    polygonFinal = polygon;
    polygonfeatureFinal = polygon_feature;
    vec.addFeatures(createfeatures);
    
    // 生成对应圈层的标签
    var content = "<span style='border: 1px; border-style: solid;border-color: red;color: red; padding: 4px 3px 4px 3px; margin-left: -4px;'>"+ obj.name;
	content += "</span>";
	var size = new Object();
	size.w = obj.name.length * 14; size.h = 20;
	anchoredWin = openWin(displayProperty,content, size);
}

