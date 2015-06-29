// 地图、动态 REST 图层
var map, layer;
//地图名称、动态 REST 图层名称、动态 REST 图层url、分析缓冲使用的url
var mapName = "map", layerName = "layer",url = "http://25.30.9.42:8090/iserver/services/map-ChengDu/rest/maps/chengduMap", analyUrl = "";
// 动态 REST 图层参数
var layerParams,layerOptions;
//地图初始控件、事件绑定
var initControls = [], eventListeners = new Object();
// 控件声明
var panzoombar,overviewmap,scaleline;
// 控件初始参数
var isPanzoombar = true, isOverviewmap = true, isScaleline = false, isLayerSwitcher = false, isNavigation = true, isMousePosition = false;
//地图显示中心的经纬度、初始图层
var mapCenLon = 104.08,mapCenLat = 30.71,mapZoom = 0;
// 是否初始化资源数据
var isInitResourceDatas = false;
var isGPSChart = false; //是否直接上图
/**
 * 初始化地图
 * @returns
 */
function initMap(){
	// 初始化地图控件
	initControl();
	// 初始化地图
	map = MapManager.getMapInstance(mapName, initControls, eventListeners);
	// 初始化rest图层
	layer = MapManager.getLayerInstance(layerName, url, layerParams, layerOptions);
	layer.events.on({"layerInitialized":addLayer});
	
	if(isInitResourceDatas) MapToobar.initResourceDatas(isInitResourceDatas);
	
	return map;
}
/**
 * 初始化地图控件
 */
function initControl(){
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
	// 鹰眼控件
	if(isOverviewmap)  initControls.push(overviewmap);
	// 复杂缩放控件类
	if(isPanzoombar)	initControls.push(panzoombar);
	// 比例尺控件
	if(isScaleline) initControls.push(scaleline);
	// 图层控制控件
	if(isLayerSwitcher) initControls.push(new SuperMap.Control.LayerSwitcher());
	// 鼠标事件（拖拽，双击、鼠标滚轮缩放）的地图浏览控件
	if(isNavigation) initControls.push(new SuperMap.Control.Navigation({
	        	dragPanOptions: {
	        		enableKinetic: true
	        		}
	        		}) );
	// 鼠标点地理坐标控件
	if(isMousePosition) initControls.push(new SuperMap.Control.MousePosition());
}
/**
 * 添加矢量图层
 */
function addLayer(){
	// 注意图层添加的顺序
    map.addLayers([layer]);
    
    //显示地图中心
    map.setCenter(new SuperMap.LonLat(mapCenLon , mapCenLat), mapZoom);
    
    // 取消鹰眼原始的显示与隐藏按钮
    try{
    	$("#SuperMap_Control_minimizeDiv").remove();
    	$(".smMapViewport .smMap").addClass("MySmMap");
    }catch(e){}
}
/**
 * 地图管理类
 */
var MapManager = {
		/**----------set Function-----------*/
		/*设置地图名称*/
		setMapName: function (name){
			mapName = name;
		},
		/*设置rest图层名称*/
		setLayerName:function(name){
			layerName = name;
		},
		/*设置rest图层url*/
		setUrl:function(rurl){
			url = rurl;
		},
		/*分析缓冲使用的url*/
		setAnalyUrl: function(aurl){
			analyUrl = aurl;
		},
		/*是否初始化资源数据*/
		setIsInitResourceDatas: function(initRes){
			isInitResourceDatas = initRes;
		},
		/*是否直接上图*/
		setIsGPSChart: function(isChart){
			isGPSChart = isChart;
		},
		/*设置地图初始化控件*/
		setInitControls: function(controls){
			initControls = controls;
		},
		/*设置地图初始化监听事件*/
		setEventListeners:function(events){
			eventListeners = events;
		},
		/*是否显示平移缩放控件*/
		setPanzoombar: function(bo){
			isPanzoombar = bo;
		},
		/*是否显示鹰眼控件*/
		setOverviewmap:function(bo){
			isOverviewmap = bo;
		},
		/*是否显示比例控件*/
		setScaleline:function(bo){
			isScaleline = bo;
		},
		/*是否显示图层控件*/
		setLayerSwitcher:function(bo){
			isLayerSwitcher = bo;
		},
		/*是否显示鼠标事件（拖拽，双击、鼠标滚轮缩放）的地图浏览控件*/
		setNavigation:function(bo){
			isNavigation = bo;
		},
		/*是否显示鼠标点的地理坐标控件*/
		setMousePosition:function(bo){
			isMousePosition = bo;
		},
		/*设置rest图层参数*/
		setLayerParams:function(params){
			layerParams = params;
		},
		setLayerOptions:function(options){
			layerOptions = options;
		},
		/*设置地图初始化时中心点的经度*/
		setMapCenLon:function(cenLon){
			mapCenLon = cenLon;
		},
		/*设置地图初始化时中心点的纬度*/
		setMapCenLat:function(cenLat){
			mapCenLat = cenLat;
		},
		/*设置地图初始化时缩放的图层*/
		setMapZoom:function(zoom){
			mapZoom = zoom;
		},
		/*通过坐标设置地图的显示中心*/
		setCenter: function(lon, lat, zoom){
			if(!zoom) zoom = mapZoom;
			map.setCenter(new SuperMap.LonLat(lon,lat), zoom);
		},
		/**----------get Function-----------*/
		/*获取map对象*/
		getMap: function(){
			return map;
		},
		/*获取rest图层对象*/
		getBaseLayer: function(){
			return layer;
		},
		/*获取rest图层url*/
		getUrl:function(){
			return url;
		},
		/*获取分析缓冲使用的url*/
		getAnalyUrl: function(){
			return analyUrl;
		},
		/*
		 * 获取地图基础子图层
		 */
		getLayersInfo:function(url, processCompleted) {
		    //获取地图状态参数必设：url
		    var getLayersInfoService = new SuperMap.REST.GetLayersInfoService(url);
		    getLayersInfoService.events.on({ "processCompleted": processCompleted});
		    getLayersInfoService.processAsync();
		},
		/**
		 * 初始化地图
		 * @param name 地图名称
		 * @param initControls 初始加载的控件
		 */
		getMapInstance: function(name,initControls, eventListeners){
			var map = new SuperMap.Map(name,{controls: initControls, eventListeners: eventListeners});
			return map;
		},
		/**
		 * 初始化动态 REST 图层
		 */
		getLayerInstance: function (name, url, params, options){
			var layer = new SuperMap.Layer.TiledDynamicRESTLayer(name, url, params, options);
			return layer;
		},
		/**
		 * 给地图添加图层
		 */
		addLayer: function(map, layers){
			if(map && layers){
				if(layers.length > 1)
					map.addLayers(layers);
				else
					map.addLayer(layers);
			}
		},
		/**
		 * 地图添加控件
		 * @param map 地图对象
		 * @param control 控件对象
		 * @param px 控件添加的像素位置
		 */
		addControl:function(map, control, px){
			map.addControl(control,px);
		},
		/**
		 * 地图添加控件数组
		 * @param map 地图对象
		 * @param controls 要添加的控件数组
		 * @param pixels 对应控件的像素位置数组
		 */
		addControls:function(map, controls, pixels){
			map.addControls(controls, pixels);
		},
		/**
		 * 设置控件是否有效
		 * @param control 控件对象
		 * @param boolean true or false
		 */
		setControlIsActivate:function(control, boolean){
			if(boolean) control.activate();
			else control.deactivate();
		},
		/**
		 * 清除所有要素
		 * @param layer
		 */
		clearFeatures:function(layer){
			try{
				vector.removeAllFeatures();
			}catch(e){
				layer.clearMarkers();
			}
		},
		/**
		 * 清理弹出框
		 * @param map 地图对象 Popup对象必须保存一个‘type’的属性，此方法才使用
		 * @param types 要清理的type类型，格式为字符串数组
		 */
		clearPopups:function(map, types){
			var popDatas = [];
			if(!types){
				popDatas = map.popups;
				map.removeAllPopup();
			}
   			var pops = map.popups;
   			if(!pops || pops.length == 0) return [];
   			var len = pops.length;
        	for(var i = len - 1; i > -1; i--){
        		var pop = pops[i];
        		for(var j = 0; j < types.length; j++){
        			if(types[j] == pop.type){
        				popDatas.push(pop);
            			map.removePopup(pop);
            		}
        		}
        	}
        	return popDatas;
		},
		/**
		 * 清理浮标  Marker对象必须保存一个‘type’的属性，此方法才使用
		 * @param mkLayer 浮标图层对象
		 * @param types 要清理的type类型，格式为字符串数组
		 */
		clearMarkers:function(mkLayer, types){
			var mkerDatas = [];
			var mkers = mkLayer.markers;
			if(!mkers || mkers.length == 0) return [];
			if(!types) {
				mkerDatas = mkers;
				mkLayer.clearMarkers();
			}
			var len = mkers.length;
        	for(var i = len-1; i > -1; i--){
        		var mker = mkers[i];
        		for(var j = 0; j < types.length; j++){
        			if(types[j] == mker.type){
        				mkerDatas.push(mker);
        				mkLayer.removeMarker(mker);
            		}
        		}
        	}
        	return mkerDatas;
		},
		/**
		 * 清理浮标指定id的浮标
		 * @param mkLayer 浮标图层对象
		 * @param id 浮标marker的id值
		 */
		clearMarkerById:function(mkLayer, id){
			var mker = null;
			var mkers = mkLayer.markers;
			if(!mkers || mkers.length == 0 || !id) return;
			var len = mkers.length;
        	for(var i = len-1; i > -1; i--){
        		mker = mkers[i];
    			if(id == mker.id){
    				mkLayer.removeMarker(mker);
    				return mker;
        		}
        	}
		},
		/**
		 * 创建矢量图层
		 * @param name 图层名称
		 * @param options 此类与父类提供的属性
		 * @param map 地图对象
		 * @returns {SuperMap.Layer.Vector}
		 */
		createVectorLayer: function(name, options, map){
			if(!name) name = "vectorLayer";
			var vector = new SuperMap.Layer.Vector(name, options);
			if(map) map.addLayer(vector);
			return vector;
		},
		/**
		 * 创建标记图层
		 * @param name 图层名称
		 * @param options 此类与父类提供的属性
		 * @param map 地图对象
		 * @returns {SuperMap.Layer.Markers}
		 */
		createMarkerLayer: function(name, options, map){
			if(!name) name = "markers";
			var marker = new SuperMap.Layer.Markers(name, options);
			if(map) map.addLayer(marker);
			return marker;
		},
		/**
		 * 创建一个矢量动画图层
		 * @param name 图层名称
		 * @param options 此类与父类提供的属性
		 * @param animatiorOption 动画Animator提供的属性。这些属性都可以用过animatorVector.animator来设置
		 * @param map 地图对象
		 * @param drawfeaturestart 事件，每次绘制在当前时间节点内的feature时触发
		 * @returns {SuperMap.Layer.AnimatorVector}
		 */
		createAnimatorVector: function(name, options, animatiorOption, map, drawfeaturestart){
			if(!name) name = "animatorVector";
			var animatorVector = new SuperMap.Layer.AnimatorVector(name, options, animatiorOption);
			if(drawfeaturestart) animatorVector.events.on({"drawfeaturestart": drawfeaturestart});
			if(map) map.addLayer(animatorVector);
			return animatorVector;
		},
		/**
		 * 创建聚散点图层
		 * @param name 图层名称
		 * @param options 此类与父类提供的属性
		 * @param map 地图对象
		 * @returns {SuperMap.Layer.ClusterLayer}
		 */
		createClusterLayer: function(name, options, map){
			if(!name) name = "clusterLayer";
			var clusterLayer = new SuperMap.Layer.ClusterLayer(name, options);
			if(map) map.addLayer(clusterLayer);
			return clusterLayer;
		},
		/**
		 * 创建热点渲染图层
		 * @param name 图层名称
		 * @param options 此类与父类提供的属性
		 * @param map 地图对象
		 * @returns {SuperMap.Layer.HeatMapLayer}
		 */
		createHeatMapLayer: function(name, options, map){
			if(!name) name = "heatMapLayer";
			var heatMapLayer = new SuperMap.Layer.HeatMapLayer(name, options);
			if(map) map.addLayer(heatMapLayer);
			return heatMapLayer;
		},
		/**
		 * 创建绘制要素
		 * @param map 地图对象
		 * @param layer 执行绘制要素的图层
		 * @param isActivate 控件是否立即生效
		 * @param handler 要素绘制事件处理器，指定当前绘制的要素类型和操作方法
		 * @param options 设置该类及其父类开放的属性
		 * @returns {SuperMap.Control.DrawFeature}
		 */
		createDrawFeature: function(map, layer, isActivate,handler, options){
			if(!layer || !handler) return null;
			var drawFeature = new SuperMap.Control.DrawFeature(layer,handler, options);
			if(map) map.addControl(drawFeature);
			if(isActivate) drawFeature.activate();
			return drawFeature;
		},
		/**
		 * 创建矢量要素编辑控件
		 * @param map 地图对象
		 * @param layer 执行绘制要素的图层
		 * @param isActivate 控件是否立即生效
		 * @param editCompleted 编辑完成的回调函数
		 * @returns {SuperMap.Control.ModifyFeature}
		 */
		createModifyFeature: function(map, layer, isActivate, editCompleted){
			var modifyFeature = new SuperMap.Control.ModifyFeature(layer);
			if(map) map.addControl(modifyFeature);
			if(isActivate) modifyFeature.activate();
			// 当图层上的要素编辑完成时，触发该事件
			if(layer) layer.events.on({"afterfeaturemodified": editCompleted});
			return modifyFeature;
		},
		/**
		 * 创建要素选择控件
		 * @param map 地图对象
		 * @param layer 作用的目标图层
		 * @param isActivate 控件是否立即生效
		 * @param onFeatureSelected 要素被选中的事件
		 * @param callbacks 事件函数集合
		 * @returns {SuperMap.Control.SelectFeature}
		 */
		createSelectFeature: function(map, layer, isActivate, onFeatureSelected, callbacks){
			if(!onFeatureSelected) onFeatureSelected = function(){}
			var selectFeature = new SuperMap.Control.SelectFeature(layer, {
			    onSelect: onFeatureSelected,
			    callbacks: callbacks,
			    hover: false
			});
			if(map) map.addControl(selectFeature);
			if(isActivate) selectFeature.activate();
			return selectFeature;
		},
		/**
		 * 创建一个支持SuperMap.Layer.ClusterLayer的选择要素的控件
		 * @param map 地图对象
		 * @param layer 作用的目标图层
		 * @param isActivate 控件是否立即生效
		 * @param callbacks 事件函数集合
		 * @returns {SuperMap.Control.SelectCluster}
		 */
		createSelectCluster: function(map, layer, isActivate, callbacks){
			var selectCluster = new SuperMap.Control.SelectCluster(layer, {
			    callbacks: callbacks,
			});
			if(map) map.addControl(selectCluster);
			if(isActivate) selectCluster.activate();
			return selectCluster;
		},
		/**
		 * 创建Marker标识
		 * @param markerlayer Marker图层
		 * @param lonLat	坐标信息
		 * @param iconUrl 图片路径
		 * @param iconSize 图片显示大小
		 * @param click marker点击事件
		 * @param rightclick marker右键事件
		 * @returns {SuperMap.Marker}
		 */
		createMarker: function(markerlayer, lonLat, iconUrl, iconSize, click, rightclick){
			if(!iconUrl) iconUrl = "../supermap/theme/images/marker.png";
			var size = null;
			if(!iconSize) 
				size = new SuperMap.Size(32,28);
			else
				size = new SuperMap.Size(iconSize.w,iconSize.h);
			var offset = new SuperMap.Pixel(-(size.w/2), -size.h);
			var marker = new SuperMap.Marker(new SuperMap.LonLat( lonLat.lon, lonLat.lat), new SuperMap.Icon(iconUrl, size, offset));
			marker.events.on({
				"click": click,
		        "rightclick": rightclick,
				"scope": marker
			 });
			if(markerlayer) markerlayer.addMarker(marker);
			return marker;
		},
		/**
		 * 创建点
		 * @param data 点坐标的数组对象 如：[101,101]
		 * @returns {SuperMap.Geometry.Point}
		 */
		createPoint: function(data){
			var point = new SuperMap.Geometry.Point(data[0],data[1]);
			return point;
		},
		/**
		  * 创建线
		 * @param points 点的数组
		 * @returns {SuperMap.Geometry.LineString}
		 */
		createLineString: function(points){
			var line = new SuperMap.Geometry.LineString(points);
			return line;
		},
		/**
		 * 创建多边形面
		 * @param points 点的数组
		 * @returns {SuperMap.Geometry.Polygon}
		 */
		createPolygon: function(points){
			var linearRing=new SuperMap.Geometry.LinearRing(points);
            var polygon=new SuperMap.Geometry.Polygon([linearRing]);
            return polygon;
		},
		/**
		 * 创建矩形面
		 * @param points 点的数组
		 * @returns {SuperMap.Geometry.Polygon}
		 */
		createMultiPolygon: function(points){
			var linearRing=new SuperMap.Geometry.LinearRing(points);
            var polygon=new SuperMap.Geometry.Polygon([linearRing]);
            var multiPolygon = new SuperMap.Geometry.MultiPolygon([polygon]);
            return multiPolygon;
		},
		/**
		 * 创建点要素
		 * @param data 点坐标的数组对象 如：[101,101]
		 * @param layer 添加点的目标图层
		 * @returns {SuperMap.Feature.Vector}
		 */
		createPointFeature: function(data, layer){
			if(!data || data.length > 2) return null;
			var point = MapManager.createPoint(data);
			var feature = new SuperMap.Feature.Vector(point);
			if(layer) layer.addFeatures(feature);
			return feature;
		},
		/**
		 * 绘制线要素
		 * @param data 点坐标的数组对象 如：[[113,19],[107,-2],[92,13],[90,21],[82,12]];
		 * @param layer 添加点的目标图层
		 * @returns {SuperMap.Feature.Vector}
		 */
		createLineFeature: function(data, layer){
			// 绘制线 需至少2个点
			if(!data || data.length < 2) return null;
			var points = [];
			for(var i = 0; i < data.length; i++){
				points.push(MapManager.createPoint(data[i]));
			}
			var line = MapManager.createLineString(points);
			var feature=new SuperMap.Feature.Vector(line);
			if(layer) layer.addFeatures(feature);
			return feature;
		},
		/**
		 * 绘制面要素
		 * @param data 点坐标的数组对象 如：[[113,19],[107,-2],[92,13],[90,21],[82,12],[74,3],[64,22]];
		 * @param layer 添加点的目标图层
		 * @returns {SuperMap.Feature.Vector}
		 */
		createPolygonFeature:function(data, layer){
			// 绘制面 需至少3个点
			if(!data || data.length < 3) return null;
			var points = [];
			for(var i = 0; i < data.length; i++){
				points.push(MapManager.createPoint(data[i]));
			}
             var polygon=MapManager.createPolygon(points);
             var feature=new SuperMap.Feature.Vector(polygon);
 			if(layer) layer.addFeatures(feature);
 			return feature;
		},
		/**
		 * 生成消息框（Popup）
		 * @param id {String} 弹窗的唯一标识ID
		 * @param obj 对象(存储消息框显示用的坐标数据),格式：{lon: 111, lat: 222}
		 * @param content 显示内容
		 * @param size 消息框大小
		 * 	@param color 背景颜色
		 * @param opacity 透明度 0.1--1
		 * @param isClose 是否显示 关闭按钮
		 * @param closeBoxCallback 关闭弹窗触发该回调函数
		 * @param closeOnMove 当地图平移时，关闭弹窗。 默认为false
		 */
		openPopup: function(id, obj, content, size, color, opacity,isClose,closeBoxCallback, closeOnMove ){
			if(isClose == null) isClose = true;
			if(!size || size.w < 60){
				size = new Object();
				size.w = 100; 
			}
			if(!size.h){
				size.h = 20;
			}
			var popup = new SuperMap.Popup(
					id, new SuperMap.LonLat(obj.lon, obj.lat),
					new SuperMap.Size(size.w,size.h),
					content, isClose, closeBoxCallback);
			// 设置背景为"",则透明
			if(color) popup.setBackgroundColor(color);
			if(opacity) popup.setOpacity(opacity);
			if(closeOnMove != null) popup.closeOnMove = closeOnMove;
			return popup;
		},
		/**
		 * 生成消息框（Anchored）
		 * @param  id {String} 弹窗的唯一标识ID
		 * @param obj 对象(存储消息框显示用的经纬度数据),格式：{lon: 111, lat: 222}
		 * @param content 显示内容
		 * @param size 消息框大小
		 * @param isClose 是否显示 关闭按钮
		 * @param closeBoxCallback 弹窗关闭事件回调处理
		 */
		openAnchored: function (id,obj, content, size, isClose, closeBoxCallback){
			if(!size || size.w < 60){
				size = new Object();
				size.w = 100; 
			}
			if(!size.h){
				size.h = 20;
			}
			var lonLat = null;
			if(obj){
				lonLat = new SuperMap.LonLat(obj.lon, obj.lat);
			}
			var popup = new SuperMap.Popup.Anchored(
					id,lonLat ,new SuperMap.Size(size.w,size.h),
					content, null, isClose, closeBoxCallback);
			return popup;
		},
		/**
		 * 生成消息框（FramedCloud）
		 * @param  id {String} 弹窗的唯一标识ID
		 * @param obj 对象(存储消息框显示用的经纬度数据),格式：{lon: 111, lat: 222}
		 * @param content 显示内容
		 * @param size 消息框大小
		 * @param isClose 是否显示 关闭按钮
		 * @param closeBoxCallback 弹窗关闭事件回调处理
		 */
		openFramedCloud: function (id,obj, content, size, isClose, closeBoxCallback){
			if(!id || id == "") id = "popwin";
			if(!size || size.w < 60){
				size = new Object();
				size.w = 100; 
			}
			if(!size.h){
				size.h = 20;
			}
			var lonLat = null;
			if(obj){
				lonLat = new SuperMap.LonLat(obj.lon, obj.lat);
			}
		    var popup = new SuperMap.Popup.FramedCloud(id,
		    		lonLat,
		    		new SuperMap.Size(size.w,size.h),
		    		content,
		    		null,
		            isClose,
		            closeBoxCallback);
		    return popup;
		},
		/**
		 * 量算距离、面积
		 * @param url 地图url
		 * @param geometry 量算的地物间的距离或某个区域的面积
		 * @param measureMode 类型进行判断和赋值，当判断出是LineString时设置SuperMap.REST.MeasureMode.DISTANCE，否则是SuperMap.REST.MeasureMode.AREA
		 * @param measureCompleted 回调函数
		 */
		measureFunction: function(url, geometry, measureMode, measureCompleted){
			if(!url) url = MapManager.getUrl();
			if(!measureMode) measureMode = SuperMap.REST.MeasureMode.DISTANCE;
			var measureParam,myMeasuerService;
			measureParam = new SuperMap.REST.MeasureParameters(geometry), /* MeasureParameters：量算参数类。 客户端要量算的地物间的距离或某个区域的面积*/
			myMeasuerService = new SuperMap.REST.MeasureService(url); //量算服务类，该类负责将量算参数传递到服务端，并获取服务端返回的量算结果
			myMeasuerService.events.on({ "processCompleted": measureCompleted });
			//对MeasureService类型进行判断和赋值，当判断出是LineString时设置MeasureMode.DISTANCE，否则是MeasureMode.AREA
			myMeasuerService.measureMode = measureMode;
		   	myMeasuerService.processAsync(measureParam); //processAsync负责将客户端的量算参数传递到服务端。
		},
		/**
		 * 对生成的线路进行缓冲区分析
		 * @param url 分析的数据路径
		 * @param feature 类型
		 * @param bdistance 半径大小
		 * @param sLineSegment 缓冲区指定点的数量
		 * @param analystCompleted 分析成功的回调方法
		 * @param analystFailed 分析失败的回调方法
		 */
		bufferAnalystProcess:function(url,feature, bdistance,sLineSegment, analystCompleted, analystFailed) { 
			if(!bdistance) bdistance = 120;
		    var bufferServiceByGeometry = new SuperMap.REST.BufferAnalystService(url),
		            bufferDistance = new SuperMap.REST.BufferDistance({
		                value: bdistance
		            }),
		            bufferSetting = new SuperMap.REST.BufferSetting({
		                endType: SuperMap.REST.BufferEndType.ROUND,
		                leftDistance: bufferDistance,
		                rightDistance: bufferDistance,
		                semicircleLineSegment: sLineSegment
		            }),
		            geoBufferAnalystParam = new SuperMap.REST.GeometryBufferAnalystParameters({
		                sourceGeometry: feature,
		                bufferSetting: bufferSetting
		            });
		    bufferServiceByGeometry.events.on(
		            {
		                "processCompleted": analystCompleted, "processFailed": analystFailed
		            });
		    bufferServiceByGeometry.processAsync(geoBufferAnalystParam);
		},
		/**
		 * 通过SQL进行范围查询 (地图查询方式)
		 * @param url 查询图层的url
		 * @param params 参数，格式：{name: "Countries@World.1", attributeFilter: "Pop_1994>1000000000 and SmArea>900"}
		 * @param queryCompleted 成功的回调方法
		 * @param queryFailed 失败的回调方法
		 */
		queryBySQL: function(url,params, queryCompleted, queryFailed){
			var queryParam, queryBySQLParams, queryBySQLService;
			queryParam = new SuperMap.REST.FilterParameter({
				name: params.name,
				attributeFilter: params.attributeFilter
			});
			queryBySQLParams = new SuperMap.REST.QueryBySQLParameters({
				queryParams: [queryParam]
			});
			queryBySQLService = new SuperMap.REST.QueryBySQLService(url, {
			eventListeners: {"processCompleted": queryCompleted, "processFailed": queryFailed}});
			queryBySQLService.processAsync(queryBySQLParams);
		},
		/**
		 *  通过几何对象进行范围查询 (地图查询方式)
		 * @param url 查询图层的url
		 * @param params 查询过滤参数
		 * @param geometry 几何类型
		 * @param queryMode 查询类型
		 * @param queryCompleted 成功的回调方法
		 * @param queryFailed 失败的回调方法
		 */
		queryByGeometry:function(url,params, geometry, queryMode, queryCompleted, queryFailed){
			if(!queryMode) queryMode = SuperMap.REST.SpatialQueryMode.INTERSECT;
		    var queryParam, queryByGeometryParameters, queryService;
		    queryParam = new SuperMap.REST.FilterParameter({name: params.name}); 
		    queryByGeometryParameters = new SuperMap.REST.QueryByGeometryParameters({
		        queryParams: [queryParam],
		        geometry: geometry,
		        spatialQueryMode: queryMode
		    });
		    queryService = new SuperMap.REST.QueryByGeometryService(url, {
		        eventListeners: {
		            "processCompleted": queryCompleted,
		            "processFailed": queryFailed
		        }
		    });
		    queryService.processAsync(queryByGeometryParameters);
		},
		/**
		 * 距离查询
		 * @param url 查询图层的url
		 * @param params 查询过滤参数
		 * @param geometry 几何类型
		 * @param distance 查询距离
		 * @param queryCompleted 成功的回调方法
		 * @param queryFailed 失败的回调方法
		 */
		queryByDistance: function (url, params, geometry, distance, queryCompleted, queryFailed) {
			if(!distance) distance = 100;
			var queryByDistanceParams = new SuperMap.REST.QueryByDistanceParameters({
				queryParams: new Array(new SuperMap.REST.FilterParameter({name: params.name})),
				returnContent: true,
				distance: distance,
				isNearest: true,
				expectCount: 1,
				geometry: geometry
			});
			var queryByDistanceService = new SuperMap.REST.QueryByDistanceService(url);
			queryByDistanceService.events.on({
				"processCompleted": queryCompleted,
				"processFailed": queryFailed
			});
			queryByDistanceService.processAsync(queryByDistanceParams);
		},
		/**
		 * 删除popup byId
		 * @param map 
		 * @param popId
		 * @param geometry 几何类型
		 * @param distance 查询距离
		 * @param queryCompleted 成功的回调方法
		 * @param queryFailed 失败的回调方法
		 */
		clearPopupsById : function(map, popId) {
		     if (!popId)map.removeAllPopup();
		     var pops = map.popups;
		     if (pops != undefined) {
			   $.each(pops, function(i, pop) {
				 if (pop!=undefined  && popId == pop.id) {
					map.removePopup(pop);
					return;
				 }
			});
		}
	}
}


/**
 * 实体类 （卡口、卡点、天网、警员、警车 等等）
 * @param entity 实体属性参数
 * @returns
 */
function Entity(entity){
	if(entity){
		// 类型, 1：天网、2： 卡口、 3：卡点、4：警员:5：警车、6：巡区:、7：社区、8：辖区
		this.type = entity.type; 
		//标识id
		this.id = entity.id; 
		//名称
		this.name = entity.name; 
		//内容描述
		this.content = entity.content;
		//图片路径
		this.iconUrl = entity.iconUrl; 
		//纬度
		this.latitude = entity.latitude;		
		//经度
		this.longitude = entity.longitude;		
		//创建时间
		this.createDate = entity.createDate;  
		//备注说明
		this.des = entity.des;	
		// 其他属性信息(对象)
		this.detailInfo = entity.detailInfo; 
	}

	this.setType = function(type){
		this.type = type;
	};
	this.setId = function(id){
		this.id = id;
	};
	this.setName = function(name){
		this.name = name;
	};
	this.setContent = function(content){
		this.content = content;
	};
	this.setIconUrl = function(iconUrl){
		this.iconUrl = iconUrl;
	};
	this.setLatitude = function(lat){
		this.latitude = lat;
	};
	this.setLongitude = function(lon){
		this.longitude = lon;
	};
	this.setDes = function(des){
		this.des = des;
	};
	this.setCreateDate = function(createDate){
		this.createDate = createDate;
	};
	this.setDetailInfo = function(dInfo){
		this.detailInfo = dInfo;
	};
}
//调用方式
/*var en = new Object();
en.name = "my";
var entity = new Entity(en);
entity.setName("test");
alert(entity.name);*/

/**
 * 服务端资源接口信息
 */
var MapResourceUrl = {
		/*获取天网地址*/
		getGBDevices: "client/GBPlatForm/getGBDeviceListByOrganId.do",
		/*获取卡口地址*/
		getBayonets: "client/bayonet/getBayonetList.do",
		/*获取卡点地址*/
		getCardPoints: "cardPoint/queryCardPointList.do",
		/*获取巡区、社区、辖区*/
		getAreaInfo: "area/queryAreaList.do",
		/*获取警员数据*/
		getPolices: "police/getPoliceDutyInfo.do",
		/*获取警车数据*/
		getPoliceCars: "vehicle/getVehicleDutyInfo.do",
		/*获取树形结构的圈层数据，包括圈层下的卡点*/
		getCricleLayers: "map/queryCircleLayerList.do",
		/*修改卡点的经纬度*/
		modifyCardPointCoordinate:"web/cardPoint/modifyCardPointCoordinate.do",
		/*修改卡口的经纬度*/
		modifyBayonetCoordinate:"client/bayonet/update.do",
		/*获取资源收藏数据*/
		getGroupDetailInfo:"client/groupManager/getGroupDetailInfo.do",
		/*删除收藏数据*/
		deleteGroupInfo:"client/groupManager/deleteGroupInfo.do"
		
};

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