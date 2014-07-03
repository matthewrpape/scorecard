package com.phantomrealm.scorecard.view;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.phantomrealm.scorecard.R;
import com.phantomrealm.scorecard.model.Player;

public class PlayerAdapter extends ArrayAdapter<Player> {

//	private static final String TAG = PlayerAdapter.class.getSimpleName();

	private List<Player> mPlayers;
	private LayoutInflater mInflater;

    public PlayerAdapter(Context context, int resource, int textViewResourceId, List<Player> players) {
		super(context, resource, textViewResourceId, players);

		mPlayers = players;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

    @Override
    public int getCount() {
    	return mPlayers == null ? 0 : mPlayers.size();
    }

    public Player getItem(Player position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
    	if (convertView == null) {
    		convertView = mInflater.inflate(R.layout.list_item_player, null);
    	}

    	TextView nameView = (TextView) convertView.findViewById(R.id.list_player_name);
    	nameView.setText(mPlayers.get(position).getName());

    	return convertView;
    }
}
