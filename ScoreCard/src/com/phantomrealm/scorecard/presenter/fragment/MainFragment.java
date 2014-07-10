package com.phantomrealm.scorecard.presenter.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.phantomrealm.scorecard.R;
import com.phantomrealm.scorecard.presenter.activity.ChooseCourseActivity;
import com.phantomrealm.scorecard.presenter.activity.CoursesActivity;
import com.phantomrealm.scorecard.presenter.activity.PlayersActivity;

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

		view.findViewById(R.id.menu_button_new_game).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				launchChooseCourseActivity();
			}
		});

		view.findViewById(R.id.menu_button_players).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				launchPlayersActivity();
			}
		});

		view.findViewById(R.id.menu_button_courses).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				launchCoursesActivity();
			}
		});
		
		return view;
	}

	/**
	 * Launch the activity for viewing choosing a course to use in a new game
	 */
	private void launchChooseCourseActivity() {
		Intent toLaunch = new Intent(getActivity(), ChooseCourseActivity.class);
		startActivity(toLaunch);
	}

	/**
	 * Launch the activity for viewing existing players
	 */
	private void launchPlayersActivity() {
		Intent toLaunch = new Intent(getActivity(), PlayersActivity.class);
		startActivity(toLaunch);
	}

	/**
	 * Launch the activity for viewing existing courses
	 */
	private void launchCoursesActivity() {
		Intent toLaunch = new Intent(getActivity(), CoursesActivity.class);
		startActivity(toLaunch);
	}

}
