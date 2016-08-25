package com.mixerbox.hackathon.vis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ShowPlayersActivity extends AppCompatActivity {

    DB db = new DB(ShowPlayersActivity.this);
    static final int ADD_PLAYER = 1, EDIT_PLAYER = 2;
    Team team;
    String teamName_intent;
    ArrayList<Player> playerArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_players);

        teamName_intent = getIntent().getStringExtra("TEAM_NAME");
        if (teamName_intent.length() != 0) {
            ((EditText) findViewById(R.id.team_name)).setText(teamName_intent);
            team = db.getTeamByTeamName(teamName_intent);
            playerArrayList = team.playerList;
        } else {
            ((EditText) findViewById(R.id.team_name)).setHint("Enter Your Team Name");
            playerArrayList = new ArrayList<Player>();
        }

        Button btnAdd = (Button) findViewById(R.id.btn_add_player);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addPlayerIntent = new Intent(ShowPlayersActivity.this, PlayerInfo.class);
                addPlayerIntent.putExtra("NAME", "");
                addPlayerIntent.putExtra("NICKNAME", "");
                addPlayerIntent.putExtra("POSITION", "");
                addPlayerIntent.putExtra("NUMBER", "");
                startActivityForResult(addPlayerIntent, ADD_PLAYER);
            }
        });

        update();

        Button btnSave = (Button) findViewById(R.id.btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                team = new Team(((EditText) findViewById(R.id.team_name)).getText().toString(), playerArrayList);
                db.writePlayers(team);
                Log.d("TEAM_NAME", team.teamName);
                Log.d("TEAM_SIZE", String.valueOf(team.playerList.size()));
                for (int i = 0; i < team.playerList.size(); i++) {
                    Log.d("TEAM_MEMBER " + String.valueOf(i), team.playerList.get(i).name);
                }
                finish();
            }
        });

        setTitle("Roster");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("REQUEST_CODE", String.valueOf(requestCode));
        if (requestCode == ADD_PLAYER) {
            if (resultCode == RESULT_OK) {
                Player player = new Player(data.getStringExtra("_NAME"), data.getStringExtra("_NICKNAME"),
                        Position.valueOf(data.getStringExtra("_POSITION")), data.getStringExtra("_NUMBER"));
                playerArrayList.add(player);
                update();
            }
        }
        if (requestCode == EDIT_PLAYER) {
            if (resultCode == RESULT_OK) {
                Player player = new Player(data.getStringExtra("_NAME"), data.getStringExtra("_NICKNAME"),
                        Position.valueOf(data.getStringExtra("_POSITION")), data.getStringExtra("_NUMBER"));
                String playerStr = player.name + " " + player.nickName + " " + String.valueOf(player.position) + " " + player.number;
                int index = data.getIntExtra("_INDEX", 0);
                Log.d("INDEX: " + String.valueOf(index), playerStr);
                team.playerList.set(index, player);
                update();
            }
        }
    }

    public void update() {
        PlayersAdapter adapter = new PlayersAdapter(ShowPlayersActivity.this, playerArrayList);
        ListView listView = (ListView) findViewById(R.id.lv);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Player _player = (Player) adapterView.getItemAtPosition(i);
                Intent playerInfoIntent = new Intent(ShowPlayersActivity.this, PlayerInfo.class);
                playerInfoIntent.putExtra("INDEX", i);
                playerInfoIntent.putExtra("NAME", _player.name);
                playerInfoIntent.putExtra("NICKNAME", _player.nickName);
                playerInfoIntent.putExtra("POSITION", String.valueOf(_player.position));
                playerInfoIntent.putExtra("NUMBER", _player.number);
                startActivityForResult(playerInfoIntent, EDIT_PLAYER);
            }
        });
    }

}
