package com.project.master.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.master.MyUserDetailsService;
import com.project.master.domain.Ticket;
import com.project.master.domain.User;
import com.project.master.dto.TicketDTO;
import com.project.master.dto.UserDTO;
import com.project.master.exception.DataException;
import com.project.master.service.UserService;
import com.project.master.util.AuthenticationRequest;
import com.project.master.util.AuthenticationResponse;
import com.project.master.util.JwtUtil;

@RestController
public class UserController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtTokenUtil;

	@Autowired
	private MyUserDetailsService userDetailsService;
	
	@Autowired
	private UserService userService;




	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<String> login(@RequestBody AuthenticationRequest authenticationRequest) {
		try {
			// Perform the authentication
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
					authenticationRequest.getUsername(), authenticationRequest.getPassword());
			System.out.println("password" + authenticationRequest.getPassword());
			Authentication authentication = authenticationManager.authenticate(token);
			System.out.println("details");
			SecurityContextHolder.getContext().setAuthentication(authentication);
			
			System.out.println(authentication.getAuthorities());

			// Reload user details so we can generate token
			UserDetails details = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
			
			System.out.println("here");
			
			return new ResponseEntity<String>(jwtTokenUtil.generateToken(details), HttpStatus.OK);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			return new ResponseEntity<String>("Invalid login", HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<String> registerUser(@RequestBody UserDTO userDTO){
		
		try {
			userService.registerUser(userDTO, "REGULAR_USER_ROLE");
		} catch (DataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<String>("Failed", HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<String>("Success", HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/delete_location_admin/{username}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteLocationEventAdminUser(@PathVariable String username){
		
		try {
			userService.deleteUser(username);
		} catch (DataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<String>("Failed", HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<String>("Success", HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/delete_user/{username}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deletenUser(@PathVariable String username){
		
		try {
			userService.deleteUser(username);
		} catch (DataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<String>("Failed", HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<String>("Success", HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/get_one_user/{username}", method = RequestMethod.GET)
	public ResponseEntity<User> getOneUser(@PathVariable String username){
		User user = new User();
		try {
			user = userService.getOneUser(username);
		} catch (DataException e) {
			e.printStackTrace();
			return new ResponseEntity<User>(user, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/get_all_users", method = RequestMethod.GET)
	public ResponseEntity<ArrayList<User>> getAllUsers(){
		ArrayList<User> users = new ArrayList<User>();
		users = userService.getAllUsers("REGULAR_USER_ROLE");
		return new ResponseEntity<ArrayList<User>>(users, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/my_tickets", method = RequestMethod.GET)
	public ResponseEntity<List<Ticket>> getMyTickets() throws DataException{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		 List<Ticket> tickets = userService.getMyTickets(auth.getName());
		return new ResponseEntity<List<Ticket>>(tickets, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/deleted", method = RequestMethod.POST)
	public ResponseEntity<Ticket> deleteTicket(@RequestBody TicketDTO ticketDTO){
		
		Ticket ticket = new Ticket();
		try {
			ticket = userService.deleteTicket(ticketDTO);
			return new ResponseEntity<Ticket>(ticket, HttpStatus.OK);
		} catch (DataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<Ticket>(ticket, HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@RequestMapping(value = "/create_location_admin", method = RequestMethod.POST)
	public ResponseEntity<String> createLocationEventAdminUser(@RequestBody UserDTO userDTO){
		
		try {
			userService.registerUser(userDTO, "LOCATION_AND_EVENT_ADMIN_ROLE");
		} catch (DataException e) {
			// TODO Auto-generated catch block
			return new ResponseEntity<String>( e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<String>("Success", HttpStatus.OK);
	}
	
	
	
	@RequestMapping(value = "/get_all_event_admins", method = RequestMethod.GET)
	public ResponseEntity<ArrayList<User>> getAllLocationEventAdminUser(){
		ArrayList<User> users = new ArrayList<User>();
		users = userService.getAllUsers("LOCATION_AND_EVENT_ADMIN_ROLE");
		return new ResponseEntity<ArrayList<User>>(users, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/get_all_profit", method = RequestMethod.GET)
	public ResponseEntity<ArrayList<User>> getAllProfit(){
		ArrayList<User> users = new ArrayList<User>();
		users = userService.getAllUsers("REGULAR_USER_ROLE");
		System.out.println(users.size());
		return new ResponseEntity<ArrayList<User>>(users, HttpStatus.OK);
	}
	
	
	
	@RequestMapping(value = "/delete_location/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteLocation(@PathVariable String id){
		try {
			userService.deleteLocation(id);
		} catch (DataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<String>("Success", HttpStatus.OK);

		}
		return new ResponseEntity<String>("Failed", HttpStatus.BAD_REQUEST);
	}
	

	
	
	
	
	

	@PreAuthorize("permitAll()")
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)
			throws Exception {
		System.out.println("here");
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getUsername(), authenticationRequest.getPassword()));
		} catch (BadCredentialsException e) {
			throw new Exception("Incorrect username or password", e);
		}

		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

		final String jwt = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}
}
