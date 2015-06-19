package com.tianyi.bph.query.system;

import java.util.List;

import com.tianyi.bph.domain.system.Organ;
import com.tianyi.bph.domain.system.Role;
import com.tianyi.bph.query.BaseQuery;

public class UserQuery extends BaseQuery{

	private Long userId;
	private String userName;
	private String loginName;
	private String organPath;
	private Integer roleId;
	private Integer organId;
	private String organName;
	private List<Role> roleList;
	private Organ organ;
	private Integer policeUserId;
	private String phone;
	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getPoliceUserId() {
		return policeUserId;
	}

	public void setPoliceUserId(Integer policeUserId) {
		this.policeUserId = policeUserId;
	}

	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

	public Organ getOrgan() {
		return organ;
	}

	public void setOrgan(Organ organ) {
		this.organ = organ;
	}

	public String getOrganName() {
		return organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

	public Integer getOrganId() {
		return organId;
	}

	public void setOrganId(Integer organId) {
		this.organId = organId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getOrganPath() {
		return organPath;
	}

	public void setOrganPath(String organPath) {
		this.organPath = organPath;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	
	
}
