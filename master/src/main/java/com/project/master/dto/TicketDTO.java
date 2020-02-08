package com.project.master.dto;

public class TicketDTO {

    EventDTO event;
	private int rowsP;
	private int columnsP;

    public TicketDTO(){

    }

	public TicketDTO(EventDTO event, int rowsP, int columnsP) {
		super();
		this.event = event;
		this.rowsP = rowsP;
		this.columnsP = columnsP;
	}

	public EventDTO getEvent() {
		return event;
	}

	public void setEvent(EventDTO event) {
		this.event = event;
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
    
}
