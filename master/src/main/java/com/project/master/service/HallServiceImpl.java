package com.project.master.service;

import com.project.master.domain.EventLocation;
import com.project.master.domain.Hall;
import com.project.master.domain.Seat;
import com.project.master.domain.TypeOfSeat;
import com.project.master.dto.HallDTO;
import com.project.master.dto.SeatDTO;
import com.project.master.dto.SeatInfoDTO;
import com.project.master.dto.SelectedSeatInfoDTO;
import com.project.master.exception.DataException;
import com.project.master.paypal.api.Order;
import com.project.master.repository.HallRepository;
import com.project.master.repository.LocationRepository;
import com.project.master.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class HallServiceImpl implements HallService {

    @Autowired
    HallRepository hallRepository;

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    SeatRepository seatRepository;
    
    @Override
    public ArrayList<Hall> getAll(){
        return hallRepository.findAll();
    }

    @Override
    public Hall findById(long id) throws DataException{
    	Optional<Hall> ohall = hallRepository.findById(id);
    	if(!ohall.isPresent()) {
    		throw new DataException("Hall does not exists");
    	}
    	Hall hall = ohall.get();
        return hall;
    }

    @Override
    public List<Hall> findByLocation(String name) throws DataException{
        Optional<EventLocation> olocation = locationRepository.findByName(name);
        if(!olocation.isPresent()) {
        	throw new DataException("No location found");
        }
        EventLocation location = olocation.get();
        List<Hall> halls = location.getHallList();
        return halls;
    }

    @Override
    public Hall saveHall(HallDTO hallDTO) throws DataException{
        String locationName = hallDTO.getEvent_location();
        Optional<EventLocation> olocation = locationRepository.findByName(locationName);
        if(!olocation.isPresent()) {
        	throw new DataException("No location found");
        }
        EventLocation eventLocation = olocation.get();
        Hall hall = new Hall(hallDTO);
        hall.setLocation(eventLocation);

        eventLocation.getHallList().add(hall);
        locationRepository.save(eventLocation);

        hallRepository.save(hall);
        return hall;


    }

    @Override
    public Hall updateHall(HallDTO hallDTO) throws DataException{
        Optional<Hall> optionalHall = hallRepository.findById(hallDTO.getId());

        if(!(optionalHall.isPresent()))  throw new DataException("No hall");


        Hall hall = optionalHall.get();

        EventLocation oldEventLocation = hall.getLocation();
        EventLocation newEventLocation = locationRepository.findByName(hallDTO.getEvent_location()).get();

        oldEventLocation.getHallList().remove(hall);
        locationRepository.save(oldEventLocation);
        newEventLocation.getHallList().add(hall);
        locationRepository.save(newEventLocation);

        hall = new Hall(hallDTO);
        hall.setLocation(newEventLocation);
        hallRepository.save(hall);
        return hall;
    }

    @Override
    public void deleteHall(HallDTO hallDTO) throws DataException{
        Optional<Hall> optionalHall = hallRepository.findById(hallDTO.getId());

        if(!(optionalHall.isPresent())) throw new DataException("No hall is present");


        hallRepository.delete(optionalHall.get());
    }

	@Override
	public String saveSeats(SeatInfoDTO seatsDTO) throws DataException {
		Optional<Hall> optionalHall = hallRepository.findById(seatsDTO.getId());
		List<Seat> seats = new ArrayList<Seat>();
		if(!optionalHall.isPresent()) {
			throw new DataException("Hall does not exists");
		}
		Hall h = optionalHall.get();
		System.out.println(seatsDTO.getSeatsP().size());
		
		for(SeatDTO s:seatsDTO.getSeatsP()) {
			System.out.println(s.getSeatLabel());
			String[] arrOfStr = s.getKey().split("_");
			Seat temp  = new Seat();
			temp.setSeat_row(Integer.valueOf(arrOfStr[0]));
			temp.setSeat_column(Integer.valueOf(arrOfStr[1]));
			temp.setTypeOfSeat(TypeOfSeat.valueOf(s.getStatus().toUpperCase()));
			temp.setEvent_id(s.getEvent_id());
			seats.add(temp);
		}
		System.out.println("here");
		h.setTotalRows(seatsDTO.getRowsP());
		h.setTotalColumns(seatsDTO.getColumnsP());
		h.setSeats(seats);
		hallRepository.save(h);
		return "Data saved";
	}

	@Override
	public void updateSeats(SelectedSeatInfoDTO seatsDTO, String buyerUsername) throws DataException {
		Optional<Hall> optionalHall = hallRepository.findById(seatsDTO.getEvent().getHall().getId());
		if(!optionalHall.isPresent()) {
			throw new DataException("Hall does not exists");
		}
		List<Seat> seats = new ArrayList<Seat>();
		Hall h = optionalHall.get();
		System.out.println(seatsDTO.getSeatsP().size());
		double price = 0;
		
		for(SeatDTO s:seatsDTO.getSeatsP()) {
			String[] arrOfStr = s.getKey().split("_");
			Seat temp  = new Seat();
			temp.setSeat_row(Integer.valueOf(arrOfStr[0]));
			temp.setSeat_column(Integer.valueOf(arrOfStr[1]));
			temp.setTypeOfSeat(TypeOfSeat.valueOf(s.getStatus().toUpperCase()));
			temp.setEvent_id(s.getEvent_id());
			seats.add(temp);
		}
		for(SeatDTO s:seatsDTO.getReservedSeats()) {
			price += s.getPrice();
		}
		h.setTotalRows(seatsDTO.getRowsP());
		h.setTotalColumns(seatsDTO.getColumnsP());
		h.setSeats(seats);
		hallRepository.save(h);
		System.out.println(price);
		return;
		
	}

	@Override
	public double getPrice(SelectedSeatInfoDTO seatInfo) {
		double price = 0;
		for(SeatDTO s:seatInfo.getReservedSeats()) {
			price += s.getPrice();
		}
		return price;
	}


}
