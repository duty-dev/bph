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
</style>
<script type="text/javascript"> 
$(function() {
	$("#ckalarmLevel1").attr("checked","checked");
	WarningAddManage.loadalarmTypeList();
});
var bph_warningAdd_pkg={};   
var m_checkedNodes_code ="";
var isPackage_complete = false;
var rehod = /\D/ig;
var WarningAddManage= {
	loadalarmTypeList:function(){
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
								    checkboxes: {
					      				checkChildren: true//允许复选框多选
					   				},
								    dataTextField: "text", 
								    height:450,
					    			check : WarningAddManage.onCheck,//check复选框
								    dataSource: [eval('(' + json_data + ')')]
								}).data("kendoTreeView");
								$("#alarmtreeview").parent().mCustomScrollbar({scrollButtons:{enable:true},advanced:{ updateOnContentResize: true } });
							}
						}
				});
	},
	onCheck : function(e) {
		var checkedNodes = [], treeView = $("#alarmtreeview").data("kendoTreeView"), message; 
		
		WarningAddManage.checkedNodeCode(treeView.dataSource.view(),checkedNodes);
		if (checkedNodes.length > 0) {
			message = checkedNodes.join(",");
			m_checkedNodes_code = message;
		} else {
			message = "";
		}
	},
	checkedNodeCode : function(nodes, checkedNodes) {
		for ( var i = 0; i < nodes.length; i++) {
			if (nodes[i].checked) {
				if(nodes[i].typeCode.indexOf("0000")==-1){
					checkedNodes.push(nodes[i].typeCode);
				}
			}
			if (nodes[i].hasChildren) {
				WarningAddManage.checkedNodeCode(nodes[i].children.view(),
						checkedNodes);
			}
		}
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
					$("body").popjs({"title":"提示","content":"新增预警信息成功！","callback":function(){ 
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
		var xr=parseInt(redGe);
		redObj.ge = redGe;
		redObj.defaultColor = "red";
		bph_warningAdd_pkg.colors.push(redObj);
		
		var yellowObj = {};
		yellowObj.id = 0;
		yellowObj.warningId = 0;
		yellowObj.level = 2;
		var yellowGe = $.trim($("#yellowvaluea").val());
		if(yellowGe.length == 0){
			$("body").popjs({"title":"提示","content":"黄色预警增幅下限值，不能为空！","callback":function(){
						$("#yellowvaluea").focus();
								return;
					}});  
				return;
		} 
		var yra=parseInt(yellowGe); 
		yellowObj.ge = yellowGe;
		
		var yellowLt = $.trim($("#yellowvalueb").val());
		if(yellowLt.length == 0){
			$("body").popjs({"title":"提示","content":"黄色预警增幅上限值，不能为空！","callback":function(){
						$("#yellowvalueb").focus();
								return;
					}});  
				return;
		} 
		var yrb=parseInt(yellowLt); 
		if(yra > yrb || yrb == yra){
			$("body").popjs({"title":"提示","content":"黄色预警下限值，不能超过上限值！","callback":function(){
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
			$("body").popjs({"title":"提示","content":"紫色预警增幅下限值，不能为空！","callback":function(){
						$("#bluevaluea").focus();
								return;
					}});  
				return;
		} 
		var bra=parseInt(blueGe); 
		blueObj.ge = blueGe;
		
		var blueLt = $.trim($("#bluevalueb").val());
		if(blueLt.length == 0){
			$("body").popjs({"title":"提示","content":"紫色预警增幅上限值，不能为空！","callback":function(){
						$("#bluevalueb").focus();
								return;
					}});  
				return;
		} 
		var brb=parseInt(blueLt); 
		
		if(bra > brb || bra == brb){
			$("body").popjs({"title":"提示","content":"紫色预警下限值，不能超过上限值！","callback":function(){
						$("#yellowvalueb").focus();
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
			$("body").popjs({"title":"提示","content":"绿色预警增幅值，不能为空！","callback":function(){
						$("#greenvalue").focus();
								return;
					}});  
				return;
		} 
		var gr=parseInt(greenGe); 
		
		greenObj.ge = greenGe;
		greenObj.defaultColor = "green";
		bph_warningAdd_pkg.colors.push(greenObj);
		bph_warningAdd_pkg.caseTypes = [];
		if(m_checkedNodes_code.length==0){ 
			$("body").popjs({"title":"提示","content":"请选择警情类别！"});
			return;
		}
		var typeList = m_checkedNodes_code.split(",");
		for(var n = 0;n<typeList.length;n++){
			var typeObj = {};
			typeObj.warningId = 0;
			typeObj.id=0;
			typeObj.caseTypeCode = typeList[n];
			if(typeList[n].indexOf("0000")==-1){
				typeObj.caseTypeLevel = 2; 
			}else{
				typeObj.caseTypeLevel = 1; 
			}
			bph_warningAdd_pkg.caseTypes.push(typeObj);
		}
		
		bph_warningAdd_pkg.caseLevels = [];
		var alarmLevel = $("input[name='alarmLevel']:checked");
		if(alarmLevel.length == 0){
			$("body").popjs({"title":"提示","content":"请选择警情级别！"});
			return;
		}
		$.each(alarmLevel,function(index,obj){
			var clevel = {};
			clevel.id = 0;
			clevel.warningId = 0;
			clevel.caseLevel = obj.value;
			bph_warningAdd_pkg.caseLevels.push(clevel);
		});
	 	isPackage_complete = true;
	},
	cancelAdd:function(){
		window.parent.window.parent.WarningManage.onClose();
		window.parent.$("#dialog").tyWindow.close();
	},
	onFillinYellowB:function(){
		var redGe = $.trim($("#redvalue").val());
		 if(redGe.length>0){
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
			$("#yellowvalueb").val(redGe);
		} 
	},
	onFillinblueB:function(){
		var yellowGe = $.trim($("#yellowvaluea").val());
		if(yellowGe.length > 0){ 
			if(rehod.test(yellowGe)){
				$("body").popjs({"title":"提示","content":"黄色预警增幅下限值，只能为数字！","callback":function(){
						$("#yellowvaluea").focus();
								return;
					}});  
				return;
			}
			var yra=parseInt(yellowGe);
			if(yra>100){
				$("body").popjs({"title":"提示","content":"黄色预警增幅下限值，不能超过100%！","callback":function(){
						$("#yellowvaluea").focus();
								return;
					}});  
				return;
			}
			
			$("#bluevalueb").val(yellowGe);
		}
	},
	onFillingreen:function(){
		var blueGe = $.trim($("#bluevaluea").val());
		if(blueGe.length > 0){ 
			if(rehod.test(blueGe)){
				$("body").popjs({"title":"提示","content":"紫色预警增幅下限值，只能为数字！","callback":function(){
						$("#bluevaluea").focus();
								return;
					}});  
				return;
			}
			var bra=parseInt(blueGe);
			if(bra>100){
				$("body").popjs({"title":"提示","content":"紫色预警增幅下限值，不能超过100%！","callback":function(){
						$("#bluevaluea").focus();
								return;
					}});  
				return;
			}
			$("#greenvalue").val(blueGe);
		}
	}
};
</script>
</head>
<body class="ty-body">
	<div id="vertical">
		<div id="horizontal" style="width: 780px;">
			<div class="pane-content">
				<!-- 左开始 -->
				<div id="CasealarmInfo" class="demo-section k-header"> 
					<div style="width:70%;float:left;height:470px">
					<table style="width:100%;height:470px">
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
								<input type="text" style="width:30px" class="k-textbox" onblur="WarningAddManage.onFillinYellowB();" name="redvalue" id="redvalue" required="required" /><em class="ty-input-end"></em><span class="ml15">%</span>
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
								<input type="text"  style="width:30px" class="k-textbox" onblur="WarningAddManage.onFillinblueB();"   name="yellowvaluea" id="yellowvaluea" required="required" /><em class="ty-input-end"></em><span class="ml15">%</span>
							</td>
							<td>
								<label style="margin-left:20px">,增幅 </label>
							</td>
							<td colspan="2">
								<label style="float:left;margin-right:10px;"><</label>
								<input type="text"  style="width:30px" class="k-textbox" readonly="readonly"  name="yellowvalueb" id="yellowvalueb" required="required" /><em class="ty-input-end"></em><span class="ml15">%</span>
							</td>
						</tr>
						<tr>
							<td> 
								<table style="width:90%;padding:3px"><tr><td style="background-color:#8b2ae3;height:25px"></td></tr></table>  
							</td>
							<td>
								<label style="margin-left:20px">紫色预警</label>
							</td>
							<td>
								<label style="margin-left:20px">增幅  </label>
							</td>
							<td>
								<label>≥</label>
							</td>
							<td>
								<input type="text"  style="width:30px" class="k-textbox"  onblur="WarningAddManage.onFillingreen();"    name="bluevaluea" id="bluevaluea" required="required" /><em class="ty-input-end"></em><span class="ml15">%</span>
							</td>
							<td>
								<label style="margin-left:20px">,增幅 </label>
							</td>
							<td colspan="2">
								<label style="float:left;margin-right:10px;">< </label>
								<input type="text"  style="width:30px" class="k-textbox"   readonly="readonly"  name="bluevalueb" id="bluevalueb" required="required" /><em class="ty-input-end"></em><span class="ml15">%</span>
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
								<input type="text"  style="width:30px" class="k-textbox"  readonly="readonly"   name="greenvalue" id="greenvalue" required="required" /><em class="ty-input-end"></em><span class="ml15">%</span>
							</td> 
						<tr>
							<td colspan="7">
								<h4>警情级别</h4>
							</td>
						</tr>
						<tr>
							<td colspan="2" style="text-align:center"> 
								<input id="ckalarmLevel1" type="checkbox" name="alarmLevel" value="1" />一级警情
							</td>
							<td colspan="2" style="text-align:center">
								<input id="ckalarmLevel2" type="checkbox" name="alarmLevel" value="2" />二级警情
							</td>
							<td colspan="2" style="text-align:center">
								<input id="ckalarmLevel3" type="checkbox" name="alarmLevel" value="3" />三级警情
							</td>
						</tr> 
					</table>
					</div>
					<div style="width:28%;height:470px;float:left;overflow:hidden;">
						<h4>警情类别</h4>
						<div id="alarmtreeview" style="overflow-x:hidden;"> 
						</div>
					</div>
					<p class="ty-input-row"> 
						<button id="undo" class="ty-button" onclick="WarningAddManage.saveWarningAction()">保存</button>
						<button id="undo" class="ty-button" onclick="WarningAddManage.cancelAdd()">取消</button>
					</p> 
				</div>
			</div>
		</div>
	</div> 
</body> 
</html>
