package com.tianyi.bph.domain.alarm;

import java.util.Date;

public class PJPolice {
	private String jjdbh;

    private Integer pjPoliceId;

	private Integer reportType;

    private Integer pjVehicleId;

    private Date startTime;

    private Date endTime;

    private Date arriveTime;

    private Date leaveTime;

    private Integer pjUserId;

    private Date pjTime;
    
    public String getJjdbh() {
		return jjdbh;
	}

	public void setJjdbh(String jjdbh) {
		this.jjdbh = jjdbh;
	}

	public Integer getPjPoliceId() {
		return pjPoliceId;
	}

	public void setPjPoliceId(Integer pjPoliceId) {
		this.pjPoliceId = pjPoliceId;
	}

    public Integer getReportType() {
        return reportType;
    }

    public void setReportType(Integer reportType) {
        this.reportType = reportType;
    }

    public Integer getPjVehicleId() {
        return pjVehicleId;
    }

    public void setPjVehicleId(Integer pjVehicleId) {
        this.pjVehicleId = pjVehicleId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
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

    public Integer getPjUserId() {
		return pjUserId;
	}

	public void setPjUserId(Integer pjUserId) {
		this.pjUserId = pjUserId;
	}

	public Date getPjTime() {
        return pjTime;
    }

    public void setPjTime(Date pjTime) {
        this.pjTime = pjTime;
    }
}