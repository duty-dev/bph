<%@ page language="java" pageEncoding="UTF-8"%>

<div class="pow" id="pow">
                   <div class="line1-mid">
                     <div class="t1-start"></div>
            		 <div class="t1-end"></div>
          		   </div>
                   <h2>账号权限</h2>
                   <div class="box">
                     <hr class="hr clear">
                     <div class="shadow" id="zhegaiceng">
						<img src="<%=basePath%>images/images/zhedang.png" style="height:100%">
   					</div>
                     <div class="box-content">
                     
                     <!-- 角色权限树开始 -->
                     
                     <div id="roleBox">
		<div id="moduletreeview"></div>
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
    
</div>

<script>
			 var json_data;
			 $(document).ready(function () {
				 $.ajax({
						url:"<%=basePath%>role/getModuleTreeByRoles.do",
						type:"post",
						data:{
							userId:"",
							random:Math.random()
						},
						dataType:"json",
						success:function(rsp){
							json_data =JSON.stringify(rsp.data);
							
							$("#moduletreeview").kendoTreeView({
								//select: onSelect,//点击触发事件
							    checkboxes: {
							        checkChildren: true//允许复选框多选
							    },
							    check: onCheck,//check复选框
							    dataSource: [eval('(' + json_data + ')')]
							}).data("kendoTreeView");
						}
					});
				 $("#pow .box .box-content").mCustomScrollbar({scrollButtons:{enable:true},advanced:{ updateOnContentResize: true } });
			 });
			  // show checked node IDs on datasource change
		        function onCheck(e) {
		            var checkedNodes = [],
		              treeView = $("#moduletreeview").data("kendoTreeView"),message;

		            //treeToJson(treeView.dataSource.view());
		            
		            checkedNodeIds(treeView.dataSource.view(), checkedNodes);

		            if (checkedNodes.length > 0) {
		                message = checkedNodes.join(",");
		            } else {
		                message = "";
		            }
		            //$("#result").html(message);
		        }
			 
			 
		 // function that gathers IDs of checked nodes
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
		</script>
<!-- 角色权限树结束 -->
                     
                     </div>
                     <hr class="hr">
                   </div>
                 </div>

