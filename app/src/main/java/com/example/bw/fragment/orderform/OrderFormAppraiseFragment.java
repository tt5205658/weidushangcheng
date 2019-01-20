package com.example.bw.fragment.orderform;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bw.R;
import com.example.bw.adapter.orderform.AppraiseOrderFromAdapter;
import com.example.bw.base.basefragment.BaseFragment;
import com.example.bw.bean.orderform.AllBean;
import com.example.bw.bean.orderform.AppreaiseDataBean;
import com.example.bw.presenter.IPresenterImpl;
import com.example.bw.utils.okhttputils.HttpModel;
import com.example.bw.view.IView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class OrderFormAppraiseFragment extends BaseFragment implements IView {

    @BindView(R.id.appraiseRecycle)
    XRecyclerView appraiseRecycle;
    Unbinder unbinder;
    private IPresenterImpl iPresenter;
    private AppraiseOrderFromAdapter appraiseOrderFromAdapter;

    @Override
    protected int setViewID() {
        return R.layout.orderformappraisefragment;
    }

    @Override
    protected void initButterKnife(View view) {
        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    public void onResume() {
        super.onResume();
        iPresenter.startRequest(HttpModel.GET, "order/verify/v1/findOrderListByStatus?status=2&page=1&count=10", null, AllBean.class);
    }

    @Override
    protected void initView() {
        iPresenter = new IPresenterImpl(this);
        iPresenter.startRequest(HttpModel.GET, "order/verify/v1/findOrderListByStatus?status=2&page=1&count=10", null, AllBean.class);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        appraiseRecycle.setLayoutManager(layoutManager);

        appraiseOrderFromAdapter = new AppraiseOrderFromAdapter(getActivity());
        appraiseRecycle.setAdapter(appraiseOrderFromAdapter);
            appraiseOrderFromAdapter.setOrderFromCallBack(new AppraiseOrderFromAdapter.OrderFromCallBack() {
                @Override
                public void gouappraise(String id) {
                    Toast.makeText(getActivity(),"去评价",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void paymenOrderFrom(String id) {
                    Toast.makeText(getActivity(),"删除?",Toast.LENGTH_SHORT).show();
                }
            });
appraiseRecycle.setLoadingListener(new XRecyclerView.LoadingListener() {
    @Override
    public void onRefresh() {

        appraiseRecycle.refreshComplete();
    }

    @Override
    public void onLoadMore() {
        appraiseRecycle.loadMoreComplete();
    }
});
    }

    @Override
    protected void initData() {

    }

    @Override
    public void getDataSuccess(Object data) {
        if (data instanceof AllBean) {
            AllBean data1 = (AllBean) data;
            List<AllBean.OrderListBean> orderList = data1.getOrderList();
            appraiseOrderFromAdapter.setmList(orderList);
        }

    }

    @Override
    public void getDataFail(String error) {

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
}
