package com.tianyi.bph.query.system;

import com.tianyi.bph.query.BaseQuery;

public class OrganQuery extends BaseQuery{

	private Long userId;
	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	private Integer parentId;
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	private String source;//1代表树最先请求.2代表后请求
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	private Integer currentOrganId;
	public Integer getCurrentOrganId() {
		return currentOrganId;
	}

	public void setCurrentOrganId(Integer currentOrganId) {
		this.currentOrganId = currentOrganId;
	}
	private String name;
	private String code;
	private Integer id;
	private String params;
	private String organLevel;
	private String path;
	private String expandeds;

	public String getExpandeds() {
		return expandeds;
	}

	public void setExpandeds(String expandeds) {
		this.expandeds = expandeds;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getOrganLevel() {
		return organLevel;
	}

	public void setOrganLevel(String organLevel) {
		this.organLevel = organLevel;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
