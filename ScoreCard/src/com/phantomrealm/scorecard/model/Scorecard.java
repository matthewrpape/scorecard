package com.phantomrealm.scorecard.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scorecard {

	private final long mId;
	private final Course mCourse;
	private final List<Player> mPlayers;
	private final Map<Player, List<Integer>> mScores;

	public Scorecard(Course course, List<Player> players) {
		this(-1, course, players);
	}

	public Scorecard(long id, Course course, List<Player> players) {
		mId = id;
		mCourse = course;
		mPlayers = players;
		mScores = createInitialScores();
	}

	public long getId() {
		return mId;
	}

	public Course getCourse() {
		return mCourse;
	}

	public List<Player> getPlayers() {
		return mPlayers;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Scorecard ")
		       .append(mId)
		       .append("\n ")
		       .append(mCourse);
		for (Player player : mPlayers) {
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
	 */
	private Map<Player, List<Integer>> createInitialScores() {
		HashMap<Player, List<Integer>> scores = new HashMap<Player, List<Integer>>(mPlayers.size());
		for (Player player : mPlayers) {
			scores.put(player, createInitialPlayerScores());
		}

		return scores;
	}

	/**
	 * Creates a list of scores, which are all initialized to par
	 */
	private List<Integer> createInitialPlayerScores() {
		ArrayList<Integer> scores = new ArrayList<Integer>(mCourse.getHoleCount());
		for (int i = 0; i < mCourse.getHoleCount(); ++i) {
			scores.add(mCourse.getParList().get(i));
		}

		return scores;
	}
}