package com.mixerbox.hackathon.vis;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
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
    static Player[] lastLocation;
    int cur_loc;
    boolean[] loc_filled;
    Game game;

    TextView tvGameNum;
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

        tvGameNum = (TextView)findViewById(R.id.tv_game_num);
        tvGameNum.setText("Game "+MainActivity.match.games.size());

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
        gvPlayers.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                if (lastLocation == null) {
                    lastLocation = new Player[7];
                } else {
                    for (int i = 0; i < 7; i++) {
                        if (lastLocation[i] != null) {
                            game.gameLocation[i] = lastLocation[i];
                            loc_filled[i] = true;
                            int idx = game.myTeam.playerList.indexOf(lastLocation[i]);
                            TextView v = (TextView)gvPlayers.getChildAt(idx);
                            if (v == null) Log.d("XDDDDD", Integer.toString(idx));
                            v.setClickable(true);
                            v.setBackground(null);
                        }
                    }
                }
                showLocation();
                gvPlayers.getViewTreeObserver().removeOnPreDrawListener(this);
                return true;
            }
        });
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, MainActivity.match.myTeam.getPlayerNumName());
        gvPlayers.setAdapter(adapter);
        gvPlayers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                if (cur_loc < 0) return;
                loc_filled[cur_loc] = true;
                game.gameLocation[cur_loc] = game.myTeam.getPlayer(position);
                v.setClickable(true);
                ((TextView)v).setTextColor(Color.LTGRAY);
                cur_loc = find_empty();
                showLocation();
            }
        });



        btnConfirm = (Button)findViewById(R.id.btn_confirm_loc);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < 7; i++) {
                    lastLocation[i] = game.gameLocation[i];
                }
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
                v.setClickable(false);
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
                switch (game.gameLocation[i].position) {
                    case WING_SPIKER:
                        tvLoc[i].setBackgroundColor(Color.RED);
                        tvLoc[i].setTextColor(Color.BLACK);
                        break;
                    case MIDDLE_BLOCKER:
                        tvLoc[i].setBackgroundColor(Color.BLACK);
                        tvLoc[i].setTextColor(Color.WHITE);
                        break;
                    case SETTER:
                        tvLoc[i].setBackgroundColor(Color.BLUE);
                        tvLoc[i].setTextColor(Color.BLACK);
                        break;
                    case OPPOSITE:
                        tvLoc[i].setBackgroundColor(Color.GREEN);
                        tvLoc[i].setTextColor(Color.BLACK);
                        break;
                    case LIBERO:
                        tvLoc[i].setBackground(null);
                        tvLoc[i].setTextColor(Color.BLACK);
                        break;
                    default:
                        Log.d("HMMMM", "Default");
                }
            } else {
                tvLoc[i].setText(numbers[i]);
                tvLoc[i].setTextColor(Color.BLACK);
                tvLoc[i].setBackground(null);
            }
        }
        if (cur_loc >= 0) tvLoc[cur_loc].setBackground(getResources().getDrawable(R.drawable.back));
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
