<jsp:directive.page language="java" pageEncoding="UTF-8"/>

<script>	
var bph_Icon_query ={};
var sessionId = $("#token").val();
$(function() {
	loadData(1);
	$("#iconsName").keydown(function(){
        if(event.keyCode == 13){
            IconManage.search();
            $("#iconsName").focus();
        }
    });
});
function loadData(pageNo){ 
	IconManage.icon_pageNo = pageNo;
	IconManage.packageQuery(pageNo);
	IconManage.loadData(pageNo); 
}
var IconManage ={
	icon_pageNo :1,
	packageQuery:function(pageNo){
		bph_Icon_query.name = $.trim($("#iconsName").val());
		var typeId =  $("#iconType").val();
		if(typeId>0){
			bph_Icon_query.typeId = typeId;
		}else{
			bph_Icon_query.typeId = 0;
		}
		bph_Icon_query.pageSize = $("#pageSize").val();
		bph_Icon_query.pageStart = pageNo; 
	},
	loadData:function(pageNo){
		$.ajax({
       			url:"<%=basePath%>iconsWeb/getIconsList.do?sessionId="+sessionId,
				type : "post",
				data : {
						"icons_Query" : JSON.stringify(bph_Icon_query),
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
               						
               						$("#gridListTotal").html(total+"张");
               						$("#iconsgrid").empty();
							$("#iconsgrid").kendoGrid({
								dataSource : {
									data : udata 
								},
								height : 550,
								sortable : true,
								selectable : "multiple", 
								columns:[{
										title : 'Id',
										field : 'id', 
										hidden : true 
									},{
	             							field: "操作",
	             							template: "<button type='button' class='ty-delete-btn' id='#: id #' title='删除' onclick='IconManage.deleteIcon(#: id #)'>删除</button> ",
	             							width:120
	             					},{
										title : '图标类型',
										field : 'typeName' 
									},{
										title : '图标名称',
										field : 'name' 
									},{
										title : '图标预览',
										template: "<img style='width:25px;height:25px;' src='<%=basePath %>#: iconUrl #'> ",
             							width:120
									}
								],
								change: function (e) {
	                                var userId = e.sender.selectable.userEvents.currentTarget.cells[0].innerHTML; 
	                            }
							}); 
								$("#iconsgrid .k-grid-content").mCustomScrollbar( {scrollButtons:{enable:true},advanced:{ updateOnContentResize: true } });
               					var pg = pagination(pageNo,total,'loadData',10);
               	                $("#page").html(pg);
						} 
					} 
				}
		});
	},
	deleteIcon:function(iconId){ 
		$("body").tyWindow({"content":"确定要删除?","center":true,"ok":true,"no":true,"okCallback":function(){
	 	$.ajax({
					url : "<%=basePath%>iconsWeb/deleteIconsById.do?iconId="+iconId+"&sessionId="+sessionId,
					type : "post", 
					dataType : "json",
					success : function(req) {
						if(req.code==200){
					$("body").popjs({"title":"提示","content":"删除成功！"});  
							IconManage.loadData(IconManage.icon_pageNo);  
						}else{ 
							$("body").popjs({"title":"提示","content":req.description});
						}
					}
			});
		}});
	},
	search:function(){ 
		var iconsName = $("#iconsName").val();
		if(iconsName.length>0)
		{
			var myReg = /^[^@\/\'\\\"#$%&\^\*]+$/;
			if(!myReg.test(iconsName)){
				$("body").popjs({"title":"提示","content":"查询条件不能包含非法字符"}); 
				return;
			}
		}
		IconManage.packageQuery(1);
		IconManage.loadData(1);
	},
	addIcon:function(){ 
		$("#dialog").tyWindow({
			width : "600px",
			height : "500px",
			title : "新增图标信息",
			position : {
				top : "100px"
			},
		content: "<%=basePath%>iconsWeb/gotoIconAdd.do?sessionId="+sessionId,
					iframe : true,
					closeCallback : IconManage.onClose
				});
	},
	onClose:function(e){
		IconManage.loadData(IconManage.icon_pageNo); 
	}
}; 
</script>
<div id="iconsgrid" style="width:150%"></div>
<div id="page"></div>
<div id="dialog"></div>

