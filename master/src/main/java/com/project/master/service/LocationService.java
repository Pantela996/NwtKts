package com.project.master.service;

import java.util.ArrayList;

import com.project.master.domain.EventLocation;
import com.project.master.dto.LocationDTO;
import com.project.master.exception.DataException;

public interface LocationService {

	String createLocation(String name, String city, String user) throws DataException;

	String deleteLocation(String location_id);

	String updateLocation(String location_id, LocationDTO locationDTO, String name);

	ArrayList<EventLocation> getAll();

	EventLocation getOne(String location_id);

	ArrayList<EventLocation> getMyLocations(String name);

}
