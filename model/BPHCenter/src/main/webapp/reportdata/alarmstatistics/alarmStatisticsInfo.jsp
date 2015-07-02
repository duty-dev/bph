<%@ page language="java" pageEncoding="UTF-8"%>
<script src="http://cdn.kendostatic.com/2015.1.429/js/jszip.min.js"></script>
<script type="text/javascript"> 
	var chartManage = {
		GetSerieOfAlarmType : function(data, xlabel, name) {
			//初始化serie
			var serie = {};
			serie.name = name;
			serie.data = [];
			//初始化serie的数组长度和初始值赋为0
			serie.data.length = xlabel.length;
			$.each(serie.data,function(index,item){
				serie.data[index] = 0;
			});
			//从data获取响应值赋予serie对象内的数组
			if (data!=undefined&&data.data !=undefined) {
				$.each(data.data, function(index, item) {
				var alarmNum = $.inArray(item.typeCode,m_Query_pkg.caseType);
				serie.data[alarmNum] = item.amount;
				});
			}
			return serie;
		},
		
		GetSerieOfPeroid : function(data, xlabel, name) {
			//初始化serie
			var serie = {};
			serie.name = name;
			serie.data = [];
			//初始化serie的数组长度和初始值赋为0
			serie.data.length = xlabel.length;
			$.each(serie.data,function(index,item){
				serie.data[index] = 0;
			});
			if(data.data ==undefined||data ==undefined)
			{
				return serie;
			}
			//存在多个重复
			//如何判断遍历的这个数据是重复的前面这个数据还是后面？月：就是跨年了；日的话：就是跨月了
			var sameTime = []; 
			var count = 0;//这里会存在翻倍的情况，但是不会影响程序的正常执行
			//检查是否存在跨年或者跨月
			$.each(xlabel,function(index,item){
				$.each(xlabel,function(index1,item1){
					if(index == index1)
						return true;
					if(item ==item1){
						sameTime[count++] = item;
						return false;
					}
				});
			});
			
			if(count ==0){//不存在			
				//获取数据
				$.each(data.data,function(index,item){
					
					var xlabelIndex = $.inArray(item.period,xlabel);
					//var xlabelIndex = xlabel.indexOf(item.period);
					serie.data[xlabelIndex] = item.amount;
				});
			}else{//存在
				$.each(data.data,function(index,item){
					var itemIndex = $.inArray(item.period,sameTime);
					//var itemIndex = sameTime.indexOf(item.period);
					
					if(itemIndex ==-1){	
						var xlabelIndex = $.inArray(item.period,xlabel);
						//var xlabelIndex = xlabel.indexOf(item.period);
						serie.data[xlabelIndex] = item.amount;
					}else{
						if(m_Query_pkg.periodType ==1){
						//按天
							if(item.months == (parseInt(data.beginYmd/100)%100)){
								var xlabelIndex = $.inArray(item.period,xlabel);
								//var xlabelIndex = xlabel.indexOf(item.period);
								serie.data[xlabelIndex] = item.amount;
							}else{
								var xlabelIndex = $.inArray(item.period,xlabel);
								//var xlabelIndex = xlabel.indexOf(item.period);
								//var xlabelIndex = xlabel.lastIndexOf(item.period);
								serie.data[xlabelIndex] = item.amount;
							}
						}else if(m_Query_pkg.periodType ==3){
						//按月
							//var xlabelIndex = xlabel.indexOf(item.period);
							var xlabelIndex = $.inArray(item.period,xlabel); 
							if(item.years ==parseInt(data.beginYmd/10000)){
								serie.data[xlabelIndex] = item.amount;
							}else{
								serie.data[xlabelIndex+12] = item.amount;
							}
						}
						
					}
				});
			}
			return serie;
		},
		GetSerieOfOrgan:function(rows,alarmTypeName,xlabel){
			var seriesArray = [];
			var types = {};

			//根据data获取相应数据
			var orgPaths= {};
			$.each(xlabel,function(index,item){
				$.each(rows,function(index1,item1){
					if (item == item1.orgName) {
						orgPaths[item] = item1.orgPath;
					}
				});
			});
			$.each(m_Query_pkg.caseType, function(index, item) {
				var t = {};
				t.name = item;
				t.data = [];
				t.data.length = xlabel.length;
				//初始化数组
				$.each(t.data,function(index1,item1){
					t.data[index1] = 0;
				});
				types[item] = t;
			});
			//从rows中获取想要的数据
			$.each(rows, function(index, item) {
				if (item.typeName == undefined) {
					return true;
				}

				//判断当前机构是否为界面显示的机构
				var mark = 0;//0表示是，1不是
				$.each(xlabel,function(count,itemOfOrgan){
					if (item.orgName==itemOfOrgan) {
						mark = 0;
						return false;
					}
					if (count ==(xlabel.length-1)) {
						mark = 1;
					}
				});

				var orgName = "";
				if(mark==0){
					orgName = item.orgName;
				}else{
					var parentOrgName = FunctionManage.FindParentOrgName(item.orgPath,orgPaths);
					if (parentOrgName !="none") {
						orgName = parentOrgName;
					}else{
						//可以不处理
					}
				}
				var t = types[item.typeCode];
				//var orgIndex = XLabel.indexOf(item.orgName);
				var orgIndex = $.inArray(orgName,xlabel); 
				if(item.amount ==undefined){
					t.data[orgIndex] += 0;	
				}else{
					t.data[orgIndex] += item.amount;
				}
			});

			$.each(types, function(index, item) {
				seriesArray.push(item);
			});
			return seriesArray;
		},
		SetChartWidth:function(len){
			if(len>8){
				$("#jqtj").css("width",900+(len-8)*120);
			}else{
				$("#jqtj").css("width",900);
			}
		}
			
	}; 
 	var gridManage = {
 		GetAlarmTypeObjectsOfAlarmType:function(data){
 			var alarmTypeObjects = {};
 			if (data ==undefined) {
 				return alarmTypeObjects;
 			}
			$.each(data, function(index, item) {
				if (item==undefined||item.data ==undefined||item.data.length ==0) {
					return true;
				}
				$.each(item.data, function(index1, item1) {
					var alarmObject = {};
					alarmObject.typeName = item1.typeName;
					alarmObject.typeCode = item1.typeCode;
					alarmObject.typeLevel = item1.typeLevel;
					alarmObject.typeParentCode = item1.typeParentCode;
					alarmTypeObjects[item1.typeCode] = alarmObject;
				});
			});
			return alarmTypeObjects;
 		},
 		GetAlarmTypeObjectsOfOrgan:function(data,alarmTypeName){
 			var alarmTypeObjects = {};

			$.each(data, function(index, item) {
				if (item.typeName != undefined&&$.inArray(item.typeCode,m_Query_pkg.caseType)>=0) {
					var alarmTypeObject = {};
					alarmTypeObject.typeName = item.typeName;
					alarmTypeObject.typeCode = item.typeCode;
					alarmTypeObject.typeLevel = item.typeLevel;
					alarmTypeObject.typeParentCode = item.typeParentCode;
					alarmTypeObjects[item.typeCode] = alarmTypeObject;
				}
			});
			return alarmTypeObjects;
 		},
 		GetColumnsOfAlarmType:function(alarmTypeObjects,xlabel){
 			var columnsArray = [];

 			var firstColumn = {
 				field : "tjTime",
				title : " ",
				width:"250px"
 			};
 			columnsArray.push(firstColumn);

			var numOfNotype  = 0;//对那些没有数据，无法获得其typeCode和parentCode的警情类型
			$.each(m_Query_pkg.caseType,function(index,item){
				//子列
				var column = {
					field:item,
					title:xlabel[index]
				};
				//将子列添加到columnsArray
				columnsArray.push(column);
			});
			//添加合计子列
			var totalColumn = {
 				field : "total",
				title : "合计"
 			};
 			columnsArray.push(totalColumn);

 			return columnsArray;
 		},
 		GetColumnsOfOrgan:function(alarmTypeObjects,alarmTypeName){
 			var columnsArray = [];

 			var firstColumn = {
 				field : "alarmType",
				title : "警情分类",
				width:"250px"
 			};
 			columnsArray.push(firstColumn);

			$.each(m_Query_pkg.caseType,function(index,item){
				//子列
				var column = {
						field : item,
						title : alarmTypeName[index]
					};
				//将子列添加到columnsArray
				columnsArray.push(column);
			});
			//添加合计子列
			var totalColumn = {
 				field : "total",
				title : "合计"
 			};
 			columnsArray.push(totalColumn);

 			return columnsArray;
 		},
 		GetTotal:function(typeCode,amount,alarmTypeObjects){
 			if (amount ==0) {
 				return 0;
 			}
 			var returnAmount = 0;
 			//判断typeCode是大类还是小类
 			$.each(alarmTypeObjects,function(index,alarmTypeObjectItem){
 				if (typeCode ==alarmTypeObjectItem.typeCode) {
 					if (alarmTypeObjectItem.typeLevel ==1) {//大类
 						returnAmount = amount;
 						return false;
 					}else{//小类
 						//判断是否存在父类，存在，返回0，不存在，返回amount
 						returnAmount = amount;
 						$.each(alarmTypeObjects,function(index1,alarmTypeObjectItem1){
 							if (alarmTypeObjectItem.typeParentCode ==alarmTypeObjectItem1.typeCode) {//存在
 								returnAmount = 0;
 								return false;
 							}
 						});
 						return false;
 					}
 				}
 				//如果到最后都没有匹配到typeCode的这种情况未处理
 			});
 			return returnAmount;
 		},
 		GetDataSourceOfAlarmType:function(data,columnsArray,alarmTypeObjects,xlabel){
 			var dataSource = [];
 			//添加小计一行
			var totalRow = {};
			totalRow['tjTime']  = "小计";
			//给小计这一行赋初始值
			$.each(columnsArray,function(index,item){
				if (item.field !="tjTime") {	
					totalRow[item.field] = 0;
				}
			});
			$.each(data, function(index, item) {
				//添加行
				var row = {};
				//给新添行赋初始值
				$.each(columnsArray,function(index2,item2){
					if (item2.field !="tjTime") {
						row[item2.field] = 0;
						}
				});	
				//根据data获取相应数据
				$.each(item.data, function(index1, item1) {
					row[item1.typeCode] = item1.amount;
					//对应合计则进行相加统计
					totalRow[item1.typeCode] +=item1.amount;
				});
				//一行进行合计
				
				var total = 0;
				$.each(row,function(indexOfRow,itemOfRow){
					if (indexOfRow =="tjTime") {
						return true;
					}
					//区分大小类
					total+=gridManage.GetTotal(indexOfRow,itemOfRow,alarmTypeObjects);
				});

				row['total'] = total;

				//小计一行中的合计计算
				totalRow['total'] +=total;

				row['tjTime'] = FunctionManage.GetStandardYM(item.beginYmd)
					+ "-" + FunctionManage.GetStandardYM(item.endYmd);
				dataSource.push(row);
			});
				
			dataSource.push(totalRow);
			return dataSource;
 		},
 		GetDataSourceOfOrgan:function(data,columnsArray,alarmTypeObjects,xlabel){
 			var dataSource = [];
 			var dataSourceObj = {};
 			//添加小计一行
			var totalRow = {};
			totalRow['alarmType']  = "小计";
			//给小计这一行赋初始值
			$.each(columnsArray,function(index,item){
				if (item.field !="alarmType") {	
					totalRow[item.field] = 0;
				}
			});
			//dataSourceObj添加元素并且初始化
			$.each(xlabel, function(index, item) {
				//添加行
				var row = {};
				row.alarmType = item;
				//给新添行赋初始值
				$.each(columnsArray,function(indexOfColumns,itemOfColumns){
					if(itemOfColumns.field !="alarmType"){
						row[itemOfColumns.field] = 0;
					}
				});
				dataSourceObj[item] = row;
			});
			//根据data获取相应数据
			var orgPaths= {};
			$.each(xlabel,function(index,item){
				$.each(data,function(index1,item1){
					if (item == item1.orgName&&item1.orgPath!=undefined) {
						orgPaths[item] = item1.orgPath;
						return false;
					}
				});
			});
			
			$.each(data,function(indexOfData,itemOfData){
				if(itemOfData.typeCode ==undefined){
					return true;
				}
				if(itemOfData.amount ==undefined||itemOfData.amount ==0){
					return true;
				}
				if(dataSourceObj[itemOfData.orgName]==undefined){
					//获取它的最终父级机构orgName
					var parentOrgName = FunctionManage.FindParentOrgName(itemOfData.orgPath,orgPaths);
					if (parentOrgName!="none") {
						dataSourceObj[parentOrgName][itemOfData.typeCode] += itemOfData.amount;	
						//小计对应列统计
						totalRow[itemOfData.typeCode]+=itemOfData.amount;	
					}
					
					return true;
				}
				dataSourceObj[itemOfData.orgName][itemOfData.typeCode] += itemOfData.amount;

				//小计对应列统计
				totalRow[itemOfData.typeCode]+=itemOfData.amount;
			});

			//合计统计
			$.each(dataSourceObj,function(index2,dataRow){
				var total = 0;
				$.each(dataRow,function(index3,rowItem){
					if (index3 =="alarmType") {
						return true;
					}
					//区分大小类
					total+=gridManage.GetTotal(index3,rowItem,alarmTypeObjects);
				});
				dataRow["total"] += total;
				totalRow["total"] +=total;
			});
			//将小计添加到dataSourceObj
			dataSourceObj["小计"] = totalRow;

			//将dataSourceObj转化成dataSource
			$.each(dataSourceObj,function(index,item){
				dataSource.push(item);
			});
			
			return dataSource;
 		}
 	};
	var ReportManage = {
		//警情分类
		initAlarmTypeData :  function(data, title, XLabel) {
			//动态缩放chart横坐标的长度
			chartManage.SetChartWidth(XLabel.length);

			var series = [];
			var sameSerie = chartManage.GetSerieOfAlarmType(data[0], XLabel, "无");
			var circleSerie = chartManage.GetSerieOfAlarmType(data[1], XLabel, "同比");
			var serie = chartManage.GetSerieOfAlarmType(data[2], XLabel, "环比");

			series.push(sameSerie);
			series.push(circleSerie);
			series.push(serie);

			$("#jqtj").empty();
			$("#jqtj").kendoChart({
				title : {
					text : title,
					color: "#81C9F0"
				},
				legend : {
					position : "top",
					visible : true,
					labels:{
						color: "#81C9F0"
					}
				},
				seriesDefaults : {
					type : "column",
					labels : {
						visible : true,
						background : "transparent"
					},
				},
				series : series,
				valueAxis : {
					//max: 1500,
					line : {
						visible : true,
						color:"#81C9F0"
					},
					minorGridLines : {
						visible : true
					},
					labels:{
				    	color: "#81C9F0"
				    },
				    majorGridLines:{
				    	color: "#232323"
				    },
				    minorGridLines:{
				    	color: "#232323"
				    }
				},
				chartArea:{
					background: "transparent"
				},
				categoryAxis : {
					categories : XLabel,
					majorGridLines : {
						visible : false
					},
					background: "transparent",
					color: "#81C9F0"
				},
				tooltip : {
					visible : true,
					template : "#= series.name #: #= value #"
				}
			});
			//获取警情类型的信息
			var alarmTypeObjects = gridManage.GetAlarmTypeObjectsOfAlarmType(data);
			//获取grid的列
			var columnsArray = gridManage.GetColumnsOfAlarmType(alarmTypeObjects,XLabel);
			//获取grid的所有的行
			var dataSource = gridManage.GetDataSourceOfAlarmType(data,columnsArray,alarmTypeObjects,XLabel);
			//设置grid的长宽
			FunctionManage.setGridWidthAndHeight(XLabel.length+1,dataSource.length);
			$("#grid").empty();
			$("#grid").kendoGrid({
				//toolbar: ["excel"],
				//excel: {
                //		fileName: title+"警情统计_警情分类统计表.xlsx"
            	//},
				dataSource : dataSource,
				columns : columnsArray
			});
			$("#grid th[data-role='droptarget']").attr("style",
					"text-align:center");
		},
		//周期
		initAlarmCircleData : function(data, title, XLabel) {
			//动态缩放chart横坐标的长度
			//chartManage.SetChartWidth(XLabel.length);
			$("#jqtj").css("width",1300); 
			var series = [];
			var name;
			for(var i = 0;i<3;i++){
				var serie = {};
				if(i ==0){
					name = "无";
				}else if(i ==1){
					name = "同比";
				}else{
					name = "环比";
				}
				serie = chartManage.GetSerieOfPeroid(data[i],XLabel,name);
				series.push(serie);
			}
 			$("#jqtj").empty();
			$("#jqtj").kendoChart({
				title : {
					text : title,
					color: "#81C9F0"
				},
				legend : {
					position : "top",
					labels:{
						color: "#81C9F0"
					}
				},
				seriesDefaults : {
					type : "line",
					style : "smooth",
				},
				series : series,
				valueAxis : {
					//max: 9,
					line : {
						visible : true,
						color:"#81C9F0"
					},
					minorGridLines : {
						visible : true
					},
					labels:{
						format : "{0}",
				    	color: "#81C9F0"
				    },
				    majorGridLines:{
				    	color: "#232323"
				    },
				    minorGridLines:{
				    	color: "#232323"
				    },
				    axisCrossingValue : -10
				},
				categoryAxis : {
					categories : XLabel,
					majorGridLines : {
						visible : false
					},
					background: "transparent",
					color: "#81C9F0"
				},
				chartArea:{
					background: "transparent"
				},
				tooltip : {
					visible : true,
					format : "{0}%",
					template : "#= series.name #: #= value #"
				}
			});
			//建立表头
			var gridColumns = [];
			gridColumns[0] = {field:"alarmCirlce"};
			for(var i = 0;i<XLabel.length;i++){
				var newColumn = {field:"date"+i};
				gridColumns.push(newColumn);
			}
			
			//获取数据
			var dataSource = [];
			$.each(data,function(index,item){
				if(item.data ==undefined){
					return true;
				}
				//针对单个数据
				var dateShow = FunctionManage.GetDateShowOfCircle(item,XLabel);
				var dataShow = FunctionManage.GetDataShowOfCircle(series[index].data,XLabel);
				dataSource.push(dateShow);
				dataSource.push(dataShow);
			}); 
			
			//设置grid的长宽
			FunctionManage.setGridWidthAndHeight(gridColumns.length,dataSource.length);
 			$("#grid").empty();
			$("#grid").kendoGrid({
				//toolbar: ["excel"],
				//excel: {
                //	fileName: title+"警情统计_周期统计表.xlsx"
            	//},
				dataSource : dataSource,
				columns : gridColumns,
				scrollable : true,
			}); 
			$("#grid th.k-header").hide();
		},
		//时间段
		initAlarmTimeSpanData : function(data, title) {
			var names = [];
			names[0] = FunctionManage.GetSerieName(data[0]);
			names[1] = FunctionManage.GetSerieName(data[1]);
			names[2] = FunctionManage.GetSerieName(data[2]);
			var seriesArray = [
					FunctionManage.GetSeriesObjectOfTimeSpan(data[0], "统计时间："
							+ names[0]),
					FunctionManage.GetSeriesObjectOfTimeSpan(data[1], "统计时间："
							+ names[1]),
					FunctionManage.GetSeriesObjectOfTimeSpan(data[2], "统计时间："
							+ names[2]) ];

			//X轴下显示的值
			var categoriesAxis = [];
			for ( var i = 0; i < 24; i++) {
				categoriesAxis[i - 1] = i.toString();
			}
			$("#jqtj").empty();
			$("#jqtj").css("width",1300);
			$("#jqtj").kendoChart({
				title : {
					text : title,
					color: "#81C9F0"
				},
				legend : {
					position : "bottom",
					labels:{
						color: "#81C9F0"
					}
				},
				seriesDefaults : {
					type : "area",
					area : {
						line : {
							style : "smooth"
						}
					}
				},
				series : seriesArray,
				valueAxis : {
					//max: 9,
					line : {
						visible : true,
						color:"#81C9F0"
					},
					minorGridLines : {
						visible : true
					},
					labels:{
						format : "{0}",
				    	color: "#81C9F0"
				    },
				    majorGridLines:{
				    	color: "#232323"
				    },
				    minorGridLines:{
				    	color: "#232323"
				    },
				    axisCrossingValue : -10
				},
				categoryAxis : {
					categories : categoriesAxis,
					majorGridLines : {
						visible : false
					},
					background: "transparent",
					color: "#81C9F0"
				},
				chartArea:{
					background: "transparent"
				},
				tooltip : {
					visible : true,
					format : "{0}%",
					template : "#= series.name #: #= value #"
				}
			});	
			var gridDataSource = [];
			for ( var count = 0; count < seriesArray.length; count++) {
				var newData = {
					timeCircle : names[count]
				};
				for ( var j = 1; j < seriesArray[count].data.length + 1; j++) {
					newData["T_" + j.toString()] = seriesArray[count].data[j - 1];
				}
				gridDataSource[count] = newData;
			}
			var gridColumns = [];
			gridColumns[0] = {
				field : "timeCircle",
				title :"时间段",
				width : "250px",
				attributes : {
					"class" : "table-cell",
					style : "text-align: left"
				}
			};
			for ( var s = 1; s < 25; s++) {
				gridColumns[s] = {
					field : "T_" + s.toString(),
					title : s.toString(),
					width : "50px"
				};
			}
			
			//设置grid的长宽
			FunctionManage.setGridWidthAndHeight(gridColumns.length,gridDataSource.length);
			$("#grid").empty();
			$("#grid").kendoGrid({
				//toolbar: ["excel"],
				//excel: {
                //fileName: title+"警情统计_时间段统计表.xlsx"
            	//},
				dataSource : gridDataSource,
				columns : gridColumns,
				resizable : true
			});
			$("#grid th[data-role='droptarget']").attr("style",
					"text-align:center");
		},
		//机构
		initAlarmOrganData : function(data, title, XLabel, alarmTypeName) {
			//动态缩放chart横坐标的长度
			chartManage.SetChartWidth(XLabel.length);

			var seriesArray = chartManage.GetSerieOfOrgan(data[0].data,alarmTypeName,XLabel);

			$("#jqtj").empty();
			$("#jqtj").kendoChart({
				title : {
					text : title,
					color: "#81C9F0"
				},
				legend : {
					visible : false
				},
				seriesDefaults : {
					type : "column"
				},
				series : seriesArray,
				valueAxis : {
					line : {
						visible : true,
						color:"#81C9F0"
					},
					minorGridLines : {
						visible : true
					},
					labels:{
						format : "{0}",
				    	color: "#81C9F0"
				    },
				    majorGridLines:{
				    	color: "#232323"
				    },
				    minorGridLines:{
				    	color: "#232323"
				    },
				    axisCrossingValue : -10
				},
				categoryAxis : {
					baseUnitStep : "auto",
					categories : XLabel,
					majorGridLines : {
						visible : false
					},
					background: "transparent",
					color: "#81C9F0"
				},
				chartArea:{
					background: "transparent"
				},
				tooltip : {
					visible : true,
					template : "#= series.name #: #= value #"
				}
			});
			//添加表格

			//获取警情类型信息
			var alarmTypeObjects = gridManage.GetAlarmTypeObjectsOfOrgan(data[0].data,alarmTypeName);
			//获取列头
			var columnsArray = gridManage.GetColumnsOfOrgan(alarmTypeObjects,alarmTypeName);
			var dataSource = gridManage.GetDataSourceOfOrgan(data[0].data,columnsArray,alarmTypeObjects,XLabel);

			//设置grid的长宽
			FunctionManage.setGridWidthAndHeight(alarmTypeName.length+1,dataSource.length);
			$("#grid").empty();
			$("#grid").kendoGrid({
				//toolbar: ["excel"],
				//excel: {
                //fileName: title+"警情统计_机构统计表.xlsx"
            	//},
				dataSource : dataSource,
				columns : columnsArray,
			});
			$("#grid th[data-role='droptarget']").attr("style",
					"text-align:center");
		}
	};

	var FunctionManage = {
		GetDataShowOfCircle:function(data,xlabel){
			var dataShow = {};
			//初始化
			dataShow["alarmCirlce"] ="警情数量(件)";
			$.each(xlabel,function(index,item){
				dataShow["date"+index] = "0";
			});
			if(data == undefined)
			return dataShow;
			$.each(data,function(index,item){
				if(item ==undefined)
					return true;
				dataShow["date"+index] = item.toString();
			});
			return dataShow;
		},
		GetDateShowOfCircle:function(data,xlabel){
			var dateShow = {};
			dateShow["alarmCirlce"] ="日期"; 
			if(m_Query_pkg.periodType ==1){//按天
				var lastXLabel = 0;
				var beginYear = parseInt(data.beginYmd/10000);
				var endYear = parseInt(data.endYmd/10000);
				var beginMonth =parseInt(data.beginYmd/100)%100;
				var endMonth =parseInt(data.endYmd/100)%100;
				
				$.each(xlabel,function(index,item){
					if(index ==0){
						lastXLabel = item;
						dateShow["date"+index] = beginYear.toString()+"."+beginMonth.toString()+"."+item;
						return true;
					}
					if(lastXLabel <item){
						lastXLabel = item;
						dateShow["date"+index] = beginYear.toString()+"."+beginMonth.toString()+"."+item;
					}else{
						lastXLabel = item;
						dateShow["date"+index] = endYear.toString()+"."+endMonth.toString()+"."+item;
						beginYear = endYear;
						beginMonth = endMonth;
					}
				});
			}else if(m_Query_pkg.periodType==3){
				//判断是否跨年
				$.each(xlabel,function(index,item){
					var lastXLabel = 0;
					var beginYear = parseInt(data.beginYmd/10000);
					var endYear = parseInt(data.endYmd/10000);
					
					if(index ==0){
						lastXLabel = item;
						dateShow["date"+index] = beginYear.toString()+"."+item;
						return true;
					}
					if(lastXLabel <item){
						lastXLabel = item;
						dateShow["date"+index] = beginYear.toString()+"."+item;
					}else{
						lastXLabel = item;
						dateShow["date"+index] = endYear.toString()+"."+item;
						beginYear = endYear;
					}
				});
			}
			return dateShow;
		},
		GetSeriesObjectOfTimeSpan : function(data, name) {
			var array = [];
			for (var i = 0; i <24; i++) {
				array[i] = 0;	
			}
			if (data!=undefined||data.data!=undefined) {
				for ( var i = 0; i < data.data.length; i++) {
				array[i] = data.data[i].amount;
			}
			};
			
			return {
				name : name,
				data : array
			};
		},
		GetStandardYM : function(date) {
			return parseInt(date / 10000).toString() + "年"
					+ (parseInt(date / 100) % 100).toString() + "月"
					+ (parseInt(date % 100)).toString() + "日";
		},
		GetSerieName : function(data) {
			var name = FunctionManage.GetStandardYM(data.beginYmd) + "-"
					+ FunctionManage.GetStandardYM(data.endYmd);
			return name;
		},
		setGridWidthAndHeight:function(columns,rows){
			if(columns > 8){
				$("#grid").css("width",900+(columns-8)*80);
			}else{
				$("#grid").css("width",900);
			} 
		},
		FindParentOrgName:function(orgPath,orgPaths){
			var orgName = "none";
			$.each(orgPaths,function(index,item){
				if (orgPath.indexOf(item)>=0) {
					orgName = index;
					return false;
				}
			});
			return orgName;
		}
	};
	function onExportExcelAction(){
		var url = "";
		switch(m_statistic_typeId)
		{
			case 1:
				url = "<%=basePath%>exportExcelWeb/exportAlarmTypeDataToExcle.do";//警情分类统计导出地址
				break;
			case 2:
				url = "<%=basePath%>exportExcelWeb/exportOrganDataToExcle.do";//警情分类统计导出地址
				break;
			case 3:
				url = "<%=basePath%>exportExcelWeb/exportPeriodDataToExcle.do";//警情分类统计导出地址
				break;
			case 4:
				url = "<%=basePath%>exportExcelWeb/exportTiemSpanDataToExcle.do";//警情分类统计导出地址
				break;
		}
		var griddata = $("#grid").data("kendoGrid"); 
		var s = [];
		$.each( griddata._data,function(index,value){
			 s.push(JSON.stringify(value));
		});
		var data = griddata._data
		$.ajax({
				url : url,
				type : "post",
				data : {"data" :  JSON.stringify(s),"query" : JSON.stringify(m_Query_pkg)},
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
</script>  
<style>
	.charts{
		width:3000px;
		background-image: url(../Skin/Default/images/bg.png);
  		background-position-x: initial;
  		background-position-y: initial;
  		background-size: initial;
  		background-repeat-x: initial;
  		background-repeat-y: initial;
  		background-attachment: initial;
  		background-origin: initial;
  		background-clip: initial;
  		background-color: initial;
  		font-family: Arial, "微软雅黑", "宋体";
	}
</style>  
<div style="width:150%;overflow:auto;height:430px">

	<div id="jqtj"></div>
</div>
<br>
<br>
<div style="width:150%;overflow:auto;"> 
	<div id="grid"></div>
</div>
