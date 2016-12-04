package com.ssms.se.seapp;

import android.os.AsyncTask;



import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DatabaseConnector extends AsyncTask<Void, Void, Void> {

    String isRegistered = "";
    //
    String usernameString = "";
    // TextView temp2 = (TextView) findViewById(R.id.password);
    String passwordString = "";


    public DatabaseConnector(String username, String password){
        this.usernameString = username;
        this.passwordString = password;
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            URL url = null;
            try {
                url = new URL("http://galadriel.cs.utsa.edu/~group6/users.php");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            HttpURLConnection client = null;
            try {
                client = (HttpURLConnection) url.openConnection();
                Thread.sleep(2000);
            } catch (IOException e) {
                e.printStackTrace();
            }
            JSONObject userCredentials = new JSONObject();

            userCredentials.put("username", usernameString);
            userCredentials.put("password", passwordString);
            System.out.println(userCredentials.toString());
            String postArray = "user_credentials=" + userCredentials.toString();
            try {
                client.setRequestMethod("POST");
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                client.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                client.setRequestProperty("charset", "utf-8");
                client.setRequestProperty("Content-Length", Integer.toString(postArray.length()));
                client.setUseCaches(false);
                client.setDoOutput(true);
            } catch (Exception e) {
                e.printStackTrace();
            }

            OutputStreamWriter out = null;
            try {
                out = new OutputStreamWriter(client.getOutputStream());
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                out.write(postArray);
                out.flush();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = "";
            while ((line = reader.readLine()) != null) {
                // Append server response in string
                sb.append(line);
            }
            isRegistered = sb.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void v) {

        if (isRegistered.equals("true")) {
            System.out.println("LOGIN TRUE");
            // user authenticated
            // display messages list vist
        } else if(isRegistered.equals("false")){
            System.out.println("LOGIN FALSE");
            //User not authenticated
            // ?
        } else {
            System.out.println("ERROR");
            //unknown error

        }
    }

}