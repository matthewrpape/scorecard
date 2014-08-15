package com.phantomrealm.scorecard.model.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.phantomrealm.scorecard.model.Player;
import com.phantomrealm.scorecard.util.db.DatabaseContract.PerformanceEntry;
import com.phantomrealm.scorecard.util.db.DatabaseHelper;
import com.phantomrealm.scorecard.util.db.DatabaseUtils;

public class PerformanceEntryUtil {

	private static final String TAG = PerformanceEntryUtil.class.getSimpleName();
	private static DatabaseHelper mHelper;
	private static PerformanceEntryUtil mInstance;
	
	public static void initializeInstance(Context context) {
		mHelper = new DatabaseHelper(context);
		mInstance = new PerformanceEntryUtil();
	}
	
	public static PerformanceEntryUtil getUtil() {
		return mInstance;
	}

	private PerformanceEntryUtil() {
	}

	/**
	 * Create a new entry in the database representing a single performance by a particular player
	 *  on a particular course.
	 * @param scorecardId denoting the scorecard to associate this performance with
	 * @param playerId denoting the player whose performance this is
	 * @param scores list containing one entry for each hole of the course
	 */
	public void insertPerformance(long scorecardId, long playerId, List<Integer> scores) {
		// get the db in write mode
		SQLiteDatabase db = mHelper.getWritableDatabase();

		// create a new map of values, where column names are the keys
		ContentValues values = new ContentValues();
		values.put(PerformanceEntry.COLUMN_SCORECARD_ID, scorecardId);
		values.put(PerformanceEntry.COLUMN_PLAYER_ID, playerId);
		values.put(PerformanceEntry.COLUMN_SCORES, DatabaseUtils.buildStringFromList(scores));

		// insert the new row
		long newRowId = db.insert(PerformanceEntry.TABLE_NAME, null, values);
		Log.d(TAG, "added a new performance with id: " + newRowId);
	}

	/**
	 * Update an existing entry in the database with the given id
	 * @param performanceId denoting the row to be updated
	 * @param scorecardId denoting the scorecard to associate this performance with
	 * @param playerId denoting the player whose performance this is
	 * @param scores list containing one entry for each hole of the course
	 */
	public void updatePerformance(long performanceId, long scorecardId, long playerId, List<Integer> scores) {
		// get the db in write mode
		SQLiteDatabase db = mHelper.getWritableDatabase();

		// create a new map of values, where column names are the keys
		ContentValues values = new ContentValues();
		values.put(PerformanceEntry.COLUMN_SCORECARD_ID, scorecardId);
		values.put(PerformanceEntry.COLUMN_PLAYER_ID, playerId);
		values.put(PerformanceEntry.COLUMN_SCORES, DatabaseUtils.buildStringFromList(scores));

		// describe which row we want to update
		String whereClause = PerformanceEntry._ID + " = " + performanceId;

		// update the existing row
		int rows = db.update(PerformanceEntry.TABLE_NAME, values, whereClause, null);
		Log.d(TAG, "updated " + rows + " rows.");
	}

	/**
	 * Delete an existing entry from the database
	 * @param id of the row to remove from the database
	 */
	public void deletePerformance(long id) {
		// get the db in write mode
		SQLiteDatabase db = mHelper.getWritableDatabase();

		// describe which row we want to delete
		String whereClause = PerformanceEntry._ID + " = " + id;

		// delete from the database
		int rows = db.delete(PerformanceEntry.TABLE_NAME, whereClause, null);
		Log.d(TAG, "deleted " + rows + " rows.");

		db.close();
	}

	/**
	 * Queries the database for all performances associated with the given scorecard id
	 *  and deletes them
	 * @param scorecardId
	 */
	public void deletePerformancesForScorecard(long scorecardId) {
		// get database and query for entries associated with the specific scorecardId
		SQLiteDatabase db = mHelper.getWritableDatabase();
		Cursor cursor = getPerformanceIdsFromDatabase(db, scorecardId);

		// convert database results into a list of performance ids
		List<Long> performanceIds = getPerformanceIdsFromResults(cursor);

		// delete rows corresponding to performance ids
		for (Long id : performanceIds) {
			deletePerformance(id);
		}
	}

	/**
	 * Generates a map of players to a list of scores for a specific scorecard
	 * @param scorecardId denoting the scorecard whose performances are to be looked up
	 * @return
	 */
	public Map<Player, List<Integer>> getPerformancesFromDatabase(long scorecardId) {
		// get database and query for entries associated with the specified scorecardId
		SQLiteDatabase db = mHelper.getReadableDatabase();
		Cursor cursor = getPerformanceResultsFromDatabase(db, scorecardId);

		// convert database results into a map of players to score lists
		Map<Player, List<Integer>> performances = getPerformancesFromResults(cursor);

		// cleanup
		cursor.close();
		db.close();

		return performances;
	}

	/**
	 * Returns the id of the performance associated with a specified scorecard and player,
	 *  or 0 if none is found;
	 * @param scorecardId
	 * @param playerId
	 * @return
	 */
	public long getPerformanceIdFromDatabase(long scorecardId, long playerId) {
		// get database and query for entries associated with the specified scorecard and player
		SQLiteDatabase db = mHelper.getReadableDatabase();
		Cursor cursor = getPerformanceIdResultsFromDatabase(db, scorecardId, playerId);

		// convert database results into a map of players to score lists
		long performanceId = getPerformanceIdFromResults(cursor);

		// cleanup
		cursor.close();
		db.close();

		return performanceId;
	}

	/**
	 * Returns a list containing the average score of a particular player for each hole of a
	 *  particular course
	 * @param playerId
	 * @param courseId
	 * @return
	 */
	public List<Integer> getPerformanceAveragesFromDatabase(long playerId, long courseId) {
		// get database and query for entries associated with the specified player and course
		SQLiteDatabase db = mHelper.getReadableDatabase();
		Cursor cursor = getPerformanceScoreResultsFromDatabase(db, playerId, courseId);

		// convert database results into a list of lists of scores
		List<List<Integer>> performanceScores = getPerformanceScoresFromResults(cursor);

		// convert scores into averages
		List<Integer> performanceAverages = getAveragesFromScores(performanceScores);

		// cleanup
		cursor.close();
		db.close();

		return performanceAverages;
	}

	/**
	 * Query the database to get the list of performances that correspond to a specific scorecard.
	 * @param db
	 * @param scorecardId
	 * @return
	 */
	private Cursor getPerformanceResultsFromDatabase(SQLiteDatabase db, long scorecardId) {
		// define which columns we are interested in
		String[] projection = {PerformanceEntry._ID, PerformanceEntry.COLUMN_PLAYER_ID, PerformanceEntry.COLUMN_SCORES};
		String whereClause = String.format("%s = ?", PerformanceEntry.COLUMN_SCORECARD_ID);
		String[] whereValues = new String[] { Long.toString(scorecardId) };

		// query the db
		return db.query(PerformanceEntry.TABLE_NAME, projection, whereClause, whereValues, null, null, null);
	}

	/**
	 * Query the database to get the list of performance ids that correspond to a specific scorecard and player.
	 * @param db
	 * @param scorecardId
	 * @param playerId
	 * @return
	 */
	private Cursor getPerformanceIdResultsFromDatabase(SQLiteDatabase db, long scorecardId, long playerId) {
		// define which columns we are interested in
		String[] projection = {PerformanceEntry._ID, PerformanceEntry.COLUMN_PLAYER_ID, PerformanceEntry.COLUMN_SCORES};
		String whereClause = String.format("%s = ? AND %s = ?", PerformanceEntry.COLUMN_SCORECARD_ID, PerformanceEntry.COLUMN_PLAYER_ID);
		String[] whereValues = new String[] { Long.toString(scorecardId), Long.toString(playerId) };

		// query the db
		return db.query(PerformanceEntry.TABLE_NAME, projection, whereClause, whereValues, null, null, null);
	}

	/**
	 * Query the database to get the list of performance ids that correspond to a specific scorecard.
	 * @param db
	 * @param scorecardId
	 * @return
	 */
	private Cursor getPerformanceIdsFromDatabase(SQLiteDatabase db, long scorecardId) {
		// define which columns we are interested in
		String[] projection = {PerformanceEntry._ID};
		String whereClause = String.format("%s = ?", PerformanceEntry.COLUMN_SCORECARD_ID);
		String[] whereValues = new String[] { Long.toString(scorecardId) };

		// query the db
		return db.query(PerformanceEntry.TABLE_NAME, projection, whereClause, whereValues, null, null, null);
	}

	/**
	 * Query the database to get a list of past performance scores for a particular player on a
	 *  particular course.
	 * @param db
	 * @param playerId
	 * @param courseId
	 * @return cursor to database results, or null if none exist
	 */
	private Cursor getPerformanceScoreResultsFromDatabase(SQLiteDatabase db, long playerId, long courseId) {
		// find out which scorecards occured at the specified course
		List<Long> scorecardIds = ScorecardEntryUtil.getUtil().getScorecardIdsFromDatabase(courseId);

		// define which columns we are interested in
		String[] projection = {PerformanceEntry.COLUMN_SCORES};
		String whereClause = String.format("%s IN (%s) AND %s = '%s'", PerformanceEntry.COLUMN_SCORECARD_ID,
		        DatabaseUtils.buildStringFromList(scorecardIds), PerformanceEntry.COLUMN_PLAYER_ID, String.valueOf(playerId));

		// query the db
		return db.query(PerformanceEntry.TABLE_NAME, projection, whereClause, null, null, null, null);
	}

	/**
	 * Creates a map from players to scores by iterating through the cursor starting at the first
	 *  position (regardless of the position of the cursor passed into the function). As a side effect
	 *  the position of this cursor will be modified (generally until it is past the final position).
	 * @param cursor
	 * @return
	 */
	private Map<Player, List<Integer>> getPerformancesFromResults(Cursor cursor) {
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
	 * Creates an id that corresponds to the performance in the first position of the cursor. The given
	 *  cursor should only contain a single result. In the even that it contains more, subsequent results
	 *  will be ignored. As a side effect the position of this cursor will be set to the first position.
	 * @param cursor
	 * @return
	 */
	private long getPerformanceIdFromResults(Cursor cursor) {
		cursor.moveToFirst();
		return getIdFromCursor(cursor);
	}

	/**
	 * Creates a list of performance ids by iterating through the cursor starting at the first
	 *  position (regardless of the position of the cursor passed into the function). As a side effect
	 *  the position of this cursor will be modified (generally until it is past the final position).
	 */
	private List<Long> getPerformanceIdsFromResults(Cursor cursor) {
		List<Long> performanceIds = new ArrayList<Long>();

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			performanceIds.add(getIdFromCursor(cursor));
			cursor.moveToNext();
		}

		return performanceIds;
	}

	/**
	 * Creates a list containing lists of scores by iterating through the cursor starting at the first
	 *  position (regardless of the position of the cursor passed into the function.) As a side effect
	 *  the position of this cursor will be modified (generally until it is past the final position).
	 * @param cursor
	 * @return
	 */
	private List<List<Integer>> getPerformanceScoresFromResults(Cursor cursor) {
		List<List<Integer>> performanceScores = new ArrayList<List<Integer>>();

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			performanceScores.add(getScoresFromCursor(cursor));
			cursor.moveToNext();
		}

		return performanceScores;
	}

	/**
	 * Retrieves a {@link Player} from the database, according to the player id specified by the
	 *  cursor at its current position.
	 * @param cursor
	 * @return
	 */
	private Player getPlayerFromCursor(Cursor cursor) {
		long playerId = cursor.getLong(cursor.getColumnIndexOrThrow(PerformanceEntry.COLUMN_PLAYER_ID));
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

	/**
	 * Gets the performance id from the given cursor at its current position
	 * @param cursor
	 * @return
	 */
	private long getIdFromCursor(Cursor cursor) {
		long id = cursor.getLong(cursor.getColumnIndexOrThrow(PerformanceEntry._ID));

		return id;
	}

	/**
	 * Creates a list of averages for each hole, based on a list of list of scores for each hole
	 * @param performanceScores
	 * @return
	 */
	private List<Integer> getAveragesFromScores(List<List<Integer>> performanceScores) {
		// initialize list of averages at 0
		List<Integer> performanceAverages = new ArrayList<Integer>();
		if (performanceScores.size() > 0) {
			int holes = performanceScores.get(0).size();
			for (int i = 0; i < holes; ++i) {
				performanceAverages.add(0);
			}
	
			// total the scores for each hole from each performance
			int performances = performanceScores.size();
			for (int perf = 0; perf < performances; ++perf) {
				for (int hole = 0; hole < holes; ++hole) {
					int previousScore = performanceAverages.get(hole);
					int newScore = performanceScores.get(perf).get(hole);
					performanceAverages.set(hole, previousScore + newScore);
				}
			}
	
			// average the scores for each hole
			for (int hole = 0; hole < holes; ++hole) {
				int previousScore = performanceAverages.get(hole);
				performanceAverages.set(hole, previousScore / performances);
			}
		}

		return performanceAverages;
	}
}