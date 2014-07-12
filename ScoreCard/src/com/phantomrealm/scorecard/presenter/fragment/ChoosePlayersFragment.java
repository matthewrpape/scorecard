package com.phantomrealm.scorecard.presenter.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.phantomrealm.scorecard.R;
import com.phantomrealm.scorecard.model.Player;
import com.phantomrealm.scorecard.model.db.PlayerEntryUtil;
import com.phantomrealm.scorecard.presenter.activity.EditPlayerActivity;
import com.phantomrealm.scorecard.view.PlayerAdapter;

public class ChoosePlayersFragment extends Fragment {

	public static final String INTENT_EXTRA_PLAYER_ID_LIST = "intent_extra_player_ids";
	public static final String INTENT_EXTRA_PLAYER_NAME_LIST = "intent_extra_player_names";
	private static final String TAG = ChoosePlayersFragment.class.getSimpleName();

	private ListView mListView;
	private PlayerAdapter mAdapter;
	private List<Player> mPlayers;
	private View mStartButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate");
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Log.d(TAG, "onCreateView");
		View view = inflater.inflate(R.layout.fragment_choose_players, container, false);

		mListView = (ListView) view.findViewById(R.id.choose_players_menu_player_list);
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				togglePlayer(view, mAdapter.getPlayer(position));
			}
		});

		view.findViewById(R.id.choose_players_menu_button_new_player).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				launchEditPlayerActivity();
			}
		});

		mStartButton = view.findViewById(R.id.choose_players_menu_button_start_game);
		mStartButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				selectPlayers();
			}
		});

		return view;
	}

	@Override
	public void onResume() {
		Log.d(TAG, "onResume");
		super.onResume();

		populatePlayerList();
		mPlayers = new ArrayList<Player>();
		mStartButton.setEnabled(false);
	}

	/**
	 * Activates previously inactive players and adds them to the list of players to be included in the selection.
	 *  Alternatively, deactivates previously activated players and removes them from the list of players to be
	 *  included in the selection. As a side effect, adding a player to a previously empty list will enable the
	 *  start game button. Removing the last player from a list which is about to become empty will deactivate
	 *  the start button.
	 * @param view
	 * @param player
	 */
	private void togglePlayer(View view, Player player) {
		boolean shouldActivate = !view.isActivated();
		
		if (shouldActivate) {
			activatePlayer(view, player);
		} else {
			deactivatePlayer(view, player);
		}
	}

	/**
	 * Activates a previously inactive player and adds it to the list of players to be included in the selection.
	 *  As a side effect, adding a player to a previously empty list will enable the start game button.
	 * @param view
	 * @param player
	 */
	private void activatePlayer(View view, Player player) {
		view.setActivated(true);
		mPlayers.add(player);
		if (mPlayers.size() == 1) {
			mStartButton.setEnabled(true);
		}
	}

	/**
	 * Deactivates a previously active player and removes it from the list of players to be included in the selection.
	 *  As a side effect, removing the last player from a list that is about to become empty will enable the start
	 *  game button.
	 * @param view
	 * @param player
	 */
	private void deactivatePlayer(View view, Player player) {
		view.setActivated(false);
		mPlayers.remove(player);
		if (mPlayers.isEmpty()) {
			mStartButton.setEnabled(false);
		}
	}

	/**
	 * Launch the activity for adding a new player
	 */
	private void launchEditPlayerActivity() {
		Intent toLaunch = new Intent(getActivity(), EditPlayerActivity.class);
		
		startActivity(toLaunch);
	}

	private void selectPlayers() {
		ArrayList<Integer> playerIds = new ArrayList<Integer>();
		ArrayList<String> playerNames = new ArrayList<String>();
		for (Player player : mPlayers) {
			playerIds.add(((Long) player.getId()).intValue());
			playerNames.add(player.getName());
		}

		Intent result = new Intent();
		result.putIntegerArrayListExtra(INTENT_EXTRA_PLAYER_ID_LIST, playerIds);
		result.putStringArrayListExtra(INTENT_EXTRA_PLAYER_NAME_LIST, playerNames);
		getActivity().setResult(Activity.RESULT_OK, result);

		getActivity().finish();
	}

	/**
	 * Populate the list of players displayed to the user
	 */
	private void populatePlayerList() {
		List<Player> players = PlayerEntryUtil.getUtil().getPlayersFromDatabase();
		
		mAdapter = new PlayerAdapter(getActivity(), R.layout.list_item_player, R.id.list_player_name, players);
		mListView.setAdapter(mAdapter);
	}

}