package com.phantomrealm.scorecard.model.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.phantomrealm.scorecard.model.Course;
import com.phantomrealm.scorecard.model.db.DatabaseContract.CourseEntry;

public class CourseEntryUtil {
	
	private static final String TAG = CourseEntryUtil.class.getSimpleName();
	private static DatabaseHelper mHelper;
	private static CourseEntryUtil mInstance;
	
	public static void initializeInstance(Context context) {
		mHelper = new DatabaseHelper(context);
		mInstance = new CourseEntryUtil();
	}
	
	public static CourseEntryUtil getUtil() {
		return mInstance;
	}

	private CourseEntryUtil() {
	}

	/**
	 * Create a new entry in the database for a course with the given name
	 * @param courseName
	 */
	public void insertCourse(String courseName) {
		// get the db in write mode
		SQLiteDatabase db = mHelper.getWritableDatabase();

		// create a new map of values, where column names are the keys
		ContentValues values = new ContentValues();
		values.put(CourseEntry.COLUMN_NAME, courseName);

		// insert the new row
		long newRowId = db.insert(CourseEntry.TABLE_NAME, null, values);
		Log.d(TAG, "added entry with id: " + newRowId + ", name: " + courseName);
		
		db.close();
	}

	/**
	 * Update an existing entry in the database with the given id by setting the
	 *  name to a given value
	 * @param id of the row to be updated
	 * @param courseName to be set on the updated row
	 */
	public void updateCourse(long id, String courseName) {
		// get the db in write mode
		SQLiteDatabase db = mHelper.getWritableDatabase();

		// create a new map of values, where column names are the keys
		ContentValues values = new ContentValues();
		values.put(CourseEntry.COLUMN_NAME, courseName);

		// describe which row we want to update
		String whereClause = CourseEntry._ID + " = " + id;

		// update the existing row
		int rows = db.update(CourseEntry.TABLE_NAME, values, whereClause, null);
		Log.d(TAG, "updated " + rows + " rows.");
		
		db.close();
	}
	
	/**
	 * Delete an existing entry from the database
	 * @param id of the row to remove from the database
	 */
	public void deleteCourse(long id) {
		// get the db in write mode
		SQLiteDatabase db = mHelper.getWritableDatabase();
		
		// describe which row we want to delete
		String whereClause = CourseEntry._ID + " = " + id;
		
		// delete from the database
		int rows = db.delete(CourseEntry.TABLE_NAME, whereClause, null);
		Log.d(TAG, "deleted " + rows + " rows.");
		
		db.close();
	}

	/**
	 * Generates a list of {@link Course} objects based on the contents of the database
	 * @return
	 */
	public List<Course> getCoursesFromDatabase() {
		// get database and query for all players
		SQLiteDatabase db = mHelper.getReadableDatabase();
		Cursor cursor = getCourseResultsFromDatabase(db);
		
		// convert database results into a list of Course objects
		List<Course> courses = getCoursesFromResults(cursor);
		
		// cleanup
		cursor.close();
		db.close();
		
		return courses;
	}

	/**
	 * Query the database to get the list of existing courses, including ids and names.
	 * @param db
	 * @return
	 */
	private Cursor getCourseResultsFromDatabase(SQLiteDatabase db) {
		// define which columns we are interested in
		String[] projection = { CourseEntry._ID, CourseEntry.COLUMN_NAME };

		// query the db
		return db.query( CourseEntry.TABLE_NAME, projection, null, null, null, null, null);
	}
	
	/**
	 * Creates a list of {@link Course} objects by iterating through the cursor starting at the first
	 *  position (regardless of the position of the cursor passed into the function). As a side effect
	 *  the position of this cursor will be modified (generally until it is past the final position).
	 * @param cursor
	 * @return
	 */
	private List<Course> getCoursesFromResults(Cursor cursor) {
		ArrayList<Course> courses = new ArrayList<Course>();
		
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			courses.add(getCourseFromCursor(cursor));
			cursor.moveToNext();
		}
		
		return courses;
	}
	
	/**
	 * Creates a {@link Course} from the given cursor in the given position. This function will not
	 *  effect the position of the cursor.
	 * @param cursor
	 * @return
	 */
	private Course getCourseFromCursor(Cursor cursor) {
		long id = cursor.getLong(cursor.getColumnIndexOrThrow(CourseEntry._ID));
		String name = cursor.getString(cursor.getColumnIndexOrThrow(CourseEntry.COLUMN_NAME));
		return Course.createDebugCourse(id, name);
	}

}