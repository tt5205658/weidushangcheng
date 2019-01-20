package com.example.bw.activity.my;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.example.bw.R;
import com.example.bw.adapter.my.MyFootAdapter;
import com.example.bw.base.baseactivity.BaseActivity;
import com.example.bw.bean.circle.FootprintBean;
import com.example.bw.bean.my.MyFootBean;
import com.example.bw.presenter.IPresenterImpl;
import com.example.bw.utils.okhttputils.HttpModel;
import com.example.bw.view.IView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyFootprintActivity extends BaseActivity implements IView {
    @BindView(R.id.xrecycle)
    XRecyclerView xrecycle;
    private IPresenterImpl iPresenter;
    private MyFootAdapter myFootAdapter;
private int page;
    @Override
    protected int setViewID() {
        return R.layout.activitymyfootprint;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        page = 1;
        iPresenter = new IPresenterImpl(this);
        iPresenter.startRequest(HttpModel.GET,"commodity/verify/v1/browseList?page="+page+"&count=10",null,MyFootBean.class);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        xrecycle.setPullRefreshEnabled(true);
        xrecycle.setLoadingMoreEnabled(true);
        xrecycle.setLayoutManager(gridLayoutManager);
        myFootAdapter = new MyFootAdapter(this);
        xrecycle.setAdapter(myFootAdapter);
xrecycle.setLoadingListener(new XRecyclerView.LoadingListener() {
    @Override
    public void onRefresh() {
        page=1;
        iPresenter.startRequest(HttpModel.GET,"commodity/verify/v1/browseList?page="+page+"&count=10",null,MyFootBean.class);
    }

    @Override
    public void onLoadMore() {
        ++page;
        iPresenter.startRequest(HttpModel.GET,"commodity/verify/v1/browseList?page="+page+"&count=10",null,MyFootBean.class);
    }
});
    }

    @Override
    protected void initData() {

    }


    @Override
    public void getDataSuccess(Object data) {
        if(data instanceof MyFootBean){
            MyFootBean data1 = (MyFootBean) data;
            if(page==1){
                myFootAdapter.setMlssBeanList(data1.getResult());
            }else{
                myFootAdapter.addMlssBeanList(data1.getResult());
            }
            xrecycle.loadMoreComplete();
        }
    }

    @Override
    public void getDataFail(String error) {

    }
}
