package com.project.master.dto;

public class SeatDTO {
	
	
	private String key;
	private double price;
	private String status;
	private String seatLabel;
	private String seatNo;
	private Long event_id;
	
	
	
	
	
	public Long getEvent_id() {
		return event_id;
	}




	public void setEvent_id(Long event_id) {
		this.event_id = event_id;
	}




	public String getKey() {
		return key;
	}




	public void setKey(String key) {
		this.key = key;
	}




	public double getPrice() {
		return price;
	}




	public void setPrice(double price) {
		this.price = price;
	}




	public String getStatus() {
		return status;
	}




	public void setStatus(String status) {
		this.status = status;
	}




	public String getSeatLabel() {
		return seatLabel;
	}




	public void setSeatLabel(String seatLabel) {
		this.seatLabel = seatLabel;
	}




	public String getSeatNo() {
		return seatNo;
	}




	public void setSeatNo(String seatNo) {
		this.seatNo = seatNo;
	}




	public SeatDTO(String key, double price, String status, String seatLabel, String seatNo) {
		super();
		this.key = key;
		this.price = price;
		this.status = status;
		this.seatLabel = seatLabel;
		this.seatNo = seatNo;
	}
	
	
	
	
	public SeatDTO() {

	}
	
	
	
	
	

}
