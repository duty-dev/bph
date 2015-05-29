package com.tianyi.bph.service.system;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tianyi.bph.domain.system.GroupManager;
import com.tianyi.bph.query.system.GroupManagerExample;
/**
 * 保存收藏信息
* @Title: GroupManagerService.java
* @Package com.tianyi.bph.service.system
* @Description: TODO(用一句话描述该文件做什么)
* @author wangxing  
* @date 2015年3月17日 上午10:52:14
* @version V1.0
 */
public interface GroupManagerService {
	int deleteByPrimaryKey(Integer groupId,Integer groupType);

    int insert(GroupManager record);

    int insertSelective(GroupManager record);

    List<GroupManager> selectByExample(GroupManagerExample example);

    GroupManager selectByPrimaryKey(Integer groupId);

    int updateByExampleSelective(@Param("record") GroupManager record, @Param("example") GroupManagerExample example);

    int updateByExample(@Param("record") GroupManager record, @Param("example") GroupManagerExample example);

    int updateByPrimaryKeySelective(GroupManager record);
    
    int saveByJsonData(GroupManager record);
    
    int deleteSource(GroupManager record);

    int updateByPrimaryKey(GroupManager record);
    
    List<GroupManager> getListByUserId(Integer userId);
}
