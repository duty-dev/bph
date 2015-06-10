package com.tianyi.bph.query.report;

import java.util.List;

import com.tianyi.bph.domain.report.WarningCaseLevel;
import com.tianyi.bph.domain.report.WarningCaseType;
import com.tianyi.bph.domain.report.WarningColor;
import com.tianyi.bph.domain.report.WarningConfig;

public class WarningCfgVM extends WarningConfig {
	private List<WarningColor> colors;
	private List<WarningCaseType> caseTypes; 
	private List<WarningCaseLevel> caseLevels; 
	public List<WarningCaseLevel> getCaseLevels() {
		return caseLevels;
	}
	public void setCaseLevels(List<WarningCaseLevel> caseLevels) {
		this.caseLevels = caseLevels;
	}
	public List<WarningColor> getColors() {
		return colors;
	}
	public void setColors(List<WarningColor> colors) {
		this.colors = colors;
	}
	public List<WarningCaseType> getCaseTypes() {
		return caseTypes;
	}
	public void setCaseTypes(List<WarningCaseType> caseTypes) {
		this.caseTypes = caseTypes;
	} 
	
}
