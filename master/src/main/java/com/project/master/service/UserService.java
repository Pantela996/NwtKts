package com.project.master.service;


import com.project.master.domain.Authority;
import com.project.master.domain.EventLocation;
import com.project.master.domain.RegularUser;
import com.project.master.domain.User;
import com.project.master.domain.UserAuthority;
import com.project.master.dto.UserDTO;
import com.project.master.exception.DataException;
import com.project.master.exception.UserNotFoundException;
import com.project.master.repository.AuthorityRepository;
import com.project.master.repository.LocationRepository;
import com.project.master.repository.UserAuthorityRepository;
import com.project.master.repository.UserRepository;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    
	@Autowired
	private AuthorityRepository authRepository;

	
	@Autowired
	private LocationRepository locationRepository;

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
		
		User user = new RegularUser();
		
		if(userDTO.getUsername() == null) throw new DataException("No username was given");
		
		if(userDTO.getPassword() == null) throw new DataException("No password was given");
		
		if(userDTO.getEmail() == null) throw new DataException("No email was given");
		
		Optional<User> oUser = userRepository.findByUsername(userDTO.getUsername());
				
		
		if(oUser.isPresent()) {
			throw new DataException("Username already exists");
		}
		
		user.setUsername(userDTO.getUsername());
		user.setPassword(userDTO.getPassword());
		
		UserAuthority authority = new UserAuthority();
		
		Optional<Authority> oauth = authRepository.findByName(typeOFUser);
		
		if(!oauth.isPresent()) {
			throw new DataException("Given authority does not exists");
		}
		
		Authority auth = oauth.get();
		
		auth.getUserAuthorities().add(authority);
		authority.setAuthority(auth);
		authority.setUser(user);
		user.getUserAuthorities().add(authority);
		BCryptPasswordEncoder enc = new BCryptPasswordEncoder();
		user.setPassword(enc.encode(user.getPassword()));
		userRepository.save(user);
		
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
					finalUsers.add(new User(u.getUsername()));
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

}