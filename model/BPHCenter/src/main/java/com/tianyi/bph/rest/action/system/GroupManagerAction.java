package com.tianyi.bph.rest.action.system;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianyi.bph.BaseLogController;
import com.tianyi.bph.common.MessageCode;
import com.tianyi.bph.common.ReturnResult;
import com.tianyi.bph.domain.system.GroupManager;
import com.tianyi.bph.domain.system.JsonPoint;
import com.tianyi.bph.service.system.GroupManagerService;
import com.tianyi.bph.service.system.GroupOtherService;

@Controller
@RequestMapping("/client/groupManager")
public class GroupManagerAction extends BaseLogController{
	
	private static final Logger log=LoggerFactory.getLogger(GroupManagerAction.class);
	
	@Autowired GroupManagerService groupManagerService;
	@Autowired GroupOtherService groupOtherService;
	/**
	 * 保存收藏信息
	 * @param groupName
	 * @param userId
	 * @param organId
	 * @param shareType
	 * @param jsonData
	 * @return
	 */
	@RequestMapping(value="/saveGroupInfo.do")
	@ResponseBody
	public ReturnResult saveGroupInfo(
		@RequestParam(value="groupName",required=true) String groupName,
		@RequestParam(value="userId",required=true) Integer userId,
		@RequestParam(value="organId",required=true) Integer organId,
		@RequestParam(value="shareType",required=true) Integer shareType,
		@RequestParam(value="groupType",required=true) Integer groupType,
		@RequestParam(value="sourceData",required=false) String sourceData,
		@RequestParam(value="areaType",required=false) Integer areaType,
		@RequestParam(value="areaContent",required=false) String areaContent,
		HttpServletRequest request){
		int id;
		try {
			GroupManager groupManager=new GroupManager();
			groupManager.setGroupName(groupName);
			groupManager.setUserId(userId);
			groupManager.setOrganId(organId);
			groupManager.setShareType(shareType);
			groupManager.setSourceData(sourceData);
			groupManager.setAreaType(areaType);
			groupManager.setAreaContent(areaContent);
			groupManager.setCreateTime(new Date());
			groupManager.setGroupType(groupType);
			id=groupManagerService.insert(groupManager);
		} catch (Exception e) {
			return ReturnResult.FAILUER("保存收藏信息失败");
		}
		addLog(request, "添加收藏信息成功", 2);
		return ReturnResult.MESSAGE(MessageCode.STATUS_SUCESS,"保存收藏信息成功",id);
	}
	
	
	/**
	 * 修改收藏信息
	 * @param groupName
	 * @param userId
	 * @param organId
	 * @param type
	 * @param shareType
	 * @param listId
	 */
	@RequestMapping(value="/updateGroupInfo.do")
	@ResponseBody
	public ReturnResult updateGroupInfo(
		@RequestParam(value="groupId",required=true) Integer groupId,
		@RequestParam(value="groupName",required=false) String groupName,
		@RequestParam(value="userId",required=false) Integer userId,
		@RequestParam(value="organId",required=false) Integer organId,
		@RequestParam(value="groupType",required=false) Integer groupType,
		@RequestParam(value="shareType",required=false) Integer shareType,
		@RequestParam(value="sourceData",required=false) String sourceData,
		@RequestParam(value="areaType",required=false) Integer areaType,
		@RequestParam(value="areaContent",required=false) String areaContent,
		HttpServletRequest request){
		
		try {
			GroupManager groupManager=new GroupManager();
			groupManager.setGroupId(groupId);
			groupManager.setGroupName(groupName);
			groupManager.setUserId(userId);
			groupManager.setOrganId(organId);
			groupManager.setShareType(shareType);
			groupManager.setSourceData(sourceData);
			groupManager.setAreaType(areaType);
			groupManager.setAreaContent(areaContent);
			groupManager.setGroupType(groupType);
			int id=groupManagerService.updateByPrimaryKeySelective(groupManager);
			if(id ==0){
				return ReturnResult.FAILUER("修改信息失败或收藏列表不存在");
			}
		} catch (Exception e) {
			return ReturnResult.FAILUER("修改信息失败");
		}
		addLog(request, "修改收藏信息成功", 2);
		return ReturnResult.MESSAGE(MessageCode.STATUS_SUCESS,"修改信息成功");
	}
	
	
	/**
	 * 添加资源
	 * @param groupId
	 * @param jsonData
	 * @return
	 */
	@RequestMapping(value="/saveByJsonData.do")
	@ResponseBody
	public ReturnResult saveByJsonData(
			@RequestParam(value="groupId",required=true) Integer groupId,
			@RequestParam(value="sourceData",required=true) String sourceData,
			HttpServletRequest request
			){
		try {
			GroupManager groupManager=new GroupManager();
			groupManager.setGroupId(groupId);
			groupManager.setSourceData(sourceData);
			groupManagerService.saveByJsonData(groupManager);
			
		} catch (Exception e) {
			return ReturnResult.MESSAGE(MessageCode.STATUS_FAIL,"添加资源失败");
		}
		addLog(request, "添加资源成功", 2);
		return ReturnResult.MESSAGE(MessageCode.STATUS_SUCESS,"添加资源成功");
	}
	/**
	 * 删除资源
	 * @param groupId
	 * @param listId
	 * @return
	 */
	@RequestMapping(value="/deleteSource.do")
	@ResponseBody
	public ReturnResult deleteSource(
			@RequestParam(value="groupId",required=true) Integer groupId,
			@RequestParam(value="sourceId",required=true) Integer sourceId,
			HttpServletRequest request
			){
		try {
			GroupManager groupManager= new GroupManager();
			groupManager.setGroupId(groupId);
			groupManager.setSourceId(sourceId);
			groupManagerService.deleteSource(groupManager);
		} catch (Exception e) {
			log.debug("删除资源失败",e.getMessage());
			return ReturnResult.MESSAGE(MessageCode.STATUS_FAIL,"删除资源失败");
		}
		addLog(request, "删除资源成功", 2);
		return ReturnResult.MESSAGE(MessageCode.STATUS_SUCESS,"删除资源成功");
	}
	/**
	 * 获取收藏列表
	 * @param groupId
	 * @return
	 */
	@RequestMapping(value="/getGroupInfo.do")
	@ResponseBody
	public ReturnResult getGroupInfo(
			@RequestParam(value="userId",required=true)Integer userId){
		
		List<GroupManager> list=groupManagerService.getListByUserId(userId);
		
		return ReturnResult.MESSAGE(MessageCode.STATUS_SUCESS,"查询成功",list);
	}
	
	/**
	 * 删除收藏列表
	 * @param groupId
	 * @return
	 */
	@RequestMapping(value="/deleteGroupInfo.do")
	@ResponseBody
	public ReturnResult deleteGroupInfo(
			@RequestParam(value="groupId",required=true)Integer groupId,
			@RequestParam(value="groupType",required=true)Integer groupType,
			HttpServletRequest request
			){
		try {
			groupManagerService.deleteByPrimaryKey(groupId,groupType);
		
		} catch (Exception e) {
			log.debug("删除收藏信息失败");
			return ReturnResult.FAILUER("删除异常，删除失败");
		}
		addLog(request, "删除收藏列表成功", 2);
		return ReturnResult.MESSAGE(MessageCode.STATUS_SUCESS,"删除成功");
	}
	
}
