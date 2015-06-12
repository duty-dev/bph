//声明变量map、layer、markerlayer、marker、url、panzoombar、overviewmap、
   // scaleline
var map,layer, markerlayer,panzoombar,overviewmap,scaleline;
var vector_style;
//var url = "http://support.supermap.com.cn:8090/iserver/services/map-world/rest/maps/World";	//世界范围
var url =  "http://25.30.9.42:8090/iserver/services/map-ChengDu/rest/maps/chengduMap"; //成都市区范围

var popupWin = null;

//创建地图控件
function initMap()
{
	initCompents();
	
    map = new SuperMap.Map("map",{controls:[
        new SuperMap.Control.Navigation({
        	dragPanOptions: {
        		enableKinetic: true
        		}
        		}) ,
        new SuperMap.Control.LayerSwitcher(),
        new SuperMap.Control.MousePosition()
    ]});
    map.events.on({
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
	//新建一个策略并使用在矢量要素图层(vector)上。
	var strategy = new SuperMap.Strategy.GeoText();
	strategy.style = {
	  fontColor:"#FF7F00",
	  fontWeight:"bolder",
	  fontSize:"14px",
	  fontSize:"14px",
	  fill: true,
	  fillColor: "#FFFFFF",
	  fillOpacity: 1,
	  stroke: true,
	  strokeColor:"#8B7B8B"
	};
	// 新建点矢量图层
	vector = new SuperMap.Layer.Vector("vectorLayer",{
			styleMap:new SuperMap.StyleMap({
				'default':vector_style
			}),
		rendererOptions: {zIndexing: true},strategies: [strategy]
	});
		
    layer= new SuperMap.Layer.TiledDynamicRESTLayer("chengduMap", url, { transparent: true, cacheEnabled: true }, { maxResolution: "auto" });
	layer.events.on({"layerInitialized":addLayer});
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

    //显示地图范围
    map.setCenter(new SuperMap.LonLat(104.08 , 30.71), 1);
}
/**
 * 添加数据
 */
 function addData()
 { 
	 var callbacks={
	            click: function(currentFeature){
	            	var cattrs = currentFeature.attributes;
	            destroyWin();
	            var fpopup = new SuperMap.Popup.FramedCloud("popwin"+ cattrs.id,
	                    new SuperMap.LonLat(cattrs.lon ,cattrs.lat ),
	                    null,
	                    cattrs.name,
	                    null,
	                    true);
	            map.addPopup(fpopup);
	        }
        };
        var  selectFeature = new SuperMap.Control.SelectFeature(vector,
                {
                    callbacks: callbacks
                });
        map.addControl(selectFeature);
        selectFeature.activate();
        
        var point1 = new SuperMap.Geometry.Point(104.08 , 30.71);
        var point2 = new SuperMap.Geometry.Point(104.07 , 30.69);
        var point3 = new SuperMap.Geometry.Point(5404.97335,-3587.53641);
        var curve = new SuperMap.Geometry.Curve([point1,point2]);
        
        var geoText = new SuperMap.Geometry.GeoText(104.08 , 30.71,"中华人民共和国");
        var geotextFeature = new SuperMap.Feature.Vector(curve);
        vector.addFeatures(geotextFeature);
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
 * 当鼠标移出maker时触发此事件。
 */
function destroyWin(){
	if(popupWin){
		try{
			popupWin.hide();
			popupWin.destroy();
		}catch(e){}
	}
	map.removeAllPopup();
}

/**
 * 获取鼠标坐标
 */
function getMousePositionPx(e){
	var lonlat= map.getLonLatFromPixel(new SuperMap.Pixel(e.clientX,e.clientY));
	var nlon = (lonlat == null ? "" : lonlat.lon.toFixed(5));
	var nlat = (lonlat == null ? "" : lonlat.lat.toFixed(5));
	var newHtml="<span style='color: #000;'><h4>经度："+ nlon + "   " + "纬度："+nlat;
	newHtml += "   层数：" + (map.getZoom() + 1);
	newHtml += "   比例尺：" + (map.getScale() * 1000000).toFixed(6) + "万 : 1";
	newHtml += "</h4></span>";
	document.getElementById("mousePositionDiv").innerHTML=newHtml;
}

// 天网Feature数组
var skynetFeatures = [];
/**
 * 获取天网，上图
 */
function alarmrequest(){
	$.ajax({
		url:  basePath+"client/GBPlatForm/getGBDeviceListByOrganId.do",
		/*url: "http://25.30.9.97:8080/BPHCenter/client/GBPlatForm/getGBDeviceListByOrganId.do?organId=1",*/
		type: "post",
		dataType: "json",
		data: {
			organId : 1,
			random: Math.random()
		},
		success: function(res){
			if(res.data && res.data.length > 0){
				for(var i = 0; i < res.data.length; i++){
					var obj = res.data[i];
					if(obj.longitude == "0.0" || obj.latitude == "0.0")
						continue;
					var skynet = new SuperMap.Geometry.Point(obj.longitude, obj.latitude);
					var feature = new SuperMap.Feature.Vector(skynet);
					feature.attributes = {
							id: obj.id,
							name: obj.name,
							lon: obj.longitude,
							lat: obj.latitude
					};
					feature.style = {
				 			strokeColor: "#00FF00",
				 			strokeOpacity: 1,
				 			strokeWidth: 3,
				 			fillColor: "#FF5500",
				 			fillOpacity: 0.5,
				 			pointRadius: 6,
				 			pointerEvents: "visiblePainted",
				 			graphicZIndex: 1,
				 		/*	label: "李四",*/
				 			labelSelect: true,
				 			fontSize: "14px",
				 			fontColor: "red",
				 			fontFamily: "Courier New, monospace",
				 			fontWeight: "bold",
				 			labelYOffset: obj.latitude+10,
				 			labelOutlineColor: "white",
				 			labelOutlineWidth: 3,
				 			externalGraphic: "http://localhost:8080/BPHCenter/supermap/theme/images/others/police.png",
				 			graphicOpacity: 1,
				 	        graphicWidth:32,
				 	        graphicHeight:32
				 	};
					skynetFeatures.push(feature);
				}
			}
			if(skynetFeatures.length > 0){
				vector.addFeatures(skynetFeatures);
			}
		}
	});
}
