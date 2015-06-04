<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript">

var ReportManage ={
	initAlarmTypeData:function(data,title,XLabel){ 
		//遍历数组，构建对象
		var dataSource = [];
		var l = data[2].data.length;
		var d = data[2].data.length;
		if(l>10){
			d = 10;
		} 
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
			width:1500,
			height:800,
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
		var secondColumns=[];
		var columns=[];
		var firstLevel=[];
		var secondLevel=[];
		var fLevel=0;
		var sLevel=0;
		//分别取出一级、二级警情
		for(var i=0;i<l;i++){
			if(data[0].data[i].typeLevel==1){
				firstLevel.push(data[0].data[i]);
			    fLevel++;
			}else if(data[0].data[i].typeLevel==2){
				secondLevel.push(data[0].data[i]);
				sLevel++;
			}
		}
		for(var m=0;m<fLevel;m++){
			for(var n=0;n<sLevel;n++){
				var fCode=firstLevel[m].typeCode;
				var sCode=secondLevel[n].typeParentCode;
				if(fCode==sCode){
					secondColumns[n]={field:secondLevel[n].typeName};
					columns[m]=secondColumns;
				}
			}
			firstColumns[m]={field:firstLevel[m].typeName,columns:columns[m]};
		}
		
		$("#grid").empty();
		$("#grid").kendoGrid({
            dataSource:[],
            columns:firstColumns,
            height: 550,
            scrollable: true,
            sortable: true,
            resizable: true,
            reorderable: true,
            filterable: false,
            pageable: {
                input: true,
                numeric: false
            }
        });
	},
	initAlarmCircleData:function(data,title,XLabel){
		//遍历数组，构建对象
		var dataSource = [];
		var l = data[2].data.length;
		var d = data[2].data.length;
		if(l>10){
			d = 10;
		} 
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
		//XLabel时间
		for(var i=0;i<XLabel.length;i++){
			gridColumns[i]={field:XLabel[i]};
		}
		gridColumns.unshift({field:"日期"});
		//dataSource
        var gridData=[];
        var g_obj={};
        for(var m=0;m<gridColumns.length;m++){
        	g_obj[gridColumns[m].field]=2;//
         }
        gridData.push(g_obj);
		$("#grid").empty();
		$("#grid").kendoGrid({
            dataSource:gridData,
            	/* [
                  { 一: "23", 二:"30",三:"34",四:"12",五:"11"},
                       ], */
            columns:gridColumns,
            	/* [
                     {field:"一"},
                     {field:"二"},
                     {field:"三"},
                     {field:"四"},
                     {field:"五"}
                     ], */
            height: 550,
            scrollable: true,
            sortable: true,
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
		$("#jqtj").empty(); 
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
            	gridColumns[s] = {field:"T_"+s.toString(),title:s.toString(),width:"40px"};
            } 
            $("#grid").empty();
            $("#grid").kendoGrid({
                        dataSource: gridDataSource,
                        columns: gridColumns
                    }); 
	},
	initAlarmOrganData:function(data,title,XLabel,alarmTypeName){
		var seriesArray=[];
		for(var i = 0;i< alarmTypeName.length;i++)
		{
			seriesArray[i]=FunctionManage.GetSeriesObjectOfOrgan(data,alarmTypeName[i]);
		}
		var maxValue = FunctionManage.GetMaxValue(data);
		$("#jqtj").empty(); 
		$("#jqtj").kendoChart({
			width:1500,
			height:800,
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
        var gridColumns = gridManage.GetGridColumns(data[0]);
        var gridDataSource = gridManage.GetGridDataSource(data,XLabel,gridColumns);
        $("#grid").empty();
        $("#grid").kendoGrid({
            dataSource: gridDataSource,
            columns: gridColumns
        });
	}
};
var gridManage = {
	GetOtherTypeAmount:function(data,typeCode,amount){
		if(data ==null||data.data ==null)
		{
			return -1;
		}	
		for(var i = 0;i< data.data.length;i++){
			if(data.data[i]==null)
				return -1;
			if(data.data[i].typeLevel !=1&&data.data[i].parentTypeCode == typeCode)
				amount = amount-data.data[i].amount;
		}
		return amount;
	},
	
	GetGridDataSource:function(data,XLabel,gridColumns){
		dataSource = [];
		if(data ==null||XLabel ==null||gridColumns==null)
			return null;
			
		for(var count =0;count<XLabel.length;count++){
			var newObject = {};
			newObject['alarmTypeName'] = XLabel[count];
			dataSource[count] = newObject;
		}
		var bigNum = gridManage.GetBigClassAmount(data[0]);
		
		if(bigNum ==-1)
			return null;
			
		if(bigNum !=0)//有大类
		{
			for(var i = 0;i<data.length;i++){
				for(var j = 0;j<data[i].data.length;j++){
					if(data[i].data[j].typeLevel ==1){
						dataSource[i]["other"+data[i].data[j].typeCode] = GetOtherTypeAmount(data[i],data[i].data[j].typeCode,data[i].data[j].amount);
					}else {
					dataSource[i][data[i].data[j].typeCode] = data[i].data[j].amount;
					}
				}
			}
		}else
		{
			for(var i = 0;i<data.length;i++){
				for(var j = 0;j<data[i].data.length;j++){
					dataSource[i][data[i].data[j].typeCode] = data[i].data[j].amount;
				}
			}
		}
		
	
	},
	
	//查询数据中大类的数量
	GetBigClassAmount(data){
		if(data==null)
			return -1;
			
		var count = 0;
		for(var i = 0;i<data.data.length;i++){
			if(data.data[i].typeLevel ==1){
				count++;
			}
		}
		return count;
	},
	
	GetGridColumns:function(data){
		if(data==null)
		return null;
	
		var gridColumns = [];
		gridColumns[0] = {field:"alarmTypeName",title:"警情类型"};
		var columnsCount = 1;
		var bigNum = gridManage.GetBigClassAmount(data);//大类计数
		if(bigNum ==-1)
		{
			return gridColumns;
		}
		if(bigNum ==0){
			//将全部小类添加到columns
			for(var i = 0;i <data.data.length;i++)
			{
				gridColumns[i+1] = {field:data.data[i].typeName};
			}
			return gridColumns;
		}
	
		for(var i = 0;i<data.data.length;i++){
			//判斷是否是大类
			if(data.data[i].typeLevel ==1)
			{
				var parentTypeColumn = {};
				parentTypeColumn.title = data.data[i].typeName;
				parentTypeColumn.field = data.data[i].typeCode;
				var columns = []; 
				var count = 0;
				//继续判断是否存在其小类
				for(var j = 0;j<data.data.length;j++)
				{
					//如果是小类，并且它的父节点是大类的code
					if(data.data[i].typeLevel ==2&&data.data[i].parentTypeCode ==data.data[i].typeCode)
					{
						var childTypeColumn = {field:data.data[i].typeCode,title:data.data[i].typeName};
						columns[count++] = childTypeColumn;
					}
				}
				var childOtherTypeColumn = {field:"other"+data.data[i].typeCode,title:"-"};
				columns[count] = childOtherTypeColumn;
				parentTypeColumn.columns = columns;
				gridColumns[columnsCount++] = parentTypeColumn;
			}else{
				var tempCount = 0;
				for(var s = 0;s<data.data.length;s++){
					if(data.data[i].parentTypeCode ==data.data[s].typeCode)
					{
						tempCount++;
						break;
					}
				}
				if(tempCount ==0){
					var newColumn = {field:"-",title:"-",columns:[{field:data.data[i].typeCode,title:data.data[i].typeName}]};
					gridColumns[columnsCount++] = newColumn;
				}
				
			}

		}
		return gridColumns;
	}
};

var FunctionManage ={
	//获取Y轴的最大显示值(当期)
	GetMaxValue:function(data){
		var max = 0;
		for(var s = 0;s<data.length;s++){
		for(var i =0;i<data[s].data.length;i++)
		{
			if(max<data[s].data[i].amount)
			{
			max = data[s].data[i].amount;
			}
		}
		}
		return max;
	},
	
	GetSeriesObjectOfTimeSpan:function(data,name){
		var array = [];
		for(var i=0;i<data.data.length;i++)
			{
			array[i] = data.data[i].amount;
			}
		return {name:name,data:array};
	},
	
	GetSeriesObjectOfOrgan:function(data,name){
		var array = [];
		
		for(var i=0;i<data.length;i++)
		{
			for(var j = 0;j<data[i].data.length;j++)
			{
				if(data[i].data[j].typeName ==name){
					array[i] = data[i].data[j].amount;
				}
			}
			
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
<div id="jqtj"></div>   
<br><br>
<div id="grid"></div>
