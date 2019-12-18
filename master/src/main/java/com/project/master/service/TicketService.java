package com.project.master.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TicketService {

    @Autowired
    private EmailService emailService;

    public boolean buyTicket(){
        emailService.sendEmail();
        return true;
    }
}
