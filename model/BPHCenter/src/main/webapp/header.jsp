<%@ page language="java" pageEncoding="utf-8"%>

<script type="text/javascript">
var conductFlag= false;
var baseFlag =false;
var dutyFlag=false;
var analyzingFlag=false;
var alarmFlag=false;
var setFlag=false;
var num=${num};

var funList=<%=session.getAttribute("funList")%>;
 var baseList=<%=session.getAttribute("baseArray")%>;
var conductList=<%=session.getAttribute("conductArray")%>;
var dutyList=<%=session.getAttribute("dutyArray")%>;
var analyzingList=<%=session.getAttribute("analyzingArray")%>;
var alarmList=<%=session.getAttribute("alarmArray")%>;
var setList=<%=session.getAttribute("setArray")%>;


$(document).ready(function(){
	$("#nMenu").kendoMenu({
	    animation: { close: { effects: "slideIn:up" }}
	});
	/* 解决ie js 数组不支持indexOf 方法 */
	if (!conductList.indexOf)
	{
		conductList.indexOf = function(elt /*, from*/)
	  {
	    var len = this.length >>> 0;
	    var from = Number(arguments[1]) || 0;
	    from = (from < 0)
	         ? Math.ceil(from)
	         : Math.floor(from);
	    if (from < 0)
	      from += len;
	    for (; from < len; from++)
	    {
	      if (from in this &&
	          this[from] === elt)
	        return from;
	    }
	    return -1;
	  };
	}
	if (!setList.indexOf)
	{
		setList.indexOf = function(elt /*, from*/)
	  {
	    var len = this.length >>> 0;
	    var from = Number(arguments[1]) || 0;
	    from = (from < 0)
	         ? Math.ceil(from)
	         : Math.floor(from);
	    if (from < 0)
	      from += len;
	    for (; from < len; from++)
	    {
	      if (from in this &&
	          this[from] === elt)
	        return from;
	    }
	    return -1;
	  };
	}
	
	if (!analyzingList.indexOf)
	{
		analyzingList.indexOf = function(elt /*, from*/)
	  {
	    var len = this.length >>> 0;
	    var from = Number(arguments[1]) || 0;
	    from = (from < 0)
	         ? Math.ceil(from)
	         : Math.floor(from);
	    if (from < 0)
	      from += len;
	    for (; from < len; from++)
	    {
	      if (from in this &&
	          this[from] === elt)
	        return from;
	    }
	    return -1;
	  };
	}
	
	if (!dutyList.indexOf)
	{
		dutyList.indexOf = function(elt /*, from*/)
	  {
	    var len = this.length >>> 0;
	    var from = Number(arguments[1]) || 0;
	    from = (from < 0)
	         ? Math.ceil(from)
	         : Math.floor(from);
	    if (from < 0)
	      from += len;
	    for (; from < len; from++)
	    {
	      if (from in this &&
	          this[from] === elt)
	        return from;
	    }
	    return -1;
	  };
	}
	if (!baseList.indexOf)
	{
		baseList.indexOf = function(elt /*, from*/)
	  {
	    var len = this.length >>> 0;
	    var from = Number(arguments[1]) || 0;
	    from = (from < 0)
	         ? Math.ceil(from)
	         : Math.floor(from);
	    if (from < 0)
	      from += len;
	    for (; from < len; from++)
	    {
	      if (from in this &&
	          this[from] === elt)
	        return from;
	    }
	    return -1;
	  };
	}
	if (!alarmList.indexOf)
	{
		alarmList.indexOf = function(elt /*, from*/)
	  {
	    var len = this.length >>> 0;
	    var from = Number(arguments[1]) || 0;
	    from = (from < 0)
	         ? Math.ceil(from)
	         : Math.floor(from);
	    if (from < 0)
	      from += len;
	    for (; from < len; from++)
	    {
	      if (from in this &&
	          this[from] === elt)
	        return from;
	    }
	    return -1;
	  };
	}
	
	for (var i = 0; i < funList.length; i++) {
		if(conductList.indexOf(funList[i]) != -1){//系统管理
			conductFlag=true;
		}else if(baseList.indexOf(funList[i]) != -1){//基础数据
			baseFlag=true;
		}else if(dutyList.indexOf(funList[i]) != -1){//勤务报备
			dutyFlag=true;
		}else if(analyzingList.indexOf(funList[i]) != -1){//研判分析
			analyzingFlag=true;
		}/* else if(alarmList.indexOf(funList[i]) != -1){//警情处置
			alarmFlag=true;
		} */else if(setList.indexOf(funList[i]) != -1){
			setFlag=true;
		}
	}
	if(conductFlag){
		$("#navMenu").append("<li><a href='javaScript:void(0)' onclick='show_hide(100)'>系统管理</a></li>");
	}
	if(baseFlag){
		$("#navMenu").append("<li><a href='javaScript:void(0)' onclick='show_hide(200)'>基础数据</a></li>");
	}
	if(dutyFlag){
		$("#navMenu").append("<li><a href='javaScript:void(0)' onclick='show_hide(300)'>勤务报备</a></li>");
	}
	if(analyzingFlag){
		$("#navMenu").append("<li><a href='javaScript:void(0)' onclick='show_hide(1000)'>研判分析</a></li>");
	}
/* 	if(alarmFlag){
		$("#navMenu").append("<li><a href='javaScript:void(0)' onclick='show_hide(400)'>警情处置</a></li>");
	} */
	if(setFlag){
		$("#navMenu").append("<li><a href='javaScript:void(0)' onclick='show_hide(500)'>配置管理</a></li>");
	}
	show_hide(num);//初始加载基础数据
});

function show_hide(type){
	if(type=='200'){
		num=200;
		$("#subMenu_5 li").remove();
		for (var i = 0; i < funList.length; i++) {
			if(funList[i]=='200001'){
				$("#subMenu_5").append("<li><a href='<%=path %>/policeWeb/gotoPoliceList.action'>警员管理</a></li>");
			}else if(funList[i]=='200002'){
				$("#subMenu_5").append("<li><a href='<%=path %>/vehicleWeb/gotoVehicleList.action'>警车管理</a></li>");
			}else if(funList[i]=='200003'){
				$("#subMenu_5").append("<li><a href='<%=path %>/weaponWeb/gotoWeaponList.action'>武器管理</a></li>");
			}else if(funList[i]=='200004'){
				$("#subMenu_5").append("<li><a href='<%=path %>/gpsWeb/gotoGpsList.action'>设备管理</a></li>");
			}else if(funList[i]=='200011'){
				$("#subMenu_5").append("<li><a href='<%=path %>/iconsWeb/gotoIconsList.action'>图标管理</a></li>");
			}else if(funList[i]=='200005'){
				$("#subMenu_5").append("<li>"+
				"<a href='<%=path %>/web/cardPoint/queryCardPointPageList.action'>卡点管理</a></li>");
			}else if(funList[i]=='200010'){
				$("#subMenu_5").append("<li>"+
				"<a href='<%=path %>/web/GBPlatForm/toGBPlatForm.action'>视频点位</a></li>");
				$("#subMenu_5").append("<li>"+
				"<a href='<%=path %>/web/Area/toArealist.action?areaType=1'>巡区</a></li>");
				$("#subMenu_5").append("<li>"+
				"<a href='<%=path %>/web/Area/toArealist.action?areaType=2'>社区</a></li>");
				$("#subMenu_5").append("<li>"+
				"<a href='<%=path %>/web/Area/toArealist.action?areaType=3'>辖区</a></li>");
			}
		}
	}else if(type=='100'){
		num=100;
		$("#subMenu_5 li").remove();
		 for (var i = 0; i < funList.length; i++) {
			if(funList[i]=='100001'){
				$("#subMenu_5").append("<li>"+
				"<a href='<%=path %>/web/organx/gotoOrganList.action'>机构管理</a></li>");
			}else if(funList[i]=='100002'){
				$("#subMenu_5").append("<li>"+
				"<a href='<%=path %>/admin/gotoUserList.action'>用户管理</a></li>");
			}else if(funList[i]=='100003'){
				$("#subMenu_5").append("<li>"+
				"<a href='<%=path %>/role/gotoRoleList.action'>角色管理</a></li>");
			}
		}		
	} else if(type=='500'){
		num=500;
		$("#subMenu_5 li").remove();
		 for (var i = 0; i < funList.length; i++) {
			 if(funList[i]=='500001'){
				 $("#subMenu_5").append("<li>"+
					"<a href='<%=path %>/serviceSet/gotoServiceSetList.action'>配置管理</a></li>");
			}
		 }
		<%-- $("#subMenu_5").append("<li>"+
		"<a href='<%=path %>/log/gotoLogList.action'>日志管理</a></li>");
		$("#subMenu_5").append("<li>"+
			"<a href='<%=path %>/map/initMap.action'>地图信息</a></li>");
		$("#subMenu_5").append("<li>"+
		"<a href='<%=path %>/map/initCircleMap.action'>圈层信息</a></li>");
		$("#subMenu_5").append("<li>"+
		"<a href='<%=path %>/map/initAlarmMap.action?organId=1'>常规警情</a></li>"); --%>
	} else if(type=='300'){
		num=300;
		$("#subMenu_5 li").remove();		
		 for (var i = 0; i < funList.length; i++) {
			if(funList[i]=='300001'){
				$("#subMenu_5").append("<li>"+
				"<a href='<%=path %>/dutyRouteWeb/gotoDutyCalendar.action'>勤务报备日程</a></li>");
			}else if(funList[i]=='300002'){
				$("#subMenu_5").append("<li>"+
				"<a href='<%=path %>/dutyReportWeb/gotoDutyReport.action'>勤务综合统计</a></li>");
			}else if(funList[i]=='300003'){
				$("#subMenu_5").append("<li>"+
				"<a href='<%=path %>/dutyTypeWeb/gotoDutyTypeList.action'>勤务类型管理</a></li>");
			}else if(funList[i]=='300004'){
				$("#subMenu_5").append("<li>"+
				"<a href='<%=path %>/dutyGroupRouteWeb/gotoPoliceGroup.action'>警员分组</a></li>");
			}else if(funList[i]=='300005'){
				$("#subMenu_5").append("<li>"+
				"<a href='<%=path %>/dutyGroupRouteWeb/gotoVehicleGroup.action'>警车分组</a></li>");
			}else if(funList[i]=='300006'){
				$("#subMenu_5").append("<li>"+
				"<a href='<%=path %>/dutyGroupRouteWeb/gotoGpsGroup.action'>GPS分组</a></li>");
			}else if(funList[i]=='300007'){
				$("#subMenu_5").append("<li>"+
				"<a href='<%=path %>/dutyGroupRouteWeb/gotoWeaponGroup.action'>武器分组</a></li>");
			}
		}
	}else if(type=='1000'){
		num=1000;
		$("#subMenu_5 li").remove();		
		 for (var i = 0; i < funList.length; i++) {
			if(funList[i]=='1000001'){
				$("#subMenu_5").append("<li>"+
				"<a href='<%=path %>/reportRouteWeb/gotoAlarmStatistics.action'>警情统计</a></li>");
				$("#subMenu_5").append("<li>"+
				"<a href='<%=path %>/reportRouteWeb/gotoFourColorWarning.action'>四色预警设置</a></li>");
			}
		}
	}else if(type=='400'){
		num=400;
		$("#subMenu_5 li").remove();		
		 for (var i = 0; i < funList.length; i++) {
			if(funList[i]=='400001'){
				$("#subMenu_5").append("<li>"+
				"<a href='<%=path %>/alarmWeb/gotoAlarmIndex.action'>常规警情</a></li>");
			}
		}
	}
}

/**
 * 修改密码界面 跳转
 */
function gotoUpdatePassword(){
	var organId = $.trim($("#organId").val());
	var sessionId = $.trim($("#token").val());
	$("#mydialog").tyWindow({
		width: "660px",
		height: "590px",
	    title: "帐号管理",
	    position: {
	        top: "30px"
	      },
		content: "<%=path %>/admin/gotoUpdatePassword.do?userId="+${User.userId}+"&random="+Math.random(),
		iframe : true
		});
	}
</script>
<div id="mydialog"></div>
<div class="navbar">
      <div class="navbar-inner">
        <div class="container-fluid">
          <a class='brand head-logo' href='#'></a>
          <div class="line1-mid">
            <div class="t1-start"></div>
            <div class="end"></div>
          </div>
          <div>
            <div class="lv1"></div>
            <ul class="nav pull-left" id="navMenu">
            </ul>
            <ul  class="nav pull-right" id="nMenu"><!----用户信息---->
              <li><div class="box"  style="color: #81C9F0;">${sessionScope.SESSIN_USERNAME }</div></li>
              <li><div class="box" style="color: #81C9F0;">授权登录</div></li>
              <li style="color: #81C9F0;">工具
              		<ul>
              			<li><a href="javascript: gotoUpdatePassword();">密码修改</a></li>
              		</ul>
              </li>
              <li><a href="<%=path %>/admin/logout.do">退出</a></li>
            </ul><!----用户信息结束---->
          </div>
          <div class="line1-mid clear">
            <div class="line6"></div>
            <div class="b1-end"></div>
          </div>
          <div class="line2 brand"></div>
          
          <ul class="lv2 nav pull-left" id="subMenu_5">
         	<!-- 二级菜单集合 -->
          </ul>
          
          <ul class="nav pull-right">帮助按钮</ul>
        </div>
        <div class="b2-mid">
          <div class="b2-left"></div>
          <div class="end"></div>
        </div>
      </div>
    </div>
    
