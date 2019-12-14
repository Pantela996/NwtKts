package com.project.master;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.project.master.domain.EventLocation;
import com.project.master.dto.LocationDTO;
import com.project.master.exception.DataException;
import com.project.master.repository.LocationRepository;
import com.project.master.service.LocationService;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Optional;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SpringTestController {

	@Autowired
	private TestRestTemplate restTemplate;
	
	/*
	@Autowired
	private LocationService realLocationService;
	 */
	
	@MockBean
	private LocationService locationServiceMock;

	
	@MockBean
	private LocationRepository locationRepositoryMock;
	
	private static final LocationDTO locationDTO = new LocationDTO(null, "Beograd", "Milos");
	
	private static final LocationDTO locationDTONotNull = new LocationDTO("Arena", "Beograd", "Milos");

	private static final String currentUser = "Milos";


	@Test
	public void createLocationEndPointShouldThrowNoDatExceptionWhenNullLocation() throws Exception{
		when(locationServiceMock.createLocation(locationDTO.getName(), locationDTO.getLocationCity(), locationDTO.getUser_id())).thenThrow(DataException.class);
		
		
		ResponseEntity<String> responseEntity = restTemplate.postForEntity("/location/create", locationDTO, String.class);
		
		String success = responseEntity.getBody();
		
		System.out.println(success);
		
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
		
		assertEquals("Failed null", success);
		
	}
	
	/*
	@Test
	public void createFailSaveCheck() throws Exception{
		
		EventLocation location = new EventLocation(locationDTONotNull.getName(), locationDTONotNull.getName(), "");
		when(locationRepositoryMock.findByName(locationDTONotNull.getName())).thenReturn(Optional.of(location));
		String success = realLocationService.createLocation(location.getName());
		System.out.println(success);
		verify(locationRepositoryMock).save(location);
		
	}
	*/
	
	

}
