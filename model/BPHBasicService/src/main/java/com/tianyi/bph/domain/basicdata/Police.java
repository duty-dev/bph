package com.tianyi.bph.domain.basicdata;
/*
 * 警员 实体类
 */
public class Police {
	/*
	 * 主键值id
	 */
    private Integer id;
    /*
	 * 警员类型id
	 */
    private Integer typeId;
    /*
	 * 警员姓名
	 */
    private String name;
    /*
	 * 所属组织机构id
	 */
    private Integer orgId;
    /*
	 * 身份证号码
	 */
    private String idcardno;
    /*
	 * 警号
	 */
    private String number;
    /*
	 * 职位
	 */
    private Integer titleId;
	/*
	 * 手机号码
	 */
    private String mobile;
    /*
	 * 公安短号
	 */
    private String mobileShort;
    /*
	 * 对讲机组呼号
	 */
    private String intercomGroup;
    /*
	 * 对讲机个呼号
	 */
    private String intercomPerson;
    /*
	 * 对应gpsId
	 */
    private Integer gpsId;
    /*
	 * 对应GPS名称
	 */
    private String gpsName;
    /*
	 * 平台标识
	 */
    private Boolean syncState;
    /*
	 * 平台id
	 */
    private Integer platformId;
    /*
	 * 是否启用
	 */
    private Boolean isused;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public String getIdcardno() {
        return idcardno;
    }

    public void setIdcardno(String idcardno) {
        this.idcardno = idcardno;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }


    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobileShort() {
        return mobileShort;
    }

    public void setMobileShort(String mobileShort) {
        this.mobileShort = mobileShort;
    }

    public String getIntercomGroup() {
        return intercomGroup;
    }

    public void setIntercomGroup(String intercomGroup) {
        this.intercomGroup = intercomGroup;
    }

    public String getIntercomPerson() {
        return intercomPerson;
    }

    public void setIntercomPerson(String intercomPerson) {
        this.intercomPerson = intercomPerson;
    }

    public Integer getGpsId() {
        return gpsId;
    }

    public void setGpsId(Integer gpsId) {
        this.gpsId = gpsId;
    }

    public String getGpsName() {
        return gpsName;
    }

    public void setGpsName(String gpsName) {
        this.gpsName = gpsName;
    }

    public Boolean getSyncState() {
        return syncState;
    }

    public void setSyncState(Boolean syncState) {
        this.syncState = syncState;
    }

    public Integer getPlatformId() {
        return platformId;
    }

    public void setPlatformId(Integer platformId) {
        this.platformId = platformId;
    }

    public Boolean getIsused() {
        return isused;
    }

    public void setIsused(Boolean isused) {
        this.isused = isused;
    }
    public Integer getTitleId() {
		return titleId;
	}

	public void setTitleId(Integer titleId) {
		this.titleId = titleId;
	}
}