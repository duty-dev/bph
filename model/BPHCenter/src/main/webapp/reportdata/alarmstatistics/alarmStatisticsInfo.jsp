<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript">

//图表的标题
var title ="sssss"
function GetSeriesArray(data)
{
	var array = [];
	for(var i=0;i<data.data.length;i++)
		{
		array[i] = data.data[i].amount;
		}
	return {name:GetSerieName(data),data:array};
}

function GetSerieName(data){
	var name = "统计时间：";
	name +=GetStandardYM(data.beginYmd)+"-"+GetStandardYM(data.endYmd);
	return name;
}
//通过数字获取标准的年月格式
function GetStandardYM(date)
{
	return parseInt(date/10000).toString()+"年"+parseInt(parseInt(date/100)%100).toString()+"月"+parseInt(date%100).toString()+"日";
	}
//获取Y轴的最大显示值(当期)
function GetMaxValue(data)
{
	var max = 0;
	for(var i =0;i<data.data.length;i++)
		{
		if(max<data.data[i].amount)
			{
			max = data.data[i].amount;
			}
		}
	return max;
	}

//获取X轴的显示值
function GetCategoryAxis(data)
{
	
	var array = [];
	for(var i = 0;i<data.length;i++)
		{
		array[i] = data[i].typeName;
		}
	return array;
	}
	
var ReportManage ={

	/* 警情分类 */
	initAlarmTypeData:function(data,orgName,XLabel){
		//series：数组   用来控制同比环比，数组一个对象就是无；ps:name用来显示筛选时间，data对应柱状图上的数据
		var seriesArray = [{name:"黄",data:[2,3,4,5,6]},{name:"蓝",data:[2,3,4,5,6]}];//GetSeriesArray(data[2]);
		//maxValue:Y轴的最大值；
		var maxValue = 20;
		//X轴下显示的值
		var categoriesAxis = ["1","2","3","4","5"];
		
		$("#jqtj").empty();
		$("#jqtj").kendoChart({
                title: {
                    text: title
                },
                legend: {
                    visible: true
                },
                seriesDefaults: {
                    type: "column"
                },
                series:seriesArray ,
                valueAxis: {
                    max: maxValue,
                    line: {
                        visible: false
                    },
                    minorGridLines: {
                        visible: true
                    }
                },
                categoryAxis: {
                    categories: categoriesAxis,
                    majorGridLines: {
                        visible: false
                    }
                },
                tooltip: {
                    visible: true,
                    template: "#= series.name #: #= value #"
                }
            });  
	},
	initAlarmCircleData:function(data){
		var seriesArray = [{name:"黄",data:[2,3,4,5,6]},{name:"蓝",data:[2,3,4,5,6]}];
		//maxValue:Y轴的最大值；
		var maxValue = 20;
		//X轴下显示的值
		var categoriesAxis = ["1","2","3","4","5"];
		$("#jqtj").empty(); 
		$("#jqtj").kendoChart({
                title: {
                    text: title
                },
                legend: {
                    position: "bottom"
                },
                seriesDefaults: {
                    type: "line",
                    style: "smooth"
                },
                series:seriesArray,
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
	},
	initAlarmTimeSpanData:function(data){
		var seriesArray = [{name:"黄",data:[2,3,4,5,6]},{name:"蓝",data:[2,3,4,5,6]}];
		//maxValue:Y轴的最大值；
		var maxValue = 20;
		//X轴下显示的值
		var categoriesAxis = ["1","2","3","4","5"];
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
	},
	initAlarmOrganData:function(data){
		var seriesArray = [{name:"黄",data:[2,3,4,5,6]},{name:"蓝",data:[2,3,4,5,6]}];
		//maxValue:Y轴的最大值；
		var maxValue = 20;
		//X轴下显示的值
		var categoriesAxis = ["1","2","3","4","5"];
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
                    max: maxValue,
                    line: {
                        visible: false
                    },
                    minorGridLines: {
                        visible: true
                    }
                },
                categoryAxis: {
                    categories: categoriesAxis,
                    majorGridLines: {
                        visible: false
                    }
                },
                tooltip: {
                    visible: true,
                    template: "#= series.name #: #= value #"
                }
            });  
	}
};

</script>
<div id="jqtj"></div>   