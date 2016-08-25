package com.mixerbox.hackathon.vis;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class PlayersAdapter extends BaseAdapter {
    LayoutInflater inflater;
    ArrayList<Player> playerList;

    public PlayersAdapter(Context ctx, ArrayList<Player> _playerList) {
        inflater = LayoutInflater.from(ctx);
        playerList = _playerList;
    }

    @Override
    public int getCount() {
        return playerList.size();
    }

    @Override
    public Object getItem(int i) {
        return playerList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = inflater.inflate(R.layout.player_list_item, null);
        Player player = (Player) getItem(i);
        ((TextView) v.findViewById(R.id.player_name)).setText(player.name);
        ((TextView) v.findViewById(R.id.player_number)).setText(player.number);

        return v;
    }
}
