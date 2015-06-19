package com.tianyi.bph.domain.system;

import java.util.Date;
import java.util.List;

public class Area {
	private Integer id;

	private String areaName;

	private Integer areaType;// 区域类型 1巡区， 2 社区，3 辖区

	private Integer organId;

	private Boolean changeRange;

	private Date createTime;

	private Integer flag;// 1正常 2 弃用

	private Integer createUserId;

	private String tel;

	private Integer nnt;

	private String organName;
	private String organPath;

	private String displayProperty;

	private String mapProperty;

	private List<Integer> relationUserKeys;// 关联警员id
	
    private String manager; // 卡点负责人
	private String managerName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public Integer getAreaType() {
		return areaType;
	}

	public void setAreaType(Integer areaType) {
		this.areaType = areaType;
	}

	public Integer getOrganId() {
		return organId;
	}

	public void setOrganId(Integer organId) {
		this.organId = organId;
	}

	public Boolean getChangeRange() {
		return changeRange;
	}

	public void setChangeRange(Boolean changeRange) {
		this.changeRange = changeRange;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public Integer getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public Integer getNnt() {
		return nnt;
	}

	public void setNnt(Integer nnt) {
		this.nnt = nnt;
	}

	public String getOrganName() {
		return organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

	public String getOrganPath() {
		return organPath;
	}

	public void setOrganPath(String organPath) {
		this.organPath = organPath;
	}

	public String getDisplayProperty() {
		return displayProperty;
	}

	public void setDisplayProperty(String displayProperty) {
		this.displayProperty = displayProperty;
	}

	public String getMapProperty() {
		return mapProperty;
	}

	public void setMapProperty(String mapProperty) {
		this.mapProperty = mapProperty;
	}

	public List<Integer> getRelationUserKeys() {
		return relationUserKeys;
	}

	public void setRelationUserKeys(List<Integer> relationUserKeys) {
		this.relationUserKeys = relationUserKeys;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

}