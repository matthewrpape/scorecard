package com.phantomrealm.scorecard.view;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.phantomrealm.scorecard.R;
import com.phantomrealm.scorecard.model.Course;

public class CourseAdapter extends ArrayAdapter<Course> {

//	private static final String TAG = CourseAdapter.class.getSimpleName();

	private List<Course> mCourses;
	private LayoutInflater mInflater;

    public CourseAdapter(Context context, int resource, int textViewResourceId, List<Course> courses) {
		super(context, resource, textViewResourceId, courses);

		mCourses = courses;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
    
    public Course getCourse(int index) {
    	return mCourses.get(index);
    }

    @Override
    public int getCount() {
    	return mCourses == null ? 0 : mCourses.size();
    }

    @SuppressLint("InflateParams")
	public View getView(int position, View convertView, ViewGroup parent) {
    	if (convertView == null) {
    		convertView = mInflater.inflate(R.layout.list_item_course, null);
    	}

    	Course course = getCourse(position);
    	
    	((TextView) convertView.findViewById(R.id.list_course_name)).setText(course.getName());
    	((TextView) convertView.findViewById(R.id.list_course_holes)).setText("Holes: " + course.getHoleCount());
    	((TextView) convertView.findViewById(R.id.list_course_par)).setText("Par: " + course.getTotalPar());

    	return convertView;
    }

}