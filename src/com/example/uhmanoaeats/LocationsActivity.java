package com.example.uhmanoaeats;

import java.util.ArrayList;
import java.util.Collections;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * LocationsActivity --- A class allows interaction the Locations on the Android app
 * @author    Alana Meditz
 * @author    Joneal Altura
 */
public class LocationsActivity extends MyActivity {
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
    	
    	user = DBHelper.getUser(this, getIntent().getLongExtra(EXTRA_USER, -1));
    	
    	((TextView) findViewById(R.id.heading)).setText("All Locations:");
    	
    	ArrayList<Location> locationsPre = DBHelper.getLocations(this);
    	Collections.sort(locationsPre);
    	final ArrayList<Location> locations = locationsPre;
    	
		if (locations.size() > 0) {
			ListAdapter adapter = new ArrayAdapter<Location>(this,
					R.layout.simple_list_item, locations); //
			ListView listView = (ListView) findViewById(R.id.list);
			listView.setAdapter(adapter);

			listView.setClickable(true);
			listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int position, long arg3) {
					Location l = locations.get(position);
					Intent intent = new Intent(LocationsActivity.this,
							RestaurantsActivity.class);
					intent.putExtra(EXTRA_USER, user.id);
					intent.putExtra(EXTRA_LOCATION, l.id);
					startActivity(intent);
				}
			});
		} else {
			Toast.makeText(this, "There are no locations to display.", Toast.LENGTH_SHORT).show();
		}
    }

    /**
     * Used to add a new location to the database
     * @param view
     * @return No return value
     */ 
    public void onAddButtonClick(View view) {
    	Intent intent = new Intent(this, AddLocationActivity.class);
		intent.putExtra(EXTRA_USER, user.id);
		startActivity(intent);
    }
}
