package com.example.uhmanoaeats;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * AddRestaurantActivity --- An activity in which users can add a restaurant to the database
 * @author    Alana Meditz
 * @author    Joneal Altura
 */
public class AddRestaurantActivity extends MySpinnerActivity {
    private Location location;

    /**
     * Required when activity is created to call its super class' method that it overrides
     * @param savedInstanceState
     * @return No return value
     */ 
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_restaurant);
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
    	location = DBHelper.getLocation(this, intent.getLongExtra(EXTRA_LOCATION, -1));
    	setupSpinner();
    }
    
    /**
     * Called when the user hits the Submit button
     * @param view
     * @return No return value
     */ 
    public void onClickSubmit(View view) {
    	String name = ((EditText) findViewById(R.id.name)).getText().toString().trim();
        String newLocation = ((Spinner) findViewById(R.id.locations_spinner)).getItemAtPosition(selected).toString();
        String hours = ((EditText) findViewById(R.id.hours)).getText().toString().trim();
    	
        if(!inputIsValid(name, hours)) {
        	return;
        }
        DBHelper.addRestaurant(this, name, DBHelper.getLocation(this, newLocation), hours);
        Intent intent = new Intent(this, RestaurantsActivity.class);
		intent.putExtra(EXTRA_USER, user.id);
		if(location != null) {
			// go back to the location the started the activity
			intent.putExtra(EXTRA_LOCATION, location.id);
		}
		startActivity(intent);
    }
    
    /**
     * Sets up the spinner for the location input
     * @param No parameter value
     * @return No return value
     */ 
    private void setupSpinner() {
    	Spinner spinner = (Spinner) findViewById(R.id.locations_spinner);
    	ArrayAdapter<Location> adapter = new ArrayAdapter<Location>(this,
				R.layout.simple_list_item, DBHelper.getLocations(this));
    	adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	spinner.setAdapter(adapter);
    	spinner.setOnItemSelectedListener(new SpinnerActivity());
    	if(location != null) {
    		spinner.setSelection(getPos(spinner, location.name));
    	}
    }

    /**
     * Checks if user input for new restaurant is valid
     * @param name
     * @param hours
     * @return A boolean value if input for name and hours are valid
     */ 
    private boolean inputIsValid(String name, String hours) {
    	if(name.length() < 1) {
    		Toast.makeText(this, "Please enter the restaurant name.",
    				Toast.LENGTH_SHORT).show();
    	} else if(DBHelper.getRestaurant(this, name) != null) {
    		Toast.makeText(this,
    				"A restaurant with this name already exists.  " +
    				"Please choose another.", Toast.LENGTH_SHORT).show();
    	} else if(hours.length() < 1){
    		Toast.makeText(this, "Please enter the restaurant hours.",
    				Toast.LENGTH_SHORT).show();
    	} else {
    		return true;
    	}
    	return false;
    }
}
