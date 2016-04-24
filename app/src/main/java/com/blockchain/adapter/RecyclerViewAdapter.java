package com.blockchain.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blockchain.fragments.ContactusFragment;
import com.blockchain.fragments.HomeFragment;
import com.blockchain.fragments.LandSaleFragment;
import com.blockchain.fragments.OwnershipFragment;
import com.blockchain.fragments.RegisterFragment;
import com.blockchain.landturtle.MainActivity;
import com.blockchain.landturtle.R;

/**
 * Created by Shubham on 23-04-2016.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {


    View currentview;
    String[] titles;
    TypedArray icons;
    Context context;

    // The default constructor to receive titles,icons and context from MainActivity.

    public RecyclerViewAdapter(String[] titles, TypedArray icons, Context context) {

        this.titles = titles;
        this.icons = icons;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView navTitle;
        ImageView navIcon;
        Context context;

        public ViewHolder(View drawerItem, int itemType, Context context) {

            super(drawerItem);
            this.context = context;
            drawerItem.setOnClickListener(this);


            if (itemType == 1) {
                navTitle = (TextView) itemView.findViewById(R.id.tv_NavTitle);
                navIcon = (ImageView) itemView.findViewById(R.id.iv_NavIcon);
            }
        }

        /**
         * This defines onClick for every item with respect to its position.
         */

        @Override
        public void onClick(View v) {

            MainActivity mainActivity = (MainActivity) context;
            mainActivity.drawerLayout.closeDrawers();
            FragmentTransaction fragmentTransaction = mainActivity.getSupportFragmentManager().beginTransaction();
            showSelected(v);
            switch (getPosition()) {
                case 1:
                    Fragment homeFragment = new HomeFragment();
                    fragmentTransaction.replace(R.id.containerView, homeFragment);
                    fragmentTransaction.commit();
                    break;
                case 2:
                    Fragment landsaleFragment = new LandSaleFragment();
                    fragmentTransaction.replace(R.id.containerView, landsaleFragment);
                    fragmentTransaction.commit();
                    break;
                case 3:
                    Fragment registerFragment = new RegisterFragment();
                    fragmentTransaction.replace(R.id.containerView, registerFragment);
                    fragmentTransaction.commit();
                    break;
                case 4:
                    Fragment ownershipFragment = new OwnershipFragment();
                    fragmentTransaction.replace(R.id.containerView, ownershipFragment);
                    fragmentTransaction.commit();
                    break;
                case 5:
                    Fragment contactusFragment = new ContactusFragment();
                    fragmentTransaction.replace(R.id.containerView, contactusFragment);
                    fragmentTransaction.commit();
                    break;
            }
        }
    }
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (viewType == 1) {
            View itemLayout = layoutInflater.inflate(R.layout.drawer_item_layout, null);
            return new ViewHolder(itemLayout, viewType, context);
        } else if (viewType == 0) {
            View itemHeader = layoutInflater.inflate(R.layout.header_layout, null);
            return new ViewHolder(itemHeader, viewType, context);
        }


        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.ViewHolder holder, int position) {

        if (position != 0) {
            holder.navTitle.setText(titles[position - 1]);
            holder.navIcon.setImageResource(icons.getResourceId(position - 1, -1));
        }
        if(position==1)
        {
            showSelected(holder.itemView);
        }

    }

    @Override
    public int getItemCount() {
        return titles.length + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) return 0;
        else return 1;
    }

    public void showSelected(View v)
    {
        if(currentview==null)
        {
            v.setBackgroundColor(Color.parseColor("#BBDEFB"));
            currentview =v;
        }
        else
        {
            currentview.setBackgroundColor(Color.parseColor("#f9f9f9"));
            v.setBackgroundColor(Color.parseColor("#BBDEFB"));
            currentview =v;
        }



    }

}
