function GetSeriesArray(data)
{
	var array = [];
	for(var i=0;i<data.data.length;i++)
		{
		array[i] = data.data[i].amount;
		}
	return {name:GetSerieName(data),data:array};
}

function GetSerieName(data)
{
	var name = "统计时间：";
	name +=GetStandardYM（data.beginYmd）+"-"+GetStandardYM（data.endYmd）;
	return name;
	}
//通过数字获取标准的年月格式
function GetStandardYM(date)
{
	return (date/1000).toString()+"年"+((date/100)%100).toString()+"月";
	}
//获取Y轴的最大显示值(当期)
function GetMaxValue(data)
{
	var max = 0;
	for(var i =0;i<data.length;i++)
		{
		if(max<data[i].amount)
			{
			max = data[i].amount;
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
		array[i] = data[i]。typeName;
		}
	return array;
	}

var ReportManage ={
	initAlarmTypeData:function(data){
		var seriesArray = GetSeriesArray(data.get(0));
		var maxValue = GetMaxValue(data.get(0));
		var categoriesAxis = GetCategoryAxis(data.get(0).data);
		
		$("#jqtj").empty();
		$("#jqtj").kendoChart({
                title: {
                    text: "四川省警情统计图"
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
		$("#jqtj").empty(); 
		$("#jqtj").kendoChart({
                title: {
                    text: "四川省各市州警情统计"
                },
                legend: {
                    position: "bottom"
                },
                seriesDefaults: {
                    type: "line",
                    style: "smooth"
                },
                series: [{
                    name: "刑事案件",
                    data: [1, 2, 5, 6, 6, 7]
                }, {
                    name: "治安案件",
                    data: [2, 4, 3, 4, 8, 9]
                }, {
                    name: "交通案件",
                    data: [3, 2, 5, 3, 6, 8]
                }],
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
                    categories: ["成都市", "德阳市", "绵阳市", "乐山市", "广元市", "达州市"],
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
		$("#jqtj").empty();
		$("#jqtj").kendoChart({
                title: {
                    text: "四川省各市州警情统计"
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
                series: [{
                    name: "刑事案件",
                    data: [1, 2, 5, 6, 6, 7]
                }, {
                    name: "治安案件",
                    data: [2, 4, 3, 4, 8, 9]
                }, {
                    name: "交通案件",
                    data: [3, 2, 5, 3, 6, 8]
                }],
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
                    categories: ["成都市", "德阳市", "绵阳市", "乐山市", "广元市", "达州市"],
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
		$("#jqtj").empty();
		$("#jqtj").kendoChart({
                title: {
                    text: "四川省各市州警情统计"
                },
                legend: {
                    visible: false
                },
                seriesDefaults: {
                    type: "column"
                },
                series: [{
                    name: "刑事案件",
                    data: [1, 2, 5, 6, 6, 7]
                }, {
                    name: "治安案件",
                    data: [2, 4, 3, 4, 8, 9]
                }, {
                    name: "交通案件",
                    data: [3, 2, 5, 3, 6, 8]
                }],
                valueAxis: {
                    max: 9,
                    line: {
                        visible: false
                    },
                    minorGridLines: {
                        visible: true
                    }
                },
                categoryAxis: {
                    categories: ["成都市", "德阳市", "绵阳市", "乐山市", "广元市", "达州市"],
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