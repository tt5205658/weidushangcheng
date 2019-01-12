package com.example.bw.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.bw.R;
import com.example.bw.adapter.orderform.OrderFormViewPageAdapter;
import com.example.bw.base.basefragment.BaseFragment;
import com.example.bw.view.IView;
import com.example.bw.view.NoScrollViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class OrdeFormFragment extends BaseFragment  {
    @BindView(R.id.ordefrom_all)
    ImageButton ordefromAll;
    @BindView(R.id.ordefrom_obligation)
    ImageButton ordefromObligation;
    @BindView(R.id.ordefrom_collect)
    ImageButton ordefromCollect;
    @BindView(R.id.ordefrom_appraise)
    ImageButton ordefromAppraise;
    @BindView(R.id.ordefrom_complete)
    ImageButton ordefromComplete;
    @BindView(R.id.ordeFrom_Viewpage)
    NoScrollViewPager ordeFromViewpage;
    Unbinder unbinder;

    @Override
    protected int setViewID() {
        return R.layout.fragment_ordeform;
    }

    @Override
    protected void initButterKnife(View view) {
        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    protected void initView() {
        OrderFormViewPageAdapter viewPageAdapter = new OrderFormViewPageAdapter(getChildFragmentManager());
        ordeFromViewpage.setAdapter(viewPageAdapter);
    }

    @Override
    protected void initData() {

    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(unbinder!=null){
            unbinder.unbind();
        }

    }

    @OnClick({R.id.ordefrom_all, R.id.ordefrom_obligation, R.id.ordefrom_collect, R.id.ordefrom_appraise, R.id.ordefrom_complete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ordefrom_all:
                ordeFromViewpage.setCurrentItem(0);
                break;
            case R.id.ordefrom_obligation:
                ordeFromViewpage.setCurrentItem(1);
                break;
            case R.id.ordefrom_collect:
                ordeFromViewpage.setCurrentItem(2);
                break;
            case R.id.ordefrom_appraise:
                ordeFromViewpage.setCurrentItem(3);
                break;
            case R.id.ordefrom_complete:
                ordeFromViewpage.setCurrentItem(4);
                break;
        }
    }


}
