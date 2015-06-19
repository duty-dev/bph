package com.tianyi.bph.web.controller.reportdata;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.tianyi.bph.service.report.CaseReportService;
import com.tianyi.bph.service.system.LogService;

/*
 * 定时执行警情案件数据导入
 * 异步执行
 */
@Component
public class CaseTask {
	
	@Autowired
	private CaseReportService  caseReportService;
	
	@Autowired 
	private LogService logService;
	
	//@Scheduled(cron = "0 32 12 * * ?")  /*每天凌晨3:30执行数据导入*/
	public  void importData() {
		try{
		Date  maxDate=caseReportService.loadMaxDate();
		
		Date beginTime=null;
		Date endTime=null;

		beginTime=maxDate;
		
//		if(maxDate==null){
//			beginTime=null;
//		}else{
//			Calendar  c=Calendar.getInstance();
//			c.setTime(maxDate);
//			c.set(Calendar.HOUR_OF_DAY,0);
//			c.set(Calendar.MINUTE, 0);
//			c.set(Calendar.SECOND, 0);
//			c.set(Calendar.MILLISECOND, 0);
//			c.add(Calendar.DATE, 1);
//			beginTime = c.getTime();
//		}
		
		Calendar c2=Calendar.getInstance();
		c2.setTime(new Date());
		c2.set(Calendar.HOUR_OF_DAY,0);
		c2.set(Calendar.MINUTE, 0);
		c2.set(Calendar.SECOND, 0);
		c2.set(Calendar.MILLISECOND, 0);
		//c2.add(Calendar.DATE, 2);
		
		endTime = c2.getTime();
		
		caseReportService.importCaseInfo(beginTime, endTime);
		
		}catch(Exception ex){
			logService.insert("", "", "","导入数据时发生错误!", 0);
		}
	}
}
