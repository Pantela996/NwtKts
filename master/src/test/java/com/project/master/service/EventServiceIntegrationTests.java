package com.project.master.service;

import static org.junit.Assert.*;

import com.project.master.domain.Category;
import com.project.master.domain.Event;
import com.project.master.domain.Seat;
import com.project.master.dto.*;
import com.project.master.exception.DataException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.ArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application.properties")
@Transactional
public class EventServiceIntegrationTests {
    @Autowired
    private EventService eventService;

    @Test
    public void whenEventDTOIsNull_createEvent(){
        try{
            eventService.createEvent(null, "Some username");
            assertTrue(false);
        } catch (DataException e) {
            e.printStackTrace();
            assertTrue(true);
        }
    }

    @Test
    public void whenUserDoesNotExist_createEvent() {
        try {
            eventService.createEvent(new EventDTO(), "User does not exist");
            assertTrue(false);
        } catch (DataException e) {
            e.printStackTrace();
            assertTrue(true);
        }
    }

    @Test
    public void whenLocationDoesNotExist_createEvent() {
        EventDTO eventDTO = new EventDTO();
        LocationDTO locationDTO = new LocationDTO();
        locationDTO.setId(-1L);
        eventDTO.setEvent_location(locationDTO);
        try {
            eventService.createEvent(new EventDTO(), "Existing user");
            assertTrue(false);
        } catch (DataException e) {
            e.printStackTrace();
            assertTrue(true);
        }
    }

    @Test
    public void whenHallDoesNotExist_createEvent() {
        EventDTO eventDTO = new EventDTO();
        LocationDTO locationDTO = new LocationDTO();
        HallDTO hallDTO = new HallDTO();
        locationDTO.setId(1L);
        hallDTO.setId(-1L);
        eventDTO.setEvent_location(locationDTO);
        eventDTO.setSeatInfo(hallDTO);
        try {
            eventService.createEvent(eventDTO, "Existing user");
            assertTrue(false);
        } catch (DataException e) {
            e.printStackTrace();
            assertTrue(true);
        }
    }

    @Test
    public void whenHallIsNotAtGivenLocation_createEvent() {
        EventDTO eventDTO = new EventDTO();
        LocationDTO locationDTO = new LocationDTO();
        HallDTO hallDTO = new HallDTO();
        locationDTO.setId(1L);
        hallDTO.setId(2L);
        eventDTO.setEvent_location(locationDTO);
        eventDTO.setSeatInfo(hallDTO);
        try {
            eventService.createEvent(eventDTO, "Existing user");
            assertTrue(false);
        } catch (DataException e) {
            e.printStackTrace();
            assertTrue(true);
        }
    }

    //kategorija ce postojati zato nije pisan test za to posto se u servisu prosledjuje 1 (tako da pretpostavljam da ce u bazi biti kategorija sa id-jem 1)

    @Test
    @Rollback
    public void whenDataIsValid_createEvent() {
        EventDTO eventDTO = new EventDTO();
        LocationDTO locationDTO = new LocationDTO();
        HallDTO hallDTO = new HallDTO();
        locationDTO.setId(1L);
        hallDTO.setId(1L);
        eventDTO.setEvent_location(locationDTO);
        eventDTO.setSeatInfo(hallDTO);
        eventDTO.setName("Some event name");
        eventDTO.setDateFrom("2020-02-10 16:00");
        eventDTO.setDateTo("2020-02-10 18:00");
        eventDTO.setDescription("Some event description");
        try {
            String result = eventService.createEvent(eventDTO, "Existing user");
            assertEquals("Success", result);
            assertTrue(true);
        } catch (DataException e) {
            e.printStackTrace();
            assertFalse(false);
        }
    }

    @Test
    public void whenEventDoesNotExist_deleteEvent() {
        try {
            eventService.deleteEvent("-1");
            assertTrue(false);
        } catch (DataException e) {
            e.printStackTrace();
            assertTrue(true);
        }
    }

    @Test
    @Rollback
    public void whenEventExists_deleteEvent() {
        try {
            String result = eventService.deleteEvent("5");
            assertEquals("Success", result);
            assertTrue(true);
        } catch (DataException e) {
            e.printStackTrace();
            assertFalse(false);
        }
    }

    @Test
    public void whenEventDoesNotExist_updateEvent() {
        try {
            eventService.updateEvent("-1", new EventDTO(), "Existing user");
            assertTrue(false);
        } catch (DataException e) {
            e.printStackTrace();
            assertFalse(false);
        }
    }

    @Test
    @Rollback
    public void whenEventExists_updateEvent() {
        EventDTO eventDTO = new EventDTO();
        LocationDTO locationDTO = new LocationDTO();
        HallDTO hallDTO = new HallDTO();
        locationDTO.setId(1L);
        hallDTO.setId(1L);
        eventDTO.setEvent_location(locationDTO);
        eventDTO.setSeatInfo(hallDTO);
        eventDTO.setName("Some new event name");
        eventDTO.setDateFrom("2020-02-10 16:00");
        eventDTO.setDateTo("2020-02-10 18:00");
        eventDTO.setDescription("Some new event description");
        try {
            String result = eventService.updateEvent("6", eventDTO, "Existing user");
            assertEquals("Success", result);
            assertTrue(true);
        } catch (DataException e) {
            e.printStackTrace();
            assertFalse(false);
        }
    }

    //getAll nije pisano posto zavisi od toga koliko je eventova uneto u bazu, ali ukoliko bi se obrisali svi eventovi mogla
    //bi samo provera za praznu listu, i suprotno provera za odredjeni broj elemenata u listi

    @Test
    public void whenEventDoesNotExist_getOne() {
        try {
            eventService.getOne("-1");
            assertTrue(false);
        } catch (DataException e) {
            e.printStackTrace();
            assertTrue(true);
        }
    }

    @Test
    public void whenEventExists_getOne() {
        try {
            Event event = eventService.getOne("7");
            assertEquals("Check this description", event.getDescription());
            assertTrue(true);
        } catch (DataException e) {
            e.printStackTrace();
            assertFalse(false);
        }
    }

    @Test
    public void whenThereAreZeroOfMyEvents_getMyEvents() {
        ArrayList<Event> events = eventService.getMyEvents("User x");
        assertEquals(0, events.size());
    }
}
