package com.tianyi.bph.domain.report;

import java.math.BigDecimal;

public class WarningColor {
    private Integer id;

    private Integer warningId;

    private Integer level;

    private double ge;

    private double lt;

    private String defaultColor;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getWarningId() {
        return warningId;
    }

    public void setWarningId(Integer warningId) {
        this.warningId = warningId;
    }



    public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public double getGe() {
        return ge;
    }

    public void setGe(double ge) {
        this.ge = ge;
    }

    public double getLt() {
        return lt;
    }

    public void setLt(double lt) {
        this.lt = lt;
    }

    public String getDefaultColor() {
        return defaultColor;
    }

    public void setDefaultColor(String defaultColor) {
        this.defaultColor = defaultColor == null ? null : defaultColor.trim();
    }
}