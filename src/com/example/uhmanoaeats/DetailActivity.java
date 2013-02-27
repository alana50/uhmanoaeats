package com.example.uhmanoaeats;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

// Extended by RestaurantDetailActivity and DishDetailActivity
/**
 * DetailActivity --- An activity extended by RestaurantDetailActivity and DishDetailActivity
 * @author    Alana Meditz
 * @author    Joneal Altura
 */
public class DetailActivity extends MyActivity {
	
    /**
     * Adapter to set up the comment list
     * @param No parameter value
     * @return No return value
     */ 
	protected class CommentAdapter extends ArrayAdapter<Comment> {
		private ArrayList<Comment> comments;

		public CommentAdapter(Context context, int textViewResourceId,
				ArrayList<Comment> comments) {
			super(context, textViewResourceId, comments);
			this.comments = comments;
		}

	    /**
	     * Gets the view of this activity
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
				v = vi.inflate(R.layout.comment_list_item, null);
			}
			Comment c = comments.get(position);
			if (c != null) {
				TextView comment = (TextView) v.findViewById(R.id.comment);
				TextView who = (TextView) v.findViewById(R.id.who);
				if (comment != null) {
					comment.setText(c.text);
				}
				if (who != null) {
					SimpleDateFormat df = new SimpleDateFormat();
					who.setText("-" + c.user + " on " + df.format(c.date));
				}
			}
			return v;
		}
	}
}
