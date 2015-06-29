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
			<div id="horizontal" style="height:468px;">
				<div id="left-pane" style="float:left;width:70%">
					<div class="pane-content">
						<!-- 左开始 -->
						<div class="demo-section k-header">
							<form id="employeeForm" data-role="validator" novalidate="novalidate">
								<h4>巡区信息</h4>
								<ul>
									<li>
										<input type="hidden" value="${organId}" id="orgId" name="orgId" />
										<input id="token" type="hidden" value="${requestScope.sessionId}"/></li>
										<li><span class="ty-input-warn" style="width:10px;margin-left:-10px;">*</span><label for="name">所属机构:</label> ${organName}</li>
									<li><span class="ty-input-warn" style="width:10px;margin-left:-10px;">*</span><label for="name">巡区名称:</label> 
										<input type="text" class="k-textbox" name="name" id="name" /></li>
									<li><label for="managerName">负责人:</label> 
										<input type="text" class="k-textbox" name="managerName" id="managerName" readonly="readonly" />
										<button type="button" class="k-button mt4 ml68" onclick="loadPolice();">选择警员</button></li>
									<li><input type="hidden" class="k-textbox" name="manager" id="manager" /></li>
									<li><label for="telephone">联系电话:</label> 
										<input type="text" class="k-textbox" name="tel" id="tel"/></li>
									<li><label for="equipment">巡区人数:</label> 
										<input type="text" class="k-textbox" name="nnt" id="nnt" /></li>
									<li class="actions">
										<button type="button" class="ty-button ty-button-te" onclick='addArea()'>提交</button>
									</li>
								</ul>
							</form>
						</div>

<script type="text/javascript">
	$(document).ready(function() {
		$("#name").focus();
	});
	/* 添加卡点 */
	function addArea(){
		if($.trim($("#name").val())==""){
			$("body").popjs({"title":"提示","content":"巡区名称不能为空！"});
			return false;
		} 
		$.ajax({
			url:"<%=basePath%>area/addArea.do",
			type:"post",
			dataType:"json",
			data:{
				organId:$("#organId").val(),
				areaName:$.trim($("#name").val()),
				areaType:1,
				createUserId:1,
				tel:$.trim($("#tel").val()),
				nnt:$("#nnt").val(),
				relationUserIds:$.trim($("#manager").val()),
				sessionId:$("#token").val(),
			},
			 success:function(msg){
				if(msg.code==200){
					window.parent.addAreaInfoSuccess();
				}else{
					$("body").popjs({"title":"提示","content":msg.description});
				}
			}
		});
	}
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

    var tempItem = new Array();
    var tempItem2 = new Array();
        /* 卡点负责人 */
        function loadPolice(){
    		$("#managerName").val("");
    		$("#manager").val("");
    		tempItem.splice(0,tempItem.length);//清空数组 
    		tempItem.splice(0,tempItem2.length);
    		
$("#policeview").remove();
$("#policeTitle").remove();
$("#policeBox").append("<h4 id='policeTitle'>警员绑定</h4><div id='policeview'>"
		+"<c:forEach var='item' items='${policeList}'>"
		+"<input name='policeName' class='policeName' id='policeName' type='checkbox' value='${item.id}' onclick='selectPolice(this,\"${item.id}\",\"${item.name}\")'/>${item.name}</br>"
		+"</c:forEach>"
		+"</div>");
        }
        function selectPolice(o,id,name){
        	if($(o).prop("checked")){
        		if(tempItem.length>0){
        			var size = tempItem.length;
         		for(var i = 0; i<size; i++){
         			if(tempItem[i] != name){
         				tempItem.push(name);
         				break;
         			}
         		}
        		}else{
        			tempItem.push(name);
        		}
        		if(tempItem2.length>0){
         		for(var i = 0;i<tempItem2.length;i++){
         			if(tempItem2[i] != id){
         				tempItem2.push(id);
         				break;
         			}
         		}
        		}else{
        			tempItem2.push(id);
        		}
        	}else{
        		for(var i = 0;i<tempItem.length;i++){
        			if(tempItem[i] == name){
        				tempItem.baoremove(i);
        			}
        		}
        		for(var i = 0;i<tempItem2.length;i++){
        			if(tempItem2[i] == id){
        				tempItem2.baoremove(i);
        			}
        		}
        	}
        	$("#managerName").val(tempItem);
        	$("#manager").val(tempItem2);
        }
        Array.prototype.baoremove = function(dx){
	    if(isNaN(dx)||dx>this.length){
	    	return false;
	    }
	          this.splice(dx,1);
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
	display: inline-block;
	padding-right: 3px;
	width: 60px;
}

span.k-tooltip {
	margin-left: 6px;
}

.demo-section {
	width: 278px;
}

.actions {
	padding-left: 106px;
	padding-top: 10px;
}
</style>
</div>
</div>
<div id="right-pane" style="float:right;width:28%">
	<div class="pane-content">
		<!-- 右开始 -->
		<!-- <div id="box">
		     <h4 id="organTitle"></h4>
             <div id="treeview"></div>
        </div> -->
        <div class="fr" id="policeBox">
		     <h4 id="policeTitle"></h4>
             <div id="policeview"></div>
        </div>
		<!-- 跨机构授权结束 -->
		<!-- 右结束-->
	</div>
</div>
<!-- 最右边点位展示 -->
<div id="right1-pane">
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
</style>

</body>
</html>