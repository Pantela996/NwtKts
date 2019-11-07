package com.project.domain;

import javax.persistence.Entity;

@Entity
public class LocationEventAdmin extends User {
	
	
	public LocationEventAdmin(Long id, String username, String password) {
		super(id,username,password);
		this.type = UserType.LOCATIONEVENTADMIN;
	}
	
	public LocationEventAdmin() {
		
	}
}
