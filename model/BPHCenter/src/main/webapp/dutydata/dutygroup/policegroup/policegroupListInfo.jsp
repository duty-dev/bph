<%@ page language="java" pageEncoding="UTF-8"%>

<script type="text/javascript"> 
var sessionId = $("#token").val(); 
var m_policegroup_Id;
$(function() {
	$("#dtPoliceGroup").empty();
	loadData(1);
});
function loadData(pageNo){  
	PolicegroupManage.pageNo = pageNo;
	PolicegroupManage.packageGroupData(pageNo);
	PolicegroupManage.loadGroupData(pageNo); 
	
}
var m_policeGroup_Query = {};

var PolicegroupManage = { 
	pageNo:1,
	packageGroupData:function(pageNo){
		m_policeGroup_Query.orgId = $("#organId").val();
		m_policeGroup_Query.orgPath = $("#organPath").val();
		m_policeGroup_Query.orgCode = $("#organPath").val();
		m_policeGroup_Query.page = pageNo;
		m_policeGroup_Query.pageSize = 10; 
	},
	policeGroupDataSource:[],
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
														title : '姓名',
														field : 'name'
													}, {
														title : '警号',
														field : 'number'
													}, {
														title : '职务',
														field : 'title'
													} ],
											selectable: "row"
										});
	},
	loadGroupData : function(pageNo) {
		$.ajax({
		type: "post",
		url: "<%=basePath%>policeGroupWeb/list.do",
		dataType: "json",
		ansyc:false,
		data: {
			"policeGroup_Query": JSON.stringify(m_policeGroup_Query) 
		},
		success: function(req) {
			if (req.code == 200) { 
				if(req.data != null){
					var rows =req.data;  
					var total =  req.totalRows;
					$("#dtPoliceGroup").kendoGrid({
					dataSource: {data:rows}, 
					columns : [ {
						title : 'Id',
						field : 'id',
						align : 'left',
						width : 10,
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
						m_policegroup_Id = groupId;
						PolicegroupManage.loadMemberData(groupId);
					}
				}); 
				$("#dtPoliceGroup .k-grid-content").mCustomScrollbar( {scrollButtons:{enable:true},advanced:{ updateOnContentResize: true } });
               									var pg = pagination(pageNo,total,'loadData',10);
               						 
               	                				$("#page").html(pg);
               	                				PolicegroupManage.initMemberGrid();
					}else{
						$("#dtPoliceGroup").kendoGrid({
					dataSource: PolicegroupManage.policeGroupDataSource,
					pageable: true,
					columns : [ {
						title : 'Id',
						field : 'id',
						align : 'left',
						width : 10,
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
						m_policegroup_Id = groupId;
						PolicegroupManage.loadMemberData(groupId);
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
			title : "新建警员分组",
			position : {
				top : "100px"
			},
		content: "<%=basePath%>dutyGroupRouteWeb/gotoPoliceGroupAdd.do?organId=" + organId+"&sessionId="+sessionId,
					iframe : true,
					closeCallback :PolicegroupManage.onCloseGroup,
					okCallback:PolicegroupManage.onCloseGroup
				});
	},
	editGroup:function(){
		var organId = $("#organId").val();
		$("#dialog").tyWindow({
			width : "680px",
			height : "500px",
			title : "警员信息",
			position : {
				top : "100px"
			},
		content: "<%=basePath%>dutyGroupRouteWeb/gotoPoliceGroupEdit.do?groupId="
							+ m_policegroup_Id + "&organId=" + organId+"&sessionId="+sessionId,
					iframe : true,
					closeCallback :PolicegroupManage.onCloseGroup,
					okCallback:PolicegroupManage.onCloseGroup
				});
	},
	deleteGroup:function(){
			var kGrid = $("#dtPoliceGroup").data("kendoGrid");
			var row = kGrid.dataItem(kGrid.select());
			if (row != null) {
			$("body").tyWindow({"content":"确定要删除?","center":true,"ok":true,"no":true,"okCallback":function(){ 
				$.ajax({
					url : "<%=basePath%>policeGroupWeb/deletePoliceGroup.do?sessionId="+sessionId,
					type : "POST",
					dataType : "json",
					data : {
						"policeGroupId" : row.id
					},
					async : false,
					success : function(req) {
						if (req.code == 200) { 
							$("body").popjs({"title":"提示","content":"删除成功"}); 
							PolicegroupManage.onCloseGroup();
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
					url: "<%=basePath%>policeGroupWeb/loadMemberByGroupId.do?groupId="+groupId,
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
														title : '姓名',
														field : 'name'
													}, {
														title : '警号',
														field : 'number'
													}, {
														title : '职务',
														field : 'title'
													} ],
											selectable: "row"
										});
									}else{
										
               	                				PolicegroupManage.initMemberGrid();
									}
								}
							});
						} ,
	addMember:function(){
		var organId = $("#organId").val();
		var kGrid = $("#dtPoliceGroup").data("kendoGrid");
			var row = kGrid.dataItem(kGrid.select());
			if (row != null) {
			var groupId = row.id;
		$("#dialog").tyWindow({
			width : "680px",
			height : "500px",
			title : "警员分组成员信息",
			position : {
				top : "100px"
			},
		content: "<%=basePath%>dutyGroupRouteWeb/gotoPoliceGroupAddMember.do?groupId="
							+ groupId + "&organId=" + organId+"&sessionId="+sessionId,
					iframe : true,
					closeCallback :PolicegroupManage.onCloseMember,
					okCallback:PolicegroupManage.onCloseMember
				});
				} else {
				$("body").popjs({"title":"提示","content":"请选择要操作的分组对象"}); 
			}
	},
	deleteMember:function(){
		var kGrid = $("#dtGroupMember").data("kendoGrid");
		var row = kGrid.dataItem(kGrid.select());
		var kgroupGrid = $("#dtPoliceGroup").data("kendoGrid");
		var grouprow = kgroupGrid.dataItem(kgroupGrid.select());
		var groupId = grouprow.id;
		if (row != null) {
		$("body").tyWindow({"content":"确定要删除该成员?","center":true,"ok":true,"no":true,"okCallback":function(){ 
			$.ajax({
				url : "<%=basePath%>policeGroupWeb/delMemberById.do?sessionId="+sessionId,
				type : "POST",
				dataType : "json",
				data : {
					'memberId' : row.id
				},
				async : false,
				success : function(req) {
					if (req.code == 200) { 
									$("body").popjs({"title":"提示","content":"删除成功"});    
									PolicegroupManage.loadMemberData(groupId);
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
		var kGrid = $("#dtPoliceGroup").data("kendoGrid");
			var row = kGrid.dataItem(kGrid.select());
			if (row != null) {
			var groupId = row.id;
			$("body").tyWindow({"content":"确定要清空当前分组成员列表?","center":true,"ok":true,"no":true,"okCallback":function(){ 
						$.ajax({
							url : "<%=basePath%>policeGroupWeb/cleanMemberByGroupId.do?sessionId="+sessionId,
							type : "POST",
							dataType : "json",
							data : {
								"policeGroupId" : row.id
							},
							async : false,
							success : function(req) {
								if (req.code == 200) { 
									$("body").popjs({"title":"提示","content":"清除成功"});   
									$("#dtGroupMember").empty();
									PolicegroupManage.loadMemberData(groupId);
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
		PolicegroupManage.loadMemberData(m_policegroup_Id);  
	},
	onCloseGroup:function(e){
		PolicegroupManage.loadGroupData(PolicegroupManage.pageNo);  
	}
};

</script>
<div class="span5">
<div id="dtPoliceGroup"></div>
<div id="page"></div>
</div>
<div id="dtGroupMember" class="span5"></div>
<div id="dialog"></div> 

