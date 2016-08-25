package com.mixerbox.hackathon.vis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ShowTeamActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_team);

        ArrayList<String> teamNameList = new ArrayList<String>();
        teamNameList.add("NTU CSIE 系排A隊");
        teamNameList.add("NTU CSIE 系排B隊");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ShowTeamActivity.this,
                R.layout.team_list_item, teamNameList);
        ListView listView = (ListView) findViewById(R.id.lv);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String teamName = (String) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(ShowTeamActivity.this, ShowPlayersActivity.class);
                intent.putExtra("TEAM_NAME", teamName);
                startActivity(intent);
            }
        });

        setTitle("Team List");
    }

}
