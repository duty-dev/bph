package com.tianyi.bph.dao.basicdata;

import java.util.List;
 

import java.util.Map;

import com.tianyi.bph.common.Pager;
import com.tianyi.bph.domain.basicdata.IntercomGroup;
import com.tianyi.bph.domain.basicdata.Police;
import com.tianyi.bph.domain.basicdata.PoliceTitle;
import com.tianyi.bph.domain.basicdata.PoliceType;
import com.tianyi.bph.query.basicdata.GpsBaseVM;
import com.tianyi.bph.query.basicdata.GroupMemberVM;
import com.tianyi.bph.query.basicdata.PoliceJJVM;
import com.tianyi.bph.query.basicdata.PoliceVM;
import com.tianyi.bph.query.basicdata.UserObjectVM;
/**
 * 警员管理映射类
 * @author lq
 *
 */
@com.tianyi.bph.dao.MyBatisRepository
public interface PoliceMapper {
	/**
	 * 根据警员id删除警员
	 */
    int deleteByPrimaryKey(Integer id);
    /**
     * 插入新的警员对象
     */
    int insert(Police record);
    /**
     * 插入新的警员对象集
     */
    int insertSelective(Police record);
    /**
     * 根据主键选择警员对象
     */
    Police selectByPrimaryKey(Integer id);
    /**
     * 更新警员对象
     */
    int updateByPrimaryKeySelective(Police record);
    /**
     * 更新警员对象
     */
    int updateByPrimaryKey(Police record);
    /**
     * 根据警号查询警员对象
     */
    List<PoliceVM> findBycode(String code); 
	/**
	 * 根据警员名称查询警员对象
	 */
	Police findByname(String name); 
	/**
	 * 查询警员列表
	 */
	List<Police> selectAll();
	/**
	 * 更新警员独享
	 */
	int updatePolice(Police police);
	/**
	 * 警员登录验证传入参数
	 */
	Police login(Map<String, Object> params);
	/**
	 * 查询警员数目
	 */
	int findCount(Police police);
	/**
	 * 查询警员对象列表，并分页
	 */
	List<Police> findPageList(Police police, Pager page);
	/**
	 * 统计警员对象总数
	 */
	int countByExample(PoliceVM police);
	/**
	 * 根据条件查询警员列表并分页
	 */
	List<PoliceVM> selectWithPage(Map<String, Object> map);
	/**
	 * 获取警员列表对象并分页
	 */
	List<PoliceVM> loadVMList(Map<String, Object> map);
	/**
	 * 根据条件查询警员总数结果
	 */
	int countByVM(Map<String, Object> map);
	/**
	 * 查询警员总记录数
	 */
	int loadVMCount(Map<String, Object> map);
	/**
	 * 查询警员类型列表
	 */
	List<PoliceType> selectPoliceType();
	/**
	 * 查询警员职务列表
	 */
	List<PoliceTitle> selectPoliceTitle();
	/**
	 * 查询警员对应组呼号列表
	 */
	List<IntercomGroup> selectIntercomGroup();
	/**
	 * 查询警员对应gps列表，
	 */
	List<GpsBaseVM> selectGpsId(int orgId);
	/**
	 * 警员资源查询，根据所选条件进行筛选
	 */
	List<PoliceVM> loadVMListWithGroup(Map<String, Object> map);
	/**
	 * 根据身份证号查询警员对象
	 */
	List<PoliceVM> findByidCard(String param);
	/**
	 * 警员资源查询，根据所选分组进行筛选
	 */
	List<PoliceVM> loadVMListWithGroupList(Map<String, Object> map);
	/**
	 * 批量删除警员
	 */
	void deleteByIds(Map<String, Object> map);
	/**
	 * 根据传入的用户名以及密码进行用户有效性验证
	 */
	UserObjectVM getUserAuthorization(Map<String, Object> map);
	/**
	 * 批量修改警员状态
	 * @param map
	 */
	void changePoliceStateByIds(Map<String, Object> map);
	/**
	 * 根据个呼号，查询警员是否存在
	 * @param param
	 * @return
	 */
	List<PoliceVM> findByintercomPerson(String param);
	/**
	 * 根据组织机构id，查询所有警员列表数据
	 * @param orgId
	 * @return
	 */
	List<PoliceVM> loadVMListWithOrgId(Integer orgId);
	/**
	 * 判断警员是否有关联数据
	 * @param pid
	 * @return
	 */ 
	List<Police> findByIdAndDtyId(String param);
	
	Integer addMemberToGroup(Map<String, Object> map);
	List<GroupMemberVM> findByGroupIDAndPoliceID(Map<String, Object> map);
	
	/**
	 * 根据组织机构id，获取所有成员列表
	 * @param orgId
	 * @return
	 */
	List<PoliceJJVM> getPoliceInfo(Map<String, Object> map);
	
	List<PoliceVM> findByidCardAndId(Map<String, Object> map);
	List<PoliceVM> findBycodeAndId(Map<String, Object> map);
	List<PoliceVM> findByintercomPersonAndId(Map<String, Object> map);
	
	 
}