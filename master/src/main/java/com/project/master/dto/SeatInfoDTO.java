package com.project.master.dto;

import java.util.ArrayList;
import java.util.List;

import com.project.master.domain.Seat;

public class SeatInfoDTO {
	
	private List<SeatDTO> seatsP;
	private int rowsP;
	private int columnsP;
	
	
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


	public SeatInfoDTO(List<SeatDTO> seatsP, int rowsP, int columnsP) {
		super();
		this.seatsP = seatsP;
		this.rowsP = rowsP;
		this.columnsP = columnsP;
	}
	
	
	
	

}
