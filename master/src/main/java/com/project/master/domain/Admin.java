package com.project.master.domain;

import javax.persistence.Entity;

@Entity
public class Admin extends User {

	public Admin(Long id, String username, String password) {
		super(id, username, password);
		this.type = UserType.SUPERADMIN;
	}

	public Admin() {

	}
}
