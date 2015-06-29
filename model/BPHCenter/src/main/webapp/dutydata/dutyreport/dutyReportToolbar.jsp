<%@ page language="java" pageEncoding="UTF-8"%>
<style type="text/css">
	.ty-drt-table td{padding:5px;}
</style>
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
<div class="fr set-hei48">
		<div class="ty-screen-mode" id="tyScreenMode">
			<i class="ty-screen-mode-btn1" title="标准模式"></i><i class="ty-screen-mode-btn2" title="模块全屏"></i><i class="ty-screen-mode-btn3" title="内容全屏"></i>
			<div class="ty-screen-mode-icon"></div>
			<span class="ty-screen-mode-txt"></span>
		</div>
	</div>
<table border=0 class="ty-drt-table">
		<tr>
			<td >时间范围:</td>
			<td style="width: 213px; "><input id="dteBeginDate"  /></td>
			<td>&nbsp;</td>
			<td><input id="spnBeginTime"  /></td>
			<td><input id="spnEndTime"    /></td>
			<td colspan="3">&nbsp;</td>
		</tr>
		<tr>
			<td >任务属性:</td>
			<td colspan="3"><select id="dutyProperty"></select></td>
			<td>衣着:<input id="ckAttireType1" type="checkbox"  value="0">制服　　　
				<input id="ckAttireType2" type="checkbox"  value="1">便衣</td>
			<td></td>
		</tr>
		<tr> 
			<td >人员类别 :</td>
			<td colspan="3"><select id="cmbpoliceType"></select></td>  
			<td>武装:<input id="ckArmamentType1" type="checkbox" value="0">非武装　　
				<input id="ckArmamentType2" type="checkbox" value="1">武装　　</td>
			<td></td> 
		</tr> 
		<tr> 
			<td>
				勤务类型 : </td>
			<td>
			 <input id="cmbdutytype"  >
			</td>
			<td class="MySearchTDTitle">　</td>
			<td>
				
			</td>
			<td></td> 
			<td class="MySearchTDTitle"> <button id="btnDutyReportSearch" class="fr ty-btn-search" onclick="reportManage.search()"/> 　</td>
		</tr>
	</table>
