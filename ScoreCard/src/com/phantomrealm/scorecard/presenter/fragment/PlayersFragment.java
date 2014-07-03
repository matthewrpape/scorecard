package com.phantomrealm.scorecard.presenter.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;

import com.phantomrealm.scorecard.R;
import com.phantomrealm.scorecard.model.Player;
import com.phantomrealm.scorecard.model.db.DatabaseContract.PlayerEntry;
import com.phantomrealm.scorecard.model.db.DatabaseHelper;
import com.phantomrealm.scorecard.presenter.activity.EditPlayerActivity;
import com.phantomrealm.scorecard.view.PlayerAdapter;

public class PlayersFragment extends Fragment {

	private static final String TAG = PlayersFragment.class.getSimpleName();
	
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
	 * Launch the activity for editing a player
	 */
	private void launchEditPlayerActivity() {
		Intent toLaunch = new Intent(getActivity(), EditPlayerActivity.class);
		startActivity(toLaunch);
	}

	/**
	 * Populate the list of players displayed to the user
	 */
	private void populatePlayerList() {
		Cursor cursor = getPlayerResultsFromDatabase();
		List<Player> players = getPlayersFromResults(cursor);
		cursor.close();
		
		mAdapter = new PlayerAdapter(getActivity(), R.layout.list_item_player, R.id.list_player_name, players);
		mListView.setAdapter(mAdapter);
	}
	
	/**
	 * Query the database to get the list of existing players, including ids and names.
	 * @return
	 */
	private Cursor getPlayerResultsFromDatabase() {
		// get the db in read mode
		DatabaseHelper dbHelper = new DatabaseHelper(getActivity());
		SQLiteDatabase db = dbHelper.getReadableDatabase();

		// define which columns we are interested in
		String[] projection = { PlayerEntry._ID, PlayerEntry.COLUMN_NAME };

		// how we want the results sorted
		String sortOrder = PlayerEntry._ID + " ASC";

		return db.query( PlayerEntry.TABLE_NAME, projection, null, null, null, null, sortOrder);
	}
	
	/**
	 * Creates a list of {@link Player} objects by iterating through the cursor starting at the first
	 *  position (regardless of the position of the cursor passed into the function). As a side effect
	 *  the position of this cursor will be modified (generally until it is past the final position).
	 * @param cursor
	 * @return
	 */
	private List<Player> getPlayersFromResults(Cursor cursor) {
		ArrayList<Player> players = new ArrayList<Player>();
		
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			players.add(getPlayerFromCursor(cursor));
			cursor.moveToNext();
		}
		
		return players;
	}
	
	/**
	 * Creates a {@link Player} from the given cursor in the given position. This function will not
	 *  effect the position of the cursor.
	 * @param cursor
	 * @return
	 */
	private Player getPlayerFromCursor(Cursor cursor) {
		long id = cursor.getLong(cursor.getColumnIndexOrThrow(PlayerEntry._ID));
		String name = cursor.getString(cursor.getColumnIndexOrThrow(PlayerEntry.COLUMN_NAME));
		return new Player(id, name);
	}

}