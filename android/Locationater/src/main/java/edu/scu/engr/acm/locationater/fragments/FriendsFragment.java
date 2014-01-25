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
public class FriendsFragment extends Fragment {

    private static FriendsFragment instance = null;

    public static FriendsFragment getInstance() {
        if (instance == null)
            instance = new FriendsFragment();
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_friends, container, false);
        return rootView;
    }
}