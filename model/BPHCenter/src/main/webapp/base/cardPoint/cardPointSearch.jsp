<%@ page language="java" pageEncoding="UTF-8"%>

<div id="template">
	<div class="fl set-hei48">
		<input type="hidden" id="organId" /> <input type="hidden" id="organPath" /> 
		查询范围选择： <select id="organOrNext" required="required" name="addOrganLevel" placeholder="请选择查询范围">
			<option value="1">本级机构</option>
			<option value="2">本级机构与下级机构</option>
		</select>
	</div>
	<div class="fl ml10 set-hei48">
		<span class="fl">卡点名称:<input type="search" class="k-textbox" id="cardPointName" name="cardPointName" placeholder="等待输入..." /></span>
		<button id="textButton" class="fr ty-btn-search" onclick="searchCardPoint(1)"></button>
		<div class="temp">
			<div class="ty-total-decorate"><span id="gridListTotal"></span><i></i></div>
			<div class="ty-decorate2"></div>
		</div>
	</div>
	<div class="fl set-hei48 ml10">
		<button id="undo" class="ty-btn-add ty-btn-offset" onclick="addCardPoint()">新增卡点</button>
		<button id="undo" class="ty-btn-qcsz ty-btn-offset" onclick="openCircleLayer()">圈层设置</button>
		<button id="txt" class="ty-btn-qchz ty-btn-offset" onclick="drawCardPoint()">圈层绘制</button>
	</div>
	<div class="fr set-hei48">
		<div class="ty-screen-mode" id="tyScreenMode">
			<i class="ty-screen-mode-btn1" title="标准模式"></i><i class="ty-screen-mode-btn2" title="模块全屏"></i><i class="ty-screen-mode-btn3" title="内容全屏"></i>
			<div class="ty-screen-mode-icon"></div>
			<span class="ty-screen-mode-txt"></span>
		</div>
	</div>
</div>
<script>
$(document).ready(function() {    
	$("#organOrNext").kendoComboBox().prev().find(".k-input").attr("readonly",true);
	$("#cardPointName").keydown(function(event) {  
        if (event.keyCode == 13) {  
        	searchCardPoint(1); 
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
