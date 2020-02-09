package com.project.master.dto;

public class EventDTO {
	
	
	Long id;
	String name;
	String dateFrom;
	String dateTo;
	LocationDTO event_location;
	HallDTO seatInfo;
	SeatInfoDTO hall;
	CategoryDTO category;
	String description;
	
	
	public EventDTO() {
		
	}
	
	public EventDTO(String name, String dateFrom, String dateTo, LocationDTO location, SeatInfoDTO hall,
			HallDTO seatInfo, CategoryDTO category, String description,Long id) {
		super();
		this.name = name;
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
		this.event_location = location;
		this.hall = hall;
		this.seatInfo = seatInfo;
		this.category = category;
		this.description = description;
		this.id = id;
	}
	
	public HallDTO getSeatInfo() {
		return seatInfo;
	}

	public void setSeatInfo(HallDTO seatInfo) {
		this.seatInfo = seatInfo;
	}


	public EventDTO(String name, String date_from, String date_to, LocationDTO location, HallDTO seatInfo,
			CategoryDTO event_category, String description,Long id) {
		super();
		this.name = name;
		this.id = id;
		this.dateFrom = date_from;
		this.dateTo = date_to;
		this.event_location = location;
		//this.hall = location_hall;
		this.category = event_category;
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}



	public SeatInfoDTO getHall() {
		return hall;
	}

	public void setHall(SeatInfoDTO location_hall) {
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

	public LocationDTO getEvent_location() {
		return event_location;
	}

	public void setEvent_location(LocationDTO event_location) {
		this.event_location = event_location;
	}




}
