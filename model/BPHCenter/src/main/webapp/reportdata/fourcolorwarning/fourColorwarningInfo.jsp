<%@ page language="java" pageEncoding="UTF-8"%>
<style>
	#alarmCaseInfo tr {heigth:50px;}
	#alarmCaseInfo td {width:12%;}
	#alarmTypes li{float:left;list-style:none; padding:25px;}
</style>
<script>
	$(function() {
		$("#alarmCaseGrid").kendoGrid({   
				selectable : "multiple", 
				dataSource:[],
				columns : [ {
					title : 'Id',
					field : 'id',
					hidden : true
				}, {
					title : '预警名称',
					field : 'name'
				} ],
				selectable: "row",
				change : function(e) {
					var tempId = e.sender.selectable.userEvents.currentTarget.cells[0].innerHTML; 
					//alert(tempId);
				}
			}); 
	});
</script>
<div style="width:25%; float:left;min-width:400px;"> 
	<h2 style="width:100%">预警列表</h2> 
	<div id="alarmCaseGridtoolbar" style="padding:5px"> 
        <span id="undo" class="k-button" onclick="">添加</span> 
        <span id="undo" class="k-button" onclick="">修改</span> 
        <span id="undo" class="k-button" onclick="">删除</span>   
    </div> 
	<div id="alarmCaseGrid" >
	</div> 
</div>
<div id="alarmCaseInfo"  style="width:60%; float:left;margin-left:10px;">
	<h2 style="width:100%">设置详情</h2>
	<table style="width:100%;heigth:650px">
		<tr>
			<td colspan="7">
				<h4>预警规则</h4>
			</td>
		</tr>
			<td>
			</td>
			<td>
				<label style="margin-left:20px">增幅 = </label>
			</td>
			<td colspan="2" style="text-align:center">
				<table style="width:100%">
					<tr style="border-bottom:1px solid #97c6dc">
						<td><label>周期    - 上一周期</label>
						</td>
					</tr>
					<tr>
						<td><label>上一周期</label>
						</td>
					</tr>
				</table> 
			</td>
			<td>
				<label>×100%</label>
			</td> 
			<td colspan="3">
				(‘周期’为‘周期设置’中选择的周期，‘上一周期’为选择周期的前一周期)
			</td>
		<tr>
			<td> 
				<table style="width:90%;padding:3px"><tr><td style="background-color:red;height:25px"></td></tr></table> 
			</td>
			<td>
				<label style="margin-left:20px">红色预警</label>
			</td>
			<td>
				<label style="margin-left:20px">增幅  </label>
			</td>
			<td>
				<label>≥</label>
			</td>
			<td>
			</td>
			<td>
			</td>
		</tr>
		<tr> 
			<td>  
				<table style="width:90%;padding:3px"><tr><td style="background-color:yellow;height:25px"></td></tr></table> 
			</td>
			<td>
				<label style="margin-left:20px">黄色预警</label>
			</td>
			<td>
				<label style="margin-left:20px">增幅  </label>
			</td>
			<td>
				<label>≥</label>
			</td>
			<td>
			</td>
			<td>
				<label style="margin-left:20px">,增幅 </label>
			</td>
			<td>
				<label style="margin-left:20px">< </label>
			</td> 
			<td>
			</td>
		</tr>
		<tr>
			<td> 
				<table style="width:90%;padding:3px"><tr><td style="background-color:blue;height:25px"></td></tr></table>  
			</td>
			<td>
				<label style="margin-left:20px">蓝色预警</label>
			</td>
			<td>
				<label style="margin-left:20px">增幅  </label>
			</td>
			<td>
				<label>≥</label>
			</td>
			<td>
			</td>
			<td>
				<label style="margin-left:20px">,增幅 </label>
			</td>
			<td>
				<label style="margin-left:20px">< </label>
			</td> 
			<td>
			</td>
		</tr>
			<td> 
				<table style="width:90%;padding:3px"><tr><td style="background-color:green;height:25px"></td></tr></table> 
			</td>
			<td>
				<label style="margin-left:20px">绿色预警</label>
			</td>
			<td>
				<label style="margin-left:20px">增幅  </label>
			</td>
			<td>
				<label><</label>
			</td>
			<td>
			</td>
			<td>
			</td>
		<tr>
			<td colspan="7">
				<h4>警情级别</h4>
			</td>
		</tr>
		<tr>
			<td colspan="2" style="text-align:center">
				<label class="a"><input id="ckalarmLevel1" type="checkbox" value="1">一级警情</label>
			</td>
			<td colspan="2" style="text-align:center">
				 <label class="a"><input id="ckalarmLevel2" type="checkbox" value="2">二级警情</label>
			</td>
			<td colspan="2" style="text-align:center">
				 <label class="a"><input id="ckalarmLevel3" type="checkbox" value="3">三级警情</label>
			</td>
		</tr>
		<tr>
			<td colspan="7">
				<h4>警情类别</h4>
			</td>
		</tr>
		<tr>
			<td colspan="7"> 
				<ul id="alarmTypes">
					<li>刑事案件
					</li>
					<li>治安警情
					</li>
					<li>行政案件
					</li>
					<li>举报投诉
					</li>
					<li>灾害事故
					</li>
				</ul>
			</td>
		</tr>
	</table>
</div>

