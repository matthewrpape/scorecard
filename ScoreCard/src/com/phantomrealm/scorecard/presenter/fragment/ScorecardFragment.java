package com.phantomrealm.scorecard.presenter.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.phantomrealm.scorecard.R;
import com.phantomrealm.scorecard.model.Course;
import com.phantomrealm.scorecard.model.Player;
import com.phantomrealm.scorecard.model.Scorecard;
import com.phantomrealm.scorecard.view.pageradapters.ScorecardPlayerPagerAdapter;

public class ScorecardFragment extends Fragment {

	private static final String TAG = ScorecardFragment.class.getSimpleName();

	private Scorecard mScorecard;
	private ViewPager mViewPager;
	private ScorecardPlayerPagerAdapter mAdapter;
	private TextView mHoleLabel;
	private TextView mParLabel;

	public ScorecardFragment(Scorecard scorecard) {
		mScorecard = scorecard;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate");
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Log.d(TAG, "onCreateView");
		View view = inflater.inflate(R.layout.fragment_scorecard, container, false);
		mViewPager = (ViewPager) view.findViewById(R.id.scorecard_player_pager);
		setupScorecardPager();
		setPagerButtons(view);

		((TextView) view.findViewById(R.id.scorecard_course_label)).setText(mScorecard.getCourse().getName());
		mHoleLabel = (TextView) view.findViewById(R.id.scorecard_hole);
		mParLabel = (TextView) view.findViewById(R.id.scorecard_par);
		setLabelsForHole(0); // start off on the first hole by default

		return view;
	}

	private void setupScorecardPager() {
		Map<Player, List<Integer>> playerScores = getPlayerScores(mScorecard.getPlayers(), mScorecard.getCourse());
		Map<Player, List<Integer>> playerAverages = getPlayerAverages(mScorecard.getPlayers(), mScorecard.getCourse());
		mAdapter = new ScorecardPlayerPagerAdapter(mScorecard.getPlayers(), mScorecard.getCourse().getParList(), playerScores, playerAverages);
		mViewPager.setAdapter(mAdapter);

		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
		    @Override
		    public void onPageSelected(int position) {
		    	setLabelsForHole(position);
		    }

			@Override
			public void onPageScrollStateChanged(int arg0) { }

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) { }
		});
	}

	/**
	 * Sets the text for the labels which display hole number and par.
	 * @param holeIndex zero based index into the list of pars (0 = hole 1, 1 = hole 2, etc)
	 */
	private void setLabelsForHole(int holeIndex) {
		mHoleLabel.setText(String.valueOf(holeIndex + 1));
		mParLabel.setText(String.valueOf(mScorecard.getCourse().getParList().get(holeIndex)));
	}

	private void setPagerButtons(View parentView) {
		parentView.findViewById(R.id.scorecard_next_hole_button).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1, true);
			}
		});

		parentView.findViewById(R.id.scorecard_previous_hole_button).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1, true);
			}
		});
	}

	// TODO - load from db
	private Map<Player, List<Integer>> getPlayerScores(List<Player> players, Course course) {
		Map<Player, List<Integer>> playerTotals = new HashMap<Player, List<Integer>>();
		for (Player player: players) {
			List<Integer> scores = getPlayerScoresForCourse(player, course);
			playerTotals.put(player, scores);
		}

		return playerTotals;
	}

	// TODO - load from db
	private List<Integer> getPlayerScoresForCourse(Player player, Course course) {
		List<Integer> scores = new ArrayList<Integer>();
		for (Integer par : course.getParList()) {
			scores.add(par);
		}

		return scores;
	}

	// TODO - load from db
	private Map<Player, List<Integer>> getPlayerAverages(List<Player> players, Course course) {
		Map<Player, List<Integer>> playerAverages = new HashMap<Player, List<Integer>>();
		for (Player player : players) {
			List<Integer> averages = getPlayerAveragesForCourse(player, course);
			playerAverages.put(player, averages);
		}

		return playerAverages;
	}

	// TODO - load from db
	private List<Integer> getPlayerAveragesForCourse(Player player, Course course) {
		List<Integer> averages = new ArrayList<Integer>();
		for (int i = 0; i < course.getHoleCount(); ++i) {
			averages.add(null);
		}

		return averages;
	}

}