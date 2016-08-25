package com.mixerbox.hackathon.vis;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.HashMap;

public class ShowStatisticsActivity extends AppCompatActivity {

    static HashMap<String, PlayerStatistic> hm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_statistics);

        Spinner sp = (Spinner) findViewById(R.id.sp_playername);
        String[] strs = hm.keySet().toArray(new String[1]);
        //String[] strs ={"user1", "player2"};

        ArrayAdapter<String> ada = new ArrayAdapter<String>(
                ShowStatisticsActivity.this, R.layout.big_spinner, strs);
        sp.setAdapter(ada);

        final ListView lv = (ListView) findViewById(R.id.lv_statistic);
        lv.setAdapter(new StatisticsAdapter(ShowStatisticsActivity.this, hm));

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                lv.smoothScrollToPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        Log.d("hmsize", hm.size()+"");
    }
}
