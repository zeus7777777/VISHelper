package com.mixerbox.hackathon.vis;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by zeus on 2016/8/25.
 */
public class DB {
    Context ctx;

    public DB(Context _ctx)
    {
        ctx = _ctx;
    }

    public ArrayList<String> getTeamList()
    {
        DBHelper hh = new DBHelper(ctx);
        SQLiteDatabase db = hh.getReadableDatabase();

        String[] proj = {DBPlayerBaseColumn.COLUMN_NAME_team};
        Cursor cs = db.query(true, DBPlayerBaseColumn.TABLE_NAME, proj, null, null, null, null, null, null);
        ArrayList<String> al = new ArrayList<>();
        if(cs.getCount()==0)
        {
            return al;
        }
        cs.moveToFirst();
        for(int i=0;i<cs.getCount();i++)
        {
            al.add(cs.getString(0));
            cs.moveToNext();
        }
        return al;
    }

    public Team getTeamByTeamName(String name)
    {
        DBHelper hh = new DBHelper(ctx);
        SQLiteDatabase db = hh.getReadableDatabase();

        String[] proj = {DBPlayerBaseColumn.COLUMN_NAME_name, DBPlayerBaseColumn.COLUMN_NAME_nickname,
            DBPlayerBaseColumn.COLUMN_NAME_position, DBPlayerBaseColumn.COLUMN_NAME_number,
            DBPlayerBaseColumn.COLUMN_NAME_team};
        String where = DBPlayerBaseColumn.COLUMN_NAME_team  + " = ?";
        String[] whereargs = {name};
        Cursor cs = db.query(DBPlayerBaseColumn.TABLE_NAME, proj, where, whereargs, null, null, null);
        cs.moveToFirst();
        ArrayList<Player> al = new ArrayList<>();
        for(int i=0;i<cs.getCount();i++)
        {
            al.add(new Player(cs.getString(0), cs.getString(1), Position.valueOf(cs.getString(2)), cs.getString(3)));
            cs.moveToNext();
        }
        return new Team(name, al);
    }

    public void writePlayers(Team team)
    {
        // check if exist, delete old
        if(chk_team_exist(team.teamName))
        {
            deleteTeam(team.teamName);
        }

        DBHelper hh = new DBHelper(ctx);
        SQLiteDatabase db = hh.getWritableDatabase();

        int i;
        for(i=0;i<team.playerList.size();i++)
        {
            Player pp = team.playerList.get(i);
            ContentValues cv = new ContentValues();
            cv.put(DBPlayerBaseColumn.COLUMN_NAME_name, pp.name);
            cv.put(DBPlayerBaseColumn.COLUMN_NAME_nickname, pp.nickName);
            cv.put(DBPlayerBaseColumn.COLUMN_NAME_position, pp.position.toString());
            cv.put(DBPlayerBaseColumn.COLUMN_NAME_number , pp.number);
            cv.put(DBPlayerBaseColumn.COLUMN_NAME_team, team.teamName);
            db.insert(DBPlayerBaseColumn.TABLE_NAME, DBPlayerBaseColumn.COLUMN_NAME_name, cv);
        }
    }

    public boolean chk_team_exist(String tname)
    {
        DBHelper hh = new DBHelper(ctx);
        SQLiteDatabase db = hh.getReadableDatabase();
        String[] proj = {DBPlayerBaseColumn._ID};
        String where = DBPlayerBaseColumn.COLUMN_NAME_team + " = ?";
        String[] whereargs = {tname};
        Cursor cs = db.query(DBPlayerBaseColumn.TABLE_NAME, proj,where, whereargs,null, null, null);
        return cs.getCount()>0;
    }

    public void deleteTeam(String name)
    {
        DBHelper hh = new DBHelper(ctx);
        SQLiteDatabase db = hh.getWritableDatabase();
        String where = DBPlayerBaseColumn.COLUMN_NAME_team + " = ?";
        String[] whereargs = {name};
        db.delete(DBPlayerBaseColumn.TABLE_NAME, where, whereargs);
    }

    // ========= Match operation ==========


    private int get_last_match_id(String time)
    {
        DBHelper hh = new DBHelper(ctx);
        SQLiteDatabase db = hh.getReadableDatabase();

        String[] proj = {DBMatchBaseColumn._ID};
        String where = DBMatchBaseColumn.COLUMN_NAME_matchtime + " = ?";
        String[] whereargs = {time};
        Cursor cs = db.query(DBMatchBaseColumn.TABLE_NAME, proj, where, whereargs, null, null, null);
        cs.moveToFirst();
        return cs.getInt(0);
    }

    public void writeMatch(Match mm)
    {
        DBHelper hh = new DBHelper(ctx);
        SQLiteDatabase db = hh.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(DBMatchBaseColumn.COLUMN_NAME_matchtime, mm.dateTime);
        cv.put(DBMatchBaseColumn.COLUMN_NAME_teamname, mm.myTeam.teamName);
        cv.put(DBMatchBaseColumn.COLUMN_NAME_opponame, mm.oppositeTeamName);
        cv.put(DBMatchBaseColumn.COLUMN_NAME_win, mm.myGamePoint);
        cv.put(DBMatchBaseColumn.COLUMN_NAME_lose, mm.oppositeGamePoint);
        cv.put(DBMatchBaseColumn.COLUMN_NAME_note, mm.note);
        cv.put(DBMatchBaseColumn.COLUMN_NAME_numgame, mm.numGame);
        cv.put(DBMatchBaseColumn.COLUMN_NAME_winscore, mm.winScore);
        cv.put(DBMatchBaseColumn.COLUMN_NAME_maxscore, mm.maxScore);
        cv.put(DBMatchBaseColumn.COLUMN_NAME_lastgamewinscore, mm.lastGameWinScore);

        db.insert(DBMatchBaseColumn.TABLE_NAME, DBMatchBaseColumn.COLUMN_NAME_matchtime, cv);

        for(int i=0;i<mm.games.size();i++)
        {
            Game gg = mm.games.get(i);
            for(int j=0;j<gg.recordList.size();j++)
            {
                Record rr = gg.recordList.get(j);
                cv = new ContentValues();
                cv.put(DBRecordBaseColumn.COLUMN_NAME_matchid, get_last_match_id(mm.dateTime));
                cv.put(DBRecordBaseColumn.COLUMN_NAME_gameid, i);
                cv.put(DBRecordBaseColumn.COLUMN_NAME_type, rr.recordType.toString());
                if(rr.recordType==RecordType.ACTION)
                {
                    cv.put(DBRecordBaseColumn.COLUMN_NAME_param1, rr.playerName);
                    cv.put(DBRecordBaseColumn.COLUMN_NAME_param2, rr.actionType.toString());
                    cv.put(DBRecordBaseColumn.COLUMN_NAME_param3, rr.actionResultType.toString());
                }
                else if(rr.recordType==RecordType.SUBSTITUTION || rr.recordType==RecordType.TEAM_FAULT ||
                    rr.recordType==RecordType.OPPONENT_ERROR)
                {
                    cv.put(DBRecordBaseColumn.COLUMN_NAME_param1, rr.substitutionUp);
                    cv.put(DBRecordBaseColumn.COLUMN_NAME_param2, rr.substitutionDown);
                }
                else if(rr.recordType==RecordType.FOUL)
                {
                    cv.put(DBRecordBaseColumn.COLUMN_NAME_param1, rr.playerName);
                    cv.put(DBRecordBaseColumn.COLUMN_NAME_param2, rr.foulType.toString());
                }
                db.insert(DBRecordBaseColumn.TABLE_NAME, DBRecordBaseColumn.COLUMN_NAME_matchid, cv);
            }
        }
    }

    public ArrayList<MatchInfo> getMatchList()
    {
        DBHelper hh = new DBHelper(ctx);
        SQLiteDatabase db = hh.getReadableDatabase();

        String[] proj = {DBMatchBaseColumn.COLUMN_NAME_matchtime, DBMatchBaseColumn.COLUMN_NAME_opponame};
        Cursor cs = db.query(DBMatchBaseColumn.TABLE_NAME, proj, null, null, null, null, null);
        ArrayList<MatchInfo> ans = new ArrayList<>();
        if(cs.getCount()==0)
        {
            return ans;
        }
        cs.moveToFirst();
        for(int i=0;i<cs.getCount();i++)
        {
            ans.add(new MatchInfo(cs.getString(0), cs.getString(1)));
        }
        return ans;
    }

    public Match getMatchByTime(String time)
    {
        DBHelper hh = new DBHelper(ctx);
        SQLiteDatabase db = hh.getReadableDatabase();

        String[] proj = {DBMatchBaseColumn.COLUMN_NAME_matchtime, DBMatchBaseColumn.COLUMN_NAME_teamname,
            DBMatchBaseColumn.COLUMN_NAME_opponame, DBMatchBaseColumn.COLUMN_NAME_win,
            DBMatchBaseColumn.COLUMN_NAME_lose, DBMatchBaseColumn.COLUMN_NAME_note,
            DBMatchBaseColumn.COLUMN_NAME_numgame, DBMatchBaseColumn.COLUMN_NAME_winscore,
            DBMatchBaseColumn.COLUMN_NAME_maxscore , DBMatchBaseColumn.COLUMN_NAME_lastgamewinscore,
            DBMatchBaseColumn._ID};
        String where = DBMatchBaseColumn.COLUMN_NAME_matchtime + " = ?";
        String[] whereargs = {time};
        Cursor cs = db.query(DBMatchBaseColumn.TABLE_NAME, proj, where, whereargs, null, null, null);
        cs.moveToFirst();

        Team tmp_team = getTeamByTeamName(cs.getString(1));

        Match ans = new Match(cs.getInt(6), cs.getString(2), cs.getInt(7), cs.getInt(8), cs.getInt(9),
                tmp_team, cs.getString(0), cs.getInt(3), cs.getInt(4));
        ans.note = cs.getString(5);
        for(int i=0;i<ans.myGamePoint+ans.oppositeGamePoint;i++)
        {
            ans.addGame(new Game(tmp_team, 0, 0));
        }

        int tmp_id = cs.getInt(10);
        cs.close();

        proj = new String[]{DBRecordBaseColumn.COLUMN_NAME_matchid, DBRecordBaseColumn.COLUMN_NAME_gameid,
            DBRecordBaseColumn.COLUMN_NAME_type, DBRecordBaseColumn.COLUMN_NAME_param1,
            DBRecordBaseColumn.COLUMN_NAME_param2, DBRecordBaseColumn.COLUMN_NAME_param3};
        where = DBRecordBaseColumn.COLUMN_NAME_matchid + " = ?";
        whereargs = new String[]{tmp_id+""};
        cs = db.query(DBRecordBaseColumn.TABLE_NAME, proj, where, whereargs, null, null,null);
        cs.moveToFirst();

        for(int i = 0;i<cs.getCount();i++)
        {
            int game_id = cs.getInt(1);
            String record_type = cs.getString(2);
            if(record_type.equals(RecordType.TEAM_FAULT.toString()) || record_type.equals(RecordType.OPPONENT_ERROR.toString()))
            {
                ans.games.get(game_id).addRecord(new Record(RecordType.valueOf(record_type)));
            }
            else if(record_type.equals(RecordType.SUBSTITUTION.toString()))
            {
                ans.games.get(game_id).addRecord(new Record(RecordType.SUBSTITUTION, cs.getString(3), cs.getString(4)));

            }
            else if(record_type.equals(RecordType.FOUL.toString()))
            {
                ans.games.get(game_id).addRecord(new Record(RecordType.FOUL, cs.getString(3), cs.getString(4)));
            }
            else
            {
                ans.games.get(game_id).addRecord(new Record(RecordType.ACTION, cs.getString(3),
                    ActionType.valueOf( cs.getString(4)), ActionResultType.valueOf( cs.getString(5))));
            }
            cs.moveToNext();
        }
        cs.close();
        for (int i=0;i<ans.games.size();i++)
        {
            set_game_scores(ans.games.get(i));
        }
        return ans;
    }

    private  void set_game_scores(Game gg)
    {
        for(int i=0;i<gg.recordList.size();i++)
        {
            if(gg.recordList.get(i).getEffect()==1)
                gg.myScore++;
            else if(gg.recordList.get(i).getEffect()==-1)
                gg.oppositeScore++;
        }
    }
}
