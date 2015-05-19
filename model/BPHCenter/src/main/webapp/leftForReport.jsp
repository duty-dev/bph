<%@ page language="java" pageEncoding="UTF-8"%>
 
<div id="navigationLeft">
        <div class="line1-mid box">
          <div class="t1-start"></div>
          <div class="end"></div>
        </div>
        <div class="pull-left box">
          <div> 
            <div class="hide" onclick="arrowZoom();"></div> 
          </div>
        </div>
        <div class="pull-right box">
          <div class="line7"></div>
        </div>
        <div class="tree-box clear"><!----机构树---->
        <div id="box">
             <div class="clear">
          		 <div class="title box">${requestScope.organName} </div> 
          		 <div>
          			<ul>
          				<c:forEach items="${requestScope.subOrgList}" var="organ">
          					<li>
          			 			${organ.shortName}
          					</li>
          				</c:forEach>
          			</ul>
          		</div>
          	 </div>
          	<div> 
          		 <div class="clear">
            		<div class="ty-input mt0">
            			<label>警情类型</label>
            		</div>
            		<div>
            			<span id="btnAddType1" class="k-button"  onclick="">添加</span>
            		</div>
            		<div>  
            		 	<ul>
            		 		<li>
            		 			刑事案件
            		 		</li>
            		 		<li>
            		 			治安案件
            		 		</li>
            		 	</ul>
            		</div>
            	</div>
          	</div>
          	<div> 
          		 <div class="clear">
            		<div class="ty-input mt0">
            			<label>警情类型</label>
            		</div>
            		<div>  
            			<span id="btnAddType2" class="k-button"  onclick="">添加</span>  
            		</div>
            		<div>  
            		 	<table>
            		 		<tr>
            		 			<td>
            		 				<input id="ckalarmLevel1" type="checkbox" value="1">一级警情
									<input id="ckalarmLevel2" type="checkbox" value="2">二级警情
									<input id="ckalarmLevel3" type="checkbox" value="3">三级警情
            		 			</td> 
            		 		</tr>
            		 	</table>
            		</div>
            	</div>
          	</div>
          	<div> 
          		 <div class="clear">
            		<div class="ty-input mt0">
            			<label>时间</label>
            		</div>
            		<div>  
            			<span id="btnAddType2" class="k-button"  onclick="">更多条件</span>  
            		</div>
            		<div>  
            		  	<label><input id="radio_bymonth" type="radio" name="timeType" value="0" onclick="" />按月查询(不超过12月)</label>
            		  	<label>起</label><input id="dpSDate" /><label>止</label><input id="dpEDate"  />
            		  	<label><input id="radio_byday" type="radio" name="timeType" value="1" onclick="" />按天查询(不超过31天)</label>
            			<label>起</label><input id="dpSDay"   /><label>止</label><input id="dpEDay"   />
            		</div>
            	</div>
          	</div>
		</div>
        </div><!----机构树结束---->
        <div class="line8 box"></div>
        <div class="line2"></div>
</div>    
<script type="text/javascript">
$(function() {
		$("#dpSDate").kendoDatePicker();	
		$("#dpEDate").kendoDatePicker();	
		$("#dpSDay").kendoDatePicker();	
		$("#dpEDay").kendoDatePicker();		  
});
	 var cf = true;
	        function arrowZoom(){
    		//箭头点击收放效果
    			if(cf){
    				$("#navigationLeft").hide('fast',function(){
    					if($("#arrowXg1").length ==0){
    						$("#main-nav").append('<div class="temp"><div class="show" id="arrowXg1"></div></div>');
    					}
    					$("#arrowXg1").css({"position":"absolute","top":11,"left":0}).bind("click",function(){
    						$("#arrowXg1").parent().remove();
    						$("#content").animate({"margin-left":"240px"},'slow',function(){
    							$("#navigationLeft").show('fast');
    						});
    						cf=true;
    					});
    					$("#content").animate({"margin-left":"0"},'slow');
    				});
    				cf=false;
    			}
	        }
</script>
