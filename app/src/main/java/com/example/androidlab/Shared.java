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
        editor.putString("username", username);
        editor.apply();
    }

    public String retrieveUsername() {
        String username = sharedPreferences.getString("username", "You do not have a Username Set");
        //System.out.println(username);
        return username;
    }
}
