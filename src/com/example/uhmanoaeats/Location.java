package com.example.uhmanoaeats;

/**
 * Location --- A class that creates the Location object
 * @author    Alana Meditz
 * @author    Joneal Altura
 */
public class Location implements Comparable<Location>{
	public final long id;
	public String name;

    /**
     * Constructor
     * @param id
     * @param name
     * @return No return value
     */ 
	public Location(long id, String name) {
		this.id = id;
		this.name = name;
	}

    /**
     * toString to textually represent the object
     * @param No paramater value
     * @return this.name
     */ 
	public String toString() {
		return name;
	}

    /**
     * compareTo method to implement Comparable
     * @param another
     * @return Integer value
     */ 
	public int compareTo(Location another) {
		return name.compareTo(another.name);
	}
}
