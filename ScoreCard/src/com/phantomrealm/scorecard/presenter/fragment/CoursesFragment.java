package com.phantomrealm.scorecard.presenter.fragment;

import java.util.List;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

import com.phantomrealm.scorecard.R;
import com.phantomrealm.scorecard.model.Course;
import com.phantomrealm.scorecard.model.db.CourseEntryUtil;
import com.phantomrealm.scorecard.presenter.activity.EditCourseActivity;
import com.phantomrealm.scorecard.view.CourseAdapter;

public class CoursesFragment extends Fragment {

	private static final String TAG = CoursesFragment.class.getSimpleName();
	
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
				launchEditCourseActivity(mAdapter.getCourse(position));
			}
		});
		mListView.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				promptToDelete(mAdapter.getCourse(position));
				return true;
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
		launchEditCourseActivity(null);
	}
	
	/**
	 * Launch the activity for editing a course
	 * @param course the existing course to edit or null to create a new player
	 */
	private void launchEditCourseActivity(Course course) {
		Intent toLaunch = new Intent(getActivity(), EditCourseActivity.class);
		if (course != null) {
			// TODO - populate
//			toLaunch.putExtra(INTENT_EXTRA_PLAYER_ID_TAG, player.getId());
//			toLaunch.putExtra(INTENT_EXTRA_PLAYER_NAME_TAG, player.getName());
		}
		
		startActivity(toLaunch);
	}

	/**
	 * Populate the list of courses displayed to the user
	 */
	private void populateCourseList() {
		List<Course> courses = CourseEntryUtil.getUtil().getCoursesFromDatabase();
		
		mAdapter = new CourseAdapter(getActivity(), R.layout.list_item_course, R.id.list_course_name, courses);
		mListView.setAdapter(mAdapter);
	}

	/**
	 * Display a dialogue asking the user if they wish to delete a given {@link Course}
	 * @param player
	 */
	private void promptToDelete(final Course course) {
		new AlertDialog.Builder(getActivity())
        .setIcon(android.R.drawable.ic_dialog_alert)
        .setTitle(R.string.delete_title)
        .setMessage(getString(R.string.delete_message, course.getName()))
        .setPositiveButton(R.string.confirm_delete, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            	// delete course
            	CourseEntryUtil.getUtil().deleteCourse(course.getId());

            	// update the list displayed to the user
        		populateCourseList();
            }
        })
        .setNegativeButton(R.string.cancel_delete, null)
        .show();
	}

}