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
<script type="text/javascript">
	$(function(){
		//警情状态复选效果
		jqczStatus();
		$("#tyJqczTable tr").each(function(i){
			$(this).click(function(){
				var id = $(this).find("input[type='hidden']").val();
				if(id==undefined){
					id = $("#tyJqczTable tr").eq(i-1).find("input[type='hidden']").val();
				}
				getAlarmInfo(id);
			});
		});
		$("#tyJqczTable").parent().mCustomScrollbar({scrollButtons:{enable:true},advanced:{ updateOnContentResize: true } });
		$("#dpSDay").kendoDatePicker({
			format: "yyyyMMdd"
		});	
		$("#dpEDay").kendoDatePicker({
			format: "yyyyMMdd"
		});
	});
	
	function jqczStatus(){
		var sItem = "1,2,5,9,";
		$("#jqczStatus .ty-jqcz-icon1").each(function(index){
			var s = "ty-jqcz-icon"+(index+1)+"-1";
			$(this).click(function(){
				if(index==0)i=1;
				else if(index==1)i=2;
				else if(index==2)i=5;
				else if(index==3)i=9;
				if($(this).hasClass(s)){
					$(this).removeClass(s);
					if(sItem.indexOf(i)<0){
						sItem+=i+",";
					}
					//alert("选中"+sItem);
					$("#alarmState").val(sItem);
				}else{
					$(this).addClass(s);
					if(sItem.indexOf(i)>-1){
						sItem = sItem.replace(i+",","");
						$("#alarmState").val(sItem);
					}
				}
			});
		});
		
	}
</script>
<div id="map" style="width: 100%;height:100%"></div>
							<div class="ty-tcc" style="width:410px;height:500px;position:absolute;top:0;left:-14px;" id="ty-tcc">
  <table class="ty-tcc-table">
    <tr class="ty-tcc-tit">
      <td width="30"><i class="ty-tcc-bg1"></i><i class="ty-tcc-bg4 opa9"></i></td>
      <td><i class="ty-tcc-bg2"></i>
        <div class="ty-tcc-top opa9"></div></td>
      <td width="30"><i class="ty-tcc-bg3"></i><i class="ty-tcc-bg5 opa9"></i>
        <div class="temp"><i class="ty-tcc-close" id="tccCloses"></i></div></td>
    </tr>
    <tr>
      <td></td>
      <td><div class="ty-tcc-main opa9" id="tyTccMains">
          <div class="ty-tcc-mh">
            <table class="ty-tcc-table">
              <tr>
                <td><div id="tyTccTitles" class="fl mr10"><i class="ty-tcc-mh-icon"></i><span class="ty-tcc-title1">
                    <p>警情处置</p>
                    <em></em></span><span class="ty-tcc-title2"></span></div></td>
                <td><span class="ty-tcc-title3">
                  <div class="temp"><em></em></div>
                  </span></td>
              </tr>
            </table>
          </div>
          <div class="ty-tcc-info">
            <table id="tyTccTables" class="ty-tcc-table">
              <tr>
                <td>
                	<div class="fl ty-jqcz-title">
                    	<span class="ty-menu-selected">今日警情</span><span>历史警情</span>
                    </div>
                    <div class="fl ty-jqcz-panel">
                    	<button class="fl ty-btn-area mt2" onclick="addOrganList()"></button>
                        <button class="fl ty-btn-term mt2" onclick="changeCondition()"></button>
                        <input type="text" class="fl ty-jqcz-search" /><button class="fl ty-btn-search"></button>
                        <div class="ty-jqcz-line"></div>
                        <div id="condition" style="display: none"> 
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
	          					<input type="hidden" class="k-textbox" id="organCodes" name="organCodes"/>
	          					<input type="hidden" class="k-textbox" id="alarmTypeTwo" name="alarmTypeTwo"/>
	          					<input type="hidden" class="k-textbox" id="alarmState" name="alarmState" value="1,2,5,9"/>
	          					<div class="ty-tj-title" onclick="addAlarmTypeList()">警情类型选择</div>
	          				</td>     
	          			</tr>
	          			<tr>
	          				<td><i class="ty-table-bottom-l"></i></td><td><i class="ty-table-bottom"></i></td><td><i class="ty-table-bottom-r"></i></td>
	          			</tr>
	          		</table>
            	</div>
          	</div>
                        <div class="fl fit" id="jqczStatus">
                        	<div class="fl ty-jqcz-icon1"><i></i>未派警</div>
                            <div class="fl ty-jqcz-icon1 ty-jqcz-icon2"><i></i>已派警</div>
                            <div class="fl ty-jqcz-icon1 ty-jqcz-icon3"><i></i>到场</div>
                            <div class="fl ty-jqcz-icon1 ty-jqcz-icon4"><i></i>结案</div>
                        </div>
                        <button id="btnAddType1" class="ty-button" onclick="searchAlarm();">搜索</button>
                        <table class="ty-tj-table mt10 ml6">
                            <tr>
                                <td width="10"><i class="ty-table-top-l2"></i></td><td><i class="ty-table-top2"></i></td><td width="10"><i class="ty-table-top-r2"></i></td>
                            </tr>
                            <tr>
                                <td colspan="3" class="ty-tj-td">
                                    <div class="fl ty-box-bg">
                                    	<table class="ty-jqcz-box-table" id="tyJqczTable">
                                    	<tbody>
                                            </tbody>
                                        </table> 
	          		                     <div id="alarmGrid"></div> 
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td><i class="ty-table-bottom-l2"></i></td><td><i class="ty-table-bottom2"></i></td><td><i class="ty-table-bottom-r2"></i></td>
                            </tr>
                        </table>
                        <div class="clear"></div>
                    </div>
                    
                    
                </td>
              </tr>
            </table>
          </div>
        </div></td>
      <td></td>
    </tr>
    <tr>
      <td><i class="ty-tcc-bg6 opa9"></i></td>
      <td><div class="ty-tcc-bottom opa9"></div></td>
      <td><i class="ty-tcc-bg7 opa9"></i></td>
    </tr>
    <tr>
      <td><i class="ty-tcc-bg8"></i></td>
      <td><i class="ty-tcc-bg9"></i></td>
      <td><i class="ty-tcc-bg10"></i></td>
    </tr>
  </table>
  
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
</div>
<!-- 警情列表弹出层结束-->
<!-- 警情详情-->
                          <div class="al-panel" id="alPanel">
                          <%@include file="alarmMsg.jsp" %>
                          </div>
                          <!-- 警情详情结束 -->							
<style type="text/css">
.ty-tcc {
	display:none;
}
/**新加样式*/
.mt2{margin-top:2px;}
.ty-jqcz-title{float:left;width:354px;height:32px;background:url(../Skin/Default/images/icon.png) no-repeat 0 -272px;}
.ty-jqcz-title span{float:left;width:auto;height:24px;line-height:24px;padding:0px 14px;font-size:13px;color:#81C9F0;margin:4px 0px 0px 4px;}
.ty-jqcz-title .ty-menu-selected{background-color:#87020B;color:#FFF;}
.ty-jqcz-panel{width:344px;height:auto;background-color:#1C3442;margin-top:10px;padding:10px 5px;}
.ty-btn-area{width:59px;height:28px;background:url(../Skin/Default/images/icon.png) no-repeat -363px -272px;border:0;}
.ty-btn-term{width:59px;height:28px;background:url(../Skin/Default/images/icon.png) no-repeat right -273px;border:0;}
.ty-jqcz-search{width:160px;height:23px;line-height:23px;padding:0px 4px;background-color:#1A2830;color:#81C9F0;border:1px solid #5B7784;margin:4px 0px 0px 10px;}
.ty-btn-search{width:25px;height:25px;border:1px solid #5B7784;background:url(../Skin/Default/images/icon.png) -455px -2px #1A2A34;cursor:pointer;margin:4px 0px 0px 10px;}
.ty-jqcz-line{float:left;width:100%;height:2px;background-color:#3A6177;margin-top:10px;}
.ty-jqcz-icon1{width:auto;height:30px;line-height:30px;color:#81C9F0;font-size:13px;}
.ty-jqcz-icon1 i{float:left;width:11px;height:16px;background:url(../Skin/Default/images/icon.png) no-repeat 0px -313px;display:block;margin:8px 5px 0px 5px;}
.ty-jqcz-icon1-1 i{background-position:0px -329px;}
.ty-jqcz-icon2 i{background-position:-19px -313px;}
.ty-jqcz-icon2-1 i{background-position:-19px -329px;}
.ty-jqcz-icon3 i{background-position:-37px -313px;}
.ty-jqcz-icon3-1 i{background-position:-37px -329px;}
.ty-jqcz-icon4 i{background-position:-57px -313px;}
.ty-jqcz-icon4-1 i{background-position:-57px -329px;}
.ty-box-bg{width:100%;height:400px;background-color:#040607;border-left:1px solid #132B38;border-right:1px solid #132B38;}

.ty-table-top-l2{float:left;width:10px;height:10px;background:url("../Skin/Default/images/tj_icon.png") no-repeat -51px -205px;display:block;}
.ty-table-top2{float:left;width:100%;height:9px;border-top:1px solid #628392;background-color:#040607;}
.ty-table-top-r2{float:left;width:10px;height:10px;background:url("../Skin/Default/images/tj_icon.png") no-repeat -118px -205px;display:block;}
.ty-table-bottom-l2{float:left;width:10px;height:10px;;background:url("../Skin/Default/images/tj_icon.png") no-repeat -51px -217px;display:block;}
.ty-table-bottom2{float:left;width:100%;height:9px;border-bottom:1px solid #628392;background-color:#040607;}
.ty-table-bottom-r2{float:left;width:10px;height:10px;;background:url("../Skin/Default/images/tj_icon.png") no-repeat -118px -217px;display:block;}
.ty-jqcz-box-table{float:left;width:100%;height:auto;border:0;border-collapse:collapse;border-spacing:0;table-layout:fixed;z-index:110;}
.ty-jqcz-box-table td{padding:0;color:#81C9F0;font-size:13px;}
.ty-jqcz-bt-td{border-bottom:1px solid #43687B;}
.ty-jqcz-color{width:13px;height:50px;line-height:50px;color:#3F2715;font-size:18px;font-weight:bold;}
.ty-jqcz-color1{background-color:#E94847;}
.ty-jqcz-color2{background-color:#F9A96A;}
.ty-jqcz-color3{background-color:#097FEC;}
.ty-jqcz-color4{background-color:#33AA46;}
.ty-jqcz-gps{float:left;width:16px;height:16px;background:url(../images/tj_icon.png) no-repeat -149px -205px;display:block;margin:0px 5px;}
.ty-jqcz-bt-txt{color:#097FEC;padding:0px 5px;}
</style>
<script type="text/javascript">
var i=1;
function changeCondition(){
	if(i==1){
		$("#condition").show();
		i=0;
	}else{
		$("#condition").hide();
		i=1;
	}
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
        		var alarmState = $.trim($("#alarmState").val());
        		$("#tyJqczTable tbody").remove();
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
/*            						if (msg.data == undefined) {
           							msg.data=[];
    							} */
           						if(msg.data != null){
           							$(msg.data).each(function(){
           								$("#tyJqczTable").append("<tbody><tr>"
           								+"<td rowspan='2' class='ty-jqcz-bt-td' width='13'><input type='hidden' value='"+this.jjdbh+"'/><div class='ty-jqcz-color ty-jqcz-color1'>×</div></td><td><i class='ty-jqcz-gps'></i>"+this.bjsj+"</td><td width='110'>"+this.bjnr+"</td>"
           								+"</tr><tr>"
           								+"<td class='ty-jqcz-bt-td'><span class='ty-jqcz-bt-txt'>一</span> "+this.bjlxmc+"</td><td class='ty-jqcz-bt-td'>"+this.sfdz+"</td>"
           								+"</tr></tbody>");
           	       				    });
           							$("#tyJqczTable tr").each(function(i){
           								$(this).click(function(){
           									var id = $(this).find("input[type='hidden']").val();
           									if(id==undefined){
           										id = $("#tyJqczTable tr").eq(i-1).find("input[type='hidden']").val();
           									}
           									getAlarmInfo(id);
           								});
           							});
           						}
           				}
           			}
           		});
            }
</script>
<%@include file="/base/map/overviewToobar.jsp" %>
<%@include file="/base/map/searchToobar.jsp" %>

	



