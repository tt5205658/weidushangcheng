package com.example.bw.adapter.orderform;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.bw.fragment.orderform.OrderFormAllFragment;
import com.example.bw.fragment.orderform.OrderFormAppraiseFragment;
import com.example.bw.fragment.orderform.OrderFormCollectFragment;
import com.example.bw.fragment.orderform.OrderFormCompleteFragment;
import com.example.bw.fragment.orderform.OrderFormObligationFragment;

import java.util.List;

public class OrderFormViewPageAdapter extends FragmentPagerAdapter {
    private final int count=5;
    public OrderFormViewPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                return new OrderFormAllFragment();
            case 1:
                return new OrderFormObligationFragment();
            case 2:
                return new OrderFormCollectFragment();
            case 3:
                return new OrderFormAppraiseFragment();
            default:
                return new OrderFormCompleteFragment();
        }
    }

    @Override
    public int getCount() {
        return count;
    }
}
