<%@ page language="java" pageEncoding="UTF-8"%>
<div id="navigationLeft">
        <div class="line1-mid box">
          <div class="t1-start"></div>
          <div class="end"></div>
        </div>
<input  id="loginUserId" type="hidden" value="${requestScope.loginUserId}"/>
<input  id="checkedOrgIds" type="hidden" />
        <div class="temp">
        <div class="pull-right box" style="position:absolute;left:238px;">
          <div class="hide" onclick="arrowZoom();" style="margin-left:-19px;"></div><div class="line7"></div>
        </div>
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
					    select: onSelect,//点击触发事件
				    	check: organOnCheck,//check复选框 
						dataSource: data
					});
					setH();
			 });
			 var parentId;
			 var k=0;
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
 	    		//loadData(1); 
			 }
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
			 	function setH(){
					var o2 = $("#navigationLeft .tree-box");
					var ct=0;
					if(o2.length>0){
						ct = o2.offset().top;
					}else{
						return;
					}
					var wh = $(window).height();
					var v = wh-ct-70;
					$("#navigationLeft .tree-box").css("height",v);
					$(".pow").css("height",v);
					$(".pow .box-content").css("height",v);
					o2.mCustomScrollbar({scrollButtons:{enable:true},advanced:{ updateOnContentResize: true } });
				}
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

