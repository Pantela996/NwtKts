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
	public String createLocation(LocationDTO locationDTO,String user)
			throws DataException {
	
		
		if (locationDTO.getName() == null){
			throw new DataException("Name is null");
		}
		if (locationDTO.getLocationCity() == null){
			System.out.println("LOCATION IS NULL");
			throw new DataException("Location is null");
		}
		
		if(user == null) {
			throw new DataException("User is null");
		}
		
		Optional<EventLocation> location = locationRepository.findByName(locationDTO.getName());
		
		int size = 0;
		int j = 0;
		System.out.println("Usao sam u create");
		if (location.isPresent()) {
			throw new DataException("Already exists");
		}
		System.out.println("Usao sam u createLocation");
		EventLocation result = new EventLocation(locationDTO.getName(), locationDTO.getLocationCity(), user, locationDTO.getNumberOfHalls());
		List<Hall> halls = new ArrayList<Hall>();
		for (int i = 0; i < locationDTO.getNumberOfHalls(); ++i) {
			if(j == 0) {
				size = 10;
				j++;
			}else if(j==1) {
				size = 20;
				j++;
			}else {
				size = 30;
				j = 0;
			}
			Hall h = new Hall(size, size, result);
			halls.add(h);
			hallRepository.save(h);

		}
		result.setHallList(halls);
		result.setNumberOfHalls(halls.size());
		System.out.println(halls.size());

		locationRepository.save(result);

		return "Success";

	}

	
	public String deleteLocation(String location_id) throws DataException {
		Optional<EventLocation> oe = locationRepository.findById(Long.valueOf(location_id));
		if (!oe.isPresent()) {
			throw new DataException("Location does not exist");

		}
		locationRepository.deleteById(Long.valueOf(location_id));

		return "Success";
	}

	public String updateLocation(String location_id, LocationDTO locationDTO, String currentUser) throws DataException {
		Optional<EventLocation> oe = locationRepository.findById(Long.valueOf(location_id));
		if (!oe.isPresent()) {
			throw new DataException("Location does not exist");
			// this.createLocation(locationDTO);
		}
		locationRepository.deleteById(Long.valueOf(location_id));

		return "Success";
	}

	public ArrayList<EventLocation> getAll() {
		System.out.println("Usao sam u servis");
		ArrayList<EventLocation> locations = (ArrayList<EventLocation>) locationRepository.findAll();
		return locations;
	}

	public EventLocation getOne(String location_id) throws DataException {
		EventLocation location = null;
		Optional<EventLocation> oe = locationRepository.findById(Long.valueOf(location_id));
		if (!oe.isPresent()) {

			throw new DataException("Event does not exist");
		}
		location = oe.get();

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
		Optional<Category> category = categoryRepository.findByName(name);

		System.out.println("CAME HERE");

		if (!category.isPresent()) {
			categoryRepository.save(new Category(name, requiredRows, requiredColumns, CategoryType.FAIRY));
			return "Success";
		} else {
			throw new DataException("Category name alredy exists");
		}

	}

	public ArrayList<Category> getAllCategories() {
		ArrayList<Category> categories = (ArrayList<Category>) categoryRepository.findAll();
		return categories;
	}

	public Category updateCategory(CategoryDTO categoryDTO) throws DataException {
		Category c = new Category();
		Optional<Category> ce = categoryRepository.findById(Long.valueOf(categoryDTO.getId()));
		if (!ce.isPresent()) {
			throw new DataException("Location does not exist");
		}
		
		c = ce.get();
		c.setName(categoryDTO.getName());
		c.setCategoryType(categoryDTO.getCategoryType());
		c.setRequiredRows(categoryDTO.getRequiredRows());
		c.setRequiredColumns(categoryDTO.getRequiredColumns());
		// this.createLocation(locationDTO);
		categoryRepository.save(c);

		return c;
	}

}
