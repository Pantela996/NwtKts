package com.project.master.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Seat {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private int seat_row;

	@Column
	private int seat_column;

	@Column
	TypeOfSeat typeOfSeat;

	/*@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "hall_id", nullable = false)
	private Hall hall;*/


	/*public Seat(Long id, int seat_row, int seat_column, boolean isReserved, boolean isAvailable, Hall hall) {
		super();
		this.id = id;
		this.seat_row = seat_row;
		this.seat_column = seat_column;
		this.isReserved = isReserved;
		this.isAvailable = isAvailable;
		this.hall = hall;
	}*/



	
	public Long getId() {
		return id;
	}

	public TypeOfSeat getTypeOfSeat() {
		return typeOfSeat;
	}

	public void setTypeOfSeat(TypeOfSeat typeOfSeat) {
		this.typeOfSeat = typeOfSeat;
	}

	public Seat(Long id, int seat_row, int seat_column, TypeOfSeat typeOfSeat) {
		super();
		this.id = id;
		this.seat_row = seat_row;
		this.seat_column = seat_column;
		this.typeOfSeat = typeOfSeat;
	}

	
	public Seat(int seat_row, int seat_column, TypeOfSeat typeOfSeat) {
		this.seat_row = seat_row;
		this.seat_column = seat_column;
		this.typeOfSeat = typeOfSeat;
	}
	
	
	public Seat() {

	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public int getSeat_row() {
		return seat_row;
	}

	public void setSeat_row(int seat_row) {
		this.seat_row = seat_row;
	}

	public int getSeat_column() {
		return seat_column;
	}

	public void setSeat_column(int seat_column) {
		this.seat_column = seat_column;
	}

	/*public Hall getHall() {
		return hall;
	}

	public void setHall(Hall hall) {
		this.hall = hall;
	}*/

}
