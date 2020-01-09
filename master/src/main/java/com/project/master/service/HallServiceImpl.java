package com.project.master.service;

import com.project.master.domain.EventLocation;
import com.project.master.domain.Hall;
import com.project.master.dto.HallDTO;
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
        return location.getHallList();
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


}
