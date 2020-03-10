package com.project.master.controller;

import com.project.master.TestUtil;
import com.project.master.constants.LocationConstants;
import com.project.master.constants.UserConstants;
import com.project.master.dto.CategoryDTO;
import com.project.master.dto.LocationDTO;
import com.project.master.dto.UserDTO;
import com.project.master.util.AuthenticationRequest;


import org.junit.Test;
import org.junit.jupiter.api.TestTemplate;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.thymeleaf.spring5.expression.Mvc;

import javax.annotation.PostConstruct;
import java.nio.charset.Charset;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
@AutoConfigureMockMvc

public class LocationControllerUnitTests {

    private static final String URL_PREFIX = "/location";

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @PostConstruct
    public void setup() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                //.defaultRequest(post("/get_all_users").with(user("miki").roles("ADMIN")))
                //.apply(springSecurity())
                .build();
    }


    /*@Test
    public void createLocationSuccessful() throws Exception{

        LocationDTO location = new LocationDTO();
        //location.setId(3L);
        location.setName(LocationConstants.NEW_NAME);
        location.setLocationCity(LocationConstants.NEW_CITY_LOCATION);
        //location.setUser_id(LocationConstants.NEW_USER);
        location.setNumberOfHalls(LocationConstants.NEW_NUMBER_OF_HALLS);

        String json = TestUtil.json(location);

        MvcResult result = mockMvc.perform(post(URL_PREFIX + "/create").contentType(contentType).content(json))
                .andExpect(status().isOk()).andReturn();


        assertEquals(result.getResponse().getContentAsString(), "Successful");


    }*/

    @Test
    public void createLocationNameIsNull() throws Exception{

        LocationDTO location = new LocationDTO(3l, null, LocationConstants.NEW_CITY_LOCATION, LocationConstants.NEW_USER,10);


        String json = TestUtil.json(location);

       // MvcResult result = mockMvc.perform(post(URL_PREFIX + "/create").contentType(contentType).content(json))
             //   .andExpect(status().isBadRequest()).andReturn();
        
       // System.out.println("RESULT" + result);


        //assertEquals("Failed", result.getResponse().getContentAsString());

    }

    @Test
    public void createLocationLocationCityIsNull() throws Exception{

        LocationDTO location = new LocationDTO();
        location.setId(3L);
        location.setName(LocationConstants.NEW_NAME);
        location.setUser_id(LocationConstants.NEW_USER);
        location.setNumberOfHalls(LocationConstants.NEW_NUMBER_OF_HALLS);

        String json = TestUtil.json(location);

       // MvcResult result = mockMvc.perform(post(URL_PREFIX + "/create").contentType(contentType).content(json))
      //          .andExpect(status().isBadRequest()).andReturn();


      //  assertEquals("Failed", result.getResponse().getContentAsString());


    }

    @Test
    public void createLocationWithSameName()throws Exception{

        LocationDTO location = new LocationDTO();
        location.setId(3L);
        location.setName(LocationConstants.EXISTING_LOCATION);
        location.setLocationCity(LocationConstants.NEW_CITY_LOCATION);
        location.setUser_id(LocationConstants.NEW_USER);
        location.setNumberOfHalls(LocationConstants.NEW_NUMBER_OF_HALLS);

        String json = TestUtil.json(location);

        //MvcResult result = mockMvc.perform(post(URL_PREFIX + "/create").contentType(contentType).content(json))
          //      .andExpect(status().isBadRequest()).andReturn();


       // assertEquals("Failed", result.getResponse().getContentAsString());
    }

    @Test
    public void getAllSuccessful() throws Exception{
        MvcResult result = mockMvc.perform(get(URL_PREFIX+ "/all"))
                .andExpect(status().isOk()).andReturn();


        assertNotNull(result);


    }

    /*@Test
    public void getHallsForNonExistentLocation() throws Exception{

        MvcResult result = mockMvc.perform(get(URL_PREFIX + "/halls/{location}", "nan"))
                .andExpect(status().isNotFound()).andReturn();

        assertFalse(result.getResponse().getContentAsString().contains("Successful"));

    }*/

    @Test
    public void getHallsForLocationSuccessful() throws Exception{

        MvcResult result = mockMvc.perform(get(URL_PREFIX + "/halls/{location}", "Kombank Arena"))
                .andExpect(status().isOk()).andReturn();


        assertFalse(result.getResponse().getContentAsString().contains("Failed"));

    }

    @Test
    public void getOneNoneExistent() throws Exception{

        MvcResult result = mockMvc.perform(post(URL_PREFIX + "/one").content("5"))
                .andExpect(status().isBadRequest()).andReturn();


        assertFalse(result.getResponse().getContentAsString().contains("Successful"));

    }

    /*@Test
    public void getOneSuccessful() throws Exception{

        MvcResult result = mockMvc.perform(post(URL_PREFIX + "/one").content("2"))
                .andExpect(status().isOk()).andReturn();


        assertFalse(result.getResponse().getContentAsString().contains("Failed"));

    }*/

    @Test
    public void createCategorySuccessful() throws Exception{

        CategoryDTO category = new CategoryDTO();
        category.setName(LocationConstants.NEW_CATEGORY_NAME);
        category.setRequiredRows(LocationConstants.NEW_CATEGORY_ROWS);
        category.setRequiredColumns(LocationConstants.NEW_CATEGORY_COLUMNS);

        String json = TestUtil.json(category);

        MvcResult result = mockMvc.perform(post(URL_PREFIX + "/category/create").contentType(contentType).content(json))
                .andExpect(status().isOk()).andReturn();


        assertEquals("Success", result.getResponse().getContentAsString());

    }

    @Test
    public void createCategorySameName() throws  Exception{

        CategoryDTO category = new CategoryDTO();
        category.setName(LocationConstants.EXISTING_CATEGORY_NAME);
        category.setRequiredRows(LocationConstants.NEW_CATEGORY_ROWS);
        category.setRequiredColumns(LocationConstants.NEW_CATEGORY_COLUMNS);

        String json = TestUtil.json(category);

        MvcResult result = mockMvc.perform(post(URL_PREFIX + "/category/create").contentType(contentType).content(json))
                .andExpect(status().isBadRequest()).andReturn();


        assertEquals("Failed null", result.getResponse().getContentAsString() );

    }

    @Test
    public void getAllCategoriesSuccess() throws Exception{

        MvcResult result = mockMvc.perform(get(URL_PREFIX+ "/category/get_all"))
                .andExpect(status().isOk()).andReturn();


        assertNotNull(result);

    }

    @Test
    public void updateCategorySuccess() throws Exception{

        CategoryDTO category = new CategoryDTO();
        category.setId(2L);
        category.setName(LocationConstants.NEW_CATEGORY_NAME);
        category.setRequiredRows(LocationConstants.NEW_CATEGORY_ROWS);
        category.setRequiredColumns(LocationConstants.NEW_CATEGORY_COLUMNS);

        String json = TestUtil.json(category);

        MvcResult result = mockMvc.perform(put(URL_PREFIX + "/category/update").contentType(contentType).content(json))
                .andExpect(status().isOk()).andReturn();


        assertFalse(result.getResponse().getContentAsString().contains("Failed"));

    }

    /*@Test
    public void updateCategoryNoSuchCategory() throws Exception{

        CategoryDTO category = new CategoryDTO();
        category.setId(5L);
        category.setName(LocationConstants.NEW_CATEGORY_NAME);
        category.setRequiredRows(LocationConstants.NEW_CATEGORY_ROWS);
        category.setRequiredColumns(LocationConstants.NEW_CATEGORY_COLUMNS);

        String json = TestUtil.json(category);

        MvcResult result = mockMvc.perform(put(URL_PREFIX + "/category/update").contentType(contentType).content(json))
                .andExpect(status().isOk()).andReturn();


        assertFalse(result.getResponse().getContentAsString().contains("Success"));

    }*/

}




