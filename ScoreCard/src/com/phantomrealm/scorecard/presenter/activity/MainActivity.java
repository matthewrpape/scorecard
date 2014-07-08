package com.phantomrealm.scorecard.presenter.activity;

import android.app.Fragment;
import android.os.Bundle;

import com.phantomrealm.scorecard.R;
import com.phantomrealm.scorecard.model.db.CourseEntryUtil;
import com.phantomrealm.scorecard.model.db.PlayerEntryUtil;
import com.phantomrealm.scorecard.presenter.fragment.MainFragment;

/**
 * The main menu activity displayed to the user on app launch
 * 
 * @author mpape
 */
public class MainActivity extends AbstractSingleFragmentActivity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		PlayerEntryUtil.initializeInstance(this);
		CourseEntryUtil.initializeInstance(this);
	}

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