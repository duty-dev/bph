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
	$("#policetype").kendoComboBox({
		dataTextField : "name",
		dataValueField : "id",
		dataSource : {
			serverFiltering : true,
			transport : {
				read : {
					url : "<%=basePath%>policeWeb/getPoliceType.do?sessionId="+sessionId,
					dataType : "json"
				}
			}
		},
		filter : "contains"
	}).prev().find(".k-input").attr("readonly",true);
	$("#policegroupno").kendoComboBox({
		dataTextField : "name",
		dataValueField : "id",
		dataSource : {
			serverFiltering : true,
			transport : {
				read : {
					url : "<%=basePath%>policeWeb/getintercomGroup.do?sessionId="+sessionId,
					dataType : "json"
				}
			}
		},
		filter : "contains"
	}).prev().find(".k-input").attr("readonly",true);
	$("#policegpsname").kendoComboBox({
		dataTextField : "name",
		dataValueField : "id",
		height:150,
		dataSource : {
			serverFiltering : true,
			transport : {
				read : {
					url : "<%=basePath%>policeWeb/getGpsId.do?orgId="+$("#orgId").val()+"&sessionId="+sessionId,
					dataType : "json"
				}
			}
		},
		filter : "contains",
        select: policeAddManage.onSelectGps 
	}).prev().find(".k-input").attr("readonly",true);
});

var bph_policeAdd_pkg={};
var isExist = false;
var bph_Exist_OrgName ="";
var policeAddManage= {
		onSelectGps:function(e){
			 var dataItem = this.dataItem(e.item.index()); 
			 bph_policeAdd_pkg.gpsId = dataItem.id;
			 bph_policeAdd_pkg.gpsName = dataItem.name; 
		},
		savePoliceWithOut:function(){ 
			policeAddManage.packagePolice(); 
			if(!bph_policeAdd_pkg.isused||bph_policeAdd_pkg.isused==undefined)
			{
				return;
			}
			$.ajax({
				url : "<%=basePath%>policeWeb/savePolice.do?sessionId="+sessionId,
				type : "post",
				data : bph_policeAdd_pkg,
				dataType : "json",
				success : function(req) { 
					$("body").popjs({"title":"提示","content":"新增警员信息成功！","callback":function(){
					
							window.parent.window.parent.PoliceManage.onClose();
							window.parent.$("#dialog").tyWindow.close();
						}});   
				}
			}); 
		},
		packagePolice:function(){
			bph_policeAdd_pkg.id=0;
			bph_policeAdd_pkg.orgId=$("#orgId").val();
			var pType= $("#policetype").val();
			if(pType>0){
				bph_policeAdd_pkg.typeId = pType;
			}else{ 
					$("body").popjs({"title":"提示","content":"请选择警员类型"}); 
				return;
			}
			var pName = $.trim($("#policename").val());
			if (pName == ""||pName==undefined) {
					$("body").popjs({"title":"提示","content":"请输入警员名称","callback":function(){
								$("#policename").focus();
							}});    
					
				return;
			}
			var myReg = /^[^@\/\'\\\"#$%&\^\*]+$/;
			if(!myReg.test(pName)){
					$("body").popjs({"title":"提示","content":"警员姓名不能包含特殊字符","callback":function(){
							$("#policename").focus();
						}});  
					return;
			}
			if (pName.length > 20) { 
					$("body").popjs({"title":"提示","content":"警员姓名长度过长，限制长度为20！","callback":function(){
								$("#policename").focus();
							}});    
				return;
			}
			bph_policeAdd_pkg.name = pName; 

			var pattern = /\D/ig;
			var idcardno = $.trim($("#policeidcardno").val());
			if (idcardno.length > 0) {
				if (idcardno.length != 18 && idcardno.length != 15) { 
					$("body").popjs({"title":"提示","content":"警员身份证号码长度出错，限制长度为15位或者18位！","callback":function(){
								$("#policeidcardno").focus(); 
							}});    
					
					return;
				}

				var Regx = /^[A-Za-z0-9]+$/;
				if (!Regx.test(idcardno)) {
					$("body").popjs({"title":"提示","content":"警员身份证号码格式出错，只能是全部数字或者最后一位是字母！","callback":function(){
								$("#policeidcardno").focus(); 
							}});    
					return;
				}
				var subIdCard = idcardno.substring(0, idcardno.length - 1);
				if (pattern.test(subIdCard)) { 
					$("body").popjs({"title":"提示","content":"警员身份证号码格式出错！","callback":function(){
								$("#policeidcardno").focus(); 
							}});    
					return;
				}
				var endIdCard = idcardno.substring(idcardno.length, idcardno.length-1);
				//为数字
				if (isNaN(endIdCard)){
					if(endIdCard!="X" && endIdCard!="x"){
						$("body").popjs({"title":"提示","content":"警员身份证号码格式出错 ,最后一位只能是数字或者字母X！","callback":function(){
								$("#policeidcardno").focus(); 
							}});    
						return;
					}
				} 
				
			}else{
				$("body").popjs({"title":"提示","content":"警员身份证号不能为空","callback":function(){
					$("#policeidcardno").focus();
				}});  
				return;
			}
			bph_policeAdd_pkg.idcardno =idcardno;
			var pnumber = $.trim($("#policecode").val());
			
			if(pnumber.length>0){ 
				var myReg = /^[^@\/\'\\\"#$%&\^\*]+$/;
				if(!myReg.test(pnumber)){
						$("body").popjs({"title":"提示","content":"警员警号不能包含特殊字符","callback":function(){
								$("#policecode").focus();
							}});  
						return;
				}
				if (pnumber.length > 20 || pnumber.length < 6) {
					$("body").popjs({"title":"提示","content":"警员警号长度出错，限制长度为6--20！","callback":function(){
								$("#policecode").focus();  
							}});    
					
					return;
				}
				
			}else{
				$("body").popjs({"title":"提示","content":"警员警号不能为空","callback":function(){
					$("#policecode").focus();
				}});  
				return;
			}
			bph_policeAdd_pkg.number= pnumber;
			 var ptitle= $.trim($("#policetitle").val());
			if (ptitle.length > 0 && ptitle.length > 20) {
					$("body").popjs({"title":"提示","content":"警员职位长度过长，限制长度为20！","callback":function(){
								$("#policetitle").focus();   
							}});      
					
				return;
			}
			if (ptitle.length > 0){
				if(!myReg.test(ptitle)){
						$("body").popjs({"title":"提示","content":"警员职位不能包含特殊字符","callback":function(){
								$("#policetitle").focus();
							}});  
						return;
				}
			}
			bph_policeAdd_pkg.title = ptitle;
			var phone = $.trim($("#policephone").val());
			if (phone.length > 0) {
				if(phone.length != 11){ 
					$("body").popjs({"title":"提示","content":"警员电话号码格式错误，只能是11位的数字！","callback":function(){
								$("#policephone").focus();    
							}});    
					return;  
				}
				if(pattern.test(phone)){
					$("body").popjs({"title":"提示","content":"警员电话号码格式错误，只能为数字！","callback":function(){
								$("#policephone").focus();    
							}});    
					return;  
				}
			}
			bph_policeAdd_pkg.mobile=phone;
			var mobileShorts = $.trim($("#policeshortno").val());
			if (mobileShorts.length > 0 && mobileShorts.length > 20) {
				$("body").popjs({"title":"提示","content":"警员公安短号长度过长，限制长度为1--20！","callback":function(){
								$("#policeshortno").focus();   
							}});         
					
				return;
			}
			if (mobileShorts.length > 0){
				if(!myReg.test(mobileShorts)){
						$("body").popjs({"title":"提示","content":"警员公安短号不能包含特殊字符","callback":function(){
								$("#policeshortno").focus();
							}});  
						return;
				}
			}
			bph_policeAdd_pkg.mobileShort = mobileShorts;
			bph_policeAdd_pkg.intercomGroup = $("#policegroupno").val();
			var intercomPerson = $("#policepersonno").val();
			if(intercomPerson.length>0){

				if(!myReg.test(intercomPerson)){
						$("body").popjs({"title":"提示","content":"警员个呼号不能包含特殊字符","callback":function(){
								$("#policepersonno").focus();
							}});  
						return;
				}
				
			}
			if(intercomPerson.length>30){ 
				$("body").popjs({"title":"提示","content":"警员对讲机个呼号长度过长，限制长度为0-30","callback":function(){
								$("#policepersonno").focus();  
							}});   
					
				return;
			}
			bph_policeAdd_pkg.intercomPerson = intercomPerson;  
			bph_policeAdd_pkg.isused = true;
		},
		savePoliceNotOut:function(){ 
			policeAddManage.packagePolice(); 
			if(!bph_policeAdd_pkg.isused||bph_policeAdd_pkg.isused==undefined)
			{
				return;
			}
			$.ajax({
				url : "<%=basePath%>policeWeb/savePolice.do?sessionId="+sessionId,
				type : "post",
				data : bph_policeAdd_pkg,
				dataType : "json",
				success : function(req) { 
					$("body").popjs({"title":"提示","content":"新增警员信息成功"}); 
					policeAddManage.clearAddForm();
				}
			});
			//parent.PoliceManage.onClose();
		},

		// 判断警员是否存在
		isExistPolice:function(param, pType,pid,optType) {
			isExist = false;
			$.ajax({
				url : "<%=basePath%>policeWeb/isExistPolice.do?sessionId="+sessionId,
				type : "POST",
				dataType : "json",
				async : false,
				data : {
					"param" : param,
					"paramType" : pType,
					"optType"   : optType,
					"id"     :   pid
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
		isExistIdCard:function(){
			var idcardno = $.trim($("#policeidcardno").val());
			if(idcardno.length>0){
			$.ajax({
				url : "<%=basePath%>policeWeb/isExistPolice.do?sessionId="+sessionId,
				type : "POST",
				dataType : "json",
				async : false,
				data : {
					"param" : idcardno,
					"paramType" : "idCard",
					"optType"   : 0,
					"id"     :   0
				},
				success : function(req) {
					if (req.code!=200) {  
						bph_Exist_OrgName = req.description;
						$("body").popjs({"title":"提示","content":"该身份证号在"+bph_Exist_OrgName+"机构下已存在，请确认后添加","callback":function(){
								$("#policeidcardno").focus(); 
								return;
							}});    
								return;
					}
				}
			});
			}
		},
		isExistNumber:function(){
			var pNumber = $.trim($("#policecode").val()); 
			
			if(pNumber.length>0){
			$.ajax({
				url : "<%=basePath%>policeWeb/isExistPolice.do?sessionId="+sessionId,
				type : "POST",
				dataType : "json",
				async : false,
				data : {
					"param" : pnumber,
					"paramType" : "number",
					"optType"   : 0,
					"id"     :   0
				},
				success : function(req) {
					if (req.code!=200) {  
						bph_Exist_OrgName = req.description;
						$("body").popjs({"title":"提示","content":"该警号号在"+bph_Exist_OrgName+"机构下已存在，请确认后添加","callback":function(){
								$("#policecode").focus(); 
								return;
							}});    
						return;
						
					}
				}
			});
			}
		},
		clearAddForm:function(){
			$("#policename").val("");
			$("#policeidcardno").val("");
			$("#policecode").val("");
			$("#policephone").val("");
			$("#policeshortno").val("");  
		}
};
</script>
</head>
<body class="ty-body">
	<div id="vertical" style="overflow-x:hidden;">
		<div id="horizontal" style="height: 300px; width: 610px;">
			<div class="pane-content">
				<!-- 左开始 -->
				<div class="demo-section k-header"> 
					<ul>
						<li class="ty-input">
							<span class="ty-input-warn"></span><label class="ty-input-label" for="policetype" style="width:88px;text-align:right;"><span class="ty-txt-red">*</span>警员类型:</label><input id="policetype" placeholder="请选择警员类别..."  /><input type="hidden" id="policeId"/><input type="hidden" id="orgId" value="${organ.id}" />
						</li>
						<li class="ty-input">
							<span class="ty-input-warn">*</span><label class="ty-input-label" for="policename">警员姓名:</label><input type="text" class="k-textbox" name="policename" id="policename" required="required" />
						</li>
						<li class="ty-input">
							<span class="ty-input-warn"></span><label class="ty-input-label" for="policeidcardno" style="width:88px;text-align:right;"><span class="ty-txt-red">*</span>身份证号:</label><input type="text" class="k-textbox" name="policeidcardno" id="policeidcardno" onblur="policeAddManage.isExistIdCard();" />
						</li>
						<li class="ty-input">
							<span class="ty-input-warn">*</span><label class="ty-input-label" for="policecode">警员警号:</label><input type="text" class="k-textbox" name="policecode" id="policecode" onblur="policeAddManage.isExistNumber();" />
						</li>
						<li class="ty-input">
							<span class="ty-input-warn"></span><label class="ty-input-label" for="policetitle" style="width:88px;text-align:right;">警员职务:</label><input type="text" class="k-textbox" name="policetitle" id="policetitle" />
						</li>
						<li class="ty-input">
							<span class="ty-input-warn"></span><label class="ty-input-label" for="policephone">手机号码:</label><input type="text" class="k-textbox" name="policephone" id="policephone" />
						</li>
						<li class="ty-input">
							<span class="ty-input-warn"></span><label class="ty-input-label" for="policeshortno" style="width:88px;text-align:right;">公安短号:</label><input type="text" class="k-textbox" name="policeshortno" id="policeshortno" />
						</li>
						<li class="ty-input">
							<span class="ty-input-warn"></span><label class="ty-input-label" for="policegroupno">对讲机组呼号:</label><input id="policegroupno" placeholder="请选择对讲机组呼号..." />
						</li>
						<li class="ty-input">
							<span class="ty-input-warn"></span><label class="ty-input-label" for="policepersonno">对讲机个呼号:</label><input type="text" class="k-textbox" name="policepersonno" id="policepersonno" />
						</li>
						<li class="ty-input">
							<span class="ty-input-warn"></span><label class="ty-input-label" for="policegpsname">GPS显示名称: </label><input id="policegpsname"  placeholder="请选择gps名称..." />
						</li>
					</ul>
					<p class="ty-input-row">
						<!--<span id="undo" class="k-button" onclick="policeAddManage.savePoliceNotOut()">保存并继续</span>-->
						<button id="undo" class="ty-button" onclick="policeAddManage.savePoliceWithOut()">保存</button>
					</p>


				</div>
			</div>
		</div>
	</div>
</body> 
</html>
