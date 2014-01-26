package edu.scu.engr.acm.locationater.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import edu.scu.engr.acm.locationater.util.FriendsTable;

/**
 * Created by lauren on 1/25/14.
 */
public class FriendsDatabase {

    // Database fields
    private SQLiteDatabase database;
    private FriendsTable friendsTable;
    private String[] friendsColumns = {
            FriendsTable.COLUMN_NODEID,
            FriendsTable.COLUMN_FIRST,
            FriendsTable.COLUMN_LAST,
            FriendsTable.COLUMN_EMAIL,
            FriendsTable.COLUMN_MESSAGE
    };

    public FriendsDatabase(Context context) {
        friendsTable = new FriendsTable(context);
    }

    public void open() throws SQLException {
        database = friendsTable.getWritableDatabase();
    }

    public void close() {
        friendsTable.close();
    }

    public Friend createFriend(String friend) {
        ContentValues values = new ContentValues();
        values.put(FriendsTable.COLUMN_FIRST, "first_name_test");
        values.put(FriendsTable.COLUMN_LAST, "last_name_test");
        values.put(FriendsTable.COLUMN_EMAIL, "shithead@fun.com");
        values.put(FriendsTable.COLUMN_MESSAGE, "I like potatoes!!");
        long insertId = database.insert(FriendsTable.TABLE_NAME, null,
                values);
        Cursor cursor = database.query(FriendsTable.TABLE_NAME,
                friendsColumns, friendsTable.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Friend newFriend = cursorToFriend(cursor);
        cursor.close();
        return newFriend;
    }

    public List<Friend> getAllFriends() {
        List<Friend> friends = new ArrayList<Friend>();

        Cursor cursor = database.query(FriendsTable.TABLE_NAME,
                friendsColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Friend friend = cursorToFriend(cursor);
            friends.add(friend);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return friends;
    }

    private Friend cursorToFriend(Cursor cursor) {
        Friend friend = new Friend();
        friend.setId(cursor.getInt(0));
        friend.setnodeid(cursor.getLong(1));
        friend.setfirstname(cursor.getString(2));
        friend.setlastname(cursor.getString(cursor.getColumnIndex(FriendsTable.COLUMN_LAST)));
        friend.setEmail(cursor.getString(cursor.getColumnIndex(FriendsTable.COLUMN_EMAIL)));
        friend.setMessage(cursor.getString(cursor.getColumnIndex(FriendsTable.COLUMN_MESSAGE)));
        return friend;
    }



}
