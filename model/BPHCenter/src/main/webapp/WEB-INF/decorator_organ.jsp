<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<META NAME="Generator" CONTENT="EditPlus">
  <META NAME="Author" CONTENT="">
  <META NAME="Keywords" CONTENT="">
  <META NAME="Description" CONTENT="">
  
<link rel="stylesheet" type="text/css" href="<%=basePath %>Skin/Default/css/global.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath %>Skin/Default/css/base.css"/>
<script type="text/javascript" src="<%=basePath %>JS/jquery-1.9.1.js"></script>
<script type="text/javascript" src="<%=basePath %>JS/jquery-1.11.1.min.js"></script>
<link href="<%=basePath%>JS/ztree/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css"/> 
<script type="text/javascript" src="<%=basePath%>JS/ztree/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="<%=basePath%>JS/ztree/jquery.ztree.core-3.5.js"></script>

<title>机构管理</title>
<script type="text/javascript">
<%-- var zNodes;
var v="";
$(document).ready(function(){
	$.ajax({
		url:"<%=basePath%>web/organx/tree.do",
		type:"get",
		data:{
			name:$("#orgName").val(),
			random:Math.random()
		},
		dataType:"json",
		success:function(msg){
			zNodes=msg.description;
			$.fn.zTree.init($("#treeDemo"), setting, eval('(' + zNodes + ')'));
		}
	});
	 
});  

function searchOrgan(){
	var name=$("#organName").val();
	window.location.href="<%=basePath%>web/organx/gotoOrganList.action?name="+name;
}
	function gotoOrganList(code,name,v){
		window.location.href="<%=basePath%>web/organx/gotoOrganList.action?name="
				+name+"&code="+code+"&params="+v;
	}

    var setting = {  
           view: {  
        	   dblClickExpand: true,
               selectedMulti: false       //禁止多点选中  
           },  
           data: {  
               simpleData: {  
                   enable:true,  
                   idKey: "id",  
                   pIdKey: "parentId",  
                   rootPId: ""  
               }  
           },  
           callback: {  
               onClick: function(treeId, treeNode) {  
            	   var treeObj=$.fn.zTree.getZTreeObj("treeDemo");
                   var nodes=treeObj.transformToArray(treeObj.getNodes());
                   var selectedNode = treeObj.getSelectedNodes()[0];
                   for(var i=0;i<nodes.length;i++){
                	   if(nodes[i].open==true){
                		   v+=nodes[i].id+",";
                	   }
                   }
                   gotoOrganList(selectedNode.code,selectedNode.name,v);
               }
           }
       }; --%> 
</script>
</head>

<body>
  <div class="head"><!----头部---->
    <div class="head-logo"><!----logo---->
      <div class="t1-start dis-cell"></div>
      <div class="w118 line1-mid dis-cell"></div>
      <div class="end dis-cell"></div>
      <div class="m4"><a href="#"><img src="<%=path %>/Skin/Default/images/logo-01.png" /></a></div>
      <div class="b1-start dis-cell"></div>
      <div class="w98 line1-mid dis-cell"></div>
      <div class="dis-cell"><a href="#"><img src="<%=path %>/Skin/Default/images/b1-button1.png" /></a></div>
      
      <div class="clear"></div>
      <div class="mt10"><img src="<%=path %>/Skin/Default/images/line2.png"></div>
    </div><!----logo结束---->
    
    <div id="menu" class="h68 fr"><!----菜单---->
      <div class="line1-mid"><!----一级菜单---->
        <div class="t1-start fl"></div>
        <div class="end fr"></div>
      </div>
      
      <div>
        <div class="mt16 fl"><img src="<%=path %>/Skin/Default/images/guanli-14.png"></div>
        <ul>
          <li><a href="#">信息摘要</a></li>
          <li><a href="#">接警处管理</a></li>
          <li><a href="#">巡逻防控</a></li>
          <li><a href="#">预案管理</a></li>
          <li><a href="#">视频防控</a></li>
          <li><a href="#">警令系统</a></li>
          <li><a href="#">勤务报备</a></li>
          <li><a href="#">基础管理</a></li>
          <li><a href="#">运维管理</a></li>
          <li><a href="#">系统设置</a></li>
        </ul>
        <div class="h26 fr">
          <div class="dis-cell">
             
             <a class="button1" href="<%=path %>/admin/logout.do">退出</a>
             <a class="button1" href="#"></a>
             <div class="lh26 fr mr20"></div>
             <div class="lh26 fr mr20">${sessionScope.SESSIN_USERNAME }  授权登录</div>
          </div>
          
        </div>
      </div><!----一级菜单结束---->
      
      <div class="clear"></div><!----二级菜单---->
      <div class="line1-mid">
        <div class="b1-end fr"></div>
      </div>
      
      <div class="clear"></div>
      
      <div class="mt16 fl"><img src="<%=path %>/Skin/Default/images/mark1.png"></div>
      <div class="h30">
        <ul class="lv2 mt5 ml10">
          <li><a href="#">警员管理</a></li>
          <li><a href="#">警车管理</a></li>
          <li><a href="#">武器管理</a></li>
          <li><a href="#">GPS设备管理</a></li>
          <li><a href="#">卡点管理</a></li>
          <li><a href="#">用户管理</a></li>
          <li><a href="#">权限管理</a></li>
        </ul>
        
        <div class="fr"><a href="#"><img src="<%=path %>/Skin/Default/images/help.png"></a></div>
      </div>

      <div class="b2-mid">
        <div class="fl"><img src="<%=path %>/Skin/Default/images/b2-left.png"></div>
        <div class="fr end"></div>
      </div><!----二级菜单结束---->
      
    </div> <!----菜单结束---->
  </div><!----头部结束---->
  
  <div class="clear"></div>
  
 <%--  <div class="mid"><!----中部---->
    <div class="mid-tree mt10 mr20"><!----中部管理树---->
      <div class="line1-mid">
        <div class="t1-start fl"></div>
        <div class="end fr"></div>
      </div>
      
      <div class="fl w172">
        <div class="mr10 mt8 fl"><img src="<%=path %>/Skin/Default/images/guanli-search.png"></div>
        <div class="search-result fl">1500</div>
        <div class="fr mt8"><a href="#"><img src="<%=path %>/Skin/Default/images/button3.png"></a></div>
        <div class="clear"></div>
        <div>
          <input type="text" id="organName" class="search-input mr5 fl">
          <a href="javascript:void(0)" onclick="searchOrgan()" class="search-button dis-cell"></a>
        </div>
      </div>
      
      <div class="fr mt8">
        <div><a href="#"><img src="<%=path %>/Skin/Default/images/button4.png"></a></div>
        <div><img src="<%=path %>/Skin/Default/images/guanli-53.png"></div>
      </div>
      
      <div class="clear"></div>
       <div class="tree-box mt10"><!----机构树---->
       <input id="orgName" type="hidden" value="${sessionScope.organName}">
       <input id="orgCode" type="hidden" value="${sessionScope.organCode}">
        <ul id="treeDemo" class="ztree">  
        </ul>  
      </div> <!----机构树结束---->
      <div><img src="<%=path %>/Skin/Default/images/line3.png"></div>
      <div class="mt16"><img src="<%=path %>/Skin/Default/images/line2.png"></div>
    </div><!----中部管理树结束---->
  </div> --%><!----中部结束---->
  <sitemesh:write property='body'/>
  
  <div class="clear"></div>
  
  <div class="mt16"><!----下部---->
    <div class="line1-mid">
      <div class="t1-start fl"></div>
      <div class="end fr"></div>
    </div>
    <div class="h26">
      <div class="lh26 fr">2014/7/19 23:23</div>
      <div class=" icon mr5" ><img src="<%=path %>/Skin/Default/images/b-6.png"></div>
      <div class="icon mr20"><img src="<%=path %>/Skin/Default/images/b-5.png"></div>
      <div class="icon mr20"><img src="<%=path %>/Skin/Default/images/b-4.png"></div>
      <div class="icon mr20"><img src="<%=path %>/Skin/Default/images/b-3.png"></div>
      <div class="icon mr20"><img src="<%=path %>/Skin/Default/images/b-2.png"></div>
      <div class="icon mr20"><img src="<%=path %>/Skin/Default/images/b-1.png"></div>
    </div>
    <div class="line1-mid">
      <div class=" b1-start fl"></div>
      <div class=" b1-end fr"></div>
    </div>
  </div><!----下部结束---->
   <script type="text/javascript">
    var i=document.getElementById("using");
    var j=document.getElementById("menu");

    var k=function(){
      j.style.width=document.body.clientWidth-220;
	  i.style.width=document.body.clientWidth-230;
    }
    k();
    window.onresize=k;
  </script>
</body>

</html>

