package com.example.uhmanoaeats;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * AddDishActivity --- An activity in which users can add dishes to a given restaurant
 * @author    Alana Meditz
 * @author    Joneal Altura
 */
public class AddDishActivity extends MySpinnerActivity {
	private Restaurant restaurant;

    /**
     * Required when activity is created to call its super class' method that it overrides
     * @param savedInstanceState
     * @return No return value
     */ 
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dish);
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
    	setupSpinner();
    }
    
    /**
     * Sets up the spinner for the location input
     * @param No parameter value
     * @return No return value
     */ 
    private void setupSpinner() {
    	Spinner spinner = (Spinner) findViewById(R.id.restaurants_spinner);
    	ArrayAdapter<Restaurant> adapter = new ArrayAdapter<Restaurant>(this,
				R.layout.simple_list_item, DBHelper.getRestaurants(this));
    	adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	spinner.setAdapter(adapter);
    	spinner.setOnItemSelectedListener(new SpinnerActivity());
    	if(restaurant != null) {
    		spinner.setSelection(getPos(spinner, restaurant.name));
    	}
    }

    /**
     * Called when the user hits the Submit button
     * @param view
     * @return No return value
     */ 
    public void onClickSubmit(View view) {
    	String name = ((EditText) findViewById(R.id.name)).getText().toString();
    	name = name.trim();
        String priceString = ((EditText) findViewById(R.id.price)).getText().toString();
        priceString = priceString.trim();
        restaurant = DBHelper.getRestaurant(this,
				((Spinner) findViewById(R.id.restaurants_spinner))
				.getItemAtPosition(selected).toString());
        final CheckBox checkBox = (CheckBox) findViewById(R.id.vegetarian);
        
        if(addIfValid(name, priceString, checkBox.isChecked())) {
        	// Go back to the restaurant's menu
        	Intent intent = new Intent(this, MenuActivity.class);
			intent.putExtra(EXTRA_USER, user.id);
			intent.putExtra(EXTRA_RESTAURANT, restaurant.id);
			startActivity(intent);
        }
    }
    
    /**
     * Adds the new dish if the user entered proper input
     * @param name
     * @param price
     * @param isChecked
     * @return A boolean value if valid values for name and price have been entered
     */
    private boolean addIfValid(String name, String price, boolean isChecked) {
    	if(name.length() < 1) {
			Toast.makeText(this, "Please enter a dish name.", Toast.LENGTH_SHORT).show();
			return false;
		}
    	if (price.length() < 1) {
			Toast.makeText(this, "Please enter a dish price.", Toast.LENGTH_SHORT).show();
			return false;
		}
    	Double p;
		try {
			p = Double.parseDouble(price);
		} catch (NumberFormatException nfe) {
			Toast.makeText(this, "You may only enter one decimal point and numbers for dish price.", Toast.LENGTH_SHORT).show();
			return false;
		}
		if (p < 0.0) {
			Toast.makeText(this, "Please enter a price greater than or equal to $0.00.", Toast.LENGTH_SHORT).show();
			return false;
		}

		// input passed all tests
		DBHelper.addDish(this, name, restaurant, p, isChecked);
		return true;
	}
}
