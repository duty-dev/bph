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
 

<!--引用需要的脚本-->
<style type="text/css">
#map{
    position: relative;
    border:1px solid #3473b7;
}
</style>
<script  type="text/javascript" charset="utf-8"  src="<%=basePath %>supermap/libs/SuperMap.Include.js"></script>
<script  type="text/javascript" charset="utf-8"  src="<%=basePath %>supermap/libs/map.js"></script>
<script type="text/javascript">

$("#mapdiv").height($(window).height()-200);
$("#mapdiv").width($(window).width()-35);
var url2 = "http://25.30.9.42:8090/iserver/services/spatialanalyst-changchun/restjsr/spatialanalyst";//长春市范围(缓冲区)
   $(document).ready(function(){
	   MapManager.setMapZoom(1);
   	   initMap();
});
  
</script>
<div id="map" style="width: 100%;height:100%"></div>
							<div class="al-panel" id="alPanel">
							<!-- 警情展示内容 -->
							<div id="navigationLeft">
        <div class="tree-box clear"><!----条件查询---->
        <div id="box">
             <div class="clear">
              	<div style="width:100%;float:left"><h2 class="no-bg">警情处置</h2></div>
              	<div class="ty-nav-line2">
              		<i class="ty-nav-line2-left"></i><i class="ty-nav-line2-right"></i>
              	</div> 
              	<div style="width:100%;float:left"><div class="ty-tj-title">今日警情</div><div class="ty-tj-title">历史警情</div></div>
              	<div class="ty-nav-line2">
              		<i class="ty-nav-line2-left"></i><i class="ty-nav-line2-right"></i>
              	</div> 
          		<table class="ty-tj-table mt10 ml6">
	          			<tr>
	          				<td width="10"><i class="ty-table-top-l"></i></td><td><i class="ty-table-top"></i></td><td width="10"><i class="ty-table-top-r"></i></td>
	          			</tr>
		          		<tr>
	          				<td colspan="3" class="ty-tj-td pd10">
	          					<div class="ty-tj-title" onclick="addOrganList()">辖区选择</div>
	          					<div class="ty-tj-title" onclick="addAlarmTypeList()">警情类型</div>
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
          	<div> 
          		 <div class="clear">
	          		 <table class="ty-tj-table mt10 ml6">
	          			<tr>
	          				<td width="10"><i class="ty-table-top-l"></i></td><td><i class="ty-table-top"></i></td><td width="10"><i class="ty-table-top-r"></i></td>
	          			</tr>
	          			<tr>
	          				<td colspan="3" class="ty-tj-td pd10">
	          				    <div class="lbltimespan">开始时间　<input id="dpSDay"/></div>
			            		<div class="lbltimespan">结束时间　<input id="dpEDay"/></div>
	          					<div>关键字: <input type="search" class="k-textbox" id="keyWord" name="keyWord"/></div>
	          					<div>事发地址: <input type="search" class="k-textbox" id="alarmAddress" name="alarmAddress"/></div>
	          					<div>报警电话: <input type="search" class="k-textbox" id="alarmPhone" name="alarmPhone"/></div>
	          					<div>警情定位： <select class="w176" id="alarmLocation">
	          					<option value="-1">全部</option>
	          					<option value="1">已定位</option>
	          					<option value="0">未定位</option>
	          					</select>
	          					</div>
	          					<div>警情级别： <select class="w176" id="alarmLevel">
	          					<option value="1">一级警情</option>
	          					<option value="2">二级警情</option>
	          					<option value="3">三级警情</option>
	          					</select>
	          					</div>
	          					<input type="search" class="k-textbox" id="organCodes" name="organCodes"/>
	          					<input type="search" class="k-textbox" id="alarmTypeTwo" name="alarmTypeTwo"/>
	          					<p class='ty-input-p'><input type="checkbox" id="alarmState" name="alarmState" value="1"/>未派警</p>
	          					<p class='ty-input-p'><input type="checkbox" id="alarmState" name="alarmState" value="2"/>已派警</p>
	          					<p class='ty-input-p'><input type="checkbox" id="alarmState" name="alarmState" value="5"/>到场</p>
	          					<p class='ty-input-p'><input type="checkbox" id="alarmState" name="alarmState" value="9"/>结案</p>
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
          		 <div class="ty-nav-line2 mt10">
              		<i class="ty-nav-line2-left"></i><i class="ty-nav-line2-right"></i>
              	 </div>
          		 <p class="leftSearchbutton">
          		 	<button id="btnAddType1" class="ty-button" onclick="searchAlarm();">搜索</button> 
            		</p>
            	</div>
          	</div>
          	<div> 
          		 <div class="clear">
	          		 <div id="alarmGrid"></div>
            	</div>
          	</div>
          	
		</div>
        </div><!----条件查询结束----> 
        <div class="line8 box"></div>
        <div class="line2"></div>
</div>    

<div id="alarmTypeWindow" style="display:none">
	<div id="alarmTypeListWin" style="overflow:hidden">
		<div style="overflow:auto;">
			<div id="alarmtreeview" style="overflow:hidden">
			</div>
		</div>
    </div> 
</div>
<div id="organWindow" style="display:none">
	<div id="organListWin" style="overflow:hidden">
		<div style="overflow:auto;">
			<div id="organtreeview" style="overflow:hidden">
			</div>
		</div>
    </div> 
</div>
<!-- 警情展示内容结束 -->							
								<div id="alarmGrid"></div>
</div>
<!-- 警情列表弹出层结束-->
<!-- 警情详情-->
                          <div class="al-panel1" id="alPanel1">
                          <%@include file="alarmMsg.jsp" %>
                          </div>
                          <!-- 警情详情结束 -->							

<style type="text/css">
.al-panel {
	width: 400px;
	height: 650px;
	position: absolute;
	top: 0;
	left: 0;
	background-color: #233742;
	display:none;
}
.al-panel1 {
	width: 400px;
	height: 650px;
	position: absolute;
	top: 0;
	left: 401px;
	background-color: #233742;
}

.al-sf {
	float: left;
	width: 24px;
	height: 70px;
	position: absolute;
	top: 0;
	left: 450px;
}

.al-line {
	float: left;
	width: 3px;
	height: 63px;
	background: url(<%=basePath%>Skin/Default/images/button4.png) no-repeat;
	display: block;
}

.al-icon {
	float: right;
	width: 19px;
	height: 19px;
	background: url(<%=basePath%>Skin/Default/images/button3.png) no-repeat;
	display: block;
}

.al-icon-s {
	float: right;
	width: 19px;
	height: 19px;
	background: url(<%=basePath%>Skin/Default/images/button3-1.png)
		no-repeat;
	display: block;
}

#mkMenu {
	background-color: #233742;
	color: #81C9F0;
}

#mkMenu ul {
	margin: 0;
	padding: 0;
}

#mkMenu li {
	float: left;
	width: 98%;
	height: 30px;
	text-align: center;
	font-size: 15px;
	line-height: 30px;
	border: 1px solid #4A748A;
	cursor: pointer;
}
#mkMenu li:hover {
	color: #00b0ff;
}
.ty-box-main{float:left;width:100%;height:auto;margin:0 atuo;background-color:#121E24;clear:both;position:absolute;display:none;}
.ty-box-title{float:left;width:72px;height:22px;line-height:22px;padding:0px 4px;margin-left:8px;font-size:13px;color:#81C9F0;background-color:#121E24;text-align:center;top:14px;left:0px;}
.ty-box-table{float:left;width:100%;height:auto;border:0;border-collapse:collapse;border-spacing:0;table-layout:fixed;color:#81C9F0;}
.ty-box-table td{padding:0;}
.ty-box-top-left{float:left;width:8px;height:8px;background:url(images/b1.png) no-repeat;font-size:1px;}
.ty-box-top{float:left;width:100%;height:6px;border-top:1px solid #4A748B;}
.ty-box-top-right{float:left;width:8px;height:8px;background:url(images/b2.png) no-repeat;font-size:1px;}
.ty-box-bottom-left{float:left;width:8px;height:8px;background:url(images/b3.png) no-repeat;font-size:1px;}
.ty-box-bottom{float:left;width:100%;height:6px;border-bottom:1px solid #4A748B;}
.ty-box-bottom-right{float:left;width:8px;height:8px;background:url(images/b4.png) no-repeat;font-size:1px;}
.ty-box-info{float:left;width:100%;height:auto;border-left:1px solid #4A748B;border-right:1px solid #4A748B;display:none;}
</style>
<script type="text/javascript">
/* var m_organId = 1;   
var alarmTypeArr = [];
var alarmTypeNameArr = [];
var alarmPeriodXLabel = []; 
var alarmOrganXLabel = [];
var alarmTimeSpanArr=[0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23];
var m_isFirst_load = true; 
var m_Query_pkg={};
var m_Query_pkgComplete = false;
var m_orgName ;
var m_statistic_typeId = 1;
var m_pageType;
$(function() {
		$("#dpSDay").kendoDatePicker({
			format: "yyyyMMdd"
		});	
		$("#dpEDay").kendoDatePicker({
			format: "yyyyMMdd"
		});
}); */

/* function selectTimeSpan(){ 
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
	
} */
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
loadAlarmTypeList();
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
	$("#alarmTypeTwo").val(message);
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

	 		function addOrganList(){ 
	 			var win =$('#organListWin');
				win.kendoWindow({
	                        width: "225px",
	                        height:"570px",
	                        title: "辖区选择",
	                        position:{
	                        	top:"25%",
	                        	left:"20%"
	                        }
	                    });
				win.data("kendoWindow").open();
	 		}
	 		function loadOrganList(){ 
            	$.ajax({
            		url:"<%=basePath%>web/organx/tree.do",
            		type:"post", 
            		dataType:"json",
            		success:function(req){
            				if(req.code == 200){ 
            					 req.data.parentId = null;
            					var json_data = JSON.stringify(req.data);
            					$("#organtreeview").empty();
            					$("#organtreeview").kendoTreeView({ 
            					    checkboxes: true, 
            					    width:200,
            					    dataTextField: "text",  
            		    			check : organOnCheck,//check复选框
            					    dataSource: [eval('(' + json_data + ')')]
            					}).data("kendoTreeView"); 
            				}
            		}
            	});
            }
	 		loadOrganList();
            function organOnCheck(e) {
                var checkedNodes = [],
                  treeView = $("#organtreeview").data("kendoTreeView"),message;

                //treeToJson(treeView.dataSource.view());
                
                checkedNodeIds(treeView.dataSource.view(), checkedNodes);

                if (checkedNodes.length > 0) {
                    message = checkedNodes.join(",");
                } else {
                    message = "";
                }
                alert(message);
                $("#organCodes").val(message);
            }
            
            function checkedNodeIds(nodes, checkedNodes) {
                for (var i = 0; i < nodes.length; i++) {
                    if (nodes[i].checked) {
                        checkedNodes.push(nodes[i].code);
                    } 
                    if (nodes[i].hasChildren) {
                        checkedNodeIds(nodes[i].children.view(), checkedNodes);
                    }
                }
            } 
	 		/*
	 		function confirmTimeSpan(){
	 			var win= $("#timeSpanWin").data("kendoWindow");
				win.close();
	 		}
	                 
/**
		*左边菜单栏改变时调用
		*/
	 	function searchAlarm() {
	      loadAlarmLit();
		}
			/* var r = document.getElementsByName("alarmState");
        	var alarmState = "";
        	for (i= 0 ;i < r.length; i++){
        	  if(r[i].checked == true){
        			if(alarmState == ""){
        				alarmState = r[i].value;
        			}else{
        				alarmState = alarmState + "," + r[i].value;
        			}
        		}
        	} */
        	
			function loadAlarmLit() {
            	$.ajax({
           			url:"<%=basePath%>alarmWeb/getAlarmList.do",
           			type:"post",
           			dataType:"json",
           			data:{
           				alarmLocation : $("#alarmLocation").val(),
						//startTime : $("#dpSDay").val()+"000000",
						//endTime : $("#dpEDay").val()+"232359",
						startTime : "20150515000000",
						endTime : "20150615232359",
						keyWord:$("#keyWord").val(),
						alarmPhone:$("#alarmPhone").val(),
						alarmAddress:$("#alarmAddress").val(),
						alarmLevel:$("#alarmLevel").val(),
						organCodes:$.trim($("#organCodes").val()),
						alarmTypeTwo:$.trim($("#alarmTypeTwo").val())
           			},
           			success:function(msg){
           				if(msg.code==200){
           						if (msg.data == undefined) {
           							msg.data=[];
    							}
           						//var total = msg.totalRows;
           						//document.getElementById("gridListTotal").innerHTML=total+"人";
           						$("#alarmGrid").kendoGrid({
           						 selectable : "multiple",
           	                     dataSource: {data:msg.data},
                                 columns : [ {
										title : 'Id',
										field : 'jjdbh',
										hidden : true
									}, 
									{
										title : '报警类型',
										field : 'bjlxmc'
									},{
										title : '报警时间',
										field : 'bjsj'
									}  
	                        		, {
										title : '报警内容',
										field : 'bjnr'
									}, {
										title : '事发地址',
										field : 'sfdz'
									}],
           	                         change: function (e) {
                                     var alarmId = e.sender.selectable.userEvents.currentTarget.cells[0].innerHTML;
                                     //loadTree(alarmId); 
                                     alert(alarmId);
           	                         }
           	                    });
           						var myGrid = $("#alarmGrid").data("kendoGrid");
           						//添加双击事件
           						/* var myGrid = $("#grid").data("kendoGrid");
           						myGrid.element.on("dblclick","tbody>tr","dblclick",function(e){
           							var id = $(this).find("td").first().text();
           							editUser(id);
           						});
           						var pg = pagination(pageNo,total,'loadData',10);
           	                	$("#page").html(pg); */
           				}
           			}
           		});
            }
</script>
<%@include file="/base/map/overviewToobar.jsp" %>
<%@include file="/base/map/searchToobar.jsp" %>

	



