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
            	<h4>1.点线面绘制</h4>
		    	<input type="button" value="绘点" onclick="draw_point()" style="width:50px;">
		    	<input type="button" value="绘线" onclick="draw_line()" style="width:50px;">
		    	<input type="button" value="绘面" onclick="draw_polygon()" style="width:50px;"><br>
		    	<input type="button" value="绘矩形" onclick="draw_drawRectangle()" style="width:50px;">
		    	<input type="button" value="绘圆" onclick="draw_RegularPolygon()" style="width:50px;">
		    	<input type="button" value="清除" onclick="clearFeatures()" style="width:50px; position: absolute; left: 178px;">
		    	<h4>2.距离量算&面积量算</h4>
		    	<input type="button" value="距离量算" onclick="distanceMeasure()" style="width:80px;"/>
		    	<input type="button" value="面积量算" onclick="areaMeasure()" style="width:80px;"/>
				<input type="button" value="清除" onclick="clearFeatures()" style="width:50px; position: absolute; left: 178px;"/>
				<h4>3.移动Marker</h4>
		    	<input type="button" value="移动" onclick="moveMarker()" style="width:50px;"/>
		    	<h4>4.鼠标的坐标及经纬度</h4>
		    	<span id="mousePositionDiv"></span>
		    	<h4>5.切换捕捉</h4>
		    	<input type="button" value="切换捕捉" onclick="switch_snap()" style="width:80px;"/>
		    	<h4>6.动画控制</h4>
		    	<input type="button" value="开始监控" onclick="startAnimator()"  style="width:75px;"/>
	            <input type="button" value="暂停监控" onclick="pauseAnimator()"  style="width:75px;"/>
	            <input type="button" value="停止监控" onclick="stopAnimator()"  style="width:75px;"/>
		    </div>
		</div>
        </div><!----机构树结束---->
        
        <div class="line8 box"></div>
        <div class="line2"></div>
</div>        
		<script>
			
		</script>

