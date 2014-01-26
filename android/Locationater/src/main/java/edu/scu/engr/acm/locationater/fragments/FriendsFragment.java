package edu.scu.engr.acm.locationater.fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import edu.scu.engr.acm.locationater.R;
import edu.scu.engr.acm.locationater.util.Friend;
import edu.scu.engr.acm.locationater.util.ServerComm;

/**
 * Created: vincente on 1/25/14.
 */
public class FriendsFragment extends Fragment {

    private static FriendsFragment instance = null;
    private ArrayList<Friend> friendArrayList = null;
    private Button addFriendButton = null;
    private ListView friendListView = null;
    private FriendArrayAdapter friendArrayAdapter = null;

    public static FriendsFragment getInstance() {
        if (instance == null)
            instance = new FriendsFragment();
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_friends, container, false);

        friendListView = (ListView) rootView.findViewById(R.id.listView);
        addFriendButton = (Button) rootView.findViewById(R.id.button_add_friend);
        friendArrayList = new ArrayList<Friend>();
        friendArrayAdapter = new FriendArrayAdapter(getActivity(), friendArrayList);

        // TODO: pull friends from the database asynchronously and update the friendArrayAdapter

        View.OnClickListener addFriendClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAddFriendDialog();
            }
        };

        addFriendButton.setOnClickListener(addFriendClickListener);

        return rootView;
    }

    private void createAddFriendDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

        alert.setTitle(R.string.dialog_friend_add_title);
        alert.setMessage(R.string.dialog_friend_add_message);

// Set an EditText view to get user input
        final EditText input = new EditText(getActivity());

        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String email = input.getText().toString();
                ServerComm comm = new ServerComm();
                if (comm.addFriend(email, getActivity())) {
                    Toast.makeText(getActivity(), R.string.dialog_friend_add_successful,
                            Toast.LENGTH_SHORT).show();
                    friendArrayAdapter.notifyDataSetChanged();
                } else
                    Toast.makeText(getActivity(), R.string.dialog_friend_add_fail,
                            Toast.LENGTH_SHORT).show();
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });

        alert.show();
    }

    class FriendArrayAdapter extends BaseAdapter {

        private ArrayList<Friend> friends;
        private Context context;

        public FriendArrayAdapter(Context context, List<Friend> objects) {

            this.friends = (ArrayList<Friend>) objects;
            this.context = context;
        }

        @Override
        public int getCount() {
            return friends.size();
        }

        @Override
        public Friend getItem(int position) {
            return friends.get(position);
        }

        @Override
        public long getItemId(int position) {
            return friends.get(position).getId();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;

            if (v == null)
                parent.inflate(context, android.R.layout.simple_list_item_1, null);

            TextView nameView = (TextView) v.findViewById(android.R.id.text1);
            if (nameView != null)
                nameView.setText(friends.get(position).getfirstname() + " " +
                        friends.get(position).getlastname());

            return v;
        }
    }
}