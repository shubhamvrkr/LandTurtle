package com.blockchain.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Shubham on 22-04-2016.
 */
public class NetworkManager {

    public  boolean isInternetPresent(Context context)
    {
        if (isConnectingToInternet(context))
        {
            try
            {
                HttpURLConnection urlc = (HttpURLConnection) (new URL("http://www.google.com").openConnection());
                urlc.setRequestProperty("User-Agent", "Test");
                urlc.setRequestProperty("Connection", "close");
                urlc.setConnectTimeout(3000); //choose your own timeframe
                urlc.setReadTimeout(4000); //choose your own timeframe
                urlc.connect();
                return (urlc.getResponseCode() == 200);
            } catch (IOException e)
            {
                return (false);  //connectivity exists, but no internet.
            }
        } else
        {
            return false;  //no connectivity
        }
    }
    private  boolean isConnectingToInternet(Context _context){
        ConnectivityManager connectivity = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }

        }
        return false;
    }
}
