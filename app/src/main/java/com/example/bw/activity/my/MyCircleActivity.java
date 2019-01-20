package com.example.bw.activity.my;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.example.bw.R;
import com.example.bw.adapter.my.MyCricleAdapter;
import com.example.bw.base.baseactivity.BaseActivity;
import com.example.bw.bean.circle.DeleteSuccess;
import com.example.bw.bean.my.MycricleBean;
import com.example.bw.presenter.IPresenterImpl;
import com.example.bw.utils.okhttputils.HttpModel;
import com.example.bw.view.IView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyCircleActivity extends BaseActivity implements IView {
    @BindView(R.id.recycle)
    XRecyclerView recycle;
    private int page;
    private MyCricleAdapter myCricleAdapter;
    private IPresenterImpl iPresenter;

    @Override
    protected int setViewID() {
        return R.layout.activity_mycircle;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        page=1;
        iPresenter = new IPresenterImpl(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycle.setLayoutManager(layoutManager);
        recycle.setPullRefreshEnabled(true);
        recycle.setLoadingMoreEnabled(true);
        myCricleAdapter = new MyCricleAdapter(this);
        recycle.setAdapter(myCricleAdapter);
        recycle.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page=1;
                initData();
            }

            @Override
            public void onLoadMore() {
                initData();
            }
        });
        myCricleAdapter.setCallBack(new MyCricleAdapter.CallBack() {
            @Override
            public void delete(String id) {
                iPresenter.startRequest(HttpModel.DELETE,"circle/verify/v1/deleteCircle?circleId="+id,null,DeleteSuccess.class);
            }
        });
    }

    @Override
    protected void initData() {
        iPresenter.startRequest(HttpModel.GET,"circle/verify/v1/findMyCircleById?page="+page+"&count=5",null,MycricleBean.class);
        page++;
    }


    @Override
    public void getDataSuccess(Object data) {
    if(data instanceof MycricleBean){
        MycricleBean data1 = (MycricleBean) data;
        if(page==1){
            myCricleAdapter.setmList(data1.getResult());
        }else{
            myCricleAdapter.addmList(data1.getResult());
        }
        recycle.loadMoreComplete();
        recycle.refreshComplete();
    }else
    if(data instanceof DeleteSuccess){
        DeleteSuccess data1 = (DeleteSuccess) data;

    }
    }

    @Override
    public void getDataFail(String error) {
        iPresenter.startRequest(HttpModel.GET,"circle/verify/v1/findMyCircleById?page="+page+"&count=5",null,MycricleBean.class);
    }
}
