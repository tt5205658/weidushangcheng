package com.example.bw.fragment.orderform;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bw.R;
import com.example.bw.activity.orderform.PaymentOrderFromActivity;
import com.example.bw.adapter.orderform.OrderFromObligation;
import com.example.bw.base.basefragment.BaseFragment;
import com.example.bw.bean.orderform.AllBean;
import com.example.bw.bean.orderform.DeleteOrderFromBean;
import com.example.bw.bean.orderform.ObligationBean;
import com.example.bw.presenter.IPresenterImpl;
import com.example.bw.utils.okhttputils.HttpModel;
import com.example.bw.view.IView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class OrderFormObligationFragment extends BaseFragment implements IView {
    @BindView(R.id.obligation_xrecycleview)
    XRecyclerView obligationXrecycleview;
    Unbinder unbinder;
    private OrderFromObligation orderFromObligation;
    private IPresenterImpl iPresenter;
private int page;
    @Override
    protected int setViewID() {
        return R.layout.orderformobligationfragment;
    }

    @Override
    protected void initButterKnife(View view) {
        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    protected void initView() {
        iPresenter = new IPresenterImpl(this);
        page=1;
       initValue();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        obligationXrecycleview.setLayoutManager(layoutManager);
        orderFromObligation = new OrderFromObligation(getActivity());
        obligationXrecycleview.setAdapter(orderFromObligation);
        obligationXrecycleview.setPullRefreshEnabled(true);
        obligationXrecycleview.setLoadingMoreEnabled(false);
        obligationXrecycleview.setLoadingListener(new XRecyclerView.LoadingListener() {
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

        orderFromObligation.setOrderFromCallBack(new OrderFromObligation.OrderFromCallBack() {
            @Override
            public void deleteOrderFrom(String id) {
                iPresenter.startRequest(HttpModel.DELETE,"order/verify/v1/deleteOrder?orderId="+id,null,DeleteOrderFromBean.class);
            }

            @Override
            public void paymenOrderFrom(String id) {
                startActivity(new Intent(getActivity(),PaymentOrderFromActivity.class).putExtra("orderId",id));
            }
        });
    }

    private void initValue() {
        iPresenter.startRequest(HttpModel.GET,"order/verify/v1/findOrderListByStatus?status=1&page="+page+"&count=10",null,AllBean.class);
        page++;
    }

    @Override
    public void onResume() {
        super.onResume();
        iPresenter.startRequest(HttpModel.GET,"order/verify/v1/findOrderListByStatus?status=1&page=1&count=10",null,ObligationBean.class);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        iPresenter.onDetach();
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
            orderFromObligation.setmList(data1.getOrderList());
        }else if(data instanceof DeleteOrderFromBean){
            DeleteOrderFromBean data1 = (DeleteOrderFromBean) data;
            if(data1.getMessage().equals("删除成功")){
                Toast.makeText(getActivity(),data1.getMessage(),Toast.LENGTH_SHORT).show();
                iPresenter.startRequest(HttpModel.GET,"order/verify/v1/findOrderListByStatus?status=1&page="+page+"&count=10",null,AllBean.class);

            }
        }
        obligationXrecycleview.loadMoreComplete();
        obligationXrecycleview.refreshComplete();
    }

    @Override
    public void getDataFail(String error) {
        Toast.makeText(getActivity(),""+error,Toast.LENGTH_SHORT).show();
    }
}
