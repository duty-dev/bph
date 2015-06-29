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
			<div id="horizontal" style="height:440px;">
				<div id="left-pane">
					<div class="pane-content">
						<!-- 左开始 -->
						<div class="demo-section k-header">
							<form id="employeeForm" data-role="validator" novalidate="novalidate">
								<h4>卡点信息</h4>
								<ul>
									<li>
										<input type="hidden" value="${organId}" id="orgId" name="orgId" />
										<input id="token" type="hidden" value="${requestScope.sessionId}"/></li></li>
									<li><span class="ty-input-warn" style="width:10px;margin-left:-10px;">*</span><label for="name">卡点名称:</label> 
										<input type="text" class="k-textbox" name="name" id="name" /></li>
									<li><label for="managerName">负责人:</label> 
										<input type="text" class="k-textbox" name="managerName" id="managerName" readonly="readonly" />
										<button type="button" class="k-button mt4 ml68" onclick="loadPolice();">选择警员</button></li>
										<!-- <button type="button" class="k-button" onclick="clearPolice();">清空警员</button></li> -->
									<li><input type="hidden" class="k-textbox" name="manager" id="manager" /></li>
									<li><span class="ty-input-warn" style="width:10px;margin-left:-10px;">*</span><label for="aCardType">卡点类型:</label> 
										<select id="addCardType" name="addCardType" width=200px>
											<option value="">---请选择类型---</option>
											<option value="1">卡点</option>
											<option value="2">机动组</option>
										</select>
									</li>
									<li><label for="telephone">联系电话:</label> 
										<input type="text" class="k-textbox" name="telephone" id="telephone"/></li>
									<li><span class="ty-input-warn" style="width:10px;margin-left:-10px;">*</span><label for="intercomGroupId">通讯组号:</label> 
										<input type="text" class="k-textbox" name="intercomGroupId" id="intercomGroupId" 
											   pattern="^[0-9]+$" validationMessage='通讯组号由一串数字组成' maxlength="8" />
									<li><label for="equipment">携带装备:</label> 
										<input type="text" class="k-textbox" name="equipment" id="equipment" /></li>
									<li><label for="assignment">职责描述:</label> 
										<input type="text" class="k-textbox" name="assignment" id="assignment" /></li>
									<li><label for="cameraName">关联天网:</label> 
										<input type="text" class="k-textbox" name="cameraName" id="cameraName" readonly="readonly" />
										<button type="button" class="k-button mt4 ml68" onclick="loadCamera();">选择天网</button></li>
									<li><input type="hidden" class="k-textbox" name="camera" id="camera" /></li>
									<li><label for="circleLayer">圈层选择:</label> 
										<select id="circleLayer" class="" name="circleLayer" value="${cardPoint.circleLayer}">
							    		</select></li>
									<li class="actions">
										<button type="button" class="ty-button ty-button-te" onclick='editCardPoint()'>提交</button>
									</li>
								</ul>
							</form>
						</div>

<script type="text/javascript">
	
	var cameraArray;
	$(document).ready(function() {
		loadCircle();
		$("#name").focus();
		$("#addCardType").kendoComboBox().prev().find(".k-input").attr("readonly",true);
		$("#circleLayer").kendoComboBox().prev().find(".k-input").attr("readonly",true);
	});
	/* 清除警员暂时屏蔽 */
	/* function clearPolice(){
		$("#managerName").val("");
		tempItem.splice(0,tempItem.length);//清空数组 
   		tempItem.splice(0,tempItem2.length);
	} */
	
	/* 加载圈层 */
	function loadCircle(){
		$("#circleLayer").append(
				"<c:forEach var='item' items='${circleList}'>"
				+"<option value='${item.id}'>${item.name}</option>"
				+"</c:forEach>");
	}
	
	/* 添加卡点 */
	function editCardPoint(){
		if($.trim($("#name").val())==""){
			$("body").popjs({"title":"提示","content":"卡点名称不能为空！"});
			return false;
		} 
		if($.trim($("#addCardType").val())==""){
			$("body").popjs({"title":"提示","content":"请选择卡点类型"});
			return false;
		}
		if($.trim($("#intercomGroupId").val())==""){
			$("body").popjs({"title":"提示","content":"请填写通讯组号！"});
			return false;
		}
		if($.trim($("#circleLayer").val())==""){
			$("body").popjs({"title":"提示","content":"请选择圈层！"});
			return false;
		}
		$.ajax({
			url:"<%=basePath%>web/cardPoint/addCardPoint.do",
			type:"post",
			dataType:"json",
			data:{
				organId: 		$("#orgId").val(),
				name:			$.trim($("#name").val()),
				manager:		$.trim($("#manager").val()),
				telephone:		$.trim($("#telephone").val()),
				intercomGroupId:$("#intercomGroupId").val(),
				equipment:		$.trim($("#equipment").val()),
				assignment:		$.trim($("#assignment").val()),
				camera:			$.trim($("#camera").val()),
				cardType:		$.trim($("#addCardType").val()),
				circleLayer:	$.trim($("#circleLayer").val()),
				sessionId:$("#token").val(),
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
     	  
     	   $("#cameraBoxPane").css("display","none");//天网列表隐藏 
     	   
     	   
     	   
    		$("#managerName").val("");
    		$("#manager").val("");
    		tempItem.splice(0,tempItem.length);//清空数组 
    		tempItem.splice(0,tempItem2.length);
    		
$("#policeview").remove();
$("#policeTitle").remove();
$("#cameraview").remove();
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
           
	/* 关联天网 */
	function loadCamera(){
		$("#policeTitle").remove();
		$("#cameraview").remove();
		 var data = new kendo.data.HierarchicalDataSource({
		        transport:{
					read:{
						url: "<%=basePath%>web/organx/lazyOrganList1.do",
						type : "post",
						data : {
							searchName:"",
							sessionId:$("#token").val(),
							random : Math.random()
						},
						dataType: "json"
					}
				},
				schema: {
		 			model: {
						hasChildren:"hasChild",
		           	}
				}
			});
		 	$("#policeview").kendoTreeView({
				select: showCameraList,//点击触发事件
				dataSource: data
			});
	}
	
	function seachCamera(){
		var cameraName=$.trim($("#cameraNameSS").val());
		if(cameraName==""){
			$("#cameraBox").remove();
			$("#cameraBoxPane").append("<div id='cameraBox'></div>");
			$(cameraArray).each(function(){
				var obj=this;
				$("#cameraBox").append("<div id='cameraview'>"
						+"<input name='cameraNames' id='cameraNames' type='checkbox' value='"+obj.id+"' onclick='selectCamera(this,\""+obj.id+"\",\""+obj.name+"\")'/>"+obj.name+"</br>"
						+"</div>");
			});
		}else{
			var cameArray=new Array();
			for(var i=0;i<cameraArray.length;i++){
				if(cameraArray[i].name.indexOf(cameraName)>-1){
					cameArray.push(cameraArray[i]);
				}
			}
			$("#cameraBox").remove();
			$("#cameraBoxPane").append("<div id='cameraBox'></div>");
			$(cameArray).each(function(){
				var obj=this;
				$("#cameraBox").append("<div id='cameraview'>"
						+"<input name='cameraNames' id='cameraNames' type='checkbox' value='"+obj.id+"' onclick='selectCamera(this,\""+obj.id+"\",\""+obj.name+"\")'/>"+obj.name+"</br>"
						+"</div>");
			});
		}
	}
	var tempItem3 = new Array();
	var tempItem4 = new Array();
	function selectCamera(o,id,name){
         	if($(o).prop("checked")){
         		if(tempItem3.length>0){
          		for(var i = 0;i<tempItem3.length;i++){
          			if(tempItem3[i] != name){
          				tempItem3.push(name);
          				break;
          			}
          		}
         		}else{
         			tempItem3.push(name);
         		}
         		if(tempItem4.length>0){
          		for(var i = 0;i<tempItem4.length;i++){
          			if(tempItem4[i] != id){
          				tempItem4.push(id);
          				break;
          			}
          		}
         		}else{
         			tempItem4.push(id);
         		}
         	}else{
         		for(var i = 0;i<tempItem3.length;i++){
         			if(tempItem3[i] == name){
         				tempItem3.baoremove(i);
         			}
         		}
         		for(var i = 0;i<tempItem4.length;i++){
         			if(tempItem4[i] == id){
         				tempItem4.baoremove(i);
         			}
         		}
         	}
         	$("#cameraName").val(tempItem3);
         	$("#camera").val(tempItem4);
	}
	
	function showCameraList(e){
		//选择节点事件
 		var pview=$('#policeview').data('kendoTreeView');
 		//获取选中节点的数据
 		var data = pview.dataItem(e.node);
 		var orgd=data.id;
		$.ajax({
			url:"<%=basePath%>/web/cardPoint/getCameraList.do",
			type:"post",
			dataType:"json",
			data:{
				organId:orgd
			},
			success:function(msg){
				if(msg.data != null && msg.data != ""){
					cameraArray=msg.data;
					
					$("#cameraBoxPane").css("display","block");
					$("#cameraBox").remove();
					$("#cameraBoxPane").append("<div id='cameraBox'></div>");
					
					$(msg.data).each(function(){
						var obj = this;
						$("#cameraBox").append("<div id='cameraview'>"
								+"<input name='cameraNames' id='cameraNames' type='checkbox' value='"+obj.id+"' onclick='selectCamera(this,\""+obj.id+"\",\""+obj.name+"\")'/>"+obj.name+"</br>"
								+"</div>");
					});
				}else{
					$("#cameraBoxPane").css("display","none");
				}
			}
		});
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

		<!-- 左结束-->
	</div>
</div>

<div id="right-pane">
	<div class="pane-content">
		<!-- 右开始 -->
		<!-- <div id="box">
		     <h4 id="organTitle"></h4>
             <div id="treeview"></div>
        </div> -->
        <div id="policeBox">
		     <h4 id="policeTitle"></h4>
             <div id="policeview"></div>
        </div>
		<!-- 跨机构授权结束 -->
		<!-- 右结束-->
	</div>
</div>
<!-- 最右边点位展示 -->
<div id="right1-pane">
	<div class="pane-content" id="cameraBoxPane" style="display:none;">
	  	<span class="fl ty-full">天网列表</span>
		<div class="ty-input">
            <input id="cameraNameSS" placeholder="等待输入..." style="width:98%" class="k-textbox ty-left-search"/>
        </div>
        <button class="ty-left-search-btn mt8" style="box-sizing:border-box" onClick="seachCamera()">搜索</button>
      <br><br>
        
        <div id="cameraBox">
		     <!-- <h4 id="cameraTitle"></h4>
             <div id="cameraview"></div> -->
        </div>
		<!-- 跨机构授权结束 -->
		<!-- 右结束-->
	</div>
</div>
</div>
</div>

<script>
$(document).ready(function() {
	/* 警员选择 */
	loadPolice();
	
	/* function addOnCheck(e) {
		text=this.text(e.node);
		var checkedNodes = [],
		treeView = $("#addRoleTreeview").data("kendoTreeView"),message;
		//treeToJson(treeView.dataSource.view());
		addCheckedNodeIds(treeView.dataSource.view(), checkedNodes);

		if (checkedNodes.length > 0) {
			message = checkedNodes.join(",");
		} else {
			message = "";
		}
		amodulesId=message;
	} */
      	
    $("#horizontal").kendoSplitter({
        panes: [
            { collapsible: true ,size:"310px"},
            { collapsible: true },
            { collapsible: true ,size:"250px"}
        ]
    });
});
</script>

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