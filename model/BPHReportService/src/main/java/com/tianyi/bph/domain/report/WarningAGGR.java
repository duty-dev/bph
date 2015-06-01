package com.tianyi.bph.domain.report;

/**
 * 四色预警结果
 * @author xml777
 *
 */
public class WarningAGGR {
	private Integer orgId;
	private Integer amount1;
	private Integer amount2;
	private double scale;
	private Integer level;
	
	public Integer getOrgId() {
		return orgId;
	}
	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}
	/**
	 * 上期案件数量
	 * @return
	 */
	public Integer getAmount1() {
		return amount1;
	}
	public void setAmount1(Integer amount1) {
		this.amount1 = amount1;
	}
	/**
	 * 当前发案数量
	 * @return
	 */
	public Integer getAmount2() {
		return amount2;
	}
	public void setAmount2(Integer amount2) {
		this.amount2 = amount2;
	}
	public double getScale() {
		return scale;
	}
	public void setScale(double scale) {
		this.scale = scale;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	
}
