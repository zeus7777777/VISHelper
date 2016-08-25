package com.mixerbox.hackathon.vis;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends AppCompatActivity {

    Button btnOE;
    Button btnTF;
    TextView tvMyScore;
    TextView tvOpScore;
    TextView tvMyGameScore;
    TextView tvOpGameScore;
    TextView[] tvGameLoc;
    LinearLayout layoutServe;
    LinearLayout layoutAction;
    LinearLayout layoutResult;
    Button btnServe;
    Button btnReceive;
    Button btnATK;
    Button btnBLK;
    Button btnSET;
    Button btnDIG;
    Button btnKILL;
    Button btnEXCL;
    Button btnATP;
    Button btnERR;

    Game game;
    Player curPlayer;
    ActionType curActionType;
    ActionResultType curActionResultType;
    boolean flagRotate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        flagRotate = false;
        curPlayer = null;
        curActionResultType = null;
        game = MainActivity.match.games.get(MainActivity.match.games.size()-1);

        tvMyGameScore = (TextView)findViewById(R.id.tv_mygamescore);
        tvMyGameScore.setText(Integer.toString(MainActivity.match.myGamePoint));

        tvOpGameScore = (TextView)findViewById(R.id.tv_opgamescore);
        tvOpGameScore.setText(Integer.toString(MainActivity.match.oppositeGamePoint));

        tvMyScore = (TextView)findViewById(R.id.tv_myscore);
        tvOpScore = (TextView)findViewById(R.id.tv_opscore);

        layoutServe = (LinearLayout)findViewById(R.id.layout_serve);
        layoutAction = (LinearLayout)findViewById(R.id.layout_action);
        layoutResult = (LinearLayout)findViewById(R.id.layout_result);

        btnServe = (Button)findViewById(R.id.btn_serve);
        btnServe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPoint();
                layoutServe.setVisibility(View.INVISIBLE);
            }
        });

        btnReceive = (Button)findViewById(R.id.btn_receive);
        btnReceive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                losePoint();
                layoutServe.setVisibility(View.INVISIBLE);
            }
        });

        btnTF = (Button)findViewById(R.id.btn_tf);
        btnTF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Record rec = new Record(RecordType.TEAM_FAULT);
                game.addRecord(rec);
                losePoint();
                refreshActivity();
            }
        });

        btnOE = (Button)findViewById(R.id.btn_oe);
        btnOE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Record rec = new Record(RecordType.OPPONENT_ERROR);
                game.addRecord(rec);
                getPoint();
                refreshActivity();
            }
        });

        tvGameLoc = new TextView[6];
        for (int i = 0; i < 6; i++) {
            tvGameLoc[i] = (TextView)findViewById(getResources().getIdentifier("tv_game_loc"+i, "id", getPackageName()));
            tvGameLoc[i].setOnClickListener(new tvOnClickListener());
            tvGameLoc[i].setOnLongClickListener(new tvOnLongClickListener());
        }

        btnATK = (Button)findViewById(R.id.btn_atk);
        btnATK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                curActionType = ActionType.ATTACK;
                showResultBtn();
            }
        });

        btnBLK = (Button)findViewById(R.id.btn_blk);
        btnBLK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                curActionType = ActionType.BLOCK;
                showResultBtn();
            }
        });

        btnSET = (Button)findViewById(R.id.btn_set);
        btnSET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                curActionType = ActionType.SET;
                showResultBtn();
            }
        });

        btnDIG = (Button)findViewById(R.id.btn_dig);
        btnDIG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                curActionType = ActionType.DIG;
                showResultBtn();
            }
        });

        btnEXCL = (Button)findViewById(R.id.btn_exl);
        btnEXCL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newResult(ActionResultType.EXCELLENT);
            }
        });

        btnKILL = (Button)findViewById(R.id.btn_kill);
        btnKILL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newResult(ActionResultType.SUCCESS);
            }
        });

        btnATP = (Button)findViewById(R.id.btn_atp);
        btnATP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newResult(ActionResultType.ATTEMPT);
            }
        });

        btnERR = (Button)findViewById(R.id.btn_err);
        btnERR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newResult(ActionResultType.FAULT);
            }
        });

        refreshActivity();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_setting, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ac_undo:
                return true;
            default:
                return true;
        }
    }

    private class tvOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            clearCurPlayer();
            int idx = getTvIndex(view);
            curPlayer = game.gameLocation[idx];
            ((TextView)view).setTextColor(Color.RED);
            if (curActionResultType != null) newResult(curActionResultType);
        }
    }

    private class tvOnLongClickListener implements View.OnLongClickListener {
        @Override
        public boolean onLongClick(View view) {
            int idx = getTvIndex(view);
            curPlayer = game.gameLocation[idx];
            ((TextView)view).setTextColor(Color.RED);
            final PopupMenu popupmenu = new PopupMenu(GameActivity.this, view);
            popupmenu.inflate(R.menu.popup_menu);
            popupmenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.menu_substitute:
                            break;
                        default:
                            FoulType foulType = FoulType.valueOf(item.getTitle().toString().toUpperCase().replace(' ', '_'));
                            game.addRecord(new Record(RecordType.FOUL, curPlayer.toString(), foulType));
                            clearCurPlayer();
                            break;
                    }
                    return true;
                }
            });
            popupmenu.show();
            return true;
        }
    }

    int getTvIndex(View view) {
        for (int i = 0; i < 7; i++) {
            if (view.getId() == getResources().getIdentifier("tv_game_loc"+i, "id", getPackageName())) {
                return i;
            }
        }
        return -1;
    }

    void newResult(ActionResultType actionResultType) {
        curActionResultType = actionResultType;
        if (curPlayer == null) {
            Toast.makeText(GameActivity.this, "Please choose a player.", Toast.LENGTH_LONG).show();
        } else {
            Record rec = new Record(RecordType.ACTION, curPlayer.toString(), curActionType, curActionResultType);
            game.addRecord(rec);
            clearCurPlayer();
            if (curActionResultType == ActionResultType.SUCCESS) {
                getPoint();
            } else if (curActionResultType == ActionResultType.FAULT) {
                losePoint();
            } else {
                showActionBtn();
            }
            curActionResultType = null;
        }
    }
    private void losePoint() {
        flagRotate = true;
        curActionType = ActionType.RECEPTION;
        showResultBtn();
        refreshActivity();
        checkFinish();
    }

    private void getPoint() {
        if (flagRotate) game.shiftClockWise();
        flagRotate = false;
        curActionType = ActionType.SERVE;
        curPlayer = game.gameLocation[0];
        tvGameLoc[0].setTextColor(Color.RED);
        showResultBtn();
        refreshActivity();
        checkFinish();
    }

    void checkFinish() {
        int a = game.myScore, b = game.oppositeScore;
        int winScore = MainActivity.match.winScore;
        if (MainActivity.match.games.size() == MainActivity.match.numGame) {
            winScore = MainActivity.match.lastGameWinScore;
        }

        if ((Math.max(a, b) >= MainActivity.match.maxScore) ||
                (Math.max(a, b) >= winScore && Math.abs(a-b) > 1)) {
            if (a > b) MainActivity.match.myGamePoint++;
            else MainActivity.match.oppositeGamePoint++;

            a = MainActivity.match.myGamePoint;
            b = MainActivity.match.oppositeGamePoint;
            if (Math.max(a, b) > MainActivity.match.numGame / 2) {
                DB db = new DB(GameActivity.this);
                db.writeMatch(MainActivity.match);
                Intent intent = new Intent(GameActivity.this, MainActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(GameActivity.this, RotationSheetActivity.class);
                startActivity(intent);
            }
        }
    }

    void clearCurPlayer() {
        int idx;
        if (curPlayer == null) return;
        for (idx = 0; idx < 6; idx++) {
            if (game.gameLocation[idx] == curPlayer) break;
        }
        if (idx < 6) {
            tvGameLoc[idx].setTextColor(Color.BLACK);
            curPlayer = null;
        }
    }

    private void showActionBtn() {
        layoutAction.setVisibility(View.VISIBLE);
        layoutResult.setVisibility(View.INVISIBLE);
    }

    private void showResultBtn() {
        layoutAction.setVisibility(View.INVISIBLE);
        layoutResult.setVisibility(View.VISIBLE);
        if (curActionType == ActionType.ATTACK || curActionType == ActionType.BLOCK || curActionType == ActionType.SERVE) {
            btnKILL.setVisibility(View.VISIBLE);
            btnEXCL.setVisibility(View.INVISIBLE);
        } else {
            btnKILL.setVisibility(View.INVISIBLE);
            btnEXCL.setVisibility(View.VISIBLE);
        }
    }

    private void refreshActivity() {
        tvMyScore.setText(Integer.toString(game.myScore));
        tvOpScore.setText(Integer.toString(game.oppositeScore));

        for (int i = 0; i < 6; i++) {
            tvGameLoc[i].setText(game.gameLocation[i].toString());
        }
    }
}
