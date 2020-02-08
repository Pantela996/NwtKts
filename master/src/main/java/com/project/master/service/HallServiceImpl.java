package com.project.master.service;

import com.project.master.domain.EventLocation;
import com.project.master.domain.Hall;
import com.project.master.domain.Seat;
import com.project.master.domain.TypeOfSeat;
import com.project.master.dto.HallDTO;
import com.project.master.dto.SeatDTO;
import com.project.master.dto.SeatInfoDTO;
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
    public Hall findById(long id){
        return hallRepository.findById(id).get();
    }

    @Override
    public List<Hall> findByLocation(String name){
        EventLocation location = locationRepository.findByName(name).get();
        List<Hall> halls = location.getHallList();
        return halls;
    }

    @Override
    public Hall saveHall(HallDTO hallDTO){
        String locationName = hallDTO.getEventLocation();
        EventLocation eventLocation = locationRepository.findByName(locationName).get();
        Hall hall = new Hall(hallDTO);
        hall.setLocation(eventLocation);

        eventLocation.getHallList().add(hall);
        locationRepository.save(eventLocation);

        hallRepository.save(hall);
        return hall;


    }

    @Override
    public Hall updateHall(HallDTO hallDTO){
        Optional<Hall> optionalHall = hallRepository.findById(hallDTO.getId());

        if(!(optionalHall.isPresent()))  return null;


        Hall hall = optionalHall.get();

        EventLocation oldEventLocation = hall.getLocation();
        EventLocation newEventLocation = locationRepository.findByName(hallDTO.getEventLocation()).get();

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
    public void deleteHall(HallDTO hallDTO){
        Optional<Hall> optionalHall = hallRepository.findById(hallDTO.getId());

        if(!(optionalHall.isPresent())) return;


        hallRepository.delete(optionalHall.get());
    }

	@Override
	public String saveSeats(SeatInfoDTO seatsDTO) {
		Optional<Hall> optionalHall = hallRepository.findById(1);
		List<Seat> seats = new ArrayList<Seat>();
		Hall h = optionalHall.get();
		System.out.println(seatsDTO.getSeatsP().size());
		
		for(SeatDTO s:seatsDTO.getSeatsP()) {
			System.out.println("seatsDTO p");
			String[] arrOfStr = s.getKey().split("_");
			System.out.println(arrOfStr[0]);
			System.out.println(arrOfStr[1]);
			System.out.println(s.getStatus());
			Seat temp  = new Seat();
			temp.setSeat_row(Integer.valueOf(arrOfStr[0]));
			temp.setSeat_column(Integer.valueOf(arrOfStr[1]));
			temp.setTypeOfSeat(TypeOfSeat.valueOf(s.getStatus().toUpperCase()));
			temp.setEvent_id(s.getEvent_id());
			seats.add(temp);
		}
		h.setTotalRows(seatsDTO.getRowsP());
		h.setTotalColumns(seatsDTO.getColumnsP());
		h.setSeats(seats);
		hallRepository.save(h);
		return "Data saved";
	}


}
