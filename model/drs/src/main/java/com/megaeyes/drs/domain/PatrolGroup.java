package com.megaeyes.drs.domain;

import java.io.Serializable;
import java.util.Date;

public class PatrolGroup extends BaseField implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4319577514487426288L;

	private int id;
	private String name;
	private String code;
	private String note;
	private Organ organ;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	// @ManyToMany(cascade = { CascadeType.ALL }, targetEntity =
	// PoliceUser.class , fetch = FetchType.EAGER)
	// @JoinTable(name = "R_PATROLGROUP_POLICEUSER", joinColumns = {
	// @JoinColumn(name = "PATROL_GROUP_ID") }, inverseJoinColumns = {
	// @JoinColumn(name = "POLICE_USER_ID") })
	// public Set<PoliceUser> getPoliceUsers() {
	// return policeUsers;
	// }
	// public void setPoliceUsers(Set<PoliceUser> policeUsers) {
	// this.policeUsers = policeUsers;
	// }

	public Organ getOrgan() {
		return organ;
	}

	public void setOrgan(Organ organ) {
		this.organ = organ;
	}

}
