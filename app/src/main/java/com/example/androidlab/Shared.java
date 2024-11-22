package com.example.androidlab;

import android.content.Context;
import android.content.SharedPreferences;

public class Shared {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    // Constructor that takes Context
    public Shared(Context context) {
        sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }




    public void storeData(String username) {
        // Storing data in SharedPreferences
        if (username.equals(""))
        {
            username="Username cannot be blank, go to settings and change";
        }
        editor.putString("username", username);

        editor.apply();
    }


    public void storeFavTitles(String favs) {
        // Storing data in SharedPreferences


        editor.putString("favtitles", favs);

        editor.apply();
    }


    public String getfavTitles() {
        // Storing data in SharedPreferences


       return sharedPreferences.getString("favtitles", "You do not have any favorites stored!");
    }

    public void storeFavlinks(String favs) {
        // Storing data in SharedPreferences


        editor.putString("favlinks", favs);

        editor.apply();
    }


    public String getFavLinks() {
        // Storing data in SharedPreferences


        return sharedPreferences.getString("favlinks", "You do not have any favorites stored!");
    }


    public String retrieveUsername() {
        String username = sharedPreferences.getString("username", "You do not have a Username Set");
        //System.out.println(username);
        return username;
    }
}
