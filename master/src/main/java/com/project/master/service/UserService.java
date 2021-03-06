package com.project.master.service;


import ch.qos.logback.core.CoreConstants;
import com.project.master.domain.*;
import com.project.master.dto.TicketDTO;
import com.project.master.dto.UserDTO;
import com.project.master.exception.DataException;
import com.project.master.exception.UserNotFoundException;
import com.project.master.repository.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.crypto.Data;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    
	@Autowired
	private AuthorityRepository authRepository;

	@Autowired
	private UserAuthorityRepository userAuthRepository;
	
	@Autowired
	private LocationRepository locationRepository;

	@Autowired
	private EventRepository eventRepository;
	

	@Autowired
	private TicketRepository ticketRepository;


    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public Optional<User> getLoggedUser() throws UserNotFoundException {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        try {
            org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) auth
                    .getPrincipal();
            return userRepository.findByUsername(user.getUsername());
        } catch (Exception e) {
            throw new UserNotFoundException();
        }
    }


	public void registerUser(UserDTO userDTO, String typeOFUser) throws DataException{
		
		User user = new User();
		
		if(typeOFUser.equalsIgnoreCase("REGULAR_USER_ROLE")) {
			user = new RegularUser();
		}else {
			user = new LocationEventAdmin();
		}
		
		
		
		if(userDTO.getUsername() == null) throw new DataException("No username was given");
		
		if(userDTO.getPassword() == null) throw new DataException("No password was given");
		
		if(userDTO.getEmail() == null) throw new DataException("No email was given");
		
		if(userDTO.getName() == null) throw new DataException("No name was given");
		
		if(userDTO.getLastName() == null) throw new DataException("No lastname was given");
		
		if(userDTO.getUsername().length() < 3 || userDTO.getUsername().length() > 20)
			throw new DataException("Username format");
		if(userDTO.getPassword().length() < 3 || userDTO.getPassword().length() > 20)
			throw new DataException("Password format");
		if(userDTO.getName().length() < 3 || userDTO.getName().length() > 20)
			throw new DataException("Name format");
		if(userDTO.getLastName().length() < 3 || userDTO.getLastName().length() > 20)
			throw new DataException("Surname format");
		
		Optional<User> oUser = userRepository.findByUsername(userDTO.getUsername());
				
		
		if(oUser.isPresent()) {
			throw new DataException("Username already exists");
		}

		oUser = userRepository.findByEmail(userDTO.getEmail());



		if(oUser.isPresent()) {
			throw new DataException("Email already exists");
		}




		
		user.setUsername(userDTO.getUsername());
		user.setPassword(userDTO.getPassword());
		user.setEmail(userDTO.getEmail());
		user.setName(userDTO.getName());
		user.setLastName(userDTO.getLastName());
		
		
		UserAuthority authority = new UserAuthority();
		
		Optional<Authority> oauth = authRepository.findByName(typeOFUser);
		
		if(!oauth.isPresent()) {
			throw new DataException("Given authority does not exists");
		}
		
		Authority auth = oauth.get();
		
		auth.getUserAuthorities().add(authority);
		authority.setAuthority(auth);
		authority.setUser(user);
		System.out.println(authority.getAuthority().getName());
		user.getUserAuthorities().add(authority);
		BCryptPasswordEncoder enc = new BCryptPasswordEncoder();
		user.setPassword(enc.encode(user.getPassword()));
		userRepository.save(user);
		userAuthRepository.save(authority);
		return;
	}


	public void deleteUser(String username) throws DataException {
		
		if (username==null) throw new DataException("Username not defined");
		
		Optional<User> ouser = userRepository.findByUsername(username);
		
		if(!ouser.isPresent()) {
			throw new DataException("Username not found");
		}
		
		User user = ouser.get();
		
		//userAuthRepository.deleteByUser(user);
		
		userRepository.delete(user);
		
		return;
		
	}


	public ArrayList<User> getAllUsers(String typeOFUsers) {
		ArrayList<User> users = (ArrayList<User>)userRepository.findAll();
		ArrayList<User> finalUsers = new ArrayList<User>();
		for(User u:users) {
			for(UserAuthority a: u.getUserAuthorities()) {
				System.out.println(a.getAuthority().getName());
				if(a.getAuthority().getName().equalsIgnoreCase(typeOFUsers)) {
					System.out.println(u.getUserAuthorities().size());
					finalUsers.add(new User(u.getId(), u.getUsername(), u.getPassword(), u.getName(), u.getLastName(), u.getEmail(), u.getDate_of_creation(), u.getTicket()));
				}
			}
		}
		return finalUsers;
	}


	public void deleteLocation(String id) throws DataException {
		
		Optional<EventLocation> oloc = locationRepository.findById(Long.valueOf(id));

		if(!oloc.isPresent()) {
			throw new DataException("Location does not exists");
		}
		
		List<Event> events = eventRepository.findAll();
		for (Event event:events){
			if(event.getLocation().getName() == oloc.get().getName()){
				throw new DataException("There is an event on this location");
			}
		}

		if(!oloc.isPresent()) {
			throw new DataException("Location does not exists");
		}
		
		EventLocation el = oloc.get();
		
		locationRepository.delete(el);
		
		return;
	}




	public User getOneUser(String username) throws DataException {
		Optional<User> oUser = userRepository.findByUsername(username);
		
		if(!oUser.isPresent()) {
			throw new DataException("User does not exist");
		}
		
		User temp = oUser.get();
		
		User user = new User(temp.getUsername(), temp.getPassword());
		
		return user;
	}


	public List<Ticket> getMyTickets(String username) throws DataException {
		Optional<User> ouser = userRepository.findByUsername(username);
		
		if(!ouser.isPresent()) {
			throw new DataException("User does not exists");
		}
		
		User user = ouser.get();
		
		
		return user.getTicket();
	}


	public Ticket deleteTicket(TicketDTO ticketDTO) throws DataException {
		Optional<Ticket> oticket = ticketRepository.findById(ticketDTO.getId());
		
		if(!oticket.isPresent()) {
			throw new DataException("Ticket does not exists");
		}
		
		Ticket ticket = oticket.get();
		
		Optional<Event> oevent = eventRepository.findById(ticket.getEvent().getId());
		
		if(!oevent.isPresent()) {
			throw new DataException("Event does not exists");
		}
		
		Event event= oevent.get();
		
		for(Seat p:event.getHall().getSeats()) {
			if(p.getSeat_row() == ticket.getSeat_row() && p.getSeat_column() == ticket.getSeat_column()) {
				p.setTypeOfSeat(TypeOfSeat.AVAILABLE);
			}
		}	
		
		
		
		eventRepository.save(event);
		

		
		ticketRepository.delete(ticket);
		


		
		return ticket;
	}


}