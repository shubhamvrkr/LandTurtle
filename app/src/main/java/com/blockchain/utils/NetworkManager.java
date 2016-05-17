package com.blockchain.utils;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shubham on 22-04-2016.
 */
public class NetworkManager {

    public boolean isInternetPresent(Context context) {
        if (isConnectingToInternet(context)) {


            try {
                return new TryConnect().execute().get();
            } catch (Exception e) {
                return false;
            }

        } else {
            return false;
        }
    }

    private boolean isConnectingToInternet(Context _context) {
        ConnectivityManager connectivity = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }

        }
        return false;
    }

    public String getResponseFromServer(String URL, JSONObject obj) {

        String responseServer="error";
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(URL);

        try {

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("req", obj.toString()));

            Log.e("mainToPost", "mainToPost" + nameValuePairs.toString());

            // Use UrlEncodedFormEntity to send in proper format which we need
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            InputStream inputStream = response.getEntity().getContent();
            responseServer = getStringFromInputStream(inputStream);
            Log.e("response", "response -----" + responseServer);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseServer;
    }

    // convert InputStream to String
    private static String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

}