package com.tianyi.bph.service.duty;

import java.util.List;
import java.util.Map;

import com.tianyi.bph.domain.duty.Duty;
import com.tianyi.bph.domain.duty.DutyItems;
import com.tianyi.bph.domain.duty.DutyProperty;
import com.tianyi.bph.query.duty.DutyItemCountVM;
import com.tianyi.bph.query.duty.DutyVM;
 

/**
 * 勤务报备逻辑接口层
 * @author lq
 *
 */
public interface DutyService {

	/**
	 * 获取报备数据信息列表，以树桩列表显示
	 * @param map
	 * @return
	 */
	List<DutyVM> loadVMList(Map<String,Object> map);
	
	/**
	 * 根据组织机构，日期，获取报备明细数据
	 * @param orgId
	 * @param ymd
	 * @return
	 */
	DutyVM loadVMByOrgIdAndYmd(Integer orgId,Integer ymd);
	
	/**
	 * 根据id，获取报备明细
	 * @param id
	 * @return
	 */
	DutyVM loadById(Integer id);
	
	/**
	 * 根据组织机构，获取报备模板名称，不加载子节点属性
	 * @param orgId
	 * @return
	 */
	List<Duty> loadTemplatesWithOutItem(Integer orgId);
	
	
	/**
	 * 保存新的报备信息
	 * @param vm
	 */
	void save(DutyVM vm);

	/**
	 * 获取报备明细的总警力数
	 * @param map
	 * @return
	 */
	List<DutyItemCountVM> loadTotalPolice(Map<String, Object> map);

	/**
	 * 获取报备明细的详细警力数
	 * @param map
	 * @return
	 */
	List<DutyItemCountVM> loadTotalPolicedetail(Map<String, Object> map);

	/**
	 * 获取报备类型的属性列表，
	 * @return
	 */
	List<DutyProperty> selectdutyProperty();

	/**
	 * 根据报备id，删除数据
	 * @param dId
	 */
	void deleteByDutyId(int dId);

	/**
	 * 根据日期，删除报备数据
	 * @param targetYmd
	 */
	void deleteByYMD(Map<String, Object> map);

	/**
	 * 插入新的报备数据信息
	 * @param nduty
	 * @return
	 */
	int insert(Duty nduty);

	/**
	 * 根据报备id，获取报备明细数据
	 * @param id
	 * @return
	 */
	List<DutyItems> loadlistByDutyId(Integer id);

	/**
	 * 插入新的报备明细节点
	 * @param di
	 * @return
	 */
	int insertDutyItem(DutyItems di);
 
	/**
	 * 根据组织机构id，日期获取报备数据信息
	 * @param maps
	 * @return
	 */
	List<Duty> loadVMListByOrgAndYmd(Map<String, Object> maps);

	/**
	 * 根据id集，删除报备数据
	 * @param map
	 */
	void deleteByDutyIdlist(Map<String, Object> map);

	/**
	 * 根据id，删除报备
	 * @param dId
	 */
	void deleteByPrimaryKey(int dId);

	/**
	 * 根据模板id ，获取模板对象
	 * @param temId
	 * @return
	 */
	Duty loadTempById(Integer param);

	/**
	 * 根据模板id，删除模板对象
	 * @param param
	 */
	void deleteTempById(Integer param);
}
