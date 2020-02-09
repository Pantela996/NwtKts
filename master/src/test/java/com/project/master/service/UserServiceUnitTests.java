package com.project.master.service;

import com.project.master.domain.*;
import com.project.master.dto.UserDTO;
import com.project.master.exception.DataException;
import com.project.master.repository.AuthorityRepository;
import com.project.master.repository.EventRepository;
import com.project.master.repository.LocationRepository;
import com.project.master.repository.UserRepository;
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
public class UserServiceUnitTests {
    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository mockedUserRepository;

    @MockBean
    private LocationRepository mockedLocationRepository;

    @MockBean
    private EventRepository mockedEventRepository;

    @MockBean
    private AuthorityRepository mockedAuthorityRepository;


    Date validStartDate =  new Date(2019, 9, 22);
    Date validEndDate =  new Date(2019, 10, 22);

    UserDTO validUserDTO = new UserDTO("Some username", "Some password", "Some email", "Some name", "Some lastname",
    		validStartDate);

    EventLocation validLocation = new EventLocation(1L, "Some name", "Some city");

    Hall validHall = new Hall(1L, 9, 10, validLocation, new ArrayList<Seat>());

    Category validCategory = new Category(1L, "Some name", 7, 8, CategoryType.FAIRY);

    Event validEvent = new Event(1L, "Some name", validStartDate, validEndDate, validLocation, validHall, validCategory,
            "Some description", "Some username");

    LocationEventAdmin validUser = new LocationEventAdmin(1L, "Some username", "Some password", "Some name", "Some lastname", "Some email");

    @Test(expected = DataException.class)
    public void whenNoUsernameIsSetThrowException_registerUser() throws Exception {
        UserDTO userDTO = new UserDTO(null, "Some password", "Some email", "Some name", "Some lastname",
        		validStartDate);
        userService.registerUser(userDTO, "regular_user_role");
    }

    @Test(expected = DataException.class)
    public void whenNoPasswordIsSetThrowException_registerUser() throws Exception {
        UserDTO userDTO = new UserDTO("Some username", null, "Some email", "Some name", "Some lastname",
        		validStartDate);
        userService.registerUser(userDTO, "regular_user_role");
    }

    @Test(expected = DataException.class)
    public void whenNoEmailIsSetThrowException_registerUser() throws Exception {
        UserDTO userDTO = new UserDTO("Some username", "Some password", null, "Some name", "Some lastname",
        		validStartDate);
        userService.registerUser(userDTO, "regular_user_role");
    }

    @Test(expected = DataException.class)
    public void whenNoNameIsSetThrowException_registerUser() throws Exception {
        UserDTO userDTO = new UserDTO("Some username", "Some password", "Some email", null, "Some lastname",
        		validStartDate);
        userService.registerUser(userDTO, "regular_user_role");
    }

    @Test(expected = DataException.class)
    public void whenNoLastnameIsSetThrowException_registerUser() throws Exception {
        UserDTO userDTO = new UserDTO("Some username", "Some password", "Some email", "Some name", null,
        		validStartDate);
        userService.registerUser(userDTO, "regular_user_role");
    }

    @Test(expected = DataException.class)
    public void whenThereIsUserWithSameUsernameThrowException_registerUser() throws Exception {
        UserDTO userDTO = new UserDTO("Some username", "Some password", "Some email", "Some name", "Some lastname",
        		validStartDate);
        when(mockedUserRepository.findByUsername("Some username")).thenReturn(Optional.of(new User()));
        userService.registerUser(userDTO, "regular_user_role");
    }

    @Test(expected = DataException.class)
    public void whenThereIsUserWithSameEmailThrowException_registerUser() throws Exception {
        UserDTO userDTO = new UserDTO("Some username", "Some password", "Some email", "Some name", "Some lastname",
        		validStartDate);
        when(mockedUserRepository.findByUsername("Some username")).thenReturn(Optional.empty());
        when(mockedUserRepository.findByEmail("Some email")).thenReturn(Optional.of(new User()));
        userService.registerUser(userDTO, "regular_user_role");
    }

    @Test(expected = DataException.class)
    public void whenThereIsNoGivenAuthorityThrowException_registerUser() throws Exception {
        UserDTO userDTO = new UserDTO("Some username", "Some password", "Some email", "Some name", "Some lastname",
        		validStartDate);
        when(mockedUserRepository.findByUsername("Some username")).thenReturn(Optional.empty());
        when(mockedUserRepository.findByEmail("Some email")).thenReturn(Optional.empty());
        when(mockedAuthorityRepository.findByName("no_valid_role")).thenReturn(Optional.empty());
        userService.registerUser(userDTO, "no_valid_role");
    }

    @Test(expected = DataException.class)
    public void whenUsernameIsNotSetThrowException_deleteUser() throws Exception{
        userService.deleteUser(null);
    }

    @Test(expected = DataException.class)
    public void whenUserIsNotFoundThrowException_deleteUser() throws Exception{
        when(mockedUserRepository.findByUsername("Some username")).thenReturn(Optional.empty());
        userService.deleteUser("Some username");
    }

    @Test
    public void whenUserIsFoundDeleteHim_deleteUser() throws Exception{
        User user = new User(1L, "Some username", "Some password");
        when(mockedUserRepository.findByUsername("Some username")).thenReturn(Optional.of(user));
        userService.deleteUser("Some username");

        Mockito.verify(mockedUserRepository, Mockito.times(1)).delete(user);
    }

    @Test(expected = DataException.class)
    public void whenEventIsOnLocationThrowException_deleteLocation() throws Exception{
        when(mockedLocationRepository.findById(1L)).thenReturn(Optional.of(validLocation));
        ArrayList<Event> events = new ArrayList<Event>();
        events.add(validEvent);
        when(mockedEventRepository.findAll()).thenReturn(events);
        userService.deleteLocation("1");
    }

    //treba izmeniti userService metodu deleteLocation da prvo ide provera da li postoji location
    @Test(expected = DataException.class)
    public void whenEventDoesNotExistThrowException_deleteLocation() throws Exception{
        when(mockedLocationRepository.findById(-1L)).thenReturn(Optional.empty());
        userService.deleteLocation("-1");
    }

    @Test
    public void whenLocationDoesNotHaveEventDeleteIt_deleteLocation() throws Exception{
        when(mockedLocationRepository.findById(1L)).thenReturn(Optional.of(validLocation));
        ArrayList<Event> events = new ArrayList<Event>();
        when(mockedEventRepository.findAll()).thenReturn(events);
        userService.deleteLocation("1");

        Mockito.verify(mockedLocationRepository, Mockito.times(1)).delete(validLocation);
    }

    @Test(expected = DataException.class)
    public void whenUserDoesNotExistThrowException_getOneUser() throws Exception{
        when(mockedUserRepository.findByUsername("Some username")).thenReturn(Optional.empty());
        userService.getOneUser("Some username");
    }

    @Test
    public void whenUserExistsReturnHim_getOneUser() throws Exception{
        when(mockedUserRepository.findByUsername("Some username")).thenReturn(Optional.of(validUser));
        User result = userService.getOneUser("Some username");
        
        System.out.println(result.getPassword());

        assertEquals("Some username", result.getUsername());
        assertEquals("Some password", result.getPassword());
    }


}
package com.project.master.service;

import com.project.master.domain.*;
import com.project.master.dto.UserDTO;
import com.project.master.exception.DataException;
import com.project.master.repository.AuthorityRepository;
import com.project.master.repository.EventRepository;
import com.project.master.repository.LocationRepository;
import com.project.master.repository.UserRepository;
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
public class UserServiceUnitTests {
    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository mockedUserRepository;

    @MockBean
    private LocationRepository mockedLocationRepository;

    @MockBean
    private EventRepository mockedEventRepository;

    @MockBean
    private AuthorityRepository mockedAuthorityRepository;


    Date validStartDate =  new Date(2019, 9, 22);
    Date validEndDate =  new Date(2019, 10, 22);

    UserDTO validUserDTO = new UserDTO("Some username", "Some password", "Some email", "Some name", "Some lastname",
            validStartDate);

    EventLocation validLocation = new EventLocation(1L, "Some name", "Some city");

    Hall validHall = new Hall(1L, 9, 10, validLocation, new ArrayList<Seat>());

    Category validCategory = new Category(1L, "Some name", 7, 8, CategoryType.FAIRY);

    Event validEvent = new Event(1L, "Some name", validStartDate, validEndDate, validLocation, validHall, validCategory,
            "Some description", "Some username");

    LocationEventAdmin validUser = new LocationEventAdmin(1L, "Some username", "Some password", "Some name", "Some lastname", "Some email");

    @Test(expected = DataException.class)
    public void whenNoUsernameIsSetThrowException_registerUser() throws Exception {
        UserDTO userDTO = new UserDTO(null, "Some password", "Some email", "Some name", "Some lastname",
                validStartDate);
        userService.registerUser(userDTO, "regular_user_role");
    }

    @Test(expected = DataException.class)
    public void whenNoPasswordIsSetThrowException_registerUser() throws Exception {
        UserDTO userDTO = new UserDTO("Some username", null, "Some email", "Some name", "Some lastname",
                validStartDate);
        userService.registerUser(userDTO, "regular_user_role");
    }

    @Test(expected = DataException.class)
    public void whenNoEmailIsSetThrowException_registerUser() throws Exception {
        UserDTO userDTO = new UserDTO("Some username", "Some password", null, "Some name", "Some lastname",
                validStartDate);
        userService.registerUser(userDTO, "regular_user_role");
    }

    @Test(expected = DataException.class)
    public void whenNoNameIsSetThrowException_registerUser() throws Exception {
        UserDTO userDTO = new UserDTO("Some username", "Some password", "Some email", null, "Some lastname",
                validStartDate);
        userService.registerUser(userDTO, "regular_user_role");
    }

    @Test(expected = DataException.class)
    public void whenNoLastnameIsSetThrowException_registerUser() throws Exception {
        UserDTO userDTO = new UserDTO("Some username", "Some password", "Some email", "Some name", null,
                validStartDate);
        userService.registerUser(userDTO, "regular_user_role");
    }

    @Test(expected = DataException.class)
    public void whenThereIsUserWithSameUsernameThrowException_registerUser() throws Exception {
        UserDTO userDTO = new UserDTO("Some username", "Some password", "Some email", "Some name", "Some lastname",
                validStartDate);
        when(mockedUserRepository.findByUsername("Some username")).thenReturn(Optional.of(new User()));
        userService.registerUser(userDTO, "regular_user_role");
    }

    @Test(expected = DataException.class)
    public void whenThereIsUserWithSameEmailThrowException_registerUser() throws Exception {
        UserDTO userDTO = new UserDTO("Some username", "Some password", "Some email", "Some name", "Some lastname",
                validStartDate);
        when(mockedUserRepository.findByUsername("Some username")).thenReturn(Optional.empty());
        when(mockedUserRepository.findByEmail("Some email")).thenReturn(Optional.of(new User()));
        userService.registerUser(userDTO, "regular_user_role");
    }

    @Test(expected = DataException.class)
    public void whenThereIsNoGivenAuthorityThrowException_registerUser() throws Exception {
        UserDTO userDTO = new UserDTO("Some username", "Some password", "Some email", "Some name", "Some lastname",
                validStartDate);
        when(mockedUserRepository.findByUsername("Some username")).thenReturn(Optional.empty());
        when(mockedUserRepository.findByEmail("Some email")).thenReturn(Optional.empty());
        when(mockedAuthorityRepository.findByName("no_valid_role")).thenReturn(Optional.empty());
        userService.registerUser(userDTO, "no_valid_role");
    }

    @Test(expected = DataException.class)
    public void whenUsernameIsNotSetThrowException_deleteUser() throws Exception{
        userService.deleteUser(null);
    }

    @Test(expected = DataException.class)
    public void whenUserIsNotFoundThrowException_deleteUser() throws Exception{
        when(mockedUserRepository.findByUsername("Some username")).thenReturn(Optional.empty());
        userService.deleteUser("Some username");
    }

    @Test
    public void whenUserIsFoundDeleteHim_deleteUser() throws Exception{
        User user = new User(1L, "Some username", "Some password");
        when(mockedUserRepository.findByUsername("Some username")).thenReturn(Optional.of(user));
        userService.deleteUser("Some username");

        Mockito.verify(mockedUserRepository, Mockito.times(1)).delete(user);
    }

    @Test(expected = DataException.class)
    public void whenEventIsOnLocationThrowException_deleteLocation() throws Exception{
        when(mockedLocationRepository.findById(1L)).thenReturn(Optional.of(validLocation));
        ArrayList<Event> events = new ArrayList<Event>();
        events.add(validEvent);
        when(mockedEventRepository.findAll()).thenReturn(events);
        userService.deleteLocation("1");
    }

    //treba izmeniti userService metodu deleteLocation da prvo ide provera da li postoji location
    @Test(expected = DataException.class)
    public void whenEventDoesNotExistThrowException_deleteLocation() throws Exception{
        when(mockedLocationRepository.findById(-1L)).thenReturn(Optional.empty());
        userService.deleteLocation("-1");
    }

    @Test
    public void whenLocationDoesNotHaveEventDeleteIt_deleteLocation() throws Exception{
        when(mockedLocationRepository.findById(1L)).thenReturn(Optional.of(validLocation));
        ArrayList<Event> events = new ArrayList<Event>();
        when(mockedEventRepository.findAll()).thenReturn(events);
        userService.deleteLocation("1");

        Mockito.verify(mockedLocationRepository, Mockito.times(1)).delete(validLocation);
    }

    @Test(expected = DataException.class)
    public void whenUserDoesNotExistThrowException_getOneUser() throws Exception{
        when(mockedUserRepository.findByUsername("Some username")).thenReturn(Optional.empty());
        userService.getOneUser("Some username");
    }

    @Test
    public void whenUserExistsReturnHim_getOneUser() throws Exception{
        when(mockedUserRepository.findByUsername("Some username")).thenReturn(Optional.of(validUser));
        User result = userService.getOneUser("Some username");

        System.out.println(result.getPassword());

        assertEquals("Some username", result.getUsername());
        assertEquals("Some password", result.getPassword());
    }


}
