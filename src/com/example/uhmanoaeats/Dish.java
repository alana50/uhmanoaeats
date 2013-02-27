package com.example.uhmanoaeats;

/**
 * Dish --- A class that creates the Dish object
 * @author    Alana Meditz
 * @author    Joneal Altura
 */
public class Dish implements Comparable<Dish> {
	public final long id;
	public long restaurant;
	public String name;
	public double price;
	public boolean vegetarian;	
	
    /**
     * Constructor
     * @param id
     * @param name
     * @param restaurant
     * @param price
     * @param vegetarian
     * @return No return value
     */ 
	public Dish(long id, String name, long restaurant, double price, boolean vegetarian) {
		this.id =id;
		this.restaurant = restaurant;
		this.name = name;
		this.price = price;
		this.vegetarian = vegetarian;
	}

    /**
     * compareTo method to implement Comparable
     * @param another
     * @return Integer value
     */ 
	public int compareTo(Dish another) {
		return name.compareTo(another.name);
	}
}