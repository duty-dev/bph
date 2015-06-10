package com.tianyi.bph.service.impl.report;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianyi.bph.common.MessageCode;
import com.tianyi.bph.common.ReturnResult;
import com.tianyi.bph.dao.report.CaseReportMapper;
import com.tianyi.bph.dao.report.WarningConfigMapper;
import com.tianyi.bph.domain.report.CaseGps;
import com.tianyi.bph.domain.report.CaseHourAGGR;
import com.tianyi.bph.domain.report.CaseOrgAGGR;
import com.tianyi.bph.domain.report.CasePeriodAGGR;
import com.tianyi.bph.domain.report.CaseTypeAGGR;
import com.tianyi.bph.domain.report.ReportPeriod;
import com.tianyi.bph.domain.report.WarningCaseLevel;
import com.tianyi.bph.domain.report.WarningCaseType;
import com.tianyi.bph.domain.report.WarningOrgAGGR;
import com.tianyi.bph.domain.system.Organ;
import com.tianyi.bph.query.report.ColorWarningResultList;
import com.tianyi.bph.query.report.QueryCondition;
import com.tianyi.bph.query.report.WarningCfgVM;
import com.tianyi.bph.service.report.CaseReportService;
import com.tianyi.bph.service.report.WarningCfgService;
import com.tianyi.bph.service.system.OrganService;

@Service
public class CaseReportServiceImpl implements CaseReportService {

	@Autowired
	private CaseReportMapper caseReportMapper;
	@Autowired
	private WarningConfigMapper warningCfgMapper;
	@Autowired
	private OrganService orgService;
	@Autowired
	WarningCfgService warningService;

	@Override
	public int loadMaxYMD() {
		int maxymd = caseReportMapper.loadMaxYMD();
		return maxymd;
	}

	@Override
	public Date loadMaxDate() {
		try {
			Date maxDate = caseReportMapper.loadMaxDate();
			return maxDate;
		} catch (Exception ex) {
			return null;
		}
	}

	@Override
	public void insertCaseInfo(Date beginTime, Date endTime) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("beginTime", beginTime);
		map.put("endTime", endTime);

		caseReportMapper.insertCaseInfo(map);
	}

	@Override
	public List<CaseTypeAGGR> loadCaseTypeReport(Map<String, Object> map) {
		List<CaseTypeAGGR> ls = caseReportMapper.loadCaseTypeReport(map);
		return ls;
	}

	@Override
	public List<CasePeriodAGGR> loadCasePeriodReport(Map<String, Object> map) {
		List<CasePeriodAGGR> ls = caseReportMapper.loadCasePeriodReport(map);
		return ls;
	}

	@Override
	public List<CaseHourAGGR> loadCaseHourReport(Map<String, Object> map) {
		List<CaseHourAGGR> ls = caseReportMapper.loadCaseHourReport(map);
		return ls;
	}

	@Override
	public List<CaseOrgAGGR> loadCaseOrgReport(Map<String, Object> map) {
		List<CaseOrgAGGR> ls = caseReportMapper.loadCaseOrgReport(map);
		return ls;
	}

	@Override
	public List<WarningOrgAGGR> loadWarningReport(Map<String, Object> map) {

		List<WarningOrgAGGR> w = caseReportMapper.loadWarningReport(map);
		return w;
	}

	@Override
	public List<CaseGps> loadCaseGps(Map<String, Object> map) {
		List<CaseGps> ls = caseReportMapper.loadCaseGps(map);
		return ls;
	}

	@Override
	public List<ColorWarningResultList> getWarningReport(QueryCondition queryCondition) {
		// TODO Auto-generated method stub
		List<ColorWarningResultList> list = new ArrayList<ColorWarningResultList>();
		try {
			if (queryCondition == null) {
				return list; 
			} else { 
				Map<String, Object> map = new HashMap<String, Object>();
				// 选择的预警Id
				int colorWarnId = queryCondition.getWarningCfgId();
				WarningCfgVM wcfg = warningService
						.loadWarningCfgVMInfoById(colorWarnId);
				if (wcfg != null) {
					List<Integer> levelList = new ArrayList<Integer>();
					for (WarningCaseLevel s : wcfg.getCaseLevels()) {
						levelList.add(s.getCaseLevel());
					}
					List<String> typeList = new ArrayList<String>();
					for (WarningCaseType s : wcfg.getCaseTypes()) {
						typeList.add(s.getCaseTypeCode());
					}
					map.put("caseLevels", levelList);
					map.put("type2Codes", typeList);
				}
				// 所属机构Id
				int organId = queryCondition.getOrganId();
				Organ organ = orgService.getOrganByPrimaryKey(organId);
				map.put("orgId", organ.getId());
				map.put("orgLevel", this.getOrgLevel(organ.getPath()));

				// 查询时间
				// 查询时间方式，1、日 2、周 3、月 4、年
				String startDate = queryCondition.getStartDate();
				String sDate = startDate.replace("-", "").trim();
				String endDate = queryCondition.getEndDate();
				String eDate = endDate.replace("-", "").trim();
				Integer bd = Integer.parseInt(sDate);
				Integer ed = Integer.parseInt(eDate);
				ReportPeriod rp = new ReportPeriod(bd, ed,
						queryCondition.getPeriodType());
				// 当前查询日期
				map.put("beginYmd", rp.getBeginYmd());
				map.put("endYmd", rp.getEndYmd());

				// 当前
				List<WarningOrgAGGR> w1 = loadWarningReport(map);

				map.put("beginYmd", rp.getMOMBeginYmd());
				map.put("endYmd", rp.getMOMEndYmd());
				// 环比
				List<WarningOrgAGGR> w2 = loadWarningReport(map);
				list = getColorWarningResult(w1, w2);
				return list;
			}
		} catch (Exception ex) {
			return list;
		}
	}

	private List<ColorWarningResultList> getColorWarningResult(
			List<WarningOrgAGGR> w1, List<WarningOrgAGGR> w2) {
		// TODO Auto-generated method stub
		List<ColorWarningResultList> list = new ArrayList<ColorWarningResultList>();
		for (int i = 0; i < w1.size(); i++) {
			ColorWarningResultList cwr = new ColorWarningResultList();
			cwr.setOrganId(w1.get(i).getOrgId());
			cwr.setOrganName(w1.get(i).getOrgName());
			double ccount = w1.get(i).getAmount();
			double pcount = w2.get(i).getAmount();
			int cc = (int) w1.get(i).getAmount();
			int pc = (int) w2.get(i).getAmount();
			cwr.setCurrentcount(cc);
			cwr.setPreviouscount(pc);
			double increase = 0;
			if (pc > 0) {
				double inse = ((ccount - pcount) * 100) / pcount;
				BigDecimal b = new BigDecimal(inse);
				increase = b.setScale(2, BigDecimal.ROUND_HALF_UP)
						.doubleValue();
			} else {
				if (cc == 0) {
					increase = 0.00;
				} else {
					increase = 100.00;
				}
			}
			cwr.setIncrease(increase);
			list.add(cwr);
		}
		return list;
	}

	private int getOrgLevel(String orgPath) {

		String[] a = orgPath.split("/");
		if (a == null) {
			return 0;
		} else {
			return a.length - 1;
		}
	}
}
