package com.project.master.controller;

import java.util.ArrayList;

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

import com.project.master.domain.EventLocation;
import com.project.master.dto.LocationDTO;
import com.project.master.service.LocationService;

@RestController
@RequestMapping("location")
public class LocationController {

	@Autowired
	private LocationService locationService;

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseEntity<String> createLocation(@RequestBody LocationDTO locationDTO) {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String message = locationService.createLocation(locationDTO, authentication.getName());
			return new ResponseEntity<String>(message, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("Invalid creation", HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public ResponseEntity<String> deleteLocation(@RequestParam String location_id) {
		try {
			String message = locationService.deleteLocation(location_id);
			return new ResponseEntity<String>(message, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("Invalid delete", HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ResponseEntity<String> updateLocation(@RequestParam String location_id,
			@RequestBody LocationDTO locationDTO) {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String message = locationService.updateLocation(location_id, locationDTO, authentication.getName());
			return new ResponseEntity<String>(message, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("Invalid delete", HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ResponseEntity<ArrayList<EventLocation>> getAll() {
		try {
			ArrayList<EventLocation> events = locationService.getAll();
			return new ResponseEntity<ArrayList<EventLocation>>(events, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<ArrayList<EventLocation>>(new ArrayList<EventLocation>(), HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/one", method = RequestMethod.POST)
	public ResponseEntity<EventLocation> getOne(@RequestParam String location_id) {
		try {
			EventLocation event = locationService.getOne(location_id);
			return new ResponseEntity<EventLocation>(event, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<EventLocation>(new EventLocation(), HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@RequestMapping(value = "/myLocations", method = RequestMethod.GET)
	public ResponseEntity<ArrayList<EventLocation>> getMyEvents() {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			ArrayList<EventLocation> locations = locationService.getMyLocations(authentication.getName());
			return new ResponseEntity<ArrayList<EventLocation>>(locations, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<ArrayList<EventLocation>>(new ArrayList<EventLocation>(), HttpStatus.BAD_REQUEST);
		}
	}
	
	
	
	
	
	//TO BE IMPLEMENTED : operacije nad halama u lokaciji

}
