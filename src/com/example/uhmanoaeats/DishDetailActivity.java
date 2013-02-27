package com.example.uhmanoaeats;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import com.example.uhmanoaeats.RestaurantDetailActivity.RatingInfo;

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
 * DishDetailtActivity --- An activity that allows the user to interact with a Dish
 * @author    Alana Meditz
 * @author    Joneal Altura
 */
public class DishDetailActivity extends DetailActivity {
	private Dish dish;

    /**
     * Required when activity is created to call its super class' method that it overrides
     * @param savedInstanceState
     * @return No return value
     */ 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish_detail);
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
        dish = DBHelper.getDish(this, intent.getLongExtra(EXTRA_DISH, -1));
        
        ((TextView) findViewById(R.id.name)).setText(dish.name);
        ((TextView) findViewById(R.id.restaurant)).setText(DBHelper.getRestaurant(this, dish.restaurant).name);
        DecimalFormat df = new DecimalFormat("0.00");
        ((TextView) findViewById(R.id.price)).setText("$" + df.format(dish.price));
        updateRatingText();
        
        ArrayList<Comment> comments = DBHelper.getComments(this, dish);
		if (comments.size() > 0) {
			Collections.sort(comments);
			ListAdapter adapter = new CommentAdapter(this,
					R.layout.comment_list_item, comments);
			ListView listView = (ListView) findViewById(R.id.list_comments);
			listView.setAdapter(adapter);
		}
	}

    /**
     * Action for viewing a restaurant
     * @param view
     * @return No return value
     */ 
	public void onViewRestaurantButtonClick(View view) {
		Intent intent = new Intent(this, RestaurantDetailActivity.class);
		intent.putExtra(EXTRA_USER, user.id);
		intent.putExtra(EXTRA_RESTAURANT, dish.restaurant);
		startActivity(intent);
	}

    /**
     * Action to rate a Dish
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
								if(DBHelper.hasRated(DishDetailActivity.this, user, dish)) {
									DBHelper.updateRating(DishDetailActivity.this, user, dish, rating, new Date());
								} else {
									DBHelper.addRating(DishDetailActivity.this, user, dish, rating, new Date());
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
     * Action to comment the Dish
     * @param view
     * @return No return value
     */ 
	public void onCommentButtonClick(View view) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		LayoutInflater inflater = getLayoutInflater();

	    builder.setTitle(R.string.title_dialog_comment)
	    	.setView(inflater.inflate(R.layout.dialog_comment, null))
	        .setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int id) {
	            	   DBHelper.addComment(DishDetailActivity.this, user, dish,
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
     * Used to update a Dish's rating
     * @param No parameter value
     * @return No return value
     */ 
	private void updateRatingText() {
		TextView ratingText = (TextView) findViewById(R.id.text_rating);
		TextView yourRatingText = (TextView) findViewById(R.id.your_rating);
		
		Double avgRating = DBHelper.getAvgRating(this, dish);
		if(avgRating > 0) {
			ratingText.setText("Average rating: " + (new DecimalFormat("#.##")).format(avgRating));
		} else {
			ratingText.setText("No ratings yet.");
		}
		
		int yourRating = DBHelper.getRating(this, user, dish);
		if(yourRating > 0) {
			yourRatingText.setText("Your rating: " + yourRating);
		} else {
			yourRatingText.setText("Your rating: None");
		}
	}
}
