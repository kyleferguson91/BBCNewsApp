package com.example.androidlab;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

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
import java.net.URLConnection;

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


    public class CatImages extends AsyncTask<String, Integer, Void> {
        Bitmap catPicture;
        ProgressBar progressBar;
        ImageView bgImage;
        private Boolean running = true;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
             progressBar = findViewById(R.id.progressBar);
             bgImage = findViewById(R.id.imageView);
            System.out.println("pre execute");

            progressBar.setVisibility(View.VISIBLE);
            }

        protected Void doInBackground(String... args) {
            String caturl = args[0];

            while (running) {
                try {
                    progressBar = findViewById(R.id.progressBar);
                    progressBar.setVisibility(View.VISIBLE);
                    // Fetch cat info
                    URL url = new URL(caturl);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");

                    InputStream response = urlConnection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(response, "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line).append("\n");
                    }

                    String result = sb.toString();
                    JSONObject catinfo = new JSONObject(result);
                    String id = catinfo.getString("_id");
                    String imageurl = "https://cataas.com/cat/" + id;

                    // Check if the image exists
                    String filename = id + ".jpg";
                    File catImageFile = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/CatImages", filename);

                    if (catImageFile.exists()) {
                        System.out.println("File exists, setting background");
                        catPicture = BitmapFactory.decodeFile(catImageFile.getAbsolutePath());
                    } else {
                        System.out.println("File " + filename + " does not exist, downloading!");
                        // Download the image
                        URL downloadURL = new URL(imageurl);
                        HttpURLConnection connection = (HttpURLConnection) downloadURL.openConnection();
                        connection.setDoInput(true);
                        connection.connect();

                        if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                            System.out.println("Error fetching cat info: " + connection.getResponseCode());
                            return null;
                        }
                        InputStream input = connection.getInputStream();
                        catPicture = BitmapFactory.decodeStream(input);

                        // Save image to device
                        if (catPicture != null) {
                            // Create directory if it doesn't exist
                            File directory = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "CatImages");
                            if (!directory.exists()) {
                                directory.mkdir();
                            }

                            // Create the file
                            File imageFile = new File(directory, filename);
                            try (FileOutputStream outputStream = new FileOutputStream(imageFile)) {
                                catPicture.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                                System.out.println("Image saved to " + imageFile.getAbsolutePath());
                            } catch (IOException e) {
                                e.printStackTrace();
                                System.out.println("IO Exception while saving image!");
                            }
                        }
                    }

                    // Update progress bar and display the image
                    for (int i = 0; i <= 100; i++) {
                        onProgressUpdate(i);
                        Thread.sleep(30);
                    }

                    // Wait for 5 seconds before fetching another image
                    System.out.println("waiting here");
                    Thread.sleep(5000);

                } catch (JSONException e) {
                    System.out.println("JSON error!");
                } catch (MalformedURLException e) {
                    System.out.println("Malformed URL! " + e.getMessage());
                } catch (IOException e) {
                    System.out.println("IO Exception");
                } catch (Exception e) {
                    System.out.println("General Exception! " + e.getMessage());
                }
            }
            return null;
        }

        protected void onProgressUpdate(Integer i) {

            System.out.println("publishing progress on prog update");
            if (i >= 99 && catPicture != null ) {
                bgImage.setImageBitmap(catPicture);

            } else {
                progressBar.setProgress(i);
            }
        }

        protected void onPostExecute(Void result) {
            progressBar.setVisibility(View.GONE);
        }
    }



}