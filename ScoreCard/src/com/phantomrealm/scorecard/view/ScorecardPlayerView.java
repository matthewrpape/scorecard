package com.phantomrealm.scorecard.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.phantomrealm.scorecard.R;
import com.phantomrealm.scorecard.model.Player;

public class ScorecardPlayerView extends RelativeLayout {

	public interface PlayerScoreAdjustmentListener {
		public void adjustScore(Player player, int adjustment);
	}

	private static final PlayerScoreAdjustmentListener EMPTY_LISTENER = new PlayerScoreAdjustmentListener() {
		@Override public void adjustScore(Player player, int adjustment) { }
	};

	public static class Builder {
		private Player player;
		private Integer holeScore;
		private Integer courseScoreDifferential;
		private Integer holeAverage;
		private PlayerScoreAdjustmentListener scoreAdjustmentListener;

		public Builder(Player player, Integer holeScore, Integer courseScoreDifferential) {
			this.player = player;
			this.holeScore = holeScore;
			this.courseScoreDifferential = courseScoreDifferential;
		}

		public Builder holeAverage(Integer holeAverage) {
			this.holeAverage = holeAverage;
			return this;
		}
		
		public Builder scoreAdjustmentListener(PlayerScoreAdjustmentListener scoreAdjustmentListener) {
			this.scoreAdjustmentListener = scoreAdjustmentListener;
			return this;
		}

		public ScorecardPlayerView build(Context context) {
			return new ScorecardPlayerView(context, this);
		}

	}

	private Player mPlayer;

	private ScorecardPlayerView(Context context, Builder builder) {
		super(context);
		LayoutInflater.from(context).inflate(R.layout.view_scorecard_player, this, true);

		setPlayer(builder.player);
		setCourseScore(builder.courseScoreDifferential);
		setHoleScore(builder.holeScore);
		setHoleAverage(builder.holeAverage);
		setAdjustmentListener(builder.scoreAdjustmentListener);
	}

	public void setPlayer(Player player) {
		mPlayer = player;
		((TextView) findViewById(R.id.view_scorecard_player_name_label)).setText(mPlayer.getName());
	}

	public void setCourseScore(Integer score) {
		String positivePrefix = this.getResources().getString(R.string.positive_prefix);
		String totalString = score < 0 ? String.valueOf(score) : positivePrefix + String.valueOf(score);
		((TextView) findViewById(R.id.view_scorecard_player_total_score)).setText(totalString);
	}

	public void setHoleScore(Integer score) {
		((TextView) findViewById(R.id.view_scorecard_player_hole_score_label)).setText(String.valueOf(score));
		findViewById(R.id.view_scorecard_player_decrease_score_button).setEnabled(score > 1);
	}

	public void setHoleAverage(Integer average) {
		String averageString = average != null ? String.valueOf(average) : getResources().getString(R.string.no_average);
		((TextView) findViewById(R.id.view_scorecard_player_average_score)).setText(averageString);
	}

	private void setAdjustmentListener(PlayerScoreAdjustmentListener scoreAdjustmentListener) {
		final PlayerScoreAdjustmentListener adjustmentListener = scoreAdjustmentListener != null ?
				scoreAdjustmentListener : EMPTY_LISTENER;

		findViewById(R.id.view_scorecard_player_decrease_score_button).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				adjustmentListener.adjustScore(mPlayer, -1);
			}
		});

		findViewById(R.id.view_scorecard_player_increase_score_button).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				adjustmentListener.adjustScore(mPlayer, 1);
			}
		});
	}
}