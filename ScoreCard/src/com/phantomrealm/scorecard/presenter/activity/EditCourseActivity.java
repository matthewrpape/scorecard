package com.phantomrealm.scorecard.presenter.activity;

import android.app.Fragment;
import android.os.Bundle;

import com.phantomrealm.scorecard.R;
import com.phantomrealm.scorecard.presenter.fragment.EditCourseFragment;

/**
 * Activity used to add a new course or edit an existing course
 * 
 * @author mpape
 */
public class EditCourseActivity extends AbstractSingleFragmentActivity {

	private long mId;
	private String mName;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO - check intent for extras
//		Intent intent = getIntent();
//		if (intent.hasExtra(PlayersFragment.INTENT_EXTRA_PLAYER_ID_TAG) && intent.hasExtra(PlayersFragment.INTENT_EXTRA_PLAYER_NAME_TAG)) {
//			mId = intent.getExtras().getLong(PlayersFragment.INTENT_EXTRA_PLAYER_ID_TAG);
//			mName = intent.getExtras().getString(PlayersFragment.INTENT_EXTRA_PLAYER_NAME_TAG);
//		}

		super.onCreate(savedInstanceState);
	}
	
	@Override
	protected Fragment createFragment() {
		return new EditCourseFragment(mId, mName);
	}

	@Override
	protected int getLayout() {
		return R.layout.activity_single;
	}

	@Override
	protected int getFragmentContainerId() {
		return R.id.fragmentContainer;
	}

}
