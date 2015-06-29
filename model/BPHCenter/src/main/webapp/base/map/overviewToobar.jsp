<%@page import="com.tianyi.bph.common.SystemConfig"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String mapUrl = SystemConfig.getInstance().getProperty("mapUrl");
	String mapAnalyUrl = SystemConfig.getInstance().getProperty("mapAnalyUrl");
%>
<!--引用需要的脚本-->
<style type="text/css">
#eyeToobarDiv{
    position: absolute;
    display:none;
    z-index: 99;
    right:78px;
    top:190px;
    font-family: Arial;
    font-size: smaller;
}
.MySmMap {
	position:relative;
    z-index: 0;
    padding: 0px!important;
    margin: 0px!important;
    /* cursor: default; */
    cursor: url("../images/cursors/Pan.cur"),default;
    border:1px solid #3473b7;
    border-radius:90px;border:solid blue 1px;;
}
.smControlOverviewMapContainer {
    text-align: right;
    width: 215px;
    height: 190px;
    top: 15px;
    right: 0px;
}
.smControlOverviewMapElement {
	float:left;
	width: 180px;
	border-left:1px ;
	border-top:1px;
	padding-top: 4px;
	padding-left: 4px;
}
img{
	cursor: pointer;
}
	/** 
	*	background-color: #e5f3ff; 
	*	border-radius:125px;border:solid rgb(100,100,100) 1px;;
	*/
</style>
<script  type="text/javascript" charset="utf-8"  src="<%=basePath %>supermap/libs/toobar.js"></script>
<script src="<%=basePath%>JS/rabbit/sockjs-0.3.4.js"  type='text/javascript'></script>
<script src="<%=basePath%>JS/rabbit/stomp.js" type='text/javascript'></script>
<script type="text/javascript">
//---------------初始化地图的参数------start----------

MapManager.setUrl("<%=mapUrl%>");
MapManager.setAnalyUrl("<%=mapAnalyUrl%>");

//---------------初始化地图的参数------end----------

var basePath = '<%= basePath%>';
   $(document).ready(function(){
	   
	   var ws = new SockJS('http://25.30.9.186:15674/stomp');
		var client = Stomp.over(ws);
		// SockJS does not support heart-beat: disable heart-beats
		client.heartbeat.incoming = 0;
		client.heartbeat.outgoing = 0;

		client.debug = function(e) {
			$('#second div').append($("<code>").text(e));
		};

		// default receive callback to get message from temporary queues
		client.onreceive = function(m) {
		}
		var on_connect = function(x) {
			id = client.subscribe("/exchange/GpsTopicExchange/routeData.GPS.510000000000.#", function(m) {
				// 异步实现获取GPS数据
				setTimeout(function(){
					MapToobar.bulidPoliceAndCar(m.body);
				},200);
			});
		};
		var on_error = function() {
			console.log('error');
		};
		client.connect('guest', 'guest', on_connect, on_error, '/');
		
		
   		var tbar = "<div style='float:left;width:auto;height:auto;position:relative;maring-top: 60px;'>";
   		var eagleEyeIn = "<div style='positon:absolute; width: 28px; height:30px;'><img onclick='eyeHide(this)' src='"+basePath+"supermap/theme/images/eagleEye/eagleEyeIn.png'></div>";
   		var mType_w = "<div style='positon:absolute; width: 28px; height:30px;'><img src='"+basePath+"supermap/theme/images/eagleEye/mType_w.png'></div>";
   		var statistical = "<div style='positon:absolute; width: 28px; height:30px;'><img src='"+basePath+"supermap/theme/images/eagleEye/statistical.png'></div>";
   		var mLayer = "<div style='positon:absolute; width: 28px; height:30px;'><img onclick='mapLayerConrol(this)'  src='"+basePath+"supermap/theme/images/eagleEye/mLayer.png'></div>";
   		var tool = "<div style='positon:absolute; width: 28px; height:30px;'><img onclick='eyeToobarControl(this)' src='"+basePath+"supermap/theme/images/eagleEye/tool.png'></div>";
   		tbar = tbar + eagleEyeIn + mType_w + statistical + mLayer + tool;
   		tbar += "</div>"; 
   		$(".smControlOverviewMap").append(tbar);
   		
});
/**
 * 鹰眼控制的显示与隐藏
 */
 function eyeHide(obj){
	$(".smControlOverviewMapElement").toggle();
	var im = MapToobar.getBarImg("eagleEyeIn", $(".smControlOverviewMapElement").css("display") != "none" ? false : true);
	if(im && im != "")
		$(obj).attr("src", im);
	var overviewStyle = "position: absolute; z-index: 1255;text-align: right; width: 40px; height: 190px; top: 23px; right: 0px;";
	if($(".smControlOverviewMapElement").css("display") != "none"){
		overviewStyle = "position: absolute; z-index: 1255;text-align: right; width: 220px; height: 190px; top: 15px; right: 0px;";
		$("#eyeToobarDiv").show();
	}else{
		$("#eyeToobarDiv").hide();
	} 
	$(".smControlOverviewMap").attr("style",overviewStyle);
}
/**
 * 控制鹰眼小工具展示与隐藏
 */
function eyeToobarControl(obj){
	$("#eyeToobarDiv").toggle();
	var im = MapToobar.getBarImg("tool", $("#eyeToobarDiv").css("display") != "none" ? true : false);
	if(im && im != "")
		$(obj).attr("src", im);
}

/**
* @obj 当前对象
* @param imgType 图片类型"markPoint","addPoint","ranging","MeasuringSurface"
* @param type 搜索类型，1，标点；2，加点；3，测距；4，测面
*/
function eyeToobars(obj,imgType, type){
   var eyeToobarType = ["markPoint","addPoint","ranging","MeasuringSurface"];
   var eyehandle = null;
   switch(type){
   case 1:
	   eyehandle = SuperMap.Handler.Point;
	   break;
   case 2:
	   eyehandle = SuperMap.Handler.Point;
	   break;
   case 3:
	   eyehandle = SuperMap.Handler.Path;
		   break;
   case 4:
	   eyehandle = SuperMap.Handler.Polygon;
		   break;
   }
   if(!map) map = MapManger.getMap();
   var eyeFea = MapToobar.initEyeFeature(map,null, type, eyehandle,null,true);
   var sourceImg = $(obj).attr("src");
   var imgIsSeleted = true;
   if(sourceImg.indexOf(imgType+"Press") > -1){
	   imgIsSeleted = false;
	   eyeFea.deactivate();
   }else{
	   for(var i = 0; i < eyeToobarType.length; i++){
		   if(eyeToobarType[i] != imgType){
			   var oim = MapToobar.getBarImg(eyeToobarType[i],  false);
				if(oim && oim != "")
					$("#"+eyeToobarType[i]).attr("src", oim);
		   }
	   }
   }
   var im = MapToobar.getBarImg(imgType,  imgIsSeleted);
	if(im && im != "")
		$(obj).attr("src", im);
  }
  /**
  * 地图图层控制
  */
 function mapLayerConrol(obj){
	  var imgIsSeleted =  true;;
	  if($("#tyTcc") && $("#tyTcc").html() != null){
		  imgIsSeleted =  false;
		  $("#mapLayerConrolDialog").tyWindow.close();
	  }
	  var im = MapToobar.getBarImg("mLayer",  imgIsSeleted);
		if(im && im != "")
			$(obj).attr("src", im);
	  if(!imgIsSeleted){
		  return;
	  }
	  var content = "<div style='width: 550px;height:320px'><div style='postition: absolute;width: 400px;'>";
	  for(var i = 0 ; i< 10; i++){
	   	content += "<img style='margin-left: 8px;margin-bottom: 8px;' width='32px' height='32px' src='<%= basePath%>supermap/theme/images/res/defaultBmp.png'>";
	  }
	  content += "<br>";
	  for(var i = 0 ; i< 10; i++){
	   	content += "<img style='margin-left: 8px;margin-bottom: 8px;' width='32px' height='32px'  src='<%= basePath%>supermap/theme/images/res/school.png'>";
	  }
	content+= "</div>";
	content+= "<div  style='width: 100%;text-align: right;margin-top: 15px;'><button type='button' onclick='' data-click='save' style='width:50px;;height:32px;line-height:24px;background-color:#B7202A;color:#FFF;font-size:14px;border:0;border-radius:2px;'>确定</button>" ;
	content+= "</div></div>";
	$("#mapLayerConrolDialog").tyWindow({
		width: "600px",
		height: "450px",
		title: "地图图层控制",
		position: {
			top: "300px",
			left: "480px"
		},
		content: content,
	});
 }
	
</script>
<div id="mapLayerConrolDialog"></div>
<div id='eyeToobarDiv' >
	<img id="markPoint" src="<%= basePath%>supermap/theme/images/eagleEye/tools/markPoint.png"  onclick="eyeToobars(this,'markPoint',1)">
	<img id="addPoint"  src="<%= basePath%>supermap/theme/images/eagleEye/tools/addPoint.png"  onclick="eyeToobars(this,'addPoint',2)">
	<img id="ranging"  src="<%= basePath%>supermap/theme/images/eagleEye/tools/ranging.png"  onclick="eyeToobars(this,'ranging',3)">
	<img id="MeasuringSurface"  src="<%= basePath%>supermap/theme/images/eagleEye/tools/MeasuringSurface.png"  onclick="eyeToobars(this,'MeasuringSurface',4)">
</div>

<div style="display:none;">
	<input id="userId" type="text"  value="${User.userId}">
	<input id="orgId" type="text"  value=" ${User.orgId}">
</div>


	



