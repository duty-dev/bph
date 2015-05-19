package com.tianyi.bph.query.basicdata;

import java.util.List;

import com.tianyi.bph.domain.basicdata.Vehicle;
  

public class VehicleInfo   extends ItemInfo<Vehicle> {
	private  List<PoliceEInfo> policeItems;
	private  List<GpsInfo> gpsItems;
	
	
	public List<GpsInfo> getGpsItems() {
		return gpsItems;
	}

	public void setGpsItems(List<GpsInfo> gpsItems) {
		this.gpsItems = gpsItems;
	}

	public List<PoliceEInfo> getPoliceItems() {
		return policeItems;
	}

	public void setPoliceItems(List<PoliceEInfo> policeItems) {
		this.policeItems = policeItems;
	} 
	public String getDutyTypeName() {
		return dutyTypeName;
	}
	public void setDutyTypeName(String dutyTypeName) {
		this.dutyTypeName = dutyTypeName;
	}
	private String dutyTypeName;
}
