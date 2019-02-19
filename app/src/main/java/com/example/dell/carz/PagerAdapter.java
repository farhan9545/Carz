package com.example.dell.carz;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class PagerAdapter extends FragmentPagerAdapter {

    int noOfTabs;
    Home h;
    Favorites f;
    My_ads m;
    Setting s;

    public PagerAdapter(FragmentManager fm, int noOfTabs) {
        super(fm);
        this.noOfTabs=noOfTabs;
        h= new Home();
        f= new Favorites();
        m= new My_ads();
        s = new Setting();
    }

    @Override
    public Fragment getItem(int position) {
        switch(position)
        {
            case 0:
                return h;
            case 1:
                return m;
            case 2:
                return f;
            case 3:
                return s;


        }
        return null;
    }

    public Home getH() {
        return h;
    }

    public void setH(Home h) {
        this.h = h;
    }

    @Override
    public int getCount() {
        return noOfTabs;
    }
}
