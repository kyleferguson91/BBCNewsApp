package com.example.androidlab;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass that allows the user to change their username
 * and provides a help section.
 * Use the {@link settings#newInstance} factory method to create an instance of this fragment.
 */
public class settings extends Fragment {

    // Parameter arguments for fragment initialization
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // Parameters for the fragment
    private String mParam1;
    private String mParam2;

    /**
     * Required empty public constructor for the fragment.
     */
    public settings() {
        // Required empty public constructor
    }

    /**
     * Factory method to create a new instance of this fragment with the provided parameters.
     *
     * @param param1 The first parameter.
     * @param param2 The second parameter.
     * @return A new instance of fragment settings.
     */
    public static settings newInstance(String param1, String param2) {
        settings fragment = new settings();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    /**
     * Inflates the layout for this fragment and sets up the UI components.
     *
     * @param inflater           The LayoutInflater object to inflate the layout.
     * @param container          The parent view that the fragment's UI should be attached to.
     * @param savedInstanceState A Bundle containing the fragment's previously saved state.
     * @return The inflated view for the fragment.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        // Initialize UI components
        Button username = rootView.findViewById(R.id.setchangeusername);
        Button help = rootView.findViewById(R.id.helpbuttonsettingspage);
        TextView helptext = rootView.findViewById(R.id.helptextsettingspage);
        Button usernamechangesubmit = rootView.findViewById(R.id.submitusernamechangebutton);
        EditText usernamestext = rootView.findViewById(R.id.usernametextsettingspage);

        // Set click listener for the "Change Username" button
        username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("clicked username settings page");
                // Show the username change input and hide the help text
                usernamechangesubmit.setVisibility(rootView.VISIBLE);
                usernamestext.setVisibility(View.VISIBLE);
                helptext.setVisibility(View.GONE);
            }
        });

        // Create Shared object for storing and retrieving data
        Shared shared = new Shared(getContext());

        // Set click listener for the "Submit Username" button
        usernamechangesubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("clicked username submit button");
                // Create new User object with the entered username
                User user = new User(usernamestext.getText().toString());

                // Hide the username input components
                usernamechangesubmit.setVisibility(rootView.GONE);
                usernamestext.setVisibility(View.GONE);

                // Store the new username in SharedPreferences
                shared.storeData(usernamestext.getText().toString());

                // Log the retrieved username
                System.out.println(shared.retrieveUsername());
            }
        });

        // Set click listener for the "Help" button
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("clicked help settings page");

                // Toggle visibility of help text
                if (helptext.getVisibility() == View.VISIBLE) {
                    helptext.setVisibility(View.GONE);
                } else {
                    helptext.setVisibility(View.VISIBLE);
                }
            }
        });

        return rootView;
    }
}
