package com.project.master.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.nio.charset.Charset;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import com.project.master.domain.Category;
import com.project.master.domain.Event;
import com.project.master.domain.EventLocation;
import com.project.master.domain.Hall;
import com.project.master.dto.CategoryDTO;
import com.project.master.dto.EventDTO;
import com.project.master.dto.HallDTO;
import com.project.master.dto.LocationDTO;
import com.project.master.exception.DataException;
import com.project.master.service.EventService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EventControllerUnitTests {
	@Autowired
	private TestRestTemplate restTemplate;

	@MockBean
	private EventService eventServiceMock;

	@MockBean
	private Authentication authentication;
	
	private static final String URL_PREFIX = "/user";

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;
	
	private static final LocationDTO locationDTO = new LocationDTO("lokacija", "ns", "miki", 1);

	private static final EventDTO validEventDTO = new EventDTO("Some name", "11.12.2020.", "15.12.2020.", locationDTO,
			new HallDTO(), new CategoryDTO(), "Some description", 1L);

	private static final EventDTO nonExistentEventDTO = new EventDTO("Some name", "11.12.2020.", "15.12.2020.",
			locationDTO, new HallDTO(), new CategoryDTO(), "Some description", -1L);

	Date validStartDate = new Date(2020, 10, 22);
	Date validEndDate = new Date(2019, 10, 22);

	@PostConstruct
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}




	@Test
	@WithUserDetails(value = "user")
	public void createEventWhenEventIsNull() throws DataException {
		when(eventServiceMock.createEvent(null, "user")).thenThrow(new DataException("Event is null"));


		ResponseEntity<String> responseEntity = restTemplate.postForEntity("/event/create", null, String.class);

		String message = responseEntity.getBody();

		assertEquals(message, "Invalid creation");
		assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
	}

	@Test
	@WithUserDetails(value = "user")
	public void createEventWhenEventIsValid() throws DataException {
		when(authentication.getName()).thenReturn("111");
		when(eventServiceMock.createEvent(validEventDTO, "111")).thenReturn("Success");

		ResponseEntity<String> responseEntity = restTemplate.postForEntity("/event/create", validEventDTO,
				String.class);

		String message = responseEntity.getBody();

		assertEquals(message, "Success");
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
	}

	@Test
	@WithUserDetails(value = "user")
	public void deleteEventWhenEventIsNonExistent() throws DataException {
		String TOKEN_ATTR_NAME = "org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository.CSRF_TOKEN";

		// ...

		HttpSessionCsrfTokenRepository httpSessionCsrfTokenRepository = new HttpSessionCsrfTokenRepository();
		CsrfToken csrfToken = httpSessionCsrfTokenRepository.generateToken(new MockHttpServletRequest());
		when(eventServiceMock.deleteEvent("-1")).thenThrow(new DataException("Event does not exist"));

		ResponseEntity<String> responseEntity = restTemplate.postForEntity("/event/delete", "-1", String.class);

		String message = responseEntity.getBody();

		assertEquals(message, "Invalid delete");
		assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
	}

	@Test
	@WithUserDetails(value = "user")
	public void deleteEventWhenEventIsValid() throws DataException {
		when(eventServiceMock.deleteEvent("1")).thenReturn("Success");

		ResponseEntity<String> responseEntity = restTemplate.postForEntity("/event/delete", "1", String.class);

		String message = responseEntity.getBody();

		assertEquals(message, "Success");
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
	}


	@Test
	public void updateEventWhenEventIsNonExistent() throws DataException {
		when(authentication.getName()).thenReturn("111");
		when(eventServiceMock.deleteEvent("-1")).thenThrow(new DataException("Event does not exist"));

		ResponseEntity<String> responseEntity = restTemplate.postForEntity("/event/update", nonExistentEventDTO,
				String.class);

		String message = responseEntity.getBody();

		assertEquals(message, "Invalid update");
		assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
	}


	@Test
	@WithUserDetails(value = "user")
	public void updateEventWhenEventIsValid() throws DataException {
		when(eventServiceMock.deleteEvent("1")).thenReturn("Success");

		ResponseEntity<String> responseEntity = restTemplate.postForEntity("/event/delete?event_id='1'", validEventDTO,
				String.class);

		String message = responseEntity.getBody();

		assertEquals(message, "Success");
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
	}


	@Test
	public void getAllEventsWhenThereIsNoEvent() {
		when(eventServiceMock.getAll()).thenReturn(new ArrayList<Event>());

		ResponseEntity<ArrayList> responseEntity = restTemplate.getForEntity("/event/all", ArrayList.class);

		ArrayList<Event> listOfEvents = responseEntity.getBody();

		assertEquals(0, listOfEvents.size());
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
	}

	@Test
	public void getAllEventsWhenThereAreTwoEvents() {
		when(authentication.getName()).thenReturn("111");
		ArrayList<Event> filledListOfElements = new ArrayList<Event>();
		filledListOfElements.add(new Event());
		filledListOfElements.add(new Event());
		when(eventServiceMock.getAll()).thenReturn(filledListOfElements);

		ResponseEntity<ArrayList> responseEntity = restTemplate.getForEntity("/event/all", ArrayList.class);

		ArrayList<Event> listOfElements = responseEntity.getBody();

		assertEquals(2, listOfElements.size());
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
	}

	@Test
	@WithUserDetails(value = "user")
	public void getOneEventsWhenThereIsNoEventWithThatId() throws DataException {
		when(eventServiceMock.getOne("-1")).thenThrow(new DataException("Event does not exist"));

		ResponseEntity<Event> responseEntity = restTemplate.postForEntity("/event/one", -1, Event.class);

		Event event = responseEntity.getBody();

		assertEquals(new Event(), event);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
	}

	@Test
	@WithUserDetails(value = "user")
	public void getOneEventWithValidId() throws DataException {
		Event event = new Event("Some name", validStartDate, validEndDate, new EventLocation(), new Hall(),
				new Category(), "Some description", "111");
		when(eventServiceMock.getOne("1")).thenReturn(event);

		ResponseEntity<Event> responseEntity = restTemplate.postForEntity("/event/one", "1", Event.class);

		Event returnedEvent = responseEntity.getBody();

		assertEquals("Some name", returnedEvent.getName());

		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
	}

	@Test
	@WithUserDetails(value = "user")
	public void getMyEventsWhenThereAreTwoOfMyEvents() {
		ArrayList<Event> events = new ArrayList<Event>();
		Event event1 = new Event();
		Event event2 = new Event();
		events.add(event1);
		events.add(event2);

		when(authentication.getName()).thenReturn("Some username");
		when(eventServiceMock.getMyEvents("Some username")).thenReturn(events);

		ResponseEntity<ArrayList> responseEntity = restTemplate.getForEntity("/event/myEvents", ArrayList.class);

		ArrayList<Event> returnedEvents = responseEntity.getBody();

		assertEquals(2, returnedEvents.size());

		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
	}

	// nisu napisani testovi za poslednje 3 metode
}
