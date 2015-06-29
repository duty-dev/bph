<%@ page language="java" pageEncoding="UTF-8"%>
  
<!--日历 s-->
<input type="hidden" id="dutyyears" value="${dutyyears }" />
<input type="hidden" id="dutymonth" value="${dutymonth }" />
	<div class="dateBox">
		<!--日历主窗体 s-->
    	<div class="dateBoxMain">
    		<div class="dateBoxMainTItle">
        		<div class="dateBoxMainTItleTxt1">报备月程表</div>
	            <div class="dateBoxMainTItleBox">
	            	<div class="dateBoxMainTItleBoxMain">
	                	<ul>
	                    	<li><img src="<%=basePath %>dutydata/dutyprepare/calendarImage/dateLast.png" onclick="getDateClick('last')" /></li>
	                        <li><span class="lispan" id="sp_years"></span></li>
	                        <li><span class="lispan" id="sp_month"></span></li>
	                        <li><img  class="liimg" src="<%=basePath %>dutydata/dutyprepare/calendarImage/dateNext.png" onclick="getDateClick('next')" /></li>
                    	</ul>
	                </div>
	            </div>	
	            <!-- 清楚本月报备数据以及清空剪切板 -->
	            <div class="dateBoxMainTItleTxt">
	            	<div class="dateBoxMainTItleTxtDIv">
	            		<button class="fl ty-btn-clear mt4" title="清除当月所有报备数据" onclick="clearAlldutyData()"></button>
	            		<button class="fl ml10 ty-btn-cancelcopy mt4" title="取消剪贴板数据" onclick="clearClipbord()"></button>
	            		<!-- 
	                    <a href="javascript:void(0);" onclick="clearAlldutyData()"><img src="<%=basePath %>dutydata/dutyprepare/calendarImage/canelpast.png" alt="清除当月所有报备数据" title="清除当月所有报备数据" /></a>
	                    <a href="javascript:void(0);" onclick="clearClipbord()"><img src="<%=basePath %>dutydata/dutyprepare/calendarImage/past.png" alt="取消剪贴板数据" title="取消剪贴板数据"  /></a>
	                	 -->
	                </div>
	            </div>
        	</div>
        	<!-- 日历主界面 -->
        	<div id="dateBoxMain" class="dateBoxMain">
	        	 <table width="100%" height="16px;" cellpadding="0" cellspacing="0">
                    <tr>
	                    <th style="text-align:center;color:red"> 日</th>
	                    <th style="text-align:center;color:black"> 一</th>
	                    <th style="text-align:center;color:black"> 二</th>
	                    <th style="text-align:center;color:black"> 三</th>
	                    <th style="text-align:center;color:black"> 四 </th>
	                    <th style="text-align:center;color:black"> 五</th>
	                    <th style="text-align:center;color:red"> 六</th>
                    </tr>
	            </table>
		        <div class="dateBoxMainDate" id="dateBoxMainDate" style="overflow:auto">
		            <table id="dateTable" class="" width="100%" height="100%" style="font-size:12px" >
		                <tbody id="dateBody"> 
		                </tbody> 
		            </table>
		        </div> 
        	</div>
    	</div>
	</div>
	
<script id="dutyItem-template" type="text/x-kendo-template">
    <span> #: item.displayName     #</span> 
</script>
<div style="display:none"> 
	<div id="dutyItemTList" style="height:500px">  
		<div id="dutyItemTVBox"> 
        	<div id="dutyItemTV"></div>
        </div>
     </div> 
</div> 