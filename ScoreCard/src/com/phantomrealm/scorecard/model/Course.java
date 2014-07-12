package com.phantomrealm.scorecard.model;

import java.util.ArrayList;
import java.util.List;

public class Course {

	public static final int DEFAULT_HOLES = 18;
	public static final int DEFAULT_PAR = 3;

	private long mId;
	private String mName;
	private List<Integer> mPars;

	public static List<Integer> createDefaultPars(int holes) {
		ArrayList<Integer> pars = new ArrayList<Integer>(holes);
		for (int i = 0; i < holes; ++i) {
			pars.add(i, DEFAULT_PAR);
		}

		return pars;
	}
	
	public Course(String name) {
		this(name, DEFAULT_HOLES);
	}

	public Course(String name, int holes) {
		this(name, createDefaultPars(holes));
	}

	public Course(String name, List<Integer> pars) {
		this(0, name, pars);
	}
	
	public Course(long id, String name, List<Integer> pars) {
		mId = id;
		mName = name;
		mPars = pars;
	}
	
	public long getId() {
		return mId;
	}

	public String getName() {
		return mName;
	}

	public int getHoleCount() {
		return mPars.size();
	}

	public ArrayList<Integer> getParList() {
		return (ArrayList<Integer>) mPars;
	}

	public int getTotalPar() {
		int totalPar = 0;
		for (int i : mPars) {
			totalPar += i;
		}
		
		return totalPar;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Course ")
		       .append(mId)
		       .append(": ")
		       .append(mName);
		for (int i = 0; i < mPars.size(); ++i) {
			builder.append(" hole " + (i + 1) + " par: " + mPars.get(i));
		}

		return builder.toString();
	}
}