package edu.scu.engr.acm.locationater.fragments;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
        TextView textView = (TextView) rootView.findViewById(R.id.section_label);

        //Get the Latitude and Longitude from JSON
        sp = PreferenceManager.getDefaultSharedPreferences(this.getActivity());

        /* Testing Stuff
        Intent i = new Intent(this.getActivity(), NotificationService.class);
        Bundle extras = new Bundle();
        extras.putString(Constants.USER_NAME, "Vincente Ciancio");
        extras.putString(Constants.ID_EVENT, "3309");
        extras.putInt(Constants.ID_NOTIFY, 0);
        i.putExtras(extras);
        this.getActivity().startService(i);
        /* */

        return rootView;
    }
}