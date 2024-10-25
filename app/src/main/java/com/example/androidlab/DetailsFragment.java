package com.example.androidlab;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class DetailsFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_details, container, false);

        Bundle bundle = getArguments();

        if (bundle != null) {
            // populate the TextViews with the values
            ((TextView) view.findViewById(R.id.textName)).setText(bundle.getString("name"));
            ((TextView) view.findViewById(R.id.textHeight)).setText(String.valueOf(bundle.getInt("age")));
            ((TextView) view.findViewById(R.id.textMass)).setText(bundle.getString("gap"));
        }


        return view;
    }
}