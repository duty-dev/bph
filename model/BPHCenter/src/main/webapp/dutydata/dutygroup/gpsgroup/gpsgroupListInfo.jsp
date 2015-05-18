<%@ page language="java" pageEncoding="UTF-8"%>

<script type="text/javascript"> 
var sessionId = $("#token").val(); 
var m_gpsgroup_Id;
var opteType = "";
$(function() {
	$("#dtGpsGroup").empty();
	loadData(1);
});
function loadData(pageNo){  
	GpsgroupManage.pageNo = pageNo;
	GpsgroupManage.packageGroupData(pageNo);
	GpsgroupManage.loadGroupData(pageNo); 
	
}
var m_gpsGroup_Query = {};

var GpsgroupManage = { 
	pageNo:1,
	packageGroupData:function(pageNo){
		m_gpsGroup_Query.orgId = $("#organId").val();
		m_gpsGroup_Query.orgPath = $("#organPath").val();
		m_gpsGroup_Query.orgCode = $("#organCode").val();
		m_gpsGroup_Query.page = GpsgroupManage.pageNo;
		m_gpsGroup_Query.pageSize = 10; 
	},
	gpsGroupDataSource:[],
	initMemberGrid:function(){
		$("#dtGroupMember").kendoGrid({
											dataSource: [],
											columns : [ {
												title : 'id',
												field : 'id',
												hidden : true
											}, {
												title : '所属单位',
												field : 'orgShortName'
											}, {
												title : '设备类型',
												field : 'typeName'
											}, {
												title : '设别编号',
												field : 'number'
											}, {
												title : '设备名称',
												field : 'gpsName'
											}],
											selectable: "row"
										});
	},
	loadGroupData : function(pageNo) {
		$.ajax({
		type: "post",
		url: "<%=basePath%>gpsGroupWeb/list.do",
		dataType: "json",
		data: {
			"gpsGroup_Query": JSON.stringify(m_gpsGroup_Query) 
		},
		success: function(req) {
			if (req.code == 200) { 
				if(req.data != null){
				var total =  req.totalRows;
				var rows =req.data; 
			 
				$("#dtGpsGroup").kendoGrid({
					dataSource:{data : rows }, 
					columns : [ {
						title : 'Id',
						field : 'id',
						hidden : true
					}, {
						title : '组名称',
						field : 'name'
					}, {
						title : '共享类型',
						field : 'shareTypeDesc'
					} ],
					selectable: "row",
					change : function(e) {
						var groupId = e.sender.selectable.userEvents.currentTarget.cells[0].innerHTML; 
						m_gpsgroup_Id = groupId;
						GpsgroupManage.loadMemberData(groupId);
					}
				}); 
				$("#dtGpsGroup .k-grid-content").mCustomScrollbar( {scrollButtons:{enable:true},advanced:{ updateOnContentResize: true } });
               									var pg = pagination(pageNo,total,'loadData',10);
               						 
               	                				$("#page").html(pg);
               	                				GpsgroupManage.initMemberGrid();
					} else{
						$("#dtGpsGroup").kendoGrid({
					dataSource: GpsgroupManage.gpsGroupDataSource, 
					columns : [ {
						title : 'Id',
						field : 'id',
						hidden : true
					}, {
						title : '组名称',
						field : 'name'
					}, {
						title : '共享类型',
						field : 'shareTypeDesc'
					} ],
					selectable: "row",
					change : function(e) {
						var groupId = e.sender.selectable.userEvents.currentTarget.cells[0].innerHTML; 
						m_gpsgroup_Id = groupId;
						GpsgroupManage.loadMemberData(groupId);
					}
				}); 
					} 
				}
			}
		});
	},  
	createGroup:function(){
		var organId = $("#organId").val();
		$("#dialog").tyWindow({
			width : "680px",
			height : "500px",
			title : "新建定位设备分组",
			position : {
				top : "100px"
			},
		content: "<%=basePath%>dutyGroupRouteWeb/gotoGpsGroupAdd.do?organId=" + organId+"&sessionId="+sessionId,
					iframe : true, 
					closeCallback :GpsgroupManage.onCloseGorup,
					okCallback:GpsgroupManage.onCloseGorup
				});
	//	GpsGroupManage.showGpsGroupDlg();
	},
	editGroup:function(){
		var organId = $("#organId").val();
		if(m_gpsgroup_Id ==undefined){
			$("body").popjs({"title":"提示","content":"请选择要操作的分组数据"}); 
			return;
		}
		$("#dialog").tyWindow({
			width : "680px",
			height : "500px",
			title : "编辑定位设备信息",
			position : {
				top : "100px"
			},
		content: "<%=basePath%>dutyGroupRouteWeb/gotoGpsGroupEdit.do?groupId="
							+ m_gpsgroup_Id + "&organId=" + organId+"&sessionId="+sessionId,
					iframe : true,
					closeCallback :GpsgroupManage.onCloseGorup,
					okCallback:GpsgroupManage.onCloseGorup
				});
	},
	deleteGroup:function(){
			var kGrid = $("#dtGpsGroup").data("kendoGrid");
			var row = kGrid.dataItem(kGrid.select());
			if (row != null) {
			$("body").tyWindow({"content":"确定要删除?","center":true,"ok":true,"no":true,"okCallback":function(){ 
				$.ajax({
					url : "<%=basePath%>gpsGroupWeb/deleteGpsGroup.do",
					type : "POST",
					dataType : "json",
					data : {
						"gpsGroupId" : row.id
					},
					async : false,
					success : function(req) {
						if (req.code == 200) { 
							$("body").popjs({"title":"提示","content":"删除成功"}); 
							GpsgroupManage.onCloseGorup()
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
					type: "post",
					url: "<%=basePath%>gpsGroupWeb/loadMemberByGroupId.do?groupId="+groupId, 
					dataType: "json", 
					success: function(req) {
						if (req.code==200) {
							var pdata = req.data;
	 
										var dataSo = new kendo.data.DataSource({
											data: pdata
										});
										$("#dtGroupMember").kendoGrid({
											dataSource: dataSo,
											columns : [ {
												title : 'id',
												field : 'id',
												hidden : true
											}, {
												title : '所属单位',
												field : 'orgShortName'
											}, {
												title : '设备类型',
												field : 'typeName'
											}, {
												title : '设别编号',
												field : 'number'
											}, {
												title : '设备名称',
												field : 'gpsName'
											}],
											selectable: "row"
										});
									}else{
										
               	                				GpsgroupManage.initMemberGrid();
									}
								}
							});
						} ,
	addMember:function(){
			var organId = $("#organId").val();
		var kGrid = $("#dtGpsGroup").data("kendoGrid");
			var row = kGrid.dataItem(kGrid.select());
			if (row != null) {
			var groupId = row.id;
		$("#dialog").tyWindow({
			width : "680px",
			height : "500px",
			title : "分组成员管理",
			position : {
				top : "100px"
			},
		content: "<%=basePath%>dutyGroupRouteWeb/gotoGpsGroupAddMember.do?groupId="
							+ groupId + "&organId=" + organId+"&sessionId="+sessionId,
					iframe : true,
					closeCallback :GpsgroupManage.onCloseMember,
					okCallback:GpsgroupManage.onCloseMember
				});
				} else {
				$("body").popjs({"title":"提示","content":"请选择要操作的分组对象"}); 
			}
	},
	deleteMember:function(){
		var kGrid = $("#dtGroupMember").data("kendoGrid");
		var row = kGrid.dataItem(kGrid.select());
		var kgroupGrid = $("#dtGpsGroup").data("kendoGrid");
		var grouprow = kgroupGrid.dataItem(kgroupGrid.select());
		if(grouprow == null){
			$("body").popjs({"title":"提示","content":"请选择要操作的分组数据"}); 
			return;		
		}
		var groupId = grouprow.id;
		if (row != null) {
		$("body").tyWindow({"content":"确定要删除该成员?","center":true,"ok":true,"no":true,"okCallback":function(){ 
			$.ajax({
				url : "<%=basePath%>gpsGroupWeb/delMemberById.do?sessionId="+sessionId,
				type : "POST",
				dataType : "json",
				data : {
					'memberId' : row.id
				},
				async : false,
				success : function(req) {
					if (req.code == 200) { 
									$("body").popjs({"title":"提示","content":"删除成功"});    
									GpsgroupManage.loadMemberData(groupId);
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
		var kGrid = $("#dtGpsGroup").data("kendoGrid");
			var row = kGrid.dataItem(kGrid.select());
			if (row != null) {
			var groupId = row.id;
			$("body").tyWindow({"content":"确定要清空当前分组成员列表?","center":true,"ok":true,"no":true,"okCallback":function(){ 
						$.ajax({
							url : "<%=basePath%>gpsGroupWeb/cleanMemberByGroupId.do?sessionId="+sessionId,
							type : "POST",
							dataType : "json",
							data : {
								"gpsGroupId" : row.id
							},
							async : false,
							success : function(req) {
								if (req.code == 200) { 
									$("body").popjs({"title":"提示","content":"清除成功"});   
									$("#dtGroupMember").empty();
									GpsgroupManage.loadMemberData(groupId);
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
		GpsgroupManage.loadMemberData(m_gpsgroup_Id);  
	},
	onCloseGorup:function(e){
		GpsgroupManage.loadGroupData(GpsgroupManage.pageNo);  
	}
};

</script>
<div class="span5">
<div id="dtGpsGroup"></div>    
<div id="page"></div>
</div> 
<div id="dtGroupMember" class="span5"></div> 
<div id="dialog"></div> 

