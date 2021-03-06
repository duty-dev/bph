package com.tianyi.bph.service.impl.duty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; 

import com.tianyi.bph.dao.duty.VehicleGroupMapper;
import com.tianyi.bph.dao.duty.VehicleGroupMemberMapper;
import com.tianyi.bph.dao.duty.VehicleGroupOrgMapper;
import com.tianyi.bph.domain.duty.VehicleGroup;
import com.tianyi.bph.domain.duty.VehicleGroupMember;
import com.tianyi.bph.domain.duty.VehicleGroupOrg;
import com.tianyi.bph.query.duty.VehicleGroupMemberVM;
import com.tianyi.bph.query.duty.VehicleGroupVM;
import com.tianyi.bph.service.duty.VehicleGroupService;
 
 
/**
 * 车辆分组接口实现
 * @author lq
 *
 */
@Service("vehicleGroupService")
public class VehicleGroupServiceImpl implements VehicleGroupService {

	@Resource(name="vehicleGroupMapper")
	private VehicleGroupMapper vehicleGroupMapper;
	
	@Resource(name="vehicleGroupOrgMapper")
	private VehicleGroupOrgMapper vehicleGroupOrgMapper;
	
	@Resource(name="vehicleGroupMemberMapper")
	private VehicleGroupMemberMapper vehicleGroupMemberMapper;
	
	/**
	 * 根据组织机构id，获取分组记录总数
	 */
	public int loadVMCountByOrgId(Map<String, Object> map) {
		int count=vehicleGroupMapper.countByOrgId(map);
		return count;
	}

	/**
	 * 根据组织机构id，获取分组记录列表，并分页
	 */
	public List<VehicleGroupVM> loadVMListByOrgId(Map<String, Object> map) {
		List<VehicleGroupVM> ls=vehicleGroupMapper.loadVMListByOrgId(map);
		return ls;
	}

	/**
	 * 获取下级共享组织机构列表
	 */
	public List<VehicleGroupOrg> loadShareOrgList(int pgid) {
		List<VehicleGroupOrg> ls=vehicleGroupOrgMapper.loadVehicleGroupOrgByPGId(pgid);
		return ls;
	}
	/**
	 * 保存车辆分组信息
	 */
	@Transactional
	public void saveVehicleGroup(VehicleGroup pg, Object[] orgIds) {
		if(pg.getId()==0){
			vehicleGroupMapper.insert(pg);
		}else{
			vehicleGroupMapper.updateByPrimaryKey(pg);
			vehicleGroupOrgMapper.deleteByPGId(pg.getId());
		}

		for(Object oid : orgIds){
			Integer id=Integer.parseInt(oid.toString());//  (Integer)oid;
			
			VehicleGroupOrg pgo=new VehicleGroupOrg();
			pgo.setOrgId(id.intValue());
			pgo.setVehicleGroupId(pg.getId());
			
			vehicleGroupOrgMapper.insert(pgo);
		}
		
	}

	/**
	 * 根据id，获取车辆分组对象信息
	 */
	public VehicleGroup loadById(Integer id) {
		return vehicleGroupMapper.selectByPrimaryKey(id);
	}

	/**
	 * 根据id，删除分组对象
	 */
	public void deleteById(Integer id) {
		vehicleGroupMapper.deleteByPrimaryKey(id);
		vehicleGroupOrgMapper.deleteByPGId(id); //删除对应共享的机构
		
	}

	/**
	 * 根据分组id，获取分组成员列表记录总数
	 */
	public int countMemberByGroupId(Integer groupId) {
		int total=vehicleGroupMemberMapper.countMemberByGroupId(groupId);
		return total;
	}

	/**
	 * 根据分组id，获取车辆分组成员列表记录，并分页
	 * 
	 */
	public List<VehicleGroupMemberVM> loadMemberByGroupId(
			Map<String, Object> map) {
		List<VehicleGroupMemberVM> ls=vehicleGroupMemberMapper.loadMemberByGroupId(map);

		return ls;
	}
	/**
	 * 增加分组成员列表
	 */
	@Transactional
	public void appendMemeber(List<VehicleGroupMember> ls) {
		if (ls.size() > 0) {
			VehicleGroupMember pgm = ls.get(0);
			vehicleGroupMemberMapper.deleteByGroupId(pgm.getGroupId());
		}
		for(VehicleGroupMember pgm : ls){
			Map<String,Object> m1=new HashMap<String,Object>();
			m1.put("groupId", pgm.getGroupId());
			m1.put("memberId", pgm.getVehicleId());
			
//			Integer count=vehicleGroupMemberMapper.existsByMemberId(m1);
//			
//			if(count==0){
				vehicleGroupMemberMapper.insert(pgm);
//			}
			
		}
		
	}

	public void delMemberById(Integer id) {
		vehicleGroupMemberMapper.deleteByPrimaryKey(id);
		
	}

	public void cleanMember(Integer groupId) {
		vehicleGroupMemberMapper.deleteByGroupId(groupId);
		
	}

	public List<VehicleGroupVM> loadVMListByOrgIdShared(Map<String, Object> map) {
		// TODO Auto-generated method stub
		List<VehicleGroupVM> ls=vehicleGroupMapper.loadVMListByOrgIdShared(map);
		return ls;
	}

	/**
	 * 判断法分组是否已存在
	 */
	public List<VehicleGroup> findByNameAndOrg(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return vehicleGroupMapper.findByNameAndOrg(map);
	}


}
