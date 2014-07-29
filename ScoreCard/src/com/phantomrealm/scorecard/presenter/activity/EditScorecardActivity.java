package com.phantomrealm.scorecard.presenter.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;

import com.phantomrealm.scorecard.R;
import com.phantomrealm.scorecard.model.Course;
import com.phantomrealm.scorecard.model.Player;
import com.phantomrealm.scorecard.model.Scorecard;
import com.phantomrealm.scorecard.presenter.fragment.EditScorecardFragment;

/**
 * Activity used to view an active Scorecard/game
 * 
 * @author mpape
 */
public class EditScorecardActivity extends AbstractSingleFragmentActivity {

	private static final String INTENT_EXTRA_SCORECARD_ID_TAG = "intent_extra_scorecard_id_tag";
	private static final String INTENT_EXTRA_SCORECARD_DATE_TAG = "intent_extra_scorecard_date_tag";
	private static final String INTENT_EXTRA_COURSE_ID_TAG = "intent_extra_course_id_tag";
	private static final String INTENT_EXTRA_COURSE_NAME_TAG = "intent_extra_course_name_tag";
	private static final String INTENT_EXTRA_COURSE_PARS_TAG = "intent_extra_course_pars_tag";
	private static final String INTENT_EXTRA_PLAYER_IDS_TAG = "intent_extra_player_ids_tag";
	private static final String INTENT_EXTRA_PLAYER_NAMES_TAG = "intent_extra_player_names_tag";

	private EditScorecardFragment mFragment;

	@Override
	protected Fragment createFragment() {
		Intent intent = getIntent();
		long scorecardId = intent.getLongExtra(INTENT_EXTRA_SCORECARD_ID_TAG, 0);
		long scorecardDate = intent.getLongExtra(INTENT_EXTRA_SCORECARD_DATE_TAG, 0);
		long courseId = intent.getLongExtra(INTENT_EXTRA_COURSE_ID_TAG, 0);
		String courseName = intent.getStringExtra(INTENT_EXTRA_COURSE_NAME_TAG);
		List<Integer> coursePars = intent.getIntegerArrayListExtra(INTENT_EXTRA_COURSE_PARS_TAG);
		Course course = new Course(courseId, courseName, coursePars);

		List<Integer> playerIds = intent.getIntegerArrayListExtra(INTENT_EXTRA_PLAYER_IDS_TAG);
		List<String> playerNames = intent.getStringArrayListExtra(INTENT_EXTRA_PLAYER_NAMES_TAG);
		List<Player> players = createPlayers(playerIds, playerNames);

		Scorecard scorecard = new Scorecard(scorecardId, scorecardDate, course, players);
		mFragment = new EditScorecardFragment(scorecard);
		return mFragment;
	}

	@Override
	protected int getLayout() {
		return R.layout.activity_single;
	}

	@Override
	protected int getFragmentContainerId() {
		return R.id.fragmentContainer;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			mFragment.saveScorecard();
		}

		return super.onKeyDown(keyCode, event);
	}

	public static Intent makeIntent(Context context, long courseId, String courseName, List<Integer> coursePars, List<Integer> playerIds, List<String> playerNames) {
		Intent intent = new Intent(context,EditScorecardActivity.class);
		intent.putExtra(INTENT_EXTRA_COURSE_ID_TAG, courseId);
		intent.putExtra(INTENT_EXTRA_COURSE_NAME_TAG, courseName);
		intent.putIntegerArrayListExtra(INTENT_EXTRA_COURSE_PARS_TAG, (ArrayList<Integer>)coursePars);
		intent.putIntegerArrayListExtra(INTENT_EXTRA_PLAYER_IDS_TAG, (ArrayList<Integer>)playerIds);
		intent.putStringArrayListExtra(INTENT_EXTRA_PLAYER_NAMES_TAG, (ArrayList<String>)playerNames);

		return intent;
	}

	public static Intent makeIntent(Context context, long scorecardId, long scorecardDate, long courseId, String courseName,
			List<Integer> coursePars, List<Integer> playerIds, List<String> playerNames) {
		Intent intent = new Intent(context,EditScorecardActivity.class);
		intent.putExtra(INTENT_EXTRA_SCORECARD_ID_TAG, scorecardId);
		intent.putExtra(INTENT_EXTRA_SCORECARD_DATE_TAG, scorecardDate);
		intent.putExtra(INTENT_EXTRA_COURSE_ID_TAG, courseId);
		intent.putExtra(INTENT_EXTRA_COURSE_NAME_TAG, courseName);
		intent.putIntegerArrayListExtra(INTENT_EXTRA_COURSE_PARS_TAG, (ArrayList<Integer>)coursePars);
		intent.putIntegerArrayListExtra(INTENT_EXTRA_PLAYER_IDS_TAG, (ArrayList<Integer>)playerIds);
		intent.putStringArrayListExtra(INTENT_EXTRA_PLAYER_NAMES_TAG, (ArrayList<String>)playerNames);

		return intent;
	}

	public List<Player> createPlayers(List<Integer> playerIds, List<String> playerNames) {
		List<Player> players = new ArrayList<Player>();
		for (int i = 0; i < playerIds.size(); ++i) {
			Player player = new Player(playerIds.get(i), playerNames.get(i));
			players.add(player);
		}

		return players;
	}
}