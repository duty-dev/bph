package com.megaeyes.drs.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Organ extends BaseField implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9091158086127929890L;

	private int id;
	private String name;
	private String code;
	private String shortName;
	private Organ parent;
	private String path;
	private String note;
	private Integer typeCode;

	// 2013-11-9
	private Integer level;

	private String cmsLoginName;
	private String cmsPassword;
	private boolean bindFlag;
	private Date lastBindTime;
	private Integer bindUserId;

	private String coordinates;

	public Organ() {
		super();
	}

	public Organ(int id) {
		super();
		this.id = id;
	}

	public Organ(Organ organ) {
		this.id = organ.getId();
		this.name = organ.getName();
		this.code = organ.getCode();
		this.shortName = organ.getShortName();
		this.typeCode = organ.getTypeCode();
		this.level = organ.getLevel();
	}

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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public Organ getParent() {
		return parent;
	}

	public void setParent(Organ parent) {
		this.parent = parent;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(Integer typeCode) {
		this.typeCode = typeCode;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getCmsLoginName() {
		return cmsLoginName;
	}

	public void setCmsLoginName(String cmsLoginName) {
		this.cmsLoginName = cmsLoginName;
	}

	public String getCmsPassword() {
		return cmsPassword;
	}

	public void setCmsPassword(String cmsPassword) {
		this.cmsPassword = cmsPassword;
	}

	public boolean isBindFlag() {
		return bindFlag;
	}

	public void setBindFlag(boolean bindFlag) {
		this.bindFlag = bindFlag;
	}

	public Date getLastBindTime() {
		return lastBindTime;
	}

	public void setLastBindTime(Date lastBindTime) {
		this.lastBindTime = lastBindTime;
	}

	public Integer getBindUserId() {
		return bindUserId;
	}

	public void setBindUserId(Integer bindUserId) {
		this.bindUserId = bindUserId;
	}

	public String getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(String coordinates) {
		this.coordinates = coordinates;
	}

}
