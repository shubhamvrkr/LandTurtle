package com.blockchain.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blockchain.landturtle.R;
import com.blockchain.utils.NetworkManager;
/**
 * Created by Shubham on 23-04-2016.
 */


public class OwnershipFragment extends android.support.v4.app.Fragment {

    CoordinatorLayout layout;
    Snackbar snackbar;
    EditText pro_et, area_et;
    Button checkwoner;
    NetworkManager nw = new NetworkManager();
    String property_id, area_code;
    LinearLayout form, land_owner_details;

    View loader;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.ownership_layout, null);

        form = (LinearLayout) v.findViewById(R.id.form);
        loader = (View) v.findViewById(R.id.loader);
        layout = (CoordinatorLayout) v.findViewById(R.id.cordinatorlayout);
        checkwoner = (Button) v.findViewById(R.id.checkbutton);
        pro_et = (EditText) v.findViewById(R.id.landid);
        area_et = (EditText) v.findViewById(R.id.pincode);


        checkwoner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                property_id = pro_et.getText().toString().trim();
                area_code = area_et.getText().toString().trim();

                if (property_id.length() > 0 && area_code.length() > 0) {
                    if (nw.isInternetPresent(getActivity())) {

                        form.setVisibility(View.INVISIBLE);
                        loader.setVisibility(View.VISIBLE);

                    } else {

                        snackbar = Snackbar
                                .make(layout, "No internet connection!", Snackbar.LENGTH_LONG)
                                .setAction("SETTINGS", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                    }
                                });

                        // Changing message text color
                        snackbar.setActionTextColor(Color.parseColor("#FF9800"));

                        // Changing action button text color
                        View sbView = snackbar.getView();
                        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                        textView.setTextColor(Color.parseColor("#2196F3"));
                        snackbar.show();
                    }
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
        return v;


    }
}
