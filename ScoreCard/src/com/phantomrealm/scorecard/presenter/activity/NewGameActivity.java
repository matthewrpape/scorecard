package com.phantomrealm.scorecard.presenter.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.phantomrealm.scorecard.R;
import com.phantomrealm.scorecard.model.Course;
import com.phantomrealm.scorecard.model.Player;
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

	private Course mCourse;
	private List<Player> mPlayers;

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
		System.out.println(mCourse);
		for (Player player : mPlayers) {
			System.out.println(player);
		}
	}

	private void handleChooseCourseResult(int resultCode, Intent data) {
		switch (resultCode) {
		case Activity.RESULT_OK:
			long courseId = data.getLongExtra(CoursesFragment.INTENT_EXTRA_COURSE_ID_TAG, 0);
			String courseName = data.getStringExtra(CoursesFragment.INTENT_EXTRA_COURSE_NAME_TAG);
			List<Integer> coursePars = data.getIntegerArrayListExtra(CoursesFragment.INTENT_EXTRA_COURSE_PAR_TAG);
			mCourse = new Course(courseId, courseName, coursePars);

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
			mPlayers = new ArrayList<Player>();
			List<Integer> playerIds = data.getIntegerArrayListExtra(ChoosePlayersFragment.INTENT_EXTRA_PLAYER_ID_LIST);
			List<String> playerNames = data.getStringArrayListExtra(ChoosePlayersFragment.INTENT_EXTRA_PLAYER_NAME_LIST);
			for (int i = 0; i < playerIds.size(); ++i) {
				mPlayers.add(new Player(playerIds.get(i), playerNames.get(i)));
			}

			launchScorecardActivity();
			break;
		case Activity.RESULT_CANCELED:
		default:
			launchChooseCourseActivity();
		}
	}

}