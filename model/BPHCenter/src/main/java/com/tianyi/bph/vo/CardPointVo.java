package com.tianyi.bph.vo;

import com.tianyi.bph.domain.system.TCardPoint;

public class CardPointVo {
	private int id;			
	private String name;	
	private String text;	
	private String note;	
	
	private Integer parentId;
	private String type;
	private boolean isDraw = false; // 是否已经绘制
	private boolean hasChild;
	private boolean selected;
		
	private TCardPoint cardPoint;

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

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public boolean isHasChild() {
		return hasChild;
	}

	public void setHasChild(boolean hasChild) {
		this.hasChild = hasChild;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public TCardPoint getCardPoint() {
		return cardPoint;
	}

	public void setCardPoint(TCardPoint cardPoint) {
		this.cardPoint = cardPoint;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public boolean getIsDraw() {
		return isDraw;
	}
	public void setIsDraw(boolean isDraw) {
		this.isDraw = isDraw;
	}
	
}
