package com.megaeyes.drs.bean;


public class Jjdb110Bean {

	private String jjdbh; // 接警单编号
	private String xzqhbh; // 行政区划编号
	private String jjdwbh; // 接警单位编号
	private String gljjdbh; // 关联接警单编号
	private String lhlxbh; // 来话类型编号
	private String bjfsbh; // 报警方式编号
	private String tfhm; // 特服号码
	private String jjybh; // 接警员编号
	private String jjyxm; // 接警员姓名
	private String bjsj; // 报警时间
	private String jjsj; // 接警时间
	private String bjdh; // 报警电话
	private String bjdhyhxm; // 报警电话用户姓名
	private String bjdhyhdz; // 报警电话用户地址
	private String bjrxm; // 报警人姓名
	private String bjrxb; // 报警人性别
	private String lxdh; // 联系电话
	private String sfdz; // 事发地址
	private String bjnr; // 报警内容
	private String gxdwbh; // 管辖单位编号
	private String bjlb; // 报警类别
	// private String bjlbMc; //报警类别名称
	private String bjlx; // 报警类型
	// private String bjlxMc; //报警类型名称
	private String bjxl; // 报警细类
	// private String bjxlMc; //报警细类名称
	private String ldgbh; // 路灯杆编号
	private Integer ywwxwz; // 有无危险物质
	private Integer ywbzxl; // 有无爆炸/泄漏
	private Integer ywbkry; // 有无被困人员
	private Integer sfsw; // 是否涉外
	private Integer sfswybj; // 是否是外语报警
	private String zddwxzb; // 自动定位X坐标（报警人位置）
	private String zddwyzb; // 自动定位Y坐标（报警人位置）
	private String sddwxzb; // 手动定位X坐标（报警人位置）
	private String sddwyzb; // 手动定位Y坐标（报警人位置）
	private String bcjjnr; // 补充接警内容
	private String rksjc; // 入库时间戳
	private String gxsj; // 更新时间
	private String gxrxm; // 更新人姓名
	private String fkyq; // 反馈要求
	private Integer ajzt; // 案件状态
	private Integer ywxajbs; // 有无效案件标识（0 无效 1有效）
	private Integer zfajbs; // 转发案件标识
	private String zagj; // 作案工具
	private String gxrbh; // 更新人编号
	private String transmitunit; // 转发单位
	private int istranslated; // 是否已将此条数据发送到警综的中间表(0未发送1已发送)
	private String facs;
	private String jzfknr; // 2013-11-8 增加警综反馈内容

	private Integer caseLevel; // 2014-08-21 警情级别

	public String getJjdbh() {
		return jjdbh;
	}

	public void setJjdbh(String jjdbh) {
		this.jjdbh = jjdbh;
	}

	public String getXzqhbh() {
		return xzqhbh;
	}

	public void setXzqhbh(String xzqhbh) {
		this.xzqhbh = xzqhbh;
	}

	public String getJjdwbh() {
		return jjdwbh;
	}

	public void setJjdwbh(String jjdwbh) {
		this.jjdwbh = jjdwbh;
	}

	public String getGljjdbh() {
		return gljjdbh;
	}

	public void setGljjdbh(String gljjdbh) {
		this.gljjdbh = gljjdbh;
	}

	public String getLhlxbh() {
		return lhlxbh;
	}

	public void setLhlxbh(String lhlxbh) {
		this.lhlxbh = lhlxbh;
	}

	public String getBjfsbh() {
		return bjfsbh;
	}

	public void setBjfsbh(String bjfsbh) {
		this.bjfsbh = bjfsbh;
	}

	public String getTfhm() {
		return tfhm;
	}

	public void setTfhm(String tfhm) {
		this.tfhm = tfhm;
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

	public String getBjsj() {
		return bjsj;
	}

	public void setBjsj(String bjsj) {
		this.bjsj = bjsj;
	}

	public String getJjsj() {
		return jjsj;
	}

	public void setJjsj(String jjsj) {
		this.jjsj = jjsj;
	}

	public String getBjdh() {
		return bjdh;
	}

	public void setBjdh(String bjdh) {
		this.bjdh = bjdh;
	}

	public String getBjdhyhxm() {
		return bjdhyhxm;
	}

	public void setBjdhyhxm(String bjdhyhxm) {
		this.bjdhyhxm = bjdhyhxm;
	}

	public String getBjdhyhdz() {
		return bjdhyhdz;
	}

	public void setBjdhyhdz(String bjdhyhdz) {
		this.bjdhyhdz = bjdhyhdz;
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

	public String getLxdh() {
		return lxdh;
	}

	public void setLxdh(String lxdh) {
		this.lxdh = lxdh;
	}

	public String getSfdz() {
		return sfdz;
	}

	public void setSfdz(String sfdz) {
		this.sfdz = sfdz;
	}

	public String getBjnr() {
		return bjnr;
	}

	public void setBjnr(String bjnr) {
		this.bjnr = bjnr;
	}

	public String getGxdwbh() {
		return gxdwbh;
	}

	public void setGxdwbh(String gxdwbh) {
		this.gxdwbh = gxdwbh;
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

	public String getLdgbh() {
		return ldgbh;
	}

	public void setLdgbh(String ldgbh) {
		this.ldgbh = ldgbh;
	}

	public Integer getYwwxwz() {
		return ywwxwz;
	}

	public void setYwwxwz(Integer ywwxwz) {
		this.ywwxwz = ywwxwz;
	}

	public Integer getYwbzxl() {
		return ywbzxl;
	}

	public void setYwbzxl(Integer ywbzxl) {
		this.ywbzxl = ywbzxl;
	}

	public Integer getYwbkry() {
		return ywbkry;
	}

	public void setYwbkry(Integer ywbkry) {
		this.ywbkry = ywbkry;
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

	public String getZddwxzb() {
		return zddwxzb;
	}

	public void setZddwxzb(String zddwxzb) {
		this.zddwxzb = zddwxzb;
	}

	public String getZddwyzb() {
		return zddwyzb;
	}

	public void setZddwyzb(String zddwyzb) {
		this.zddwyzb = zddwyzb;
	}

	public String getSddwxzb() {
		return sddwxzb;
	}

	public void setSddwxzb(String sddwxzb) {
		this.sddwxzb = sddwxzb;
	}

	public String getSddwyzb() {
		return sddwyzb;
	}

	public void setSddwyzb(String sddwyzb) {
		this.sddwyzb = sddwyzb;
	}

	public String getBcjjnr() {
		return bcjjnr;
	}

	public void setBcjjnr(String bcjjnr) {
		this.bcjjnr = bcjjnr;
	}

	public String getRksjc() {
		return rksjc;
	}

	public void setRksjc(String rksjc) {
		this.rksjc = rksjc;
	}

	public String getGxsj() {
		return gxsj;
	}

	public void setGxsj(String gxsj) {
		this.gxsj = gxsj;
	}

	public String getGxrxm() {
		return gxrxm;
	}

	public void setGxrxm(String gxrxm) {
		this.gxrxm = gxrxm;
	}

	public String getFkyq() {
		return fkyq;
	}

	public void setFkyq(String fkyq) {
		this.fkyq = fkyq;
	}

	public Integer getAjzt() {
		return ajzt;
	}

	public void setAjzt(Integer ajzt) {
		this.ajzt = ajzt;
	}

	public Integer getYwxajbs() {
		return ywxajbs;
	}

	public void setYwxajbs(Integer ywxajbs) {
		this.ywxajbs = ywxajbs;
	}

	public Integer getZfajbs() {
		return zfajbs;
	}

	public void setZfajbs(Integer zfajbs) {
		this.zfajbs = zfajbs;
	}

	public String getZagj() {
		return zagj;
	}

	public void setZagj(String zagj) {
		this.zagj = zagj;
	}

	public String getGxrbh() {
		return gxrbh;
	}

	public void setGxrbh(String gxrbh) {
		this.gxrbh = gxrbh;
	}

	public String getTransmitunit() {
		return transmitunit;
	}

	public void setTransmitunit(String transmitunit) {
		this.transmitunit = transmitunit;
	}

	public int getIstranslated() {
		return istranslated;
	}

	public void setIstranslated(int istranslated) {
		this.istranslated = istranslated;
	}

	public String getFacs() {
		return facs;
	}

	public void setFacs(String facs) {
		this.facs = facs;
	}

	public String getJzfknr() {
		return jzfknr;
	}

	public void setJzfknr(String jzfknr) {
		this.jzfknr = jzfknr;
	}

	public Integer getCaseLevel() {
		return caseLevel;
	}

	public void setCaseLevel(Integer caseLevel) {
		this.caseLevel = caseLevel;
	}

}
