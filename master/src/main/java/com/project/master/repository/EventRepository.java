package com.project.master.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.master.domain.Event;

public interface EventRepository extends JpaRepository<Event, Long> {
	
}
