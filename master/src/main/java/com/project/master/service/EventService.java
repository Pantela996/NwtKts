package com.project.master.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import com.project.master.domain.Category;
import com.project.master.domain.Event;
import com.project.master.domain.EventLocation;
import com.project.master.domain.Frame;
import com.project.master.domain.Hall;
import com.project.master.domain.LocationEventAdmin;
import com.project.master.dto.EventDTO;
import com.project.master.exception.DataException;
import com.project.master.repository.CategoryRepository;
import com.project.master.repository.EventRepository;
import com.project.master.repository.FrameRepository;
import com.project.master.repository.HallRepository;
import com.project.master.repository.LocationRepository;
import com.project.master.repository.UserRepository;

@Service
public class EventService {

	@Autowired
	private EventRepository eventRepository;
	
	@Autowired
	private LocationRepository locationRepository;
	
	@Autowired
	private HallRepository hallRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private FrameRepository frameRepository;
	
	@Autowired 
	UserRepository userRepository;
	
	private static String uploadDirectory = System.getProperty("user.dir") + "/src/main/resources/uploads";

	public String createEvent(EventDTO eventDTO, String currentUser) {
		try {
			
			if (eventDTO == null) throw new DataException("Event is null");
			LocationEventAdmin lea = (LocationEventAdmin) userRepository.findByUsername(currentUser).get();
			if (lea == null) throw new DataException("User does not exist");
			
			EventLocation el;
			Hall hall;
			Category category;
			
			Optional<EventLocation> ol = locationRepository.findById(Long.valueOf(eventDTO.getEvent_location()));

			if (ol.isPresent()) {
				el = ol.get();
			}else {
				throw new DataException("Location does not exist");
			}
			Optional<Hall> ohall = hallRepository.findById(Long.valueOf(eventDTO.getLocation_hall()));
			if (ohall.isPresent()) {
				hall = ohall.get();
			}else {
				throw new DataException("Hall does not exist");
			}
			if (!(hall.getLocation().getId().equals(el.getId()))) {
				throw new DataException("Hall is not at this location!");
			}
			
			
			Optional<Category> ocategory = categoryRepository.findById(Long.valueOf(eventDTO.getEvent_category()));
			if (ocategory.isPresent()) {
				category = ocategory.get();
			}else {
				throw new DataException("Location does not exist");
			}
			if (category == null )throw new DataException("Category does not exist");
			Event event = new Event(eventDTO.getName(), Date.valueOf(eventDTO.getDate_from()), Date.valueOf(eventDTO.getDate_to()), el, hall.getId(), category.getId(), eventDTO.getDescription(), lea.getUsername());
			eventRepository.save(event);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "Success";
	}

	public String deleteEvent(String event_id) {
		try {
			Optional<Event> oe = eventRepository.findById(Long.valueOf(event_id));
			if(oe.isPresent()) {
				eventRepository.deleteById(Long.valueOf(event_id));
			}else {
				throw new DataException("Event does not exist");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
		return "Success";
	}

	public String updateEvent(String event_id, EventDTO eventDTO,String currentUser) {
		try {
			Optional<Event> oe = eventRepository.findById(Long.valueOf(event_id));
			if(oe.isPresent()) {
				eventRepository.deleteById(Long.valueOf(event_id));
				this.createEvent(eventDTO, currentUser);
			}else {
				throw new DataException("Event does not exist");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return "Success";
	}

	public ArrayList<Event> getAll() {
		ArrayList<Event> events = (ArrayList<Event>)eventRepository.findAll();
		System.out.println(events.size());
		return events;
	}

	public Event getOne(String event_id) {
		Event event = null;
		try {
			Optional<Event> oe = eventRepository.findById(Long.valueOf(event_id));
			if(oe.isPresent()) {
				event = oe.get();
			}else {
				throw new DataException("Event does not exist");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return event;
	}

	public ArrayList<Event> getMyEvents(String name) {
		ArrayList<Event> myEvents = new ArrayList<Event>();
		ArrayList<Event> events = (ArrayList<Event>)eventRepository.findAll();
		for (Event e: events) {
			if(e.getUser().equalsIgnoreCase(name)) {
				myEvents.add(e);
			}
		}
		return myEvents;
	}

	public boolean saveImage(MultipartFile[] files, Long id) throws FileNotFoundException {
		Optional<Event> optEvent = eventRepository.findById(id);
		
		StringBuilder sb = new StringBuilder();
		//find event and save img
		if(optEvent.isPresent()) {
			Event event = optEvent.get();
			for (MultipartFile file : files) {
				Path fileNameAndPath = Paths.get(uploadDirectory, file.getOriginalFilename());
				try {
					Files.write(fileNameAndPath, file.getBytes());
					Frame f = new Frame();
					sb.append("/uploads/");
					sb.append(String.valueOf(id));
					sb.append("/");
					sb.append(file.getOriginalFilename());
					System.out.println(sb.toString());
					f.setUrl(sb.toString());
					event.getFrames().add(f);
					frameRepository.save(f);
					eventRepository.save(event);
					
				} catch (IOException e) {
					e.printStackTrace();
					throw new FileNotFoundException();
				}

			}
		}else {
			return false;
		}
		
		return true;
		
	}

	public List<byte[]> getFrames(String event_id) throws IOException, DataException {
		List<byte[]> frames = new ArrayList<byte[]>();
		byte[] frame;
		Optional<Event> oEvent = eventRepository.findById(Long.valueOf(event_id));
		if(oEvent.isPresent()) {
			
			Event event = oEvent.get();
			for(Frame f : event.getFrames()) {
				System.out.println(f.getUrl());
				InputStream in = getClass().getResourceAsStream(f.getUrl());
				frame = StreamUtils.copyToByteArray(in);
				System.out.println(frame);
				frames.add(frame);
			}
		}else {
			throw new DataException();
		}
		
		return frames;
	}

}
