package com.phantomrealm.scorecard.presenter.fragment;

import android.app.Fragment;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;

import com.phantomrealm.scorecard.R;
import com.phantomrealm.scorecard.model.db.DatabaseContract.PlayerEntry;
import com.phantomrealm.scorecard.model.db.DatabaseHelper;

public class EditPlayerFragment extends Fragment {

	private static final String TAG = EditPlayerFragment.class.getSimpleName();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (savedInstanceState == null) {
			Log.d(TAG, "onCreate from scratch");
		} else {
			Log.d(TAG, "onCreate from saved instance");
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Log.d(TAG, "onCreateView");
		View view = inflater.inflate(R.layout.fragment_edit_player, container, false);

		final EditText editText = (EditText) view.findViewById(R.id.edit_player_menu_name_edit_text);
		view.findViewById(R.id.edit_player_menu_button_save).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				savePlayer(0, editText.getText().toString());
			}
		});

		return view;
	}

	public void savePlayer(int playerId, String playerName) {
		// get the db in write mode
		DatabaseHelper dbHelper = new DatabaseHelper(getActivity());
		SQLiteDatabase db = dbHelper.getWritableDatabase();

		// create a new map of values, where column names are the keys
		ContentValues values = new ContentValues();
		values.put(PlayerEntry.COLUMN_NAME, playerName);

		// insert the new row
		long newRowId = db.insert(PlayerEntry.TABLE_NAME, null, values);
		Log.d(TAG, "added entry with id: " + newRowId + ", name: " + playerName);
	}

}