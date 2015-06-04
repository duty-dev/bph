package com.tianyi.bph.query.report;

import java.util.List;

import com.tianyi.bph.domain.alarm.AlarmType;

public class AlarmTypeTreeVM extends AlarmType { 
	private List<AlarmTypeTreeVM> items;
	public List<AlarmTypeTreeVM> getItems() {
		return items;
	}
	public void setItems(List<AlarmTypeTreeVM> items) {
		this.items = items;
	}
	private boolean checked;
	private boolean expanded;
	private boolean selected;
	private String text;
	private int parentId;
	private int id; 
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public boolean isExpanded() {
		return expanded;
	}
	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	} 
}
