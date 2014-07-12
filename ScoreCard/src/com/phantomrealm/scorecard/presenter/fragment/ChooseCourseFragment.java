package com.phantomrealm.scorecard.presenter.fragment;

import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.phantomrealm.scorecard.R;
import com.phantomrealm.scorecard.model.Course;
import com.phantomrealm.scorecard.model.db.CourseEntryUtil;
import com.phantomrealm.scorecard.presenter.activity.EditCourseActivity;
import com.phantomrealm.scorecard.view.CourseAdapter;

public class ChooseCourseFragment extends Fragment {

	private static final String TAG = ChooseCourseFragment.class.getSimpleName();
	
	private ListView mListView;
	private CourseAdapter mAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate");
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Log.d(TAG, "onCreateView");
		View view = inflater.inflate(R.layout.fragment_courses, container, false);

		mListView = (ListView) view.findViewById(R.id.courses_menu_course_list);
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				selectCourse(mAdapter.getCourse(position));
			}
		});

		view.findViewById(R.id.courses_menu_button_new_course).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				launchEditCourseActivity();
			}
		});

		return view;
	}

	@Override
	public void onResume() {
		Log.d(TAG, "onResume");
		super.onResume();

		populateCourseList();
	}

	/**
	 * Launch the activity for adding a new course
	 */
	private void launchEditCourseActivity() {
		Intent toLaunch = new Intent(getActivity(), EditCourseActivity.class);
		
		startActivity(toLaunch);
	}

	private void selectCourse(Course selectedCourse) {
		Intent result = new Intent();
		result.putExtra(CoursesFragment.INTENT_EXTRA_COURSE_ID_TAG, selectedCourse.getId());
		result.putExtra(CoursesFragment.INTENT_EXTRA_COURSE_NAME_TAG, selectedCourse.getName());
		result.putIntegerArrayListExtra(CoursesFragment.INTENT_EXTRA_COURSE_PAR_TAG, selectedCourse.getParList());
		getActivity().setResult(Activity.RESULT_OK, result);

		getActivity().finish();
	}

	/**
	 * Populate the list of courses displayed to the user
	 */
	private void populateCourseList() {
		List<Course> courses = CourseEntryUtil.getUtil().getCoursesFromDatabase();
		
		mAdapter = new CourseAdapter(getActivity(), R.layout.list_item_course, R.id.list_course_name, courses);
		mListView.setAdapter(mAdapter);
	}

}