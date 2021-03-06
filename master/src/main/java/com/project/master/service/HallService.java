package com.project.master.service;

import com.project.master.domain.Hall;
import com.project.master.domain.Seat;
import com.project.master.dto.HallDTO;
import com.project.master.dto.SeatInfoDTO;
import com.project.master.dto.SelectedSeatInfoDTO;
import com.project.master.exception.DataException;
import com.project.master.paypal.api.Order;

import java.util.ArrayList;
import java.util.List;

public interface HallService {

    public ArrayList<Hall> getAll();
    public Hall findById(long id) throws DataException;


    public List<Hall> findByLocation(String name) throws DataException;


    public Hall saveHall(HallDTO hallDTO) throws DataException;
    public String saveSeats(SeatInfoDTO seatsDTO) throws DataException;
    public Hall updateHall(HallDTO hallDTO) throws DataException;
    public void deleteHall(HallDTO hallDTO) throws DataException;
	public void updateSeats(SelectedSeatInfoDTO seatDTO, String buyerUsername)  throws DataException;
	public double getPrice(SelectedSeatInfoDTO seatInfo);

}
