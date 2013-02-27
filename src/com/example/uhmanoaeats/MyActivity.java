package com.example.uhmanoaeats;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

/**
 * MyActivity --- Activity used as the default
 * @author    Alana Meditz
 * @author    Joneal Altura
 */
public class MyActivity extends Activity {
	public static final String EXTRA_USER = "com.example.uhmanoaeats.USER";
	public static final String EXTRA_RESTAURANT = "com.example.uhmanoaeats.RESTAURANT";
	public static final String EXTRA_LOCATION = "com.example.uhmanoaeats.LOCATION";
	public static final String EXTRA_DISH = "com.example.uhmanoaeats.DISH";
	protected static User user;

    /**
     * Action to return to go to home
     * @param view
     * @return No return value
     */ 
    public void onHomeButtonClick(View view) {
		startActivity((new Intent(this, MainMenuActivity.class)).putExtra(EXTRA_USER, user.id));
    }
}
