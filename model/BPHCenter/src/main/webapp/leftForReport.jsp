<%@ page language="java" pageEncoding="UTF-8"%>
 <style>
 	li{ float:left;list-style:none; padding:5px;} 
 	#tbl_alarmType {border:1px solid #fff;margin-top:5px;}
 	#tbl_alarmType td{border:1px solid #fff;}
 	#tbl_alarmType tr{border:1px solid #fff;} 
 	.a {float:left;}
 </style> 
<div id="navigationLeft">
        <div class="line1-mid box">
          <div class="t1-start"></div>
          <div class="end"></div>
        </div>
        <div class="pull-left box">
          <div>
            <div class="title box"  style="width:315px;"></div>
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
            			<span id="btnAddType2" class="k-button"  onclick="addOtherTimeSpan()">更多条件</span>  
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
	<div id="alarmTypeListWin" style="overflow:hidden">
		<div><button id="undo" class="ty-button" onclick="confirmAlarmType();">确定</button></div> 
		<div style="overflow:auto;">
			<div id="alarmtreeview" style="overflow:hidden">
			</div>
		</div>
    </div> 
</div>
<div id="timeSpanWindow" style="display:none">
	<div id="timeSpanWin" style="overflow:hidden">
		<div><span id="undo" class="k-button" onclick="confirmTimeSpan();">确定</span></div>  
		<div style="overflow:auto;height:290px">
			<table id="div_timaspan" style="width:100%;height:91%;border:1px solid white;">
				<tr>
					<td rowspan="6" style="width:250px;text-align:center"><img width="200px" src="<%=basePath%>images/images/xx.jpg" /></td> 　
					<td></td> 
					<td style="width:90px"><input id="radio_allday" type="radio" name="timeArea" value="1" onclick="selectTimeSpan();" />全天</td>
					<td>00:00——23:59</td>  　
				</tr> 
				<tr> 
					<td></td> 
					<td style="width:90px"><input id="radio_birght" type="radio" name="timeArea" value="2" onclick="selectTimeSpan();" />白天</td>
					<td>06:00——19:59</td> 
  				</tr>
				<tr> 
					<td></td> 
					<td style="width:90px"><input id="radio_night" type="radio" name="timeArea" value="3" onclick="selectTimeSpan();" />晚上</td>
					<td>20:00——05:59</td> 
  				</tr>
				<tr> 
					<td></td> 
					<td style="width:90px"><input id="radio_morning" type="radio" name="timeArea" value="4" onclick="selectTimeSpan();" />凌晨</td>
					<td>23:00——05:59</td> 
  				</tr>
				<tr> 
					<td></td> 
					<td style="width:90px"><input id="radio_heavyUp1" type="radio" name="timeArea" value="5" onclick="selectTimeSpan();" />早高峰</td>
					<td>07:00——09:59</td> 
  				</tr>
				<tr> 
					<td></td> 
					<td style="width:90px"><input id="radio_heavyUp2" type="radio" name="timeArea" value="6" onclick="selectTimeSpan();" />完高峰</td>
					<td>16:00——19:59</td> 
  				</tr>
			</table> 
		</div>
    </div> 
</div>
<script type="text/javascript">
var m_organId = 1;   
var alarmTypeArr = [];
var alarmTypeNameArr = [];
var alarmPeriodXLabel = []; 
var alarmOrganXLabel = [];
var alarmTimeSpanArr=[0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23];
var m_isFirst_load = true; 
var m_Query_pkg={};
var m_orgName ;
$(function() {
	    m_orgName = $("#organName").val(); 
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
		$("#div_alarmLevel input:checkbox").attr("checked","checked");  
		$("#radio_byday").attr("checked","checked");  
		$("#dpSDate").data("kendoDatePicker").enable(false);
		$("#dpEDate").data("kendoDatePicker").enable(false); 
	 	searchAction(1);
	 	loadAlarmTypeList();
});
function loadAlarmTypeList(){ 
	$.ajax({
		url:"<%=basePath%>alarmStatisticWeb/getAlarmTypeList.do?warningId=0",
		type:"post", 
		dataType:"json",
		success:function(req){
				if(req.code == 200){ 
					 req.data.parentId = null;
					var json_data = JSON.stringify(req.data);
					$("#alarmtreeview").empty();
					$("#alarmtreeview").kendoTreeView({ 
					    checkboxes: true, 
					    width:200,
					    dataTextField: "text",  
		    			check : onCheck,//check复选框
					    dataSource: [eval('(' + json_data + ')')]
					}).data("kendoTreeView"); 
				}
		}
	});
}
function selectTimeSpan(){ 
	var sObj = $("#div_timaspan input[name='timeArea']:checked");
	var timet = sObj[0].value;
	switch(timet)
	{
		case "1":
			alarmTimeSpanArr=[0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23];
			break;
		case "2":
			alarmTimeSpanArr=[6,7,8,9,10,11,12,13,14,15,16,17,18,19,20];
			break;
		case "3":
			alarmTimeSpanArr=[20,21,22,23,0,1,2,3,4,5,6];
			break;
		case "4":
			alarmTimeSpanArr=[23,0,1,2,3,4,5,6];
			break;
		case "5":
			alarmTimeSpanArr=[7,8,9,10];
			break;
		case "6":
			alarmTimeSpanArr=[16,17,18,19,20];
			break; 
	}
	
}
function searchByMonth(){
	var datepicker1 = $("#dpSDay").data("kendoDatePicker");
	var datepicker2 = $("#dpEDay").data("kendoDatePicker");
	datepicker1.enable(false);
	datepicker2.enable(false);
	var datepicker3 = $("#dpSDate").data("kendoDatePicker");
	var datepicker4 = $("#dpEDate").data("kendoDatePicker");
	datepicker3.enable();
	datepicker4.enable();
	$("#dpSDay").data("kendoDatePicker").value("");
	$("#dpEDay").data("kendoDatePicker").value("");
	$("#dpSDate").data("kendoDatePicker").value("");
	$("#dpEDate").data("kendoDatePicker").value(""); 
	m_Query_pkg.periodType = 1;
}
function searchByDay(){
	var datepicker1 = $("#dpSDay").data("kendoDatePicker");
	var datepicker2 = $("#dpEDay").data("kendoDatePicker");
	var datepicker3 = $("#dpSDate").data("kendoDatePicker");
	var datepicker4 = $("#dpEDate").data("kendoDatePicker");
	datepicker1.enable();
	datepicker2.enable();
	datepicker3.enable(false);
	datepicker4.enable(false);
	$("#dpSDay").data("kendoDatePicker").value("");
	$("#dpEDay").data("kendoDatePicker").value("");
	$("#dpSDate").data("kendoDatePicker").value("");
	$("#dpEDate").data("kendoDatePicker").value(""); 
	m_Query_pkg.periodType = 2;
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
				$("#dpEDate").data("kendoDatePicker").value("");   
				return;
			}else if(monthe == months){
				$("body").popjs({"title":"提示","content":"按月查询，起止月份不能相等"});  
				$("#dpEDate").data("kendoDatePicker").value("");  
				return;
			}else{ 
				alarmPeriodXLabel = [];
				for(var m = months; m<monthe+1; m++){
					alarmPeriodXLabel.push(m);
				} 
				m_Query_pkg.startDate =  years + "-" + months + "-" + "01";
				m_Query_pkg.endDate = yeare + "-" + monthe + "-" + "01";
				m_Query_pkg.periodType = 1;
			}
		}else if(yeare < years){
			$("body").popjs({"title":"提示","content":"按月查询，起始年份不能大于截止年份"}); 
			$("#dpEDate").data("kendoDatePicker").value("");    
			return;
		}else{
			if(((monthe+12)- months)>12){
				$("body").popjs({"title":"提示","content":"按月查询，起止月份不能超过12个月"}); 
				$("#dpEDate").data("kendoDatePicker").value("");  
				return;
			}else{ 
				alarmPeriodXLabel = [];
				for(var m = months; m<monthe+1; m++){
					alarmPeriodXLabel.push(m);
				} 
				for(var n = 1; n<monthe+1; n++){
					alarmPeriodXLabel.push(n);
				} 
				m_Query_pkg.startDate =  years + "-" + months + "-" + "01";
				m_Query_pkg.endDate = yeare + "-" + monthe + "-" + "01";
				m_Query_pkg.periodType = 1;
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
			$("#dpEDay").data("kendoDatePicker").value(""); 
			return;
		}else{
			var iDay = parseInt(Math.abs(start - end) / 1000 / 60 / 60 / 24); //把相差的毫秒数转换为天数
			if(iDay > 31){
				$("body").popjs({"title":"提示","content":"按天查询，起止日期不能大于31天"});  
				$("#dpEDay").data("kendoDatePicker").value(""); 
				return;
			}else{ 
				alarmPeriodXLabel = [];
				if(months < monthe){
					if(months==2){
						if(((years%4)==0)&&((years%100)!=0)||((years%400)==0)){ 
							for(var d = days; d<30; d++){
								alarmPeriodXLabel.push(d);
							} 
						}else{
							for(var d = days; d<29; d++){
								alarmPeriodXLabel.push(d);
							} 
						}
					}else if(months==4||months==6||months==9||months==11){
						for(var d = days; d<31; d++){
								alarmPeriodXLabel.push(d);
							} 
					}else{
						for(var d = days; d<32; d++){
								alarmPeriodXLabel.push(d);
							} 
					}
						
					for(var n = 1; n<daye+1; n++){
						alarmPeriodXLabel.push(n);
					} 
				}else{
					for(var n = days; n<daye+1; n++){
						alarmPeriodXLabel.push(n);
					} 
				}
				m_Query_pkg.startDate = years + "-" + months + "-" + days;
				m_Query_pkg.endDate = yeare + "-" + monthe + "-" + daye;
				m_Query_pkg.periodType = 2;
			}
		}
	}
} 
	 var cf = true;
	 var m_checkedNodes_code = "";
	 var m_checkedNodes_name = ""
	 		function parentNodeClick(parentId){
	 			alarmOrganXLabel.length = 0;
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
										subListHtml += "<a name='a_orgName' style='margin-left:30px;float:left'";
										if(req.data[i].hasChild){
											subListHtml += " onclick='parentNodeClick("+req.data[i].id+")'";
										}else{
											subListHtml += " onclick='loadData("+req.data[i].id+",'"+req.data[i].shortName+"')'";
										}
										subListHtml += ">"+req.data[i].shortName+"</a>";
									}
								}  
								$("#div_suborgList").empty();
								$("#div_suborgList").append(subListHtml); 
							}else{
								$("body").popjs({"title":"提示","content":"获取组织机构数据出错"}); 
							}			
					}
				});
	 		}
	 		
	 		function loadData(orgId,orgName){
	 			m_organId = orgId;
	 			m_orgName = orgName;
	 			alarmOrganXLabel.length = 0;
	 			//alert("加载数据");
	 			searchAction(1);
	 		}
	 		function addAlarmTypeList(){ 
	 			var win =$('#alarmTypeListWin');
				win.kendoWindow({
	                        width: "225px",
	                        height:"570px",
	                        title: "警情选择",
	                        position:{
	                        	top:"25%",
	                        	left:"20%"
	                        }
	                    });
				win.data("kendoWindow").open();
	 		}
	 		function onCheck(e) {
					var checkedNodes = [],checkNodesName=[], treeView = $("#alarmtreeview").data("kendoTreeView"), message,nameStr; 
		
					checkedNodeCode(treeView.dataSource.view(),checkedNodes,checkNodesName);
					if (checkedNodes.length > 0) {
						message = checkedNodes.join(",");
						nameStr = checkNodesName.join(",");
						m_checkedNodes_code = message;
						m_checkedNodes_name = nameStr;
					} else {
						message = "";
						nameStr = "";
					}
			}
			function checkedNodeCode(nodes, checkedNodes,checkNodesName) {
				for ( var i = 0; i < nodes.length; i++) {
					if (nodes[i].checked) {
						if(nodes[i].typeCode!="caseType110000000"){
							checkedNodes.push(nodes[i].typeCode);
							checkNodesName.push(nodes[i].typeName+"|"+nodes[i].typeCode+"|"+nodes[i].id);
						} 
					}
					if (nodes[i].hasChildren) {
						checkedNodeCode(nodes[i].children.view(),
							checkedNodes,checkNodesName); 
						}
				}
			}
	 		function confirmAlarmType(){ 
	 			alarmTypeNameArr.length = 0;
	 		 	alarmTypeArr = m_checkedNodes_code.split(","); 
	 		 	var namecodeArr = m_checkedNodes_name.split(",");
	 		 	var tyhtml = "";
	 		 	for(var i = 0;i<namecodeArr.length;i++){
	 		 		var namecode = namecodeArr[i];
	 		 		var s = namecode.split("|"); 
	 		 		alarmTypeNameArr.push(s[0]);
	 		 		tyhtml +="<li id='li_"+s[1]+"'>";
	 		 		tyhtml += s[0] + "<button type='button' class='ty-delete-btn' title='删除' onclick=deleteTypeNode('"+s[1]+"','"+s[0]+"',"+s[2]+")></button> ";
	 		 		tyhtml += "</li>";
	 		 	}
	 		 	$("#ul_alarmTypeList").empty();
	 		 	if(tyhtml.length>0){
 					$("#ul_alarmTypeList").html(tyhtml);
 				}else{ 
 					$("#ul_alarmTypeList").html("<li>无相关警情类型</li>")
 				}
				var win= $("#alarmTypeListWin").data("kendoWindow");
				win.close();
	 		} 
	 		function deleteTypeNode(typeCode,typeName,typeid){
	 			$.each(alarmTypeNameArr,function(index,value){
	 				if(value == typeName){
	 					alarmTypeNameArr.splice(index,1);
	 				}
	 			});
	 			$.each(alarmTypeArr,function(index,value){
	 				if(value == typeCode){
	 					alarmTypeArr.splice(index,1);
	 				}
	 			});
	 			$("#li_"+typeCode).hide(); 
	 			var treeview = $("#alarmtreeview").data("kendoTreeView");
	 			var dataSource = treeview.dataSource;
	 			var dataItem = dataSource.get(typeid);
	 			dataItem.checked = false;
	 			var uid = dataItem.uid;
	 			$("li[data-uid='"+uid+"']:input[type='checkbox']").attr("checked",false);
	 			$("li[data-uid='"+uid+"']:input[type='checkbox']").attr("disabled","disabled");
	 		}
	 		function addOtherTimeSpan(){ 
				$("#dpSDay").data("kendoDatePicker").value("");
				$("#dpEDay").data("kendoDatePicker").value("");
				$("#dpSDate").data("kendoDatePicker").value("");
				$("#dpEDate").data("kendoDatePicker").value("");
	 			var win =$('#timeSpanWin');
				win.kendoWindow({
	                        width: "500px",
	                        height:"350px",
	                        title: "小时选择"
	                    });
				win.data("kendoWindow").open();
	 		}
	 		function confirmTimeSpan(){
	 			var win= $("#timeSpanWin").data("kendoWindow");
				win.close();
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
	        
	        function onChangeReportAction(reportType){
	        	searchAction(reportType);
	        }
	        
	        function getActionUrl (rType){
	        	var urlStr = "";
	        	switch(rType)
	        	{
	        		case 1:
	        			urlStr = "<%=basePath%>caseReportWeb/loadCaseTypeReport.do";
	        			break;
	        		case 2:
	        			urlStr = "<%=basePath%>caseReportWeb/loadCasePeriodReport.do";
	        			break;
	        		case 3:
	        			urlStr = "<%=basePath%>caseReportWeb/loadCaseHourReport.do";
	        			break;
	        		case 4:
	        			urlStr = "<%=basePath%>caseReportWeb/loadCaseOrgReport.do";
	        			break;
	        	}
	        	return urlStr;
	        }
	         function searchAction(repType){
	         	if(m_isFirst_load){
	         		m_isFirst_load = false;
	         		return;
	         	}
	         	if(repType==undefined){
	         		repType = 1;
	         	}
	         	packageQuery();
	         	var url = getActionUrl(repType); 
	         	$.ajax({
						url : url,
						type : "post",
						data : {"query" : JSON.stringify(m_Query_pkg)},
						dataType : "json",
						success : function(req) { 
							if(req.code==200){  
								if(repType == 1){
									ReportManage.initAlarmTypeData(req.data,m_orgName,alarmTypeNameArr);	
								}else if(repType == 2){
									ReportManage.initAlarmCircleData(req.data,m_orgName,alarmPeriodXLabel);
								}else if(repType == 3){
									ReportManage.initAlarmTimeSpanData(req.data,m_orgName);
								}else if(repType == 4){
									var orglist = $("#div_suborgList a[name='a_orgName']");
									$.each(orglist,function(index,s){
	       		 						alarmOrganXLabel.push(s.text);
	       		 					});
									ReportManage.initAlarmOrganData(req.data,m_orgName,alarmOrganXLabel);
								}
							}else{
								$("body").popjs({"title":"提示","content":"查询统计数据失败！"});
							} 
						}
					}); 
	         }
	        
	       function packageQuery(){ 
	       		 m_Query_pkg.organId = m_organId; 
	       		 m_Query_pkg.alarmTypeArr = alarmTypeArr;
	       		 if(m_Query_pkg.alarmTypeArr.length==0&&m_Query_pkg.alarmTypeArr.length==0){
	       			$("body").popjs({"title":"提示","content":"请选择警情类型，至少选取一个警情类别！","callback":function(){
					 		return;
						}});   
	       			return;
	       		 }
	       		 m_Query_pkg.alarmTimeSpan = alarmTimeSpanArr;
	       		 m_Query_pkg.alarmLevel = []
	       		 var alrlel = $("#div_alarmLevel input:checkbox:checked");
	       		 $.each(alrlel,function(index,s){
	       		 	m_Query_pkg.alarmLevel.push(s.value);
	       		 });  
	       		 if(m_Query_pkg.alarmLevel.length==0){
	       			$("body").popjs({"title":"提示","content":"请选择警情级别，至少选取一个等级警情！","callback":function(){
					 		return;
						}});   
	       			return;
	       		 }
	       }
</script>
