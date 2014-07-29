package com.phantomrealm.scorecard.model.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.phantomrealm.scorecard.model.Course;
import com.phantomrealm.scorecard.model.Player;
import com.phantomrealm.scorecard.model.Scorecard;
import com.phantomrealm.scorecard.util.db.DatabaseContract.ScorecardEntry;
import com.phantomrealm.scorecard.util.db.DatabaseHelper;

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
	 * Update an existing entry in the database representing a given {@link Scorecard}
	 * @param scorecard
	 */
	public void updateScorecard(Scorecard scorecard) {
		// get the db in write mode
		SQLiteDatabase db = mHelper.getWritableDatabase();

		// create a new mape of values, where column names are the keys
		ContentValues values = new ContentValues();
		values.put(ScorecardEntry.COLUMN_COURSE_ID, scorecard.getCourse().getId());
		values.put(ScorecardEntry.COLUMN_DATE, System.currentTimeMillis());

		// describe which row we want to update
		String whereClause = ScorecardEntry._ID + " = " + scorecard.getId();

		// update the existing row
		int rows = db.update(ScorecardEntry.TABLE_NAME, values, whereClause, null);
		Log.d(TAG, "update " + rows + " rows.");

		// update entries for performances
		updatePerformances(scorecard.getId(), scorecard.getPlayerScores());
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
	 * Generates a list of {@link Scorecard} objects based on the contents of the database
	 * @return
	 */
	public List<Scorecard> getScorecardsFromDatabase() {
		// get database and query for all scorecards
		SQLiteDatabase db = mHelper.getReadableDatabase();
		Cursor cursor = getScorecardResultsFromDatabase(db);

		// convert database results into a list of Scorecard objects
		List<Scorecard> scorecards = getScorecardsFromResults(cursor);

		// cleanup
		cursor.close();
		db.close();

		return scorecards;
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
	 * Query the database to get the list of all scorecards
	 * @param db
	 * @return
	 */
	private Cursor getScorecardResultsFromDatabase(SQLiteDatabase db) {
		// define which columns we are interested in
		String[] projection = {ScorecardEntry._ID, ScorecardEntry.COLUMN_COURSE_ID, ScorecardEntry.COLUMN_DATE};

		// query the db
		return db.query(ScorecardEntry.TABLE_NAME, projection, null, null, null, null, null);
	}

	/**
	 * Creates a list of scorecards by iterating through the cursor starting at the first position
	 *  (regardless of the position of the cursor passed into the function). As a side effect the
	 *  position of this cursor will be modified (generally until it is past the final position).
	 * @param cursor
	 * @return
	 */
	private List<Scorecard> getScorecardsFromResults(Cursor cursor) {
		List<Scorecard> scorecards = new ArrayList<Scorecard>();

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			scorecards.add(getScorecardFromCursor(cursor));
			cursor.moveToNext();
		}

		return scorecards;
	}

	/**
	 * Retrieves a {@link Scorecard} from cursor at its current position
	 * @param cursor
	 * @return
	 */
	private Scorecard getScorecardFromCursor(Cursor cursor) {
		long scorecardId = cursor.getLong(cursor.getColumnIndexOrThrow(ScorecardEntry._ID));
		long scorecardDate = cursor.getLong(cursor.getColumnIndexOrThrow(ScorecardEntry.COLUMN_DATE));
		long courseId = cursor.getLong(cursor.getColumnIndexOrThrow(ScorecardEntry.COLUMN_COURSE_ID));
		Course course = CourseEntryUtil.getUtil().getCourseFromDatabase(courseId);

		Map<Player, List<Integer>> playerScores = PerformanceEntryUtil.getUtil().getPerformancesFromDatabase(scorecardId);
		List<Player> players = new ArrayList<Player>();
		for (Player player : playerScores.keySet()) {
			players.add(player);
		}

		Scorecard scorecard = new Scorecard(scorecardId, scorecardDate, course, players);
		for (Player player : players) {
			scorecard.setScoresForPlayer(player, playerScores.get(player));
		}

		return scorecard;
	}

}