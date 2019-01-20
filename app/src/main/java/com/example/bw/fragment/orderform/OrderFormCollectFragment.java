package com.example.bw.fragment.orderform;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bw.R;
import com.example.bw.adapter.orderform.CollectOrderFromAdapter;
import com.example.bw.base.basefragment.BaseFragment;
import com.example.bw.bean.orderform.AllBean;
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
private int page;
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
        page = 1;
        iPresenter = new IPresenterImpl(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());

        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        colleobligationXrecycleview.setLayoutManager(layoutManager);
        orderFromAdapter = new CollectOrderFromAdapter(getActivity());
        colleobligationXrecycleview.setAdapter(orderFromAdapter);
        colleobligationXrecycleview.setLoadingMoreEnabled(false);
initValue();
        orderFromAdapter.setOrderFromCallBack(new CollectOrderFromAdapter.OrderFromCallBack() {


            @Override
            public void paymenOrderFrom(String id) {
                Map<String,String>map = new HashMap<>();
                map.put("orderId",id);
                iPresenter.startRequest(HttpModel.PUT,"order/verify/v1/confirmReceipt",map,CollectSuccessBean.class);
            }
        });
        colleobligationXrecycleview.setLoadingListener(new XRecyclerView.LoadingListener() {
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

    private void initValue() {
        iPresenter.startRequest(HttpModel.GET,"order/verify/v1/findOrderListByStatus?status=2&page="+page+"&count=10",null,AllBean.class);
        page++;
    }

    @Override
    public void onResume() {
        super.onResume();
      //  iPresenter.startRequest(HttpModel.GET,"order/verify/v1/findOrderListByStatus?status=2&page="+page+"&count=10",null,CollectOrderFromBean.class);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        iPresenter.onDetach();
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
            orderFromAdapter.setmList(data1.getOrderList());
        }else if(data instanceof CollectSuccessBean){
            CollectSuccessBean data1 = (CollectSuccessBean) data;
            String message = data1.getMessage();
            Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
            if(message.equals("确认收货成功")){
                iPresenter.startRequest(HttpModel.GET,"order/verify/v1/findOrderListByStatus?status=2&page="+page+"&count=10",null,AllBean.class);
            }
        }
        colleobligationXrecycleview.loadMoreComplete();
        colleobligationXrecycleview.refreshComplete();
    }

    @Override
    public void getDataFail(String error) {

    }
}
