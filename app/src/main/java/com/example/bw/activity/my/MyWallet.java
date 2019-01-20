package com.example.bw.activity.my;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bw.R;
import com.example.bw.adapter.my.MyWalletAdapter;
import com.example.bw.base.baseactivity.BaseActivity;
import com.example.bw.bean.my.MyWalletBean;
import com.example.bw.presenter.IPresenterImpl;
import com.example.bw.utils.okhttputils.HttpModel;
import com.example.bw.view.IView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyWallet extends BaseActivity implements IView {
    @BindView(R.id.userprice)
    TextView userprice;
    @BindView(R.id.xrecycle)
    XRecyclerView xrecycle;
    private MyWalletAdapter myWalletAdapter;
private int page;
    private IPresenterImpl iPresenter;

    @Override
    protected int setViewID() {
        return R.layout.mywallet;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        page=1;
        iPresenter = new IPresenterImpl(this);
        initNetData();

    }

    private void initNetData() {
        iPresenter.startRequest(HttpModel.GET,"user/verify/v1/findUserWallet?page="+page+"&count=10",null, MyWalletBean.class);
        page++;

    }

    @Override
    protected void initData() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        xrecycle.setLayoutManager(layoutManager);
        xrecycle.setLoadingMoreEnabled(true);
        xrecycle.setPullRefreshEnabled(true);
        myWalletAdapter = new MyWalletAdapter(this);
        xrecycle.setAdapter(myWalletAdapter);
        xrecycle.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page=1;
                initNetData();
            }

            @Override
            public void onLoadMore() {
                initNetData();
            }
        });
        myWalletAdapter.setMyWalletCallBack(new MyWalletAdapter.MyWalletCallBack() {
            @Override
            public void skip(String id) {
                startActivity(new Intent(MyWallet.this,MyWalletDataActivity.class).putExtra("orderId",id));

            }
        });
    }


    @Override
    public void getDataSuccess(Object data) {
        if(data instanceof MyWalletBean){
            MyWalletBean data1 = (MyWalletBean) data;
            userprice.setText(data1.getResult().getBalance()+"");
           if(page==1){
               myWalletAdapter.setmList(data1.getResult().getDetailList());
           }else{
               myWalletAdapter.addmList(data1.getResult().getDetailList());
           }
            xrecycle.loadMoreComplete();
            xrecycle.refreshComplete();
        }
    }

    @Override
    public void getDataFail(String error) {

    }
}
