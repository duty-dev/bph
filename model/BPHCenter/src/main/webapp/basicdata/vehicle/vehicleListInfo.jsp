<jsp:directive.page language="java" pageEncoding="UTF-8"/>

<script>	
var bph_vehicle_query ={}; 
var sessionId = $("#token").val();
$(function() {
	loadData(1);
	$("#vehicleNumber").keydown(function(){
        if(event.keyCode == 13){
            VehicleManage.search();
            $("#vehicleNumber").focus();
        }
    });
});
function loadData(pageNo){
	VehicleManage.vehicle_pageNo = pageNo;
	VehicleManage.packageQuery(pageNo);
	VehicleManage.loadData(pageNo);
} 
var VehicleManage = {
	vehicle_pageNo:1,
	packageQuery : function(pageNo) {
			bph_vehicle_query.isSubOrg = $("#organLevel").val();
			bph_vehicle_query.number = $.trim($("#vehicleNumber").val());
			bph_vehicle_query.orgId = $("#organId").val();
			bph_vehicle_query.orgPath = $("#organPath").val();
			bph_vehicle_query.pageSize = $("#pageSize").val();
			bph_vehicle_query.pageStart = pageNo;
		},
	loadData : function(pageNo) {
			$.ajax({
					url : "<%=basePath%>vehicleWeb/getVehicleList.do?sessionId="+sessionId,
					type : "post",
					data : {
						"vehicle_Query" : JSON.stringify(bph_vehicle_query),
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
               						$("#gridListTotal").html(total+"台");
               						$("#vehiclegrid").empty();
									$("#vehiclegrid").kendoGrid({
							dataSource : {
								data : udata 
							},
							height : 472,
							sortable : true,
							resizable: true,
							selectable : "multiple", 
							columns:[{
									title : 'Id',
									field : 'id', 
									width : 10 
								},{
             							field: "操作",
             							template: "<button type='button' class='ty-edit-btn' id='#: id #' title='修改' onclick='VehicleManage.editVehicle(#: id #)'>修改</button> "+
             							"<button type='button' class='ty-delete-btn' id='#: id #' title='删除' onclick='VehicleManage.deleteVehicle(#: id #)'>删除</button> ",
             							width:120
             					},{
									title : '车辆类型',
									field : 'typeName' 
								},{
									title : '车辆所属组织机构',
									field : 'orgId' ,
									hidden: true
								},{
									title : '车牌号码',
									field : 'number'  
								},{
									title : '对讲机组呼号',
									field : 'intercomgroupNumber' 
								},{
									title : '对讲机个呼号',
									field : 'intercomPerson' 
								},{
									title : 'GPS名称',
									field : 'gpsName' 
								},{
									title : '车辆用途',
									field : 'purpose' 
								},{
									title : '车辆品牌',
									field : 'brand' 
								},{
									title : '座位数',
									field : 'siteQty' 
								}
							],
							change: function (e) {
                                var userId = e.sender.selectable.userEvents.currentTarget.cells[0].innerHTML; 
                            }
						});
							var myGrid = $("#vehiclegrid").data("kendoGrid");
            				myGrid.element.on("dblclick","tbody>tr","dblclick",function(e){
             					var id = $(this).find("td").first().text();
             					VehicleManage.editVehicle(id);
             				});
             				var pg = pagination(pageNo,total,'loadData',10);
               	            $("#page").html(pg);
						} 
					} 
				}
			});
	}, 
	deleteVehicle:function(vehicleId){
		$("body").tyWindow({"content":"确定要删除?","center":true,"ok":true,"no":true,"okCallback":function(){ 
	 	$.ajax({
					url : "<%=basePath%>vehicleWeb/deleteVehicleById.do?vehicleId="+vehicleId+"&sessionId="+sessionId,
					type : "post", 
					dataType : "json",
					success : function(req) {
						if(req.code==200){
							VehicleManage.loadData(VehicleManage.vehicle_pageNo); 
						}else{
							$("body").popjs({"title":"提示","content":req.description}); 
						}
					}
			});
		}});	
	},
	search:function(){ 
		var vehiclenumber = $("#vehicleNumber").val();
		if(vehiclenumber.length>0)
		{
			var myReg = /^[^@\/\'\\\"#$%&\^\*]+$/;
			if(!myReg.test(vehiclenumber)){
				$("body").popjs({"title":"提示","content":"查询条件不能包含非法字符"}); 
				return;
			}
		}
		VehicleManage.packageQuery(1);
		VehicleManage.loadData(1);
	},
	addVehicle:function(){
		var organId = $("#organId").val();
		$("#dialog").tyWindow({
			width : "680px",
			height : "500px",
			title : "新增车辆信息",
			position : {
				top : "100px"
			},
		content: "<%=basePath%>vehicleWeb/gotoVehicleAdd.do?organId=" + organId+"&sessionId="+sessionId,
					iframe : true,
					closeCallback : VehicleManage.onClose,
					okCallback:VehicleManage.onClose
				});
		//$("#dialog").data("kendoWindow").open();
	},
	editVehicle:function(vehicleId){
		var organId = $("#organId").val();
		$("#dialog").tyWindow({
			width : "680px",
			height : "500px",
			title : "编辑车辆信息",
			position : {
				top : "100px"
			},
		content: "<%=basePath%>vehicleWeb/gotoVehicleEdit.do?vehicleId="
							+ vehicleId + "&organId=" + organId+"&sessionId="+sessionId,
					iframe : true,
					closeCallback : VehicleManage.onClose,
					okCallback:VehicleManage.onClose
				});
		//$("#dialog").data("kendoWindow").open();
	},
	onClose:function(e){
		VehicleManage.loadData(VehicleManage.vehicle_pageNo);  
	},
	importVehicle:function(){
		var organId = $("#organId").val();
		$("#dialog").tyWindow({
			width : "500px",
			height : "450px",
			title : "车辆信息导入",
			position : {
				top : "100px"
			},
		content: "<%=basePath%>vehicleWeb/gotoVehicleImport.do?organId=" + organId+"&sessionId="+sessionId,
					iframe : true,
					closeCallback : VehicleManage.onClose
				});
		//$("#dialog").data("kendoWindow").open();
	},
	outPortVehicle:function(){
		VehicleManage.packageQuery();
		$.ajax({
					url : "<%=basePath%>excelOutWeb/exportVehicleDataToExcle.do?sessionId="+sessionId,
					type : "post",
					data : {
						"vehicle_Query" : JSON.stringify(bph_vehicle_query)
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

<div id="vehiclegrid" style="width:150%"></div>
<div id="page"></div>
<div id="dialog"></div>

