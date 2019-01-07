package com.example.bw.fragment;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bw.R;
import com.example.bw.base.basefragment.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MyFragment extends BaseFragment {
    @BindView(R.id.fragment_my_mypic)
    ImageView fragmentMyMypic;
    @BindView(R.id.fragment_my_myname)
    TextView fragmentMyMyname;
    @BindView(R.id.fragment_my_personaldata)
    TextView fragmentMyPersonaldata;
    @BindView(R.id.fragment_my_mycircle)
    TextView fragmentMyMycircle;
    @BindView(R.id.fragment_my_myfootprint)
    TextView fragmentMyMyfootprint;
    @BindView(R.id.fragment_my_mywallet)
    TextView fragmentMyMywallet;
    @BindView(R.id.fragment_my_myaddress)
    TextView fragmentMyMyaddress;
    @BindView(R.id.fragment_my_userbackground)
    ConstraintLayout fragmentMyUserbackground;
    Unbinder unbinder;

    @Override
    protected int setViewID() {
        return R.layout.fragment_my;
    }

    @Override
    protected void initButterKnife(View view) {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.fragment_my_personaldata, R.id.fragment_my_mycircle, R.id.fragment_my_myfootprint, R.id.fragment_my_mywallet, R.id.fragment_my_myaddress})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fragment_my_personaldata:
                break;
            case R.id.fragment_my_mycircle:
                break;
            case R.id.fragment_my_myfootprint:
                break;
            case R.id.fragment_my_mywallet:
                break;
            case R.id.fragment_my_myaddress:
                break;
        }
    }
}
