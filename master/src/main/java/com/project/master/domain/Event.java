package com.project.master.domain;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity

public class Event {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private Date dateFrom;

	@Column(nullable = true)
	private Date DateTo;

	@OneToOne
	private EventLocation location;

	@Column
	private Long hallId;

	@Column
	private Long categoryId;

	@Column
	private String description;

	@Column
	private String user_id;

	@Column
	private int numberOfTakenPlaces;
	
	public Event() {

	}

	public Event(Long id, String name, Date dateFrom, Date dateTo, EventLocation location, Long hallId, Long categoryId,
			String description, String user) {
		super();
		this.id = id;
		this.name = name;
		this.dateFrom = dateFrom;
		DateTo = dateTo;
		this.location = location;
		this.hallId = hallId;
		this.categoryId = categoryId;
		this.description = description;
		this.user_id = user;
	}
	
	
	public Event(String name, Date dateFrom, Date dateTo, EventLocation location, Long hallId, Long categoryId,
			String description, String user) {
		super();
		this.name = name;
		this.dateFrom = dateFrom;
		DateTo = dateTo;
		this.location = location;
		this.hallId = hallId;
		this.categoryId = categoryId;
		this.description = description;
		this.user_id = user;
	}


	public Event(Long id, String name, Date dateFrom, Date dateTo, Long locationId, Long hallId, Long categoryId, String description, String user_id, int numberOfTakenPlaces) {
		this.id = id;
		this.name = name;
		this.dateFrom = dateFrom;
		DateTo = dateTo;
		this.locationId = locationId;
		this.hallId = hallId;
		this.categoryId = categoryId;
		this.description = description;
		this.user_id = user_id;
		this.numberOfTakenPlaces = numberOfTakenPlaces;
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

	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return DateTo;
	}

	public void setDateTo(Date dateTo) {
		DateTo = dateTo;
	}

	public EventLocation getLocationId() {
		return location;
	}

	public void setLocationId(EventLocation locationId) {
		this.location = locationId;
	}

	public Long getHallId() {
		return hallId;
	}

	public void setHallId(Long hallId) {
		this.hallId = hallId;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUser() {
		return user_id;
	}

	public void setUser(String user) {
		this.user_id = user;
	}

	public int getNumberOfTakenPlaces() {
		return numberOfTakenPlaces;
	}

	public void setNumberOfTakenPlaces(int numberOfTakenPlaces) {
		this.numberOfTakenPlaces = numberOfTakenPlaces;
	}
}
