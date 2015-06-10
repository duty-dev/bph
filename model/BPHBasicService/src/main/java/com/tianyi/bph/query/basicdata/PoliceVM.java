package com.tianyi.bph.query.basicdata;

import com.tianyi.bph.domain.basicdata.Police;
 
/**
 * 警员虚拟类，继承警员实体类，用于前台显示
 * @author lq
 *
 */
public class PoliceVM extends Police {
	
	/**
	 * 组呼号对应编号
	 */
	private String intercomGroupNumber;
	/**
	 * 警员类型名称
	 */
    private String typeName;
    /**
     * 组织机构名称
     */
    private String orgName;
    /**
     * 组织机构代码
     */
	private String orgCode;
	/**
	 * 组织机构路径
	 */
	private String orgPath;
	/**
	 * 页面显示名称
	 */
	private String displayName;
	/**
	 * 警员关联图标路径
	 */
	private String iconUrl;
	
	public String getIntercomGroupNumber() {
		return intercomGroupNumber;
	}

	public void setIntercomGroupNumber(String intercomGroupNumber) {
		this.intercomGroupNumber = intercomGroupNumber;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgPath() {
		return orgPath;
	}

	public void setOrgPath(String orgPath) {
		this.orgPath = orgPath;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	} 
}
