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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link favsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class favsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public favsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment favsFragment.
     */
    // TODO: Rename and change types and number of parameters
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
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_favs, container, false);

        ListView favsListView = rootView.findViewById(R.id.favslist);

        ArrayAdapter adapter = new ArrayAdapter<>(getActivity(), R.layout.list_item_favorites, R.id.itemTitle, User.favTitles);


        favsListView.setAdapter(adapter);

        favsListView.setOnItemClickListener((parent, view, position, id) -> {

            System.out.println("clicked fav list @ position " + position);

            TextView linktext = view.findViewById(R.id.itemlink);
            Button removefav = view.findViewById(R.id.removefavorite);


            String text = "Click <a href="+User.favLinks.get(position)+">here</a> to visit the website.";
            linktext.setText(Html.fromHtml(text));
            linktext.setOnClickListener(v -> {
                String articleurl = User.favLinks.get(position);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(articleurl));
                startActivity(intent);
            });

            if (removefav.getVisibility() == View.GONE || linktext.getVisibility() == View.GONE)
            {
                removefav.setVisibility(View.VISIBLE);
                linktext.setVisibility(View.VISIBLE);
            }
            removefav.setOnClickListener(v -> {
                // Code to execute when the button is clicked
                System.out.println("remove fav clicked " + position);
                User.favTitles.remove(position);
                User.favLinks.remove(position);


                Shared shared = new Shared(getContext());
                shared.storeFavTitles(User.favTitles.toString());
                shared.storeFavlinks(User.favLinks.toString());

                System.out.println("shared pref fav titles " + shared.getfavTitles() + " links " + shared.getFavLinks());


                adapter.notifyDataSetChanged();
            });

        });


        return rootView;
    }
}