package com.phantomrealm.scorecard.util.db;

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
		public static final String COLUMN_PARS = "course_pars";
	}

	public static abstract class ScorecardEntry implements BaseColumns {
		public static final String TABLE_NAME = "scorecards";
		public static final String COLUMN_DATE = "scorecard_date";
		public static final String COLUMN_COURSE_ID = "scorecard_course_id";
	}

	public static abstract class PerformanceEntry implements BaseColumns {
		public static final String TABLE_NAME = "performances";
		public static final String COLUMN_SCORECARD_ID = "performance_scorecard_id";
		public static final String COLUMN_PLAYER_ID = "performance_player_id";
		public static final String COLUMN_SCORES = "performance_scores";
	}

}