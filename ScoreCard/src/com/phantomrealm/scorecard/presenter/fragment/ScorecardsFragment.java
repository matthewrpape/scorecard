package com.phantomrealm.scorecard.presenter.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

import com.phantomrealm.scorecard.R;
import com.phantomrealm.scorecard.model.Course;
import com.phantomrealm.scorecard.model.Player;
import com.phantomrealm.scorecard.model.Scorecard;
import com.phantomrealm.scorecard.model.db.ScorecardEntryUtil;
import com.phantomrealm.scorecard.presenter.activity.EditScorecardActivity;
import com.phantomrealm.scorecard.view.listadapters.ScorecardAdapter;

public class ScorecardsFragment extends Fragment {

	private static final String TAG = ScorecardsFragment.class.getSimpleName();

	private ListView mListView;
	private ScorecardAdapter mAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate");
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Log.d(TAG, "onCreateView");
		View view = inflater.inflate(R.layout.fragment_scorecards, container, false);

		mListView = (ListView) view.findViewById(R.id.scorecard_menu_scorecard_list);
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				launchEditScorecardActivity(mAdapter.getScorecard(position));
			}
		});
		mListView.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				promptToDelete(mAdapter.getScorecard(position));
				return true;
			}
		});

		return view;
	}

	@Override
	public void onResume() {
		Log.d(TAG, "onResume");
		super.onResume();

		populateScorecardList();
	}

	/**
	 * Launch the activity for editing a scorecard
	 * @param scorecard to edit
	 */
	private void launchEditScorecardActivity(Scorecard scorecard) {
		Course course = scorecard.getCourse();
		List<Integer> playerIds = new ArrayList<Integer>();
		List<String> playerNames = new ArrayList<String>();
		for (Player player : scorecard.getPlayers()) {
			playerIds.add((int) (long) player.getId());
			playerNames.add(player.getName());
		}

		Intent toLaunch = EditScorecardActivity.makeIntent(getActivity(), scorecard.getId(), scorecard.getDate(),
				course.getId(), course.getName(), course.getParList(), playerIds, playerNames);

		startActivity(toLaunch);
	}

	/**
	 * Populate the list of players displayed to the user
	 */
	private void populateScorecardList() {
		List<Scorecard> scorecards = ScorecardEntryUtil.getUtil().getScorecardsFromDatabase();
		
		mAdapter = new ScorecardAdapter(getActivity(), R.layout.list_item_scorecard, R.id.list_course_name, scorecards);
		mListView.setAdapter(mAdapter);
	}

	/**
	 * Display a dialogue asking the user if they wish to delete a given {@link Scorecard}
	 * @param scorecard
	 */
	private void promptToDelete(final Scorecard scorecard) {
		new AlertDialog.Builder(getActivity())
        .setIcon(android.R.drawable.ic_dialog_alert)
        .setTitle(R.string.delete_title)
        .setMessage(getString(R.string.delete_message, (scorecard.getCourse().getName() + " scorecard")))
        .setPositiveButton(R.string.confirm_delete, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            	// delete scorecard
            	ScorecardEntryUtil.getUtil().deleteScorecard(scorecard.getId());

            	// update the list displayed to the user
        		populateScorecardList();
            }
        })
        .setNegativeButton(R.string.cancel_delete, null)
        .show();
	}

}