package com.phantomrealm.scorecard.presenter.fragment;

import com.phantomrealm.scorecard.R;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MainFragment extends Fragment {

	private static final String TAG = MainFragment.class.getSimpleName();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (savedInstanceState == null) {
			Log.d(TAG, "onCreate from scratch");
		} else {
			Log.d(TAG, "onCreate from saved instance");
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Log.d(TAG, "onCreateView");
		View view = inflater.inflate(R.layout.fragment_main, container, false);
		
		return view;
	}

}
