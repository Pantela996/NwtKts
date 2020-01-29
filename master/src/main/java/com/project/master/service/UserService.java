package com.project.master.service;


import com.project.master.domain.Authority;
import com.project.master.domain.RegularUser;
import com.project.master.domain.User;
import com.project.master.domain.UserAuthority;
import com.project.master.dto.UserDTO;
import com.project.master.exception.DataException;
import com.project.master.exception.UserNotFoundException;
import com.project.master.repository.AuthorityRepository;
import com.project.master.repository.UserRepository;

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


	public void registerUser(UserDTO userDTO) throws DataException{
		
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
		
		UserAuthority authorities = new UserAuthority();
		
		Optional<Authority> oauth = authRepository.findByName("REGULAR_USER_ROLE");
		
		if(!oauth.isPresent()) {
			throw new DataException("Given authority does not exists");
		}
		
		Authority auth = oauth.get();
		
		auth.getUserAuthorities().add(authorities);
		authorities.setAuthority(auth);
		authorities.setUser(user);
		user.getUserAuthorities().add(authorities);
		BCryptPasswordEncoder enc = new BCryptPasswordEncoder();
		user.setPassword(enc.encode(user.getPassword()));
		userRepository.save(user);
		
		return;
	}

}