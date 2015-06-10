package com.tianyi.bph.domain.system;

public class IconGroup {
    private Integer groupId;

    private String groupName;

    private Integer iconType;
    
    private String nomalUrl;
    
    private String selectedUrl;
    
    private String intoEnclosureUrl;
    
    private String dispatchUrl;
    
    private String arriveUrl;
    
    private Integer iconTypeDetaiID;
    
    private String typeName;

    public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Integer getIconTypeDetaiID() {
		return iconTypeDetaiID;
	}

	public void setIconTypeDetaiID(Integer iconTypeDetaiID) {
		this.iconTypeDetaiID = iconTypeDetaiID;
	}

	public String getNomalUrl() {
		return nomalUrl;
	}

	public void setNomalUrl(String nomalUrl) {
		this.nomalUrl = nomalUrl;
	}

	public String getSelectedUrl() {
		return selectedUrl;
	}

	public void setSelectedUrl(String selectedUrl) {
		this.selectedUrl = selectedUrl;
	}

	public String getIntoEnclosureUrl() {
		return intoEnclosureUrl;
	}

	public void setIntoEnclosureUrl(String intoEnclosureUrl) {
		this.intoEnclosureUrl = intoEnclosureUrl;
	}

	public String getDispatchUrl() {
		return dispatchUrl;
	}

	public void setDispatchUrl(String dispatchUrl) {
		this.dispatchUrl = dispatchUrl;
	}

	public String getArriveUrl() {
		return arriveUrl;
	}

	public void setArriveUrl(String arriveUrl) {
		this.arriveUrl = arriveUrl;
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

    public Integer getIconType() {
        return iconType;
    }

    public void setIconType(Integer iconType) {
        this.iconType = iconType;
    }
}