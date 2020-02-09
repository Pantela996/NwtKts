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
import java.util.Optional;

import com.project.master.domain.*;
import com.project.master.dto.CategoryDTO;
import com.project.master.dto.EventDTO;
import com.project.master.exception.DataException;
import com.project.master.repository.*;
import com.project.master.service.EventService;
import com.project.master.service.HallService;
import com.project.master.service.LocationServiceImpl;
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
public class HallServiceUnitTests {
    @Autowired
    private HallService hallService;

    @MockBean
    private HallRepository mockedHallRepository;

    @MockBean
    private LocationRepository mockedLocationRepository;

    EventLocation validLocation = new EventLocation(1L, "Some name", "Some city");

    @Test
    public void whenThereIsNoHallReturnEmptyList_findAll() throws Exception{
        when(mockedHallRepository.findAll()).thenReturn(new ArrayList<Hall>());
        ArrayList<Hall> result = hallService.getAll();

        assertEquals(0, result.size());
    }

    @Test
    public void whenThereAreTwoHallsReturnTheList_findAll() throws Exception{
        ArrayList<Hall> halls = new ArrayList<Hall>();
        Hall hall1 = new Hall(1L, 3, 4, validLocation, null);
        Hall hall2 = new Hall(2L, 5, 5, validLocation, null);
        halls.add(hall1);
        halls.add(hall2);
        when(mockedHallRepository.findAll()).thenReturn(halls);
        ArrayList<Hall> result = hallService.getAll();

        assertEquals(2, result.size());
    }

    @Test(expected = DataException.class)
    public void whenThereIsNoHallWithGivenIdThrowException_findById() throws Exception{
        when(mockedHallRepository.findById(-1L)).thenReturn(Optional.empty());
        hallService.findById(-1L);
    }

    @Test
    public void whenThereIsHallWithGivenIdReturnIt_findById() throws Exception{
        Hall hall = new Hall(1L, 3, 4, validLocation, null);
        when(mockedHallRepository.findById(1L)).thenReturn(Optional.of(hall));
        Hall result = hallService.findById(1L);

        assertEquals(hall, result);
    }

    @Test(expected = DataException.class)
    public void whenThereIsNoLocationWithGivenNameThrowException_findByLocation() throws Exception{
        when(mockedLocationRepository.findByName("Some name")).thenReturn(Optional.empty());
        hallService.findByLocation("Some name");
    }

    @Test
    public void whenThereIsNoHallOnGivenLocationReturnEmptyList_findByLocation() throws Exception{
        EventLocation location = new EventLocation(1L, "Some name", "Some city");
        location.setHallList(new ArrayList<Hall>());
        when(mockedLocationRepository.findByName("Some name")).thenReturn(Optional.of(location));
        ArrayList<Hall> result = (ArrayList<Hall>)hallService.findByLocation("Some name");

        assertEquals(0, result.size());
    }

    @Test
    public void whenThereAreTwoHallsOnGivenLocationReturnTheList_findByLocation() throws Exception{
        EventLocation location = new EventLocation(1L, "Some name", "Some city");
        ArrayList<Hall> halls = new ArrayList<Hall>();
        Hall hall1 = new Hall(1L, 3, 4, location, null);
        Hall hall2 = new Hall(2L, 5, 5, location, null);
        halls.add(hall1);
        halls.add(hall2);
        location.setHallList(halls);
        when(mockedLocationRepository.findByName("Some name")).thenReturn(Optional.of(location));
        ArrayList<Hall> result = (ArrayList<Hall>)hallService.findByLocation("Some name");
        
        System.out.println();
        assertEquals(2, result.size());
    }
    
    
}
