package com.phantomrealm.scorecard.presenter.activity;

import android.app.Fragment;

import com.phantomrealm.scorecard.R;
import com.phantomrealm.scorecard.presenter.fragment.PlayersFragment;

/**
 * Activity used to view existing players
 * 
 * @author mpape
 */
public class PlayersActivity extends AbstractSingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		return new PlayersFragment();
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
