package com.tianyi.bph.domain.system;

import java.util.Date;
import java.util.List;

public class GroupManager {
    private Integer groupId;

    private String groupName;

    private Integer groupType;

    private Integer userId;

    private Integer organId;

    private Date createTime;

    private Integer shareType;
    
    private String sourceData;
    
    private Integer areaType;
    
    private String areaContent;
    

	public String getSourceData() {
		return sourceData;
	}

	public void setSourceData(String sourceData) {
		this.sourceData = sourceData;
	}

	public Integer getAreaType() {
		return areaType;
	}

	public void setAreaType(Integer areaType) {
		this.areaType = areaType;
	}

	public String getAreaContent() {
		return areaContent;
	}

	public void setAreaContent(String areaContent) {
		this.areaContent = areaContent;
	}

	private Integer sourceId;
    

	public Integer getSourceId() {
		return sourceId;
	}

	public void setSourceId(Integer sourceId) {
		this.sourceId = sourceId;
	}

	public List<GroupOther> getGroupOther() {
		return groupOther;
	}

	public void setGroupOther(List<GroupOther> groupOther) {
		this.groupOther = groupOther;
	}

	private List<GroupOther> groupOther;
	
	private AreaGroup areaGroup;

	

	public AreaGroup getAreaGroup() {
		return areaGroup;
	}

	public void setAreaGroup(AreaGroup areaGroup) {
		this.areaGroup = areaGroup;
	}

	public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Integer getGroupType() {
        return groupType;
    }

    public void setGroupType(Integer groupType) {
        this.groupType = groupType;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getOrganId() {
        return organId;
    }

    public void setOrganId(Integer organId) {
        this.organId = organId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getShareType() {
        return shareType;
    }

    public void setShareType(Integer shareType) {
        this.shareType = shareType;
    }
}