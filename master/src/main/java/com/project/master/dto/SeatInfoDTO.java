package com.project.master.dto;

import java.util.List;


public class SeatInfoDTO {
	
	private Long id;


	private List<SeatDTO> seatsP;
	private int rowsP;
	private int columnsP;
	private EventDTO event;
	
	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}

	
	public EventDTO getEvent() {
		return event;
	}


	public SeatInfoDTO(Long id, List<SeatDTO> seatsP, int rowsP, int columnsP, EventDTO event) {
		super();
		this.id = id;
		this.seatsP = seatsP;
		this.rowsP = rowsP;
		this.columnsP = columnsP;
		this.event = event;
	}


	public void setEvent(EventDTO event) {
		this.event = event;
	}


	public List<SeatDTO> getSeatsP() {
		return seatsP;
	}


	public void setSeatsP(List<SeatDTO> seatsP) {
		this.seatsP = seatsP;
	}


	public int getRowsP() {
		return rowsP;
	}


	public void setRowsP(int rowsP) {
		this.rowsP = rowsP;
	}


	public int getColumnsP() {
		return columnsP;
	}


	public void setColumnsP(int columnsP) {
		this.columnsP = columnsP;
	}


	public SeatInfoDTO() {
		super();
	}


	public SeatInfoDTO(List<SeatDTO> seatsP, int rowsP, int columnsP, EventDTO eventhtelp) {
		super();
		this.seatsP = seatsP;
		this.rowsP = rowsP;
		this.columnsP = columnsP;
		this.event = eventhtelp;
	}
	
	
	
	

}
