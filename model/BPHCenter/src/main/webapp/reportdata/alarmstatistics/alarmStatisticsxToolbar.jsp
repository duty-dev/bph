<%@ page language="java" pageEncoding="UTF-8"%>

<script>
$(document).ready(function() {     
	//屏幕模式
	$("#tyScreenMode i").each(function(i){
		$(this).click(function(){
			var cls = $(this).attr("class");
			var lump = cls.substring(cls.length-1,cls.length);
			var icon = $("#tyScreenMode .ty-screen-mode-icon");
			var txt = $("#tyScreenMode .ty-screen-mode-txt");
			if(lump == 1){
				icon.animate({"left":"6px"},300);
				txt.css("background-position","-106px -265px");
				$("#tyScreenMode").mouseover(function(){
					txt.css("background-position","-106px -265px");
				}).mouseout(function(){
					txt.css("background-position","12px -265px");
				});
				window.external.ChangeScreenModel("标准模式");
			}else if(lump == 2){
				icon.animate({"left":"35px"},300);
				txt.css("background-position","-104px -306px");
				$("#tyScreenMode").mouseover(function(){
					txt.css("background-position","-104px -306px");
				}).mouseout(function(){
					txt.css("background-position","4px -306px");
				});
				window.external.ChangeScreenModel("模块全屏");
			}else if(lump == 3){
				icon.animate({"left":"64px"},300);
				txt.css("background-position","-106px -347px");
				$("#tyScreenMode").mouseover(function(){
					txt.css("background-position","-106px -347px");
				}).mouseout(function(){
					txt.css("background-position","2px -347px");
				});
				window.external.ChangeScreenModel("内容全屏");
			}
		});
	});
});
</script>
<div id="template">
 
	<div class="fl ml30 set-hei48">
		<input id="organName" type="hidden" value="${requestScope.organName}" />
		<input id="pageType" type="hidden" value="${requestScope.pageType}" />
		<a id="btnalarmType" href="<%=basePath %>reportRouteWeb/gotoAlarmStatistics.do?pageType=1"  ><span class="fl ty-btn-jqfl cursor"></span></a>
        <a id="btnalarmOrgan" href="<%=basePath %>reportRouteWeb/gotoAlarmStatistics.do?pageType=2"  ><span class="fl ty-btn-jg cursor ml10"></span></a>   
        <a id="btnalarmCircle" onclick="onChangeReportAction(3);" ><span class="fl ty-btn-zq cursor ml10"></span></a> 
        <a id="btnalarmTimeSpan"   onclick="onChangeReportAction(4);" ><span class="fl ty-btn-sjd cursor ml10"></span></a>
          					<div id="div_orgPath" class="ty-tj-title2"></div>
	</div>
	<div class="fr set-hei48">
		<div class="ty-screen-mode" id="tyScreenMode">
			<i class="ty-screen-mode-btn1" title="标准模式"></i><i class="ty-screen-mode-btn2" title="模块全屏"></i><i class="ty-screen-mode-btn3" title="内容全屏"></i>
			<div class="ty-screen-mode-icon"></div>
			<span class="ty-screen-mode-txt"></span>
		</div>
	</div>
        <a id="btnExportExcel" style="margin-right:150px" onclick="onExportExcelAction();"><span class="fr ty-btn-dc cursor ml10  ty-btn-offset"></span></a>   
		
	<div class="ty-nav-line ml10">
    	<div class="ty-nav-line-left"></div><div class="ty-nav-line-right"></div>
    </div>
</div>
 