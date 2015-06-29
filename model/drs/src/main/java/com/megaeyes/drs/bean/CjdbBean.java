package com.megaeyes.drs.bean;


public class CjdbBean {

	private String cjdbh; // 处警单编号
	private String jjdbh; // 接警单编号
	private String cjdwbh; // 处警单位编号
	private String cjybh; // 处警员编号
	private String cjyxm; // 处警员姓名
	private String czsj; // 处置时间
	private String ddxcsj; // 到达现场时间
	private String clwbsj; // 处理完毕时间
	private String scfksj; // 首次反馈时间
	private String fknr; // 反馈内容
	private String cjqk; // 出警情况
	private String cljglx; // 处理结果类型
	private String cljg; // 处理结果
	private String cdry; // 出动人员
	private String cdcl; // 出动车辆
	private Integer zt; // 状态
	private String czlx; // 处置类型
	private String cjsj; // 创建时间
	private String jjdpqjl; // 某些地方要求加上接警台派人派车的文字记录字段
	private int istranslated; // 是否已将此条数据发送到警综的中间表(0未发送1已发送)
	private String sMyd; // 对处警情况的满意度评价(部分地市要求使用)
	private String dHftime; // 回访时间(部分地市要求使用)
	private String sHftel; // 回访电话(部分地市要求使用)
	private String sHfnr; // 回访内容(部分地市要求使用)
	private String sJjdwxzqhbh; // 接警单位行政区划编号(部分地市要求使用)
	private int nHfcs; // 回访次数(部分地市要求使用)
	private String jqpqdwbh; // 警情派遣单位（就是执行案件的派发操作的单位）

	// 出警信息
	private String cjdwbh2; // 出警单位编号
	private String cjdwmc2; // 出警单位名称
	private String cjsj2; // 出警时间
	private String clsj2; // 撤离时间

	// 巡逻信息
	private String xldwbh2; // 巡逻单位编号
	private String xldwmc2; // 巡逻单位名称
	private String xlzqwsj2; // 巡逻组前往时间
	private String xlzddxcsj2;// 巡逻组到达现场时间

	private String dsrdh; // 当事人电话
	private String dsrxm; // 当事人姓名

	private String xlzddxcsj; // 巡逻组到达现场时间
	private String xlzclxcsj; // 巡逻组撤离现场时间

	public String getCjdbh() {
		return cjdbh;
	}

	public void setCjdbh(String cjdbh) {
		this.cjdbh = cjdbh;
	}

	public String getJjdbh() {
		return jjdbh;
	}

	public void setJjdbh(String jjdbh) {
		this.jjdbh = jjdbh;
	}

	public String getCjdwbh() {
		return cjdwbh;
	}

	public void setCjdwbh(String cjdwbh) {
		this.cjdwbh = cjdwbh;
	}

	public String getCjybh() {
		return cjybh;
	}

	public void setCjybh(String cjybh) {
		this.cjybh = cjybh;
	}

	public String getCjyxm() {
		return cjyxm;
	}

	public void setCjyxm(String cjyxm) {
		this.cjyxm = cjyxm;
	}

	public String getCzsj() {
		return czsj;
	}

	public void setCzsj(String czsj) {
		this.czsj = czsj;
	}

	public String getDdxcsj() {
		return ddxcsj;
	}

	public void setDdxcsj(String ddxcsj) {
		this.ddxcsj = ddxcsj;
	}

	public String getClwbsj() {
		return clwbsj;
	}

	public void setClwbsj(String clwbsj) {
		this.clwbsj = clwbsj;
	}

	public String getScfksj() {
		return scfksj;
	}

	public void setScfksj(String scfksj) {
		this.scfksj = scfksj;
	}

	public String getFknr() {
		return fknr;
	}

	public void setFknr(String fknr) {
		this.fknr = fknr;
	}

	public String getCjqk() {
		return cjqk;
	}

	public void setCjqk(String cjqk) {
		this.cjqk = cjqk;
	}

	public String getCljglx() {
		return cljglx;
	}

	public void setCljglx(String cljglx) {
		this.cljglx = cljglx;
	}

	public String getCljg() {
		return cljg;
	}

	public void setCljg(String cljg) {
		this.cljg = cljg;
	}

	public String getCdry() {
		return cdry;
	}

	public void setCdry(String cdry) {
		this.cdry = cdry;
	}

	public String getCdcl() {
		return cdcl;
	}

	public void setCdcl(String cdcl) {
		this.cdcl = cdcl;
	}

	public Integer getZt() {
		return zt;
	}

	public void setZt(Integer zt) {
		this.zt = zt;
	}

	public String getCzlx() {
		return czlx;
	}

	public void setCzlx(String czlx) {
		this.czlx = czlx;
	}

	public String getCjsj() {
		return cjsj;
	}

	public void setCjsj(String cjsj) {
		this.cjsj = cjsj;
	}

	public String getJjdpqjl() {
		return jjdpqjl;
	}

	public void setJjdpqjl(String jjdpqjl) {
		this.jjdpqjl = jjdpqjl;
	}

	public int getIstranslated() {
		return istranslated;
	}

	public void setIstranslated(int istranslated) {
		this.istranslated = istranslated;
	}

	public String getSMyd() {
		return sMyd;
	}

	public void setSMyd(String myd) {
		sMyd = myd;
	}

	public String getSHftel() {
		return sHftel;
	}

	public void setSHftel(String hftel) {
		sHftel = hftel;
	}

	public String getSHfnr() {
		return sHfnr;
	}

	public void setSHfnr(String hfnr) {
		sHfnr = hfnr;
	}

	public String getSJjdwxzqhbh() {
		return sJjdwxzqhbh;
	}

	public void setSJjdwxzqhbh(String jjdwxzqhbh) {
		sJjdwxzqhbh = jjdwxzqhbh;
	}

	public int getNHfcs() {
		return nHfcs;
	}

	public void setNHfcs(int hfcs) {
		nHfcs = hfcs;
	}

	public String getJqpqdwbh() {
		return jqpqdwbh;
	}

	public void setJqpqdwbh(String jqpqdwbh) {
		this.jqpqdwbh = jqpqdwbh;
	}

	public String getDHftime() {
		return dHftime;
	}

	public void setDHftime(String hftime) {
		dHftime = hftime;
	}

	public String getCjdwbh2() {
		return cjdwbh2;
	}

	public void setCjdwbh2(String cjdwbh2) {
		this.cjdwbh2 = cjdwbh2;
	}

	public String getCjdwmc2() {
		return cjdwmc2;
	}

	public void setCjdwmc2(String cjdwmc2) {
		this.cjdwmc2 = cjdwmc2;
	}

	public String getCjsj2() {
		return cjsj2;
	}

	public void setCjsj2(String cjsj2) {
		this.cjsj2 = cjsj2;
	}

	public String getClsj2() {
		return clsj2;
	}

	public void setClsj2(String clsj2) {
		this.clsj2 = clsj2;
	}

	public String getXldwbh2() {
		return xldwbh2;
	}

	public void setXldwbh2(String xldwbh2) {
		this.xldwbh2 = xldwbh2;
	}

	public String getXldwmc2() {
		return xldwmc2;
	}

	public void setXldwmc2(String xldwmc2) {
		this.xldwmc2 = xldwmc2;
	}

	public String getXlzqwsj2() {
		return xlzqwsj2;
	}

	public void setXlzqwsj2(String xlzqwsj2) {
		this.xlzqwsj2 = xlzqwsj2;
	}

	public String getXlzddxcsj2() {
		return xlzddxcsj2;
	}

	public void setXlzddxcsj2(String xlzddxcsj2) {
		this.xlzddxcsj2 = xlzddxcsj2;
	}

	public String getDsrdh() {
		return dsrdh;
	}

	public void setDsrdh(String dsrdh) {
		this.dsrdh = dsrdh;
	}

	public String getDsrxm() {
		return dsrxm;
	}

	public void setDsrxm(String dsrxm) {
		this.dsrxm = dsrxm;
	}

	public String getXlzddxcsj() {
		return xlzddxcsj;
	}

	public void setXlzddxcsj(String xlzddxcsj) {
		this.xlzddxcsj = xlzddxcsj;
	}

	public String getXlzclxcsj() {
		return xlzclxcsj;
	}

	public void setXlzclxcsj(String xlzclxcsj) {
		this.xlzclxcsj = xlzclxcsj;
	}

}
