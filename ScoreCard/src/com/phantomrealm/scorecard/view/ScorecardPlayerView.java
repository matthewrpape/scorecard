package com.phantomrealm.scorecard.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.phantomrealm.scorecard.R;
import com.phantomrealm.scorecard.model.Player;

public class ScorecardPlayerView extends RelativeLayout {

	private Player mPlayer;

	public ScorecardPlayerView(Context context, Player player) {
		super(context);

		mPlayer = player;
		LayoutInflater.from(context).inflate(R.layout.view_scorecard_player, this, true);
		((TextView) findViewById(R.id.view_scorecard_player_name_label)).setText(mPlayer.getName());
	}

	public void setPlayer(Player player) {
		mPlayer = player;
	}

	public Player getPlayer() {
		return mPlayer;
	}
}