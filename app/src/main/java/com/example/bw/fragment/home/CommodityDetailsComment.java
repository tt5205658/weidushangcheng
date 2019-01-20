package com.example.bw.fragment.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bw.R;
import com.example.bw.adapter.fragmenthome.ShoppingPinglun;
import com.example.bw.bean.home.CommodityDetailsCommentBean;
import com.example.bw.presenter.IPresenterImpl;
import com.example.bw.utils.okhttputils.HttpModel;
import com.example.bw.view.IView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

@SuppressLint("ValidFragment")
public class CommodityDetailsComment extends Fragment implements IView {
    String id;
    @BindView(R.id.commoditydetails_comment_xrecycle)
    XRecyclerView commoditydetailsCommentXrecycle;
    private Unbinder bind;
    private IPresenterImpl iPresenter;
    private ShoppingPinglun shoppingPinglun;

    public CommodityDetailsComment(String id) {
        this.id = id;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.commoditydetails_comment, null);
        bind = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        iPresenter = new IPresenterImpl(this);
        iPresenter.startRequest(HttpModel.GET, "commodity/v1/CommodityCommentList?commodityId=" + id + "&page=1&count=10", null, CommodityDetailsCommentBean.class);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        commoditydetailsCommentXrecycle.setLayoutManager(layoutManager);
        shoppingPinglun = new ShoppingPinglun(getActivity());
        commoditydetailsCommentXrecycle.setPullRefreshEnabled(false);
        commoditydetailsCommentXrecycle.setLoadingMoreEnabled(false);
        commoditydetailsCommentXrecycle.setAdapter(shoppingPinglun);

    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        if (bind != null) {
            bind.unbind();
        }
    }


    @Override
    public void getDataSuccess(Object data) {
        if (data instanceof CommodityDetailsCommentBean) {
            CommodityDetailsCommentBean data1 = (CommodityDetailsCommentBean) data;
            shoppingPinglun.setmList(data1.getResult());
        }
    }

    @Override
    public void getDataFail(String error) {
        String error1 = error;
    }


}
