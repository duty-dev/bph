<jsp:directive.page language="java" pageEncoding="UTF-8"/>

<script>	
function loadData(pageNo){ 
		$.ajax({
       			url:"<%=basePath%>iconsWeb/getIconsList.do",
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
								resizable: true,
								columns:[{
										title : 'Id',
										field : 'groupId', 
										hidden : true 
									},{
	             							field: "操作",
	             							template: "<button type='button' class='ty-delete-btn' id='#: groupId #' title='删除' onclick='deleteIcon(#: groupId #)'>删除</button> ",
	             							width:120
	             					},{
	             						title : '图标名称',
										field : 'groupName' 
									},{
										title : '常态',
										template: "<img style='width:25px;height:25px;' src='<%=basePath %>#: nomalUrl #'> "
									},{
	             						title : '选中',
	             						template: "<img style='width:25px;height:25px;' src='<%=basePath %>#: selectedUrl #'> "
									},{
	             						title : '进入电子围栏',
	             						template: "<img style='width:25px;height:25px;' src='<%=basePath %>#: intoEnclosureUrl #'> "
									},{
	             						title : '已派警',
	             						template: "<img style='width:25px;height:25px;' src='<%=basePath %>#: dispatchUrl #'> "
									},{
	             						title : '到达现场',
	             						template: "<img style='width:25px;height:25px;' src='<%=basePath %>#: arriveUrl #'> "
									}<%-- ,{
										title : '图标预览',
										template: "<img style='width:25px;height:25px;' src='<%=basePath %>#: iconUrl #'> ",
             							width:120
									} --%>
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
	function deleteIcon(groupId){ 
		$("body").tyWindow({"content":"确定要删除?","center":true,"ok":true,"no":true,"okCallback":function(){
	 	$.ajax({
					url : "<%=basePath%>iconsWeb/deleteIconsById.do?groupId="+groupId,
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
	function addIcon(){ 
		var typeName = $("#iconTypeName").val();
		var typeId = $("#iconType").val();
		var url = "<%=basePath%>iconsWeb/gotoIconAdd.do?typeName="+typeName+"&iconType="+typeId
		$("#dialog").tyWindow({
			width : "600px",
			height : "600px",
			title : "新增"+typeName+"图标",
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

