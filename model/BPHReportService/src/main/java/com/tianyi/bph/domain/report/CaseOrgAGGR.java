package com.tianyi.bph.domain.report;

public class CaseOrgAGGR {
	private Integer orgId;
	private String orgCode;
	private String orgPath;
	private String orgParentPath;
	private Integer orgParentId;
	private String orgName;
	private String typeCode;
	private String typeName;
	private String typeParentCode;
	private String typeLevel;
	private Integer amount;
	
	public String getOrgPath() {
		return orgPath;
	}
	public void setOrgPath(String orgPath) {
		this.orgPath = orgPath;
	}
	public String getOrgParentPath() {
		return orgParentPath;
	}
	public void setOrgParentPath(String orgParentPath) {
		this.orgParentPath = orgParentPath;
	}
	public Integer getOrgParentId() {
		return orgParentId;
	}
	public void setOrgParentId(Integer orgParentId) {
		this.orgParentId = orgParentId;
	}
	public Integer getOrgId() {
		return orgId;
	}
	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
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
	public String getTypeParentCode() {
		return typeParentCode;
	}
	public void setTypeParentCode(String typeParentCode) {
		this.typeParentCode = typeParentCode;
	}
	public String getTypeLevel() {
		return typeLevel;
	}
	public void setTypeLevel(String typeLevel) {
		this.typeLevel = typeLevel;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	
}
