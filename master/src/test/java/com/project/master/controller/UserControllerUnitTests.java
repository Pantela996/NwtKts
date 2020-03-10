package com.project.master.controller;

import com.project.master.TestUtil;
import com.project.master.constants.UserConstants;
import com.project.master.dto.UserDTO;
import com.project.master.util.AuthenticationRequest;


import org.junit.Test;
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

import javax.annotation.PostConstruct;
import java.nio.charset.Charset;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
@AutoConfigureMockMvc

public class UserControllerUnitTests {

    private static final String URL_PREFIX = "";

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @PostConstruct
    public void setup(){ this.mockMvc = MockMvcBuilders
            .webAppContextSetup(webApplicationContext)
            //.defaultRequest(post("/get_all_users").with(user("miki").roles("ADMIN")))
            //.apply(springSecurity())
            .build();
    }




    @Test
    public void successfulLogin() throws Exception {
        AuthenticationRequest login = new AuthenticationRequest();
        login.setUsername(UserConstants.SUCC_LOGIN_USERNAME);
        login.setPassword(UserConstants.SUCC_LOGIN_PASSWORD);
        String json = TestUtil.json(login);

        MvcResult result = mockMvc.perform(post(URL_PREFIX + "/login").contentType(contentType).content(json))
                .andExpect(status().isOk()).andReturn();

        assertFalse(result.getResponse().getContentAsString().contains("Invalid login"));
    }

    @Test
    public void badLogin() throws Exception {
        AuthenticationRequest login = new AuthenticationRequest();
        login.setUsername(UserConstants.SUCC_LOGIN_USERNAME);
        login.setPassword(UserConstants.SUCC_LOGIN_PASSWORD);
        String json = TestUtil.json(login);

        MvcResult result = mockMvc.perform(post(URL_PREFIX + "/login").contentType(contentType).content(json))
                .andExpect(status().isOk()).andReturn();

        assertFalse(result.getResponse().getContentAsString().contains("Successful"));
    }


    @Test
    public void successfulRegister() throws Exception{
        UserDTO user = new UserDTO();
        user.setUsername(UserConstants.NEW_USERNAME);
        user.setPassword(UserConstants.NEW_PASSWORD);
        user.setEmail(UserConstants.NEW_EMAIL);
        user.setName(UserConstants.NEW_NAME);
        user.setLastName(UserConstants.NEW_LASTNAME);
        user.setDate_of_creation(UserConstants.NEW_DATE_OF_CREATION);

        String json = TestUtil.json(user);

        System.out.println(json);
        MvcResult result = mockMvc.perform(post("/register").contentType(contentType).content(json))
                .andExpect(status().isBadRequest()).andReturn();

        assertEquals("Failed", result.getResponse().getContentAsString());

    }

    @Test
    public void registerUsernameNull() throws Exception{
        UserDTO user = new UserDTO();
        user.setUsername(null);
        user.setPassword(UserConstants.NEW_PASSWORD);
        user.setEmail(UserConstants.NEW_EMAIL);
        user.setName(UserConstants.NEW_NAME);
        user.setLastName(UserConstants.NEW_LASTNAME);
        user.setDate_of_creation(UserConstants.NEW_DATE_OF_CREATION);

        String json = TestUtil.json(user);

        System.out.println(json);
        MvcResult result = mockMvc.perform(post("/register").contentType(contentType).content(json))
                .andExpect(status().isBadRequest()).andReturn();

        assertEquals("Failed", result.getResponse().getContentAsString());

    }

    @Test
    public void registerPasswordNull() throws Exception{
        UserDTO user = new UserDTO();
        user.setUsername(UserConstants.NEW_USERNAME);
        user.setPassword(null);
        user.setEmail(UserConstants.NEW_EMAIL);
        user.setName(UserConstants.NEW_NAME);
        user.setLastName(UserConstants.NEW_LASTNAME);
        user.setDate_of_creation(UserConstants.NEW_DATE_OF_CREATION);

        String json = TestUtil.json(user);

        System.out.println(json);
        MvcResult result = mockMvc.perform(post("/register").contentType(contentType).content(json))
                .andExpect(status().isBadRequest()).andReturn();

        assertEquals("Failed", result.getResponse().getContentAsString());

    }

    @Test
    public void registerEmailNull() throws Exception{
        UserDTO user = new UserDTO();
        user.setUsername(UserConstants.NEW_USERNAME);
        user.setPassword(UserConstants.NEW_PASSWORD);
        user.setEmail(null);
        user.setName(UserConstants.NEW_NAME);
        user.setLastName(UserConstants.NEW_LASTNAME);
        user.setDate_of_creation(UserConstants.NEW_DATE_OF_CREATION);

        String json = TestUtil.json(user);

        System.out.println(json);
        MvcResult result = mockMvc.perform(post("/register").contentType(contentType).content(json))
                .andExpect(status().isBadRequest()).andReturn();

        assertEquals("Failed", result.getResponse().getContentAsString());

    }

    @Test
    public void registerFirstNameNull() throws Exception{
        UserDTO user = new UserDTO();
        user.setUsername(UserConstants.NEW_USERNAME);
        user.setPassword(UserConstants.NEW_PASSWORD);
        user.setEmail(UserConstants.NEW_EMAIL);
        user.setName(null);
        user.setLastName(UserConstants.NEW_LASTNAME);
        user.setDate_of_creation(UserConstants.NEW_DATE_OF_CREATION);

        String json = TestUtil.json(user);

        System.out.println(json);
        MvcResult result = mockMvc.perform(post("/register").contentType(contentType).content(json))
                .andExpect(status().isBadRequest()).andReturn();

        assertEquals("Failed", result.getResponse().getContentAsString());

    }

    @Test
    public void registerLastNameNull() throws Exception{
        UserDTO user = new UserDTO();
        user.setUsername(UserConstants.NEW_USERNAME);
        user.setPassword(UserConstants.NEW_PASSWORD);
        user.setEmail(UserConstants.NEW_EMAIL);
        user.setName(UserConstants.NEW_NAME);
        user.setLastName(null);
        user.setDate_of_creation(UserConstants.NEW_DATE_OF_CREATION);

        String json = TestUtil.json(user);

        System.out.println(json);
        MvcResult result = mockMvc.perform(post("/register").contentType(contentType).content(json))
                .andExpect(status().isBadRequest()).andReturn();

        assertEquals("Failed", result.getResponse().getContentAsString());

    }

    @Test
    public void registerUsernameShort() throws Exception{
        UserDTO user = new UserDTO();
        user.setUsername(UserConstants.USERNAME_SHORT);
        user.setPassword(UserConstants.NEW_PASSWORD);
        user.setEmail(UserConstants.NEW_EMAIL);
        user.setName(UserConstants.NEW_NAME);
        user.setLastName(UserConstants.NEW_LASTNAME);
        user.setDate_of_creation(UserConstants.NEW_DATE_OF_CREATION);

        String json = TestUtil.json(user);

        System.out.println(json);
        MvcResult result = mockMvc.perform(post("/register").contentType(contentType).content(json))
                .andExpect(status().isBadRequest()).andReturn();

        assertEquals("Failed", result.getResponse().getContentAsString());

    }

    @Test
    public void registerUsernameLong() throws Exception{
        UserDTO user = new UserDTO();
        user.setUsername(UserConstants.USERNAME_LONG);
        user.setPassword(UserConstants.NEW_PASSWORD);
        user.setEmail(UserConstants.NEW_EMAIL);
        user.setName(UserConstants.NEW_NAME);
        user.setLastName(UserConstants.NEW_LASTNAME);
        user.setDate_of_creation(UserConstants.NEW_DATE_OF_CREATION);

        String json = TestUtil.json(user);

        System.out.println(json);
        MvcResult result = mockMvc.perform(post("/register").contentType(contentType).content(json))
                .andExpect(status().isBadRequest()).andReturn();

        assertEquals("Failed", result.getResponse().getContentAsString());

    }

    @Test
    public void registerPasswordShort() throws Exception{
        UserDTO user = new UserDTO();
        user.setUsername(UserConstants.NEW_USERNAME);
        user.setPassword(UserConstants.PASSWORD_SHORT);
        user.setEmail(UserConstants.NEW_EMAIL);
        user.setName(UserConstants.NEW_NAME);
        user.setLastName(UserConstants.NEW_LASTNAME);
        user.setDate_of_creation(UserConstants.NEW_DATE_OF_CREATION);

        String json = TestUtil.json(user);

        System.out.println(json);
        MvcResult result = mockMvc.perform(post("/register").contentType(contentType).content(json))
                .andExpect(status().isBadRequest()).andReturn();

        assertEquals("Failed", result.getResponse().getContentAsString());

    }

    @Test
    public void registerPasswordLong() throws Exception{
        UserDTO user = new UserDTO();
        user.setUsername(UserConstants.NEW_USERNAME);
        user.setPassword(UserConstants.PASSWORD_LONG);
        user.setEmail(UserConstants.NEW_EMAIL);
        user.setName(UserConstants.NEW_NAME);
        user.setLastName(UserConstants.NEW_LASTNAME);
        user.setDate_of_creation(UserConstants.NEW_DATE_OF_CREATION);

        String json = TestUtil.json(user);

        System.out.println(json);
        MvcResult result = mockMvc.perform(post("/register").contentType(contentType).content(json))
                .andExpect(status().isBadRequest()).andReturn();

        assertEquals("Failed", result.getResponse().getContentAsString());

    }

    @Test
    public void registerNameShort() throws Exception{
        UserDTO user = new UserDTO();
        user.setUsername(UserConstants.NEW_USERNAME);
        user.setPassword(UserConstants.NEW_PASSWORD);
        user.setEmail(UserConstants.NEW_EMAIL);
        user.setName(UserConstants.NAME_SHORT);
        user.setLastName(UserConstants.NEW_LASTNAME);
        user.setDate_of_creation(UserConstants.NEW_DATE_OF_CREATION);

        String json = TestUtil.json(user);

        System.out.println(json);
        MvcResult result = mockMvc.perform(post("/register").contentType(contentType).content(json))
                .andExpect(status().isBadRequest()).andReturn();

        assertEquals("Failed", result.getResponse().getContentAsString());

    }

    @Test
    public void registerNameLong() throws Exception{
        UserDTO user = new UserDTO();
        user.setUsername(UserConstants.NEW_USERNAME);
        user.setPassword(UserConstants.NEW_PASSWORD);
        user.setEmail(UserConstants.NEW_EMAIL);
        user.setName(UserConstants.NAME_LONG);
        user.setLastName(UserConstants.NEW_LASTNAME);
        user.setDate_of_creation(UserConstants.NEW_DATE_OF_CREATION);

        String json = TestUtil.json(user);

        System.out.println(json);
        MvcResult result = mockMvc.perform(post("/register").contentType(contentType).content(json))
                .andExpect(status().isBadRequest()).andReturn();

        assertEquals("Failed", result.getResponse().getContentAsString());

    }

    @Test
    public void registerLastNameShort() throws Exception{
        UserDTO user = new UserDTO();
        user.setUsername(UserConstants.NEW_USERNAME);
        user.setPassword(UserConstants.NEW_PASSWORD);
        user.setEmail(UserConstants.NEW_EMAIL);
        user.setName(UserConstants.NEW_NAME);
        user.setLastName(UserConstants.LASTNAME_SHORT);
        user.setDate_of_creation(UserConstants.NEW_DATE_OF_CREATION);

        String json = TestUtil.json(user);

        System.out.println(json);
        MvcResult result = mockMvc.perform(post("/register").contentType(contentType).content(json))
                .andExpect(status().isBadRequest()).andReturn();

        assertEquals("Failed", result.getResponse().getContentAsString());

    }

    @Test
    public void registerLastNameLong() throws Exception{
        UserDTO user = new UserDTO();
        user.setUsername(UserConstants.NEW_USERNAME);
        user.setPassword(UserConstants.NEW_PASSWORD);
        user.setEmail(UserConstants.NEW_EMAIL);
        user.setName(UserConstants.NEW_NAME);
        user.setLastName(UserConstants.LASTNAME_LONG);
        user.setDate_of_creation(UserConstants.NEW_DATE_OF_CREATION);

        String json = TestUtil.json(user);

        System.out.println(json);
        MvcResult result = mockMvc.perform(post("/register").contentType(contentType).content(json))
                .andExpect(status().isBadRequest()).andReturn();

        assertEquals("Failed", result.getResponse().getContentAsString());

    }



    @Test
    public void registerEmailAlreadyExists() throws Exception{
        UserDTO user = new UserDTO();
        user.setUsername(UserConstants.NEW_USERNAME);
        user.setPassword(UserConstants.NEW_PASSWORD);
        user.setEmail(UserConstants.TAKEN_EMAIL);
        user.setName(UserConstants.NEW_NAME);
        user.setLastName(UserConstants.NEW_LASTNAME);
        user.setDate_of_creation(UserConstants.NEW_DATE_OF_CREATION);

        String json = TestUtil.json(user);

        System.out.println(json);
        MvcResult result = mockMvc.perform(post("/register").contentType(contentType).content(json))
                .andExpect(status().isOk()).andReturn();

        assertEquals("Success", result.getResponse().getContentAsString());

    }



    @Test
    public void registerUsernameTaken() throws Exception{
        UserDTO user = new UserDTO();
        user.setUsername(UserConstants.TAKEN_USERNAME);
        user.setPassword(UserConstants.NEW_PASSWORD);
        user.setEmail(UserConstants.NEW_EMAIL);
        user.setName(UserConstants.NEW_NAME);
        user.setLastName(UserConstants.NEW_LASTNAME);
        user.setDate_of_creation(UserConstants.NEW_DATE_OF_CREATION);

        String json = TestUtil.json(user);

        //System.out.println(json);
        MvcResult result = mockMvc.perform(post("/register").contentType(contentType).content(json))
                .andExpect(status().isOk()).andReturn();

        assertEquals("Success", result.getResponse().getContentAsString());

    }

    @Test
    public void getAllUsers() throws Exception{
        MvcResult result = mockMvc.perform(get("/get_all_users"))
                .andExpect(status().isOk()).andReturn();


        assertNotNull(result);
    }

    @Test
    public void getAllEventAdmins() throws Exception{
        MvcResult result = mockMvc.perform(get("/get_all_event_admins"))
                .andExpect(status().isOk()).andReturn();


        assertNotNull(result);
    }

    @Test
    public void deleteLocationAdminNonExistent() throws Exception{

        MvcResult result = mockMvc.perform(delete("/delete_location_admin/{username}", "nan"))
                .andExpect(status().isBadRequest()).andReturn();



        assertEquals(("Failed"), result.getResponse().getContentAsString());
    }

    @Test
    public void deleteLocationAdminSuccess() throws Exception{

        MvcResult result = mockMvc.perform(delete("/delete_location_admin/{username}", "user"))
                .andExpect(status().isOk()).andReturn();



        assertEquals(("Success"), result.getResponse().getContentAsString());
    }

    @Test
    public void deleteUserNonExistent() throws Exception{

        MvcResult result = mockMvc.perform(delete("/delete_location_admin/{username}", "nan"))
                .andExpect(status().isBadRequest()).andReturn();



        assertEquals(("Failed"), result.getResponse().getContentAsString());
    }

    @Test
    public void deleteUserSuccess() throws Exception{

        MvcResult result = mockMvc.perform(delete("/delete_user/{username}", "u1"))
                .andExpect(status().isOk()).andReturn();



        assertEquals(("Success"), result.getResponse().getContentAsString());
    }

    @Test
    public void deleteLocationNonExistent() throws Exception{

        MvcResult result = mockMvc.perform(delete("/delete_location/5"))
                .andExpect(status().isOk()).andReturn();
        
        System.out.println(result);

        assertEquals(("Success"), result.getResponse().getContentAsString());
    }

    @Test
    public void deleteLocationSuccess() throws Exception{

        MvcResult result = mockMvc.perform(delete("/delete_location/{id}", "2"))
                .andExpect(status().isBadRequest()).andReturn();



        assertEquals(("Failed"), result.getResponse().getContentAsString() );
    }

    @Test
    public void getOneUserNonExistent() throws Exception{
        MvcResult result = mockMvc.perform(get("/get_one_user/{username}", "nan"))
                .andExpect(status().isBadRequest()).andReturn();

        assertFalse(result.getResponse().getContentAsString().contains("Success"));
    }

    @Test
    public void getOneUserSuccess() throws Exception{
        MvcResult result = mockMvc.perform(get("/get_one_user/{username}", "miki"))
                .andExpect(status().isOk()).andReturn();

        assertFalse(result.getResponse().getContentAsString().contains("Failed"));
    }


}
