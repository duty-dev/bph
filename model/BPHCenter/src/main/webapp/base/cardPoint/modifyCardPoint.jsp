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
				<div id="left-pane">
					<div class="pane-content">
						<!-- 左开始 -->
						<div class="demo-section k-header">
							<form id="employeeForm" data-role="validator" novalidate="novalidate">
								<h4>卡点信息</h4>
								<input type="hidden" value="${organId}" id="orgId" name="orgId" />
								<input id="token" type="hidden" value="${requestScope.sessionId}"/></li></li>
								<input value="${user.userId}" id="userId" name="userId" type="hidden"/>
								<ul>
									<li><input type="hidden" value="${cardPoint.organId}" id="organId" name="organId" />
										<input id="token" type="hidden" value="${requestScope.sessionId}"/></li>
									<li><input type="hidden" id="cardPointId" name="cardPointId" value="${cardPoint.cardPointId}" /></li>
									<li><span class="ty-input-warn" style="width:10px;margin-left:-10px;">*</span><label for="name">卡点名称:</label> 
										<input type="text" class="k-textbox" name="name" id="name" required="required" value="${cardPoint.name}" /></li>
									<li><label for="managerName">负责人:</label> 
										<input type="text" class="k-textbox" name="managerName" id="managerName" value="${cardPoint.managerName}" readOnly="true" />
										<input type="hidden" class="k-textbox" name="manager" id="manager" value="${cardPoint.manager}" />
										<button type="button" class="k-button mt4 ml68" onclick="loadPolice();">选择警员</button></li>
										<!-- <button type="button" class="k-button" onclick="clearPolice();">清除警员</button></li> -->
									<li><span class="ty-input-warn" style="width:10px;margin-left:-10px;">*</span><label for="eCardType">卡点类型:</label> 
										<input id="type" type="hidden" value="${cardPoint.type}">
										<select id="editCardType" name="editCardType" width="200px">
											<option value="">---请选择类型---</option>
											<option value="1">卡点</option>
											<option value="2">机动组</option>
										</select>
									</li>
									<li><label for="telephone">联系电话:</label> 
										<input type="text" class="k-textbox" name="telephone" id="telephone" value="${cardPoint.telephone}"/></li>
									<li><span class="ty-input-warn" style="width:10px;margin-left:-10px;">*</span><label for="intercomGroupId">通讯组号:</label> 
										<input type="text" class="k-textbox" name="intercomGroupId" id="intercomGroupId" value="${cardPoint.intercomGroupId}"
											   pattern="^[0-9]+$" validationMessage='通讯组号由一串数字组成' maxlength="8" />
									<li><label for="equipment">携带装备:</label> 
										<input type="text" class="k-textbox" name="equipment" id="equipment" value="${cardPoint.equipment}" /></li>
									<%-- <li><label for="policeNote">警力描述:</label> 
										<input type="text" class="k-textbox" name="policeNote" id="policeNote" value="${cardPoint.policeNote}" /></li> --%>
									<li><label for="assignment">职责描述:</label> 
										<input type="text" class="k-textbox" name="assignment" id="assignment" value="${cardPoint.assignment}" /></li>
									<li><label for="cameraName">关联天网:</label> 
										<input type="text" class="k-textbox" name="cameraName" id="cameraName" value="${cardPoint.cameraName}" readonly="readonly" />
										<button type="button" class="k-button mt4 ml68" onclick="loadCamera();">选择天网</button></li>
									<li><input type="hidden" class="k-textbox" name="camera" id="camera" value="${cardPoint.camera}" /></li>
										
									<li><label for="circleLayer">所属圈层:</label> 
										<select id="circleLayer" class="" name="circleLayer" value="${cardPoint.circleLayerId}">
							    		</select></li>
																		
									<li class="actions">
										<button type="button" class="ty-button ty-button-te" onclick='modifyCardPoint()'>提交</button>
									</li>
								</ul>
							</form>
						</div>

<script type="text/javascript">
	$(document).ready(function() {
		$("#editCardType").val($("#type").val());
		loadCircle();
		$("#editCardType").kendoComboBox().prev().find(".k-input").attr("readonly",true);
		$("#circleLayer").kendoComboBox().prev().find(".k-input").attr("readonly",true);
	});
	
	/* function clearPolice(){
		$("#managerName").val("");
	} */
	 
	/* 加载圈层 */
	function loadCircle(){
		$("#circleLayer").append(
				"<c:forEach var='item' items='${circleList}'>"
				+"<option value='${item.id}' <c:if test='${cardPoint.circleLayerId eq item.id}'>selected='selected'</c:if>>${item.name}</option>"
				+"</c:forEach>");
	}
	
	//修改卡点
	function modifyCardPoint(){
		if($.trim($("#name").val())==""){
			$("body").popjs({"title":"提示","content":"请填写卡点名称！"});
			return false;
		}
		if($.trim($("#editCardType").val())==""){
			$("body").popjs({"title":"提示","content":"请选择卡点类型！"});
			return false;
		}
		if($.trim($("#intercomGroupId").val())==""){
			$("body").popjs({"title":"提示","content":"请填写通讯组号！"});
			return false;
		}
		$.ajax({
			url:"<%=basePath%>web/cardPoint/modifyCardPoint.do",
			type:"post",
			dataType:"json",
			data:{
				cardPointId:	$("#cardPointId").val(),
				organId: 		$("#organId").val(),
				name:			$.trim($("#name").val()),
				manager:		$.trim($("#manager").val()),
				telephone:		$.trim($("#telephone").val()),
				intercomGroupId:$("#intercomGroupId").val(),
				equipment:		$.trim($("#equipment").val()),
				cardType:		$.trim($("#editCardType").val()),
				assignment:		$.trim($("#assignment").val()),
				camera:			$.trim($("#camera").val()),
				circleLayer:	$.trim($("#circleLayer").val()),
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
	
	/* 卡点负责人 */
    function loadPolice(){
    	$("#cameraBoxPane").css("display","none");//天网列表隐藏 
		
		$("#policeview").remove();
		$("#policeTitle").remove();
		$("#cameraview").remove();
		$("#policeBox").append("<h4 id='policeTitle'>警员绑定</h4><div id='policeview'>"
				+"<c:forEach var='item' items='${policeList}'>"
				+"<input name='policeName' class='policeName' id='policeName' type='checkbox' value='${item.id}' onclick='selectPolice(this,\"${item.id}\",\"${item.name}\")'/>${item.name}</br>"
				+"</c:forEach>"
				+"</div>");
    }
    var tempItem = new Array();
    var tempItem2 = new Array();
    function selectPolice(o,id,name){
    	/* var managerName = document.getElementById("managerName");
    	var manager = document.getElementById("manager"); */
		var s = name;
    	if($(o).prop("checked")){
    		if(tempItem.length>0){
        		for(var i = 0;i<tempItem.length;i++){
        			if(tempItem[i] != s){
        				tempItem.push(s);
        				break;
        			}
        		}
    		}else{
    			tempItem.push(s);
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
    			if(tempItem[i] == s){
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
		$("#treeview").remove();
		$("#addRoleTreeview").remove();
		$("#policeview").remove();
		$("#policeTitle").remove();
		$("#organTitle").remove();
		$("#roleTitle").remove();
		$("#cameraTitle").remove();
		$("#cameraview").remove();
		var  uri="<%=basePath%>web/GBPlatForm/getGBOrganListByOrganId.do";
		$.ajax({
			url : uri,
			type : "post",
			data : {
				organId : $("#orgId").val(),
				sessionId:$("#token").val(),
				random : Math.random()
			},
			dataType : "json",
			success : function(rsp) {
				$("#policeview").remove();//
				$("#policeBox").append("<div id='policeview'></div>");
				$("#policeview").kendoTreeView(
						{
							dataValueField : "id",
							dataTextField : "name",
							select : showCameraList,//点击触发事件
							dataSource : [ eval('('
									+ JSON.stringify(rsp.data) + ')') ]
						}).data("kendoTreeView");
			}
		}); 
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
		<!-- 跨机构授权开始 -->

		<!-- <div class="box-col" id="addRoleBox">
			<h4 id="policeTitle">警员选择</h4>
			<div id="addPoliceTreeview"></div>
		</div> -->
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
        
        </div>
		<!-- 跨机构授权结束 -->
		<!-- 右结束-->
	</div>
</div>
</div>
</div>

<script>
	$(document).ready(function() {
		/* 警员绑定 */
		loadPolice();		
		
		/* function addOnCheck(e) {
			text=this.text(e.node);
		  	var checkedNodes = [],
		    treeView = $("#addRoleTreeview").data("kendoTreeView"),message;
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