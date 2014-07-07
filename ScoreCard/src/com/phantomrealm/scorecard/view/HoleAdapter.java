package com.phantomrealm.scorecard.view;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.phantomrealm.scorecard.R;
import com.phantomrealm.scorecard.model.Course;

public class HoleAdapter extends ArrayAdapter<Integer> {

	private static final String HOLE_LABEL = "Hole ";
	private static final String PAR_LABEL = "Par";

	private List<Integer> mPars;
	private LayoutInflater mInflater;

    public HoleAdapter(Context context, int resource, int textViewResourceId, List<Integer> pars) {
		super(context, resource, textViewResourceId, pars);

		mPars = pars;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
    
    public Integer getPar(int index) {
    	return mPars.get(index);
    }

    public void addHole() {
    	mPars.add(Course.DEFAULT_PAR);
    	notifyDataSetChanged();
    }
    
    public void removeHole() {
    	mPars.remove(mPars.size() - 1);
    	notifyDataSetChanged();
    }

    @Override
    public int getCount() {
    	return mPars == null ? 0 : mPars.size();
    }

    @SuppressLint("InflateParams")
	public View getView(final int position, View convertView, ViewGroup parent) {
    	if (convertView == null) {
    		convertView = mInflater.inflate(R.layout.list_item_hole, null);
    	}

    	Integer par = getPar(position);
    	
    	((TextView) convertView.findViewById(R.id.list_item_hole_number_text_view)).setText(HOLE_LABEL + (position + 1));
    	((TextView) convertView.findViewById(R.id.list_item_hole_par_text_view)).setText(PAR_LABEL);
    	final TextView parCountView = (TextView) convertView.findViewById(R.id.list_item_hole_par_label);
    	parCountView.setText(Integer.toString(par));
    	final TextView decreaseParButton = (TextView) convertView.findViewById(R.id.list_item_hole_decrease_par_button);
    	decreaseParButton.setEnabled(mPars.get(position) > 1);
    	decreaseParButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mPars.set(position, mPars.get(position) - 1);
				
				int par = mPars.get(position);
				parCountView.setText(Integer.toString(par));
				if (par == 1) {
					decreaseParButton.setEnabled(false);
				}
				
			}
		});
    	
    	final TextView increaseButton = (TextView) convertView.findViewById(R.id.list_item_hole_increase_par_button);
    	increaseButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mPars.set(position, mPars.get(position) + 1);
				
				int par = mPars.get(position);
				parCountView.setText(Integer.toString(par));
				if (par == 2) {
					decreaseParButton.setEnabled(true);
				}
			}
		});

    	return convertView;
    }

}