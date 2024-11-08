package com.example.androidlab;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewsFeedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsFeedFragment extends Fragment {

    // ListView and Adapter
    private ListView listView;
    private ArrayAdapter<String> adapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NewsFeedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewsFeedFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewsFeedFragment newInstance(String param1, String param2) {
        NewsFeedFragment fragment = new NewsFeedFragment();
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
    // Create a list of junk data
    ArrayList<String> junkData = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_news_feed, container, false);

        // Find the ListView in the layout
        listView = rootView.findViewById(R.id.listView);


        junkData.add("Item 1");
        junkData.add("Item 2");
        junkData.add("Item 3");
        junkData.add("Item 4");
        junkData.add("Item 5");
        junkData.add("Item 6");
        junkData.add("Item 7");
        junkData.add("Item 8");

        // Create an ArrayAdapter to bind the data to the ListView
        adapter = new ArrayAdapter<>(getActivity(), R.layout.list_item, R.id.itemTitle, junkData);

        // Set the adapter on the ListView
        listView.setAdapter(adapter);

        // Set up an item click listener
        listView.setOnItemClickListener((parent, view, position, id) -> {
            // Retrieve the clicked item
            String clickedItem = junkData.get(position);


            // Display a toast message
            Toast.makeText(getActivity(), "Clicked: " + clickedItem, Toast.LENGTH_SHORT).show();


        });

        return rootView;

    }
}