package com.mixerbox.hackathon.vis;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;

public class ShowHistoryActivity extends AppCompatActivity {

    static ArrayList<Match> alm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_history);

        DB db = new DB(ShowHistoryActivity.this);

        ArrayList<MatchInfo> al = db.getMatchList();
        alm = new ArrayList<>();
        for(int i=0;i<al.size();i++)
        {
            alm.add(db.getMatchByTime(al.get(i).matchTime));
        }

        ListView lv = (ListView) findViewById(R.id.lv_show_history);
        lv.setAdapter(new HistoryAdapter(ShowHistoryActivity.this, alm));
    }

    @Override
    protected void onStart() {
        super.onStart();
        DB db = new DB(ShowHistoryActivity.this);

        ArrayList<MatchInfo> al = db.getMatchList();
        alm = new ArrayList<>();
        for(int i=0;i<al.size();i++)
        {
            alm.add(db.getMatchByTime(al.get(i).matchTime));
        }

        ListView lv = (ListView) findViewById(R.id.lv_show_history);
        lv.setAdapter(new HistoryAdapter(ShowHistoryActivity.this, alm));
    }
}
