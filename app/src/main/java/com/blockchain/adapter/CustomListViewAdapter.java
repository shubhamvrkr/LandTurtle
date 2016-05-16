package com.blockchain.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.blockchain.bean.SaleLand;
import com.blockchain.landturtle.R;

import java.util.List;

/**
 * Created by shubham_verekar on 5/16/2016.
 */
public class CustomListViewAdapter extends BaseAdapter {

    Activity activity;
    List<SaleLand> list;
    LayoutInflater inflater = null;

    public CustomListViewAdapter() {

    }

    public CustomListViewAdapter(Activity activity, List<SaleLand> list) {

        this.activity = activity;
        this.list = list;
        inflater = (LayoutInflater) activity.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class Holder {
        TextView survey_id;
        TextView property_id;
        TextView land_type;
        TextView area_size;
        TextView price;
        TextView location;
        Button contact_owner;
        ImageView land_img;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Holder holder = new Holder();
        View rowView = inflater.inflate(R.layout.list_item, null);
        holder.survey_id = (TextView) rowView.findViewById(R.id.survey_id);
        holder.property_id = (TextView) rowView.findViewById(R.id.property_id);
        holder.land_type = (TextView) rowView.findViewById(R.id.land_type);
        holder.area_size = (TextView) rowView.findViewById(R.id.area_size);
        holder.price = (TextView) rowView.findViewById(R.id.price);
        holder.location = (TextView) rowView.findViewById(R.id.location);
        holder.contact_owner = (Button) rowView.findViewById(R.id.contact_owner);
        holder.land_img = (ImageView) rowView.findViewById(R.id.land_img);


        final SaleLand land = list.get(i);
        holder.survey_id.setText(land.getSurveyId());
        holder.property_id.setText(land.getPropertyid());
        holder.land_type.setText(land.getLandtype());
        holder.area_size.setText(land.getAreaSize());
        holder.location.setText(land.getLocation());
        holder.price.setText(land.getPrice());
        holder.land_img.setImageResource(land.getImageid());


        holder.contact_owner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri number = Uri.parse("tel:" + land.getContactNo());
                Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                activity.startActivity(callIntent);
            }
        });


        return rowView;
    }
}
