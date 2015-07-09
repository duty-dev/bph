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
		filter : "contains",
		suggest : true 
	}).prev().find(".k-input").attr("readonly",true);
	$("#vehiclePurpose").kendoComboBox({
		dataTextField : "name",
		dataValueField : "id",
		dataSource : {
			serverFiltering : true,
			transport : {
				read : {
					url : "<%=basePath%>vehicleWeb/getVehiclePurpose.do?sessionId="+sessionId,
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
		filter : "contains",
		suggest : true
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
		suggest : true ,
        select: vehicleEditManage.onSelectGps
	}).prev().find(".k-input").attr("readonly",true);
	
	bph_vehicleEdit_pkg.gpsId = $("#vehicleGpsName").val();
	bph_vehicleEdit_pkg.gpsName = $("#txtvehiclegpsname").val();
});

var bph_vehicleEdit_pkg={}; 
var isExist = false;
var bph_Exist_OrgName ="";
var vehicleEditManage= {
			bph_iscomplete : false,
			
			onSelectGps:function(e){
				 var dataItem = this.dataItem(e.item.index()); 
				 bph_vehicleEdit_pkg.gpsId = dataItem.id;
				 bph_vehicleEdit_pkg.gpsName = dataItem.name; 
			},
			saveVehicleWithOut:function(){ 
				vehicleEditManage.packageVehicle(); 
				 if(!vehicleEditManage.bph_iscomplete){
					 return;
				 }
				$.ajax({
					url : "<%=basePath%>vehicleWeb/saveVehicle.do?groupId=1",
					type : "post",
					data : bph_vehicleEdit_pkg,
					dataType : "json",
					success : function(req) { 
						$("body").popjs({"title":"提示","content":"修改车辆信息成功！","callback":function(){
							window.parent.window.parent.VehicleManage.onClose();
							window.parent.$("#dialog").tyWindow.close();
						}});  
					}
				});
				//parent.VehicleManage.onClose();
			},
			packageVehicle:function(){
				var vId = $("#vehicleId").val();
				bph_vehicleEdit_pkg.id=vId;
				bph_vehicleEdit_pkg.orgId=$("#orgId").val();
				var vType= $("#vehicleType").val();
				if(vType>0){
					bph_vehicleEdit_pkg.vehicleTypeId = vType;
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
						$("body").popjs({"title":"提示","content":"车牌号码不能包含特殊字符","callback":function(){
								$("#vehicleNumber").focus();
							}});  
						return;
					}
				}
				if (pNumber.length > 10) {
							$("body").popjs({"title":"提示","content":"车牌号码长度过长，限制长度为10！","callback":function(){
								$("#vehicleNumber").focus();
							}});  
					return;
				}
				bph_vehicleEdit_pkg.number = pNumber; 
	 
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
				bph_vehicleEdit_pkg.brand= brand;
				
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
				bph_vehicleEdit_pkg.siteQty = siteQty;

				var purpose = $("#vehiclePurpose").val();
				if (purpose>0) {
					bph_vehicleEdit_pkg.purposeId=purpose;
				}
				
				
				var intercomGroup =$("#vehicleIntercomGroup").val();
				if (intercomGroup>0) {
					bph_vehicleEdit_pkg.intercomGroup = intercomGroup;
				}
				 
				var intercomPerson = $("#vehicleIntercomPerson").val(); 
				if(intercomPerson.length>30){ 
							$("body").popjs({"title":"提示","content":"对讲机个呼号长度过长，限制长度为0-30","callback":function(){
								$("#vehicleIntercomPerson").focus();
							}});  
					return;
				}
				bph_vehicleEdit_pkg.intercomPerson = intercomPerson;  
				vehicleEditManage.bph_iscomplete = true;
			} ,

		isExistNumber:function(){
			var pNumber = $.trim($("#vehicleNumber").val()); 
			if(pNumber.length>0){
				var vid = $("#vehicleId").val();
				$.ajax({
					url : "<%=basePath%>vehicleWeb/isExistVehicle.do?sessionId="+sessionId,
					type : "POST",
					dataType : "json",
					async : false,
					data : {
						"param" : pNumber,
						"type"   : 1,
						"id"     :   vid
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
							$("body").popjs({"title":"提示","content":"该车牌号在"+bph_Exist_OrgName+"机构下已存在，请确认后添加","callback":function(){
								$("#vehicleNumber").focus(); 
								return;
							}});   
							return; 
						}
					}
				});
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
						<li class="ty-input"><span class="ty-input-warn">*</span><label class="ty-input-label" for="vehicleType">车辆类型:</label><input type="text"
							name="vehicleType"   id="vehicleType" value="${vehicle.vehicleTypeId}"
							required="required" /></li>
						<li class="ty-input"><span class="ty-input-warn">*</span><label class="ty-input-label" for="vehicleNumber">车牌号码:</label><input type="text"
							class="k-textbox" name="vehicleNumber" id="vehicleNumber"  value="${vehicle.number}"   onblur="vehicleEditManage.isExistNumber()" /></li>
						<li class="ty-input"><span class="ty-input-warn"></span><label class="ty-input-label" for="vehicleBrand">车辆品牌:</label><input 	type="text" class="k-textbox" id="vehicleBrand" value="${vehicle.brand }"
							 /><input type="hidden"
							id="vehicleId"  value="${vehicle.id}" ><input type="hidden"
							id="orgId" value="${organ.id}" /></li>
						<li class="ty-input"><span class="ty-input-warn"></span><label class="ty-input-label" for="vehicleSiteQty">&nbsp;&nbsp;&nbsp;座位数:</label><input
							type="text" class="k-textbox" value="${vehicle.siteQty}" name="vehicleSiteQty"
							id="vehicleSiteQty" /></li>  
						<li class="ty-input"><span class="ty-input-warn"></span><label class="ty-input-label" for="vehiclePurpose">车辆用途:</label><input id="vehiclePurpose"
							placeholder="请选择车辆用途..."  value="${vehicle.purposeId }" /></li>
						<li class="ty-input"><span class="ty-input-warn"></span><label class="ty-input-label" for="vehicleIntercomGroup">&nbsp;&nbsp;&nbsp;组呼号:</label><input type="text"
							name="vehicleIntercomGroup" id="vehicleIntercomGroup" value="${vehicle.intercomGroup}" /></li>
						<li class="ty-input"><span class="ty-input-warn"></span><label class="ty-input-label" for="vehicleIntercomPerson">&nbsp;&nbsp;&nbsp;个呼号:</label><input
							type="text" class="k-textbox" name="vehicleIntercomPerson"
							id="vehicleIntercomPerson"  value="${vehicle.intercomPerson}"/></li>
						<li class="ty-input"><span class="ty-input-warn"></span><label class="ty-input-label" for="vehicleGpsName">GPS设备: </label><input
							id="vehicleGpsName" placeholder="请选择gps名称..." value="${vehicle.gpsId}" />
							<input type="hidden"
							id="txtvehiclegpsname" value="${vehicle.gpsName}" /></li>
					</ul>
					<p class="ty-input-row">
						<button class="ty-button"  onclick="vehicleEditManage.saveVehicleWithOut()">确定</button>
					</p>


				</div>
			</div>
		</div>
	</div>
</body>
</style>
</html>
