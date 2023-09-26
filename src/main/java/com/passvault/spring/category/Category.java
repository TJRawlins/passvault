package com.passvault.spring.category;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.passvault.spring.entry.Entry;
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
	private String name = "";
	@Column(columnDefinition="datetime not null")
	private LocalDate dateCreated = LocalDate.now();
	
	// FOREIGN KEYS
	@JsonManagedReference
	@OneToMany(mappedBy="category")
	private List<Entry> entries;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="userId")
	private User user;

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
	public List<Entry> getEntries() {
		return entries;
	}
	public void setEntries(List<Entry> entries) {
		this.entries = entries;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	

}
