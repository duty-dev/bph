package com.tianyi.bph.domain.system;

import java.util.Date;

/**
 * 卡点 bean
 * 
 * @author Administrator
 * 
 */
public class CardPointBean {

	private int cardPointId; // 卡点id
	private int circleLayerId; // 所属圈层 +
	private String name; // 名称
	private int organId; // 责任机构
	private int type; // 类型
	private String angle; // 角度
	private String telephone; // 电话 +
	private int intercomGroupId; // 对讲机组号
	private String latitude; // 纬度
	private String longitude; // 经度
	private String altitude; // 海拔高度+
	private String assignment; // 职责
	private String policeNote; // 警力描述+
	private String equipment; // 携行装备+
	private int iconId; // 图标编号+
	private Date createTime; // 创建时间
	private Date updateTime; // 更新时间

	private String camera; // 关联天网
	private String cameraName;
	private String circleLayerName; // 圈层名称
	private String manager; // 卡点负责人
	private String managerName;

	private int allPoliceCount;// 警力总数

	private String mark;

	public String getCameraName() {
		return cameraName;
	}

	public void setCameraName(String cameraName) {
		this.cameraName = cameraName;
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

	public int getCardPointId() {
		return cardPointId;
	}

	public void setCardPointId(int cardPointId) {
		this.cardPointId = cardPointId;
	}

	public int getCircleLayerId() {
		return circleLayerId;
	}

	public void setCircleLayerId(int circleLayerId) {
		this.circleLayerId = circleLayerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getOrganId() {
		return organId;
	}

	public void setOrganId(int organId) {
		this.organId = organId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getAngle() {
		return angle;
	}

	public void setAngle(String angle) {
		this.angle = angle;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public int getIntercomGroupId() {
		return intercomGroupId;
	}

	public void setIntercomGroupId(int intercomGroupId) {
		this.intercomGroupId = intercomGroupId;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getAltitude() {
		return altitude;
	}

	public void setAltitude(String altitude) {
		this.altitude = altitude;
	}

	public String getAssignment() {
		return assignment;
	}

	public void setAssignment(String assignment) {
		this.assignment = assignment;
	}

	public String getPoliceNote() {
		return policeNote;
	}

	public void setPoliceNote(String policeNote) {
		this.policeNote = policeNote;
	}

	public String getEquipment() {
		return equipment;
	}

	public void setEquipment(String equipment) {
		this.equipment = equipment;
	}

	public int getIconId() {
		return iconId;
	}

	public void setIconId(int iconId) {
		this.iconId = iconId;
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

	public String getCamera() {
		return camera;
	}

	public void setCamera(String camera) {
		this.camera = camera;
	}

	public String getCircleLayerName() {
		return circleLayerName;
	}

	public void setCircleLayerName(String circleLayerName) {
		this.circleLayerName = circleLayerName;
	}

	public int getAllPoliceCount() {
		return allPoliceCount;
	}

	public void setAllPoliceCount(int allPoliceCount) {
		this.allPoliceCount = allPoliceCount;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

}
