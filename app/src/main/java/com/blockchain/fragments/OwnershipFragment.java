package com.blockchain.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blockchain.landturtle.R;
import com.blockchain.utils.Config;
import com.blockchain.utils.NetworkManager;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by Shubham on 23-04-2016.
 */


public class OwnershipFragment extends android.support.v4.app.Fragment {
    JSONObject res_obj;

    CoordinatorLayout layout;
    Snackbar snackbar;
    EditText pro_et, area_et;
    Button checkwoner, searchagain, prevowner;
    NetworkManager nw = new NetworkManager();
    String property_id, survey_code;
    LinearLayout form, land_owner_details;
    TextView surveytv, propertytv, landtypetv, locationtv, areatv, ownername, contact, aadharno;
    View loader;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.ownership_layout, null);

        initialize(v);
        checkwoner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                property_id = pro_et.getText().toString().trim();
                survey_code = area_et.getText().toString().trim();

                if (property_id.length() > 0 && survey_code.length() > 0) {
                   // if (nw.isInternetPresent(getActivity())) {

                        form.setVisibility(View.INVISIBLE);
                        land_owner_details.setVisibility(View.GONE);
                        loader.setVisibility(View.VISIBLE);

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    JSONObject obj = new JSONObject();
                                    obj.put("propertyid", property_id);
                                    obj.put("type", "0");
                                    obj.put("surveyid", survey_code);
                                    String response = nw.getResponseFromServer(Config.CHECKOWNERSHIP_URL, obj).trim();
                                    if(response.compareToIgnoreCase("Entry Not Present")==0)
                                    {
                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {

                                                Toast.makeText(getContext(),"Invalid Property ID or Survey No",Toast.LENGTH_SHORT).show();
                                                form.setVisibility(View.VISIBLE);
                                                land_owner_details.setVisibility(View.GONE);
                                                loader.setVisibility(View.INVISIBLE);
                                            }
                                        });
                                    }
                                    else
                                    {
                                        res_obj = new JSONObject(response);
                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {

                                                setData();
                                            }
                                        });



                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        }).start();


               //     } else {

                /*        snackbar = Snackbar
                                .make(layout, "No internet connection!", Snackbar.LENGTH_LONG)
                                .setAction("SETTINGS", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
                                    }
                                });

                        // Changing message text color
                        snackbar.setActionTextColor(Color.parseColor("#FF9800"));

                        // Changing action button text color
                        View sbView = snackbar.getView();
                        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                        textView.setTextColor(Color.parseColor("#2196F3"));
                        snackbar.show();
                    }*/
                } else {

                    snackbar = Snackbar
                            .make(layout, "All fields are mandatory !", Snackbar.LENGTH_LONG)
                            .setAction("OK", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    snackbar.dismiss();
                                }
                            });
                    snackbar.setActionTextColor(Color.parseColor("#FF9800"));
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setTextColor(Color.parseColor("#2196F3"));
                    snackbar.show();
                }

            }
        });
        searchagain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                form.setVisibility(View.VISIBLE);
                land_owner_details.setVisibility(View.GONE);
                loader.setVisibility(View.INVISIBLE);
            }
        });
        prevowner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if (nw.isInternetPresent(getActivity())) {

                    form.setVisibility(View.INVISIBLE);
                    land_owner_details.setVisibility(View.GONE);
                    loader.setVisibility(View.VISIBLE);

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {

                                JSONObject obj = new JSONObject();
                                obj.put("hash", res_obj.getString("prevblock"));
                                obj.put("prevhash", res_obj.getString("prevhash"));
                                obj.put("type", "1");
                                String response = nw.getResponseFromServer(Config.CHECKOWNERSHIP_URL, obj).trim();
                                Log.d("response", response);
                                if(response.compareToIgnoreCase("Entry Not Present")==0)
                                {
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {

                                            Toast.makeText(getContext(),"Invalid Hash",Toast.LENGTH_SHORT).show();
                                            form.setVisibility(View.VISIBLE);
                                            land_owner_details.setVisibility(View.GONE);
                                            loader.setVisibility(View.INVISIBLE);
                                        }
                                    });
                                }
                                else
                                {
                                    res_obj = new JSONObject(response);
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {

                                            setData();
                                        }
                                    });

                                }



                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }).start();


               // } else {

                   /* snackbar = Snackbar
                            .make(layout, "No internet connection!", Snackbar.LENGTH_LONG)
                            .setAction("SETTINGS", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
                                }
                            });

                    // Changing message text color
                    snackbar.setActionTextColor(Color.parseColor("#FF9800"));

                    // Changing action button text color
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setTextColor(Color.parseColor("#2196F3"));
                    snackbar.show();
                }*/
            }
        });
        return v;


    }

    private void initialize(View v) {
        form = (LinearLayout) v.findViewById(R.id.form);
        loader = v.findViewById(R.id.loader);
        layout = (CoordinatorLayout) v.findViewById(R.id.cordinatorlayout);
        checkwoner = (Button) v.findViewById(R.id.checkbutton);
        pro_et = (EditText) v.findViewById(R.id.landid);
        area_et = (EditText) v.findViewById(R.id.pincode);
        land_owner_details = (LinearLayout) v.findViewById(R.id.result_layout);
        surveytv = (TextView) v.findViewById(R.id.surveyno);
        propertytv = (TextView) v.findViewById(R.id.propertyno);
        landtypetv = (TextView) v.findViewById(R.id.landtype);
        locationtv = (TextView) v.findViewById(R.id.location);
        areatv = (TextView) v.findViewById(R.id.area);
        ownername = (TextView) v.findViewById(R.id.ownername);
        contact = (TextView) v.findViewById(R.id.contact);
        aadharno = (TextView) v.findViewById(R.id.aadharno);
        prevowner = (Button) v.findViewById(R.id.prevowner);
        searchagain = (Button) v.findViewById(R.id.searchagain);

    }

    private void setData() {


        try {

            surveytv.setText(res_obj.getString("postal_code"));
            propertytv.setText(res_obj.getString("land_id"));
            landtypetv.setText(res_obj.getString("landtype"));
            locationtv.setText(res_obj.getString("location"));
            areatv.setText(res_obj.getString("area"));
            ownername.setText(res_obj.getString("name"));
            contact.setText(res_obj.getString("contact"));
            aadharno.setText(res_obj.getString("adhaar"));
            form.setVisibility(View.INVISIBLE);
            land_owner_details.setVisibility(View.VISIBLE);
            loader.setVisibility(View.INVISIBLE);
            if (res_obj.getString("genesisblock").compareToIgnoreCase("true") == 0) {
                prevowner.setVisibility(View.INVISIBLE);
            } else {
                prevowner.setVisibility(View.VISIBLE);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
