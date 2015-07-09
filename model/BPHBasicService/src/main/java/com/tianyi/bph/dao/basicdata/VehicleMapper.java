package com.tianyi.bph.dao.basicdata;

import java.util.List;
import java.util.Map;

import com.tianyi.bph.dao.MyBatisRepository;
import com.tianyi.bph.domain.basicdata.IntercomGroup;
import com.tianyi.bph.domain.basicdata.Vehicle;
import com.tianyi.bph.domain.basicdata.VehiclePurpose;
import com.tianyi.bph.domain.basicdata.VehicleType;
import com.tianyi.bph.query.basicdata.GpsBaseVM;
import com.tianyi.bph.query.basicdata.GroupMemberVM;
import com.tianyi.bph.query.basicdata.VehicleVM;
 
/*
 * 车辆映射文件类
 */
@MyBatisRepository
public interface VehicleMapper {
	/*
	 * 根据主键删除车辆对象
	 */
    int deleteByPrimaryKey(Integer id);
	/*
	 * 插入新的车辆对象
	 */
    int insert(Vehicle record);
	/*
	 * 插入车辆查询对象集
	 */
    int insertSelective(Vehicle record);
	/*
	 * 根据id查询车辆对象
	 */
    Vehicle selectByPrimaryKey(Integer id);
	/*
	 * 更新车辆对象
	 */
    int updateByPrimaryKeySelective(Vehicle record);
	/*
	 * 更新车辆对象
	 */
    int updateByPrimaryKey(Vehicle record);
	/*
	 * 查询车辆总记录数
	 */
    int countByExample(Vehicle vehicle);
	/*
	 * 根据参数获取车辆列表集
	 */
    List<Vehicle> selectByExample(Map<String, Object> map);
	/*
	 * 根据参数查询车辆总数
	 */
    int countByVM(Map<String, Object> map);
	/*
	 * 根据参数获取车辆列表集，并分页
	 */
    List<VehicleVM> loadVMList(Map<String, Object> map);
	/*
	 * 根据参数获取车辆总记录数，用于分页
	 */
	int loadVMCount(Map<String, Object> query);
	/*
	 * 查询车辆类型列表集
	 */
	List<VehicleType> selectVehicleType();
	/*
	 * 查询车辆用途列表集
	 */
	List<VehiclePurpose> selectVehiclePurpose();
	/*
	 * 查询车辆对应组呼号列表集
	 */
	List<IntercomGroup> selectIntercomGroup();
	/*
	 * 查询车辆资源，按照分组对象查询
	 */
	List<VehicleVM> loadVMListWithGroup(Map<String, Object> map);
	/*
	 * 批量删除车辆对象
	 */
	void deleteByIds(Map<String, Object> map);
	/**
	 * 根据车牌号码，判断是否存在
	 * @param param
	 * @return
	 */
	List<VehicleVM> findByNumber(String param);
	/**
	 * 判断是否有车辆关联信息
	 * @param param
	 * @return
	 */
	List<Vehicle> findByIdAndDtyId(String param);
	/**
	 * 根据组织机构id，获取所有成员列表
	 * @param map
	 * @return
	 */
	List<Vehicle> getVehicleInfo(Map<String, Object> map);
	List<GpsBaseVM> selectGpsId(int orgId);
	List<VehicleVM> findByNumberAndId(Map<String, Object> map);
	List<VehicleVM> loadVMListWithGroupList(Map<String, Object> map);
	
	Integer addMemberToGroup(Map<String, Object> map);
	List<GroupMemberVM> findByGroupIDAndVehicleID(Map<String, Object> map);
	
	
    
}