package com.tianyi.bph.domain.system;

public class IconTypeDetail {
    private Integer id;

    private String typeName;

    private Integer parentIconType;

    private Integer iconGroupId;
  
    private String nomalUrl;
    
    private String selectedUrl;
    
    private String intoEnclosureUrl;
    
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

	private String dispatchUrl;
    
    private String arriveUrl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getParentIconType() {
        return parentIconType;
    }

    public void setParentIconType(Integer parentIconType) {
        this.parentIconType = parentIconType;
    }

    public Integer getIconGroupId() {
        return iconGroupId;
    }

    public void setIconGroupId(Integer iconGroupId) {
        this.iconGroupId = iconGroupId;
    }
}