package com.tianyi.bph.service.impl.system;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tianyi.bph.common.JsonUtils;
import com.tianyi.bph.dao.system.AreaGroupMapper;
import com.tianyi.bph.dao.system.GroupManagerMapper;
import com.tianyi.bph.dao.system.GroupOtherMapper;
import com.tianyi.bph.domain.system.AreaGroup;
import com.tianyi.bph.domain.system.GroupManager;
import com.tianyi.bph.domain.system.GroupOther;
import com.tianyi.bph.domain.system.JsonPoint;
import com.tianyi.bph.domain.system.JsonPointVO;
import com.tianyi.bph.exception.RestException;
import com.tianyi.bph.query.system.GroupManagerExample;
import com.tianyi.bph.service.system.GroupManagerService;

@Service
@Transactional
public class GroupManagerServiceImpl implements GroupManagerService {

	@Autowired
	GroupManagerMapper groupDao;
	@Autowired
	GroupOtherMapper groupOtherDao;
	@Autowired
	AreaGroupMapper areaGroupDao;

	@Override
	public int deleteByPrimaryKey(Integer groupId,Integer groupType) {
		// TODO Auto-generated method stub
		if (groupId == null) {
			throw new RestException("对象不能为空");
		}
		if(groupType==1){
			groupOtherDao.deleteByPrimaryKey(groupId);
		}else{
			areaGroupDao.deleteByPrimaryKey(groupId);
		}
		int i = groupDao.deleteByPrimaryKey(groupId);

		return i;
	}

	@Override
	public int insert(GroupManager record) {
		// TODO Auto-generated method stub
		if (record == null) {
			throw new RestException("对象不能为空");
		}
		groupDao.insert(record);
		if(record.getGroupType()==1){//资源收藏
			if (!StringUtils.isEmpty(record.getSourceData())) {
				JSONArray array = JSONArray.fromObject(record.getSourceData());
				for (int i = 0; i < array.size(); i++) {
					JSONObject o = (JSONObject) array.get(i);
					int type = o.getInt("sourceType");
					int id = o.getInt("sourceId");
					GroupOther other = new GroupOther();
					other.setGroupId(record.getGroupId());
					other.setSourceType(type);
					other.setSourceId(id);
					groupOtherDao.insert(other);
				}
			}
		}else{//区域收藏
			AreaGroup areaGroup = new AreaGroup();
			areaGroup.setGroupId(record.getGroupId());
			areaGroup.setAreaGroupType(record.getAreaType());
			areaGroup.setAreaGroupContent(record.getAreaContent());
			areaGroupDao.insert(areaGroup);
		}
		
		return record.getGroupId();
	}

	@Override
	public int insertSelective(GroupManager record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<GroupManager> selectByExample(GroupManagerExample example) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GroupManager selectByPrimaryKey(Integer groupId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateByExampleSelective(GroupManager record,
			GroupManagerExample example) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByExample(GroupManager record, GroupManagerExample example) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByPrimaryKeySelective(GroupManager record) {
		// TODO Auto-generated method stub
		if (record == null) {
			throw new RestException("对象不能为空");
		}
		int id = 0;
		GroupManager group = groupDao.selectByPrimaryKey(record.getGroupId());
		if (group != null) {
			id = groupDao.updateByPrimaryKeySelective(record);
			if(record.getGroupType()==1){
				if (!StringUtils.isEmpty(record.getSourceData())) {
					groupOtherDao.deleteByPrimaryKey(record.getGroupId());

					JSONArray array = JSONArray.fromObject(record.getSourceData());
					for (int i = 0; i < array.size(); i++) {
						JSONObject o = (JSONObject) array.get(i);
						int type = o.getInt("sourceType");
						int Id = o.getInt("sourceId");
						GroupOther other = new GroupOther();
						other.setGroupId(group.getGroupId());
						other.setSourceType(type);
						other.setSourceId(Id);
						groupOtherDao.insert(other);
					}
				}
			}else{
				AreaGroup areagroup =new AreaGroup();
				areagroup.setGroupId(record.getGroupId());
				areagroup.setAreaGroupType(record.getAreaType());
				areagroup.setAreaGroupContent(record.getAreaContent());
				areaGroupDao.updateByExampleSelective(areagroup);
			}
		}
		return id;
	}

	@Override
	public int updateByPrimaryKey(GroupManager record) {
		// TODO Auto-generated method stub
		return groupDao.updateByPrimaryKey(record);
	}

	@Override
	public List<GroupManager> getListByUserId(Integer userId) {
		if (userId == null) {
			throw new RestException("userId 不能为空");
		}
		List<GroupManager> groupList = groupDao.getListByUserId(userId);
		if(groupList != null && groupList.size()>0){
			for (GroupManager groupManager : groupList) {//区域收藏
				if(groupManager.getAreaGroup() != null){
					if(!StringUtils.isEmpty(groupManager.getAreaGroup().getAreaGroupContent())){
						String aa=groupManager.getAreaGroup().getAreaGroupContent().replaceAll("\"","'");
						groupManager.getAreaGroup().setAreaGroupContent(aa);
					}else{
						groupManager.setAreaGroup(null);
					}
				}
				//自定义收藏
				if(groupManager.getGroupOther() !=null && groupManager.getGroupOther().size()>0){
					for(GroupOther other:groupManager.getGroupOther()){
						if(other.getSourceId()==null ||other.getSourceId()==0){
							groupManager.setGroupOther(null);
						}
					}
				}
			}
		}
		return groupList;
	}

	@Override
	public int saveByJsonData(GroupManager record) {
		// TODO Auto-generated method stub
		if (record == null) {
			throw new RestException("对象不能为空");
		}
		int id=0;
		GroupManager group = groupDao.selectByPrimaryKey(record.getGroupId());
		if (group != null) {
			if (!StringUtils.isEmpty(record.getSourceData())) {
				JSONArray array = JSONArray.fromObject(record.getSourceData());
				for (int i = 0; i < array.size(); i++) {
					JSONObject o = (JSONObject) array.get(i);
					int type = o.getInt("sourceType");
					int Id = o.getInt("sourceId");
					GroupOther other = new GroupOther();
					other.setGroupId(group.getGroupId());
					other.setSourceType(type);
					other.setSourceId(Id);
					groupOtherDao.insert(other);
				}
			}
			id=1;
		}
		return id;
	}

	@Override
	public int deleteSource(GroupManager record) {
		// TODO Auto-generated method stub
		if(record==null){
			throw new RestException("对象不能为空");
		}
		GroupOther orther=new GroupOther();
		orther.setGroupId(record.getGroupId());
		orther.setSourceId(record.getSourceId());
		int i=groupOtherDao.deleteSource(orther);
		return i;
	}

}
