package com.example.uhmanoaeats;

import java.util.ArrayList;
import java.util.Collections;

import android.os.Bundle;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * RestaurantDetailtActivity --- An activity that allows the user to interact with a Restaurant
 * @author    Alana Meditz
 * @author    Joneal Altura
 */
public class RestaurantsActivity extends MyActivity {
	private ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();
	private Location location;

    /**
     * Required when activity is created to call its super class' method that it overrides
     * @param savedInstanceState
     * @return No return value
     */ 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
    	TextView heading = ((TextView) findViewById(R.id.heading));
		if (heading != null) {
			if (location == null) {
				heading.setText("All Restaurants:");
				fillRestaurantList();
			} else {
				heading.setText(location.name + ":");
				fillRestaurantList(location);
			}
			Collections.sort(restaurants);
		}
    	
		if (restaurants.size() > 0) {
			ListAdapter adapter = new ArrayAdapter<Restaurant>(this,
					R.layout.simple_list_item, restaurants);
			ListView listView = (ListView) findViewById(R.id.list);
			listView.setAdapter(adapter);

			listView.setClickable(true);
			listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int position, long arg3) {
					Restaurant r = restaurants.get(position);
					Intent intent = new Intent(RestaurantsActivity.this,
							RestaurantDetailActivity.class);
					intent.putExtra(EXTRA_USER, user.id);
					intent.putExtra(EXTRA_RESTAURANT, r.id);
					startActivity(intent);
				}
			});
		} else {
			Toast.makeText(this, "There are no restaurants to display.", Toast.LENGTH_SHORT).show();
		}
    }

    /**
     * Action to create the Restaurant's menu
     * @param menu
     * @return boolean value
     */ 
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    /**
     * Used to populate the list
     * @param No parameter value
     * @return No return value
     */ 
    private void fillRestaurantList() {
    	restaurants = DBHelper.getRestaurants(this);
    	Collections.sort(restaurants);
    }

    /**
     * Used to populate the list
     * @param location
     * @return No return value
     */ 
    private void fillRestaurantList(Location location) {
    	restaurants = DBHelper.getRestaurants(this, location);
    }

    /**
     * Action to add new restaurant
     * @param view
     * @return No return value
     */ 
    public void onAddButtonClick(View view) {
    	Intent intent = new Intent(this, AddRestaurantActivity.class);
		intent.putExtra(EXTRA_USER, user.id);
		if(location != null) {
			intent.putExtra(EXTRA_LOCATION, location.id);
		}
		startActivity(intent);
    }
}