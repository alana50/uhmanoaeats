package com.example.uhmanoaeats;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * RestaurantDetailtActivity --- An activity that allows the user to interact with a Restaurant
 * @author    Alana Meditz
 * @author    Joneal Altura
 */
public class RestaurantDetailActivity extends DetailActivity {
	private Restaurant restaurant;

    /**
     * Used for the Restaurants rating
     * @param No parameter value
     * @return No return value
     */ 
	static class RatingInfo {
    	public static int ratingPos;
    }

    /**
     * Required when activity is created to call its super class' method that it overrides
     * @param savedInstanceState
     * @return No return value
     */ 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_detail);
	}

    /**
     * Called after onCreate() when the activity is restarted
     * @param No parameter value
     * @return No return value
     */ 
	@Override
	public void onResume() {
		super.onResume(); 
        Intent intent = getIntent();
        user = DBHelper.getUser(this, intent.getLongExtra(EXTRA_USER, -1));
        restaurant = DBHelper.getRestaurant(this, intent.getLongExtra(EXTRA_RESTAURANT, -1));
        
        setText();
        updateRatingText();
        ArrayList<Comment> comments = DBHelper.getComments(this, restaurant);
		if (comments.size() > 0) {
			Collections.sort(comments);
			ListAdapter adapter = new CommentAdapter(this,
					R.layout.comment_list_item, comments);
			ListView listView = (ListView) findViewById(R.id.list_comments);
			listView.setAdapter(adapter);
		}
	}

    /**
     * Used to set the fields of the Restaurant
     * @param No parameter value
     * @return No return value
     */ 
	private void setText() {
        TextView name = (TextView) findViewById(R.id.text_name);
        TextView location = (TextView) findViewById(R.id.text_location);
        TextView hours = (TextView) findViewById(R.id.text_hours);
		if (name != null) {
			name.setText(restaurant.name);
		}
		if (location != null) {
			location.setText(restaurant.location.name);
		}
		if (hours != null) {
			hours.setText(restaurant.hours);
		}
	}

    /**
     * Action used to view the Restaurant menu
     * @param view
     * @return No return value
     */ 
	public void onViewMenuButtonClick(View view) {
		Intent intent = new Intent(this, MenuActivity.class);
		intent.putExtra(EXTRA_USER, user.id);
		intent.putExtra(EXTRA_RESTAURANT, restaurant.id);
		startActivity(intent);
	}

    /**
     * Action used to rate the restaurant
     * @param view
     * @return No return value
     */ 
	public void onRateButtonClick(View view) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.title_dialog_rate)
				.setSingleChoiceItems(R.array.rating_choices, -1,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								RatingInfo.ratingPos = which;
							}
						})
				// Set the action buttons
				.setPositiveButton(R.string.button_ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								int rating = Integer.parseInt(getResources().getStringArray(R.array.rating_choices)[RatingInfo.ratingPos]);
								if(DBHelper.hasRated(RestaurantDetailActivity.this, user, restaurant)) {
									DBHelper.updateRating(RestaurantDetailActivity.this, user, restaurant, rating, new Date());
								} else {
									DBHelper.addRating(RestaurantDetailActivity.this, user, restaurant, rating, new Date());
								}
								updateRatingText();
							}
						})
				.setNegativeButton(R.string.button_cancel,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
							}
						});
		builder.show();
	}

    /**
     * Action used to comment on the Restaurant menu
     * @param view
     * @return No return value
     */ 
	public void onCommentButtonClick(View view) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		LayoutInflater inflater = getLayoutInflater();

	    builder.setTitle(R.string.title_dialog_comment)
	    	// Set the layout for the dialog
	    	.setView(inflater.inflate(R.layout.dialog_comment, null))
	    	// Add action buttons
	        .setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int id) {
	            	   DBHelper.addComment(RestaurantDetailActivity.this, user, restaurant,
	            			   ((EditText) ((AlertDialog) dialog).findViewById(R.id.text_comment)).getText().toString(),
	            			   new Date());
	            	   onResume();
	               }
	           })
	        .setNegativeButton(R.string.button_cancel, new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int id) {
	                   
	               }
	           });      
	    builder.show();
	}

    /**
     * Action used to update the Restaurant's rating
     * @param No paramter value
     * @return No return value
     */ 
	private void updateRatingText() {
		TextView ratingText = (TextView) findViewById(R.id.text_rating);
		TextView yourRatingText = (TextView) findViewById(R.id.your_rating);
		
		Double avgRating = DBHelper.getAvgRating(this, restaurant);
		if (ratingText != null) {
			if (avgRating > 0) {
				ratingText.setText("Average rating: "
						+ (new DecimalFormat("0.00")).format(avgRating));
			} else {
				ratingText.setText("No ratings yet.");
			}
		}
		
		int yourRating = DBHelper.getRating(this, user, restaurant);
		if (yourRatingText != null) {
			if (yourRating > 0) {
				yourRatingText.setText("Your rating: " + yourRating);
			} else {
				yourRatingText.setText("Your rating: None");
			}
		}
	}
}