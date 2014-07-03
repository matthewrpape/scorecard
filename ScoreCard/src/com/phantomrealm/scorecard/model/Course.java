package com.phantomrealm.scorecard.model;

public class Course {

	private static final int DEFAULT_HOLES = 18;
	private static final int DEFAULT_PAR = 3;

	private long mId;
	private String mName;
	private int[] mPars;

	private static int[] createDefaultPars(int holes) {
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
		this(-1, name, pars);
	}
	
	public Course(int id, String name, int[] pars) {
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

	public int getHoles() {
		return mPars.length;
	}

	public int getTotalPar() {
		int totalPar = 0;
		for (int i : mPars) {
			totalPar += i;
		}
		
		return totalPar;
	}

	public int getPar(int index) {
		return mPars[index];
	}

}