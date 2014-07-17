package com.phantomrealm.scorecard.view.listadapters;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.phantomrealm.scorecard.model.Player;
import com.phantomrealm.scorecard.view.ScorecardPlayerView;

public class ScorecardPlayerAdapter extends ArrayAdapter<Player> {

	private List<Player> mPlayers;
	private List<Integer> mScores; // total score (difference from par) of each player for the course
	private List<Integer> mAverages; // average score of each player for the hole

	/**
	 * 
	 * @param context
	 * @param resource
	 * @param players
	 * @param scores total score (difference form par) of each player for the course
	 * @param averages
	 */
    public ScorecardPlayerAdapter(Context context, int resource, List<Player> players, List<Integer> scores, List<Integer> averages) {
		super(context, resource);

		mPlayers = players;
		mScores = scores;
		mAverages = averages;
	}

    @Override
    public int getCount() {
    	return mPlayers == null ? 0 : mPlayers.size();
    }

    @SuppressLint("InflateParams")
	public View getView(int position, View convertView, ViewGroup parent) {
    	if (convertView == null) {
    		convertView = new ScorecardPlayerView(getContext(), mPlayers.get(position), mScores.get(position), mAverages.get(position));
    	} else {
    		((ScorecardPlayerView)convertView).setPlayer(mPlayers.get(position));
    		((ScorecardPlayerView)convertView).setTotal(mScores.get(position));
    		((ScorecardPlayerView)convertView).setAverage(mAverages.get(position));
    	}

    	return convertView;
    }

}