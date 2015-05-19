package com.tianyi.bph.service.system;

import java.util.List;

import com.tianyi.bph.common.Pager;
import com.tianyi.bph.common.ReturnResult;
import com.tianyi.bph.domain.system.Organ;
import com.tianyi.bph.query.system.OrganQuery;


public interface OrganService {
	
	//增
	public Organ addOrgan(Organ organ);
	/**
	 * 通过id查询自身机构信息
	 * @param Id
	 * @return
	 */
	public Organ getOrganByPrimaryKey(Integer Id);
	
	//删
	public int deleteOrgan(Integer id);
	//改
	public Organ updateOrgan(Organ organ);
	
	//查
	public Organ getOrganById(Integer id);
	
	//是否重复
	public ReturnResult isUnique(OrganQuery organQuery);
	
	//是否有子机构
	public ReturnResult hasChild(Integer parentId);
	
	//分页查询
	public Pager<Organ> getPageList(OrganQuery query);
	
	//条件查询
	public List<Organ> getQueryList(OrganQuery query);
	/**
	 * 遍历上级机构
	 * @param query
	 * @return
	 */
	public List<Organ> getOrganQueryList(OrganQuery query);
	
	public void updateOrganByMySelect(Organ organ);
	/**
	 * 获取机构树
	 * @param query
	 * @param database
	 * @return
	 */
	public Organ getOrganTree(OrganQuery query,String database);
	//public Organ getOrganTree(Integer organId,String name,String database);
	/**
	 * 获取跨机构功能树
	 * @param query
	 * @param database
	 * @return
	 */
	public Organ getJumpTree(OrganQuery query,String database);
	
	public List<String> getIdsByName(OrganQuery organQuery);
	
	public List<String> getStringByPath(String path);
	
	List<Organ> getOrgansByName(OrganQuery organQuery);

	//List<Organ> getOrganListByParentId(Integer organId,Integer parentId);
	List<Organ> getOrganListByParentId(OrganQuery organQuery);
	public List<Organ> findOrganById(OrganQuery query);
}
