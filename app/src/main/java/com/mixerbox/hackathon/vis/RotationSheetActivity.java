package com.mixerbox.hackathon.vis;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class RotationSheetActivity extends AppCompatActivity {

    static String[] numbers = {"I", "II", "III", "IV", "V", "VI", "L"};
    int cur_loc;
    boolean[] loc_filled;
    Game game;

    ImageButton btnRotateClockwise;
    ImageButton btnRotateCounterClockwise;
    TextView[] tvLoc;
    GridView gvPlayers;
    Button btnConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rotation_sheet);

        cur_loc = 0;
        loc_filled = new boolean[7];

        game = new Game(MainActivity.match.myTeam, 0, 0);
        MainActivity.match.games.add(game);

        btnRotateClockwise = (ImageButton)findViewById(R.id.btn_rotate_clockwise);
        btnRotateClockwise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                game.shiftClockWise();
                boolean tmp = loc_filled[0];
                for (int i = 0; i < 5; i++) loc_filled[i] = loc_filled[i+1];
                loc_filled[5] = tmp;
                if (cur_loc >= 0 && cur_loc < 6) cur_loc = (cur_loc + 5) % 6;
                showLocation();
            }
        });

        btnRotateCounterClockwise = (ImageButton)findViewById(R.id.btn_rotate_counterclockwise);
        btnRotateCounterClockwise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                game.shiftCounterClockWise();
                boolean tmp = loc_filled[5];
                for (int i = 5; i > 0; i--) loc_filled[i] = loc_filled[i-1];
                loc_filled[0] = tmp;
                if (cur_loc >= 0 && cur_loc < 6) cur_loc = (cur_loc + 1) % 6;
                showLocation();
            }
        });

        tvLoc = new TextView[7];
        for (int i = 0; i < 7; i++) {
            tvLoc[i] = (TextView)findViewById(getResources().getIdentifier("tv_loc"+i, "id", getPackageName()));
            tvLoc[i].setOnClickListener(new tvOnClickListener());
        }

        gvPlayers = (GridView)findViewById(R.id.gv_players);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, MainActivity.match.myTeam.getPlayerNumName());
        gvPlayers.setAdapter(adapter);
        gvPlayers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                if (cur_loc < 0) return;
                loc_filled[cur_loc] = true;
                game.gameLocation[cur_loc] = game.myTeam.getPlayer(position);
                //v.setEnabled(false);
                v.setClickable(true);
                Log.d("a", v.isClickable()+"");
                //v.setFocusable(false);
                ((TextView)v).setTextColor(Color.LTGRAY);
                cur_loc = find_empty();
                showLocation();
            }
        });


        btnConfirm = (Button)findViewById(R.id.btn_confirm_loc);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RotationSheetActivity.this, GameActivity.class);
                startActivity(intent);
            }
        });

        showLocation();
    }

    class tvOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            int idx = getTvIndex(view);
            if (loc_filled[idx]) {
                loc_filled[idx] = false;
                int i = game.myTeam.playerList.indexOf(game.gameLocation[idx]);
                TextView v = (TextView) gvPlayers.getChildAt(i);
                //v.setEnabled(true);
                v.setClickable(false);
                Log.d("b", v.isClickable()+"");
                //v.setFocusable(true);
                v.setTextColor(Color.BLACK);
                game.gameLocation[idx] = null;
                tvLoc[idx].setText(numbers[idx]);
            }
            cur_loc = idx;
            showLocation();
        }
    }

    int find_empty() {
        for (int i = 1; i < 8; i++) {
            if (!loc_filled[(cur_loc+i) % 7]) {
                return (cur_loc+i) % 7;
            }
        }
        return -1;
    }

    void showLocation() {
        for (int i = 0; i < 7; i++) {
            if (loc_filled[i]) {
                tvLoc[i].setText(game.gameLocation[i].toString());
            } else {
                tvLoc[i].setText(numbers[i]);
            }
            tvLoc[i].setTextColor(Color.BLACK);
        }
        if (cur_loc >= 0) tvLoc[cur_loc].setTextColor(Color.RED);
    }

    int getTvIndex(View view) {
        for (int i = 0; i < 7; i++) {
            if (view.getId() == getResources().getIdentifier("tv_loc"+i, "id", getPackageName())) {
                return i;
            }
        }
        return -1;
    }
}
