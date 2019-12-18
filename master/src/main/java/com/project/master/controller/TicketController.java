package com.project.master.controller;


import com.project.master.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

@RestController
@RequestMapping(value = "/ticket")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @RequestMapping(value = "/buy", method = RequestMethod.GET)
    public ResponseEntity<String> buyTicket(){

        ticketService.buyTicket();

        return new ResponseEntity<String>("Ticket bought", HttpStatus.OK);
    }

}
