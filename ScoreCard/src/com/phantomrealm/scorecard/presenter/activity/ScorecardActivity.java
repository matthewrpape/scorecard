package com.phantomrealm.scorecard.presenter.activity;

import android.app.Fragment;

import com.phantomrealm.scorecard.R;
import com.phantomrealm.scorecard.presenter.fragment.ScorecardFragment;

/**
 * Activity used to view an active Scorecard/game
 * 
 * @author mpape
 */
public class ScorecardActivity extends AbstractSingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		return new ScorecardFragment();
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
