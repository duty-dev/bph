package com.megaeyes.drs.domain;

import java.io.Serializable;

/**
 * 武器
 * 
 * @author lj
 * @time 2013-10-29 上午09:06:27
 */
public class Weapon implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9091158086111129890L;

	private int id;
	private String code; // 武器编号 枪号
	private String standard; // 武器制式
	private Integer type; // 武器类型 0手枪 1 冲锋枪 2: 54式手枪 3: 64式手枪 4:77式手枪 5:92式手枪
							// 6:92转轮手枪 7:79式冲锋枪 8:97防暴枪 9:95式突击步枪

	private String shot; // 弹发
	private String nomalShot; // 常规配发子弹数
	private Organ organ; // 所属机构

	private int markStatus; // 标记状态 0:正常 1:已删除

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getStandard() {
		return standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getShot() {
		return shot;
	}

	public void setShot(String shot) {
		this.shot = shot;
	}

	public String getNomalShot() {
		return nomalShot;
	}

	public void setNomalShot(String nomalShot) {
		this.nomalShot = nomalShot;
	}

	public Organ getOrgan() {
		return organ;
	}

	public void setOrgan(Organ organ) {
		this.organ = organ;
	}

	public int getMarkStatus() {
		return markStatus;
	}

	public void setMarkStatus(int markStatus) {
		this.markStatus = markStatus;
	}

}
