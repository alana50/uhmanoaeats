package com.example.uhmanoaeats;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

/**
 * MyActivity --- Activity used for Spinner
 * @author    Alana Meditz
 * @author    Joneal Altura
 */
public class MySpinnerActivity extends MyActivity {
	protected int selected;

    /**
     * Used to get the position of the Spinner
     * @param spinner
     * @param string
     * @return Integer value
     */ 
	protected int getPos(Spinner spinner, String string){
        for(int i = 0; i < spinner.getCount(); i++) {
            if(spinner.getItemAtPosition(i).toString().equals(string)){
                return i;
            }
        }
        return -1;
	}

    /**
     * A class that defines the actions for the spinner
     * @param No parameter value
     * @return No return value
     */ 
    protected class SpinnerActivity implements OnItemSelectedListener {
        /**
         * Action for a selected item from the Spinner
         * @param arg0
         * @param arg1
         * @param pos
         * @param id
         * @return No return value
         */ 
		public void onItemSelected(AdapterView<?> arg0, View arg1, int pos,
				long id) {
			selected = pos;
		}

        /**
         * Action for a nothing selected from the Spinner
         * @param arg0
		 * @return No return value
         */ 
		public void onNothingSelected(AdapterView<?> arg0) {
		}
    }
}
