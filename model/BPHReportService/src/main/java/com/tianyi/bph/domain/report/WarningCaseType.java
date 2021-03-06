package com.tianyi.bph.domain.report;

public class WarningCaseType {
    private Integer id;

    private Integer warningId;

    private String caseTypeCode;
    
    private String caseTypeName;

    private Integer caseTypeLevel;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getWarningId() {
        return warningId;
    }

    public void setWarningId(Integer warningId) {
        this.warningId = warningId;
    }

    public String getCaseTypeCode() {
        return caseTypeCode;
    }

    public void setCaseTypeCode(String caseTypeCode) {
        this.caseTypeCode=caseTypeCode;
    }

    public Integer getCaseTypeLevel() {
        return caseTypeLevel;
    }

    public void setCaseTypeLevel(Integer caseTypeLevel) {
        this.caseTypeLevel = caseTypeLevel;
    }

	public String getCaseTypeName() {
		return caseTypeName;
	}

	public void setCaseTypeName(String caseTypeName) {
		this.caseTypeName = caseTypeName;
	}
}