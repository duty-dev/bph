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
 	<div class="fl ml30 set-hei48" style="width:165px;"></div>
	<div class="fl ml30 set-hei48">
		<button id="btnaddParentNode" class="ty-btn-xjgjd ty-btn-offset"  onclick="DutyTyepManage.addParentNode()">新建根节点</button>
        <button id="btnaddChildNode" class="ty-btn-xjzjd ty-btn-offset"  onclick="DutyTyepManage.addChildNode()">新建子节点</button> 
        <button id="btnaddChildNode" class="ty-btn-edit ty-btn-offset"  onclick="DutyTyepManage.editDutyType()">编辑</button> 
        <button id="btndeleteNode" class="ty-btn-delete ty-btn-offset"  onclick="DutyTyepManage.deleteDutyType()">删除</button> 
        <button id="btnunLockNode" class="ty-btn-start ty-btn-offset"  onclick="DutyTyepManage.unLockNode()">启用</button> 
        <button id="btnlockNode" class="ty-btn-stop ty-btn-offset"  onclick="DutyTyepManage.lockNode()">停用</button>    
		<div class="temp">
			<div class="ty-total-decorate"><span id="gridListTotal"></span><i></i></div>
			
		</div>
	</div>
	
	<div class="fr set-hei48">
		<div class="ty-screen-mode" id="tyScreenMode">
			<i class="ty-screen-mode-btn1" title="标准模式"></i><i class="ty-screen-mode-btn2" title="模块全屏"></i><i class="ty-screen-mode-btn3" title="内容全屏"></i>
			<div class="ty-screen-mode-icon"></div>
			<span class="ty-screen-mode-txt"></span>
		</div>
	</div>
</div>

 
