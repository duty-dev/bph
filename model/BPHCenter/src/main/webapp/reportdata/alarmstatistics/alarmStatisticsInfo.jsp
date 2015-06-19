<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript"> 
	var chartManage = {
		GetSerie : function(data, xlabel, name) {
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
				var alarmNum = xlabel.indexOf(item.typeName);
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
					var xlabelIndex = xlabel.indexOf(item.period);
					serie.data[xlabelIndex] = item.amount;
				});
			}else{//存在
				$.each(data.data,function(index,item){
					var itemIndex = sameTime.indexOf(item.period);
					
					if(itemIndex ==-1){
						var xlabelIndex = xlabel.indexOf(item.period);
						serie.data[xlabelIndex] = item.amount;
					}else{
						if(m_Query_pkg.periodType ==1){
						//按天
							if(item.months == (parseInt(data.beginYmd/100)%100)){
								var xlabelIndex = xlabel.indexOf(item.period);
								serie.data[xlabelIndex] = item.amount;
							}else{
								var xlabelIndex = xlabel.lastIndexOf(item.period);
								serie.data[xlabelIndex] = item.amount;
							}
						}else if(m_Query_pkg.periodType ==3){
						//按月
							var xlabelIndex = xlabel.indexOf(item.period);
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
		}
			
	}; 
 
	var ReportManage = {
		initAlarmTypeData : function(data, title, XLabel) {
		
			var len = XLabel.length;
			if(len>8){
				$("#jqtj").css("width",1000+(len-8)*80);
			}
			var series = [];
			var sameSerie = chartManage.GetSerie(data[0], XLabel, "同比");
			var circleSerie = chartManage.GetSerie(data[1], XLabel, "环比");
			var serie = chartManage.GetSerie(data[2], XLabel, "无");

			series.push(sameSerie);
			series.push(circleSerie);
			series.push(serie);

			$("#jqtj").empty();
			$("#jqtj").kendoChart({
				title : {
					text : title
				},
				legend : {
					position : "top",
					visible : true
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
					//max: 9,
					line : {
						visible : false
					},
					minorGridLines : {
						visible : true
					}
				},
				categoryAxis : {
					categories : XLabel,
					majorGridLines : {
						visible : false
					}
				},
				tooltip : {
					visible : true,
					template : "#= series.name #: #= value #"
				}
			});

			var columns = {};
			var columnsArray = [];
			columns['tjTime'] = {
				field : "tjTime",
				title : " "
			};

			var alarmTypeObjects = {};
			$.each(data, function(index, item) {
				$.each(item.data, function(index1, item1) {
					var alarmObject = {};
					alarmObject.typeName = item1.typeName;
					alarmObject.typeCode = item1.typeCode;
					alarmObject.typeLevel = item1.typeLevel;
					alarmObject.typeParentCode = item1.typeParentCode;
					alarmTypeObjects[item1.typeName] = alarmObject;
				});
			});
			var count = 0;
			$.each(alarmTypeObjects, function(index, item) {
				count++;
			});

			var numOfNotype  = 0;//对那些没有数据，无法获得其typeCode和parentCode的警情类型
			$.each(XLabel,function(index, item) {
								var column = {};
								
								if (alarmTypeObjects[item] == undefined) {
									column = {
										field : "none" + numOfNotype,
										title : item
									};
									columns["none" + numOfNotype] = column;
									numOfNotype++;
								} else {
									if (alarmTypeObjects[item].typeLevel == 1) {
										var childColumns = [];
										var otherChildColumn = {
											field : "other"
													+ alarmTypeObjects[item].typeCode,
											title : "-"
										};
										childColumns.push(otherChildColumn);

										column = {
											field : alarmTypeObjects[item].typeCode,
											title : alarmTypeObjects[item].typeName,
											columns : childColumns
										};
										columns[alarmTypeObjects[item].typeCode] = column;
									} else if (alarmTypeObjects[item].typeLevel == 2) {
										//检查是否存在大类
										var num = 0;
										$.each(alarmTypeObjects,function(index1, item1) {
															num++;
															if (alarmTypeObjects[item].typeParentCode == item1.typeCode) {
																var childColumn = {
																	field : alarmTypeObjects[item].typeCode,
																	title : alarmTypeObjects[item].typeName
																};

																columns[item1.typeCode].columns
																		.push(childColumn);
																return false;
															} else {
																if (num == count) {
																	column = {
																		field : alarmTypeObjects[item].typeCode,
																		title : alarmTypeObjects[item].typeName
																	};
																	columns[alarmTypeObjects[item].typeCode] = column;
																}
															}
														});
									}
								}
							});

			$.each(columns, function(index, item) {
				columnsArray.push(item);
			});
			columnsArray.push({
				field : "count",
				title : "合计"
			});

			var dataSource = [];
			var totalRow = {};
			totalRow['tjTime']  = "小计";
			//给grid数据赋初始值
			$.each(columns,function(index,item){
				if (index !="tjTime") {
					if (item.columns!=undefined) {
						$.each(item.columns,function(indexOfChildColumns,itemOfChildColumns){
							totalRow[itemOfChildColumns.field] = 0;
						});
					}else{
						totalRow[index] = 0;
					}
				}
			});
			$.each(data, function(index, item) {
				var row = {};
				//给grid数据赋初始值
				$.each(columns,function(index2,item2){
				if (index2 !="tjTime") {
					if (item2.columns!=undefined) {
						$.each(item2.columns,function(indexOfChildColumns,itemOfChildColumns){
							row[itemOfChildColumns.field] = 0;
						});
					}else{
						row[index2] = 0;
					}
				}
				});	
				$.each(item.data, function(index1, item1) {
					if (item1.typeLevel == 1) {
						row["other" + item1.typeCode] = item1.amount;
						$.each(item.data, function(index2, item2) {
							if (item1.typeCode == item2.typeParentCode) {
								row["other" + item1.typeCode] -= item2.amount;
							}
						});
						totalRow["other" + item1.typeCode] = (totalRow["other" + item1.typeCode]==undefined?row["other" + item1.typeCode]:totalRow["other" + item1.typeCode]+row["other" + item1.typeCode]);
					} else if (item1.typeLevel == 2) {
						row[item1.typeCode] = item1.amount;
						totalRow[item1.typeCode] = (totalRow[item1.typeCode] ==undefined ?item1.amount:totalRow[item1.typeCode]+item1.amount);
					}

				});
				var total = 0;
				for (d in row) {
					total += row[d];
				}
				row['count'] = total;
				if(index ==0)
					totalRow['count'] =total;
				else
					totalRow['count'] +=total;
				row['tjTime'] = FunctionManage.GetStandardYM(item.beginYmd)
						+ "-" + FunctionManage.GetStandardYM(item.endYmd);
				dataSource[index] = row;
			});
			dataSource[3] = totalRow;
			

			//设置grid的长宽
			FunctionManage.setGridWidthAndHeight(XLabel+1,dataSource.length);
			$("#grid").empty();
			$("#grid").kendoGrid({
				dataSource : dataSource,
				columns : columnsArray
			});
			$("#grid th[data-role='droptarget']").attr("style",
					"text-align:center");
		},
		initAlarmCircleData : function(data, title, XLabel) {
			var len = XLabel.length;
			if(len>8){
				$("#jqtj").css("width",1000+(len-8)*80);
			}
			var series = [];
			var name;
			for(var i = 0;i<3;i++){
				var serie = {};
				if(i ==0){
					name = "同比";
				}else if(i ==1){
					name = "环比";
				}else{
					name = "无";
				}
				serie = chartManage.GetSerieOfPeroid(data[i],XLabel,name);
				series.push(serie);
			}
 			$("#jqtj").empty();
			$("#jqtj").kendoChart({
				title : {
					text : title
				},
				legend : {
					position : "top"
				},
				seriesDefaults : {
					type : "line",
					style : "smooth",
				},
				series : series,
				valueAxis : {
					labels : {
						format : "{0}"
					},
					line : {
						visible : false
					},
					axisCrossingValue : -10
				},
				categoryAxis : {
					categories : XLabel,
					majorGridLines : {
						visible : false
					}
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
				dataSource : dataSource,
				columns : gridColumns,
				scrollable : true,
			}); 
			$(".k-header").hide();
		},

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
			$("#jqtj").kendoChart({
				title : {
					text : title
				},
				legend : {
					position : "bottom"
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
					labels : {
						format : "{0}"
					},
					line : {
						visible : false
					},
					axisCrossingValue : -10
				},
				categoryAxis : {
					categories : categoriesAxis,
					majorGridLines : {
						visible : false
					}
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
				title : "时间段",
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
			FunctionManage.setGridWidthAndHeight(gridColumns,gridDataSource.length);
			$("#grid").empty();
			$("#grid").kendoGrid({
				dataSource : gridDataSource,
				columns : gridColumns,
				resizable : true
			});
			$("#grid th[data-role='droptarget']").attr("style",
					"text-align:center");
		},

		initAlarmOrganData : function(data, title, XLabel, alarmTypeName) {

			var len = XLabel.length;
			if(len>8){
				$("#jqtj").css("width",1000+(len-8)*80);
			}
			var seriesArray = [];
			var rows = data[0].data;
			var types = {};

			$.each(alarmTypeName, function(index, item) {
				var t = {};
				t.name = item;
				t.data = [];
				t.data.length = XLabel.length;
				$.each(t.data,function(index1,item1){
					t.data[index1] = 0;
				});
				types[item] = t;
			});

			$.each(rows, function(index, item) {
				if (item.typeName != undefined) {
					var t = types[item.typeName];
					var orgIndex = XLabel.indexOf(item.orgName);
					t.data[orgIndex] = item.amount;
				}
			});

			$.each(types, function(index, item) {
				seriesArray.push(item);
			})

			$("#jqtj").empty();
			$("#jqtj").kendoChart({
				title : {
					text : title
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
						visible : false
					},
					minorGridLines : {
						visible : true
					}
				},
				categoryAxis : {
					baseUnitStep : "auto",
					categories : XLabel,
					majorGridLines : {
						visible : false
					}
				},
				tooltip : {
					visible : true,
					template : "#= series.name #: #= value #"
				}
			});
			//添加表格
			var gridColumns = {};
			gridColumns['alarmType'] = ({
				field : "alarmType",
				title : "警情分类"
			});

			var alarmTypeObject = {};
			$.each(alarmTypeName, function(index, item) {
				var t = {};
				t.name = item;
				t.data = [];
				t.data.length = 3;//0是类型，1是typeCode，2是parentCode
				alarmTypeObject[item] = t;

			});

			$.each(data[0].data, function(index, item) {
				if (item.typeName != undefined) {
					var t = alarmTypeObject[item.typeName];
					t.data[0] = item.typeLevel;
					t.data[1] = item.typeCode;
					t.data[2] = item.typeParentCode;
				}
			});

			var countNoTypeName = 0;
			$.each(alarmTypeObject, function(index, item) {
				var newColumn = {};
				if (item.data[0] == undefined) {
					newColumn = {
						field : "NoTypeName" + countNoTypeName,
						title : item.name
					};
					gridColumns["NoTypeName" + countNoTypeName] = newColumn;
					countNoTypeName++;
				} else {
					if (item.data[0] == 1) {
						var childColumn = [];
						childColumn.push({
							field : "other" + item.data[1],
							title : "-"
						});
						newColumn = {
							field : item.data[1],
							title : item.name,
							columns : childColumn
						};
						gridColumns[item.data[1]] = newColumn;
					} else if (item.data[0] == 2) {
						//如果是小类，则判断是否存在大类是它的父类；存在，放到其对应的下面；不存在，则建立一个column;
						$.each(alarmTypeObject, function(index1, item1) {
							if (item1.data[0] != undefined
									&& item.data[2] == item1.data[1]) {
								gridColumns[item1.data[1]].columns.push({
									field : item.data[1],
									title : item.name
								});
								return false;
							} else if (item1.data[0] != undefined
									&& index == (alarmTypeName.length - 1)) {
								newColumn = {
									title : item.name,
									field : item.data[1]
								};
								gridColumns[item.data[1]] = newColumn;
							}
						});
					}
				}
			});

			var gridDataSource = {};
			$.each(XLabel, function(index, item) {
				var t = {};
				t.alarmType = item;
				$.each(gridColumns,function(indexOfColumns,itemOfColumns){
					if(indexOfColumns !="alarmType"){
						if (itemOfColumns.columns ==undefined) {
							t[itemOfColumns.field] = 0;
						}else
						{
							$.each(itemOfColumns.columns,function(indexOfChildColumn,itemOfChildColumns){
								t[itemOfChildColumns.field] =0;
							});
						}
					}	
				});
				gridDataSource[item] = t;
			});
			$
					.each(
							data[0].data,
							function(index, item) {
								if (item.typeName != undefined) {
									if (item.typeLevel == 2) {
										gridDataSource[item.orgName][item.typeCode] = item.amount;
									} else if (item.typeLevel == 1) {
										gridDataSource[item.orgName]["other"
												+ item.typeCode] = item.amount;
									}
								}
							});

			$
					.each(
							alarmTypeObject,
							function(index, item) {
								if (item.data[0] == 1) {
									$
											.each(
													gridDataSource,
													function(index1, item1) {
														if (item1[item.name] != undefined) {
															$
																	.each(
																			gridColumns[item.name].columns,
																			function(
																					index2,
																					item2) {
																				if (("other" + item.name) != item2.field)
																					item1["other"
																							+ item.typeCode] -= item1[item2.field];
																			});
														}

													});
								}
							});

			//转换
			var columns = [];
			$.each(gridColumns, function(index, item) {
				columns.push(item);
			});
			var dataSource = [];
			$.each(gridDataSource, function(index, item) {
				dataSource.push(item);
			});

			//设置grid的长宽
			FunctionManage.setGridWidthAndHeight(alarmTypeName+1,dataSource.length);
			$("#grid").empty();
			$("#grid").kendoGrid({
				dataSource : dataSource,
				columns : columns,
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
			$("#grid").css("width",1000+(columns-8)*80);
			$("#grid").css("height",rows*50);
		}
	};
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
<div style="width:1000px;overflow:auto;height:430px">

	<div id="jqtj"></div>
</div>
<br>
<br>
<div style="width:1000px;overflow:auto;"> 
	<div id="grid"></div>
</div>
