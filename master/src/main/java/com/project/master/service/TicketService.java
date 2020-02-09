package com.project.master.service;

import com.project.master.domain.*;
import com.project.master.dto.MessageDTO;
import com.project.master.dto.SeatDTO;
import com.project.master.dto.SelectedSeatInfoDTO;
import com.project.master.dto.TicketDTO;
import com.project.master.exception.UserNotFoundException;
import com.project.master.repository.CategoryRepository;
import com.project.master.repository.EventRepository;
import com.project.master.repository.HallRepository;
import com.project.master.repository.TicketRepository;
import com.project.master.repository.UserRepository;

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
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;


    public boolean buyTicket() {
        emailService.sendEmail();
        return true;
    }

    public MessageDTO reserveTicket(SelectedSeatInfoDTO seatInfoDTO) throws UserNotFoundException {

        Optional<User> ouser = userService.getLoggedUser();

        
        if(!ouser.isPresent()){
            return new MessageDTO(false,"User not found!");
        }
        
        User user = ouser.get();

        ArrayList<Event> events = (ArrayList<Event>)eventRepository.findAll();

        Optional<Event> oevent = null;
        for (int i = 0; i < events.size(); i++) {
            if (events.get(i).getId() == seatInfoDTO.getEvent().getId()) {
                oevent = eventRepository.findById(seatInfoDTO.getEvent().getId());
            }
        }

        if (!oevent.isPresent()) {
            return new MessageDTO(false, "Event with id " + seatInfoDTO.getEvent().getId() + " wasn't found!");
        }

        Date date = new Date();
        
        Event event = oevent.get();
        


        for(SeatDTO s:seatInfoDTO.getReservedSeats()) {
    		System.out.println("FINAL STEP");
			String[] arrOfStr = s.getKey().split("_");
			int row = (Integer.valueOf(arrOfStr[0]));
			int column = (Integer.valueOf(arrOfStr[1]));
        	Ticket t = new Ticket(s.getPrice(), row,column, event);
        	ticketRepository.save(t);
        	user.getTicket().add(t);
        	userRepository.save(user);
        }

        
      
        
        return new MessageDTO(true, "Ticket was succesfully reserved!");
    }

}