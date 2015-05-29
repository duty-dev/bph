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
	String typeParentCode;
	
	public String getTypeParentCode() {
		return typeParentCode;
	}
	public void setTypeParentCode(String typeParentCode) {
		this.typeParentCode = typeParentCode;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getTypeName() {
		return typeName;
	}

	public String getTypeCode() {
		return typeCode;
	}
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
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
