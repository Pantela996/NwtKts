package com.project.master.domain;

import javax.persistence.Entity;

@Entity
public class LocationEventAdmin extends User {
	
	
	public LocationEventAdmin(Long id, String username, String password, String name, String lastname, String email) {
		super(id,username,password,name,lastname,email);
		//this.type = UserType.LOCATIONEVENTADMIN;
	}
	
	public LocationEventAdmin() {
		
	}
}
