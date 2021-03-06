package com.tianyi.bph.service.impl.duty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tianyi.bph.dao.duty.DutyTaskMapper;
import com.tianyi.bph.domain.duty.Org;
import com.tianyi.bph.domain.system.Organ;
import com.tianyi.bph.query.duty.TaskTargetVM;
import com.tianyi.bph.service.duty.DutyTaskService;
 

/**
 * 勤务报备关联任务服务接口实现
 * @author lq
 *
 */
@Service("dutyTaskService")
public class DutyTaskServiceImpl implements DutyTaskService{

	@Resource(name = "dutyTaskMapper")
	private DutyTaskMapper dutyTaskMapper;
	
	/**
	 * 根据勤务类型，组织机构对象，获取关联任务列表
	 * 巡区：巡区名称、必到点、停留时间、经过次数
	 * 社区：社区名称
	 * 卡点：卡点名称
	 */
	public List<TaskTargetVM> loadTaskTargetVMList(Integer taskType, Organ org) {
		Map<String,Object>map=new HashMap<String, Object>();
		map.put("orgId", org.getId());
		map.put("orgCode", org.getCode());
		
		List<TaskTargetVM> ls=null;
		
		switch(taskType){
		case 1:
			ls=dutyTaskMapper.loadCommunityByOrg(map);
			break;
		case 2:
			ls=dutyTaskMapper.loadPatrolAreaByOrg(map);
			break;
		case 3:
			ls=dutyTaskMapper.loadBayonetByOrg(map);
			break;
		}
		
		return ls;
	}

	@Override
	public TaskTargetVM getTaskTargetInfo(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return dutyTaskMapper.getTaskTargetInfo(map);
	}

}
