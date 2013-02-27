package com.example.uhmanoaeats;

/**
 * User --- A class that creates the User object
 * @author    Alana Meditz
 * @author    Joneal Altura
 */
public class User {
	public final long id;
	public String username;
	public String password;
	public boolean admin;

    /**
     * Constructor
     * @param id
     * @param username
     * @param password
     * @param admin
     * @return No return value
     */ 
	public User(long id, String username, String password, boolean admin) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.admin = admin;
	}

    /**
     * Override constructor
     * @param id
     * @param username
     * @param password
     * @return No return value
     */ 
	public User(long id, String username, String password) {
		this(id, username, password, false);
	}

    /**
     * toString to textually represent the object
     * @param No paramater value
     * @return this.username
     */ 
	public String toString() {
		return username;
	}
}
