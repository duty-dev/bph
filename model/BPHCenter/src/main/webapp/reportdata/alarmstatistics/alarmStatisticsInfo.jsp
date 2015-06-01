<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript">

var ReportManage ={
	initAlarmTypeData:function(data,title,XLabel){
		$("#jqtj").empty();
		$("#jqtj").kendoChart({
                title: {
                    text: "四川省各市州警情统计"
                },
                legend: {
                    visible: true
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
	},
	initAlarmCircleData:function(data,title,XLabel){
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
	initAlarmOrganData:function(data,title,XLabel){
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

</script>
<div id="jqtj"></div>   