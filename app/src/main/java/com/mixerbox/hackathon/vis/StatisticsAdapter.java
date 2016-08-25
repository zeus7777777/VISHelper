package com.mixerbox.hackathon.vis;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.HashMap;

/**
 * Created by zeus on 2016/8/26.
 */
public class StatisticsAdapter extends BaseAdapter {

    HashMap<String, PlayerStatistic> hm;

    LayoutInflater inflater;

    public StatisticsAdapter(Context ctx, HashMap<String, PlayerStatistic> _hm)
    {
        inflater = LayoutInflater.from(ctx);
        hm = _hm;
    }

    @Override
    public int getCount() {
        return hm.size();
    }

    @Override
    public Object getItem(int i) {
        return hm.get(hm.keySet().toArray(new String[1])[i]);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder vh ;
        if(view==null)
        {
            view = inflater.inflate(R.layout.player_statistics, null);
            vh = new ViewHolder();
            vh.tv = (TextView) view.findViewById(R.id.tv_statistics_name);
            vh.tv_sta_1 = (TextView) view.findViewById(R.id.tv_sta_1);
            vh.tv_sta_2 = (TextView) view.findViewById(R.id.tv_sta_2);
            vh.tv_sta_3 = (TextView) view.findViewById(R.id.tv_sta_3);
            vh.tv_sta_4 = (TextView) view.findViewById(R.id.tv_sta_4);
            vh.tv_sta_5 = (TextView) view.findViewById(R.id.tv_sta_5);
            vh.tv_sta_6 = (TextView) view.findViewById(R.id.tv_sta_6);
            vh.tv_sta_7 = (TextView) view.findViewById(R.id.tv_sta_7);
            vh.tv_sta_8 = (TextView) view.findViewById(R.id.tv_sta_8);
            vh.tv_sta_9 = (TextView) view.findViewById(R.id.tv_sta_9);
            vh.tv_sta_10 = (TextView) view.findViewById(R.id.tv_sta_10);
            vh.tv_sta_11 = (TextView) view.findViewById(R.id.tv_sta_11);
            vh.tv_sta_12 = (TextView) view.findViewById(R.id.tv_sta_12);
            vh.tv_sta_13 = (TextView) view.findViewById(R.id.tv_sta_13);
            vh.tv_sta_14 = (TextView) view.findViewById(R.id.tv_sta_14);
            vh.tv_sta_15 = (TextView) view.findViewById(R.id.tv_sta_15);
            vh.tv_sta_16 = (TextView) view.findViewById(R.id.tv_sta_16);
            vh.tv_sta_17 = (TextView) view.findViewById(R.id.tv_sta_17);
            vh.tv_sta_18 = (TextView) view.findViewById(R.id.tv_sta_18);
            view.setTag(vh);
        }
        else
        {
            vh = (ViewHolder) view.getTag();
        }
        //PlayerStatistic ps = (PlayerStatistic) getItem(i);
        String[] arr = hm.keySet().toArray(new String[1]);
        vh.tv.setText(arr[i]);
        vh.tv_sta_1.setText(hm.get(arr[i]).result[0]+"");
        vh.tv_sta_2.setText(hm.get(arr[i]).result[1]+"");
        vh.tv_sta_3.setText(hm.get(arr[i]).result[2]+"");
        vh.tv_sta_4.setText(hm.get(arr[i]).result[3]+"");
        vh.tv_sta_5.setText(hm.get(arr[i]).result[4]+"");
        vh.tv_sta_6.setText(hm.get(arr[i]).result[5]+"");
        vh.tv_sta_7.setText(hm.get(arr[i]).result[6]+"");
        vh.tv_sta_8.setText(hm.get(arr[i]).result[7]+"");
        vh.tv_sta_9.setText(hm.get(arr[i]).result[8]+"");
        vh.tv_sta_10.setText(hm.get(arr[i]).result[9]+"");
        vh.tv_sta_11.setText(hm.get(arr[i]).result[10]+"");
        vh.tv_sta_12.setText(hm.get(arr[i]).result[11]+"");
        vh.tv_sta_13.setText(hm.get(arr[i]).result[12]+"");
        vh.tv_sta_14.setText(hm.get(arr[i]).result[13]+"");
        vh.tv_sta_15.setText(hm.get(arr[i]).result[14]+"");
        vh.tv_sta_16.setText(hm.get(arr[i]).result[15]+"");
        vh.tv_sta_17.setText(hm.get(arr[i]).result[16]+"");
        vh.tv_sta_18.setText(hm.get(arr[i]).result[17]+"");
        return view;
    }

    static class ViewHolder
    {
        TextView tv;
        TextView tv_sta_1;
        TextView tv_sta_2;
        TextView tv_sta_3;
        TextView tv_sta_4;
        TextView tv_sta_5;
        TextView tv_sta_6;
        TextView tv_sta_7;
        TextView tv_sta_8;
        TextView tv_sta_9;
        TextView tv_sta_10;
        TextView tv_sta_11;
        TextView tv_sta_12;
        TextView tv_sta_13;
        TextView tv_sta_14;
        TextView tv_sta_15;
        TextView tv_sta_16;
        TextView tv_sta_17;
        TextView tv_sta_18;
    }
}
