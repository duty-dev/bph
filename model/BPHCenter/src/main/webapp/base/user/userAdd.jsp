<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>


<!DOCTYPE html>
<html>
<head>
<title>扁平化指挥系统</title>
<%@ include file="../../emulateIE.jsp" %>
</head>

<body class="nopd">
<div id="vertical">
			<div id="horizontal" style="height: 450px; width: 590px;">
				<div id="left-pane">
					<div class="pane-content">
						<!-- 左开始 -->
						<div class="demo-section k-header">
							<form id="employeeForm" data-role="validator"
								novalidate="novalidate">
								<h4>用户信息</h4>
								<ul>
									<li><label for="loginName">所属机构:</label> 
										<input type="hidden" value="${organ.id}" id="orgId" name="orgId" />
										<input type="hidden" id="policeId" name="policeId"/>
										${organ.name}</li>
									<li class="ty-input"><label for="loginName">帐号:</label>
										<input type="text" class="k-textbox" name="loginName" id="loginName" style="width:75%;"/><em class="ty-input-end"></em></li>
									<li class="ty-input"><label for="addUserName">姓名:</label> 
<<<<<<< .mine
										<input type="text" class="k-textbox" name="addUserName" id="addUserName" style="width:55%;" /><em class="ty-input-end"></em>
=======
										<input type="text" class="k-textbox" name="addUserName" id="addUserName" style="width:55%;"/><em class="ty-input-end"></em>
>>>>>>> .r2103
										<button type="button" class="k-button" style="margin-left:10px;" onclick="loadPolice();">选择</button></li>
									<li class="ty-input"><label for="password">密码:</label> 
										<input type="text" class="k-textbox" name="password" id="password" style="width:75%;"/><em class="ty-input-end"></em></li>
									<li class="ty-input"><label for="userOtherOrgans">跨机构授权:</label>
										<input type="text" class="k-textbox" name="userOtherOrgans" id="userOtherOrgans" style="width:36%;" /><em class="ty-input-end"></em>
										<button type="button" data-role="button" style="margin-left:10px;" onclick="loadOrganTree();">选择</button></li>
									<li class="ty-input accept"><div style="width:100%"><div style="float:left;width:15%;"><label for="otherOgans">角色:</label></div><div id="selectRoles" style="float:left;width:85%;"> 
									<c:forEach var="item" items="${roleList}">
											<input type="checkbox" id="rolesId" name="rolesId"
												value="${item.id}" onclick="refreshRole();"/>${item.name}
                        			</c:forEach></div></div></li>
									<li class="ty-input actions">
										<button type="button" data-role="button"
											data-sprite-css-class="k-icon k-i-tick" data-click='save'>提交</button>
									</li>
								</ul>
							</form>
						</div>

						<script type="text/javascript">
                $(function () {
                    var container = $("#employeeForm");
                    kendo.init(container);
                    container.kendoValidator({
                        rules: {
                            validmask: function (input) {
                                if (input.is("[data-validmask-msg]") && input.val() != "") {
                                    var maskedtextbox = input.data("kendoMaskedTextBox");
                                    return maskedtextbox.value().indexOf(maskedtextbox.options.promptChar) == -1;
                                }

                                return true;
                            }
                        }
                    });
                });

                function save(e) {
<<<<<<< .mine
                    //var validator = $("#employeeForm").data("kendoValidator");
                   // if (validator.validate()) {
                	   if($("#addUserName").val()==""){
                		  $("body").popjs({"title":"提示","content":"登录名不能为空！"});
=======
                	   if($.trim($("#loginName").val())==""){
                		  $("body").popjs({"title":"提示","content":"帐号不能为空！"});
>>>>>>> .r2103
                		   return false;
                		   } 
                	   if($.trim($("#password").val())==""){
                		   $("body").popjs({"title":"提示","content":"密码不能为空！"});
                		   return false;
                		   }
                	   var flag = false;
                	   $("#selectRoles input[type='checkbox']").each(function(i){
                		   if($(this).prop("checked")){
                			   flag = true;
                		   }
                	   });               	   
                	   if(!flag){
                  		  $("body").popjs({"title":"提示","content":"角色不能为空！"});
                  		   return false;
                  		   }
                    	userSave();
                   // }
                }
                
                function userSave(){
            		var r = document.getElementsByName("rolesId");
            		var rolesId = "";
            		   for (i= 0 ;i < r.length; i++){
            		      if(r[i].checked == true){
            				 if(rolesId == ""){
            					 rolesId = r[i].value;
            				 }else{
            					 rolesId = rolesId + "," + r[i].value;
            				 }
            			  }
            		  }
            		$.ajax({
              			url:"<%=basePath%>admin/userAdd.do",
              			type:"post",
              			dataType:"json",
              			data:{
              				orgId:$("#orgId").val(),
              				userName:$("#addUserName").val(),
              				loginName:$("#loginName").val(),
              				password:$("#password").val(),
              				policeId:$("#policeId").val(),
              				userOtherOrgans:$("#userOtherOrgans").val(),
              				sessionId:$("#token").val(),
              				rolesId:rolesId
              			},
            	  		 success:function(msg){
            	  			if(msg.code==200){
            	  				$("body").popjs({"title":"提示","content":msg.description,"callback":function(){
            	  					window.parent.window.parent.onClose();
									window.parent.$("#dialog").tyWindow.close();
								}});
            	  			}else{
            	  				$("body").popjs({"title":"提示","content":msg.description});
            	  			}
            	  		}
              		});
              	}
                
                function refreshRole(){
                	var r = document.getElementsByName("rolesId");
            		var rolesId = "";
            		   for (i= 0 ;i < r.length; i++){
            		      if(r[i].checked == true){
            				 if(rolesId == ""){
            					 rolesId = r[i].value;
            				 }else{
            					 rolesId = rolesId + "," + r[i].value;
            				 }
            			  }
            		  }
            		   loadRoleTree(rolesId);
                }
                
              //点击每行单元格在右边显示功能信息
                function loadRoleTree(rolesId){
                   	 $.ajax({
                			url:"<%=basePath%>role/getModuleTreeByRoles.do",
                			type:"post",
                			data:{
                				roleIds:rolesId,
                				sessionId:$("#token").val(),
                				random:Math.random()
                			},
                			dataType:"json",
                			success:function(rsp){
                				var roleData=JSON.stringify(rsp.data);
                				$("#treeview").remove();
        						$("#addRoleTreeview").remove();
        						$("#policeview").remove();
        						$("#organTitle").remove();
        						$("#roleTitle").remove();
        						$("#policeTitle").remove();
                				$("#addRoleBox").append("<h4 id='roleTitle'>角色权限</h4><div id='addRoleTreeview'></div>");
                				$("#addRoleTreeview").kendoTreeView({
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
              
                function onCheck(e) {
		            var checkedNodes = [],
		              treeView = $("#addRoleTreeview").data("kendoTreeView"),message;

		            //treeToJson(treeView.dataSource.view());
		            
		           // checkedNodeIds(treeView.dataSource.view(), checkedNodes);

		            if (checkedNodes.length > 0) {
		                message = checkedNodes.join(",");
		            } else {
		                message = "";
		            }
		            //$("#result").html(message);
		        }
                
              //点击每行单元格在右边显示功能信息
                function loadOrganTree(){
                	var zNodes;
       			 var json_data;
       			 var currentText;
       			$.ajax({
					url:"<%=basePath%>web/organx/tree.do",
					type:"post",
					data:{
						organId:"1",
						userId:"",
						sessionId:$("#token").val(),
						random:Math.random()
					},
					dataType:"json",
					success:function(rsp){
						json_data =JSON.stringify(rsp.data);
						$("#treeview").remove();
						$("#addRoleTreeview").remove();
						$("#policeview").remove();
						$("#organTitle").remove();
						$("#roleTitle").remove();
						$("#policeTitle").remove();
        				$("#box").append("<h4 id='organTitle'>机构选择</h4><div id='treeview'></div>");
						$("#treeview").kendoTreeView({
							//select: onSelect,//点击触发事件
						     checkboxes: {
						        checkChildren: true//允许复选框多选
						    },
						    check: organOnCheck,//check复选框 
						    dataSource: [eval('(' + json_data + ')')]
						}).data("kendoTreeView");
					}
				});
                }
              
                function organOnCheck(e) {
		            var checkedNodes = [],
		              treeView = $("#treeview").data("kendoTreeView"),message;

		            //treeToJson(treeView.dataSource.view());
		            
		            checkedNodeIds(treeView.dataSource.view(), checkedNodes);

		            if (checkedNodes.length > 0) {
		                message = checkedNodes.join(",");
		            } else {
		                message = "";
		            }
		            //$("#result").html(message);
		            $("#userOtherOrgans").val(message);
		        }
                
                function checkedNodeIds(nodes, checkedNodes) {
    	            for (var i = 0; i < nodes.length; i++) {
    	                if (nodes[i].checked) {
    	                    checkedNodes.push(nodes[i].id);
    	                } 
    	                if (nodes[i].hasChildren) {
    	                    checkedNodeIds(nodes[i].children.view(), checkedNodes);
    	                }
    	            }
    	        }  
                
                function loadPolice(){
					$("#treeview").remove();
					$("#addRoleTreeview").remove();
					$("#policeview").remove();
					$("#organTitle").remove();
					$("#roleTitle").remove();
					$("#policeTitle").remove();
    				$("#policeBox").append("<h4 id='policeTitle'>警员绑定</h4><div id='policeview'>"
    						+"<c:forEach var='item' items='${policeList}'>"
    						+"<input name='policeName' id='policeName' type='radio' value='${item.id}' onclick='selectPolice(\"${item.id}\",\"${item.name}\")'/>${item.name}</br>"
    						+"</c:forEach>"
    						+"</div>");
                }
                
                function selectPolice(id,name){
                	$("#addUserName").val(name);
                	$("#policeId").val(id);
                }

            </script>

						<style scoped>
#employeeForm ul {
	list-style-type: none;
	margin: 0;
	padding: 0;
}

#employeeForm li {
	margin-top: 10px;
}

label {
	display:inline;
	padding-right: 3px;
	width: 60px;
}

#employeeForm .accept input{
vertical-align:sub;
*vertical-align:middle;
vertical-align:middle\9\0;
}

span.k-tooltip {
	margin-left: 6px;
}

.demo-section {
	width: 290px;
}

.actions {
	padding-left: 106px;
	padding-top: 10px;
}
</style>

						<!-- 左结束-->
					</div>
				</div>

				<div id="right-pane">
					<div class="pane-content">
						<!-- 右开始 -->
						<!-- 跨机构授权开始 -->

						<div class="box-col" id="addRoleBox">
							<h4 id="roleTitle">角色权限</h4>
							<!-- 添加遮盖层 -->
                             <div class="shadow" id="zhegaiceng">
								<img src="<%=basePath%>images/images/zhedang.png" style="height:800px">
   							</div>
							<div id="addRoleTreeview"></div>
						</div>
						<div id="box">
						     <h4 id="organTitle"></h4>
                             <div id="treeview"></div>
		                </div>
		                <div id="policeBox">
						     <h4 id="policeTitle"></h4>
                             <div id="policeview"></div>
		                </div>
						<!-- 跨机构授权结束 -->
						<!-- 右结束-->
					</div>
				</div>
			</div>
	</div>
<style type="text/css">
	   		.shadow{
	   		position:absolute;
	   		left:40;top:50;  
	   		z-index:10; 
	   		width:80%; 
	   		height:100%;
	   		}
	   </style>
	<script>
                $(document).ready(function() {
                	var amodulesId="";
      				 $.ajax({
      						url:"<%=basePath%>role/getModuleTree.do",
      						type:"post",
      						data:{
      							roleId:"",
      							random:Math.random()
      						},
      						dataType:"json",
      						success:function(rsp){
      							var addRoleData =JSON.stringify(rsp.data);
      							
      							$("#addRoleTreeview").kendoTreeView({
      								//select: onSelect,//点击触发事件
      							    checkboxes: {
      							        checkChildren: true//允许复选框多选
      							    },
      							    check: addOnCheck,//check复选框
      							    dataSource: [eval('(' + addRoleData + ')')]
      							}).data("kendoTreeView");
      						}
      					});
      				 
      				function addOnCheck(e) {
      		 			text=this.text(e.node);
      		            var checkedNodes = [],
      		              treeView = $("#addRoleTreeview").data("kendoTreeView"),message;

      		            //treeToJson(treeView.dataSource.view());
      		            
      		            //addCheckedNodeIds(treeView.dataSource.view(), checkedNodes);

      		            if (checkedNodes.length > 0) {
      		                message = checkedNodes.join(",");
      		            } else {
      		                message = "";
      		            }
      		            amodulesId=message;
      		            //$("#result").html(message);
      		        }
                	
                    $("#horizontal").kendoSplitter({
                        panes: [
                            { collapsible: true, size: "320px" },
                            { collapsible: true, size: "320px" }
                        ]
                    });
                });
            </script>

	<style scoped>

.pane-content {
	padding: 0 10px;
}
</style>

</body>
</html>