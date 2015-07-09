package com.tianyi.bph.domain.basicdata;
/*
 * 车辆用途实体类
 */
public class VehiclePurpose {
	/*
	 * 主键值id
	 */
    private Integer id;
    /*
	 * 车辆用途名称
	 */
    private String name;
    /*
	 *平台标识 
	 */
    private Boolean syncState;
    /*
	 * 平台id
	 */
    private Integer platformId;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Boolean getSyncState() {
		return syncState;
	}
	public void setSyncState(Boolean syncState) {
		this.syncState = syncState;
	}
	public Integer getPlatformId() {
		return platformId;
	}
	public void setPlatformId(Integer platformId) {
		this.platformId = platformId;
	}
    

}
