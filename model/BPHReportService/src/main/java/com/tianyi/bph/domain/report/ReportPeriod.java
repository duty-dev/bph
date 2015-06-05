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
			
			int bm=bc.get(Calendar.MONTH);
			int em=ec.get(Calendar.MONTH);
			
			
			switch(periodType){
			case 1:
				bc.add(Calendar.MONTH, -1);
				ec.add(Calendar.MONTH, -1);
				break;
			case 2:
				bc.add(Calendar.WEEK_OF_YEAR, -1);
				ec.add(Calendar.WEEK_OF_YEAR, -1);
				break;
			case 3:
				int m=this.diffMonth(bc, ec)+1;
				bc.add(Calendar.MONTH, -m);
				ec.add(Calendar.MONTH, -m);
				break;
			case 4:
				bc.add(Calendar.YEAR, -1);
				ec.add(Calendar.YEAR, -1);
				break;
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
	
	private int diffMonth(Calendar c1,Calendar c2){
		int y1=c1.get(Calendar.YEAR);
		int y2=c2.get(Calendar.YEAR);
		int m1=c1.get(Calendar.MONTH);
		int m2=c2.get(Calendar.MONTH);
		
		return  (y2-y1)*12+ m2 -m1;
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
