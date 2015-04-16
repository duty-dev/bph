<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'gpsgroupAddGroup.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

<script type="text/javascript">
$(function() {
	$.ajax({
			url : "orgTest/treelist.do",
			type : "POST",
			dataType : "json",
			data : {
				orgId : m_weaponGroup_Org.id,
				orgCode : m_weaponGroup_Org.code,
				orgPath : m_weaponGroup_Org.path
			},
			// async : false,
			success : function(req) {
				if (req.code==200) {

					var json_data = JSON.stringify(req.data);
					
					$("#treeOrg").kendoTreeView({ 
					    checkboxes: true,
					    check: WeaponGroupManage.onCheck,//check复选框
					    dataTextField: "shortName",
					    dataSource: [eval('(' + json_data + ')')]
					}).data("kendoTreeView");
				} else {
					alert("提示, "+req.msg+", warning");
				}
			}
		});
});
var WeaponGroupManage ={
	//判断是否共享
	changeShareType:function() {
		var val = $('input:radio[name="shareType"]:checked').val();

		if (val == 0) {
			$("#divOrg").css("visibility", "hidden");
		//	cleanShareOrgs();
		} else {
			$("#divOrg").css("visibility", "visible");

		}
	},
	onCheck:function(e) {
		var checkedNodes = [],treeView = $("#treeOrg").data("kendoTreeView"),message;

		   //treeToJson(treeView.dataSource.view());
		   
		   WeaponGroupManage.checkedNodeIds(treeView.dataSource.view(), checkedNodes);

		   if (checkedNodes.length > 0) {
		       message = checkedNodes.join(",");
		       m_checkedNodes_id = message;
		   } else {
		       message = "";
		   }
	},
	checkedNodeIds:function(nodes, checkedNodes) {
		for (var i = 0; i < nodes.length; i++) {
	         if (nodes[i].checked) {
	             checkedNodes.push(nodes[i].id);
	         } 
	         if (nodes[i].hasChildren) {
	        	 WeaponGroupManage.checkedNodeIds(nodes[i].children.view(), checkedNodes);
	         }
	     }
	},
	//保存
	saveWeaponGroup:function() {
		var pg = {};
		pg.shareOrgIds = [];

		pg.orgId = m_weaponGroup_Org.id;
		pg.id = $("#txtWeaponGroupId").val();
		if(pg.id==undefined||pg.id==""){
			pg.id=0;
		}
		var groupName = $.trim($("#txtWeaponGroupName").val());
		if (groupName == "" && groupName == undefined) {
			alert("操作提示, 请填写分组名称", "error");
			$("#txtWeaponGroupName").focus();
			return;
		}

		if(groupName.length>20){
			alert("错误提示, 分组名称长度过长，限制长度1-20！", "error");
			$("#txtWeaponGroupName").focus();
			return;
		}
		
		var myReg = /^[^|"'<>]*$/;
		if(!myReg.test(groupName)){
			alert("错误提示, 分组名称含有非法字符！", "error");
			$("#txtWeaponGroupName").focus();
			return;
		}
		if (opteType == "add") {
			WeaponGroupManage.isExistGroup(groupName, m_weaponGroup_Org.id);
			if (!isExist) {
				alert("错误提示, 该分组名称已存在，请重新填写分组名称", "error");
				$("#txtWeaponGroupName").focus();
				return;
			}
		}
		pg.name = groupName;
		pg.shareType = $('input:radio[name="shareType"]:checked').val();
		var s = m_checkedNodes_id;
		 
		 if(m_checkedNodes_id.length>0){
		 	pg.shareOrgIds = m_checkedNodes_id.split(",");
		 }
		/**
		 * 强制选择根节点！
		 */
		/* var node = $('#treeOrg').tree('find', m_weaponGroup_Org.id);
		$('#treeOrg').tree('check', node.target);

		var nodes = $('#treeOrg').tree('getChecked');
		var count = nodes.length;

		for ( var i = 0; i < count; i++) {
			var n = nodes[i];
			pg.shareOrgIds.push(n.id);
		} */

		$.ajax({
			url : "weaponGroupTest/saveWeaponGroup.do",
			type : "POST",
			dataType : "json",
			data : {
				'weaponGroup' : JSON.stringify(pg)
			},
			async : false,
			success : function(req) {
				if (req.isSuccess) {
					/* $('#dtWeaponGroup').datagrid('reload');
					$('#txtWeaponGroupId').val(req.id);// 回写保存后的id
					$('#winPG').window('close'); */
					WeaponGroupManage.bindDtGroup("weaponGroupTest/list.do");
					alert("提示, 保存成功!");
					$("#winPG").data("kendoWindow").close();
				} else {
					alert("提示, "+req.msg+", warning");
				}
			}
		});
	},
	//查询武器组名，用于判断创建时组名是否存在
	isExistGroup:function(name, orgId) {
		isExist = false;
		$.ajax({
			url : "weaponGroupTest/isExistGroup.do",
			type : "POST",
			dataType : "json",
			async : false,
			data : {
				"name" : name,
				"orgId" : orgId
			},
			success : function(req) {
				if (req.isSuccess && req.Message == "UnExits") {
					isExist = true;
				}
			}
		});
	},
};

</script>
  </head>
  
  <body>
		<!-- <div id="vertical" style="overflow-x:hidden;"> -->
		<div id="winPG" style="width:330px;height:320px;" title="武器分组管理">
			<div class="pane-content">
				<!-- 左开始 -->
				<div class="demo-section k-header"> 
					<input type="hidden" id="txtWeaponGroupId"></input>
					<ul>
						<li class="ty-input"><label class="ty-input-label" for="txtWeaponGroupName">组名称:</label><input
							type="text" class="k-textbox" name="txtWeaponGroupName"
							id="txtWeaponGroupName" /></li>
						<li class="ty-input"><label class="ty-input-label" for="shareType">共享类型:</label>
							<label><input type="radio" class="k-textbox" name="shareType" value="0"
							onclick="WeaponGroupManage.changeShareType()" id="radioShare1" />不共享</label>
							<label><input type="radio" class="k-textbox" name="shareType" value="1"
							onclick="WeaponGroupManage.changeShareType()" id="radioShare2" />共享到下级</label>
							</li>
						<li class="ty-input">
							<div class="groupwindowdiv">
								<div id="divOrg">
									<ul id="treeOrg" style="overflow:auto"></ul>
								</div>
							</div>
						</li>
						
					</ul>
					<p style="float:left;width:100%;margin-top:10px;">
						<span class="k-button"  onclick="WeaponGroupManage.saveWeaponGroup()">保存</span>
					</p>
				</div>
			</div>
		<!-- </div> -->
	</div>
  </body>
</html>
