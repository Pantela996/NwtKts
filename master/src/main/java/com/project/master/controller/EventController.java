package com.project.master.controller;

import java.util.ArrayList;

import javax.annotation.security.PermitAll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.master.domain.Event;
import com.project.master.dto.EventDTO;
import com.project.master.service.EventService;

@RestController
@RequestMapping("event")
public class EventController {

	@Autowired
	private EventService eventService;

	// @PreAuthorize("hasAuthority('LOCATION_AND_EVENT_ADMIN_ROLE')")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseEntity<String> createEvent(@RequestBody EventDTO eventDTO) {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String message = eventService.createEvent(eventDTO, authentication.getName());
			return new ResponseEntity<String>(message, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("Invalid creation", HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public ResponseEntity<String> deleteEvent(@RequestParam String event_id) {
		try {
			String message = eventService.deleteEvent(event_id);
			return new ResponseEntity<String>(message, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("Invalid delete", HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public ResponseEntity<String> updateEvent(@RequestParam String event_id, @RequestBody EventDTO eventDTO) {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String message = eventService.updateEvent(event_id, eventDTO, authentication.getName());
			return new ResponseEntity<String>(message, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("Invalid update", HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	@PermitAll()
	public ResponseEntity<ArrayList<Event>> getAll() {
		try {
			ArrayList<Event> events = eventService.getAll();
			return new ResponseEntity<ArrayList<Event>>(events, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<ArrayList<Event>>(new ArrayList<Event>(), HttpStatus.BAD_REQUEST);
		}
	}
	
	
	

	@RequestMapping(value = "/one", method = RequestMethod.POST)
	public ResponseEntity<Event> getOne(@RequestParam String event_id) {
		try {
			Event event = eventService.getOne(event_id);
			return new ResponseEntity<Event>(event, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Event>(new Event(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/myEvents", method = RequestMethod.GET)
	public ResponseEntity<ArrayList<Event>> getMyEvents() {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			ArrayList<Event> events = eventService.getMyEvents(authentication.getName());
			return new ResponseEntity<ArrayList<Event>>(events, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<ArrayList<Event>>(new ArrayList<Event>(), HttpStatus.BAD_REQUEST);
		}
	}
	
	
	
	

}
