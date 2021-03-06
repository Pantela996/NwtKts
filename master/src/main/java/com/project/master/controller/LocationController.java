package com.project.master.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.PermitAll;

import com.project.master.domain.Event;
import com.project.master.domain.Hall;
import com.project.master.service.HallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.project.master.domain.Category;
import com.project.master.domain.EventLocation;
import com.project.master.dto.CategoryDTO;
import com.project.master.dto.LocationDTO;
import com.project.master.exception.DataException;
import com.project.master.service.LocationService;

@RestController
@RequestMapping("location")
public class LocationController {

	@Autowired
	private LocationService locationService;

	@Autowired
	private HallService hallService;

	@PermitAll()
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseEntity<String> createLocation(@RequestBody LocationDTO locationDTO) {

		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String message = locationService.createLocation(locationDTO, authentication.getName());
			return new ResponseEntity<String>("Successful", HttpStatus.OK);
		} catch (DataException e) {
			// TODO Auto-generated catch block
			return new ResponseEntity<>("Failed null", HttpStatus.BAD_REQUEST);
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
		ArrayList<EventLocation> events = new ArrayList<EventLocation>();
		events = locationService.getAll();
		return new ResponseEntity<ArrayList<EventLocation>>(events, HttpStatus.OK);
	}

	@RequestMapping(value = "/halls/{location}", method = RequestMethod.GET)
	public ResponseEntity<List<Hall>> getHalls(@PathVariable String location) throws DataException{
		System.out.println("Usao sam u controller za sve hale");
		List<Hall> halls =  hallService.findByLocation(location);
		return new ResponseEntity<List<Hall>>(halls, HttpStatus.OK);

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

	// TO BE IMPLEMENTED : operacije nad halama u lokaciji
	
	@RequestMapping(value = "/category/create", method = RequestMethod.POST)
	public ResponseEntity<String> createCategory(@RequestBody CategoryDTO categoryDTO) {

		try {
			String message = locationService.createCategory(categoryDTO.getName(), categoryDTO.getRequiredRows(), categoryDTO.getRequiredColumns());
			return new ResponseEntity<String>(message, HttpStatus.OK);
		} catch (DataException e) {
			// TODO Auto-generated catch block
			return new ResponseEntity<>("Failed null", HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@RequestMapping(value = "/category/get_all", method = RequestMethod.GET)
	public ResponseEntity<ArrayList<Category>> getCategories() {
			ArrayList<Category> categories = locationService.getAllCategories();
			return new ResponseEntity<ArrayList<Category>>(categories, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/category/update", method = RequestMethod.PUT)
	public ResponseEntity<Category> updateCategories(@RequestBody CategoryDTO categoryDTO) throws DataException {
			Category category = locationService.updateCategory(categoryDTO);
			return new ResponseEntity<Category>(category, HttpStatus.OK);
	}

}
