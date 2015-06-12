package com.tianyi.bph.service.impl.duty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tianyi.bph.common.basicUtil.ResultMsg;
import com.tianyi.bph.common.basicUtil.TreeHelper;
import com.tianyi.bph.dao.duty.DutyTypeMapper;
import com.tianyi.bph.dao.duty.DutyTypePropertyRelateMapper;
import com.tianyi.bph.domain.duty.DutyType;
import com.tianyi.bph.domain.duty.DutyTypePropertyRelate;
import com.tianyi.bph.query.duty.DutyItemCountVM;
import com.tianyi.bph.query.duty.DutyTypePropertyVM;
import com.tianyi.bph.query.duty.DutyTypeVM;
import com.tianyi.bph.service.duty.DutyTypeService;

/**
 * 勤务类型服务接口实现
 * 
 * @author lq
 * 
 */
@Service
public class DutyTypeServiceImpl implements DutyTypeService {

	@Autowired
	DutyTypeMapper dutyTypeMapper;

	@Autowired
	DutyTypePropertyRelateMapper dtprMapper;

	/**
	 * 获取勤务类型属性列表
	 */
	public List<DutyTypePropertyVM> loadProperties() {

		return dutyTypeMapper.loadProperties();
	}

	/**
	 * 获取勤务类型列表数据
	 */
	public List<DutyTypeVM> loadDutyTypeVM(Boolean isUsed) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("isUsed", isUsed);
		return dutyTypeMapper.loadDutyTypeVM(map);
	}

	/**
	 * 保存新增的勤务类型数据
	 */
	@Transactional
	public void save(DutyTypeVM vm) {
		DutyType parent = null;
		DutyType target = new DutyType();

		if (vm.getParentId() != null && vm.getParentId() != 0)
			parent = dutyTypeMapper.selectByPrimaryKey(vm.getParentId());
		else
			parent = new DutyType();
		//
		target.setId(vm.getId());
		target.setName(vm.getName());
		target.setParentId(vm.getParentId() == null ? 0 : vm.getParentId());
		target.setFullpath(parent.getFullpath() == null ? target.getName()
				: parent.getFullpath() + "." + target.getName());
		target.setLevel(parent.getLevel() + 1);
		target.setIsLeaf(vm.getIsLeaf());
		target.setMaxPolice(vm.getMaxPolice());
		target.setAssoTaskType(vm.getAssoTaskType());
		target.setAttireType(vm.getAttireType());
		target.setArmamentType(vm.getArmamentType());
		target.setIsShowname(vm.getIsShowname());
		target.setIsUsed(vm.getIsUsed());

		if (target.getId() == 0) {
			dutyTypeMapper.insert(target);
			vm.setId(target.getId());
		} else {
			dutyTypeMapper.updateByPrimaryKey(target);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("itemId", target.getId());
			map.put("itemName", target.getName());
			dutyTypeMapper.updateDutyItemTypeName(map);
		}

		// 修改父节点的isLeaf=false!
		if (parent.getId() != 0 && parent.getIsLeaf()) {
			parent.setIsLeaf(false);
			dutyTypeMapper.updateByPrimaryKey(parent);
		}

		// 先删除以前关联的属性子表数据
		dtprMapper.deleteByDutyTypeId(target.getId());

		for (DutyTypePropertyVM dtpvm : vm.getProperties()) {

			DutyTypePropertyRelate dtpr = new DutyTypePropertyRelate();

			dtpr.setDutyTypeId(target.getId());
			dtpr.setPropertyId(dtpvm.getId());

			dtprMapper.insert(dtpr);

		}

	}

	/**
	 * 更新勤务类型状态，启用或者锁定 启用，只能一级一级启用， 锁定，锁定上级节点，一并锁定下级节点
	 */
	public void updateUseStateByFullPath(Integer id, Boolean isUsed) {
		Map<String, Object> map = new HashMap<String, Object>();

		DutyType dt = dutyTypeMapper.selectByPrimaryKey(id);

		if (dt != null) {
			map.put("fullPath", dt.getFullpath());
			map.put("isUsed", isUsed);
			map.put("name", dt.getName());
			List<String> ls = TreeHelper.getAllParentFullPath(dt.getFullpath());
			map.put("list", ls);
			dutyTypeMapper.updateUseStateByFullPath(map);
		}
	}

	/**
	 * 根据id，删除勤务类型数据
	 */
	@Transactional
	public boolean deleteNode(Integer id) {
		boolean rm = false;
		DutyType dt = dutyTypeMapper.selectByPrimaryKey(id);
		int usedCount = dutyTypeMapper.checkUsed(dt.getFullpath());
		if (usedCount == 0) {
			dutyTypeMapper.deleteByPrimaryKey(id);
			rm = true;
		}
		return rm;
	}

	/**
	 * 获取勤务类型列表数据，用于勤务类型选择
	 */
	public List<DutyType> loadDutyType(Map<String, Object> map) {
		return dutyTypeMapper.loadDutyType(map);
	}

	/**
	 * 获取勤务类型下级节点属性
	 */
	public List<DutyItemCountVM> loadDutyItemCount(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return dutyTypeMapper.loadDutyItemCount(map);
	}

	public DutyType selectByPrimaryKey(Integer did) {
		// TODO Auto-generated method stub
		return dutyTypeMapper.selectByPrimaryKey(did);
	}

	public int updateByPrimaryKey(DutyType dutytype) {
		// TODO Auto-generated method stub
		return dutyTypeMapper.updateByPrimaryKey(dutytype);
	}

	@Override
	public List<DutyType> loadDutyTypeByParentId(Integer pid) {
		// TODO Auto-generated method stub
		return dutyTypeMapper.loadDutyTypeByParentId(pid);
	}

	@Override
	public List<DutyType> findByNameAndId(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return dutyTypeMapper.findByNameAndId(map);
	}

	@Override
	public List<DutyType> findByName(String typeName) {
		// TODO Auto-generated method stub
		return dutyTypeMapper.findByName(typeName);
	}

}
