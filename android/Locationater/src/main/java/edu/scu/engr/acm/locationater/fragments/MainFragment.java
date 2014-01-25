package edu.scu.engr.acm.locationater.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.scu.engr.acm.locationater.R;
import edu.scu.engr.acm.locationater.services.LocationService;
import edu.scu.engr.acm.locationater.util.Constants;

/**
 * Created: vincente on 1/24/14.
 */
public class MainFragment extends Fragment {

    private static MainFragment instance = null;
    private LocationService locationService;
    private SharedPreferences sp;

    public static MainFragment getInstance() {
        if (instance == null)
            instance = new MainFragment();
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        TextView textView = (TextView) rootView.findViewById(R.id.section_label);


        //Get the Latitude and Longitude from JSON
        sp = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
        try {
            JSONObject locationJson = new JSONObject(sp.getString(Constants.JSON_LOCATION, "{latitude:0, longitude:0}"));
            textView.setText("Latitude: " + locationJson.get(Constants.LATITUDE) +
                    "\nLongitude: " + locationJson.get(Constants.LONGITUTDE));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return rootView;
    }







}