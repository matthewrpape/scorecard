package com.phantomrealm.scorecard.presenter.activity;

import android.app.Fragment;

import com.phantomrealm.scorecard.R;
import com.phantomrealm.scorecard.presenter.fragment.MainFragment;

/**
 * The main menu activity displayed to the user on app launch
 * 
 * @author mpape
 */
public class MainActivity extends AbstractSingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		return new MainFragment();
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
