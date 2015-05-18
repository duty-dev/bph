<%@ page language="java" pageEncoding="UTF-8"%>

<div id="grid"></div>
<div id="page"></div>
<script type="text/javascript">
	var organId;
	var organPath;
	var organOrNext;
	var cardPointTotal = 0;
	var zNodes;
	var v="";
	var cardPointData;	//修改卡点时，数据对象
	
	function loadData(pageNo){
		organId = $("#organId").val();
    	organPath=$("#organPath").val();
    	organLevel=$("#organLevel").val();
		searchCardPoint(pageNo);
	}
	
	$(document).ready(function(){
		loadData(1);
	});  
	
	/* 卡点查询 */
	function searchCardPoint(pageNo){
		$.ajax({
			url:"<%=path%>/web/cardPoint/queryCardPointList.do",
			type:"post",
			dataType:"json",
			data:{
				organId: organId,
				name:$.trim($("#cardPointName").val()),
				sessionId:$("#token").val(),
				organOrNext:$("#organOrNext").val(),
				selectName:$("#selectName").val(),
				pageNo:pageNo,
				pageSize:10
			},
			success:function(msg){
				if(msg.code==200){
					if(msg.data != null){
						var udata = msg.data;
						var total = msg.totalRows;
						document.getElementById("gridListTotal").innerHTML=total+"个";
						$("#grid").kendoGrid({
	                        dataSource: {
	                            data: udata
	                        },
	                     	height: 550,
                          	sortable: true,
                          	resizable: true,
	                        columns: [{
	   	                        	field: "cardPointId",
	   	                            title: "卡点ID",
	   	                            hidden:true
	   	                        },
  	                        	{field: "操作", template: 
                        			"<button class='ty-edit-btn' title='编辑' id='openCardPoint' type='button' onclick='editCardPoint(#: cardPointId #)'>编辑</button>"+
                        			"<button class='ty-delete-btn' title='删除' id='deleteCardPoint' type='button' onclick='deleteCardPoint(#: cardPointId #)'>删除</button>",
                        		},
	                            {field: "name", title: "卡点名称"}, 
	                        	{field: "intercomGroupId", title: "通讯组号"}, 
	                        	{field: "circleLayerName", title: "所属圈层"},
	                        	{field: "camera", title: "关联天网"},
	                        	{field: "assignment", title: "职责"}],
	                    });
						var myGrid = $("#grid").data("kendoGrid");
   						myGrid.element.on("dblclick","tbody>tr","dblclick",function(e){
   							var id = $(this).find("td").first().text();
   							editCardPoint(id);
   						});
						var pg = pagination(pageNo,total,'loadData',10);
   	                	$("#page").html(pg);
					}
				}
			}
		});
	}
<%--     function saveCardPoint(){
    	$.ajax({
			url:"<%=basePath%>/web/cardPoint/deleteCardPoint.do",
			type:"post",
			dataType:"json",
			data:{
				id: Id
			},
			success:function(msg){
				 if(msg.code==200){
					searchCardPoint(1);					
				}else{
					alert(msg.description);
				}
			}
		});
		return true; 
    } --%>
    
 	/* 删除卡点 */
    function deleteCardPoint(Id){
    	var sessionId = $("#token").val();
    	$("body").tyWindow({"content":"确定是否删除该卡点?","center":true,"ok":true,"no":true,"okCallback":function(){
    		$("#table1 tbody").remove();
    		$.ajax({
    			url:"<%=basePath%>/web/cardPoint/deleteCardPoint.do?sessionId="+sessionId,
    			type:"post",
    			dataType:"json",
    			data:{
    				id: Id
    			},
    			success:function(msg){
    				 if(msg.code==200){
    					searchCardPoint(1);					
    				}else{
    					$("body").popjs({"title":"提示","content":msg.description});
    				}
    			}
    		});
    	}});
	}
    
	</script>


<div id="dialog"></div>

<script>
/* 卡点标绘 */
function drawCardPoint(cardPointId){
    window.external.MarkCartPoint(cardPointId);
}

/* 圈层设置 */
function openCircleLayer(){
	var sessionId = $("#token").val();
	$("#dialog").tyWindow({
		width: "680px",
		height: "480px",
	    title: "圈层设置",
	    position: {
	        top: "100px"
	      },
		content: "<%=path%>/web/cardPoint/gotoCircleLayer.do?sessionId="+sessionId,
		iframe : true,
		closeCallback: onClose
	});
	//$("#dialog").data("kendoWindow").open();
}
	
function addCardPoint(){
	if(organId == null || organId == ""){
		alert("请选择机构");
	}else{
		var sessionId = $("#token").val();
		$("#dialog").tyWindow({
			width: "820px",
			height: "648px",
		    title: "新增卡点",
		    position: {
		        top: "100px"
		      },
			content: "<%=path%>/web/cardPoint/gotoCardPointAdd.do?organId="+organId+"&sessionId="+sessionId,
			iframe : true,
			closeCallback: onClose,
		});
		//$("#dialog").data("kendoWindow").open();
	}
}
	
function editCardPoint(cardPointId){
	/* var organId = $("#organId").val(); */
	var sessionId = $("#token").val();
	$("#dialog").tyWindow({
		width: "820px",
		height: "648px",
	    title: "卡点管理",
	    position: {
	        top: "100px"
	      },
		content: "<%=path%>/web/cardPoint/gotoCardPointEdit.do?cardPointId="
									+ cardPointId + "&sessionId=" + sessionId,
							iframe : true,
							closeCallback : onClose
						});
	}

	function onClose(e) {
		searchCardPoint(1);
	}
</script>
