package com.example.uhmanoaeats;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * LoginActivity --- Used by users to login into the UHManoaEats
 * @author    Alana Meditz
 * @author    Joneal Altura
 */
public class LoginActivity extends MyActivity {
    /**
     * Required when activity is created to call its super class' method that it overrides
     * @param savedInstanceState
     * @return No return value
     */ 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    /**
     * Called if user pressed the return button on the phone
     * @param No parameter value
     * @return No return value
     */ 
    @Override
    public void onBackPressed() {
    }

    /**
     * Action that logs in the user into the app
     * @param view
     * @return No return value
     */ 
    public void onClickLogin(View view) {
    	String userInput = ((EditText) findViewById(R.id.user_name)).getText().toString();
    	String passwordInput = ((EditText) findViewById(R.id.password)).getText().toString();
    	
    	User user = DBHelper.getUser(this, userInput);
    	
    	if(user == null) {
    		Toast.makeText(this, "Username does not exist.", Toast.LENGTH_SHORT).show();
    	} else if(user.password.equals(passwordInput)) {
    		Intent intent = new Intent(this, MainMenuActivity.class);
    	    intent.putExtra(EXTRA_USER, user.id);
    	    startActivity(intent);
    	} else {
    		Toast.makeText(this, "Invalid username and password combination!", Toast.LENGTH_SHORT).show();
    	}
    }

    /**
     * Action that sends the user to register to use the app
     * @param view
     * @return No return value
     */ 
    public void onClickRegister(View view) {
    	Intent intent = new Intent(this, RegisterActivity.class);
    	startActivity(intent);
    }
}