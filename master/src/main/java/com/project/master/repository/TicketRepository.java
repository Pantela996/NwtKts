package com.project.master.repository;

import com.project.master.domain.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    ArrayList<Ticket> findAll();
}
