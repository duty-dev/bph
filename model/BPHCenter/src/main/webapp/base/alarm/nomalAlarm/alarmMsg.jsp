<%@ page language="java" pageEncoding="utf-8"%>

<div>
        <div class="tree-box clear"><!----条件查询---->
        <div id="box">
             <div class="clear">
              	<div style="width:100%;float:left"><h2 class="no-bg">警情反馈</h2></div>
              	<div class="ty-nav-line2">
              		<i class="ty-nav-line2-left"></i><i class="ty-nav-line2-right"></i>
              	</div> 
              	<div style="width:100%;float:left"><div class="ty-tj-title">周边资源</div><div class="ty-tj-title" onclick="openAlarmInfo()">综合记录详单</div></div>
              	<div class="ty-nav-line2">
              		<i class="ty-nav-line2-left"></i><i class="ty-nav-line2-right"></i>
              	</div> 
          		<table class="ty-tj-table mt10 ml6">
	          			<tr>
	          				<td width="10"><i class="ty-table-top-l"></i></td><td><i class="ty-table-top"></i></td><td width="10"><i class="ty-table-top-r"></i></td>
	          			</tr>
		          		<tr>
	          				<td colspan="3" class="ty-tj-td pd10">
	          					<div class="ty-tj-title">警情定位</div>
	          					<div class="ty-tj-title">管辖单位</div>
	          					<div class="ty-tj-title">定位信息</div>
	          					<div class="ty-tj-type-list">
		          					<ul id="ul_alarmTypeList"> 
			            		 		<li>
			            		 			杆体、电话、地图标注
			            		 		</li>
			            		 	</ul>
		            		 	</div>
		            		 	<input type="hidden" id="jjdbh" name="jjdbh"/>
	          				</td>
	          			</tr>
	          			<tr>
	          				<td><i class="ty-table-bottom-l"></i></td><td><i class="ty-table-bottom"></i></td><td><i class="ty-table-bottom-r"></i></td>
	          			</tr>
	          		</table>
          	 </div>
          	<div> 
          		 <div class="clear">
	          		 <table class="ty-tj-table mt10 ml6">
	          			<tr>
	          				<td width="10"><i class="ty-table-top-l"></i></td><td><i class="ty-table-top"></i></td><td width="10"><i class="ty-table-top-r"></i></td>
	          			</tr>
	          			<tr>
	          				<td colspan="3" class="ty-tj-td pd10">
	          				<div class="ty-tj-title">人员派遣</div>
	          					<table id="tablePJ">
	          					<tbody>
	          					<tr><td>反馈意见内容</td></tr>
	          					</tbody>
	          					</table>
	          				</td>     
	          			</tr>
	          			<tr>
	          				<td><i class="ty-table-bottom-l"></i></td><td><i class="ty-table-bottom"></i></td><td><i class="ty-table-bottom-r"></i></td>
	          			</tr>
	          		</table>
            	</div>
          </div>
          <div> 
          		 <div class="clear">
	          		 <table class="ty-tj-table mt10 ml6">
	          			<tr>
	          				<td width="10"><i class="ty-table-top-l"></i></td><td><i class="ty-table-top"></i></td><td width="10"><i class="ty-table-top-r"></i></td>
	          			</tr>
	          			<tr>
	          				<td colspan="3" class="ty-tj-td pd10">
	          				<div class="ty-tj-title">处警意见</div>
	          				<table id="tablecjInfo">
	          					<tbody>
	          					<tr><td>反馈意见内容</td></tr>
	          					</tbody>
	          					</table>	          					
	          				</td>     
	          			</tr>
	          			<tr>
	          				<td><i class="ty-table-bottom-l"></i></td><td><i class="ty-table-bottom"></i></td><td><i class="ty-table-bottom-r"></i></td>
	          			</tr>
	          		</table>
            	</div>
          </div>
          <div> 
          		 <div class="clear">
	          		 <table class="ty-tj-table mt10 ml6">
	          			<tr>
	          				<td width="10"><i class="ty-table-top-l"></i></td><td><i class="ty-table-top"></i></td><td width="10"><i class="ty-table-top-r"></i></td>
	          			</tr>
	          			<tr>
	          				<td colspan="3" class="ty-tj-td pd10">
	          				<div class="ty-tj-title">反馈意见（三台合一）</div>
	          					<table id="tableFeedBack">
	          					<tbody>
	          					<tr><td>反馈意见内容</td></tr>
	          					</tbody>
	          					</table>
	          				</td>     
	          			</tr>
	          			<tr>
	          				<td><i class="ty-table-bottom-l"></i></td><td><i class="ty-table-bottom"></i></td><td><i class="ty-table-bottom-r"></i></td>
	          			</tr>
	          		</table>
            	</div>
          </div>
		</div>
		<div> 
          		 <div class="clear">
	          		 <table class="ty-tj-table mt10 ml6">
	          			<tr>
	          				<td width="10"><i class="ty-table-top-l"></i></td><td><i class="ty-table-top"></i></td><td width="10"><i class="ty-table-top-r"></i></td>
	          			</tr>
	          			<tr>
	          				<td colspan="3" class="ty-tj-td pd10">
	          				<div class="ty-tj-title">反馈意见（扁平化）</div>
	          					<table id="tableCommun">
	          					<tbody>
	          					<tr><td>反馈意见内容</td></tr>
	          					</tbody>
	          					</table>
	          				</td>     
	          			</tr>
	          			<tr>
	          				<td><i class="ty-table-bottom-l"></i></td><td><i class="ty-table-bottom"></i></td><td><i class="ty-table-bottom-r"></i></td>
	          			</tr>
	          		</table>
            	</div>
          </div>
		</div>
        </div><!----条件查询结束----> 
        <div class="line8 box"></div>
        <div class="line2"></div>
</div>    

<style type="text/css">
.al-panel {
	width: 400px;
	height: 650px;
	position: absolute;
	top: 0;
	left: 401px;
	background-color: #233742;
	display:none;
}

.al-sf {
	float: left;
	width: 24px;
	height: 70px;
	position: absolute;
	top: 0;
	left: 450px;
}

.al-line {
	float: left;
	width: 3px;
	height: 63px;
	background: url(<%=basePath%>Skin/Default/images/button4.png) no-repeat;
	display: block;
}

.al-icon {
	float: right;
	width: 19px;
	height: 19px;
	background: url(<%=basePath%>Skin/Default/images/button3.png) no-repeat;
	display: block;
}

.al-icon-s {
	float: right;
	width: 19px;
	height: 19px;
	background: url(<%=basePath%>Skin/Default/images/button3-1.png)
		no-repeat;
	display: block;
}

#mkMenu {
	background-color: #233742;
	color: #81C9F0;
}

#mkMenu ul {
	margin: 0;
	padding: 0;
}

#mkMenu li {
	float: left;
	width: 98%;
	height: 30px;
	text-align: center;
	font-size: 15px;
	line-height: 30px;
	border: 1px solid #4A748A;
	cursor: pointer;
}
#mkMenu li:hover {
	color: #00b0ff;
}
</style>
<script>
function getAlarmInfo(alarmId){
	$("#alPanel").slideDown();
	$("#jjdbh").val(alarmId);
	$("#tablePJ tbody").remove();
	$("#tablecjInfo tbody").remove();
	$("#tableFeedBack tbody").remove();
	$("#tableCommun tbody").remove();
		$.ajax({
			url:"<%=path%>/alarmWeb/gotoAlarmInfo.do",
			type:"post",
			dataType:"json",
			data:{
				jjdbh:alarmId
			},
			success:function(msg){
				if(msg.code==200){
					if(msg.data.pjPoliceList != null){
						$(msg.data.pjPoliceList).each(function(){
							$("#tablePJ").append("<tbody><tr>"
							+"<td>"+this.pjPoliceId+"</td>"
							+"</tr></tbody>");
       				});
					}
					if(msg.data.cjFeedBackList != null){
						$(msg.data.cjFeedBackList).each(function(){
							$("#tablecjInfo").append("<tbody><tr>"
							+"<td>"+this.info+"</td>"
							+"</tr></tbody>");
       				});
					}
					if(msg.data.cjFeedBackList != null){
						$(msg.data.cjFeedBackList).each(function(){
							$("#tableFeedBack").append("<tbody><tr>"
							+"<td>"+this.info+"</td>"
							+"</tr></tbody>");
       				});
					}
					if(msg.data.alarmCommunicationList != null){
						$(msg.data.alarmCommunicationList).each(function(){
							$("#tableCommun").append("<tbody><tr>"
							+"<td>"+this.textInfo+"</td>"
							+"</tr></tbody>");
       				});
					}
				}
			}
		});
}
function openAlarmInfo(){
	var jjdbh = $("#jjdbh").val();
	alert(jjdbh);
	$("#dialog").tyWindow({
		width: "660px",
		height: "620px",
	    title: "警情详情",
	    position: {
	        top: "100px"
	      },
		content: "<%=path%>/alarmWeb/gotoAlarmInfo.do?jjdbh=" + jjdbh,
		iframe : true
		//okCallback:onClose,
		//closeCallback:onClose
		});
}
</script>