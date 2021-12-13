package com.codingdojo.loginandreg.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="ideas")
public class Idea {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	@NotNull
	@Size(min=5, max=50, message="Homie, this needs a name between 5 and 50")
	private String name;
	
	@NotNull
	@Size(min=5, max=50)
	private String location;
	
	@NotNull
	@Min(value=2)
	@Max(value=10)
	private int days;
	
	@NotNull
	@Size(min=5, max=200)
	private String description;
	
	
	
	@Column(updatable=false)
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date createdAt;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date updatedAt;
	
	// Add the many to one relationship
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User user;
	
	
	// Also build constructor and getters and setters after adding relation
	
	public Idea() {
		super();
	}

	public Idea(@NotNull @Size(min = 5, max = 50) String name, @NotNull @Size(min = 5, max = 50) String location,
			@NotNull @Size(min = 2, max = 10) int days, @NotNull @Size(min = 5, max = 200) String description) {
		super();
		this.name = name;
		this.location = location;
		this.days = days;
		this.description = description;
	}
	
	


	public Idea(Long id,
			@NotNull @Size(min = 5, max = 50, message = "Homie, this needs a name between 5 and 50") String name,
			@NotNull @Size(min = 5, max = 50) String location, @NotNull @Min(2) @Max(10) int days,
			@NotNull @Size(min = 5, max = 200) String description, Date createdAt, Date updatedAt, User user) {
		super();
		this.id = id;
		this.name = name;
		this.location = location;
		this.days = days;
		this.description = description;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	
	
	

	@PrePersist
	protected void onCreate() {
		this.createdAt = new Date();
	}
	
	@PreUpdate 
	protected void onUpdate() {
		this.updatedAt = new Date();
	}
}
