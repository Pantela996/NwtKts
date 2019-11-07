package com.project.master.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class LoginController {
	
	@PreAuthorize("permitAll()")
	@RequestMapping(value = "/demo")	
	public ResponseEntity<String> login() {
		System.out.println("jjj");
		return new ResponseEntity<String>(
				"Vehicle successfully created.", HttpStatus.OK);
	}
}
	