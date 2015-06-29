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
<title>扁平化指挥调度系统</title>
<%@ include file="../../emulateIE.jsp" %>
</head>

<body>
	<div id="vertical">
	<div id="horizontal" style="height:104%; width:100%;">
				<div id="left-pane">
			<div class="pane-content">
				<!-- 左开始 -->
				<div class="demo-section k-header">
					<form id="employeeForm" data-role="validator"
						novalidate="novalidate">
						<h4>密码修改</h4>
						<input value="${user.userId}" id="userId" name="userId" type="hidden"/>
						<ul>
							<li>&nbsp;</li>
							<li><label>&nbsp; 帐&nbsp;&nbsp;&nbsp;&nbsp;号:</label> ${user.loginName}</li>
							<%-- <li><label>&nbsp; 姓&nbsp;&nbsp;&nbsp;&nbsp;名:</label> ${user.userName}</li> --%>
							<li><label for="oldPwd"><span style="color: red;">* </span>旧密码:</label> <input type="password"
								class="k-textbox" name="oldPwd" id="oldPwd"
								 /></li>
							<li><label for="newPwd"><span style="color: red;">* </span>新密码:</label> <input type="password"
								class="k-textbox" name="newPwd" id="newPwd"
								 /></li>
							<li><label for="cfmPwd"><span style="color: red;">* </span>确认密码:</label> <input type="password"
								class="k-textbox" name="cfmPwd" id="cfmPwd"
								 /></li>
							<li class="actions">
								<button type="button" class="ty-tcc-btn-no" style="margin-right: 15px;" onclick="uclose()">取消</button>
								<button type="button"  class="ty-tcc-btn-ok" onclick="save()">确定</button>
							</li>
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
	height: 300px;
	width: 440px;
	margin:0 auto;
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
	padding-right:3px;
	margin-left:60px;
	width:80px;
}

span.k-tooltip {
	margin-left: 6px;
}

.demo-section {
	width: 400px;
	height:270px;
}

.actions {
	padding-left: 100px;
	padding-top: 20px;
}
</style>

	<script>
                $(document).ready(function() {
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
                function save(e) {
                	if($("#oldPwd").val()==""){
                    	$("body").popjs({"title":"提示","content":"旧密码不能为空！"});
             		    return false;
                    }
                	if($("#newPwd").val()==""){
                    	$("body").popjs({"title":"提示","content":"新密码不能为空！"});
             		    return false;
                    }
                    if($("#cfmPwd").val()==""){
                    	$("body").popjs({"title":"提示","content":"确认密码不能为空！"});
             		    return false;
                    }
                    updatePwd();
                }
                
                /**
                *	更新密码
                **/
             function updatePwd(){
                	$.ajax({
           	  			url:"<%=basePath%>admin/updatePassword.do",
           	  			type:"post",
           	  			dataType:"json",
           	  			data:{
           	  				userId:$("#userId").val(),
           	  				oldPwd:$("#oldPwd").val(),	
           	  			    loginName:$("#loginName").val(),
           	  				newPwd:$("#newPwd").val(),
           	  				cfmPwd:$("#cfmPwd").val(),
           	  				sessionId:$("#token").val(),
           	  				random: Math.random()
           	  			},
           		  		 success:function(msg){
           		  			if(msg.code==200){
           		  				$("body").popjs({"title":"提示","content":msg.description,"callback":function(){
           		  					window.parent.$("#mydialog").tyWindow.close();
           		  				}});
           		  				
           		  			}else{
           		  				$("body").popjs({"title":"提示","content":msg.description});
           		  			}
           		  		}
           	  		});
          		}
               	/**
               	* 关闭弹出窗口
               	**/
             function uclose(){
	  				window.parent.$("#mydialog").tyWindow.close();
                }
                
                $(".closeButton").kendoButton({
                    icon: "close"
                });
            </script>
</body>
</html>
