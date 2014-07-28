package com.phantomrealm.scorecard.model.db;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.phantomrealm.scorecard.model.Player;
import com.phantomrealm.scorecard.model.Scorecard;
import com.phantomrealm.scorecard.util.db.DatabaseContract.CourseEntry;
import com.phantomrealm.scorecard.util.db.DatabaseContract.PerformanceEntry;
import com.phantomrealm.scorecard.util.db.DatabaseContract.ScorecardEntry;
import com.phantomrealm.scorecard.util.db.DatabaseHelper;
import com.phantomrealm.scorecard.util.db.DatabaseUtils;

public class ScorecardEntryUtil {

	private static final String TAG = ScorecardEntryUtil.class.getSimpleName();
	private static DatabaseHelper mHelper;
	private static ScorecardEntryUtil mInstance;
	
	public static void initializeInstance(Context context) {
		mHelper = new DatabaseHelper(context);
		mInstance = new ScorecardEntryUtil();
	}
	
	public static ScorecardEntryUtil getUtil() {
		return mInstance;
	}

	private ScorecardEntryUtil() {
	}

	/**
	 * Create a new entry in the database representing a given {@link Scorecard}
	 * @param scorecard
	 */
	public void insertScorecard(Scorecard scorecard) {
		// get the db in write mode
		SQLiteDatabase db = mHelper.getWritableDatabase();

		// create a new map of values, where column names are the keys
		ContentValues values = new ContentValues();
		values.put(ScorecardEntry.COLUMN_COURSE_ID, scorecard.getCourse().getId());
		values.put(ScorecardEntry.COLUMN_DATE, System.currentTimeMillis());

		// insert the new row
		long scorecardId = db.insert(ScorecardEntry.TABLE_NAME, null, values);
		Log.d(TAG, "added a new scorecard with id: " + scorecardId);

		// create entries for performances
		insertPerformances(scorecardId, scorecard.getPlayerScores());
	}

	/**
	 * Update an existing entry in the database with the given id
	 * @param scorecardId denoting the row to be updated
	 * @param scorecard
	 */
	public void updateScorecard(long scorecardId, Scorecard scorecard) {
		// get the db in write mode
		SQLiteDatabase db = mHelper.getWritableDatabase();

		// create a new mape of values, where column names are the keys
		ContentValues values = new ContentValues();
		values.put(ScorecardEntry.COLUMN_COURSE_ID, scorecard.getCourse().getId());
		values.put(ScorecardEntry.COLUMN_DATE, System.currentTimeMillis());

		// describe which row we want to update
		String whereClause = ScorecardEntry._ID + " = " + scorecardId;

		// update the existing row
		int rows = db.update(ScorecardEntry.TABLE_NAME, values, whereClause, null);
		Log.d(TAG, "update " + rows + " rows.");

		// update entries for performances
		updatePerformances(scorecardId, scorecard.getPlayerScores());
	}

	/**
	 * Delete an existing entry from the database
	 * @param id of the row to remove from the database
	 */
	public void deleteScorecard(long id) {
		// delete performances associated with this scorecard
		PerformanceEntryUtil.getUtil().deletePerformancesForScorecard(id);

		// get the db in write mode
		SQLiteDatabase db = mHelper.getWritableDatabase();

		// describe which row we want to delete
		String whereClause = ScorecardEntry._ID + " = ?";
		String[] whereArgs = new String[]{ Long.toString(id) };

		// delete from the database
		int rows = db.delete(ScorecardEntry.TABLE_NAME, whereClause, whereArgs);
		Log.d(TAG, "deleted " + rows + " rows.");

		db.close();
	}

	/**
	 * Generates a map of players to a list of scores for a specific scorecard
	 * @param scorecardId denoting the scorecard whose performances are to be looked up
	 * @return
	 */
	public Map<Player, List<Integer>> getPerformancesFromDatabase(int scorecardId) {
		// get database and query for entries associated with the specific scorecardId
		SQLiteDatabase db = mHelper.getReadableDatabase();
		Cursor cursor = getPerformanceResultsFromDatabase(db, scorecardId);

		// convert database results into a map of players to score lists
		Map<Player, List<Integer>> performances = getPerformanceResults(cursor);

		// cleanup
		cursor.close();
		db.close();

		return performances;
	}

	/**
	 * Create performance entries for the given players, and associate them with a specific scorecard.
	 * @param scorecardId
	 * @param scores
	 */
	private void insertPerformances(long scorecardId, Map<Player, List<Integer>> scores) {
		for (Player player : scores.keySet()) {
			PerformanceEntryUtil.getUtil().insertPerformance(scorecardId, player.getId(), scores.get(player));
		}
	}

	/**
	 * TODO - implement this!
	 * @param scorecardId
	 * @param scores
	 */
	private void updatePerformances(long scorecardId, Map<Player, List<Integer>> scores) {
		// TODO - finish this
	}

	/**
	 * Query the database to get the list of performances that correspond to a specific scorecard.
	 * @param db
	 * @param scorecardId
	 * @return
	 */
	private Cursor getPerformanceResultsFromDatabase(SQLiteDatabase db, int scorecardId) {
		// define which columns we are interested in
		String[] projection = {PerformanceEntry._ID, PerformanceEntry.COLUMN_PLAYER_ID, PerformanceEntry.COLUMN_SCORES};
		String whereClause = String.format("%s = ?", PerformanceEntry.COLUMN_SCORECARD_ID);
		String[] whereValues = new String[] { Integer.toString(scorecardId) };

		// query the db
		return db.query( CourseEntry.TABLE_NAME, projection, whereClause, whereValues, null, null, null);
	}

	/**
	 * Creates a map from players to scores by iterating through the cursor starting at the first
	 *  position (regardless of the position of the cursor passed into the function). As a side effect
	 *  the position of this cursor will be modified (generally until it is past the final position).
	 * @param cursor
	 * @return
	 */
	private Map<Player, List<Integer>> getPerformanceResults(Cursor cursor) {
		HashMap<Player, List<Integer>> performances = new HashMap<Player, List<Integer>>();

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Player player = getPlayerFromCursor(cursor);
			List<Integer> scores = getScoresFromCursor(cursor);
			performances.put(player, scores);
			cursor.moveToNext();
		}

		return performances;
	}

	/**
	 * Retrieves a {@link Player} from the database, according to the player id specified by the
	 *  cursor at its current position.
	 * @param cursor
	 * @return
	 */
	private Player getPlayerFromCursor(Cursor cursor) {
		long playerId = cursor.getLong(cursor.getColumnIndexOrThrow(PerformanceEntry._ID));
		List<Player> players = PlayerEntryUtil.getUtil().getPlayersFromDatabase();
		Player returnPlayer = null;
		for (Player player : players) {
			if (player.getId() == playerId) {
				returnPlayer = player;
				break;
			}
		}

		return returnPlayer;
	}

	/**
	 * Creates a list of scores from the given cursor at its current position.
	 * @param cursor
	 * @return
	 */
	private List<Integer> getScoresFromCursor(Cursor cursor) {
		String scoresString = cursor.getString(cursor.getColumnIndexOrThrow(PerformanceEntry.COLUMN_SCORES));
		List<Integer> scores = DatabaseUtils.buildListFromString(scoresString);

		return scores;
	}

}