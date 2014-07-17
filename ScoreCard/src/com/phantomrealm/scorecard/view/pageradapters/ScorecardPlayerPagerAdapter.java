package com.phantomrealm.scorecard.view.pageradapters;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.phantomrealm.scorecard.R;
import com.phantomrealm.scorecard.model.Player;
import com.phantomrealm.scorecard.view.listadapters.ScorecardPlayerAdapter;
import com.phantomrealm.scorecard.view.listadapters.ScorecardPlayerAdapter.ScoreAdjustmentListener;

public class ScorecardPlayerPagerAdapter extends PagerAdapter {

	private List<Player> mPlayers;
	private List<Integer> mPars;
	private int mParTotal;
	private Map<Player, List<Integer>> mPlayerScores;
	private Map<Player, List<Integer>> mPlayerAverages;

	public ScorecardPlayerPagerAdapter(List<Player> players, List<Integer> pars, Map<Player, List<Integer>> playerScores, Map<Player, List<Integer>> playerAverages) {
		mPlayers = players;
		mPars = pars;
		mParTotal = totalPars(mPars);
		mPlayerScores = playerScores;
		mPlayerAverages = playerAverages;
	}

	@Override
	public int getCount() {
		return mPars != null ? mPars.size() : 0;
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == ((View) object);
	}

	@Override
	public void destroyItem(View view, int integer, Object object) {
		((ViewPager)view).removeView((View)object);
	}

	@Override
	public Object instantiateItem(View collection, final int position) {
		Context pagerContext = collection.getContext();
		ViewPager pager = (ViewPager) collection;

		List<Integer> holeScores = createHoleScores(position);
		List<Integer> courseScoreDifferentials = createCourseScoreDifferentialsList();
		List<Integer> holeAverages = createAveragesList(position);

		ListView playerListView = (ListView) LayoutInflater.from(collection.getContext()).inflate(R.layout.view_scorecard_player_list, null);
		ScorecardPlayerAdapter listAdapter = new ScorecardPlayerAdapter(pagerContext, R.id.scorecard_player_list, position, mPlayers,
				holeScores, courseScoreDifferentials, holeAverages, new ScoreAdjustmentListener() {
					@Override
					public void adjustScore(int holeIndex, Player player, int adjustment) {
						// TODO - make this work
						System.out.println("adjust score at hole: " + (holeIndex + 1) + " for player: " + player.getName() + " by: " + adjustment);
					}
				});
		playerListView.setAdapter(listAdapter);

		pager.addView(playerListView, 0);
		return playerListView;
	}

	private int totalPars(List<Integer> pars) {
		int total = 0;
		for (Integer par : pars) {
			total += par;
		}

		return total;
	}

	private List<Integer> createHoleScores(int holeIndex) {
		List<Integer> holeScores = new ArrayList<Integer>();
		for (Player player : mPlayers) {
			holeScores.add(mPlayerScores.get(player).get(holeIndex));
		}

		return holeScores;
	}

	/**
	 * Creates a list of scores (one for each player) representing the difference between their combined score for
	 *  the entire course, and par for the entire course.
	 * @return
	 */
	private List<Integer> createCourseScoreDifferentialsList() {
		List<Integer> courseScores = new ArrayList<Integer>();
		for (Player player : mPlayers) {
			int total = 0;
			for (Integer score : mPlayerScores.get(player)) {
				total += score;
			}
			total -= mParTotal;
			courseScores.add(total);
		}

		return courseScores;
	}

	private List<Integer> createAveragesList(int holeIndex) {
		List<Integer> averages = new ArrayList<Integer>();
		for (Player player : mPlayers) {
			averages.add(mPlayerAverages.get(player).get(holeIndex));
		}

		return averages;
	}

}