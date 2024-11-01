package com.example.androidlab;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class dadjoke extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.activity_main); // Set your own layout


        TextView jokeTextView = findViewById(R.id.joke);
        jokeTextView.setText("Why did the scarecrow win an award? Because he was outstanding in his field!");


    }

}
