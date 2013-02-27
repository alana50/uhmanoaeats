package com.example.uhmanoaeats;

//import java.sql.Time;
import java.util.ArrayList;

/**
 * Restaurant --- A class that creates a Restaurant object
 * @author    Alana Meditz
 * @author    Joneal Altura
 */
public class Restaurant implements Comparable<Restaurant>{
	public final long id;
	public String name = "Unknown";
	public Location location;
	//private Time openTime;
	//private Time closeTime;
	public String hours = "Unknown";
	public ArrayList<Dish> dishes = new ArrayList<Dish>();
	public ArrayList<Comment> comments = new ArrayList<Comment>();
	public double cheapestPrice; //depending on how you incorporate ArrayList<Dish> i think i can maximize its uses
	
    /**
     * Constructor
     * @param id
     * @param name
     * @param location
     * @param hours
     * @return No return value
     */ 
	public Restaurant(long id, String name, Location location, String hours) {
		this.id = id;
		this.name = name;
		this.location = location;
		this.hours = hours;
	}

    /**
     * toString to textually represent the object
     * @param No parameter value
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
	public int compareTo(Restaurant another) {
		return name.compareTo(another.name);
	}

    /**
     * Finds the cheapest price on the menu of the Restaurant
     * @param No paramater value
     * @return this.cheapestPrice
     */ 
	public double getCheapest() {
		return this.cheapestPrice;
	}
}