package com.example.androidlab;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {
    // character list
    public static List<swCharacter> characterlist = new ArrayList();
    class swCharacter {
        public String name;
        public String height;
        public String mass;


        public swCharacter(String name, String height, String mass) {
            this.name = name;
            this.height = height;
            this.mass = mass;
        }

        public String getName() {
            return name;
        }

        public String getHeight() {
            return height;
        }

        public  String getMass()
        {
            return mass;
        }


    }
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





        // we have the base adapter now we want to populate the list view
        // class to represent each item



    }


    private class swAsync extends AsyncTask<Void, Integer, String> {

        @Override
        protected void onPreExecute() {
            System.out.println("pre execute");
            super.onPreExecute();
        }


        @Override
        protected String doInBackground(Void... voids) {

// I had to add this as everytime I tried to connect I kept facing SSL errors, could not use http as site redirects to https
            try {
                TrustManager[] trustAllCerts = new TrustManager[]{
                        new X509TrustManager() {
                            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                                return null;
                            }

                            public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
                            }

                            public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
                            }
                        }
                };
                SSLContext sc = SSLContext.getInstance("SSL");
                sc.init(null, trustAllCerts, new java.security.SecureRandom());
                HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println("do in background");

            String urlstring = "https://swapi.dev/api/people/?format=json";
            StringBuilder result = new StringBuilder();
            HttpsURLConnection connection = null;

            try {
                System.out.println("attempt connection");
                URL url = new URL(urlstring);

                // get connection

                connection = (HttpsURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                // check for response

                int response = connection.getResponseCode();

                System.out.println(response + " response is this<");

                if (response == HttpURLConnection.HTTP_OK) {

                    System.out.println("connection successful");


                    BufferedReader reader = new BufferedReader(new InputStreamReader((connection.getInputStream())));

                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }
                    reader.close();
                } else {
                    System.out.println("connection error!");
                }
            } catch (Exception e) {
                System.out.println("there was an error!" + e.getMessage());
            }
            System.out.println("string reusult here");
            System.out.println(result);

            // now to get json out of the string

            try {
                JSONObject results = new JSONObject(result.toString());

                JSONArray resultsArray = results.getJSONArray("results");


                for (int i = 0; i < resultsArray.length(); i++) {
                    JSONObject character = resultsArray.getJSONObject(i);
                    String name = character.getString("name");
                    String height = character.getString("height");
                    String mass = character.getString("mass");

                  //  System.out.println("Name: " + name);
                   // System.out.println("Height: " + height);
                   // System.out.println("Mass: " + mass);


                    // we want to create a character object with each item and add to list
                    swCharacter newchar = new swCharacter(name, height, mass);

                    // now we have new object, add it to our master character list
                    characterlist.add(newchar);



                }
                return "";
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }





        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            System.out.println("on post execute");


            // iterate our list and make sure all the things are in it

            for (swCharacter item : characterlist)
            {
                System.out.println(item.getName());
                System.out.println(item.getMass());
                System.out.println(item.getHeight());
            }


            System.out.println("notify list changed");


            ListView listView = findViewById(R.id.list);
            swAdapter adapter = new swAdapter(MainActivity.this, characterlist);
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }

        class swAdapter extends BaseAdapter{
            private Context context;
            private List<swCharacter> characters;
            @Override
            public int getCount() {
               return characters.size();
            }

            public  swAdapter(Context context, List<swCharacter> characterList)
            {
                this.context = context;
                this.characters = characterList;
            }
            @Override
            public Object getItem(int i) {
               return characters.get(i);
            }

            @Override
            public long getItemId(int i) {
                return i;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {

                if (view == null) {
                    view = LayoutInflater.from(context).inflate(R.layout.list_item, viewGroup, false);
                }

                TextView nameTextView = view.findViewById(R.id.textName);
                swCharacter character = characters.get(i);
                nameTextView.setText(character.getName());

                return view;
            }

        }

    }}