package com.project.master;

import com.project.master.util.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private MyUserDetailsService userDetailsService;


	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.csrf().disable()
		.authorizeRequests()
		.antMatchers("/paypal/start").permitAll()
		.antMatchers("/paypal/pay").permitAll()
		.antMatchers("/paypal/pay/success").permitAll()
		.antMatchers("/paypal/pay/cancel").permitAll()
		.antMatchers("/paypal/greeting").permitAll()
		.antMatchers("/event/create").hasAuthority("LOCATION_AND_EVENT_ADMIN_ROLE")
		.antMatchers("/event/delete").hasAuthority("LOCATION_AND_EVENT_ADMIN_ROLE")
		.antMatchers("/event/update").hasAuthority("LOCATION_AND_EVENT_ADMIN_ROLE")
		.antMatchers("/event/all").hasAuthority("LOCATION_AND_EVENT_ADMIN_ROLE")
		.antMatchers("/event/one").hasAuthority("LOCATION_AND_EVENT_ADMIN_ROLE")
		.antMatchers("/event/myEvents").hasAuthority("LOCATION_AND_EVENT_ADMIN_ROLE")
		//.antMatchers("/location/create").hasAuthority("LOCATION_AND_EVENT_ADMIN_ROLE")
		.antMatchers("/location/create").permitAll()
		.antMatchers("/location/delete").hasAuthority("LOCATION_AND_EVENT_ADMIN_ROLE")
		.antMatchers("/location/update").hasAuthority("LOCATION_AND_EVENT_ADMIN_ROLE")
		.antMatchers("/location/all").hasAuthority("LOCATION_AND_EVENT_ADMIN_ROLE")
		.antMatchers("/location/one").hasAuthority("LOCATION_AND_EVENT_ADMIN_ROLE")
		.antMatchers("/location/myLocations").hasAuthority("LOCATION_AND_EVENT_ADMIN_ROLE")
		.antMatchers("/login").permitAll().anyRequest()
				.authenticated().and().exceptionHandling().and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
		httpSecurity.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);

	}
	
	@Autowired
	public void configureAuthentication(
			AuthenticationManagerBuilder authenticationManagerBuilder)
			throws Exception {
		
		authenticationManagerBuilder
				.userDetailsService(this.userDetailsService).passwordEncoder(
						passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	@Bean
	public JwtRequestFilter authenticationTokenFilterBean()
			throws Exception {
		JwtRequestFilter authenticationTokenFilter = new JwtRequestFilter();
		authenticationTokenFilter
				.setAuthenticationManager(authenticationManagerBean());
		return authenticationTokenFilter;
	}



	/*
	@Bean
	public PasswordEncoder passwordEncoder() {
		System.out.println();
		System.out.println(NoOpPasswordEncoder.getInstance().toString());
		return NoOpPasswordEncoder.getInstance();
	}
	*/

}
