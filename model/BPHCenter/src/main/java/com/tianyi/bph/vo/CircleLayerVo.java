package com.tianyi.bph.vo;

import java.util.List;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.tianyi.bph.common.JsonUtils;
import com.tianyi.bph.domain.system.CircleLayer;

public class CircleLayerVo {

	private int id;			//圈层id
	private String name;	//圈层名称
	private String text;	//圈层描述
	private String note;	//描述

	private DisplayProperty displayProperty;
	private List<MapProperty> mapProperty;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
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
	public DisplayProperty getDisplayProperty() {
		return displayProperty;
	}
	public void setDisplayProperty(DisplayProperty displayProperty) {
		this.displayProperty = displayProperty;
	}
	public List<MapProperty> getMapProperty() {
		return mapProperty;
	}
	public void setMapProperty(List<MapProperty> mapProperty) {
		this.mapProperty = mapProperty;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public CircleLayer ctrate() {
		CircleLayer circleLayer = new CircleLayer();
		circleLayer.setId(this.id);
		circleLayer.setName(this.name);
		circleLayer.setNote(this.note);
		if (this.displayProperty != null) {
			circleLayer.setDisplayProperty(JsonUtils.toJson(this.displayProperty));
		} else {
			circleLayer.setDisplayProperty(null);
		}
		if (this.mapProperty != null) {
			circleLayer.setMapProperty(JsonUtils.toJson(this.mapProperty));
		} else {
			circleLayer.setDisplayProperty(null);
		}
		return circleLayer;
	}
	public CircleLayerVo( ) {}
	
	
	public CircleLayerVo(CircleLayer circleLayer) {
		if (circleLayer != null) {
			setId(circleLayer.getId());
			setName(circleLayer.getName());
			setNote(circleLayer.getNote());
			setText(circleLayer.getName());
			if (StringUtils.hasLength(circleLayer.getDisplayProperty())) {
				setDisplayProperty(JsonUtils.toObj(circleLayer.getDisplayProperty(),
						DisplayProperty.class));
			} else {
				setDisplayProperty(null);
			}
			if (StringUtils.hasLength(circleLayer.getMapProperty())) {
				List<MapProperty> relist = JsonUtils.toObj(
						circleLayer.getMapProperty(),
						new TypeReference<List<MapProperty>>() {
						});
				setMapProperty(relist);
			} else {
				setMapProperty(null);
			}
		}
	}
	
}
