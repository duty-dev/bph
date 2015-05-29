<%@ page language="java" pageEncoding="UTF-8"%>

            <div id="grid" style="width:150%;"></div>
            <div id="page"></div>
            <div id="dialog"></div>
            <script>	
                	function loadData(pageNo) {
                	var	organId = $("#organId").val();
                	var	organName = $.trim($("#organName").val());
                	var organPath=$("#organPath").val();
                	var organLevel=$("#organLevel").val();
                	$.ajax({
               			url:"<%=basePath%>web/organx/getOrganList.do",
               			type:"post",
               			dataType:"json",
               			data:{
               				organId:organId,
               				name:organName,
               				organPath:organPath,
               				organLevel:organLevel,
               				sessionId:$("#token").val(),
               				expandeds:expandeds,
               				selectName:$("#selectName").val(),
               				pageNo:pageNo,
            				pageSize:10
               			},
               			success:function(msg){
               				if(msg.code==200){
               					if(msg.data.data != null){
               						var odata = msg.data.data;
               						var total = msg.totalRows;
               						$("#gridListTotal").text(total+"个");
               						$("#grid").kendoGrid({
               	                        dataSource: {
               	                            data:odata
               	                        },
               	                     height: 502,
                                     sortable: true,
									 resizable: true,
                                     selectable: "multiple",
               	                        columns: [{
               	                            field: "id",
               	                            title: "机构id",
               	                         	hidden:true
               	                        },{
                 							field: "操作",
                 							template: "<button type='button' id='#: id#' class='ty-edit-btn' title='修改' onclick='editOrgan1(#: id #)'>修改</button> "
                 							+"<button type='button' id='#: id#' class='ty-delete-btn' title='删除' onclick=\"delteAction('#: id #')\">删除</button> "
                 						}, {
               	                            field: "name",
               	                            title: "机构名称"
               	                        }, {
               	                            field: "code",
               	                            title: "机构编码"
               	                        }, {
               	                            field: "orgTypeName",
               	                            title: "机构类型"
               	                        }, {
               	                            field: "shortName",
               	                            title: "机构简称"
               	                        }, {
               	                            field: "note",
               	                            title: "机构备注"
               	                        }]
               	                    });
               						var myGrid = $("#grid").data("kendoGrid");
               						myGrid.element.on("dblclick","tbody>tr","dblclick",function(e){
               							var id = $(this).find("td").first().text();
               							editOrgan1(id);
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
                	var flag = checkForms($("#organName").get(0));
                	if(!flag)return;
                	loadData(1);
        		}
                
                //显示添加机构弹出框
                function showAddOrgan(){
                	var sessionId = $("#token").val();
                	$("#dialog").tyWindow({
                		width: "680px",
                		height: "490px",
                	    title: "机构新增",
                	    position: {
                	        top: "100px"
                	      },
                		 content: "<%=basePath%>web/organx/gotoAddOrgan.do?organId="+$("#organId").val()+"&sessionId="+sessionId,
                		iframe : true,
                		closeCallback: onClose
                		});
                	//$("#dialog").data("kendoWindow").open();
                }
                function onClose(e){
                	loadData(1);
                	/* 刷新左边的树 */
	               	 if($("#searchOrganName").val()==""){
						 loadOrganTreeList();
					 }else{
						 queryOrgan();
					 }
                	
                
                <%--  $("#treeview").remove();
				 $("#box").append("<div id='treeview'></div>");
               	  $.ajax({
						url:"<%=basePath%>web/organx/tree.do",
						type:"post",
						data:{
							expandeds:expandeds,
							sessionId:$("#token").val(),
							random:Math.random()
						},
						dataType:"json",
						success:function(rsp){
							json_data =JSON.stringify(rsp.data);
							
							 treeview=$("#treeview").kendoTreeView({
								select: onSelect,//点击触发事件
							    dataSource: [eval('(' + json_data + ')')]
							}).data("kendoTreeView");
	        		  		selectClass();
						}
					});--%>
                } 
                
                function editOrgan1(id){
                	var sessionId = $("#token").val();
                	$("#dialog").tyWindow({
                		width: "670px",
                		height: "522px",
                	    title: "机构修改",
                	    position: {
                	        top: "100px"
                	      },
                		 content: "<%=basePath%>web/organx/queryOrganDetail.do?organId="+id+"&sessionId="+sessionId,
                		iframe : true,
                		closeCallback: onClose
                		});
                }
                /* 删除机构信息 */
                function delteAction(e){
                	var sessionId = $("#token").val();
                    $("body").tyWindow({"content":"确定要删除该机构?","center":true,"ok":true,"no":true,"okCallback":function(){
	           			$.ajax({ 
	           				url:"<%=basePath %>web/organx/deleteOrgan.do",
	           				type:"post",
	           				dataType:"json",
	           				data:{
	           					sessionId:sessionId,
	           					organId:e
	           				},
	           				success:function(msg){
	           					 if(msg.code==200){
	           						$("body").popjs({"title":"提示","content":msg.description});
	           						 //loadData(1);
	           						 onClose();
	           					}else{
	           						$("body").popjs({"title":"提示","content":msg.description});
	           					}
	           				}
	           			});
                    }});
                }
                
            </script>
