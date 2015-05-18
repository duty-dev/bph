<%@ page language="java" pageEncoding="UTF-8"%>
<%-- <%
Cookie cname=new Cookie("c_name","55555");//设置cookie的键键c_name
cname.setMaxAge(0);//设置cookie的有效期.
//response.setCharacterEncoding("utf-8");
response.addCookie(cname);//设置cookie,将cookie存放到respones里面
%>
<%
//读取cookie
Cookie cookie[]=request.getCookies();
if(cookie!=null){
	for(int i=0;i<cookie.length;i++){
		  Cookie c=cookie[i];	
		  //out.println(c.getName());
		  String name=c.getName();
		  out.println(name);
	}
} 
%> --%>
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
            	<input id="searchOrganName" value="${requestScope.searchOrganName}" placeholder="等待输入..." class="k-textbox ty-left-search"/>
            </div>
            <button class="ty-left-search-btn" onClick="queryOrgan()">搜索</button><br><br>
            <input type="hidden" value="${requestScope.selectName}" style="width:80px;height:50px;" id="selectName">
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
			 var zNodes;
			 var json_data;
			 var currentText;//当前选中的文本
			 var treeview;
			 var expandeds="";
			 var selectNode="";
			 var barElement;//当前对象
			 var parentId;
			 var k=0;
			 $(function() {
					 if($("#searchOrganName").val()==""){
						 loadOrganTreeList();
					 }else{
						 queryOrgan();
					 }
				});
			 
			 function loadOrganTreeList(){
				 var data = new kendo.data.HierarchicalDataSource({
				        transport: {
							read: {
								url: "<%=basePath%>web/organx/lazyOrganList.do",
								type : "post",
								data : {
									searchName:$("#searchOrganName").val(),
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
					 
				 	$("#treeview").remove();
					$("#box").append("<div id='treeview'></div>");
					$("#treeview").kendoTreeView({
						select: onSelect,//点击触发事件
						dataSource: data
					});
					 //test();
			 }
			/*  function  selectClass(){
				 treeView = $("#treeview").data("kendoTreeView");
				 var barDataItem;
				 selectNode=$("#organId").val();
				 if(selectNode !=""){
					 barDataItem = treeView.dataSource.get(selectNode);
				 }else{
					 barDataItem =treeView.dataSource.get(parentId);//暂时用1
				 }
				 barElement = treeView.findByUid(barDataItem.uid);
				 $(barElement).find("div .k-in").first().addClass("k-state-selected");
			 } */
			function test(){
				 var expandedNodes = [],treeView = $("#treeview").data("kendoTreeView");
		            getExpanded(treeView.dataSource.view(),expandedNodes);
		            expandeds=expandedNodes.join(",");
			 }
			 function getExpanded(nodes,expandedNodes) {
		            for (var i = 0; i < nodes.length; i++) {
		            	if(k == 0){
		            		parentId=nodes[0].id;
		            		k=1;
		            	}
		                if(nodes[i].expanded){
		                	expandedNodes.push(nodes[i].id);
		                }
		                if (nodes[i].hasChildren) {
		                	getExpanded(nodes[i].children.view(),expandedNodes);
		                }
		            }
		        }
			
			 //查询通过name模糊查询
		        function queryOrgan(){
				 if($("#searchOrganName").val()==""){
					 loadOrganTreeList();
					 test();
				 }else{
					 $.ajax({
							url:"<%=basePath%>web/organx/searchOrganListByName.do",
							type:"post",
							data:{
								searchName:$("#searchOrganName").val(),
								expandeds:expandeds,
								sessionId:$("#token").val(),
								random:Math.random()
							},
							dataType:"json",
							success:function(rsp){
								//json_data =JSON.stringify(rsp.data);
								json_data =rsp.data;
								searchOrgAction();
							}
			        	});
				 	}
		        }
			 
		        function searchOrgAction() {
		        	var name = $.trim($('#searchOrganName').val());
		        	var a = findOrgs(name);
		        	$("#treeview").remove();
					$("#box").append("<div id='treeview'></div>");
					
					treeview=$("#treeview").kendoTreeView({
						select: onSelect,
					    dataSource: a
					}).data("kendoTreeView");
		        }
		        function findOrgs(name) {
		        	var a = [];
		        	if (json_data != null) {
		        		$(json_data).each(function(index, value) {
		        			var o = findOrgTree(value, name);
		        			if (o != null) {
		        				a.push(o);
		        			}
		        		});
		        	}
		        	return a;
		        }
		        function findOrgTree(org, name) {
		        	var ls = [];
		        	if (org.items != null) {
		        		$(org.items).each(function(index, value) {
		        			var o = findOrgTree(value, name);
		        			if (o != null) {
		        				ls.push(o);
		        			}
		        		});
		        	}
		        	org.items = ls;
		        	if (name =="" || org.name.indexOf(name) >= 0 || ls.length > 0) {
		        		org.expanded=true;
		        		if($("#selectName").val() != "" && $("#selectName").val()==org.name){
		        			org.selected=true;
		        		}
		        		return org;
		        	} else {
		        		return null;
		        	}
		        }
			 //单击触发事件
		 	 function onSelect(e) {
		 		//选择节点事件
		 		var treeview=$('#treeview').data('kendoTreeView');
		 		//获取选中节点的数据
		 		var data = treeview.dataItem(e.node);
		 		//移出样式
		 		//$(barElement).removeClass("k-state-selected");
		 		
		 		$("#organId").val(data.id);
 	    		$("#organPath").val(data.path);
 	    		test();
 	    		$("#expandeds").val(expandeds);
 	    		$("#selectName").val(data.name);
 	    		loadData(1); 
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
	        // show checked node IDs on datasource change
	        function onCheck() {
	            var checkedNodes = [],
	                treeView = $("#treeview").data("kendoTreeView"),message;

	            checkedNodeIds(treeView.dataSource.view(), checkedNodes);

	            if (checkedNodes.length > 0) {
	                message = "IDs of checked nodes: " + checkedNodes.join(",");
	            } else {
	                message = "No nodes checked.";
	            }
	            //alert(message);
	        }
	        var cf = true;
	        function arrowZoom(){
    		//箭头点击收放效果
    			if(cf){
    				$("#navigationLeft").hide('fast',function(){
    					if($("#arrowXg1").length ==0){
    						$("#main-nav").append('<div class="temp"><div class="show" id="arrowXg1"></div></div>');
    					}
    					$("#arrowXg1").css({"position":"absolute","top":11,"left":0}).bind("click",function(){
    						$("#arrowXg1").parent().remove();
    						$("#content").animate({"margin-left":"240px"},'slow',function(){
    							$("#navigationLeft").show('fast');
    						});
    						cf=true;
    					});
    					$("#content").animate({"margin-left":"0"},'slow');
    				});
    				cf=false;
    			}
	        }
		</script>

