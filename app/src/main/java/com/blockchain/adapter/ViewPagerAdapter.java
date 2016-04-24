package com.blockchain.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.blockchain.fragments.Register_Steps_Fragment;
import com.blockchain.fragments.Trasfer_Steps_Fragment;

/**
 * Created by Shubham on 24-04-2016.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {
    private Context _context;
    public static int totalPage = 2;

    public ViewPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        _context = context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment f = new Fragment();
        switch (position) {
            case 0:
                f = new Register_Steps_Fragment();
                break;
            case 1:
                f = new Trasfer_Steps_Fragment();
                break;
        }
        return f;
    }

    @Override
    public int getCount() {
        return totalPage;
    }
}
