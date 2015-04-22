﻿<%@ page language="java" pageEncoding="UTF-8"%>
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
		filter : "contains",
		suggest : true 
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
		filter : "contains",
		suggest : true 
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
		suggest : true ,

        select: policeAddManage.onSelectGps 
	}).prev().find(".k-input").attr("readonly",true);
});

var bph_policeAdd_pkg={};
var isExist = false;
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
					$("body").popjs({"title":"提示","content":"警员身份证号码格式出错  ！","callback":function(){
								$("#policeidcardno").focus(); 
							}});    
					return;
				}
				policeAddManage.isExistPolice(idcardno, "idCard",0,0);
				if (!isExist) {
					isExist = false;  
					$("body").popjs({"title":"提示","content":"警员身份证号码重复，请检查","callback":function(){
								$("#policeidcardno").focus(); 
							}});    
					return;
				}
			}
			bph_policeAdd_pkg.idcardno =idcardno;
			var pnumber = $.trim($("#policecode").val());
			
			if(pnumber.length>0){
				if (pnumber.length > 20 || pnumber.length < 6) {
					$("body").popjs({"title":"提示","content":"警员警号长度出错，限制长度为6--20！","callback":function(){
								$("#policecode").focus();  
							}});    
					
					return;
				}
				policeAddManage.isExistPolice(pnumber, "number",0,0);
				if (!isExist) { 
					$("body").popjs({"title":"提示","content":"警员警号重复，请检查","callback":function(){
								$("#policecode").focus();  
							}});    
					return;
				}
			}
			bph_policeAdd_pkg.number= pnumber;
			 var ptitle= $.trim($("#policetitle").val());
			if (ptitle.length > 0 && ptitle.length > 20) {
					$("body").popjs({"title":"提示","content":"警员职位长度过长，限制长度为20！","callback":function(){
								$("#policetitle").focus();   
							}});      
					
				return;
			}
			bph_policeAdd_pkg.title = ptitle;
			var phone = $.trim($("#policephone").val());
			if (phone.length > 0 && phone.length > 20) {
				$("body").popjs({"title":"提示","content":"警员电话号码长度过长，限制长度为20！","callback":function(){
								$("#policephone").focus();    
							}});         
					
				return;  
			}
			bph_policeAdd_pkg.mobile=phone;
			var mobileShorts = $.trim($("#policeshortno").val());
			if (mobileShorts.length > 0 && mobileShorts.length > 20) {
			$("body").popjs({"title":"提示","content":"警员公安短号长度过长，限制长度为1--20！","callback":function(){
								$("#policeshortno").focus();   
							}});         
					
				return;
			}
			bph_policeAdd_pkg.mobileShort = mobileShorts;
			bph_policeAdd_pkg.intercomGroup = $("#policegroupno").val();
			var intercomPerson = $("#policepersonno").val();
			if(intercomPerson.length>0){
				policeAddManage.isExistPolice(intercomPerson, "intercomPerson",0,0);
				if (!isExist) { 
					$("body").popjs({"title":"提示","content":"个呼号为  " + intercomPerson + " 的警员已存在，请检查！","callback":function(){
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
					if (req.code=200) {
						isExist = true;
					}
				}
			});
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
<body>
	<div id="vertical" style="overflow-x:hidden;">
		<div id="horizontal" style="height: 300px; width: 590px;">
			<div class="pane-content">
				<!-- 左开始 -->
				<div class="demo-section k-header"> 
					<ul>
						<li class="ty-input">
							<label class="ty-input-label" for="policetype">警员类型:</label><input id="policetype" placeholder="请选择警员类别..."  /><input type="hidden" id="policeId"/><input type="hidden" id="orgId" value="${organ.id}" />
						</li>
						<li class="ty-input">
							<label class="ty-input-label" for="policename">警员姓名:</label><input type="text" class="k-textbox" name="policename" id="policename" required="required" />
						</li>
						<li class="ty-input">
							<label class="ty-input-label" for="policeidcardno">身份证号:</label><input type="text" class="k-textbox" name="policeidcardno" id="policeidcardno" />
						</li>
						<li class="ty-input">
							<label class="ty-input-label" for="policecode">警员警号:</label><input type="text" class="k-textbox" name="policecode" id="policecode" />
						</li>
						<li class="ty-input">
							<label class="ty-input-label" for="policetitle">警员职务:</label><input type="text" class="k-textbox" name="policetitle" id="policetitle" />
						</li>
						<li class="ty-input">
							<label class="ty-input-label" for="policephone">手机号码:</label><input type="text" class="k-textbox" name="policephone" id="policephone" />
						</li>
						<li class="ty-input">
							<label class="ty-input-label" for="policeshortno">公安短号:</label><input type="text" class="k-textbox" name="policeshortno" id="policeshortno" />
						</li>
						<li class="ty-input">
							<label class="ty-input-label" for="policegroupno">对讲机组呼号:</label><input id="policegroupno" placeholder="请选择对讲机组呼号..." />
						</li>
						<li class="ty-input">
							<label class="ty-input-label" for="policepersonno">对讲机个呼号:</label><input type="text" class="k-textbox" name="policepersonno" id="policepersonno" />
						</li>
						<li class="ty-input">
							<label class="ty-input-label" for="policegpsname">GPS显示名称: </label><input id="policegpsname" placeholder="请选择gps名称..." />
						</li>
					</ul>
					<p style="float:left;width:100%;margin-top:10px;">
						<!--<span id="undo" class="k-button" onclick="policeAddManage.savePoliceNotOut()">保存并继续</span>-->
						<span id="undo" class="k-button" onclick="policeAddManage.savePoliceWithOut()">保存</span>
					</p>


				</div>
			</div>
		</div>
	</div>
</body> 
</html>
