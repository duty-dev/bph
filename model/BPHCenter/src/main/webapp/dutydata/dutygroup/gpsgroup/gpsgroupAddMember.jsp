<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML>
<html>
<head>
<base href="<%=basePath%>">

<title>扁平化客户端</title>
<%@ include file="../../../emulateIE.jsp" %>
	<script type="text/javascript">
	var sessionId = $("#token").val();
	
	var m_org_id;
	var m_memberDt =[];
	var m_group_id;
	$(function() {
		m_org_id = $("#organId").val();
		m_group_id = $("#txtGpsGroupId").val();
		
		var treeData = new kendo.data.HierarchicalDataSource({
	        transport: {
				read: {
					url : "<%=basePath%>organWeb/listWithGps.do?sessionId="+sessionId +"&rootId=" + m_org_id, 
					dataType: "json"
				}
			},
			schema: {
	 			model: {
					id: "id",
					hasChildren: "isOrg"
	           	}
			}
		});
	
		$("#treeOrgWithGps").kendoTreeView({
			dataSource: treeData,
			dataTextField: "name",
			selectable: "row"
		}).on('dblclick', '.k-in', function (event) { 
     			var rows = $("#treeOrgWithGps").data("kendoTreeView");
				var node = rows.dataItem(rows.select()); 
				GpsGroupManage.selectMemberModel(node);
     			return false;
			}).data("kendoTreeView");
		 
		$.ajax({
					type: "post",
					url: "<%=basePath%>gpsGroupWeb/loadMemberByGroupId.do?sessionId="+sessionId+"&groupId="+m_group_id,
					dataType: "json", 
					success: function(req) {
						if (req.code==200) {
							var pdata = req.data; 
							m_memberDt = pdata;
										var dataSo = new kendo.data.DataSource({
											data: pdata
										});
										$("#dtSelGroupMember").kendoGrid({
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
													},{
														title : '设备名称',
														field : 'gpsName'
													} ],
											selectable: "row"
										});
										
										var myGrid = $("#dtSelGroupMember").data("kendoGrid");
				           						myGrid.element.on("dblclick","tbody>tr","dblclick",function(e){ 
				           							GpsGroupManage.unselectMember();
				           						}); 
									}else{ 
										$("#dtSelGroupMember").kendoGrid({
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
													} ],
											selectable: "row"
										});
									}
								}
							});
		$("#orgWithGps").mCustomScrollbar({scrollButtons:{enable:true},advanced:{ updateOnContentResize: true } });
		$("#dtSelGroupMember").parent().mCustomScrollbar({scrollButtons:{enable:true},advanced:{ updateOnContentResize: true } });
	}); 	
	var GpsGroupManage ={ 
			selectMember:function() {
				var rows = $("#treeOrgWithGps").data("kendoTreeView");
				var node = rows.dataItem(rows.select()); 
				GpsGroupManage.selectMemberModel(node);
			}, 
			unselectMember:function() {
				var rows = $("#dtSelGroupMember").data("kendoGrid");
				if(rows._data.length == 0){
					$("body").popjs({"title":"提示","content":"没有可操作的数据源"}); 
					return;
				}
				var row = rows.dataItem(rows.select()); 
				if(row != null){ 
					for (var i = 0; i<m_memberDt.length; i++) {
						 if(row.gpsId==m_memberDt[i].gpsId){ 
							 m_memberDt.splice(i, 1);
						 }
					}
					var dataSource = new kendo.data.DataSource({
  								data: m_memberDt
							});
						//替换以前的dataSource
					var grid = $("#dtSelGroupMember").data("kendoGrid");
					grid.setDataSource(dataSource); 
				}else{
					$("body").popjs({"title":"提示","content":"请选择要删除的成员"}); 
				}
			},
			selectMemberModel:function(node) {
				 
				 if (node != null && node.dataType == 2) {

					var datas = $("#dtSelGroupMember").data("kendoGrid");

					var count = datas._data.length;

					var exists = false;

					for ( var i = 0; i < count; i++) {
						var row = datas._data[i];
						if (row.gpsId == node.rid) {
							exists = true;
							break;
						}
					}

					if (!exists) {
					
						var obj = {};
						obj.id = node.rid;
						obj.groupId = m_group_id;
						var codeStr = "("+node.code+")";
						var typenameStr =  node.typename+"(";
						var numberStr = ":"+node.code+")";
						obj.name = node.name.replace(codeStr,"");
						obj.number = node.code;
						obj.orgName = node.orgName;
						obj.orgShortName =  node.orgName;
						obj.typeName = node.typename;
						var subStr = node.name.replace(typenameStr,"");
						obj.gpsName =subStr.replace(numberStr,"");
						obj.gpsId = node.rid; 
						m_memberDt.push(obj);
						var dataSource = new kendo.data.DataSource({
  							data: m_memberDt
						});
						//替换以前的dataSource
						var grid = $("#dtSelGroupMember").data("kendoGrid");
						grid.setDataSource(dataSource); 
					} 
				} else {
					$("body").popjs({"title":"提示","content":"不能选择组织机构作为组成员"}); 
				}
			},
			//组员保存
			appendMember:function() {
				var members = []; 
				var groupid = m_group_id;
 
				for ( var i = 0; i < m_memberDt.length; i++) {
					var data = m_memberDt[i];
					var member = {};
					member.id = 0;
					member.groupId = groupid;
					member.gpsId = data.gpsId;
					members.push(member);
				}

				$.ajax({
					url : "<%=basePath%>gpsGroupWeb/appendMember.do?sessionId="+sessionId,
					type : "POST",
					dataType : "json",
					data : {
						"members" : JSON.stringify(members)
					},
					async : false,
					success : function(req) {
						if (req.code == 200) { 
							 $("body").popjs({"title":"提示","content":"分组成员信息保存成功","callback":function(){ 
								window.parent.window.parent.GpsgroupManage.onCloseMember();
								window.parent.$("#dialog").tyWindow.close();
							}}); 
						} else {
							$("body").popjs({"title":"提示","content":"分组成员信息保存失败"});
						}
					}
				});
			}
	};
	</script>
	
  </head>
  
  <body class="ty-body">
    <!-- <div id="vertical" style="overflow-x:hidden;"> -->
		<div id="winPG" style="width:700px;float:left;" title="定位设备分组成员管理">
			<div style="width:650px;margin-top:10px;">
				<!-- 左开始 -->
				<div class="demo-section k-header"> 
					<input id="txtGpsGroupId"  type="hidden"  value="${groupId }"></input>
				 
							<div style="width:40%;float:left;height:330px" id="orgWithGps">  
								<ul id="treeOrgWithGps"></ul>
							</div>
					 	    <div  style="width:6%;float:left;height:210px;margin-top:120px">
								<button onclick="GpsGroupManage.selectMember()">&gt&gt</button>
								<button onclick="GpsGroupManage.unselectMember()">&lt&lt</button>
						    </div>
						    <div  style="width:50%;float:left;height:330px;overflow:hidden;"> 
							<div id="dtSelGroupMember"></div>
					 
					</div>
					<p class="ty-input-row">
						<button class="ty-button"  onclick="GpsGroupManage.appendMember()">确定</button>
					</p>
				</div>
			</div> 
	</div>
  </body>
</html>
