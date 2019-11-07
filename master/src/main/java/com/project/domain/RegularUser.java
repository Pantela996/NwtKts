package com.project.domain;

import javax.persistence.Entity;

@Entity
public class RegularUser extends User {

	public RegularUser(Long id, String username, String password) {
		super(id, username, password);
		this.type = UserType.REGULARUSER;
	}

	public RegularUser() {
		
	}
}
