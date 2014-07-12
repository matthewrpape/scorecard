package com.phantomrealm.scorecard.presenter.fragment;

import java.util.ArrayList;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.phantomrealm.scorecard.R;
import com.phantomrealm.scorecard.model.Player;
import com.phantomrealm.scorecard.view.listadapters.ScorecardPlayerAdapter;

public class ScorecardFragment extends Fragment {

	private static final String TAG = ScorecardFragment.class.getSimpleName();
	
	private ListView mListView;
	private ScorecardPlayerAdapter mAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate");
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Log.d(TAG, "onCreateView");
		View view = inflater.inflate(R.layout.fragment_scorecard, container, false);
		mListView = (ListView) view.findViewById(R.id.scorecard_player_list);

		return view;
	}

	@Override
	public void onResume() {
		Log.d(TAG, "onResume");
		super.onResume();

		populatePlayerList();
	}

	/**
	 * Populate the list of courses displayed to the user
	 */
	private void populatePlayerList() {
		//TODO - finish this
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(new Player(1, "Matt Pape"));
		players.add(new Player(2, "Lisa Good"));

		mAdapter = new ScorecardPlayerAdapter(getActivity(), R.layout.view_scorecard_player, players, null, null);
		mListView.setAdapter(mAdapter);
	}

}