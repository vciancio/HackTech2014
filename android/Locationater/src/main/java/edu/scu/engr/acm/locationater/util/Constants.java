package edu.scu.engr.acm.locationater.util;

/**
 * Created: vincente on 1/25/14.
 */
public class Constants {
    public static final boolean DEBUGGING = true;

    public static final int TIME_DELAY = 1000 * 60 * 2; //Millis * seconds * minutes

    public static final String JSON_LOCATION = "json_location";

    public static final String TIME = "time";
    public static final String USER_EMAIL = "email";
    public static final String LATITUDE = "latitude";
    public static final String LONGITUTDE = "longitude";
    public static final String URL_BASE = "http://goatsgonewild.net";
    public static final String URL_POST_SEND = URL_BASE + "/sendLocation";
    public static final String URL_GET_FRIEND_LOCATION = URL_BASE + "/getFriendLocation";
}
