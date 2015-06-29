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

 
