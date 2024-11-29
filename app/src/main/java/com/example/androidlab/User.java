package com.example.androidlab;

import java.util.ArrayList;
import java.util.List;

/**
 * User class that represents a user with a list of favorite RSS items.
 * The class provides methods to manage favorites, such as adding new ones and getting the list.
 */
public class User {

    /** List to store the favorite RSS items for the user. */
    public static List<RSSItem> favorites = new ArrayList<>();

    /** List to store the titles of the favorite RSS items. */
    public static List<String> favTitles = new ArrayList<>();

    /** List to store the links of the favorite RSS items. */
    public static List<String> favLinks = new ArrayList<>();

    /** List of users. */
    private List<User> users = new ArrayList<>();

    /** The username of the user. */
    private String username;

    /**
     * Constructs a new User object with the specified username.
     *
     * @param username The username of the user.
     */
    public User(String username) {
        this.username = username;
    }

    /**
     * Gets the username of the user.
     *
     * @return The username of the user.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the user.
     *
     * @param username The username to set for the user.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the list of favorite RSS items for the user.
     *
     * @return The list of favorite RSS items.
     */
    public List<RSSItem> getFavorites() {
        return favorites;
    }

    /**
     * Sets the list of favorite RSS items for the user.
     *
     * @param favorites The list of favorite RSS items to set.
     */
    public void setFavorites(List<RSSItem> favorites) {
        this.favorites = favorites;
    }

    /**
     * Adds a new RSS item to the list of favorites.
     *
     * @param favorite The RSS item to add to the list of favorites.
     */
    public void addFavorite(RSSItem favorite) {
        favorites.add(favorite);
    }
}
