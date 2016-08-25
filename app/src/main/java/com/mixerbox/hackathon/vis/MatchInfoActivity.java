package com.mixerbox.hackathon.vis;

import android.content.Intent;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MatchInfoActivity extends AppCompatActivity {

    Spinner spMyTeam;
    Button btnStartMatch;
    EditText edOpTeam;
    EditText edNumGame;
    EditText edScore;
    EditText edMaxScore;
    EditText edLastScore;
    DB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_info);


        spMyTeam = (Spinner)findViewById(R.id.sp_myteam);
        db = new DB(MatchInfoActivity.this);
        ArrayList<String> teamList = db.getTeamList();
        ArrayAdapter<String> adapterList = new ArrayAdapter<String>(MatchInfoActivity.this,
                R.layout.big_spinner, teamList);
        spMyTeam.setAdapter(adapterList);

        edOpTeam = (EditText)findViewById(R.id.ed_opteam);
        edNumGame = (EditText)findViewById(R.id.ed_num_game);
        edScore = (EditText)findViewById(R.id.ed_score);
        edMaxScore = (EditText)findViewById(R.id.ed_max_score);
        edLastScore = (EditText)findViewById(R.id.ed_last_score);


        btnStartMatch = (Button)findViewById(R.id.btn_start_match);
        btnStartMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int numGame = Integer.parseInt(edNumGame.getText().toString());
                String opTeam = edOpTeam.getText().toString();
                int winScore = Integer.parseInt(edScore.getText().toString());
                int maxScore = Integer.parseInt(edMaxScore.getText().toString());
                int lastScore = Integer.parseInt(edLastScore.getText().toString());
                String myTeamName = spMyTeam.getSelectedItem().toString();
                Team myTeam = db.getTeamByTeamName(myTeamName);
                SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                Date now = new Date();
                String dateTime = sdfDate.format(now);

                MainActivity.match = new Match(numGame, opTeam, winScore, maxScore, lastScore, myTeam, dateTime, 0, 0);

                Intent intent = new Intent(MatchInfoActivity.this, RotationSheetActivity.class);
                startActivity(intent);
            }
        });
    }
}
