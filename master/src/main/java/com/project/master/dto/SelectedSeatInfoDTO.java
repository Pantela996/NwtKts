package com.project.master.dto;

import java.util.ArrayList;
import java.util.List;


public class SelectedSeatInfoDTO {
	
	private List<SeatDTO> reservedSeats;
	private List<SeatDTO> seatsP;
	private int rowsP;
	private int columnsP;
	EventDTO event;
	
	
	public EventDTO getEvent() {
		return event;
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

	
	public List<SeatDTO> getReservedSeats() {
		return reservedSeats;
	}


	public void setReservedSeats(List<SeatDTO> reservedSeats) {
		this.reservedSeats = reservedSeats;
	}

	
	public SelectedSeatInfoDTO() {
		super();
	}


	public SelectedSeatInfoDTO(List<SeatDTO> seatsP, int rowsP, int columnsP, List<SeatDTO> reservedSeats) {
		super();
		this.seatsP = seatsP;
		this.rowsP = rowsP;
		this.columnsP = columnsP;
		this.reservedSeats = reservedSeats;
	}
	
	
	
	

}
