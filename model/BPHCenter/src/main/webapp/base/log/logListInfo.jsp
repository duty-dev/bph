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
                	var	logType = $("#logType").val().trim();
                	$.ajax({
               			url:"<%=basePath%>log/getLogList.do",
               			type:"post",
               			dataType:"json",
               			data:{
               				logTypeId:logType,
               				sessionId:$("#token").val(),
               				pageNo:pageNo,
	                        pageSize:10
               			},
               			success:function(msg){
               				if(msg.code==200){
               					if(msg.data.data != null){
               						var udata = msg.data.data;
               						var total = msg.data.totalRows;
               						$("#gridListTotal").text(total+"个");
               						$("#grid").kendoGrid({
               	                        dataSource: {
               	                            data:udata
               	                        },
               	                     height: 550,
                                     sortable: true,
                                     selectable: "multiple",
               	                        columns: [{
               	                        	field: "logId",
               	                            title: "日志ID",
               	                            hidden:true
               	                        }, /* {
               							field: "操作",
               							template: "<button id='#: logId#' class='ty-edit-btn' title='修改'>修改</button> "
               							+"<button id='#: logId#' class='ty-delete-btn' title='删除'>删除</button> "
               							}, */ {
               	                            field: "logTypeName",
               	                            title: "日志类型"
               	                        }/* ,{
               	                            field: "loginUserId",
               	                            title: "用户ID"
               	                        } */, {
               	                            field: "loginUserName",
               	                            title: "用户名称"
               	                        }, {
               	                            field: "loginIp",
               	                            title: "登录IP"
               	                        }, /* {
               	                            field: "loginMac",
               	                            title: "登录mac"
               	                        }, */ {
               	                            field: "operateDate",
               	                            title: "操作时间"
               	                        },{
               	                            field: "logContext",
               	                            title: "日志内容"
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
            </script>
