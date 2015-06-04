package com.tianyi.bph.domain.report;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 报表的时间区间
 * @author xml777
 *
 */
public class ReportPeriod {

	private Integer beginYmd;
	private Integer endYmd;
	private Integer YOYBeginYmd;
	private Integer YOYEndYmd;
	private Integer MOMBeginYmd;
	private Integer MOMEndYmd;
	
	/**
	 * 
	 * @param beginYMD
	 * @param endYMD
	 * @param periodType  
	 ** 查询日期类型
	 ** 1：天
	 ** 2：周
	 ** 3：月
	 ** 4：年 
	 * @throws Exception
	 */
	public ReportPeriod(Integer beginYMD,Integer endYMD,int periodType) throws Exception{
		try{
			this.beginYmd =beginYMD;
			this.endYmd=endYMD;
			
			SimpleDateFormat  sdf = new SimpleDateFormat( "yyyyMMdd" );
			
			Date bd=sdf.parse(beginYMD.toString());
			Date ed=sdf.parse(endYMD.toString());
			
			Calendar  bc=Calendar.getInstance();
			bc.setTime(bd);
			Calendar ec =Calendar.getInstance();
			ec.setTime(ed);
			
			bc.add(Calendar.YEAR, -1);
			ec.add(Calendar.YEAR, -1);
			
			Date ybd=bc.getTime();
			Date yed=ec.getTime();
			this.YOYBeginYmd =Integer.parseInt(sdf.format(ybd));
			this.YOYEndYmd =Integer.parseInt(sdf.format(yed));
			
			bc.setTime(bd);
			ec.setTime(ed);
			
			if(periodType==1){
				
			}else{
				bc.add(Calendar.MONTH, -1);
				ec.add(Calendar.MONTH, -1);
			}
			
			Date mbd=bc.getTime();
			Date med =ec.getTime();
			this.MOMBeginYmd =Integer.parseInt(sdf.format(mbd));
			this.MOMEndYmd =Integer.parseInt(sdf.format(med));

		}catch(Exception ex){
			throw ex;
		}
		
		
//		String beginTmp=beginYMD.toString();
//		String beginDayStr=beginTmp.substring(0,4) +"-" + beginTmp.substring(4,2) + "-" + beginTmp.substring(6);
//		beginDay=beginDay
	}
	
	public Integer getBeginYmd() {
		return beginYmd;
	}

	public void setBeginYmd(Integer beginYmd) {
		this.beginYmd = beginYmd;
	}

	public Integer getEndYmd() {
		return endYmd;
	}

	public void setEndYmd(Integer endYmd) {
		this.endYmd= endYmd;
	}
	
	public Integer getYOYBeginYmd() {
		return YOYBeginYmd;
	}
	public void setYOYBeginYmd(Integer yOYBeginYmd) {
		YOYBeginYmd = yOYBeginYmd;
	}
	public Integer getYOYEndYmd() {
		return YOYEndYmd;
	}
	public void setYOYEndYmd(Integer yOYEndYmd) {
		YOYEndYmd = yOYEndYmd;
	}
	public Integer getMOMBeginYmd() {
		return MOMBeginYmd;
	}
	public void setMOMBeginYmd(Integer mOMBeginYmd) {
		MOMBeginYmd = mOMBeginYmd;
	}
	public Integer getMOMEndYmd() {
		return MOMEndYmd;
	}
	public void setMOMEndYmd	(Integer mOMEndYmd) {
		MOMEndYmd = mOMEndYmd;
	}
	
	
}
