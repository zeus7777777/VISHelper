package com.mixerbox.hackathon.vis;

import android.provider.BaseColumns;

/**
 * Created by zeus on 2016/8/25.
 */
public class DBPlayerBaseColumn implements BaseColumns{
    public static final String TABLE_NAME = "Player",
            COLUMN_NAME_name = "name",
            COLUMN_NAME_nickname = "nickname",
            COLUMN_NAME_position = "position",
            COLUMN_NAME_number = "number",
            COLUMN_NAME_team = "team";
    public static final String Player_TABLE_CREATE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("+
            _ID + " integer primary key, "+
            COLUMN_NAME_name + " string , "+
            COLUMN_NAME_nickname + " string, "+
            COLUMN_NAME_position + " string, "+
            COLUMN_NAME_number + " string, "+
            COLUMN_NAME_team + " integer "+
            ")";
}
