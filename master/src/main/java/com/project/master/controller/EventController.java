package com.project.master.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.project.master.dto.EventDTO;
import com.project.master.service.EventService;

@RestController
@RequestMapping("event")
public class EventController {
 
	@Autowired
	private EventService eventService;

	
	//@PreAuthorize("hasAuthority('LOCATION_AND_EVENT_ADMIN_ROLE')")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseEntity<String> createEvent(@RequestBody EventDTO eventDTO) {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String currentPrincipalName = authentication.getName();
			System.out.println(currentPrincipalName);
			System.out.println(authentication.getAuthorities());
			//String token = req.getHeader(HEADER_STRING).replace(TOKEN_PREFIX,"");
			String message = eventService.createEvent(eventDTO, authentication.getName());
			return new ResponseEntity<String>(message, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("Invalid creation", HttpStatus.BAD_REQUEST);
		}
	}
	
	
	
}
