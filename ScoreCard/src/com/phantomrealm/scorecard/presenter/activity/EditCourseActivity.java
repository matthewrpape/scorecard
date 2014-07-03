package com.phantomrealm.scorecard.presenter.activity;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

import com.phantomrealm.scorecard.R;
import com.phantomrealm.scorecard.presenter.fragment.CoursesFragment;
import com.phantomrealm.scorecard.presenter.fragment.EditCourseFragment;

/**
 * Activity used to add a new course or edit an existing course
 * 
 * @author mpape
 */
public class EditCourseActivity extends AbstractSingleFragmentActivity {

	private long mId;
	private String mName;
	private int[] mPars;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Intent intent = getIntent();
		mId = intent.getLongExtra(CoursesFragment.INTENT_EXTRA_COURSE_ID_TAG, 0);
		mName = intent.getStringExtra(CoursesFragment.INTENT_EXTRA_COURSE_NAME_TAG);
		mPars = intent.getIntArrayExtra(CoursesFragment.INTENT_EXTRA_COURSE_PAR_TAG);

		super.onCreate(savedInstanceState);
	}
	
	@Override
	protected Fragment createFragment() {
		return new EditCourseFragment(mId, mName, mPars);
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
