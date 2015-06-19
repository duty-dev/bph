package com.tianyi.bph.service.admin;

import java.util.List;

import com.tianyi.bph.domain.system.IconTypeDetail;
import com.tianyi.bph.query.admin.TypeQuery;

public interface TypeService {
	
	public List<IconTypeDetail> getTypeList(TypeQuery typeQuery);
	
	public void addType(IconTypeDetail iconTypeDetail);
	
	public void deleteTypeById(int id);
	
	public List<IconTypeDetail> getTypeListByGoupId(int iconGroupId);
	
	public void updateType(IconTypeDetail iconTypeDetail);
	
	public IconTypeDetail selectByPrimaryKey(Integer id);

}
