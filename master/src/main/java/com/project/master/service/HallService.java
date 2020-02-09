package com.project.master.service;

import com.project.master.domain.Hall;
import com.project.master.domain.Seat;
import com.project.master.dto.HallDTO;
import com.project.master.dto.SeatInfoDTO;
import com.project.master.dto.SelectedSeatInfoDTO;
import com.project.master.paypal.api.Order;

import java.util.ArrayList;
import java.util.List;

public interface HallService {

    public ArrayList<Hall> getAll();
    public Hall findById(long id);


    public List<Hall> findByLocation(String name);


    public Hall saveHall(HallDTO hallDTO);
    public String saveSeats(SeatInfoDTO seatsDTO);
    public Hall updateHall(HallDTO hallDTO);
    public void deleteHall(HallDTO hallDTO);
	public void updateSeats(SelectedSeatInfoDTO seatDTO, String buyerUsername);
	public double getPrice(SelectedSeatInfoDTO seatInfo);

}
