package edu.scu.engr.acm.locationater.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by lauren on 1/25/14.
 */
public class FriendsTable extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "friends";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_NODEID = "NodeID";
    public static final String COLUMN_FIRST = "firstname";
    public static final String COLUMN_LAST = "lastname";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_MESSAGE = "message";

    private static final String DATABASE_NAME = "friends.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_NAME + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_NODEID + " integer, "
            + COLUMN_FIRST + " text not null,"
            + COLUMN_LAST + " text not null,"
            + COLUMN_EMAIL + " text not null,"
            + COLUMN_MESSAGE + " text not null"
            + ");";


    public FriendsTable (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(FriendsTable.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

}
