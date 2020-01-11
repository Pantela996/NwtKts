package com.project.master.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.master.domain.Event;

import java.util.ArrayList;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {
	ArrayList<Event> findAll();

	Optional<Event> findById(Long id);
}
