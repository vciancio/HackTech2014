package edu.scu.engr.acm.locationater.util;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
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

    public boolean verifyUser(final String email, final String password) {
        // TODO: Implement a method to verify the user to log-in
        List<NameValuePair> url_args = new ArrayList<NameValuePair>();

        return true;
    }

    //First Object in the array is the event id (int), the second is the name of the person whom
    //Is requesting you to share your location (string)
    public Object[] hasEvent() {

        List<NameValuePair> url_args = new ArrayList<NameValuePair>();
        Object[] args = {Constants.URL_GET_EVENTS, url_args};
        JSONObject response = null;
        int eventid = -1;
        String name = "Missingno";
        SendInfo sendInfo = new SendInfo();
        sendInfo.execute(args);

        try {
            response = new JSONObject(String.valueOf(sendInfo.get()));
            eventid = response.getInt(Constants.ID_EVENT);
            name = response.getString(Constants.USER_NAME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Object[] passing = {String.valueOf(eventid), name};
        if (Constants.DEBUGGING)
            Log.i("ServerComm", "Passing: " + eventid + ", " + name);
        return passing;
    }

    public boolean createUser(final String email, final String password,
                              final String firstName, final String lastName) {
        // TODO: Implement a method to verify the user to log-in
        List<NameValuePair> url_args = new ArrayList<NameValuePair>();

        return true;
    }

    public JSONArray getFriends(final String email, final String password) {

        // TODO: Implement method which returns a JSONArray of User Values
        List<NameValuePair> url_args = new ArrayList<NameValuePair>();

        return null;
    }

    //Returns distance as an integer
    public int sendLocation(final double latitude, final double longitude, long millis, int eventId) {

        List<NameValuePair> url_args = new ArrayList<NameValuePair>();
        url_args.add(new BasicNameValuePair(Constants.LATITUDE, Double.toString(latitude)));
        url_args.add(new BasicNameValuePair(Constants.LONGITUTDE, Double.toString(longitude)));
        url_args.add(new BasicNameValuePair(Constants.USER_EMAIL, "vciancio@socaldevs.com"));
        url_args.add(new BasicNameValuePair(Constants.TIME, Long.toString(millis)));
        url_args.add(new BasicNameValuePair(Constants.ID_EVENT, Integer.toString(eventId)));
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

    public boolean addFriend(String email) {
        SendInfo sendInfo = new SendInfo();

        List<NameValuePair> url_args = new ArrayList<NameValuePair>();
        url_args.add(new BasicNameValuePair(Constants.URL_ARG_EMAIL, email));

        Object[] args = {Constants.URL_ADD_USER, url_args};
        sendInfo.execute(args);

        JSONObject response = null;
        try {
            response = new JSONObject(String.valueOf(sendInfo.get()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (response == null)
            return false;

        // TODO: Add Friend to database here
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
                return response.getEntity().getContent().toString();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
    }
}