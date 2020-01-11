package com.project.master;

import java.util.List;
import java.util.Optional;
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

		Optional<User> user = userRepository.findByUsername(username);

		if (user.isPresent()) {

			System.out.println(user.get().getUsername());
			System.out.println(user.get().getUsername() + "<- Nadjeni ->" + user.get().getPassword());

			List<GrantedAuthority> grantedAuthorities = user.get().getUserAuthorities().stream()
					.map(authority -> new SimpleGrantedAuthority(authority.getAuthority().getName()))
					.collect(Collectors.toList());

			return new org.springframework.security.core.userdetails.User(user.get().getUsername(), user.get().getPassword(),
					grantedAuthorities);

		} else {
			throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
		}

	}

}
