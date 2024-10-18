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
    }


    private class CatImagesTask extends AsyncTask<Void, Bitmap, Void> {
        public Bitmap catImage;

        @Override
        protected Void doInBackground(Void... voids) {

            while (true) {
                try {

                    HttpURLConnection urlConnection = null;

                    URL url = new URL("https://cataas.com/cat?json=true");
                    urlConnection = (HttpURLConnection) url.openConnection();

                    urlConnection.setRequestMethod("GET");
                    urlConnection.connect();


                    // check for successful connection
                    int responseCode = urlConnection.getResponseCode();

                    if (responseCode == HttpURLConnection.HTTP_OK) {

                        //variable to hold respons
                        StringBuilder jsonResponse = new StringBuilder();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                        String line;
                        while ((line = reader.readLine()) != null) {
                            jsonResponse.append(line + "\n");
                        }
                        reader.close();


                        JSONObject jsonObject = new JSONObject(jsonResponse.toString());
                        String catId = jsonObject.getString("id");
                        String imageUrl = jsonObject.getString("url");

                        //now we have a catId and imageUrl
                        //we want to check if this exists locally
                        // check for local image
                        File localImage = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), catId + ".jpg");

                        if (localImage.exists()) {

                            Bitmap existingImage = BitmapFactory.decodeFile(localImage.getAbsolutePath());

                            // set image view to this
                            ImageView imageView = findViewById(R.id.imageView); // Replace with your actual ImageView ID
                            imageView.setImageBitmap(existingImage);

                        } else {
                            try {
                                // Download the new image
                                URL imageurl = new URL(imageUrl);
                                HttpURLConnection imageConnection = (HttpURLConnection) imageurl.openConnection();
                                imageConnection.setRequestMethod("GET");
                                imageConnection.connect();

                                // check response
                                if (imageConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                                    // get InputStream
                                    InputStream inputStream = imageConnection.getInputStream();
                                    Bitmap newImage = BitmapFactory.decodeStream(inputStream);
                                    inputStream.close();

                                    // save image to local storage
                                    FileOutputStream outputStream = new FileOutputStream(localImage);
                                    newImage.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                                    outputStream.flush();
                                    outputStream.close();
                                }
                            } catch (IOException e) {
                                e.printStackTrace(); // Handle the exception
                            }
                        }

                    }

                } catch (ProtocolException e) {
                    throw new RuntimeException(e);
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }


                return null;
            }


        }
    }
}