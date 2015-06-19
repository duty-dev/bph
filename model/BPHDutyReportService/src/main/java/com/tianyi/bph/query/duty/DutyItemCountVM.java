package com.tianyi.bph.query.duty;

/**
 * 报备明细model类，用作前台显示使用
 */
public class DutyItemCountVM {
	/**
	 * 组织机构名称
	 */
	private String orgName;
	/**
	 * 报备明细类型名称
	 */
	private String itemTypeName;
	/**
	 * 值班领导总人数
	 */
	private String shiftLeaderCount;
	/**
	 * 值班主任总人数
	 */
	private String chiefLeaderCount;
	/**
	 * 值班警力总数
	 */
	private String dutypoliceCount;
	/**
	 * 投入车辆总数
	 */
	private String vehicleCount;
	/**
	 * 投入武器总数
	 */
	private String weaponCount;
	/**
	 * 单位值班总数
	 */
	private String unitdutyCount;
	/**
	 * 枪支总数
	 */
	private String illuminatesCount;
	/**
	 * 巡逻总警力数
	 */
	private String patrolpoliceCount;
	/**
	 * 必到点警力数
	 */
	private String checkpointpoliceCount;
	/**
	 * 巡区总警力
	 */
	private String patrolareaPoliceCount; 
	
	/**
	 * 节点数量
	 */
	private String itemCount;
	
	/**
	 * 日期
	 */
	private Integer ymd;
	
	public String getorgName() {
		return orgName;
	}
	public void setorgName(String orgName) {
		this.orgName = orgName;
	}
	public String getItemTypeName() {
		return itemTypeName;
	}
	public void setItemTypeName(String itemTypeName) {
		this.itemTypeName = itemTypeName;
	}
	 
	public String getDutypoliceCount() {
		return dutypoliceCount;
	}
	public void setDutypoliceCount(String dutypoliceCount) {
		this.dutypoliceCount = dutypoliceCount;
	}
	public String getVehicleCount() {
		return vehicleCount;
	}
	public void setVehicleCount(String vehicleCount) {
		this.vehicleCount = vehicleCount;
	}
	public String getWeaponCount() {
		return weaponCount;
	}
	public void setWeaponCount(String weaponCount) {
		this.weaponCount = weaponCount;
	}
	public String getUnitdutyCount() {
		return unitdutyCount;
	}
	public void setUnitdutyCount(String unitdutyCount) {
		this.unitdutyCount = unitdutyCount;
	}
	public String getIlluminatesCount() {
		return illuminatesCount;
	}
	public void setIlluminatesCount(String illuminatesCount) {
		this.illuminatesCount = illuminatesCount;
	}
	public String getPatrolpoliceCount() {
		return patrolpoliceCount;
	}
	public void setPatrolpoliceCount(String patrolpoliceCount) {
		this.patrolpoliceCount = patrolpoliceCount;
	}
	public String getCheckpointpoliceCount() {
		return checkpointpoliceCount;
	}
	public void setCheckpointpoliceCount(String checkpointpoliceCount) {
		this.checkpointpoliceCount = checkpointpoliceCount;
	}
	public String getPatrolareaPoliceCount() {
		return patrolareaPoliceCount;
	}
	public void setPatrolareaPoliceCount(String patrolareaPoliceCount) {
		this.patrolareaPoliceCount = patrolareaPoliceCount;
	}
	public String getShiftLeaderCount() {
		return shiftLeaderCount;
	}
	public void setShiftLeaderCount(String shiftLeaderCount) {
		this.shiftLeaderCount = shiftLeaderCount;
	}
	public String getChiefLeaderCount() {
		return chiefLeaderCount;
	}
	public void setChiefLeaderCount(String chiefLeaderCount) {
		this.chiefLeaderCount = chiefLeaderCount;
	}
	public String getItemCount() {
		return itemCount;
	}
	public void setItemCount(String itemCount) {
		this.itemCount = itemCount;
	}
	public Integer getYmd() {
		return ymd;
	}
	public void setYmd(Integer ymd) {
		this.ymd = ymd;
	}
}
