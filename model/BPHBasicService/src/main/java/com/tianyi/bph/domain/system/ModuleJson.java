package com.tianyi.bph.domain.system;

import java.util.List;

public class ModuleJson {
	private String name;
	private String title;
	private String moduleType;
	private String moduleOpType;
	private String url;
	private String assemblyName;
	private String typeName;
	private List<ModuleJson> children;
	private Integer parentId;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getModuleType() {
		return moduleType;
	}
	public void setModuleType(String moduleType) {
		this.moduleType = moduleType;
	}
	public String getModuleOpType() {
		return moduleOpType;
	}
	public void setModuleOpType(String moduleOpType) {
		this.moduleOpType = moduleOpType;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getAssemblyName() {
		return assemblyName;
	}
	public void setAssemblyName(String assemblyName) {
		this.assemblyName = assemblyName;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public List<ModuleJson> getChildren() {
		return children;
	}
	public void setChildren(List<ModuleJson> children) {
		this.children = children;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
}
