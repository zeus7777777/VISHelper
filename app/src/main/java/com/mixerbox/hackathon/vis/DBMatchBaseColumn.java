package com.mixerbox.hackathon.vis;

import android.provider.BaseColumns;

/**
 * Created by zeus on 2016/8/25.
 */
public class DBMatchBaseColumn implements BaseColumns {
    public static final String TABLE_NAME = "Match",
        COLUMN_NAME_matchtime = "matchtime",
        COLUMN_NAME_teamname = "teamname",
        COLUMN_NAME_opponame = "opponame",
        COLUMN_NAME_win = "win",
        COLUMN_NAME_lose = "lose",
        COLUMN_NAME_note = "note",
        COLUMN_NAME_numgame = "numgame",
        COLUMN_NAME_winscore = "winscore",
        COLUMN_NAME_maxscore = "maxscore",
        COLUMN_NAME_lastgamewinscore = "lastgamewinscore";

    public static final String Match_TABLE_CREATE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("+
        _ID + " integer primary key, "+
        COLUMN_NAME_matchtime + " string , "+
        COLUMN_NAME_teamname + " string, "+
        COLUMN_NAME_opponame + " string, "+
        COLUMN_NAME_win + " integer, "+
        COLUMN_NAME_lose + " integer, "+
        COLUMN_NAME_note + " integer," +
        COLUMN_NAME_numgame + " integer, " +
        COLUMN_NAME_winscore + " integer, " +
        COLUMN_NAME_maxscore + " integer, " +
        COLUMN_NAME_lastgamewinscore + " integer"+
        ")";
}

