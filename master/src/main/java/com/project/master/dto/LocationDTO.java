package com.project.master.dto;

public class LocationDTO {


	Long id;
	String name;
	String locationCity;
	String user_id;
	private int numberOfHalls;



	public LocationDTO(String name, String locationCity, String user,Long id) {
		this.user_id = user;
		this.name = name;
		this.locationCity = locationCity;
	}

	public LocationDTO(){

	}

	public LocationDTO(String name, String locationCity, String user, int numberOfHalls) {
		this.user_id = user;
		this.name = name;
		this.locationCity = locationCity;
		this.numberOfHalls = numberOfHalls;
	}
	
	public LocationDTO(Long id,String name, String locationCity, String user, int numberOfHalls) {
		this.id = id;
		this.user_id = user;
		this.name = name;
		this.locationCity = locationCity;
		this.numberOfHalls = numberOfHalls;
	}

	


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
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
		if(locationCity.equalsIgnoreCase("nullValue")) {
			this.locationCity = null;
		}else {
			this.locationCity = locationCity;
		}
		
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

}
