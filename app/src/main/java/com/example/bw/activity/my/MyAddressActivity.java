package com.example.bw.activity.my;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bw.R;
import com.example.bw.adapter.fragmentmy.MyAddressListAdapter;
import com.example.bw.base.baseactivity.BaseActivity;
import com.example.bw.bean.user.ReceiveAddressList;
import com.example.bw.presenter.IPresenterImpl;
import com.example.bw.utils.okhttputils.HttpModel;
import com.example.bw.view.IView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyAddressActivity extends BaseActivity implements IView {

    @BindView(R.id.enter)
    TextView enter;
    @BindView(R.id.myaddress_recycle)
    RecyclerView myaddressRecycle;
    @BindView(R.id.myaddress_addbutton)
    Button myaddressAddbutton;
    @BindView(R.id.c1)
    ConstraintLayout c1;
    private IPresenterImpl iPresenter;
    private MyAddressListAdapter addressListAdapter;

    @Override
    protected int setViewID() {
        return R.layout.myaddress;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        iPresenter = new IPresenterImpl(this);
        initRecycle();
        iPresenter.startRequest(HttpModel.GET, "user/verify/v1/receiveAddressList", null, ReceiveAddressList.class);

    }

    private void initRecycle() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myaddressRecycle.setLayoutManager(layoutManager);
        addressListAdapter = new MyAddressListAdapter(this);
        myaddressRecycle.setAdapter(addressListAdapter);
        addressListAdapter.setMyAddressCallBack(new MyAddressListAdapter.MyAddressCallBack() {
            @Override
            public void upDataButton(int id) {
                Toast.makeText(MyAddressActivity.this, id + "", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void defauAddress(int id) {
                Toast.makeText(MyAddressActivity.this, id + "", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    public void getDataSuccess(Object data) {
        if (data instanceof ReceiveAddressList) {
            ReceiveAddressList data1 = (ReceiveAddressList) data;
            addressListAdapter.setmList(data1.getResult());
        }
    }

    @Override
    public void getDataFail(String error) {

    }


    @OnClick({R.id.enter, R.id.myaddress_addbutton,R.id.c1})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.enter:
                break;
            case R.id.myaddress_addbutton:
                break;
            case R.id.c1:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


}
