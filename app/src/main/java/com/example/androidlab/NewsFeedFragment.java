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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

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

    ProgressBar progressBar = null;

    // ListView and Adapter to display news
    private ListView listView;
    private ArrayAdapter<String> adapter;

    // Arguments for fragment initialization
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public NewsFeedFragment() {
        // Required empty public constructor
    }

    /**
     * Factory method to create a new instance of this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewsFeedFragment.
     */
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

    // ArrayList to hold news data
    ArrayList<String> newsData = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_news_feed, container, false);

        Shared sharedinit = new Shared(getContext());

        // Get the stored favorite titles and links from Shared Preferences
        String[] titles = sharedinit.getfavTitles().split(",");
        String[] links = sharedinit.getFavLinks().split(",");

        // Check if there are any saved favorites and add them to the User's favorite list
        if (titles.length >= 1 && !titles[0].equals("[]")) {
            for (int i = 0; i < titles.length; i++) {
                if (!titles[i].equals(" ") && !titles[i].equals("") && !titles[i].equals("[") && !titles[i].equals("]")) {
                    String title = titles[i].replaceAll("[\\[\\]]", "");
                    String link = links[i].replaceAll("[\\[\\]]", "").trim();
                    if (!User.favTitles.contains(title) || !User.favLinks.contains(link)) {
                        if (!title.equals("") || !title.equals(null) || !title.equals(" ")) {
                            User.favLinks.add(link);
                            User.favTitles.add(title);
                        }
                    }
                }
            }
        }

        // Find the ListView in the layout
        listView = rootView.findViewById(R.id.listView);

        // Initialize the ProgressBar and make it visible while fetching data
        progressBar = rootView.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        // Fetch the RSS feed data
        String url = "http://feeds.bbci.co.uk/news/world/us_and_canada/rss.xml";
        new FetchRSSFeedTask().execute(url);

        // Create an ArrayAdapter to bind the news data to the ListView
        adapter = new ArrayAdapter<>(getActivity(), R.layout.list_item, R.id.itemTitle, newsData);
        listView.setAdapter(adapter);

        // Set up an item click listener for the ListView items
        listView.setOnItemClickListener((parent, view, position, id) -> {
            TextView date = view.findViewById(R.id.dateexpanded);
            date.setText(getString(R.string.date) + ": " + RSSItem.items.get(position).getDate());
            TextView desc = view.findViewById(R.id.descriptionexpanded);
            desc.setText(RSSItem.items.get(position).getDescription());
            TextView link = view.findViewById(R.id.linkexpanded);
            String text = getString(R.string.click) + " " + "<a href=" + RSSItem.items.get(position).getLink() + ">" + " " + getString(R.string.here) + "</a>" + " "+getString(R.string.tovisitthewebsite);
            link.setText(Html.fromHtml(text));
            link.setOnClickListener(v -> {
                // Open the article link when clicked
                String articleurl = RSSItem.items.get(position).getLink();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(articleurl));
                startActivity(intent);
            });

            // Handle favorite link click
            TextView favlink = view.findViewById(R.id.favslinkexpanded);
            favlink.setOnClickListener(v -> {
                // Add to favorites if not already in the list
                if (!User.favTitles.contains(RSSItem.items.get(position).getTitle()) && !User.favLinks.contains(RSSItem.items.get(position).getLink())) {
                    User.favTitles.add(RSSItem.items.get(position).getTitle());
                    User.favLinks.add(RSSItem.items.get(position).getLink());

                    Shared shared = new Shared(getContext());
                    shared.storeFavTitles(User.favTitles.toString());
                    shared.storeFavlinks(User.favLinks.toString());

                    Toast.makeText(getActivity(), getString(R.string.favadded), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), getString(R.string.favexists), Toast.LENGTH_LONG).show();
                }
            });

            // Toggle visibility of expanded item details
            if (date.getVisibility() == View.GONE && desc.getVisibility() == View.GONE && link.getVisibility() == View.GONE && favlink.getVisibility() == View.GONE) {
                date.setVisibility(View.VISIBLE);
                desc.setVisibility(View.VISIBLE);
                link.setVisibility(View.VISIBLE);
                favlink.setVisibility(View.VISIBLE);
            } else {
                date.setVisibility(View.GONE);
                desc.setVisibility(View.GONE);
                link.setVisibility(View.GONE);
                favlink.setVisibility(View.GONE);
            }
        });

        return rootView;
    }

    // Update the list with fetched RSS feed items
    public void updateListWithRSSFeed(List<RSSItem> newsdata) {
        for (RSSItem item : newsdata) {
            newsData.add(item.getTitle());
        }

        // Notify the adapter that the data has changed and refresh the ListView
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    // AsyncTask to fetch RSS feed in background
    public class FetchRSSFeedTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                Document doc = Jsoup.connect(params[0]).get();
                Elements items = doc.select("item");

                // Parse the RSS feed items
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
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result) {
            progressBar.setVisibility(View.GONE);
            updateListWithRSSFeed(RSSItem.items);
        }
    }
}
