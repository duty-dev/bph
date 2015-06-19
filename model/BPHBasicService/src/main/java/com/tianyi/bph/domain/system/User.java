package com.tianyi.bph.domain.system;

import java.util.Date;
import java.util.List;

public class User {
    

	private Long userId;

    //登陆名
    private String loginName;

    private String userName;

    private String password;
    
    private String sessionId;//客户端调用。通过sessionId来验证。

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	private List<MqConfig> messeageServer;

	public List<MqConfig> getMesseageServer() {
		return messeageServer;
	}

	public void setMesseageServer(List<MqConfig> messeageServer) {
		this.messeageServer = messeageServer;
	}
	private FtpConfig ftpconf;
	

	public FtpConfig getFtpconf() {
		return ftpconf;
	}

	public void setFtpconf(FtpConfig ftpconf) {
		this.ftpconf = ftpconf;
	}

	public GpsConfig getGpsconf() {
		return gpsconf;
	}

	public void setGpsconf(GpsConfig gpsconf) {
		this.gpsconf = gpsconf;
	}

	private GpsConfig gpsconf;
	
	

	//所属机构
    private Integer orgId;
    
    private Organ organ;

    private Date createTime;

    private Date updateTime;
    
    private Integer policeUserId;
    
    private String phone;
    
    public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getPoliceUserId() {
		return policeUserId;
	}

	public void setPoliceUserId(Integer policeUserId) {
		this.policeUserId = policeUserId;
	}

	public long lastOptTime;
    
    private String userOtherOrgans;

    public String getUserOtherOrgans() {
		return userOtherOrgans;
	}

	public void setUserOtherOrgans(String userOtherOrgans) {
		this.userOtherOrgans = userOtherOrgans;
	}

	public long getLastOptTime() {
		return lastOptTime;
	}

	public void setLastOptTime(long lastOptTime) {
		this.lastOptTime = lastOptTime;
	}

	//预置 0非预置 1预置
    private Short preset = 1;

    //0正常 1禁用
    private Integer state = 0;;

    private Date loginTime;

    public String getOrganName() {
		return organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

	private String note;
    private String organPath;
    private String organName;

    public String getOrganPath() {
		return organPath;
	}

	public void setOrganPath(String organPath) {
		this.organPath = organPath;
	}

	//所拥有角色
    private List<Role> roles;
    
    //所拥有权限
    private List<ModuleJson> modules;
    
    public List<ModuleJson> getModules() {
		return modules;
	}

	public void setModules(List<ModuleJson> modules) {
		this.modules = modules;
	}

	public List<String> getFunctionList() {
		return functionList;
	}

	public void setFunctionList(List<String> functionList) {
		this.functionList = functionList;
	}

	private List<String> functionList;
    
    private String stateView;
   	
    public String getStateView() {
    	if(state==0){
    		return "正常";
    	}else{
    		return "禁用";
    	}
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public void setStateView(String stateView) {
		this.stateView = stateView;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName == null ? null : loginName.trim();
	}

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
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

    public Short getPreset() {
        return preset;
    }

    public void setPreset(Short preset) {
        this.preset = preset;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }

	public Organ getOrgan() {
		return organ;
	}

	public void setOrgan(Organ organ) {
		this.organ = organ;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
}