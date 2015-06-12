package com.tianyi.bph.dao.duty;

import java.util.List;
import java.util.Map;

import com.tianyi.bph.dao.MyBatisRepository;
import com.tianyi.bph.domain.duty.DutyType;
import com.tianyi.bph.query.duty.DutyItemCountVM;
import com.tianyi.bph.query.duty.DutyTypePropertyVM;
import com.tianyi.bph.query.duty.DutyTypeVM;
 

/**
 * 勤务类型映射类
 * @author lq
 *
 */
@MyBatisRepository
public interface DutyTypeMapper {
	
	/**
	 * 根据id删除对象
	 * @param id
	 * @return
	 */
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入新的勤务类型对象
     * @param record
     * @return
     */
    int insert(DutyType record);

    /**
     * 插入新的勤务类型对象
     * @param record
     * @return
     */
    int insertSelective(DutyType record);

    /**
     * 根据id获取勤务类型对象
     * @param id
     * @return
     */
    DutyType selectByPrimaryKey(Integer id);

    /**
     * 更新勤务类型对象
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(DutyType record);

    /**
     * 更新勤务类型对象
     * @param record
     * @return
     */
    int updateByPrimaryKey(DutyType record);
    
    //自定义
    /**
     * 获取勤务类型属性列表
     * @return
     */
    List<DutyTypePropertyVM> loadProperties();
    
    /**
     * 获取勤务类型列表
     * @param map
     * @return
     */
    List<DutyTypeVM> loadDutyTypeVM(Map<String,Object> map);

    /**
     * 更新勤务类型启用或锁定状态，
     * 
     * 停用的时候，是从节点包括下级节点都停用，
     * 启用的时候，只能一级一级 的启用
     * @param map
     */
    void updateUseStateByFullPath(Map<String,Object> map);
    
    /**
     * 检验勤务类型启用状态
     * @param fullPath
     * @return
     */
    int checkUsed(String fullPath);
 
    /**
     * 获取勤务类型列表数据
     * @param map
     * @return
     */
	List<DutyType> loadDutyType(Map<String, Object> map);

	/**
	 * 获取勤务报备明细总数
	 * @param map
	 * @return
	 */
	List<DutyItemCountVM> loadDutyItemCount(Map<String, Object> map);

	DutyType loadDutyTypeById(Integer did);

	int updateDutyType(DutyType dutytype);

	List<DutyType> loadDutyTypeByParentId(Integer pid);

	List<DutyType> findByNameAndId(Map<String, Object> map);

	List<DutyType> findByName(String typeName);

	void updateDutyItemTypeName(Map<String, Object> map);
 
}