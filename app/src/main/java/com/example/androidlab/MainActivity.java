package com.example.androidlab;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        swAsync swtask = new swAsync();
        swtask.execute();
    }


    private class swAsync extends AsyncTask<Void, Integer, String>
    {

        @Override
        protected void onPreExecute() {
            System.out.println("pre execute");
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {
            System.out.println("do in background");

            String url = "https://swapi.dev/api/people/?format=json";



            return "";
        }


        @Override
        protected void onProgressUpdate(Integer... values) {
            System.out.println("on progress update");
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {
            System.out.println("post execute");
            super.onPostExecute(s);
        }
    }
}