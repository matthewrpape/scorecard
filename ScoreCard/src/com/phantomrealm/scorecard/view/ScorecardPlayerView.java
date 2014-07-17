package com.phantomrealm.scorecard.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.phantomrealm.scorecard.R;
import com.phantomrealm.scorecard.model.Player;

public class ScorecardPlayerView extends RelativeLayout {

	private static final String POSITIVE_PREFIX = "+";

	private Player mPlayer;
	private Integer mTotal;
	private Integer mAverage;

	public ScorecardPlayerView(Context context, Player player, Integer total, Integer average) {
		super(context);
		LayoutInflater.from(context).inflate(R.layout.view_scorecard_player, this, true);

		setPlayer(player);
		setTotal(total);
		setAverage(average);
	}

	public void setPlayer(Player player) {
		mPlayer = player;
		((TextView) findViewById(R.id.view_scorecard_player_name_label)).setText(mPlayer.getName());
	}

	public Player getPlayer() {
		return mPlayer;
	}

	public void setTotal(Integer total) {
		mTotal = total;
		String totalString = mTotal < 0 ? String.valueOf(mTotal) : POSITIVE_PREFIX + String.valueOf(mTotal);
		((TextView) findViewById(R.id.view_scorecard_player_total_score)).setText(totalString);
	}

	public void setAverage(Integer average) {
		mAverage = average;
		String averageString = mAverage != null ? String.valueOf(mAverage) : getResources().getString(R.string.no_average);
		((TextView) findViewById(R.id.view_scorecard_player_average_score)).setText(averageString);
	}
}