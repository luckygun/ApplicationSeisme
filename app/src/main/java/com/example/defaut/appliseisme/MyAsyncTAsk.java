package com.example.defaut.appliseisme;

import android.os.AsyncTask;
import android.webkit.WebView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by p1400336 on 08/03/2016.
 */
public class MyAsyncTAsk extends AsyncTask<Object,Void,String> {
    TextView tv;
    WebView wv;
    boolean b;
    String chaine;
    String data;
    ArrayList Seisme;
    JSONObject reader;
    String coucou;

    @Override
    protected String doInBackground(Object[] params) {





        String chaine = (String)params[0];
        if(params[1] instanceof TextView) {
            tv = (TextView) params[1];
            b = true;
        }else{
            wv = (WebView) params[1];
            b = false;
        }
        String chaine2 = "";
        try {
            URL url = new URL(chaine);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while((data = in.readLine()) != null){
                    chaine2 += data;
                }
                in.close();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // PARSAGE DU JSON //

        try {
            reader = new JSONObject(chaine2);

            JSONArray test = reader.getJSONArray("features");
            for(int i =0 ; i<test.length(); i++) {

                JSONObject test2 = test.getJSONObject(i);
                coucou = "";
                coucou += test2.getString("type");
                return coucou;

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return "echec";
    }










    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(b == true) {
            tv.setText(s);
        }else{
            wv.loadData(s, "text/html; charset=utf-8", "UTF-8");
        }

    }
}
