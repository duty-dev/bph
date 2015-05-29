<jsp:directive.page language="java" pageEncoding="UTF-8"/>
 
<script id="dutyType-checkbox-template" type="text/x-kendo-template">
           <input id="ckAttireType1" type="checkbox"  value="0">
           <div >#: name #</div>
</script>

<script id="policeSource-template" type="text/x-kendo-template"> 
    <span> #: item.name #</span>
	<span> (#: item.number #)</span>
	<span> 　#: item.orgName #</span>
	#item.itemTypeId=2#
</script>

<script id="vehicleSource-template" type="text/x-kendo-template">
    <span> #: item.number #</span>
	<span> (#: item.typeName#  #:item.brand#)</span>
	<span> 　#: item.orgName #</span>
	#item.itemTypeId=1#
</script>

<script id="weaponSource-template" type="text/x-kendo-template">
    <span> #: item.typeName #</span>
	<span> (#: item.number# )</span>
	<span> 　#: item.orgName #</span>
	#item.itemTypeId=3#
</script>

<script id="gpsSource-template" type="text/x-kendo-template">
    <span> #: item.typeName #</span>
	<span> (#: item.gpsName#   #: item.number#)</span>
	<span> 　#: item.orgName #</span>
	#item.itemTypeId=4#
</script>

<script id="dutyItem-template" type="text/x-kendo-template"> 
    <span> #: item.displayName     #</span>
	<button type="button" class="ty-delete-btn" style="margin:0;"  onclick=DutyItemManage.onDeleteNode('#: item.uid #')></button>
</script>

<div id="dutyResource" style="width:25%; float:left;min-width:400px;">
	<div class="demo-section k-header"> 
        <h4>报备资源</h4> 
        <div id="tabstrip-sprites">
        	<ul>
        		<li style="width:24%;height:25px;line-height:0;">
        			警员
        		</li>
        		<li style="width:24%;height:25px;line-height:0;">
        			警车
        		</li>
        		<li style="width:24%;height:25px;line-height:0;">
        			武器
        		</li>
        		<li style="width:24%;height:25px;line-height:0;">
        			定位设备
        		</li>
        	</ul>
        	<div>
				<div class="policeresourcetoolbar"> 
                	<span id="undo" class="k-button" onclick="DutyBaseManage.selectPolResCondition();">过滤条件</span> 
                	<input type="search" class="k-textbox" id="policeresName" name="policeresName" style="width:100px"  placeholder="等待输入..." /><em class="ty-input-end"></em>
                	<span id="undo" class="k-button" style="margin-left:10px;" onclick="DutyBaseManage.searchPoliceResWithOutList();">查询</span>  
                </div>
        		<div id='policeSourceTV'  style="height:700px;overflow;auto"></div>
        	</div>
        	<div>
				<div class="vehicleresourcetoolbar">
                	<span id="undo" class="k-button" onclick="DutyBaseManage.selectVehResCondition();">过滤条件</span> 
                	<input type="search" class="k-textbox" id="vehicleresName" name="vehicleresName" style="width:100px"  placeholder="等待输入..." /><em class="ty-input-end"></em>
                	<span id="undo" class="k-button" style="margin-left:10px;" onclick="DutyBaseManage.searchVehicleResWithOutList();">查询</span> 
                </div>
        		<div id='vehicleSourceTV' style="height:700px;overflow;auto"></div>
        	</div>
			<div>
				<div class="weaponresourcetoolbar">
                	<span id="undo" class="k-button" onclick="DutyBaseManage.selectWepResCondition();">过滤条件</span> 
                	<input type="search" class="k-textbox" id="weaponresName" name="weaponresName" style="width:100px"  placeholder="等待输入..." /><em class="ty-input-end"></em>
                	<span id="undo" class="k-button" style="margin-left:10px;" onclick="DutyBaseManage.searchWeaponResWithOutList();">查询</span> 
                </div>
        		<div id='weaponSourceTV' style="height:700px;overflow;auto"></div>
        	</div>
        	<div>
				<div class="gpsresourcetoolbar">
                	<span id="undo" class="k-button" onclick="DutyBaseManage.selectGpsResCondition();">过滤条件</span> 
                	<input type="search" class="k-textbox" id="gpsresName" name="gpsresName" style="width:100px"  placeholder="等待输入..." /><em class="ty-input-end"></em>
                	<span id="undo" class="k-button" style="margin-left:10px;" onclick="DutyBaseManage.searchGpsResWithOutList();">查询</span> 
                </div>
        		<div id='gpsSourceTV' style="height:700px;overflow;auto"></div>
        	</div>
        </div>
     </div>
</div> 	
<div id="dutyItems"  style="width:60%; float:left;margin-left:10px;">
	<div> 
        <h4>报备明细</h4>
		<div class="dutyItemtoolbar">
			<span class="k-button" onclick="DutyItemManage.addShift()">添加班次</span>
			<span  class="k-button" onclick="DutyItemManage.setShift()">设置班次</span>
            <span  class="k-button" onclick="DutyItemManage.addUserNode()">添加编组</span>
			<span class="k-button" onclick="DutyItemManage.setUserNode()">设置编组</span>
			<span  class="k-button" onclick="DutyItemManage.setTask()">关联任务</span> 
			<span  class="k-button" onclick="DutyItemManage.onDeleteNode()">删除节点</span> 
		</div>
		<div id="dutyItemTVBox"> 
        	<div id="dutyItemTV"></div>
        </div>
     </div>
</div> 
<div id="dialog">
</div>
 

<div id="dutyTempleteWindow" style="display:none">
	<div id="windowTemplete">
		<div class="templeteresourcetoolbar"> 
            <span id="undosure" class="k-button" onclick="DutyPrepareManage.selectTempleteToLoad();">确定</span>  
            <span id="undodelete" class="k-button" onclick="DutyPrepareManage.deleteTempleteById();">删除</span>  
        </div>
		<div id="Templetegrid"></div>
    </div> 
</div>

<div id="dutyTypeSelectWindow" style="display:none">
	<div id="windowDutyType" style="height:550px;overflow:auto">
		<div id="DutyTypetreeList"></div>
    </div> 
</div>


<div id="policeResConWindow" style="display:none">
	<div id="polResConWindow">
		<div><span id="undo" class="k-button" onclick="DutyBaseManage.searchPoliceResWithList();">查询</span></div>
		<div id="polGroupgrid" style="width:45%; float:left"></div>
		<div id="polTypegrid" style="width:45%; float:left"></div>
    </div> 
</div>

<div id="vehicleResConWindow" style="display:none">
	<div id="vehResConWindow">
		<div><span id="undo" class="k-button" onclick="DutyBaseManage.searchVehicleResWithList();">查询</span></div>
		<div id="vehGroupgrid" style="width:45%; float:left"></div>
		<div id="vehTypegrid" style="width:45%; float:left"></div>
    </div> 
</div>

<div id="gpsResConditionWindow" style="display:none">
	<div id="gpsResConWindow">
		<div><span id="undo" class="k-button" onclick="DutyBaseManage.searchGpsResWithList();">查询</span></div>
		<div id="gpsGroupgrid" style="width:45%; float:left"></div>
		<div id="gpsTypegrid" style="width:45%; float:left"></div>
    </div> 
</div>

<div id="weaponResConWindow" style="display:none">
	<div id="wepResConWindow">
		<div><span id="undo" class="k-button" onclick="DutyBaseManage.searchWeaponResWithList();">查询</span></div>
		<div id="wepGroupgrid" style="width:45%; float:left"></div>
		<div id="wepTypegrid" style="width:45%; float:left"></div>
    </div> 
</div>

<div  style="display:none">
	<div id="shiftWindow" >
		<div class="pane-content">
			<div class="demo-section k-header"> 
				<ul>
					<li class="ty-input">
						<label class="ty-input-label" for="txtShiftName">班次名称:</label><input type="text" class="k-textbox"  id="txtShiftName" placeholder="请输入班次名称.."  />
					</li>
					<li class="ty-input">
						<label class="ty-input-label" for="policename">开始时间:</label><input id="tpkBeginTime"  />
					</li>
					<li class="ty-input">
						<label class="ty-input-label" for="policename">结束时间:</label><input id="tpkEndTime"  /><input type="checkbox" id="chkDayType"></input>第二天</label></td>
					</li>
				</ul>
				<p style="float:left;width:100%;margin-top:10px;">
					<span id="btnShiftConfirm" class="k-button" onclick="DutyItemManage.onShiftConfirm()">确定</span>
				</p>
			</div>
		</div>
    </div> 
</div>

<div  style="display:none">
	<div id="userNodeWindow" >
		<div class="pane-content">
			<div class="demo-section k-header"> 
				<ul>
					<li class="ty-input">
						<label class="ty-input-label" for="txtUserNodeName">编组名称:</label><input type="text" class="k-textbox"  id="txtUserNodeName" placeholder="请输入自定义名称..."  />
					</li>
				</ul>
				<p style="float:left;width:100%;margin-top:10px;">
					<span id="btnUserNodeConfirm" class="k-button" onclick="DutyItemManage.onUserNodeConfirm()">确定</span>
				</p>
			</div>
		</div>
    </div> 
</div>

<div  style="display:none">
	<div id="taskWindow" >
		<div class="pane-content">
			<div class="demo-section k-header"> 
				<ul>
					<li>
						<label id="lblPoliceInfo"></label>
						<input type="hidden" id="pol_taskType" />
					</li>
					<li>
						<div id="taskBox" style="height:400px;overflow:auto">
							<div id="taskGrid"></div>
						</div>
					</li>
				</ul>
				<p style="float:left;width:100%;margin-top:10px;">
					<span id="btntaskConfirm" class="k-button" onclick="DutyItemManage.onTaskConfirm()">确定</span>
				</p>
			</div>
		</div>
    </div> 
</div>

<div  style="display:none">
	<div id="templateWindow" >
		<div class="pane-content">
			<div class="demo-section k-header"> 
				<ul>
					<li class="ty-input">
						<label class="ty-input-label" for="txtTemplateName">模板名称:</label><input type="text" class="k-textbox"  id="txtTemplateName" placeholder="请输入模板名称..."  />
					</li>
				</ul>
				<p style="float:left;width:100%;margin-top:10px;">
					<span id="btnSaveTemplate" class="k-button" onclick="DutyPrepareManage.onSaveTemplate()">确定</span>
				</p>
			</div>
		</div>
    </div> 
</div>

<div  style="display:none">
	<div id="calendarWindow" >
		<div class="pane-content">
			<div class="demo-section k-header"> 
				<ul>
					<li class="ty-input">
						<div id="cc"></div>
					</li>
				</ul>
				<p style="float:left;width:100%;margin-top:10px;">
					<span id="btnCopyDuty" class="k-button" onclick="DutyPrepareManage.onCopyDuty()">确定</span>
				</p>
			</div>
		</div>
    </div> 
</div>

