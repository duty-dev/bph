<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>扁平化客户端</title>
<%@ include file="../../../emulateIE.jsp" %>

<script type="text/javascript">
var sessionId = $("#token").val(); 
var bph_Exist_OrgName; 
var m_checkedNodes_id ="" ;
var m_policeGroup_Org = {
	id:$("#organId").val(),
	orgCode: "",
	orgPath :$("#organPath").val(),
	groupId : 0,
	sourceType:"Police"
};
	$(function() {
		//获取树的所有信息
		$.ajax({
			url : "<%=basePath%>organWeb/treelist.do?sessionId="+sessionId,
			type : "POST",
			dataType : "json",
			data : m_policeGroup_Org,
			// async : false,
			success : function(req) {
				if (req.code==200) {

					var json_data = JSON.stringify(req.data);
					
					$("#treeOrg").kendoTreeView({ 
					    checkboxes: true,
					    dataTextField: "shortName",
					    check : PoliceGroupManage.onCheck,//check复选框
					    dataSource: [eval('(' + json_data + ')')]
					}).data("kendoTreeView");
					
				} else {
					alert("提示, "+req.msg+"", "warning");
				}
			}
		});
		$("#radio_unshared").attr("checked","checked");
		$("#divOrg").css("visibility", "hidden");
	});
	
	var PoliceGroupManage = {
			//是否共享（单选）事件
			changeShareType:function() {
				var val = $('input:radio[name="shareType"]:checked').val();

				if (val == 0) {
					$("#divOrg").css("visibility", "hidden");
//					cleanShareOrgs();
				} else {
					$("#divOrg").css("visibility", "visible");
					//PoliceGroupManage.loadOrgs();
				}
			},
			onCheck : function(e) {
				var checkedNodes = [], treeView = $("#treeOrg").data(
						"kendoTreeView"), message;

				//treeToJson(treeView.dataSource.view());

				PoliceGroupManage.checkedNodeIds(treeView.dataSource.view(),
						checkedNodes);

				if (checkedNodes.length > 0) {
					message = checkedNodes.join(",");
					m_checkedNodes_id = message;
				} else {
					message = "";
				}
			},
			checkedNodeIds : function(nodes, checkedNodes) {
				for ( var i = 0; i < nodes.length; i++) {
					if (nodes[i].checked) {
						checkedNodes.push(nodes[i].id);
					}
					if (nodes[i].hasChildren) {
						PoliceGroupManage.checkedNodeIds(nodes[i].children.view(),
								checkedNodes);
					}
				}
			},
		//组保存
		savePoliceGroup : function() {
			var pg = {};
			pg.shareOrgIds = [];

			pg.orgId = m_policeGroup_Org.id;
			pg.id = $("#txtPoliceGroupId").val();
			if (pg.id == undefined || pg.id == "") {
				pg.id = 0;
			}
			// pg.name = $('#txtPoliceGroupName').val();
			var groupName = $.trim($("#txtPoliceGroupName").val());
				if (groupName == "" && groupName == undefined) { 
					$("body").popjs({"title":"提示","content":"请填写分组名称","callback":function(){
								$("#txtPoliceGroupName").focus();
								return;
							}});     
					return;
				}

				if (groupName.length > 20) {
					$("body").popjs({"title":"提示","content":"分组名称长度过长","callback":function(){
								$("#txtPoliceGroupName").focus();
								return;
							}});      
						return;  
				}   
			pg.name = groupName;
			pg.shareType = $('input:radio[name="shareType"]:checked').val();
 
				pg.shareType = $('input:radio[name="shareType"]:checked').val();
				if(pg.shareType==""||pg.shareType ==undefined){
					$("body").popjs({"title":"提示","content":"请选择共享类型"});
					return;
				}
				var s = m_checkedNodes_id;
				if(pg.shareType ==0||pg.shareType=="0"){
					
					var shOrgId =  m_policeGroup_Org.id;
					pg.shareOrgIds.push(shOrgId);
				}else{
					if (m_checkedNodes_id.length > 0) {
						pg.shareOrgIds = m_checkedNodes_id.split(",");
					} 
				}
			$.ajax({
				url : "<%=basePath%>policeGroupWeb/savePoliceGroup.do?sessionId="+sessionId,
				type : "POST",
				dataType : "json",
				data : {
					'policeGroup' : JSON.stringify(pg)
				},
				async : false,
				success : function(req) {
					if (req.code == 200) { 
							 $("body").popjs({"title":"提示","content":"分组信息保存成功","callback":function(){ 
								window.parent.window.parent.PolicegroupManage.onCloseGroup();
								window.parent.$("#dialog").tyWindow.close();
							}}); 
						} else {
							$("body").popjs({"title":"提示","content":"分组信息保存失败"});
						}
				}
			});
		},
			isExistGroup : function() {
				isExist = false;
				var name =  $.trim($("#txtPoliceGroupName").val());
				var myReg = /^[^|"'<>]*$/;
				if (!myReg.test(name)) {
					$("body").popjs({"title":"提示","content":"分组名称包含特殊字符，请重新输入"}); 
					$("#txtPoliceGroupName").focus();
					return;
				}  
				if(name.length>0){
					$.ajax({
						url : "<%=basePath%>policeGroupWeb/isExistGroup.do?sessionId="+sessionId,
						type : "POST",
						dataType : "json",
						async : false,
						data : {
							"name" : name,
							"orgId" : $("#organId").val(),
							"groupId" :0,
							"optType":0
						},
						success : function(req) {
							if (req.code!=200) {  
									bph_Exist_OrgName = req.description;
									$("body").popjs({"title":"提示","content":"该分组名称当前机构下已存在，请确认后添加","callback":function(){
									$("#txtPoliceGroupName").focus(); 
									return;
							}});    
							return;
							
							}
						}
					});
				}
			}
	};
</script>
</head>

<body class="ty-body">
	<!-- <div id="vertical" style="overflow-x:hidden;"> -->
		<div id="winPG" style="width:622px;height:320px;" title="警员分组管理">
			<div style="float:left;width:250px;margin-top:10px;">
				<!-- 左开始 -->
				<div class="demo-section k-header"> 
					<input type="hidden" id="txtPoliceGroupId"></input>
					<ul>
						<li class="ty-input fit"><label class="ty-input-label" for="txtPoliceGroupName">组名称:</label><input
							type="text" class="k-textbox" name="txtPoliceGroupName"  onblur="PoliceGroupManage.isExistGroup();"
							id="txtPoliceGroupName" /></li>
						<li class="ty-input"><label>共享类型:</label>
							<label><input id="radio_unshared" type="radio" name="shareType" value="0"
							onclick="PoliceGroupManage.changeShareType()" id="radioShare1" />不共享</label>
							<label><input id="radio_shared"  type="radio" name="shareType" value="1"
							onclick="PoliceGroupManage.changeShareType()" id="radioShare2" />共享到下级</label>
							</li> 
						
					</ul>
					</div>
					<p style="float:left;width:250px;margin-top:10px;">
						<button class="ty-button"  onclick="PoliceGroupManage.savePoliceGroup()">确定</button>
					</p>
				
			</div> 
			
							<div style="width:370px; float:left">
								<div id="divOrg" style="height:320px; overflow:auto" class="ty-tree-bg">
									<ul id="treeOrg" style="overflow:auto"></ul>
								</div>
							</div> 
	</div>
</body>
</html>
