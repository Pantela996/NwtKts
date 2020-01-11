package com.project.master.dto;

public class TicketDTO {
    String eventId;
    String x;
    String y;
    boolean hasSeats;

    public TicketDTO(){

    }

    public TicketDTO(String eventId, String x, String y, boolean hasSeats) {
        this.eventId = eventId;
        this.x = x;
        this.y = y;
        this.hasSeats = hasSeats;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public boolean isHasSeats() {
        return hasSeats;
    }

    public void setHasSeats(boolean hasSeats) {
        this.hasSeats = hasSeats;
    }
}
