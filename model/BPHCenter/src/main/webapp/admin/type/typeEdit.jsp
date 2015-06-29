<%@ page language="java"  import="java.util.*"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> 
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>


<!DOCTYPE html>
<html>
<head> 
<base href="<%=basePath%>">  
<title>扁平化指挥系统</title>
<meta
	content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no'
	name='viewport' />

<%@ include file="../../emulateIE.jsp" %>
<script type="text/javascript">
function typeFormSubmit(){
	if($.trim($("#typeName").val())==""){
		$("body").popjs({"title":"提示","content":"请输入名称"}); 
		return false;
	}
	var radios = document.getElementsByName("groupId");
	var tag = false;
	var val;
	for(radio in radios) {
	   if(radios[radio].checked) {
	      tag = true;
	      break;
	   }
	}
	if(tag) {
		var groupId = $("input[name='groupId']:checked").val();
	} 
	else {
		$("body").popjs({"title":"提示","content":"请选择图标组"}); 
		return false;
	}
	typeSave();
}

function typeSave(){
	var typeName = $.trim($("#typeName").val());
	var iconType = $.trim($("#iconType").val());
    var groupId = $("input[name='groupId']:checked").val();
	var id = $("#id").val();
	alert(groupId);
	$.ajax({
			url:"<%=basePath%>typeWeb/typeUpdate.do",
			type:"post",
			dataType:"json",
			data:{
				typeName:typeName,
				iconType:iconType,
				groupId:groupId,
				id:id
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
</head>

<body class="ty-body">
<div id="typeSubForm" method="post" action="" >  
     <div id="horizontal" style="height: 220px;">
			<div class="pane-content">
				<!-- 左开始 -->
				<div class="demo-section k-header"> 
					<ul>
						<li><span class="ty-input-warn">*</span><label for="typeName">名称:</label><input
							type="text" class="k-textbox" name="typeName" id="typeName" value = "${iconTypeDetail.typeName}"/>
							<input type="hidden" class="k-textbox" name="id" id="id" value = "${iconTypeDetail.id}"/>
							<input type="hidden" class="k-textbox" name="iconType" id="iconType" value = "${iconType}"/>
							</li>
						<li><span class="ty-input-warn">*</span><label for="otherOgans">图标组:</label>
									</li>
                        			<table> 
                        			<c:forEach var="item" items="${iconGroupList}">
									<tr><td colspan=5><p class='ty-input-p'><input name='groupId' id='groupId' type='radio' value="${item.groupId}"/>${item.groupName}</p></td></tr>
                        			<tr><td>常态</td><td>选中</td><td>进入电子围栏</td><td>已派警</td><td>到达现场</td></tr>
                        			<tr><td><img style="width:25px;height:25px;" src="<%=basePath %>${item.nomalUrl}"></td>
                        			<td><img style="width:25px;height:25px;" src="<%=basePath %>${item.selectedUrl}"></td>
                        			<td><img style="width:25px;height:25px;" src="<%=basePath %>${item.intoEnclosureUrl}"></td>
                        			<td><img style="width:25px;height:25px;" src="<%=basePath %>${item.dispatchUrl}"></td>
                        			<td><img style="width:25px;height:25px;" src="<%=basePath %>${item.arriveUrl}"></td>
                        			</tr>		
                        			</c:forEach> 
                        			</table>
					</ul>
					<p class="ty-input-row">  	
					 <button id="submit" class="ty-button" onClick="typeFormSubmit();" >确定</button>  
					</p>
				</div>
				
			</div>
		</div> 
 </div>   
</body>
</html>
