<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript">

var sessionId = $("#token").val();
var myDate = new Date();
var defalultDate = myDate.getFullYear() + "-" + (myDate.getMonth() + 1) + "-" + myDate.getDate();
var lastDate = new Date(myDate.getFullYear(), myDate.getMonth() + 1, 0);
var lastDateNumber = lastDate.getDate();
$(function() {
	$("#calendar").empty();  
	CalendarManage.initDutyCalendar(); 
	
});
function loadData(pageNo){
	CalendarManage.initDutyCalendar(); 
}
var CalendarManage = { 
	retData:[],
	initDutyCalendar:function(){
	 	$.ajax({
			url : "<%=basePath%>dutyCalendarWeb/getCalender.do?sessionId="+sessionId,
			type : "POST",
			dataType : "json",
			data : {
						"date" : defalultDate,
						"expandeds":expandeds,
						"organId":$("#organId").val(),
						"orgId":$("#organId").val(),
						"organPath":$("#organPath").val(),
						"selectName":$("#selectName").val()
					},
			success : function(req) {
				 if(req.code==200){
				 	CalendarManage.retData = req.data;
				 }
				 else{ 
				 	for(var i=1;i<=lastDateNumber;i++){
				 		var obj = {}; 
				 		var selectDt =  (myDate.getFullYear()  + "-" + (myDate.getMonth() + 1) + "-" + i);	
						obj.title = "无报备";
						obj.url = "javascript:selectDateToDuty('"+selectDt+"');";
						obj.start = selectDt;
						CalendarManage.retData.push(obj);
				 	}	
				 }		
				$('#calendar').fullCalendar({
					defaultDate: defalultDate,
					editable: true,
					eventLimit: true, // allow "more" link when too many events
					events:  CalendarManage.retData
				}); 
			}
		}); 
	} 
};

function selectDateToDuty(d){
	 
	}
</script>
<div id="calendar" style="width:100%;height:100%"></div> 