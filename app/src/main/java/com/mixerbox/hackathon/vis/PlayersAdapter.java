package com.mixerbox.hackathon.vis;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class PlayersAdapter extends ArrayAdapter<Player> {

    private static class ViewHolder {
        private TextView itemView;
    }

    public PlayersAdapter(Context context, int textViewResourceId, ArrayList<Player> items) {
        super(context, textViewResourceId, items);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(this.getContext())
                    .inflate(R.layout.player_list_item, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.itemView = (TextView) convertView.findViewById(R.id.player_name);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Player item = getItem(position);
        if (item!= null) {
            // My layout has only one TextView
            // do whatever you want with your string and long
            viewHolder.itemView.setText(String.format("%s %s", item.number, item.name));
        }

        return convertView;
    }

}
