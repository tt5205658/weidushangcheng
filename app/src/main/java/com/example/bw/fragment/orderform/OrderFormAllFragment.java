package com.example.bw.fragment.orderform;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bw.R;
//import com.example.bw.adapter.orderform.AllAdapter;


import com.example.bw.adapter.orderform.AllAdapter;
import com.example.bw.base.basefragment.BaseFragment;
import com.example.bw.bean.orderform.AllBean;
import com.example.bw.bean.orderform.AppreaiseDataBean;
import com.example.bw.model.IModel;
import com.example.bw.presenter.IPresenterImpl;
import com.example.bw.utils.okhttputils.HttpModel;
import com.example.bw.view.IView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class OrderFormAllFragment extends BaseFragment implements IView {
    @BindView(R.id.allXRecycle)
    XRecyclerView allRecycle;
    Unbinder unbinder;
    private AllAdapter allAdapter;
    private IPresenterImpl iPresenter;

    @Override
    protected int setViewID() {
        return R.layout.orderformallfragment;
    }

    @Override
    protected void initButterKnife(View view) {
        unbinder = ButterKnife.bind(this, view);
    }
private int page;
    @Override
    protected void initView() {
        page=1;
        iPresenter = new IPresenterImpl(this);
        initValue();
    }

    private void initValue() {
        iPresenter.startRequest(HttpModel.GET, "order/verify/v1/findOrderListByStatus?status=0&page="+page+"&count=10", null, AllBean.class);
        page++;
    }

    @Override
    protected void initData() {
       LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        allRecycle.setLayoutManager(layoutManager);
        allAdapter = new AllAdapter(getActivity());
        allRecycle.setLoadingMoreEnabled(true);
        allRecycle.setPullRefreshEnabled(true);
       allRecycle.setAdapter(allAdapter);
       allRecycle.setLoadingListener(new XRecyclerView.LoadingListener() {
           @Override
           public void onRefresh() {
               page=1;
               initValue();
           }

           @Override
           public void onLoadMore() {
                initValue();
           }
       });
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void getDataSuccess(Object data) {
        AllBean data1 = (AllBean) data;
        List<AllBean.OrderListBean> orderList = data1.getOrderList();
        if(page==1){
            allAdapter.setmList(orderList);
        }else{
            allAdapter.addmList(orderList);
        }

allRecycle.loadMoreComplete();
allRecycle.refreshComplete();
    }

    @Override
    public void getDataFail(String error) {

    }
}
