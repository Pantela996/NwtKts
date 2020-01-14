package com.project.master.service;

import com.project.master.domain.Hall;
import com.project.master.domain.Seat;
import com.project.master.dto.HallDTO;
import com.project.master.dto.SeatInfoDTO;

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

}
