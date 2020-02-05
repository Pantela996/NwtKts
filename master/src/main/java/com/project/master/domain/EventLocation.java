package com.project.master.domain;

import javax.persistence.*;

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

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Hall> hallList;

	
	
	public EventLocation() {
		super();
	}

	public EventLocation(Long id, String name, String locationCity) {
		super();
		this.id = id;
		this.name = name;
		this.locationCity = locationCity;
	}
	
	
	public EventLocation(String name, String locationCity, String user) {
		super();
		this.name = name;
		this.locationCity = locationCity;
		this.user = user;
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
