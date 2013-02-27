package com.example.uhmanoaeats;

import android.provider.BaseColumns;

/**
 * FeedEntry --- A class that holds all strings used for variables in the Android app
 * @author    Alana Meditz
 * @author    Joneal Altura
 */
public class FeedEntry implements BaseColumns {
	public static final String DATABASE_NAME = "UH Manoa Eats Database";
	
	public static final String USER_TABLE_NAME = "users";
	public static final String USER_COLUMN_NAME_USERNAME = "username";
	public static final String USER_COLUMN_NAME_PASSWORD = "password";
	
	public static final String RESTAURANT_TABLE_NAME = "restaurants";
    public static final String RESTAURANT_COLUMN_NAME_NAME = "name";
    public static final String RESTAURANT_COLUMN_NAME_LOCATION = "location";
    public static final String RESTAURANT_COLUMN_NAME_HOURS = "hours";
    
    public static final String DISH_TABLE_NAME = "dishes";
    public static final String DISH_COLUMN_NAME_NAME = "name";
    public static final String DISH_COLUMN_NAME_RESTAURANT = "restaurant";
    public static final String DISH_COLUMN_NAME_PRICE = "price";
    public static final String DISH_COLUMN_NAME_VEGETARIAN = "vegetarian";
    
    public static final String COMMENT_TABLE_NAME = "comments";
    public static final String COMMENT_COLUMN_NAME_USER = "user";
    public static final String COMMENT_COLUMN_NAME_RESTAURANT = "restaurant";
    public static final String COMMENT_COLUMN_NAME_TEXT = "text";
    public static final String COMMENT_COLUMN_NAME_DATE = "date";
    
    public static final String DISH_COMMENT_TABLE_NAME = "dish_comments";
    public static final String DISH_COMMENT_COLUMN_NAME_USER = "user";
    public static final String DISH_COMMENT_COLUMN_NAME_DISH = "dish";
    public static final String DISH_COMMENT_COLUMN_NAME_TEXT = "text";
    public static final String DISH_COMMENT_COLUMN_NAME_DATE = "date";
    
    public static final String RATING_TABLE_NAME = "ratings";
    public static final String RATING_COLUMN_NAME_USER = "user";
    public static final String RATING_COLUMN_NAME_RESTAURANT = "restaurant";
    public static final String RATING_COLUMN_NAME_RATING = "rating";
    public static final String RATING_COLUMN_NAME_DATE = "date";
    
    public static final String DISH_RATING_TABLE_NAME = "dish_ratings";
    public static final String DISH_RATING_COLUMN_NAME_USER = "user";
    public static final String DISH_RATING_COLUMN_NAME_DISH = "dish";
    public static final String DISH_RATING_COLUMN_NAME_RATING = "rating";
    public static final String DISH_RATING_COLUMN_NAME_DATE = "date";
    
    public static final String LOCATION_TABLE_NAME = "locations";
    public static final String LOCATION_COLUMN_NAME_NAME = "name";
    
    private FeedEntry() {}
}
