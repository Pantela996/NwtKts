package com.project.master.dto;

public class HallDTO {

    private long id;
    private int totalRows;
    private int totalColumns;
    private String eventLocation;


    public HallDTO(){

    }

    public HallDTO(int totalRows, int totalColumns, String eventLocation) {
        this.totalRows = totalRows;
        this.totalColumns = totalColumns;
        this.eventLocation = eventLocation;
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

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }
}
