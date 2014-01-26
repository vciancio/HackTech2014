package edu.scu.engr.acm.locationater.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.scu.engr.acm.locationater.R;

/**
 * Created by lauren on 1/25/14.
 */
public class AddFriendFragment extends Fragment {

    private static AddFriendFragment instance = null;

    public static AddFriendFragment getInstance() {
        if (instance == null)
            instance = new AddFriendFragment();
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_friend, container, false);
        return rootView;
    }



}