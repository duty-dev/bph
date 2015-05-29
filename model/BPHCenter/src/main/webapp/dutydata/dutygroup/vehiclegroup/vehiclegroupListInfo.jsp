<%@ page language="java" pageEncoding="UTF-8"%>

<script type="text/javascript"> 
var sessionId = $("#token").val();  
var m_vehiclegroup_Id;
$(function() {
	$("#dtVehicleGroup").empty();
	loadData(1);
});
function loadData(pageNo){  
	VehicleGroupManage.pageNo = pageNo;
	VehicleGroupManage.packageGroupData(pageNo);
	VehicleGroupManage.loadGroupData(pageNo); 
	
}
var m_vehicleGroup_Query = {};

var VehicleGroupManage = { 
	pageNo:1,
	packageGroupData:function(pageNo){
		m_vehicleGroup_Query.orgId = $("#organId").val();
		m_vehicleGroup_Query.orgPath = $("#organPath").val();
		m_vehicleGroup_Query.orgCode = $("#organPath").val();
		m_vehicleGroup_Query.page = pageNo;
		m_vehicleGroup_Query.pageSize = 10; 
	},
	vehicleGroupDataSource:[],
	initMemberGrid:function(){
	$("#dtGroupMember").kendoGrid({
							dataSource : [],
							columns : [ {
								title : 'id',
								field : 'id',
								hidden : true
							}, {
								title : '所属单位',
								field : 'orgShortName'
							}, {
								title : '车辆类型',
								field : 'typeName'
							}, {
								title : '车牌号码',
								field : 'number'
							}, {
								title : '车辆用途',
								field : 'purpose'
							}, ],
							selectable : "row"
						});
	},
	loadGroupData : function(pageNo) {
		$.ajax({
						type : "post",
						url: "<%=basePath%>vehicleGroupWeb/list.do",
						dataType : "json",
						data : {
							"vehicleGroup_Query" : JSON.stringify(m_vehicleGroup_Query) 
						},
						success : function(req) {
							if (req.code == 200) {
								var rows = req.data;
								 
					var total =  req.totalRows;
					$("#dtVehicleGroup").empty();
								$("#dtVehicleGroup")
										.kendoGrid(
												{
													dataSource :{data:rows}, 
													columns : [
															{
																title : 'Id',
																field : 'id',
																align : 'left',
																width : 10,
																hidden : true
															},
															{
																title : '组名称',
																field : 'name'
															},
															{
																title : '共享类型',
																field : 'shareTypeDesc'
															} ],
													selectable : "row",
													change : function(e) {
														var groupId = e.sender.selectable.userEvents.currentTarget.cells[0].innerHTML;
														m_vehiclegroup_Id = groupId;
														VehicleGroupManage.loadMemberData(groupId);
													}
												});
												$("#dtVehicleGroup .k-grid-content").mCustomScrollbar( {scrollButtons:{enable:true},advanced:{ updateOnContentResize: true } });
               									var pg = pagination(pageNo,total,'loadData',10);
               						 
               	                				$("#page").html(pg);
               	                				VehicleGroupManage.initMemberGrid();
							}else{
								
								$("#dtVehicleGroup")
										.kendoGrid(
												{
													dataSource : VehicleGroupManage.vehicleGroupDataSource,
													pageable : true,
													columns : [
															{
																title : 'Id',
																field : 'id',
																align : 'left',
																width : 10,
																hidden : true
															},
															{
																title : '组名称',
																field : 'name'
															},
															{
																title : '共享类型',
																field : 'shareTypeDesc'
															} ],
													selectable : "row",
													change : function(e) {
														var groupId = e.sender.selectable.userEvents.currentTarget.cells[0].innerHTML;
														m_vehiclegroup_Id = groupId;
														VehicleGroupManage.loadMemberData(groupId);
													}
												});
							}
						}
					});
	},  
	createGroup:function(){
		var organId = $("#organId").val();
		$("#dialog").tyWindow({
			width : "688px",
			height : "500px",
			title : "车辆分组信息",
			position : {
				top : "100px"
			},
		content: "<%=basePath%>dutyGroupRouteWeb/gotoVehicleGroupAdd.do?organId=" + organId+"&sessionId="+sessionId,
					iframe : true,
					closeCallback :VehicleGroupManage.onCloseGorup,
					okCallback:VehicleGroupManage.onCloseGorup
				});
	},
	editGroup:function(){
		var organId = $("#organId").val();
		var organId = $("#organId").val();
		if(m_vehiclegroup_Id ==undefined){
			$("body").popjs({"title":"提示","content":"请选择要操作的分组数据"}); 
			return;
		}
		if(m_vehiclegroup_Id!=undefined){
		$("#dialog").tyWindow({
			width : "688px",
			height : "500px",
			title : "车辆分组信息",
			position : {
				top : "100px"
			},
		content: "<%=basePath%>dutyGroupRouteWeb/gotoVehicleGroupEdit.do?groupId="
							+ m_vehiclegroup_Id + "&organId=" + organId+"&sessionId="+sessionId,
					iframe : true,
					closeCallback :VehicleGroupManage.onCloseGorup,
					okCallback:VehicleGroupManage.onCloseGorup
				});
				}else{
				$("body").popjs({"title":"提示","content":"请选择要操作的对象"}); 
				}
	},
	deleteGroup:function(){ 
			var kGrid = $("#dtVehicleGroup").data("kendoGrid");
			var row = kGrid.dataItem(kGrid.select());
			if (row != null) {
			$("body").tyWindow({"content":"确定要删除?","center":true,"ok":true,"no":true,"okCallback":function(){ 
				$.ajax({
					url : "<%=basePath%>vehicleGroupWeb/deleteVehicleGroup.do",
					type : "POST",
					dataType : "json",
					data : {
						"vehicleGroupId" : row.id
					},
					async : false,
					success : function(req) {
						if (req.code == 200) { 
							$("body").popjs({"title":"提示","content":"删除成功"}); 
							VehicleGroupManage.onCloseGorup()
							$("#dtGroupMember").empty();
						} else {
							$("body").popjs({"title":"提示","content":"删除分组数据失败"}); 
						}
					}
				});
				}});
			} else {
				$("body").popjs({"title":"提示","content":"请选择要操作的数据"}); 
			}
	},
	loadMemberData:function(groupId){
		$.ajax({
				type : "post",
				url : "<%=basePath%>vehicleGroupWeb/loadMemberByGroupId.do?groupId="
						+ groupId,
				dataType : "json",
				success : function(req) {
					if (req.code == 200) {
						var pdata = req.data;

						var dataSo = new kendo.data.DataSource({
							data : pdata
						});
						$("#dtGroupMember").empty();
						$("#dtGroupMember").kendoGrid({
							dataSource : dataSo,
							columns : [ {
								title : 'id',
								field : 'id',
								hidden : true
							}, {
								title : '所属单位',
								field : 'orgShortName'
							}, {
								title : '车辆类型',
								field : 'typeName'
							}, {
								title : '车牌号码',
								field : 'number'
							}, {
								title : '车辆用途',
								field : 'purpose'
							}, ],
							selectable : "row"
						});
					}else{
						
               	                				VehicleGroupManage.initMemberGrid();
					}
				}
			});
	} ,
	addMember:function(){
			var organId = $("#organId").val();
		var kGrid = $("#dtVehicleGroup").data("kendoGrid");
			var row = kGrid.dataItem(kGrid.select());
			if (row != null) {
			var groupId = row.id;
		$("#dialog").tyWindow({
			width : "680px",
			height : "500px",
			title : "分组成员信息",
			position : {
				top : "100px"
			},
		content: "<%=basePath%>dutyGroupRouteWeb/gotoVehicleGroupAddMember.do?groupId="
							+ groupId + "&organId=" + organId+"&sessionId="+sessionId,
					iframe : true,
					closeCallback :VehicleGroupManage.onCloseMember,
					okCallback:VehicleGroupManage.onCloseMember
				});} else {
				$("body").popjs({"title":"提示","content":"请选择要操作的分组对象"}); 
			}
	},
	deleteMember:function(){
		var kGrid = $("#dtGroupMember").data("kendoGrid");
		var row = kGrid.dataItem(kGrid.select());
		var kgroupGrid = $("#dtVehicleGroup").data("kendoGrid");
		var grouprow = kgroupGrid.dataItem(kgroupGrid.select());
		if(grouprow == null){
			$("body").popjs({"title":"提示","content":"请选择要操作的分组数据"}); 
			return;		
		}
		var groupId = grouprow.id;
		if (row != null) {
		$("body").tyWindow({"content":"确定要删除该成员?","center":true,"ok":true,"no":true,"okCallback":function(){ 
			$.ajax({
				url : "<%=basePath%>vehicleGroupWeb/delMemberById.do?sessionId="+sessionId,
				type : "POST",
				dataType : "json",
				data : {
					'memberId' : row.id
				},
				async : false,
				success : function(req) {
					if (req.code == 200) { 
									$("body").popjs({"title":"提示","content":"删除成功"});    
									VehiclegroupManage.loadMemberData(groupId);
								} else {
									$("body").popjs({"title":"提示","content":"清空分组成员列表失败"}); 
								}
				}
			});
			}});
		} else {
			$("body").popjs({"title":"提示","content":"请选择要操作的数据"}); 
		}
	},
	clearUpMember:function(){
		var kGrid = $("#dtVehicleGroup").data("kendoGrid");
			var row = kGrid.dataItem(kGrid.select());
			if (row != null) {
			var groupId = row.id;
			$("body").tyWindow({"content":"确定要清空当前分组成员列表?","center":true,"ok":true,"no":true,"okCallback":function(){ 
						$.ajax({
							url : "<%=basePath%>vehicleGroupWeb/cleanMemberByGroupId.do?sessionId="+sessionId,
							type : "POST",
							dataType : "json",
							data : {
								"vehicleGroupId" : row.id
							},
							async : false,
							success : function(req) {
								if (req.code == 200) { 
									$("body").popjs({"title":"提示","content":"清除成功"});   
									$("#dtGroupMember").empty();
									VehiclegroupManage.loadMemberData(groupId);
								} else {
									$("body").popjs({"title":"提示","content":"清空分组成员列表失败"}); 
								}
							}
						});}});
			} else {
				$("body").popjs({"title":"提示","content":"请选择要操作的分组"}); 
			}
	},
	onCloseMember:function(e){
		VehicleGroupManage.loadMemberData(m_vehiclegroup_Id);  
	},
	onCloseGorup:function(e){
		VehicleGroupManage.loadGroupData(VehicleGroupManage.pageNo);  
	}
};

</script>
<div class="span5">
<div id="dtVehicleGroup" ></div>   
<div id="page"></div>
</div>
<div id="dtGroupMember" class="span5"></div> 
<div id="dialog"></div> 

