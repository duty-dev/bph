package com.tianyi.bph.query.report;

public class ColorWarningResultList {
	private Integer organId;
	private String organName;
	private int previouscount;
	private int currentcount;
	private double  increase;
	public Integer getOrganId() {
		return organId;
	}
	public void setOrganId(Integer organId) {
		this.organId = organId;
	}
	public String getOrganName() {
		return organName;
	}
	public void setOrganName(String organName) {
		this.organName = organName;
	}
	public Integer getPreviouscount() {
		return previouscount;
	}
	public void setPreviouscount(Integer previouscount) {
		this.previouscount = previouscount;
	}
	public Integer getCurrentcount() {
		return currentcount;
	}
	public void setCurrentcount(Integer currentcount) {
		this.currentcount = currentcount;
	}
	public double getIncrease() {
		return increase;
	}
	public void setIncrease(double increase) {
		this.increase = increase;
	}
}
