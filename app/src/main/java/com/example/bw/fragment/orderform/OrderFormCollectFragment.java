package com.example.bw.fragment.orderform;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bw.R;
import com.example.bw.adapter.orderform.CollectOrderFromAdapter;
import com.example.bw.base.basefragment.BaseFragment;
import com.example.bw.bean.orderform.CollectOrderFromBean;
import com.example.bw.bean.orderform.CollectSuccessBean;
import com.example.bw.bean.orderform.ObligationBean;
import com.example.bw.presenter.IPresenterImpl;
import com.example.bw.utils.okhttputils.HttpModel;
import com.example.bw.view.IView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class OrderFormCollectFragment extends BaseFragment implements IView {
    @BindView(R.id.obligation_xrecycleview)
    XRecyclerView colleobligationXrecycleview;
    Unbinder unbinder;
    private IPresenterImpl iPresenter;
    private CollectOrderFromAdapter orderFromAdapter;

    @Override
    protected int setViewID() {
        return R.layout.orderformcollectfragment;
    }

    @Override
    protected void initButterKnife(View view) {
        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    protected void initView() {
        iPresenter = new IPresenterImpl(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());

        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        colleobligationXrecycleview.setLayoutManager(layoutManager);
        orderFromAdapter = new CollectOrderFromAdapter(getActivity());
        colleobligationXrecycleview.setAdapter(orderFromAdapter);
        iPresenter.startRequest(HttpModel.GET,"order/verify/v1/findOrderListByStatus?status=2&page=1&count=10",null,CollectOrderFromBean.class);

        orderFromAdapter.setOrderFromCallBack(new CollectOrderFromAdapter.OrderFromCallBack() {
            @Override
            public void deleteOrderFrom(String id) {
                Map<String,String>map = new HashMap<>();
                map.put("orderId",id);
                iPresenter.startRequest(HttpModel.PUT,"order/verify/v1/confirmReceipt",map,CollectSuccessBean.class);
            }

            @Override
            public void paymenOrderFrom(String id) {

            }
        });
    }

   /* @Override
    public void onResume() {
        super.onResume();
        iPresenter.startRequest(HttpModel.GET,"order/verify/v1/findOrderListByStatus?status=2&page=1&count=10",null,CollectOrderFromBean.class);

    }*/

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
        if(data instanceof CollectOrderFromBean){
            CollectOrderFromBean data1 = (CollectOrderFromBean) data;
            orderFromAdapter.setmList(data1.getOrderList());
        }else if(data instanceof CollectSuccessBean){
            CollectSuccessBean data1 = (CollectSuccessBean) data;
            String message = data1.getMessage();
            if(message.equals("")){

            }
        }
    }

    @Override
    public void getDataFail(String error) {

    }
}
