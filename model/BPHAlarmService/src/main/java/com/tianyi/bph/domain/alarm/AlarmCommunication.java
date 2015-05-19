package com.tianyi.bph.domain.alarm;

import java.util.Date;

public class AlarmCommunication{
	private String jjdbh; //接警单编号

    private Integer policeId; //警员编号

    private Date thetime; //时间

    private Integer source; //来源

    private Integer infoType; //信息类型

    private String textInfo; //文本内容

	private String fileCode; //文件编号
    
    private Integer isByMyself; //1：代表自已  0：代表别人
    
    private Integer userId; //发送人ID
    
    private String userLoginName; //发送人帐号
    
    private String organName; //机构名称
    
    public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserLoginName() {
		return userLoginName;
	}

	public void setUserLoginName(String userLoginName) {
		this.userLoginName = userLoginName;
	}

	public String getOrganName() {
		return organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

	public Integer getIsByMyself() {
		return isByMyself;
	}

	public void setIsByMyself(Integer isByMyself) {
		this.isByMyself = isByMyself;
	}

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public Integer getInfoType() {
        return infoType;
    }

    public void setInfoType(Integer infoType) {
        this.infoType = infoType;
    }

    public String getTextInfo() {
        return textInfo;
    }

    public void setTextInfo(String textInfo) {
        this.textInfo = textInfo;
    }

    public String getFileCode() {
        return fileCode;
    }

    public void setFileCode(String fileCode) {
        this.fileCode = fileCode;
    }

    public String getJjdbh() {
        return jjdbh;
    }

    public void setJjdbh(String jjdbh) {
        this.jjdbh = jjdbh;
    }

    public Integer getPoliceId() {
    	if(policeId==null){
    		return 0;
    	}else{
    		return policeId;
    	}
    }

    public void setPoliceId(Integer policeId) {
        this.policeId = policeId;
    }

    public Date getThetime() {
        return thetime;
    }

    public void setThetime(Date thetime) {
        this.thetime = thetime;
    }
}