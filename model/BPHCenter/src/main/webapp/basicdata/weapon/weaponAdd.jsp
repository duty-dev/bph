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
<script type="text/javascript">
var sessionId = $("#token").val();
$(function() {
	$("#weaponType").kendoComboBox({
		dataTextField : "name",
		dataValueField : "id",
		dataSource : {
			serverFiltering : true,
			transport : {
				read : {
					url : "<%=basePath%>weaponWeb/getWeaponType.do?sessionId="+sessionId,
					dataType : "json"
				}
			}
		},
		filter : "contains",
		suggest : true,
		index : 0 
	}).prev().find(".k-input").attr("readonly",true);
});
 
var bph_weaponAdd_pkg={};
var isExist = false;
var bph_Exist_OrgName ="";
var WeaponAddManage= { 
		isComplete:false,
		saveWeaponWithOut:function(){ 
			WeaponAddManage.packageWeapon(); 
			if(!WeaponAddManage.isComplete)
			{
				return;
			}
			$.ajax({
				url : "<%=basePath%>weaponWeb/saveWeapon.do?sessionId="+sessionId,
				type : "post",
				data : bph_weaponAdd_pkg,
				dataType : "json",
				success : function(req) {
							 
						$("body").popjs({"title":"提示","content":"新增武器信息成功！","callback":function(){
							window.parent.window.parent.WeaponManage.onClose();
							window.parent.$("#dialog").tyWindow.close();
						}});  
				}
			}); 
		},
		packageWeapon:function(){
			bph_weaponAdd_pkg.id=0;
			bph_weaponAdd_pkg.orgId=$("#orgId").val();
			var pType= $("#weaponType").val();
			if(pType>0){
				bph_weaponAdd_pkg.typeId = pType;
			}else{ 
							$("body").popjs({"title":"提示","content":"请选择武器类型!"});  
				return;
			}
			 
			var pnumber = $.trim($("#weaponNumber").val()); 
			var myReg = /^[^@\/\'\\\"#$%&\^\*]+$/;
			if(pnumber.length>0){
			
				if(!myReg.test(pnumber)){
						$("body").popjs({"title":"提示","content":"武器编号不能包含特殊字符","callback":function(){
								$("#weaponNumber").focus();
							}});  
						return;
				}
				if (pnumber.length > 20) { 
							$("body").popjs({"title":"提示","content":"武器编号长度出错，限制长度为0--20！","callback":function(){
								$("#weaponNumber").focus();
							}});    
					return;
				}
				//WeaponAddManage.isExistWeapon(pnumber,0, 0);
				//if (!isExist) { 
				//			$("body").popjs({"title":"提示","content":"该武器编号在"+bph_Exist_OrgName+"机构下已经存在，请确认之后添加","callback":function(){
				//				$("#weaponNumber").focus();
				//			}});    
				//	return; 
				//}
			}else{ 
							$("body").popjs({"title":"提示","content":"请录入武器编号","callback":function(){
								$("#weaponNumber").focus();
							}});    
				return;
			}
			bph_weaponAdd_pkg.number= pnumber;
			var wstandard= $.trim($("#weaponStandard").val());
			var pattern = /\D/ig;
			if (pattern.test(wstandard)) { 
					$("body").popjs({"title":"提示","content":"武器子弹数目字能为数字!","callback":function(){
								$("#weaponStandard").focus();
							}});    
					return;
			}
			if (wstandard.length > 20) { 
							$("body").popjs({"title":"提示","content":"子弹数目长度过长，限制长度为20！","callback":function(){
								$("#weaponStandard").focus();
							}});    
				return;
			}
			if(wstandard.length >0){
				if(!myReg.test(wstandard)){
						$("body").popjs({"title":"提示","content":"子弹数目不能包含特殊字符","callback":function(){
								$("#weaponStandard").focus();
							}});  
						return;
				}
			}
			bph_weaponAdd_pkg.standard = wstandard; 
			WeaponAddManage.isComplete = true;
		},
		saveWeaponNotOut:function(){ 
			WeaponAddManage.packageWeapon(); 
			if(!WeaponAddManage.isComplete)
			{
				return;
			}
			$.ajax({
				url : "<%=basePath%>weaponWeb/saveWeapon.do?sessionId="+sessionId,
				type : "post",
				data : bph_weaponAdd_pkg,
				dataType : "json",
				success : function(req) { 
							$("body").popjs({"title":"提示","content":"新增武器信息成功"});
					WeaponAddManage.clearAddFrom();
				}
			});
			//parent.WeaponManage.onClose();
		},

		// 判断警员是否存在
		isExistWeapon:function(param,wid,type) {
			isExist = false;
			$.ajax({
				url : "<%=basePath%>weaponWeb/isExistWeapon.do?sessionId="+sessionId,
				type : "POST",
				dataType : "json",
				async : false,
				data : {
					"param" : param,
					"type"  : type,
					"id"    : wid
				},
				success : function(req) {
					if (req.code==200) {
						isExist = true;
					}else{
						bph_Exist_OrgName = req.description;
					}
				}
			});
		},
			// 判断警员是否存在
			isExistNumber:function() { 
			var pnumber = $.trim($("#weaponNumber").val()); 
			
			if(pnumber.length>0){
				$.ajax({
					url : "<%=basePath%>weaponWeb/isExistWeapon.do?sessionId="+sessionId,
					type : "POST",
					dataType : "json",
					async : false,
					data : {
						"param" : pnumber,
						"type"  : 0,
						"id"    : 0
					},
					success : function(req) {
					if (req.code!=200) {  
						bph_Exist_OrgName = req.description; 
						$("body").popjs({"title":"提示","content":"该武器编号在"+bph_Exist_OrgName+"机构下已存在，请确认后添加","callback":function(){
								$("#weaponNumber").focus(); 
								return;
							}});   
						return; 
						
					}
					}
				});
				}
			},
		clearAddFrom:function(){
			$("#weaponNumber").val();
			$("#weaponStandard").val();
		}
};
</script>
</head>

<body class="ty-body">
	<div id="vertical">
		<div id="horizontal">
			<div class="pane-content">
				<!-- 左开始 -->
				<div class="demo-section k-header"> 
					<ul>
						<li style="padding:5px;"><span class="ty-input-warn">*</span><label for="weaponType" class="fl mr5">武器类型:</label><input id="weaponType"
							placeholder="请选择车辆类型..." /><input type="hidden"
							id="weaponId"><input type="hidden"
							id="orgId" value="${organ.id}" /></li>
						<li style="padding:5px;"><span class="ty-input-warn">*</span><label for="weaponNumber" class="fl mr5">武器编号:</label><input
							type="text" class="k-textbox" name="weaponNumber"  onblur="WeaponAddManage.isExistNumber()" 
							id="weaponNumber" /></li>
						<li style="padding:5px;"><span class="ty-input-warn"></span><label for="weaponStandard" class="fl mr5">子弹数目:</label><input type="text"
							class="k-textbox" name="weaponStandard" id="weaponStandard" /></li>
					</ul>
					<p class="ty-input-row">
						<!--<span class="k-button"  onclick="WeaponAddManage.saveWeaponNotOut()">保存并继续</span>-->
						<button class="ty-button"  onclick="WeaponAddManage.saveWeaponWithOut()">确定</button>
					</p>


				</div>
			</div>
		</div>
	</div>
</body>
</style>
</html>
