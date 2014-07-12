package com.phantomrealm.scorecard.presenter.activity;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.phantomrealm.scorecard.R;
import com.phantomrealm.scorecard.presenter.fragment.ChoosePlayersFragment;
import com.phantomrealm.scorecard.presenter.fragment.CoursesFragment;

/**
 * Activity used as a base to launch activities for setting up and starting
 *  a new game
 * 
 * @author mpape
 */
public class NewGameActivity extends Activity {
	private static final int CHOOSE_COURSE_REQUEST_CODE = 101;
	private static final int CHOOSE_PLAYERS_REQUEST_CODE = 102;

	// course information
	private long mCourseId;
	private String mCourseName;
	private List<Integer> mPars;

	// player information
	private List<Integer> mPlayerIds;
	private List<String> mPlayerNames;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.view_loading);
		launchChooseCourseActivity();
	}

	@Override
	protected void onActivityResult (int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case CHOOSE_COURSE_REQUEST_CODE:
			handleChooseCourseResult(resultCode, data);
			break;
		case CHOOSE_PLAYERS_REQUEST_CODE:
			handleChoosePlayersResult(resultCode, data);
			break;
		default:
			break;
		}
	}

	private void launchChooseCourseActivity() {
		Intent toLaunch = new Intent(this, ChooseCourseActivity.class);
		startActivityForResult(toLaunch, CHOOSE_COURSE_REQUEST_CODE);
	}

	private void launchChoosePlayersActivity() {
		Intent toLaunch = new Intent(this, ChoosePlayersActivity.class);
		startActivityForResult(toLaunch, CHOOSE_PLAYERS_REQUEST_CODE);
	}

	private void launchScorecardActivity() {
		System.out.println("course: " + mCourseId + ": " + mCourseName);
		for (int i = 0; i < mPars.size(); ++i) {
			System.out.println(" hole " + (i + 1) + " par: " + mPars.get(i));
		}
		System.out.println("Players...");
		for (int i = 0; i < mPlayerIds.size(); ++i) {
			System.out.println(" " + mPlayerIds.get(i) + ": " + mPlayerNames.get(i));
		}
	}

	private void handleChooseCourseResult(int resultCode, Intent data) {
		switch (resultCode) {
		case Activity.RESULT_OK:
			mCourseId = data.getLongExtra(CoursesFragment.INTENT_EXTRA_COURSE_ID_TAG, 0);
			mCourseName = data.getStringExtra(CoursesFragment.INTENT_EXTRA_COURSE_NAME_TAG);
			mPars = data.getIntegerArrayListExtra(CoursesFragment.INTENT_EXTRA_COURSE_PAR_TAG);
			launchChoosePlayersActivity();
			break;
		case Activity.RESULT_CANCELED:
		default:
			finish();
		}
	}

	private void handleChoosePlayersResult(int resultCode, Intent data) {
		switch (resultCode) {
		case Activity.RESULT_OK:
			mPlayerIds = data.getIntegerArrayListExtra(ChoosePlayersFragment.INTENT_EXTRA_PLAYER_ID_LIST);
			mPlayerNames = data.getStringArrayListExtra(ChoosePlayersFragment.INTENT_EXTRA_PLAYER_NAME_LIST);
			launchScorecardActivity();
			break;
		case Activity.RESULT_CANCELED:
		default:
			launchChooseCourseActivity();
		}
	}

}