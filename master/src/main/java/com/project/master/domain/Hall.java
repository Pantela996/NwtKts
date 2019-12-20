package com.project.master.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Hall {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "category_id", nullable = false)
	private Category category;

	@Column
	private int totalRows;

	@Column
	private int totalColumns;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "location_id", nullable = false)
	private EventLocation location;


	@JoinTable(
			name = "hall_seat",
			joinColumns = @JoinColumn(
					name = "hall_id",
					referencedColumnName = "id"
			),
			inverseJoinColumns = @JoinColumn(
					name = "seat_id",
					referencedColumnName = "id"
			)
	)
	@OneToMany
	//@OneToMany(fetch = FetchType.LAZY, mappedBy = "hall")
	//@JoinColumn(name = "seat_id", nullable = false)
	private List<Seat> seats;



	public Hall() {
		super();
	}

	public Hall(Long id, Category category, int totalRows, int totalColumns, EventLocation location) {
		super();
		this.id = id;
		this.category = category;
		this.totalRows = totalRows;
		this.totalColumns = totalColumns;
		this.location = location;
	}

	public Hall(Long id, Category category, int totalRows, int totalColumns, EventLocation location, List<Seat> seats) {
		this.id = id;
		this.category = category;
		this.totalRows = totalRows;
		this.totalColumns = totalColumns;
		this.location = location;
		this.seats = seats;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public int getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

	public int getTotalColumns() {
		return totalColumns;
	}

	public void setTotalColumns(int totalColumns) {
		this.totalColumns = totalColumns;
	}

	public EventLocation getLocation() {
		return location;
	}

	public void setLocation(EventLocation location) {
		this.location = location;
	}

	public List<Seat> getSeats() {
		return seats;
	}

	public void setSeats(List<Seat> seats) {
		this.seats = seats;
	}
}
