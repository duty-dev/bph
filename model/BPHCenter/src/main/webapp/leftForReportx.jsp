<%@ page language="java" pageEncoding="UTF-8"%>
 <style>
 	#tbl_alarmType {border:1px solid #fff;margin-top:5px;}
 	#tbl_alarmType td{border:1px solid #fff;}
 	#tbl_alarmType tr{border:1px solid #fff;} 
 	.lbla {float:left;margin-left:2%;line-height:30px;}
 	.lbltimespan {padding:0 5 5 5;}
 	.lbltime{float:left;width:100%;line-height:24px;}
 	.lbltimespan input{vertical-align: middle;}  
 	.leftSearchbutton{float: left;width: 98%;margin-top: 10px;text-align: right;padding-top: 2px;}
 </style> 
<div id="navigationLeft">
		<div class="fl fit" id="leftNotice"><h2 class="no-bg">条件设置</h2></div>
        <div class="tree-box clear"><!----机构树---->
        <div id="box">
             <div class="clear">
              	<div class="ty-nav-line2">
              		<i class="ty-nav-line2-left"></i><i class="ty-nav-line2-right"></i>
              	</div> 
          		<table class="ty-tj-table mt10 ml6">
          			<tr>
          				<td width="10"><i class="ty-table-top-l"></i></td><td><i class="ty-table-top"></i></td><td width="10"><i class="ty-table-top-r"></i></td>
          			</tr>
	          		<tr>
          				<td colspan="3" class="ty-tj-td pd10">
          					<div class="ty-tj-title">机构选择</div>
          					<div id="div_suborgList" class="ty-tj-list" ></div>
          				</td>
          			</tr>
          			<tr>
          				<td><i class="ty-table-bottom-l"></i></td><td><i class="ty-table-bottom"></i></td><td><i class="ty-table-bottom-r"></i></td>
          			</tr>
          		</table>
          	 </div>
          	<div> 
          		 <div class="clear">
	          		 <table class="ty-tj-table mt10 ml6">
	          			<tr>
	          				<td width="10"><i class="ty-table-top-l"></i></td><td><i class="ty-table-top"></i></td><td width="10"><i class="ty-table-top-r"></i></td>
	          			</tr>
		          		<tr>
	          				<td colspan="3" class="ty-tj-td pd10">
	          					<div class="ty-tj-title">警情类型
	          					<span class="fr cursor" id="btnAddType1" onclick="clearAlarmTypeList()"><button class="ty-btn-qc"></button>清除</span>
	          					<span class="fr cursor" id="btnAddType1" onclick="addAlarmTypeList()"><button class="ty-btn-tj"></button>添加</div>
	          					<div class="ty-tj-type-list">
		          					<ul id="ul_alarmTypeList"> 
			            		 		<li>
			            		 			无相关警情类型
			            		 		</li>
			            		 	</ul>
		            		 	</div>
	          				</td>
	          			</tr>
	          			<tr>
	          				<td><i class="ty-table-bottom-l"></i></td><td><i class="ty-table-bottom"></i></td><td><i class="ty-table-bottom-r"></i></td>
	          			</tr>
	          		</table>
            	</div>
          	</div>
          	<div> 
          		 <div class="clear">
          		 	<table class="ty-tj-table mt10 ml6">
	          			<tr>
	          				<td width="10"><i class="ty-table-top-l"></i></td><td><i class="ty-table-top"></i></td><td width="10"><i class="ty-table-top-r"></i></td>
	          			</tr>
		          		<tr>
	          				<td colspan="3" class="ty-tj-td pd10">
	          					<div class="ty-tj-title">警情级别</div>
	          					<div id="div_alarmLevel">  
			            		 	<label class="lbla"><input id="ckalarmLevel1" type="checkbox" value="1">一级警情</label>
									<label class="lbla"><input id="ckalarmLevel2" type="checkbox" value="2">二级警情</label>
									<label class="lbla"><input id="ckalarmLevel3" type="checkbox" value="3">三级警情</label> 
			            		</div>
	          				</td>
	          			</tr>
	          			<tr>
	          				<td><i class="ty-table-bottom-l"></i></td><td><i class="ty-table-bottom"></i></td><td><i class="ty-table-bottom-r"></i></td>
	          			</tr>
	          		</table> 
            	</div>
          	</div>
          	<div> 
          		 <div class="clear">
          		 	<table class="ty-tj-table mt10 ml6">
	          			<tr>
	          				<td width="10"><i class="ty-table-top-l"></i></td><td><i class="ty-table-top"></i></td><td width="10"><i class="ty-table-top-r"></i></td>
	          			</tr>
		          		<tr>
	          				<td colspan="3" class="ty-tj-td pd10">
	          					<div class="ty-tj-title">周期 </div>
	          					<div id="div_dateType" class="fl fit mt10">  
			            		  	<div class="lbltime" >
			            		  		<input id="radio_bymonth" type="radio" name="timeType" value="0" onclick="searchByMonth();" />按月查询(不超过12月)
			            		  	</div>
			            		  	<div class="lbltimespan" >起　<input id="dpSDate" />　</div>
			            		  	<div class="lbltimespan mt4">止　<input id="dpEDate"  /></div>
			            		   
			            		  	<div class="lbltime" >
			            		  		<input id="radio_byday" type="radio" name="timeType" value="1" onclick="searchByDay();" />按天查询(不超过31天)
			            		  	</div>
			            			<div class="lbltimespan" >起　<input id="dpSDay"   /></div>
			            			<div class="lbltimespan mt4">止　<input id="dpEDay"   /></div>
			            		</div> 
	          				</td>
	          			</tr>
	          			<tr>
	          				<td><i class="ty-table-bottom-l"></i></td><td><i class="ty-table-bottom"></i></td><td><i class="ty-table-bottom-r"></i></td>
	          			</tr>
	          		</table>
            	</div>
          	</div>
          	<div> 
          		 <div class="clear">
          		 	<table class="ty-tj-table mt10 ml6">
	          			<tr>
	          				<td width="10"><i class="ty-table-top-l"></i></td><td><i class="ty-table-top"></i></td><td width="10"><i class="ty-table-top-r"></i></td>
	          			</tr>
		          		<tr>
	          				<td colspan="3" class="ty-tj-td pd10">
	          					<div class="ty-tj-title">时间段<span class="fr cursor" id="btnAddType2" onclick="addOtherTimeSpan()"><button class="ty-btn-gdtj"></button>更多</span></div>
	          					<div id="div_dateType" class="fl fit mt10">  
			            		  	<div class="lbltime" >
			            		  		<div class="lbltimespan" >时间段：</div>
			            		  		<div id="div_selectTimespan" class="lbltimespan" >00：00-23：59</div>
			            		  	</div> 
			            		</div> 
	          				</td>
	          			</tr>
	          			<tr>
	          				<td><i class="ty-table-bottom-l"></i></td><td><i class="ty-table-bottom"></i></td><td><i class="ty-table-bottom-r"></i></td>
	          			</tr>
	          		</table>
            	</div>
            	<div class="fl fit mt10">
            		<div class="ty-nav-line2">
            			<i class="ty-nav-line2-left"></i><i class="ty-nav-line2-right"></i>
            		</div>
            	</div>
          	</div>
		</div>
        </div><!----机构树结束---->
        <div class="fl fit">
          	<p class="leftSearchbutton">
          		<button id="btnAddType1" class="ty-button" onclick="searchAction();">搜索</button> 
            </p>
        </div> 
		<div class="ty-left-bottom-line"><i class="fl line8"></i><i class="ty-left-bottom-line2"></i></div>
</div>  

<div id="alarmTypeWindow" style="display:none">
	<div id="alarmTypeListWin" style="overflow:hidden"> 
		<div style="overflow:auto;">
			<div id="alarmtreeview" style="height:370px;">
			</div>
		</div>
    </div> 
</div>
<div id="timeSpanWindow" style="display:none">
	<div id="timeSpanWin" style="overflow:hidden">
		 
		<div style="overflow:hidden;height:190px">
			<table id="div_timaspan" style="width:100%;height:91%;border:0px;">
				<tr>
					<td style="width:150px;text-align:center">
						<img  id="img_timespan" width="140px" />
					</td>
					<td>
						<style>
							#ul_timespan {width:160px;} 
							#ul_timespan li {float:left;width:40px;padding:5px; list-style: none;font-size:13px;}
							#ul_timespan li:hover {float:left;width:40px;padding:5px; list-style: none;cursor:pointer;background:black;font-size:12px;}
							.liselect {float:left;width:40px;padding:5px; list-style: none;cursor:pointer;color:wheat;}
						</style>
						<ul id="ul_timespan">
							<li id="li_timespan1" name='li_timespan' onclick="selectTimeSpan(1)">全天</li>
							<li id="li_timespan2" name='li_timespan'  onclick="selectTimeSpan(2)">白天</li>
							<li id="li_timespan3" name='li_timespan'  onclick="selectTimeSpan(3)">晚上</li>
							<li id="li_timespan5" name='li_timespan'  onclick="selectTimeSpan(5)">早高峰</li>
							<li id="li_timespan6" name='li_timespan'  onclick="selectTimeSpan(6)">晚高峰</li>
							<li id="li_timespan4" name='li_timespan'  onclick="selectTimeSpan(4)">凌晨</li>
						</ul>
					</td>
				</tr> 
			</table> 
		</div>
    </div> 
</div>
<div id="dialog"></div>
<script type="text/javascript">
var m_organId = 1;   
var alarmTypeArr = [];
var alarmTypeNameArr = [];
var alarmPeriodXLabel = []; 
var alarmOrganXLabel = [];
var alarmTimeSpanArr=[0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23];
var m_isFirst_load = true; 
var m_Query_pkg={};
var m_Query_pkgComplete = false;
var m_statistic_typeId = 3;
var m_isLeaf_organ = false;
var m_orgName ="" ;
var m_pageType;
$(function() {
	    m_orgName = $("#organName").val(); 
	    m_pageType = $("#pageType").val(); 
	    if(m_pageType == 3||m_pageType == "3"){
	    	m_statistic_typeId = 3;
	    	$("#btnalarmType").parent().find("span").each(function(i){$(this).removeClass("ty-btn-selected");});
	    	$("#btnalarmCircle span").addClass("ty-btn-selected");
	    }else{ 
	   		m_statistic_typeId = 4;
	   		$("#btnalarmType").parent().find("span").each(function(i){$(this).removeClass("ty-btn-selected");});
	   		$("#btnalarmTimeSpan span").addClass("ty-btn-selected");
	   	}
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
		m_Query_pkg.periodType = 1;
		$("#dpSDate").data("kendoDatePicker").enable(false);
		$("#dpEDate").data("kendoDatePicker").enable(false); 
	 	searchAction();
	 	if(m_pageType==3){
	   		$("#h_title").text("警情统计——周期统计");
	   	}else if(m_pageType==4){
	   		$("#h_title").text("警情统计——时间段统计");
	   	}
	 	setH();
	 	$("#navigationLeft .tree-box").mCustomScrollbar({scrollButtons:{enable:true},advanced:{ updateOnContentResize: true } });
});
function setH(){
	var o = $("#content-wrapper .span12 .span8");
	var o2 = $("#navigationLeft .tree-box");
	var ct=0;
	if(o.length>0){
		ct = o.offset().top;
	}else{
		if(o2.length>0){
			ct = o2.offset().top;
		}else{
			return;
		}
	}
	var wh = $(window).height();
	var v = wh-ct-70;
	$("#navigationLeft .tree-box").css("height",v);
	$(".pow").css("height",v);
	$(".pow .box-content").css("height",v);
}
function loadAlarmTypeList(){ 
	$.ajax({
		url:"<%=basePath%>alarmStatisticWeb/getAlarmTypeList.do?warningId=0",
		type:"post", 
		dataType:"json",
		success:function(req){
				if(req.code == 200){ 
					 req.data.parentId = null;
					  if(alarmTypeArr.length>0){
						 $.each(alarmTypeArr,function(index1,sobj){
					 		 $.each(req.data.items,function(index2,pobj){
					 		 		if(sobj == pobj.typeCode){
					 		 			pobj.checked = true;
					 		 		}
					 		 		$.each(pobj.items,function(index3,cobj){
					 		 			if(sobj == cobj.typeCode){
					 		 				cobj.checked = true;
					 		 			}
					 		 		});
					 		 });
						 });
					 }else{ 
					 		 $.each(req.data.items,function(index2,pobj){ 
					 		 			pobj.checked = true; 
					 		 		$.each(pobj.items,function(index3,cobj){ 
					 		 				cobj.checked = true; 
					 		 		});
					 		 }); 
					 }
					 
					var json_data = JSON.stringify(req.data);
					$("#tyTccMain #alarmtreeview").empty();
					$("#tyTccMain #alarmtreeview").kendoTreeView({ 
					    checkboxes: {
					      	checkChildren: true//级联选择
					   	}, 
					    width:200,
					    dataTextField: "text",  
		    			check : onCheck,//check复选框
					    dataSource: [eval('(' + json_data + ')')]
					}).data("kendoTreeView"); 
					 onCheck();
	 				$("#tyTccMain #alarmtreeview").mCustomScrollbar({scrollButtons:{enable:true},advanced:{ updateOnContentResize: true } });
				}
		}
	});
}
function selectTimeSpan(timet){ 
	//var sObj = $("#div_timaspan input[name='timeArea']:checked");
	//var timet = sObj[0].value;
	switch(timet)
	{
		case 1:
			$("#tyTccMain #img_timespan").attr("src","<%=basePath%>images/images/allDay.png"); 
			alarmTimeSpanArr=[0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23];
			$("#div_selectTimespan").html("00:00--23:59");
			$("#tyTccMain li[name='li_timespan']").removeAttr("class");
			$("#tyTccMain #li_timespan1").attr("class","liselect"); 
			break;
		case 2:
			$("#tyTccMain #img_timespan").attr("src","<%=basePath%>images/images/brightless.png");
			alarmTimeSpanArr=[6,7,8,9,10,11,12,13,14,15,16,17,18,19,20];
			$("#div_selectTimespan").html("06:00--19:59");
			$("#tyTccMain li[name='li_timespan']").removeAttr("class");
			$("#tyTccMain #li_timespan2").attr("class","liselect"); 
			break;
		case 3:
			$("#tyTccMain #img_timespan").attr("src","<%=basePath%>images/images/nightless.png");
			alarmTimeSpanArr=[20,21,22,23,0,1,2,3,4,5,6];
			$("#div_selectTimespan").html("20:00--05:59");
			$("#tyTccMain li[name='li_timespan']").removeAttr("class");
			$("#tyTccMain #li_timespan3").attr("class","liselect"); 
			break;
		case 4:
			$("#tyTccMain #img_timespan").attr("src","<%=basePath%>images/images/morning.png");
			alarmTimeSpanArr=[23,0,1,2,3,4,5,6];
			$("#div_selectTimespan").html("23:00--05:59");
			$("#tyTccMain li[name='li_timespan']").removeAttr("class");
			$("#tyTccMain #li_timespan4").attr("class","liselect"); 
			break;
		case 5:
			$("#tyTccMain #img_timespan").attr("src","<%=basePath%>images/images/heavyMorning.png");
			alarmTimeSpanArr=[7,8,9,10];
			$("#div_selectTimespan").html("07:00--09:59");
			$("#tyTccMain li[name='li_timespan']").removeAttr("class");
			$("#tyTccMain #li_timespan5").attr("class","liselect"); 
			break;
		case 6:
			$("#tyTccMain #img_timespan").attr("src","<%=basePath%>images/images/heavyEveing.png");
			alarmTimeSpanArr=[16,17,18,19,20];
			$("#div_selectTimespan").html("16:00--19:59");
			$("#tyTccMain li[name='li_timespan']").removeAttr("class");
			$("#tyTccMain #li_timespan6").attr("class","liselect"); 
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
	/* $("#dpSDay").data("kendoDatePicker").value("");
	$("#dpEDay").data("kendoDatePicker").value("");
	$("#dpSDate").data("kendoDatePicker").value("");
	$("#dpEDate").data("kendoDatePicker").value("");  */
	m_Query_pkg.periodType = 3;
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
	/* $("#dpSDay").data("kendoDatePicker").value("");
	$("#dpEDay").data("kendoDatePicker").value("");
	$("#dpSDate").data("kendoDatePicker").value("");
	$("#dpEDate").data("kendoDatePicker").value(""); */ 
	m_Query_pkg.periodType = 1;
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
			}else{ 
				alarmPeriodXLabel = [];
				for(var m = months; m<monthe+1; m++){
					alarmPeriodXLabel.push(m);
				} 
				if(months<10){
					months = "0"+months;
				}
				if(monthe<10){
					monthe = "0"+monthe;
				}
				var dayse = "";
				if(monthe==2){
					if(((yeare%4)==0)&&((yeare%100)!=0)||((yeare%400)==0)){ 
						dayse = "29";
					}else{
						dayse = "28";
					}
				}else if(monthe==4||monthe==6||monthe==9||monthe==11){
					dayse = "30";
				}else{
					dayse = "31";
				}
				m_Query_pkg.startDate =  years + "-" + months + "-" + "01";
				m_Query_pkg.endDate = yeare + "-" + monthe + "-" + dayse;
				m_Query_pkg.periodType = 3;
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
				for(var m = months; m<13; m++){
					alarmPeriodXLabel.push(m);
				} 
				for(var n = 1; n<monthe+1; n++){
					alarmPeriodXLabel.push(n);
				} 
				if(months<10){
					months = "0"+months;
				}
				if(monthe<10){
					monthe = "0"+monthe;
				}
				var dayse = "";
				if(monthe==2){
					if(((yeare%4)==0)&&((yeare%100)!=0)||((yeare%400)==0)){ 
						dayse = "29";
					}else{
						dayse = "28";
					}
				}else if(monthe==4||monthe==6||monthe==9||monthe==11){
					dayse = "30";
				}else{
					dayse = "31";
				}
				m_Query_pkg.startDate =  years + "-" + months + "-" + "01";
				m_Query_pkg.endDate = yeare + "-" + monthe + "-" + dayse;
				m_Query_pkg.periodType = 3;
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
				if(months<10){
					months = "0"+months;
				}
				if(monthe<10){
					monthe = "0"+monthe;
				}
				if(days<10){
					days = "0"+days;
				}
				if(daye<10){
					daye = "0"+daye;
				}
				m_Query_pkg.startDate = years + "-" + months + "-" + days;
				m_Query_pkg.endDate = yeare + "-" + monthe + "-" + daye; 
				m_Query_pkg.periodType =1; 
			}
		}
	}
} 
	 var cf = true;
	 var m_checkedNodes_code = "";
	 var m_checkedNodes_name = ""
	 		function parentNodeClick(parentId,orgname){
	 			alarmOrganXLabel.length = 0;
	 			m_organId = parentId;
	 			m_orgName = orgname;   
	 			$("#organName").val(m_orgName);
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
								$("#div_orgPath").append("当前机构路径："+parentHtml);
								
								if(req.data.length>0){
									for(var i = 0;  i<req.data.length;i++ ){
										subListHtml += "<a name='a_orgName' style='margin-left:30px;float:left'";
										if(req.data[i].hasChild){
											subListHtml += "  onclick=parentNodeClick("+req.data[i].id+",'"+req.data[i].shortName+"')";
										}else{
											subListHtml += " onclick=loadData("+req.data[i].id+",'"+req.data[i].shortName+"')";
										}
										subListHtml += ">"+req.data[i].shortName+"</a>";
									}
								}  
								m_isLeaf_organ = false;
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
	 			$("#organName").val(m_orgName);
	 			alarmOrganXLabel.length = 0;
	 			//alert("加载数据");
	 			searchAction(m_statistic_typeId);
	 		}
	 		function addAlarmTypeList(){ 
	 			$("#dialog").tyWindow({
					width: "355px",
	                height:"570px",
	                title: "警情选择",
					position : {
						top:"25%",
	                    left:"340px"
					},
					content: $("#alarmTypeListWin").html(),
						iframe : false,
						"ok":true,  
						okCallback:confirmAlarmType
				});
				
				
	 			loadAlarmTypeList();
	 			//var win =$('#alarmTypeListWin');
				//win.kendoWindow({
	            //            width: "225px",
	            //            height:"570px",
	            //            title: "警情选择",
	           //             position:{
	           //             	top:"25%",
	           //             	left:"340px"
	           //             }
	           //         });
				//win.data("kendoWindow").open();
	 		}
	 		function onCheck(e) {
					var checkedNodes = [],checkNodesName=[], treeView = $("#tyTccMain #alarmtreeview").data("kendoTreeView"), message,nameStr; 
		
					checkedNodeCode(treeView.dataSource.view(),checkedNodes,checkNodesName);
					if (checkedNodes.length > 0) {
						message = checkedNodes.join(",");
						nameStr = checkNodesName.join(",");
						m_checkedNodes_code = message;
						m_checkedNodes_name = nameStr;
					} else {
						message = "";
						nameStr = "";
						m_checkedNodes_code = message;
						m_checkedNodes_name = nameStr;
					}
			}
			function checkedNodeCode(nodes, checkedNodes,checkNodesName) {
				for ( var i = 0; i < nodes.length; i++) {
					if (nodes[i].checked) {
						if(nodes[i].typeCode.indexOf("0000")==-1){
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
	 			alarmTypeArr.length = 0;
	 			if(m_checkedNodes_code.length>0){
	 		 		alarmTypeArr = m_checkedNodes_code.split(","); 
	 		 	}
	 		 	var namecodeArr = m_checkedNodes_name.split(",");
	 		 	var tyhtml = "";
	 		 	for(var i = 0;i<namecodeArr.length;i++){
	 		 		var namecode = namecodeArr[i];
	 		 		var s = namecode.split("|"); 
	 		 		alarmTypeNameArr.push(s[0]); 
	 		 	}
	 		 	
	 		 	if(alarmTypeArr.length>0){
	 		 		tyhtml += "<li>已选择<span style='font-size:14px;color:red'> " + alarmTypeArr.length + "</span> 个警情类型</li>";
	 		 	}
	 		 	
	 		 	$("#ul_alarmTypeList").empty();
	 		 	if(tyhtml.length>0){
 					$("#ul_alarmTypeList").html(tyhtml);
 				}else{ 
 					$("#ul_alarmTypeList").html("<li>无相关警情类型</li>")
 				}
				//var win= $("#alarmTypeListWin").data("kendoWindow");
				//win.close();
	 		}  
	 		function clearAlarmTypeList(){
	 			alarmTypeNameArr.length = 0;
	 			alarmTypeArr.length = 0;
	 			m_checkedNodes_code = "";
	 			m_checkedNodes_name = "";
	 			$("#ul_alarmTypeList").html("<li>无相关警情类型</li>");
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
	 			//var treeview = $("#alarmtreeview").data("kendoTreeView");
	 			//var dataSource = treeview.dataSource;
	 			//var dataItem = dataSource.get(typeid);
	 			//dataItem.checked = false;
	 			//var uid = dataItem.uid;
	 			//$("#alarmtreeview li[data-uid='"+uid+"']:input[type='checkbox']")[0].children[0].children[0].children[0].removeAttr("checked"); 
	 		}
	 		function addOtherTimeSpan(){  
	 			
				$("#img_timespan").attr("src","<%=basePath%>images/images/allDay.png");
	 			$("#dialog").tyWindow({
					width: "380px",
	                height:"220px",
	                title: "小时选择",
					position : {
						top:"50%",
	                    left:"340px"
					},
					content: $("#timeSpanWin").html(),
						iframe : false,
						"ok":true, 
						closeCallback : confirmTimeSpan,
						okCallback:confirmTimeSpan
				});
	 		
	 			//var win =$('#timeSpanWin');
				//win.kendoWindow({
	            //            width: "300px",
	            //            height:"250px",
	            //            title: "小时选择",
	            //            position:{
	            //            	top:"50%",
	            //            	left:"340px"
	            //            }
	            //        });
				//win.data("kendoWindow").open(); 
				//$("#li_timespan1").attr("class","liselect");
	 		}
	 		function confirmTimeSpan(){
	 			//var win= $("#timeSpanWin").data("kendoWindow");
				//win.close();
	 		}
	 		var az = true;
	        function leftZoomx(obj){
    		//箭头点击收放效果
    			if(az){
    				$("#main-nav").animate({"width":0,"margin-left":"-18px"},"fast",function(){
    					$("#leftNotice").hide();
    					$(obj).removeClass("ty-btn-zoom").addClass("ty-btn-zoom-in");
    					$("#content").animate({"margin-left":"0"},'slow');
    				});
    				az=false;
    			}else{
    				$("#content").animate({"margin-left":"340px"},'slow',function(){
    					$("#leftNotice").show();
						$("#main-nav").animate({"width":338,"margin-left":"0"},"fast");
					});
					$(obj).removeClass("ty-btn-zoom-in").addClass("ty-btn-zoom");
					az=true;
    			}
	        } 
	        
	        function onChangeReportAction(reportType){
	        	$("#btnalarmType").parent().find("span").each(function(i){$(this).removeClass("ty-btn-selected");});
	        	//searchAction(reportType);
	        	if(reportType == 3){
	        		m_statistic_typeId = 3;
	        		$("#h_title").text("警情统计——周期统计");
	        		$("#btnalarmCircle span").addClass("ty-btn-selected");
	        	}else if(reportType == 4){
	        		m_statistic_typeId = 4;
	        		$("#h_title").text("警情统计——时间段统计");
	        		$("#btnalarmTimeSpan span").addClass("ty-btn-selected");
	        	}else{
	        		m_statistic_typeId = 3;
					$("#h_title").text("警情统计——周期统计");
					$("#btnalarmCircle span").addClass("ty-btn-selected");
	        	}
	        }
	        
	        function getActionUrl (rType){
	        	var urlStr = "";
	        	switch(rType)
	        	{ 
	        		case 3:
	        			urlStr = "<%=basePath%>caseReportWeb/loadCasePeriodReport.do";
	        			break;
	        		case 4:
	        			urlStr = "<%=basePath%>caseReportWeb/loadCaseHourReport.do";
	        			break;
	        	}
	        	return urlStr;
	        }
	         function searchAction(){
	         	if(m_isFirst_load){
	         		m_isFirst_load = false;
	         		return;
	         	} 
	         	var url = getActionUrl(m_statistic_typeId); 
	         	packageQuery();
	         	if(!m_Query_pkgComplete){ 
	         		return;
	         	}
	         	$.ajax({
						url : url,
						type : "post",
						data : {"query" : JSON.stringify(m_Query_pkg)},
						dataType : "json",
						success : function(req) { 
	         				m_isFirst_load = false;
	   						m_orgName = $("#organName").val();
							if(req.code==200){ 
								if(m_statistic_typeId == 3){
									ReportManage.initAlarmCircleData(req.data,m_orgName,alarmPeriodXLabel);
								}else if(m_statistic_typeId == 4){
									ReportManage.initAlarmTimeSpanData(req.data,m_orgName);
								}
							}else{
								$("body").popjs({"title":"提示","content":"查询统计数据失败！"});
							} 
						}
					}); 
	         }
	        
	       function packageQuery(){ 
	       		 m_Query_pkg.organId = m_organId; 
	       		 m_Query_pkg.caseType = alarmTypeArr;
	       		 if(m_Query_pkg.caseType.length==0&&m_Query_pkg.caseType.length==0){
	       			$("body").popjs({"title":"提示","content":"请选择警情类型，至少选取一个警情类型！","callback":function(){
					 		return;
						}});   
	       			return;
	       		 }
	       		 m_Query_pkg.caseTimaSpan = alarmTimeSpanArr;
	       		 m_Query_pkg.caseLevels = []
	       		 var alrlel = $("#div_alarmLevel input:checkbox:checked");
	       		 $.each(alrlel,function(index,s){
	       		 	m_Query_pkg.caseLevels.push(s.value);
	       		 });  
	       		 if(m_Query_pkg.caseLevels.length==0){
	       			$("body").popjs({"title":"提示","content":"请选择警情级别，至少选取一个等级警情！","callback":function(){
					 		return;
						}});   
	       			return;
	       		 }
	       		 
	       		 if(m_Query_pkg.startDate ==undefined||m_Query_pkg.startDate ==""){
	       		 	$("body").popjs({"title":"提示","content":"请选择日期查询方式，并选择相应开始日期！","callback":function(){
					 		return;
						}});   
	       			return;
	       		 }
	       		 if(m_Query_pkg.endDate ==undefined||m_Query_pkg.endDate ==""){
	       		 	$("body").popjs({"title":"提示","content":"请选择截止日期！","callback":function(){
					 		return;
						}});   
	       			return;
	       		 }
	       		 m_Query_pkgComplete = true;
	       }
</script>
