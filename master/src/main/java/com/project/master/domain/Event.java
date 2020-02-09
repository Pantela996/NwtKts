package com.project.master.domain;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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

	@OneToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	private EventLocation location;

	@OneToOne
	private Hall hall;

	@OneToOne
	private Category category;

	@Column
	private String description;

	@Column
	private String user_id;

	@Column
	private int numberOfTakenPlaces;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Frame> frames;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Ticket> tickets;


	public Event() {

	}

	public Event(Long id, String name, Date dateFrom, Date dateTo, EventLocation location, Hall hall, Category category,
			String description, String user) {
		super();
		this.id = id;
		this.name = name;
		this.dateFrom = dateFrom;
		this.DateTo = dateTo;
		this.location = location;
		this.hall = hall;
		this.category = category;
		this.description = description;
		this.user_id = user;
		this.frames = new ArrayList<Frame>();
		this.tickets = new ArrayList<Ticket>();
	}
	
	
	public Event(String name, Date dateFrom, Date dateTo, EventLocation location, Hall hall, Category category,
			String description, String user) {
		super();
		this.name = name;
		this.dateFrom = dateFrom;
		DateTo = dateTo;
		this.location = location;
		this.hall = hall;
		this.category = category;
		this.description = description;
		this.user_id = user;
		this.frames = new ArrayList<Frame>();
		this.tickets = new ArrayList<Ticket>();
	}


	public Event(Long id, String name, Date dateFrom, Date dateTo, EventLocation location, Hall hall, Category category, String description, String user, int numberOfTakenPlaces) {
		this.id = id;
		this.name = name;
		this.dateFrom = dateFrom;
		this.DateTo = dateTo;
		this.location = location;
		this.hall = hall;
		this.category = category;
		this.description = description;
		this.user_id = user;
		this.numberOfTakenPlaces = 0;
		this.frames = new ArrayList<Frame>();
		this.tickets = new ArrayList<Ticket>();
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

	public EventLocation getLocation() {
		return location;
	}

	public void setLocation(EventLocation locationId) {
		this.location = locationId;
	}

	public Hall getHall() {
		return hall;
	}

	public void setHall(Hall hallId) {
		this.hall = hallId;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category categoryId) {
		this.category = categoryId;
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

	public List<Frame> getFrames() {
		return frames;
	}

	public void setFrames(List<Frame> frames) {
		this.frames = frames;
	}
	
	
	public List<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}


	
	
}
