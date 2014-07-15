package com.phantomrealm.scorecard.view.pageradapters;

import java.util.List;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.phantomrealm.scorecard.R;
import com.phantomrealm.scorecard.model.Player;
import com.phantomrealm.scorecard.view.listadapters.ScorecardPlayerAdapter;

public class ScorecardPlayerPagerAdapter extends PagerAdapter {

	private List<Player> mPlayers;
	private List<Integer> mPars;

	public ScorecardPlayerPagerAdapter(List<Player> players, List<Integer> pars) {
		mPlayers = players;
		mPars = pars;
	}

	@Override
	public int getCount() {
		return mPars != null ? mPars.size() : 0;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == ((View)arg1);
	}

	@Override
	public void destroyItem(View arg0, int arg1, Object arg2) {
		((ViewPager)arg0).removeView((View)arg2);
	}

	@Override
	public Object instantiateItem(View collection, final int position) {
		Context pagerContext = collection.getContext();
		ViewPager pager = (ViewPager) collection;

		ListView playerListView = (ListView) LayoutInflater.from(collection.getContext()).inflate(R.layout.view_scorecard_player_list, null);
		ScorecardPlayerAdapter listAdapter = new ScorecardPlayerAdapter(pagerContext, R.id.scorecard_player_list, mPlayers, null, null);
		playerListView.setAdapter(listAdapter);

		pager.addView(playerListView, 0);
		return playerListView;
	}

}