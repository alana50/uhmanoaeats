package com.example.uhmanoaeats;

import java.util.Date;

/**
 * Comment --- A class that creates the Comment object used for customer experiences
 * @author    Alana Meditz
 * @author    Joneal Altura
 */
public class Comment implements Comparable<Comment> {
	public final long id;
	public User user;
	public String text;
	public Date date;

    /**
     * Override constructor
     * @param user
     * @param text
     * @return No return value
     */ 
	public Comment(User user, String text) {
		this(-1, user, text, new Date());
	}

    /**
     * Override constructor
     * @param user
     * @param text
     * @param date
     * @return No return value
     */ 
	public Comment(User user, String text, Date date) {
		this(-1, user, text, date);
	}

    /**
     * Constructor
     * @param id
     * @param user
     * @param text
     * @param date
     * @return No return value
     */ 
	public Comment(long id, User user, String text, Date date) {
		this.id = id;
		this.user = user;
		this.text = text;
		this.date = date;
	}

    /**
     * CompareTo method that compares to Comment object using their date fields
     * @param another
     * @return int
     */ 
	public int compareTo(Comment another) {
		return date.compareTo(another.date) * -1;
	}
}