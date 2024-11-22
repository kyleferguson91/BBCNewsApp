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
 * A simple {@link Fragment} subclass.
 * Use the {@link settings#newInstance} factory method to
 * create an instance of this fragment.
 */
public class settings extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public settings() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment settings.
     */
    // TODO: Rename and change types and number of parameters
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        Button username = rootView.findViewById(R.id.setchangeusername);
        Button help = rootView.findViewById(R.id.helpbuttonsettingspage);
        TextView helptext = rootView.findViewById(R.id.helptextsettingspage);
        Button usernamechangesubmit = rootView.findViewById(R.id.submitusernamechangebutton);
        EditText usernamestext = rootView.findViewById(R.id.usernametextsettingspage);

        username.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                System.out.println("clicked username settings page");
                //show the username settings pane
                usernamechangesubmit.setVisibility(rootView.VISIBLE);
                usernamestext.setVisibility(View.VISIBLE);
                helptext.setVisibility(View.GONE);
            }
        });
        Shared shared = new Shared(getContext());
        usernamechangesubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                System.out.println("clicked username submit button");
              //Create new user object!
                User user = new User(usernamestext.getText().toString());





                usernamechangesubmit.setVisibility(rootView.GONE);
                usernamestext.setVisibility(View.GONE);


                shared.storeData(usernamestext.getText().toString());

                System.out.println(shared.retrieveUsername());

            }
        });

        help.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                System.out.println("clicked help settings page");

                if (helptext.getVisibility() == View.VISIBLE)
                {
                    helptext.setVisibility(View.GONE);
                }
                else
                {
                    helptext.setVisibility(View.VISIBLE);
                }

            }
        });


        return rootView;
    }
}