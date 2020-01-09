package com.project.master.domain;

import com.project.master.dto.HallDTO;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Hall {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private int totalRows;

	@Column
	private int totalColumns;

	@ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.PERSIST)
	private EventLocation location;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Seat> seats;



	public Hall() {
		super();
	}

	public Hall(Long id, int totalRows, int totalColumns, EventLocation location) {
		super();
		this.id = id;
		this.totalRows = totalRows;
		this.totalColumns = totalColumns;
		this.location = location;
	}

	public Hall(Long id, int totalRows, int totalColumns, EventLocation location, List<Seat> seats) {
		this.id = id;
		this.totalRows = totalRows;
		this.totalColumns = totalColumns;
		this.location = location;
		this.seats = seats;
	}

	public Hall(HallDTO hallDTO){
		this.totalRows = hallDTO.getTotalRows();
		this.totalColumns = hallDTO.getTotalColumns();

		this.seats = new ArrayList<>(this.totalColumns*this.totalRows);

	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
