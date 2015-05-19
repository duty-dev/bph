package com.tianyi.bph.domain.alarm;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.tianyi.bph.query.alarm.JJDBView;

public class Jjdb110 {
	private String jjdbh;

	private Integer ajzt;

	private String bjdh;

	private String bjdhyhdz;

	private String bjdhyhxm;

	private String bjfsbh;

	private Integer caseLevel;

	private String bjlb;

	private String bjlx;

	private String bjxl;

	private String bjnr;

	private String bcjjnr;

	private String bjrxm;

	private String bjrxb;

	private Date bjsj;

	private String fkyq;

	private String gljjdbh;

	private String gxdwbh;

	private String jjdwbh;

	private Date jjsj;

	private String jjybh;

	private String jjyxm;

	private String ldgbh;

	private String lhlxbh;

	private String lxdh;

	private double sddwxzb;

	private double sddwyzb;

	private String sfdz;

	private Integer sfsw;

	private Integer sfswybj;

	private String tfhm;

	private String transmitunit;

	private String xzqhbh;

	private Integer ywbkry;

	private Integer ywbzxl;

	private Integer ywwxwz;

	private Integer ywxajbs;

	private String zagj;

	private double zddwxzb;

	private double zddwyzb;

	private Integer zfajbs;

	private String jams;

	private String jar;

	private Date jasj;

	private Integer tPatrolAreaId;

	private Integer tCommunityAreaId;

	private Boolean mark;

	private Integer markUserId;

	private Date markTime;

	private String gpsConfig;
	
	private String bjlbmc; //类别名称

	private String bjlxmc; //类型名称

	private String bjxlmc; //细类名称
	
	public String getBjlbmc() {
		return bjlbmc;
	}

	public void setBjlbmc(String bjlbmc) {
		this.bjlbmc = bjlbmc;
	}

	public String getBjlxmc() {
		return bjlxmc;
	}

	public void setBjlxmc(String bjlxmc) {
		this.bjlxmc = bjlxmc;
	}

	public String getBjxlmc() {
		return bjxlmc;
	}

	public void setBjxlmc(String bjxlmc) {
		this.bjxlmc = bjxlmc;
	}

	private Pole pole; //杆体
	private List<CJInfo> cjInfoList; //处警信息
	private List<CJFeedba> cjFeedBackList; //三台合一处警反馈内容
	private List<PJPolice> pjPoliceList; //派警警员
	private List<AlarmCommunication> alarmCommunicationList; //扁平化反馈信息

	public Pole getPole() {
		return pole;
	}

	public void setPole(Pole pole) {
		this.pole = pole;
	}

	public List<AlarmCommunication> getAlarmCommunicationList() {
		return alarmCommunicationList;
	}

	public void setAlarmCommunicationList(
			List<AlarmCommunication> alarmCommunicationList) {
		this.alarmCommunicationList = alarmCommunicationList;
	}

	public List<CJInfo> getCjInfoList() {
		return cjInfoList;
	}

	public void setCjInfoList(List<CJInfo> cjInfoList) {
		this.cjInfoList = cjInfoList;
	}

	public List<CJFeedba> getCjFeedBackList() {
		return cjFeedBackList;
	}

	public void setCjFeedBackList(List<CJFeedba> cjFeedBackList) {
		this.cjFeedBackList = cjFeedBackList;
	}

	public List<PJPolice> getPjPoliceList() {
		return pjPoliceList;
	}

	public void setPjPoliceList(List<PJPolice> pjPoliceList) {
		this.pjPoliceList = pjPoliceList;
	}

	public String getJjdbh() {
		return jjdbh;
	}

	public void setJjdbh(String jjdbh) {
		this.jjdbh = jjdbh;
	}

	public Integer getAjzt() {
		return ajzt;
	}

	public void setAjzt(Integer ajzt) {
		this.ajzt = ajzt;
	}

	public String getBjdh() {
		return bjdh;
	}

	public void setBjdh(String bjdh) {
		this.bjdh = bjdh;
	}

	public String getBjdhyhdz() {
		return bjdhyhdz;
	}

	public void setBjdhyhdz(String bjdhyhdz) {
		this.bjdhyhdz = bjdhyhdz;
	}

	public String getBjdhyhxm() {
		return bjdhyhxm;
	}

	public void setBjdhyhxm(String bjdhyhxm) {
		this.bjdhyhxm = bjdhyhxm;
	}

	public String getBjfsbh() {
		return bjfsbh;
	}

	public void setBjfsbh(String bjfsbh) {
		this.bjfsbh = bjfsbh;
	}

	public Integer getCaseLevel() {
		return caseLevel;
	}

	public void setCaseLevel(Integer caseLevel) {
		this.caseLevel = caseLevel;
	}

	public String getBjlb() {
		return bjlb;
	}

	public void setBjlb(String bjlb) {
		this.bjlb = bjlb;
	}

	public String getBjlx() {
		return bjlx;
	}

	public void setBjlx(String bjlx) {
		this.bjlx = bjlx;
	}

	public String getBjxl() {
		return bjxl;
	}

	public void setBjxl(String bjxl) {
		this.bjxl = bjxl;
	}

	public String getBjnr() {
		return bjnr;
	}

	public void setBjnr(String bjnr) {
		this.bjnr = bjnr;
	}

	public String getBcjjnr() {
		return bcjjnr;
	}

	public void setBcjjnr(String bcjjnr) {
		this.bcjjnr = bcjjnr;
	}

	public String getBjrxm() {
		return bjrxm;
	}

	public void setBjrxm(String bjrxm) {
		this.bjrxm = bjrxm;
	}

	public String getBjrxb() {
		return bjrxb;
	}

	public void setBjrxb(String bjrxb) {
		this.bjrxb = bjrxb;
	}

	public Date getBjsj() {
		return bjsj;
	}

	public void setBjsj(Date bjsj) {
		this.bjsj = bjsj;
	}

	public String getFkyq() {
		return fkyq;
	}

	public void setFkyq(String fkyq) {
		this.fkyq = fkyq;
	}

	public String getGljjdbh() {
		return gljjdbh;
	}

	public void setGljjdbh(String gljjdbh) {
		this.gljjdbh = gljjdbh;
	}

	public String getGxdwbh() {
		return gxdwbh;
	}

	public void setGxdwbh(String gxdwbh) {
		this.gxdwbh = gxdwbh;
	}

	public String getJjdwbh() {
		return jjdwbh;
	}

	public void setJjdwbh(String jjdwbh) {
		this.jjdwbh = jjdwbh;
	}

	public Date getJjsj() {
		return jjsj;
	}

	public void setJjsj(Date jjsj) {
		this.jjsj = jjsj;
	}

	public String getJjybh() {
		return jjybh;
	}

	public void setJjybh(String jjybh) {
		this.jjybh = jjybh;
	}

	public String getJjyxm() {
		return jjyxm;
	}

	public void setJjyxm(String jjyxm) {
		this.jjyxm = jjyxm;
	}

	public String getLdgbh() {
		return ldgbh;
	}

	public void setLdgbh(String ldgbh) {
		this.ldgbh = ldgbh;
	}

	public String getLhlxbh() {
		return lhlxbh;
	}

	public void setLhlxbh(String lhlxbh) {
		this.lhlxbh = lhlxbh;
	}

	public String getLxdh() {
		return lxdh;
	}

	public void setLxdh(String lxdh) {
		this.lxdh = lxdh;
	}

	public double getSddwxzb() {
		return sddwxzb;
	}

	public void setSddwxzb(double sddwxzb) {
		this.sddwxzb = sddwxzb;
	}

	public double getSddwyzb() {
		return sddwyzb;
	}

	public void setSddwyzb(double sddwyzb) {
		this.sddwyzb = sddwyzb;
	}

	public String getSfdz() {
		return sfdz;
	}

	public void setSfdz(String sfdz) {
		this.sfdz = sfdz;
	}

	public Integer getSfsw() {
		return sfsw;
	}

	public void setSfsw(Integer sfsw) {
		this.sfsw = sfsw;
	}

	public Integer getSfswybj() {
		return sfswybj;
	}

	public void setSfswybj(Integer sfswybj) {
		this.sfswybj = sfswybj;
	}

	public String getTfhm() {
		return tfhm;
	}

	public void setTfhm(String tfhm) {
		this.tfhm = tfhm;
	}

	public String getTransmitunit() {
		return transmitunit;
	}

	public void setTransmitunit(String transmitunit) {
		this.transmitunit = transmitunit;
	}

	public String getXzqhbh() {
		return xzqhbh;
	}

	public void setXzqhbh(String xzqhbh) {
		this.xzqhbh = xzqhbh;
	}

	public Integer getYwbkry() {
		return ywbkry;
	}

	public void setYwbkry(Integer ywbkry) {
		this.ywbkry = ywbkry;
	}

	public Integer getYwbzxl() {
		return ywbzxl;
	}

	public void setYwbzxl(Integer ywbzxl) {
		this.ywbzxl = ywbzxl;
	}

	public Integer getYwwxwz() {
		return ywwxwz;
	}

	public void setYwwxwz(Integer ywwxwz) {
		this.ywwxwz = ywwxwz;
	}

	public Integer getYwxajbs() {
		return ywxajbs;
	}

	public void setYwxajbs(Integer ywxajbs) {
		this.ywxajbs = ywxajbs;
	}

	public String getZagj() {
		return zagj;
	}

	public void setZagj(String zagj) {
		this.zagj = zagj;
	}

	public double getZddwxzb() {
		return zddwxzb;
	}

	public void setZddwxzb(double zddwxzb) {
		this.zddwxzb = zddwxzb;
	}

	public double getZddwyzb() {
		return zddwyzb;
	}

	public void setZddwyzb(double zddwyzb) {
		this.zddwyzb = zddwyzb;
	}

	public Integer getZfajbs() {
		return zfajbs;
	}

	public void setZfajbs(Integer zfajbs) {
		this.zfajbs = zfajbs;
	}

	public String getJams() {
		return jams;
	}

	public void setJams(String jams) {
		this.jams = jams;
	}

	public String getJar() {
		return jar;
	}

	public void setJar(String jar) {
		this.jar = jar;
	}

	public Date getJasj() {
		return jasj;
	}

	public void setJasj(Date jasj) {
		this.jasj = jasj;
	}

	public Integer gettPatrolAreaId() {
		return tPatrolAreaId;
	}

	public void settPatrolAreaId(Integer tPatrolAreaId) {
		this.tPatrolAreaId = tPatrolAreaId;
	}

	public Integer gettCommunityAreaId() {
		return tCommunityAreaId;
	}

	public void settCommunityAreaId(Integer tCommunityAreaId) {
		this.tCommunityAreaId = tCommunityAreaId;
	}

	public Boolean getMark() {
		return mark;
	}

	public void setMark(Boolean mark) {
		this.mark = mark;
	}

	public Integer getMarkUserId() {
		return markUserId;
	}

	public void setMarkUserId(Integer markUserId) {
		this.markUserId = markUserId;
	}

	public Date getMarkTime() {
		return markTime;
	}

	public void setMarkTime(Date markTime) {
		this.markTime = markTime;
	}

	public String getGpsConfig() {
		return gpsConfig;
	}

	public void setGpsConfig(String gpsConfig) {
		this.gpsConfig = gpsConfig;
	}
	
	public JJDBView ctrate() {
		JJDBView jjdbView = new JJDBView();
		if(this.ajzt != null){jjdbView.setAjzt(this.ajzt);}
		if(!StringUtils.isEmpty(this.bjdh)){jjdbView.setBjdh(this.bjdh);}
		if(!StringUtils.isEmpty(this.bjdhyhxm)){jjdbView.setBjdhyhxm(bjdhyhxm);}
		if(!StringUtils.isEmpty(this.bjfsbh)){jjdbView.setBjfsbh(this.bjfsbh);}
		if(!StringUtils.isEmpty(this.bjlb)){jjdbView.setBjlb(this.bjlb);}
		if(!StringUtils.isEmpty(this.bjlx)){jjdbView.setBjlx(this.bjlx);}
		if(!StringUtils.isEmpty(this.bjnr)){jjdbView.setBjnr(this.bjnr);}
		if(this.bjsj != null){jjdbView.setBjsj(this.bjsj);}
		if(this.caseLevel != null){jjdbView.setCaseLevel(String.valueOf(this.caseLevel));}
		if(!StringUtils.isEmpty(this.gxdwbh)){jjdbView.setGxdwbh(this.gxdwbh);}
		if(!StringUtils.isEmpty(this.jjdbh)){jjdbView.setJjdbh(this.jjdbh);}
		if(this.mark != null){jjdbView.setMark(this.mark);}
		if(!StringUtils.isEmpty(this.sfdz)){jjdbView.setSfdz(this.sfdz);}
		return jjdbView;		
	}

}