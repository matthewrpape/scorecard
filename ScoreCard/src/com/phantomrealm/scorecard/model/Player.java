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
		return new StringBuilder().append("Player ")
								  .append(mId)
		                          .append(": ")
		                          .append(mName)
		                          .toString();
	}
}