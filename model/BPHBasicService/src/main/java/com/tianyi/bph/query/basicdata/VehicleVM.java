package com.tianyi.bph.query.basicdata;

import com.tianyi.bph.domain.basicdata.Vehicle;
 
/**
 * 车辆虚拟类，继承车辆实体类，用于前台显示
 * @author lq
 *
 */
public class VehicleVM extends Vehicle {
	/**
	 * 车辆类型名称
	 */
	private String typeName;
	/**
	 * 组织机构名称
	 */
	private String orgName;
	/**
	 * 组织机构编码
	 */
	private String orgCode;
	/**
	 * 组织机构路径
	 */
	private String orgPath;
	/**
	 * gps编码
	 */
	private String gpsNumber;
	/**
	 * GPS名称
	 */
	private String gpsName;
	/**
	 * groupNumber
	 */
	private String intercomgroupNumber;
	/**
	 * 关联gps图标路径
	 */
	private String iconUrl;
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
	
	public String getGpsNumber() {
		return gpsNumber;
	}

	public void setGpsNumber(String gpsNumber) {
		this.gpsNumber = gpsNumber;
	}

	public String getGpsName() {
		return gpsName;
	}

	public void setGpsName(String gpsName) {
		this.gpsName = gpsName;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public String getIntercomgroupNumber() {
		return intercomgroupNumber;
	}

	public void setIntercomgroupNumber(String intercomgroupNumber) {
		this.intercomgroupNumber = intercomgroupNumber;
	}

	

}
