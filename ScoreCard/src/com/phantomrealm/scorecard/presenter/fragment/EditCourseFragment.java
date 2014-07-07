package com.phantomrealm.scorecard.presenter.fragment;

import java.util.ArrayList;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.phantomrealm.scorecard.R;
import com.phantomrealm.scorecard.model.Course;
import com.phantomrealm.scorecard.model.db.CourseEntryUtil;
import com.phantomrealm.scorecard.view.HoleAdapter;

public class EditCourseFragment extends Fragment {

	private static final String TAG = EditCourseFragment.class.getSimpleName();
	
	private long mId;
	private String mName;
	private int[] mPars;
	private EditText mNameField;
	private TextView mNumberHolesField;
	private HoleAdapter mAdapter;
	private ListView mHolesListView;

	public EditCourseFragment(long id, String name, int[] pars) {
		mId = id;
		mName = name;
		mPars = pars;
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

		mNameField = (EditText) view.findViewById(R.id.edit_course_menu_name_edit_text);
		if (mName != null) {
			mNameField.setText(mName);
		}
		
		mNumberHolesField = (TextView) view.findViewById(R.id.edit_course_menu_holes_text_view);
		if (mPars == null) {
			mPars = Course.createDefaultPars(Course.DEFAULT_HOLES);
		}
		mNumberHolesField.setText(Integer.toString(mPars.length));

		ArrayList<Integer> parList = new ArrayList<Integer>(mPars.length);
		for (int index = 0; index < mPars.length; ++index) {
			parList.add(index, mPars[index]);
		}
		mAdapter = new HoleAdapter(getActivity(), R.layout.list_item_hole, R.id.list_item_hole_number_text_view, parList);
		mHolesListView = (ListView) view.findViewById(R.id.edit_course_menu_holes_list);
		mHolesListView.setAdapter(mAdapter);

		view.findViewById(R.id.edit_course_menu_increment_holes_button).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO
			}
		});

		view.findViewById(R.id.edit_course_menu_decrement_holes_button).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO
			}
		});

		view.findViewById(R.id.edit_course_menu_button_save).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				saveCourse(mNameField.getText().toString());
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