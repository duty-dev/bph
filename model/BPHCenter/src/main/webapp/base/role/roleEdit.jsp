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
			<div id="horizontal">
				<div id="left-pane">
					<div class="pane-content">
						<!-- 左开始 -->
						<div class="demo-section k-header">
								<h4>角色信息</h4>
								<ul>
									<input id="eRoleId" type="hidden" value="${role.id}"/>
									<li class="ty-input"><span class="ty-input-warn">*</span><label for="eRoleName" class="fl">角色名称:</label> 
										<input type="text" class="k-textbox" name="editRoleName" id="editRoleName" value="${role.name}" style="width:65%;"/><em class="ty-input-end"></em>
										
									</li>
									<li class="ty-input"><span class="ty-input-warn">*</span><label for="eRoleNote" class="fl">角色描述:</label> 
										<textarea class="ty-edit-txtarea" id="editRoleNote">${role.note}</textarea>
									</li>
									<li class="ty-input actions">
										<button type="button" class="ty-button ty-button-te" onclick="edit(this);" data-click='edit'>提交</button></li>
								</ul>
						</div>
						<!-- 左结束-->
					</div>
				</div>
				
				<div id="right-pane">
					<div class="pane-content">
						<!-- 右开始 -->
						<!-- 跨机构授权开始 -->
						<div class="box-col" id="editRoleBox">
							<h4>角色权限</h4>
							<div id="editRoleTreeview"></div>
						</div>
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
	width: 60px;
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

<script>
		var emoduleIds="";
        $(document).ready(function() {
            var window = $("#window"),
                undo = $("#undo")
                        .bind("click", function() {
                            $("body").tyWindow.open();
                        });

            
            $("#horizontal").kendoSplitter({
                panes: [
                    { collapsible: true },
                    { collapsible: true }
                ]
            });
            
            /* var container = $("#employeeForm");
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
            }); */
            
            $.ajax({
					url:"<%=basePath%>role/getModuleTree.do",
					type:"post",
					data:{
						roleId:$("#eRoleId").val(),
						sessionId:$("#token").val(),
						random:Math.random()
					},
					dataType:"json",
					success:function(rsp){
						var editRoleData =JSON.stringify(rsp.data);
						$("#editRoleTreeview").kendoTreeView({
							//select: onSelect,//点击触发事件
						    checkboxes: {
						        checkChildren: true//允许复选框多选
						    },
						    check: editOnCheck,//check复选框
						    dataSource: [eval('(' + editRoleData + ')')]
						}).data("kendoTreeView");
					}
				});
            $("#right-pane").mCustomScrollbar({scrollButtons:{enable:true},advanced:{ updateOnContentResize: true } });
        });
        
        function edit(e) {
        	if($.trim($("#editRoleName").val())==""){
           		$("body").popjs({"title":"提示","content":"角色名称不能为空"});
    		    return false;
           }
    	   if($.trim($("#editRoleNote").val())==""){
           		$("body").popjs({"title":"提示","content":"角色备注不能为空"});
    		    return false;
           }
    	   editRole(); 
        }
        
        /* 修改角色 */
   	 function editRole(){
   	 	var roleId=$("#eRoleId").val();
   		var roleName=$.trim($("#editRoleName").val());
   		var roleNote=$.trim($("#editRoleNote").val());
   		$.ajax({
   			url:"<%=basePath%>role/updateRole.do",
   			type:"post",
   			dataType:"json",
   			data:{
   				roleId:roleId,
   				roleName:roleName,
   				roleNote:roleNote,
   				modulesId:emoduleIds,
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
    	  // show checked node IDs on datasource change
       function editOnCheck(e) {
           var checkedNodes = [],
              treeView = $("#editRoleTreeview").data("kendoTreeView"),message;

           editCheckedNodeIds(treeView.dataSource.view(), checkedNodes);

           if (checkedNodes.length > 0) {
               message = checkedNodes.join(",");
           } else {
               message = "";
           }
           emoduleIds=message;
           //$("#result").html(message);
       }
 
 
// function that gathers IDs of checked nodes
      function editCheckedNodeIds(nodes, checkedNodes) {
          for (var i = 0; i < nodes.length; i++) {
              if (nodes[i].checked) {
                  checkedNodes.push(nodes[i].id);
              } 
              if (nodes[i].hasChildren) {
            	  editCheckedNodeIds(nodes[i].children.view(), checkedNodes);
              }
          }
      }
</script>
</body>
</html>
            

