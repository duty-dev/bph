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
        
        #myMenu{
position: absolute;
background-color: silver;
visibility: hidden;
}

#myMenu ul{
list-style-type:none;
width:112px;
float:left;
border:1px solid #979797;
background:#f1f1f1  36px 0 repeat-y;
padding:2px;
box-shadow:2px 2px 2px rgba(0,0,0,.6);}
#myMenu ul li{
width:112px;
float:left;
clear:both;
height:35px;
cursor:pointer;
line-height:32px;
}
#myMenu ul li:hover{
background-color: #CAE1FF;
}
    </style>
    <script  type="text/javascript" charset="utf-8"  src="<%=basePath %>supermap/libs/SuperMap.Include.js"></script>
     <script  type="text/javascript" charset="utf-8"  src="<%=basePath %>supermap/libs/map.js"></script>
    <%-- <script  type="text/javascript" charset="utf-8"  src="<%=basePath %>supermap/libs/mapmodify.js"></script> --%>
     <%-- <script  type="text/javascript" charset="utf-8"  src="<%=basePath %>supermap/libs/mapofrightclick.js"></script> --%>
    <script type="text/javascript">
    var basePath = '<%= basePath%>';
    $(document).ready(function(){
    	initMap();
	});
	/**
     * 跳转界面 
     */
    function gotoDrawKpoint(){
    	var organId = $.trim($("#organId").val());
    	var sessionId = $.trim($("#token").val());
    	
    	$("#drawDialog").tyWindow({
    		width: "660px",
    		height: "590px",
    	    title: "图形属性设置",
    	    position: {
    	        top: "100px"
    	      },
    		content: "<%=path %>/map/gotoDrawKpoint.do?userId="+${User.userId}+"&random="+Math.random(),
    		iframe : true,
    		init:function(){
    			//alert($("#tyIframeContent").prop("id"));
    			//alert($("#tyIframeContent #centerPointX").val());
    		}
    		}); 
    		
    	}
    </script>
    
    <!--地图显示的div-->
    <div id="map" style="position:absolute;left:260px;right:0px;width: 82%;height:85%;">
	 <div id ="myMenu">
		<ul style="margin-top: 0px; margin-bottom: 0px;" >
			<li>
				<div style="float:left;width:52px;height:30px; text-align: center; font-size: 15px;">居中</div> </li>
			<li>
				<div style="float:left;width:52px;height:30px; text-align: center; font-size: 15px;">删除</div> </li>
			</li>
		</ul>
	</div>
		<div id="drawDialog"></div>
    </div>


