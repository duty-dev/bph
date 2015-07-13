package com.tianyi.bph.query.basicdata;

import java.util.Date;

/**
 * 数据库查询结果集
 * @author Owen
 *
 */
public class ExtDbResult {
	/**
	 * 备勤id
	 * 对应 t_duty 中的 id
	 */
	private Integer dutyId;
	/**
	 * itemid
	 */
	private Integer dutyItemId;
	/**
	 * 组织机构
	 */
	private Integer orgId;
	/**
	 * 年月日
	 */
	private Integer ymd;
	/**
	 * 备勤类型id
	 */
	private Integer dutyTypeId;
	/**
	 * 备勤类型名称
	 */
	private String dutyTypeName;
	/**
	 * 班次名称
	 */
	private String name;
	/**
	 * 开始时间
	 */
	private Date beginTime;
	/**
	 * 结束时间
	 */
	private Date endTime;
	/**
	 * 数据类型
	 * 100 		备勤类型
	 * 101  	班次
	 * 999		自定义
	 * 1		车辆
	 * 2		警员
	 * 3		武器
	 * 4		定位设备
	 */
	private Integer itemTypeId;
	/**
	 * 数据id 
	 */
	private Integer itemId;
	/**
	 * 层次
	 */
	private Integer level;
	/**
	 * 父节点id
	 */
	private Integer parentId;
	/**
	 * 全路径
	 */
	private String fullIdPath;
	/*****************************************************
	 * 车辆
	 */
	/**
	 * 车辆主键值id
	 */
    private int vehicleId;
    /**
	 * 车辆类型id
	 */
    private int vehicleVehicleTypeId;
    /**
	 * 车辆所属组织机构id
	 */
    private int vehicleOrgId;
    /**
	 * 车牌号码
	 */
    private String vehicleNumber;
    /*
	 * 对讲机组呼号
	 */
    private String vehicleIntercomGroup;
    /*
	 * 对讲机个呼号
	 */
    private String vehicleIntercomPerson;
    /*
	 * 对应gpsid
	 */
    private int vehicleGpsId;
    /*
	 * 对饮gps名称
	 */
    private String vehicleGpsName;
    /*
	 * 车辆用途
	 */
    private Integer purposeId;
    /*
	 * 车辆用途
	 */
    private String purposeName;
    /*
	 * 车辆品牌
	 */
    private String vehicleBrand;
    /*
	 * 座位数
	 */
    private String vehicleSiteQty;
    /**
     * 类型
     */
    private Integer vehicleTypeId;
	
    /***********************************************
     * 警员
     */
	/**
	 * 警员主键值id
	 */
    private Integer policeId;
    /**
	 * 警员类型id
	 */
    private Integer policeTypeId;
    /**
	 * 警员姓名
	 */
    private String policeName;
    /**
	 * 所属组织机构id
	 */
    private Integer policeOrgId;
    /**
	 * 身份证号码
	 */
    private String policeIdcardno;
    /**
	 * 警号
	 */
    private String policeNumber;
    /**
	 * 职位
	 */
    private Integer titleId;
    /**
	 * 职位
	 */
    private String titleName;
    /**
	 * 手机号码
	 */
    private String policeMobile;
    /**
	 * 公安短号
	 */
    private String policeMobileShort;
    /**
	 * 对讲机组呼号
	 */
    private String policeIntercomGroup;
    /**
	 * 对讲机个呼号
	 */
    private String policeIntercomPerson;
    /**
	 * 对应gpsId
	 */
    private Integer policeGpsId;
    /**
	 * 对应GPS名称
	 */
    private String policeGpsName;
    
    private Integer jjcount;
    
    /***************************************************************************************
     * 武器
     */
    
    /**
	 * 武器主键值id
	 */
    private Integer weaponId;
    /**
	 * 规格型号
	 */
    private String  weaponStandard;
    /**
	 * 武器编号
	 */
    private String  weaponNumber;
    /**
	 * 武器类型id
	 */
    private Integer  weaponTypeId;
    /**
	 * 武器所属组织机构id
	 */
    private Integer  weaponOrgId;
    /*****************************************************************
     * gps
     *
     */
    
    /**
	 * gps主键值id
	 */
    private Integer GpsId;
    /**
	 * 定位设备类型id
	 */
    private Integer GpsTypeId;
    /**
	 * 定位设备所属组织机构id
	 */
    private Integer GpsOrgId;
    /**
	 * 定位设备编号
	 */
    private String GpsNumber;
    /**
	 * 定位设备名称
	 */
    private String gpsName;
    
    private String gpsIconUrl;
    
    
	public Integer getDutyId() {
		return dutyId;
	}
	public void setDutyId(Integer dutyId) {
		this.dutyId = dutyId;
	}
	public Integer getDutyItemId() {
		return dutyItemId;
	}
	public void setDutyItemId(Integer dutyItemId) {
		this.dutyItemId = dutyItemId;
	}
	public Integer getOrgId() {
		return orgId;
	}
	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}
	public Integer getYmd() {
		return ymd;
	}
	public void setYmd(Integer ymd) {
		this.ymd = ymd;
	}
	public Integer getDutyTypeId() {
		return dutyTypeId;
	}
	public void setDutyTypeId(Integer dutyTypeId) {
		this.dutyTypeId = dutyTypeId;
	}
	public String getDutyTypeName() {
		return dutyTypeName;
	}
	public void setDutyTypeName(String dutyTypeName) {
		this.dutyTypeName = dutyTypeName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Integer getItemTypeId() {
		return itemTypeId;
	}
	public void setItemTypeId(Integer itemTypeId) {
		this.itemTypeId = itemTypeId;
	}
	public Integer getItemId() {
		return itemId;
	}
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public String getFullIdPath() {
		return fullIdPath;
	}
	public void setFullIdPath(String fullIdPath) {
		this.fullIdPath = fullIdPath;
	}
	
	public int getVehicleId() {
		return vehicleId;
	}
	public void setVehicleId(int vehicleId) {
		this.vehicleId = vehicleId;
	}
	public int getVehicleVehicleTypeId() {
		return vehicleVehicleTypeId;
	}
	public void setVehicleVehicleTypeId(int vehicleVehicleTypeId) {
		this.vehicleVehicleTypeId = vehicleVehicleTypeId;
	}
	public int getVehicleOrgId() {
		return vehicleOrgId;
	}
	public void setVehicleOrgId(int vehicleOrgId) {
		this.vehicleOrgId = vehicleOrgId;
	}
	public String getVehicleNumber() {
		return vehicleNumber;
	}
	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}
	public String getVehicleIntercomGroup() {
		return vehicleIntercomGroup;
	}
	public void setVehicleIntercomGroup(String vehicleIntercomGroup) {
		this.vehicleIntercomGroup = vehicleIntercomGroup;
	}
	public String getVehicleIntercomPerson() {
		return vehicleIntercomPerson;
	}
	public void setVehicleIntercomPerson(String vehicleIntercomPerson) {
		this.vehicleIntercomPerson = vehicleIntercomPerson;
	}
	public int getVehicleGpsId() {
		return vehicleGpsId;
	}
	public void setVehicleGpsId(int vehicleGpsId) {
		this.vehicleGpsId = vehicleGpsId;
	}
	public String getVehicleGpsName() {
		return vehicleGpsName;
	}
	public void setVehicleGpsName(String vehicleGpsName) {
		this.vehicleGpsName = vehicleGpsName;
	}
	public Integer getPurposeId() {
		return purposeId;
	}
	public void sePurposeId(Integer purposeId) {
		this.purposeId = purposeId;
	}
	public String getVehicleBrand() {
		return vehicleBrand;
	}
	public void setVehicleBrand(String vehicleBrand) {
		this.vehicleBrand = vehicleBrand;
	}
	public String getVehicleSiteQty() {
		return vehicleSiteQty;
	}
	public void setVehicleSiteQty(String vehicleSiteQty) {
		this.vehicleSiteQty = vehicleSiteQty;
	}
	public Integer getVehicleTypeId() {
		return vehicleTypeId;
	}
	public void setVehicleTypeId(Integer vehicleTypeId) {
		this.vehicleTypeId = vehicleTypeId;
	}
	public Integer getPoliceId() {
		return policeId;
	}
	public void setPoliceId(Integer policeId) {
		this.policeId = policeId;
	}
	public Integer getPoliceTypeId() {
		return policeTypeId;
	}
	public void setPoliceTypeId(Integer policeTypeId) {
		this.policeTypeId = policeTypeId;
	}
	public String getPoliceName() {
		return policeName;
	}
	public void setPoliceName(String policeName) {
		this.policeName = policeName;
	}
	public Integer getPoliceOrgId() {
		return policeOrgId;
	}
	public void setPoliceOrgId(Integer policeOrgId) {
		this.policeOrgId = policeOrgId;
	}
	public String getPoliceIdcardno() {
		return policeIdcardno;
	}
	public void setPoliceIdcardno(String policeIdcardno) {
		this.policeIdcardno = policeIdcardno;
	}
	public String getPoliceNumber() {
		return policeNumber;
	}
	public void setPoliceNumber(String policeNumber) {
		this.policeNumber = policeNumber;
	}
	public Integer getTitleId() {
		return titleId;
	}
	public void setTitleId(Integer titleId) {
		this.titleId = titleId;
	}
	public String getPoliceMobile() {
		return policeMobile;
	}
	public void setPoliceMobile(String policeMobile) {
		this.policeMobile = policeMobile;
	}
	public String getPoliceMobileShort() {
		return policeMobileShort;
	}
	public void setPoliceMobileShort(String policeMobileShort) {
		this.policeMobileShort = policeMobileShort;
	}
	public String getPoliceIntercomGroup() {
		return policeIntercomGroup;
	}
	public void setPoliceIntercomGroup(String policeIntercomGroup) {
		this.policeIntercomGroup = policeIntercomGroup;
	}
	public String getPoliceIntercomPerson() {
		return policeIntercomPerson;
	}
	public void setPoliceIntercomPerson(String policeIntercomPerson) {
		this.policeIntercomPerson = policeIntercomPerson;
	}
	public Integer getPoliceGpsId() {
		return policeGpsId;
	}
	public void setPoliceGpsId(Integer policeGpsId) {
		this.policeGpsId = policeGpsId;
	}
	public String getPoliceGpsName() {
		return policeGpsName;
	}
	public void setPoliceGpsName(String policeGpsName) {
		this.policeGpsName = policeGpsName;
	}
	public Integer getWeaponId() {
		return weaponId;
	}
	public void setWeaponId(Integer weaponId) {
		this.weaponId = weaponId;
	}
	public String getWeaponStandard() {
		return weaponStandard;
	}
	public void setWeaponStandard(String weaponStandard) {
		this.weaponStandard = weaponStandard;
	}
	public String getWeaponNumber() {
		return weaponNumber;
	}
	public void setWeaponNumber(String weaponNumber) {
		this.weaponNumber = weaponNumber;
	}
	public Integer getWeaponTypeId() {
		return weaponTypeId;
	}
	public void setWeaponTypeId(Integer weaponTypeId) {
		this.weaponTypeId = weaponTypeId;
	}
	public Integer getWeaponOrgId() {
		return weaponOrgId;
	}
	public void setWeaponOrgId(Integer weaponOrgId) {
		this.weaponOrgId = weaponOrgId;
	}
	public Integer getGpsId() {
		return GpsId;
	}
	public void setGpsId(Integer gpsId) {
		GpsId = gpsId;
	}
	public Integer getGpsTypeId() {
		return GpsTypeId;
	}
	public void setGpsTypeId(Integer gpsTypeId) {
		GpsTypeId = gpsTypeId;
	}
	public Integer getGpsOrgId() {
		return GpsOrgId;
	}
	public void setGpsOrgId(Integer gpsOrgId) {
		GpsOrgId = gpsOrgId;
	}
	public String getGpsNumber() {
		return GpsNumber;
	}
	public void setGpsNumber(String gpsNumber) {
		GpsNumber = gpsNumber;
	}
	public String getGpsName() {
		return gpsName;
	}
	public void setGpsName(String gpsName) {
		this.gpsName = gpsName;
	}
	public String getGpsIconUrl() {
		return gpsIconUrl;
	}
	public void setGpsIconUrl(String gpsIconUrl) {
		this.gpsIconUrl = gpsIconUrl;
	}
	public Integer getJjcount() {
		return jjcount;
	}
	public void setJjcount(Integer jjcount) {
		this.jjcount = jjcount;
	}
	public String getPurposeName() {
		return purposeName;
	}
	public void setPurposeName(String purposeName) {
		this.purposeName = purposeName;
	}
	public String getTitleName() {
		return titleName;
	}
	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}
	
	
	
}
