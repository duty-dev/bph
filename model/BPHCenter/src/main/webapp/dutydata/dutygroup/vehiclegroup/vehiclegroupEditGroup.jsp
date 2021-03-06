<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
<head>
<base href="<%=basePath%>">

<title>扁平化客户端</title>
<%@ include file="../../../emulateIE.jsp" %>


<script type="text/javascript"> 
var sessionId = $("#token").val(); 
var m_group_Id = 0;
var bph_Exist_OrgName; 
var m_checkedNodes_id ="";
var m_vehicleGroup_Org = {
	id : $("#organId").val(),
	orgCode : "",
	orgPath : $("#organPath").val(),
	groupId : m_group_Id,
	sourceType:"Vehicle"
}; 
	//获取树的信息
	$(function() {
		m_group_Id =  $("#txtVehicleGroupId").val();
		m_vehicleGroup_Org.groupId = m_group_Id;
		//获取树的信息
		$.ajax({
			url : "<%=basePath%>organWeb/treelist.do?sessionId="+sessionId,
			type : "POST",
			dataType : "json",
			data : m_vehicleGroup_Org,
			// async : false,
			success : function(req) {
				if (req.code==200) { 
					var json_data = JSON.stringify(req.data);
					
					$("#treeOrg").kendoTreeView({ 
					    checkboxes: true,
					    dataTextField: "shortName",
					    check : VehicleGroupManage.onCheck,//check复选框
					    dataSource: [eval('(' + json_data + ')')]
					}).data("kendoTreeView");
				} else {
					alert("提示, "+req.msg+"", "warning");
				}
			}
		});
		var isshared = $("#txtIsShared").val();
		if(isshared==0||isshared=="0"){
			$("#radio_unshared").attr("checked","checked");
				$("#divOrg").css("visibility", "hidden");
		}else{
			$("#radio_shared").attr("checked","checked");
				$("#divOrg").css("visibility", "visible");
		}
		$("#divOrg").mCustomScrollbar({scrollButtons:{enable:true},advanced:{ updateOnContentResize: true } });
	}); 

var VehicleGroupManage = { 
		//单选是否共享事件
		changeShareType : function() {
			var val = $('input:radio[name="shareType"]:checked').val();

			if (val == 0) {
				$("#divOrg").css("visibility", "hidden");
				//cleanShareOrgs();
			} else {
				$("#divOrg").css("visibility", "visible");
			}
		},
		onCheck : function(e) {
			var checkedNodes = [], treeView = $("#treeOrg").data(
					"kendoTreeView"), message;

			//treeToJson(treeView.dataSource.view());

			VehicleGroupManage.checkedNodeIds(treeView.dataSource.view(),
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
					VehicleGroupManage.checkedNodeIds(nodes[i].children.view(),
							checkedNodes);
				}
			}
		},
		//保存
		saveVehicleGroup : function() {
			var pg = {};
			pg.shareOrgIds = [];

			pg.orgId = m_vehicleGroup_Org.id;
			pg.id = $("#txtVehicleGroupId").val();
			if (pg.id == undefined || pg.id == "") {
				pg.id = 0;
			}
			// pg.name = $('#txtVehicleGroupName').val();
			var groupName = $.trim($("#txtVehicleGroupName").val());
			if (groupName == "" || groupName == undefined) {
					$("body").popjs({"title":"提示","content":"请填写分组名称","callback":function(){
								$("#txtVehicleGroupName").focus();
								return;
							}});     
					return;
			}

				if (groupName.length > 20) {
					$("body").popjs({"title":"提示","content":"分组名称长度过长","callback":function(){
								$("#txtVehicleGroupName").focus();
								return;
							}});      
						return;  
				} 
			pg.name = groupName;

			pg.shareType = $('input:radio[name="shareType"]:checked').val();
 
			if(pg.shareType ==0||pg.shareType=="0"){
					var shOrgId =  m_vehicleGroup_Org.id;
					pg.shareOrgIds.push(shOrgId);
			}else{
 				var selectIds ="";
 				var  selectNode = $("#treeOrg").data("kendoTreeView");
 				var checkedNodes = [];
 				VehicleGroupManage.checkedNodeIds(selectNode.dataSource.view(),
						checkedNodes);
				if (checkedNodes.length > 0) {
					selectIds = checkedNodes.join(","); 
				} else {
					selectIds = "";
				}		
				if (selectIds.length > 0) {
					pg.shareOrgIds = selectIds.split(",");
				}		
			}
			$.ajax({
					url : "<%=basePath%>vehicleGroupWeb/saveVehicleGroup.do?sessionId="+sessionId,
					type : "POST",
					dataType : "json",
					data : {
						'vehicleGroup' : JSON.stringify(pg)
					},
					async : false,
					success : function(req) {
						if (req.code == 200) { 
							 $("body").popjs({"title":"提示","content":"分组信息保存成功","callback":function(){ 
								window.parent.window.parent.VehicleGroupManage.onCloseGorup();
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
				var name =  $.trim($("#txtVehicleGroupName").val());
				var myReg = /^[^|"'<>]*$/;
				if (!myReg.test(name)) {
					$("body").popjs({"title":"提示","content":"分组名称包含特殊字符，请重新输入"}); 
					$("#txtVehicleGroupName").focus();
					return;
				}  
				if(name.length>0){
					$.ajax({
						url : "<%=basePath%>vehicleGroupWeb/isExistGroup.do?sessionId="+sessionId,
						type : "POST",
						dataType : "json",
						async : false,
						data : {
							"name" : name,
							"orgId" : $("#organId").val(),
							"groupId" :$("#txtVehicleGroupId").val(),
							"optType":1
						},
						success : function(req) {
							if (req.code!=200) {  
									bph_Exist_OrgName = req.description;
									$("body").popjs({"title":"提示","content":"该分组名称当前机构下已存在，请确认后添加","callback":function(){
									$("#txtVehicleGroupName").focus(); 
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
		<div id="winPG" style="width:622px;height:320px;" title="车辆分组管理">
			<div style="float:left;width:250px;margin-top:10px;">
				<!-- 左开始 -->
				<div class="demo-section k-header"> 
					<input type="hidden" id="txtVehicleGroupId" value="${vehiclegroup.id}"></input>
					<input type="hidden" id="txtIsShared" value="${vehiclegroup.shareType}"></input>
					<ul>
						<li class="ty-input fit"><label class="ty-input-label" for="txtVehicleGroupName">组名称:</label><input
							type="text" class="k-textbox" name="txtVehicleGroupName" value="${vehiclegroup.name}"  onblur="VehicleGroupManage.isExistGroup();"
							id="txtVehicleGroupName" /></li>
						<li class="ty-input"><label>共享类型:</label>
							<label><input id="radio_unshared" type="radio" name="shareType" value="0"
							onclick="VehicleGroupManage.changeShareType()" />不共享</label>
							<label><input id="radio_shared" type="radio" name="shareType" value="1"
							onclick="VehicleGroupManage.changeShareType()"  />共享到下级</label>
							</li> 
					</ul>
					<p style="float:left;width:250px;margin-top:10px;">
						<button class="ty-button"  onclick="VehicleGroupManage.saveVehicleGroup()">确定</button>
					</p>
				</div>
			</div>
							<div style="width:370px; float:left">
								<div id="divOrg" style="height:320px; overflow:auto" class="ty-tree-bg ty-tree-te">
									<ul id="treeOrg" style="overflow:auto"></ul>
								</div>
							</div> 
	</div>
  </body>
</html>
