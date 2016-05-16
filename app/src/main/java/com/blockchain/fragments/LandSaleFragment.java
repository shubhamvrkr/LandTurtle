package com.blockchain.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.blockchain.adapter.CustomListViewAdapter;
import com.blockchain.bean.SaleLand;
import com.blockchain.landturtle.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shubham on 23-04-2016.
 */
public class LandSaleFragment extends android.support.v4.app.Fragment {

    ListView listview;
    FloatingActionButton fab;
    List<SaleLand> list = new ArrayList<SaleLand>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.landsale_layout, null);
        listview = (ListView) view.findViewById(R.id.list);
        fab = (FloatingActionButton) view.findViewById(R.id.fab);

        list = generateDummyList();

        listview.setDivider(this.getResources().getDrawable(R.drawable.transperent_color));
        listview.setAdapter(new CustomListViewAdapter(getActivity(), list));

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return view;

    }

    private List<SaleLand> generateDummyList() {
        List<SaleLand> list = new ArrayList<>();

        SaleLand land1 = new SaleLand(R.drawable.land1, "Survery1/NG/", "PON/14", "Residential Land", "450 sqm", "17000 psm", "Sainagar,Bethora-Road,Opp Menezes Factory,Ponda-Goa", "9823610263");
        SaleLand land2 = new SaleLand(R.drawable.land2, "Survery5A/SG/", "MG/50", "Residential Land", "250 sqm", "14000 psm", "Arlem,Opp Fatorda Stadium,Margao-Goa", "9023707308");
        SaleLand land3 = new SaleLand(R.drawable.land3, "Survery4/NG/", "MAP/21", "Residential Land", "300 sqm", "20000 psm", "Sainagar,Bethora-Road,Near DMC College,Mapusa-Goa", "8934897489");
        SaleLand land4 = new SaleLand(R.drawable.land4, "Survery1/SG/", "TISK/11", "Agricultual Land", "15000 sqm", "5000 psm", "Usgao-Tisk,Near MRF factory,Tisk-Goa", "9023740709");
        SaleLand land5 = new SaleLand(R.drawable.land5, "Survery97/SG/", "DHAR/252", "Agricultual Land", "1000000 sqm", "2500 psm", "Shantinagar,Belgaum-Road,Opp Sugar Factory,Dharbhandoda-Goa", "9247897833");


        list.add(land1);
        list.add(land2);
        list.add(land3);
        list.add(land4);
        list.add(land5);


        return list;


    }
}
