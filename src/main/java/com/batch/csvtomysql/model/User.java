package com.batch.csvtomysql.model;

public class User {
	
	private Long userId;
	private String firstName;
	private String lastName;
	
	private String country;

	public User(Long userId, String firstName, String lastName, String country) {
		super();
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.country = country;
	}
	
	
	

	public User(String firstName, String lastName, String country) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.country = country;
	}




	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	
	
	
	
	
	

}
