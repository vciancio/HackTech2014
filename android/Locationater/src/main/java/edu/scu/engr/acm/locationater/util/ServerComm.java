package edu.scu.engr.acm.locationater.util;

import android.content.Context;
import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created: vincente on 1/25/14.
 */
public class ServerComm {

    public static boolean verifyUser(final String email, final String password) {
        // TODO: Implement a method to verify the user to log-in
        return true;
    }

    public static boolean createUser(final String email, final String password,
                                     final String firstName, final String lastName) {
        // TODO: Implement a method to verify the user to log-in

        return true;
    }

    public static JSONArray getFriends(final String email, final String password) {

        // TODO: Implement method which returns a JSONArray of User Values

        return null;
    }

    public static boolean sendLocation(final double latitude, final double longitude, long millis) {

        List<NameValuePair> url_args = new ArrayList<NameValuePair>();
        url_args.add(new BasicNameValuePair(Constants.LATITUDE, Double.toString(latitude)));
        url_args.add(new BasicNameValuePair(Constants.LONGITUTDE, Double.toString(longitude)));
        url_args.add(new BasicNameValuePair(Constants.USER_EMAIL, "vciancio@socaldevs.com"));
        url_args.add(new BasicNameValuePair(Constants.TIME, Long.toString(millis)));
        Object[] args = {Constants.URL_POST_SEND, url_args};
//        sendInfo.execute(args);
        return true;
    }

    //First Object is the URL as a string. The Second is a List<NameValuePair> for the url arguments
    private static AsyncTask<Object, Object, Object> sendInfo = new AsyncTask<Object, Object, Object>() {
        HttpClient httpClient;
        HttpPost httpPost;

        @Override
        protected Object doInBackground(Object... params) {
            httpClient = new DefaultHttpClient();
            httpPost = new HttpPost((String) params[0]);
            try {
                httpPost.setEntity(new UrlEncodedFormEntity((List<NameValuePair>) params[1]));
                HttpResponse response = httpClient.execute(httpPost);
                return new JSONObject(response.getEntity().getContent().toString());
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
    };
}
