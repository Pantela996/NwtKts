package com.project.master.controller;


import com.project.master.dto.MessageDTO;
import com.project.master.dto.TicketDTO;
import com.project.master.exception.UserNotFoundException;
import com.project.master.service.TicketService;
import com.project.master.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/ticket")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/buy", method = RequestMethod.GET)
    public ResponseEntity<String> buyTicket(){

        ticketService.buyTicket();

        return new ResponseEntity<String>("Ticket bought", HttpStatus.OK);
    }

    @RequestMapping(value = "/reserveTicket", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageDTO> reserveTicket(@RequestBody TicketDTO ticketDTO) throws UserNotFoundException {

        MessageDTO messageDTO = ticketService.reserveTicket(ticketDTO);

        return new ResponseEntity<MessageDTO>(messageDTO, HttpStatus.OK);
    }

}
