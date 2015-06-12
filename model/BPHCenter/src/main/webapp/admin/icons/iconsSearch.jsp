
<%@ page language="java" pageEncoding="UTF-8"%>
<div id="template">
	<div class="fl set-hei48">
	<input type="hidden" id="iconType" name="iconType" value=1>
	<input type="hidden" id="iconTypeName" name="iconTypeName" value="人员">
		<button id="undo" class="ty-btn-add ty-btn-offset" onclick="addIcon()"></button>
	</div>
</div>
<script>
$(document).ready(function() {    
	$("#searchType").kendoComboBox().prev().find(".k-input").attr("readonly",true);
	$("#userName").keydown(function(event) {  
        if (event.keyCode == 13) {  
        	search(); 
        }  
    });
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
