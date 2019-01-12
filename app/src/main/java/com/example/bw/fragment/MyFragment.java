package com.example.bw.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.bw.R;
import com.example.bw.activity.HomeViewpageActivity;
import com.example.bw.activity.my.MyAddressActivity;
import com.example.bw.activity.my.MyParticularsActivity;
import com.example.bw.base.basefragment.BaseFragment;
import com.example.bw.bean.user.UserDataBean;
import com.example.bw.presenter.IPresenterImpl;
import com.example.bw.utils.okhttputils.HttpModel;
import com.example.bw.view.IView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MyFragment extends BaseFragment implements IView {
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
    private IPresenterImpl iPresenter;

    @Override
    protected int setViewID() {
        return R.layout.fragment_my;
    }

    @Override
    protected void initButterKnife(View view) {
        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    protected void initView() {
        iPresenter = new IPresenterImpl(this);
        iPresenter.startRequest(HttpModel.GET,"user/verify/v1/getUserById",null,UserDataBean.class);
    }

    @Override
    public void onResume() {
        super.onResume();
        iPresenter.startRequest(HttpModel.GET,"user/verify/v1/getUserById",null,UserDataBean.class);
    }

    @Override
    protected void initData() {

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
                startActivity(new Intent(getActivity(),MyParticularsActivity.class));
                break;
            case R.id.fragment_my_mycircle:

                break;
            case R.id.fragment_my_myfootprint:
                break;
            case R.id.fragment_my_mywallet:
                break;
            case R.id.fragment_my_myaddress:
                startActivity(new Intent(getActivity(),MyAddressActivity.class));
                break;
        }
    }

    @Override
    public void getDataSuccess(Object data) {
        if(data instanceof  UserDataBean){
            UserDataBean data1 = (UserDataBean) data;
            Glide.with(getActivity()).load(data1.getResult().getHeadPic()).into(fragmentMyMypic);
            fragmentMyMyname.setText (data1.getResult().getNickName());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        iPresenter.onDetach();
    }

    @Override
    public void getDataFail(String error) {

    }
}
