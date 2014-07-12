package com.phantomrealm.scorecard.model;

public class Player {

	private long mId;
	private String mName;
	
	public Player(long id, String name) {
		mId = id;
		mName = name;
	}

	public long getId() {
		return mId;
	}

	public String getName() {
		return mName;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Player " + mId + ": " + mName);
		
		return builder.toString();
	}
}