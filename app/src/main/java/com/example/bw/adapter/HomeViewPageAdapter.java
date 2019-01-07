package com.example.bw.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.bw.fragment.CircleFragment;
import com.example.bw.fragment.HomeFragment;
import com.example.bw.fragment.MyFragment;
import com.example.bw.fragment.OrdeFormFragment;
import com.example.bw.fragment.ShoppingFragment;

public class HomeViewPageAdapter extends FragmentPagerAdapter {
    private final int COUNT = 5;

    public HomeViewPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return new HomeFragment();
            case 1:
                return new CircleFragment();
            case 2:
                return new ShoppingFragment();
            case 3:
                return new OrdeFormFragment();
            default:
                return new MyFragment();
        }

    }

    @Override
    public int getCount() {
        return COUNT;
    }
}
