package com.tianyi.bph.service.impl.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianyi.bph.common.Constants;
import com.tianyi.bph.common.annotation.MQDataInterceptor;
import com.tianyi.bph.dao.basicdata.GpsMapper;
import com.tianyi.bph.dao.basicdata.IconsMapper;
import com.tianyi.bph.domain.basicdata.Icons;
import com.tianyi.bph.domain.basicdata.IconsType;
import com.tianyi.bph.query.admin.IconsQuery;
import com.tianyi.bph.query.basicdata.IconsVM;
import com.tianyi.bph.service.admin.IconsService;

/**
 * 图标接口层实现
 * 
 * @author lq
 * 
 */
@Service
public class IconsServiceImpl implements IconsService {

	@Autowired IconsMapper iconsMapper;

	@Autowired GpsMapper gpsMapper;
	/**
	 * (non-Javadoc)
	 * 
	 * @see com.tianyi.drs.basedata.service.IconsService#loadCount(Map<String,
	 *      Object> map)
	 */
	public int loadCount(IconsQuery iconsQuery) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageStart", iconsQuery.getPageStart());
		map.put("pageSize", iconsQuery.getPageSize());
		return iconsMapper.loadCount(map);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.tianyi.drs.basedata.service.IconsService#loadList(Map<String,
	 *      Object> map)
	 */
	public List<IconsVM> loadList(IconsQuery iconsQuery) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageStart", iconsQuery.getPageStart());
		map.put("pageSize", iconsQuery.getPageSize());
		return iconsMapper.loadList(map);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.tianyi.bph.service.admin.drs.basedata.service.IconsService#deleteByPrimaryKey(int
	 *      id)
	 */
	public int deleteByPrimaryKey(int id) {
		// TODO Auto-generated method stub
		iconsMapper.deleteByPrimaryKey(id);
		return id;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.tianyi.bph.service.admin.drs.basedata.service.IconsService#updateByPrimaryKey(Icons
	 *      icons)
	 */
	public Icons updateByPrimaryKey(Icons icons) {
		// TODO Auto-generated method stub
		iconsMapper.updateByPrimaryKey(icons);
		return icons;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.tianyi.bph.service.admin.drs.basedata.service.IconsService#insert(Icons icons)
	 */

	@MQDataInterceptor(type = Constants.MQ_TYPE_ICON, operate = Constants.MQ_OPERATE_ADD)
	public Icons insert(Icons icons) {
		// TODO Auto-generated method stub
		iconsMapper.insert(icons);
		return icons;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.tianyi.bph.service.admin.drs.basedata.service.IconsService#loadById(int id)
	 */
	public Icons loadById(int id) {
		// TODO Auto-generated method stub
		return iconsMapper.loadById(id);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.tianyi.drs.basedata.service.IconsService#deleteByIds(Map<String,
	 *      Object> map)
	 */
	@MQDataInterceptor(type = Constants.MQ_TYPE_ICON, operate = Constants.MQ_OPERATE_DELETE)
	public void deleteByIds(Map<String, Object> map) {
		// TODO Auto-generated method stub
		iconsMapper.deleteByIds(map);
	}

	public List<Icons> getIconsInfo() {
		// TODO Auto-generated method stub
		return iconsMapper.getIconsInfo();
	}

	@Override
	public Icons selectByPrimaryKey(Integer iconId) {
		// TODO Auto-generated method stub
		return iconsMapper.selectByPrimaryKey(iconId);
	}

	@Override
	public List<IconsType> selectIconType() {
		// TODO Auto-generated method stub
		return iconsMapper.selectIconType();
	}

	@Override
	public List<Icons> findByIdAndGpsId(String iconId) {
		// TODO Auto-generated method stub
		return iconsMapper.findByIdAndGpsId(iconId);
	}

	@Override
	public int saveOrUpdate(Icons icons) {
		// TODO Auto-generated method stub
		if (icons.getId() > 0) {
			iconsMapper.updateByPrimaryKey(icons);
		} else {
			iconsMapper.insert(icons);
		} 
		return icons.getId();
	}

	@Override
	public int updateIconsInfoById(int iconId) {
		// TODO Auto-generated method stub
		iconsMapper.updateGpsById(iconId);
		iconsMapper.deleteByPrimaryKey(iconId);
		return iconId;
	}
}
