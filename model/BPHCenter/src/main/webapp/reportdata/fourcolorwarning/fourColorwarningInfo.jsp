<%@ page language="java" pageEncoding="UTF-8"%>
<style> 
	#alarmCaseInfo td {width:12%;} 
	.a{width:auto;float:left;margin-right:40px;}
</style>
<script>
$(function() {
	WarningManage.loadalarmList(1); 
});
function loadData(pageNo){
	WarningManage.pageNo = pageNo;
	WarningManage.loadalarmList(pageNo);
} 
var WarningManage = {
	loadalarmTypeList:function(wid){
		$.ajax({
					url:"<%=basePath%>alarmStatisticWeb/getAlarmTypeList.do?warningId="+wid,
					type:"post",  
					dataType:"json",
					success:function(req){
							if(req.code == 200){ 
								req.data.parentId = null;
								var json_data = JSON.stringify(req.data);
								$("#alarmtreeview").empty();
								$("#alarmtreeview").kendoTreeView({ 
								    checkboxes: {
					      				checkChildren: true//允许复选框多选
					   				},
								    dataTextField: "text", 
								    dataSource: [eval('(' + json_data + ')')]
								}).data("kendoTreeView");
								$("#alarmtreeview").parent().mCustomScrollbar({scrollButtons:{enable:true},advanced:{ updateOnContentResize: true } });
								$("input[type='checkbox']").attr("disabled","disabled")
							}
						}
				});
	},
	pageNo:1,
	loadalarmList:function(pageNo){
		$.ajax({
				url : "<%=basePath%>colorWarningWeb/getColorWarningList.do",
				type : "post",
				data : {
					"organId" : $("#organId").val(),
					"pageStart"	:pageNo,
					"pageSize":10 
				},
				dataType : "json",
				success : function(req) {
					if (req.code == 200) {
							if (req.data != null) {
								var udata = req.data;
               					var total = req.totalRows;
               					$("#alarmCaseGrid").empty();
               					$("#alarmCaseGrid").kendoGrid({   
									selectable : "multiple", 
									dataSource : {
														data : udata 
													},
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
											var caseGrid = $("#alarmCaseGrid").data("kendoGrid");
											var row = caseGrid.dataItem(caseGrid.select());
											if(row!=null){
												WarningManage.showWarningInfo(row);
											}
										}
									}); 
									var myGrid = $("#alarmCaseGrid").data("kendoGrid");
									myGrid.element.on("dblclick","tbody>tr","dblclick",function(e){
										 var id = $(this).find("td").first().text();
										 WarningManage.editWarningAction(id);
									});  
       		   						WarningManage.loadFirstData();
               				}
               		} else{
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
								selectable: "row" 
						});  
               		}	
				}
		});
	},
	addWarning:function(){
		var organId = $("#organId").val();
		$("#dialog").tyWindow({
			width : "880px",
			height : "710px",
			title : "新增预警信息",
			position : {
				top : "100px"
			},
		content: "<%=basePath%>reportRouteWeb/gotoFourColorWarningAdd.do?organId=" + organId,
					iframe : true,
					closeCallback : WarningManage.onClose,
					okCallback:WarningManage.onClose
				});
	},
	editWarning:function(){
		var caseGrid = $("#alarmCaseGrid").data("kendoGrid");
		var row = caseGrid.dataItem(caseGrid.select());
		if(row != null){
			WarningManage.editWarningAction(row.id);
		}else{
			$("body").popjs({"title":"提示","content":"请选择要操作的数据"});
		}
	},
	editWarningAction:function(wid){ 
		$("#dialog").tyWindow({
			width : "880px",
			height : "710px",
			title : "修改预警信息",
			position : {
				top : "100px"
			},
		content: "<%=basePath%>reportRouteWeb/gotoFourColorWarningEdit.do?caseId="+wid,
					iframe : true,
					closeCallback : WarningManage.onClose,
					okCallback:WarningManage.onClose
				});
	},
	deleteWarning:function(){
		var caseGrid = $("#alarmCaseGrid").data("kendoGrid");
		var row = caseGrid.dataItem(caseGrid.select());
		if(row != null){
			$("body").tyWindow({"content":"确定要删除名称为  "+row.name+" 的预警信息吗?","center":true,"ok":true,"no":true,"okCallback":function(){
					$.ajax({
					url : "<%=basePath%>colorWarningWeb/deleteCaseById.do?caseId="+row.id,
					type : "post", 
					dataType : "json",
					success : function(req) {
						if(req.code==200){
							loadData(WarningManage.pageNo);
						}else{ 
							$("body").popjs({"title":"提示","content":req.description}); 
						}
					}
			});
				 }
			});
		}else{
			$("body").popjs({"title":"提示","content":"请选择要操作的数据"});
		}
	},
	loadFirstData:function(){
		var caseGrid = $("#alarmCaseGrid").data("kendoGrid");
		if(caseGrid._data.length>0){ 
			var row = caseGrid._data[0]; 
			WarningManage.showWarningInfo(row);
		}
	},
	showWarningInfo:function(row){ 		
		if(row!=null){
			WarningManage.loadalarmTypeList(row.id);
			$("#caseTitle").text("");
			$("#caseTitle").text(row.name+"   设置详情"); 
			var colors = row.colors;
			var caseTypes = row.caseTypes;
			var levels =row.caseLevels;   
			$("#lbl_ckalarmLevel1").html("");
			$("#lbl_ckalarmLevel2").html("");
			$("#lbl_ckalarmLevel3").html(""); 
			$.each(levels,function(index,value){ 
				switch(value.caseLevel){
					case 1: 
						var ckhtml = "<input id='ckalarmLevel1' type='checkbox' checked='checked'  /><span>一级警情</span>";
						$("#lbl_ckalarmLevel1").append(ckhtml);
						break;
					case 2: 
						var ckhtml = "<input id='ckalarmLevel2'  type='checkbox' checked='checked'  /><span>二级警情</span>";
						$("#lbl_ckalarmLevel2").append(ckhtml);
						break;
					case 3: 
						var ckhtml = "<input id='ckalarmLevel3'  type='checkbox' checked='checked'  /><span>三级警情</span>";
						$("#lbl_ckalarmLevel3").append(ckhtml);
						break;
				}				
			});
			 
			$.each(colors,function(index,cObj){
				var solorlevel = cObj.level;
				switch(solorlevel){
					case 1:
						$("#redValue").text("");
						$("#redValue").text(cObj.ge+" %");
						break;
					case 2:
						$("#yellowValuea").text("");
						$("#yellowValueb").text("");
						$("#yellowValuea").text(cObj.ge+" %");
						$("#yellowValueb").text(cObj.lt+" %");
						break;
					case 3:
						$("#blueValuea").text("");
						$("#blueValueb").text("");
						$("#blueValuea").text(cObj.ge+" %");
						$("#blueValueb").text(cObj.lt+" %");
						break;
					case 4:
						$("#greenValue").text("");
						$("#greenValue").text(cObj.ge+" %");
						break;
				}
			}); 
		}
	},
	onClose:function(){
		loadData(WarningManage.pageNo);
	}
};
</script>
<div style="width:25%; float:left;min-width:400px;"> 
	<h2 style="width:100%">预警列表</h2> 
	<div id="alarmCaseGridtoolbar" style="padding:5px"> 
        <span id="undo" class="k-button" onclick="WarningManage.addWarning()">添加</span> 
        <span id="undo" class="k-button" onclick="WarningManage.editWarning()">修改</span> 
        <span id="undo" class="k-button" onclick="WarningManage.deleteWarning()">删除</span>   
    </div> 
	<div id="alarmCaseGrid" >
	</div>  
</div>
<div id="dialog"></div>
<div id="alarmCaseInfo"  style="width:50%;height:480px;float:left;margin-left:10px;">
	<h2 id="caseTitle" style="width:100%"></h2>
	<table style="width:100%;height:350px">
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
				<label id="redValue"></label>
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
				<label id="yellowValuea"></label>
			</td>
			<td>
				<label style="margin-left:20px">,增幅 </label>
			</td>
			<td>
				<label style="margin-left:20px">< </label>
			</td> 
			<td>
				<label id="yellowValueb"></label>
			</td>
		</tr>
		<tr>
			<td> 
				<table style="width:90%;padding:3px"><tr><td style="background-color:#8b2ae3;height:25px"></td></tr></table>  
			</td>
			<td>
				<label style="margin-left:20px">紫色预警</label>
			</td>
			<td>
				<label style="margin-left:20px">增幅  </label>
			</td>
			<td>
				<label>≥</label>
			</td>
			<td>
				<label id="blueValuea"></label>
			</td>
			<td>
				<label style="margin-left:20px">,增幅 </label>
			</td>
			<td>
				<label style="margin-left:20px">< </label>
			</td> 
			<td>
				<label id="blueValueb"></label>
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
				<label id="greenValue"></label>
			</td>
			<td>
			</td>
		<tr>
			<td colspan="7">
				<h4>警情级别</h4>
			</td>
		</tr>
		<tr>
			<td colspan="6" style="text-align:center">
				<label id="lbl_ckalarmLevel1" class="a"></label> 
				 <label id="lbl_ckalarmLevel2"  class="a"></label> 
				 <label id="lbl_ckalarmLevel3"  class="a"></label>
			</td>
		</tr> 
	</table>
</div>
<div id="div_alarmType" style="height:460px;overflow-x:hidden;overflow-y:auto;">
	<h4>警情类别</h4>
	<div id="alarmtreeview" > 
	</div>
</div>

