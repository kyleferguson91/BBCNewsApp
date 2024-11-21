package com.example.androidlab;

import java.util.ArrayList;
import java.util.List;

public class RSSItem {



    private String title;
    private String date;
    private String description;
    private String link;

    public RSSItem()
    {

    }

public static List<RSSItem> items = new ArrayList<>();

    public RSSItem(String title, String date, String description, String link)
    {
        this.title = title;
        this.date = date;
        this.description = description;
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
