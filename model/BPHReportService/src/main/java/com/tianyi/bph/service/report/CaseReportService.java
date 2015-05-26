package com.tianyi.bph.service.report;

import java.util.Date;

public interface CaseReportService {

	int loadMaxYMD();
	
	Date loadMaxDate();
	
	void insertCaseInfo(Date beginTime,Date endTime);
}
