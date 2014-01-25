package edu.scu.engr.acm.locationater.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.scu.engr.acm.locationater.R;

/**
 * Created: vincente on 1/25/14.
 */
public class SharesFragment extends Fragment {

    private static SharesFragment instance = null;

    public static SharesFragment getInstance() {
        if (instance == null)
            instance = new SharesFragment();
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_shares, container, false);
        return rootView;
    }
}