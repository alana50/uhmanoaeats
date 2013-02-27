package com.example.uhmanoaeats;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * MenuActivity --- Activity that allows the user to interact with a Restaurant's menu
 * @author    Alana Meditz
 * @author    Joneal Altura
 */
public class MenuActivity extends MyActivity {
	private ArrayList<Dish> dishes;
	private Restaurant restaurant;

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
		restaurant = DBHelper.getRestaurant(this, intent.getLongExtra(EXTRA_RESTAURANT, -1));
		TextView heading = (TextView) findViewById(R.id.heading);
		if (heading != null) {
			if (restaurant == null) {
				heading.setText("All Dishes:");
				dishes = DBHelper.getDishes(this);
			} else {
				heading.setText(restaurant.name + ":");
				dishes = DBHelper.getDishes(this, restaurant);
			}
		}
              
		if (dishes.size() > 0) {
			Collections.sort(dishes);
			ListAdapter adapter = new DishAdapter(this,
					R.layout.dish_list_item, dishes);
			ListView listView = (ListView) findViewById(R.id.list);
			listView.setAdapter(adapter);
			listView.setClickable(true);
	        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	        	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
	        		Intent intent = new Intent(MenuActivity.this, DishDetailActivity.class);
	        		intent.putExtra(EXTRA_USER, user.id);
	        		intent.putExtra(EXTRA_DISH, dishes.get(position).id);
	        	    startActivity(intent);
	        	}
	        });
		} else {
			Toast.makeText(this, "There are no dishes to display.", Toast.LENGTH_SHORT).show();
		}
    }

    /**
     * Action to add a Dish to a Restaurant's menu
     * @param view
     * @return No return value
     */ 
    public void onAddButtonClick(View view) {
    	Intent intent = new Intent(this, AddDishActivity.class);
		intent.putExtra(EXTRA_USER, user.id);
		if(restaurant != null) {
			intent.putExtra(EXTRA_RESTAURANT, restaurant.id);
		}
		startActivity(intent);
    }

    /**
     * Adapter to set up the dish list
     * @param No parameter value
     * @return No return value
     */ 
	private class DishAdapter extends ArrayAdapter<Dish> {
		private ArrayList<Dish> dishes;

		public DishAdapter(Context context, int textViewResourceId,
				ArrayList<Dish> dishes) {
			super(context, textViewResourceId, dishes);
			this.dishes = dishes;
		}

	    /**
	     * Action for viewing the dishes
	     * @param position
	     * @param convertView
	     * @param parent
	     * @return v
	     */ 
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			if (v == null) {
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.dish_list_item, null);
			}
			Dish d = dishes.get(position);
			if (d != null) {
				TextView name = (TextView) v.findViewById(R.id.name);
				TextView veg = (TextView) v.findViewById(R.id.veg);
				TextView price = (TextView) v.findViewById(R.id.price);
				if (name != null) {
					name.setText(d.name);
				}
				if (veg != null) {
					if (d.vegetarian) {
						veg.setText("(v)");
					} else {
						veg.setText("");
					}
				}
				if (price != null) {
					DecimalFormat df = new DecimalFormat("0.00");
					price.setText("$" + df.format(d.price));
				}
			}
			return v;
		}
	}
}