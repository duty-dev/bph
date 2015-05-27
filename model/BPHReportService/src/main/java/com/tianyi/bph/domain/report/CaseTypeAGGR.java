package com.tianyi.bph.domain.report;

/**
 * 从案件类型统计
 * @author xml777
 *
 */
public class CaseTypeAGGR {
	String typeCode;
	String typeName;
	Integer typeLevel;
	Integer  amount;
	
	public String getTypeCode() {
		return typeCode;
	}
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setType1Name(String typeName) {
		this.typeName = typeName;
	}
	public Integer getTypeLevel() {
		return typeLevel;
	}
	public void setTypeLevel(Integer typeLevel) {
		this.typeLevel = typeLevel;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
}
