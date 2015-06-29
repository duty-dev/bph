<jsp:directive.page language="java" pageEncoding="UTF-8"/>

<script>	
function loadData(pageNo){ 
		$.ajax({
       			url:"<%=basePath%>typeWeb/getTypeList.do",
				type : "post",
				data : {
					
					pageNo:pageNo,
    				pageSize:10,
    				iconType:$("#iconType").val()
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
								height : 472,
								sortable : true,
								selectable : "multiple", 
								columns:[{
										title : 'Id',
										field : 'id', 
										hidden : true 
									},{
	             							field: "操作",
	             							template: "<button type='button' class='ty-edit-btn' id='#: id #' title='修改' onclick='editType(#: id #)'>修改</button> "+
	             								"<button type='button' class='ty-delete-btn' id='#: id #' title='删除' onclick='deleteType(#: id #)'>删除</button> ",
	             							width:120
	             					},{
	             						title : '类型名称',
										field : 'typeName' 
									},{
										title : '常态',
										template: "<img style='width:30px;height:30px;' src='<%=basePath %>#: nomalUrl #'> "
									},{
	             						title : '选中',
	             						template: "<img style='width:30px;height:30px;' src='<%=basePath %>#: selectedUrl #'> "
									},{
	             						title : '进入电子围栏',
	             						template: "<img style='width:30px;height:30px;' src='<%=basePath %>#: intoEnclosureUrl #'> "
									},{
	             						title : '已派警',
	             						template: "<img style='width:30px;height:30px;' src='<%=basePath %>#: dispatchUrl #'> "
									},{
	             						title : '到达现场',
	             						template: "<img style='width:30px;height:30px;' src='<%=basePath %>#: arriveUrl #'> "
									}
								],
								change: function (e) {
	                                var userId = e.sender.selectable.userEvents.currentTarget.cells[0].innerHTML; 
	                            }
							}); 
               					var pg = pagination(pageNo,total,'loadData',10);
               	                $("#page").html(pg);
						} 
					} 
				}
		});
	}
    loadData(1);
	function deleteType(typeId){ 
		$("body").tyWindow({"content":"确定要删除?","center":true,"ok":true,"no":true,"okCallback":function(){
	 	$.ajax({
					url : "<%=basePath%>typeWeb/deleteTypeById.do?typeId="+typeId,
					type : "post", 
					dataType : "json",
					success : function(req) {
						if(req.code==200){
					$("body").popjs({"title":"提示","content":"删除成功！"});  
							loadData(1);  
						}else{ 
							$("body").popjs({"title":"提示","content":req.description});
						}
					}
			});
		}});
	}
	function search(){ 
		var iconsName = $("#iconsName").val();
		if(iconsName.length>0)
		{
			var myReg = /^[^@\/\'\\\"#$%&\^\*]+$/;
			if(!myReg.test(iconsName)){
				$("body").popjs({"title":"提示","content":"查询条件不能包含非法字符"}); 
				return;
			}
		}
		loadData(1);
	}
	function addType(){ 
		var typeName = $("#iconTypeName").val();
		var typeId = $("#iconType").val();
		var url = "<%=basePath%>typeWeb/gotoTypeAdd.do?iconType="+typeId
		$("#dialog").tyWindow({
			width : "600px",
			height : "600px",
			title : "新增"+typeName+"类型",
			position : {
				top : "20px"
			},
		content: url,
					iframe : true,
					closeCallback : onClose
				});
	}
	function editType(id){ 
		var typeName = $("#iconTypeName").val();
		var url = "<%=basePath%>typeWeb/gotoTypeUpdate.do?id="+id
		$("#dialog").tyWindow({
			width : "600px",
			height : "600px",
			title : "修改"+typeName+"类型",
			position : {
				top : "20px"
			},
		content: url,
					iframe : true,
					closeCallback : onClose
				});
	}
	function onClose(e){
		loadData(1); 
	}
	function reload(typeId,typeName){
		$("#iconType").val(typeId);
		$("#iconTypeName").val(typeName);
		loadData(1);
	}
</script>
<div id="iconsgrid" style="width:150%"></div>
<div id="page"></div>
<div id="dialog"></div>

