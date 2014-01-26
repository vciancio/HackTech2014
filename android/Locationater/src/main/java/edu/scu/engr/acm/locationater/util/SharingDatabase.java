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
public class SharingDatabase {

    // Database fields
    private SQLiteDatabase database;
    private SharingTable sharingTable;
    private String[] sharingColumns = {
            SharingTable.COLUMN_NODEID,
            SharingTable.COLUMN_FIRST,
            SharingTable.COLUMN_LAST,
            SharingTable.COLUMN_EMAIL,
            SharingTable.COLUMN_MESSAGE,
            SharingTable.COLUMN_DISTANCE,
            SharingTable.COLUMN_VELOCITY,
            SharingTable.COLUMN_ETA
    };

    public SharingDatabase(Context context) {
        sharingTable = new SharingTable(context);
    }

    public void open() throws SQLException {
        database = sharingTable.getWritableDatabase();
    }

    public void close() {
        sharingTable.close();
    }

    public Sharing createSharing(String sharing) {
        ContentValues values = new ContentValues();
        values.put(SharingTable.COLUMN_FIRST, "first_name_test");
        values.put(SharingTable.COLUMN_LAST, "last_name_test");
        values.put(SharingTable.COLUMN_EMAIL, "shithead@fun.com");
        values.put(SharingTable.COLUMN_MESSAGE, "I like potatoes!!");
        long insertId = database.insert(SharingTable.TABLE_NAME, null,
                values);
        Cursor cursor = database.query(SharingTable.TABLE_NAME,
                sharingColumns, sharingTable.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Sharing newSharing = cursorToSharing(cursor);
        cursor.close();
        return newSharing;
    }

    public List<Sharing> getAllSharing() {
        List<Sharing> sharings = new ArrayList<Sharing>();

        Cursor cursor = database.query(SharingTable.TABLE_NAME,
                sharingColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Sharing sharing = cursorToSharing(cursor);
            sharings.add(sharing);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return sharings;
    }

    private Sharing cursorToSharing(Cursor cursor) {
        Sharing sharing = new Sharing();
        sharing.setId(cursor.getInt(cursor.getColumnIndex(SharingTable.COLUMN_ID)));
        sharing.setnodeid(cursor.getLong(cursor.getColumnIndex(SharingTable.COLUMN_NODEID)));
        sharing.setfirstname(cursor.getString(cursor.getColumnIndex(SharingTable.COLUMN_FIRST)));
        sharing.setlastname(cursor.getString(cursor.getColumnIndex(SharingTable.COLUMN_LAST)));
        sharing.setEmail(cursor.getString(cursor.getColumnIndex(SharingTable.COLUMN_EMAIL)));
        sharing.setMessage(cursor.getString(cursor.getColumnIndex(SharingTable.COLUMN_MESSAGE)));
        sharing.setDistance(cursor.getInt(cursor.getColumnIndex(SharingTable.COLUMN_DISTANCE)));
        sharing.setVelocity(cursor.getInt(cursor.getColumnIndex(SharingTable.COLUMN_VELOCITY)));
        sharing.setETA(cursor.getInt(cursor.getColumnIndex(SharingTable.COLUMN_ETA)));
        return sharing;
    }



}
