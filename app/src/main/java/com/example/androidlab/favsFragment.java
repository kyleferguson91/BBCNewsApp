package com.example.androidlab;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.material.snackbar.Snackbar;
import java.util.ArrayList;
import java.util.List;

/**
 * A fragment that displays a list of favorite items and handles user interactions with them.
 * It allows users to view, remove, and visit links associated with their favorite items.
 */
public class favsFragment extends Fragment {

    // Parameters for fragment initialization (optional, not used in this implementation)
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // Variables to hold the fragment arguments (if passed)
    private String mParam1;
    private String mParam2;

    // Default constructor for the fragment
    public favsFragment() {
        // Required empty public constructor
    }

    /**
     * Factory method to create a new instance of this fragment using the provided parameters.
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment favsFragment.
     */
    public static favsFragment newInstance(String param1, String param2) {
        favsFragment fragment = new favsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retrieve fragment arguments (if any)
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    /**
     * Inflates the layout for this fragment and sets up the ListView and its adapter.
     * Handles user interactions with the list items, including viewing and removing favorites.
     *
     * @param inflater The LayoutInflater used to inflate the view.
     * @param container The parent view that the fragment's UI should be attached to.
     * @param savedInstanceState A Bundle containing the saved state of the fragment.
     * @return The root view of the fragment.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the fragment layout
        View rootView = inflater.inflate(R.layout.fragment_favs, container, false);

        // Initialize the ListView and set its adapter
        ListView favsListView = rootView.findViewById(R.id.favslist);
        ArrayAdapter adapter = new ArrayAdapter<>(getActivity(), R.layout.list_item_favorites, R.id.itemTitle, User.favTitles);
        favsListView.setAdapter(adapter);

        // Set a listener for when a list item is clicked
        favsListView.setOnItemClickListener((parent, view, position, id) -> {
            System.out.println("clicked fav list @ position " + position);

            // Find the views for the link and remove favorite button
            TextView linktext = view.findViewById(R.id.itemlink);
            Button removefav = view.findViewById(R.id.removefavorite);

            // Set the text for the link (using HTML to make it clickable)
            String text = getString(R.string.click) + " " + "<a href=" + User.favLinks.get(position) + ">" + getString(R.string.here) + " </a>" + getString(R.string.tovisitthewebsite);
            linktext.setText(Html.fromHtml(text));

            // Set a click listener on the link text to open the URL
            linktext.setOnClickListener(v -> {
                String articleurl = User.favLinks.get(position);
                if (articleurl != "" && articleurl != null) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(articleurl.trim()));
                    startActivity(intent);
                }
            });

            // Toggle visibility of the link and remove favorite button
            if (linktext.getVisibility() == View.GONE) {
                linktext.setVisibility(View.VISIBLE);
                removefav.setVisibility(View.VISIBLE);
            } else {
                linktext.setVisibility(View.GONE);
                removefav.setVisibility(View.GONE);
            }

            // Set a listener for when the remove favorite button is clicked
            removefav.setOnClickListener(v -> {
                System.out.println("remove fav clicked " + position);
                User.favTitles.remove(position);  // Remove the favorite item title
                User.favLinks.remove(position);   // Remove the associated link

                // Save the updated favorites list to shared preferences
                Shared shared = new Shared(getContext());
                shared.storeFavTitles(User.favTitles.toString());
                shared.storeFavlinks(User.favLinks.toString());

                // Show a Snackbar to confirm the removal
                Snackbar.make(rootView, getString(R.string.snackbartext), Snackbar.LENGTH_LONG)
                        .setAction(getString(R.string.snackdismiss), v1 -> {
                            // Action when Snackbar dismiss button is clicked
                        })
                        .show();

                // Print the shared preferences to the console for debugging
                System.out.println("shared pref fav titles " + shared.getfavTitles() + " links " + shared.getFavLinks());

                // Notify the adapter that the data has changed
                adapter.notifyDataSetChanged();
            });
        });

        // Prevent touch events on the ListView from triggering further actions
        favsListView.setOnTouchListener((v, event) -> {
            return false;  // Disables touch events
        });

        return rootView;
    }
}
