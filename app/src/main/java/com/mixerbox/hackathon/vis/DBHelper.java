package com.mixerbox.hackathon.vis;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by zeus on 2016/8/25.
 */
public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "Volley", null ,2);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DBMatchBaseColumn.Match_TABLE_CREATE);
        sqLiteDatabase.execSQL(DBPlayerBaseColumn.Player_TABLE_CREATE);
        sqLiteDatabase.execSQL(DBRecordBaseColumn.Record_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
