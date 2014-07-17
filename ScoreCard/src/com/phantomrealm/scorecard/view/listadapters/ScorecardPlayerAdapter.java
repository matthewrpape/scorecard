package com.phantomrealm.scorecard.view.listadapters;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.phantomrealm.scorecard.model.Player;
import com.phantomrealm.scorecard.view.ScorecardPlayerView;
import com.phantomrealm.scorecard.view.ScorecardPlayerView.PlayerScoreAdjustmentListener;

public class ScorecardPlayerAdapter extends ArrayAdapter<Player> {

	public interface ScoreAdjustmentListener {
		public void adjustScore(int holeIndex, Player player, int adjustment);
	}

	private class EmptyScoreAdjustmentListener implements ScoreAdjustmentListener {
		@Override public void adjustScore(int holeIndex, Player player, int adjustment) { }
	}

	private int mHoleIndex;
	private List<Player> mPlayers;
	private List<Integer> mScores; // score of each player for the hole
	private List<Integer> mCourseScoreDifferentials; // total score (difference from par) of each player for the course
	private List<Integer> mAverages; // average score of each player for the hole
	private ScoreAdjustmentListener mListener;

    public ScorecardPlayerAdapter(Context context, int resource, int holeIndex, List<Player> players, List<Integer> scores,
    		List<Integer> courseScoreDifferentials, List<Integer> averages, ScoreAdjustmentListener listener) {
    	super(context, resource);

    	mHoleIndex = holeIndex;
    	mPlayers = players;
		mScores = scores;
		mCourseScoreDifferentials = courseScoreDifferentials;
		mAverages = averages;
		mListener = listener != null ? listener : new EmptyScoreAdjustmentListener();
    }

    @Override
    public int getCount() {
    	return mPlayers == null ? 0 : mPlayers.size();
    }

    @SuppressLint("InflateParams")
	public View getView(int position, View convertView, ViewGroup parent) {
    	Player player = mPlayers.get(position);
    	Integer holeScore = mScores.get(position);
    	Integer courseScore = mCourseScoreDifferentials.get(position);
    	Integer holeAverage = mAverages.get(position);

    	if (convertView == null) {
    		convertView = new ScorecardPlayerView.Builder(player, holeScore, courseScore)
    		.holeAverage(holeAverage)
    		.scoreAdjustmentListener(new PlayerScoreAdjustmentListener() {
				@Override
				public void adjustScore(Player player, int adjustment) {
					mListener.adjustScore(mHoleIndex, player, adjustment);
				}
			}).build(getContext());
    	} else {
    		((ScorecardPlayerView)convertView).setPlayer(player);
    		((ScorecardPlayerView)convertView).setCourseScore(courseScore);
    		((ScorecardPlayerView)convertView).setHoleScore(holeScore);
    		((ScorecardPlayerView)convertView).setHoleAverage(holeAverage);
    	}

    	return convertView;
    }

}