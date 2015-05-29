package com.tianyi.bph.domain.report;

import java.util.List;

public class CaseReportResult<T> {
	private Integer beginYmd;
	private Integer endYmd;
	
	List<T> data;

	public Integer getBeginYmd() {
		return beginYmd;
	}

	public void setBeginYmd(Integer beginYmd) {
		this.beginYmd = beginYmd;
	}

	public Integer getEndYmd() {
		return endYmd;
	}

	public void setEndYmd(Integer endYmd) {
		this.endYmd = endYmd;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}
}
