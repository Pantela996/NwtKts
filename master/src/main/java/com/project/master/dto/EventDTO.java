package com.project.master.dto;

public class EventDTO {
	
	
	Long id;
	String name;
	String dateFrom;
	String dateTo;
	LocationDTO location;
	HallDTO hall;
	CategoryDTO category;
	String description;
	
	
	public EventDTO() {
		
	}

	public EventDTO(String name, String date_from, String date_to, LocationDTO location, HallDTO location_hall,
			CategoryDTO event_category, String description,Long id) {
		super();
		this.name = name;
		this.id = id;
		this.dateFrom = date_from;
		this.dateTo = date_to;
		this.location = location;
		this.hall = location_hall;
		this.category = event_category;
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}



	public HallDTO getHall() {
		return hall;
	}

	public void setHall(HallDTO location_hall) {
		this.hall = location_hall;
	}

	public CategoryDTO getCategory() {
		return category;
	}

	public void setCategory(CategoryDTO event_category) {
		this.category = event_category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}

	public String getDateTo() {
		return dateTo;
	}

	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}

	public LocationDTO getLocation() {
		return location;
	}

	public void setLocation(LocationDTO location) {
		this.location = location;
	}


}
