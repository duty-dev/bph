<%@ page language="java" pageEncoding="UTF-8"%>
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
<title>扁平化指挥系统</title>
<%@ include file="../../emulateIE.jsp"%>
</head>

<body>
	<div id="vertical">
		<div id="horizontal">
			<div id="left-pane">
				<div class="pane-content">
					<!-- 左开始 -->
					<div class="demo-section k-header">
						<form id="employeeForm" data-role="validator"
							novalidate="novalidate">
							<h4>添加圈层</h4>
							<ul>
								<li><label for="name">圈层名称:</label> <input type="text"
									class="k-textbox" name="name" id="name"/></li>
								<li class="actions">
									<button type="button" class="ty-button ty-button-te"
										onclick="saveCircleLayer()">提交</button>
								</li>
							</ul>
						</form>
					</div>

					<script type="text/javascript">
/* 圈层新增 */
function saveCircleLayer(){
	var sessionId = $("#token").val();
	var name = $("#name").val();
	if($.trim(name)==""){
		$("body").popjs({"title":"提示","content":"圈层名称不能为空！"});
		return false;
	}
	
	$.ajax({
		url:"<%=basePath%>web/cardPoint/addCircleLayer.do?sessionId="+sessionId,
		type:"post",
		dataType:"json",
		data:{
			name: name,
		},
		 success:function(msg){
			if(msg.code==200){
				/* alert(msg.description); */
				searchCircleLayer();
			}else{
				alert(msg.description);
			}
			$("#name").val("");
		}
	});
}

$(function () {
    var container = $("#employeeForm");
    kendo.init(container);
    container.kendoValidator({
        rules: {
            validmask: function (input) {
               /*  console.log(input); */
                if (input.is("[data-validmask-msg]") && input.val() != "") {
                    var maskedtextbox = input.data("kendoMaskedTextBox");
                    return maskedtextbox.value().indexOf(maskedtextbox.options.promptChar) == -1;
                }

                return true;
            }
        }
    });
});

function editCircle(){
	alert("修改");
}

/* 删除圈层 */
function deleteCircle(id){
	/* if(confirm("确认是否删除该圈层?")){  */
		/* $("#circleBox").remove(); */
		$("body").tyWindow({"content":"确定是否删除该圈层?","center":true,"ok":true,"no":true,"okCallback":function(){
		var sessionId = $("#token").val();
		$.ajax({
			url:"<%=basePath%>/web/cardPoint/deleteCircle.do?sessionId="+sessionId,
			type:"post",
			dataType:"json",
			data:{
				id: id
			},
			success:function(msg){
				 if(msg.code==200){
					 searchCircleLayer();
				}else{
					$("body").popjs({"title":"提示","content":msg.description});
				}
			}
		});
		}});
	/* }else{
		return false;
	} */
}

/* 圈层查询 */
function searchCircleLayer(){
	var sessionId = $("#token").val();
	$.ajax({
		url:"<%=path%>/web/cardPoint/queryCircleList.do?sessionId="
												+ sessionId,
										type : "post",
										dataType : "json",
										data : {},
										success : function(msg) {
											if (msg.code == 200) {
												if (msg.data != null) {
													var udata = msg.data;

													$("#circleBox")
															.kendoGrid(
																	{
																		dataSource : {
																			data : udata,
																			pageSize : 20
																		},
																		/* height: 550,
																		sortable: true,
																		pageable: {
																		 	refresh: true,
																		 	pageSizes: true,
																		 	buttonCount: 5
																		}, */
																		columns : [
																				{
																					field : "name",
																					title : "圈层名称"
																				},
																				{
																					title : "操作",
																					template :
																					/* "<button type='button' onclick='editCircle(#: id #)'>编辑</button>"+ */
																					"<button type='button' onclick='deleteCircle(#: id #)'>删除</button>",
																					width : 120
																				} ],
																	});
												}
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
					<div id="box">
						<h4 id="organTitle"></h4>
						<div id="treeview"></div>
					</div>
					<div id="circleBox">
						<h4 id="policeTitle"></h4>
						<div id="policeview"></div>
						</dir>
						<!-- 右结束-->
					</div>
				</div>
			</div>
		</div>

		<script>
			$(document).ready(function() {

				searchCircleLayer();

				$("#horizontal").kendoSplitter({
					panes : [ {
						collapsible : true
					}, {
						collapsible : true
					} ]
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