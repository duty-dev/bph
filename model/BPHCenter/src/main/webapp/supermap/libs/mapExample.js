//声明变量map、layer、markerlayer、marker、url、panzoombar、overviewmap、
   // scaleline
  var map,layer, markerlayer,panzoombar,overviewmap,scaleline,testVectorLayer;
  var tempLayerID;//子图层id
   var  marker, markCenter, mk;
   var drawPoint, drawLine,drawPolygon,regularPolygon,drawRectangle;
   var drawLineMath, drawPolygonMath;
   var gmarker,menu, snap01;
   var mymodifyFeature,vector,snapState=false;
   var animatorVector,styleCar1=
   		{
           externalGraphic: "http://localhost:8080/BPHCenter/supermap/theme/images/blueCar.png",
           graphicWidth:32,
           graphicHeight:32
       };
   var vector_style,style = {
		   strokeColor: "#304DBE",
		   strokeWidth: 2,
		   pointerEvents: "visiblePainted",
		   fillColor: "#304DBE",
		   fillOpacity: 0.8
		   };
   var menu,mkMenu, selectFeature;
   var drawPointBounds, drawLineBounds, drawPolygonBounds;
   var boundsObj = null; // 点周边、线周边 保存的公共对象
   var boundspoint = null;// 点周边、线周边 保存的公共点对象
   // 聚散图层
   var clusterLayer, selectCluster;
   // 热点图层
   var heatMapLayer;
   //var url="http://support.supermap.com.cn:8090/iserver/services/map-china400/rest/maps/China";  //中国范围
  //url = "http://support.supermap.com.cn:8090/iserver/services/map-world/rest/maps/World";	//世界范围
  url = "http://25.30.9.42:8090/iserver/services/map-changchun/rest/maps/长春市区图"; //长春市范围
  var url2 = "http://25.30.9.42:8090/iserver/services/spatialanalyst-changchun/restjsr/spatialanalyst";//长春市范围(缓冲区)
  //var url =  "http://25.30.9.42:8090/iserver/services/map-ChengDu/rest/maps/chengduMap"; //成都市区范围
  

//创建地图控件
function initMap()
{
	getLayersInfo();
	initCompents();
	
	map = new SuperMap.Map("map",{controls:[
	       new SuperMap.Control.Zoom() ,
	        new SuperMap.Control.Navigation({
	        	dragPanOptions: {
	        		enableKinetic: true
	        		}
	        		}) ,
	        new SuperMap.Control.LayerSwitcher(),
	        new SuperMap.Control.MousePosition(),mymodifyFeature
	    ], eventListeners:{
	    	"movestart":function(){
		        menu.style.visibility="hidden";
		    },
	        "click":function(){
	            menu.style.visibility="hidden";
	        }
	    }
	});
	var initZoom = false;
    map.events.on({"click": callbackFunction,
    	"mousemove":getMousePositionPx,
	    "zoomend": function(arg){
	    	if(!initZoom){
	    		initZoom = true;
	    		return;
	    	}
	    	var mk = map.getLayersByName("markerLayer")[0];
	    	if(map.getZoom() > 3){ // 当层数大于3时，则隐藏名为“markerLayer”的图层
	    		if(!mk.getVisibility()) mk.setVisibility(true);
	    	}else{
	    		if(mk.getVisibility()) mk.setVisibility(false);
	    	}
	    },
    	"scope": map});
    map.events.register("mousedown");
    
    addData();
}

function initCompents(){
	vector_style = new SuperMap.Style({
		strokeColor: "#00FF00",
		strokeOpacity: 1,
		strokeWidth: 3,
		fillColor: "#FF5500",
		fillOpacity: 0.5,
		pointRadius: 6,
		pointerEvents: "visiblePainted",
		graphicZIndex: 1,
		// label with \n linebreaks
		label: "${name}",
		labelSelect: true,
		fontColor: "${favColor}",
		fontSize: "14px",
		fontFamily: "Courier New, monospace",
		fontWeight: "bold",
		labelAlign: "${align}",
		labelXOffset: "${xOffset}",
		labelYOffset: "${yOffset}",
		labelOutlineColor: "white",
		labelOutlineWidth: 3,
		externalGraphic: "http://localhost:8080/BPHCenter/supermap/theme/images/MapIcos/XunPoliceIco.png",
		graphicOpacity: 1,
        graphicWidth:32,
        graphicHeight:32
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
	vector = new SuperMap.Layer.Vector("vectorLayer");
	//新建一个策略并使用在矢量要素图层(vector)上。
	var strategy = new SuperMap.Strategy.GeoText();
	strategy.style = {
	  fontColor:"#FF7F00",
	  fontWeight:"bolder",
	  fontSize:"14px",
	  fontSize:"14px",
	  fill: true,
	  fillColor: "#FFFFFF",
	  fillOpacity: 0.2,
	  stroke: true,
	  strokeColor:"#8B7B8B"
	};
	// 测试矢量图层
	testVectorLayer = new SuperMap.Layer.Vector("testLayer",{strategies: [strategy]}); 
	//testVectorLayer.geometryType = SuperMap.Geometry.Point; // 对图层要素限制
	//创建聚散图层并添加layers
    clusterLayer = new SuperMap.Layer.ClusterLayer("Cluster");
	// DrawFeature--鼠标拖拽要素类
    drawPoint = MapManager.createDrawFeature(map,testVectorLayer, false,SuperMap.Handler.Point, { multi: true});
    drawPoint.events.on({"featureadded": drawPointCompleted} );
	drawLine = new SuperMap.Control.DrawFeature(vector, SuperMap.Handler.Path, { multi: true});
	drawLine.events.on({"featureadded":drawLineCompleted});
	drawPolygon = new SuperMap.Control.DrawFeature(vector, SuperMap.Handler.Polygon);
	drawPolygon.events.on({"featureadded": drawPolygonCompleted});
	/*绘制规则多边形（包括正方形、圆）
	 * sides: {Integer} 规则多边形的边的数量，要求必须大于2。默认的值为4
	 */
	regularPolygon = new SuperMap.Control.DrawFeature(vector, SuperMap.Handler.RegularPolygon, {handlerOptions:{sides:50}});
	regularPolygon.events.on({"featureadded": regularPolygonCompleted});
	//矩形
    drawRectangle = new SuperMap.Control.DrawFeature(vector, SuperMap.Handler.Box);
    drawRectangle.events.on({"featureadded":  drawRectangleCompleted});
	
	drawLineMath = new SuperMap.Control.DrawFeature(vector, SuperMap.Handler.Path, { multi: true});
	/*
	注册featureadded事件,触发drawCompleted()方法
	例如注册"loadstart"事件的单独监听
	events.on({ "loadstart": loadStartListener });
	*/
	drawLineMath.events.on({"featureadded": drawCompleted});
	// (面积量算)
	drawPolygonMath = new SuperMap.Control.DrawFeature(vector, SuperMap.Handler.Polygon);
	drawPolygonMath.events.on({"featureadded": drawCompletedArea});
	// 动画移动 rendererType	{String} 渲染类型，当前支持： 1、基本动画渲染：”AnimatorCanvas” 2、点闪烁、尾巴渲染：”TadpolePoint” 3、线伸缩渲染：”StretchLine” 4、点发射渲染：”RadiatePoint” 默认为 “AnimatorCanvas”
	animatorVector = new SuperMap.Layer.AnimatorVector("Cars", {rendererType:"TadpolePoint"} ,{
        //设置速度为每帧播放0.01小时的数据（设置为0.05时，移动速度想对于0.01就越快）
        speed:0.01,
        //开始时间为0晨
        startTime:0,
        //结束时间设置为最后运行结束的汽车结束时间
        endTime:55
    });
	animatorVector.events.on({"drawfeaturestart": drawfeaturestart});
	// 点周边绘制
	drawPointBounds = new SuperMap.Control.DrawFeature(testVectorLayer, SuperMap.Handler.Point, { multi: true});
	drawPointBounds.events.on({"featureadded": drawBoundsCompleted} );
	// 线周边绘制
	drawLineBounds = new SuperMap.Control.DrawFeature(testVectorLayer, SuperMap.Handler.Path, { multi: true});
	drawLineBounds.events.on({"featureadded": drawBoundsCompleted} );
	// 多边形绘制（查询）
	drawPolygonBounds = new SuperMap.Control.DrawFeature(testVectorLayer, SuperMap.Handler.Path, { multi: true});
	drawPolygonBounds.events.on({"featureadded": drawBoundsCompleted} );
	
	selectCluster = new SuperMap.Control.SelectCluster(clusterLayer,{
        callbacks:{
            click:function(f){ //点击兴趣点弹出信息窗口
                closeInfoWinCluster();
                if(!f.isCluster){ //当点击聚散点的时候不弹出信息窗口
                    openInfoWinCluster(f);
                }
            },
            clickout:function(){       //点击空白处关闭信息窗口
                closeInfoWinCluster();
            }
        }
    });
	
    layer= new SuperMap.Layer.TiledDynamicRESTLayer("chengduMap", url,  {transparent: true, cacheEnabled: false, redirect: true, layersID: tempLayerID}, {maxResolution: "auto", bufferImgCount: 0});
	layer.events.on({"layerInitialized":addLayer});

	markerlayer = new SuperMap.Layer.Markers("markerLayer");
	
	snap01=new SuperMap.Snap([vector],4,2,{actived:true});
    //矢量要素编辑控件
	mymodifyFeature = new SuperMap.Control.ModifyFeature(vector);
	mymodifyFeature.snap=snap01;
    
	// 初始化 marker右键功能
	mkMenu = document.getElementById("mkMenu");
	menu= document.getElementById("myMenu");
	EventUtil.addHandler(window,"load",function(event){
         EventUtil.addHandler(myMenu,"contextmenu",function(event){
             event= EventUtil.getEvent(event);
             EventUtil.preventDefault(event);
             menu.style.visibility="visible";
         });
         EventUtil.addHandler(document,"click",function(event){
             menu.style.visibility="hidden";
         });
	});
	var callbacks = {
			click: function(currentFeature){
				map.removeAllPopup();
				var geo = currentFeature.geometry;
				if(!geo.x &&!geo.components){return;}
				if(geo.x || geo.components[0].x){ // 判断是否是选择 卡点或者卡口
					var s = new Object();
					var c = "";
					var obj = currentFeature.attributes.obj;
					if(currentFeature.attributes.pointType){
						s.w = "270px"; s.h = "300px;";
						 c = "<div style='width: 100%; height: 100%; '>";
						c += "<div style='position:absolute;width:20px; height: 95%; '><img src='"+currentFeature.attributes.imgurl+"'/></div>";
						c += "<div style='position:absolute;left: 23px; width: 72%; height: 95%; '>";
						c += "<table width='100%' height='100%'><tr><td width='100px'>卡点名称：</td> <td width='160px'>"+obj.name+"</td><tr>";
						c += "<tr><td>负责人：</td> <td>张三</td><tr>";
						c += "<tr><td>联系电话：</td> <td>"+obj.tel+"</td><tr>";
						c += "<tr><td>通信组号：</td> <td>"+obj.communityGroupNum+"</td><tr>";
						c += "<tr><td>所属圈层：</td> <td>第二圈层</td><tr>";
						c += "</table></div></div>";
					 }else{ 
						s.w = "450px"; s.h = "350px;";
						c = "<div style='width: 100%; height: 100%; '>";
						c += "<div style='position:absolute;width:120px; height: 95%; '><img src='"+currentFeature.attributes.imgurl+"'/></div>";
						c += "<div style='position:absolute;left: 123px; width: 62%; height: 95%; '>";
						c += "<table width='100%' height='100%'><tr><td width='120px'>卡口状态：</td> <td>待建 </td><tr>";
						c += "<tr><td>卡口名称：</td> <td>"+obj.name+"</td><tr>";
						c += "<tr><td>卡口方向：</td> <td>东南</td><tr>";
						c += "<tr><td>车道名称：</td> <td>1</td><tr>";
						c += "<tr><td>管辖单位：</td> <td>成都公安局</td><tr>";
						c += "</table></div></div>";
					 }
					openWin(geo.components  ? geo.components[0] : geo, c, s, "#2A3D47", 0.7);
				}else{
					var s = new Object();
					var c = "";
					var obj = currentFeature.attributes.obj;
					s.w = "270px"; s.h = "60px;";
					c = "<div style='width: 100%; height: 100%; '>";
					c += "<span style='margin-bottom: 15px;'>请输入缓冲区分析的距离值：</span><span>";
					c += "<input id='boundStyle' type='text' style='width: 180px;'/>";
					c += "&nbsp;&nbsp;<button onclick='changeStyle()'  style='width: 60px; height: 25px;'>确定</button>";
					c += "</span></div>";
					var boundsObj = boundspoint
					if(boundspoint){
						if(!boundspoint.x){
							boundsObj = boundspoint.components[boundspoint.components.length - 1];
						}
						openWin(boundsObj, c, s, "#2A3D47", 0.7);
					}
				}
			},
			clickout: function(feature){
				map.removeAllPopup();
			}
		};
		selectFeature = new SuperMap.Control.SelectFeature(testVectorLayer,{
			callbacks: callbacks
		});
		
		heatMapLayer = new SuperMap.Layer.HeatMapLayer(
                "heatMap",
                {
                    "radius":45,
                    "featureWeight":"value",
                    "featureRadius":"geoRadius"
                }
        );
		// 为热点设置渐变颜色
		/*var colors = [
		              new  SuperMap.REST.ServerColor(255,240,233),
		              new  SuperMap.REST.ServerColor(180,245,185),
		              new  SuperMap.REST.ServerColor(223,250,177)
		         ];
		heatMapLayer.colors = colors;*/
}

function addLayer(){
	// 注意图层添加的顺序
    map.addLayers([layer,markerlayer]);
    map.addLayer(vector);
    map.addLayer(animatorVector);
    // 热点图层
    map.addLayer(heatMapLayer);
 // 聚散点图层
    map.addLayer(clusterLayer);
    // 矢量图层
    map.addLayer(testVectorLayer);
    
    map.addControl(panzoombar);
    //map.addControl(overviewmap);
    MapManager.addControl(map, overviewmap);
    map.addControl(scaleline);

    map.addControl(drawPoint);
    map.addControl(drawLine);
    map.addControl(drawPolygon);
    map.addControl(regularPolygon);
    map.addControl(drawRectangle);
    
    map.addControl(drawPointBounds);
    map.addControl(drawLineBounds);
    map.addControl(drawLineMath);
    map.addControl(drawPolygonMath);
    map.addControl(selectFeature);
    
    map.addControl(selectCluster);
    
    selectCluster.activate();
    selectFeature.activate();
    //显示地图中心
    //map.setCenter(new SuperMap.LonLat(104.08 , 30.71), 1); // 成都地图中心
    map.setCenter(new SuperMap.LonLat(5105, -3375), 4);      // 长春市区地图中心
}
  //添加数据
 function addData()
 { 
	 
	/* drawPoint = MapManager.toobar.initDrawFeature(map,
			 testVectorLayer, 
			 SuperMap.Handler.Point,
			 { multi: true},
			 false,
			 drawPointCompleted);
	 drawLineBounds = MapManager.toobar.initDrawFeature(map,
			 testVectorLayer, 
			 SuperMap.Handler.Path,  
			 { multi: true},
			 false,
			 drawBoundsCompleted);*/
	 
    	  //数据1
	markerlayer.removeMarker(marker);
	var size = new SuperMap.Size(44,33);
	var offset = new SuperMap.Pixel(-(size.w/2), -size.h);
	var icon = new SuperMap.Icon(basePath+"supermap/theme/images/marker.png", size, offset);
	    marker =new SuperMap.Marker(new SuperMap.LonLat(104.06662 , 30.70079),icon) ;
	    marker.attributes = {
	    		name: "test"
	    }
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
	 
	 //点数据
     var point_data=[[104.06662 , 30.71189],[104.06662 , 30.73399],[104.06662 , 30.75559]];
     var point_features=[];
     for(var i = 0,len=point_data.length;i<len;i++){
         var point = new SuperMap.Geometry.Point(point_data[i][0],point_data[i][1]);
         var feature=new SuperMap.Feature.Vector(point,{
             FEATUREID: "fId",
             //根据节点生成时间
             TIME: i});
        /* },styleCar1);*/
         if(i == (len-1)){
        	 feature.attributes.isLast = true;
         }
         point_features.push(feature);
     }
     vector.addFeatures(new SuperMap.Feature.Vector(new SuperMap.Geometry.Point(104.06662 , 30.79559)));
     animatorVector.addFeatures(point_features);
     switch_snap();
     
     // 创建4G三角形,电信行业4G专业符号形容类似为：-▷
     var resolution = map.getResolutionForZoom(map.zoom);
     var origin = new SuperMap.Geometry.Point(104.08662 , 30.73399);
     var geoTriangle = SuperMap.Geometry.Polygon.createRegularPolygonTriangle(origin,0.02,0.004,0.012,135,resolution);
     var vectorTriangle = new SuperMap.Feature.Vector(
    		 geoTriangle,
    		 {},
    		 style
    		 );
     vector.addFeatures(vectorTriangle);
     
     // 曲线绘制 有待验证！！！
/*     var point1 = new SuperMap.Geometry.Point(5344.59369,-3609.27308);
     var point2 = new SuperMap.Geometry.Point(5192.43694,-3624.36800);
     var point3 = new SuperMap.Geometry.Point(5404.97335,-3587.53641);
     var curve = new SuperMap.Geometry.Curve([point1,point2]);*/
     
     /*var geoText = new SuperMap.Geometry.GeoText(5404.97335,-3587.53641,"中华人民共和国");
     var geotextFeature = new SuperMap.Feature.Vector(geoText);
     testVectorLayer.addFeatures(geotextFeature);*/
}

 var isdrag = null;
 function drawPointCompleted(eventArgs){
	 var geometryB = eventArgs.feature.geometry;
	 var mypoint = geometryB.components[0];
	 var mx = mypoint.x, my = mypoint.y;
	 if(pType == 2.1){// 会移动的卡口
		var size = new SuperMap.Size(32,37);
		var offset = new SuperMap.Pixel(-(size.w/2), -size.h);
		mk = new SuperMap.Marker(new SuperMap.LonLat( mx, my), new SuperMap.Icon(basePath+"supermap/theme/images/MapIcos/XunPoliceIco.png", size, offset));
		/*mk.events.register("mousedown",mk,function(e){
			isdrag = true;
			//得到marker的经纬度坐标
			var lon = mk.lonlat;
			//将经纬度坐标转换成屏幕坐标
			var pixel = map.getPixelFromLonLat(lon);
			//取整
			tx = parseInt(pixel.x);
			ty = parseInt(pixel.y);
			//获取marker相对鼠标的位置
			x = mk ? e.clientX : event.clientX;
			y = mk ? e.clientY : event.clientY;
			});*/
		 mk.attributes = {
				 source: mk
		 }
		 mk.events.on({ "rightclick": mkMenuDiv});
	 	 markerlayer.removeMarker(mk);
	 	 markerlayer.addMarker(mk);
	 	drawPoint.deactivate();
		 testVectorLayer.removeFeatures(eventArgs.feature);
	 	 return;
	 }
	 if(pType == 3){ // 浮动对象
		 var con = "<div>";
		 con += "<input style='width: 120px;' type='text' id='moveObj'>";
		 con += "<button onclick='createMoveObj()' style='width: 50px;height: 25px;' >确定</button></div>";
		 openTyWin(eventArgs.feature, con);
		 drawPoint.deactivate();
		 testVectorLayer.removeFeatures(eventArgs.feature);
		 return;
	 }
	 var pLabel = "卡口";
	 var pGraphic = "http://localhost:8080/BPHCenter/supermap/theme/images/MapIcos/GisGunCameraNormal.png";
	 if(pointType){
		 pLabel = "卡点";
		 pGraphic = "http://localhost:8080/BPHCenter/supermap/theme/images/MapIcos/CardDotNomal.png";
	 }
	var obj = {
		name: "卡点、卡口",
		communityGroupNum: "G34354646",
		tel: "028-88888888"
	};
 	eventArgs.feature.attributes = {
 			/*name: "李四",
 			yOffset: my+10,
 			age: 20,
 			favColor: 'red',
 			align: "cm"*/
 			obj: obj,
 			imgurl: pGraphic,
 			pointType: pointType
 		};
	// 重新渲染样式
 	eventArgs.feature.style = {
 			strokeColor: "#00FF00",
 			strokeOpacity: 1,
 			strokeWidth: 3,
 			fillColor: "#FF5500",
 			fillOpacity: 0.5,
 			pointRadius: 6,
 			pointerEvents: "visiblePainted",
 			graphicZIndex: 1,
 			label: pLabel,
 			labelSelect: true,
 			fontSize: "14px",
 			fontColor: "#00BDFF",
 			fontFamily: "Courier New, monospace",
 			fontWeight: "bold",
 			labelYOffset: my-5,
 			labelOutlineColor: "white",
 			labelOutlineWidth: 3,
 			externalGraphic: pGraphic,
 			graphicOpacity: 1,
 	        graphicWidth:32,
 	        graphicHeight:32
 	}
 	drawPoint.deactivate();
 	testVectorLayer.addFeatures(eventArgs.feature);
 	//pointLayer.removeAllFeatures();
    // 数据2
 }

 function drawLineCompleted(eventArgs){
 	drawLine.deactivate();
 }
 function drawPolygonCompleted(eventArgs){
 	drawPolygon.deactivate();
 	var geometryB = eventArgs.feature.geometry;
 	eventArgs.feature.attributes = {
 			name: "Polygon",
 			age: 20,
 			favColor: 'red',
 			align: "cm"
 		};
 	// 查询操作
 	queryByGeometry(geometryB);
 	 // 查询业务对象
    queryMydatas(geometryB);
 }
 /**
  * 绘制规则多边形 的回调函数
  * @param eventArgs
  */
 function regularPolygonCompleted(eventArgs){
 	var geometryB = eventArgs.feature.geometry;
 }
 /**
  * 绘制圆形 的回调函数
  * @param eventArgs
  */
 function drawRectangleCompleted(eventArgs){
 	var geometryB = eventArgs.feature.geometry;
 }
 
// 打开信息框
var infowin = null;
function openInfoWin(){
	closeInfoWin();
    var marker = this;
    var attributes = marker.attributes;
    var lonlat = marker.getLonLat();
    var size = new SuperMap.Size(0, 23);
    var offset = new SuperMap.Pixel(0, -size.h);
    var icon = new SuperMap.Icon(basePath+"supermap/theme/images/marker.png", 
		size, offset);

var  c = "<div style='background-color: #2A3D47;'>点击弹出框信息<br>";
c += "<table border='0'><tr>";
c += "<td>name: </td><td><input type='text' style='width: 120px;' value='"+attributes.name+"'/></td></tr>";
c += "<tr><td>password: </td><td><input type='password' style='width: 120px;'/></td></tr>";

c += "<tr><td colspan='2'>&nbsp;</td></tr>"
c += "<tr><td align='center'  colspan='2'><input type= 'button' value='保存' style='width: 60px;'/>  <input type= 'button' onclick='closeInfoWin()' value='取消' style='width: 60px;'/></td></tr>";
c += "</table</div>";
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
/**
 * 生成消息框
 * @param obj 对象
 * @param content 显示内容
 * @param size 消息框大小
 * 	@param color 背景颜色
 * @param opacity 透明度
 * @param isClose 是否显示 关闭按钮
 */
function openWin(obj, content, size, color, opacity,isClose){
	if(isClose == null) isClose = true;
	if(!size || size.w < 60){
		size = new Object();
		size.w = 60; 
	}
	if(!size.h){
		size.h = 20;
	}
	var popup = new SuperMap.Popup(
			"anPopWin", new SuperMap.LonLat(obj.x, obj.y),
			new SuperMap.Size(size.w,size.h),
			content, isClose);
	// 设置背景为"",则透明
	if(color) popup.setBackgroundColor(color);
	if(opacity) popup.setOpacity(opacity);
	map.addPopup(popup);
	return popup;
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
	isQuery = null;
	drawPoint.deactivate();
	drawLine.deactivate();
	drawPolygon.deactivate();
	regularPolygon.deactivate();
	drawRectangle.deactivate();
	drawLineMath.deactivate();
	drawPolygonMath.deactivate();
	drawPointBounds.deactivate();
	drawLineBounds.deactivate();
}

var pointType = true;
// 绘点类型，用于区分不同的功能
var pType = 0;
// 绘点，激活控件
function draw_point(con){
	pType = con;
	if(con == 1){ // 卡点
		pointType = true;
	}else if(con == 2){ // 卡口
		pointType = false;
	}
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
/**
 * 绘圆
 */
function draw_RegularPolygon(){
	regularPolygon.deactivate();
	regularPolygon.activate();
}
/**
 * 绘矩形
 */
function draw_drawRectangle(){
	drawRectangle.deactivate();
	drawRectangle.activate();
}
// 清除，注销所有绘制控件
   function clearFeatures(){
	   deactiveAll();
	   markerlayer.clearMarkers();
	   vector.removeAllFeatures();
	   testVectorLayer.removeAllFeatures();
	   clusterLayer.removeAllFeatures();
   }
   
   
 /**
  * 绘完触发事件（距离）
  * @param drawGeometryArgs
  */
function drawCompleted(drawGeometryArgs) {
   //停止画面控制
	drawLineMath.deactivate();
   //获得图层几何对象
   var geometry = drawGeometryArgs.feature.geometry;
   // 量算距离
   measureFunction(url,geometry, SuperMap.REST.MeasureMode.DISTANCE, measureCompleted);
}

/**
 * 绘完触发事件(面积)
 * @param drawGeometryArgs
 */
function drawCompletedArea(drawGeometryArgs) {
   //停止画面控制
	drawPolygonMath.deactivate();
   //获得图层几何对象
   var geometry = drawGeometryArgs.feature.geometry;
   // 量算面积
   measureFunction(url,geometry, SuperMap.REST.MeasureMode.AREA, measureCompletedArea);
}

//测量结束调用事件(距离)
function measureCompleted(measureEventArgs) {
	var distance = measureEventArgs.result.distance;
	var unit = measureEventArgs.result.unit;
	alert("量算结果:"+distance + "米");
}

/**
 * 量算距离、面积
 * @param url 地图url
 * @param geometry 量算的地物间的距离或某个区域的面积
 * @param measureMode 类型进行判断和赋值，当判断出是LineString时设置SuperMap.REST.MeasureMode.DISTANCE，否则是SuperMap.REST.MeasureMode.AREA
 * @param measureCompleted 回调函数
 */
function measureFunction(url, geometry, measureMode, measureCompleted){
	var measureParam,myMeasuerService;
	measureParam = new SuperMap.REST.MeasureParameters(geometry), /* MeasureParameters：量算参数类。 客户端要量算的地物间的距离或某个区域的面积*/
	myMeasuerService = new SuperMap.REST.MeasureService(url); //量算服务类，该类负责将量算参数传递到服务端，并获取服务端返回的量算结果
	myMeasuerService.events.on({ "processCompleted": measureCompleted });
	//对MeasureService类型进行判断和赋值，当判断出是LineString时设置MeasureMode.DISTANCE，否则是MeasureMode.AREA
	myMeasuerService.measureMode = measureMode;

   	myMeasuerService.processAsync(measureParam); //processAsync负责将客户端的量算参数传递到服务端。
}

//测量结束调用事件(面积)
function measureCompletedArea(measureEventArgs) {
var area = measureEventArgs.result.area,
unit = measureEventArgs.result.unit;
alert("量算结果:"+ area + "平方米");
}
// 距离测量
function distanceMeasure(){
	//clearFeatures();
	drawLineMath.activate();
}
// 面积测量
function areaMeasure(){
	//clearFeatures();
	drawPolygonMath.activate();
}


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
	/* 比例尺另外计算方式
	var scale = map.getScale();
    scale = parseInt(1 / scale * 10) / 10;
    var scaleText = document.getElementById("scaleText");
    scaleText.value="比例尺： 1/" + scale;*/
	
	var lonlat= map.getLonLatFromPixel(new SuperMap.Pixel(e.clientX,e.clientY));
	var nlon = (lonlat == null ? "" : lonlat.lon.toFixed(5));
	var nlat = (lonlat == null ? "" : lonlat.lat.toFixed(5));
	var showHtml="<span style='color: #000;'><h4>像素坐标：" + "x="+Math.floor(e.clientX)+"，"+"y="+Math.floor(e.clientY) +"  "+map.getScale()+"<br>经度："+ nlon + "   " + "纬度："+nlat;
	showHtml += "   层数：" + (map.getZoom() + 1);
	showHtml += "   比例尺：" + (map.getScale() * 1000000).toFixed(2) + "万 : 1";
	showHtml += "</h4></span>"; 
	document.getElementById("mousePositionDivTitle").innerHTML=showHtml;
	
	if(map.getZoom() > 5){
		var point = new SuperMap.Geometry.Point(nlon,nlat );
		//queryByDistance(point);
		MapManager.queryByDistance(url, 
				{name: "Government@Changchun.2"},
				point,
				null,
				processCompletedByDistance,
				bufferAnalystFailed);
	}
}

/**
 * 将对应的点作为中心
 */
function setCenter(){
	//menu.style.visibility="hidden";
	//map.setCenter(gmarker.lonlat,2);
	map.setCenter(marker.lonlat,2);
}

function switch_snap(){
	if(snapState){
		mymodifyFeature.deactivate();
		snap01.off();
		snapState = false;
	}else{
		mymodifyFeature.activate();
		 snap01.on();
		 snapState = true;
	}
}


//开始播放动画
function startAnimator(){
    animatorVector.animator.start();
}
//暂停播放动画
function pauseAnimator(){
    animatorVector.animator.pause();
}
//停止播放动画
function stopAnimator(){
    animatorVector.animator.stop();
}

/**
 * 移动marker
 * @param type
 */
function gotoMove(type){
	// 关闭下拉菜单
	cancelMenu();
	//注册事件
	mk.events.register("mousedown",mk,function(e){
		if(type == 'save'){
			mk.attributes = {
					source: mk
			};
			isdrag=false;
			return;
		}
		isdrag = true;
		var lon = mk.lonlat;
		var pixel = map.getPixelFromLonLat(lon);
		tx = parseInt(pixel.x);
		ty = parseInt(pixel.y);
		x = mk ? e.clientX : event.clientX;
		y = mk ? e.clientY : event.clientY;
	});
}

/**
 * 右键显示下拉菜单
 */
function menuDiv(){
    var p= map.getPixelFromLonLat(this.lonlat);
    menu.style.left= p.x+"px";
    menu.style.top= p.y+"px";
    menu.style.visibility="visible";
  //  gmarker= this;
}
/**
 * 右键显示下拉菜单
 */
function mkMenuDiv(){
	var p= map.getPixelFromLonLat(this.lonlat);
	mkMenu.style.left= p.x+"px";
	mkMenu.style.top= p.y+"px";
	mkMenu.style.visibility="visible";
}
/**
 * 关闭下拉菜单
 */
function cancelMenu(type){
	if(type == "init"){
		var sourceMk = mk.attributes.source;
		markerlayer.removeMarker(mk);
		mk = new SuperMap.Marker(sourceMk.lonlat, sourceMk.icon);
		 mk.attributes = {
				 source: sourceMk
		 }
		 mk.events.on({ "rightclick": mkMenuDiv});
		 //注册事件
		 mk.events.register("mousedown",mk,function(e){isdrag=false;});
	 	 markerlayer.addMarker(mk);
	}
	menu.style.visibility = "visible";
	mkMenu.style.visibility="hidden";
}


/* --------------------业务方法-------------------------*/
/**
 * 获取卡点、卡口，上图
 */
var featureIds = new Object();
function cardrequest(type){
	var u = basePath;
	if(type == 1){
		u += "cardPoint/queryCardPointList.do";
	}else if(type == 2){
		u += "client/bayonet/getBayonetList.do";
	}
	$.ajax({
		url:  u,
		/*url: "http://25.30.9.97:8080/BPHCenter/client/GBPlatForm/getGBDeviceListByOrganId.do?organId=1",*/
		type: "post",
		dataType: "json",
		data: {
			organId : 1,
			random: Math.random()
		},
		success: function(res){
			if(res.data && res.data.length > 0){
				var fids = [];
				var skynetFeatures = [];
				for(var i = 0; i < res.data.length; i++){
					 var pGraphic = "http://localhost:8080/BPHCenter/supermap/theme/images/MapIcos/GisGunCameraNormal.png";
					 if(type == 1){
						 pGraphic = "http://localhost:8080/BPHCenter/supermap/theme/images/MapIcos/CardDotNomal.png";
					 }
					var obj = res.data[i];
					if(obj.longitude == "0.0" || obj.latitude == "0.0")
						continue;
					var skynet = new SuperMap.Geometry.Point(obj.longitude, obj.latitude);
					var feature = new SuperMap.Feature.Vector(skynet);
					feature.id = "FeatureVector"+type+(obj.id ? obj.id : obj.bayonetId);
					fids.push(feature.id);
					feature.attributes = {
							obj: obj,
				 			imgurl: pGraphic,
				 			pointType: type == 1? true : false
				 		};
					// 重新渲染样式
					feature.style = {
				 			strokeColor: "#00FF00",
				 			strokeOpacity: 1,
				 			strokeWidth: 3,
				 			fillColor: "#FF5500",
				 			fillOpacity: 0.5,
				 			pointRadius: 6,
				 			pointerEvents: "visiblePainted",
				 			graphicZIndex: 1,
				 			label: obj.name,
				 			labelSelect: true,
				 			fontSize: "14px",
				 			fontColor: "#00BDFF",
				 			fontFamily: "Courier New, monospace",
				 			fontWeight: "bold",
				 			labelYOffset: obj.latitude,
				 			labelOutlineColor: "white",
				 			labelOutlineWidth: 3,
				 			externalGraphic: pGraphic,
				 			graphicOpacity: 1,
				 	        graphicWidth:32,
				 	        graphicHeight:32
				 	}
					skynetFeatures.push(feature);
				}
			}
			if(skynetFeatures.length > 0){
				testVectorLayer.addFeatures(skynetFeatures);
			}
			if(type == 1){
				featureIds.cardPoint = fids;
			}else if(type == 2){
				featureIds.bayonet = fids;
			}
		}
	});
}
/**
 * 显示部分卡点 or 卡口
 * @param type
 */
function randomShow(type){
	if(featureIds && featureIds.bayonet){
		var fs = [];
		var r = (Math.random()).toFixed(1) * 10;
		for(var i = 0; i < featureIds.bayonet.length; i++){
			if(i < 2)
				continue;
			fs.push(testVectorLayer.getFeatureById(featureIds.bayonet[i]));
		}
		testVectorLayer.removeAllFeatures();
		testVectorLayer.addFeatures(fs);
	}
}

var moveFea = null;
/**
 * 弹出录入信息的消息框
 * @param fea
 * @param content 显示内容
 */
function openTyWin(fea, content){
	moveFea = fea;
	$("#drawDialog").tyWindow({
		width: "300px",
		height: "200px",
		title: "请输入信息",
		position: {
			top: "275px",
			left: "335px"
		},
		content: content,
	});
}

function createMoveObj(){
	var obj = moveFea.geometry.components[0];
	var cm = document.getElementById("moveObj").value;
	var c = "<span style='color: red'>" + cm + "</span>";
	var size = {
			w: cm.length * 14,
			h:20
	};
	openWin(obj, c , size ,null, 0.8, false);
	$("#drawDialog").tyWindow.close();
}

/**
 * 基础图层控制
 */
function controlBaseLayer(){
	$("#popupWin").css("display", "block");
}

/**
 * 获取地图基础子图层
 */
function getLayersInfo() {
    //获取地图状态参数必设：url
    var getLayersInfoService = new SuperMap.REST.GetLayersInfoService(url);
    getLayersInfoService.events.on({ "processCompleted": getLayersInfoCompleted});
    getLayersInfoService.processAsync();
}

/**
 * 与服务器交互成功，得到子图层信息
 */
var subLayers = new Array();
function getLayersInfoCompleted(getLayersInfoEventArgs) {
    if (getLayersInfoEventArgs.result) {
        {
            if (getLayersInfoEventArgs.result.subLayers) {
                for (var j = 0; j < getLayersInfoEventArgs.result.subLayers.layers.length; j++) {
                    subLayers.push(getLayersInfoEventArgs.result.subLayers.layers[j]);
                }
            }
        }
    }
    installPanel(subLayers);
}

/**
 * 组装操作面板，显示子图层列表，并初始化地图显示
 */
function installPanel(subLayers, type) {
    var layersList = "";
    for (var i = 0; i < subLayers.length; i++) {
        if (eval(subLayers[i].visible) == true || (type == "my" && subLayers[i].getVisibility())) {
            layersList += '<label class="checkbox" style="line-height: 28px; display: block"><input type="checkbox"  class = "checkboxSel" id="layersList' + i + '" name="layersList" value="' + subLayers[i].name + '" checked=true title="是否可见" />' + subLayers[i].name + '</label>';
        }
        else {
            layersList += '<label class="checkbox" style="line-height: 28px; display: block"><input type="checkbox" class = "checkboxSel" id="layersList' + i + '" name="layersList" value="' + subLayers[i].name + '" title="是否可见"  />' + subLayers[i].name + '</label>';
        }
    }
    showWindow(layersList);
    $(".checkbox").click(setLayerStatus);

    //样式为BootStrap框架设置
    $(".checkbox").hover(function () {
        $(this).addClass("label-success");
    }, function () {
        $(this).removeClass("label-success");
    });
    createTempLayer();
}

/**
 * 创建临时图层来初始化当前地图显示
 */
function createTempLayer() {
    //子图层控制参数必设：url、mapName、SetLayerStatusParameters
    var layerStatusParameters = new SuperMap.REST.SetLayerStatusParameters();
    layerStatusParameters = getLayerStatusList(layerStatusParameters);

    var setLayerStatusService = new SuperMap.REST.SetLayerStatusService(url);
    setLayerStatusService.events.on({ "processCompleted": createTempLayerCompleted});
    setLayerStatusService.processAsync(layerStatusParameters);
}

/**
 * 获取当前地图子图层状态信息
 * @param parameters
 * @returns
 */
function getLayerStatusList(parameters) {
    var layersList = document.getElementsByName("layersList");
    for (var i = 0; i < layersList.length; i++) {
        var layerStatus = new SuperMap.REST.LayerStatus();
        layerStatus.layerName = layersList[i].value;
        layerStatus.isVisible = eval(layersList[i].checked);
        parameters.layerStatusList.push(layerStatus);
    }
    //设置资源在服务端保存的时间，单位为分钟，默认为10
    parameters.holdTime = 30;
    return parameters;
}

/**
 * 与服务器交互成功，创建临时图层
 */
function createTempLayerCompleted(createTempLayerEventArgs) {
    tempLayerID = createTempLayerEventArgs.result.newResourceID;

    //创建 TiledDynamicRESTLayer
    /*layer = new SuperMap.Layer.TiledDynamicRESTLayer("World", url, {transparent: true, cacheEnabled: false, redirect: true, layersID: tempLayerID}, {maxResolution: "auto", bufferImgCount: 0});
    layer.bufferImgCount = 0;
    layer.events.on({"layerInitialized": addLayer});*/
}

function showWindow(winMessage) {
    if(document.getElementById("popupWin")) {
        $("#popupWin").css("display", "block");
    } else {
        $("<div id='popupWin'></div>").addClass("popupWindow").appendTo($("#result"));
    }
    $("#popupWin").css("display", "none");
    var str = "";
    str += '<div class="winTitle" onMouseDown="startMove(this,event)" onMouseUp="stopMove(this,event)"><span class="title_left">World地图子图层</span><span class="title_right"><a href="javascript:closeWindow()" title="关闭窗口">关闭</a></span><br style="clear:right"/></div>';  //标题栏

    str += '<div class="winContent" style="overflow-y:auto;height:400px;">';
    str += winMessage;
    str += '</div>';
    $("#popupWin").html(str);
    document.getElementById("popupWin").style.width = "250px";
    document.getElementById("popupWin").style.height = "432px";
}

/**
 * 子图层可见性控制
 */
function setLayerStatus() {
    //方法一：使用发送子图层控制参数请求来控制子图层，不推荐使用
    //子图层控制参数必设：url、mapName、SetLayerStatusParameters
//var layerStatusParameters = new SuperMap.REST.SetLayerStatusParameters();
//layerStatusParameters = getLayerStatusList(layerStatusParameters);
//layerStatusParameters.resourceID = tempLayerID;
//var setLayerStatusService = new SuperMap.REST.SetLayerStatusService(url);
//setLayerStatusService.events.on({ "processCompleted": setLayerStatusCompleted});
//setLayerStatusService.processAsync(layerStatusParameters);

    //方法二：通过TiledDynamicRESTLayer的属性layersID来控制子图层的可见性，推荐使用此方法
    var layersList = document.getElementsByName("layersList");
    var str = "[0:"; var lnames
    var tl = null;
    for (var i = 0; i < layersList.length; i++){
    	tl = map.getLayersByName(layersList[i].value)[0];
        if(eval(layersList[i].checked) == true)
        {
            if(i<layersList.length)
            {
                str += i.toString();
            }
            if(i<layersList.length-1)
            {
                str += ",";
            }
            
        	if(tl) tl.setVisibility(true);
        }else{
        	if(tl) tl.setVisibility(false);
        }
    }
    
    if(tl != null){ // tl !=null 表示此时为业务图层操作；反之则为基础图层操作
    	return;
    }
    
    str += "]";
    //当所有图层都不可见时
    if(str.length<5)
    {
        str = "[]";
    }
    layer.params.layersID = str;
    layer.redraw();

}
/**
 * 关闭弹出框
 */
window.closeWindow = function(){
    $("#popupWin").css("display", "none");
}
/**
 * 移动弹出框
 */
window.startMove = function(o,e){
    var wb;
    if(SuperMap.Browser.name === "msie" && e.button === 1) wb = true;
    else if(e.button === 0) wb = true;
    if(wb){
        var x_pos = parseInt(e.clientX-o.parentNode.offsetLeft);
        var y_pos = parseInt(e.clientY-o.parentNode.offsetTop);
      //  if(y_pos<= o.offsetHeight){
            document.documentElement.onmousemove = function(mEvent){
                var eEvent = (SuperMap.Browser.name === "msie")?event:mEvent;
                o.parentNode.style.left = eEvent.clientX-x_pos+"px";
                o.parentNode.style.top = eEvent.clientY-y_pos+"px";
            }
      //  }
    }
}
/**
 * 停止移动事件
 */
window.stopMove = function(o,e){
    document.documentElement.onmousemove = null;
}

/**
 * 获取业务图层
 */
function getMyLayers(){
	var lnames = ['chengduMap','markerLayer','vectorLayer','testLayer','Cars'];
	var mylayers = [];
	var o = null;
	for(var i = 0; i < lnames.length; i++){
		if(map.getLayersByName(lnames[i]) != null && map.getLayersByName(lnames[i]).length > 0)
			mylayers.push(map.getLayersByName(lnames[i])[0]);
	}
	if(mylayers != null){
		
	}
	//alert("有"+map.getNumLayers()+"层业务图层！");
	installPanel(mylayers, "my");
	controlBaseLayer();
}

/**
 * 卡口卡点图层控制
 */
function displayLayer(){
	var tl = map.getLayersByName("testLayer")[0];
	if(tl){
		tl.setVisibility(!tl.getVisibility());
	}
}
/**
 * 增加、删除图层
 */
function addDelLayer(){
	var ln = "testLayer";
	var tl = map.getLayersByName(ln)[0];
	if(tl){
		map.removeLayer(tl);
	}else{
		testVectorLayer = new SuperMap.Layer.Vector(ln); 
		map.addLayer(testVectorLayer);
	}
}

var isQuery = null;
/**
 * 点周边绘制
 */
function drawPointBoundsFun(type){
	deactiveAll();
	drawPointBounds.activate();
	isQuery = type;
}
/**
 * 线周边绘制
 */
function drawLineBoundsFun(type){
	deactiveAll();
	drawLineBounds.activate();
	isQuery = type;
}
function drawBoundsCompleted(eventArgs){
	var geometryB = eventArgs.feature.geometry;
	var mypoint = geometryB.components[0];
	var obj = {
		name: "点周边",
		tel: "028-88888888"
	};
 	eventArgs.feature.attributes = {
 			obj: obj
 		};
 	drawPointBounds.deactivate();
 	drawLineBounds.deactivate();
 	boundspoint = mypoint;
// 	bufferAnalystProcess(mypoint);
 	MapManager.bufferAnalystProcess(url2, mypoint, null, 25, bufferAnalystCompleted, bufferAnalystFailed);
 	
 	testVectorLayer.addFeatures(eventArgs.feature);
 }

/**
 * 对生成的线路进行缓冲区分析
 * @param fea
 * @param bdistance
 */
function  bufferAnalystProcess(fea, bdistance) {
	if(!bdistance) bdistance = 120;
    var bufferServiceByGeometry = new SuperMap.REST.BufferAnalystService(url2),
            bufferDistance = new SuperMap.REST.BufferDistance({
                value: bdistance
            }),
            bufferSetting = new SuperMap.REST.BufferSetting({
                endType: SuperMap.REST.BufferEndType.ROUND,
                leftDistance: bufferDistance,
                rightDistance: bufferDistance,
                semicircleLineSegment: 25
            }),
            geoBufferAnalystParam = new SuperMap.REST.GeometryBufferAnalystParameters({
                sourceGeometry: fea,
                bufferSetting: bufferSetting
            });

    bufferServiceByGeometry.events.on(
            {
                "processCompleted": bufferAnalystCompleted, "processFailed": bufferAnalystFailed
            });
    bufferServiceByGeometry.processAsync(geoBufferAnalystParam);
}
var styleRegion = {
        strokeColor: "#304DBE",
        strokeWidth: 2,
        pointerEvents: "visiblePainted",
        fillColor: "green",
        fillOpacity: 0.4
    };
/**
 * 分析成功后的回调函数
 * @param BufferAnalystEventArgs
 */
function bufferAnalystCompleted(BufferAnalystEventArgs) {
    var feature = new SuperMap.Feature.Vector();
    var bufferResultGeometry = BufferAnalystEventArgs.result.resultGeometry;
    feature.geometry = bufferResultGeometry;
    feature.style = styleRegion;
    boundsObj = feature;
    testVectorLayer.addFeatures(feature);
    
    if(isQuery == null || (isQuery+"").length == 1){ // 只绘图，不查询
		return;
	}
    // 执行查询操作
    //queryByGeometry(bufferResultGeometry);
    MapManager.queryByGeometry(url, {name: "Government@Changchun.2"}, 
    		bufferResultGeometry, 
    		SuperMap.REST.SpatialQueryMode.INTERSECT, 
    		processQueryCompleted, 
    		bufferAnalystFailed);
    // 查询业务对象
    queryMydatas(bufferResultGeometry);
}
/**
 * 处理失败的回调函数
 * @param args
 */
function bufferAnalystFailed(args) {
	alert("=="+args.error.errorMsg);
}

/**
 * 改变点周边 半径
 */
function changeStyle(){
	if(boundsObj && boundspoint){
		testVectorLayer.removeFeatures(boundsObj);
		markerlayer.clearMarkers();
		bufferAnalystProcess(boundspoint, $("#boundStyle").val());
	}
}

/**
 *  范围查询(自带机制)
 * @param geometry
 */
function queryByGeometry(geometry){
    var queryParam, queryByGeometryParameters, queryService;
    queryParam = new SuperMap.REST.FilterParameter({name: "Government@Changchun.2"});  // 政府部门 School@Changchun.2
    queryByGeometryParameters = new SuperMap.REST.QueryByGeometryParameters({
        queryParams: [queryParam],
        geometry: geometry,
        spatialQueryMode: SuperMap.REST.SpatialQueryMode.INTERSECT//SuperMap.REST.SpatialQueryMode.INTERSECT
    });
    queryService = new SuperMap.REST.QueryByGeometryService(url, {
        eventListeners: {
            "processCompleted": processQueryCompleted,
            "processFailed": bufferAnalystFailed
        }
    });
    queryService.processAsync(queryByGeometryParameters);
}
/**
 * （自带）查询结束的回调函数
 * @param queryEventArgs
 */
function processQueryCompleted(queryEventArgs) {
    var i, j, result = queryEventArgs.result;
    if (result && result.recordsets) {
        for (i=0, recordsets=result.recordsets, len=recordsets.length; i<len; i++) {
            if (recordsets[i].features) {
                for (j=0; j<recordsets[i].features.length; j++) {
                    var feature = recordsets[i].features[j];
                    var point = feature.geometry;
                    if(point.CLASS_NAME == SuperMap.Geometry.Point.prototype.CLASS_NAME){
                    	createQueryResult(feature);
                    }else{
                        feature.style = style;
                        testVectorLayer.addFeatures(feature);
                    }

                }
            }
        }
    }
}
/**
 * 对业务对象 模拟查询
 * @param geometry
 */
function queryMydatas(geometry){
	var existsPoints = [];
	var ps = [[5344.59369,-3609.27308], [5286.62921,-3653.35024], [5192.43694, -3624.36800],[5400.14297,-3657.57681],[5404.97335,-3587.53641],
	          [5189.41796,-3410.02020],[5337.34813,-3461.34292],[5001.63721,-3393.71769],[5037.86501,-3523.53397],[5487.69348,-3656.97302]];
	for(var i = 0; i < ps.length; i++){
		var point = new SuperMap.Geometry.Point(ps[i][0], ps[i][1]);
		if(geometry.intersects(point)){
			var fea = new SuperMap.Feature.Vector(point);
			fea.attributes = {
				NAME: "卡点"+(i+1)
			}
			createQueryResult(fea, "my");
			existsPoints.push(point);
		}
	}
}

/**
 * 根据查询结果 绘点
 * @param point 
 * @param type 类型
 */
function createQueryResult(feature, type){
	 point = feature.geometry;
	 var points = [];
     points.push(point);
     points.push(boundspoint);
    var lineGeometry=new SuperMap.Geometry.LineString(points);
	var size = new SuperMap.Size(44, 33),
    offset = new SuperMap.Pixel(-(size.w/2), -size.h);
    var ipath = basePath+"supermap/theme/images/marker.png";
	if(type == "my"){
		ipath = basePath + "supermap/theme/images/MapIcos/GisGunCameraNormal.png";
	}
    var icon = new SuperMap.Icon(ipath, size, offset);
	var marker = new SuperMap.Marker(new SuperMap.LonLat(point.x, point.y), icon);
	marker.attributes = {
		feaObj: feature,
		geometry: lineGeometry
	};
	marker.events.on({
	"click": function (){
		var marker = this;
	    var attributes = marker.attributes;
	    // 量算距离
	    measureFunction(url,attributes.geometry, SuperMap.REST.MeasureMode.DISTANCE, function(measureEventArgs){
	    	var distance = measureEventArgs.result.distance;
	    	var lonlat = marker.getLonLat();
		    var size = new SuperMap.Size(0, 23);
		    var offset = new SuperMap.Pixel(0, -size.h);
		    var icon = new SuperMap.Icon(basePath+"supermap/theme/images/marker.png", 
				size, offset);
	    	var  c = "<div style='background-color: #2A3D47;'>信息展示<br>";
	    	c += "<table border='0'><tr>";
	    	c += "<tr><td>名称： </td><td>"+attributes.feaObj.attributes.NAME+"</td></tr>";
	    	c += "<tr><td>离中心点距离： </td><td>"+distance.toFixed(5)+"米</td></tr>";
	    	c += "</table</div>";
	    	var popup = new SuperMap.Popup.FramedCloud("popwin"+marker.id,
	    	    		new SuperMap.LonLat(lonlat.lon, lonlat.lat), null,
	    	    		c, null, true);
	    	    map.addPopup(popup);
	    });
	},
	"scope": marker
	});
	markerlayer.addMarker(marker);
}

/**
 * marker移动方法
 * @param e
 */
function movemouse(e)
{
	//鼠标的实时位置
	var x2,y2;
	if (isdrag)
	{
		//获取鼠标的实时位置
		x2 = mk ? tx + e.clientX - x : tx + event.clientX - x;
		y2 = mk ? ty + e.clientY - y : ty + event.clientY - y;
		//转为屏幕坐标
		var pix = new SuperMap.Pixel(x2,y2);
		//屏幕坐标转为经纬度坐标
		var lon1 = map.getLonLatFromPixel(pix);
		//将此前的marker坐标清理
		markerlayer.clearMarkers();
		//var offset = new SuperMap.Pixel(x2,y2);
		var size = new SuperMap.Size(32,37);
		var offset = new SuperMap.Pixel(-(size.w/2), -size.h);
		var icon = new SuperMap.Icon(basePath+"supermap/theme/images/MapIcos/XunPoliceIco.png",size, offset);
		var sourceMk = mk.attributes.source;
		mk = new SuperMap.Marker(lon1, icon);
		mk.attributes = {
				source: sourceMk
		}
		//注册事件
		mk.events.register("mousedown",mk,function(e){
			isdrag = true;
			var lon = mk.lonlat;
			var pixel = map.getPixelFromLonLat(lon);
			tx = parseInt(pixel.x);
			ty = parseInt(pixel.y);
			x = mk ? e.clientX : event.clientX;
			y = mk ? e.clientY : event.clientY;
		});
		mk.events.on({ "rightclick": mkMenuDiv});
		markerlayer.addMarker(mk);
	}
}
document.onmousemove=movemouse;
document.onmouseup=new Function("isdrag=false");

function getFeatures(){
    var b = map.getExtent();
    var ps = [];
    var fs = changchundata;
    for(var i=0;i<fs.length;i++){
        var fi = fs[i];
        var c = fi.geometry.center;

        var f = new SuperMap.Feature.Vector();
        f.geometry = new SuperMap.Geometry.Point(c.x, c.y);
        f.style = {
            pointRadius: 4,
            graphic:true,
            externalGraphic:"http://localhost:8080/BPHCenter/supermap/theme/images/MapIcos/GisBallCameraNormal.png",
            graphicWidth:12,
            graphicHeight:12
        };
        f.info = {
            "name":fi.fieldValues[4]
        };
        ps.push(f);
    }
    return ps;

}
/**
 * 聚散点 提示信息
 * @param feature
 */
function openInfoWinCluster(feature){
    var geo = feature.geometry;
    var bounds = geo.getBounds();
    var center = bounds.getCenterLonLat();
    var contentHTML = "<div style='font-size:.8em; opacity: 0.8; overflow-y:hidden;'>";
    contentHTML += "<div>"+feature.info.name+"</div></div>";

    var popup = new SuperMap.Popup.FramedCloud("popwin",
            new SuperMap.LonLat(center.lon,center.lat),
            null,
            contentHTML,
            null,
            true);
    feature.popup = popup;
    infowin = popup;
    map.addPopup(popup);
}
function closeInfoWinCluster(){
    if(infowin){
        try{
            infowin.hide();
            infowin.destroy();
        }
        catch(e){}
    }
}

/**
 * 加载聚散点
 */
function getClusterDatas(){
	//初始化数据
	var fs1 = getFeatures();
	clusterLayer.removeAllFeatures();
	clusterLayer.addFeatures(fs1);
}

/**
 * 加载热点图显示的point
 */
function createHeatPoints(){
    clearHeatPoints();
    var heatPoints = [];
    var num = parseInt(document.getElementById('heatNums').value);
    var radius = parseInt(document.getElementById('heatradius').value);
    //var useGeoRadius = "checked" == $('#useGeoRadius').attr('checked');
    var unit = document.getElementById("radiusUnit").value,
            useGeoRadius = false;
    if("degree" == unit){
        useGeoRadius = true;
    }
    heatMapLayer.radius = radius
    
    for(var i=0; i < num; i++){
        heatPoints[i] = new SuperMap.Feature.Vector(
                new SuperMap.Geometry.Point(
                        Math.random()*1000 + 4600,
                        Math.random()*1000 - 3900
                ),
                {
                    "value":Math.random()*9,
                    "geoRadius":useGeoRadius?radius:null
                }
        );
    }
    heatMapLayer.addFeatures(heatPoints);
}

/**
 * 清除热点图
 */
function clearHeatPoints(){
    heatMapLayer.removeAllFeatures();
}

/**
 * 距离查询
 * @param point
 */
function queryByDistance(point) {
	var queryByDistanceParams = new SuperMap.REST.QueryByDistanceParameters({
		queryParams: new Array(new SuperMap.REST.FilterParameter({name: "Government@Changchun.2"})),
		returnContent: true,
		distance: 100,
		isNearest: true,
		expectCount: 1,
		geometry: point
	});
	var queryByDistanceService = new SuperMap.REST.QueryByDistanceService(url);
	queryByDistanceService.events.on({
		"processCompleted": processCompletedByDistance,
		"processFailed": bufferAnalystFailed
	});
	queryByDistanceService.processAsync(queryByDistanceParams);
}
/**
 * 距离查询完成回调函数
 * @param queryEventArgs
 */
function processCompletedByDistance(queryEventArgs) {
	var i, j, result = queryEventArgs.result;
	for(i = 0;i < result.recordsets.length; i++) {
		for(j = 0; j < result.recordsets[i].features.length; j++) {
			var point = result.recordsets[i].features[j].geometry;
			var size = new SuperMap.Size(32,32);
			var offset = new SuperMap.Pixel(-(size.w/2), -size.h);
			var mk = new SuperMap.Marker(new SuperMap.LonLat( point.x, point.y), new SuperMap.Icon(basePath+"supermap/theme/images/marker.png", size, offset))
			mk.attributes = {
				obj: result.recordsets[i].features[j]
			};
			mk.events.on({
				"click": function (){
					var marker = this;
			    	var lonlat = marker.getLonLat();
			    	var  c = "<div style='background-color: #2A3D47;'>";
			    	c += "<table border='0'><tr>";
			    	c += "<tr><td>名称： </td><td>"+mk.attributes.obj.attributes.NAME+"</td></tr>";
			    	c += "</table</div>";
			    	var popup = new SuperMap.Popup.FramedCloud("popwin"+marker.id,
			    	    		new SuperMap.LonLat(lonlat.lon, lonlat.lat), null,
			    	    		c, null, true);
			    	    map.addPopup(popup);
				},
				"scope": mk
				});
			markerlayer.addMarker(mk);
		}
	}
}

function goMove(data){
	var obj = JSON.parse(data);
	var point = new SuperMap.Geometry.Point(obj.x, obj.y);
	var feature=new SuperMap.Feature.Vector(point,{
        FEATUREID: "fId",
        //根据节点生成时间
        TIME: 3
    });
	feature.attributes.isLast = true;
	animatorVector.addFeatures(feature);
	animatorVector.animator.setSpeed(animatorVector.animator.getSpeed()*0.3);
	//animatorVector.renderer.glint = !animatorVector.renderer.glint;
	startAnimator();
}

function drawfeaturestart(feature) {
	if(feature.attributes.isLast){
		pauseAnimator();
		feature.attributes.isLast = false;
	}
}

function Person(name, age){
	var name2 = name;
	var age2 = age;
	this.myname = name;
	this.getName = function(){
		alert(name2 + "==" + age2);
	};
	this.getTest = function(){
		alert(name);
	}
}

