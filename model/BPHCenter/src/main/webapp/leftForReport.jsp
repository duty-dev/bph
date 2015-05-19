<%@ page language="java" pageEncoding="UTF-8"%>
 <style>
 	li{ float:left;list-style:none; padding:5px;width:120px;} 
 	table{border:1px solid #fff;margin-top:5px;}
 	table td{border:1px solid #fff;}
 	table tr{border:1px solid #fff;} 
 	.a {float:left;}
 </style> 
<div id="navigationLeft">
        <div class="line1-mid box">
          <div class="t1-start"></div>
          <div class="end"></div>
        </div>
        <div class="pull-left box">
          <div>
            <div class="title box"></div>
            <div class="hide" onclick="arrowZoom();"></div>
            <div class="clear"> 
            </div>
          </div>
        </div>
        <div class="pull-right box">
          <div class="line7"></div>
        </div>
        <div class="tree-box clear"><!----机构树---->
        <div id="box">
             <div class="clear">
              	<div style="width:100%;float:left"><h5>机构选择</h5></div> 
          		 <div style="width:100%;float:left"> 
          		 <div id="div_orgPath"> 
          		 
          		 </div>
          		 <div id="div_suborgList" style="margin-top:5px" > 
          		 
          		 </div>
          		</div>
          	 </div>
          	<div> 
          		 <div class="clear"> 
            		<div style="width:60%;float:left">
            			<h5>警情类型</h5>
            		</div>
            		<div style="width:38%;float:left">
            			<span id="btnAddType1" class="k-button"  onclick="addAlarmTypeList()">添加</span>
            		</div> 
            	</div>
          	</div>
          	<div>
          		 <div class="clear">
          		 	<div>  
            		 	<ul id="ul_alarmTypeList"> 
            		 		<li>
            		 			无相关警情类型
            		 		</li>
            		 	</ul>
            		</div>
          		 </div>
          	</div>
          	<div> 
          		 <div class="clear"> 
            		<div style="width:100%;float:left"><h5> 警情类型</h5></div> 
            		<div>  
            		 	<label class="a"><input id="ckalarmLevel1" type="checkbox" value="1">一级警情</label>
						<label class="a"><input id="ckalarmLevel2" type="checkbox" value="2">二级警情</label>
						<label class="a"><input id="ckalarmLevel3" type="checkbox" value="3">三级警情</label> 
            		</div>
            	</div>
          	</div>
          	<div> 
          		 <div class="clear">
            		<div style="width:60%;float:left">
            			<h5>时间</h5>
            		</div>
            		<div style="width:38%;float:left">  
            			<span id="btnAddType2" class="k-button"  onclick="">更多条件</span>  
            		</div>
            		<div style="width:100%;float:left">  
            		  	<label><input id="radio_bymonth" type="radio" name="timeType" value="0" onclick="" />按月查询(不超过12月)</label>
            		  	<label>起</label><input id="dpSDate" /><label>止</label><input id="dpEDate"  />
            		  	<label><input id="radio_byday" type="radio" name="timeType" value="1" onclick="" />按天查询(不超过31天)</label>
            			<label>起</label><input id="dpSDay"   /><label>止</label><input id="dpEDay"   />
            		</div>
            	</div>
          	</div>
		</div>
        </div><!----机构树结束---->
        <div class="line8 box"></div>
        <div class="line2"></div>
</div>    

<div id="alarmTypeWindow" style="display:none">
	<div id="alarmTypeListWin">
		<div><span id="undo" class="k-button" onclick="confirmAlarmType();">确定</span></div> 
		 
			<table id="tbl_alarmType" >
			</table>
		 
		<div><span id="undo" class="k-button" onclick="confirmAlarmType();">确定</span></div> 
    </div> 
</div>
<script type="text/javascript">
var m_organId = 1;
$(function() {
		$("#dpSDate").kendoDatePicker();	
		$("#dpEDate").kendoDatePicker();	
		$("#dpSDay").kendoDatePicker();	
		$("#dpEDay").kendoDatePicker();		  
		m_organId = $("#organId").val();
		parentNodeClick(m_organId);
});
	 var cf = true;
	 
	 		function parentNodeClick(parentId){
	 			$.ajax({
					url:"<%=basePath%>organManageWeb/getOrgAndSubOrgList.do",
					type:"post",
					data:{
						organId:parentId
					},
					dataType:"json",
					success:function(req){
							if(req.code == 200){
								var parentHtml = req.description;;
								var subListHtml = "";
								$("#div_orgPath").empty();
								$("#div_orgPath").append(parentHtml);
								
								if(req.data.length>0){
									for(var i = 0;  i<req.data.length;i++ ){
										subListHtml += "<a style='margin-left:30px;'";
										if(req.data[i].hasChild){
											subListHtml += " onclick='parentNodeClick("+req.data[i].id+")'";
										}else{
											subListHtml += " onclick='loadData("+req.data[i].id+")'";
										}
										subListHtml += ">"+req.data[i].shortName+"</a>"
									}
								}  
								$("#div_suborgList").empty();
								$("#div_suborgList").append(subListHtml);
								loadData(parentId);
							}else{
								$("body").popjs({"title":"提示","content":"获取组织机构数据出错"}); 
							}			
					}
				});
	 		}
	 		
	 		function loadData(orgId){
	 			//alert("加载数据");
	 		}
	 		function addAlarmTypeList(){
	 			$.ajax({
					url:"<%=basePath%>alarmStatisticWeb/getAlarmTypeList.do",
					type:"post", 
					dataType:"json",
					success:function(req){
							if(req.code == 200){ 
								var html = "";  
								
								for(var j = 0; j< req.data.length; j++){
									  html += "<tr><td style='width:100px'>";
									  html += "<input type='checkbox' value='"+req.data[j].alarmType.typeCode+"' /><span id='sp_"+req.data[j].alarmType.typeCode+"'>"+ req.data[j].alarmType.typeName+ "</span></td><td><ul>";  
									  for(var m =0; m<req.data[j].alarmTypeList.length;m++){
									  	html += "<li><input type='checkbox' value='"+req.data[j].alarmTypeList[m].typeCode+"' /><span id='sp_"+req.data[j].alarmTypeList[m].typeCode+"'>"+req.data[j].alarmTypeList[m].typeName+"</span></li>"; 
									  } 
									  html += "<ul></td></tr>";
								} 
								$("#tbl_alarmType").empty();
								$("#tbl_alarmType").html(html);
							}
					}
				});
	 		
	 			var win =$('#alarmTypeListWin');
				win.kendoWindow({
	                        width: "700px",
	                        height:"450px",
	                        title: "警情选择"
	                    });
				win.data("kendoWindow").open();
	 		}
	 		function confirmAlarmType(){
	 			var count = $("#tbl_alarmType input:checkbox:checked").length;
	 			if(count > 0){
	 				var typeHtml = "";
	 				var alarmTypeObj = $("#tbl_alarmType input:checkbox:checked");
	 				$.each(alarmTypeObj, function(index, tobj){
	 					var tpId = tobj.value;
	 					var tpName = $("#sp_"+tpId).html();
	 					typeHtml += "<li id='li_"+tpId+"'>";
	 					typeHtml += tpName + "<button type='button' class='ty-delete-btn' title='删除' onclick=deleteSelectNode('"+tpId+"')></button> ";
	 					typeHtml += "</li>";
	 				}); 
	 				$("#ul_alarmTypeList").empty();
	 				$("#ul_alarmTypeList").html(typeHtml)
	 				
	 			}else{
	 				$("#ul_alarmTypeList").empty();
	 				$("#ul_alarmTypeList").html("<li>无相关警情类型</li>")
	 			}
	 			
				var win= $("#alarmTypeListWin").data("kendoWindow");
				win.close();
	 		}
	 		function deleteSelectNode(code){
	 			$("#li_"+code).hide();
	 		}
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
	        
	        //<li>
          //  		 			刑事案件<button type='button' class='ty-delete-btn' title='删除' onclick=''></button>
          //  		 		</li>
</script>
