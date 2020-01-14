package com.project.master.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.master.domain.Event;
import com.project.master.domain.EventLocation;
import com.project.master.dto.LocationDTO;
import com.project.master.exception.DataException;
import com.project.master.repository.LocationRepository;

@Service
public class LocationServiceImpl implements LocationService {

	@Autowired
	private LocationRepository locationRepository;

	@Override
	public String createLocation(String locationName, String locationCity, String user) throws DataException {
		System.out.println("here");
		Optional<EventLocation> location  = locationRepository.findByName(locationName);
		if (locationName == null)
			throw new DataException("Location is null");
		if((location == null)) {
			locationRepository.save(location.get());
		}else {
			return "False";
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

}