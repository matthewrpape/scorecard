package com.phantomrealm.scorecard.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.phantomrealm.scorecard.model.db.DatabaseContract.CourseEntry;
import com.phantomrealm.scorecard.model.db.DatabaseContract.PlayerEntry;

public class DatabaseHelper extends SQLiteOpenHelper {

	// If you change the database schema, you must increment the database version.
	public static final int DATABASE_VERSION = 2;
	public static final String DATABASE_NAME = "scorecard.db";
	private static final String TEXT_TYPE = " TEXT";
	private static final String COMMA_SEP = ",";
	private static final String SQL_CREATE_PLAYER_TABLE = "CREATE TABLE "
			+ PlayerEntry.TABLE_NAME + " (" + PlayerEntry._ID
			+ " INTEGER PRIMARY KEY," + PlayerEntry.COLUMN_NAME + TEXT_TYPE + " )";
	private static final String SQL_DELETE_PLAYER_TABLE = "DROP TABLE IF EXISTS "
			+ PlayerEntry.TABLE_NAME;
	private static final String SQL_CREATE_COURSE_TABLE = "CREATE TABLE "
			+ CourseEntry.TABLE_NAME + " (" + CourseEntry._ID
			+ " INTEGER PRIMARY KEY," + CourseEntry.COLUMN_NAME + TEXT_TYPE
			+ COMMA_SEP + CourseEntry.COLUMN_PARS + TEXT_TYPE + " )";
	private static final String SQL_DELETE_COURSE_TABLE = "DROP TABLE IF EXISTS "
			+ CourseEntry.TABLE_NAME;

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_CREATE_PLAYER_TABLE);
		db.execSQL(SQL_CREATE_COURSE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO - handle updates / downgrades without losing all the data
		db.execSQL(SQL_DELETE_PLAYER_TABLE);
		db.execSQL(SQL_DELETE_COURSE_TABLE);

		onCreate(db);
	}

	@Override
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		onUpgrade(db, oldVersion, newVersion);
	}

}