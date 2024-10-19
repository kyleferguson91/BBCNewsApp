package com.example.androidlab;

import android.content.Context;
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


    public class CatImages extends AsyncTask <String, Void, Void>
    {
        Bitmap catPicture;

        protected Void doInBackground(String... args) {
            String caturl = args[0];


            try {




                URL url = new URL(caturl);

                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
               // urlConnection.connect();



                System.out.println("Connected " + urlConnection);

                // now we have a connection we need to parse the data

                InputStream response = urlConnection.getInputStream();


                BufferedReader reader = new BufferedReader(new InputStreamReader(response, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null)
                {
                    sb.append(line + "\n");
                }

                String result = sb.toString();

                System.out.println(result);


                // now extract the data we need from the url

                JSONObject catinfo = new JSONObject(result);

                // get attributes
                String id = catinfo.getString("_id");
                String imageurl = "https://cataas.com/cat/" + id;
                System.out.println(id + " " + imageurl);


                // now we have the data and must check if this file exists on our decvice

                   String filename = id + ".jpg";
                 //   String filename = "Mbq6mCCcZKN862f2";
                //FileOutputStream outputStream = openFileOutput(filename, Context.MODE_PRIVATE);

                File catImageFile = new File(filename);

                if (catImageFile.exists())
                {
                    System.out.println("File exists, setting background");
                    // file exists so set the background to this


                     catPicture = BitmapFactory.decodeFile(catImageFile.getAbsolutePath());
                    ImageView bgImage = findViewById(R.id.imageView);
                    bgImage.setImageBitmap(catPicture);


                }
                else
                {
                    System.out.println("File " + filename + " does not exist, downloading!");
                    // file does not exist, download it and process
                    URL downloadURL = new URL(imageurl);
                    HttpURLConnection connection = (HttpURLConnection) downloadURL.openConnection();
                    connection.setDoInput(true);
                    connection.connect();

                    if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                        System.out.println("Error fetching cat info: " + connection.getResponseCode());
                        return null;
                    }
                    InputStream input = connection.getInputStream();

                    if (input == null)
                    {
                        System.out.println("download image input stream is null!");
                    }

                     //save image
                    //create cat images folder
                    File directory = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "CatImages");
                    if (!directory.exists())
                    {
                        directory.mkdir();
                    }

                    // create a file
                    File imageFile = new File(directory, filename + ".jpg");


                    catPicture = BitmapFactory.decodeStream(input);
                    if (catPicture == null) {
                        System.out.println("Failed to decode bitmap.");

                    }


                    try (FileOutputStream outputStream = new FileOutputStream(imageFile)) {
                        // Get the bitmap and compress it

                        if (catPicture == null)
                        {
                            System.out.println("catpic is null");
                        }


                       // catPicture.compress(Bitmap.CompressFormat.JPEG, 100, outputStream); // Save as JPEG
                       // outputStream.flush(); // Flush the output stream
                        System.out.println("Image saved to " + imageFile.getAbsolutePath());
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("IO Exception while saving image!");
                    }

                    for (int i = 0; i < 100; i++) {
                        try {
                            publishProgress(i);
                            Thread.sleep(30);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    return null;
                }

            }
            catch (JSONException e)
            {
                System.out.println("JSON error!");
            }
            catch (MalformedURLException e)
            {
                System.out.println("Malformed URL! " + e.getMessage());
            }
            catch (IOException e)
            {
                System.out.println("Io Excelption");
            }
            catch (Exception e)
            {
                System.out.println("General Exception!");
            }




            return null;
        }


        protected void onProgressUpdate(Integer i) {
           super.onProgressUpdate(i);
        }




        protected void onPostExecute(Void result) {
            System.out.println("Setting Image!");
            ImageView bgImage = findViewById(R.id.imageView);
            bgImage.setImageBitmap(catPicture);
        }

    }



}