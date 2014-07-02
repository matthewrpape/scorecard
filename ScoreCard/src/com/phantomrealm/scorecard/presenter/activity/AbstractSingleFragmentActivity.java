package com.phantomrealm.scorecard.presenter.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;

import com.phantomrealm.scorecard.R;

public abstract class AbstractSingleFragmentActivity extends Activity {
	protected void onCreateContentView() {
        setContentView(getLayout());
    }

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        onCreateContentView();
        FragmentManager manager = getFragmentManager();
        Fragment fragment = manager.findFragmentById(getFragmentContainerId());
        if (fragment == null) {
            fragment = createFragment();
            manager.beginTransaction()
                   .add(getFragmentContainerId(), fragment)
                   .commit();
        }
		
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_single);
	}

	/**
     * Return the single fragment to be displayed in this activity.
     * @return
     */
    protected abstract Fragment createFragment();
	
	/**
     * Returns the layout to use for this activity. Assumes a Fragment container
     * of R.id.fragmentContainer.
     * @return
     */
	protected abstract int getLayout();
	
	protected abstract int getFragmentContainerId();

}
