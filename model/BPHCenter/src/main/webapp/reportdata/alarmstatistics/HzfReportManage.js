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
	var name = "ͳ��ʱ�䣺";
	name +=GetStandardYM��data.beginYmd��+"-"+GetStandardYM��data.endYmd��;
	return name;
	}
//ͨ�����ֻ�ȡ��׼�����¸�ʽ
function GetStandardYM(date)
{
	return (date/1000).toString()+"��"+((date/100)%100).toString()+"��";
	}
//��ȡY��������ʾֵ(����)
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

//��ȡX�����ʾֵ
function GetCategoryAxis(data)
{
	
	var array = [];
	for(var i = 0;i<data.length;i++)
		{
		array[i] = data[i]��typeName;
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
                    text: "�Ĵ�ʡ����ͳ��ͼ"
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
                    text: "�Ĵ�ʡ�����ݾ���ͳ��"
                },
                legend: {
                    position: "bottom"
                },
                seriesDefaults: {
                    type: "line",
                    style: "smooth"
                },
                series: [{
                    name: "���°���",
                    data: [1, 2, 5, 6, 6, 7]
                }, {
                    name: "�ΰ�����",
                    data: [2, 4, 3, 4, 8, 9]
                }, {
                    name: "��ͨ����",
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
                    categories: ["�ɶ���", "������", "������", "��ɽ��", "��Ԫ��", "������"],
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
                    text: "�Ĵ�ʡ�����ݾ���ͳ��"
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
                    name: "���°���",
                    data: [1, 2, 5, 6, 6, 7]
                }, {
                    name: "�ΰ�����",
                    data: [2, 4, 3, 4, 8, 9]
                }, {
                    name: "��ͨ����",
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
                    categories: ["�ɶ���", "������", "������", "��ɽ��", "��Ԫ��", "������"],
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
                    text: "�Ĵ�ʡ�����ݾ���ͳ��"
                },
                legend: {
                    visible: false
                },
                seriesDefaults: {
                    type: "column"
                },
                series: [{
                    name: "���°���",
                    data: [1, 2, 5, 6, 6, 7]
                }, {
                    name: "�ΰ�����",
                    data: [2, 4, 3, 4, 8, 9]
                }, {
                    name: "��ͨ����",
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
                    categories: ["�ɶ���", "������", "������", "��ɽ��", "��Ԫ��", "������"],
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