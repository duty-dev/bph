<jsp:directive.page language="java" pageEncoding="UTF-8"/>

<script>	
var bph_weapon_query ={};
var sessionId = $("#token").val();
$(function() {
	loadData(1); 
	
	$("#weaponNumber").keydown(function(){
        if(event.keyCode == 13){
            WeaponManage.search();
            $("#weaponNumber").focus();
        }
    });
});
function loadData(pageNo){
	WeaponManage.weapon_pageNo = pageNo;
	WeaponManage.packageQuery(pageNo);
	WeaponManage.loadData(pageNo);
}
var WeaponManage= {
	weapon_pageNo:1,
	packageQuery:function(pageNo){
			bph_weapon_query.isSubOrg = $("#organLevel").val();
			bph_weapon_query.number = $.trim($("#weaponNumber").val());
			bph_weapon_query.orgId = $("#organId").val();
			bph_weapon_query.orgPath = $("#organPath").val();
			bph_weapon_query.pageSize = $("#pageSize").val();
			bph_weapon_query.pageStart =pageNo;
	},
	loadData:function(pageNo){
			$.ajax({
					url : "<%=basePath%>weaponWeb/getWeaponList.do?sessionId="+sessionId,
					type : "post",
					data : {
						"weapon_Query" : JSON.stringify(bph_weapon_query),
						"expandeds"		:expandeds,
						"organId":$("#organId").val(),
						"organPath":$("#organPath").val(),
						"selectName":$("#selectName").val()
					},
					dataType : "json",
					success : function(req) {
							if (req.code == 200) {
								if (req.data != null) {
									var udata = req.data;
               						var total = req.totalRows;
               						$("#gridListTotal").html(total+"个");
               						$("#weapongrid").empty();
									$("#weapongrid").kendoGrid({
							dataSource : {
								data : udata 
							},
							height : 472,
							sortable : true,
							resizable: true,
							selectable : "multiple", 
							columns:[{
									title : 'Id',
									field : 'id', 
									width : 10,
									hidden:true
								},{
             							field: "操作",
             							template: "<button type='button' class='ty-edit-btn' id='#: id #' title='修改' onclick='WeaponManage.editWeapon(#: id #)'>修改</button> "+
             							"<button type='button' class='ty-delete-btn' id='#: id #' title='删除' onclick='WeaponManage.deleteWeapon(#: id #)'>删除</button> ",
             							width:120
             					},{
									title : '武器类型',
									field : 'typeName'  
								},{
									title : '武器编号',
									field : 'number' 
								},{
									title : '子弹数目',
									field : 'standard' 
								},{
									title : '机构',
									field : 'orgName' ,
									hidden:true
								}
							],
							change: function (e) {
                                var weaponId = e.sender.selectable.userEvents.currentTarget.cells[0].innerHTML;
                               
                            }
						});
							var myGrid = $("#weapongrid").data("kendoGrid");
             				myGrid.element.on("dblclick","tbody>tr","dblclick",function(e){
             					var id = $(this).find("td").first().text();
             					WeaponManage.editWeapon(id);
             				});
               				var pg = pagination(pageNo,total,'loadData',10);
               	            $("#page").html(pg);
						} 
					} 
				}
			});
	}, 
	deleteWeapon:function(weaponId){
		$("body").tyWindow({"content":"确定要删除?","center":true,"ok":true,"no":true,"okCallback":function(){ 
	 	$.ajax({
					url : "<%=basePath%>weaponWeb/deleteWeaponById.do?weaponId="+weaponId+"&sessionId="+sessionId,
					type : "post", 
					dataType : "json",
					success : function(req) {
						if(req.code==200){
							WeaponManage.loadData(WeaponManage.weapon_pageNo); 
						}else{ 
							$("body").popjs({"title":"提示","content":req.description});
						}
					}
			});
		}});	
	},
	search:function(){ 
		var weaponNumber = $("#weaponNumber").val();
		if(weaponNumber.length>0)
		{
			var myReg = /^[^@\/\'\\\"#$%&\^\*]+$/;
			if(!myReg.test(weaponNumber)){
				$("body").popjs({"title":"提示","content":"查询条件不能包含非法字符"}); 
				return;
			}
		}
		WeaponManage.packageQuery(1);
		WeaponManage.loadData(1);
	},
	addWeapon:function(){
		var organId = $("#organId").val();
		$("#dialog").tyWindow({
			width : "640px",
			height : "540px",
			title : "新增武器信息",
			position : {
				top : "50px"
			},
		content: "<%=basePath%>weaponWeb/gotoWeaponAdd.do?organId=" + organId+"&sessionId="+sessionId,
					iframe : true,
					closeCallback : WeaponManage.onClose,
					okCallback:WeaponManage.onClose 
				});
		//$("#dialog").data("kendoWindow").open();
	},
	editWeapon:function(weaponId){
		var organId = $("#organId").val();
		$("#dialog").tyWindow({
			width : "640px",
			height : "540px",
			title : "编辑武器信息",
			position : {
				top : "50px"
			},
		content: "<%=basePath%>weaponWeb/gotoWeaponEdit.do?weaponId="
							+ weaponId + "&organId=" + organId+"&sessionId="+sessionId,
					iframe : true,
					closeCallback : WeaponManage.onClose,
					okCallback : WeaponManage.onClose 
				});
		//$("#dialog").data("kendoWindow").open();
	},
	onClose:function(e){
		WeaponManage.loadData(WeaponManage.weapon_pageNo);  
	},
	importWeapon:function(){
		var organId = $("#organId").val();
		$("#dialog").tyWindow({
			width : "500px",
			height : "450px",
			title : "武器信息导入",
			position : {
				top : "50px"
			},
		content: "<%=basePath%>weaponWeb/gotoWeaponImport.do?organId=" + organId+"&sessionId="+sessionId,
					iframe : true,
					closeCallback : WeaponManage.onClose
				});
		//$("#dialog").data("kendoWindow").open();
	},
	outPortWeapon:function(){
		WeaponManage.packageQuery();
		$.ajax({
					url : "<%=basePath%>excelOutWeb/exportWeaponDataToExcle.do?sessionId="+sessionId,
					type : "post",
					data : {
						"weapon_Query" : JSON.stringify(bph_weapon_query)
					},
					dataType : "json",
					success : function(req) {
						if (req.code == 200) {
							var urlStr = "<%=basePath %>"+req.description; 
							window.location.href = urlStr;
						}else{ 
							$("body").popjs({"title":"提示","content":req.description});
						}
					}
				});
	}
};
          
</script>

<div id="weapongrid" style="width:150%"></div>
<div id="page"></div>
<div id="dialog"></div>

