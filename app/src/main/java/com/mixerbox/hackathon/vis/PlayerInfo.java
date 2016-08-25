package com.mixerbox.hackathon.vis;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class PlayerInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_info);

        ((TextView)findViewById(R.id.player_name)).setText(getIntent().getStringExtra("NAME"));
        ((TextView)findViewById(R.id.player_nickname)).setText(getIntent().getStringExtra("NICKNAME"));
        ((TextView)findViewById(R.id.player_position)).setText(getIntent().getStringExtra("POSITION"));
        ((TextView)findViewById(R.id.player_number)).setText(getIntent().getStringExtra("NUMBER"));

        setTitle("Player Information");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
