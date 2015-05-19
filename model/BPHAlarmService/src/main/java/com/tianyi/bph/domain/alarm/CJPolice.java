package com.tianyi.bph.domain.alarm;

import java.util.Date;

public class CJPolice{
	private String jjdbh;

    private Integer cjPoliceId;

    private String cjdbh;

    private String cjPoliceName;

    private String organCode;

    private String organName;

    private Date theTime;

    private Date arriveTime;

    private Date leaveTime;

    public String getCjdbh() {
        return cjdbh;
    }

    public void setCjdbh(String cjdbh) {
        this.cjdbh = cjdbh;
    }

    public String getCjPoliceName() {
        return cjPoliceName;
    }

    public void setCjPoliceName(String cjPoliceName) {
        this.cjPoliceName = cjPoliceName;
    }

    public String getOrganCode() {
        return organCode;
    }

    public void setOrganCode(String organCode) {
        this.organCode = organCode;
    }

    public String getOrganName() {
        return organName;
    }

    public void setOrganName(String organName) {
        this.organName = organName;
    }

    public Date getTheTime() {
        return theTime;
    }

    public void setTheTime(Date theTime) {
        this.theTime = theTime;
    }

    public Date getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(Date arriveTime) {
        this.arriveTime = arriveTime;
    }

    public Date getLeaveTime() {
        return leaveTime;
    }

    public void setLeaveTime(Date leaveTime) {
        this.leaveTime = leaveTime;
    }

    public String getJjdbh() {
        return jjdbh;
    }

    public void setJjdbh(String jjdbh) {
        this.jjdbh = jjdbh;
    }

    public Integer getCjPoliceId() {
        return cjPoliceId;
    }

    public void setCjPoliceId(Integer cjPoliceId) {
        this.cjPoliceId = cjPoliceId;
    }
}