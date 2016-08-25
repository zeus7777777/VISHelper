package com.mixerbox.hackathon.vis;

import android.provider.BaseColumns;

/**
 * Created by zeus on 2016/8/25.
 */
public class DBRecordBaseColumn implements BaseColumns {
    public static final String TABLE_NAME = "Record",
            COLUMN_NAME_matchid = "matchid",
            COLUMN_NAME_gameid = "gameid",
            COLUMN_NAME_type = "type",
            COLUMN_NAME_param1 = "param1",
            COLUMN_NAME_param2 = "param2",
            COLUMN_NAME_param3 = "param3";
    public static final String Record_TABLE_CREATE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("+
            _ID + " integer primary key, "+
            COLUMN_NAME_matchid + " integer , "+
            COLUMN_NAME_gameid + " integer, "+
            COLUMN_NAME_type + " string, "+
            COLUMN_NAME_param1 + " string, "+
            COLUMN_NAME_param2 + " string, "+
            COLUMN_NAME_param3 + " string" +
            ")";
}
