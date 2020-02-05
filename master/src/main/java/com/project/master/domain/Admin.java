package com.project.master.domain;

import javax.persistence.Entity;

@Entity
public class Admin extends User {

	public Admin(Long id, String username, String password, String name, String lastname, String email) {
		super(id,username,password,name,lastname,email);
		//this.type = UserType.SUPERADMIN;
	}

	public Admin() {

	}
}
