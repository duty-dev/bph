<%@ page language="java" pageEncoding="UTF-8"%>
<style type="text/css">
	.ty-drt-table td{padding:5px;}
</style>

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
