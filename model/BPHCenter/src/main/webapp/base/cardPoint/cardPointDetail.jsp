<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
<title>扁平化指挥系统</title>
<%@ include file="../../emulateIE.jsp" %>	
</head>
<body>

	<script type="text/javascript">
	var zNodes;
	$(document).ready(function(){
		$("#orgTypeCode").val($("#typeCode").val());
		$.ajax({
			url:"<%=basePath%>web/organx/tree.do",
			type:"post",
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
		
		var circleLayer = ${cardPoint.circleLayer};
		var t = document.getElementById("circleLayer");
		for(var i=0; i<t.length; i++){//给select赋值  
            if(circleLayer == t.options[i].value){  
                t.options[i].selected=true;
            }  
        } 
	});  
	
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
	                   /* $("#parent").val(selectedNode.id);
	                   $("#parentId").val(selectedNode.name); */
	               }
	           }
	       };
	 
	 //修改卡点
  	 function editCardPoint(){
  		 $.ajax({
  			url:"<%=basePath%>web/cardPoint/modifyCardPoint.do",
  			type:"post",
  			dataType:"json",
  			data:{
  				cardPointId:$("#cardPointId").val(),
  				name:$("#name").val(),
  				commGroupId:$("#commGroupId").val(),
  				commEquipment:$("#commEquipment").val(),
  				peoplePoliceCount:$("#peoplePoliceCount").val(),
  				trafficPoliceCount:$("#trafficPoliceCount").val(),
  				armsPoliceCount:$("#armsPoliceCount").val(),
  				circleLayer:$("#circleLayer").val(),
  				assignment:$("#assignment").val()
  			},
	  		 success:function(msg){
	  			if(msg.code==200){
	  				alert(msg.description);
	  				window.location.href="<%=basePath%>web/cardPoint/queryCardPointPageList.action";
	  				<%-- window.location.href="<%=basePath%>web/organx/gotoOrganList.action"; --%>
	  			}else{
	  				alert(msg.description);
	  			}
	  		}
  		});
  	}
  </script>
	
	<div class="mid"><!----中部---->
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
        <ul id="treeDemo" class="ztree">  
        </ul>  
      </div> <!----机构树结束---->
      <div><img src="<%=path %>/Skin/Default/images/line3.png"></div>
      <div class="mt16"><img src="<%=path %>/Skin/Default/images/line2.png"></div>
    </div><!----中部管理树结束---->
    
    
    <div id="using" class="fl"><!----应用界面---->
       
       <div class="mt5">
        <div class="fl mt5 mr5"><img src="<%=path %>/Skin/Default/images/mark3.png"></div>
        <h2>卡点编辑</h2>
      </div>
      <hr class="hr mt8">
      <div class="mid-form auto mt10"><!----表单---->
        <table class="form-table">
        <input type="hidden" id="cardPointId" value="${cardPoint.cardPointId}"/>
        <%-- <input id="typeCode" value="${organ.orgTypeCode}" type="hidden"/> --%>
          <tr>
            <td>卡点名称：</td>
            <td>
              <table class="text-table">
                <tr>
                  <td class=" text-left"></td>
                  <td><input id="name" type="text" class="form-control w258 text" value="${cardPoint.name}" placeholder="等待输入..."></td>
                  <td class="text-right"></td>
                </tr>
              </table>
            </td>
          </tr>
           <tr>
            <td>通讯组号：</td>
            <td>
              <table class="text-table">
                <tr>
                  <td><img src="<%=path %>/Skin/Default/images/text-left.png"></td>
                  <td><input id="commGroupId" type="text" class="form-control w258 text"  placeholder="等待输入..."></td> <%-- value="${cardPoint.commGroupId}" --%>
                  <td><img src="<%=path %>/Skin/Default/images/text-right.png"></td>
                </tr>
              </table>
            </td>
          </tr>
          <tr>
            <td>通讯设备：</td>
            <td>
              <table class="text-table">
                <tr>
                  <td><img src="<%=path %>/Skin/Default/images/text-left.png"></td>
                  <td><input id="commEquipment" type="text" class="form-control w258 text"  placeholder="等待输入..."></td> <%-- value="${cardPoint.commEquipment}" --%>
                  <td><img src="<%=path %>/Skin/Default/images/text-right.png"></td>
                </tr>
              </table>
            </td>
          </tr>
          <tr>
            <td>民警：</td>
            <td>
              <table class="text-table">
                <tr>
                  <td><img src="<%=path %>/Skin/Default/images/text-left.png"></td>
                  <td><input id="peoplePoliceCount" type="text" class="form-control w258 text" value="${cardPoint.peoplePoliceCount}" placeholder="等待输入..."></td>
                  <td><img src="<%=path %>/Skin/Default/images/text-right.png"></td>
                </tr>
              </table>
            </td>
          </tr>
          <tr>
            <td>交警：</td>
            <td>
              <table class="text-table">
                <tr>
                  <td><img src="<%=path %>/Skin/Default/images/text-left.png"></td>
                  <td><input id="trafficPoliceCount" type="text" class="form-control w258 text" value="${cardPoint.trafficPoliceCount}" placeholder="等待输入..."></td>
                  <td><img src="<%=path %>/Skin/Default/images/text-right.png"></td>
                </tr>
              </table>
            </td>
          </tr>
          <tr>
            <td>武警：</td>
            <td>
              <table class="text-table">
                <tr>
                  <td><img src="<%=path %>/Skin/Default/images/text-left.png"></td>
                  <td><input id="armsPoliceCount" type="text" class="form-control w258 text" value="${cardPoint.armsPoliceCount}" placeholder="等待输入..."></td>
                  <td><img src="<%=path %>/Skin/Default/images/text-right.png"></td>
                </tr>
              </table>
            </td>
          </tr>
          <tr>
            <td>所属圈层：</td>
            <td>
               <select id="circleLayer" class="form-control w258" name="circleLayer" value="${cardPoint.circleLayer}">
		    		<option value="1">第一圈层</option>
		    		<option value="2">第二圈层</option>
		    		<option value="3">第三圈层</option>
	    		</select>
            </td>
          </tr>
          <tr>
            <td>职责：</td>
            <td>
               <textarea id="assignment" class=" textarea w640 h260 mt12" >${cardPoint.assignment}</textarea>
            </td>
          </tr>
        </table>
        <hr class="hr mt20">
        <div>
          <a href="#" onclick="editCardPoint()" class="button-red auto mt40">确认添加</a>
        </div>
      </div><!----表单结束---->
      <hr class="hr mt10">
</div>
</body>
</html>

