package edu.scu.engr.acm.locationater.util;

import android.graphics.Typeface;

/**
 * Created: vincente on 1/25/14.
 */
public class Constants {
    public static final boolean DEBUGGING = true;

    public static final int TIME_DELAY = 1000 * 10; //Millis * seconds * minutes

    public static final String JSON_LOCATION = "json_location";

    public static final String TIME = "time";
    public static final String ERROR = "Error";
    public static final String SUCCESS = "Success";
    public static final String USER_F_NAME = "first";
    public static final String USER_L_NAME = "last";
    public static final String USER_PASSWORD = "pass";
    public static final String USER_EMAIL = "email";
    public static final String MESSAGE = "message";
    public static final String DISTANCE = "dist";
    public static final String USER_NAME = "Name";

    public static final String LATITUDE = "Lat";

    public static final String LONGITUTDE = "Lon";
    public static final String ID_NOTIFY = "notify_type";
    //http://ec2-54-193-61-57.us-west-1.compute.amazonaws.com/cgi-bin/server/AddUser.py?E=none@all.com&P=shithead&F=Billy&L=Bob
    public static final String URL_BASE = "http://ec2-54-193-61-57.us-west-1.compute.amazonaws.com/cgi-bin/server";
    public static final String URL_ADD_USER = URL_BASE + "/AddUser.py";
    public static final String URL_LOGIN = URL_BASE + "/Login.py";
    public static final String URL_ADD_FRIEND = URL_BASE + "/AddFriend.py";
    public static final String URL_ADD_EVENT = URL_BASE + "/AddEvent.py";
    public static final String URL_GET_EVENTS = URL_BASE + "/CheckForEvents.py";
    public static final String URL_CONFIRM_EVENT = URL_BASE + "/ConfirmEvent.py";
    public static final String URL_SEND_LOCATION = URL_BASE + "/UpdateLocation.py";
    public static final String URL_ARG_PASSWORD = "P";
    public static final String URL_ARG_EMAIL = "E";
    public static final String URL_ARG_F_NAME = "F";
    public static final String URL_ARG_L_NAME = "L";
    public static final String USER_NODE_ID = "uID";
    public static final String EVENT_NODE_ID = "eID";
}
