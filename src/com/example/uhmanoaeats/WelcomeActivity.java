package com.example.uhmanoaeats;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * WelcomeActivity --- An activity that is the first thing seen in the Android app
 * @author    Alana Meditz
 * @author    Joneal Altura
 */
public class WelcomeActivity extends Activity {
    /**
     * Required when activity is created to call its super class' method that it overrides
     * @param savedInstanceState
     * @return No return value
     */ 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		(new Thread() {
		    /**
		     * Action to run this activity
		     * @param No parameter value
		     * @return No return value
		     */ 
			@Override
			public void run() {
				try {
					int waited = 0;
					// pause while the welcome screen is showing
					while (waited < 2000) {
						sleep(100);
						waited += 100;
					}
				} catch (InterruptedException e) {
				} finally {
	               finish();
	               startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
	            }
			}
		}).start();
	}
}
