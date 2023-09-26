package com.passvault.spring.entry;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.passvault.spring.category.Category;

import jakarta.persistence.*;

@Entity
@Table(name="entries")
public class Entry {
	
	// PROPERTIES
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@Column(length=30)
	private String name = "";
	@Column(length=80)
	private String description = "";
	@Column(length=30)
	private String username = "";
	@Column(length=100)
	private String password = "";
	@Column(length=30)
	private String url = "";
	@Column(columnDefinition="datetime not null")
	private LocalDate dateCreated = LocalDate.now();
	
	@JsonBackReference
	@ManyToOne(optional=false)
	@JoinColumn(name="categoryId")
	private Category category;
	


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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public LocalDate getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(LocalDate dateCreated) {
		this.dateCreated = dateCreated;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	
	

}
