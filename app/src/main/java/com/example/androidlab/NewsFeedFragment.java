package com.example.androidlab;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

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

    // Create a list of news data

    ArrayList<String> newsData = new ArrayList<>();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_news_feed, container, false);

        // Find the ListView in the layout
        listView = rootView.findViewById(R.id.listView);


        // we will parse xml data here to add it to the list!
        String url = "http://feeds.bbci.co.uk/news/world/us_and_canada/rss.xml";

        System.out.println("News fragment calling fetchRSSFeed");

        new FetchRSSFeedTask().execute("http://feeds.bbci.co.uk/news/world/us_and_canada/rss.xml");

        System.out.println("rss feed fetched, news frag adding to display");





        // Create an ArrayAdapter to bind the data to the ListView
        adapter = new ArrayAdapter<>(getActivity(), R.layout.list_item, R.id.itemTitle, newsData);

        // Set the adapter on the ListView
        listView.setAdapter(adapter);

        // Set up an item click listener
        listView.setOnItemClickListener((parent, view, position, id) -> {

            //upon clicking, lets unexpand all of them!

            System.out.println("called on click list listener");


            TextView date = view.findViewById(R.id.dateexpanded);
            date.setText("Date: "  + RSSItem.items.get(position).getDate());
            TextView desc = view.findViewById(R.id.descriptionexpanded);
            desc.setText(RSSItem.items.get(position).getDescription());
            TextView link = view.findViewById(R.id.linkexpanded);
            String text = "Click <a href="+RSSItem.items.get(position).getLink()+">here</a> to visit the website.";
            link.setText(Html.fromHtml(text));
            link.setOnClickListener(v -> {
                // Manually open the link
                String articleurl = RSSItem.items.get(position).getLink();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(articleurl));
                startActivity(intent);
            });
            TextView favlink = view.findViewById(R.id.favslinkexpanded);
            favlink.setOnClickListener(v -> {

                System.out.println("fav link clicked");
            });


            if (date.getVisibility() == View.GONE && desc.getVisibility() == View.GONE && link.getVisibility() == View.GONE && favlink.getVisibility() == View.GONE) {
                // Show the hidden view (expand the item)
                date.setVisibility(View.VISIBLE);
                desc.setVisibility(View.VISIBLE);
                link.setVisibility(View.VISIBLE);
                favlink.setVisibility(View.VISIBLE);
            } else {
                // Hide the view (collapse the item)
                date.setVisibility(View.GONE);
                desc.setVisibility(View.GONE);
                link.setVisibility(View.GONE);
                favlink.setVisibility(View.GONE);
            }

        });

        return rootView;

    }

    public void updateListWithRSSFeed(List<RSSItem> newsdata) {
        // parse the RSS content and add items to the list


        for (RSSItem item : newsdata) {
            System.out.println("Printing data after fetch");
            newsData.add(item.getTitle());
            System.out.println(item.getTitle());
        }
        System.out.println("adapter " + adapter);

        // Notify the adapter that the data has changed
        if (adapter != null) {
            adapter.notifyDataSetChanged();  // Refresh the ListView
        }
    }


    public class FetchRSSFeedTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                // fetch the RSS feed
                Document doc = Jsoup.connect(params[0]).get();

                //parse rSS
                Elements items = doc.select("item");

                // Loop to get info
                for (org.jsoup.nodes.Element item : items) {
                    String title = item.select("title").text();
                    String date = item.select("pubDate").text();
                    String link = item.select("link").text();
                    String description = item.select("description").text();

                    RSSItem article = new RSSItem(title, date, description, link);
                    RSSItem.items.add(article);


                    Log.d("RSS Item", "Title: " + title + "\nLink: " + link + "\nDescription: " + description + "\nDate:" + date);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            System.out.println("Fetch is done, size of list is " + RSSItem.items.size() + " updating news data");

            updateListWithRSSFeed(RSSItem.items);
        }
    }

}