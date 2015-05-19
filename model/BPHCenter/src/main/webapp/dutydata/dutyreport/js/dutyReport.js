	var sessionId = $("#token").val(); 
	
	$(function() {
		reportManage.initCtl();
		reportManage.loadBaseData();
	});
	
	function loadData(){
		reportManage.loadReport();
		
	}
	
	var reportManage={
		initCtl:function(){
			var bd=new Date();
			var ed =new Date();
			
			$("#dteBeginDate").kendoDatePicker({
    			value: bd
			});
			bd.setHours(0, 0);
			ed.setHours(23,30);
			$("#spnBeginTime").kendoTimePicker();
			var spnBeginTime = $("#spnBeginTime").data("kendoTimePicker");
			spnBeginTime.value(bd);
			
			$("#spnEndTime").kendoTimePicker();
			var spnEndTime = $("#spnEndTime").data("kendoTimePicker");
			spnEndTime.value(ed);
			
			$('#tlSReport').kendoTreeList({
				columns:[
					{title : '机构树',	field : 'orgDescription',align : 'left',width : 120,expandable:true},
					{title : '值班领导',	field : 'leaderCount',align : 'left',width : 80},
					{title : '值班主任',	field : 'directorCount',align : 'left',width : 80},
					{title : '警力',	field : 'policeCount',align : 'right',width : 80},
					{title : '警车',	field : 'vehicleCount',align : 'right',width : 80},
					{title : '枪支',	field : 'weaponCount',align : 'right',width : 80},
					{title : '社区',	field : 'communityCount',align : 'right',width : 80},
					{title : '巡区',	field : 'patrolAreaCount',align : 'right',width : 80},
					{title : '卡点',	field : 'bayonetCount',align : 'right',width : 80}
				]
			});
			
			$('#tlReport').kendoTreeList({
				columns:[
					{title : '机构树',	field : 'orgShortName',align : 'left',width : 120,expandable:true},
					{title : '值班领导',	field : 'leaderNames',align : 'left',width : 80},
					{title : '值班主任',	field : 'directorName',align : 'left',width : 80},
					{title : '警力',	field : 'policeCount',align : 'right',width : 80},
					{title : '警车',	field : 'vehicleCount',align : 'right',width : 80},
					{title : '枪支',	field : 'weaponCount',align : 'right',width : 80},
					{title : '社区',	field : 'communityCount',align : 'right',width : 80},
					{title : '巡区',	field : 'patrolAreaCount',align : 'right',width : 80},
					{title : '卡点',	field : 'bayonetCount',align : 'right',width : 80}
				]
			});
			
		},
		loadBaseData :function(){
			//var dutyProperty=$("#dutyProperty").data("kendoComboBox");
			$.ajax({
				url : "/BPHCenter/dutyWeb/getdutyProperty.do?sessionId="+sessionId,
				type : "POST",
				dataType : "json",
				// async : false,
				success : function(req) {
					var ds=new  kendo.data.DataSource({
						data:req
					});
					
					$("#dutyProperty").kendoMultiSelect({
						dataSource : ds,
						dataTextField: "name",
		  				dataValueField: "id"
		             });
				}
			});
		
			$.ajax({
				url : "/BPHCenter/policeWeb/getPoliceType.do?sessionId="+sessionId,
				type : "POST",
				dataType : "json",
				// async : false,
				success : function(req) {
					var ds=new  kendo.data.DataSource({
						data:req
					});
					
					$("#cmbpoliceType").kendoMultiSelect({
						dataSource : ds,
						dataTextField: "name",
		  				dataValueField: "id"
		             });
				}
			});
			$.ajax({
				url : "/BPHCenter/dutyTypeWeb/getDutyTypelist.do?sessionId="+sessionId,
				type : "POST",
				dataType : "json",
				// async : false,
				success : function(req) {
					var ds=new  kendo.data.DataSource({
						data:req.data
					});
					
					$("#cmbdutytype").kendoComboBox({
						dataTextField: "name",
		  				dataValueField: "id",
		  				dataSource : ds
		             });
				}
			});
		},
		
		packCriteria :function (){
			var criteria = {};
			var beginDate = $("#dteBeginDate").data("kendoDatePicker").value();
			var endDate = $("#dteBeginDate").data("kendoDatePicker").value();
			
			var t1=$('#spnBeginTime').data("kendoTimePicker").value();
			var t2=$('#spnEndTime').data("kendoTimePicker").value();
			
			criteria.beginTime2 = beginDate;
			
			//t1.setFullYear(beginDate.getFullYear(),beginDate.getMonth(),beginDate.getDate);
			//t2.setFullYear(beginDate.getFullYear(),beginDate.getMonth(),beginDate.getDate);
			
			//beginDate.setTime(t1.getTime());
			//endDate.setTime(t2.getTime());
		
			criteria.beginTime=beginDate.getFullYear()+ "-"
								+ ((beginDate.getMonth() + 1) > 10 ? (beginDate.getMonth() + 1) : "0" + (beginDate.getMonth() + 1))+ "-" 
								+ (beginDate.getDate() < 10 ? "0" + beginDate.getDate() : beginDate.getDate()) + " "
								+ (t1.getHours() < 10 ? "0" + t1.getHours() : t1.getHours())+":"
								+ (t1.getMinutes() < 10 ? "0" + t1.getMinutes() : t1.getMinutes())+":00";
			criteria.endTime=endDate.getFullYear()+ "-"
								+ ((endDate.getMonth() + 1) > 10 ? (endDate.getMonth() + 1) : "0" + (endDate.getMonth() + 1))+ "-" 
								+ (endDate.getDate() < 10 ? "0" + endDate.getDate() : endDate.getDate()) + " "
								+ (t2.getHours() < 10 ? "0" + t2.getHours() : t2.getHours())+":"
								+ (t2.getMinutes() < 10 ? "0" + t2.getMinutes() : t2.getMinutes())+":00";
		
			criteria.ymd = criteria.beginTime2.toYMD();
		
			criteria.taskPropertyIds = [];
			var dutyProperty=$("#dutyProperty").data("kendoMultiSelect");
			var ps=dutyProperty.dataItems();
			$.each(ps, function(i, v) {
				if (v.id != -1 || v.id != "-1") {
					criteria.taskPropertyIds.push(v.id);
				}
			});
			
			//criteria.taskPropertyIds.push($("#dutyProperty").data("kendoMultiSelect").value());
			criteria.policeTypeIds = [];
			var cmbPoliceType=$("#cmbpoliceType").data("kendoMultiSelect");
			var pts = cmbPoliceType.dataItems();

			$.each(pts, function(i, v) {
				if (v.id == -1 || v.id == "-1") {
					criteria.policeTypeIds.length=0;/*如果包括 -1 就表示选择红 “全部”.*/
				}else{
					criteria.policeTypeIds.push(v.id);
				}
			});

			
			criteria.dutyTypeIds = [];
			var dutyTypeId=$("#cmbdutytype").data("kendoComboBox").value();
			if(dutyTypeId !=undefined && dutyTypeId !=null && dutyTypeId>0){
				criteria.dutyTypeIds.push(dutyTypeId);
			}
			//($("#cmbdutytype").data("kendoComboBox").value());
			
			criteria.attireTypeIds = [];
			if ($("#ckAttireType1").prop('checked')) {
				criteria.attireTypeIds.push(0);
			}
			if ($("#ckAttireType2").prop('checked')) {
				criteria.attireTypeIds.push(1);
			}
			
			
			criteria.armamentTypeIds = [];
			if ($("#ckArmamentType1").prop('checked')) {
				criteria.armamentTypeIds.push(0);
			}
			if ($("#ckArmamentType2").prop('checked')) {
				criteria.armamentTypeIds.push(1);
			}
			
			criteria.orgIds=[];
			var s=$("#checkedOrgIds").val();
			criteria.orgIds=s.split(',');
			

			//criteria.orgIds.push($("#organId").val());

			return criteria;

		},
		/*
		查询
		*/
		search:function(){
			this.loadReport();
		},
		loadReport : function(){
			var criteria = this.packCriteria();

			var query = JSON.stringify(criteria);

			$.ajax({
				url : "/BPHCenter/dutyReportWeb/loadDutyReport.do?sessionId="+sessionId,
				type : "POST",
				dataType : "json",
				data : {
					criteria : query
				},
				// async : false,
				success : function(req) {
					if (req.code == 200) {// 成功填充数据
						 var ds = new kendo.data.TreeListDataSource({
						 	data:req.data,
						 	schema: { 
	                            model: {
	                                id: "id",
	                                expanded: true
	                            }
	                        }
						 });
						
						//var ss = buildReportTree(data);
//						var tlRepport=$('#tlReport').data("kendoTreeList");
//						tlRepport.setDataSource(ds);
						$('#tlSReport').empty();
						$('#tlReport').empty();
						
						var result=reportManage.fmtReportData(req.data);
						
						var sds= new kendo.data.TreeListDataSource({
						 	data:result.sum,
						 	schema: { 
	                            model: {
	                                id: "id",
	                                expanded:true
	                            }
	                        }
						});
						
						var ds= new kendo.data.TreeListDataSource({
						 	data:result.list,
						 	schema: { 
	                            model: {
	                                id: "id",
	                                expanded:true
	                            }
	                        }
						});
						
						$('#tlSReport').kendoTreeList({
							dataSource:sds,
							columns:[
								{title : '机构树',	field : 'orgDescription',align : 'left',width : 120},
								{title : '值班领导',	field : 'leaderCount',align : 'left',width : 80},
								{title : '值班主任',	field : 'directorCount',align : 'left',width : 80},
								{title : '警力',	field : 'policeCount',align : 'right',width : 80},
								{title : '警车',	field : 'vehicleCount',align : 'right',width : 80},
								{title : '枪支',	field : 'weaponCount',align : 'right',width : 80},
								{title : '社区',	field : 'communityCount',align : 'right',width : 80},
								{title : '巡区',	field : 'patrolAreaCount',align : 'right',width : 80},
								{title : '卡点',	field : 'bayonetCount',align : 'right',width : 80}
							]
						});
						
						$('#tlReport').kendoTreeList({
							dataSource:ds,
							columns:[
								{title : '机构树',	field : 'orgShortName',align : 'left',width : 120,expandable:true},
								{title : '值班领导',	field : 'leaderNames',align : 'left',width : 80},
								{title : '值班主任',	field : 'directorName',align : 'left',width : 80},
								{title : '警力',	field : 'policeCount',align : 'right',width : 80},
								{title : '警车',	field : 'vehicleCount',align : 'right',width : 80},
								{title : '枪支',	field : 'weaponCount',align : 'right',width : 80},
								{title : '社区',	field : 'communityCount',align : 'right',width : 80},
								{title : '巡区',	field : 'patrolAreaCount',align : 'right',width : 80},
								{title : '卡点',	field : 'bayonetCount',align : 'right',width : 80}
							]
						});
						
					} else {
						alert("获取数据失败");
					}
				}
			});
		},
		/*格式化返回数据*/
		fmtReportData:function(data){
			var ls=[];
			var sum_ls=[];
			var sumObj={};
			
			sumObj = {};
			sumObj.id=0;
			sumObj.parentId=null;
			sumObj.orgCount1 = data.length;
			sumObj.orgCount2 = 0;
			sumObj.leaderCount = 0;
			sumObj.directorCount = 0;
			sumObj.policeCount = 0;
			sumObj.vehicleCount = 0;
			sumObj.weaponCount = 0;
			sumObj.gpsCount = 0;
			sumObj.communityCount = 0;
			sumObj.patrolAreaCount = 0;
			sumObj.bayonetCount = 0;
			
			var ids={};
			
			$.each(data, function(i, v) {
				
				if (v.policeCount > 0) {
					sumObj.orgCount2++;
				}
				
				sumObj.leaderCount += v.leaderCount;
				sumObj.directorCount += v.directorCount;
				sumObj.policeCount += v.policeCount;
				sumObj.vehicleCount += v.vehicleCount;
				sumObj.weaponCount += v.weaponCount;
				sumObj.gpsCount += v.gpsCount;
				sumObj.communityCount += v.communityCount;
				sumObj.patrolAreaCount += v.patrolAreaCount;
				sumObj.bayonetCount += v.bayonetCount;
				
				ids['key'+v.id] = v.id;
			});
			
			sumObj.orgDescription=sumObj.orgCount2 + "/" + sumObj.orgCount1;
			
			$.each(data, function(i, v) {
				if(ids['key'+v.parentId]==undefined){
					v.parentId=null;
				}
			});
			
			var result={};
			result.sum=[];
			result.sum.push(sumObj);
			result.list=data;
			
			return result;
		}
	};