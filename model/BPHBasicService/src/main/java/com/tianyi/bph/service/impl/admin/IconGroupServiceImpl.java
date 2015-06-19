package com.tianyi.bph.service.impl.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianyi.bph.dao.system.IconGroupMapper;
import com.tianyi.bph.domain.system.IconGroup;
import com.tianyi.bph.query.admin.IconsQuery;
import com.tianyi.bph.service.admin.IconGroupService;

@Service
public class IconGroupServiceImpl implements IconGroupService{
	
	@Autowired IconGroupMapper iconGroupMapper;

	@Override
	public List<IconGroup> getIconGroupList(IconsQuery iconsQuery) {
		// TODO Auto-generated method stub
		IconGroup iconGroup = new IconGroup();
		return iconGroupMapper.selectByExample(iconsQuery);
	}

	@Override
	public void deleteIconGroupById(int groupId) {
		// TODO Auto-generated method stub
		iconGroupMapper.deleteByPrimaryKey(groupId);
	}
}
