package com.example.uhmanoaeats;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * AddLocationActivity --- An activity in which users can add a location to the database
 * @author    Alana Meditz
 * @author    Joneal Altura
 */
public class AddLocationActivity extends MyActivity {
    /**
     * Required when activity is created to call its super class' method that it overrides
     * @param savedInstanceState
     * @return No return value
     */ 
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_location);
	}

    /**
     * Called after onCreate() when the activity is restarted
     * @param No parameters
     * @return No return value
     */ 
	@Override
	public void onResume() {
		super.onResume();
		Intent intent = getIntent();
		user = DBHelper.getUser(this, intent.getLongExtra(EXTRA_USER, -1));
	}

    /**
     * Called when the user hits the submit button
     * @param view
     * @return No return value
     */
	public void onClickSubmit(View view) {
		String name = ((EditText) findViewById(R.id.name)).getText().toString().trim();
		if(name.length() < 1) {
			Toast.makeText(this, "Please enter the location name.", Toast.LENGTH_SHORT).show();
			return;
		}
		if(DBHelper.getLocation(this, name) == null) {
			// The location doesn't exist in the database
			DBHelper.addLocation(this, name);
			Intent intent = new Intent(this, LocationsActivity.class);
			intent.putExtra(EXTRA_USER, user.id);
			startActivity(intent);
		} else {
			Toast.makeText(this, "Location already exists.", Toast.LENGTH_SHORT).show();
		}
	}
}
