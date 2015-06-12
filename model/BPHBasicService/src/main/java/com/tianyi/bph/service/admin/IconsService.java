package com.tianyi.bph.service.admin;

import java.util.List;
import java.util.Map;

import com.tianyi.bph.domain.basicdata.Icons;
import com.tianyi.bph.domain.basicdata.IconsType;
import com.tianyi.bph.query.admin.IconsQuery;
import com.tianyi.bph.query.basicdata.IconsVM;
 
/**
 * 图片图标后台逻辑服务层
 * @author lq
 *
 */
public interface IconsService {
	/**
	 * 查询记录总数
	 * @param map
	 * @return
	 */
	int loadCount(IconsQuery iconsQuery);
	/**
	 * 查询图标列表数据集，并分页
	 * @param map
	 * @return
	 */
	List<IconsVM> loadList(IconsQuery iconsQuery);
	/**
	 * 删除对象
	 * @param id
	 * @return
	 */
	int deleteByPrimaryKey(int id);
	/**
	 * 更新图标对象
	 * @param icons
	 * @return
	 */
	Icons updateByPrimaryKey(Icons icons);
	/**
	 * 插入新图标对象
	 * @param icons
	 * @return
	 */
	Icons insert(Icons icons);
	/**
	 * 根据id获取图标对象
	 * @param id
	 * @return
	 */
	Icons loadById(int id);
	/**
	 * 批量删除图标对象
	 * @param map
	 */
	void deleteByIds(Map<String, Object> map);

	/**
	 * 读取图标基础信息
	 * @return
	 */
	List<Icons> getIconsInfo();
	Icons selectByPrimaryKey(Integer iconId);
	List<IconsType> selectIconType();
	List<Icons> findByIdAndGpsId(String iconId);
	int saveOrUpdate(Icons icons);
	int updateIconsInfoById(int iconId);
	
}
