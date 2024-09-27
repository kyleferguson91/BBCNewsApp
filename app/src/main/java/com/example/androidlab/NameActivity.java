package com.example.androidlab;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Button;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class NameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_name);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



        //SharedPreferences prefs = getSharedPreferences("file", MODE_PRIVATE);
// get intent, get extra string and append to name field
        Intent mainpage = getIntent();
        String name = mainpage.getStringExtra("name");

        TextView namefield = findViewById(R.id.textView2);
        namefield.setText(name);


        // listerners for thank you

        Button thankyou = findViewById(R.id.buttonthankyou);
        Intent pageone = new Intent(this, MainActivity.class);
        thankyou.setOnClickListener(click ->
        {




            setResult(1, pageone);
            finish();

        });

        // listener for don't call me that


        Button dontcallme = findViewById(R.id.buttondontcallme);

        dontcallme.setOnClickListener(click ->
        {



            setResult(0, pageone);
            finish();
        });

    }
}