package com.phantomrealm.scorecard.presenter.activity;

import android.app.Fragment;

import com.phantomrealm.scorecard.R;
import com.phantomrealm.scorecard.presenter.fragment.EditPlayerFragment;

/**
 * Activity used to add a new player or edit an existing player
 * 
 * @author mpape
 */
public class EditPlayerActivity extends AbstractSingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		return new EditPlayerFragment();
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
