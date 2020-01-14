package com.project.master.service;

import com.project.master.domain.*;
import com.project.master.dto.MessageDTO;
import com.project.master.dto.TicketDTO;
import com.project.master.exception.UserNotFoundException;
import com.project.master.repository.CategoryRepository;
import com.project.master.repository.EventRepository;
import com.project.master.repository.HallRepository;
import com.project.master.repository.TicketRepository;
//import javafx.scene.media.MediaErrorEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import sun.plugin2.message.Message;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;


@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private HallRepository hallRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryRepository categoryRepository;


    public boolean buyTicket() {
        emailService.sendEmail();
        return true;
    }

    public MessageDTO reserveTicket(TicketDTO ticketDTO) throws UserNotFoundException {

        Optional<User> user = userService.getLoggedUser();


        if(user == null){
            return new MessageDTO(false,"User not found!");
        }

        ArrayList<Event> events = (ArrayList<Event>)eventRepository.findAll();

        Event event = null;
        for (int i = 0; i < events.size(); i++) {
            if (events.get(i).getId() == Long.parseLong(ticketDTO.getEventId())) {
                event = eventRepository.findById(Long.parseLong(ticketDTO.getEventId())).get();
            }
        }

        if (event == null) {
            return new MessageDTO(false, "Event with id " + ticketDTO.getEventId() + " wasn't found!");
        }

        Date date = new Date();

        if(event.getDateFrom().before(date)){
            return new MessageDTO(false, "Event was passed!");
        }


        ArrayList<Hall> halls = hallRepository.findAll();
        int r = Integer.parseInt(ticketDTO.getX());
        int c = Integer.parseInt(ticketDTO.getY());


        Hall hall = null;
        for (int i = 0; i < halls.size(); i++) {
            if (halls.get(i).getId() == event.getHallId()) {
                hall = halls.get(i);
                break;
            }
        }

        if(hall == null){
            return new MessageDTO(false, "Hall doesnt exists for this event!");
        }


        Ticket ticket = null;
        if(ticketDTO.isHasSeats()){
            if (!((r * hall.getTotalColumns() + c) <= (hall.getTotalRows() * hall.getTotalColumns() - 1))) {
                return new MessageDTO(false, "Wrong seat coordinates!");
            }


            if ((hall.getSeats().get(r * hall.getTotalColumns() + c).getTypeOfSeat()) == TypeOfSeat.RESERVED) {
                return new MessageDTO(false, "Seat was already taken!");
            }

            if (!((hall.getSeats().get(r * hall.getTotalColumns() + c).getTypeOfSeat()) == TypeOfSeat.AVAILABLE)) {
                return new MessageDTO(false, "There is not seat at this coordinates!");
            }

            hall.getSeats().get(r * hall.getTotalColumns() + c).setTypeOfSeat(TypeOfSeat.RESERVED);
            hallRepository.save(hall);
            eventRepository.save(event);

            ticket = new Ticket(hall.getSeats().get(r * hall.getTotalColumns() + c).getId(),event,user.get());


        }else{
            Category category = categoryRepository.findById(event.getCategoryId()).get();
            if(event.getNumberOfTakenPlaces() == category.getRequiredColumns()*category.getRequiredRows()){
                return new MessageDTO(false, "Hall is full!");
            }

            event.setNumberOfTakenPlaces(event.getNumberOfTakenPlaces()+1);
            hallRepository.save(hall);
            eventRepository.save(event);

            ticket = new Ticket(hall.getSeats().get(r * hall.getTotalColumns() + c).getId(),event,user.get());

        }

        ticketRepository.save(ticket);
        return new MessageDTO(true, "Ticket was succesfully reserved!");
    }

}