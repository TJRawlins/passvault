package com.passvault.spring.user;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;

@Entity
@Table(name="users",uniqueConstraints=@UniqueConstraint(name="UIDX_Username", columnNames = { "username" }))
public class User {
	// PROPERTIES
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@Column(length=30, nullable=false)
	private String username;
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@Column(length=255, nullable=false)
	private String password;
	@Column(columnDefinition="bit not null")
	private boolean admin;

	// GETTERS & SETTERS
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public boolean getAdmin() {
		return admin;
	}
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	
	

}
