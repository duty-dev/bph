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
	$("#vehicleType").kendoComboBox({
		dataTextField : "name",
		dataValueField : "id",
		dataSource : {
			serverFiltering : true,
			transport : {
				read : {
					url : "<%=basePath%>vehicleWeb/getVehicleType.do?sessionId="+sessionId,
					dataType : "json"
				}
			}
		},
		filter : "contains" 
	}).prev().find(".k-input").attr("readonly",true);
	$("#vehicleIntercomGroup").kendoComboBox({
		dataTextField : "name",
		dataValueField : "id",
		dataSource : {
			serverFiltering : true,
			transport : {
				read : {
					url : "<%=basePath%>vehicleWeb/getintercomGroup.do?sessionId="+sessionId,
					dataType : "json"
				}
			}
		},
		filter : "contains" 
	}).prev().find(".k-input").attr("readonly",true);
	$("#vehicleGpsName").kendoComboBox({
		dataTextField : "name",
		dataValueField : "id",
		height:150,
		dataSource : {
			serverFiltering : true,
			transport : {
				read : {
					url : "<%=basePath%>vehicleWeb/getGpsId.do?orgId="+$("#orgId").val()+"&sessionId="+sessionId,
					dataType : "json"
				}
			}
		},
		filter : "contains", 

        select: vehicleAddManage.onSelectGps
	}).prev().find(".k-input").attr("readonly",true);
});

var bph_vehicleAdd_pkg={}; 
var isExist = false;
var bph_Exist_OrgName = "";
var vehicleAddManage= {
		bph_iscomplete : false,
		
		onSelectGps:function(e){
			 var dataItem = this.dataItem(e.item.index()); 
			 bph_vehicleAdd_pkg.gpsId = dataItem.id;
			 bph_vehicleAdd_pkg.gpsName = dataItem.name; 
		},
		saveVehicleWithOut:function(){ 
			vehicleAddManage.packageVehicle(); 
			 if(!vehicleAddManage.bph_iscomplete){
				 return;
			 }
			$.ajax({
				url : "<%=basePath%>vehicleWeb/saveVehicle.do?sessionId="+sessionId,
				type : "post",
				data : bph_vehicleAdd_pkg,
				dataType : "json",
				success : function(req) { 
				
					$("body").popjs({"title":"提示","content":"新增车辆信息成功！","callback":function(){
							window.parent.window.parent.VehicleManage.onClose();
							window.parent.$("#dialog").tyWindow.close();
						}});    
				}
			});
			//window.parent.$("#dialog").tyWindow.close();
		},
		packageVehicle:function(){
			bph_vehicleAdd_pkg.id=0;
			bph_vehicleAdd_pkg.orgId=$("#orgId").val();
			var vType= $("#vehicleType").val();
			if(vType>0){
				bph_vehicleAdd_pkg.vehicleTypeId = vType;
			}else{ 
							$("body").popjs({"title":"提示","content":"请选择车辆类型!"}); 
				return;
			}
			var pNumber = $.trim($("#vehicleNumber").val());
			if (pNumber == ""||pNumber==undefined) {
							$("body").popjs({"title":"提示","content":"请输入车牌号码","callback":function(){
								$("#vehicleNumber").focus();
							}});  
			
				return;
			}
			if(pNumber.length>0)
			{
				var myReg = /^[^@\/\'\\\"#$%&\^\*]+$/;
				if(!myReg.test(pNumber)){
					$("body").popjs({"title":"提示","content":"车牌号码不能包含特殊字符"}); 
					return;
				}
			}
			if (pNumber.length > 10) { 
				$("body").popjs({"title":"提示","content":"车牌号码长度过长，限制长度为10！","callback":function(){
					$("#vehicleNumber").focus();
				}});  
				return;
			}
			//vehicleAddManage.isExistVehicle(pNumber,0,0);
			//if(!isExist){ 
			//				$("body").popjs({"title":"提示","content":"该车牌号在"+bph_Exist_OrgName+"机构下已存在，请确认后添加！","callback":function(){
			//					$("#vehicleNumber").focus();
			//				}});  
			//	return;
			//}
			bph_vehicleAdd_pkg.number = pNumber; 
 
			var brand = $.trim($("#vehicleBrand").val());
		
			if (brand.length > 20) { 
							$("body").popjs({"title":"提示","content":"车辆品牌长度出错，限制长度为20！","callback":function(){
								$("#vehicleBrand").focus();
							}});  
				return;
			}
				
				if(brand.length>0)
				{
					var myReg = /^[^@\/\'\\\"#$%&\^\*]+$/;
					if(!myReg.test(brand)){
						$("body").popjs({"title":"提示","content":"车辆品牌不能包含特殊字符！","callback":function(){
									$("#vehicleBrand").focus();
								}}); 
						return;
					}
				}
			bph_vehicleAdd_pkg.brand= brand;
			
			 var siteQty= $.trim($("#vehicleSiteQty").val());
			if (siteQty.length > 20) { 
							$("body").popjs({"title":"提示","content":"车辆座位数长度过长，限制长度为20！","callback":function(){
								$("#vehicleSiteQty").focus();
							}});  
				return;
			}
			if(siteQty.length>0)
			{
				var myReg = /^[^@\/\'\\\"#$%&\^\*]+$/;
				if(!myReg.test(siteQty)){
					$("body").popjs({"title":"提示","content":"车辆座位数不能包含特殊字符！","callback":function(){
								$("#vehicleSiteQty").focus();
							}}); 
					return;
				}
			}
			bph_vehicleAdd_pkg.siteQty = siteQty;
			var purpose = $.trim($("#vehiclePurpose").val());
			if (purpose.length > 20) {
							$("body").popjs({"title":"提示","content":"车辆用途长度过长，限制长度为20！","callback":function(){
								$("#vehiclePurpose").focus();
							}});  
			}
			if(purpose.length>0)
			{
				var myReg = /^[^@\/\'\\\"#$%&\^\*]+$/;
				if(!myReg.test(purpose)){
					$("body").popjs({"title":"提示","content":"车辆用途不能包含特殊字符！","callback":function(){
								$("#vehiclePurpose").focus();
							}}); 
					return;
				}
			}
			bph_vehicleAdd_pkg.purpose=purpose;
			
			
			var intercomGroup =$("#vehicleIntercomGroup").val();
			if (intercomGroup>0) {
				bph_vehicleAdd_pkg.intercomGroup = intercomGroup;
			}
			 
			var intercomPerson = $("#vehicleIntercomPerson").val(); 
			if(intercomPerson.length>30){ 
							$("body").popjs({"title":"提示","content":"对讲机个呼号长度过长，限制长度为0-30","callback":function(){
								$("#vehicleIntercomPerson").focus();
							}});    
				return;
			}
			bph_vehicleAdd_pkg.intercomPerson = intercomPerson;  
			vehicleAddManage.bph_iscomplete = true;
		},
		saveVehicleNotOut:function(){ 
			vehicleAddManage.packageVehicle(); 
			 if(!vehicleAddManage.bph_iscomplete){
				 return;
			 }
			$.ajax({
				url : "<%=basePath%>vehicleWeb/saveVehicle.do?sessionId="+sessionId,
				type : "post",
				data : bph_vehicleAdd_pkg,
				dataType : "json",
				success : function(req) {
							$("body").popjs({"title":"提示","content":"新增车辆信息成功"});   
					vehicleAddManage.clearAddFrom();
				}
			});
			//parent.VehicleManage.onClose();
		},

		// 判断警员是否存在
		isExistVehicle:function(param,vid,type) {
			isExist = false;
			$.ajax({
				url : "<%=basePath%>vehicleWeb/isExistVehicle.do?sessionId="+sessionId,
				type : "POST",
				dataType : "json",
				async : false,
				data : {
					"param" : param,
					"type"  : type,
					"id"    : vid
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
		isExistNumber:function(){
			var pNumber = $.trim($("#vehicleNumber").val()); 
			
			if(pNumber.length>0){
				$.ajax({
					url : "<%=basePath%>vehicleWeb/isExistVehicle.do?sessionId="+sessionId,
					type : "POST",
					dataType : "json",
					async : false,
					data : {
						"param" : pNumber,
						"type"   : 0,
						"id"     :   0
					},
					success : function(req) {
						if (req.code!=200) {  
							bph_Exist_OrgName = req.description; 
							$("body").popjs({"title":"提示","content":"该车牌号在"+bph_Exist_OrgName+"机构下已存在，请确认后添加","callback":function(){
									$("#vehicleNumber").focus(); 
									return;
								}});   
							return; 
							
						}
					}
				});
			}
		},
		clearAddFrom:function(){
			$("#vehicleNumber").val("");
			$("#vehicleBrand").val("");
			$("#vehicleSiteQty").val("");
			$("#vehiclePurpose").val(""); 
		}
};
</script>
</head>

<body class="ty-body">
	<div id="vertical" style="overflow-x:hidden;">
		<div id="horizontal" style="height: 220px; width: 590px;">
			<div class="pane-content">
				<!-- 左开始 -->
				<div class="demo-section k-header"> 
					<ul>
						<li class="ty-input"><span class="ty-input-warn">*</span><label class="ty-input-label" for="vehicleType">车辆类型:</label><input id="vehicleType"
							placeholder="请选择车辆类型..." /></li>
						<li class="ty-input"><span class="ty-input-warn">*</span><label class="ty-input-label" for="vehicleNumber">车牌号码:</label><input
							type="text" class="k-textbox" name="vehicleNumber"  onblur="vehicleAddManage.isExistNumber()"
							id="vehicleNumber" /></li>
						<li class="ty-input"><span class="ty-input-warn"></span><label class="ty-input-label" for="vehicleBrand">车辆品牌:</label><input type="text"
							class="k-textbox" name="vehicleBrand" id="vehicleBrand"
							required="required" /><input type="hidden"
							id="vehicleId"><input type="hidden"
							id="orgId" value="${organ.id}" /></li>
						<li class="ty-input"><span class="ty-input-warn"></span><label class="ty-input-label" for="vehicleSiteQty">　座位数:</label><input type="text"
							class="k-textbox" name="vehicleSiteQty" id="vehicleSiteQty"
							required="required" /></li>
						<li class="ty-input"><span class="ty-input-warn"></span><label class="ty-input-label" for="vehiclePurpose">车辆用途:</label><input type="text"
							class="k-textbox" name="vehiclePurpose" id="vehiclePurpose" /></li>
						<li class="ty-input"><span class="ty-input-warn"></span><label class="ty-input-label" for="vehicleIntercomGroup">　组呼号:</label><input id="vehicleIntercomGroup"
							placeholder="请选择对讲机组呼号..." /></li>
						<li class="ty-input"><span class="ty-input-warn"></span><label class="ty-input-label" for="vehicleIntercomPerson">　个呼号:</label><input type="text"
							class="k-textbox" name="vehicleIntercomPerson" id="vehicleIntercomPerson" /></li>
						<li class="ty-input"><span class="ty-input-warn"></span><label class="ty-input-label" for="vehicleGpsName">GPS设备: </label><input
							id="vehicleGpsName"  style="width:199px" placeholder="请选择gps名称..." /></li>
					</ul>
					<p class="ty-input-row">
						<!--<span class="k-button"  onclick="vehicleAddManage.saveVehicleNotOut()">保存并继续</span>-->
						<button class="ty-button"  onclick="vehicleAddManage.saveVehicleWithOut()">确定</button>
					</p>


				</div>
			</div>
		</div>
	</div>
</body>
</style>
</html>
