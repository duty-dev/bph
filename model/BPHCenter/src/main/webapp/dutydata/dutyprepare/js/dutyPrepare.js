var sessionId = $("#token").val();
var ymd ;
var m_ymd=null;
var m_xid_max = 0; // duty的treegrid的id,必须确保唯一性
var m_changestates = "2";
var isFirstLoad = true;
//var bph_dutyPrepare_OrgId = $("#organId").val();
var m_dutyprepare_Org={}; 
var m_policesourceData=null;
var m_vehicleSourceData=null;
var m_weaponSourceData=null;
var m_gpsSourceData =null;
var m_shift={}; 
var m_userNode={};
var m_target={};
var m_duty={}; 
 
$(function() {
	$("#bd_prepare").keypress(function(e){
        if(e.keyCode==13){
        	//alert(1);
            e.preventDefault();
        }
    });
	ymd = $("#dutyDate").val();
	
	m_ymd = YMD.createNew((ymd),true);
	
	m_dutyprepare_Org.id=$("#organId").val();
	m_dutyprepare_Org.path=$("#organPath").val();
	
	DutyPrepareManage.initControl();
	var obj = {};
	
	obj.orgId = $("#organId").val();
	obj.ymd = ymd;
	DutyPrepareManage.loadDuty(obj,0); 	
	var hei = window.screen.availHeight;
	var t = $("#policeSourceTV").offset().top;
	var b = hei-t-160;
	$("#policeSourceTV").css("height",b);
	$("#vehicleSourceTV").css("height",b);
	$("#weaponSourceTV").css("height",b);
	$("#gpsSourceTV").css("height",b);
	//var backdate = ymd.substring(0,4)+"-"+ymd.substring(4,6)+"-"+ymd.substring(6,8);
	//$("#a_backToCalendar").attr("href","/BPHCenter/dutyRouteWeb/gotoDutyCalendar.do?orgId="+m_dutyprepare_Org.id+"&date="+backdate);
});
//管理窗体及对话框。
var DutyPrepareManage={
		initControl:function(){
			DutyBaseManage.initResourceTabStrip();
			DutyItemManage.initCtl();
		},
		isInitDutyType:false,
		initDutyTypeControl:function(){
			this.initDutyTypeWindow();
		},
		isInitDutyTemplate:false,
		initDutyTemplateControl:function(){ 
			this.initTemplateGridList();
		},
		isInitDutyCalendar:false,
		initDutyCalendarControl:function(){
			this.initCalendarWindow();
		},
		initDutyTypeWindow:function(){
			$("#DutyTypetreeList").kendoTreeList({
				columns : [{
					field : "name",
					expandable: true,
					title : "名称",
					width : 150
					//,template: $("#dutyType-checkbox-template").html()
				} , {
					title : "人数上限",
					field : "maxPolice",
					align : 'left',
					width : 80
				}],
				scrollable: true,
				selectable: "row" 
			});
			$("#DutyTypetreeList").delegate("tbody>tr", "dblclick", this.onSelectDutyType);
			$("#DutyTypetreeList").parent().mCustomScrollbar({scrollButtons:{enable:true},advanced:{ updateOnContentResize: true } });
		},
		initTemplateGridList:function(){
			$("#Templetegrid").kendoGrid({  
				sortable : true,
				resizable: true,
				selectable : "multiple", 
				columns : [ {
					title : 'Id',
					field : 'id',
					hidden : true
				}, {
					title : '模板名称',
					field : 'name'
				} ],
				selectable: "row",
				change : function(e) {
					var tempId = e.sender.selectable.userEvents.currentTarget.cells[0].innerHTML; 
					//alert(tempId);
				}
			}); 
		}, 
		initCalendarWindow:function(){
			$("#calendarWindow").kendoWindow({
                width: "300px",
                title:"复制报备信息",
    	        position:{
    	        	top:'25%',
    	        	left:'40%'
    	        }
            });
			
			$("#cc").kendoCalendar({
				value: new Date()
			});
			
		},
		showDutyTypeWindow : function(){
			if(!DutyPrepareManage.isInitDutyType){
				DutyPrepareManage.initDutyTypeControl();
				DutyPrepareManage.isInitDutyType = true;
			}
		 	$.ajax({
				url : "/BPHCenter/dutyTypeWeb/getDutyTypelist.do?isUsed=true",
				type : "POST",
				dataType : "json",
				async : false,
				success : function(req) {
					if(req.code == 200){
						if(req.data != null){
							//[{id:1,name:'a1',parentId:null},{id:2,name:"a2",parentId:1}]
							$.each(req.data, function(index, value) {
								if(value.parentId==0)
									value.parentId=null;
								});
							var ds = new kendo.data.TreeListDataSource({
								data:req.data,
								schema: {
		                            model: {
		                                id: "id",
		                                expanded: true
		                            }
		                        }
							});
							var dutyTypeTreeList=$("#DutyTypetreeList").data("kendoTreeList");
							
							dutyTypeTreeList.setDataSource(ds);
						} 
					}
				}
			});
			var win =$('#windowDutyType');
			win.kendoWindow({
	                        width: "600px",
	                        title: "备勤类型",
	            	        position:{
	            	        	top:'25%',
	            	        	left:'40%'
	            	        }
	                    });
			win.data("kendoWindow").open();
		},
		onSelectDutyType:function(e,b){
			var dutyTypeTreeList=$("#DutyTypetreeList").data("kendoTreeList");
			var tr=dutyTypeTreeList.select();
			var row= dutyTypeTreeList.dataItem(tr); 
			if(!row.isLeaf){
				$("body").popjs({"title":"提示","content":"请选择最末级节点"}); 
			}else{
				DutyItemManage.addDutyTypeRow(row);
			}
		},
		onSaveDuty:function(){
			DutyPrepareManage.saveDuty(false,null);
		},
		onShowTemplateWindow:function(){

			var win =$('#templateWindow');
			win.kendoWindow({
	                        width: "300px",
	                        title: "备勤模板",
	            	        position:{
	            	        	top:'25%',
	            	        	left:'40%'
	            	        }
	                    });
			//var winTmp=$("#templateWindow").data("kendoWindow");
			win.data("kendoWindow").open();
		},
		onShowCalendarWindow:function(){
			if(!DutyPrepareManage.isInitDutyCalendar){
				DutyPrepareManage.initDutyCalendarControl();
				DutyPrepareManage.isInitDutyCalendar = true;
			}
			var winCal=$("#calendarWindow").data("kendoWindow");
			winCal.open();
		},
		onSaveTemplate:function(){
			var name = $('#txtTemplateName').val();

			var myReg = /^[^@\/\'\\\"#$%&\^\*]+$/;
			if (!myReg.test($.trim(name))) { 
				$("body").popjs({"title":"提示","content":"模板名称含有非法字符"}); 
				$('#txtTemplateName').focus();
				return;
			}
			if (name.length > 20) { 
				$("body").popjs({"title":"提示","content":"模板名称长度过长，限制长度1-20！"}); 
				$('#txtTemplateName').focus();
				return;
			}
			if (name == null || name.lenght == 0 || name == "" || name == undefined) { 
				$("body").popjs({"title":"提示","content":"请输入模板名称"}); 
				return;
			} else {
				DutyPrepareManage.saveDuty(true, name);
				var winTmp=$("#templateWindow").data("kendoWindow");
				winTmp.close();
				//$('#templateWindows').window('close');
			}
		},
		loadDuty:function(pars, type){
			$.ajax({
				url : "/BPHCenter/dutyWeb/loadDutyByOrgIdAndYMD.do",
				type : "POST",
				dataType : "json",
				data : pars,
				async : false,
				success : function(req) {
					if (req.code==200) {// 成功填充数据
						var duty = req.data;
						m_targetPoint = req.data;
						if (duty == null) {
							duty = {};
							duty.id = 0;
							duty.ymd = m_ymd.ymd;
							duty.orgId = m_dutyprepare_Org.id;
							duty.items = [];
						}
						switch (type) { 
						case 1:
							DutyItemManage.clearId(duty);
							duty.isTemplate = false;
							duty.id = 0;
							break;
						case 2:
							DutyItemManage.clearId(duty);
							duty.id = 0;
							break;
						}
						
						DutyItemManage.reCalcDuty(duty.items);
						
						m_duty = duty; 
					} else {
					$("body").popjs({"title":"提示","content":"获取报备明细数据信息失败!"});  
					}
				}
			});
		},
		saveDuty:function(isTemplate, name){
			var duty = {};
			duty.id = m_duty.id;
			duty.orgId = m_dutyprepare_Org.id;
			duty.ymd = ymd;
			duty.name = name;
			duty.isTemplate = isTemplate;
			if (isTemplate) {
				duty.id = 0; // 模板新建
			}

			var tv=$("#dutyItemTV").data("kendoTreeView");
			
			duty.items = tv.dataSource.data();

			DutyItemManage.dutyRegul(duty);

			$.ajax({
				url:"/BPHCenter/dutyWeb/save.do",
				type : "POST",
				dataType : "json",
				data : {
					'duty' : JSON.stringify(duty)
				},
				async : false,
				success : function(req) {
					if (req.code == 200) {// 成功填充数据
						if(duty.isTemplate){
							m_duty.id = 0 ;
							$('#txtTemplateName').val("");
						}else{ 
							m_duty.id = req.data;
						}
 
						$("body").popjs({"title":"提示","content":"保存成功!"}); 
					} else { 
						$("body").popjs({"title":"提示","content":"保存失败!"}); 
					}
				},
				error : function(a) {  
				}
			});

			DutyItemManage.reCalcDuty();
			m_changestates = "2";
		},
		clearDuty:function() {
			
			$("body").tyWindow({"content":"确定要清空所有报备信息?","center":true,"ok":true,"no":true,
				"okCallback":function(){ 
					var tv = $("#dutyItemTV").data("kendoTreeView");
					var items=tv.dataSource.data();
					items.length=0;
					DutyItemManage.reCalcDuty(items);
			}});	
		},
		selectTempleteToLoad:function(){
			var kGrid = $("#Templetegrid").data("kendoGrid");
			var row = kGrid.dataItem(kGrid.select());
			if (row != null) {
				var tempDate = row.ymd; 
				var orgId = row.orgId;
				var obj = {}; 
				obj.id = row.id;
				obj.orgId = orgId;
				obj.ymd = tempDate;
				DutyPrepareManage.loadDuty(obj,1);

				var win =$('#windowTemplete');
				win.data("kendoWindow").close(); 
			}else{
				$("body").popjs({"title":"提示","content":"请选择要操作的数据"}); 
			}
		},
		deleteTempleteById:function(){
			var kGrid = $("#Templetegrid").data("kendoGrid");
			var row = kGrid.dataItem(kGrid.select());
			if (row != null) {
				$("body").tyWindow({"content":"确定要删除名称为 "+row.name+" 的模板?","center":true,"ok":true,"no":true,"okCallback":function(){ 
						$.ajax({
							url : "/BPHCenter/dutyWeb/deleteDutyTemplateAction.do?sessionId="+sessionId,
							type : "POST",
							dataType : "json",
							data : {
								"temId" : row.id
							},
							success : function(req) {
								if (req.code==200) {// 成功填充数据
									DutyBaseManage.loaddutyTemplete();
								} else {
									$("body").popjs({"title":"提示","content":"删除模板失败"}); 
								}
							}
						});
					}
				});
			}else{
				$("body").popjs({"title":"提示","content":"请选择要操作的数据"}); 
			}
		},
		onCopyDuty:function(){ 
			var calendar = $("#cc").data("kendoCalendar"); 
				 var date = calendar.value();
				 var y = date.getFullYear();
				var m = date.getMonth() + 1;
				var d = date.getDate();
				var s = y.toString() + (m < 10 ? '0' + m : m)
							+ (d < 10 ? '0' + d : d);
				var pars = {
						orgId : m_dutyprepare_Org.id,
						ymd : s
					};
				DutyPrepareManage.loadDuty(pars,2);
				var winCal=$("#calendarWindow").data("kendoWindow");
				winCal.close(); 
		}, 
		returnBackToCalendar:function(){ 
			if(m_changestates == "0"){
				$("body").tyWindow({"content":"当前报备信息尚未保存，是否返回报备月程页面?","center":true,"ok":true,"no":true,"okCallback":function(){ 
						DutyPrepareManage.returnBackToCalendarAction();
					}
				});
			}else{
				DutyPrepareManage.returnBackToCalendarAction();
			} 
		},
		returnBackToCalendarAction:function(){
			window.history.back(-1)
			//var date = ymd.substring(0,4)+"-"+ymd.substring(4,6)+"-"+ymd.substring(6,8);
			//window.location.href="/BPHCenter/dutyRouteWeb/gotoDutyCalendar.do?sessionId="+sessionId+"&orgId="+m_dutyprepare_Org.id+"&date="+date;
		}
};
//管理基础资料
var DutyBaseManage = {
	
	initResourceTabStrip:function(){
		$("#tabstrip-sprites").kendoTabStrip({
        	   animation: { open: { effects: "fadeIn"} },
               dataTextField: "text",
               dataSpriteCssClass: "spriteCssClass",
               dataContentField: "content"
           }).data("kendoTabStrip").select(0);
		   DutyBaseManage.initPoliceResourceGridList();	
           DutyBaseManage.initPoliceResource();
		   DutyBaseManage.initVehicleResourceGridList();
           DutyBaseManage.initVehicleResource();
		   DutyBaseManage.initWeaponResourceGridList();
           DutyBaseManage.initWeaponResource();
		   DutyBaseManage.initGpsResourceGridList();
           DutyBaseManage.initGpsResource();
           
	},
	initPoliceResourceGridList:function(){
		$("#policeSourceTV").kendoTreeView({
			//toolbar: kendo.template($("#polContemplate").html()),
			dragAndDrop: true,
			drop:function(e){
				var point=e.dropPosition;
				var sRow=$("#policeSourceTV").data("kendoTreeView").dataItem(e.sourceNode);
				var tRow=$("#dutyItemTV").data("kendoTreeView").dataItem(e.destinationNode );
				
				if(tRow==null || sRow == null){
					e.setValid(false);
				}
				
				if(DutyItemManage.checkDrop(tRow,sRow,point)){
					DutyItemManage.doDrop(tRow, sRow, point);
				}else{
					e.setValid(false);
				}
			},
			dragend:DutyItemManage.doDragEnd,
			template: kendo.template($("#policeSource-template").html()),
			dataTextField:["name","number"]
			});
	},
	bph_police_query:{},
	packagePolQuery:function(){
		DutyBaseManage.bph_police_query.orgId = $("#organId").val();
		DutyBaseManage.bph_police_query.orgCode = $("#organCode").val();
		DutyBaseManage.bph_police_query.orgPath = $("#organPath").val();
		var policeresname =  $.trim($("#policeresName").val());
		if(policeresname.length>0){
			var myReg = /^[^@\/\'\\\"#$%&\^\*]+$/;
			if (!myReg.test(policeresname)) {
						$("body").popjs({"title":"提示","content":"过滤条件名称包含特殊字符，请重新输入"}); 
						$("#policeresName").focus();
						return;
			} 
		}
		DutyBaseManage.bph_police_query.name = policeresname;
		DutyBaseManage.bph_police_query.typeId = "";
		DutyBaseManage.bph_police_query.groupId = "";  
	},
	initPoliceResource:function(){
		DutyBaseManage.packagePolQuery();
		
		$.ajax({
			url : "/BPHCenter/dutyResourceWeb/getPoliceSource.do?sessionId="+sessionId,
			type : "POST",
			dataType : "json",
			data : {
						"police_Query" : JSON.stringify(DutyBaseManage.bph_police_query)
					},
			success : function(req) {
				if(req.code == 200){
					if(req.data != null){
						for(var i= 0;i<req.data.length;i++){
							if(req.data[i].iconUrl == undefined){
								req.data[i].iconUrl = "/BPHCenter/images/images/jihui.png";
							}
						} 
						m_policesourceData = req.data;
						$("#policeSourceTV").data("kendoTreeView").setDataSource(req.data);
						$("#policeSourceTV").mCustomScrollbar({scrollButtons:{enable:true},advanced:{updateOnContentResize:true}});
					} 
				}
			}
		}); 
	},
	polGroupResData:[],
	polTypeResData:[],
	loadPolResCondition:function(){ 
		$.ajax({
			url : "/BPHCenter/dutyResourceWeb/getPoliceGrouplist.do?sessionId="+sessionId,
					type : "post",
					async:false,
					data : {
						"orgId" : m_dutyprepare_Org.id
					},
					dataType : "json",
					success:function(req){
						if(req.code==200){
							if(req.data!=null){
								DutyBaseManage.polGroupResData = req.data;
							}
						}else{
							$("body").popjs({"title":"提示","content":"获取警员分组查询条件失败"}); 
						}
					} 
		});
		$.ajax({
			url : "/BPHCenter/dutyResourceWeb/getPoliceTypeList.do?sessionId="+sessionId,
					type : "post", 
					async:false,
					dataType : "json",
					success:function(req){
						if(req.code==200){
							if(req.data!=null){
								DutyBaseManage.polTypeResData = req.data;
							}
						}else{
							$("body").popjs({"title":"提示","content":"获取警员类型查询条件失败"}); 
						}
					} 
		});
	},
	selectPolResCondition:function(){
		DutyBaseManage.loadPolResCondition();
				 $("#polGroupgrid").kendoGrid({
                         dataSource: {
                            data: DutyBaseManage.polGroupResData
                        }, 
						sortable : true,
                        selectable: "multiple row", 
                        columns: [{
									title : 'id',
									field : 'id',
									hidden: true
								},{
									title : '组名称', 
									template: "<input type='checkbox' id='gp_ck_#: id #' value='#: id #' /> #: name # "
								}] 
                    });
                     $("#polTypegrid").kendoGrid({
                         dataSource: {
                            data: DutyBaseManage.polTypeResData
                        }, 
						sortable : true,
                        selectable: "multiple row", 
                        columns: [{
									title : 'id',
									field : 'id',
									hidden: true
								},{
									title : '类型名称', 
									template: "<input type='checkbox' id='ty_ck_#: id #' value='#: id #' /> #: name #  "
								}]
                    });
                    
                    var win =$('#polResConWindow');
			win.kendoWindow({
	                        width: "450px",
	                        title: "警员分组过滤条件",
	            	        position:{
	            	        	top:'25%',
	            	        	left:'40%'
	            	        }
	                    });
			win.data("kendoWindow").open(); 
	}, 
	searchPoliceResWithOutList:function(){ 
		DutyBaseManage.initPoliceResource();
	},
	searchPoliceResWithList:function(){ 
		DutyBaseManage.bph_police_query.orgId = $("#organId").val();
		DutyBaseManage.bph_police_query.orgCode = $("#organCode").val();
		DutyBaseManage.bph_police_query.orgPath = $("#organPath").val();
		var policeresname =  $.trim($("#policeresName").val());
		if(policeresname.length>0){
			var myReg = /^[^@\/\'\\\"#$%&\^\*]+$/;
			if (!myReg.test(policeresname)) {
						$("body").popjs({"title":"提示","content":"过滤条件名称包含特殊字符，请重新输入"}); 
						$("#policeresName").focus();
						return;
			} 
		}
		DutyBaseManage.bph_police_query.name = policeresname;

		var pol_groupId ="";
		var pol_typeId = "";
		var g = $("#polGroupgrid input:checkbox:checked").length; 
		if(g>0){ 
			var groupObj = $("#polGroupgrid input:checkbox:checked");
			$.each(groupObj, function(index, gobj){
				pol_groupId +=gobj.value+",";
			});
			if(pol_groupId.length>0){
				pol_groupId = pol_groupId.substring(0,pol_groupId.length-1);
			}
		}
		var t = $("#polTypegrid input:checkbox:checked").length;
		if(t>0){ 
			var typeObj = $("#polTypegrid input:checkbox:checked");
			$.each(typeObj, function(index, tobj){
				if(tobj.value!="-1"){
					pol_typeId +=tobj.value+",";
				}
			});
			if(pol_typeId.length>0){
				pol_typeId = pol_typeId.substring(0,pol_typeId.length-1);
			}
		} 
		DutyBaseManage.bph_police_query.typeId = pol_typeId;
		DutyBaseManage.bph_police_query.groupId =pol_groupId;  
				
		$.ajax({
			url : "/BPHCenter/dutyResourceWeb/getPoliceSource.do?sessionId="+sessionId,
			type : "POST",
			dataType : "json",
			data : {
						"police_Query" : JSON.stringify(DutyBaseManage.bph_police_query)
					},
			success : function(req) {
				if(req.code == 200){
					if(req.data != null){
						m_policesourceData = req.data;
						$("#policeSourceTV").data("kendoTreeView").setDataSource(req.data);
					} 
				}
			}
		}); 
		
	},
	initVehicleResourceGridList:function(){
		$("#vehicleSourceTV").kendoTreeView({
			//toolbar: kendo.template($("#polContemplate").html()),
			dragAndDrop: true,
			drop:function(e){
				var point=e.dropPosition;
				var sRow=$("#vehicleSourceTV").data("kendoTreeView").dataItem(e.sourceNode);
				var tRow=$("#dutyItemTV").data("kendoTreeView").dataItem(e.destinationNode );
				
				if(tRow==null || sRow == null){
					e.setValid(false);
				}
				
				if(DutyItemManage.checkDrop(tRow,sRow,point)){
					DutyItemManage.doDrop(tRow, sRow, point);
				}else{
					e.setValid(false);
				}
			},
			dragend:DutyItemManage.doDragEnd,
			template: kendo.template($("#vehicleSource-template").html()),
			dataTextField:["name","number"]
			});
	},
	bph_vehicle_query:{},
	packageVelQuery:function(){
		DutyBaseManage.bph_vehicle_query.orgId = $("#organId").val();
		DutyBaseManage.bph_vehicle_query.orgCode = $("#organCode").val();
		DutyBaseManage.bph_vehicle_query.orgPath = $("#organPath").val();
		var vehicleresName =  $.trim($("#vehicleresName").val());
		if(vehicleresName.length>0){
			var myReg = /^[^@\/\'\\\"#$%&\^\*]+$/;
			if (!myReg.test(vehicleresName)) {
						$("body").popjs({"title":"提示","content":"过滤条件名称包含特殊字符，请重新输入"}); 
						$("#vehicleresName").focus();
						return;
			} 
		}
		DutyBaseManage.bph_vehicle_query.number = vehicleresName; 
		DutyBaseManage.bph_vehicle_query.typeId = "";
		DutyBaseManage.bph_vehicle_query.groupId = ""; 
	 
	},
	initVehicleResource:function(){
		DutyBaseManage.packageVelQuery(); 
		$.ajax({
			url : "/BPHCenter/dutyResourceWeb/getVehicleSource.do?sessionId="+sessionId,
			type : "POST",
			dataType : "json",
			data : {
						"vehicle_Query" : JSON.stringify(DutyBaseManage.bph_vehicle_query)
					},
			success : function(req) {
				if(req.code == 200){
					if(req.data != null){
						m_vehicleSourceData = req.data;
						$("#vehicleSourceTV").data("kendoTreeView").setDataSource(req.data);
						$("#vehicleSourceTV").mCustomScrollbar({scrollButtons:{enable:true},advanced:{updateOnContentResize:true}});
					} 
				}
			}
		}); 
	}, 
	vehGroupResData:[],
	vehTypeResData:[],
	loadVelResCondition:function(){
		$.ajax({
			url : "/BPHCenter/dutyResourceWeb/getVehicleGrouplist.do?sessionId="+sessionId,
					type : "post",
					async:false,
					data : {
						"orgId" : m_dutyprepare_Org.id
					},
					dataType : "json",
					success:function(req){
						if(req.code==200){
							if(req.data!=null){
								DutyBaseManage.vehGroupResData = req.data;
							}
						}else{
							$("body").popjs({"title":"提示","content":"获取警员分组查询条件失败"}); 
						}
					} 
		});
		$.ajax({
			url : "/BPHCenter/dutyResourceWeb/getvehicleTypelist.do?sessionId="+sessionId,
					type : "post", 
					async:false,
					dataType : "json",
					success:function(req){
						if(req.code==200){
							if(req.data!=null){
								DutyBaseManage.vehTypeResData = req.data;
							}
						}else{
							$("body").popjs({"title":"提示","content":"获取警员类型查询条件失败"}); 
						}
					} 
		});
	},
	selectVehResCondition:function(){
		DutyBaseManage.loadVelResCondition();
		 			 $("#vehGroupgrid").kendoGrid({
                 		 dataSource: {
                            data: DutyBaseManage.vehGroupResData
                        }, 
						sortable : true,
                        selectable: "multiple row", 
                        columns: [{
									title : 'id',
									field : 'id',
									hidden: true
								},{
									title : '组名称',
									template: "<input type='checkbox' id='gp_ck_#: id #' value='#: id #' /> #: name # "
								}] 
                    });
                     $("#vehTypegrid").kendoGrid({
                        dataSource: {
                            data: DutyBaseManage.vehTypeResData
                        }, 
						sortable : true,
                        selectable: "multiple row",
                        columns: [{
									title : 'id',
									field : 'id',
									hidden: true
								},{
									title : '类型名称',
									template: "<input type='checkbox' id='ty_ck_#: id #' value='#: id #' /> #: name #  "
								}]
                    });
                     var win =$('#vehResConWindow');
         			win.kendoWindow({
         	                        width: "450px",
         	                        title: "车辆分组过滤条件",
         	           	        position:{
         	       	        	top:'25%',
         	       	        	left:'40%'
         	       	        }
         	                    });
         			win.data("kendoWindow").open();     
	},
	searchVehicleResWithOutList:function(){ 
		DutyBaseManage.initVehicleResource();
	},
	searchVehicleResWithList:function(){
		DutyBaseManage.bph_vehicle_query.orgId = $("#organId").val();
		DutyBaseManage.bph_vehicle_query.orgCode = $("#organCode").val();
		DutyBaseManage.bph_vehicle_query.orgPath = $("#organPath").val();
		var vehicleresName =  $.trim($("#vehicleresName").val());
		if(vehicleresName.length>0){
			var myReg = /^[^@\/\'\\\"#$%&\^\*]+$/;
			if (!myReg.test(vehicleresName)) {
						$("body").popjs({"title":"提示","content":"过滤条件名称包含特殊字符，请重新输入"}); 
						$("#vehicleresName").focus();
						return;
			} 
		}
		DutyBaseManage.bph_vehicle_query.number = vehicleresName;  
		
		var veh_groupId = "";
		var veh_typeId = "";
		var g=$("#vehGroupgrid input:checkbox:checked").length;
		if(g>0){ 
			var groupObj = $("#vehGroupgrid input:checkbox:checked");
			$.each(groupObj, function(index, gobj){
				veh_groupId +=gobj.value+",";
			});
			if(veh_groupId.length>0){
				veh_groupId = veh_groupId.substring(0,veh_groupId.length-1);
			}
		}
		var t = $("#vehTypegrid input:checkbox:checked").length;
		if(t>0){ 
			var typeObj = $("#vehTypegrid input:checkbox:checked");
			$.each(typeObj, function(index, tobj){
				if(tobj.value!="-1"){
					veh_typeId +=tobj.value+",";
				}
			});
			if(veh_typeId.length>0){
				veh_typeId = veh_typeId.substring(0,veh_typeId.length-1);
			}
		}
		DutyBaseManage.bph_vehicle_query.typeId = veh_typeId;
		DutyBaseManage.bph_vehicle_query.groupId =veh_groupId; 

		
		$.ajax({
			url : "/BPHCenter/dutyResourceWeb/getVehicleSource.do?sessionId="+sessionId,
			type : "POST",
			dataType : "json",
			data : {
						"vehicle_Query" : JSON.stringify(DutyBaseManage.bph_vehicle_query)
					},
			success : function(req) {
				if(req.code == 200){
					if(req.data != null){
						m_vehicleSourceData = req.data;
						$("#vehicleSourceTV").data("kendoTreeView").setDataSource(req.data);
					} 
				}
			}
		}); 
	}, 
	initWeaponResourceGridList:function(){
		$("#weaponSourceTV").kendoTreeView({
			//toolbar: kendo.template($("#polContemplate").html()),
			dragAndDrop: true,
			drop:function(e){
				var point=e.dropPosition;
				var sRow=$("#weaponSourceTV").data("kendoTreeView").dataItem(e.sourceNode);
				var tRow=$("#dutyItemTV").data("kendoTreeView").dataItem(e.destinationNode );
				
				if(tRow==null || sRow == null){
					e.setValid(false);
				}
				
				if(DutyItemManage.checkDrop(tRow,sRow,point)){
					DutyItemManage.doDrop(tRow, sRow, point);
				}else{
					e.setValid(false);
				}
			},
			dragend:DutyItemManage.doDragEnd,
			template: kendo.template($("#weaponSource-template").html()),
			dataTextField:["typeName","number"]
		});
		
	},
	bph_weapon_query:{},
	packageWepQuery:function(){
		DutyBaseManage.bph_weapon_query.orgId = $("#organId").val();
		DutyBaseManage.bph_weapon_query.orgCode = $("#organCode").val();
		DutyBaseManage.bph_weapon_query.orgPath = $("#organPath").val();
		var weaponresName =  $.trim($("#weaponresName").val());
		if(weaponresName.length>0){
			var myReg = /^[^@\/\'\\\"#$%&\^\*]+$/;
			if (!myReg.test(weaponresName)) {
						$("body").popjs({"title":"提示","content":"过滤条件名称包含特殊字符，请重新输入"}); 
						$("#weaponresName").focus();
						return;
			} 
		}
		DutyBaseManage.bph_weapon_query.number = weaponresName;  
		DutyBaseManage.bph_weapon_query.typeId = "";
		DutyBaseManage.bph_weapon_query.groupId = "";  
	},
	initWeaponResource:function(){
		DutyBaseManage.packageWepQuery();
		
		
		$.ajax({
			url : "/BPHCenter/dutyResourceWeb/getWeaponSource.do?sessionId="+sessionId,
			type : "POST",
			dataType : "json",
			dataType : "json",
			data : {
						"weapon_Query" : JSON.stringify(DutyBaseManage.bph_weapon_query)
					},
			success : function(req) {
				if(req.code == 200){
					if(req.data != null){
						m_weaponSourceData = req.data;
						$("#weaponSourceTV").data("kendoTreeView").setDataSource(req.data);
						$("#weaponSourceTV").mCustomScrollbar({scrollButtons:{enable:true},advanced:{updateOnContentResize:true}});
					} 
				}
			}
		}); 
	},
	wepGroupResData:[],
	wepTypeResData:[],
	loadWepResCondition:function(){
		$.ajax({
			url : "/BPHCenter/dutyResourceWeb/getWeaponGrouplist.do?sessionId="+sessionId,
					type : "post",
					async:false,
					data : {
						"orgId" : m_dutyprepare_Org.id
					},
					dataType : "json",
					success:function(req){
						if(req.code==200){
							if(req.data!=null){
								DutyBaseManage.wepGroupResData = req.data;
							}
						}else{
							$("body").popjs({"title":"提示","content":"获取武器分组查询条件失败"}); 
						}
					} 
		});
		$.ajax({
			url : "/BPHCenter/dutyResourceWeb/getWeaponTypelist.do?sessionId="+sessionId,
					type : "post", 
					dataType : "json",
					async:false,
					success:function(req){
						if(req.code==200){
							if(req.data!=null){
								DutyBaseManage.wepTypeResData = req.data;
							}
						}else{
							$("body").popjs({"title":"提示","content":"获取武器类型查询条件失败"}); 
						}
					} 
		}); 
	},
	selectWepResCondition:function(){
		DutyBaseManage.loadWepResCondition();
		 $("#wepGroupgrid").kendoGrid({ 
                        dataSource: {
                            data: DutyBaseManage.wepGroupResData
                        }, 
						sortable : true,
                        selectable: "multiple row",
                        columns: [{
									title : 'id',
									field : 'id',
									hidden: true
								},{
									title : '组名称',
									template: "<input type='checkbox' id='gp_ck_#: id #' value='#: id #' /> #: name #  "
								}] 
                    });
                     $("#wepTypegrid").kendoGrid({
                          dataSource: {
                            data: DutyBaseManage.wepTypeResData
                        }, 
						sortable : true,
                        selectable: "multiple row",
                        columns: [{
									title : 'id',
									field : 'id',
									hidden: true
								},{
									title : '类型名称', 
									template: "<input type='checkbox' id='ty_ck_#: id #' value='#: id #' /> #: name #  "
								}]
                    });
              var win =$('#wepResConWindow');
         			win.kendoWindow({
         	                        width: "450px",
         	                        title: "武器分组过滤条件",
         	           	        position:{
         	       	        	top:'25%',
         	       	        	left:'40%'
         	       	        }
         	                    });
         			win.data("kendoWindow").open();  
	},
	searchWeaponResWithOutList:function(){
		DutyBaseManage.initWeaponResource();　
	},
	searchWeaponResWithList:function(){　
		DutyBaseManage.bph_weapon_query.orgId = $("#organId").val();
		DutyBaseManage.bph_weapon_query.orgCode = $("#organCode").val();
		DutyBaseManage.bph_weapon_query.orgPath = $("#organPath").val();
		var weaponresName =  $.trim($("#weaponresName").val());
		if(weaponresName.length>0){
			var myReg = /^[^@\/\'\\\"#$%&\^\*]+$/;
			if (!myReg.test(weaponresName)) {
						$("body").popjs({"title":"提示","content":"过滤条件名称包含特殊字符，请重新输入"}); 
						$("#weaponresName").focus();
						return;
			} 
		}
		DutyBaseManage.bph_weapon_query.number = weaponresName;    
		
		var wep_groupId = "";
		var wep_typeId = "";
		var g=$("#wepGroupgrid input:checkbox:checked").length;
		if(g>0){ 
			var groupObj = $("#wepGroupgrid input:checkbox:checked");
			$.each(groupObj, function(index, gobj){
				wep_groupId +=gobj.value+",";
			});
			if(wep_groupId.length>0){
				wep_groupId = wep_groupId.substring(0,wep_groupId.length-1);
			}
		}
		var t = $("#wepTypegrid input:checkbox:checked").length;
		if(t>0){ 
			var typeObj = $("#wepTypegrid input:checkbox:checked");
			$.each(typeObj, function(index, tobj){
				if(tobj.value!="-1"){
					wep_typeId +=tobj.value+",";
				}
			});
			if(wep_typeId.length>0){
				wep_typeId = wep_typeId.substring(0,wep_typeId.length-1);
			}
		}
		DutyBaseManage.bph_weapon_query.typeId = wep_typeId; 
		DutyBaseManage.bph_weapon_query.groupId = wep_groupId;
		
		$.ajax({
			url : "/BPHCenter/dutyResourceWeb/getWeaponSource.do?sessionId="+sessionId,
			type : "POST",
			dataType : "json",
			dataType : "json",
			data : {
						"weapon_Query" : JSON.stringify(DutyBaseManage.bph_weapon_query)
					},
			success : function(req) {
				if(req.code == 200){
					if(req.data != null){
						m_weaponSourceData = req.data;
						$("#weaponSourceTV").data("kendoTreeView").setDataSource(req.data);
					} 
				}
			}
		}); 
		
	},
	initGpsResourceGridList:function(){
		$("#gpsSourceTV").kendoTreeView({
			//toolbar: kendo.template($("#polContemplate").html()),
			dragAndDrop: true,
			drop:function(e){
				var point=e.dropPosition;
				var sRow=$("#gpsSourceTV").data("kendoTreeView").dataItem(e.sourceNode);
				var tRow=$("#dutyItemTV").data("kendoTreeView").dataItem(e.destinationNode );
				
				if(tRow==null || sRow == null){
					e.setValid(false);
				}
				
				if(DutyItemManage.checkDrop(tRow,sRow,point)){
					DutyItemManage.doDrop(tRow, sRow, point);
				}else{
					e.setValid(false);
				}
			},
			dragend:DutyItemManage.doDragEnd,
			template: kendo.template($("#gpsSource-template").html()),
			dataTextField:["typeName","number"]
		});
		
	},
	bph_gps_query:{},
	packageGpsQuery:function(){
		DutyBaseManage.bph_gps_query.orgId = $("#organId").val();
		DutyBaseManage.bph_gps_query.orgCode = $("#organCode").val();
		DutyBaseManage.bph_gps_query.orgPath = $("#organPath").val();
		var gpsresName =  $.trim($("#gpsresName").val());
		if(gpsresName.length>0){
			var myReg = /^[^@\/\'\\\"#$%&\^\*]+$/;
			if (!myReg.test(gpsresName)) {
						$("body").popjs({"title":"提示","content":"过滤条件名称包含特殊字符，请重新输入"}); 
						$("#gpsresName").focus();
						return;
			} 
		}
		DutyBaseManage.bph_gps_query.gpsname = gpsresName;   
		DutyBaseManage.bph_gps_query.typeId = "";
		DutyBaseManage.bph_gps_query.groupId = "";  
	},
	/*
	 * 初始化定位设备数据
	 */
	initGpsResource:function(){
		DutyBaseManage.packageGpsQuery();
		 
		$.ajax({
			url : "/BPHCenter/dutyResourceWeb/getGpsSource.do?sessionId="+sessionId,
			type : "POST",
			dataType : "json",
			data : {
						"gps_Query" : JSON.stringify(DutyBaseManage.bph_gps_query)
					},
			success : function(req) {
				if(req.code==200){
					if(req.data!=null){
						m_gpsSourceData = req.data;
						$("#gpsSourceTV").data("kendoTreeView").setDataSource(req.data);
						$("#gpsSourceTV").mCustomScrollbar({scrollButtons:{enable:true},advanced:{updateOnContentResize:true}});
					}
				}else{
					$("body").popjs({"title":"提示","content":"获取定位设备失败"}); 
				}
			}
		}); 
	},
	gpsGroupResData:[],
	gpsTypeResData:[],
	loadGpsResCondition:function(){ 
		$.ajax({
			url : "/BPHCenter/dutyResourceWeb/getGpsGrouplist.do?sessionId="+sessionId,
					type : "post",
					async:false,
					data : {
						"orgId" : m_dutyprepare_Org.id
					},
					dataType : "json",
					success:function(req){
						if(req.code==200){
							if(req.data!=null){
								DutyBaseManage.gpsGroupResData = req.data;
							}
						}else{
							$("body").popjs({"title":"提示","content":"获取定位设备分组查询条件失败"}); 
						}
					} 
		});
		$.ajax({
			url : "/BPHCenter/dutyResourceWeb/getGpsTypelist.do?sessionId="+sessionId,
					type : "post", 
					dataType : "json",
					async:false,
					success:function(req){
						if(req.code==200){
							if(req.data!=null){
								DutyBaseManage.gpsTypeResData = req.data;
							}
						}else{
							$("body").popjs({"title":"提示","content":"获取定位设备类型查询条件失败"}); 
						}
					} 
		}); 
	},
	selectGpsResCondition:function(){
		DutyBaseManage.loadGpsResCondition(); 
		  $("#gpsGroupgrid").kendoGrid({
                        dataSource: {
                            data: DutyBaseManage.gpsGroupResData
                        }, 
						sortable : true,
                        selectable: "multiple row",
                        columns: [{
									title : 'id',
									field : 'id',
									hidden: true
								},{
									title : '组名称',
									template: "<input type='checkbox' id='gp_ck_#: id #' value='#: id #' /> #: name #  "
								}] 
                    });
                     $("#gpsTypegrid").kendoGrid({
                        dataSource: {
                            data: DutyBaseManage.gpsTypeResData
                        }, 
						sortable : true,
                        selectable: "multiple row",
                        columns: [{
									title : 'id',
									field : 'id',
									hidden: true
								},{
									title : '类型名称',
									template: "<input type='checkbox' id='ty_ck_#: id #' value='#: id #' /> #: name #  "
								}]
                    });
              var win =$('#gpsResConWindow');
         			win.kendoWindow({
         	                        width: "450px",
         	                        title: "武器分组过滤条件",
         	           	        position:{
         	       	        	top:'25%',
         	       	        	left:'40%'
         	       	        }
         	                    });
         			win.data("kendoWindow").open();   
	},
	searchGpsResWithOutList:function(){
		DutyBaseManage.initGpsResource();
	},
	searchGpsResWithList:function(){ 
		DutyBaseManage.bph_gps_query.orgId = $("#organId").val();
		DutyBaseManage.bph_gps_query.orgCode = $("#organCode").val();
		DutyBaseManage.bph_gps_query.orgPath = $("#organPath").val();
		var gpsresName =  $.trim($("#gpsresName").val());
		if(gpsresName.length>0){
			var myReg = /^[^@\/\'\\\"#$%&\^\*]+$/;
			if (!myReg.test(gpsresName)) {
						$("body").popjs({"title":"提示","content":"过滤条件名称包含特殊字符，请重新输入"}); 
						$("#gpsresName").focus();
						return;
			} 
		}
		DutyBaseManage.bph_gps_query.gpsname = gpsresName;    
		
		var gps_groupId = "";
		var gps_typeId = "";
		var g=$("#gpsGroupgrid input:checkbox:checked").length;
		if(g>0){ 
			var groupObj = $("#gpsGroupgrid input:checkbox:checked");
			$.each(groupObj, function(index, gobj){
				gps_groupId +=gobj.value+",";
			});
			if(gps_groupId.length>0){
				gps_groupId = gps_groupId.substring(0,gps_groupId.length-1);
			}
		}
		var t = $("#gpsTypegrid input:checkbox:checked").length;
		if(t>0){ 
			var typeObj = $("#gpsTypegrid input:checkbox:checked");
			$.each(typeObj, function(index, tobj){
				if(tobj.value!="-1"){
					gps_typeId +=tobj.value+",";
				}
			});
			if(gps_typeId.length>0){
				gps_typeId = gps_typeId.substring(0,gps_typeId.length-1);
			}
		}
		DutyBaseManage.bph_gps_query.typeId = gps_typeId; 
		DutyBaseManage.bph_gps_query.groupId = gps_groupId;
		$("#gpsSourceTV").empty();
		$("#gpsSourceTV").kendoTreeView({
			//toolbar: kendo.template($("#polContemplate").html()),
			dragAndDrop: true,
			drop:function(e){
				var point=e.dropPosition;
				var sRow=$("#gpsSourceTV").data("kendoTreeView").dataItem(e.sourceNode);
				var tRow=$("#dutyItemTV").data("kendoTreeView").dataItem(e.destinationNode );
				
				if(tRow==null || sRow == null){
					e.setValid(false);
				}
				
				if(DutyItemManage.checkDrop(tRow,sRow,point)){
					DutyItemManage.doDrop(tRow, sRow, point);
				}else{
					e.setValid(false);
				}
			},
			dragend:DutyItemManage.doDragEnd,
			template: kendo.template($("#gpsSource-template").html()),
			dataTextField:["typeName","number"]
		});
		
		$.ajax({
			url : "/BPHCenter/dutyResourceWeb/getGpsSource.do?sessionId="+sessionId,
			type : "POST",
			dataType : "json",
			data : {
						"gps_Query" : JSON.stringify(DutyBaseManage.bph_gps_query)
					},
			success : function(req) {
				if(req.code==200){
					if(req.data!=null){
						m_gpsSourceData = req.data;
						$("#gpsSourceTV").data("kendoTreeView").setDataSource(req.data);
					}
				}else{
					$("body").popjs({"title":"提示","content":"获取定位设备失败"}); 
				}
			}
		}); 
	}, 
	selectDutyTemplete:function(){ 
		if(!DutyPrepareManage.isInitDutyTemplate){
			DutyPrepareManage.initDutyTemplateControl();
			DutyPrepareManage.isInitDutyTemplate = true;
		}
		DutyBaseManage.loaddutyTemplete();
		var win =$('#windowTemplete');
		win.kendoWindow({
                        width: "450px",
                        title: "备勤模板",
            	        position:{
            	        	top:'25%',
            	        	left:'40%'
            	        }
                    });
		win.data("kendoWindow").open(); 
	},
	loaddutyTemplete:function(){
		$.ajax({
			url : "/BPHCenter/dutyWeb/loadTemplateByOrgId.do?sessionId="+sessionId+"&orgId="+m_dutyprepare_Org.id,
			type : "POST",
			dataType : "json",
			async : false,
			success : function(req) {
				if(req.code == 200){
					if(req.data != null){
						var udata = req.data;
						var dtsource = new kendo.data.DataSource({
							  data: udata
							});
						var dutyTempleteList=$("#Templetegrid").data("kendoGrid");
						
						dutyTempleteList.setDataSource(dtsource);
					} 
				}
			}
		});
	}, 
	exportDuty:function(){
	
	},
	showDialog:function(title,msg){
		var dlgEl=$("#dialog");
		dlgEl.kendoWindow({
			'width': "300px",
            'title': title,
            'actions': [
                "Close"
            ]
		});
		var dialog=dlgEl.data("kendoWindow");
		dialog.content(msg);
		dialog.open();
	}
};
// 备情功能比较复杂，所以单独建立一个对象管理器.
var DutyItemManage={
		
		initCtl:function(){
			this.initShiftWindow();
			this.initdutyItemTV();
			this.initUserNodeWindow();
			this.initTaskWindow();
		},
		initShiftWindow:function(){
			 $("#tpkBeginTime").kendoTimePicker();
			 $("#tpkEndTime").kendoTimePicker();
			 
			 $("#shiftWindow").kendoWindow({
                 width: "450px",
     	        position:{
    	        	top:'25%',
    	        	left:'40%'
    	        }
             });
		},
		initUserNodeWindow:function(){
			$("#userNodeWindow").kendoWindow({
                width: "450px",
    	        position:{
    	        	top:'25%',
    	        	left:'40%'
    	        }
            });
		},
		initTaskWindow:function(){
			$("#taskGrid").kendoGrid(	{
				height : 250,
				sortable : true,
				selectable : "multiple", 
				columns : [{
						title : 'Id',
						field : 'id',
						width : 10
					},{
						title : "名称",
						field:"areaName"
					},{
						title:"点位名称",
						field : "name"
					},{
						title : "经过次数",
						field : "count"
					},{
						title : "停留时间(分钟)",
						field : "stayTime"
					},{
						title : "操作",
						template:""
					} ]
			}); 
		},
		initdutyItemTV:function(){
			m_duty.id = 0;
			$("#dutyItemTV").kendoTreeView({
				template:kendo.template($("#dutyItem-template").html()),
				dragAndDrop: true,
				drop: DutyItemManage.onDutyItemDrop,
				dragend:DutyItemManage.onDutyItemDragEnd,
				dataTextField:["displayName"]
			});	     

		},
		doDrop:function(tRow,sRow,point){

			if (sRow.xid == undefined) {
				/* 从资源拖动过来 */
				/* itemId,name,typeId,innerTypeId,innerTypeName,dutyRow */
				var name = sRow.itemTypeId == 2 ? sRow.name : sRow.number;
				// sRow.iconUrl = tRow.iconUrl == undefined ? null : tRow.iconUrl;

				switch (sRow.itemTypeId) {
				case 1:
					name = sRow.number;
					break;
				case 2:
					name = sRow.name;
					break;
				case 3:
					name = sRow.number;
					break;
				case 4:
					name = sRow.number;
					break;
				}

				DutyItemManage.genDutyRow(sRow.id, name, sRow.itemTypeId, sRow.typeId, sRow.typeName,sRow);
									
				if (sRow.itemTypeId == 1) {
					//$('#source_vehicle').treegrid('loadData', m_vehiclesourceData);
				} else if (sRow.itemTypeId == 2) {
					if (m_policesourceData != null && m_policesourceData.length > 0) {
						$.each(m_policesourceData, function(index, value) {
							var iconUrl = value.iconUrl;// .substring(1,
							// value.length);
							//itemiconCls = createIconStyle(value, 2, iconUrl);
						});
					}
					//$('#source_police').treegrid('loadData', m_policesourceData);
				} else if (sRow.itemTypeId == 3) {
					//$('#source_weapon').treegrid('loadData', m_weaponsourceData);
				} else if (sRow.itemTypeId == 4) {
					//$('#source_gpsdevice').treegrid('loadData', m_gpssourceData);
				}
			}
			
		},
		doDragEnd:function(e){
			DutyItemManage.reCalcDuty();
		},
		checkDrop:function(tRow,sRow,point){
			
			if(tRow==null || sRow == null){
				return false;
			}
			var pTypeId = null;

			if (point == "over") {
				pTypeId = tRow.itemTypeId;
			} else {
				var p = tRow.getParent();
				pTypeId = p == null ? 0 : p.itemTypeId;
			}

			var isSuccess = dutyItemRelate.check(pTypeId, sRow.itemTypeId);

			if (!isSuccess) {
				//$.messager.alert("操作提示", "资源载入类型不符合规则，请按规则添加资源", "error");
				$("body").popjs({"title":"操作提示","content":"资源载入类型不符合规则，请按规则添加资源"}); 
				return false;
			} else {
				var shiftRowT = null;
				var shiftRowS = null;
				var dutyTypeRow = DutyItemManage.findDutyTypeRow(tRow);
				
				var exists = false;
				var isMaxPolice = false;

				if (sRow.xid != undefined) {
					shiftRowT = DutyItemManage.findShiftRow(tRow);
					shiftRowS = DutyItemManage.findShiftRow(sRow);
					isMaxPolice = DutyItemManage.checkMaxPolice(dutyTypeRow, shiftRowT, sRow);
					if (shiftRowT.xid != shiftRowS.xid) {
						exists = DutyItemManage.existsResource(shiftRowT, sRow);

					}
				} else {
					shiftRowT = DutyItemManage.findShiftRow(tRow);
					isMaxPolice = DutyItemManage.checkMaxPolice(dutyTypeRow, shiftRowT, sRow);
					exists =DutyItemManage.existsResource(shiftRowT, sRow);
				}
				if (exists) {
					var name = sRow.itemTypeId == 2 ? sRow.name : sRow.number;
					//$.messager.alert('提示', name + ' 在班次 ' + shiftRowT.name + '中已经存在!',	"warning");
					$("body").popjs({"title":"操作提示","content":name + ' 在班次 ' + shiftRowT.name + '中已经存在!'}); 
				}

				if (isMaxPolice) {
					//$.messager.alert('提示', '勤务类型: ' + dutyTypeRow.name + ' 警员数量上限是:'+ dutyTypeRow.maxPolice, "warning");
					$("body").popjs({"title":"操作提示","content": '勤务类型: ' + dutyTypeRow.name + ' 警员数量上限是:'+ dutyTypeRow.maxPolice}); 
				}

				return !exists && !isMaxPolice;
			}
		},
		addDutyTypeRow :function(value){
			var duty = {};
			duty.maxPolice = value.maxPolice;
			if(value.assoTaskType == "巡区"){
				duty.taskType = 1;
			}else if(value.assoTaskType == "社区"){ 
				duty.taskType = 2;
			}else if(value.assoTaskType == "卡点"){
				duty.taskType = 3;
			}else{
				duty.taskType = value.assoTaskType;
			} 
			duty.targets = [];
			duty.expanded =true;
			
			var shift = {};
			this.genDutyRow(value.id, value.name, 100, value.typeId, value.name, duty);
			shift.getParent = function() {
				return duty;
			};
			shift.beginTime2 = new Date(m_ymd.getYear(), m_ymd.getMonth() - 1, m_ymd
					.getDay(), 9, 30);
			shift.endTime2 = new Date(m_ymd.getYear(), m_ymd.getMonth() - 1, m_ymd
					.getDay(), 16, 30);
			this.genDutyRow(null, "班次", 101, null, "班次", shift);
			
			duty.items=[];
			duty.items.push(shift);
			
			var tv = $("#dutyItemTV").data("kendoTreeView");
			
			var dutyItems=tv.dataSource.data();
			
			dutyItems.push(duty);
			
			this.reCalcDuty(dutyItems);
			
//			//TreeListDataSource HierarchicalDataSource
//			var ds = new kendo.data.HierarchicalDataSource({
//			    data:dutyItems,
//			    schema: {
//			        model: {
//			          children: "items"
//			        }
//			      }
//			});
//
//			
//			tv.setDataSource(ds);
//			tv.expand(".k-item");

		},
		genDutyRow:function(itemId, name, typeId, innerTypeId, innerTypeName, dutyRow) {
			if (dutyRow.id == undefined || dutyRow.id == null)
				dutyRow.id = 0;
			
			dutyRow.xid = this.genXId(typeId);
			dutyRow.name = name;
			dutyRow.itemTypeId = typeId;
			dutyRow.itemId = itemId;
			// dutyRow.itemInnerTypeId = innerTypeId;
			dutyRow.itemInnerTypeName = innerTypeName;
			dutyRow.displayName = this.genDisplayName(dutyRow); //typeId, innerTypeName, name
			dutyRow.itemTypeName = this.genItemTypeName(typeId);
		},
		genDisplayName:function(row){
			//itemTypeId, iteminnerTypeName, name
			var rs = '';
			switch (row.itemTypeId) {
			case 1:
				rs = row.itemInnerTypeName + ":" + row.name;
				break;
			case 2:
				rs = row.itemInnerTypeName + ":" + row.name;
				break;
			case 3:
				rs = row.itemInnerTypeName + ":" + row.name;
				break;
			case 4:
				rs = row.itemInnerTypeName + ":" + row.name;
				break;
			case 100:
				rs =  row.name;
				break;
			case 101:
				var bts =row.beginTime2.getHours() + ":" +(row.beginTime2.getMinutes()>9?row.beginTime2.getMinutes():"0"+row.beginTime2.getMinutes());
				var ets=row.endTime2.getHours() + ":" +(row.endTime2.getMinutes()>9?row.endTime2.getMinutes():"0"+row.endTime2.getMinutes());
				rs =  row.name +"  [" + bts +"到" + (row.isOverDay?"第二天":"") + ets +"] ";
				
				break;
			case 999:
				rs = row.itemInnerTypeName + ":" + row.name;
				break;
			}
			var sts=DutyItemManage.getRowStatistics(row);
			return rs + sts;
		},
		getRowStatistics:function(row){
			var rs="";
			if( row.policeCount !=null && row.policeCount >0){
				rs += "  警员:"+row.policeCount;
			}
			if( row.velicleCount !=null && row.velicleCount >0){
				rs += "  车辆:"+row.velicleCount;
			}
			if( row.weaponCount !=null && row.weaponCount >0){
				rs += "  武器:"+row.weaponCount;
			}
			if( row.gpsCount !=null && row.gpsCount >0){
				rs += "  定位:"+row.gpsCount;
			}
			
			if(rs !=""){
				rs= "  < " +rs + " >";
			}
			
			return rs;
		},
		genItemTypeName:function (itemTypeId) {
			switch (itemTypeId) {
			case 1:
				return "车辆";
			case 2:
				return "警员";
			case 3:
				return "武器";
			case 4:
				return "定位设备";
			case 100:
				return "备勤类型";
			case 101:
				return "班次";
			case 999:
				return "自定义编组";
			}
		},
		genXId:function (itemTypeId, itemId) {
			m_xid_max++;
			return itemTypeId + "_AI_" + m_xid_max;
		},
		findDutyTypeRow:function(tRow) {
			if (tRow.itemTypeId == 100)
				return tRow;
			else
				return DutyItemManage.findDutyTypeRow(tRow.getParent());
		},
		findShiftRow:function(tRow) {
			if (tRow.itemTypeId == 101)
				return tRow;
			else
				return DutyItemManage.findShiftRow(tRow.getParent());
		},
		checkMaxPolice:function(dutyTypeRow, shiftRow, row) {
			if (row.itemTypeId == 2 && dutyTypeRow.maxPolice > 0
					&& shiftRow.policeCount >= dutyTypeRow.maxPolice) {
				return true;
			} else {
				return false;
			}
		},
		/**
		 * 判断资源是否在班次中重复
		 * 
		 * @param p
		 * @param row
		 * @returns {Boolean}
		 */
		existsResource:function(p, row) {
			var exists = false;
			if (row.xid == undefined && p.itemTypeId == row.itemTypeId
					&& p.itemId == row.id) {
				return true;
			} else if (row.xid != undefined && p.itemTypeId == row.itemTypeId
					&& p.itemId == row.itemId) {
				return true;
			} else if (p.items != null && p.items.length > 0) {
				$.each(p.items, function(index, value) {
					exists = DutyItemManage.existsResource(value, row);
					if (exists) {
						return false;
					}
				});
			}
			return exists;
		},
		/* 从新计算并加载数据 */
		reCalcDuty:function(data) {
//			for(var m = 0;m<data.length;m++){
//				if(data[m].iconUrl == undefined){
//					data[m].iconUrl = "/BPHCenter/images/images/jihui.png";
//				}
//			}
			/* 从新计算并加载数据 */
			var item_new =null;
			var item_old =null;
			
			if(data==undefined)
			{
				var tv = $("#dutyItemTV").data("kendoTreeView");
				item_old=tv.dataSource.data();
				
			}else{
				item_old = data;
			}
			
			item_new=DutyItemManage.rebuildItems(item_old);
			
			DutyItemManage.reloadItems(item_new);
			if(!isFirstLoad){ 
				m_changestates = "0";
			}
			isFirstLoad = false;
			
		},
		reloadItems:function(items){
			var ds = new kendo.data.HierarchicalDataSource({
			    data:items,
			    schema: {
			        model: {
			          children: "items"
			        }
			      }
			});
			
			$("#dutyItemTV").remove();
			$("#dutyItemTVBox").append("<div id='dutyItemTV'></div>");
			
			var tv  = $("#dutyItemTV").kendoTreeView({
				template:kendo.template($("#dutyItem-template").html()),
				dataSource:ds,
				dragAndDrop: true,
				drop: DutyItemManage.onDutyItemDrop,
				dragend:DutyItemManage.onDutyItemDragEnd,
				dataTextField:["displayName"]
			}).data("kendoTreeView");
			var hei = window.screen.availHeight;
    		var t = $("#dutyItemTV").offset().top;
    		$("#dutyItemTV").css("height",hei-t-160).mCustomScrollbar({scrollButtons:{enable:true},advanced:{ updateOnContentResize: true } });
			//tv.expand(".k-item");
		},
		structureItemTree:function(items) {
			$.each(items, function(i, val) {
				DutyItemManage.structureItem(val, null);
			});
		},
		structureItem:function(item, parent) {
			item.getParent = function() {
				return parent;
			};
			/* 初始化数量等于0 */
			item.velicleCount = 0;
			item.policeCount = 0;
			item.weaponCount = 0;
			item.gpsCount = 0;
			item.xid =DutyItemManage.genXId(item.itemTypeId);

			if (item.itemTypeId == 101) {
				DutyItemManage.initDate(item);
			}

			//itemiconCls = createIconStyle(item, item.itemTypeId, item.iconUrl);

			switch (item.itemTypeId) {
			case 1:
				item.velicleCount = 1;
				break;
			case 2:
				item.policeCount = 1;
				break;
			case 3:
				item.weaponCount = 1;
				break;
			case 4:
				item.gpsCount = 1;
				break;
			}
			if (item.items != undefined && item.items != null
					&& item.items.length > 0) {
				$.each(item.items, function(i, val) {
					DutyItemManage.structureItem(val, item);/* 获取下级的汇总 */
					item.velicleCount += val.velicleCount;
					item.policeCount += val.policeCount;
					item.weaponCount += val.weaponCount;
					item.gpsCount += val.gpsCount;
				});
			}
		},
		dutyRegul:function(duty){
			if (duty.items != null) {
				$.each(duty.items, function(i, row) {
					DutyItemManage.itemRegul(row);
				});
			}
		},
		itemRegul:function(item){
			if (item.itemTypeId == 101) {
				item.beginTime = item.beginTime2.toSimpleString();
				item.endTime = item.endTime2.toSimpleString();
			}
			item.beginTime2 = undefined;
			item.endTime2 = undefined;
			if (item.items != null) {
				$.each(item.items, function(i, row) {
					DutyItemManage.itemRegul(row);
				});
			}
		},
		/**
		 * 初始化日期
		 * 
		 * @param ymd
		 * @param item
		 */
		initDate:function(item) {

			if (item.beginTime2 == undefined || item.endTime2 == undefined) {
				var b = gCreateDate(item.beginTime);
				var e = gCreateDate(item.endTime);

				var diffDay = b.dateDiffOfDay(e);

				if (diffDay > 1) {
					alert('date diff day is error !');
				}

				b.setFullYear(m_ymd.getYear(), m_ymd.getMonth() - 1, m_ymd.getDay());
				e.setFullYear(m_ymd.getYear(), m_ymd.getMonth() - 1, m_ymd.getDay());
				e.add('d', diffDay);

				item.beginTime2 = b;
				item.endTime2 = e;
			}
		},
		createDate:function(dateStr){
			return new Date(Date.parse(dateStr.replace(/-/g, "/")));
		},
		/**
		 * 备勤菜单
		 */
		/*
		 * 添加班次
		 */
		addShift:function(){
			var tv = $("#dutyItemTV").data("kendoTreeView");
			var row=tv.dataItem(tv.select());
			
			if (row == null) {
				//$.messager.alert('提示', "请选择备勤类型", "warning");
				$("body").popjs({"title":"提示","content":"请选择备勤类型"}); 
			} else {
				if (dutyItemRelate.check(row.itemTypeId, 101)) {
					$('#txtShiftName').val('');

					var tpkBeginTime = $("#tpkBeginTime").data("kendoTimePicker");
					var tpkEndTime = $("#tpkEndTime").data("kendoTimePicker");
					
					var bt=YMD.createNew(m_ymd.ymd,false).getDate();
					bt.setHours(8,0,0,0);
					
					var et=YMD.createNew(m_ymd.ymd,false).getDate();
					et.setHours(22,0,0,0);
					
					tpkBeginTime.value(bt);
					tpkEndTime.value(et);
					
					$('#chkDayType').val(false);
					
					var winShift= $("#shiftWindow").data("kendoWindow");
					winShift.title("添加班次");
					winShift.open();

					m_shift.targetRow = row;
					m_shift.editType = "new";
				} else {
					$("body").popjs({"title":"提示","content":"只能勤务类型下面定义班次!"}); 
				}
			}
		},
		/**
		 * 设置班次
		 */
		setShift:function(){
			var tv = $("#dutyItemTV").data("kendoTreeView");
			var row=tv.dataItem(tv.select());
			if (row == null) {
				$("body").popjs({"title":"提示","content":"请选择班次!"}); 
			} else if (row.itemTypeId != 101) {
				$("body").popjs({"title":"提示","content":"请选择班次所在行!"}); 
			} else {
				$('#txtShiftName').val(row.name);
				
				$('#tpkBeginTime').data("kendoTimePicker").value(row.beginTime2);
				$('#tpkEndTime').data("kendoTimePicker").value(row.endTime2);

				if (row.beginTime2.dateDiffOfDay(row.endTime2) == 1) {
					$('#chkDayType').prop('checked', true); 
				} else {
					$('#chkDayType').prop('checked', false); 
				}
				m_shift.targetRow = row;
				m_shift.editType = "edit";
				
				var winShift= $("#shiftWindow").data("kendoWindow");
				winShift.title("编辑班次");
				winShift.open();
			}
		},
		addUserNode:function() {
			//var row = $("#tdDuty").treegrid("getSelected");
			var tv = $("#dutyItemTV").data("kendoTreeView");
			var row=tv.dataItem(tv.select());
			if (row == null) {
				//$.messager.alert('提示', "请选择父节点", "warning");
				$("body").popjs({"title":"提示","content":"请选择父节点"}); 
			} else {
				if (dutyItemRelate.check(row.itemTypeId, 999)) {
					$('#txtUserNodeName').val('');
					m_userNode.targetRow = row;
					m_userNode.editType = "new";
					//$('#userNodeWindow').window('open');
					var winUserNode= $("#userNodeWindow").data("kendoWindow");
					winUserNode.open();
				} else {
				$("body").popjs({"title":"提示","content":"只能在班次，车辆下面自定义节点!"}); 
					//$.messager.alert('提示', "只能在班次，车辆下面自定义节点!", "warning");
				}
			}
		},
		setUserNode:function() {
			//var row = $("#tdDuty").treegrid("getSelected");
			var tv = $("#dutyItemTV").data("kendoTreeView");
			var row=tv.dataItem(tv.select());
			if (row == null) {
				//$.messager.alert('提示', "请选择编组!", "warning");
				$("body").popjs({"title":"提示","content":"请选择编组"}); 
			} else if (row.itemTypeId != 999) {
				//$.messager.alert('提示', "请选择编组所在行!", "warning");
				$("body").popjs({"title":"提示","content":"请选择编组所在行!"}); 
			} else {
				$('#txtUserNodeName').val(row.name);
				m_userNode.targetRow = row;
				m_userNode.editType = "edit";
				//$('#userNodeWindows').window('open');
				var winUserNode= $("#userNodeWindow").data("kendoWindow");
				winUserNode.open();
			}
		},
		/**
		 * 显示关联任务对话框
		 */
		setTask:function(){
			var tv = $("#dutyItemTV").data("kendoTreeView");
			var row=tv.dataItem(tv.select());
			
			if (row != null) {
				if (row.itemTypeId == 2) {
					var dutyTypeRow = DutyItemManage.findDutyTypeRow(row);
					var taskType = dutyTypeRow.taskType;

					if (taskType > 0) {
						DutyItemManage.loadTaskTarget(taskType,row.itemId,row.id);
						$('#lblPoliceInfo').text("警员："+row.name + " 的关联任务");
						m_target = row; 
						m_target.taskType = taskType;
						 var winTask =$('#taskWindow');
						 winTask.kendoWindow({
					                        width: "600px", 
					                        height:"530px",
					                        title: "警员关联任务",
					            	        position:{
					            	        	top:'25%',
					            	        	left:'40%'
					            	        }
					                    });
						 winTask.data("kendoWindow").open();  
					} else {
						$("body").popjs({"title":"提示","content":"当前勤务类型没有关联任务!"}); 
					}
				} else {
					$("body").popjs({"title":"提示","content":"只能在警员上设置关联任务!!"}); 
				}
			} else {
				$("body").popjs({"title":"提示","content":"请选择操作数据，只能在警员上设置关联任务"}); 
			}
		},
		loadTaskTarget:function(taskType,itemid,did) {
			var pars = {
				'orgId' : m_dutyprepare_Org.id,
				'taskType' : taskType,
				'policeId' : itemid,
				'dutyId' : did
			};

			$.ajax({
				url:"/BPHCenter/dutyWeb/loadTaskTargetByOrg.do?sessionId="+sessionId,
				type : "POST",
				dataType : "json",
				data : pars,
				async : false,
				success : function(req) {
					if(req.code == 200){
						if(req.data != null){
							
							var ds = new kendo.data.DataSource({
								data:req.data,
								  schema: {
									    model: { id: "targetId" }
									  }
							});
							$("#taskGrid").empty();
							//$("taskGridBox").append("<div id='taskGrid'></div>");
							
							$('#pol_taskType').val(taskType);
							switch(taskType){
							case 2:
								$("#taskGrid").kendoGrid(	{
									dataSource:ds,   
									sortable : true,
									selectable : "multiple", 
									columns : [{
											title : "社区名称",
											template: "<input type='checkbox' id='tk_ck_#: targetId #' value='#: targetId #' /> #: areaName #  "
									 
											 
										} ]
								}); 
								break;
							case 1:
								$("#taskGrid").kendoGrid(	{ 
									dataSource:ds,   
									sortable : true,  
									selectable : "multiple", 
									columns : [{
											title : "名称", 
											field: "areaName"
										},{
											title:"必达点",
											field : "name"
										},{
											title : "经过次数",
											field : "count"
										},{
											title : "停留时间(分钟)",
											field : "stayTime"
										} ],
									editable: true
								}); 
								break;
							case 3:
								$("#taskGrid").kendoGrid(	{  
									dataSource:ds, 
									sortable : true,
									selectable : "multiple", 
									columns : [{
											title : "点位名称",
											template: "<input type='checkbox' id='tk_ck_#: targetId #' value='#: targetId #' /> #: areaName #  "
												  
										}]
								}); 
								break;
							}
							
							$.each(req.data,function(index,dobj){
								if(dobj.isSelected){
									$("#tk_ck_"+dobj.targetId).attr("checked","checked");
								}
								
							});
							
						}
					}else{
						$("body").popjs({"title":"提示","content":"获取关联任务数据信息失败"}); 
					}
				}
			});
		},
		onShiftConfirm:function(){
			var tpkBeginTime = $("#tpkBeginTime").data("kendoTimePicker");
			var tpkEndTime = $("#tpkEndTime").data("kendoTimePicker");
			var name = $('#txtShiftName').val();
			
			var bt=tpkBeginTime.value();
			var et=tpkEndTime.value(); 
			if(bt.dateDiffOfDay(et) == 1){ 
				if (!$('#chkDayType').prop('checked')) {  
					et.add('d',-1);
				}
			}else{
				if ($('#chkDayType').prop('checked')) {  
					et.add('d',1);
				}
			}
			var tv=$("#dutyItemTV").data("kendoTreeView");
					
			if (!verifyTime(bt, et)) {
				$("body").popjs({"title":"提示","content":"结束时间不能小于开始时间"}); 
				return;
			} else if (name == null || name.length == 0) {
				$("body").popjs({"title":"提示","content":"请输入班次名称!"}); 
			} else {
				if (m_shift.editType == 'new') {
					var row = {};
					row.beginTime2 = bt;
					row.endTime2 = et;

					DutyItemManage.genDutyRow(0, name, 101, 0, '班次', row);					
					tv.append(row,  tv.findByUid(m_shift.targetRow.uid));
				} else {
					m_shift.targetRow.name = name;
					m_shift.targetRow.displayName = name;
					m_shift.targetRow.itemInnerTypeName = name;
					m_shift.targetRow.beginTime2 = bt;
					m_shift.targetRow.endTime2 = et;
				}
				DutyItemManage.reCalcDuty();
				var winShift= $("#shiftWindow").data("kendoWindow");
				winShift.close();
			}
			function verifyTime(b, e) {
				if (b.dateDiff('n', e) < 0) {
					return false;
				} else {
					return true;
				}
			}
		},
		onUserNodeConfirm:function(){
			var name = $('#txtUserNodeName').val();
			
			var tv=$("#dutyItemTV").data("kendoTreeView");
			
			if (name == '') {
				//$.messager.alert('提示', "请输入编组名称", "warning");
				$("body").popjs({"title":"提示","content":"请输入编组名称"}); 
			} else {
				if (m_userNode.editType == 'new') {
					var row = {};
					DutyItemManage.genDutyRow(0, name, 999, 0, '自定义编组', row);
					
					tv.append(row,  tv.findByUid(m_userNode.targetRow.uid));
					
				} else {
					m_userNode.targetRow.name = name;
					m_userNode.targetRow.displayName = name;
				}
				DutyItemManage.reCalcDuty();
				//$('#userNodeWindows').window('close');
				var winUserNode= $("#userNodeWindow").data("kendoWindow");
				winUserNode.close();
			}	
		},
		onTaskConfirm:function(){   
			DutyItemManage.getTaskList(); 
			var winpolicetask= $("#taskWindow").data("kendoWindow");
			winpolicetask.close();
		},
		getTaskList:function(){
			m_target.targets =[];  
			var grid = $("#taskGrid").data("kendoGrid");
			if(m_target.taskType == 1 || m_target.taskType == "1"){
				for(var i = 0;i< grid._data.length ;i++){  
					if(grid._data[i].dirty)
					{
						var pt = {};
						pt.dutyId = m_target.dutyId;
						pt.dutyItemId = m_target.id;
						pt.policeId = m_target.itemId;
						pt.taskTypeId = m_target.taskType;
						pt.targetId = grid._data[i].pointId;
						if(grid._data[i].count != undefined){
							if($.trim(grid._data[i].count) != ""){
								pt.count = $.trim(grid._data[i].count);
							}else{
								pt.count = 0;
							}
						}else{
							pt.count = 0;
						}
						if(grid._data[i].stayTime != undefined){
							if($.trim(grid._data[i].stayTime) != ""){
								pt.stayTime = $.trim(grid._data[i].stayTime);
							}else{
								pt.stayTime = 0;
							}
						}else{
							pt.stayTime = 0;
						} 
						m_target.targets.push(pt);  
					}
					 
				} 
			}else{ 
				var t = $("#taskGrid input:checkbox:checked").length;
				if(t>0){
					var taskObj = $("#taskGrid input:checkbox:checked");
					$.each(taskObj, function(index, tobj){
						var pt = {};
						pt.dutyId = m_target.dutyId;
						pt.dutyItemId = m_target.id;
						pt.policeId = m_target.itemId;
						pt.taskTypeId = m_target.taskType;
						pt.targetId = tobj.value; 
						pt.count = null; 
						pt.stayTime = null; 
						m_target.targets.push(pt);
					});
					 
				}else{
					$("body").popjs({"title":"提示","content":"未选择相关关联任务点"}); 
				}
			}
		},
		onDeleteNode:function(uid){
			var tv = $("#dutyItemTV").data("kendoTreeView");
			var re=null;
			
			if(uid==undefined){
				re=tv.select();
				if(re ==null||re.length==0){
					$("body").popjs({"title":"提示","content":"请选择要删除节点"}); 
					return;
				}
			}else{
				re=tv.findByUid(uid);
			}
			var row=tv.dataItem(re);
			
			if(row.items !=null && row.items.length>0){
				$("body").tyWindow({content:"确定要删除[ " + row.name + " ]及下级所有节点?",center:true,ok:true,no:true,
					okCallback:function(){ 
						tv.remove(re);
						DutyItemManage.reCalcDuty();
				}});	
			}else{
				tv.remove(re);
				DutyItemManage.reCalcDuty();
			}
		},
		rebuildItems:function(items){
			var items2=[];
			
			$.each(items, function(i, v) {
				var item =rebuildItem(v,null);
				items2.push(item);
			});
			
			return items2;
			
			function rebuildItem(v,p){
				var v2={};
				v2.id=v.id;
				v2.xid =v.xid;
				v2.name = v.name;
				v2.itemTypeId = v.itemTypeId;
				v2.itemId =v.itemId;
				v2.itemInnerTypeId = v.itemInnerTypeId;
				v2.itemInnerTypeName =v.itemInnerTypeName;
				
				v2.itemTypeName =v.displayName;
				v2.beginTime2=v.beginTime2;
				v2.endTime2=v.endTime2;
				
				v2.beginTime=v.beginTime;
				v2.endTime=v.endTime;
				
				v2.maxPolice = v.maxPolice;
				v2.taskType = v.taskType;
				v2.targets = v.targets;

				v2.getParent = function() {
					return p;
				};
				
				v2.xid =DutyItemManage.genXId(v2.itemTypeId); //为什么要重新设置xid? 

				if (v2.itemTypeId == 101) {
					DutyItemManage.initDate(v2);
					if (v2.beginTime2.dateDiffOfDay(v2.endTime2) == 1) {
						v2.isOverDay=true;
					} else {
						v2.isOverDay=false;
					}
				}

				//itemiconCls = createIconStyle(item, item.itemTypeId, item.iconUrl);
				
				/* 初始化数量等于0 */
				v2.velicleCount = 0;
				v2.policeCount = 0;
				v2.weaponCount = 0;
				v2.gpsCount = 0;
				
				switch (v2.itemTypeId) {
				case 1:
					v2.velicleCount = 1;
					break;
				case 2:
					v2.policeCount = 1;
					break;
				case 3:
					v2.weaponCount = 1;
					break;
				case 4:
					v2.gpsCount = 1;
					break;
				}

				if(v.items != null && v.items.length>0){
					v2.items=[];
					$.each(v.items, function(i, val) {
						var nv=rebuildItem(val,v2);
						v2.velicleCount += nv.velicleCount;
						v2.policeCount += nv.policeCount;
						v2.weaponCount += nv.weaponCount;
						v2.gpsCount += nv.gpsCount;
						v2.items.push(nv);
						v2.expanded =true;
					});
				}
				
				v2.displayName = DutyItemManage.genDisplayName(v2);
				return v2;
			}
			
		},
		/**
		 * 克隆一个items,
		 * SB kendo ui
		 * @param items
		 */
		rebuildItems2:function(v,p){
			var items2=[];
			
			$.each(items, function(i, v) {
				var v2={};
				v2.id=v.id;
				v2.xid =v.xid;
				v2.name = v.name;
				v2.itemTypeId = v.itemTypeId;
				v2.itemId =v.itemId;
				v2.itemInnerTypeId = v.itemInnerTypeId;
				v2.itemInnerTypeName =v.itemInnerTypeName;
				
				v2.itemTypeName =v.displayName;
				v2.beginTime2=v.beginTime2;
				v2.endTime2=v.endTime2;
				
				v2.beginTime=v.beginTime;
				v2.endTime=v.endTime;
				
				v2.maxPolice = v.maxPolice;
				v2.taskType = v.taskType;
				v2.targets = v.targets;

				v2.getParent = function() {
					return parent;
				};
				
				
				
				v2.xid =DutyItemManage.genXId(v2.itemTypeId); //为什么要重新设置xid? 

				if (v2.itemTypeId == 101) {
					DutyItemManage.initDate(v2);
					if (v2.beginTime2.dateDiffOfDay(v2.endTime2) == 1) {
						v2.isOverDay=true;
					} else {
						v2.isOverDay=false;
					}
				}

				//itemiconCls = createIconStyle(item, item.itemTypeId, item.iconUrl);
				
				/* 初始化数量等于0 */
				v2.velicleCount = 0;
				v2.policeCount = 0;
				v2.weaponCount = 0;
				v2.gpsCount = 0;
				
				switch (v2.itemTypeId) {
				case 1:
					v2.velicleCount = 1;
					break;
				case 2:
					v2.policeCount = 1;
					break;
				case 3:
					v2.weaponCount = 1;
					break;
				case 4:
					v2.gpsCount = 1;
					break;
				}

				if(v.items != null && v.items.length>0){
					v2.items=DutyItemManage.rebuildItems(v.items,v);
					v2.expanded =v.expanded;
				}
				
				if(parent !=undefined && parent != null){
					parent.velicleCount += v2.velicleCount;
					parent.policeCount += v2.policeCount;
					parent.weaponCount += v2.weaponCount;
					parent.gpsCount += v2.gpsCount;
				}
				//displayName 的生成必选放在最后! 
				v2.displayName = DutyItemManage.genDisplayName(v2);
				items2.push(v2);
			});
			return items2;
		},
		clearId:function(duty) {
			duty.id = 0;
			if (duty.items != null) {
				$.each(duty.items, function(i, v) {
					DutyItemManage.clearItemId(v);
				});
			}
		},
		clearItemId:function(item) {
			item.id = 0;
			if (item.targets != null) {
				$.each(item.targets, function(i, v) {
					v.id = 0;
				});
			}
			if (item.children != null) {
				$.each(item.items, function(i2, v2) {
					DutyItemManage.clearItemId(v2);
				});
			}
		},
		onDutyItemDrop:function(e){
			var point=e.dropPosition;
			var sRow=$("#dutyItemTV").data("kendoTreeView").dataItem(e.sourceNode);
			var tRow=$("#dutyItemTV").data("kendoTreeView").dataItem(e.destinationNode );
			
			if(tRow==null || sRow == null){
				e.setValid(false);
			}
			
			if(DutyItemManage.checkDrop(tRow,sRow,point)){
				DutyItemManage.doDrop(tRow, sRow, point);
			}else{
				e.setValid(false);
			}
		},
		onDutyItemDragEnd:function(e){
			DutyItemManage.reCalcDuty();
		}
};

/*
 * 
 * 
 */
//日期格式化
var YMD = {
		createNew : function(ymd,isAddNewShift) {
			var _ymd = {};
			_ymd.ymd = ymd;

			var yearStr = ymd.substr(0, 4);
			var monthStr = ymd.substr(4, 2);
			var dayStr = ymd.substr(6, 2);
		
			var year = Number(yearStr);
			var month = Number(monthStr);
			if(!isAddNewShift){ 
				month = month-1;
			}
			var day = Number(dayStr);

			_ymd.getYear = function() {
				return year;
			};
			_ymd.getMonth = function() {
				return month;
			};
			_ymd.getDay = function() {
				return day;
			};

			var date=new Date();
			date.setFullYear(year, month, day);
			
			_ymd.getDate=function(){
				return date;
			};
			
			_ymd.format = function() {
				return yearStr + '-' + monthStr + '-' + dayStr;
			};
			return _ymd;
		}
	};
//类型关联规则
var dutyItemRelate = {
		root : {
			children : [ 100 /* 勤务类型 */
			]
		},
		dutytype : {
			children : [ 101 /* 班次 */
			]
		},
		shift : {
			children : [ 1, 2, 999 ]
		},

		vehicle : {
			children : [ 2, 4, 999 ]
		},
		police : {
			children : [ 3, 4 ]
		},
		weapon : {
			children : []
		},
		gps : {
			children : []
		},
		usernode : {
			children : [ 1, 2 ]
		},
		/**
		 * 检查拖动是否符合规则
		 * 
		 * @param parenttype
		 * @param childtype
		 * @returns {Boolean}
		 */
		check : function(parenttype, childtype) {
			var p = null;
			switch (parenttype) {
			case 0:
				p = this.root;
			case 100:
				p = this.dutytype;
				break;
			case 101:
				p = this.shift;
				break;
			case 999:
				p = this.usernode;
				break;
			case 1:
				p = this.vehicle;
				break;
			case 2:
				p = this.police;
				break;
			case 3:
				p = this.weapon;
				break;
			case 4:
				p = this.gps;
				break;
			}

			if ($.inArray(childtype, p.children) >= 0) {
				return true;
			} else {
				return false;
			}
		}
	};
