package com.mixerbox.hackathon.vis;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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
                db.insert(DBRecordBaseColumn.TABLE_NAME, DBRecordBaseColumn.COLUMN_NAME_matchid, cv);
            }
        }
    }
}
