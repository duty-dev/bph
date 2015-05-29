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
		$("#gpsType").kendoComboBox({
			dataTextField : "name",
			dataValueField : "id",
			dataSource : {
				serverFiltering : true,
				transport : {
					read : {
						url : "<%=basePath%>gpsWeb/getGpsType.do?sessionId="+sessionId,
						dataType : "json"
					}
				}
			},
			filter : "contains",
			suggest : true,
			index : 0
		}).prev().find(".k-input").attr("readonly",true);
//		$("#gpsIcons").kendoComboBox({ 
//			dataTextField : "name",
//			dataValueField : "id",
//			dataSource : {
//				serverFiltering : true,
///				transport : {
//					read : {
//						url: "<=basePath%>gpsWeb/getIconsList.do?sessionId="+sessionId,
///						dataType : "json"
//					}
//				}
//			},
//			filter : "contains",
//			suggest : true,
//	        select: GpsAddManage.onSelectIcon ,
//			index : 0 
//		});

		$.ajax({
				url : "<%=basePath%>gpsWeb/getIconsList.do?sessionId="+sessionId,
				type : "post", 
				dataType : "json",
				async : false,
				success : function(req) {
					gpsIconData = req;
				}
		});
		var rootPath = '<%=basePath%>';
//		$("#gpsIcons").kendoComboBox({ 
//			dataTextField : "name",
//			dataValueField : "id",
//			dataSource : gpsIconData,
//			template: '<span><img width="25px" height="25px" src="'+rootPath+'#: iconUrl #" />#: name #</span>',
//
//	        select: GpsAddManage.onSelectIcon  
//		}).prev().find(".k-input").attr("readonly",true);
	});
	
	var gpsIconData="";
	var bph_gpsAdd_pkg={};
	var isExist = false; 
	var bph_Exist_OrgName = "";
	var GpsAddManage= { 
			onSelectIcon:function(e){
				var dataItem = this.dataItem(e.item.index()); 
				bph_gpsAdd_pkg.iconId = dataItem.id;
				bph_gpsAdd_pkg.iconUrl = dataItem.iconUrl; 
				//$("#icon_thumb").attr("src","<%=basePath %>"+dataItem.iconUrl);
			},
			isComplete:false,
			saveGpsWithOut:function(){ 
				GpsAddManage.packageGps(); 
				if(!GpsAddManage.isComplete)
				{
					return;
				}
				$.ajax({
					url : "<%=basePath%>gpsWeb/saveGps.do?sessionId="+sessionId,
					type : "post",
					data : bph_gpsAdd_pkg,
					dataType : "json",
					success : function(req) { 
						$("body").popjs({"title":"提示","content":"新增定位设备信息成功！","callback":function(){
							window.parent.window.parent.GpsManage.onClose();
							window.parent.$("#dialog").tyWindow.close();
						}}); 
					}
				}); 
			},
			packageGps:function(){
				bph_gpsAdd_pkg.id=0;
				bph_gpsAdd_pkg.orgId=$("#orgId").val();
				var pType= $("#gpsType").val();
				if(pType>0){
					bph_gpsAdd_pkg.typeId = pType;
				}else{ 
						$("body").popjs({"title":"提示","content":"请选择定位设备类型!"}); 
					return;
				}
				 	var gname= $.trim($("#gpsName").val());
				if(gname.length>0){
					if (gname.length > 20) {
					$("body").popjs({"title":"提示","content":"定位设备名称长度过长，限制长度为20！","callback":function(){
								$("#gpsName").focus();
							}});  
						return;
					}
				}else{ 
					$("body").popjs({"title":"提示","content":"请录入定位设备名称！","callback":function(){
								$("#gpsName").focus();
							}});  
					return;
				}
				bph_gpsAdd_pkg.gpsName = gname; 
				
				var pnumber = $.trim($("#gpsNumber").val()); 
				if(pnumber.length>0){
					if (pnumber.length > 20) {
						$("body").popjs({"title":"提示","content":"定位设备编号长度出错，限制长度为0--20！","callback":function(){
								$("#gpsNumber").focus();
							}});  
						return;
					}
					//GpsAddManage.isExistGps(pnumber,0,0);
					//if (!isExist) {
					//	$("body").popjs({"title":"提示","content":"该设备编号在"+bph_Exist_OrgName+"机构下已经存在，请确认之后添加","callback":function(){
					//			$("#gpsNumber").focus();
					//		}});   
					//	return;
					//}
				}else{ 
					$("body").popjs({"title":"提示","content":"请录入定位设备编号！","callback":function(){
								$("#gpsNumber").focus();
							}});  
					return;
				}
				bph_gpsAdd_pkg.number= pnumber;
			
				
				GpsAddManage.isComplete = true;
			},
			saveGpsNotOut:function(){ 
				GpsAddManage.packageGps(); 
				if(!GpsAddManage.isComplete)
				{
					return;
				}
				$.ajax({
					url : "<%=basePath%>gpsWeb/saveGps.do?sessionId="+sessionId,
					type : "post",
					data : bph_gpsAdd_pkg,
					dataType : "json",
					success : function(req) { 
					$("body").popjs({"title":"提示","content":"新增定位设备信息成功"});  
						GpsAddManage.clearAddForm();
					}
				});
				//parent.GpsManage.onClose();
			},

			// 判断警员是否存在
			isExistGps:function(param,gid,type) {
				isExist = false;
				$.ajax({
					url : "<%=basePath%>gpsWeb/isExistGps.do?sessionId="+sessionId,
					type : "POST",
					dataType : "json",
					async : false,
					data : {
						"param" : param,
						"type"  : type,
						"id"    : gid
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
			var pnumber = $.trim($("#gpsNumber").val()); 
			if(pnumber.length>0){
		 	
				$.ajax({
					url : "<%=basePath%>gpsWeb/isExistGps.do?sessionId="+sessionId,
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
						$("body").popjs({"title":"提示","content":"该定位设备编号在"+bph_Exist_OrgName+"机构下已存在，请确认后添加","callback":function(){
								$("#gpsNumber").focus(); 
								return;
							}});   
						return; 
						
					}
					}
				});
				}
			},
			clearAddForm:function(){
				$("#gpsName").val("");
				$("#gpsNumber").val(""); 
			}
	};
</script>
</head>

<body  class="ty-body">
	<div id="vertical">
		<div id="horizontal">
			<div class="pane-content">
				<!-- 左开始 -->
				<div class="demo-section k-header">
					 <table style="width:100%">
					 	<tr>
					 		<td style="width:400px">
					 			<ul  style="float:left; width:300px">
									<li style="padding:5px;"><span class="ty-input-warn">*</span><label for="gpsType" class="fl mr5">GPS类型:</label><input id="gpsType"
										placeholder="请选择类别..." /><input type="hidden"
										id="gpsId"><input type="hidden"
										id="orgId" value="${organ.id}" /></li>
									<li style="padding:5px;"><span class="ty-input-warn">*</span><label for="gpsName" class="fl mr5">GPS名称:</label><input type="text"
										class="k-textbox" name="gpsName" id="gpsName"
										required="required" /></li>
									<li style="padding:5px;"><span class="ty-input-warn">*</span><label for="gpsNumber" class="fl mr5">GPS编号:</label><input
										type="text" class="k-textbox" name="gpsNumber"  onblur="GpsAddManage.isExistNumber()" 
										id="gpsNumber" /></li>
									<!--<li style="padding:5px;"><span class="ty-input-warn"></span><label for="gpsIcons" class="fl mr5">GPS图标:</label><input
										id="gpsIcons" placeholder="请选择对应图标..."  /></li>-->
								</ul> 
					 		</td>
					 		<td>
					 		<!--<img id="icon_thumb" alt="" width="100px" height="100px" src="">-->
					 		</td>
					 	</tr>
					 </table>
					
					<p class="ty-input-row">
						<!--<span class="k-button"  onclick="GpsAddManage.saveGpsNotOut()">保存并继续</span>-->
						<button class="ty-button"  onclick="GpsAddManage.saveGpsWithOut()">确定</button>
					</p>


				</div>
			</div>
		</div>
	</div>
</body> 
</html>
