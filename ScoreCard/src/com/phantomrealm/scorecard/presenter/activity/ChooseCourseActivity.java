package com.phantomrealm.scorecard.presenter.activity;

import android.app.Fragment;

import com.phantomrealm.scorecard.R;
import com.phantomrealm.scorecard.presenter.fragment.ChooseCourseFragment;

/**
 * Activity used to select a course for use
 * 
 * @author mpape
 */
public class ChooseCourseActivity extends AbstractSingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		return new ChooseCourseFragment();
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