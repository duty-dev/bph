package com.tianyi.bph.dao.system;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tianyi.bph.dao.MyBatisRepository;
import com.tianyi.bph.domain.system.Organ;
import com.tianyi.bph.query.system.OrganQuery;

@MyBatisRepository
public interface OrganDAO {
    int deleteByPrimaryKey(Integer id);

    int insert(Organ record);

    int insertSelective(Organ record);

    Organ selectByPrimaryKey(Integer id);
    
    Organ selectById(Integer id);
    
    Organ selectByCode(String code);
    
    Organ selectByName(String name);

    int updateByPrimaryKeySelective(Organ record);

    int updateByPrimaryKey(Organ record);
    
    //根据条件查询总数
    int getUniqueCountByQuery(OrganQuery organQuery);
    
    //根据子机构总数
    int getChildCount(Integer parentId);
    //验证机构下是否有用户
    int getUserCount(Integer organId);
    
    //查询排序号
    int getSortNo(Integer parentId);
    
    //分页查询
    List<Organ> findByPage(OrganQuery organQuery);
    
    List<Organ> getOrgansByName(OrganQuery organQuery);
    
    List<Organ> getOrgByName(String name);
    
    //分页总条数
    int findCount(OrganQuery organQuery);
    
    //条件查询
    List<Organ> findByQuery(OrganQuery organQuery);
   
    /**
     * 通过name遍历上级机构
     * @param organQuery
     * @return
     */
    List<Organ> findOrganByQuery(OrganQuery organQuery);
    
    /**
     * 通过id 获取机构信息
     * @param id
     * @return
     */
    List<Organ> findOrganById(OrganQuery organQuery);
    
    //修改指定字段
    int updateByMySelective(Organ record);
    
    List<String> getIdsByName(OrganQuery organQuery);
    
    List<String> getIdsById(Integer id);
    
    List<String> getStringByPath(String path);
    
    List<Organ> getOrganListByParentId(@Param(value="organId")Integer organId,@Param(value="parentId")Integer parentId);
    
    List<Organ> getOrganListParentIdIsNull(Integer organId);
}