package com.project.master.dto;

public class EventDTO {
	
	Long id;
	String name;
	String date_from;
	String date_to;
	String event_location;
	String location_hall;
	String event_category;
	String description;

	public EventDTO(String name, String date_from, String date_to, String event_location, String location_hall,
			String event_category, String description,Long id) {
		super();
		this.name = name;
		this.id = id;
		this.date_from = date_from;
		this.date_to = date_to;
		this.event_location = event_location;
		this.location_hall = location_hall;
		this.event_category = event_category;
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDate_from() {
		return date_from;
	}

	public void setDate_from(String date_from) {
		this.date_from = date_from;
	}

	public String getDate_to() {
		return date_to;
	}

	public void setDate_to(String date_to) {
		this.date_to = date_to;
	}

	public String getEvent_location() {
		return event_location;
	}

	public void setEvent_location(String event_location) {
		this.event_location = event_location;
	}

	public String getLocation_hall() {
		return location_hall;
	}

	public void setLocation_hall(String location_hall) {
		this.location_hall = location_hall;
	}

	public String getEvent_category() {
		return event_category;
	}

	public void setEvent_category(String event_category) {
		this.event_category = event_category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


}
