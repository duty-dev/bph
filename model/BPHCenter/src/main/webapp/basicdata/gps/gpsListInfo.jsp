<jsp:directive.page language="java" pageEncoding="UTF-8"/>

<script>	
var bph_gps_query ={};

var iconUrl;
var sessionId = $("#token").val();
$(function() {
	loadData(1);	
	$("#gpsNumber").keydown(function(){
        if(event.keyCode == 13){
            GpsManage.search();
            $("#gpsNumber").focus();
        }
    });	
	$("#gpsName").keydown(function(){
        if(event.keyCode == 13){
            GpsManage.search();
            $("#gpsName").focus();
        }
    });
});

function loadData(pageNo){ 
	GpsManage.gps_pageNo= pageNo;
	GpsManage.packageQuery(pageNo);
	GpsManage.loadData(pageNo); 
}
var GpsManage = {
	gps_pageNo:1,
	packageQuery:function(pageNo){
		bph_gps_query.isSubOrg = $("#organLevel").val();
        bph_gps_query.name = $.trim($("#gpsName").val()); 
        bph_gps_query.number = $.trim($("#gpsNumber").val()); 
        bph_gps_query.orgId = $("#organId").val();
        bph_gps_query.orgPath = $("#organPath").val();
        bph_gps_query.pageSize = $("#pageSize").val();
        bph_gps_query.pageStart = pageNo; 
	},
	loadData : function(pageNo) {
		$.ajax({
					url : "<%=basePath%>gpsWeb/getGpsList.do?sessionId="+sessionId,
					type : "post",
					data : {
						"gps_Query" : JSON.stringify(bph_gps_query),
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
               						$("#gpsgrid").empty();
								$("#gpsgrid").kendoGrid({
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
             							template: "<button type='button' class='ty-edit-btn' id='#: id #' title='修改' onclick='GpsManage.editGps(#: id #)'>修改</button> "+
             							"<button type='button' class='ty-delete-btn' id='#: id #' title='删除' onclick='GpsManage.deleteGps(#: id #)'>删除</button> ",
             							width:120
             					},{
									title : '定位设备类型',
									field : 'typeName' 
								},{
									title : '机构',
									field : 'orgId',
									hidden:true 
								},{
									title : 'GPS编号',
									field : 'number'  
								},{
									title : 'GPS名称',
									field : 'gpsName' 
								}
							],
							change: function (e) {
                                var userId = e.sender.selectable.userEvents.currentTarget.cells[0].innerHTML; 
                            }
						});
								var myGrid = $("#gpsgrid").data("kendoGrid");
           						myGrid.element.on("dblclick","tbody>tr","dblclick",function(e){
           							var id = $(this).find("td").first().text();
           							GpsManage.editGps(id);
           						});
             					var pg = pagination(pageNo,total,'loadData',10);
              	                $("#page").html(pg);
						} 
				} 
			}
		});
	},
	deleteGps:function(gpsId){
		$("body").tyWindow({"content":"确定要删除?","center":true,"ok":true,"no":true,"okCallback":function(){
	 	$.ajax({
					url : "<%=basePath%>gpsWeb/deleteGpsById.do?gpsId="+gpsId+"&sessionId="+sessionId,
					type : "post", 
					dataType : "json",
					success : function(req) {
						if(req.code==200){
							GpsManage.loadData(GpsManage.gps_pageNo);  
						}else{
					$("body").popjs({"title":"提示","content":req.description});  
						}
					}
			});
		}});
	},
	search:function(){ 
	
		var gpsNumber = $("#gpsNumber").val();
		if(gpsNumber.length>0)
		{
			var myReg = /^[^@\/\'\\\"#$%&\^\*]+$/;
			if(!myReg.test(gpsNumber)){
				$("body").popjs({"title":"提示","content":"编号查询条件不能包含非法字符"}); 
				return;
			}
		}
		
		var gpsName = $("#gpsName").val();
		if(gpsName.length>0)
		{
			var myReg = /^[^@\/\'\\\"#$%&\^\*]+$/;
			if(!myReg.test(gpsName)){
				$("body").popjs({"title":"提示","content":"名称查询条件不能包含非法字符"}); 
				return;
			}
		}
		GpsManage.packageQuery(1);
		GpsManage.loadData(1);
	},
	addGps:function(){
		var organId = $("#organId").val();
		$("#dialog").tyWindow({
			width : "600px",
			height : "500px",
			title : "新增定位设备信息",
			position : {
				top : "100px"
			},
		content: "<%=basePath%>gpsWeb/gotoGpsAdd.do?organId=" + organId+"&sessionId="+sessionId,
					iframe : true, 
					closeCallback :GpsManage.onClose,
					okCallback:GpsManage.onClose
				});
		//$("#dialog").data("kendoWindow").open();
	},
	editGps:function(gpsId){
		var organId = $("#organId").val();
		$("#dialog").tyWindow({
			width : "600px",
			height : "500px",
			title : "编辑定位设备信息",
			position : {
				top : "100px"
			},
		content: "<%=basePath%>gpsWeb/gotoGpsEdit.do?gpsId="
							+ gpsId + "&organId=" + organId+"&sessionId="+sessionId,
					iframe : true,
					closeCallback :GpsManage.onClose,
					okCallback:GpsManage.onClose
				});
		//$("#dialog").data("kendoWindow").open();
	},
	onClose:function(e){
		GpsManage.loadData(GpsManage.gps_pageNo);  
	},
	importGps:function(){
		var organId = $("#organId").val();
		$("#dialog").tyWindow({
			width : "500px",
			height : "450px",
			title : "Gps信息导入",
			position : {
				top : "100px"
			},
		content: "<%=basePath%>gpsWeb/gotoGpsImport.do?organId=" + organId+"&sessionId="+sessionId,
					iframe : true,
					closeCallback : GpsManage.onClose
				});
		//$("#dialog").data("kendoWindow").open();
	},
	outPortGps:function(){
		GpsManage.packageQuery();
		$.ajax({
					url : "<%=basePath%>excelOutWeb/exportGpsDataToExcle.do?sessionId="+sessionId,
					type : "post",
					data : {
						"gps_Query" : JSON.stringify(bph_gps_query)
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

<div id="gpsgrid" style="width:150%"></div>
<div id="page"></div>
<div id="dialog"></div>

