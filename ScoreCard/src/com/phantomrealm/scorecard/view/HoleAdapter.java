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

    @Override
    public int getCount() {
    	return mPars == null ? 0 : mPars.size();
    }

    @SuppressLint("InflateParams")
	public View getView(int position, View convertView, ViewGroup parent) {
    	if (convertView == null) {
    		convertView = mInflater.inflate(R.layout.list_item_hole, null);
    	}

    	Integer par = getPar(position);
    	
    	((TextView) convertView.findViewById(R.id.list_item_hole_number_text_view)).setText(HOLE_LABEL + (position + 1));
    	((TextView) convertView.findViewById(R.id.list_item_hole_par_text_view)).setText(PAR_LABEL);
    	((TextView) convertView.findViewById(R.id.list_item_hole_par_label)).setText(Integer.toString(par));
    	((TextView) convertView.findViewById(R.id.list_item_hole_decrease_par_button)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO
			}
		});
    	((TextView) convertView.findViewById(R.id.list_item_hole_increase_par_button)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO
			}
		});

    	return convertView;
    }

}