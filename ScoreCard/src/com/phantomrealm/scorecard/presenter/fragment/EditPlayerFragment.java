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
import com.phantomrealm.scorecard.model.db.PlayerEntryUtil;

public class EditPlayerFragment extends Fragment {

	private static final String TAG = EditPlayerFragment.class.getSimpleName();
	
	private long mId;
	private String mName;
	
	public EditPlayerFragment(long id, String name) {
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
		View view = inflater.inflate(R.layout.fragment_edit_player, container, false);

		final EditText editText = (EditText) view.findViewById(R.id.edit_player_menu_name_edit_text);
		if (mName != null) {
			editText.setText(mName);
		}
			
		view.findViewById(R.id.edit_player_menu_button_save).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				savePlayer(editText.getText().toString());
			}
		});

		return view;
	}

	public void savePlayer(String playerName) {
		if (mId > 0) {
			PlayerEntryUtil.getUtil().updatePlayer(mId, playerName);
		} else {
			PlayerEntryUtil.getUtil().insertPlayer(playerName);
		}
		
		getActivity().finish();
	}

}