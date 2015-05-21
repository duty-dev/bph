<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript">

var ReportManage ={
	initAlarmTypeData:function(data){
		$("#jqtj").html("<h2> 警情分类统计  </h2>");
	},
	initAlarmCircleData:function(data){
		$("#jqtj").html("<h2> 警情周期统计  </h2>");
	},
	initAlarmTimeSpanData:function(data){
		$("#jqtj").html("<h2> 警情时间段统计  </h2>"); 
	},
	initAlarmOrganData:function(data){
		$("#jqtj").html("<h2> 警情组织统计  </h2>");
	}
};

</script>
<div id="jqtj">
 警情统计 </div>  