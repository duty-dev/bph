<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<head>
<title>扁平化指挥调度系统</title>
<%@ include file="../../../emulateIE.jsp" %>
<script  type="text/javascript" charset="utf-8"  src="<%=basePath %>supermap/libs/jquery.js"></script>
<script  type="text/javascript" charset="utf-8"  src="<%=basePath %>supermap/libs/jquery.colorpicker.js"></script>
</head>

<body>
	<div id="vertical">
	<div id="horizontal" style="height:100%; width:100%;">
				<div id="left-pane">
			<div class="pane-content">
				<!-- 左开始 -->
				<div class="demo-section k-header">
					<form id="employeeForm" data-role="validator"
						novalidate="novalidate">
						<input value="${user.userId}" id="userId" name="userId" type="hidden"/>
						<input value="${circleLayer.id}" id="circleLayerId" name="circleLayerd" type="hidden"/>
						<ul>
							<li>&nbsp;</li>
							<li><span>名&nbsp;&nbsp;&nbsp;称:</span> <span id="circleLayerName">${circleLayer.name }</span></li>
							<%-- <li><label>&nbsp; 姓&nbsp;&nbsp;&nbsp;&nbsp;名:</label> ${user.userName}</li> --%>
							<li><span class="fl" style="line-height:30px;">边框色:</span> 
							<input type="hidden"	class="k-textbox fl"  onchange="changePloy()"  value="${displayProperty.borderColor == null ? '#66CCFF' :  displayProperty.borderColor}" name="borderColor" id="borderColor"/>
							<div id="myborderColordiv" style="float:left;width: 20px; background-color:  ${displayProperty.borderColor == null ? '#66CCFF' :  displayProperty.borderColor};margin-top:5px;margin-right:5px;margin-left:8px;" >&nbsp;&nbsp;&nbsp;</div>	 
								 <img src='<%=basePath %>supermap/theme/images/others/colorpicker.png' id="cp1" style="cursor:pointer"/>
								 <span style="margin-left: 10px;">透明度:</span>
								 <select class="w176"  style="width: 70px;"id="borderOpacity"  onchange="changePloy()" >
								 	<option value="0"  ${displayProperty.borderOpacity ==0 ? 'selected' : '' }>0%</option>
								 	<option value="0.1"  ${displayProperty.borderOpacity ==0.1 ? 'selected' : '' }>10%</option>
								 	<option value="0.2"  ${displayProperty.borderOpacity ==0.2 ? 'selected' : '' }>20%</option>
								 	<option value="0.3" ${displayProperty.borderOpacity ==0.3 ? 'selected' : '' }>30%</option>
								 	<option value="0.4"  ${displayProperty.borderOpacity ==0.4 ? 'selected' : '' }>40%</option>
								 	<option value="0.5"  ${displayProperty.borderOpacity ==0.5 ? 'selected' : '' }>50%</option>
								 	<option value="0.6" ${displayProperty.borderOpacity == 0.0 || displayProperty.borderOpacity ==0.6 ? 'selected' : '' }>60%</option>
								 	<option value="0.7"  ${displayProperty.borderOpacity ==0.7 ? 'selected' : '' }>70%</option>
								 	<option value="0.8"  ${displayProperty.borderOpacity ==0.8 ? 'selected' : '' }>80%</option>
								 	<option value="0.9" ${displayProperty.borderOpacity ==0.9 ? 'selected' : '' }>90%</option>
								 	<option value="1" ${displayProperty.borderOpacity ==1 ? 'selected' : '' }>100%</option>
								 </select>
						    </li>
							<li><span class="fl" style="line-height:30px;">背景色:</span> 
							<input type="hidden" class="k-textbox"  onchange="changePloy()" value="${displayProperty.fillColor == null ? '#99CC99' :  displayProperty.fillColor}" name="fColor" id="fColor" />
							<div id="fColordiv" style="float:left;width: 20px; background-color:  ${displayProperty.fillColor == null ? '#99CC99' :  displayProperty.fillColor};margin-top:5px;margin-right:5px;margin-left:8px;"">&nbsp;&nbsp;&nbsp;</div>	
								<img src='<%=basePath %>supermap/theme/images/others/colorpicker.png' id="cp2" style="cursor:pointer"/>
								<span style="margin-left: 10px;">透明度:</span> 
								<select class="w176" style="width: 70px;" id="fOpacity" placeholder="" onchange="changePloy()" >
								 	<option value="0"  ${displayProperty.fillOpacity ==0 ? 'selected' : '' }>0%</option>
								 	<option value="0.1"  ${displayProperty.fillOpacity ==0.1 ? 'selected' : '' }>10%</option>
								 	<option value="0.2"  ${displayProperty.fillOpacity ==0.2 ? 'selected' : '' }>20%</option>
								 	<option value="0.3" ${displayProperty.fillOpacity ==0.3 ? 'selected' : '' }>30%</option>
								 	<option value="0.4"  ${displayProperty.fillOpacity ==0.4 ? 'selected' : '' }>40%</option>
								 	<option value="0.5"  ${displayProperty.fillOpacity ==0.5 ? 'selected' : '' }>50%</option>
								 	<option value="0.6" ${displayProperty.fillOpacity == 0.0 || displayProperty.fillOpacity ==0.6 ? 'selected' : '' }>60%</option>
								 	<option value="0.7"  ${displayProperty.fillOpacity ==0.7 ? 'selected' : '' }>70%</option>
								 	<option value="0.8"  ${displayProperty.fillOpacity ==0.8 ? 'selected' : '' }>80%</option>
								 	<option value="0.9" ${displayProperty.fillOpacity ==0.9 ? 'selected' : '' }>90%</option>
								 	<option value="1" ${displayProperty.fillOpacity ==1 ? 'selected' : '' }>100%</option>
								 </select>
							</li>
							<li><span>中心点:</span> 
								<input type="text" class="k-textbox" style="margin-left: 5px;width: 90px;" name="latitude" id="latitude"  value = "${displayProperty.y }"/>
								<em class="ty-input-end" style="margin-top: 1px;"></em>
								<button type="button"  class="ty-button"  style="margin-left: 25px;height:28px;line-height:20px;"  onclick="getPoint()">取点</button>	 
							</li>
							<li>
								<input type="text" class="k-textbox"  style="margin-left: 52px;width: 90px;" name="longitude" id="longitude" value = "${displayProperty.x }"/>
								<em class="ty-input-end"></em>
							</li>
							<li class="actions" >
								<button type="button"  class="ty-button" onclick="save()">保存</button>
								<button type="button" data-role="button" class="ty-button" style="margin-left: 20px;" onclick="uclose()">取消</button>
							</li>
						</ul>
					</form>
				</div>
				<!-- 左结束-->
		</div>
		</div>
		</div>
	</div>

	<style scoped>
#vertical {
	height: 260px;
	width: 400px;
	margin:0 auto;
}

.pane-content {
	padding: 0 10px;
}

#employeeForm ul {
	list-style-type: none;
	margin: 0;
	padding: 0;
}

#employeeForm li {
	margin-top: 10px;
	margin-left: 80px;
	/* text-align:center; */
}

label {
	display: inline-block;
	padding-right:3px;
	margin-left:60px;
	width:80px;
}

span.k-tooltip {
	margin-left: 6px;
}

.demo-section {
	width: 400px;
	height:270px;
}

.actions {
	padding-left: 100px;
	padding-top: 10px;
}
</style>

<script>
var color=[];
  $(document).ready(function() {
  	/**
  	*	初始化 边框颜色控件
  	*/
  	$("#cp1").colorpicker({
          ishex:true,
          fillcolor:true,
          event:'mouseover',
          target:$("#borderColor") ,
          success: borderColorSuccess
      });
  	/**
  	*	初始化 背景颜色控件
  	*/
  	$("#cp2").colorpicker({
  		ishex:true,
          fillcolor:true,
          event:'mouseover',
          target:$("#fColor") ,
          success: fColorSuccess
      });
      $("#_creset").css("font-size","12px");
      $("#_creset").css("padding-right","20px");
      $("#colorpanel").css("left","533px");
      $("#employeeForm button").css("width","50px");

  });
/**
* 边框色控件 设置成功的回调函数
*/
function borderColorSuccess(obj,color){
   	$("#myborderColordiv").css("background-color", color);
   	changePloy();
   }
 /**
* 背景色控件 设置成功的回调函数
*/
function fColorSuccess(obj,color){
   	$("#fColordiv").css("background-color", color);
   	changePloy();
   }
   
function save() {
	var obj = window.parent.currentObj;
	var displayProperty = obj.displayProperty;
	if(!displayProperty) displayProperty = new Object();
	if(!window.parent.polygonFinal){
		window.parent.$("body").popjs({"title":"提示","content":"请先结束绘制操作！"});
		return;
	}
	var points = window.parent.polygonFinal.components[0].components;
	var mapProperty = [];
	for(var i = 0; i < points.length; i++){
		var point = new Object();
		point.x = points[i].x;
		point.y = points[i].y;
		mapProperty.push(point);
	}
 	/* if($("#borderColor").val()==""){
     	$("body").popjs({"title":"提示","content":"旧密码不能为空！"});
    	return false;
     } */
     if($("#borderColor").val() != "") displayProperty.borderColor = $("#borderColor").val();
     if($("#borderOpacity").val() != "") displayProperty.borderOpacity = $("#borderOpacity").val();
     if($("#fColor").val() != "") displayProperty.fillColor = $("#fColor").val();
     if($("#fOpacity").val() != "") displayProperty.fillOpacity = $("#fOpacity").val();
     if($("#longitude").val() != "") displayProperty.x = $("#longitude").val();
     if($("#latitude").val() != "") displayProperty.y = $("#latitude").val();
     obj.displayProperty = displayProperty;
 	 obj.mapProperty = mapProperty;
     updateCircle(obj);
 }
       
   /**
   *	更新圈层数据
   **/
function updateCircle(obj){
   	$.ajax({
 		url:"<%=basePath%>map/modifyCircleLayer.do",
		type:"post",
		dataType:"json",
		data:{
			circleLayerVo: JSON.stringify(obj),	
			random: Math.random()
		},
		 success:function(msg){
			 window.parent.$("#"+obj.id).attr("src","<%=basePath%>supermap/theme/images/others/CaseMarkPressed.png");
			 window.parent.$("body").popjs({"title":"提示","content":"圈层保存"+msg.description});
			 //window.parent.loadCircleLayerTreeList();
			 window.parent.saveCreatePolygon();
			 uclose();
		}
	});
}
   	/**
   	* 关闭弹出窗口
   	**/
 function uclose(){
	 window.parent.deactiveAll();
	 window.parent.createCricleLayers(null, null);
	window.parent.$("#drawDialog").tyWindow.close();
}
        	
   	/**
   	* 取点
   	*/
 function getPoint(){
		 window.parent.getPoint();
   	}
    
 	/**
 	* 调用父类的changePloy方法，改变圈层对应的样式
 	*/
function changePloy(){
	window.parent.changePloy();
}
 </script>
</body>
</html>
