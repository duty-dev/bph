package com.tianyi.bph.domain.report;

public class CaseGps implements Comparable<CaseGps>{
	private Integer orgId;
	private String type1;
	private String type2;
	private Integer caseLevel;
	private String gps;
	private String jjcode;
	private String parTypeName;
	private String subTypeName;
	
	public String getParTypeName() {
		return parTypeName;
	}
	public void setParTypeName(String parTypeName) {
		this.parTypeName = parTypeName;
	}
	public String getSubTypeName() {
		return subTypeName;
	}
	public void setSubTypeName(String subTypeName) {
		this.subTypeName = subTypeName;
	}
	public Integer getOrgId() {
		return orgId;
	}
	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}
	public String getType1() {
		return type1;
	}
	public void setType1(String type1) {
		this.type1 = type1;
	}
	public String getType2() {
		return type2;
	}
	public void setType2(String type2) {
		this.type2 = type2;
	}
	public Integer getCaseLevel() {
		return caseLevel;
	}
	public void setCaseLevel(Integer caseLevel) {
		this.caseLevel = caseLevel;
	}
	public String getGps() {
		return gps;
	}
	public void setGps(String gps) {
		this.gps = gps;
	}
	public String getJjcode() {
		return jjcode;
	}
	public void setJjcode(String jjcode) {
		this.jjcode = jjcode;
	}
	@Override
	public int compareTo(CaseGps o) {
		if(o != null && o.getType2() !=null){
			return this.getType2().compareTo(o.getType2());
		}else{
			return 0;
		}
		
	} 
	
}
