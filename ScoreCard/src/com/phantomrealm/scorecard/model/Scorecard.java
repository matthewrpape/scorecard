package com.phantomrealm.scorecard.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scorecard {

	private final long mId;
	private final long mDate;
	private final Course mCourse;
	private final List<Player> mPlayers;
	private final Map<Player, List<Integer>> mScores;

	public Scorecard(Course course, List<Player> players) {
		this(0, 0, course, players);
	}

	public Scorecard(long id, long date, Course course, List<Player> players) {
		mId = id;
		mDate = date;
		mCourse = course;
		mPlayers = players;
		mScores = createDefaultScores(players, mCourse);
	}

	public long getId() {
		return mId;
	}

	public long getDate() {
		return mDate;
	}

	public Course getCourse() {
		return mCourse;
	}

	public List<Player> getPlayers() {
		return mPlayers;
	}

	public Map<Player, List<Integer>> getPlayerScores() {
		return mScores;
	}

	public void setScoresForPlayer(Player player, List<Integer> scores) {
		if (mPlayers.contains(player)) {
			mScores.put(player, scores);
		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Scorecard ")
		       .append(mId)
		       .append("\n ")
		       .append(mCourse);
		for (Player player : mScores.keySet()) {
			builder.append("\n ")
				   .append(player)
				   .append("\n ")
				   .append("Scores ");
			for (int i = 0; i < mScores.get(player).size(); ++i) {
				builder.append(i + 1)
					   .append(": ")
					   .append(mScores.get(player).get(i))
					   .append(" ");
			}
		}

		return builder.toString();
	}

	/**
	 * Creates a map that links each player to a list of scores, which are all initialized to par
	 * @param players
	 * @param course
	 * @return
	 */
	private Map<Player, List<Integer>> createDefaultScores(List<Player> players, Course course) {
		HashMap<Player, List<Integer>> scores = new HashMap<Player, List<Integer>>(players.size());
		for (Player player : players) {
			scores.put(player, createDefaultPlayerScores(course));
		}

		return scores;
	}

	/**
	 * Creates a list of scores, which are all initialized to par
	 * @param course
	 * @return
	 */
	private List<Integer> createDefaultPlayerScores(Course course) {
		ArrayList<Integer> scores = new ArrayList<Integer>(course.getHoleCount());
		for (int i = 0; i < course.getHoleCount(); ++i) {
			scores.add(course.getParList().get(i));
		}

		return scores;
	}
}