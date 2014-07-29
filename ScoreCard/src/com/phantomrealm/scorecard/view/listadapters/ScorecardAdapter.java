package com.phantomrealm.scorecard.view.listadapters;

import java.util.Date;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.phantomrealm.scorecard.R;
import com.phantomrealm.scorecard.model.Player;
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

    	Scorecard scorecard = mScorecards.get(position);
    	TextView courseNameView = (TextView) convertView.findViewById(R.id.list_scorecard_course);
    	courseNameView.setText(scorecard.getCourse().getName());
    	TextView dateView = (TextView) convertView.findViewById(R.id.list_scorecard_date);
    	dateView.setText(generateDateString(scorecard.getDate()));
    	TextView playersView = (TextView) convertView.findViewById(R.id.list_scorecard_players);
    	playersView.setText(generatePlayersString(scorecard.getPlayerScores(), scorecard.getCourse().getTotalPar()));

    	return convertView;
    }

    /**
     * Generates a date string from a long that represents the time in milliseconds since
     *  January 1, 1970 00:00:00.0 UTC.
     * @param dateValue
     * @return
     */
    private String generateDateString(long dateValue) {
    	Date date = new Date(dateValue);
    	return date.toString();
    }

    /**
     * Generates a string that represents the players and their scores
     * @param playerScores map from player to a list of their scores
     * @param par total for the course
     * @return
     */
    private String generatePlayersString(Map<Player, List<Integer>> playerScores, int par) {
    	StringBuilder builder = new StringBuilder();
    	for (Player player : playerScores.keySet()) {
    		builder.append(player.getName())
    		       .append("(")
    		       .append(generateParString(playerScores.get(player), par))
    		       .append(") ");
    	}

    	return builder.toString();
    }

    /**
     * Generates a string that represents a total score compared to par
     * @param scores list of scores to be totaled
     * @param par combined value for the course
     * @return
     */
    private String generateParString(List<Integer> scores, int par) {
    	String positivePrefix = this.getContext().getString(R.string.positive_prefix);
    	int totalScore = -par;
    	for (Integer score : scores) {
    		totalScore += score;
    	}

    	return totalScore < 0 ? String.valueOf(totalScore) : positivePrefix + String.valueOf(totalScore);
    }
}