<%@ page language="java" pageEncoding="UTF-8"%>
<%-- <%
Cookie cname=new Cookie("c_name","55555");//设置cookie的键键c_name
cname.setMaxAge(0);//设置cookie的有效期.
//response.setCharacterEncoding("utf-8");
response.addCookie(cname);//设置cookie,将cookie存放到respones里面
%>
<%
//读取cookie
Cookie cookie[]=request.getCookies();
if(cookie!=null){
	for(int i=0;i<cookie.length;i++){
		  Cookie c=cookie[i];	
		  //out.println(c.getName());
		  String name=c.getName();
		  out.println(name);
	}
} 
%> --%>
<div id="navigationLeft">
        <div class="line1-mid box">
          <div class="t1-start"></div>
          <div class="end"></div>
        </div>
        <div class="pull-left box">
          <div>
				<h3>功能汇集</h3>
          </div>
        </div>
        <div class="pull-right box">
          <div class="line7"></div>
        </div>
        
        <div class="tree-box clear"><!----机构树---->
        <div id="box">
            <div id="mapToolBar">
            	<h4>1.单点对象标注(卡点、卡口)绘制</h4>
            	<input type="button" value="卡点" onclick="draw_point(1)" style="width:50px;">
            	<input type="button" value="卡口" onclick="draw_point(2)" style="width:50px;">
            	<input type="button" value="移动点位" onclick="draw_point(2.1)" style="width:70px;">
<!-- 		    	<input type="button" value="绘点" onclick="draw_point()" style="width:50px;">
		    	<input type="button" value="绘线" onclick="draw_line()" style="width:50px;">
		    	<input type="button" value="绘面" onclick="draw_polygon()" style="width:50px;"><br>
		    	<input type="button" value="绘矩形" onclick="draw_drawRectangle()" style="width:50px;">
		    	<input type="button" value="绘圆" onclick="draw_RegularPolygon()" style="width:50px;"> -->
		    	
		    	<br>
		    	<input type="button" value="卡点上图" onclick="cardrequest(1)" style="width:80px;">
            	<input type="button" value="卡口上图" onclick="cardrequest(2)" style="width:80px;"><br>
            	<input type="button" value="部分卡点上图" onclick="randomShow(1)" style="width:100px;">
            	<input type="button" value="清除" onclick="clearFeatures()" style="width:50px; position: absolute; left: 178px;">
		    	<h4>2.距离量算&面积量算</h4>
		    	<input type="button" value="距离量算" onclick="distanceMeasure()" style="width:80px;"/>
		    	<input type="button" value="面积量算" onclick="areaMeasure()" style="width:80px;"/>
				<input type="button" value="清除" onclick="clearFeatures()" style="width:50px; position: absolute; left: 178px;"/>
				<h4>3.移动Marker</h4>
		    	<input type="button" value="移动" onclick="moveMarker()" style="width:50px;"/>
		    	<h4>4.切换捕捉</h4>
		    	<input type="button" value="切换捕捉" onclick="switch_snap()" style="width:80px;"/>
		    	<h4>5.动画控制</h4>
		    	<input type="button" value="开始监控" onclick="startAnimator()"  style="width:75px;"/>
	            <input type="button" value="暂停监控" onclick="pauseAnimator()"  style="width:75px;"/>
	            <input type="button" value="停止监控" onclick="stopAnimator()"  style="width:75px;"/>
	            <h4>6.浮动对象</h4>
	            <input type="button" value="浮动对象" onclick="draw_point(3)" style="width:80px;"/>
	            <h4>7.基础图层控制</h4>
	            <input type="button" value="基础图层控制" onclick="controlBaseLayer()" style="width: 80px;"/>
	            <h4>8.业务图层控制</h4>
	            <input type="button" value="业务图层控制" onclick="getMyLayers()" style="width: 130px;"/> 
	            <input type="button" value="卡口卡点图层控制" onclick="displayLayer()" style="width: 130px;"/>
	            <input type="button" value="删除、新增业务图层" onclick="addDelLayer()" style="width: 130px;"/> 
	            <h4>9.点周边搜索</h4>
	            <input type="button" value="点周边" onclick="drawPointBoundsFun(1)" style="width: 80px;"/> 
	            <input type="button" value="点周边搜索" onclick="drawPointBoundsFun(1.1)" style="width: 120px;"/> 
	            <h4>10.线周边搜索</h4>
	            <input type="button" value="线周边" onclick="drawLineBoundsFun(2)" style="width: 80px;"/> 
	            <input type="button" value="线周边搜索" onclick="drawLineBoundsFun(2.1)" style="width: 120px;"/> 
	            <h4>11.多边形搜索</h4>
	            <input type="button" value="多边形搜索" onclick="draw_polygon()" style="width:120px;">
	            <input type="button" value="清除" onclick="clearFeatures()" style="width:50px; position: absolute; left: 178px;">
	            <h4>12.聚散点</h4>
	            <input type="button" value="加载数据" onclick="getClusterDatas()" style="width:80px;"><br>
	            <h4>13.热点图</h4>
	            <span>热点数量：</span>
	            <input type='text' style='width:50px' id='heatNums' value='100'/><br>
	            <span>热点半径：</span>
	            <input type='text'  style='width:30px' value='50' id='heatradius'/>
	            <select style='width:70px' id='radiusUnit'><option value='px'>px</option><option value ='degree'>degree</option></select>
	            <input type="button" value="渲染热点" onclick="createHeatPoints()"  style="width: 80px;"/>
	            <input type="button" value="清除" onclick="clearHeatPoints()"  style="width: 50px;"/>
		    </div>
		</div>
        </div><!----机构树结束---->
        
        <div class="line8 box"></div>
        <div class="line2"></div>
</div>        
		<script>
			
		</script>

