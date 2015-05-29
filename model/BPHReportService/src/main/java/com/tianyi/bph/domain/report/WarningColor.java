package com.tianyi.bph.domain.report;

import java.math.BigDecimal;

public class WarningColor {
    private Integer id;

    private Integer warningId;

    private Integer index;

    private BigDecimal ge;

    private BigDecimal lt;

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

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public BigDecimal getGe() {
        return ge;
    }

    public void setGe(BigDecimal ge) {
        this.ge = ge;
    }

    public BigDecimal getLt() {
        return lt;
    }

    public void setLt(BigDecimal lt) {
        this.lt = lt;
    }

    public String getDefaultColor() {
        return defaultColor;
    }

    public void setDefaultColor(String defaultColor) {
        this.defaultColor = defaultColor == null ? null : defaultColor.trim();
    }
}