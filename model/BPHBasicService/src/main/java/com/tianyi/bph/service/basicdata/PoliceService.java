package com.tianyi.bph.service.basicdata;

import java.util.List;
import java.util.Map;

import com.tianyi.bph.common.Pager;
import com.tianyi.bph.domain.basicdata.IntercomGroup;
import com.tianyi.bph.domain.basicdata.Police;
import com.tianyi.bph.domain.basicdata.PoliceTitle;
import com.tianyi.bph.domain.basicdata.PoliceType;
import com.tianyi.bph.query.basicdata.GpsBaseVM;
import com.tianyi.bph.query.basicdata.GroupMemberVM;
import com.tianyi.bph.query.basicdata.PoliceExtItem;
import com.tianyi.bph.query.basicdata.PoliceInfo;
import com.tianyi.bph.query.basicdata.PoliceJJVM;
import com.tianyi.bph.query.basicdata.PoliceVM;
import com.tianyi.bph.query.basicdata.UserObjectVM;
 
/**
 * 警员后台逻辑服务层
 * @author lq
 *
 */
public interface PoliceService {
	/**
	 * 删除对象
	 * @param id
	 * @return
	 */
	public int deleteByPrimaryKey(Integer id);
    /**
     * 插入新的 警员对象
     * @param record
     * @return
     */
	public Police insert(Police record);
    /**
     * 插入查询对象集
     * @param record
     * @return
     */
	public int insertSelective(Police record);
    /**
     * 根据id查询对象
     * @param id
     * @return
     */
	public Police selectByPrimaryKey(Integer id);
    /**
     * 更新对象
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Police record);
    /**
     * 跟新对象
     * @param record
     * @return
     */
    public Police updateByPrimaryKey(Police record);
    /**
     * 根据编码获取警员对象
     * @param code
     * @return
     */
    public List<PoliceVM> findBycode(String code);
    /**
     * 根据姓名查询警员对象
     * @param name
     * @return
     */
    Police findByname(String name);
    /**
     * 查询所有对象列表
     * @return
     */
    public List<Police> selectAll();
    /**
     * 更新对象
     * @param policeman
     * @return
     */
    public int updatePolice(Police policeman);
     /**
      * 传入参数，验证登陆
      * @param params
      * @return
      */
    public Police login(Map<String, Object> params);
	 /**
	  * 查询记录总数
	  * @param query
	  * @return
	  */
	 int findCount(PoliceVM query);
	 /**
	  * 查询分页记录
	  * @param query
	  * @param page
	  * @return
	  */
	 public List<PoliceVM> findPageList(PoliceVM query, Pager page);
	 /**
	  * 查询分页记录总数
	  * @param map
	  * @return
	  */
	 int loadVMCount(Map<String, Object> map);
	 /**
	  * 查询列表记录，分页
	  * @param map
	  * @return
	  */
	 public List<PoliceVM> loadVMList(Map<String, Object> map);
	 /**
	  * 查询警员类型列表
	  * @return
	  */
	 public List<PoliceType> selectPoliceType();
	 /**
	  * 查询警员职务列表
	  * @return
	  */
	 public List<PoliceTitle> selectPoliceTitle();
	 /**
	  * 查询对讲机组呼号列表
	  * @return
	  */
	 public List<IntercomGroup> selectIntercomGroup();
	 /**
	  * 查询gpsId对象列表
	  * @param orgId
	  * @return
	  */
	 public List<GpsBaseVM> selectGpsId(int orgId);
	 /**
	  * 查询记录分页显示列表
	  * @param map
	  * @return
	  */
	 public List<PoliceVM> loadVMListWithGroup(Map<String, Object> map);
	 /**
	  * 根据身份证号码查询警员对象
	  * @param param
	  * @return
	  */
	 public List<PoliceVM> findByidCard(String param);
	 /**
	  * 查询分组警员对象列表
	  * @param map
	  * @return
	  */
	 public List<PoliceVM> loadVMListWithGroupList(Map<String, Object> map);
	 /**
	  * 批量删除警员对象
	  * @param map
	  */
	 public void deleteByIds(Map<String, Object> map);
	 /**
	  * 验证用户的有效性
	  * @param map
	  * @return
	  */
	 public UserObjectVM getUserAuthorization(Map<String, Object> map);
	 /**
	  * 批量更改警员的状态
	  * 启用或者锁定
	  * @param map
	  */
	 public void changePoliceStateByIds(Map<String, Object> map);
	/**
	 * 根据个呼号，查询是否已存在警员
	 * @param param
	 * @return
	 */
	 public List<PoliceVM> findByintercomPerson(String param);
	/**
	 * 根据组织机构，查询所有数据
	 * @param orgId
	 * @return
	 */
	 public List<PoliceVM> loadVMListWithOrgId(Integer orgId);
	/**
	 * 判断资源元素是否有相关联数据
	 * @param string
	 * @return
	 */
	 public List<Police> findByIdAndDtyId(String string);
	
	/**
	 * 根据组织机构id，获取所有成员列表
	 * @param orgId
	 * @return
	 */
	 public List<PoliceJJVM> getPoliceInfo(Integer orgId,Integer isSubOrg);
	 
	/**
	 * 添加警员分组记录
	 * @param policeId
	 * @param grouId
	 * @return
	 */
	 public Integer addMemberToGroup(Integer policeId,Integer groupId);
	 
	 public List<GroupMemberVM> findByGroupIDAndPoliceID(Integer policeId,Integer groupId);
	
	
	

	/**
	 * 读取警员备勤信息及基础信息
	 * @param orgId
	 * @param ymd
	 * @return
	 */
	 public List<PoliceExtItem> getPoliceDutyInfo(Integer orgId, Integer ymd,Integer isSubOrg);
	 public List<PoliceVM> findByidCardAndId(Map<String, Object> map);
	 public List<PoliceVM> findBycodeAndId(Map<String, Object> map);
	 public List<PoliceVM> findByintercomPersonAndId(Map<String, Object> map);
	
	
	 
	
}
