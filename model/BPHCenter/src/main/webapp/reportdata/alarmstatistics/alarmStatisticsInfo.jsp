<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript">

var ReportManage ={
	getOtherAmount:function(data,typeCode,amount){
			for(var i = 0;i< data.data.length;i++){
				if(data.data[i].typeLevel !=1&&data.data[i].parentTypeCode == typeCode)
					amount = amount-data.data[i].amount;
			}
			return amount;
		},
		getTotalAmount:function(data){
			
			var totalAmount=0;
			var firstLevel=[];
			var secondLevel=[];
			//分别取出一级、二级警情
			for(var i=0;i<data.data.length;i++){
				if(data.data[i].typeLevel==1){
					firstLevel.push(data.data[i]);
				}else if(data.data[i].typeLevel==2){
					secondLevel.push(data.data[i]);
				}
			}
			for(var m=0;m<firstLevel.length;m++){
				var fCode=firstLevel[m].typeCode;
				for(var n=0;n<secondLevel.length;n++){
					var sCode=secondLevel[n].typeParentCode;
					if(fCode==sCode){
						secondLevel.splice(n,1);//删掉在大类下的小类，剩下的就是无大类的小类
						n--;
					}
				}
			}
			for(var i = 0;i< data.data.length;i++){
				if(data.data[i].typeLevel==1){
					totalAmount+=data.data[i].amount;//所有大类数量
				}
			}
			for(var j=0;j<secondLevel.length;j++){
				totalAmount+=secondLevel[j].amount;//加上无大类的小类数量
			}
			return totalAmount;
		},
	initAlarmTypeData:function(data,title,XLabel){  
		//遍历数组，构建对象
		var dataSource = [];
		var d = data[2].data.length;
		//遍历同比数据
		var tb={};
		tb.name="同比";
		tb.data =[];
		for(var i = 0;i<d;i++){  
			if(data[0].data.length==0){
				tb.data.push(0);
			}else{
				tb.data.push(data[0].data[i].amount);
			}
		}
		dataSource.push(tb);
		//遍历环比数据
		var hb={};
		hb.name="环比";
		hb.data =[];
		for(var m = 0;m<d;m++){  
			if(data[1].data.length==0){
				hb.data.push(0);
			}else{
				if(data[1].data[m].amount!=undefined){
					hb.data.push(data[1].data[m].amount);
				}
				else{
					hb.data.push(0);
				}
			}
		}
		dataSource.push(hb);
		//遍历当期数据
		var normal={};
		normal.name="无";
		normal.data =[];
		for(var n = 0;n<d;n++){  
			if(data[2].data.length==0){
				normal.data.push(0);
			}else{
				normal.data.push(data[2].data[n].amount);
			}
		}
		dataSource.push(normal);
		$("#jqtj").empty();
		$("#jqtj").kendoChart({
            title: {
                text: title
            },
            legend: {
                position:"top",
                visible: true
            },
            seriesDefaults: {
                type: "column",
                labels: {
                        visible: true,
                        background: "transparent"
                    },
            },
            series:dataSource,
            valueAxis: {
                //max: 9,
                line: {
                    visible: false
                },
                minorGridLines: {
                    visible: true
                }
            },
            categoryAxis: {
                categories: XLabel,
                majorGridLines: {
                    visible: false
                }
            },
            tooltip: {
                visible: true,
                template: "#= series.name #: #= value #"
            }
        });
		var firstColumns=[];
		var firstLevel=[];
		var secondLevel=[];
		var noPColumns=[];
		//分别取出一级、二级警情
		for(var i=0;i<d;i++){
			if(data[0].data[i].typeLevel==1){
				firstLevel.push(data[0].data[i]);
			}else if(data[0].data[i].typeLevel==2){
				secondLevel.push(data[0].data[i]);
			}
		}
		for(var m=0;m<firstLevel.length;m++){
			var secondColumns=[];
			var k=0;
			var fCode=firstLevel[m].typeCode;
			for(var n=0;n<secondLevel.length;n++){
				var sCode=secondLevel[n].typeParentCode;
				if(fCode==sCode){
					secondColumns[k++]={title:secondLevel[n].typeName,field:secondLevel[n].typeCode};//小类放到对应的大类中
					secondLevel.splice(n,1);//删掉在大类下的小类，剩下的就是无大类的小类
					n--;
				}
			}
			secondColumns.push({title:"-",field:"sOther"+firstLevel[m].typeCode});//小类末尾“-”
			firstColumns[m]={title:firstLevel[m].typeName,field:firstLevel[m].typeCode,columns:secondColumns};
		}
		for(var j=0;j<secondLevel.length;j++){
			noPColumns.push({title:secondLevel[j].typeName,field:secondLevel[j].typeCode});//无大类的小类放到一起
		}
		firstColumns.push({title:"其他",field:"fOther",columns:noPColumns}); //无大类的小类列
		firstColumns.unshift({title:" ",field:"nothing",columns:[]});//表头第一列空
		firstColumns.push({title:"合计",field:"totals",columns:[{title:"-",field:"total"}]});//表头最后一列“合计”
		//dataSource
		var gridData=[];
		//同比
		var Tb={};
		for(var j=0;j<d;j++){
			if(data[0].data[j].typeLevel==2){
				Tb[data[0].data[j].typeCode] = data[0].data[j].amount;
			}else{
				Tb["sOther"+data[0].data[j].typeCode]=ReportManage.getOtherAmount(data[0],data[0].data[j].typeCode,data[0].data[j].amount);
			}
		}
		Tb["nothing"]=data[0].beginYmd+"-"+data[0].endYmd;
		Tb["total"]=ReportManage.getTotalAmount(data[0]);
		gridData.push(Tb);
		//环比
		/* var Hb={};
		for(var j=0;j<d;j++){
			if(data[1].data[j].typeLevel==2){
				Hb[data[1].data[j].typeCode] = data[1].data[j].amount;
			}else{
				Hb["sOther"+data[1].data[j].typeCode]=ReportManage.getOtherAmount(data[1],data[1].data[j].typeCode,data[1].data[j].amount);
			}
		}
		Hb["nothing"]=data[1].beginYmd+"-"+data[1].endYmd;
		Hb["total"]=ReportManage.getTotalAmount(data[1]);
		gridData.push(Hb); */
		//无
		var no={};
		for(var j=0;j<d;j++){
			if(data[2].data[j].typeLevel==2){
				no[data[2].data[j].typeCode] = data[2].data[j].amount;
			}else{
				no["sOther"+data[2].data[j].typeCode]=ReportManage.getOtherAmount(data[2],data[2].data[j].typeCode,data[2].data[j].amount);
			}
		}
		no["nothing"]=data[2].beginYmd+"-"+data[2].endYmd;
		no["total"]=ReportManage.getTotalAmount(data[2]);
		gridData.push(no);
		$("#grid").empty();
		$("#grid").kendoGrid({
            dataSource:gridData, 
            columns:firstColumns, 
            height: 550,
            scrollable: true, 
            resizable: true,
            reorderable: true,
            filterable: false,
            pageable: {
                input: true,
                numeric: false
            }
        });
        $("#grid th[data-role='droptarget']").attr("style","text-align:center");
	},
	initAlarmCircleData:function(data,title,XLabel){
		//遍历数组，构建对象
		var dataSource = [];
		var d = data[2].data.length;
		//遍历同比数据
		var tb={};
		tb.name="同比";
		tb.data =[];
		for(var i = 0;i<d;i++){  
			if(data[0].data.length==0){
				tb.data.push(0);
			}else{
				tb.data.push(data[0].data[i].amount);
			}
		}
		dataSource.push(tb);
		//遍历环比数据
		var hb={};
		hb.name="环比";
		hb.data =[];
		for(var m = 0;m<d;m++){  
			if(data[1].data.length==0){
				hb.data.push(0);
			}else{
				hb.data.push(data[1].data[m].amount);
			}
		}
		dataSource.push(hb);
		//遍历当期数据
		var normal={};
		normal.name="无";
		normal.data =[];
		for(var n = 0;n<d;n++){  
			if(data[2].data.length==0){
				normal.data.push(0);
			}else{
				normal.data.push(data[2].data[n].amount);
			}
		}
		dataSource.push(normal);
		$("#jqtj").empty(); 
		$("#jqtj").kendoChart({
                title: {
                    text: title
                },
                legend: {
                    position: "top"
                },
                seriesDefaults: {
                    type: "line",
                    style: "smooth",
                },
                series: dataSource,
                valueAxis: {
                    labels: {
                        format: "{0}"
                    },
                    line: {
                        visible: false
                    },
                    axisCrossingValue: -10
                },
                categoryAxis: {
                    categories: XLabel,
                    majorGridLines: {
                        visible: false
                    }
                },
                tooltip: {
                    visible: true,
                    format: "{0}%",
                    template: "#= series.name #: #= value #"
                }
            });
		//表头
		var gridColumns=[];
		gridColumns=[{title:"title",field:"title"},{title:"begin",field:"begin"},{title:"end",field:"end"}];
		//dataSource
		//同比
        var gridData=[];
        var tbDate={};
        var date1=[];
        var tbAmount={};
        date1=[data[0].beginYmd,data[0].endYmd];
        date1.unshift("日期");
        for(var m=0;m<gridColumns.length;m++){
        	tbDate[gridColumns[m].field]=date1[m];
         }
        gridData.push(tbDate);
        for(var n=0;n<gridColumns.length;n++){
        	if(n==0){
        		tbAmount[gridColumns[0].field]="警情数量(件)";
        	}else{
        		tbAmount[gridColumns[n].field]=data[0].data[n-1].amount;
        	}
         }
        gridData.push(tbAmount);
        //环比
        /* var hbDate={};
        var date2=[];
        var hbAmount={};
        date2=[data[1].beginYmd,data[1].endYmd];
        date2.unshift("日期");
        for(var m=0;m<gridColumns.length;m++){
        	hbDate[gridColumns[m].field]=date2[m];
         }
        gridData.push(hbDate);
        for(var n=0;n<gridColumns.length;n++){
        	if(n==0){
        		hbAmount[gridColumns[0].field]="警情数量(件)";
        	}else{
        		hbAmount[gridColumns[n].field]=data[0].data[n-1].amount;
        	}
         }
        gridData.push(hbAmount); */
        //无
        var nDate={};
        var date3=[];
        var nAmount={};
        date3=[data[2].beginYmd,data[2].endYmd];
        date3.unshift("日期");
        for(var m=0;m<gridColumns.length;m++){
        	nDate[gridColumns[m].field]=date3[m];
         }
        gridData.push(nDate);
        for(var n=0;n<gridColumns.length;n++){
        	if(n==0){
        		nAmount[gridColumns[0].field]="警情数量(件)";
        	}else{
        		nAmount[gridColumns[n].field]=data[0].data[n-1].amount;
        	}
         }
        gridData.push(nAmount);
		$("#grid").empty();
		$("#grid").kendoGrid({
            dataSource:gridData, 
            columns:gridColumns, 
            height: 550,
            scrollable: true, 
            resizable: true,
            reorderable: true,
            filterable: false,
            pageable: {
                input: true,
                numeric: false
            }
        });
	},
	initAlarmTimeSpanData:function(data,title){
		var names = [];
		names[0] = FunctionManage.GetSerieName(data[0]);
		names[1] = FunctionManage.GetSerieName(data[1]);
		names[2] = FunctionManage.GetSerieName(data[2]);
		var seriesArray = [FunctionManage.GetSeriesObjectOfTimeSpan(data[0],"统计时间："+names[0]),FunctionManage.GetSeriesObjectOfTimeSpan(data[1],"统计时间："+names[1]),FunctionManage.GetSeriesObjectOfTimeSpan(data[2],"统计时间："+names[2])];
	  
		//X轴下显示的值
		var categoriesAxis = [];
		for(var i =0;i<24;i++)
		{
			categoriesAxis[i-1] = i.toString();
		}
		$("#jqtj").empty();
		$("#jqtj").kendoChart({
                title: {
                    text: title
                },
                legend: {
                    position: "bottom"
                },
                seriesDefaults: {
                    type: "area",
                    area: {
                        line: {
                            style: "smooth"
                        }
                    }
                },
                series:seriesArray ,
                valueAxis: {
                    labels: {
                        format: "{0}"
                    },
                    line: {
                        visible: false
                    },
                    axisCrossingValue: -10
                },
                categoryAxis: {
                    categories: categoriesAxis,
                    majorGridLines: {
                        visible: false
                    }
                },
                tooltip: {
                    visible: true,
                    format: "{0}%",
                    template: "#= series.name #: #= value #"
                }
            });
            
            var gridDataSource =[];
            for(var count = 0;count <seriesArray.length;count++){
            	var newData = {timeCircle:names[count]};
             	for(var j =1;j< seriesArray[count].data.length+1 ; j++){
            		newData["T_"+j.toString()] = seriesArray[count].data[j-1];
            	}
            	gridDataSource[count] = newData;
            }
            var gridColumns = [];
            gridColumns[0] = {field:"timeCircle",title:"时间段",width:"250px",attributes:{"class": "table-cell",style:"text-align: left"}};
            for(var s = 1;s<25;s++){
            	gridColumns[s] = {field:"T_"+s.toString(),title:s.toString(),width:"50px"};
            } 
            $("#grid").empty();
            $("#grid").kendoGrid({
                        dataSource: gridDataSource,
                        columns: gridColumns,
                        resizable: true
                    }); 
                    $("#grid th[data-role='droptarget']").attr("style","text-align:center");
	},
	
	initAlarmOrganData:function(data,title,XLabel,alarmTypeName){
	
		$("#lbl_reportTime").text(m_Query_pkg.startDate +"____"+m_Query_pkg.endDate);
	
		var seriesArray=[]; 
		var rows=data[0].data;  
		var types={};
		
		$.each(alarmTypeName,function(index,item){
			var t={};
			t.name=item;
			t.data = [];
			t.data.length=XLabel.length;
			types[item]=t;
		});  
		
		$.each(rows,function(index,item){
			if(item.typeName != undefined){  
				var t=types[item.typeName]; 
				var orgIndex=XLabel.indexOf(item.orgName);
				t.data[orgIndex]=item.amount;
			} 
		});
		
		$.each(types,function(index,item){
			seriesArray.push(item);
		})

		$("#jqtj").empty(); 
		$("#jqtj").kendoChart({
                title: {
                    text: title
                },
                legend: {
                    visible: false
                },
                seriesDefaults: {
                    type: "column"
                },
                series: seriesArray,
                valueAxis: {
                    line: {
                        visible: false
                    },
                    minorGridLines: {
                        visible: true
                    }
                },
                categoryAxis: {
                	baseUnitStep :"auto",
                    categories: XLabel,
                    majorGridLines: {
                        visible: false
                    }
                },
                tooltip: {
                    visible: true,
                    template: "#= series.name #: #= value #"
                }
            }); 
        //添加表格
        var gridColumns = {};
        gridColumns['alarmType']=({field:"alarmType",title:"警情分类"});
        var gridDataSource ={};
        $.each(XLabel,function(index,item){
        	var t = {};
        	t.alarmType = item;
        	gridDataSource[item] = t;
        });
        
        var alarmTypeObject = {};
        $.each(alarmTypeName,function(index,item){
        	var t = {};
        	t.name = item;
        	t.data = [];
        	t.data.length = 3;//0是类型，1是typeCode，2是parentCode
        	alarmTypeObject[item] = t;
        	
        });

        $.each(data[0].data,function(index,item){
        	if(item.typeName!=undefined)
        	{
        	 var t = alarmTypeObject[item.typeName];
        	 t.data[0] = item.typeLevel;
        	 t.data[1] = item.typeCode;
        	 t.data[2] = item.typeParentCode;
        	}
        });
        
         $.each(alarmTypeObject,function(index,item){
         	var newColumn = {};
         	if(item.data[0]==undefined){
         		newColumn = {field:"NoTypeName"+index,title:item.name};
         		gridColumns["NoTypeName"+index] = newColumn;
        	}else
        	{
        		if(item.data[0]==1){
        			var childColumn = [];
        			childColumn.push({field:"other"+item.data[1],title:"-"});
        			newColumn ={field:item.data[1],title:item.name,columns:childColumn};
          			gridColumns[item.data[1]] = newColumn;
        		}else if(item.data[0]==2){
        			//如果是小类，则判断是否存在大类是它的父类；存在，放到其对应的下面；不存在，则建立一个column;
        			$.each(alarmTypeObject,function(index,item1){
        				if(item1.data[0]!=undefined&&item.data[2] ==item1.data[1]){
        					gridColumns[item1.data[1]].columns.push({field:item.data[1],title:item.name});
        					return false;
        				}else if(item1.data[0]!=undefined&&index ==(alarmTypeName.length -1)){
        					newColumn = {title:item.name,field:item.data[1]};
         					gridColumns[item.data[1]] = newColumn;
        				}
        			});
        		}
        	}
        });
        
        $.each(data[0].data,function(index,item){
        	if(item.typeName!=undefined){
        		if(item.typeLevel ==2){
        		gridDataSource[item.orgName][item.typeCode] = item.amount;
        		}else if(item.typeLevel==1){
        		gridDataSource[item.orgName]["other"+item.typeCode] = item.amount;
        		}
        	}
        });
        
        $.each(alarmTypeObject,function(index,item){
        	if(item.data[0]==1){
        		$.each(gridDataSource,function(index1,item1){
        			if(item1[item.name]!=undefined){
        				$.each(gridColumns[item.name].columns,function(index2,item2){
        					if(("other"+item.name) !=item2.field)
        					item1["other"+item.typeCode]-=item1[item2.field];
        				});
        			}
        			
        		});
        	}
        });
        
        
        //转换
        var columns = [];
        $.each(gridColumns,function(index,item){
        	columns.push(item);
        });
        var dataSource = [];
        $.each(gridDataSource,function(index,item){
        	dataSource.push(item);
        });
        
        $("#grid").empty();
        $("#grid").kendoGrid({
            dataSource: dataSource,
            columns: columns,
        });
        $("#grid th[data-role='droptarget']").attr("style","text-align:center");
	}
};

var FunctionManage ={
	
	GetSeriesObjectOfTimeSpan:function(data,name){
		var array = [];
		for(var i=0;i<data.data.length;i++)
			{
			array[i] = data.data[i].amount;
			}
		return {name:name,data:array};
	},
	
	GetStandardYM:function(date){
		return parseInt(date/10000).toString()+"年"+(parseInt(date/100)%100).toString()+"月"+(parseInt(date%100)).toString()+"日";
	},
	
	GetSerieName:function(data){
		var name =FunctionManage.GetStandardYM(data.beginYmd)+"-"+FunctionManage.GetStandardYM(data.endYmd);
		return name;
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

<div id="jqtj" style="width:4000px;"></div>
</div>
<br>
<br>
<div style="width:1000px;overflow:auto;">
<div id="grid" style="width:4000px;height:500px;"></div>
</div> 
