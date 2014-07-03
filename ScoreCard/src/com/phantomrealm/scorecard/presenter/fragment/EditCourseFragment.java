package com.phantomrealm.scorecard.presenter.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;

import com.phantomrealm.scorecard.R;
import com.phantomrealm.scorecard.model.db.CourseEntryUtil;

public class EditCourseFragment extends Fragment {

	private static final String TAG = EditCourseFragment.class.getSimpleName();
	
	private long mId;
	private String mName;
	
	public EditCourseFragment(long id, String name) {
		mId = id;
		mName = name;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate");
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Log.d(TAG, "onCreateView");
		View view = inflater.inflate(R.layout.fragment_edit_course, container, false);

		final EditText editText = (EditText) view.findViewById(R.id.edit_course_menu_name_edit_text);
		if (mName != null) {
			editText.setText(mName);
		}
			
		view.findViewById(R.id.edit_course_menu_button_save).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				saveCourse(editText.getText().toString());
			}
		});

		return view;
	}

	public void saveCourse(String courseName) {
		if (mId > 0) {
			CourseEntryUtil.getUtil().updateCourse(mId, courseName);
		} else {
			CourseEntryUtil.getUtil().insertCourse(courseName);
		}
		
		getActivity().finish();
	}

}