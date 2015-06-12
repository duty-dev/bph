package com.tianyi.bph.query.report;

import java.util.List;

public class ColorWarningList {
	private Integer id;
	private String Name;
	private List<String> colors;
	private List<String> levelList;
	private List<String> typeCodes;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public List<String> getColors() {
		return colors;
	}
	public void setColors(List<String> colors) {
		this.colors = colors;
	}
	public List<String> getLevelList() {
		return levelList;
	}
	public void setLevelList(List<String> levelList) {
		this.levelList = levelList;
	}
	public List<String> getTypeCodes() {
		return typeCodes;
	}
	public void setTypeCodes(List<String> typeCodes) {
		this.typeCodes = typeCodes;
	}
}
