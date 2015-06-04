package com.tianyi.bph.domain.system;

import java.io.Serializable;
import java.util.List;

public class Module implements Serializable {
    /**  
	* @Title: Module.java
	* @Package com.tianyi.bph.domain.system
	* @Description: TODO(用一句话描述该文件做什么)
	* @author wangxing  
	* @date 2015年4月2日 上午11:29:10
	* @version V1.0  
	*/
	private static final long serialVersionUID = 2080301375994767917L;

	private Integer id;

    private String code;

    private String name;

    private String param;
    
    private Boolean checked;
    
    private Boolean enable;
    
    private String url;//功能模块的Url地址
    
    private String moduleType;//模块类型（客户端需要，现在写死WebSite）
    
    private String moduleOpType;//(同上，客户端需要,现在写死Control)
    
    private String assemblyName;//客户端自己写的页面需要（数据库已写死）
    
    private String typeName;//同上

    public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getModuleType() {
		return moduleType;
	}

	public void setModuleType(String moduleType) {
		this.moduleType = moduleType;
	}

	public String getModuleOpType() {
		return moduleOpType;
	}

	public void setModuleOpType(String moduleOpType) {
		this.moduleOpType = moduleOpType;
	}

	public String getAssemblyName() {
		return assemblyName;
	}

	public void setAssemblyName(String assemblyName) {
		this.assemblyName = assemblyName;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	private Integer parentId;

    private Integer mtype;

    private Integer opType;

    private Integer sortNo;
    
    private Boolean expanded;
    
    private List<Module> items;
    
    private String text;

    public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<Module> getItems() {
		return items;
	}

	public void setItems(List<Module> items) {
		this.items = items;
	}

	public Integer getId() {
        return id;
    }

    public Boolean getExpanded() {
		return expanded;
	}

	public void setExpanded(Boolean expanded) {
		this.expanded = expanded;
	}

	public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param == null ? null : param.trim();
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getMtype() {
        return mtype;
    }

    public void setMtype(Integer mtype) {
        this.mtype = mtype;
    }

    public Integer getOpType() {
        return opType;
    }

    public void setOpType(Integer opType) {
        this.opType = opType;
    }

    public Integer getSortNo() {
        return sortNo;
    }

    public void setSortNo(Integer sortNo) {
        this.sortNo = sortNo;
    }
}