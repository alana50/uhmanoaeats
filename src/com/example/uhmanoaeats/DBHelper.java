package com.example.uhmanoaeats;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * DBHelper --- A class that builds the database for the app
 * @author    Alana Meditz
 * @author    Joneal Altura
 */
public class DBHelper extends SQLiteOpenHelper {
	private static final int DATABASE_VERSION = 11;
	private static final SimpleDateFormat df = new SimpleDateFormat();
	
	// Create table statements
	private static final String USER_TABLE_CREATE = "CREATE TABLE "
			+ FeedEntry.USER_TABLE_NAME + " ("
			+ FeedEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ FeedEntry.USER_COLUMN_NAME_USERNAME + " TEXT UNIQUE, "
			+ FeedEntry.USER_COLUMN_NAME_PASSWORD + " TEXT);";
	
	private static final String RESTAURANT_TABLE_CREATE = "CREATE TABLE "
			+ FeedEntry.RESTAURANT_TABLE_NAME + " ("
			+ FeedEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ FeedEntry.RESTAURANT_COLUMN_NAME_NAME + " TEXT, "
			+ FeedEntry.RESTAURANT_COLUMN_NAME_LOCATION + " INT, "
			+ FeedEntry.RESTAURANT_COLUMN_NAME_HOURS + " TEXT);";
	
	private static final String DISH_TABLE_CREATE = "CREATE TABLE "
			+ FeedEntry.DISH_TABLE_NAME + " ("
			+ FeedEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ FeedEntry.DISH_COLUMN_NAME_NAME + " TEXT, "
			+ FeedEntry.DISH_COLUMN_NAME_RESTAURANT + " INT, "
			+ FeedEntry.DISH_COLUMN_NAME_PRICE + " DECIMAL, "
			+ FeedEntry.DISH_COLUMN_NAME_VEGETARIAN + " TINYINT);";
	
	private static final String COMMENT_TABLE_CREATE = "CREATE TABLE "
			+ FeedEntry.COMMENT_TABLE_NAME + " ("
			+ FeedEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ FeedEntry.COMMENT_COLUMN_NAME_USER + " INT, "
			+ FeedEntry.COMMENT_COLUMN_NAME_RESTAURANT + " INT, "
			+ FeedEntry.COMMENT_COLUMN_NAME_TEXT + " TEXT, "
			+ FeedEntry.COMMENT_COLUMN_NAME_DATE + " TEXT);";
	
	private static final String DISH_COMMENT_TABLE_CREATE = "CREATE TABLE "
			+ FeedEntry.DISH_COMMENT_TABLE_NAME + " ("
			+ FeedEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ FeedEntry.DISH_COMMENT_COLUMN_NAME_USER + " INT, "
			+ FeedEntry.DISH_COMMENT_COLUMN_NAME_DISH + " INT, "
			+ FeedEntry.DISH_COMMENT_COLUMN_NAME_TEXT + " TEXT, "
			+ FeedEntry.DISH_COMMENT_COLUMN_NAME_DATE + " TEXT);";
	
	private static final String RATING_TABLE_CREATE = "CREATE TABLE "
			+ FeedEntry.RATING_TABLE_NAME + " ("
			+ FeedEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ FeedEntry.RATING_COLUMN_NAME_USER + " INT, "
			+ FeedEntry.RATING_COLUMN_NAME_RESTAURANT + " INT, "
			+ FeedEntry.RATING_COLUMN_NAME_RATING + " TINYINT, "
			+ FeedEntry.RATING_COLUMN_NAME_DATE + " TEXT);";
	
	private static final String DISH_RATING_TABLE_CREATE = "CREATE TABLE "
			+ FeedEntry.DISH_RATING_TABLE_NAME + " ("
			+ FeedEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ FeedEntry.DISH_RATING_COLUMN_NAME_USER + " INT, "
			+ FeedEntry.DISH_RATING_COLUMN_NAME_DISH + " INT, "
			+ FeedEntry.DISH_RATING_COLUMN_NAME_RATING + " TINYINT, "
			+ FeedEntry.DISH_RATING_COLUMN_NAME_DATE + " TEXT);";
	
	private static final String LOCATION_TABLE_CREATE = "CREATE TABLE "
			+ FeedEntry.LOCATION_TABLE_NAME + " ("
			+ FeedEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ FeedEntry.LOCATION_COLUMN_NAME_NAME + " TEXT);";

    /**
     * Constructor
     * @param context
     * @return No return value
     */ 
    public DBHelper(Context context) {
        super(context, FeedEntry.DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Required when activity is created to call its super class' method that it overrides
     * @param db
     * @return No return value
     */ 
    @Override
    public void onCreate(SQLiteDatabase db) {
    	db.execSQL(USER_TABLE_CREATE);
        db.execSQL(RESTAURANT_TABLE_CREATE);
        db.execSQL(DISH_TABLE_CREATE);
        db.execSQL(COMMENT_TABLE_CREATE);
        db.execSQL(DISH_COMMENT_TABLE_CREATE);
        db.execSQL(RATING_TABLE_CREATE);
        db.execSQL(DISH_RATING_TABLE_CREATE);
        db.execSQL(LOCATION_TABLE_CREATE);
        
        // Initial data population
        addUser(db, "a", "a");
        Location location = addLocation(db, "Sustainability Courtyard");
        Restaurant govindas = addRestaurant(db, "Govinda's", location, "M-F 10-2");
        addDish(db, "Halava", govindas, 0.75, true);
        addDish(db, "Lemonade", govindas, 1.25, true);
        addDish(db, "Curry", govindas, 3.50, true);
        Restaurant daSpot = addRestaurant(db, "Da Spot", location, "M-F 8-3");
        addDish(db, "Smoothie", daSpot, 3.75, true);
        location = addLocation(db, "Paradise Palms");
        Restaurant panda = addRestaurant(db, "Panda Express", location, "M-F 10-2");
        Restaurant landl = addRestaurant(db, "L&L", location, "M-F 10-2");
        Restaurant dominos = addRestaurant(db, "Dominos", location, "M-F 10-2");
        addDish(db, "Orange Chicken", panda, 4.25, false);
        addDish(db, "Beef Broccoli", panda, 4.25, false);
        addDish(db, "Loco Moco", landl, 5.75, false);
        addDish(db, "Spam Musubi", landl, 2.25, false);
        addDish(db, "Personal Pan Pizza", dominos, 5.50, false);
        location = addLocation(db, "Campus Center");
        Restaurant subway = addRestaurant(db, "Subway", location, "M-F 8-4");
        Restaurant tacoBell = addRestaurant(db, "Taco Bell", location, "M-F 10-2");
        Restaurant pizzaHut = addRestaurant(db, "Pizza Hut", location, "M-F 10-2");
        Restaurant jamba = addRestaurant(db, "Jamba Juice", location, "M-F 8-4");
        Restaurant starbucks = addRestaurant(db, "Starbucks", location, "M-F 7-4");
    }

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		db.execSQL("DROP TABLE IF EXISTS " + FeedEntry.USER_TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + FeedEntry.RESTAURANT_TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + FeedEntry.COMMENT_TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + FeedEntry.DISH_COMMENT_TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + FeedEntry.RATING_TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + FeedEntry.DISH_RATING_TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + FeedEntry.DISH_TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + FeedEntry.LOCATION_TABLE_NAME);
    	onCreate(db);
	}
	
    /**
     * Write functions
     * 
     * Adds new user to the database
     * @param context
     * @param username
     * @param password
     * @return User object
     */ 
	public static User addUser(Context context, String username, String password) {
		SQLiteDatabase db = (new DBHelper(context)).getWritableDatabase();
		return addUser(db, username, password);
	}

    /**
     * Adds new user to the database
     * @param db
     * @param username
     * @param password
     * @return User object
     */ 
	public static User addUser(SQLiteDatabase db, String username, String password) {
		ContentValues values = new ContentValues();
        values.put(FeedEntry.USER_COLUMN_NAME_USERNAME, username);
        values.put(FeedEntry.USER_COLUMN_NAME_PASSWORD, password);
        return getUser(db, db.insert(FeedEntry.USER_TABLE_NAME, null, values));
	}

    /**
     * Adds new restaurant to the database
     * @param context
     * @param name
     * @param location
     * @param hours
     * @return Restaurant object
     */ 
	public static Restaurant addRestaurant(Context context, String name, Location location, String hours) {
    	SQLiteDatabase db = (new DBHelper(context)).getWritableDatabase();
		return addRestaurant(db, name, location, hours);
	}

    /**
     * Adds new restaurant to the database
     * @param db
     * @param name
     * @param location
     * @param hours
     * @return Restaurant object
     */ 
	public static Restaurant addRestaurant(SQLiteDatabase db, String name, Location location, String hours) {
		ContentValues values = new ContentValues();
        values.put(FeedEntry.RESTAURANT_COLUMN_NAME_NAME, name);
        values.put(FeedEntry.RESTAURANT_COLUMN_NAME_LOCATION, location.id);
        values.put(FeedEntry.RESTAURANT_COLUMN_NAME_HOURS, hours);
        return getRestaurant(db, db.insert(FeedEntry.RESTAURANT_TABLE_NAME, null, values));
	}

    /**
     * Adds new user to the database
     * @param context
     * @param name
     * @return Location object
     */ 
	public static Location addLocation(Context context, String name) {
		SQLiteDatabase db = (new DBHelper(context)).getWritableDatabase();
		return addLocation(db, name);
	}
	
    /**
     * Adds new location to the database
     * @param db
     * @param name
     * @return Location object
     */ 
	public static Location addLocation(SQLiteDatabase db, String name) {
		ContentValues values = new ContentValues();
		values.put(FeedEntry.LOCATION_COLUMN_NAME_NAME, name);
		return getLocation(db, db.insert(FeedEntry.LOCATION_TABLE_NAME, null, values));
	}

    /**
     * Adds new dish to a restaurant in the database
     * @param context
     * @param name
     * @param restaurant
     * @param price
     * @param vegetarian
     * @return No return value
     */ 
	public static void addDish(Context context, String name, Restaurant restaurant, Double price, boolean vegetarian) {
		SQLiteDatabase db = (new DBHelper(context)).getWritableDatabase();
		addDish(db, name, restaurant, price, vegetarian);
	}

    /**
     * Adds new dish to a restaurant in the database
     * @param db
     * @param name
     * @param restaurant
     * @param price
     * @param vegetarian
     * @return No return value
     */ 
	public static void addDish(SQLiteDatabase db, String name, Restaurant restaurant, Double price, boolean vegetarian) {
		ContentValues values = new ContentValues();
        values.put(FeedEntry.DISH_COLUMN_NAME_NAME, name);
        values.put(FeedEntry.DISH_COLUMN_NAME_RESTAURANT, restaurant.id);
        values.put(FeedEntry.DISH_COLUMN_NAME_PRICE, price);
        if(vegetarian) {
        	values.put(FeedEntry.DISH_COLUMN_NAME_VEGETARIAN, 1);
        } else {
        	values.put(FeedEntry.DISH_COLUMN_NAME_VEGETARIAN, 0);
        }
        db.insert(FeedEntry.DISH_TABLE_NAME, null, values);
	}

    /**
     * Adds new comment to a restaurant in the database
     * @param context
     * @param user
     * @param restaurant
     * @param text
     * @param date
     * @return No return value
     */ 
	public static void addComment(Context context, User user, Restaurant restaurant, String text, Date date) {
		SQLiteDatabase db = (new DBHelper(context)).getWritableDatabase();
		addComment(db, user, restaurant, text, date);
	}

    /**
     * Adds new comment to a restaurant in the database
     * @param db
     * @param user
     * @param restaurant
     * @param text
     * @param date
     * @return No return value
     */ 
	public static void addComment(SQLiteDatabase db, User user, Restaurant restaurant, String text, Date date) {
		ContentValues values = new ContentValues();
		values.put(FeedEntry.COMMENT_COLUMN_NAME_USER, user.id);
		values.put(FeedEntry.COMMENT_COLUMN_NAME_RESTAURANT, restaurant.id);
		values.put(FeedEntry.COMMENT_COLUMN_NAME_TEXT, text);
		values.put(FeedEntry.COMMENT_COLUMN_NAME_DATE, df.format(date));
		db.insert(FeedEntry.COMMENT_TABLE_NAME, null, values);
	}

    /**
     * Adds new comment to a dish in the database
     * @param context
     * @param user
     * @param dish
     * @param text
     * @param date
     * @return No return value
     */ 
	public static void addComment(Context context, User user, Dish dish, String text, Date date) {
		SQLiteDatabase db = (new DBHelper(context)).getWritableDatabase();
		addComment(db, user, dish, text, date);
	}

    /**
     * Adds new comment to a dish in the database
     * @param db
     * @param user
     * @param dish
     * @param text
     * @param date
     * @return No return value
     */ 
	public static void addComment(SQLiteDatabase db, User user, Dish dish, String text, Date date) {
		ContentValues values = new ContentValues();
		values.put(FeedEntry.DISH_COMMENT_COLUMN_NAME_USER, user.id);
		values.put(FeedEntry.DISH_COMMENT_COLUMN_NAME_DISH, dish.id);
		values.put(FeedEntry.DISH_COMMENT_COLUMN_NAME_TEXT, text);
		values.put(FeedEntry.DISH_COMMENT_COLUMN_NAME_DATE, df.format(date));
		db.insert(FeedEntry.DISH_COMMENT_TABLE_NAME, null, values);
	}

    /**
     * Adds new rating to a restaurant in the database
     * @param context
     * @param user
     * @param restaurant
     * @param rating
     * @param date
     * @return No return value
     */ 
	public static void addRating(Context context, User user, Restaurant restaurant, int rating, Date date) {
		SQLiteDatabase db = (new DBHelper(context)).getWritableDatabase();
		addRating(db, user, restaurant, rating, date);
	}

    /**
     * Adds new rating to a restaurant in the database
     * @param db
     * @param user
     * @param restaurant
     * @param rating
     * @param date
     * @return No return value
     */ 
	public static void addRating(SQLiteDatabase db, User user, Restaurant restaurant, int rating, Date date) {
		ContentValues values = new ContentValues();
		values.put(FeedEntry.RATING_COLUMN_NAME_USER, user.id);
		values.put(FeedEntry.RATING_COLUMN_NAME_RESTAURANT, restaurant.id);
		values.put(FeedEntry.RATING_COLUMN_NAME_RATING, rating);
		values.put(FeedEntry.RATING_COLUMN_NAME_DATE, df.format(date));
		db.insert(FeedEntry.RATING_TABLE_NAME, null, values);
	}

    /**
     * Adds new rating to a dish in the database
     * @param context
     * @param user
     * @param dish
     * @param rating
     * @param date
     * @return No return value
     */ 
	public static void addRating(Context context, User user, Dish dish, int rating, Date date) {
		SQLiteDatabase db = (new DBHelper(context)).getWritableDatabase();
		addRating(db, user, dish, rating, date);
	}

    /**
     * Adds new rating to a dish in the database
     * @param db
     * @param user
     * @param dish
     * @param rating
     * @param date
     * @return No return value
     */ 
	public static void addRating(SQLiteDatabase db, User user, Dish dish, int rating, Date date) {
		ContentValues values = new ContentValues();
		values.put(FeedEntry.DISH_RATING_COLUMN_NAME_USER, user.id);
		values.put(FeedEntry.DISH_RATING_COLUMN_NAME_DISH, dish.id);
		values.put(FeedEntry.DISH_RATING_COLUMN_NAME_RATING, rating);
		values.put(FeedEntry.DISH_RATING_COLUMN_NAME_DATE, df.format(date));
		db.insert(FeedEntry.DISH_RATING_TABLE_NAME, null, values);
	}
	
    /**
     * Read functions
     * 
     * Gets a location from the database
     * @param context
     * @param name
     * @return Location object
     */ 
	public static Location getLocation(Context context, String name) {
		SQLiteDatabase db = (new DBHelper(context)).getReadableDatabase();
		return getLocation(db, name);
	}

    /**
     * Gets a location from the database
     * @param db
     * @param name
     * @return Location object
     */ 
	public static Location getLocation(SQLiteDatabase db, String name) {
		Cursor c = db.rawQuery(
				"SELECT * FROM " + FeedEntry.LOCATION_TABLE_NAME + " WHERE "
						+ FeedEntry.LOCATION_COLUMN_NAME_NAME + "='" + name.replace("'", "''") + "';", null);
		if(c.moveToFirst()) {
			return new Location(c.getLong(0), c.getString(1));
		} else {
			return null;
		}
	}

    /**
     * Gets a location from the database
     * @param context
     * @param id
     * @return Location object
     */ 
	public static Location getLocation(Context context, long id) {
		SQLiteDatabase db = (new DBHelper(context)).getReadableDatabase();
		return getLocation(db, id);
	}

    /**
     * Gets a location from the database
     * @param db
     * @param id
     * @return Location object
     */ 
	public static Location getLocation(SQLiteDatabase db, long id) {
		Cursor c = db.rawQuery(
				"SELECT * FROM " + FeedEntry.LOCATION_TABLE_NAME + " WHERE "
						+ FeedEntry._ID + "='" + id + "';", null);
		if(c.moveToFirst()) {
			return new Location(c.getLong(0), c.getString(1));
		} else {
			return null;
		}
	}

    /**
     * Gets a dish from the database
     * @param context
     * @param id
     * @return Dish object
     */ 
	public static Dish getDish(Context context, long id) {
		SQLiteDatabase db = (new DBHelper(context)).getReadableDatabase();
		return getDish(db, id);
	}

    /**
     * Gets a dish from the database
     * @param db
     * @param id
     * @return Dish object
     */ 
	public static Dish getDish(SQLiteDatabase db, long id){
		Cursor c = db.rawQuery(
				"SELECT * FROM " + FeedEntry.DISH_TABLE_NAME + " WHERE "
						+ FeedEntry._ID + "='" + id + "';", null);
		if(c.moveToFirst()) {
			if(c.getInt(4) == 1) {
				return new Dish(c.getLong(0), c.getString(1), c.getLong(2), c.getDouble(3), true);
			} else {
				return new Dish(c.getLong(0), c.getString(1), c.getLong(2), c.getDouble(3), false);
			}
		} else {
			return null;
		}
	}

    /**
     * Gets a list of restaurants from the database
     * @param context
     * @return ArrayList of Restaurant
     */ 
	public static ArrayList<Restaurant> getRestaurants(Context context) {
		SQLiteDatabase db = (new DBHelper(context)).getReadableDatabase();
		return getRestaurants(db);
	}

    /**
     * Gets a list of restaurants from the database
     * @param db
     * @return ArrayList of Restaurant
     */ 
	public static ArrayList<Restaurant> getRestaurants(SQLiteDatabase db) {
		ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();
		Cursor c = db.rawQuery(
				"SELECT * FROM " + FeedEntry.RESTAURANT_TABLE_NAME + ";", null);		
    	if(c.moveToFirst()) {
    		do {
    			restaurants.add(new Restaurant(c.getInt(0), c.getString(1), getLocation(db, c.getLong(2)), c.getString(3)));
    		} while(c.moveToNext());
    	}
    	return restaurants;
	}

    /**
     * Gets a list of restaurants from the database
     * @param context
     * @param location
     * @return ArrayList of Restaurant
     */ 
	public static ArrayList<Restaurant> getRestaurants(Context context, Location location) {
		SQLiteDatabase db = (new DBHelper(context)).getReadableDatabase();
		return getRestaurants(db, location.id);
	}

    /**
     * Gets a list of restaurants from the database
     * @param db
     * @param location
     * @return ArrayList of Restaurant
     */ 
	public static ArrayList<Restaurant> getRestaurants(SQLiteDatabase db, long location) {
		ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();
		Cursor c = db.rawQuery(
				"SELECT * FROM " + FeedEntry.RESTAURANT_TABLE_NAME + 
				" WHERE " + FeedEntry.RESTAURANT_COLUMN_NAME_LOCATION + "='" + location + "';", null);		
    	if(c.moveToFirst()) {
    		do {
    			restaurants.add(new Restaurant(c.getInt(0), c.getString(1), getLocation(db, c.getLong(2)), c.getString(3)));
    		} while(c.moveToNext());
    	}
    	return restaurants;
	}

    /**
     * Gets a restaurant from the database
     * @param context
     * @param id
     * @return Restaurant object
     */ 
	public static Restaurant getRestaurant(Context context, long id) {
		SQLiteDatabase db = (new DBHelper(context)).getReadableDatabase();
		return getRestaurant(db, id);
    }

    /**
     * Gets a restaurant from the database
     * @param db
     * @param id
     * @return Restaurant object
     */ 
	public static Restaurant getRestaurant(SQLiteDatabase db, long id) {
		Cursor c = db.rawQuery(
				"SELECT * FROM " + FeedEntry.RESTAURANT_TABLE_NAME + " WHERE "
						+ FeedEntry._ID + "='" + id + "';", null);
		if(c.moveToFirst()) {
			return new Restaurant(c.getInt(0), c.getString(1), getLocation(db, c.getLong(2)), c.getString(3));
		} else {
			return null;
		}
	}

    /**
     * Gets a restaurant from the database
     * @param context
     * @param name
     * @return Restaurant object
     */ 
	public static Restaurant getRestaurant(Context context, String name) {
		SQLiteDatabase db = (new DBHelper(context)).getReadableDatabase();
		return getRestaurant(db, name);
    }

    /**
     * Gets a restaurant from the database
     * @param db
     * @param name
     * @return Restaurant object
     */ 
	public static Restaurant getRestaurant(SQLiteDatabase db, String name) {
		Cursor c = db.rawQuery(
				"SELECT * FROM " + FeedEntry.RESTAURANT_TABLE_NAME + " WHERE "
						+ FeedEntry.RESTAURANT_COLUMN_NAME_NAME + "='" + name.replace("'", "''") + "';", null);
		if(c.moveToFirst()) {
			return new Restaurant(c.getInt(0), c.getString(1), getLocation(db, c.getLong(2)), c.getString(3));
		} else {
			return null;
		}
	}

    /**
     * Gets a user from the database
     * @param context
     * @param id
     * @return Restaurant object
     */ 
    public static User getUser(Context context, long id) {
    	SQLiteDatabase db = (new DBHelper(context)).getReadableDatabase();
		return getUser(db, id);
    }

    /**
     * Gets a user from the database
     * @param db
     * @param id
     * @return Restaurant object
     */ 
    public static User getUser(SQLiteDatabase db, long id) {
    	Cursor c = db.rawQuery(
				"SELECT * FROM " + FeedEntry.USER_TABLE_NAME + " WHERE "
						+ FeedEntry._ID + "='" + id + "';", null);
		if(c.moveToFirst()) {
			return new User(c.getInt(0), c.getString(1), c.getString(2));
		} else {
			return null;
		}
    }

    /**
     * Gets a list of dishes from a restaurant in the database
     * @param context
     * @param restaurant
     * @return ArrayList of Dish
     */ 
    public static ArrayList<Dish> getDishes(Context context, Restaurant restaurant) {
    	ArrayList<Dish> result = new ArrayList<Dish>();
    	Cursor c = (new DBHelper(context)).getReadableDatabase().rawQuery(
				"SELECT * FROM " + FeedEntry.DISH_TABLE_NAME + " WHERE "
						+ FeedEntry.DISH_COLUMN_NAME_RESTAURANT + "='" + restaurant.id + "';", null);
		if(c.moveToFirst()) {
			do {
				if(c.getInt(4) == 1) {
					result.add(new Dish(c.getInt(0), c.getString(1), c.getInt(2), c.getDouble(3), true));
				} else {
					result.add(new Dish(c.getInt(0), c.getString(1), c.getInt(2), c.getDouble(3), false));
				}
			} while (c.moveToNext());
		}
		return result;
    }

    /**
     * Gets a list of dishes from a restaurant in the database
     * @param context
     * @return ArrayList of Dish
     */ 
    public static ArrayList<Dish> getDishes(Context context) {
    	ArrayList<Dish> result = new ArrayList<Dish>();
    	Cursor c = (new DBHelper(context)).getReadableDatabase().rawQuery(
				"SELECT * FROM " + FeedEntry.DISH_TABLE_NAME + ";", null);
		if(c.moveToFirst()) {
			do {
				if(c.getInt(4) == 1) {
					result.add(new Dish(c.getLong(0), c.getString(1), c.getLong(2), c.getDouble(3), true));
				} else {
					result.add(new Dish(c.getLong(0), c.getString(1), c.getLong(2), c.getDouble(3), false));
				}
			} while (c.moveToNext());
		}
		return result;
    }

    /**
     * Gets a list of comments of a dish from the database
     * @param context
     * @param dish
     * @return ArrayList of Comment
     */ 
    public static ArrayList<Comment> getComments(Context context, Dish dish) {
		ArrayList<Comment> result = new ArrayList<Comment>();
		Cursor c = (new DBHelper(context)).getReadableDatabase().rawQuery(
				"SELECT * FROM " + FeedEntry.DISH_COMMENT_TABLE_NAME + " WHERE "
						+ FeedEntry.DISH_COMMENT_COLUMN_NAME_DISH + "='" + dish.id + "';", null);
		if(c.moveToFirst()) {
			do {
				try {
					result.add(new Comment(c.getLong(0), getUser(context, c.getLong(1)),
							c.getString(3), df.parse(c.getString(4))));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} while (c.moveToNext());
		}
		return result;
    }

    /**
     * Gets a list of comments of a restaurant from the database
     * @param context
     * @param restaurant
     * @return ArrayList of Comment
     */ 
    public static ArrayList<Comment> getComments(Context context, Restaurant restaurant) {
    	ArrayList<Comment> result = new ArrayList<Comment>();
		Cursor c = (new DBHelper(context)).getReadableDatabase().rawQuery(
				"SELECT * FROM " + FeedEntry.COMMENT_TABLE_NAME + " WHERE "
						+ FeedEntry.COMMENT_COLUMN_NAME_RESTAURANT + "='" + restaurant.id + "';", null);
		if(c.moveToFirst()) {
			do {
				try {
					result.add(new Comment(c.getLong(0), DBHelper.getUser(context, c.getLong(1)),
							c.getString(3), df.parse(c.getString(4))));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} while (c.moveToNext());
		}
		return result;
    }

    /**
     * Gets a user from the database
     * @param context
     * @param username
     * @return User object
     */ 
    public static User getUser(Context context, String username) {
		SQLiteDatabase db = (new DBHelper(context)).getReadableDatabase();
		return getUser(db, username);
    }

    /**
     * Gets a user from the database
     * @param db
     * @param username
     * @return User object
     */ 
    public static User getUser(SQLiteDatabase db, String username) {
    	Cursor c = db.rawQuery(
				"SELECT * FROM " + FeedEntry.USER_TABLE_NAME + " WHERE "
						+ FeedEntry.USER_COLUMN_NAME_USERNAME + "='" + username + "';", null);
		if(c.moveToFirst()) {
			return new User(c.getLong(0), c.getString(1), c.getString(2));
		} else {
			return null;
		}
    }

    /**
     * Gets a list of locations from the database
     * @param context
     * @return ArrayList of Location
     */ 
    public static ArrayList<Location> getLocations(Context context) {
    	Cursor c = (new DBHelper(context)).getReadableDatabase().rawQuery(
				"SELECT * " +
				"FROM " + FeedEntry.LOCATION_TABLE_NAME, null);
    	ArrayList<Location> result = new ArrayList<Location>();
		if(c.moveToFirst()) {
			do {
				result.add(new Location(c.getLong(0), c.getString(1)));
			} while (c.moveToNext());
		}
		return result;
    }

    /**
     * Determines if a restaurant has been rated
     * @param context
     * @param user
     * @param restaurant
     * @return boolean value
     */ 
    public static boolean hasRated(Context context, User user, Restaurant restaurant) {
    	Cursor c = (new DBHelper(context)).getReadableDatabase().rawQuery(
				"SELECT * FROM " + FeedEntry.RATING_TABLE_NAME + 
				" WHERE " + FeedEntry.RATING_COLUMN_NAME_USER + "='" + user.id + 
				"' AND " + FeedEntry.RATING_COLUMN_NAME_RESTAURANT + "='" + restaurant.id + "';", null);
    	return c.moveToFirst();
    }

    /**
     * Determines if a dish has been rated
     * @param context
     * @param user
     * @param dish
     * @return boolean value
     */ 
    public static boolean hasRated(Context context, User user, Dish dish) {
    	Cursor c = (new DBHelper(context)).getReadableDatabase().rawQuery(
				"SELECT * FROM " + FeedEntry.DISH_RATING_TABLE_NAME + 
				" WHERE " + FeedEntry.DISH_RATING_COLUMN_NAME_USER + "='" + user.id + 
				"' AND " + FeedEntry.DISH_RATING_COLUMN_NAME_DISH + "='" + dish.id + "';", null);
    	return c.moveToFirst();
    }

    /**
     * Determines average rating of a restaurant
     * @param context
     * @param restaurant
     * @return double value
     */ 
    public static double getAvgRating(Context context, Restaurant restaurant) {
    	Cursor c = (new DBHelper(context)).getReadableDatabase().rawQuery(
				"SELECT " + FeedEntry.RATING_COLUMN_NAME_RATING + " FROM " + FeedEntry.RATING_TABLE_NAME + " WHERE "
						+ FeedEntry.RATING_COLUMN_NAME_RESTAURANT + "='" + restaurant.id + "';", null);
		if(c.moveToFirst()) {
			int count = 0;
			double sum = 0;
			do {
				sum += c.getInt(0);
				count++;
			} while (c.moveToNext());
			return sum/count;
		} else {
			return -1;
		}
    }

    /**
     * Determines average rating of a dish
     * @param context
     * @param dish
     * @return double value
     */ 
    public static double getAvgRating(Context context, Dish dish) {
    	Cursor c = (new DBHelper(context)).getReadableDatabase().rawQuery(
				"SELECT " + FeedEntry.DISH_RATING_COLUMN_NAME_RATING + " FROM " + FeedEntry.DISH_RATING_TABLE_NAME + " WHERE "
						+ FeedEntry.DISH_RATING_COLUMN_NAME_DISH + "='" + dish.id + "';", null);
		if(c.moveToFirst()) {
			int count = 0;
			double sum = 0;
			do {
				sum += c.getInt(0);
				count++;
			} while (c.moveToNext());
			return sum/count;
		} else {
			return -1;
		}
    }

    /**
     * Gets the rating of a restaurant
     * @param context
     * @param user
     * @param restaurant
     * @return int value
     */ 
    public static int getRating(Context context, User user, Restaurant restaurant) {
    	Cursor c = (new DBHelper(context)).getReadableDatabase().rawQuery(
				"SELECT * FROM " + FeedEntry.RATING_TABLE_NAME + 
				" WHERE " + FeedEntry.RATING_COLUMN_NAME_USER + "='" + user.id + 
				"' AND " + FeedEntry.RATING_COLUMN_NAME_RESTAURANT + "='" + restaurant.id + "';", null);
    	if(c.moveToFirst()) {
    		return c.getInt(3);
    	} else {
    		return -1;
    	}
    }

    /**
     * Gets the rating of a dish
     * @param context
     * @param user
     * @param dish
     * @return int value
     */ 
    public static int getRating(Context context, User user, Dish dish) {
    	Cursor c = (new DBHelper(context)).getReadableDatabase().rawQuery(
				"SELECT * FROM " + FeedEntry.DISH_RATING_TABLE_NAME + 
				" WHERE " + FeedEntry.DISH_RATING_COLUMN_NAME_USER + "='" + user.id + 
				"' AND " + FeedEntry.DISH_RATING_COLUMN_NAME_DISH + "='" + dish.id + "';", null);
    	if(c.moveToFirst()) {
    		return c.getInt(3);
    	} else {
    		return -1;
    	}
    }
    
    /**
     * Update functions
     * 
     * Updates the rating of a restaurant
     * @param context
     * @param user
     * @param restaurant
     * @param rating
     * @param date
     * @return No return value
     */ 
    public static void updateRating(Context context, User user, Restaurant restaurant, int rating, Date date) {
    	ContentValues values = new ContentValues();
    	values.put(FeedEntry.RATING_COLUMN_NAME_RATING, rating);
    	values.put(FeedEntry.RATING_COLUMN_NAME_DATE, date.toString());
    	(new DBHelper(context)).getWritableDatabase().update(FeedEntry.RATING_TABLE_NAME, values,
    			FeedEntry.RATING_COLUMN_NAME_RESTAURANT + "='" + restaurant.id + 
				"' AND " + FeedEntry.RATING_COLUMN_NAME_USER + "='" + user.id + "'", null);
    }

    /**
     * Updates the rating of a dish
     * @param context
     * @param user
     * @param restaurant
     * @param rating
     * @param date
     * @return No return value
     */ 
    public static void updateRating(Context context, User user, Dish dish, int rating, Date date) {
    	ContentValues values = new ContentValues();
    	values.put(FeedEntry.DISH_RATING_COLUMN_NAME_RATING, rating);
    	values.put(FeedEntry.DISH_RATING_COLUMN_NAME_DATE, date.toString());
    	(new DBHelper(context)).getWritableDatabase().update(FeedEntry.DISH_RATING_TABLE_NAME, values,
    			FeedEntry.DISH_RATING_COLUMN_NAME_DISH + "='" + dish.id + 
				"' AND " + FeedEntry.DISH_RATING_COLUMN_NAME_USER + "='" + user.id + "'", null);
    }
}
