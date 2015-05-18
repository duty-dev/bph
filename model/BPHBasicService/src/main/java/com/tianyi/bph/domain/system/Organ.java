package com.tianyi.bph.domain.system;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
public class Organ implements Serializable{
    /**  
	* @Title: Organ.java
	* @Package com.tianyi.bph.domain.system
	* @Description: TODO(用一句话描述该文件做什么)
	* @author wangxing  
	* @date 2015年4月1日 上午11:11:41
	* @version V1.0  
	*/
	private static final long serialVersionUID = -2423277906385113954L;
	private Integer id;
    //机构编码
    private String code;

    private String name;
    
    private boolean expanded;
    
    private Integer childCount;
    
    private boolean selected;
    
   
    public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public void addChild(Organ node) {
		if (this.items == null) {
			this.items = new ArrayList<Organ>();
		}
		this.items.add(node);
	}

	public Integer getChildCount() {
		return childCount;
	}

	public void setChildCount(Integer childCount) {
		this.childCount = childCount;
	}

	private boolean hasChild;
    public boolean isHasChild() {
		return hasChild;
	}

	public void setHasChild(boolean hasChild) {
		this.hasChild = hasChild;
	}

	private boolean checked;
    
    public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public boolean isExpanded() {
		return expanded;
	}

	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}

	private String text;

    public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	private Integer parentId;
    
    private List<Organ> items;
    
    public List<Organ> getItems() {
		return items;
	}

	public void setItems(List<Organ> items) {
		this.items = items;
	}

	private String parentName;
    public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	//父对象
    private Organ parentOrgan;

    //路径
    private String path;

    private String shortName;

    //机构类型编码
    private String orgTypeCode;
    
    private String orgTypeName;
    
    public String getOrgTypeName() {
    	if(orgTypeCode.equals("1")){
    		orgTypeName="市局";
    	}else if(orgTypeCode.equals("2")){
    		orgTypeName="分局";
    	}else if(orgTypeCode.equals("3")){
    		orgTypeName="市局直属";
    	}else if(orgTypeCode.equals("4")){
    		orgTypeName="分局机关";
    	}else{
    		orgTypeName="派出所";
    	}
		return orgTypeName;
	}

	public void setOrgTypeName(String orgTypeName) {
		this.orgTypeName = orgTypeName;
	}

	private OrganType organType;

    //机构警种编码
   /* private String orgPcCgyCode;
    
    private String orgPcCgyName;
    
    public String getOrgPcCgyName() {
    	if(orgPcCgyCode.equals("201")){
    		orgPcCgyName="局";
    	}else if(orgPcCgyCode.equals("202")){
    		orgPcCgyName="交警";
    	}else if(orgPcCgyCode.equals("203")){
    		orgPcCgyName="治安";
    	}else if(orgPcCgyCode.equals("204")){
    		orgPcCgyName="刑侦";
    	}else if(orgPcCgyCode.equals("205")){
    		orgPcCgyName="国保";
    	}else if(orgPcCgyCode.equals("206")){
    		orgPcCgyName="巡警";
    	}else if(orgPcCgyCode.equals("207")){
    		orgPcCgyName="政治处";
    	}else if(orgPcCgyCode.equals("208")){
    		orgPcCgyName="指挥中心";
    	}else if(orgPcCgyCode.equals("209")){
    		orgPcCgyName="经侦";
    	}else if(orgPcCgyCode.equals("210")){
    		orgPcCgyName="禁毒";
    	}else if(orgPcCgyCode.equals("211")){
    		orgPcCgyName="特警";
    	}else if(orgPcCgyCode.equals("212")){
    		orgPcCgyName="看守所";
    	}else if(orgPcCgyCode.equals("213")){
    		orgPcCgyName="纪委";
    	}else if(orgPcCgyCode.equals("214")){
    		orgPcCgyName="督察";
    	}else if(orgPcCgyCode.equals("215")){
    		orgPcCgyName="派出所";
    	}else if(orgPcCgyCode.equals("216")){
    		orgPcCgyName="作战室";
    	}else{
    		orgPcCgyName="指挥岗";
    	}
		return orgPcCgyName;
	}

	public void setOrgPcCgyName(String orgPcCgyName) {
		this.orgPcCgyName = orgPcCgyName;
	}*/

	private OrgPcCgy orgPcCgy;
    //级别
    private Integer levle;
    
    //0正常 1 禁用
    private Integer state = 0;

    private Date createTime;

    //预置 0非预置 1预置
    private Short preset = 0;

    private Date updateTime;

    private String note;

    //排序号
    private Integer sortNo;

    public Integer getId() {
        return id;
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

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName == null ? null : shortName.trim();
    }

    public String getOrgTypeCode() {
        return orgTypeCode;
    }

    public void setOrgTypeCode(String orgTypeCode) {
        this.orgTypeCode = orgTypeCode == null ? null : orgTypeCode.trim();
    }
    public Integer getLevle() {
        return levle;
    }

    public void setLevle(Integer levle) {
        this.levle = levle;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Short getPreset() {
        return preset;
    }

    public void setPreset(Short preset) {
        this.preset = preset;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }

    public Integer getSortNo() {
        return sortNo;
    }

    public void setSortNo(Integer sortNo) {
        this.sortNo = sortNo;
    }

	public Organ getParentOrgan() {
		return parentOrgan;
	}

	public void setParentOrgan(Organ parentOrgan) {
		this.parentOrgan = parentOrgan;
	}

	public OrganType getOrganType() {
		return organType;
	}

	public void setOrganType(OrganType organType) {
		this.organType = organType;
	}

	public OrgPcCgy getOrgPcCgy() {
		return orgPcCgy;
	}

	public void setOrgPcCgy(OrgPcCgy orgPcCgy) {
		this.orgPcCgy = orgPcCgy;
	}
}