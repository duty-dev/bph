package com.tianyi.bph.query.system;

import com.tianyi.bph.query.BaseQuery;

public class RoleQuery extends BaseQuery{
	
    private String name;
    
    private String note;
	
	private Integer nameSort;//名称排序
	
	private Integer timeSort;//时间排序
	
	private Long userId;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Integer getNameSort() {
		return nameSort;
	}

	public void setNameSort(Integer nameSort) {
		this.nameSort = nameSort;
	}

	public Integer getTimeSort() {
		return timeSort;
	}

	public void setTimeSort(Integer timeSort) {
		this.timeSort = timeSort;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
