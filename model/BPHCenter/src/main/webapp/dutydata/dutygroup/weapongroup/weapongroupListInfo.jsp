<%@ page language="java" pageEncoding="UTF-8"%>

<script type="text/javascript"> 
var sessionId = $("#token").val(); 
var m_weapongroup_Id;
$(function() {
	$("#dtWeaponGroup").empty();
	loadData(1);
});
function loadData(pageNo){  
	WeapongroupManage.pageNo = pageNo;
	WeapongroupManage.packageGroupData(pageNo);
	WeapongroupManage.loadGroupData(pageNo); 
	
}
var m_weaponGroup_Query = {};

var WeapongroupManage = { 
	pageNo:1,
	packageGroupData:function(pageNo){
		m_weaponGroup_Query.orgId =1;// $("#organId").val();
		m_weaponGroup_Query.orgPath = $("#organPath").val();
		m_weaponGroup_Query.orgCode = $("#organPath").val();
		m_weaponGroup_Query.page = pageNo;
		m_weaponGroup_Query.pageSize = 10; 
	},
	weaponGroupDataSource:[],
	loadGroupData : function(pageNo) {
		$.ajax({
			type: "post",
			url: "<%=basePath%>weaponGroupWeb/list.do",
			dataType: "json",
			data: {
				"weaponGroup_Query": JSON.stringify(m_weaponGroup_Query) 
			},
			success: function(req) {
				if (req.code == 200) { 
					var rows =req.data; 
					WeapongroupManage.weaponGroupDataSource = new kendo.data.DataSource({
						data: rows,
						batch: true,
						pageSize: 20
					});
					
					$("#dtWeaponGroup").kendoGrid({
						dataSource: WeapongroupManage.weaponGroupDataSource,
						pageable: true,
						columns : [ {
							title : 'Id',
							field : 'id',
							align : 'left',
							width : 10,
							hidden : true
						}, {
							title : '组名称',
							field : 'name',
							align : 'left',
							width : 150
						}, {
							title : '共享类型',
							field : 'shareTypeDesc',
							align : 'left',
							width : 200
						} ],
						selectable: "row",
						change : function(e) {
							var groupId = e.sender.selectable.userEvents.currentTarget.cells[0].innerHTML; 
							m_weapongroup_Id = groupId;
							WeapongroupManage.getMemberBygroupId(groupId);
						}
					}); 
				}else{
					
					$("#dtWeaponGroup").kendoGrid({
						dataSource: WeapongroupManage.weaponGroupDataSource,
						pageable: true,
						columns : [ {
							title : 'Id',
							field : 'id',
							align : 'left',
							width : 10,
							hidden : true
						}, {
							title : '组名称',
							field : 'name',
							align : 'left',
							width : 150
						}, {
							title : '共享类型',
							field : 'shareTypeDesc',
							align : 'left',
							width : 200
						} ],
						selectable: "row",
						change : function(e) {
							var groupId = e.sender.selectable.userEvents.currentTarget.cells[0].innerHTML; 
							m_weapongroup_Id = groupId;
							WeapongroupManage.getMemberBygroupId(groupId);
						}
					}); 
				}
			}
		});
		
	},  
	createGroup:function(){
		var organId = $("#organId").val();
		$("#dialog").kendoWindow({
			width : "680px",
			height : "500px",
			title : "新建武器分组信息",
			position : {
				top : "100px"
			},
		content: "<%=basePath%>dutyGroupRouteWeb/gotoWeaponGroupAdd.do?organId=" + organId+"&sessionId="+sessionId,
					iframe : true,
					closeCallback :WeapongroupManage.onCloseGorup,
					okCallback:WeapongroupManage.onCloseGorup
				});
	},
	editGroup:function(){
		var organId = $("#organId").val();
		$("#dialog").kendoWindow({
			width : "680px",
			height : "500px",
			title : "编辑武器分组信息",
			position : {
				top : "100px"
			},
		content: "<%=basePath%>dutyGroupRouteWeb/gotoWeaponGroupEdit.do?groupId="
							+ m_weapongroup_Id + "&organId=" + organId+"&sessionId="+sessionId,
					iframe : true,
					closeCallback :WeapongroupManage.onCloseGorup,
					okCallback:WeapongroupManage.onCloseGorup
				});
	},
	deleteGroup:function(){ 
			var kGrid = $("#dtWeaponGroup").data("kendoGrid");
			var row = kGrid.dataItem(kGrid.select());
			if (row != null) {
			$("body").tyWindow({"content":"确定要删除?","center":true,"ok":true,"no":true,"okCallback":function(){ 
				$.ajax({
					url : "<%=basePath%>weaponGroupWeb/deleteWeaponGroup.do",
					type : "POST",
					dataType : "json",
					data : {
						"weaponGroupId" : row.id
					},
					async : false,
					success : function(req) {
						if (req.code == 200) { 
							$("body").popjs({"title":"提示","content":"删除成功"}); 
							WeaponGroupManage.onCloseGorup()
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
	getMemberBygroupId:function(groupId){
		$.ajax({
			type: "post",
			url: "<%=basePath%>weaponGroupWeb/loadMemberByGroupId.do?groupId="+groupId, 
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
										title : '武器类型',
										field : 'typeName'
									}, {
										title : '武器编号',
										field : 'number'
									}, {
										title : '子弹发数',
										field : 'standard'
									}, ], 
								selectable: "row"
								});
							}else{
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
										title : '武器类型',
										field : 'typeName'
									}, {
										title : '武器编号',
										field : 'number'
									}, {
										title : '子弹发数',
										field : 'standard'
									}, ], 
								selectable: "row"
								});
							}
						}
				});
						} ,
	addMember:function(){
		var organId = $("#organId").val();
		var kGrid = $("#dtWeaponGroup").data("kendoGrid");
			var row = kGrid.dataItem(kGrid.select());
			if (row != null) {
			var groupId = row.id;
		$("#dialog").kendoWindow({
			width : "680px",
			height : "500px",
			title : "分组成员信息",
			position : {
				top : "100px"
			},
		content: "<%=basePath%>dutyGroupRouteWeb/gotoWeaponGroupAddMember.do?groupId="
							+ groupId + "&organId=" + organId+"&sessionId="+sessionId,
					iframe : true,
					closeCallback :WeapongroupManage.onCloseMember,
					okCallback:WeapongroupManage.onCloseMember
				});} else {
				$("body").popjs({"title":"提示","content":"请选择要操作的分组对象"}); 
			}
	},
	deleteMember:function(){
			var kGrid = $("#dtGroupMember").data("kendoGrid");
		var row = kGrid.dataItem(kGrid.select());
		var kgroupGrid = $("#dtWeaponGroup").data("kendoGrid");
		var grouprow = kgroupGrid.dataItem(kgroupGrid.select());
		var groupId = grouprow.id;
		if (row != null) {
		$("body").tyWindow({"content":"确定要删除该成员?","center":true,"ok":true,"no":true,"okCallback":function(){ 
			$.ajax({
				url : "<%=basePath%>weaponGroupWeb/delMemberById.do?sessionId="+sessionId, 
				type : "POST",
				dataType : "json",
				data : {
					'memberId' : row.id
				},
				async : false,
				success : function(req) {
					if (req.code == 200) { 
									$("body").popjs({"title":"提示","content":"删除成功"});    
									WeapongroupManage.getMemberBygroupId(groupId);
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
	var kGrid = $("#dtWeaponGroup").data("kendoGrid");
			var row = kGrid.dataItem(kGrid.select());
			if (row != null) {
			var groupId = row.id;
			$("body").tyWindow({"content":"确定要清空当前分组成员列表?","center":true,"ok":true,"no":true,"okCallback":function(){ 
							$.ajax({ 
								url : "<%=basePath%>weaponGroupWeb/cleanMemberByGroupId.do?sessionId="+sessionId,
								type : "POST",
								dataType : "json",
								data : {
									"weaponGroupId" : row.id
								},
								async : false,
								success : function(req) {
									if (req.code == 200) { 
									$("body").popjs({"title":"提示","content":"清除成功"});   
									$("#dtGroupMember").empty();
									WeapongroupManage.getMemberBygroupId(groupId);
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
		WeapongroupManage.getMemberBygroupId(m_weapongroup_Id);  
	},
	onCloseGorup:function(e){
		WeapongroupManage.loadGroupData(WeapongroupManage.pageNo);  
	}
};

</script>
<div id="dtWeaponGroup" style="width:330px"></div>   
<div id="dtGroupMember" style="width:300px"></div> 
<div id="dialog"></div> 

