package edu.scu.engr.acm.locationater.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import edu.scu.engr.acm.locationater.R;
import edu.scu.engr.acm.locationater.services.LocationService;

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
        ListView currentSharedList = (ListView) rootView.findViewById(R.id.listView);
        ArrayList<String[]> currentSharedArray = new ArrayList<String[]>();
        String[] tempArray = {"Vincente Ciancio", ".2", "5"};
        currentSharedArray.add(tempArray);

        SharedViewAdapter mAdapter = new SharedViewAdapter(currentSharedArray, getActivity());
        currentSharedList.setAdapter(mAdapter);

        //Get the Latitude and Longitude from JSON
        sp = PreferenceManager.getDefaultSharedPreferences(this.getActivity());

        return rootView;
    }

    class SharedViewAdapter extends BaseAdapter {

        ArrayList<String[]> items;
        Context context;

        public SharedViewAdapter(ArrayList<String[]> items, Context context) {
            this.items = items;
            this.context = context;
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;

            if (v == null) {
                v = parent.inflate(context, R.layout.list_view_row_item, null);
            }

            TextView nameView = (TextView) v.findViewById(R.id.name);
            TextView distanceView = (TextView) v.findViewById(R.id.distance);
            TextView timeView = (TextView) v.findViewById(R.id.time);

            if (nameView != null)
                nameView.setText(items.get(position)[0]);
            if (distanceView != null)
                distanceView.setText(String.format(getResources().getString(R.string.format_distance), items.get(position)[1]));
            if (timeView != null)
                timeView.setText(String.format(getResources().getString(R.string.format_time), items.get(position)[2]));
            return v;
        }
    }
}