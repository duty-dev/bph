<%@ page language="java" pageEncoding="utf-8"%>

<script type="text/javascript">
var conductFlag= false;
var baseFlag =false;
var dutyFlag=false;
var analyzingFlag=false;
var num=${num};

var funList=<%=session.getAttribute("funList")%>;
 var baseList=<%=session.getAttribute("baseArray")%>;
var conductList=<%=session.getAttribute("conductArray")%>;
var dutyList=<%=session.getAttribute("dutyArray")%>;
var analyzingList=<%=session.getAttribute("analyzingArray")%>;


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
	show_hide(num);//初始加载基础数据
});

function show_hide(type){
		num=500;
		$("#subMenu_5 li").remove();
		$("#subMenu_5").append("<li>"+
		"<a href='<%=path %>/iconsWeb/gotoIconsList.action'>图标管理</a></li>");
		$("#subMenu_5").append("<li>"+
		"<a href='<%=path %>/typeWeb/gotoTypeList.action'>类型管理</a></li>");
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
            <ul class="nav pull-left" id="navMenu"><!----一级菜单---->
            	<li><a href='javaScript:void(0)' onclick="show_hide(500)">系统后台管理</a></li>
            </ul><!----一级菜单结束---->
            <ul  class="nav pull-right" id="nMenu"><!----用户信息---->
              <li><div class="box"  style="color: #81C9F0;">${sessionScope.SESSIN_USERNAME }</div></li>
              <li><div class="box" style="color: #81C9F0;">授权登录</div></li>
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
    
