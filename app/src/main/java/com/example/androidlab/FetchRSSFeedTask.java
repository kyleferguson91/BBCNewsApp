package com.example.androidlab;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

import java.io.IOException;

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
    protected void onPreExecute() {
        super.onPreExecute();

    }


    @Override
    protected void onPostExecute(String result) {
    System.out.println("Fetch is done, size of list is " + RSSItem.items.size() + " updating news data");
    NewsFeedFragment news = new NewsFeedFragment();
    news.updateListWithRSSFeed(RSSItem.items);
    }
}
