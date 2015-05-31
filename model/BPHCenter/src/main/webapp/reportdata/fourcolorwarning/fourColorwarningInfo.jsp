<%@ page language="java" pageEncoding="UTF-8"%>
<style> 
	#alarmCaseInfo td {width:12%;}
	#alarmTypes li{float:left;list-style:none; padding:25px;}
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
									$("#alarmCaseGrid .k-grid-content").mCustomScrollbar( {scrollButtons:{enable:true},advanced:{ updateOnContentResize: true } });
       		    					var pg = pagination(pageNo,total,'loadData',10);
       		   						$("#page").html(pg);
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
			$("#caseTitle").text("");
			$("#caseTitle").text(row.name+"   设置详情"); 
			var colors = row.colors;
			var caseTypes = row.caseTypes;
			var level = caseTypes[0].caseTypeLevel;
			if(level ==1||level =="1"){
				$("#ckalarmLevel1").removeAttr("disabled");
				$("#ckalarmLevel2").removeAttr("disabled");
				$("#ckalarmLevel3").removeAttr("disabled");
				$("#ckalarmLevel2").removeAttr("checked");
				$("#ckalarmLevel3").removeAttr("checked");
				$("#ckalarmLevel1").attr("checked","checked");  
				$("#ckalarmLevel2").attr("disabled","disabled");
				$("#ckalarmLevel3").attr("disabled","disabled");
			}else if(level==2||level =="2"){
				$("#ckalarmLevel1").removeAttr("disabled");
				$("#ckalarmLevel2").removeAttr("disabled");
				$("#ckalarmLevel3").removeAttr("disabled");
				$("#ckalarmLevel1").removeAttr("checked");
				$("#ckalarmLevel3").removeAttr("checked");
				$("#ckalarmLevel2").attr("checked","checked");  
				$("#ckalarmLevel1").attr("disabled","disabled");
				$("#ckalarmLevel3").attr("disabled","disabled");
			}else{
				$("#ckalarmLevel1").removeAttr("disabled");
				$("#ckalarmLevel2").removeAttr("disabled");
				$("#ckalarmLevel3").removeAttr("disabled");
				$("#ckalarmLevel1").removeAttr("checked");
				$("#ckalarmLevel2").removeAttr("checked");
				$("#ckalarmLevel3").attr("checked","checked");  
				$("#ckalarmLevel1").attr("disabled","disabled");
				$("#ckalarmLevel2").attr("disabled","disabled");
			}
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
			var caseTypehtml = "";
			$.each(caseTypes,function(index,tObj){
				caseTypehtml +="<li>"+tObj.caseTypeName+"</li>"
			});
			if(caseTypehtml.length>0){
				$("#alarmTypes").html("");
				$("#alarmTypes").html(caseTypehtml); 
			}
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
	<div id="page"></div>
</div>
<div id="dialog"></div>
<div id="alarmCaseInfo"  style="width:60%;height:700px;float:left;margin-left:10px;">
	<h2 id="caseTitle" style="width:100%"></h2>
	<table style="width:100%;heigth:95%">
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
			<td colspan="2" style="text-align:center">
				<label class="a"><input id="ckalarmLevel1" name="ckalarmLevel" type="radio"  value="1">一级警情</label>
			</td>
			<td colspan="2" style="text-align:center">
				 <label class="a"><input id="ckalarmLevel2" name="ckalarmLevel"  type="radio"  value="2">二级警情</label>
			</td>
			<td colspan="2" style="text-align:center">
				 <label class="a"><input id="ckalarmLevel3" name="ckalarmLevel"  type="radio" value="3">三级警情</label>
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
				</ul>
			</td>
		</tr>
	</table>
</div>

