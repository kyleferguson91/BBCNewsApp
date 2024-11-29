package com.example.androidlab;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Shared class that handles storing and retrieving data in SharedPreferences.
 * Provides methods to store and retrieve user data such as username and favorite items.
 */
public class Shared {

    /** SharedPreferences object used to store data in the app's private storage. */
    private SharedPreferences sharedPreferences;

    /** Editor for SharedPreferences used to modify stored data. */
    private SharedPreferences.Editor editor;

    /**
     * Constructor that initializes the SharedPreferences object and editor.
     *
     * @param context The context of the calling activity or application.
     */
    public Shared(Context context) {
        sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    /**
     * Stores the provided username in SharedPreferences.
     * If the username is blank, it sets a default message.
     *
     * @param username The username to be stored.
     */
    public void storeData(String username) {
        // Storing data in SharedPreferences
        if (username.equals("")) {
            username = "Username cannot be blank, go to settings and change";
        }
        editor.putString("username", username);
        editor.apply();
    }

    /**
     * Stores the provided favorite titles in SharedPreferences.
     *
     * @param favs The favorite titles to be stored.
     */
    public void storeFavTitles(String favs) {
        // Storing data in SharedPreferences
        editor.putString("favtitles", favs);
        editor.apply();
    }

    /**
     * Retrieves the stored favorite titles from SharedPreferences.
     *
     * @return The stored favorite titles or a default message if none are stored.
     */
    public String getfavTitles() {
        // Retrieving stored data from SharedPreferences
        return sharedPreferences.getString("favtitles", "You do not have any favorites stored!");
    }

    /**
     * Stores the provided favorite links in SharedPreferences.
     *
     * @param favs The favorite links to be stored.
     */
    public void storeFavlinks(String favs) {
        // Storing data in SharedPreferences
        editor.putString("favlinks", favs);
        editor.apply();
    }

    /**
     * Retrieves the stored favorite links from SharedPreferences.
     *
     * @return The stored favorite links or a default message if none are stored.
     */
    public String getFavLinks() {
        // Retrieving stored data from SharedPreferences
        return sharedPreferences.getString("favlinks", "You do not have any favorites stored!");
    }

    /**
     * Retrieves the stored username from SharedPreferences.
     *
     * @return The stored username or a default message if no username is set.
     */
    public String retrieveUsername() {
        String username = sharedPreferences.getString("username", "You do not have a Username Set");
        return username;
    }
}
