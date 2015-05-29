package com.tianyi.bph.dao.duty;

import java.util.List;
import java.util.Map;

import com.tianyi.bph.dao.MyBatisRepository;
import com.tianyi.bph.domain.duty.Duty;
import com.tianyi.bph.domain.duty.DutyProperty;
import com.tianyi.bph.query.duty.DutyItemCountVM;
import com.tianyi.bph.query.duty.DutyVM;
 
/**
 * 勤务报备映射文件类
 * @author lq
 *
 */
@MyBatisRepository
public interface DutyMapper {
	/**
	 * 根据id删除勤务报备数据
	 * @param id
	 * @return
	 */
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入新的勤务报备
     * @param record
     * @return
     */
    int insert(Duty record);

    /**
     * 插入新的勤务报备
     * @param record
     * @return
     */
    int insertSelective(Duty record);
    /**
     * 根据id查询勤务报备对象
     * @param id
     * @return
     */
    Duty selectByPrimaryKey(Integer id);

    /**
     * 更新对象
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Duty record);
    
    /**
     * 更新对象
     * @param record
     * @return
     */
    int updateByPrimaryKey(Duty record);
    
    /**
     * 获取勤务报备数据列表
     * @param map
     * @return
     */
    List<DutyVM> loadDutyVMList(Map<String,Object> map);
    
    /**
     * 根据组织机构id，获取勤务报备数据模板，不获报备明细
     * @param orgId
     * @return
     */
    List<Duty> loadTemplatesWithOutItem(Integer orgId);

    /**
     * 获取报备明细总警力
     * @param map
     * @return
     */
	List<DutyItemCountVM> loadTotalPolice(Map<String, Object> map);

	/**
	 * 获取报备明细详细的警力数
	 * @param map
	 * @return
	 */
	List<DutyItemCountVM> loadTotalPolicedetail(Map<String, Object> map);

	/**
	 * 获取勤务报备类型属性列表
	 * @return
	 */
	List<DutyProperty> selectdutyProperty();

	/**
	 * 根据日期删除勤务报备
	 * @param targetYmd
	 */
	void deleteByYMD(Integer targetYmd);
 
	/**
	 * 根据日期，获取勤务报备详细数据
	 * @param maps
	 * @return
	 */
	List<Duty> loadVMListByOrgAndYmd(Map<String, Object> maps);

	/**
	 * 根据模板id，获取模板信息
	 * @param id
	 * @return
	 */
	Duty loadTempById(Integer param);

	/**
	 * 根据id，删除模板对象
	 * @param param
	 */
	void deleteTempById(Integer param);

	Duty getdutyIdByYmd(Integer targetYmd);
}