package com.example.bw.fragment.orderform;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bw.R;
import com.example.bw.adapter.orderform.CompleteOrderFromAdapter;
import com.example.bw.base.basefragment.BaseFragment;
import com.example.bw.bean.orderform.AllBean;
import com.example.bw.bean.orderform.AppreaiseDataBean;
import com.example.bw.bean.orderform.ComleteBean;
import com.example.bw.bean.orderform.DeleteSuccessBean;
import com.example.bw.presenter.IPresenterImpl;
import com.example.bw.utils.okhttputils.HttpModel;
import com.example.bw.view.IView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class OrderFormCompleteFragment extends BaseFragment implements IView {
    @BindView(R.id.completeRecycle)
    XRecyclerView completeRecycle;
    Unbinder unbinder;
    private IPresenterImpl iPresenter;
    private CompleteOrderFromAdapter completeOrderFromAdapter;

    @Override
    protected int setViewID() {
        return R.layout.orderformcompletefragment;
    }

    @Override
    protected void initButterKnife(View view) {
        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    public void onResume() {
        super.onResume();
        iPresenter.startRequest(HttpModel.GET, "order/verify/v1/findOrderListByStatus?status=9&page=1&count=10", null, AllBean.class);
    }

    @Override
    protected void initView() {
        iPresenter = new IPresenterImpl(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        completeRecycle.setLayoutManager(layoutManager);
        completeOrderFromAdapter = new CompleteOrderFromAdapter(getActivity());
        completeRecycle.setAdapter(completeOrderFromAdapter);
        completeOrderFromAdapter.setOrderFromCallBack(new CompleteOrderFromAdapter.OrderFromCallBack() {

            @Override
            public void paymenOrderFrom(String id) {
                iPresenter.startRequest(HttpModel.DELETE,"order/verify/v1/deleteOrder?orderId="+id,null,DeleteSuccessBean.class);
              //  iPresenter.startRequest(HttpModel.GET, "order/verify/v1/findOrderListByStatus?status=2&page=1&count=10", null, AllBean.class);
            }
        });
        completeRecycle.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

                completeRecycle.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                completeRecycle.loadMoreComplete();
            }
        });
    }

    @Override
    protected void initData() {

    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void getDataSuccess(Object data) {
        if(data instanceof AllBean){
            AllBean data1 = (AllBean) data;
            List<AllBean.OrderListBean> orderList = data1.getOrderList();
            completeOrderFromAdapter.setmList(orderList);
        }else if( data instanceof DeleteSuccessBean){
            DeleteSuccessBean data1 = (DeleteSuccessBean) data;
            Toast.makeText(getActivity(),data1.getMessage(),Toast.LENGTH_SHORT).show();
            iPresenter.startRequest(HttpModel.GET, "order/verify/v1/findOrderListByStatus?status=2&page=1&count=10", null, AllBean.class);
        }

    }

    @Override
    public void getDataFail(String error) {

    }
}
