<%@ page language="java" pageEncoding="UTF-8"%>

<div id="template">
                <div class="fl set-hei48">
                                                         服务名:<input type="search" id="serviceName" name="serviceName" placeholder="等待输入..."/>
                   <button id="textButton" class="fr ty-btn-search" style="margin-left:80px;" onclick="alarmrequest()"></button>
                   <button onclick="openTyWin()"></button>
                   <div class="temp">
						<div class="ty-total-decorate" style="left:160px;top:-80px;"><span id="gridListTotal"></span><i></i></div>
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
<script>
function openTyWin(){
	$("#drawDialog").tyWindow({
		width: "480px",
		height: "680px",
		title: "提示",
		position: {
			top: "275px",
			left: 30
		},
		content: $("#vertical").html(),
	});
}

$(document).ready(function() {    
	$("#serviceName").kendoMaskedTextBox();
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