package com.project.master.domain;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class EventLocation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String locationCity;
	
	@Column(nullable = false)
	private String user;

	@Column(nullable = false)
	private int numberOfHalls;

	@Column
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "location")
	@JsonBackReference
	private List<Hall> hallList;

	
	
	public EventLocation() {
		super();
	}

	public int getNumberOfHalls() {
		return numberOfHalls;
	}

	public void setNumberOfHalls(int numberOfHalls) {
		this.numberOfHalls = numberOfHalls;
	}

	public EventLocation(Long id, String name, String locationCity) {
		super();
		this.id = id;
		this.name = name;
		this.locationCity = locationCity;

	}
	
	
	public EventLocation(String name, String locationCity, String user, int numberOfHalls) {
		super();
		this.name = name;
		this.locationCity = locationCity;
		this.user = user;
		this.numberOfHalls = numberOfHalls;
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

	public List<Hall> getHallList() {
		return hallList;
	}

	public void setHallList(List<Hall> hallList) {
		this.hallList = hallList;
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

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

}
