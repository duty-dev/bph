package com.tianyi.bph.service.impl.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianyi.bph.dao.system.IconTypeDetailMapper;
import com.tianyi.bph.domain.system.IconTypeDetail;
import com.tianyi.bph.query.admin.TypeQuery;
import com.tianyi.bph.service.admin.TypeService;

@Service
public class TypeServiceImpl implements TypeService{
	
	@Autowired IconTypeDetailMapper iconTypeDetailMapper;

	@Override
	public List<IconTypeDetail> getTypeList(TypeQuery typeQuery) {
		// TODO Auto-generated method stub
		return iconTypeDetailMapper.selectByExample(typeQuery);
	}

	@Override
	public void addType(IconTypeDetail iconTypeDetail) {
		// TODO Auto-generated method stub
		iconTypeDetailMapper.insert(iconTypeDetail);
	}

	@Override
	public void deleteTypeById(int id) {
		// TODO Auto-generated method stub
		iconTypeDetailMapper.deleteByPrimaryKey(id);
	}

	@Override
	public List<IconTypeDetail> getTypeListByGoupId(int iconGroupId) {
		// TODO Auto-generated method stub
		return iconTypeDetailMapper.getTypeListByGoupId(iconGroupId);
	}

	@Override
	public void updateType(IconTypeDetail iconTypeDetail) {
		// TODO Auto-generated method stub
		iconTypeDetailMapper.updateByPrimaryKeySelective(iconTypeDetail);
	}

	@Override
	public IconTypeDetail selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return iconTypeDetailMapper.selectByPrimaryKey(id);
	}

}
