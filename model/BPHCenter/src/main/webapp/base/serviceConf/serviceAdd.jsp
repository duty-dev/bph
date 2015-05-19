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

<body>
	<div id="vertical">
			<div id="horizontal" style="height: 100%; width: 100%;">
				<div id="left-pane">
					<div class="pane-content">
					<h4>服务信息</h4>
						<!-- 左开始 -->
						<div class="demo-section k-header" style="margin-left:160px;">
							<form id="employeeForm" data-role="validator"
								novalidate="novalidate">
								
								<ul>
									<li class="ty-input"><span class="ty-input-warn ml150">*</span><label for="aServiceName">服务名称:</label>
									<input type="text" class="k-textbox" name="addServiceName" id="addServiceName" style="width:60%" /><em class="ty-input-end"></em>
									<li class="ty-input"><span class="ty-input-warn ml150">*</span><label for="aServiceIp">服务IP:</label> 
									<input type="text" class="k-textbox" name="addServiceIp" id="addServiceIp" style="width:60%" /><em class="ty-input-end"></em>
									<li class="ty-input"><span class="ty-input-warn ml150">*</span><label for="aServiceType">服务类型:</label> 
										<select id="addServiceType" name="addServiceType">
											<option value="">---请选择服务类型---</option>
											<option value="1">mq</option>
											<option value="2">ftp</option>
											<option value="3">gps</option>
										</select>
									<li class="ty-input"><span class="ty-input-warn ml150">*</span><label for="aServicePort">服务端口:</label> 
									<input type="text" class="k-textbox" name="addServicePort" id="addServicePort" style="width:60%" /><em class="ty-input-end"></em>
									<li class="ty-input"><span class="ty-input-warn ml150">*</span><label for="aServiceAccount">用户名:</label> 
									<input type="text" class="k-textbox" name="addServiceAccount" id="addServiceAccount" style="width:60%" /><em class="ty-input-end"></em>
									<li class="ty-input"><span class="ty-input-warn ml150">*</span><label for="aServicePwd">用户密码:</label> 
									<input type="password" class="k-textbox" name="addServicePwd" id="addServicePwd" style="width:60%" /><em class="ty-input-end"></em>
									<li class="ty-input"><span class="ty-input-warn ml150">*</span><label for="aServiceVersion">服务版本:</label> 
									<input type="text" class="k-textbox" name="addServiceVersion" id="addServiceVersion" style="width:60%" /><em class="ty-input-end"></em>
									<li class="ty-input"><span class="ty-input-warn ml150">*</span><label for="aServiceExchange">交换机名:</label> 
									<input type="text" class="k-textbox" name="addServiceExchange" id="addServiceExchange" style="width:60%" /><em class="ty-input-end"></em>
									<li class="ty-input actions">
										<button type="button" data-role="button" onclick="saveService()">提交</button></li>
								</ul>
							</form>
						</div>
						<!-- 左结束-->
					</div>
				</div>
			</div>
	</div>

<style scoped>
#vertical {
	height: 350px;
	width: 580px;
	margin: 0 auto;
}

.pane-content {
	padding: 0 10px;
}

#employeeForm ul {
	list-style-type: none;
	margin: 0;
	padding: 0;
}

#employeeForm li {
	margin-top: 10px;
}

label {
	display: inline-block;
	padding-right: 3px;
	width: 80px;
}

span.k-tooltip {
	margin-left: 6px;
}

.demo-section {
	height:340px;
	width: 300px;
}

.actions {
	padding-left: 106px;
	padding-top: 10px;
}
</style>

<script>

				$(document).ready(function() {
					$("#addServiceType").kendoComboBox().prev().find(".k-input").attr("readonly",true);
					$("#addServiceName").focus();
				    var container = $("#employeeForm");
				    kendo.init(container);
				    container.kendoValidator({
				        rules: {
				            validmask: function (input) {
				                if (input.is("[data-validmask-msg]") && input.val() != "") {
				                    var maskedtextbox = input.data("kendoMaskedTextBox");
				                    return maskedtextbox.value().indexOf(maskedtextbox.options.promptChar) === -1;
				                }
				                return true;
				            }
				        }
				    });
				});

               function saveService(){
            	   if($.trim($("#addServiceName").val())==""){
                  		$("body").popjs({"title":"提示","content":"请输入服务名称"});
           		    return false;
                  }
           	   if($.trim($("#addServiceIp").val())==""){
                 		$("body").popjs({"title":"提示","content":"请输入服务IP"});
          		     return false;
                  }
           	   if($.trim($("#addServiceType").val())==""){
                		$("body").popjs({"title":"提示","content":"请选择服务类型"});
         		     return false;
                  }
           	   if($.trim($("#addServicePort").val())==""){
               		$("body").popjs({"title":"提示","content":"服务端口不能为空"});
        		    	return false;
                  }
           	   if($.trim($("#addServiceAccount").val())==""){
              		$("body").popjs({"title":"提示","content":"请输入用户名"});
       		    	return false;
                  }
           	   if($.trim($("#addServicePwd").val())==""){
                 		$("body").popjs({"title":"提示","content":"请输入用户密码"});
          		    return false;
                  }
           	   if($.trim($("#addServiceVersion").val())==""){
                 		$("body").popjs({"title":"提示","content":"请输入服务版本"});
          		    	return false;
                  }
                  if($.trim($("#addServiceExchange").val())==""){
                		$("body").popjs({"title":"提示","content":"请输入交换机名称"});
         		    	return false;
                  } 
                  addService();
               } 
                /* 添加服务 */
       	   	 function addService(){
       	   		var serviceName=$.trim($("#addServiceName").val());
       	   		var addServiceIp=$.trim($("#addServiceIp").val());
       	   		var addServiceType=$.trim($("#addServiceType").val());
       	   		var addServicePort=$.trim($("#addServicePort").val());
       	   		var addServiceAccount=$.trim($("#addServiceAccount").val());
       	   		var addServicePwd=$.trim($("#addServicePwd").val());
       	   		var addServiceVersion=$.trim($("#addServiceVersion").val());
       	   		var addServiceExchange=$.trim($("#addServiceExchange").val());
       	   		$.ajax({
       	   			url:"<%=basePath%>serviceSet/insert.do",
       	   			type:"post",
       	   			dataType:"json",
       	   			data:{
       	   				serviceName:serviceName,
       	   				serviceIp:addServiceIp,
       	   				serviceType:addServiceType,
       	   				servicePort:addServicePort,
       	   				serviceAccount:addServiceAccount,
       	   				servicePwd:addServicePwd,
       	   				serviceVersion:addServiceVersion,
       	   				exchangeName:addServiceExchange,
       	   				sessionId:$("#token").val()
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
            </script>
        </body>
        </html>   

