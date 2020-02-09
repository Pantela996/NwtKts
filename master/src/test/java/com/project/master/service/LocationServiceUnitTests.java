package com.project.master.service;

import com.project.master.domain.*;
import com.project.master.dto.CategoryDTO;
import com.project.master.dto.LocationDTO;
import com.project.master.dto.UserDTO;
import com.project.master.exception.DataException;
import com.project.master.repository.AuthorityRepository;
import com.project.master.repository.CategoryRepository;
import com.project.master.repository.EventRepository;
import com.project.master.repository.LocationRepository;
import com.project.master.repository.UserRepository;
import com.project.master.service.LocationService;
import com.project.master.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LocationServiceUnitTests {
    @Autowired
    private LocationService locationService;

    @MockBean
    private UserRepository mockedUserRepository;

    @MockBean
    private LocationRepository mockedLocationRepository;


    @MockBean
    private CategoryRepository mockedCategoryRepository;


    @MockBean
    private EventRepository mockedEventRepository;

    EventLocation validLocation = new EventLocation(1L, "Some name", "Some city");

    LocationEventAdmin validUser = new LocationEventAdmin(1L, "Some username", "Some password", "Some name", "Some lastname", "Some email");

    @Test(expected = DataException.class)
    public void whenLocationWithSameNameExistsThrowException_createLocation() throws Exception {
        when(mockedLocationRepository.findByName("name")).thenReturn(Optional.of(validLocation));
        locationService.createLocation("name", "Some city", "name", 3);
    }

    @Test
    public void whenDataIsValidCreateLocation_createLocation() throws Exception {
        when(mockedLocationRepository.findByName("name")).thenReturn(Optional.empty());
        //String result = locationService.createLocation("name", "Some city", "name", 3);

        // assertEquals("Success", result);
    }

    @Test(expected = DataException.class)
    public void whenLocationDoesNotExistsThrowException_deleteLocation() throws Exception {
        when(mockedLocationRepository.findById(-1L)).thenReturn(Optional.empty());
        locationService.deleteLocation("-1");
    }

    @Test
    public void whenLocationExistsDeleteIt_deleteLocation() throws Exception {
        when(mockedLocationRepository.findById(1L)).thenReturn(Optional.of(validLocation));
        String result = locationService.deleteLocation("1");

        Mockito.verify(mockedLocationRepository, Mockito.times(1)).deleteById(1L);
        assertEquals("Success", result);
    }

    @Test(expected = DataException.class)
    public void whenLocationDoesNotExistsThrowException_updateLocation() throws Exception {
        when(mockedLocationRepository.findById(-1L)).thenReturn(Optional.empty());
        locationService.updateLocation("-1", new LocationDTO(), "Some username");
    }

    @Test
    public void whenLocationExistsUpdateIt_updateLocation() throws Exception {
        when(mockedLocationRepository.findById(1L)).thenReturn(Optional.of(validLocation));
        LocationDTO locationDTO = new LocationDTO("Some new name", "Some city", "Some username", 1L);
        String result = locationService.updateLocation("1", locationDTO, "Some username");

        assertEquals("Success", result);
    }

    @Test
    public void whenLocationListIsEmpty_getAll() throws Exception {
        ArrayList<EventLocation> locations = new ArrayList<EventLocation>();
        when(mockedLocationRepository.findAll()).thenReturn(locations);
        ArrayList<EventLocation> result = locationService.getAll();

        assertEquals(0, result.size());
    }

    @Test
    public void whenLocationListHasTwoLocations_getAll() throws Exception {
        EventLocation eventLocation = new EventLocation(1L, "Some name", "Some city");
        EventLocation eventLocation2 = new EventLocation(2L, "Some name 2", "Some city");
        ArrayList<EventLocation> locations = new ArrayList<EventLocation>();
        locations.add(eventLocation);
        locations.add(eventLocation2);
        when(mockedLocationRepository.findAll()).thenReturn(locations);
        ArrayList<EventLocation> result = locationService.getAll();

        assertEquals(2, result.size());
    }


    @Test(expected = DataException.class)
    public void whenLocationDoesNotExistThrowException_getOne() throws Exception{
        when(mockedLocationRepository.findById(-1L)).thenReturn(Optional.empty());
        locationService.getOne("-1");
    }

    @Test
    public void whenLocationExistsReturnIt_getOne() throws Exception{
        when(mockedLocationRepository.findById(1L)).thenReturn(Optional.of(validLocation));
        EventLocation result = locationService.getOne("1");

        assertEquals(validLocation, result);
    }

    @Test
    public void whenThereAreNoMyLocationsReturnEmptyList_getMyLocations() throws Exception{
        ArrayList<EventLocation> locations = new ArrayList<EventLocation>();
        EventLocation location = new EventLocation("Some name", "Some city", "Other username", 3);
        locations.add(location);

        when(mockedLocationRepository.findAll()).thenReturn(locations);
        ArrayList<EventLocation> result = locationService.getMyLocations("Some username");

        assertEquals(0, result.size());
    }

    @Test
    public void whenThereIsOneLocationInMyLocationsReturnTheList_getMyLocations() throws Exception{
        ArrayList<EventLocation> locations = new ArrayList<EventLocation>();
        EventLocation location1 = new EventLocation("Some name", "Some city", "Some username", 3);
        EventLocation location2 = new EventLocation("Some name 2", "Some city", "Other username", 3);
        locations.add(location1);
        locations.add(location2);
        when(mockedLocationRepository.findAll()).thenReturn(locations);
        ArrayList<EventLocation> result = locationService.getMyLocations("Some username");

        assertEquals(1, result.size());
    }

    @Test(expected = DataException.class)
    public void whenEventCategoryNameAlreadyExistsThrowException_createCategory() throws Exception{
        Category category = new Category(1L, "Some name", 3, 4, CategoryType.FAIRY);
        when(mockedCategoryRepository.findByName("Some name")).thenReturn(Optional.of(category));
        locationService.createCategory("Some name", 3, 4);
    }

    @Test
    public void whenEventCategoryNameDoesNotExistCreateCategory_createCategory() throws Exception{
        when(mockedCategoryRepository.findByName("Some name")).thenReturn(Optional.empty());
        String result = locationService.createCategory("Some name", 3, 4);

        assertEquals("Success", result);
    }

    @Test
    public void whenThereIsNoCategoryReturnEmptyList_getAllCategories() throws Exception{
        when(mockedCategoryRepository.findAll()).thenReturn(new ArrayList<Category>());
        ArrayList<Category> result = locationService.getAllCategories();

        assertEquals(0, result.size());
    }

    @Test
    public void whenThereAreTwoCategoriesReturnTheList_getAllCategories() throws Exception{
        ArrayList<Category> categories = new ArrayList<Category>();
        Category category1 = new Category(1L, "Some name", 3, 4, CategoryType.FAIRY);
        Category category2 = new Category(2L, "Some name 2", 3, 4, CategoryType.FAIRY);
        categories.add(category1);
        categories.add(category2);
        when(mockedCategoryRepository.findAll()).thenReturn(categories);
        ArrayList<Category> result = locationService.getAllCategories();

        assertEquals(2, result.size());
    }

    @Test(expected = DataException.class)
    public void whenCategoryDoesNotExistThrowException_updateCategory() throws Exception{
        when(mockedCategoryRepository.findById(-1L)).thenReturn(Optional.empty());
        CategoryDTO categoryDTO = new CategoryDTO(-1L, "Some name", 3, 4, CategoryType.FAIRY);
        locationService.updateCategory(categoryDTO);
    }

    @Test
    public void whenCategoryExistsUpdateIt_updateCategory() throws Exception{
        Category category = new Category(1L, "Some name", 3, 4, CategoryType.MUSIC);
        when(mockedCategoryRepository.findById(1L)).thenReturn(Optional.of(category));
        CategoryDTO categoryDTO = new CategoryDTO(1L, "Some new name", 5, 5, CategoryType.FAIRY);
        Category result = locationService.updateCategory(categoryDTO);

        assertEquals("Some new name", result.getName());
    }
}




