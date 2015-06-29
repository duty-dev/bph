<%@ page language="java" pageEncoding="UTF-8"%>
 	<!--引用需要的脚本-->
    <style type="text/css">
        #map{
            position: relative;
            height: 553px;
            border:1px solid #3473b7;
        }
        
        #mapToolBar{
            position: relative;
            height: 33px;
            padding-top:5;
        }
        
        #meunUl{
            list-style-type:none;
            width:112px;
           } 
        #myMenu{
            position: absolute;
            visibility: hidden;
            z-index: 9999;
        }
        #myMenu ul{
            float:left;
            border:1px solid #979797;
            background:#f1f1f1  36px 0 repeat-y;
            padding:2px;
            box-shadow:2px 2px 2px rgba(0,0,0,.6);}
        #myMenu ul li{
            width:112px;
            float:left;
            clear:both;
            height:30px;
            cursor:pointer;
            line-height:30px;
           }
        #myMenu ul li:hover{
            background-color: #CAE1FF;
        }
        
        #mkMenu{
            position: absolute;
            visibility: hidden;
            z-index: 9999;
        }
        #mkMenu ul{
            float:left;
            border:1px solid #979797;
            background:#2B3E48  36px 0 repeat-y;
            padding:2px;
            box-shadow:2px 2px 2px rgba(0,0,0,.6);}
        #mkMenu ul li{
            width:65px;
            float:left;
            clear:both;
            height:30px;
            cursor:pointer;
            line-height:30px;
           }
        #mkMenu ul li:hover{
            background-color: #CAE1FF;
        }
        
#mousePositionDivTitle{
                position: absolute;
                z-index: 99;
                left:650px;
                top:105px;
                font-family: Arial;
                font-size: smaller;
            }
            
            .win_bg{background:#CCC; opacity:0.2; filter:alpha(opacity=20); position:absolute; left:0px; top:0px; width:100%; height:100%; z-index:998;}
            .winTitle{background:#4192c9; height:20px; line-height:20px}
            .winTitle .title_left{font-weight:bold; color:#FFF; padding-left:5px; float:left}
            .winTitle .title_right{float:right; padding-right:3px;}
            .winTitle .title_right a{color:#FFF; text-decoration:none; padding-right:3px;}
            .winTitle .title_right a:hover{text-decoration:underline; color:#FF0000; padding-right:3px;}
            .transferPreference {
                border: 1px solid #D6E3F1;
                height: 20px;
                margin: 1px 30%;
                padding: 0 12px;
            }
            .winContent{padding:5px; overflow-y:auto; height:550px;}
            .popupWindow {
                right:20px;
                top:100px;
                position: absolute;
                width: 400px;
                height: 600px;
                border: 2px solid #D6E3F1;
                background: #FFF;
                z-index: 9999;
            }
    </style>
    <script  type="text/javascript" charset="utf-8"  src="<%=basePath %>supermap/libs/SuperMap.Include.js"></script>
	<script  type="text/javascript" charset="utf-8"  src="<%=basePath %>supermap/libs/map.js"></script>
	<script type="text/javascript" charset="utf-8" src="<%=basePath %>supermap/libs/mapExample.js"></script>
	<script  type="text/javascript" charset="utf-8"  src="<%=basePath %>supermap/libs/changchundata.js"></script>
 <%-- <script  type="text/javascript" charset="utf-8"  src="<%=basePath %>supermap/libs/mapbright.js"></script> --%>
    <%-- <script  type="text/javascript" charset="utf-8"  src="<%=basePath %>supermap/libs/mapmodify.js"></script> --%>
     <%-- <script  type="text/javascript" charset="utf-8"  src="<%=basePath %>supermap/libs/mapofrightclick.js"></script> --%>
     <script src="<%=basePath%>JS/rabbit/sockjs-0.3.4.js" type='text/javascript'></script>
     <script src="<%=basePath%>JS/rabbit/stomp.js" type='text/javascript'></script>

    <script type="text/javascript">
    var basePath = '<%= basePath%>';
    $(document).ready(function(){
    	initMap();
	});
    
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
 // --------------------MQ--start---------------------------
 // 管理端访问：http://localhost:15672 u:guest p:guest
var ws = new SockJS('http://25.30.9.18:15674/stomp');
var client = Stomp.over(ws);
// SockJS does not support heart-beat: disable heart-beats
client.heartbeat.incoming = 0;
client.heartbeat.outgoing = 0;

client.debug = function(e) {
	$('#second div').append($("<code>").text(e));
};

// default receive callback to get message from temporary queues
client.onreceive = function(m) {
	alert("onreceive: "+m.body);
}

var on_connect = function(x) {
	id = client.subscribe("/exchange/BaseDataExchange/routeData.H.51000.51001", function(m) {
		alert(m.body);
		// url: /exchange/gps/51000.51001
		// 数据格式：{"x":104.06662 ,"y": 30.78559}
		goMove(m.body);
	});
};
var on_error = function() {
	console.log('error');
};
client.connect('admin', 'admin', on_connect, on_error, '/');
// --------------------MQ--end---------------------------	
    </script>
    <div id='result' class='container'></div>
    <span id="mousePositionDivTitle"></span>
    <!--地图显示的div-->
    <div id="map"  style="position:absolute;left:260px;right:0px;width: 82%;height:85%;"></div>
	 <div id ="myMenu">
        <ul id="meunUl" style="margin-top: 0px; margin-bottom: 0px;" >
            <li>
                     <div style="float:left;padding-right: 5px;width:30px;height:30px"></div>
                    <div style="float:left;width:52px;height:30px;  text-align: center; font-size: 15px;">放大</div>
            </li>
            <li>
                <div style="float:left;padding-right: 5px;width:30px;height:30px"></div>
                <div style="float:left;width:52px;height:30px;  text-align: center; font-size: 15px;">缩小</div>
        </ul>
    </div>
	<div id="drawDialog"></div>
	
	<div id ="mkMenu">
        <ul style="margin-top: 0px; margin-bottom: 0px;list-style-type:none;width:65px;" >
            <li>
                    <div style="float:left;width:52px;height:30px;  text-align: center; font-size: 15px;" onclick="gotoMove('move')">移动</div>
            </li>
            <li>
                    <div style="float:left;width:52px;height:30px;  text-align: center; font-size: 15px;" onclick="gotoMove('save')">保存</div>
            </li>
            <li>
                <div style="float:left;width:52px;height:30px;  text-align: center; font-size: 15px;" onclick="cancelMenu('init')">取消</div>
        </ul>
    </div>
