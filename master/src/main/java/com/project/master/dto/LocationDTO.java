package com.project.master.dto;

public class LocationDTO {

	private String name;
	private String locationCity;
	private String user_id;
	private int numberOfHalls;

	public LocationDTO(){

	}

	public LocationDTO(String name, String locationCity, String user, int numberOfHalls) {
		super();
		this.user_id = user;
		this.name = name;
		this.locationCity = locationCity;
		this.numberOfHalls = numberOfHalls;

	}


	public int getNumberOfHalls() {
		return numberOfHalls;
	}

	public void setNumberOfHalls(int numberOfHalls) {
		this.numberOfHalls = numberOfHalls;
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocationCity() {
		return locationCity;
	}

	public void setLocationCity(String locationCity) {
		this.locationCity = locationCity;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

}
