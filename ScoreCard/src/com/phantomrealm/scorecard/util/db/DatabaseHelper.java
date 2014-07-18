package com.phantomrealm.scorecard.util.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.phantomrealm.scorecard.util.db.DatabaseContract.CourseEntry;
import com.phantomrealm.scorecard.util.db.DatabaseContract.PerformanceEntry;
import com.phantomrealm.scorecard.util.db.DatabaseContract.PlayerEntry;
import com.phantomrealm.scorecard.util.db.DatabaseContract.ScorecardEntry;

public class DatabaseHelper extends SQLiteOpenHelper {

	// If you change the database schema, you must increment the database version.
	public static final int DATABASE_VERSION = 2;
	public static final String DATABASE_NAME = "scorecard.db";
	private static final String INT_TYPE = " INTEGER";
	private static final String KEY_TYPE = INT_TYPE + " PRIMARY KEY";
	private static final String TEXT_TYPE = " TEXT";
	private static final String COMMA_SEP = ",";
	private static final String SQL_CREATE_PLAYER_TABLE = "CREATE TABLE "
			+ PlayerEntry.TABLE_NAME + " (" + PlayerEntry._ID + KEY_TYPE + COMMA_SEP
			+ PlayerEntry.COLUMN_NAME + TEXT_TYPE + ")";
	private static final String SQL_DELETE_PLAYER_TABLE = "DROP TABLE IF EXISTS "
			+ PlayerEntry.TABLE_NAME;
	private static final String SQL_CREATE_COURSE_TABLE = "CREATE TABLE "
			+ CourseEntry.TABLE_NAME + " (" + CourseEntry._ID + KEY_TYPE + COMMA_SEP
			+ CourseEntry.COLUMN_NAME + TEXT_TYPE + COMMA_SEP
			+ CourseEntry.COLUMN_PARS + TEXT_TYPE + ")";
	private static final String SQL_DELETE_COURSE_TABLE = "DROP TABLE IF EXISTS "
			+ CourseEntry.TABLE_NAME;
	private static final String SQL_CREATE_PERFORMANCE_TABLE = "CREATE TABLE "
			+ PerformanceEntry.TABLE_NAME + " (" + PerformanceEntry._ID + KEY_TYPE + COMMA_SEP
			+ PerformanceEntry.COLUMN_PLAYER_ID + INT_TYPE + COMMA_SEP
			+ PerformanceEntry.COLUMN_COURSE_ID + INT_TYPE + COMMA_SEP
			+ PerformanceEntry.COLUMN_SCORES + TEXT_TYPE + ")";
	private static final String SQL_DELETE_PERFORMANCE_TABLE = "DROP TABLE IF EXISTS "
			+ PerformanceEntry.TABLE_NAME;
	private static final String SQL_CREATE_SCORECARD_TABLE = "CREATE TABLE "
			+ ScorecardEntry.TABLE_NAME + " (" + ScorecardEntry._ID + KEY_TYPE + COMMA_SEP
			+ ScorecardEntry.COLUMN_DATE + TEXT_TYPE + COMMA_SEP
			+ ScorecardEntry.COLUMN_PERFORMANCES + TEXT_TYPE + ")";
	private static final String SQL_DELETE_SCORECARD_TABLE = "DROP TABLE IF EXISTS "
			+ ScorecardEntry.TABLE_NAME;

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_CREATE_PLAYER_TABLE);
		db.execSQL(SQL_CREATE_COURSE_TABLE);
		db.execSQL(SQL_CREATE_PERFORMANCE_TABLE);
		db.execSQL(SQL_CREATE_SCORECARD_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO - handle updates / downgrades without losing all the data
		db.execSQL(SQL_DELETE_PLAYER_TABLE);
		db.execSQL(SQL_DELETE_COURSE_TABLE);
		db.execSQL(SQL_DELETE_PERFORMANCE_TABLE);
		db.execSQL(SQL_DELETE_SCORECARD_TABLE);

		onCreate(db);
	}

	@Override
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		onUpgrade(db, oldVersion, newVersion);
	}

}