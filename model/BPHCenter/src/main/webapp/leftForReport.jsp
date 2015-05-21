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
          		 <div id="div_suborgList" style="margin-top:5px;width:200px;" > 
          		 
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
            		<div style="width:100%;float:left"><h5> 警情级别</h5></div> 
            		<div id="div_alarmLevel">  
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
            		<div id="div_dateType" style="width:100%;float:left">  
            		  	<label><input id="radio_bymonth" type="radio" name="timeType" value="0" onclick="searchByMonth();" />按月查询(不超过12月)</label>
            		  	<label>起</label><input id="dpSDate" /><label>止</label><input id="dpEDate"  />
            		  	<label><input id="radio_byday" type="radio" name="timeType" value="1" onclick="searchByDay();" />按天查询(不超过31天)</label>
            			<label>起</label><input id="dpSDay"   /><label>止</label><input id="dpEDay"   />
            		</div> 
            	</div>
          	</div>
          	<div> 
          		 <div class="clear">
            		<span id="btnAddType1" class="k-button" style="float:right"  onclick="searchAction()">搜索</span>
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
var alarmTypeArr = []; 
var m_byMonth = false;
var m_byDay = false;
var m_timeSpan_Start ;//通过更多条件选择的时间区域开始时间
var m_timeSpan_End ;//通过更多条件选择的时间区域结束时间
var m_Query_pkg={};
$(function() {
		$("#dpSDate").kendoDatePicker({ 
             start: "year", 
             depth: "year", 
             format: "yyyy MMMM",
             change: onDpDate
        });
        $("#dpEDate").kendoDatePicker({ 
             start: "year", 
             depth: "year", 
             format: "yyyy MMMM",
             change: onDpDate
        });
		$("#dpSDay").kendoDatePicker({
             change: onDpDay
		});	
		$("#dpEDay").kendoDatePicker({
             change: onDpDay
		});		  
		m_organId = $("#organId").val();
		parentNodeClick(m_organId);
		$("#radio_byday").attr("checked","checked");  
		$("#dpSDate").data("kendoDatePicker").enable(false);
		$("#dpEDate").data("kendoDatePicker").enable(false);
		
});
function searchByMonth(){
	var datepicker1 = $("#dpSDay").data("kendoDatePicker");
	var datepicker2 = $("#dpEDay").data("kendoDatePicker");
	datepicker1.enable(false);
	datepicker2.enable(false);
	var datepicker3 = $("#dpSDate").data("kendoDatePicker");
	var datepicker4 = $("#dpEDate").data("kendoDatePicker");
	datepicker3.enable();
	datepicker4.enable();
}
function searchByDay(){
	var datepicker1 = $("#dpSDay").data("kendoDatePicker");
	var datepicker2 = $("#dpEDay").data("kendoDatePicker");
	datepicker1.enable();
	datepicker2.enable();
	var datepicker3 = $("#dpSDate").data("kendoDatePicker");
	var datepicker4 = $("#dpEDate").data("kendoDatePicker");
	datepicker3.enable(false);
	datepicker4.enable(false);
}
function onDpDate(){
	var dates = $("#dpSDate").data("kendoDatePicker").value();
	var datee = $("#dpEDate").data("kendoDatePicker").value();
	if(datee !=null && dates != null){
		var years = dates.getFullYear();
		var yeare = datee.getFullYear();
		var months = dates.getMonth() + 1;
		var monthe = datee.getMonth() + 1;
		if(years == yeare){
			if(monthe < months){
				$("body").popjs({"title":"提示","content":"按月查询，起始月份不能大于截止月份"});  
			}else if(monthe == months){
				$("body").popjs({"title":"提示","content":"按月查询，起止月份不能相等"});  
			}else{
				m_byMonth = true;	
			}
		}else if(yeare < years){
			$("body").popjs({"title":"提示","content":"按月查询，起始年份不能大于截止年份"});   
		}else{
			if(((monthe+12)- months)>12){
				$("body").popjs({"title":"提示","content":"按月查询，起止月份不能超过12个月"});  
			}else{
				m_byMonth = true;	
			}
		}
	}
} 
function onDpDay(){
	var dates = $("#dpSDay").data("kendoDatePicker").value();
	var datee = $("#dpEDay").data("kendoDatePicker").value();
	if(datee !=null && dates != null){
		var years = dates.getFullYear();
		var yeare = datee.getFullYear();
		var months = dates.getMonth() + 1;
		var monthe = datee.getMonth() + 1;
		var days = dates.getDate();
		var daye = datee.getDate();
		
		var startdate = years + "/" + months + "/" + days;
		var enddate = yeare + "/" + monthe + "/" + daye;
		
		var start = new Date(startdate);
		var end = new Date(enddate);
		if(start  > end){
			$("body").popjs({"title":"提示","content":"按天查询，起始日期不能大于截止日期"});   
		}else{
			var iDay = parseInt(Math.abs(start - end) / 1000 / 60 / 60 / 24); //把相差的毫秒数转换为天数
			if(iDay > 31){
				$("body").popjs({"title":"提示","content":"按天查询，起止日期不能大于31天"});   
			}else{
				m_byDay = true;
			}
		}
	}
} 
	 var cf = true;
	 
	 		function parentNodeClick(parentId){
	 			m_organId = parentId;
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
										subListHtml += "<a style='margin-left:30px;float:left'";
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
	 					alarmTypeArr.push(tpId);
	 					var tpName = $("#sp_"+tpId).html();
	 					typeHtml += "<li id='li_"+tpId+"'>";
	 					typeHtml += tpName + "<button type='button' class='ty-delete-btn' title='删除' onclick=deleteSelectNode('"+tpId+"')></button> ";
	 					typeHtml += "</li>";
	 				}); 
	 				$("#ul_alarmTypeList").empty();
	 				$("#ul_alarmTypeList").html(typeHtml)
	 				
	 			}else{
	 				
	 				alarmTypeArr = [];
	 				
	 				$("#ul_alarmTypeList").empty();
	 				$("#ul_alarmTypeList").html("<li>无相关警情类型</li>")
	 			}
	 			
				var win= $("#alarmTypeListWin").data("kendoWindow");
				win.close();
	 		}
	 		function deleteSelectNode(code){
	 		
	 			$("#li_"+code).hide();
	 			$.each(alarmTypeArr,function(index,value){
	 				if(code == value){
	 					alarmTypeArr.splice(index,1);
	 				}
	 			});
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
	         function searchAction(){
	         	packageQuery();
	         	$.ajax({
						url : "<%=basePath%>alarmStatisticWeb/getReportDataByQuery.do",
						type : "post",
						data : {"QueryString" : JSON.stringify(m_Query_pkg)},
						dataType : "json",
						success : function(req) { 
							if(req.code==200){ 
								 //加载统计图形数据
								 alert("调用后台方法成功")
							}else{
								$("body").popjs({"title":"提示","content":"查询统计数据失败！"});
							} 
						}
					}); 
	         }
	        
	       function packageQuery(){ 
	       		 m_Query_pkg.organId = m_organId;
	       		 m_Query_pkg.alarmType = alarmTypeArr;
	       		 m_Query_pkg.alarmLevel = []
	       		 var alrlel = $("#div_alarmLevel input:checkbox:checked");
	       		 $.each(alrlel,function(index,s){
	       		 	m_Query_pkg.alarmLevel.push(s.value);
	       		 }); 
	       		 var dateType = $('#div_dateType input:radio[name="timeType"]:checked').val()
	       		 if(dateType == 0){
	 				 //kendoDatePicker 
	 				 if(m_byMonth){
	 				 	var dates = $("#dpSDate").data("kendoDatePicker").value();
						var datee = $("#dpEDate").data("kendoDatePicker").value();
	 				 	m_Query_pkg.startDate = dates.getFullYear()+ "-" + ((dates.getMonth() + 1) > 10 ? (dates.getMonth() + 1) : "0" + (dates.getMonth() + 1))+ "-" + "01";
	 				 	m_Query_pkg.endData = datee.getFullYear()+ "-" + ((datee.getMonth() + 1) > 10 ? (datee.getMonth() + 1) : "0" + (datee.getMonth() + 1))+ "-" + "01";
	 				 }else{ 
	 				 	m_Query_pkg.startDate = "";
	 				 	m_Query_pkg.endData = "";
	 				 }
	 			 }else if(dateType == 1){
	 			 	if(m_byDay){
	 			 		var dates = $("#dpSDay").data("kendoDatePicker").value();
						var datee = $("#dpEDay").data("kendoDatePicker").value();
	 				 	m_Query_pkg.startDate = dates.getFullYear()+ "-" + ((dates.getMonth() + 1) > 10 ? (dates.getMonth() + 1) : "0" + (dates.getMonth() + 1))+ "-" + (dates.getDate() < 10 ?"0" + dates.getDate() : dates.getDate());
	 				 	m_Query_pkg.endData = datee.getFullYear()+ "-" + ((datee.getMonth() + 1) > 10 ? (datee.getMonth() + 1) : "0" + (datee.getMonth() + 1))+ "-" + (datee.getDate() < 10 ?"0" + datee.getDate() : datee.getDate());
	 				 }else{
	 				 	m_Query_pkg.startDate = "";
	 				 	m_Query_pkg.endData = "";
	 				 }
	       		 }else{
	       		 	if(m_timeSpan_Start !=undefined&&m_timeSpan_Start!=""){
	       		 		m_Query_pkg.startDate = m_timeSpan_Start;
	 				 	m_Query_pkg.endData = m_timeSpan_End;
	       		 	}else{
	       		 		m_Query_pkg.startDate = "";
	 				 	m_Query_pkg.endData = "";
	       		 	}
	       		 }
	       		 
	       }
</script>
