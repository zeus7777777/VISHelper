package com.mixerbox.hackathon.vis;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by zeus on 2016/8/26.
 */
public class HistoryAdapter extends BaseAdapter{

    ArrayList<Match> alm;
    LayoutInflater inflater ;
    Context ctx;

    static boolean[] initClick;

    public HistoryAdapter(Context _ctx, ArrayList<Match> _alm)
    {
        alm = _alm;
        inflater = LayoutInflater.from(_ctx);
        ctx = _ctx;
        initClick = new boolean[alm.size()];
    }

    @Override
    public int getCount() {
        return alm.size();
    }

    @Override
    public Object getItem(int i) {
        return alm.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        ViewHolder vh;
        if(view==null)
        {
            view = inflater.inflate(R.layout.spinner_in_listview, null);
            vh = new ViewHolder();
            vh.tv = (TextView) view.findViewById(R.id.tv_match_name);
            vh.sp = (Spinner) view.findViewById(R.id.sp_history);
            view.setTag(vh);
        }
        else
        {
            vh = (ViewHolder) view.getTag();
        }
        vh.tv.setText(alm.get(i).dateTime+" "+alm.get(i).oppositeTeamName);

        String[] arr = new String[alm.get(i).games.size()+1];
        arr[0] = "Choose a set";
        for(int j=1;j<=arr.length-1;j++)
        {
            arr[j] = "Set " + (j);
        }

        ArrayAdapter<String> ada = new ArrayAdapter<String>(
                ctx, R.layout.big_spinner, arr);
        vh.sp.setAdapter(ada);

        boolean ft = false;
        vh.sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int j, long l) {
                if(j==0)
                    return;
                Log.d("select", j + "");
                Intent it = new Intent(ctx, ShowStatisticsActivity.class);
                    ShowStatisticsActivity.hm = alm.get(i).games.get(j-1).getStatistic();
                    ctx.startActivity(it);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return view;
    }

    static class ViewHolder
    {
        TextView tv;
        Spinner sp;
    }
}
