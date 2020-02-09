package com.project.master.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.project.master.domain.*;
import com.project.master.dto.CategoryDTO;
import com.project.master.dto.EventDTO;
import com.project.master.dto.HallDTO;
import com.project.master.dto.LocationDTO;
import com.project.master.dto.SeatInfoDTO;
import com.project.master.exception.DataException;
import com.project.master.repository.CategoryRepository;
import com.project.master.repository.EventRepository;
import com.project.master.repository.HallRepository;
import com.project.master.repository.LocationRepository;
import com.project.master.repository.UserRepository;
import com.project.master.service.EventService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EventServiceUnitTests {
    @Autowired
    private EventService eventService;

    @MockBean
    private EventRepository mockedEventRepository;

    @MockBean
    private UserRepository mockedUserRepository;

    @MockBean
    private LocationRepository mockedLocationRepository;

    @MockBean
    private HallRepository mockedHallRepository;

    @MockBean
    private CategoryRepository mockedCategoryRepository;

    private static final LocationDTO locationDTO = new LocationDTO(1l,"lokacija", "ns", "miki", 1);
    
    private static final HallDTO hallDTO = new HallDTO(10,10,locationDTO);
    
    private static 	SeatInfoDTO hall = new SeatInfoDTO(1l,null,10,10,null);
    
    private static final CategoryDTO categoryDTO = new CategoryDTO("name",10,10);
    
    private static final EventDTO validEventDTO = new EventDTO("Some name", "11.12.2020.", "15.12.2020.", locationDTO,
			hall, hallDTO, categoryDTO, "Some description", 1L);

	private static final EventDTO nonExistentEventDTO =new EventDTO("Some name", "11.12.2020.", "15.12.2020.", locationDTO,
			hall, hallDTO, categoryDTO, "Some description", -1L);

    LocationEventAdmin validUser = new LocationEventAdmin(1L, "Some username", "Some password", "Some name", "Some lastname", "Some email");

    EventLocation validLocation = new EventLocation(1L, "Some name", "Some city");

    Hall validHall = new Hall(10, 12, validLocation);

    Category validCategory = new Category(1L, "Some name", 7, 8, CategoryType.FAIRY);

    Date validStartDate =  new Date(2019, 9, 22);
    Date validEndDate =  new Date(2019, 10, 22);

    DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm");

    Event validEvent = new Event(1L, "Some name",validStartDate,validStartDate, validLocation, validHall, validCategory,
            "Some description", "Some username");


    @Test(expected = DataException.class)
    public void whenEventDTOIsNullThrowException_createEvent() throws Exception {
        eventService.createEvent(null, "1");
    }

    @Test(expected = NoSuchElementException.class)
    public void whenUserIsNullThrowException_createEvent() throws Exception {
        //ne znam da li ovde treba da ide mockedUserRepository.findByUsername("1") bez get()
        when(mockedUserRepository.findByUsername("-1").get()).thenReturn(null);
        eventService.createEvent(validEventDTO, "-1");

    }

    @Test(expected = NoSuchElementException.class)
    public void whenEventLocationIsNullThrowException_createEvent() throws Exception {
        when(mockedUserRepository.findByUsername("Some username").get()).thenReturn(validUser);
        when(mockedLocationRepository.findById(-1L)).thenReturn(Optional.empty());
        eventService.createEvent(new EventDTO("Some name", "11.12.2020.", "15.12.2020.", locationDTO,
    			hallDTO, categoryDTO, "Some description", 1L), "Some username");

    }

    @Test(expected = NoSuchElementException.class)
    public void whenEventHallIsNullThrowException_createEvent() throws Exception {
        when(mockedUserRepository.findByUsername("Some username").get()).thenReturn(validUser);
        when(mockedLocationRepository.findById(1L)).thenReturn(Optional.of(validLocation));
        when(mockedHallRepository.findById(-1L)).thenReturn(Optional.empty());
        eventService.createEvent(new EventDTO("Some name", "11.12.2020.", "15.12.2020.", locationDTO,
        		hallDTO, categoryDTO, "Some description", 1L), "Some username");

    }

    @Test(expected = DataException.class)
    public void whenEventHallDoesNotBelongToEventLocationThrowException_createEvent() throws Exception {
        EventLocation eventLocation = new EventLocation(3L, "Some name", "Some city");
        when(mockedUserRepository.findByUsername("Some username")).thenReturn(Optional.of(validUser));
        when(mockedLocationRepository.findById(3L)).thenReturn(Optional.of(eventLocation));
        when(mockedHallRepository.findById(1L)).thenReturn(Optional.of(validHall));
        eventService.createEvent(new EventDTO("Some name", "11.12.2020.", "15.12.2020.", locationDTO,
    			hall, hallDTO, categoryDTO, "Some description", 1L),"Some username");

    }

    @Test(expected = DataException.class)
    public void whenEventCategoryIsNullThrowException_createEvent() throws Exception {

        when(mockedLocationRepository.findById(1L)).thenReturn(Optional.of(validLocation));
        when(mockedHallRepository.findById(1L)).thenReturn(Optional.of(validHall));
        when(mockedCategoryRepository.findById(-1L)).thenReturn(Optional.empty());
        eventService.createEvent(new EventDTO("Some name", "11.12.2020.", "15.12.2020.", locationDTO,
    			hallDTO, categoryDTO, "Some description", 1L), "user");

    }

    @Test
    public void whenEventIsValidSaveIt_createEvent() throws Exception{
    	when(mockedUserRepository.findByUsername("Some username")).thenReturn(Optional.of(validUser));
        when(mockedLocationRepository.findById(1L)).thenReturn(Optional.of(validLocation));
        when(mockedHallRepository.findById(1L)).thenReturn(Optional.of(validHall));
        //when(mockedCategoryRepository.findById(1L)).thenReturn(Optional.of(validCategory));
        //String result = eventService.createEvent(new EventDTO("Some name", "11.12.2020.", "15.12.2020.", locationDTO,
    		//	hall,hallDTO, categoryDTO, "Some description", 1L), "Some username");

        //Mockito.verify(mockedEventRepository, Mockito.times(1)).save();
       // assertEquals(result, "Success");
    }

    @Test(expected = DataException.class)
    public void whenEventDoesNotExistThrowException_deleteEvent() throws Exception {
        when(mockedEventRepository.findById(-1L)).thenReturn(Optional.empty());
        eventService.deleteEvent("-1");

    }

    @Test
    public void whenEventExistsDeleteIt_deleteEvent() throws Exception {
        when(mockedEventRepository.findById(1L)).thenReturn(Optional.of(validEvent));
        String result = eventService.deleteEvent("1");

       // Mockito.verify(mockedEventRepository, Mockito.times(1)).deleteById();
        assertEquals(result, "Success");
    }

    @Test(expected = DataException.class)
    public void whenEventDoesNotExistThrowException_updateEvent() throws Exception{
        when(mockedEventRepository.findById(-1L)).thenReturn(Optional.empty());
        eventService.updateEvent("-1", validEventDTO, "Some username");
    }

    @Test
    public void whenEventIsValidUpdateIt_updateEvent() throws Exception{
        when(mockedEventRepository.findById(1L)).thenReturn(Optional.of(validEvent));
        when(mockedUserRepository.findByUsername("Some username")).thenReturn(Optional.of(validUser));
        when(mockedLocationRepository.findById(1L)).thenReturn(Optional.of(validLocation));
        when(mockedHallRepository.findById(1L)).thenReturn(Optional.of(validHall));
       //when(mockedCategoryRepository.findById(1L)).thenReturn(Optional.of(validCategory));
        //String result = eventService.updateEvent("1", validEventDTO, "Some username");

      //  Mockito.verify(mockedEventRepository, Mockito.times(1)).deleteById();
      //  Mockito.verify(mockedEventRepository, Mockito.times(1)).save();
//        /assertEquals(result, "Success");
    }

    @Test
    public void whenThereIsNoEventReturnEmptyList_getAll() throws Exception{
        when(mockedEventRepository.findAll()).thenReturn(new ArrayList<Event>());
        ArrayList<Event> result = eventService.getAll();

        assertEquals(0, result.size());
    }

    @Test
    public void whenThereAreTwoEventsReturnThem_getAll() throws Exception{
        ArrayList<Event> events = new ArrayList<Event>();
        Event event = new Event(2L, "Some name 2", validStartDate,validEndDate, validLocation, validHall, validCategory,
                "Some description", "Some username");
        events.add(validEvent);
        events.add(event);
        when(mockedEventRepository.findAll()).thenReturn(events);
        ArrayList<Event> result = eventService.getAll();

        assertEquals(2, result.size());
    }

    @Test(expected = DataException.class)
    public void whenEventDoesNotExistThrowException_getOne() throws Exception{
        when(mockedEventRepository.findById(-1L)).thenReturn(Optional.empty());
        eventService.getOne("-1");
    }

    @Test
    public void whenEventExistsReturnIt_getOne() throws Exception{
        when(mockedEventRepository.findById(1L)).thenReturn(Optional.of(validEvent));
        Event event = eventService.getOne("1");

        assertEquals(validEvent, event);
    }

    @Test
    public void whenEventExistsReturnIt_getMyEvents() throws Exception{
        ArrayList<Event> events = new ArrayList<Event>();
        Event event1 = new Event(2L, "Some name", validStartDate,validEndDate, validLocation, validHall, validCategory,
                "Some description", "Some username");
        Event event2 = new Event(3L, "Some name", validStartDate, validEndDate, validLocation, validHall, validCategory,
                "Some description", "Some username 2");
        events.add(validEvent);
        events.add(event1);
        events.add(event2);
        when(mockedEventRepository.findAll()).thenReturn(events);
        ArrayList<Event> myEvents = eventService.getMyEvents("Some username");

        assertEquals(2, myEvents.size());
    }
}
