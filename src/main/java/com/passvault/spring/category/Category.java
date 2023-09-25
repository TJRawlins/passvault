package com.passvault.spring.category;

import java.time.LocalDate;

import com.passvault.spring.user.User;

import jakarta.persistence.*;

@Entity
@Table(name="categories")
public class Category {
	// PROPERTIES
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@Column(length=30)
	private String name;
	@Column(columnDefinition="datetime not null")
	private LocalDate dateCreated;
	@ManyToOne(optional=false)
	@JoinColumn(name="users")
	private User users;

	// GETTERS & SETTERS
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public LocalDate getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(LocalDate dateCreated) {
		this.dateCreated = dateCreated;
	}
	public User getUsers() {
		return users;
	}
	public void setUsers(User users) {
		this.users = users;
	}
	

}
