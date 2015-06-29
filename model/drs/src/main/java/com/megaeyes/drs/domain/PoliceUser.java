package com.megaeyes.drs.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 警员
 * 
 * @author gm
 * @time 2013-7-22 上午09:31:13
 */
public class PoliceUser extends BaseField implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 858145682293526402L;

	private int id;
	private Integer policeType; // 人员类别 0:民警 1：辅警 2：职工
	private String name; // 警员名称
	private String identityCard; // 身份证
	private String position;
	private String phone; // 手机号码
	private String shortCode; // 公安短号
	private IntercomGroupCode intercomGroupCode;
	private String intercomId; // 对讲机个呼号
	private String policeId; // 警员编号
	private Integer dutyType; // 0:巡逻 1:社区 2:刑侦 3:单位领导 4:其他 5:接处警 6:值班警力 7:卡点报备
								// 8:指挥室
	private boolean sex; // 性别
	private String email;
	private boolean dealFlag; // 是否参加接处警
	private Organ organ; // 所属机构
	private String gpsDeviceId;
	private String note;

	private int localRPR; // 是否本地户口 0:否 1：是
	private String ethnic; // 民族
	private String street; // 街路巷
	private String address; // 门牌楼号
	private String markContent; // 标注修改内容 格式：0000000(姓名、性别、名族、街路巷、门牌号、警号、手机号码),
								// 已修改属性用1代替

	private boolean busy;
	private boolean dutyStatus; // 值班状态 true 值班

	private boolean callName; // 是否点名

	private Weapon weapon; // 临时装备对象
	private boolean party; // 是否是党委成员,默认false
	private int markStatus; // 标记状态 0:在岗 1:离岗

	// 2014-05-05
	private int trafficType; // 交通工具类型 0:未知 1：汽车 2：摩托车 3：自行车 4：步巡
	private int trafficId; // 交通工具id (目前只有vehicle_id)

	private int iconType; // 图标类型

	// 2014-09-29 by cd
	private boolean centerDuty;

	private Date createTime;

	public PoliceUser() {
		super();
	}

	public PoliceUser(PoliceUser user) {
		super();
		this.id = user.getId();
		this.policeType = user.getPoliceType();
		this.name = user.getName();
		this.identityCard = user.getIdentityCard();
		this.position = user.getPosition();
		this.phone = user.getPhone();
		this.shortCode = user.getShortCode();
		this.intercomGroupCode = user.getIntercomGroupCode();
		this.intercomId = user.getIntercomId();
		this.policeId = user.getPoliceId();
		this.dutyType = user.getDutyType();
		this.sex = user.isSex();
		this.email = user.getEmail();
		this.dealFlag = user.isDealFlag();
		this.gpsDeviceId = user.getGpsDeviceId();
		this.note = user.getNote();
		this.localRPR = user.getLocalRPR();
		this.ethnic = user.getEthnic();
		this.street = user.getStreet();
		this.address = user.getAddress();
		this.markContent = user.getMarkContent();
		this.busy = user.isBusy();
		this.dutyStatus = user.isDutyStatus();
		this.callName = user.isCallName();
		this.trafficType = user.getTrafficType();
		this.trafficId = user.getTrafficId();
		this.iconType = user.getIconType();
		// 2014-09-29 by cd
		this.centerDuty = user.isCenterDuty();
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getPoliceType() {
		return policeType;
	}

	public void setPoliceType(Integer policeType) {
		this.policeType = policeType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdentityCard() {
		return identityCard;
	}

	public void setIdentityCard(String identityCard) {
		this.identityCard = identityCard;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getShortCode() {
		return shortCode;
	}

	public void setShortCode(String shortCode) {
		this.shortCode = shortCode;
	}

	public IntercomGroupCode getIntercomGroupCode() {
		return intercomGroupCode;
	}

	public void setIntercomGroupCode(IntercomGroupCode intercomGroupCode) {
		this.intercomGroupCode = intercomGroupCode;
	}

	public String getIntercomId() {
		return intercomId;
	}

	public void setIntercomId(String intercomId) {
		this.intercomId = intercomId;
	}

	public String getPoliceId() {
		return policeId;
	}

	public void setPoliceId(String policeId) {
		this.policeId = policeId;
	}

	public Integer getDutyType() {
		return dutyType;
	}

	public void setDutyType(Integer dutyType) {
		this.dutyType = dutyType;
	}

	public boolean isSex() {
		return sex;
	}

	public void setSex(boolean sex) {
		this.sex = sex;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isDealFlag() {
		return dealFlag;
	}

	public void setDealFlag(boolean dealFlag) {
		this.dealFlag = dealFlag;
	}

	public Organ getOrgan() {
		return organ;
	}

	public void setOrgan(Organ organ) {
		this.organ = organ;
	}

	public String getGpsDeviceId() {
		return gpsDeviceId;
	}

	public void setGpsDeviceId(String gpsDeviceId) {
		this.gpsDeviceId = gpsDeviceId;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public int getLocalRPR() {
		return localRPR;
	}

	public void setLocalRPR(int localRPR) {
		this.localRPR = localRPR;
	}

	public String getEthnic() {
		return ethnic;
	}

	public void setEthnic(String ethnic) {
		this.ethnic = ethnic;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMarkContent() {
		return markContent;
	}

	public void setMarkContent(String markContent) {
		this.markContent = markContent;
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

	public boolean isParty() {
		return party;
	}

	public void setParty(boolean party) {
		this.party = party;
	}

	public int getMarkStatus() {
		return markStatus;
	}

	public void setMarkStatus(int markStatus) {
		this.markStatus = markStatus;
	}

	public boolean isBusy() {
		return busy;
	}

	public void setBusy(boolean busy) {
		this.busy = busy;
	}

	public boolean isCallName() {
		return callName;
	}

	public void setCallName(boolean callName) {
		this.callName = callName;
	}

	public Weapon getWeapon() {
		return weapon;
	}

	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}

	public boolean isDutyStatus() {
		return dutyStatus;
	}

	public void setDutyStatus(boolean dutyStatus) {
		this.dutyStatus = dutyStatus;
	}

	public int getTrafficType() {
		return trafficType;
	}

	public void setTrafficType(int trafficType) {
		this.trafficType = trafficType;
	}

	public int getTrafficId() {
		return trafficId;
	}

	public void setTrafficId(int trafficId) {
		this.trafficId = trafficId;
	}

	public int getIconType() {
		return iconType;
	}

	public void setIconType(int iconType) {
		this.iconType = iconType;
	}

	public boolean isCenterDuty() {
		return centerDuty;
	}

	public void setCenterDuty(boolean centerDuty) {
		this.centerDuty = centerDuty;
	}

}
