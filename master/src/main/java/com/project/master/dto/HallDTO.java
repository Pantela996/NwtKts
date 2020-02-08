package com.project.master.dto;

public class HallDTO {

    private long id;
    private int totalRows;
    private int totalColumns;
    private String event_location;

    


    public HallDTO(){

    }

	public HallDTO(int totalRows, int totalColumns, LocationDTO eventLocation) {
        this.totalRows = totalRows;
        this.totalColumns = totalColumns;
    }
	
    public String getEvent_location() {
		return event_location;
	}

	public void setEvent_location(String event_location) {
		this.event_location = event_location;
	}

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

}
