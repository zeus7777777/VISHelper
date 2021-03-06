package com.mixerbox.hackathon.vis;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by zeus on 2016/8/25.
 */
public class TestDb {
    static void addTestTeam(Context ctx)
    {
        DB dd = new DB(ctx);
        ArrayList<Player> al = new ArrayList<>();
        al.add(new Player("zeus2", "zeus_nick", Position.MIDDLE_BLOCKER, "25"));
        al.add(new Player("zeus3", "zeus_nick", Position.MIDDLE_BLOCKER, "16"));
        dd.writePlayers(new Team("testteam2", al));
    }

    static  void addTestMatch(Context ctx)
    {
        DB dd = new DB(ctx);
        ArrayList<String >  a = dd.getTeamList();
        for(int i=0;i<a.size();i++)
        {
            dd.writeMatch(new Match(3, "an oppo name", 25, 25, 15, dd.getTeamByTeamName(a.get(i)), "0102",2,1));

            Team tt = dd.getTeamByTeamName(a.get(i));
            Match mm = new Match(3, "an oppo name", 25, 25, 15, dd.getTeamByTeamName(a.get(i)), "8787",2,1);
            Game gg = new Game(dd.getTeamByTeamName(a.get(i)),0,0);
            gg.addRecord(new Record(RecordType.TEAM_FAULT));
            gg.addRecord(new Record(RecordType.SUBSTITUTION, tt.playerList.get(0).name, tt.playerList.get(0).name));
            gg.addRecord(new Record(RecordType.ACTION, tt.playerList.get(0).toString(), ActionType.ATTACK, ActionResultType.ATTEMPT));
            mm.addGame(gg);
            dd.writeMatch(mm);
        }
    }

    static void getTestMatchList(Context ctx)
    {
        DB dd = new DB(ctx);
        ArrayList<MatchInfo> mi = dd.getMatchList();
        for(int i=0;i<mi.size();i++)
        {
            Log.d("MatchList", mi.get(i).matchTime);
            Log.d("MatchList", mi.get(i).oppoName);
            Match mm =  dd.getMatchByTime(mi.get(i).matchTime);
            Log.d("numgame", mm.numGame+"");
        }
    }

    static void testAll(Context ctx)
    {
        DB db = new DB(ctx);

        ArrayList<String> al  =db.getTeamList();

        Player p1 = new Player("xtxqqzeus@", "zzzeus_nick@", Position.MIDDLE_BLOCKER, "50");
        Player p2 = new Player("xtxqqzeus@2", "zzzeus2_nick@", Position.SETTER, "60");
        ArrayList<Player> al1 = new ArrayList<>();
        al1.add(p1);
        al1.add(p2);
        Team t1 = new Team("xtxteam1@8787", al1);
        db.writePlayers(t1);

        al = db.getTeamList();
        for(int i=0;i<al.size();i++)
        {
            Log.w("inserted teams", al.get(i));
            Team tm = db.getTeamByTeamName(al.get(i));
            Log.w("team content", tm.getPlayer(0).toString());
        }
        t1 = db.getTeamByTeamName(al.get(1));

        Record r1 = new Record(RecordType.ACTION, t1.playerList.get(1).toString(), ActionType.ATTACK, ActionResultType.ATTEMPT);
        Record r2 = new Record(RecordType.TEAM_FAULT);
        Game gg = new Game(t1, 0 ,0);
        gg.addRecord(r1);
        gg.addRecord(r2);
        Match mm = new Match(1, "oppo1", 25, 25, 15, t1, "8787", 0, 1);
        mm.addGame(gg);
        db.writeMatch(mm);
    }
}
