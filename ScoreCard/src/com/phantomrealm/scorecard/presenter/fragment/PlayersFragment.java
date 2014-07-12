package com.phantomrealm.scorecard.presenter.fragment;

import java.util.List;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

import com.phantomrealm.scorecard.R;
import com.phantomrealm.scorecard.model.Player;
import com.phantomrealm.scorecard.model.db.PlayerEntryUtil;
import com.phantomrealm.scorecard.presenter.activity.EditPlayerActivity;
import com.phantomrealm.scorecard.view.listadapters.PlayerAdapter;

public class PlayersFragment extends Fragment {

	private static final String TAG = PlayersFragment.class.getSimpleName();
	public static final String INTENT_EXTRA_PLAYER_ID_TAG = "intent_extra_player_id";
	public static final String INTENT_EXTRA_PLAYER_NAME_TAG = "intent_extra_player_name";
	
	private ListView mListView;
	private PlayerAdapter mAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate");
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Log.d(TAG, "onCreateView");
		View view = inflater.inflate(R.layout.fragment_players, container, false);

		mListView = (ListView) view.findViewById(R.id.player_menu_player_list);
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				launchEditPlayerActivity(mAdapter.getPlayer(position));
			}
		});
		mListView.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				promptToDelete(mAdapter.getPlayer(position));
				return true;
			}
		});
		
		view.findViewById(R.id.player_menu_button_new_player).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				launchEditPlayerActivity();
			}
		});

		return view;
	}

	@Override
	public void onResume() {
		Log.d(TAG, "onResume");
		super.onResume();

		populatePlayerList();
	}

	/**
	 * Launch the activity for adding a new player
	 */
	private void launchEditPlayerActivity() {
		launchEditPlayerActivity(null);
	}
	
	/**
	 * Launch the activity for editing a player
	 * @param player the existing player to edit or null to create a new player
	 */
	private void launchEditPlayerActivity(Player player) {
		Intent toLaunch = new Intent(getActivity(), EditPlayerActivity.class);
		if (player != null) {
			toLaunch.putExtra(INTENT_EXTRA_PLAYER_ID_TAG, player.getId());
			toLaunch.putExtra(INTENT_EXTRA_PLAYER_NAME_TAG, player.getName());
		}
		
		startActivity(toLaunch);
	}

	/**
	 * Populate the list of players displayed to the user
	 */
	private void populatePlayerList() {
		List<Player> players = PlayerEntryUtil.getUtil().getPlayersFromDatabase();
		
		mAdapter = new PlayerAdapter(getActivity(), R.layout.list_item_player, R.id.list_player_name, players);
		mListView.setAdapter(mAdapter);
	}

	/**
	 * Display a dialogue asking the user if they wish to delete a given {@link Player}
	 * @param player
	 */
	private void promptToDelete(final Player player) {
		new AlertDialog.Builder(getActivity())
        .setIcon(android.R.drawable.ic_dialog_alert)
        .setTitle(R.string.delete_title)
        .setMessage(getString(R.string.delete_message, player.getName()))
        .setPositiveButton(R.string.confirm_delete, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            	// delete player
            	PlayerEntryUtil.getUtil().deletePlayer(player.getId());

            	// update the list displayed to the user
        		populatePlayerList();
            }
        })
        .setNegativeButton(R.string.cancel_delete, null)
        .show();
	}

}