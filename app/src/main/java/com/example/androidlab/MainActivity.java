package com.example.androidlab;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

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

            try{
                System.out.println("attempt connection");
                URL url = new URL(urlstring);

                // get connection

                connection = (HttpsURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                // check for response

                int response = connection.getResponseCode();

                System.out.println(response + " response is this<");

                if (response == HttpURLConnection.HTTP_OK)
                {

                    System.out.println("connection successful");


                    BufferedReader reader = new BufferedReader(new InputStreamReader((connection.getInputStream())));

                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }
                    reader.close();
                }
                else {
                    System.out.println("connection error!");
                }
            }
            catch(Exception e)
            {
            System.out.println("there was an error!" + e.getMessage());
            }
            System.out.println("string reusult here");
            System.out.println(result);
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