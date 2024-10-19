package com.example.androidlab;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Parameter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

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


        String urlString = "https://cataas.com/cat?json=true";

        new CatImages().execute(urlString);
    }


    public class CatImages extends AsyncTask <String, Void, Void>
    {
        Bitmap catPicture;

        protected Void doInBackground(String... args) {
            String caturl = args[0];


            try {




                URL url = new URL(caturl);

                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                System.out.println("Connected " + urlConnection);
            }
            catch (MalformedURLException e)
            {
                System.out.println("Malformed URL! " + e.getMessage());
            }
            catch (IOException e)
            {
                System.out.println("Malformed URL!");
            }
            catch (Exception e)
            {
                System.out.println("Malformed URL!");
            }


            // now we have a connection we need to parse the data

            return null;
        }


        protected void onProgressUpdate(Integer ...args) {
           // super.onProgressUpdate(args);
        }


        protected void onPostExecute(String fromDoInBackground) {

        }

    }



}