package com.example.uhmanoaeats;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * RegisterActivity --- Activity that allows the user to register to use UHManoaEats
 * @author    Alana Meditz
 * @author    Joneal Altura
 */
public class RegisterActivity extends MyActivity {
    /**
     * Required when activity is created to call its super class' method that it overrides
     * @param savedInstanceState
     * @return No return value
     */ 
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
	}

    /**
     * Called after onCreate() when the activity is restarted
     * @param No parameter value
     * @return No return value
     */ 
	public void onClickSubmit(View view) {
		String username = ((EditText) findViewById(R.id.username)).getText().toString();
		username = username.trim();
    	String password = ((EditText) findViewById(R.id.password)).getText().toString();
    	password = password.trim();
    	String passwordRepeated = ((EditText) findViewById(R.id.repeatPassword)).getText().toString();
    	passwordRepeated = passwordRepeated.trim();
    	
    	if(validateUsername(username)
    	   && validatePassword(password, passwordRepeated)) {
    		DBHelper.addUser(this, username, password);
    		Toast.makeText(this, "New account created.", Toast.LENGTH_SHORT).show();
    		Intent intent = new Intent(this, LoginActivity.class);
    	    startActivity(intent);
    	}
	}

    /**
     * Use to make sure a username to register is valid
     * @param username
     * @return boolean value
     */ 
	private boolean validateUsername(String username) {
		if(DBHelper.getUser(this, username) != null) {
    		Toast.makeText(this, "Username is already in use.  Please choose another.", Toast.LENGTH_SHORT).show();
    		return false;
    	} else if (username.length() < 6 || username.contains(" ")) {
    		Toast.makeText(this, "Username must be at least 6 characters long and cannot contain spaces.", Toast.LENGTH_SHORT).show();
    		return false;
    	}
		return true;
	}

    /**
     * Used to validate a password created by the user registering
     * @param password
     * @param passwordRepeated
     * @return valid
     */ 
	private boolean validatePassword(String password, String passwordRepeated) {
		boolean valid = false;
		if(!password.equals(passwordRepeated)) {
    		Toast.makeText(this, "Please check that the passwords match.", Toast.LENGTH_SHORT).show();
    	}
		else if(password.length() < 6 || password.contains(" ")) {
    		Toast.makeText(this, "Password must be at least 6 characters long and cannot contain spaces.", Toast.LENGTH_SHORT).show();
    	}

		else if(!(hasCharacter(password) && hasNumber(password) && hasUpperCase(password))) {
			Toast.makeText(this, "Password must have at least one symbol, one digit, and one upper case letter.", Toast.LENGTH_SHORT).show();
		}
		else {
			valid = true;
		}
		return valid;
	}

    /**
     * Used to check if password has at least one special character
     * @param password
     * @return valid
     */ 
	private boolean hasCharacter(String password) {
		boolean valid = false;
		for(int index = 0; index < password.length(); index++) {
			int value = password.charAt(index);
			if((value >= 33 && value <= 47) || (value >= 58 && value <= 64) || 
			   (value >= 91 && value <= 96)|| (value >= 123 && value <= 126)) {
				valid = true;
			}
		}
		return valid;
	}

    /**
     * Used to check if password has at least one number
     * @param password
     * @return valid
     */ 
	private boolean hasNumber(String password) {
		boolean valid = false;
		for(int index = 0; index < password.length(); index++) {
			char c = password.charAt(index);
			if(Character.isDigit(c)) {
				valid = true;
			}
		}
		return valid;
	}

    /**
     * Used to check if password has at least one uppercase character
     * @param password
     * @return valid
     */ 
	private boolean hasUpperCase(String password) {
		boolean valid = false;
		for(int index = 0; index < password.length(); index++) {
			char c = password.charAt(index);
			if(Character.isUpperCase(c)) {
				valid = true;
			}
		}
		return valid;
	}
}
