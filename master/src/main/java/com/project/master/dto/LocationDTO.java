package com.project.master.dto;

public class LocationDTO {

	Long id;
	String name;
	String locationCity;
	String user_id;

	public LocationDTO(String name, String locationCity, String user,Long id) {
		super();
		this.user_id = user;
		this.name = name;
		this.locationCity = locationCity;
		this.id = id;
	}

	
	public LocationDTO() {
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
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
