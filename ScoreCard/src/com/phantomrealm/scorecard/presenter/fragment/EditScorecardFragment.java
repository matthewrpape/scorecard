package com.phantomrealm.scorecard.presenter.fragment;

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
import com.phantomrealm.scorecard.model.db.PerformanceEntryUtil;
import com.phantomrealm.scorecard.model.db.ScorecardEntryUtil;
import com.phantomrealm.scorecard.view.pageradapters.ScorecardPlayerPagerAdapter;

public class EditScorecardFragment extends Fragment {

	private static final String TAG = EditScorecardFragment.class.getSimpleName();

	private Scorecard mScorecard;
	private ViewPager mViewPager;
	private ScorecardPlayerPagerAdapter mAdapter;
	private TextView mHoleLabel;
	private TextView mParLabel;

	public EditScorecardFragment(Scorecard scorecard) {
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

	public void saveScorecard() {
		if (mScorecard.getId() > 0 && mScorecard.getDate() > 0) {
			ScorecardEntryUtil.getUtil().updateScorecard(mScorecard);
		} else {
			ScorecardEntryUtil.getUtil().insertScorecard(mScorecard);
		}
	}

	private void setupScorecardPager() {
		Map<Player, List<Integer>> playerScores = mScorecard.getPlayerScores();
		Map<Player, List<Integer>> playerAverages = getAveragesForPlayers(mScorecard.getPlayers(), mScorecard.getCourse());
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

	/**
	 * Sets the click listeners for the buttons which advance and regress the viewpager.
	 * @param parentView
	 */
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

	/**
	 * Generates a map between players and a list of their average scores for each hole on a
	 *  specified course.
	 * @param players
	 * @param course
	 * @return
	 */
	private Map<Player, List<Integer>> getAveragesForPlayers(List<Player> players, Course course) {
		Map<Player, List<Integer>> playerAverages = new HashMap<Player, List<Integer>>();
		for (Player player : players) {
			List<Integer> averages = getAveragesForPlayer(player.getId(), course);
			playerAverages.put(player, averages);
		}

		return playerAverages;
	}

	/**
	 * Generates a list containing the average score for a specified player of each hole on a
	 *  specified course.
	 * @param playerId
	 * @param course
	 * @return
	 */
	private List<Integer> getAveragesForPlayer(long playerId, Course course) {
		List<Integer> averages = PerformanceEntryUtil.getUtil().getPerformanceAveragesFromDatabase(playerId, course.getId());

		if (averages.size() == 0) {
			// create an appropriately sized list of null values
			for (int i = 0; i < course.getHoleCount(); ++i) {
				averages.add(null);
			}
		}

		return averages;
	}
}