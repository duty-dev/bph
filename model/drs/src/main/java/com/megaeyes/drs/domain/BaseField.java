package com.megaeyes.drs.domain;

import java.io.Serializable;
import java.util.Date;

public abstract class BaseField implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7472704867900726700L;

	protected Date createTime; // ����ʱ��
	protected Date updateTime; // �޸�ʱ��

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

}
