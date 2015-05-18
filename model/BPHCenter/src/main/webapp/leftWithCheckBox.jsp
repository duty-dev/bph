<%@ page language="java" pageEncoding="UTF-8"%>
<div id="navigationLeft">
        <div class="line1-mid box">
          <div class="t1-start"></div>
          <div class="end"></div>
        </div>
        <div class="pull-left box">
          <div>
            <div class="title box">搜索</div>
            <div class="hide" onclick="arrowZoom();"></div>
            <div class="clear">
            <div class="ty-input mt0"> 
<input  id="loginUserId" type="hidden" value="${requestScope.loginUserId}"/>
<input  id="checkedOrgIds" type="hidden" />
           <!-- <input id="searchOrganName" value="${requestScope.searchOrganName}" placeholder="等待输入..." class="k-textbox ty-left-search"/> --> 
            </div>
           <!-- <button class="ty-left-search-btn" onClick="queryOrgan()">搜索</button><br><br> --> 
            </div>
          </div>
        </div>
        <div class="pull-right box">
          <div class="line7"></div>
        </div>
        <div class="tree-box clear"><!----机构树---->
        <div id="box">
            <div id="treeview"></div>
		</div>
        </div><!----机构树结束---->
        <div class="line8 box"></div>
        <div class="line2"></div>
</div>        
		<script> 
			 
			 $(document).ready(function () {
				 
				 var data = new kendo.data.HierarchicalDataSource({
				        transport: {
							read: {
								url: "<%=basePath%>web/organx/lazyOrganList.do",
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
					 
					$("#treeview").kendoTreeView({
						checkboxes: {
					        checkChildren: false//允许复选框多选
					    },
				    	check: organOnCheck,//check复选框 
						dataSource: data
					});
			 });
			 
                function organOnCheck(e) {
		            var checkedNodes = [],
		              treeView = $("#treeview").data("kendoTreeView"),message;

		            //treeToJson(treeView.dataSource.view());
		            
		            checkedNodeIds(treeView.dataSource.view(), checkedNodes);

		            if (checkedNodes.length > 0) {
		                message = checkedNodes.join(",");
		            } else {
		                message = "";
		            }
		            //$("#result").html(message);
		            $("#checkedOrgIds").val(message);
		        }
                
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

