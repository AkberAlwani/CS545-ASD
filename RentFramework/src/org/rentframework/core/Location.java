package org.rentframework.core;
/*
 * Author: Akber
 */
import cs525.annotations.Validations.NotNull;

public class Location {
	private int locationId;
	
	@NotNull
	private String locationName;


	public Location(){
		
	}
	public int getLocationId() {
		return locationId;
	}
	
	

	/**
	 * @param locationId
	 * @param locationName
	 */
	public Location(int locationId, String locationName) {
		super();
		this.locationId = locationId;
		this.locationName = locationName;
	}



	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
}
