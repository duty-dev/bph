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
 	<div class="span5 txt-right">
 		<button id="btncreateGroup" class="ty-btn-cjfz ty-btn-offset2"  onclick="GpsgroupManage.createGroup()">创建分组</button>
		<button id="btneditGroup" class="ty-btn-xgfz ty-btn-offset2"  onclick="GpsgroupManage.editGroup()">修改分组</button> 
        <button id="btndeleteGroup" class="ty-btn-scfz ty-btn-offset2"  onclick="GpsgroupManage.deleteGroup()">删除分组</button>
        <button id="btnaddMember" class="ty-btn-tjcy ty-btn-offset2"  onclick="GpsgroupManage.addMember()">添加定位设备</button>
 	</div>
 	<div class="span5">
 		<button id="btndeleteMember" class="ty-btn-sccy ty-btn-offset2" style="margin-left:40px;" onclick="GpsgroupManage.deleteMember()">删除定位设备</button> 
        <button id="btnclearUpMember" class="ty-btn-qkcy ty-btn-offset2"  onclick="GpsgroupManage.clearUpMember()">清空定位设备</button>
 	</div>
	<div class="fr set-hei48">
		<div class="ty-screen-mode" id="tyScreenMode">
			<i class="ty-screen-mode-btn1" title="标准模式"></i><i class="ty-screen-mode-btn2" title="模块全屏"></i><i class="ty-screen-mode-btn3" title="内容全屏"></i>
			<div class="ty-screen-mode-icon"></div>
			<span class="ty-screen-mode-txt"></span>
		</div>
	</div>
</div>
 