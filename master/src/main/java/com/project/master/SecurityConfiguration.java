package com.project.master;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private MyUserDetailsService userDetailsService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		System.out.println("here");
		auth.userDetailsService(userDetailsService);
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.formLogin().successForwardUrl("/user/demo").and().authorizeRequests().antMatchers("/login").permitAll().
				antMatchers("/user/demo").hasAuthority("USER")
				//.antMatchers("/user/register").permitAll().antMatchers("/ticket/create").permitAll()
				//.antMatchers("/ticket/getAll/{username}").permitAll().antMatchers("/ticket/validate/{username}&{type}")
				//.permitAll().antMatchers("/addVehicle/getLineInfo/{type}/{line_name}").permitAll()
				//.antMatchers("/addVehicle/getLinesPerType").permitAll()
				// .antMatchers("/line/get_stations").hasAuthority("PASSENGER_ROLE")
				// .antMatchers(HttpMethod.POST, "/api/**")
				// .hasAuthority("ROLE_ADMIN") //only administrator can add and edit data
				.anyRequest().authenticated();
		// if we use AngularJS on client side
		// .and().csrf().csrfTokenRepository(csrfTokenRepository());

		// Custom JWT based authentication
		//httpSecurity.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);

	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

}
