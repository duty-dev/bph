package com.tianyi.bph.service.impl.system;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.googlecode.ehcache.annotations.Cacheable;
import com.tianyi.bph.common.Constants;
import com.tianyi.bph.common.MessageCode;
import com.tianyi.bph.common.Pager;
import com.tianyi.bph.common.ReturnResult;
import com.tianyi.bph.common.annotation.MQDataInterceptor;
import com.tianyi.bph.dao.system.OrganDAO;
import com.tianyi.bph.dao.system.UserOtherOrganDAO;
import com.tianyi.bph.domain.system.GBOrgan;
import com.tianyi.bph.domain.system.Organ;
import com.tianyi.bph.exception.RestException;
import com.tianyi.bph.query.system.GBOrganExample;
import com.tianyi.bph.query.system.OrganQuery;
import com.tianyi.bph.service.impl.system.GBPlatFormServiceImpl.Tree;
import com.tianyi.bph.service.system.OrganService;


@Service
public class OrganServiceImpl implements OrganService{

	private static final Logger log=LoggerFactory.getLogger(OrganServiceImpl.class);
	public  static  Map<String,List<String>> parentMap=new HashMap<String,List<String>>();
	public 	static Map<String,List<String>> expandedMap=new HashMap<String,List<String>>();
	public 	static Map<String,List<Organ>> nameOrganMap=new HashMap<String,List<Organ>>();
	private static final String cacheName="ORGAN_BASE_DATA";
	private static int i=0;
	
	@Autowired OrganDAO organDao;
	@Autowired UserOtherOrganDAO userOtherOrganDao;
	
	//增
	@MQDataInterceptor(type = Constants.MQ_TYPE_ORGAN, operate = Constants.MQ_OPERATE_ADD)
	public Organ addOrgan(Organ organ){
		OrganQuery organQuery = new OrganQuery();
		organQuery.setName(organ.getName());
		organQuery.setCode(organ.getCode());
		int count = organDao.getUniqueCountByQuery(organQuery);
		if (count > 0) {
			return null;
		}
		organ.setPath("");
		organ.setLevle(1);
		organ.setSortNo(0);
		organDao.insert(organ);
		
		String path=organDao.selectByPrimaryKey(organ.getParentId()).getPath();
		Integer levle=organDao.selectByPrimaryKey(organ.getParentId()).getLevle();
		organ.setPath(path+"/"+organ.getCode());
		organ.setLevle(levle+1);
		organDao.updateByPrimaryKey(organ);
		
		log.info("添加机构成功");
		return organ;
	}
	/**
	 * 通过id查询自身机构信息
	 * @param Id
	 * @return
	 */
	public Organ getOrganByPrimaryKey(Integer Id){
		return organDao.selectByPrimaryKey(Id);
	}
	
	//删
	@MQDataInterceptor(type = Constants.MQ_TYPE_ORGAN, operate = Constants.MQ_OPERATE_DELETE)
	public int deleteOrgan(Integer id){
		
		int count = organDao.getChildCount(id);
		if (count > 0) {
			return 0;
		}
		int i=organDao.getUserCount(id);
		if(i > 0){
			return 0;
		}
		organDao.deleteByPrimaryKey(id);
		
		log.info("删除机构成功");
		
		return id;
	}
	//改
	@MQDataInterceptor(type = Constants.MQ_TYPE_ORGAN, operate = Constants.MQ_OPERATE_UPDATE)
	public Organ updateOrgan(Organ organ){
		if(organ==null){
			throw new RestException("机构对象不能为空");
		}
		Organ o=organDao.selectByPrimaryKey(organ.getId());
		if(o != null){
			if(!(o.getName().equals(organ.getName()) && o.getCode().equals(organ.getCode()))){
				if(!o.getName().equals(organ.getName())){//名称不同
					Organ ooo=organDao.selectByName(organ.getName());
					if(ooo !=null){
						return null;
					}
				}
				if(!o.getCode().equals(organ.getCode())){
					Organ oo=organDao.selectByCode(organ.getCode());
					if(oo !=null){
						return null;
					}
				}
			}
			organDao.updateByPrimaryKeySelective(organ);
		}
		return organ;
	}
	
	//查
	public Organ getOrganById(Integer id){
		return organDao.selectById(id);
	}
	
	//是否重复
	public ReturnResult isUnique(OrganQuery organQuery){
		
		int count = organDao.getUniqueCountByQuery(organQuery);
		if (count > 0) {
			return ReturnResult.MESSAGE(MessageCode.STATUS_NOTUNIQUE, "机构名或机构代码重复");
		}
		return ReturnResult.SUCCESS(MessageCode.STATUS_SUCESS);
	}
	
	//是否有子机构
	public ReturnResult hasChild(Integer parentId){
		
		int count = organDao.getChildCount(parentId);
		if (count > 0) {
			return ReturnResult.MESSAGE(MessageCode.STATUS_HASCHILD, "该机构有子节点不能删除");
		}else{
			return ReturnResult.MESSAGE(MessageCode.STATUS_SUCESS);
		}
	}
	
	//分页查询
	public Pager<Organ> getPageList(OrganQuery query){
		Pager<Organ> page = new Pager<Organ>(query.getPageSize(),
				query.getPageNo());
		page.setTotalRows(organDao.findCount(query));// 总条数
		page.setData(organDao.findByPage(query));// 数据
		page.setPageNo(query.getPageNo());
		return page;
	}
	
	//条件查询
	public List<Organ> getQueryList(OrganQuery query){
		return organDao.findByQuery(query);// findOrganByQuery
	}
	/**
	 * 遍历上级机构
	 * @param query
	 * @return
	 */
	public List<Organ> getOrganQueryList(OrganQuery query){
		
		return organDao.findOrganByQuery(query);
	}
	
	public void updateOrganByMySelect(Organ organ){

		organDao.updateByMySelective(organ);
		
	}
	
	/**
	 * 获取机构数
	 * @param query
	 * @param database
	 * @return
	 */
	/*public Organ getOrganTree(OrganQuery query,String database){
		Organ organ=null;
		List<Organ> organList=null;
		List<String> expandedList=null;
		//List<String> orgPList=new ArrayList<String>();//存储当前机构的上级机构
		List<Organ> parentList = organDao.findOrganById(query);//通过id获取上级机构集合
		for (Organ organV : parentList) {
			orgPList.add(organV.getId()+"");
		}
		
		*//**
		 * 存储展开节点的节点集合
		 *//*
		if(!StringUtils.isEmpty(query.getExpandeds())){
			String[] strList=query.getExpandeds().split(",");
			expandedList=Arrays.asList(strList);
			expandedMap.put("expandedList", expandedList);
		}else{
			expandedMap.clear();
		}
		
			//根据用户获取跨机构ids
			List<String> jumpList=userOtherOrganDao.getOrganIdByUserId(query.getUserId());
			for (String string : orgPList) {
				if(jumpList.contains(string)){//跨越机构中有当前机构的上级机构，则直接跳过。用当前机构去遍历树
					organ=organDao.selectByPrimaryKey(Integer.parseInt(string));
					//将上级机构的子机构集合放入Map中
					parentMap.put("parentList",organDao.getStringByPath(organ.getPath()));
					break;
				}else{
					parentMap.clear();
				}
			}
			if(organ == null){//跨机构中没有上级机构，则用当前机构作为根节点
			  organ=organDao.selectByPrimaryKey(query.getId());//这里确立的是根节点;
		    }
			organ.setExpanded(true);
			
			//下面这是模糊查询的内容
			if(!StringUtils.isEmpty(query.getName())){
				//List<String> nameList=new ArrayList<String>();//存储通过Name查询获取的机构Ids
				List<String> idsStr=new ArrayList<String>();
				
				List<Organ> list=organDao.getOrgansByName(query);//获取Name模糊查询出来的数据
				if(list.size() ==0){
					return null;
				}
				for (Organ organVV : list) {
					nameList.add(organVV.getId()+"");
				}
				boolean flag=false;
				List<String> oneList=organDao.getStringByPath(organ.getPath());//获取他的子机构集合
				for (String string : oneList) {
					if(nameList.contains(string)){
						flag=true;
					}
				}
				if(!flag){
					return null;
				}
				organList=new ArrayList<Organ>();
				for(Organ o:list){//遍历通过name查询出来的list。然后添加到organlist中，遍历去重
					query.setId(o.getId());
					List<Organ> idList = organDao.findOrganById(query);//通过id获取上级机构
					for(Organ og:idList){
						if(!idsStr.contains(og.getId()+"")){
							idsStr.add(og.getId()+"");
							og.setExpanded(true);
							organList.add(og);
						}
					}
				}
			}else{//name为空的情况
				organList=organDao.findOrganByQuery(query);
			}
			initializeOrgan(organ, organList);
		return organ;
	}
	*/
	public Organ getOrganTree(OrganQuery query,String database){
		List<Organ> list=organDao.findOrganByQuery(query);
		if (list != null && !list.isEmpty()) {
			return new Tree(list).buildTree(query.getId());
		}
		return null;
	}
	class Tree {
		private Iterator<Organ> it;
		final private List<Organ> nodes;

		public Tree(List<Organ> nodes) {
			this.nodes = nodes;
		}

		//@Cacheable(cacheName = cacheName)
		public Organ buildTree(Integer parentId) {
			
			Organ parent = null;
			it = nodes.iterator();
			while (it.hasNext()) {
				Organ node = it.next();
				if (node.getId() == parentId) {
					parent = node;
					parent.setExpanded(true);
					it.remove();
					build(node);
				}
			}
			return parent;
		}

		private void build(Organ node) {
			List<Organ> children = getChildren(node);
			if (children != null && !children.isEmpty()) {
				for (Organ child : children) {
					build(child);
				}
			}
		}

		private List<Organ> getChildren(Organ node) {
			Integer id = node.getId();
			it = nodes.iterator();
			boolean exp = false;
			while (it.hasNext()) {
				Organ child = it.next();
				if (id.equals(child.getParentId())) {
					if (child.isChecked()) {
						exp = true;
					}
					node.addChild(child);
					it.remove();
				}
			}
			node.setExpanded(exp);
			return node.getItems();
		}
	}
	/**
	 * 获取跨机构树
	 * @param query
	 * @param database
	 * @return
	 */
	public Organ getJumpTree(OrganQuery query,String database){
		Organ organ=null;
		List<Organ> organList=null;
		List<String> jumpList=null;
		organ=organDao.selectByPrimaryKey(query.getId());
		organ.setExpanded(true);
		if(!StringUtils.isEmpty(query.getUserId()+"")){
			//根据用户获取跨机构ids
		    jumpList=userOtherOrganDao.getOrganIdByUserId(query.getUserId());
		}
		organList=organDao.findOrganByQuery(query);
		if(jumpList != null){
			for (Organ oo : organList) {
				if(jumpList.contains(oo.getId()+"")){
					oo.setChecked(true);
				}
			}
		}
		initializeOrgan(organ, organList);
		return organ;
	}
	
	
	
	/**
	 * 递归查询机构信息
	 * @param parentOrgan
	 * @param childOrgans
	 * @param list
	 */
	public void initializeOrgan(Organ parentOrgan, List<Organ> childOrgans){
		List<Organ> children = new ArrayList<Organ>();
		for (Organ child : childOrgans) {
			if (child.getParentId() != null && child.getParentId() == parentOrgan.getId()) {
				if(expandedMap.get("expandedList") != null && 
						expandedMap.get("expandedList").contains(child.getId()+"")){
					child.setExpanded(true);
				}
				children.add(child);
				initializeOrgan(child, childOrgans);
			}
		}
		parentOrgan.setItems(children);
	}
	
	
	public List<String> getIdsByName(OrganQuery organQuery){
		return organDao.getIdsByName(organQuery);
	}
	@Override
	public List<String> getStringByPath(String path) {
		// TODO Auto-generated method stub
		return organDao.getStringByPath(path);
	}
	@Override
	public List<Organ> getOrgansByName(OrganQuery organQuery) {
		// TODO Auto-generated method stub
		return organDao.getOrgansByName(organQuery);
	}
	@Override
	public List<Organ> getOrganListByParentId(OrganQuery query) {
		// TODO Auto-generated method stub
		List<String> expandedList=null;
		if(!StringUtils.isEmpty(query.getExpandeds())){//获取展开集合
			String[] strList=query.getExpandeds().split(",");
			expandedList=Arrays.asList(strList);
			expandedMap.put("expandedList", expandedList);
		}else{
			expandedMap.clear();
		}
		List<Organ> organList=organDao.getOrganListByParentId(query.getId(),query.getParentId());
		for (Organ organ : organList) {
			/*if(organ.getId()==query.getId()){
				organ.setExpanded(true);
			}*/
			if(expandedMap.get("expandedList") != null && 
					expandedMap.get("expandedList").contains(organ.getId()+"")){
				organ.setExpanded(true);
			}
			if(query.getCurrentOrganId() != null &&!StringUtils.isEmpty(query.getCurrentOrganId()+"")){
				if(organ.getId()==query.getCurrentOrganId()){
					organ.setSelected(true);
				}
			}
		}
		return organList;
	}
	
	@Override
	public List<Organ> findOrganById(OrganQuery query) {
		// TODO Auto-generated method stub
		return organDao.findOrganById(query);
	}
	@Override
	public List<Organ> getOrganListByParentId1(OrganQuery query) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
				List<String> expandedList=null;
				if(!StringUtils.isEmpty(query.getExpandeds())){//获取展开集合
					String[] strList=query.getExpandeds().split(",");
					expandedList=Arrays.asList(strList);
					expandedMap.put("expandedList", expandedList);
				}else{
					expandedMap.clear();
				}
				List<Organ> organList=organDao.getOrganListByParentId(query.getId(),query.getParentId());
				for (Organ organ : organList) {
					if(organ.getId()==query.getId()){
						organ.setExpanded(true);
					}
					if(expandedMap.get("expandedList") != null && 
							expandedMap.get("expandedList").contains(organ.getId()+"")){
						organ.setExpanded(true);
					}
					if(query.getCurrentOrganId() != null &&!StringUtils.isEmpty(query.getCurrentOrganId()+"")){
						if(organ.getId()==query.getCurrentOrganId()){
							organ.setSelected(true);
						}
					}
				}
				return organList;
	}

}
