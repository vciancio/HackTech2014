package edu.scu.engr.acm.locationater.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lauren on 1/25/14.
 */
public class SharedDatabase {


    // Database fields
    private SQLiteDatabase database;
    private SharedTable sharedTable;
    private String[] sharedColumns = {
            SharedTable.COLUMN_NODEID,
            SharedTable.COLUMN_FIRST,
            SharedTable.COLUMN_LAST,
            SharedTable.COLUMN_EMAIL,
            SharedTable.COLUMN_MESSAGE,
            SharedTable.COLUMN_DISTANCE,
            SharedTable.COLUMN_VELOCITY,
            SharedTable.COLUMN_ETA
    };

    public SharedDatabase(Context context) {
        sharedTable = new SharedTable(context);
    }

    public void open() throws SQLException {
        database = sharedTable.getWritableDatabase();
    }

    public void close() {
        sharedTable.close();
    }

    public Shared createShared(String shared) {
        ContentValues values = new ContentValues();
        values.put(SharedTable.COLUMN_FIRST, "first_name_test");
        values.put(SharedTable.COLUMN_LAST, "last_name_test");
        values.put(SharedTable.COLUMN_EMAIL, "shithead@fun.com");
        values.put(SharedTable.COLUMN_MESSAGE, "I like potatoes!!");
        long insertId = database.insert(SharedTable.TABLE_NAME, null,
                values);
        Cursor cursor = database.query(SharedTable.TABLE_NAME,
                sharedColumns, sharedTable.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Shared newShared = cursorToShared(cursor);
        cursor.close();
        return newShared;
    }

    public List<Shared> getAllShared() {
        List<Shared> shareds = new ArrayList<Shared>();

        Cursor cursor = database.query(SharedTable.TABLE_NAME,
                sharedColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Shared shared = cursorToShared(cursor);
            shareds.add(shared);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return shareds;
    }

    private Shared cursorToShared(Cursor cursor) {
        Shared shared = new Shared();
        shared.setId(cursor.getInt(cursor.getColumnIndex(SharedTable.COLUMN_ID)));
        shared.setnodeid(cursor.getLong(cursor.getColumnIndex(SharedTable.COLUMN_NODEID)));
        shared.setfirstname(cursor.getString(cursor.getColumnIndex(SharedTable.COLUMN_FIRST)));
        shared.setlastname(cursor.getString(cursor.getColumnIndex(SharedTable.COLUMN_LAST)));
        shared.setEmail(cursor.getString(cursor.getColumnIndex(SharedTable.COLUMN_EMAIL)));
        shared.setMessage(cursor.getString(cursor.getColumnIndex(SharedTable.COLUMN_MESSAGE)));
        shared.setDistance(cursor.getInt(cursor.getColumnIndex(SharedTable.COLUMN_DISTANCE)));
        shared.setVelocity(cursor.getInt(cursor.getColumnIndex(SharedTable.COLUMN_VELOCITY)));
        shared.setETA(cursor.getInt(cursor.getColumnIndex(SharedTable.COLUMN_ETA)));
        return shared;
    }




}
