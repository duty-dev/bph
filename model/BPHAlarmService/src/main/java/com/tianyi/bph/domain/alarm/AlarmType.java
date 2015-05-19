package com.tianyi.bph.domain.alarm;

public class AlarmType {
	
	private String typeCode;
	private String typeName;
	private String parentTypeCode;
	private String note;
	private String constrast;
	private Integer typeLevel;
	
	public String getTypeCode() {
		return typeCode;
	}
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getParentTypeCode() {
		return parentTypeCode;
	}
	public void setParentTypeCode(String parentTypeCode) {
		this.parentTypeCode = parentTypeCode;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}

	public String getConstrast() {
		return constrast;
	}
	public void setConstrast(String constrast) {
		this.constrast = constrast;
	}
	public Integer getTypeLevel() {
		if(parentTypeCode==null || "caseType110".equals(parentTypeCode)){
			return 1;
		}else{
			return 2;
		}
	}
	public void setTypeLevel(Integer typeLevel) {
		this.typeLevel = typeLevel;
	}
	

}
