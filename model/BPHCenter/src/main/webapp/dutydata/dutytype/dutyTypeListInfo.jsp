<%@ page language="java" pageEncoding="UTF-8"%>

<script type="text/javascript"> 
var sessionId = $("#token").val(); 
var m_parentNode_pkg={};
var m_
$(function() {
	$("#dutyTypeTreeList").empty();
	loadData(1);
});
function loadData(pageNo){  
	DutyTyepManage.loadData(); 
}
var m_dutyTypeObj = {};
var DutyTyepManage = { 
	loadData : function() {
		$.ajax({
			url : "<%=basePath%>dutyTypeWeb/getDutyTypelist.do",
			type : "POST",
			dataType : "json",
			success : function(req) {
				if(req.code == 200){
					if(req.data != null){
						$.each(req.data, function(index, value) {
								if(value.parentId==0)
									value.parentId=null;
								});
							var ds = new kendo.data.TreeListDataSource({
								data:req.data,
								schema: { 
		                            model: {
		                                id: "id",
		                                expanded: true
		                            }
		                        }
							});
	$("#dutyTypeTreeList").html("");			 				 	
    $("#dutyTypeTreeList").kendoTreeList({
						 dataSource: ds,
						 
							columns : [{
								field : "name",
								expandable: true,
								title : "名称",
								width : 150
							} , {
								title : "人数上限",
								field : "maxPolice"
							}, {
								field : "isShowname",
								title : "统计显示方式"

							}, {
								field : "properties",
								title : "属性"
							}, {
								field : "assoTaskType",
								title : "关联任务"
							}, {
								field : "attireType",
								title : "着装"
							}, {
								field : "armamentType",
								title : "武器"
							}, {
								field : "isUsed",
								title : "停用"
							}],
							selectable: "row" 
						});
					} else{
					$("#dutyTypeTreeList").kendoTreeList({
						 dataSource: [],
						 
							columns : [{
								field : "name",
								expandable: true,
								title : "名称",
								width : 150
							} , {
								title : "人数上限",
								field : "maxPolice"
							}, {
								field : "isShowname",
								title : "统计显示方式"

							}, {
								field : "properties",
								title : "属性"
							}, {
								field : "assoTaskType",
								title : "关联任务"
							}, {
								field : "attireType",
								title : "着装"
							}, {
								field : "armamentType",
								title : "武器"
							}, {
								field : "isUsed",
								title : "停用"
							}],
							selectable: "row" 
						});
						}
				}
			}
		});
	}, 
	editDutyType:function(dId){
		var dutyTypeTreeList=$("#dutyTypeTreeList").data("kendoTreeList");
		var tr=dutyTypeTreeList.select();
		var row= dutyTypeTreeList.dataItem(tr); 
		if(row!=null){  
		 	m_dutyTypeObj.isLeaf = row.isLeaf; 
		 	m_dutyTypeObj.id= row.id;
		 	m_dutyTypeObj.parentId = row.parentId;
		 	m_dutyTypeObj.parentName = row.parentName;
		 	m_dutyTypeObj.parentFullPath = row.parentFullPath;
		 	m_dutyTypeObj.name = row.name;
		 	m_dutyTypeObj.maxPolice = row.maxPolice;
		 	m_dutyTypeObj.isShowname = row.isShowname; 
			m_dutyTypeObj.properties =  row.properties;
			m_dutyTypeObj.attireType = row.attireType;
			m_dutyTypeObj.armamentType = row.armamentType;
			m_dutyTypeObj.isUsed = row.isUsed;
			m_dutyTypeObj.assoTaskType = row.assoTaskType;  
			$("#dialog").tyWindow({
			width : "680px",
			height : "500px",
			title : "编辑勤务类型",
			position : {
				top : "100px"
			},
		content: "<%=basePath%>dutyTypeWeb/gotoDutyTypeEdit.do?sessionId="+sessionId,
					iframe : true, 
					closeCallback :DutyTyepManage.onClose,
					okCallback:DutyTyepManage.onClose
				});
		 }else{
		 	$("body").popjs({"title":"提示","content":"请选择要操作的数据！"});
		 }
	},
	deleteDutyType:function(dId){
		var dutyTypeTreeList=$("#dutyTypeTreeList").data("kendoTreeList");
		var tr=dutyTypeTreeList.select();
		var row= dutyTypeTreeList.dataItem(tr); 
		if(row!=null){
		if(!row.isLeaf){
			 $("body").popjs({"title":"提示","content":"只能删除末级节点！"});
			 return;
		} else{
		
		$("body").tyWindow({"content":"确认删除该报备类型?","center":true,"ok":true,"no":true,"okCallback":function(){
		 		$.ajax({
					url : "<%=basePath%>dutyTypeWeb/deleteDutyType.do?sessionId="+sessionId,
					type : "POST",
					dataType : "json",
					data : {
						"id" : row.id,
						"parentid" : row.parentId
					},
					async : false,
					success : function(req) {
						if (req.code == 200) { 
							$("body").popjs({"title":"提示","content":"删除成功"});  
							$("#dutyTypeTreeList").empty();
							DutyTyepManage.loadData(); 
						} else {
							$("body").popjs({"title":"提示","content":"删除失败，该勤务类型，可能存在关联数据！"}); 
						}
					}
					});
			 }
		});
		}
		}else{
			$("body").popjs({"title":"提示","content":"请选择要操作的数据！"});
		}
		
	},
	addParentNode:function(){  
			m_parentNode_pkg.parentName = "";
			m_parentNode_pkg.parentFullPath = ""; 
			m_parentNode_pkg.parentId = 0;  
		$("#dialog").tyWindow({
			width : "680px",
			height : "500px",
			title : "新建根节点",
			position : {
				top : "100px"
			},
		content: "<%=basePath%>dutyTypeWeb/gotoDutyTypeAdd.do?sessionId="+sessionId,
					iframe : true, 
					closeCallback :DutyTyepManage.onClose,
					okCallback:DutyTyepManage.onClose
				});
	},
	addChildNode:function(){ 
	var dutyTypeTreeList=$("#dutyTypeTreeList").data("kendoTreeList");
		var tr=dutyTypeTreeList.select();
		var row= dutyTypeTreeList.dataItem(tr); 
		if(row!=null){
			m_parentNode_pkg.parentName = row.name;
			m_parentNode_pkg.parentFullPath = row.fullpath; 
			m_parentNode_pkg.parentId = row.id; 
		$("#dialog").tyWindow({
			width : "680px",
			height : "500px",
			title : "新建子节点",
			position : {
				top : "100px"
			},
		content: "<%=basePath%>dutyTypeWeb/gotoDutyTypeAdd.do?sessionId="+sessionId,
					iframe : true, 
					closeCallback :DutyTyepManage.onClose,
					okCallback:DutyTyepManage.onClose
				});
				}else{
					$("body").popjs({"title":"提示","content":"请选择节点作为父节点！"});
				}
	}, 
	unLockNode:function(){
		var dutyTypeTreeList=$("#dutyTypeTreeList").data("kendoTreeList");
		var tr=dutyTypeTreeList.select();
		var row= dutyTypeTreeList.dataItem(tr); 
		if(row!=null){
		if(row.isUsed=="启用"){
			$("body").popjs({"title":"提示","content":"该勤务类型状态已是启用状态！"});
			return;
		} else{
			DutyTyepManage.changeDutyTypeStates(row.id,"true");
		}
		}else{
			$("body").popjs({"title":"提示","content":"请选择要操作的数据！"});
		}
	},
	lockNode:function(){
		var dutyTypeTreeList=$("#dutyTypeTreeList").data("kendoTreeList");
		var tr=dutyTypeTreeList.select();
		var row= dutyTypeTreeList.dataItem(tr); 
		if(row!=null){
		if(row.isUsed=="锁定"){
			$("body").popjs({"title":"提示","content":"该勤务类型状态已是锁定状态！"});
			return;
		} else{ 
			if(!row.isLeaf){
				$("body").tyWindow({"content":"确认锁定该报备类型及下级报备类型?","center":true,"ok":true,"no":true,"okCallback":function(){
						DutyTyepManage.changeDutyTypeStates(row.id,"false");
					}
				});
			}else{
				DutyTyepManage.changeDutyTypeStates(row.id,"false");
			} 
		}
		}else{
			$("body").popjs({"title":"提示","content":"请选择要操作的数据！"});
		}
	},
	changeDutyTypeStates:function(dutytypeId,isUsed){
		$.ajax({
					url : "<%=basePath%>dutyTypeWeb/changeDutyTypeUseState.do?sessionId="+sessionId,
					type : "POST",
					dataType : "json",
					data : {
						"id" : dutytypeId,
						"isUsed" : isUsed
					},
					async : false,
					success : function(req) {
						if (req.code == 200) { 
							$("body").popjs({"title":"提示","content":"状态修改成功"});  
							$("#dutyTypeTreeList").empty();
							DutyTyepManage.loadData(); 
						} else {
							$("body").popjs({"title":"提示","content":"状态修改失败"}); 
						}
					}
					});
	},
	onClose:function(e){   
		$("#dutyTypeTreeList").empty();
		DutyTyepManage.loadData();  
	}
};

</script> 
<div id="dutyTypeTreeList" style="width:150%;"></div>    
<div id="dialog"></div> 

