package com.phantomrealm.scorecard.model;

public class Course {

	public static final int DEFAULT_HOLES = 18;
	public static final int DEFAULT_PAR = 3;

	private long mId;
	private String mName;
	private int[] mPars;

	public static int[] createDefaultPars(int holes) {
		int[] pars = new int[holes];
		for (int i = 0; i < pars.length; ++i) {
			pars[i] = DEFAULT_PAR;
		}
		
		return pars;
	}
	
	public Course(String name) {
		this(name, DEFAULT_HOLES);
	}

	public Course(String name, int holes) {
		this(name, createDefaultPars(holes));
	}

	public Course(String name, int[] pars) {
		this(0, name, pars);
	}
	
	public Course(long id, String name, int[] pars) {
		mId = id;
		mName = name;
		mPars = pars;
	}
	
	// TODO - remove this function
	public static Course createDebugCourse(long id, String name) {
		return new Course(id, name, createDefaultPars(DEFAULT_HOLES));
	}

	public long getId() {
		return mId;
	}

	public String getName() {
		return mName;
	}

	public int getHoleCount() {
		return mPars.length;
	}

	public int[] getParList() {
		return mPars;
	}

	public int getTotalPar() {
		int totalPar = 0;
		for (int i : mPars) {
			totalPar += i;
		}
		
		return totalPar;
	}

}