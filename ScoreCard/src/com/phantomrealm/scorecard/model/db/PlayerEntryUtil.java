package com.phantomrealm.scorecard.model.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.phantomrealm.scorecard.model.Player;
import com.phantomrealm.scorecard.model.db.DatabaseContract.PlayerEntry;

public class PlayerEntryUtil {
	
	private static final String TAG = PlayerEntryUtil.class.getSimpleName();
	private static DatabaseHelper mHelper;
	private static PlayerEntryUtil mInstance;
	
	public static void initializeInstance(Context context) {
		mHelper = new DatabaseHelper(context);
		mInstance = new PlayerEntryUtil();
	}
	
	public static PlayerEntryUtil getUtil() {
		return mInstance;
	}

	private PlayerEntryUtil() {
	}

	/**
	 * Create a new entry in the database for a player with the given name
	 * @param playerName
	 */
	public void insertPlayer(String playerName) {
		// get the db in write mode
		SQLiteDatabase db = mHelper.getWritableDatabase();

		// create a new map of values, where column names are the keys
		ContentValues values = new ContentValues();
		values.put(PlayerEntry.COLUMN_NAME, playerName);

		// insert the new row
		long newRowId = db.insert(PlayerEntry.TABLE_NAME, null, values);
		Log.d(TAG, "added entry with id: " + newRowId + ", name: " + playerName);
	}

	/**
	 * Update an existing entry in the database with the given id by setting the
	 *  name to a given value
	 * @param id of the row to be updated
	 * @param playerName to be set on the updated row
	 */
	public void updatePlayer(long id, String playerName) {
		// get the db in write mode
		SQLiteDatabase db = mHelper.getWritableDatabase();

		// create a new map of values, where column names are the keys
		ContentValues values = new ContentValues();
		values.put(PlayerEntry.COLUMN_NAME, playerName);

		// describe which row we want to update
		String whereClause = PlayerEntry._ID + " = " + id;

		// update the existing row
		int rows = db.update(PlayerEntry.TABLE_NAME, values, whereClause, null);
		Log.d(TAG, "updated " + rows + " rows.");
	}

	/**
	 * Generates a list of {@link Player} objects based on the contents of the database
	 * @return
	 */
	public List<Player> getPlayersFromDatabase() {
		Cursor cursor = getPlayerResultsFromDatabase();
		List<Player> players = getPlayersFromResults(cursor);
		cursor.close();
		
		return players;
	}

	/**
	 * Query the database to get the list of existing players, including ids and names.
	 * @return
	 */
	private Cursor getPlayerResultsFromDatabase() {
		// get the db in read mode
		SQLiteDatabase db = mHelper.getReadableDatabase();

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