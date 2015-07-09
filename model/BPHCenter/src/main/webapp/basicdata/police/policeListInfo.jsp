<%@ page language="java" pageEncoding="UTF-8"%>

<script type="text/javascript">
var bph_police_query ={};
var sessionId = $("#token").val(); 
$(function() {
	$("#policegrid").empty();
	loadData(1);
	$("#policeName").keydown(function(){
        	if(event.keyCode == 13){
           		 PoliceManage.search();
            		$("#policeName").focus();
		}
    	});
});
function loadData(pageNo){ 
	PoliceManage.police_pageNo = pageNo;
	PoliceManage.packageQuery(pageNo);
	PoliceManage.loadData(pageNo); 
}
var PoliceManage = {
 	police_pageNo:1,
	packageQuery : function(pageNo) {
		bph_police_query.isSubOrg = $("#organLevel").val();
		bph_police_query.name = $.trim($("#policeName").val());
		bph_police_query.orgId = $("#organId").val();
		bph_police_query.orgPath = $("#organPath").val(); 
		bph_police_query.pageSize = $("#pageSize").val();
		bph_police_query.pageStart = pageNo;
	},
	loadData : function(pageNo) {
		$.ajax({
					url : "<%=basePath%>policeWeb/getPoliceList.do?sessionId="+sessionId,
					type : "post",
					data : {
						"police_Query" : JSON.stringify(bph_police_query),
						"expandeds"		:expandeds,
						"organId":$("#organId").val(),
						"organPath":$("#organPath").val(),
						"selectName":$("#selectName").val()
					},
					dataType : "json",
					success : function(req) {
						if (req.code == 200) {
							if (req.data != null) {
								var udata = req.data;
               						var total = req.totalRows;
               						$("#gridListTotal").html(total+"人");
               						$("#policegrid").empty();
								$("#policegrid")
										.kendoGrid(
												{
													dataSource : {
														data : udata 
													},
													height : 472,
													sortable : true,
													resizable: true,
													selectable : "multiple", 
													columns : [
															{
																title : 'Id',
																field : 'id',
																width : 10
															},
															{
																title : '状态',
																template: "<img width='25px' onclick='PoliceManage.changePoliceStates(#: id #,#: isused #)' height='25px' src='<%=basePath %>images/images/#: isused #.png'> "
             								 
															},
															{
																field : "操作",
																template : "<button type='button' class='ty-edit-btn' id='#: id #' title='修改' onclick='PoliceManage.editUser(#: id #)'>修改</button> " 
																		+ "<button type='button' class='ty-delete-btn' id='#: id #' title='删除' onclick='PoliceManage.deleteUser(#: id #)'>删除</button> ",
																width : 120
															},
															{
																title : '职务',
																field : 'titleName'
															},
															{
																title : '姓名',
																field : 'name'
															},
															{
																title : '警号',
																field : 'number'
															},
															{
																title : 'GPS显示名称',
																field : 'gpsName'
															},
															{
																title : '手机号码',
																field : 'mobile'
															},
															{
																title : '公安短号',
																field : 'mobileShort'
															},
															{
																title : '身份证号码',
																field : 'idcardno'
															},
															{
																title : '机构简称',
																field : 'orgName'
															},
															{
																title : '警员类别',
																field : 'typeName'
															},
															{
																title : '组呼号',
																field : 'intercomGroupNumber'
															},
															{
																title : '个呼号',
																field : 'intercomPerson'
															} ],
													change : function(e) {
														var userId = e.sender.selectable.userEvents.currentTarget.cells[0].innerHTML;
													}
												}); 
												var myGrid = $("#policegrid").data("kendoGrid");
				           						myGrid.element.on("dblclick","tbody>tr","dblclick",function(e){
				           							var id = $(this).find("td").first().text();
				           							PoliceManage.editUser(id);
				           						}); 
               									var pg = pagination(pageNo,total,'loadData',10);
               						 
               	                				$("#page").html(pg);
						} 
					} 
				}
			});
	},
	deleteUser:function(userId){
		$("body").tyWindow({"content":"确定要删除?","center":true,"ok":true,"no":true,"okCallback":function(){ 
	 	$.ajax({
					url : "<%=basePath%>policeWeb/deletePoliceById.do?policeId="+userId+"&sessionId="+sessionId,
					type : "post", 
					dataType : "json",
					success : function(req) {
						if(req.code==200){
							PoliceManage.loadData(PoliceManage.police_pageNo); 
						}else{ 
							$("body").popjs({"title":"提示","content":req.description}); 
						}
					}
			});
		}});	
	},
	search:function(){ 
		var policeName = $("#policeName").val();
		if(policeName.length>0)
		{
			var myReg = /^[^@\/\'\\\"#$%&\^\*]+$/;
			if(!myReg.test(policeName)){
				$("body").popjs({"title":"提示","content":"查询条件不能包含非法字符"}); 
				return;
			}
		}
		PoliceManage.packageQuery(1);
		PoliceManage.loadData(1);
	},
	addPolice:function(){
		var organId = $("#organId").val();
		$("#dialog").tyWindow({
			width : "680px",
			height : "510px",
			title : "新增警员信息",
			position : {
				top : "100px"
			},
		content: "<%=basePath%>policeWeb/gotoPoliceAdd.do?organId=" + organId+"&sessionId="+sessionId,
					iframe : true,
					closeCallback : PoliceManage.onClose,
					okCallback:PoliceManage.onClose
				});
		//$("#dialog").data("kendoWindow").open();
	},
	editUser:function(userId){
		var organId = $("#organId").val();
		$("#dialog").tyWindow({
			width : "680px",
			height : "510px",
			title : "编辑警员信息",
			position : {
				top : "100px"
			},
		content: "<%=basePath%>policeWeb/gotoPoliceEdit.do?userId="
							+ userId + "&organId=" + organId+"&sessionId="+sessionId,
					iframe : true,
					closeCallback : PoliceManage.onClose,
					okCallback:PoliceManage.onClose
				});
		//$("#dialog").data("kendoWindow").open();
	},
	onClose:function(e){
		PoliceManage.loadData(PoliceManage.police_pageNo);  
	},
	changePoliceStates:function(pid,states){
		var state = 0;
		if(!states){
		 	state = 1;
		} 
		$.ajax({
					url : "<%=basePath%>policeWeb/changePoliceStates.do?policeId="+pid+"&state="+state+"&sessionId="+sessionId,
					type : "post", 
					dataType : "json",
					success : function(req) {
						if(req.code==200){ 
							PoliceManage.loadData(PoliceManage.police_pageNo); 
						} 
					}
			});	
	},
	importPolice:function(){
		var organId = $("#organId").val();
		$("#dialog").tyWindow({
			width : "730px",
			height : "530px",
			title : "警员信息导入",
			position : {
				top : "80px"
			},
		content: "<%=basePath%>policeWeb/gotoPoliceImport.do?organId=" + organId+"&sessionId="+sessionId,
					iframe : true,
					closeCallback : PoliceManage.onClose
				});
		//$("#dialog").data("kendoWindow").open();
	},
	outPortPolice:function(){
		PoliceManage.packageQuery();
		$.ajax({
					url : "<%=basePath%>excelOutWeb/exportPoliceDataToExcle.do?sessionId="+sessionId,
					type : "post",
					data : {
						"police_Query" : JSON.stringify(bph_police_query)
					},
					dataType : "json",
					success : function(req) {
						if (req.code == 200) {
							var urlStr = "<%=basePath %>"+req.description; 
							window.location.href = urlStr;
						}else{
							$("body").popjs({"title":"提示","content":req.description}); 
						}
					}
				});
	}
};

</script>
<div id="policegrid" style="width:150%"></div>  
<div id="page"></div>
<div id="dialog"></div> 

