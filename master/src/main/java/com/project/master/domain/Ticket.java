package com.project.master.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Ticket {

	public Boolean getIs_payed() {
		return is_payed;
	}

	public void setIs_payed(Boolean is_payed) {
		this.is_payed = is_payed;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private double price;
	
	@Column
	private int seat_row;
	
	@Column
	private int seat_column;
	
	@Column
	private Boolean is_payed;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	private Event event;


	public Ticket() {

	}

	public Ticket(Long id, double price, Event event) {
		super();
		this.id = id;
		this.price = price;
		this.event = event;
	}


	public Ticket(Boolean is_payed, double price, int row, int column, Event event) {
		super();
		this.is_payed = is_payed;
		this.price = price;
		this.seat_row = row;
		this.seat_column = column;
		this.event = event;
	}

	public Ticket(double price, int row, int column, Event event) {
		super();
		this.price = price;
		this.seat_row = row;
		this.seat_column = column;
		this.event = event;
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

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	
	

}
