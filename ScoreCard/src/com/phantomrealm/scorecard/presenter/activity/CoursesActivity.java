package com.phantomrealm.scorecard.presenter.activity;

import android.app.Fragment;

import com.phantomrealm.scorecard.R;
import com.phantomrealm.scorecard.presenter.fragment.CoursesFragment;

/**
 * Activity used to view existing courses
 * 
 * @author mpape
 */
public class CoursesActivity extends AbstractSingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		return new CoursesFragment();
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
