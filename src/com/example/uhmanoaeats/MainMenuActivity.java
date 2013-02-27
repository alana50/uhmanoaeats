package com.example.uhmanoaeats;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * MainMenuActivity --- Activity that connects all the activity together in the app
 * @author    Alana Meditz
 * @author    Joneal Altura
 */
public class MainMenuActivity extends MyActivity {
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }

    /**
     * Called after onCreate() when the activity is restarted
     * @param No parameter value
     * @return No return value
     */ 
    @Override
    public void onResume() {
    	super.onResume();  	
    	user = DBHelper.getUser(this, getIntent().getLongExtra(EXTRA_USER, -1));
    }

    /**
     * Action to view all the restaurants on the database
     * @param view
     * @return No return value
     */ 
    public void onRestaurantsButtonClick(View view) {
    	Intent intent = new Intent(this, RestaurantsActivity.class);
	    intent.putExtra(EXTRA_USER, user.id);
	    startActivity(intent);
    }

    /**
     * Action to view all the locations on the database
     * @param view
     * @return No return value
     */ 
	public void onLocationsButtonClick(View view) {
		Intent intent = new Intent(this, LocationsActivity.class);
	    intent.putExtra(EXTRA_USER, user.id);
	    startActivity(intent);
	}

    /**
     * Action to view all the dishes on the database
     * @param view
     * @return No return value
     */ 
	public void onDishesButtonClick(View view) {
		Intent intent = new Intent(this, MenuActivity.class);
	    intent.putExtra(EXTRA_USER, user.id);
	    startActivity(intent);
	}

    /**
     * Action to log out of the Android application
     * @param view
     * @return No return value
     */ 
	public void onLogOutButtonClick(View view) {
		startActivity(new Intent(this, LoginActivity.class));
	}
}
