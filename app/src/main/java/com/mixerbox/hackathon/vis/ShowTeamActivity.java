package com.mixerbox.hackathon.vis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class ShowTeamActivity extends AppCompatActivity {

    DB db = new DB(ShowTeamActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_team);

        Button addTeam = (Button) findViewById(R.id.btn_add_team);
        addTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShowTeamActivity.this, ShowPlayersActivity.class);
                intent.putExtra("TEAM_NAME", "");
                startActivity(intent);
            }
        });

        update();

        setTitle("Team List");
    }

    @Override
    protected void onResume() {
        super.onResume();
        update();
    }

    public void update() {
        ArrayList<String> teamNameList = db.getTeamList();
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
    }
}
