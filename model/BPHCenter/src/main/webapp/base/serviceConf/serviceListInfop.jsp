<%@ page language="java" pageEncoding="UTF-8"%>
           <%@ include file="../../emulateIE.jsp" %>
            <div id="grid" style="width:150%;"></div>
            <div id="page"></div>
            <div id="dialog">
            	 
            </div>
            <script>	
            
            $(document).ready(function() {
            	loadData(1);
            });
                	function loadData(pageNo) {
                	var	serviceName = $.trim($("#serviceName").val());
                	$.ajax({
               			url:"<%=basePath%>serviceSet/getServiceSetList.do",
               			type:"post",
               			dataType:"json",
               			data:{
               				name:serviceName,
               				sessionId:$("#token").val(),
               			},
               			success:function(msg){
               				if(msg.code==200){
               					if(msg.data.data != null){
               						var udata = msg.data.data;
               						var total = msg.data.totalRows;
               						$("#gridListTotal").text(total+"个");
               						$("#grid").kendoGrid({
               	                        dataSource: {
               	                            data:udata,
               	                        },
               	                     height: 550,
                                     sortable: true,
                                     selectable: "multiple",
               	                        columns: [{
               	                        	field: "serviceId",
               	                            title: "服务ID",
               	                            hidden:true
               	                        }, {
               							field: "操作",
               							template: "<button id='#: serviceId#' class='ty-edit-btn' title='修改' onclick='editAction(#: serviceId#)'>修改</button> "
               							+"<button id='#: serviceId#' class='ty-delete-btn' title='删除' onclick='delteAction(#: serviceId #)'>删除</button> "
               							}, {
               	                            field: "serviceName",
               	                            title: "服务名称"
               	                        }, {
               	                            field: "serviceIp",
               	                            title: "服务IP"
               	                        }, {
               	                            field: "servicePort",
               	                            title: "服务端口"
               	                        }, {
               	                            field: "serviceAccount",
               	                            title: "用户名"
               	                        }, {
               	                            field: "serviceTypeName",
               	                            title: "服务类型"
               	                        },{
               	                            field: "servicePwd",
               	                            title: "用户密码"
               	                        }, {
               	                            field: "serviceVersion",
               	                            title: "服务版本"
               	                        },{
               	                            field: "exchangeName",
               	                            title: "交换机名称"
               	                        }],
               	                         change: function (e) {
                                         var serviceId = e.sender.selectable.userEvents.currentTarget.cells[0].innerHTML;
                                         //loadTree(serviceId);//根据角色id 加载右边的功能权限
                                     }
               	                    });

               						$("#grid .k-grid-content").mCustomScrollbar( {scrollButtons:{enable:true},advanced:{ updateOnContentResize: true } });
               						var pg = pagination(pageNo,total,'loadData',10);
               	                	$("#page").html(pg);
               					}
               				}
               			}
               		});
                }
                function search(){
                	loadData(1);
        		}
                
                function showAddService(){
                	var sessionId=$("#token").val();
                	$("#dialog").tyWindow({
                		width: "680px",
                		height: "580px",
                	    title: "服务新增",
                	    position: {
                	        top: "100px"
                	      },
                		 content: "<%=basePath%>serviceSet/gotoAddService.do?sessionId="+sessionId,
                		iframe : true,
                		closeCallback: onClose
                		});
                	//$("#dialog").data("kendoWindow").open();
                }
                
                function onClose(e){
                	loadData(1);
                }
                function delteAction(serviceId){
                	var sessionId=$("#token").val();
                	
                    $("body").tyWindow({"content":"确定要删除?","center":true,"ok":true,"no":true,"okCallback":function(){
           			$.ajax({
           				url:"<%=basePath%>serviceSet/delete.do",
           				type:"post",
           				dataType:"json",
           				data:{
           					serviceId:serviceId,
           					sessionId:sessionId
           				},
           				success:function(msg){
           					if(msg.code==200){
           						//alert(msg.description);
           						$("body").popjs({"title":"提示","content":msg.description});
           						loadData(1);
           					}else{
           						//alert(msg.description);
           						$("body").popjs({"title":"提示","content":msg.description});
           					}
           				}
           			});
                    }});
                }
                //单击修改按钮弹出对话框
                function editAction(serviceId){
                	var sessionId = $("#token").val();
                	$("#dialog").tyWindow({
                		width: "680px",
                		height: "580px",
                	    title: "服务信息编辑",
                	    position: {
                	        top: "100px"
                	      },
                		 content: "<%=basePath%>serviceSet/gotoServiceDetail.do?serviceId="+serviceId+"&sessionId="+sessionId,
                		iframe : true,
                		closeCallback: onClose
                		});
				}
            </script>
