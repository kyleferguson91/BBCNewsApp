package com.example.androidlab;

import java.util.ArrayList;
import java.util.List;

public class User {

    public static List<RSSItem> favorites = new ArrayList<>();
    public static List<String> favTitles = new ArrayList<>();
    public static List<String> favLinks = new ArrayList<>();
    private  List<User> users = new ArrayList<>();
    private String username;

    public User(String username)
    {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<RSSItem> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<RSSItem> favorites) {
        this.favorites = favorites;
    }

    public void addFavorite(RSSItem favorite)
    {
        favorites.add(favorite);
    }


}
