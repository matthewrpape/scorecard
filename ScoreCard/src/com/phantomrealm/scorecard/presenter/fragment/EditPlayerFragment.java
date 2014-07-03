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
	
	private long mId;
	private String mName;
	
	public EditPlayerFragment(long id, String name) {
		mId = id;
		mName = name;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate");
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Log.d(TAG, "onCreateView");
		View view = inflater.inflate(R.layout.fragment_edit_player, container, false);

		final EditText editText = (EditText) view.findViewById(R.id.edit_player_menu_name_edit_text);
		if (mName != null) {
			editText.setText(mName);
		}
			
		view.findViewById(R.id.edit_player_menu_button_save).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				savePlayer(editText.getText().toString());
			}
		});

		return view;
	}

	public void savePlayer(String playerName) {
		if (mId > 0) {
			updatePlayer(mId, playerName);
		} else {
			insertPlayer(playerName);
		}
	}

	private void updatePlayer(long id, String playerName) {
		// get the db in write mode
		DatabaseHelper dbHelper = new DatabaseHelper(getActivity());
		SQLiteDatabase db = dbHelper.getWritableDatabase();

		// create a new map of values, where column names are the keys
		ContentValues values = new ContentValues();
		values.put(PlayerEntry.COLUMN_NAME, playerName);

		// describe which row we want to update
		String whereClause = PlayerEntry._ID + " = " + id;

		// update the existing row
		int rows = db.update(PlayerEntry.TABLE_NAME, values, whereClause, null);
		Log.d(TAG, "updated " + rows + " rows.");
	}

	private void insertPlayer(String playerName) {
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