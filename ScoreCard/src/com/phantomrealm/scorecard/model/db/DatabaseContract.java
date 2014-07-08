package com.phantomrealm.scorecard.model.db;

import android.provider.BaseColumns;

public class DatabaseContract {

	public DatabaseContract() {
	}

	public static abstract class PlayerEntry implements BaseColumns {
		public static final String TABLE_NAME = "players";
		public static final String COLUMN_NAME = "player_name";
	}

	public static abstract class CourseEntry implements BaseColumns {
		public static final String TABLE_NAME = "courses";
		public static final String COLUMN_NAME = "course_name";
	}

}
