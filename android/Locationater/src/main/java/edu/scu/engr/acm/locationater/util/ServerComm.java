package edu.scu.engr.acm.locationater.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created: vincente on 1/25/14.
 */
public class ServerComm {

    public ServerComm() {

    }

    public boolean verifyUser(final String email, final String password, Context context) {
        // TODO: Implement a method to verify the user to log-in

        List<NameValuePair> url_args = new ArrayList<NameValuePair>();
        url_args.add(new BasicNameValuePair(Constants.URL_ARG_EMAIL, email));
        url_args.add(new BasicNameValuePair(Constants.URL_ARG_PASSWORD, password));

        Object[] params = {Constants.URL_LOGIN, url_args};
        SendInfo sendInfo = new SendInfo();
        sendInfo.execute(params);
        try {
            JSONObject response = new JSONObject(String.valueOf(sendInfo.get()));
            if (!response.getBoolean(Constants.SUCCESS)) {
                Log.i("ServerComm:verifyUser", "Failed with an error number of: #" +
                        response.getJSONObject(Constants.ERROR));
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public String hasEvent(Context context) {

        List<NameValuePair> url_args = new ArrayList<NameValuePair>();
        SharedPreferences sp = context.getSharedPreferences("cred", Context.MODE_PRIVATE);
        url_args.add(new BasicNameValuePair(Constants.USER_NODE_ID, String.valueOf(sp.getInt(Constants.USER_NODE_ID, 0))));
        url_args.add(new BasicNameValuePair(Constants.URL_ARG_PASSWORD, sp.getString(Constants.USER_PASSWORD, "")));

        Object[] args = {Constants.URL_GET_EVENTS, url_args};

        String response = null;
        SendInfo sendInfo = new SendInfo();
        sendInfo.execute(args);

        try {
            response = String.valueOf(sendInfo.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (Constants.DEBUGGING)
            Log.i("ServerComm", "Passing: " + response);
        return response;
    }

    public boolean createUser(final String email, final String password,
                              final String firstName, final String lastName, Context context) {
        // TODO: Implement a method to verify the user to log-in
        List<NameValuePair> url_args = new ArrayList<NameValuePair>();
        url_args.add(new BasicNameValuePair(Constants.URL_ARG_EMAIL, email));
        url_args.add(new BasicNameValuePair(Constants.URL_ARG_PASSWORD, password));
        url_args.add(new BasicNameValuePair(Constants.URL_ARG_F_NAME, firstName));
        url_args.add(new BasicNameValuePair(Constants.URL_ARG_L_NAME, lastName));

        Object[] params = {Constants.URL_ADD_USER, url_args};
        SendInfo sendInfo = new SendInfo();
        sendInfo.execute(params);
        JSONObject jsonResponse = null;

        try {
            jsonResponse = new JSONObject(String.valueOf(sendInfo.get()));
            if (jsonResponse.getBoolean(Constants.SUCCESS)) {
                SharedPreferences sp = context.getSharedPreferences("cred", Context.MODE_PRIVATE);
                sp.edit().putInt(Constants.USER_NODE_ID, jsonResponse.getInt(Constants.USER_NODE_ID)).commit();
                return true;
            } else {
                Log.i("ServerConn:createUser", "Error Creating User in Database: #" +
                        jsonResponse.getInt(Constants.ERROR));
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public JSONArray getFriends(final String email, final String password) {

        // TODO: Implement method which returns a JSONArray of User Values
        List<NameValuePair> url_args = new ArrayList<NameValuePair>();

        return null;
    }

    //Confirm the event so we don't keep on getting notifications about it.
    public void confirmEvent(int eventId, Context context) {
        SharedPreferences sp = context.getSharedPreferences("cred", Context.MODE_PRIVATE);
        List<NameValuePair> url_args = new ArrayList<NameValuePair>();
        url_args.add(new BasicNameValuePair(Constants.EVENT_NODE_ID, String.valueOf(eventId)));
        url_args.add(new BasicNameValuePair(Constants.URL_ARG_PASSWORD, sp.getString(Constants.USER_PASSWORD, "")));
        url_args.add(new BasicNameValuePair(Constants.USER_NODE_ID, String.valueOf(sp.getInt(Constants.USER_NODE_ID, 0))));

        //Get the less-secure stuff like current position, etc.
        sp = PreferenceManager.getDefaultSharedPreferences(context);
        try {
            JSONObject locationJSON = new JSONObject(sp.getString(Constants.JSON_LOCATION, ""));
            url_args.add(new BasicNameValuePair(Constants.LATITUDE, String.valueOf(locationJSON.get(Constants.LATITUDE))));
            url_args.add(new BasicNameValuePair(Constants.LONGITUTDE, String.valueOf(locationJSON.get(Constants.LONGITUTDE))));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        url_args.add(new BasicNameValuePair(Constants.TIME, String.valueOf(System.currentTimeMillis())));
        Object[] args = {Constants.URL_CONFIRM_EVENT, url_args};
        SendInfo sendInfo = new SendInfo();
        sendInfo.execute(args);
    }

    //Returns distance as an integer
    public int sendLocation(final double latitude, final double longitude, long millis, int eventId, Context context) {
        SharedPreferences sp = context.getSharedPreferences("cred", Context.MODE_PRIVATE);
        List<NameValuePair> url_args = new ArrayList<NameValuePair>();
        url_args.add(new BasicNameValuePair(Constants.TIME, Long.toString(millis)));
        url_args.add(new BasicNameValuePair(Constants.EVENT_NODE_ID, Integer.toString(eventId)));
        url_args.add(new BasicNameValuePair(Constants.LATITUDE, Double.toString(latitude)));
        url_args.add(new BasicNameValuePair(Constants.LONGITUTDE, Double.toString(longitude)));
        url_args.add(new BasicNameValuePair(Constants.URL_ARG_PASSWORD, sp.getString(Constants.USER_PASSWORD, "")));
        url_args.add(new BasicNameValuePair(Constants.USER_NODE_ID, String.valueOf(sp.getInt(Constants.USER_NODE_ID, 0))));
        Object[] args = {Constants.URL_SEND_LOCATION, url_args};
        SendInfo sendInfo = new SendInfo();
        sendInfo.execute(args);

        JSONObject response = null;
        int distance = 0;

        try {
            response = new JSONObject(String.valueOf(sendInfo.get()));
            distance = response.getInt(Constants.DISTANCE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return distance;
    }

    public boolean addFriend(String email, Context context) {
        SendInfo sendInfo = new SendInfo();
        SharedPreferences sp = context.getSharedPreferences("cred", Context.MODE_PRIVATE);
        List<NameValuePair> url_args = new ArrayList<NameValuePair>();
        url_args.add(new BasicNameValuePair(Constants.URL_ARG_EMAIL, email));
        url_args.add(new BasicNameValuePair(Constants.USER_NODE_ID,
                String.valueOf(sp.getInt(Constants.USER_NODE_ID, 0))));
        url_args.add(new BasicNameValuePair(Constants.URL_ARG_PASSWORD,
                sp.getString(Constants.USER_PASSWORD, "")));

        Object[] args = {Constants.URL_ADD_FRIEND, url_args};
        sendInfo.execute(args);

        JSONObject response = null;

        try {
            response = new JSONObject(String.valueOf(sendInfo.get()));
            if (!response.getBoolean(Constants.SUCCESS)) {
                if (Constants.DEBUGGING)
                    Log.i("ServerConn:addFriend", "Failed with error: #" +
                            response.getInt(Constants.ERROR));
                return false;
            }
            // TODO: Add new fried to our database either in here or in the Friends Fragment
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        // TODO: Add Friend to database here
        FriendsDatabase friendsData = new FriendsDatabase(context);
        friendsData.open();


        return true;
    }

    //First Object is the URL as a string. The Second is a List<NameValuePair> for the url arguments
    private class SendInfo extends AsyncTask<Object, Object, Object> {
        HttpClient httpClient;
        HttpPost httpPost;

        @Override
        protected Object doInBackground(Object... params) {
            httpClient = new DefaultHttpClient();
            httpPost = new HttpPost((String) params[0]);
            try {
                httpPost.setEntity(new UrlEncodedFormEntity((List<NameValuePair>) params[1]));
                HttpResponse response = httpClient.execute(httpPost);
                return EntityUtils.toString(response.getEntity());
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
    }
}
