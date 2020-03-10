package com.project.master.dto;

public class TicketDTO {

	private Long id;
	private Double price;
	private Integer seat_row;
	private Integer seat_column;
	private EventDTO event;
	
	

	public TicketDTO() {
		super();
	}
	
	public TicketDTO(Long id, Double price, Integer seat_row, Integer seat_column, EventDTO event) {
		super();
		this.id = id;
		this.price = price;
		this.seat_row = seat_row;
		this.seat_column = seat_column;
		this.event = event;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Integer getSeat_row() {
		return seat_row;
	}
	public void setSeat_row(Integer seat_row) {
		this.seat_row = seat_row;
	}
	public Integer getSeat_column() {
		return seat_column;
	}
	public void setSeat_column(Integer seat_column) {
		this.seat_column = seat_column;
	}
	public EventDTO getEvent() {
		return event;
	}
	public void setEvent(EventDTO event) {
		this.event = event;
	}
	
	
}
