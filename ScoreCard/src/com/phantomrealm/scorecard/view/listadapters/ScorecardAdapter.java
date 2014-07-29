package com.phantomrealm.scorecard.view.listadapters;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.phantomrealm.scorecard.R;
import com.phantomrealm.scorecard.model.Scorecard;

public class ScorecardAdapter extends ArrayAdapter<Scorecard> {

	private List<Scorecard> mScorecards;
	private LayoutInflater mInflater;

    public ScorecardAdapter(Context context, int resource, int textViewResourceId, List<Scorecard> scorecards) {
		super(context, resource, textViewResourceId, scorecards);

		mScorecards = scorecards;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
    
    public Scorecard getScorecard(int index) {
    	return mScorecards.get(index);
    }

    @Override
    public int getCount() {
    	return mScorecards == null ? 0 : mScorecards.size();
    }

    @SuppressLint("InflateParams")
	public View getView(int position, View convertView, ViewGroup parent) {
    	if (convertView == null) {
    		convertView = mInflater.inflate(R.layout.list_item_scorecard, null);
    	}

    	TextView courseNameView = (TextView) convertView.findViewById(R.id.list_scorecard_course);
    	courseNameView.setText(mScorecards.get(position).getCourse().getName());
    	TextView dateView = (TextView) convertView.findViewById(R.id.list_scorecard_date);
    	dateView.setText(mScorecards.get(position).getCourse().getName());
    	TextView playersView = (TextView) convertView.findViewById(R.id.list_scorecard_players);
    	playersView.setText(mScorecards.get(position).getCourse().getName());

    	return convertView;
    }

}