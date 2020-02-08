package com.project.master.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.project.master.domain.*;
import com.project.master.repository.HallRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.master.dto.CategoryDTO;
import com.project.master.dto.LocationDTO;
import com.project.master.exception.DataException;
import com.project.master.repository.CategoryRepository;
import com.project.master.repository.LocationRepository;

@Service
public class LocationServiceImpl implements LocationService {

	@Autowired
	private LocationRepository locationRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private HallRepository hallRepository;

	@Override
	public String createLocation(String locationName, String locationCity, String user, int numberOfHalls) throws DataException {
		Optional<EventLocation> location  = locationRepository.findByName(locationName);
		System.out.println("Usao sam u create");
		if(!location.isPresent()) {
			System.out.println("Usao sam u createLocation");
			EventLocation result  = new EventLocation(locationName, locationCity, user, 1);
			List<Hall> halls = new ArrayList<Hall>();
			for (int i=0; i<numberOfHalls; ++i){
				Hall h = new Hall(10,10, result);
				halls.add(h);
				hallRepository.save(h);

			}
			result.setHallList(halls);
			result.setNumberOfHalls(halls.size());
			System.out.println(halls.size());

			locationRepository.save(result);
		}else {
			return "Location already exists";
		}
		
		return "Success";

	}

	public String deleteLocation(String location_id) {
		try {
			Optional<EventLocation> oe = locationRepository.findById(Long.valueOf(location_id));
			if (oe.isPresent()) {
				locationRepository.deleteById(Long.valueOf(location_id));
			} else {
				throw new DataException("Location does not exist");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "Success";
	}

	public String updateLocation(String location_id, LocationDTO locationDTO, String currentUser) {
		try {
			Optional<EventLocation> oe = locationRepository.findById(Long.valueOf(location_id));
			if (oe.isPresent()) {
				locationRepository.deleteById(Long.valueOf(location_id));
				// this.createLocation(locationDTO);
			} else {
				throw new DataException("Location does not exist");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Success";
	}

	public ArrayList<EventLocation> getAll() {
		System.out.println("Usao sam u servis");
		ArrayList<EventLocation> locations = (ArrayList<EventLocation>) locationRepository.findAll();
		return locations;
	}

	public EventLocation getOne(String location_id) {
		EventLocation location = null;
		try {
			Optional<EventLocation> oe = locationRepository.findById(Long.valueOf(location_id));
			if (oe.isPresent()) {
				location = oe.get();
			} else {
				throw new DataException("Event does not exist");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return location;
	}

	public ArrayList<EventLocation> getMyLocations(String name) {
		ArrayList<EventLocation> myLocations = new ArrayList<EventLocation>();
		ArrayList<EventLocation> locations = (ArrayList<EventLocation>) locationRepository.findAll();
		for (EventLocation e : locations) {
			if (e.getUser().equalsIgnoreCase(name)) {
				myLocations.add(e);
			}
		}
		return myLocations;
	}

	public String createCategory(String name, int requiredRows, int requiredColumns) throws DataException {
		Optional<Category> category  = categoryRepository.findByName(name);
		
		System.out.println("CAME HERE");
		
		if(!category.isPresent()) {
			categoryRepository.save(new Category(name, requiredRows, requiredColumns, CategoryType.FAIRY));
			return "Success";
		}else {
			throw new DataException("Category name alredy exists");
		}
		
	}

	public ArrayList<Category> getAllCategories() {
		ArrayList<Category> categories = (ArrayList<Category>)categoryRepository.findAll();
		return categories;
	}

	public Category updateCategory(CategoryDTO categoryDTO) {
		Category c = new Category();
		try {
			Optional<Category> ce = categoryRepository.findById(Long.valueOf(categoryDTO.getId()));
			if (ce.isPresent()) {
				c = ce.get();
				c.setName(categoryDTO.getName());
				c.setCategoryType(categoryDTO.getCategoryType());
				c.setRequiredRows(categoryDTO.getRequiredRows());
				c.setRequiredColumns(categoryDTO.getRequiredColumns());
				// this.createLocation(locationDTO);
				categoryRepository.save(c);
			} else {
				throw new DataException("Location does not exist");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return c;
	}

}
