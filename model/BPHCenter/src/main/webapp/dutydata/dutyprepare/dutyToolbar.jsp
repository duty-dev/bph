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
				icon.animate({"left":"6px"},1000);
				txt.css("background-position","-17px -731px");
				$("#tyScreenMode").mouseover(function(){
					txt.css("background-position","-17px -731px");
				}).mouseout(function(){
					txt.css("background-position","-147px -757px");
				});
				
			}else if(lump == 2){
				icon.animate({"left":"43px"},1000);
				txt.css("background-position","-17px -767px");
				$("#tyScreenMode").mouseover(function(){
					txt.css("background-position","-17px -767px");
				}).mouseout(function(){
					txt.css("background-position","-147px -793px");
				});
				
			}else if(lump == 3){
				icon.animate({"left":"78px"},1000);
				txt.css("background-position","-18px -803px");
				$("#tyScreenMode").mouseover(function(){
					txt.css("background-position","-18px -803px");
				}).mouseout(function(){
					txt.css("background-position","-147px -828px");
				});
				
			}
		}); 
	});
});
</script>
<div id="template">
 
<div class="fl ml30 set-hei48">
					<input type="hidden" id="organCode" name="organCode" value="${organ.code}">
					<input type="hidden" id="dutyDate" name="dutyDate" value="${dutyDate}">
                   <span id="btnaddParentNode" class="k-button"  onclick="DutyPrepareManage.showDutyTypeWindow()">勤务类型选择</span>
                   <span id="btnaddChildNode" class="k-button"  onclick="DutyBaseManage.selectDutyTemplete()">报备模板选择</span> 
                   <span id="btndeleteNode" class="k-button"  onclick="DutyPrepareManage.onShowCalendarWindow()">报备复制</span> 
                   <span id="btnunLockNode" class="k-button"  onclick="DutyPrepareManage.clearDuty()">清空报备</span> 
                   <span id="btnlockNode" class="k-button"  onclick="DutyPrepareManage.onShowTemplateWindow()">保存模板</span> 
                   <span id="btnlockNode" class="k-button"  onclick="DutyPrepareManage.onSaveDuty()">保存</span> 
                   <!--<span id="btnlockNode" class="k-button"  onclick="DutyPrepareManage.onExportDuty()">导出</span>-->  
		<div class="temp"> 
			<div class="ty-total-decorate"><span id="gridListTotal"></span><i></i></div>
		</div>
	</div> 
	<div class="fr set-hei48">
		<div class="temp">
			<button id="btnlockNode" class="k-button ty-back-btn"  onclick="DutyPrepareManage.returnBackToCalendar()">返回</button>
		</div> 
		<div class="ty-screen-mode" id="tyScreenMode">
			<i class="ty-screen-mode-btn1" title="标准模式"></i><i class="ty-screen-mode-btn2" title="模块全屏"></i><i class="ty-screen-mode-btn3" title="内容全屏"></i>
			<div class="ty-screen-mode-icon"></div>
			<span class="ty-screen-mode-txt"></span>
		</div>
	</div>
</div>

 
