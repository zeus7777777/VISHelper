package com.mixerbox.hackathon.vis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ShowPlayersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_players);

        ((TextView) findViewById(R.id.team_name)).setText(getIntent().getStringExtra("TEAM_NAME"));

        ArrayList<Player> playerList = new ArrayList<Player>();

        playerList.add(new Player(0, "小火龍", Position.WING_SPIKER, "10"));
        playerList.add(new Player(1, "傑尼龜", Position.LIBERO, "2"));
        playerList.add(new Player(2, "妙蛙種子", Position.MIDDLE_BLOCKER, "3"));

        PlayersAdapter adapter = new PlayersAdapter(ShowPlayersActivity.this, playerList);
        ListView listView = (ListView) findViewById(R.id.lv);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Player _player = (Player) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(ShowPlayersActivity.this, PlayerInfo.class);
                intent.putExtra("NAME", _player.name);
                intent.putExtra("NICKNAME", _player.nickname);
                intent.putExtra("POSITION", _player.position);
                intent.putExtra("NUMBER", _player.number);
                startActivity(intent);
            }
        });
    }
}