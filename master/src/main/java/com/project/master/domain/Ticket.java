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
public class Ticket {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private int price;

	@Column(nullable = false)
	private Long seat_id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "event_id", nullable = false)
	private Event event;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	public Ticket() {

	}

	public Ticket(Long id, int price, Long seat, Event event, User user) {
		super();
		this.id = id;
		this.price = price;
		this.seat_id = seat;
		this.event = event;
		this.user = user;
	}

	public Ticket(Long seat, Event event, User user) {
		super();
		this.id = id;
		this.seat_id = seat;
		this.event = event;
		this.user = user;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getSeat() {
		return seat_id;
	}

	public void setSeat(Long seat) {
		this.seat_id = seat;
	}
	
	

}
