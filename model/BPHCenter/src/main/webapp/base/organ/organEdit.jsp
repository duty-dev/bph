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

<body class="ty-body">
	<div id="vertical">
			<div id="horizontal">
				<div id="left-pane">
					<div class="pane-content">
						<!-- 左开始 -->
						<div class="demo-section k-header">
							<form id="employeeForm" data-role="validator"
								novalidate="novalidate">
								<h4>机构信息</h4>
								<ul>
									<input id="id" value="${organ.id}" type="hidden"/>
									<li><span class="ty-input-warn ml100">*</span><label for="eOrganName">机构名称:</label> <input
										type="text" class="k-textbox" name="editOrganName"
										id="editOrganName"  value="${organ.name}"/>
									</li>
									<li><span class="ty-input-warn ml100">*</span><label for="eOrganCode">机构编码:</label> <input
										type="text" class="k-textbox" name="editOrganCode"
										id="editOrganCode"  value="${organ.code}"/>
									</li>
									<li><span class="ty-input-warn ml100">*</span><label for="eShortName">机构简称:</label> <input
										type="text" class="k-textbox" name="editShortName"
										id="editShortName"  value="${organ.shortName}"/>
									</li>
									<li class="options"><span class="ty-input-warn ml100">*</span><label for="aOrganType">机构类型:</label>
										<input id="typeCode" value="${organ.orgTypeCode}" type="hidden"/>
										<select  id="editOrganType" name="editOrganType">
								    		<option value="1">市局</option>
								    		<option value="2">分局</option>
								    		<option value="3">市局直属</option>
								    		<option value="4">分局机关</option>
								    		<option value="5">派出所</option>
	                					</select>
	                				</li>
									<li><span class="ty-input-warn ml100"></span><label for="eParentName">上级机构:</label> 
										<input type="hidden" id="editParentId" value="${organ.parentId}"/>
										<input
										type="text" class="k-textbox" name="editParentName"
										id="editParentName" value="${organ.parentName}" readonly="readonly" style="color:#929496;" /></li>
									<li><span class="ty-input-warn ml100" style="margin-top:18px;">*</span><label for="eOrganNote">机构备注:</label> 
										<textarea class="ty-edit-txtarea" id="editOrganNote" name="editOrganNote">${organ.note}</textarea>
										</li>
									<li class="actions">
										<button type="button" class="ty-button ty-button-te" onclick="edit(this);" data-click='edit'>修改</button></li>
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
	width: 490px;
	height:350px;
}

.actions {
	padding-left: 206px;
	padding-top: 10px;
}
.ml100{margin-left:100px;}
</style>

<script>
                $(document).ready(function() {
                	$("#editOrganType").val($("#typeCode").val()).kendoComboBox().prev().find(".k-input").attr("readonly",true);
                	//$("#editOrganType").val($("#typeCode").val());
                    var window = $("#window"),
                        undo = $("#undo")
                                .bind("click", function() {
                                    $("body").tyWindow.open();
                                });
                    
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
                
                function edit(e) {
                	if($.trim($("#editOrganName").val())==""){
                    	$("body").popjs({"title":"提示","content":"机构名不能为空！"});
             		    return false;
                    }
                    if($.trim($("#editShortName").val())==""){
                    	$("body").popjs({"title":"提示","content":"机构简称不能为空！"});
             		    return false;
                    }
                    if($.trim($("#editOrganType").val())==""){
                    	$("body").popjs({"title":"提示","content":"请选择机构类型！"});
             		    return false;
                    }
                    if($.trim($("#editOrganNote").val())==""){
                    	$("body").popjs({"title":"提示","content":"请填写机构备注！"});
             		    return false;
                    }
                    if($.trim($("#editOrganCode").val())==""){
                    	$("body").popjs({"title":"提示","content":"机构编码不能为空！"});
             		    return false;
                    }
                    if(isNaN($.trim($("#editOrganCode").val()))){
           		        $("body").popjs({"title":"提示","content":"机构编码只能为数字！"});
           		        return false;
             		 }
                    
                    editOrgan();
                }
                
                function editOrgan(){
             		 $.ajax({
             			url:"<%=basePath%>web/organx/modifyOrgan.do",
             			type:"post",
             			dataType:"json",
             			data:{
             				organId:$("#id").val(),
             				code:$.trim($("#editOrganCode").val()),
             				name:$.trim($("#editOrganName").val()),
             				shortName:$.trim($("#editShortName").val()),
             				parentId:$("#editParentId").val(),
             				orgTypeCode:$.trim($("#editOrganType").val()),
             				sessionId:$("#token").val(),
             				note:$.trim($("#editOrganNote").val())
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
