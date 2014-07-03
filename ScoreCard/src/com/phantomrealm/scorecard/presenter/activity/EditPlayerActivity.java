package com.phantomrealm.scorecard.presenter.activity;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

import com.phantomrealm.scorecard.R;
import com.phantomrealm.scorecard.presenter.fragment.EditPlayerFragment;
import com.phantomrealm.scorecard.presenter.fragment.PlayersFragment;

/**
 * Activity used to add a new player or edit an existing player
 * 
 * @author mpape
 */
public class EditPlayerActivity extends AbstractSingleFragmentActivity {

	private long mId;
	private String mName;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Intent intent = getIntent();
		if (intent.getExtras().containsKey(PlayersFragment.INTENT_EXTRA_PLAYER_ID_TAG)) {
			mId = intent.getExtras().getLong(PlayersFragment.INTENT_EXTRA_PLAYER_ID_TAG);
		}
		if (intent.getExtras().containsKey(PlayersFragment.INTENT_EXTRA_PLAYER_NAME_TAG)) {
			mName = intent.getExtras().getString(PlayersFragment.INTENT_EXTRA_PLAYER_NAME_TAG);
		}

		super.onCreate(savedInstanceState);
	}
	
	@Override
	protected Fragment createFragment() {
		return new EditPlayerFragment(mId, mName);
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
