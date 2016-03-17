package com.example.defaut.appliseisme;

import android.os.AsyncTask;
import android.webkit.WebView;
import android.widget.TextView;

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

        return chaine2;
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
