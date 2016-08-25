package com.mixerbox.hackathon.vis;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ///
        /*
        DB dd = new DB(MainActivity.this);
        Log.d("DBDB", dd.chk_team_exist("testteam")?"aaa":"bbb");
        /*ArrayList<Player> al = new ArrayList<>();
        al.add(new Player("zeus2", "zeus_nick", Position.MIDDLE_BLOCKER.toString(), "25"));
        al.add(new Player("zeus3", "zeus_nick", Position.MIDDLE_BLOCKER.toString(), "16"));
        dd.writePlayers(new Team("testteam2", al));
        ArrayList<String >  a = dd.getTeamList();
        for(int i=0;i<a.size();i++)
        {
            Log.d("QQQ", a.get(i));
            Log.d("WWW", dd.getTeamByTeamName(a.get(i)).teamName);

            dd.writeMatch(new Match(3, "an oppo name", 25, 25, 15, dd.getTeamByTeamName(a.get(i))));
        }*/
    }
}
