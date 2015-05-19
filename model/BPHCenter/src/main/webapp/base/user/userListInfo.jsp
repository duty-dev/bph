<%@ page language="java" pageEncoding="UTF-8"%>

            <div id="grid"></div>
            <div id="page"></div>
            <script>	
                	function loadData(pageNo) {
                	$("#grid").empty();
                	var	userName = $.trim($("#userName").val());
                	var organId = $.trim($("#organId").val());
                	var searchType = $.trim($("#searchType").val());
                	var organPath = $.trim($("#organPath").val());
                	$.ajax({
               			url:"<%=path %>/admin/getUserList.do",
               			type:"post",
               			dataType:"json",
               			data:{
               				organId:organId,
               				userName:userName,
               				searchType:searchType,
               				organPath:organPath,
               				expandeds:expandeds,
               				selectName:$("#selectName").val(),
               				sessionId:$.trim($("#token").val()),
               				pageNo:pageNo,
            				pageSize:10
               			},
               			success:function(msg){
               				if(msg.code==200){
               					if(msg.data != null){
               						var udata = msg.data;
               						var total = msg.totalRows;
               						document.getElementById("gridListTotal").innerHTML=total+"人";
               						$("#grid").kendoGrid({
               	                        dataSource: {
               	                            data:udata
               	                        },
               	                     height: 502,
                                     sortable: true,
                                     resizable: true,
                                     selectable: "multiple",
               	                        columns: [{
               	                        	field: "userId",
               	                            title: "用户ID",
               	                            hidden:true
               	                        },
               	                     	{
                 							field: "操作",
                 							template: "<button type='button' class='ty-edit-btn' id='#: userId #' title='修改' onclick='editUser(#: userId #)'>修改</button> "+
                 							"<button type='button' class='ty-delete-btn' id='#: userId #' title='删除' onclick='deleteUser(#: userId #)'>删除</button> "
                 						}
               	                        ,{
               	                            field: "loginName",
               	                            title: "帐号"
               	                        }, {
               	                            field: "userName",
               	                            title: "姓名"
               	                        }, {
               	                            field: "createTime",
               	                            title: "创建时间"
               	                        }, {
               	                            field: "updateTime",
               	                            title: "修改时间"
               	                        }, {
            	                            field: "stateView",
            	                            title: "用户状态"
               	                        }],
               	                         change: function (e) {
                                         var userId = e.sender.selectable.userEvents.currentTarget.cells[0].innerHTML;
                                         loadTree(userId);
                                     }
               	                    });
               						//添加双击事件
               						var myGrid = $("#grid").data("kendoGrid");
               						myGrid.element.on("dblclick","tbody>tr","dblclick",function(e){
               							var id = $(this).find("td").first().text();
               							editUser(id);
               						});
               						
               						var pg = pagination(pageNo,total,'loadData',10);
               	                	$("#page").html(pg);
               					}
               				}
               			}
               		});
                }
                loadData(1);
                function search(){
                	var flag = checkForms($("#userName").get(0));
                	if(!flag)return;
                	loadData(1);
        		}
                //删除用户
                function deleteUser(userId){
                	if(userId==1){
              		  $("body").popjs({"title":"提示","content":"初始系统管理员不能删除！"});
              		   return false;
              		   } 
                	//var statu = confirm("确定要删除该用户?");
                	$("body").tyWindow({"content":"确定要删除该用户?","center":true,"ok":true,"okCallback":function(){
                		$.ajax({
            				url:"<%=basePath %>admin/userDelete.do",
            				type:"get",
            				dataType:"json",
            				data:{
            					sessionId:$.trim($("#token").val()),
            					userId:userId
            				},
            				success:function(msg){
            					 if(msg.code==200){
            						 $("body").popjs({"title":"提示","content":msg.description});
            						loadData();
            					}else{
            						$("body").popjs({"title":"提示","content":msg.description});
            					}
            				}
            			});
                	},"no":true});
        		}
            </script>
            
            <div id="dialog"></div>
<script>
function addUser(){
	var organId = $.trim($("#organId").val());
	var sessionId = $.trim($("#token").val());
	$("#dialog").tyWindow({
		width: "660px",
		height: "630px",
	    title: "新增帐号",
	    position: {
	        top: "100px"
	      },
		content: "<%=path%>/admin/gotoUserAdd.do?organId=" + organId+"&sessionId="+sessionId,
		iframe : true,
		okCallback:onClose,
		closeCallback:onClose
		});
	}
	
function editUser(userId){
	var organId = $.trim($("#organId").val());
	var sessionId = $.trim($("#token").val());
	$("#dialog").tyWindow({
		width: "660px",
		height: "630px",
	    title: "帐号管理",
	    position: {
	        top: "100px"
	      },
		content: "<%=path %>/admin/gotoUserEdit.do?userId="+userId+"&organId="+organId+"&sessionId="+sessionId,
		iframe : true,
		okCallback:onClose,
		closeCallback:onClose
		});
	}

function onClose(e) {
	loadData(1);
	
	/* 刷新左边的树 */
    $("#treeview").remove();
	 $("#box").append("<div id='treeview'></div>");
  	  $.ajax({
			url:"<%=basePath%>web/organx/tree.do",
			type:"post",
			data:{
				expandeds:$.trim($("#expandeds").val()),
				sessionId:$.trim($("#token").val()),
				random:Math.random()
			},
			dataType:"json",
			success:function(rsp){
				json_data =JSON.stringify(rsp.data);
				
				 treeview=$("#treeview").kendoTreeView({
					select: onSelect,//点击触发事件
				     //checkboxes: {
				        //checkChildren: true//允许复选框多选
				    //},
				    //check: onCheck,//check复选框 
				    dataSource: [eval('(' + json_data + ')')]
				}).data("kendoTreeView");
               /* var selectedNode = treeview.select();

               if (selectedNode.length == 0) {
                   selectedNode = null;
               } */
		  		<%-- $.ajax({
		  			url:"<%=basePath%>web/organx/addOrganTreeElement.do",
					type:"post",
					data:{
						random:Math.random()
					},
					dataType:"json",
					success:function(rsp){
						$(rsp.data).each(function(){
							treeview.append(eval('(' + JSON.stringify(this) + ')'), selectedNode);
          				});
					}
		  		}); --%>
		  		selectClass();//追加样式
			}
		});
}

//点击每行单元格在右边显示功能信息
function loadTree(userId){
   	 $.ajax({
			url:"<%=basePath%>role/getModuleTreeByRoles.do",
			type:"post",
			data:{
				userId:userId,
				sessionId:$.trim($("#token").val()),
				random:Math.random()
			},
			dataType:"json",
			success:function(rsp){
				var roleData=JSON.stringify(rsp.data);
				$("#moduletreeview").remove();
				$("#roleBox").append("<div id='moduletreeview'></div>");
				$("#moduletreeview").kendoTreeView({
					//select: onSelect,
				     checkboxes: {
				        checkChildren: true
				    }, 
				    check: onCheck,
				    dataSource: [eval('(' + roleData + ')')]
				}).data("kendoTreeView");
			}
		});
}
$(function(){
	
});
</script>

