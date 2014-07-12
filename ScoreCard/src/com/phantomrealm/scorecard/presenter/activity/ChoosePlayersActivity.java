package com.phantomrealm.scorecard.presenter.activity;

import android.app.Fragment;

import com.phantomrealm.scorecard.R;
import com.phantomrealm.scorecard.presenter.fragment.ChoosePlayersFragment;

/**
 * Activity used to select one or more players
 * 
 * @author mpape
 */
public class ChoosePlayersActivity extends AbstractSingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		return new ChoosePlayersFragment();
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
