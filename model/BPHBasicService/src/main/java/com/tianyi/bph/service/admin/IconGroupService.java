package com.tianyi.bph.service.admin;

import java.util.List;

import com.tianyi.bph.domain.system.IconGroup;
import com.tianyi.bph.query.admin.IconsQuery;

public interface IconGroupService {
	
	public List<IconGroup> getIconGroupList(IconsQuery iconsQuery);

}
