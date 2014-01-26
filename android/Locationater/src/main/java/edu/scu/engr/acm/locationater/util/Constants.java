package edu.scu.engr.acm.locationater.util;

import android.graphics.Typeface;

/**
 * Created: vincente on 1/25/14.
 */
public class Constants {
    public static final boolean DEBUGGING = true;

    public static final int TIME_DELAY = 1000 * 60 * 1; //Millis * seconds * minutes

    public static final String JSON_LOCATION = "json_location";

    public static final String TIME = "time";
    public static final String USER_PASSWORD = "pass";
    public static final String USER_EMAIL = "email";
    public static final String MESSAGE = "message";
    public static final String DISTANCE = "dist";
    public static final String ID_EVENT = "id_event";
    public static final String USER_NAME = "Name";
    public static final String LATITUDE = "Lat";
    public static final String LONGITUTDE = "Lon";

    public static final String ID_NOTIFY = "notify_type";

    //http://ec2-54-193-61-57.us-west-1.compute.amazonaws.com/cgi-bin/server/AddUser.py?E=none@all.com&P=shithead&F=Billy&L=Bob
    public static final String URL_BASE = "http://ec2-54-193-61-57.us-west-1.compute.amazonaws.com/cgi-bin/server/";
    public static final String URL_ADD_USER = URL_BASE + "/AddUser.py";
    public static final String URL_ADD_EVENT = URL_BASE + "/AddEvent.py";
    public static final String URL_ARG_PASSWORD = "P";
    public static final String URL_ARG_EMAIL = "E";
    public static final String URL_ARG_F_NAME = "F";
    public static final String URL_ARG_L_NAME = "L";

}
