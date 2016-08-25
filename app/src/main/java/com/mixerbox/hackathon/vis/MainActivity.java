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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ///

        DB dd = new DB(MainActivity.this);
        Log.d("DBDB", dd.chk_team_exist("testteam")?"aaa":"bbb");
        /*ArrayList<Player> al = new ArrayList<>();
        al.add(new Player("zeus2", "zeus_nick", Position.MIDDLE_BLOCKER.toString(), "25"));
        al.add(new Player("zeus3", "zeus_nick", Position.MIDDLE_BLOCKER.toString(), "16"));
        dd.writePlayers(new Team("testteam2", al));*/
        ArrayList<String >  a = dd.getTeamList();
        for(int i=0;i<a.size();i++)
        {
            Log.d("QQQ", a.get(i));
            Log.d("WWW", dd.getTeamByTeamName(a.get(i)).teamName);

            dd.writeMatch(new Match(3, "an oppo name", 25, 25, 15, dd.getTeamByTeamName(a.get(i)), "0102"));

            Team tt = dd.getTeamByTeamName(a.get(i));
            Match mm = new Match(3, "an oppo name", 25, 25, 15, dd.getTeamByTeamName(a.get(i)), "8787");
            Game gg = new Game(dd.getTeamByTeamName(a.get(i)));
            gg.addRecord(new Record(RecordType.TEAM_FAULT));
            gg.addRecord(new Record(RecordType.SUBSTITUTION, tt.playerList.get(0).name, tt.playerList.get(0).name));
            gg.addRecord(new Record(RecordType.ACTION, tt.playerList.get(0).name, ActionType.ATTACK, ActionResultType.ATTEMPT));
            mm.addGame(gg);
            dd.writeMatch(mm);
        }

        Button editBtn = (Button) findViewById(R.id.btn_edit_team);
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ShowTeamActivity.class);
                startActivity(intent);
            }
        });

    }
}
