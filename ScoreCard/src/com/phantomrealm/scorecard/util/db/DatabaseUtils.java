package com.phantomrealm.scorecard.util.db;

import java.util.ArrayList;
import java.util.List;

public class DatabaseUtils {

	private static final String STRING_SEPARATOR = ",";

	public static String buildStringFromList(List<Integer> list) {
		StringBuilder builder = new StringBuilder();
		for (Integer value : list) {
			builder.append(value.toString());
			builder.append(STRING_SEPARATOR);
		}
		String listString = builder.toString();

		return listString.substring(0, listString.length() - 1);
	}

	public static List<Integer> buildListFromString(String listString) {
		String[] strings = listString.split(STRING_SEPARATOR);
		ArrayList<Integer> stringList = new ArrayList<Integer>();
		for (String string : strings) {
			stringList.add(Integer.parseInt(string));
		}

		return stringList;
	}
}