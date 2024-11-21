package com.example.androidlab;

import java.util.ArrayList;
import java.util.List;

public class User {

    private List<RSSItem> favorites = new ArrayList<>();

    private String username;


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
