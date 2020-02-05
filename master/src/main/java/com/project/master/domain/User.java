package com.project.master.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.OneToMany;

@Inheritance
@Entity
//@org.hibernate.annotations.DiscriminatorOptions(force=true)
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(nullable = false)
	private String username;

	@Column(nullable = false)
	private String password;
	
	@Column(nullable=false) 
	String name;
	
	@Column(nullable=false)
	String lastName;
	
	@Column(nullable=false)
	String email;


	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	private Set<UserAuthority> userAuthorities = new HashSet<UserAuthority>();

	/*
	 * @Column(nullable = false) protected UserType type;
	 */

	public User(Long id, String username, String password) {
		this.id = id;
		this.username = username;
		this.password = password;
	}

	public User(String username, String password, Set<UserAuthority> userAuthorities) {
		super();
		this.username = username;
		this.password = password;
		this.userAuthorities = userAuthorities;
	}

	public User(String username, String password, String name, String lastName, String email,
			Set<UserAuthority> userAuthorities) {
		super();
		this.username = username;
		this.password = password;
		this.name = name;
		this.lastName = lastName;
		this.email = email;
		this.userAuthorities = userAuthorities;
	}
	
	public User(long id, String username, String password, String name, String lastName, String email) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.name = name;
		this.lastName = lastName;
		this.email = email;
	}

	public User() {

	}


	public User(String username) {
		super();
		this.username = username;
	}


	public User(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}




	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<UserAuthority> getUserAuthorities() {
		return userAuthorities;
	}

	public void setUserAuthorities(Set<UserAuthority> userAuthorities) {
		this.userAuthorities = userAuthorities;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	

}
