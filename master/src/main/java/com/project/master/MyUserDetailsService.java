package com.project.master;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.project.master.domain.User;
import com.project.master.repository.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Transactional
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("here");

		User user = userRepository.findByUsername(username);

		System.out.println(user.getUsername());

		try {
			System.out.println(user.getUsername() + "<- Nadjeni ->" + user.getPassword());
		} catch (Exception e) {

		}

		if (user == null) {
			throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
		} else {

			// Java 1.8 way

			List<GrantedAuthority> grantedAuthorities = user.getUserAuthorities().stream()
					.map(authority -> new SimpleGrantedAuthority(authority.getAuthority().getName()))
					.collect(Collectors.toList());

			return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);

		}
	}

}
