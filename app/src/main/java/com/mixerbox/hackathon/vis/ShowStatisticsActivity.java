package com.mixerbox.hackathon.vis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
        try {
            Log.d("base", getBase64());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getBase64() throws JSONException {
        JSONArray ja = new JSONArray();
        String[] keys = hm.keySet().toArray(new String[1]);
        for(int i=0;i<hm.size();i++)
        {
            JSONObject jo = new JSONObject();
            jo.put("name", keys[i]);
            JSONArray arr = new JSONArray();
            for(int j=0;j<18;j++)
                arr.put(hm.get(keys[i]).result[j]);
            jo.put("result", arr);
            ja.put(jo);
        }
        String ans = Base64.encodeToString(ja.toString().getBytes(), Base64.DEFAULT);
        return ans;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sharemenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.act_share)
        {
            Intent it = new Intent();
            it.setAction(Intent.ACTION_SEND);
            try {
                it.putExtra(Intent.EXTRA_TEXT, "http://140.119.19.32/table.html?base64="+getBase64());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            it.setType("text/plain");
            startActivity(Intent.createChooser(it, "share content by"));
            return true;
        }
        else
        {
            return super.onOptionsItemSelected(item);
        }
    }
}
