package com.example.androidlab;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class EmptyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_empty);



        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String name = extras.getString("name");
            String height = extras.getString("height");
            String mass = extras.getString("mass");


            DetailsFragment detailsFragment = new DetailsFragment();
            Bundle fragmentArgs = new Bundle();
            fragmentArgs.putString("name", name);
            fragmentArgs.putString("height", height);
            fragmentArgs.putString("mass", mass);
            detailsFragment.setArguments(fragmentArgs);

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.framelayout, detailsFragment);
            fragmentTransaction.commit();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.framelayout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });





    } }

    public static class DetailsFragment extends Fragment {

        public DetailsFragment()
        {}
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_details, container, false);

            Bundle args = getArguments();
            if (args != null) {
                String name = args.getString("name");
                String height = args.getString("height");
                String mass = args.getString("mass");

                // Find TextViews and set their text
                TextView nameTextView = view.findViewById(R.id.textName);
                TextView heightTextView = view.findViewById(R.id.textHeight);
                TextView massTextView = view.findViewById(R.id.textMass);

                nameTextView.setText("Name:" + name);
                heightTextView.setText("Height:" +height);
                massTextView.setText("Mass:" +mass);


            }
            return view;
        }
}}