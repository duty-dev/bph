 // 资源数据
var ResourceDatas = {
		datas: [], /*地图初始化的各种资源数据*/
		delResDatas: [], /*隐藏的各种资源数据*/
		gpsControl: false, /*MQ推送GPS数据的控制，默认情况关闭*/
		gpsResDatas:[], /*随gps移动的资源数据，如警车、警员*/
		policeControl: false, /*GPS推送时，警员是否上图控制，默认否*/
		policeCarControl: false /*GPS推送时，警车是否上图控制，默认否*/
}; 
var searchObj = null; // 搜索绘制控件对象
var eyeObj = new Object(); // 鹰眼功能全局对象
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
	 * @param drawCompleted 绘制完成后的回调方法
	 * @returns
	 */
	initDrawFeature: function(map,layer,handler, options, isActivate, drawCompleted){
		var drawFeature = MapManager.createDrawFeature(map, layer, isActivate, handler, options);
		drawFeature.events.on({"featureadded": drawCompleted} );
		return drawFeature;
	},
	/**
	 * 描点专题
	 * @param map 地图对象
	 * @param layer 执行绘制要素的图层
	 * @param options 设置该类及其父类开放的属性
	 * @param isActivate 是否立即生效
	 * @param drawPointCompleted 绘制完成后的回调方法
	 * @returns
	 */
	initPointFeature: function(map,layer, options, isActivate, drawPointCompleted){
		if(!map) map = MapManager.getMap();
		if(!layer){
			var resourceLayer = map.getLayersByName("resourceLayer")[0];
			 if(!resourceLayer) {
				 resourceLayer = MapManager.createVectorLayer("resourceLayer");
				 MapManager.addLayer(map,resourceLayer);
			 }
			 layer = resourceLayer
		}
		var handler = SuperMap.Handler.Point;
		var drawFeature = MapToobar.initDrawFeature(map,layer,handler, options, isActivate, drawPointCompleted);
		return drawFeature;
	},
	/**
	 * 鹰眼功能中的绘制控件
	 * @param map 地图对象
	 * @param layer 执行绘制要素的图层
	 * @param barType 搜索类型，1，标点；2，加点；3，测距；4，测面
	 * @param handler 要素绘制事件处理器，指定当前绘制的要素类型和操作方法
	 * @param options 设置该类及其父类开放的属性
	 * @param isActivate 是否立即生效
	 * @returns
	 */
	initEyeFeature: function(map,layer,barType,handler, options, isActivate){
		if(layer == null){
			if(eyeObj.attrs && eyeObj.attrs.eyeLayer){
				layer = eyeObj.attrs.eyeLayer;
			}else{
				layer = MapManager.createVectorLayer("eyeVLayer", options, map);
			}
		}
		if(eyeObj.attrs && eyeObj.attrs.eyeFeature) eyeObj.attrs.eyeFeature.deactivate();
		var drawFeature = MapToobar.initDrawFeature(map,layer,handler, options, isActivate, MapToobar.drawEyeCompleted);
		eyeObj.attrs = {
				eyeLayer: layer,
				eyeFeature: drawFeature,
				eyeBarType: barType, /*搜索类型，1，标点；2，加点；3，测距；4，测面*/
				eyeHandler: handler /*要素绘制事件处理器，指定当前绘制的要素类型和操作方法*/
		};
		return drawFeature;
	},
	/**
	 *  绘制控件专题(点搜索、线搜索、多边形搜索等)
	 *  @param point 搜索基准点
	 * @param entitys 需要搜索的对象(数组);entitys的第一值表示搜索类型：1，点周边；2，线周边；3，矩形搜索；4，多边形搜索
	 * @param map  地图对象
	 * @param layer 执行绘制要素的图层
	 * @param handler 要素绘制事件处理器，指定当前绘制的要素类型和操作方法
	 * @param queryResType 需查询资源类型(1：天网、2： 卡口、 3：卡点、警员、警车等等，(数组))
	 * @param options 设置该类及其父类开放的属性
	 * @param isActivate 是否立即生效
	 * @param isSearchMap 是否查询地图原始资源
	 * @param isNowSearch是否直接搜索（只针对 点周边、线周边）
	 * @returns
	 */
	initPointSearchFeature: function(point, entitys,queryResourceType,layer,sUrl,distance,lineSegment,handler, queryResType,options, isActivate, isSearchMap, isNowSearch){
		var searchMLayer = map.getLayersByName("searchMarkerLayer");
		if(searchMLayer && searchMLayer[0]) searchMLayer = searchMLayer[0];
		/*保存绘制所用到的属性*/
		var searchFeature = new Object();
		searchFeature.attrs = {
				searchEntitys: entitys, /*需要搜索的对象(数组)*/
				searchLayer: layer, /*搜索缓冲区显示的图层*/
				searchMarkerLayer: searchMLayer,
				searchHandler: handler,/*搜索类型*/
				searchUrl: sUrl,/*分析缓冲区的url*/
				searchDistance: distance ? distance : 120,/*缓冲区半径大小*/
				searchLineSegment: lineSegment ? lineSegment : 25,/*缓冲区指定点的数量*/
				searchQueryResType: queryResType, /*需查询资源类型(数组)*/
				markerDispaly: [], /**/ 
				isSearchMap: isSearchMap ? isSearchMap : true, /*是否查询地图原始资源*/ 
				flag: true,
				isNowSearch: isNowSearch  ? true : true
		};
		searchObj = searchFeature;
		/*var point = new SuperMap.Geometry.Point(5344.59369, -3609.27308);
		var entity = new Entity();
		entity.name = "测试";
		entity.type = 2;
		entity.longitude = 5344.59369;
		entity.latitude = -3609.27308;*/
		//MapToobar.doResourceChartOfPoint(entity, null, 30, 30);
        //var feature=new SuperMap.Feature.Vector(point);
		searchObj.attrs.searchSourceGeo = point;
		if(searchObj && searchObj.attrs) {
			searchObj.attrs.searchGeo = point; /* 缓冲区搜索源（要素）*/
			MapManager.bufferAnalystProcess(searchObj.attrs.searchUrl,
					point, 
					searchObj.attrs.searchDistance,
					searchObj.attrs.searchLineSegment, 
					MapToobar.bufferAnalystCompleted, null);
		}
		
		return searchFeature;
	},
	/**
	 *  绘制控件专题(点搜索、线搜索、多边形搜索等)
	 * @param entitys 需要搜索的对象(数组);entitys的第一值表示搜索类型：1，点周边；2，线周边；3，矩形搜索；4，多边形搜索
	 * @param map  地图对象
	 * @param layer 执行绘制要素的图层
	 * @param handler 要素绘制事件处理器，指定当前绘制的要素类型和操作方法
	 * @param queryResType 需查询资源类型(1：天网、2： 卡口、 3：卡点、警员、警车等等，(数组))
	 * @param options 设置该类及其父类开放的属性
	 * @param isActivate 是否立即生效
	 * @param isSearchMap 是否查询地图原始资源
	 * @param isNowSearch是否直接搜索（只针对 点周边、线周边）
	 * @returns
	 */
	initSearchFeature: function(entitys,queryResourceType,layer,sUrl,distance,lineSegment,handler, queryResType,options, isActivate, isSearchMap, isNowSearch){
		var searchFeature = MapManager.createDrawFeature(map, layer, isActivate, handler, options);
		var searchMLayer = map.getLayersByName("searchMarkerLayer");
		if(searchMLayer && searchMLayer[0]) searchMLayer = searchMLayer[0];
		/*保存绘制所用到的属性*/
		searchFeature.attrs = {
				searchEntitys: entitys, /*需要搜索的对象(数组)*/
				searchLayer: layer, /*搜索缓冲区显示的图层*/
				searchMarkerLayer: searchMLayer,
				searchHandler: handler,/*搜索类型*/
				searchUrl: sUrl,/*分析缓冲区的url*/
				searchDistance: distance ? distance : 120,/*缓冲区半径大小*/
				searchLineSegment: lineSegment ? lineSegment : 25,/*缓冲区指定点的数量*/
				searchQueryResType: queryResType, /*需查询资源类型(数组)*/
				markerDispaly: [], /**/ 
				isSearchMap: isSearchMap ? isSearchMap : true, /*是否查询地图原始资源*/ 
				flag: true,
				isNowSearch: isNowSearch  ? isSearchMap : false
		};
		searchObj = searchFeature;
		searchFeature.events.on({"featureadded": MapToobar.drawSearchCompleted} );
		return searchFeature;
	},
	/**
	 * 初始化 资源数据
	 * @param isInitDatas 是否初始化参数
	 * @param orgId 组织机构id
	 */
	initResourceDatas: function(isInitDatas, orgId){
		if(isInitDatas == null || isInitDatas == false){
			return;
		}
		var types = [1,2,3,4,5,6,7,8];
		for (var i = 0; i < types.length; i++){
			MapToobar.resourceRequest(orgId,types[i], null);
		}
	},
	/**
	 * 资源上图
	 * @param type 1：天网、2： 卡口、 3：卡点、4：警员:5：警车、6：巡区:、7：社区、8：辖区等等
	 * @param resDatas 格式为：[type,datas,status]
	 * @param layer
	 */
	initResourceChart: function(type, resDatas, layer){
		if(!resDatas) {
			resDatas = ResourceDatas.datas;
		}
		if(!layer){
			 if(type < 6){// 小于6的使用marker显示，其他使用Feature显示
				 var resMarkerLayer = map.getLayersByName("resourceMarkerLayer")[0];
				 if(!resMarkerLayer){
					 resMarkerLayer = MapManager.createMarkerLayer("resourceMarkerLayer");
				   	 MapManager.addLayer(map,resMarkerLayer);
				 }
				 layer = resMarkerLayer;
			 }else{
				 var resourceLayer = map.getLayersByName("resourceLayer")[0];
				 if(!resourceLayer) {
					 resourceLayer = MapManager.createVectorLayer("resourceLayer");
					 MapManager.addLayer(map,resourceLayer);
				 }
				 layer = resourceLayer
			 }
		}
		var resSatus = null; // 资源显示状态
		var resData = null; // 当前类型资源数据 对象
		for(var i = 0; i < resDatas.length; i++){
			if(resDatas[i][0] == type){
				resData = resDatas[i][1];
				resSatus = resDatas[i][2];
				ResourceDatas.datas[i][2] = !ResourceDatas.datas[i][2];
				break;
			}
		}
		var isEx = false; // 是否已经存在
		if(resSatus){//隐藏对应类型的资源数据
			if(type == 4) ResourceDatas.policeControl = false;
			else if(type == 5) ResourceDatas.policeCarControl = false;
			var delResData = [];
			if(type < 6){
				delResData = MapManager.clearMarkers(layer, [type]);
			}else{
				var features = layer.getFeaturesByAttribute("type", type);
				if(features){
					var delPops = MapManager.clearPopups(map,[type]);
					delResData = [features,delPops];
					layer.removeFeatures(features);
				}
			}
			for(var i = 0; i < ResourceDatas.delResDatas.length; i++){
				if(type == ResourceDatas.delResDatas[i][0]){
					 ResourceDatas.delResDatas[i][1] = delResData;
					isEx = true;
					break;
				}
			}
			if(!isEx)  {
				ResourceDatas.delResDatas.push([type, delResData]);
			}
			return;
		}else{
			for(var i = 0; i <  ResourceDatas.delResDatas.length; i++){
				if(type ==  ResourceDatas.delResDatas[i][0]){
					var das =  ResourceDatas.delResDatas[i][1];
					if(type < 6){
						for(var j in das){
							layer.addMarker(das[j]);
						}
					}else{
						layer.addFeatures(das[0]);
						for(var j in das[1]){
							map.addPopup(das[1][j]);
						}
					}
					isEx = true;
					break;
				}
			}
			if(!isEx){ // 不存在，则重新生成
				MapToobar.doResourceChart(resData, layer);
			}
			if(type == 4) ResourceDatas.policeControl = true;
			else if(type == 5) ResourceDatas.policeCarControl = true;
		}
	},
	
	
	
	
	/**********************二级方法 分割线************************/
	/**
	 * 鹰眼小工具 完成的回调方法
	 * @param eventArgs
	 */
	drawEyeCompleted: function(eventArgs){
		if(eyeObj.attrs.eyeFeature) eyeObj.attrs.eyeFeature.deactivate();
		var eyeGeo = eventArgs.feature.geometry;
		eyeObj.attrs.sourceGeo = eyeGeo;
		var queryMode = null;
		if(eyeObj.attrs.eyeBarType == 3){
			queryMode = SuperMap.REST.MeasureMode.DISTANCE;
		}else if(eyeObj.attrs.eyeBarType == 4){
			queryMode = SuperMap.REST.MeasureMode.AREA;
		}
		if(queryMode){
			if(!url) url = MapManager.getUrl();
			MapManager.measureFunction(url, eyeGeo,  queryMode, MapToobar.measureEyeCompleted);
		}
	},
	measureEyeCompleted: function(args){
		var sourceGeo = eyeObj.attrs.sourceGeo;
		var c = "",s = new Object();
    	s.w = "135px"; s.h = "20px;";
    	c = "<div style='width: 100%; height: 100%; color: red; '>";
		if(eyeObj.attrs.eyeBarType == 3){
			sourceGeo = sourceGeo.components[sourceGeo.components.length-1];
			var distance = args.result.distance/1000;
			c += "距离: "+distance.toFixed(2)+" km";
		}else if(eyeObj.attrs.eyeBarType == 4){
			sourceGeo = sourceGeo.components[0].components[sourceGeo.components[0].components.length-2];
			var area = args.result.area/1000;
			c += "面积: "+area.toFixed(2)+" k㎡";
		}
		c += "</div>";
    	var obj = {
    			lon: sourceGeo.x,
    			lat: sourceGeo.y
    	};
	  var pp = MapManager.openPopup ("",obj, c, s," ", 1, false);
	  pp.type = "eye";
	  map.addPopup(pp);
	},
	/**
	 * 绘制图形完成的回调方法
	 * @param eventArgs
	 */
	drawSearchCompleted: function(eventArgs){
		if(searchObj) MapManager.setControlIsActivate(searchObj, false);
		var geometry = eventArgs.feature.geometry.components[0];
		searchObj.attrs.searchSourceGeo = eventArgs.feature.geometry;
		if(searchObj && searchObj.attrs) {
			searchObj.attrs.searchGeo = geometry; /* 缓冲区搜索源（要素）*/
			if(searchObj.attrs.searchHandler != SuperMap.Handler.Polygon 
					&& searchObj.attrs.searchHandler != SuperMap.Handler.RegularPolygon){
				MapManager.bufferAnalystProcess(searchObj.attrs.searchUrl,
						geometry, 
						searchObj.attrs.searchDistance,
						searchObj.attrs.searchLineSegment, 
						MapToobar.bufferAnalystCompleted, null);
			}else{
				if(searchObj.attrs.searchHandler == SuperMap.Handler.RegularPolygon){
					geometry =  new SuperMap.Geometry.Polygon([geometry]);
				}
				searchObj.attrs.searchFea = eventArgs.feature;
				MapToobar.queryByGeo(geometry);
			}
		}
	 } ,
	 /**
	  * 根据图形查询数据（地图提供）
	  * @param geo 查询区图形
	  * @param params
	  * @param type 参数类型，0：需查询
	  */
	 queryByGeo: function(geo){
		 searchObj.attrs.existsDatas = [];
		 if(searchObj.attrs.searchMarkerLayer) searchObj.attrs.searchMarkerLayer.clearMarkers();
		 $("#searchResultDialog").data("kendoWindow").close();
		 
		 MapToobar.openSearchResult();
		 /*异步执行*/
		 setTimeout(function () {
　　　　　　var entitys = searchObj.attrs.searchEntitys;
						 if(entitys && entitys.length > 1){
							 for(var i = 0; i < entitys[1].length; i++){
								 var entity = entitys[1][i];
								 MapToobar.queryCenterdatas(geo, entity);
								 MapToobar.buildResDatas(entity[0]);
							 }
						 }
　　　　}, 300);
		 
		 // 是否查询地图原始资源
		 /*var isQueryMapRes = false;
		 var resTypes = searchObj.attrs.searchQueryResType;
		 for(var i = 0; i < resTypes.length; i++){
			 if(resTypes[i] > 10){
				 isQueryMapRes = true;
				 continue;
			 }
		 }
		 if(isQueryMapRes && searchObj.attrs.isSearchMap){
		 	var geoParams = {
					name: "Government@Changchun.2"	
				};
		 	MapManager.queryByGeometry(url,
		 		geoParams, 
		 		geo,
				SuperMap.REST.SpatialQueryMode.INTERSECT, 
				MapToobar.queryCompleted);
		 }else{
			 
			 //MapToobar.openSearchResult();
			 
			 // 距离测算 只针对 点周边 类型 （暂时取消测距功能）
			 if(searchObj.attrs.searchEntitys.length > 1 && searchObj.attrs.searchEntitys[0] != 1){
				 MapToobar.openSearchResult();
			 }else{
				 MapToobar.measureDistance();
			 }
		 }*/
	 },
	 /**
	  * 绑定资源搜索数据
	  */
	 buildResDatas: function(type, searchValue){
		 var existsDatas = searchObj.attrs.existsDatas;
			var existsDataDistances = searchObj.attrs.existsDataDistances;
			var cardPointHtml = "",cardPointNum = 0,cardPointMarkers = [],
			bayonetHtml = "",bayonetNum = 0,
			gBDeviceHtml = "",gBDeviceNum = 0,
			otherHtml = "",otherNum = 0,
			policeHtml = "",policeNum = 0,
			policeCarHtml = "", policeCarNum = 0;
			for(var i = 0 ; i < existsDatas.length; i ++){
				var distance = "--";
				if(existsDataDistances && existsDataDistances.length > i){
					distance = existsDataDistances[i] ? existsDataDistances[i].toFixed(4) : "--";
				}
				var existsData = existsDatas[i];
				if(!searchValue && searchValue != "" && type != existsData[0]){
					continue;
				}
				var datas = existsData[1];
				for(var j in datas){
					var data = datas[j];
					if((searchValue && data.name && data.name.indexOf(searchValue) > -1) || !searchValue){
						if(existsData[0] == 1){
							gBDeviceNum++;
							gBDeviceHtml += "<tr  height='25px'><td align='center'><input name='gBDeviceBox' type='checkbox'></td><td width='210px;'>"+data.name+"</td><td align='center'>"+distance+"</td><tr>";
						}else if(existsData[0] == 2){
							bayonetNum++;
							bayonetHtml += "<tr height='25px'><td align='center'><input name='bayonetBox' type='checkbox'></td><td width='210px;'>"+data.name+"</td><td align='center'>"+distance+"</td><tr>";
						}else if(existsData[0] == 3){
							cardPointNum++;
							cardPointHtml += "<tr height='25px'><td align='center'><input name='cardPointBox' type='checkbox'></td><td width='210px;'>"+data.name+"</td><td align='center'>"+distance+"</td><tr>";
						}else if(existsData[0] == 4){
							policeNum++;
							policeHtml += "<tr height='25px'><td align='center'><input name='policeBox' type='checkbox'></td><td width='210px;'>"+data.name+"</td><td align='center'>"+distance+"</td><tr>";
						}else if(existsData[0] == 5){
							policeCarNum++;
							policeCarHtml += "<tr height='25px'><td align='center'><input name='policeCarBox' type='checkbox'></td><td width='210px;'>"+data.name+"</td><td align='center'>"+distance+"</td><tr>";
						}
					}
					if((searchValue && data.name 
							&& data.name.indexOf(searchValue) > -1) || !searchValue){
						if(existsData[0] == 11){
							otherNum++;
							otherHtml += "<tr height='25px'><td align='center'><input name='otherBox' type='checkbox'></td><td width='210px;'>"+data.name+"</td><td align='center'>"+distance+"</td><tr>";
						}
					}
				}
				if(existsData[0] == 1){
					gBDeviceHtml = "<tr><td  width='35px' align='center'><input name='gBDeviceAllBox' type='checkbox' onclick='MapToobar.selectIsAll(this,\"gBDevice\")'></td><td width='210px;' align='center'>点位名称</td><td align='center'>距离(km)</td><tr>"
						+gBDeviceHtml;
					$("#gBDeviceHtml").html(gBDeviceHtml);
					$("#gBDeviceNum").html(gBDeviceNum);
				}else if(existsData[0] == 2){
					bayonetHtml = "<tr><td  width='35px' align='center'><input name='cardPointAllBox' type='checkbox' onclick='MapToobar.selectIsAll(this,\"cardPoint\")'></td><td width='210px;' align='center'>卡点名称</td><td align='center'>距离(km)</td><tr>"
						+bayonetHtml;
					$("#bayonetHtml").html(bayonetHtml);
					$("#bayonetNum").html(bayonetNum);
				}else if(existsData[0] == 3){
					cardPointHtml = "<tr><td  width='35px' align='center'><input name='bayonetAllBox' type='checkbox' onclick='MapToobar.selectIsAll(this,\"bayonet\")'></td><td width='210px;' align='center'>卡口名称</td><td align='center'>距离(km)</td><tr>"
						+cardPointHtml;
					$("#cardPointHtml").html(cardPointHtml);
					 $("#cardPointNum").html(cardPointNum);
				}else if(existsData[0] == 4){
					policeHtml = "<tr><td  width='35px' align='center'><input name='policeAllBox' type='checkbox' onclick='MapToobar.selectIsAll(this,\"police\")'></td><td width='210px;' align='center'>姓名</td><td align='center'>距离(km)</td><tr>"
						+policeHtml;
					$("#policeHtml").html(policeHtml);
					 $("#policeNum").html(policeNum);
				}else if(existsData[0] == 5){
					policeCarHtml = "<tr><td  width='35px' align='center'><input name='policeCarAllBox' type='checkbox' onclick='MapToobar.selectIsAll(this,\"policeCar\")'></td><td width='210px;' align='center'>名称</td><td align='center'>距离(km)</td><tr>"
						+policeCarHtml;
					$("#policeCarHtml").html(policeCarHtml);
					 $("#policeCarNum").html(policeCarNum);
				}
				/*else if(existsData[0] == 11){
					otherHtml = "<tr><td  width='35px' align='center'><input name='otherAllBox' type='checkbox' onclick='MapToobar.selectIsAll(this,\"other\")'></td><td width='210px;' align='center'>名称</td><td align='center'>距离(km)</td><tr>"
						+otherHtml;
					$("#otherHtml").html(otherHtml);
					$("#otherNum").html(otherNum);
				}*/
			}
	 },
	 
	 /**
	  * 对业务对象 模拟查询
	  * @param geometry
	  */
	 queryCenterdatas: function(geometry, datas){
	 	var existsPoints = [];
	 	if(!datas){
	 		datas = [[5344.59369,-3609.27308], [5286.62921,-3653.35024], [5192.43694, -3624.36800],[5400.14297,-3657.57681],[5404.97335,-3587.53641],
		 	          [5189.41796,-3410.02020],[5337.34813,-3461.34292],[5001.63721,-3393.71769],[5037.86501,-3523.53397],[5487.69348,-3656.97302]];
	 		var ens = [];
	 		for(var i in datas){
	 			var en = new Entity();
	 			en.longitude =datas[i][0] ;
	 			en.latitude = datas[i][1] ;
	 			ens.push(en);
	 		}
	 		datas = [2,ens];
	 	}
	 	var entitys = datas[1];
	 	for(var i = 0; i < entitys.length; i++){
	 		var point = new SuperMap.Geometry.Point(entitys[i].longitude, entitys[i].latitude);
	 		var pointDatas = entitys[i];
	 		if(geometry.intersects(point)){
	 			var fea = new SuperMap.Feature.Vector(point);
	 			fea.attributes = {
	 				datas: pointDatas,
	 				type: datas[0]
	 			}
	 			var markerObj = MapToobar.createQueryResult(fea, "my");
	 			existsPoints.push(pointDatas);
	 		}
	 	}
	 	searchObj.attrs.existsDatas.push([datas[0],existsPoints,true]);
	 },
	/**
	 * 分析成功后的回调函数
	 * @param BufferAnalystEventArgs
	 */
	bufferAnalystCompleted: function (BufferAnalystEventArgs) {
		if(searchObj.attrs.searchMarkerLayer) searchObj.attrs.searchMarkerLayer.clearMarkers();
		if(searchObj && searchObj.attrs && searchObj.attrs.searchFea) {
			searchObj.attrs.searchLayer.removeFeatures(searchObj.attrs.searchFea);
		}
	    var feature = new SuperMap.Feature.Vector();
	    var bufferResultGeometry = BufferAnalystEventArgs.result.resultGeometry;
	    feature.geometry = bufferResultGeometry;
	    searchObj.attrs.searchFea = feature; /*搜索缓冲区要素*/
	    searchObj.attrs.searchLayer.addFeatures(feature);
	    // 直接执行搜索操作
	    if(searchObj.attrs.isNowSearch){
	    	MapToobar.queryByGeo(searchObj.attrs.searchFea.geometry);
	    	return;
	    }
	    
	    if(!searchObj.attrs.flag){
	    	return;
	    }
	    searchObj.attrs.flag = false;
		var la = bufferResultGeometry.bounds;
    	var lonlat = new Object();
    	lonlat.lon = (la.right + la.left)/2 + (la.right - la.left)/4;
    	lonlat.lat = (la.top + la.bottom)/2;
    	var content = "<div style='height: 70px;'><div style=‘position: absolute; left: 1px; top;10px;’>";
    	content += "<span>半径</span>";
    	content += "<input id='slider' style='width: 200px;' />";
    	content += "<span id='distance'>500</span>";
    	content += "米</div>";
    	content += "<div style='position: absolute;left: 105px;top; 10px;'><input type='button' value='搜索' onclick='MapToobar.queryByGeo(searchObj.attrs.searchFea.geometry);' style='width: 50px;background: red'></div>";
    	content += "</div>";
    	var popup = MapManager.openFramedCloud(null, lonlat, content,null, false);
    	map.addPopup(popup);
    	searchObj.attrs.searchPop = popup; /*搜索提示框对象*/
    	$("#slider").kendoSlider({
 			min:10,
 			max:500,
 			change:function(e){
 				$("#distance").html(e.value);
 				MapManager.bufferAnalystProcess(searchObj.attrs.searchUrl, 
 						searchObj.attrs.searchGeo, 
 						Number(e.value), 
 						searchObj.attrs.searchLineSegment, 
 						MapToobar.bufferAnalystCompleted);
 			}
 		});
    	var slider = $("#slider").data("kendoSlider");
    	slider.value(searchObj.attrs.searchDistance);
	},
	/**
	 * 查询完成回调方法
	 * @param queryEventArgs
	 */
	queryCompleted: function (queryEventArgs){
		var  type = 11;
		var i, j, result = queryEventArgs.result;
	    if (result && result.recordsets) {
	    	var entitys = [];
	        for (i=0, recordsets=result.recordsets, len=recordsets.length; i<len; i++) {
	            if (recordsets[i].features) {
	                for (j=0; j<recordsets[i].features.length; j++) {
	                    var feature = recordsets[i].features[j];
	                    var point = feature.geometry;
	                    if(point.CLASS_NAME == SuperMap.Geometry.Point.prototype.CLASS_NAME){
	                    	var objInfo = feature;
	                    	feature.attributes = {
		        	 				datas: objInfo,
		        	 				type: type
		        	 			}
	                    	var point = feature.geometry;
	                    	var entity = new Entity();
	                    	entity.name = feature.data.NAME;
	                    	entity.longitude = point.y;
	                    	entity.latitude = point.x;
	                    	entity.type = type;
	                    	entity.detailInfo = feature;
	                    	entitys.push(entity);
	                    	//var datas = [type,point.x, point.y,objInfo, type];
	                    	var markerObj =  MapToobar.createQueryResult(feature);
	        	 			//datas.push(markerObj);
	                    }else{
	                        feature.style = style;
	                        searchObj.attrs.searchLayer.addFeatures(feature);
	                    }

	                }
	            }
	        }
	        var isHave = false;
	        for(var j in searchObj.attrs.existsDatas){
	        	if(searchObj.attrs.existsDatas[j][0] == type){
	        		isHave = true;
	        		searchObj.attrs.existsDatas[j][1] = entitys
	        	}
	        }
	        if(!isHave) searchObj.attrs.existsDatas.push([type, entitys, true]);
	    }
	},
	/**
	 * 根据查询结果 绘点
	 * @param point 
	 * @param type 类型
	 */
	createQueryResult: function(feature, type){
		var point = feature.geometry;
		var pointType = feature.attributes.type;
		if(!type) pointType = type;
		var icon = MapToobar.getIcon(pointType,22,22);
		var mk = new SuperMap.Marker(new SuperMap.LonLat(point.x, point.y), icon);
		mk.attributes = {
			feaObj: feature
		};
		mk.type = feature.attributes.type;
		mk.events.on({
	        "click": function(){
	        	MapManager.clearPopups(map,["search"]);
	        	var mkObj = this;
	        	var currentFeature = mkObj.attributes.feaObj;
	        	var objinfo = currentFeature.attributes.datas;
	        	var c = "",s = new Object();
	        	s.w = "270px"; s.h = "40px;";
    			c = "<div style='width: 100%; height: 100%; '>";
    			c += "<div style='position:absolute;left: 10px; width: 90%; '>";
	        	if(currentFeature.attributes.type == 1){
	    			c += "<table width='100%'><tr><td width='80px'>天网名称：</td> <td width='200px'>"+objinfo.name+"</td></tr></table>";
	    		}else if(currentFeature.attributes.type == 2){ 
	    			c += "<table width='100%'><tr><td width='80px'>卡口名称：</td> <td width='200px'>"+objinfo.name+"</td></tr></table>";
	    		}else if(currentFeature.attributes.type == 3){
	    			c += "<table width='100%'><tr><td width='80px'>卡点名称：</td> <td width='200px'>"+objinfo.name+"</td></tr></table>";
	    		}else if(currentFeature.attributes.type == 4){
	    			c += "<table width='100%'><tr><td width='80px'>姓名：</td> <td width='200px'>"+objinfo.name+"</td></tr></table>";
	    		}else if(currentFeature.attributes.type == 5){
	    			c += "<table width='100%'><tr><td width='80px'>名称：</td> <td width='200px'>"+objinfo.name+"</td></tr></table>";
	    		}else if(currentFeature.attributes.type == 10){
		    		c += "<table width='100%'><tr><td width='50px'>名称：</td> <td width='150px'>"+currentFeature.data.NAME+"</td></tr>";
	    		}else{
	    			  return;
	    		}
	        	c += "</div></div>";
	        	var obj = {
	        			lon: currentFeature.geometry.x,
	        			lat: currentFeature.geometry.y
	        	};
	        	  var pp = MapManager.openPopup ("",obj, c, s,"#2A3D47", 0.7, false);
	        	  pp.type = "search";
	        	  map.addPopup(pp);
	        },
		    "scope": mk
		});
		if(searchObj && searchObj.attrs 
				&& searchObj.attrs.searchMarkerLayer) {
			searchObj.attrs.searchMarkerLayer.addMarker(mk);
		}
		return mk;
	},
	/**
	 * 对搜索结果进行选择性显示
	 * @param type
	 */
	selectDisplay: function(type){
		var isHave = false;
		var layer = searchObj.attrs.searchMarkerLayer;
		var existsDatas = searchObj.attrs.existsDatas;
		for(var i in existsDatas){
			if(existsDatas[i][0] == type){
				isHave = true;
				var datas = existsDatas[i][1];
				var resStatus = searchObj.attrs.existsDatas[i][2];
				searchObj.attrs.existsDatas[i][2] = !searchObj.attrs.existsDatas[i][2];
				if(!searchObj.attrs.delDatas) searchObj.attrs.delDatas = [];
				if(resStatus) {
					var delMaks = MapManager.clearMarkers(layer, [type]);
					var isEx = false;
					for(var j in searchObj.attrs.delDatas){
						if(searchObj.attrs.delDatas[j][0] == type){
							isEx = true;break;
						}
					}
					if(!isEx)searchObj.attrs.delDatas.push([type,delMaks]);
				}else{
					for(var j in searchObj.attrs.delDatas){
						var delData = searchObj.attrs.delDatas[j];
						if(delData[0] == type){
							for(var k in delData[1]){
								layer.addMarker(delData[1][k]);
							}
						}
					}
				}
				break;
			}
		}
		if(!isHave && type > 10){ // 表示地图原始图层搜索
			var geoParams = {
					name: "Government@Changchun.2"	
				};
		 	MapManager.queryByGeometry(url,
		 		geoParams, 
		 		searchObj.attrs.searchFea.geometry,
				SuperMap.REST.SpatialQueryMode.INTERSECT, 
				MapToobar.queryCompleted);
		 	return;
		}
	},
	
	/**
	 * 距离测算
	 */
	measureDistance: function(){
		searchObj.attrs.existsDataDistances = [];
		var existsDatas = searchObj.attrs.existsDatas;
		var centerPoint = searchObj.attrs.searchSourceGeo.components[0];
		for(var i = 0 ; i < existsDatas.length; i ++){
			var existsData = existsDatas[i];
			for(var j in existsData){
				var points = [];
				points.push(centerPoint);
				points.push(MapManager.createPoint([existsData[1].longitude, existsData[1].latitude]));
				var geometry = MapManager.createLineString(points);
				MapManager.measureFunction(url, geometry,  SuperMap.REST.MeasureMode.DISTANCE, MapToobar.measureCompleted);
			}
		}
	},
	/**
	 * 距离测算 回调方式
	 */
	measureCompleted: function(arg){
		var cur = this;
		searchObj.attrs.existsDataDistances.push(arg.result.distance/1000);
		//if(searchObj.attrs.existsDataDistances.length == searchObj.attrs.existsDatas.length){
			MapToobar.openSearchResult();
		//}
	},
	/**
	 * 范围搜索结果 展示
	 */
	openSearchResult: function(){
		var rc = "<div>";
		rc += "<div style='height: 90px;'>&nbsp;&nbsp;" +
				"<img onclick='MapToobar.selectDisplay(4)' width='25px' height='25px' src='"+basePath+"supermap/theme/images/MapIcos/jiaojingPoliceIco.png'>&nbsp;&nbsp;" +
				"<img onclick='MapToobar.selectDisplay(5)' width='25px' height='25px' src='"+basePath+"supermap/theme/images/MapIcos/carIco.png'>&nbsp;&nbsp;" +
				"<img onclick='MapToobar.selectDisplay(2)' width='25px' height='25px' src='"+basePath+"supermap/theme/images/MapIcos/GisGunCameraNormal.png'>&nbsp;&nbsp;" +
				"<img onclick='MapToobar.selectDisplay(3)' width='25px' height='25px' src='"+basePath+"supermap/theme/images/MapIcos/CardDotNomal.png'>&nbsp;&nbsp;" +
				"<img onclick='MapToobar.selectDisplay(1)' width='25px' height='25px' src='"+basePath+"supermap/theme/images/MapIcos/GisBallCameraNormal.png'>";
		rc += "&nbsp;&nbsp;<img onclick='MapToobar.selectDisplay(11)' src='"+basePath+"Skin/Default/images/button2.png'>&nbsp;&nbsp;<img src='"+basePath+"Skin/Default/images/button2.png'>&nbsp;&nbsp;<img src='"+basePath+"Skin/Default/images/button2.png'>";
		rc += "<br>&nbsp;&nbsp;<img src='"+basePath+"Skin/Default/images/button2.png'>&nbsp;&nbsp;<img src='"+basePath+"Skin/Default/images/button2.png'>&nbsp;&nbsp;<img src='"+basePath+"Skin/Default/images/button2.png'>";
		rc += "</div>";
		rc += "<div style='margin-top: 15px;'><input id='searchValue' class='k-textbox' type='text' value=''><img onclick='MapToobar.searchByValue()' src='"+basePath+"Skin/Default/images/button2.png'></div>";
		rc += "<div id='resultsDiv' style='margin-top: 15px;height: 410px;OVERFLOW-Y: auto; OVERFLOW-X:hidden;'>";
		rc += MapToobar.buildSearchTable();
		rc += "</div>";
		rc += "<div style='padding-top: 15px; width: 92%;text-align: right;'><button type='button' onclick='MapToobar.saveArea(this);' data-click='save' style='width:85px;;height:32px;line-height:24px;background-color:#B7202A;color:#FFF;font-size:14px;border:0;border-radius:2px;'>区域收藏</button></div>";
		rc += "</div>";
		
		var searchResultDialog = $("#searchResultDialog").data("kendoWindow");
		searchResultDialog.content(rc);
		searchResultDialog.open();
	},
	/**
	 * 组装结果
	 * @returns {String}
	 */
	buildSearchTable: function(){
		var content = "";
		content += "<div onclick='MapToobar.controlDisplay(\"police\")' style='width: 340px;height: 25px;padding-top:5px;padding-left:8px;background: #3E5662;cursor:pointer;margin-bottom: 10px;'>警员(<span id='policeNum'>"
			+"0</span>个)</div><div id='police' style='display:none; height: 200px;margin-top:10px;OVERFLOW-Y: auto; OVERFLOW-X:hidden;'>" +
					"<table id='policeHtml'></table></div>";
		content += "<div onclick='MapToobar.controlDisplay(\"policeCar\")' style='width: 340px;height: 25px;padding-top:5px;padding-left:8px;background: #3E5662;cursor:pointer;margin-bottom: 10px;'>警车(<span id='policeCarNum'>"
			+"0</span>个)</div><div id='policeCar' style='display:none; height: 200px;margin-top:10px;OVERFLOW-Y: auto; OVERFLOW-X:hidden;'>" +
					"<table id='policeCarHtml'></table></div>";
		content += "<div onclick='MapToobar.controlDisplay(\"gBDevice\")' style='width: 340px;height: 25px;padding-top:5px;padding-left:8px;background: #3E5662;cursor:pointer;margin-bottom: 10px;'>天网(<span id='gBDeviceNum'>"
			+"0</span>个)</div><div id='gBDevice' style='display:none; height: 200px;margin-top:10px;OVERFLOW-Y: auto; OVERFLOW-X:hidden;'>" +
					"<table id='gBDeviceHtml'></table></div>";
		content += "<div onclick='MapToobar.controlDisplay(\"cardPoint\")' style='width: 340px;height: 25px;padding-top:5px;padding-left:8px; background: #3E5662;cursor:pointer;margin-bottom: 10px;'>卡点(<span id='cardPointNum'>"
			+"0</span>个)</div><div  id='cardPoint' style='display:none; height: 200px;OVERFLOW-Y: auto; OVERFLOW-X:hidden;'>" +
					"<table id='cardPointHtml'></table></div>";
		content += "<div onclick='MapToobar.controlDisplay(\"bayonet\")' style='width: 340px;height: 25px;padding-top:5px;padding-left:8px;background: #3E5662;cursor:pointer;margin-bottom: 10px;'>卡口(<span id='bayonetNum'>"
			+"0</span>个)</div><div id='bayonet' style='display:none; height: 200px;margin-top:10px;OVERFLOW-Y: auto; OVERFLOW-X:hidden;'>" +
					"<table id='bayonetHtml'></table></div>";
/*		content += "<div onclick='MapToobar.controlDisplay(\"otherDiv\")' style='width: 340px;height: 25px;padding-top:5px;padding-left:8px;background: #3E5662;cursor:pointer;'>其他(<span id='otherNum'>"
			+"0</span>个)</div><div id='otherDiv' style='display:none; height: 200px;margin-top:10px;OVERFLOW-Y: auto; OVERFLOW-X:hidden;'>" +
					"<table id='otherHtml'></table></div>";*/
		return content;
	},
	/**
	 * 全选事件
	 * @param obj
	 * @param idStr
	 */
	selectIsAll:function(obj, idStr){
		var checks = document.getElementsByName(idStr+"Box");
		for(var i = 0; i < checks.length; i++){
			var check = checks[i];
			if(check) {
				if(obj.checked){
					check.checked = true;
				}else{
					check.checked = false;
				}
			}
		}
	},
	/**
	 * 查询功能
	 */
	searchByValue: function(){
		var content = MapToobar.buildResDatas(null, $("#searchValue").val());
		if(content){
			$("#resultsDiv").html("");
			$("#resultsDiv").html(content);
		}
	},
	/**
	 * 保存区域收藏
	 * @param obj
	 */
	saveArea: function(obj){
		var content = "<div style='width: 100%;height: 100%'><div style='postition: absolute;width: 100%;'>";
		content+= "<span>区域收藏名称 : </span><input id='groupName' type='text' class='k-textbox' ></div>";
		content+= "<div  style='width: 100%;text-align: right;margin-top: 15px;'><button type='button' onclick='MapToobar.doSaveArea();' data-click='save' style='width:50px;height:32px;line-height:24px;background-color:#B7202A;color:#FFF;font-size:14px;border:0;border-radius:2px;'>保存</button>" +
				"&nbsp;&nbsp;&nbsp;&nbsp;<button type='button' onclick='javascript:$(\"#addGroupDialog\").tyWindow.close();' data-click='save' style='width:50px;;height:32px;line-height:24px;background-color:#B7202A;color:#FFF;font-size:14px;border:0;border-radius:2px;'>取消</button>&nbsp;&nbsp;&nbsp;&nbsp;";
		content+= "</div></div>";
		$("#addGroupDialog").tyWindow({
			width: "380px",
			height: "250px",
			title: "添加区域收藏",
			position: {
				top: "400px",
				left: "600px"
			},
			content: content,
		});
		return;
	},
	/**
	 * 执行保存区域收藏
	 */
	doSaveArea: function(){
		if(!searchObj.attrs || !searchObj.attrs.searchFea || !searchObj.attrs.searchFea.geometry){
			return;
		}
		if(!$("#groupName").val() || $("#groupName").val().trim() == ""){
			$("body").popjs({"title":"提示","content":"区域收藏名称不能为空！"});
			return;
		}
		var entitys = searchObj.attrs.searchEntitys;
		if(!entitys || entitys.length <1) return;
		var geo = searchObj.attrs.searchFea.geometry;
		var points = [];
		var areaType = entitys[0];
		if(areaType == 3){
			points = geo.components[0].components;
		}else if(areaType == 1 || areaType == 2 || areaType == 4){
			points = geo.components[0].components[0].components;
		}
		var area_Points = [];
		for(var i = 0; i < points.length; i++){
			var point = points[i];
			var pointJSON = {
					"x": point.x,
					"y": point.y
			};
			area_Points.push(pointJSON);
		}
		// 中心点，暂时只记录 点周边类型
		var centerPoint = null;
		if(searchObj.attrs.searchSourceGeo && areaType == 1){
			var sourceGeo = searchObj.attrs.searchSourceGeo;
			centerPoint = {
					"x": sourceGeo.components[0].x,
					"y": sourceGeo.components[0].y
			};
		}
		var areaContent = {
				"centerPoint": centerPoint,
				"areaType": areaType,
				"area_Points": area_Points
		};
		$.ajax({
			url:  basePath+ "client/groupManager/saveGroupInfo.do",
			type: "post",
			dataType: "json",
			data: {
				groupName: $("#groupName").val(),
				userId: $("#userId").val(),
				organId: $("#orgId").val(),
				shareType: 1,
				groupType: 2,
				sourceData: "",
				areaType: areaType,
				areaContent: JSON.stringify(areaContent),
				random: Math.random()
			},
			success: function(msg){
				if(msg.code==200){
					$("body").popjs({"title":"提示","content": "保存成功！"});
					$("#addGroupDialog").tyWindow.close();
				}else{
					$("body").popjs({"title":"提示","content":msg.description});
				}
			}
		});
	},
	/**
	 * 控制元素的显示与隐藏
	 * @param idstr
	 */
	controlDisplay:function(idstr){ 
		$("#"+idstr).toggle();
	},
	/**
	 * @param orgId 组织机构id
	 * @param type 资源类型，1：天网、2： 卡口、 3：卡点、4：警员:5：警车、6：巡区:、7：社区、8：辖区等等
	 * @param data 参数
	 */
	resourceRequest: function(orgId, type, data){
		if(!orgId) orgId  = Number($("#orgId").val());
		if(data == null){
			data = {
					organId :  orgId,
					random: Math.random()
				};
		}
		var u = basePath;
		if(type == 1){
			u += MapResourceUrl.getGBDevices;
		}else if(type == 2){
			u += MapResourceUrl.getBayonets;
		}else if(type == 3){
			u += MapResourceUrl.getCardPoints;
		}else if(type == 4){
			u += MapResourceUrl.getPolices;
			if(!data.isSubOrg) data.isSubOrg = 2;
		}else if(type == 5){
			u += MapResourceUrl.getPoliceCars;
			if(!data.isSubOrg) data.isSubOrg = 2;
		}else if(type == 6 || type == 7 || type == 8){
			u += MapResourceUrl.getAreaInfo;
			if(!data.organIds) data.organIds = orgId;
			if(!data.areaType) data.areaType = type - 5;
		}
		$.ajax({
			url:  u,
			type: "post",
			dataType: "json",
			data: data,
			success: function(res){
				if(res.data && res.data.length > 0){
					var entitys = [];
					for(var i = 0; i < res.data.length; i++){
						var obj = res.data[i];
						if(type == 4 || type == 5) {
							obj = obj.data;
							if(!obj.gpsId || obj.gpsId == 0) continue;
						}
						if(type < 4 && (obj.longitude == "0.0" || obj.latitude == "0.0"))
							continue;
						var entity = new Entity();
						entity.type = type;
						entity.id = type == 2 ? obj.bayonetId : obj.id;
						entity.name = type < 5 ? obj.name : (type == 5 ? obj.number : obj.areaName);
						entity.latitude = type < 4 ? obj.latitude :  obj.y;
						entity.longitude = type < 4 ? obj.longitude : obj.x;
						entity.detailInfo = obj;
						entitys.push(entity);
					}
					if(entitys.length > 0){
						var resStatus = false;
						// 删除数据集中的旧数据
						for(var j in ResourceDatas.datas){
							var resDatas = ResourceDatas.datas[j];
							if(resDatas[0] == type) {
								resStatus = resDatas[2];
								ResourceDatas.datas.splice(j,1);
								break;
							}
						}
						// 删除GPS数据集中的旧数据
						for(var j in ResourceDatas.gpsResDatas){
							var gpsResDatas = ResourceDatas.gpsResDatas[j];
							if(gpsResDatas[0] == type) {
								resStatus = gpsResDatas[2];
								ResourceDatas.gpsResDatas.splice(j,1);
								break;
							}
						}
						var datas = [];
						// 参数介绍：type为资源类型；entitys为对应具体数据；false表示当前资源的状态为‘未上图’，如果是true表示已上图
						datas.push(type, entitys, resStatus);
						ResourceDatas.datas.push(datas);
						if(type == 4 || type == 5){
							ResourceDatas.gpsResDatas.push(datas);
						}
					}
				}
			}
		});
	},
	/**
	 * 上图操作
	 * @param resData
	 * @param layer
	 */
	doResourceChart: function(resData, layer){
		for(var j = 0; resData != null && j < resData.length; j++){
			var entity = resData[j];
			if(entity.type < 6){ // type小于6，表示只是关于点的上图
				if(!entity.latitude || !entity.longitude) continue;
				MapToobar.doResourceChartOfPoint(entity, layer);
			}else{
				var name = entity.name;
				var displayProperty = entity.detailInfo.displayProperty;
				var mapProperty = entity.detailInfo.mapProperty;
				if(mapProperty == null) return;
				// 组装圈层各个顶点
			    var points=[], createfeatures =[];
			    for(var i= 0,len=mapProperty.length; i<len;i++){
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
			    var feature=new SuperMap.Feature.Vector(polygon,null, mystyle);
			    feature.attributes.type = entity.type;
			    createfeatures.push(feature);
			    layer.addFeatures(createfeatures);
			    
			    // 生成对应圈层的标签
			    var content = "<span style='border: 1px; border-style: solid;border-color: red;color: red; padding: 4px 3px 4px 3px; margin-left: -4px;'>"+ entity.name;
				content += "</span>";
				var size = new Object();
				size.w = entity.name.length * 14; size.h = 20;
				var lonlat = {
						lon: displayProperty.x,
						lat: displayProperty.y
				};
				var tipWin = MapManager.openPopup("test", lonlat, content, size," ", 1, false);
				tipWin.type = entity.type;
				map.addPopup(tipWin);
			}
		}
	},
	/**
	 * 对于点对象上图
	 * @param entity 数据实体类
	 * @param layer
	 * @param iw 图片的宽度
	 * @param ih 图片的高度
	 */
	doResourceChartOfPoint: function(entity, layer,iw,ih){
		if(!layer){
			layer = map.getLayersByName("resourceMarkerLayer")[0];
			 if(!layer){
				 layer = MapManager.createMarkerLayer("resourceMarkerLayer");
			   	 MapManager.addLayer(map,layer);
			 }
		}
		if(!iw) iw = 22; if(!ih) ih = 22;
		var icon =MapToobar.getIcon(entity.type,iw,ih);
		var mk = new SuperMap.Marker(new SuperMap.LonLat(entity.longitude, entity.latitude), icon);
		mk.attributes = {
			entity: entity
		};
		mk.id = entity.id;
		mk.type = entity.type;
		layer.addMarker(mk);
		mk.events.on({
	        "click": function(){
	        	MapManager.clearPopups(map, ["search"]);
	        	var mkObj = this;
	        	var currentEntity = mkObj.attributes.entity;
	        	var info = currentEntity.detailInfo;
	        	var c = "",s = new Object();
	        	s.w = "270px"; s.h = "40px";
    			c = "<div style='width: 100%; height: 100%; '>";
    			c += "<div style='position:absolute;left: 10px; width: 90%; '>";
    			c += "<table width='100%' id='resTable'>";
	        	if(currentEntity.type == 1){
	    			c += "<tr><td width='80px'>天网名称：</td> <td width='200px'>"+currentEntity.name+"</td></tr>";
	    		}else if(currentEntity.type == 2){ 
	    			 s.h = "240px";
	    			c += "<tr height='35px'><td width='80px'>卡口状态：</td> <td width='200px'>"+(info.state ==1 ? "待建" : "已建")+"</td></tr>";
	    			c += "<tr height='35px'><td width='80px'>卡口名称：</td> <td width='200px'>"+currentEntity.name+"</td></tr>";
	    			c += "<tr height='35px'><td width='80px'>卡口方向：</td> <td width='200px'>"+(info.direction == null ? "" : info.direction)+"</td></tr>";
	    			c += "<tr height='35px'><td width='80px'>卡口类型：</td> <td width='200px'></td><tr>";
	    			c += "<tr height='35px'><td width='80px'>车道名称：</td> <td width='200px'>"+(info.lane == null ? "" : info.lane)+"</td></tr>";
	    			c += "<tr height='35px'><td width='80px'>管辖单位：</td> <td width='200px'>"+info.organId +"</td></tr>";
	    		}else if(currentEntity.type == 3){
	    			 s.h = "315px"; s.w = "255px";
	    			c += "<tr height='35px'><td width='80px'>卡点名称：</td> <td width='200px'>"+info.name+"</td></tr>";
	    			c += "<tr height='35px'><td width='80px'>负责人：</td> <td width='200px'>"+info.assignment+"</td></tr>";
	    			c += "<tr height='35px'><td width='80px'>联系电话：</td> <td width='200px'>"+info.tel+"</td></tr>";
	    			c += "<tr height='35px'><td width='80px'>通信组号：</td> <td width='200px'>"+info.communityGroupNum+"</td></tr>";
	    			c += "<tr height='35px'><td width='80px'>所属圈层：</td> <td width='200px'>"+(currentEntity.des == null ? "" : currentEntity.des)+"</td></tr>";
	    			c += "<tr ><td width='80px' rowspan='3'>警力部署：</td> <td width='200px'>武警"+(info.armsPoliceCount == null ? 0 : info.armsPoliceCount)+"人</td></tr>";
	    			c += "<tr> <td width='200px'>交警"+(info.trafficPoliceCount == null ? 0 : info.trafficPoliceCount)+"人</td></tr>";
	    			c += "<tr><td width='200px'>民警"+(info.peoplePoliceCount == null ? 0 : info.peoplePoliceCount)+"人</td></tr>";
	    			c += "<tr height='35px'><td width='80px'>装备：</td> <td width='200px'>"+(info.equip == null ? "" : info.equip)+"</td></tr>";
	    			c += "<tr height='35px'><td width='80px'>关联天网：</td> <td width='200px'></td></tr>";
	    		}else if(currentEntity.type == 4){
	    			 s.h = "360px";
		    		c += "<tr  height='35px'><td width='80px'>姓名：</td> <td width='150px'>"+currentEntity.name+"</td></tr>";
		    		c += "<tr height='35px'><td width='80px'>所属机构：</td> <td width='150px'>"+info.orgId+"</td></tr>";
		    		c += "<tr height='35px'><td width='80px'>报备类型：</td> <td width='150px'>"+(info.dutyTypeName ? info.dutyTypeName : "")+"</td></tr>";
		    		c += "<tr height='35px'><td width='80px'>警员短号：</td> <td width='150px'>"+info.mobileShort+"</td></tr>";
		    		c += "<tr height='35px'><td width='80px'>手机号：</td> <td width='150px'>"+info.mobile+"</td></tr>";
		    		c += "<tr height='35px'><td width='80px'>组呼号：</td> <td width='150px'>"+info.intercomGroup+"</td></tr>";
		    		c += "<tr height='35px'><td width='80px'>个呼号：</td> <td width='150px'>"+info.intercomPerson+"</td></tr>";
		    		c += "<tr height='35px'><td width='80px'>乘坐车辆：</td> <td width='150px'></td></tr>";
		    		c += "<tr height='35px'><td width='80px'>携带装备：</td> <td width='150px'></td></tr>";
		    		c += "<tr height='35px'><td width='80px'>操作：</td> <td width='150px'></td></tr>";
	    		}else if(currentEntity.type == 5){
	    			 s.h = "340px";
		    		c += "<tr height='35px'><td width='80px'>名称：</td> <td width='150px'>"+currentEntity.name+"</td></tr>";
		    		c += "<tr height='35px'><td width='80px'>车牌号：</td> <td width='150px'>"+info.number+"</td></tr>";
		    		c += "<tr height='35px'><td width='80px'>用途：</td> <td width='150px'>"+info.purpose+"</td></tr>";
		    		c += "<tr height='35px'><td width='80px'>品牌：</td> <td width='150px'>"+info.brand+"</td></tr>";
		    		c += "<tr height='35px'><td width='80px'>所属机构：</td> <td width='150px'>"+info.orgId+"</td></tr>";
		    		c += "<tr height='35px'><td width='80px'>组呼号：</td> <td width='150px'>"+info.intercomGroup+"</td></tr>";
		    		c += "<tr height='35px'><td width='80px'>车上警员：</td> <td width='150px'></td></tr>";
		    		c += "<tr height='35px'><td width='80px'>操作：</td> <td width='150px'>"+info.name+"</td></tr>";
	    		}else if(currentEntity.type == 10){
		    		c += "<tr><td width='50px'>名称：</td> <td width='150px'>"+currentEntity.NAME+"</td></tr>";
	    		}else{
	    			  return;
	    		}
	        	c += "</table></div></div>";
	        	var obj = {
	        			lon: currentEntity.longitude,
	        			lat: currentEntity.latitude
	        	};
	        	  var pp = MapManager.openPopup ("",obj, c, s,"#2A3D47", 0.7, false);
	        	  pp.type = "search";
	        	  map.addPopup(pp);
	        },
		    "scope": mk
		});
		entity.source = mk;
		return mk;
	},
	/**
	 * 生成图片
	 * @param w 图片宽
	 * @param h 图片高
	 * @param type 类型
	 * @returns {SuperMap.Icon}
	 */
	getIcon: function(type, w,h){
		if(!w) w = 25; if(!h) h = 25;
	    var ipath = basePath+"supermap/theme/images/marker.png";
		if(type == 1){
			ipath = basePath+"supermap/theme/images/MapIcos/GisBallCameraNormal.png";
		}else if(type == 2){
			ipath =  basePath+"supermap/theme/images/MapIcos/GisGunCameraNormal.png";
		}else if(type == 3){
			ipath =  basePath+"supermap/theme/images/MapIcos/CardDotNomal.png";
		}else if(type == 4){
			w = 30; h = 32;
			ipath =  basePath+"supermap/theme/images/MapIcos/jiaojingPoliceIco.png";
		}else if(type == 5){
			w = 32;  h = 30;
			ipath =  basePath+"supermap/theme/images/MapIcos/carIco.png";
		}
		var size = new SuperMap.Size(w, h);
	    var offset = new SuperMap.Pixel(-(size.w/2), -size.h);
	    var icon = new SuperMap.Icon(ipath, size, offset);
	    return icon;
	},
	
	/**
	 * 解析MQ推送的GPS数据
	 * @param datas
	 */
	bulidPoliceAndCar: function(datas){
		//if(!ResourceDatas.gpsControl) return;
		if(!datas || datas == null) return;
		var gpsId = null, lon,lat;
		var mqDatas = datas.split("|");
		if(mqDatas && mqDatas.length > 6){
			gpsId = mqDatas[1];
			lon = mqDatas[5];
			lat = mqDatas[6];
		}
		d:for(var i in ResourceDatas.gpsResDatas){
			var resDatas = ResourceDatas.gpsResDatas[i];
			for(var r in resDatas[1]){
				var entity = resDatas[1][r];
				if(entity.detailInfo.gpsId == gpsId){
					 var resMarkerLayer = map.getLayersByName("resourceMarkerLayer")[0];
					 if(!resMarkerLayer){
						 resMarkerLayer = MapManager.createMarkerLayer("resourceMarkerLayer");
					   	 MapManager.addLayer(map,resMarkerLayer);
					 }
					 if(lon == entity.longitude && lat == entity.latitude) break d;  // 如果坐标没变，则直接跳出循环
					 resDatas[1][r].longitude = entity.longitude = lon;
					 resDatas[1][r].latitude = entity.latitude = lat;
					 if(entity.type == 4 && !ResourceDatas.policeControl){
						 break d;
					 } else	 if(entity.type == 5 && !ResourceDatas.policeCarControl){
						 break d;
					 }
					 if(entity.source) resMarkerLayer.removeMarker(entity.source);
					 try{
						 MapToobar.doResourceChartOfPoint(entity, resMarkerLayer);
					 }catch(e){}
					 break d;
				}
			}
		}
	},
	/**
	* 更加类型获取 背景图片
	*/
	getBarImg: function(type, imgIsSelected){
		var returnImage = "";
		var barImages = [["tool",""+basePath+"supermap/theme/images/eagleEye/tool.png", ""+basePath+"supermap/theme/images/eagleEye/toolPress.png"],
		                 ["mLayer",""+basePath+"supermap/theme/images/eagleEye/mLayer.png", ""+basePath+"supermap/theme/images/eagleEye/mLayerPress.png"],
		                 ["mType_w",""+basePath+"supermap/theme/images/eagleEye/mType_w.png", ""+basePath+"supermap/theme/images/eagleEye/mType_s.png"],
		                 ["statistical",""+basePath+"supermap/theme/images/eagleEye/statistical.png", ""+basePath+"supermap/theme/images/eagleEye/statisticalPress.png"],
		                 ["eagleEyeIn",""+basePath+"supermap/theme/images/eagleEye/eagleEyeIn.png", ""+basePath+"supermap/theme/images/eagleEye/eagleEye_Out.png"],
		                 ["addPoint",""+basePath+"supermap/theme/images/eagleEye/tools/addPoint.png", ""+basePath+"supermap/theme/images/eagleEye/tools/addPointPress.png"],
		                 ["markPoint",""+basePath+"supermap/theme/images/eagleEye/tools/markPoint.png", ""+basePath+"supermap/theme/images/eagleEye/tools/markPointPress.png"],
		                 ["MeasuringSurface",""+basePath+"supermap/theme/images/eagleEye/tools/MeasuringSurface.png", ""+basePath+"supermap/theme/images/eagleEye/tools/MeasuringSurfacePress.png"],
		                 ["ranging",""+basePath+"supermap/theme/images/eagleEye/tools/ranging.png", ""+basePath+"supermap/theme/images/eagleEye/tools/rangingPress.png"]];
		for(var i = 0; i < barImages.length; i++){
			var barImage = barImages[i];
			if(barImage[0] == type){
				if(imgIsSelected == true){
					returnImage = barImage[2];
					break;
				}else{
					returnImage = barImage[1];
					break;
				}
			}
		}
		return returnImage;
	},
	/**
	 * 清理搜索区要素
	 * @param type 类型，当值为null 或者 0时，就直接清理搜索图层，表示全部清理
	 */
	clearSearchInfo: function(type){
		if(searchObj){
			if(searchObj.attrs){
				if(searchObj.attrs.searchPop) map.removePopup(searchObj.attrs.searchPop);
				if(searchObj.attrs.searchMarkerLayer) searchObj.attrs.searchMarkerLayer.clearMarkers();
				if(searchObj.attrs.searchMarkerLayer && (!type || type == '0')){
					map.removeLayer(searchObj.attrs.searchMarkerLayer);
				}
				if(searchObj.attrs.searchLayer) searchObj.attrs.searchLayer.removeAllFeatures();
				if(searchObj.attrs.searchLayer && (!type || type == '0')){
					map.removeLayer(searchObj.attrs.searchLayer);
				}
				searchObj.attrs = new Object();
				searchObj.attrs.existsDatas = [];
				searchObj.attrs.existsDataDetails = [];
				searchObj.attrs.flag = true; 
			}
			searchObj.deactivate();
			map.removeControl(searchObj);
			$("#searchResultDialog").data("kendoWindow").close();
			map.removeAllPopup();
		}
		// 清理鹰眼的一些数据
		if(eyeObj.attrs && eyeObj.attrs.eyeLayer) eyeObj.attrs.eyeLayer.removeAllFeatures();
		eyeObj.attrs = new Object();
	}
}