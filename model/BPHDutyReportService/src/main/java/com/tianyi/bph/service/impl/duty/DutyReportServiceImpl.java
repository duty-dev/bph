package com.tianyi.bph.service.impl.duty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tianyi.bph.dao.duty.DutyReportMapper;
import com.tianyi.bph.query.duty.DutyReportCriteria;
import com.tianyi.bph.query.duty.DutyReportVM;
import com.tianyi.bph.service.duty.DutyReportService;
 

/**
 * 警务综合查询，接口实现
 * @author lq
 *
 */
@Service("dutyReportService")
public class DutyReportServiceImpl implements DutyReportService{

	/**
	 * 初始化警务综合查询映射
	 */
	@Resource(name = "dutyReportMapper")
	private DutyReportMapper dutyReportMapper;
	
	/**
	 * 根据查询报条件，查询警务综合查询数据
	 * param：组织机构id
	 * 日期
	 * 开始时间、结束时间
	 * 着装
	 * 武器
	 * 属性等
	 */
	public List<DutyReportVM> loadDutyReport(DutyReportCriteria criteria) {
		Map<String,Object> map=new HashMap<String,Object>();
		if(criteria.getOrgIds().size()>0){
			map.put("orgIds", criteria.getOrgIds());
		}
		map.put("ymd", criteria.getYmd());
		map.put("beginTime", criteria.getBeginTime());
		map.put("endTime", criteria.getEndTime());
		if(criteria.getTaskPropertyIds().size()>0){
			map.put("taskPropertyIds", criteria.getTaskPropertyIds());
		}
		if(criteria.getAttireTypeIds().size()>0){
			map.put("attireTypeIds", criteria.getAttireTypeIds());
		}
		if(criteria.getPoliceTypeIds().size()>0){ 
			 
			map.put("policeTypeIds", criteria.getPoliceTypeIds());
			 
		}
		if(criteria.getArmamentTypeIds().size()>0){
			map.put("armamentTypeIds", criteria.getArmamentTypeIds());
		}
		if(criteria.getDutyTypeIds().size()>0){
			map.put("dutyTypeIds", criteria.getDutyTypeIds());
		}
		
		List<DutyReportVM> ls=dutyReportMapper.loadDutyReport(map);
		
		return ls;
	}

}
