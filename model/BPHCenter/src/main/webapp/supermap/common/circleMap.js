//声明变量map、layer、markerlayer、marker
   // scaleline
var  markerlayer;
var  marker,drawPoint,drawPolygon;
var  snap01,mymodifyFeature,vector,modifyVector,snapState=false;
var vector_style;
// 初始化的圈层数据对象(包括所有值)
var circleDatas = [];
//当前选中的数据对象
var currentObj = null;
// 描绘的图层对象
var polygonFinal = null;
// 圈层
var polygonfeatureFinal = null;
// 取点
var isGetPoint = false;
// 是否绘制卡点
var isDrawCardPoint = false;
// 是否通过 绘制卡点
var isProcess = false;

/**
 * 初始化各种控件、图层
 */
function initCompents(){
	vector_style = new SuperMap.Style({
		strokeColor: "#66CCFF",
		strokeOpacity: 0.6,
		strokeWidth: 3,
		fillColor: "#99CC99",
		fillOpacity: 0.6,
		pointRadius: 6,
		pointerEvents: "visiblePainted",
		graphicZIndex: 1
    });
	
	// 新建矢量图层
	vector = new SuperMap.Layer.Vector("vectorLayer");
	// 新建矢量图层
	modifyVector = new SuperMap.Layer.Vector("modifyVector",{
			styleMap:new SuperMap.StyleMap({
				'default':vector_style
			}),
		rendererOptions: {zIndexing: true}
	});
	// DrawFeature--鼠标拖拽要素类
	drawPoint = new SuperMap.Control.DrawFeature(vector, SuperMap.Handler.Point, { multi: true});
	drawPoint.events.on({"featureadded": drawPointCompleted} );
	drawPolygon = new SuperMap.Control.DrawFeature(modifyVector, SuperMap.Handler.Polygon);
	drawPolygon.events.on({"featureadded": drawPolygonCompleted});
	
	snap01=new SuperMap.Snap([modifyVector],4,2,{actived:true});
    //矢量要素编辑控件
	mymodifyFeature = new SuperMap.Control.ModifyFeature(modifyVector);
	mymodifyFeature.snap=snap01;
	// 当图层上的要素编辑完成时，触发该事件
	modifyVector.events.on({"afterfeaturemodified": editFeatureCompleted});
	
	map.addLayers([vector,modifyVector]);
}
/**
 * 要素编辑完成的回调函数
 * @param event
 */
function editFeatureCompleted(eventArgs){
	createCenterPoint(eventArgs, "edit");
}

/**
 * 绘制圈层后的回调函数
 * @param eventArgs
 */
function drawPolygonCompleted(eventArgs){
	createCenterPoint(eventArgs, "new");
}

/**
 * 生成绘制圈层的中心点，并记录对应的坐标
 * @param eventArgs
 */
function createCenterPoint(eventArgs, type){
	if(type == "new"){
		var circleId = $.trim($("#circleId").val());
		if(circleId && circleId != "") openInfoPage(circleId);
	}
	// 先销毁中心点提示框
	deactiveAll();	
	if(!document.getElementById("tyIframeContent")) return;
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
	var circleLName= currentObj.name;
	var content = "<span style='border: 1px; border-style: solid;border-color: red;color: red; padding: 4px 3px 4px 3px; margin-left: -4px;'>"+circleLName;
	content += "</span>";
	var size = new Object();
	size.w = circleLName.length * 14; size.h = 20;
	
	MapManager.clearPopups(map, [currentObj.id]);
	obj.id = currentObj.id;
	openWin(obj,content, size);
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
	drawPoint.deactivate();
	vector.removeFeatures(eventArgs.feature);
	var geometryB = eventArgs.feature.geometry;
	var mypoint = geometryB.components[0];
	if(!isDrawCardPoint){
		var iobj  = document.getElementById("tyIframeContent").contentWindow;
		var mx = (mypoint.x).toFixed(7), my = (mypoint.y).toFixed(7);
		$(iobj.document.getElementById("longitude")).val(mx);
		$(iobj.document.getElementById("latitude")).val(my);
		map.setCenter(new SuperMap.LonLat(mx, my), 1);
		var circleLName= $(iobj.document.getElementById("circleLayerName")).html();
		var content = "<span style='border: 1px; border-style: solid;border-color: red;color: red; padding: 4px 3px 4px 3px; margin-left: -4px;'>"+circleLName;
		content += "</span>";
		var size = new Object();
		size.w = circleLName.length * 14; size.h = 20;
		
		MapManager.clearPopups(map, [currentObj.id]);
		mypoint.id = currentObj.id;
		openWin(mypoint,content, size);
		isGetPoint = false;
	}else{
		isDrawCardPoint = false; //复原初始值
		
		if(!currentObj) return;
		var cardPoint = currentObj.cardPoint;
		var entity = new Entity();
		entity.id = cardPoint.id;
		entity.name = cardPoint.name;
		entity.type = 3;
		entity.longitude = mypoint.x;
		entity.latitude = mypoint.y;
		//entity.des = circleData.name; // 特殊处理 ，表示所属圈层name
		entity.detailInfo = cardPoint;
		if(entity.longitude && entity.latitude){
			$("body").tyWindow({"content":"确定要在该处绘制卡点?","center":true,"ok":true,"okCallback":function(){
				MapManager.clearMarkerById(markerlayer, entity.id);
				// 更新页面变量的数据
				currentObj.cardPoint.longitude = mypoint.x;
				currentObj.cardPoint.latitude = mypoint.y;
				for(var i in circleDatas){
					var circleData = circleDatas[i];
					if(circleData && circleData.items && circleData.items.length > 0){
						for(var j in circleData.items){
							var cardPoint = circleData.items[j].cardPoint;
							if(cardPoint.id = entity.id){
								circleDatas[i].items[j].cardPoint.longitude = mypoint.x;
								circleDatas[i].items[j].cardPoint.latitude = mypoint.y;
								break;
							}
						}
					}
				}
				MapToobar.doResourceChartOfPoint(entity, markerlayer, 38, 38);
				try{
					modifyCardPointCoordinate(cardPoint.id, cardPoint);
				}catch(e){}
        	},"no":true});
		}
	}
}

/**
 * 设置圈层的各种属性
 */
function changePloy(){
	//mymodifyFeature.deactivate();
	var iobj  = document.getElementById("tyIframeContent").contentWindow;
	if(polygonfeatureFinal){
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
		
		 var point_features=[];
		 point_features.push(myPolygonFeature);
		 modifyVector.addFeatures(point_features);
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
	var popup = new SuperMap.Popup(
			"anPopWin", new SuperMap.LonLat(Number(obj.x), Number(obj.y)),
			new SuperMap.Size(size.w,size.h),
			content, false);
	// 设置背景为透明
	popup.setBackgroundColor("");
	popup.type = obj.id;
	MapManager.getMap().addPopup(popup);
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
 *  注销控件
 */
function deactiveAll(){
	drawPolygon.deactivate();
	drawPoint.deactivate();
}
/**
	* 取点
	*/
function getPoint(){
	if(polygonFinal){
		isGetPoint = !isGetPoint;
		if(isGetPoint) drawPoint.activate();
		else drawPoint.deactivate();
	}
}
 /**
  *  绘面，激活控件
  */
  function draw_polygon(){
	   deactiveAll();
	   if(!polygonfeatureFinal)
		   drawPolygon.activate();
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
	   mymodifyFeature.deactivate();
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

function saveCreatePolygon(){
	if(currentObj) {
		mymodifyFeature.deactivate();
		try{
			$("#drawDialog").tyWindow.close();
		}catch(e){}
		
		var modifyVectorCircle = modifyVector.getFeaturesByAttribute("type", currentObj.id);
		modifyVector.removeFeatures(modifyVectorCircle);
		var circleFeas = vector.getFeaturesByAttribute("type", currentObj.id);
		vector.removeFeatures(circleFeas);
		createPolygon(currentObj,vector);
	}
}

/**
 * 初始化圈层
 * @param obj
 * @param ver
 */
function createPolygon(obj, vec){
	var mapProperty = obj.mapProperty;
	if(!mapProperty || mapProperty.length == 0) return;
	var name = obj.name;
	var displayProperty = obj.displayProperty;
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
    if(displayProperty){
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
    }
	mystyle.strokeWidth = 3;
	mystyle.pointRadius = 6;
    // 生成圈层
    var cricleLayerFea=new SuperMap.Feature.Vector(polygon,null, mystyle);
    cricleLayerFea.attributes.type = obj.id;
    cricleLayerFea.type = obj.id;
    createfeatures.push(cricleLayerFea);
    vec.addFeatures(createfeatures);
    
    polygonFinal = polygon;
    polygonfeatureFinal = cricleLayerFea;
    
    if(displayProperty && displayProperty.x != null && displayProperty.y){
    	var data = {
    			id: obj.id,
    			name: name,
    			x: displayProperty.x,
    			y: displayProperty.y
    	};
    	setTimeout(function(){
    		showCircleTitle(data);
    	},200);
    }
}

function showCircleTitle(data){
	
	MapManager.clearPopups(map, [data.id]);
	 // 生成对应圈层的标签
    var content = "<span style='border: 1px; border-style: solid;border-color: red;color: red; padding: 4px 3px 4px 3px; margin-left: -4px;'>"+ data.name;
	content += "</span>";
	var size = new Object();
	size.w = data.name.length * 14; size.h = 20;
	openWin(data,content, size);
}

function selectCircleLayer(data){
	var selectedCircle = vector.getFeaturesByAttribute("type", data.id);
	vector.removeFeatures(selectedCircle);
	
	modifyVector.removeAllFeatures();
	createPolygon(data, modifyVector);
}

/**
 * 修改界面 跳转
 */
function gotoDrawKpoint(){
	selectCircleLayer(currentObj);
	var circleId = $.trim($("#circleId").val());
	var sessionId = $.trim($("#token").val());
	if(circleId == ""){
		$("body").popjs({"title":"提示","content":"请先选择圈层！"});
		return;
	}
	
	if(!currentObj.mapProperty || currentObj.mapProperty.length == 0){
		draw_polygon();
	}else{
		openInfoPage(circleId);
		acModifyFeature();
	}
}
/**
 * 打开修改属性界面
 * @param circleId
 */
function openInfoPage(circleId){
	$("#drawDialog").tyWindow({
		width: "480px",
		height: "480px",
	    title: "图形属性设置",
	    position: {
	        top: "100px",
	        left:  document.body.clientWidth-480
	      },
		content: basePath+"map/gotoDrawCircle.do?circleId="+circleId+"&random="+Math.random(),
		iframe : true,
		closeCallback: function(){
			createCricleLayers();
		}
	}); 
}

/**
 * 初始化圈层中的卡点 上图
 * @param datas
 */
function doCardPointChart(datas){
	if(!datas) datas = circleDatas;
	var mklayer = map.getLayersByName("resourceMarkerLayer")[0];
	 if(!markerlayer){
		 markerlayer = MapManager.createMarkerLayer("resourceMarkerLayer");
	   	 MapManager.addLayer(map,markerlayer);
	 }
	 markerlayer.clearMarkers();
	for(var i in datas){
		var circleData = datas[i];
		if(circleData && circleData.items && circleData.items.length > 0){
			for(var j in circleData.items){
				var cardPoint = circleData.items[j].cardPoint;
				var entity = new Entity();
				entity.id = cardPoint.id;
				entity.name = cardPoint.name;
				entity.type = 3;
				entity.longitude = cardPoint.longitude;
				entity.latitude = cardPoint.latitude;
				entity.des = circleData.name; // 特殊处理 ，表示所属圈层name
				entity.detailInfo = cardPoint;
				if(cardPoint.longitude && cardPoint.latitude)
					MapToobar.doResourceChartOfPoint(entity, markerlayer, 38, 38);
			}
		}
	}
}


//单击触发事件
 function onSelect(e) {
	drawPoint.deactivate();
	polygonFinal = null;
	polygonfeatureFinal = null;
	//选择节点事件
	var treeview=$('#treeview').data('kendoTreeView');
	//获取选中节点的数据
	var data = treeview.dataItem(e.node);
	$("#circleId").val(data.id);
	if(data.type == "CircleLayer"){
		$("#drawButtom").css("display","");
		if(data.mapProperty && data.mapProperty.length > 0){
			if(data.displayProperty) MapManager.setCenter(data.displayProperty.x, data.displayProperty.y,MapManager.getMap().getZoom());
			saveCreatePolygon();
		}
	}else if(data.type == "CardPoint"){
		$("#drawButtom").css("display","none");
		currentObj = data;
		if(isProcess){
			isProcess = false;
			isDrawCardPoint = true;
			drawPoint.activate();
		}
		
		doCardPointChart();
		// 设置被选中的卡口为地图中心位置
		MapManager.setCenter(data.cardPoint.longitude, data.cardPoint.latitude,map.getZoom());
		var selectedMak = MapManager.clearMarkerById(markerlayer, data.id);
		if(selectedMak) {
			selectedMak.icon.url = basePath+"supermap/theme/images/MapIcos/CardDotReceived.png";
			markerlayer.addMarker(selectedMak);
		}
		
	}else{
		return;
	}
	currentObj = data;
}
 
function drawCardPoint(obj, type, isDraw){
	if(!currentObj) return;
	var id = $(obj).attr("id");
	//if(type == "CardPoint" && isDraw == false){
	if(type == "CardPoint"){
		if(currentObj.id+"" == id){
			isDrawCardPoint = true;
			drawPoint.activate();
			return
		}else{
			isProcess = true;
		}
	}
}
 
function modifyCardPointCoordinate(cardPointId, point){
	$.ajax({
		url: basePath + MapResourceUrl.modifyCardPointCoordinate,
		type: "post",
		dataType: "json",
		data: {
			cardPointId: cardPointId,
			latitude: point.latitude,
			longitude: point.longitude
		},
		success: function(res){
			$("#"+cardPointId).attr("src",basePath+"supermap/theme/images/others/CaseMarkPressed.png");
			$("body").popjs({"title":"提示","content":"成功绘制卡点！"});
		}
	});
}

/**
 * 初始化圈层数据 上图
 * @param datas
 * @param vlayer
 */
function createCricleLayers(datas, vlayer){
	if(!datas) datas = circleDatas;
	if(!vlayer) vlayer = vector;
	modifyVector.removeAllFeatures();
	vlayer.removeAllFeatures();
	for(var i in datas){
		createPolygon(datas[i], vlayer);
	}
	polygonfeatureFinal = null;
	//点‘取消 按钮的操作，删除生成的提示信息
	if(currentObj && currentObj.id)MapManager.clearPopups(map, [currentObj.id]);
}