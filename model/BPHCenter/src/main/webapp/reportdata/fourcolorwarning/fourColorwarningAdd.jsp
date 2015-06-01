<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>


<!DOCTYPE html>
<html>
<head>
<title>扁平化指挥系统</title>
<meta
	content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no'
	name='viewport' />

<%@ include file="../../emulateIE.jsp" %>
<style type="text/css"> 
	#CasealarmInfo td {width:14%;}
	#ul_alarmTypeList li{float:left;list-style:none; padding:3px;width:150px;}
	#tbl_alarmType li{float:left;list-style:none;width:130px;}
 	#tbl_alarmType {border:1px solid #fff;margin-top:5px;}
 	#tbl_alarmType td{border:1px solid #fff;}
 	#tbl_alarmType tr{border:1px solid #fff;}  
</style>
<script type="text/javascript"> 
$(function() {
	$("#ckalarmLevel1").attr("checked","checked");
});
var bph_warningAdd_pkg={};  
var alarmParentTypeArr = []; 
var alarmSubTypeArr = []; 
var alarmTypeNameArr = [];
var isPackage_complete = false;
var WarningAddManage= {
	addAlarmType:function(){
		$.ajax({
					url:"<%=basePath%>alarmStatisticWeb/getAlarmTypeList.do",
					type:"post", 
					dataType:"json",
					success:function(req){
							if(req.code == 200){ 
								var html = "";  
								
								for(var j = 0; j< req.data.length; j++){
									  html += "<tr><td style='width:100px'>";
									  html += "<input id='ipt_"+req.data[j].alarmType.typeCode+"' name='parentAlarmType' type='checkbox' value='"+req.data[j].alarmType.typeCode+"' /><span id='sp_"+req.data[j].alarmType.typeCode+"'>"+ req.data[j].alarmType.typeName+ "</span></td><td><ul>";  
									  for(var m =0; m<req.data[j].alarmTypeList.length;m++){
									  	html += "<li><input  id='ipt_"+req.data[j].alarmTypeList[m].typeCode+"'  name='subAlarmType' type='checkbox' value='"+req.data[j].alarmTypeList[m].typeCode+"' /><span id='sp_"+req.data[j].alarmTypeList[m].typeCode+"'>"+req.data[j].alarmTypeList[m].typeName+"</span></li>"; 
									  } 
									  html += "<ul></td></tr>";
								} 
								$("#tbl_alarmType").empty();
								$("#tbl_alarmType").html(html);
								
	 							for(var i = 0; i<alarmParentTypeArr.length;i++){
	 								$("#ipt_"+alarmParentTypeArr[i]).attr("checked","checked"); 
	 							}
	 		
	 							for(var i = 0; i<alarmSubTypeArr.length;i++){
	 								$("#ipt_"+alarmSubTypeArr[i]).attr("checked","checked"); 
	 							}
							}
					}
				});
	 		
	 			var win =$('#alarmTypeListWin');
				win.kendoWindow({
	                        width: "750px",
	                        height:"450px",
	                        title: "警情选择"
	                    });
				win.data("kendoWindow").open();
	},
	confirmAlarmType:function(){
		alarmParentTypeArr.length = 0;
		alarmSubTypeArr.length = 0;
		alarmTypeNameArr.length = 0;
		var parentcount = $("#tbl_alarmType input[name='parentAlarmType']:checkbox:checked").length;
		var totalhtml = "";
		if(parentcount > 0){
			var parenttypeHtml = "";
			var parentalarmTypeObj = $("#tbl_alarmType input[name='parentAlarmType']:checkbox:checked");
			$.each(parentalarmTypeObj, function(index, pobj){
				var tpcode = pobj.value;
				var tpName = pobj.parentElement.innerText;
				alarmParentTypeArr.push(tpcode);
				alarmTypeNameArr.push(tpName);
				var tpName = $("#sp_"+tpcode).html();
				parenttypeHtml += "<li id='li_"+tpcode+"'>";
				parenttypeHtml += tpName + "<button type='button' class='ty-delete-btn' title='删除' onclick=WarningAddManage.deleteParentNode('"+tpcode+"','"+tpName+"')></button> ";
				parenttypeHtml += "</li>";
			}); 
			
			totalhtml +=parenttypeHtml; 
			
		}else{ 
			alarmParentTypeArr = [];  
		}
		
		var subcount = $("#tbl_alarmType input[name='subAlarmType']:checkbox:checked").length;
		if(subcount > 0){
			var subtypeHtml = "";
			var subalarmTypeObj = $("#tbl_alarmType input[name='subAlarmType']:checkbox:checked");
			$.each(subalarmTypeObj, function(index, sobj){
				var spcode = sobj.value;
				var spName = sobj.parentElement.innerText;
				alarmSubTypeArr.push(spcode);
				alarmTypeNameArr.push(spName);
				var tpName = $("#sp_"+spcode).html();
				subtypeHtml += "<li id='li_"+spcode+"'>";
				subtypeHtml += tpName + "<button type='button' class='ty-delete-btn' title='删除' onclick=WarningAddManage.deleteSubNode('"+spcode+"','"+tpName+"')></button> ";
				subtypeHtml += "</li>";
			}); 
			totalhtml += subtypeHtml
			
		}else{
			
			alarmSubTypeArr = [];
		} 
		$("#ul_alarmTypeList").empty();
		if(totalhtml.length>0){
			$("#ul_alarmTypeList").html(totalhtml);
		}
		var win= $("#alarmTypeListWin").data("kendoWindow");
		win.close();
	},
	deleteParentNode:function(code,tName){ 
		$("#li_"+code).hide();
		$.each(alarmParentTypeArr,function(index,value){
			if(code == value){
				alarmParentTypeArr.splice(index,1);
			}
		}); 
		$.each(alarmTypeNameArr,function(index,value){
			if(tName == value){
				alarmTypeNameArr.splice(index,1);
			}
		});  
	 },
	 deleteSubNode:function(code,tName){ 
		$("#li_"+code).hide();
		$.each(alarmSubTypeArr,function(index,value){
			if(code == value){
				alarmSubTypeArr.splice(index,1);
			}
		}); 
		$.each(alarmTypeNameArr,function(index,value){
			if(tName == value){
				alarmTypeNameArr.splice(index,1);
			}
		});  
	},
	saveWarningAction:function(){
		WarningAddManage.packageData();
		if(isPackage_complete){
			$.ajax({
				url : "<%=basePath%>colorWarningWeb/saveWarningConfig.do",
				type : "post",
				data : {
					'colorWarning' : JSON.stringify(bph_warningAdd_pkg)
				}, 
				dataType : "json",
				success : function(req) { 
					$("body").popjs({"title":"提示","content":"修改预警信息成功！","callback":function(){ 
							window.parent.window.parent.WarningManage.onClose();
							window.parent.$("#dialog").tyWindow.close();
						}});   
				}
			}); 
		}else{
			$("body").popjs({"title":"提示","content":"请完善预警信息，再保存"});
		}
	},
	packageData:function(){
		bph_warningAdd_pkg.id = 0;
		bph_warningAdd_pkg.orgId = $("#organId").val();
		var caseName = $.trim($("#warningname").val());
		if(caseName.length == 0){
			$("body").popjs({"title":"提示","content":"请输入预警名称","callback":function(){
								$("#warningname").focus();
								return;
							}});    
					
				return;
		}
		if(caseName.length > 30){
			$("body").popjs({"title":"提示","content":"预警名称长度过长，限制长度为1-30！","callback":function(){
								$("#warningname").focus();
								return;
							}});    
					
				return;		
		}
		var myReg = /^[^@\/\'\\\"#$%&\^\*]+$/;
		if(!myReg.test(caseName)){
				$("body").popjs({"title":"提示","content":"预警名称不能包含特殊字符！","callback":function(){
						$("#warningname").focus();
								return;
					}});  
				return;
		}
		bph_warningAdd_pkg.name = caseName;
		bph_warningAdd_pkg.share = false;
		bph_warningAdd_pkg.colors = [];
		var rehod = /\D/ig;
		var redObj = {};
		redObj.id = 0; 
		redObj.warningId = 0;
		redObj.level = 1;
		var redGe = $.trim($("#redvalue").val());
		if(redGe.length == 0){
			$("body").popjs({"title":"提示","content":"红色预警增幅值，不能为空！","callback":function(){
						$("#redvalue").focus();
								return;
					}});  
				return;
		}
		if(rehod.test(redGe)){
			$("body").popjs({"title":"提示","content":"红色预警增幅值，只能为数字！","callback":function(){
						$("#redvalue").focus();
								return;
					}});  
				return;
		}
		var xr=parseInt(redGe);
		if(xr>100){
			$("body").popjs({"title":"提示","content":"红色预警增幅值，不能超过100%！","callback":function(){
						$("#redvalue").focus();
								return;
					}});  
				return;
		}
		redObj.ge = redGe;
		redObj.defaultColor = "red";
		bph_warningAdd_pkg.colors.push(redObj);
		
		var yellowObj = {};
		yellowObj.id = 0;
		yellowObj.warningId = 0;
		yellowObj.level = 2;
		var yellowGe = $.trim($("#yellowvaluea").val());
		if(yellowGe.length == 0){
			$("body").popjs({"title":"提示","content":"黄色预警增幅上线值，不能为空！","callback":function(){
						$("#yellowvaluea").focus();
								return;
					}});  
				return;
		}
		if(rehod.test(yellowGe)){
			$("body").popjs({"title":"提示","content":"红色预警增幅上线值，只能为数字！","callback":function(){
						$("#yellowvaluea").focus();
								return;
					}});  
				return;
		}
		var yra=parseInt(yellowGe);
		if(yra>100){
			$("body").popjs({"title":"提示","content":"黄色预警增幅上线值，不能超过100%！","callback":function(){
						$("#yellowvaluea").focus();
								return;
					}});  
				return;
		}
		yellowObj.ge = yellowGe;
		
		var yellowLt = $.trim($("#yellowvalueb").val());
		if(yellowLt.length == 0){
			$("body").popjs({"title":"提示","content":"黄色预警增幅下线值，不能为空！","callback":function(){
						$("#yellowvalueb").focus();
								return;
					}});  
				return;
		}
		if(rehod.test(yellowLt)){
			$("body").popjs({"title":"提示","content":"红色预警增幅下线值，只能为数字！","callback":function(){
						$("#yellowvalueb").focus();
								return;
					}});  
				return;
		}
		var yrb=parseInt(yellowLt);
		if(yrb>100){
			$("body").popjs({"title":"提示","content":"黄色预警增幅下线值，不能超过100%！","callback":function(){
						$("#yellowvalueb").focus();
								return;
					}});  
				return;
		}
		yellowObj.lt = yellowLt;
		yellowObj.defaultColor = "yellow";
		bph_warningAdd_pkg.colors.push(yellowObj);
		var blueObj = {};
		blueObj.id = 0;
		blueObj.warningId = 0;
		blueObj.level = 3;
		var blueGe = $.trim($("#bluevaluea").val());
		if(blueGe.length == 0){
			$("body").popjs({"title":"提示","content":"黄色预警增幅上线值，不能为空！","callback":function(){
						$("#bluevaluea").focus();
								return;
					}});  
				return;
		}
		if(rehod.test(blueGe)){
			$("body").popjs({"title":"提示","content":"红色预警增幅上线值，只能为数字！","callback":function(){
						$("#bluevaluea").focus();
								return;
					}});  
				return;
		}
		var bra=parseInt(blueGe);
		if(bra>100){
			$("body").popjs({"title":"提示","content":"黄色预警增幅上线值，不能超过100%！","callback":function(){
						$("#bluevaluea").focus();
								return;
					}});  
				return;
		}
		blueObj.ge = blueGe;
		
		var blueLt = $.trim($("#bluevalueb").val());
		if(blueLt.length == 0){
			$("body").popjs({"title":"提示","content":"黄色预警增幅下线值，不能为空！","callback":function(){
						$("#bluevalueb").focus();
								return;
					}});  
				return;
		}
		if(rehod.test(blueLt)){
			$("body").popjs({"title":"提示","content":"红色预警增幅下线值，只能为数字！","callback":function(){
						$("#bluevalueb").focus();
								return;
					}});  
				return;
		}
		var brb=parseInt(blueLt);
		if(brb>100){
			$("body").popjs({"title":"提示","content":"黄色预警增幅下线值，不能超过100%！","callback":function(){
						$("#bluevalueb").focus();
								return;
					}});  
				return;
		}
		blueObj.lt = blueLt;
		blueObj.defaultColor = "blue";
		bph_warningAdd_pkg.colors.push(blueObj);
		var greenObj = {};
		greenObj.id = 0;
		greenObj.warningId = 0;
		greenObj.level = 4;
		var greenGe = $.trim($("#greenvalue").val());
		if(greenGe.length == 0){
			$("body").popjs({"title":"提示","content":"红色预警增幅值，不能为空！","callback":function(){
						$("#greenvalue").focus();
								return;
					}});  
				return;
		}
		if(rehod.test(greenGe)){
			$("body").popjs({"title":"提示","content":"红色预警增幅值，只能为数字！","callback":function(){
						$("#greenvalue").focus();
								return;
					}});  
				return;
		}
		var gr=parseInt(greenGe);
		if(gr>100){
			$("body").popjs({"title":"提示","content":"红色预警增幅值，不能超过100%！","callback":function(){
						$("#greenvalue").focus();
								return;
					}});  
				return;
		}
		greenObj.ge = greenGe;
		greenObj.defaultColor = "green";
		bph_warningAdd_pkg.colors.push(greenObj);
		bph_warningAdd_pkg.caseTypes = [];
		var alarmLevel = $("input[name='alarmLevel']:checked")[0].value
		if(alarmParentTypeArr.length==0&&alarmSubTypeArr.length==0){
			$("body").popjs({"title":"提示","content":"请选择相关警情类型！","callback":function(){
						return;
					}});  
				return;
		} 
		$.each(alarmParentTypeArr,function(index,value){
			var palarmType = {};
	 		palarmType.id = 0;
	 		palarmType.warningId = 0;
	 		palarmType.caseTypeCode = value;
	 		palarmType.caseTypeLevel = alarmLevel;
	 		bph_warningAdd_pkg.caseTypes.push(palarmType);
	 	});  
		$.each(alarmSubTypeArr,function(index,value){
			var salarmType = {};
	 		salarmType.id = 0;
	 		salarmType.warningId = 0;
	 		salarmType.caseTypeCode = value;
	 		salarmType.caseTypeLevel = alarmLevel;
	 		bph_warningAdd_pkg.caseTypes.push(salarmType);
	 	}); 
	 	isPackage_complete = true;
	},
	cancelAdd:function(){
		window.parent.window.parent.WarningManage.onClose();
		window.parent.$("#dialog").tyWindow.close();
	}
};
</script>
</head>
<body class="ty-body">
	<div id="vertical" style="overflow-x:hidden;">
		<div id="horizontal" style="height: 300px; width: 780px;">
			<div class="pane-content">
				<!-- 左开始 -->
				<div id="CasealarmInfo" class="demo-section k-header"> 
					<table style="width:100%;heigth:680px">
						<tr>
							<td colspan="7">
								<h4>名称</h4>
							</td>
						</tr>
						<tr>
							<td>
							</td> 
							<td colspan="4">
								<span class="ty-input-warn">*</span><label class="ty-input-label" for="warningname">名称:</label><input type="text"  class="k-textbox"   name="warningname" id="warningname" required="required" />
							</td>
						</tr>
						<tr>
							<td colspan="7">
								<h4>预警规则</h4>
							</td>
						</tr>
							<td>
							</td>
							<td>
								<label style="margin-left:20px">增幅 = </label>
							</td>
							<td colspan="2" style="text-align:center">
								<table style="width:100%">
									<tr style="border-bottom:1px solid #97c6dc">
										<td><label>周期    - 上一周期</label>
										</td>
									</tr>
									<tr>
										<td><label>上一周期</label>
										</td>
									</tr>
								</table> 
							</td>
							<td>
								<label>×100%</label>
							</td> 
							<td colspan="5">
								(‘周期’为‘周期设置’中选择的周期，‘上一周期’为选择周期的前一周期)
							</td>
						<tr>
							<td> 
								<table style="width:90%;height:80%;padding:3px"><tr><td style="background-color:red;height:25px"></td></tr></table> 
							</td>
							<td>
								<label style="margin-left:20px">红色预警</label>
							</td>
							<td>
								<label style="margin-left:20px">增幅  </label>
							</td>
							<td>
								<label>≥</label>
							</td>
						 	<td colspan="3">
								<input type="text" style="width:20px"  name="redvalue" id="redvalue" required="required" />%
							</td> 
						</tr>
						<tr> 
							<td>  
								<table style="width:90%;padding:3px"><tr><td style="background-color:yellow;height:25px"></td></tr></table> 
							</td>
							<td>
								<label style="margin-left:20px">黄色预警</label>
							</td>
							<td>
								<label style="margin-left:20px">增幅  </label>
							</td>
							<td>
								<label>≥</label>
							</td>
							<td>
								<input type="text"  style="width:20px"  name="yellowvaluea" id="yellowvaluea" required="required" />%
							</td>
							<td>
								<label style="margin-left:20px">,增幅 </label>
							</td>
							<td>
								<label style="margin-left:20px"><</label>
							</td> 
							<td>
								<input type="text"  style="width:20px"   name="yellowvalueb" id="yellowvalueb" required="required" />%
							</td>
						</tr>
						<tr>
							<td> 
								<table style="width:90%;padding:3px"><tr><td style="background-color:blue;height:25px"></td></tr></table>  
							</td>
							<td>
								<label style="margin-left:20px">蓝色预警</label>
							</td>
							<td>
								<label style="margin-left:20px">增幅  </label>
							</td>
							<td>
								<label>≥</label>
							</td>
							<td>
								<input type="text"  style="width:20px"   name="bluevaluea" id="bluevaluea" required="required" />%
							</td>
							<td>
								<label style="margin-left:20px">,增幅 </label>
							</td>
							<td>
								<label style="margin-left:20px">< </label>
							</td> 
							<td>
								<input type="text"  style="width:20px"   name="bluevalueb" id="bluevalueb" required="required" />%
							</td>
						</tr>
							<td> 
								<table style="width:90%;padding:3px"><tr><td style="background-color:green;height:25px"></td></tr></table> 
							</td>
							<td>
								<label style="margin-left:20px">绿色预警</label>
							</td>
							<td>
								<label style="margin-left:20px">增幅  </label>
							</td>
							<td>
								<label><</label>
							</td>
							<td>
								<input type="text"  style="width:20px"  name="greenvalue" id="greenvalue" required="required" />%
							</td> 
						<tr>
							<td colspan="7">
								<h4>警情级别</h4>
							</td>
						</tr>
						<tr>
							<td colspan="2" style="text-align:center"> 
								<input id="ckalarmLevel1" type="radio" name="alarmLevel" value="1" />一级警情
							</td>
							<td colspan="2" style="text-align:center">
								<input id="ckalarmLevel2" type="radio" name="alarmLevel" value="2" />二级警情
							</td>
							<td colspan="2" style="text-align:center">
								<input id="ckalarmLevel3" type="radio" name="alarmLevel" value="3" />三级警情
							</td>
						</tr>
						<tr>
							<td colspan="7">
								<h4>警情类别</h4>
							</td>
							<td>
								<span id="undo" class="k-button" onclick="WarningAddManage.addAlarmType()">添加</span>  
							</td>
						</tr>
						<tr>
							<td colspan="10"> 
								<ul id="ul_alarmTypeList">  
								</ul>
							</td>
						</tr>
					</table>
					<p class="ty-input-row"> 
						<button id="undo" class="ty-button" onclick="WarningAddManage.saveWarningAction()">保存</button>
						<button id="undo" class="ty-button" onclick="WarningAddManage.cancelAdd()">取消</button>
					</p> 
				</div>
			</div>
		</div>
	</div>
<div id="alarmTypeWindow" style="display:none">
	<div id="alarmTypeListWin" style="overflow:hidden">
		<div><button id="undo" class="ty-button" onclick="WarningAddManage.confirmAlarmType();">确定</button></div> 
		<div style="overflow:auto;height:380px">
			<table id="tbl_alarmType" >
			</table> 
		</div>
    </div> 
</div>
</body> 
</html>
