package com.mixerbox.hackathon.vis;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    static Match match;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TestDb.addTestTeam(MainActivity.this);
        TestDb.addTestMatch(MainActivity.this);
        TestDb.getTestMatchList(MainActivity.this);

        Button editBtn = (Button) findViewById(R.id.btn_edit_team);
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ShowTeamActivity.class);
                startActivity(intent);
            }
        });

        Button btnNewMatch = (Button)findViewById(R.id.btn_new_match);
        btnNewMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MatchInfoActivity.class);
                startActivity(intent);
            }
        });

    }
}
