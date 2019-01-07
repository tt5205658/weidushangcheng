package com.example.bw.fragment;


import android.content.SharedPreferences;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.bw.R;
import com.example.bw.adapter.FragmentCircleRecycleAdapter;
import com.example.bw.base.basefragment.BaseFragment;
import com.example.bw.bean.circle.FindCircleListBean;
import com.example.bw.presenter.IPresenterImpl;
import com.example.bw.utils.okhttputils.HttpModel;
import com.example.bw.view.IView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;

public class CircleFragment extends BaseFragment implements IView {
    IPresenterImpl iPresenter;
    @BindView(R.id.fragment_circle_xrecycleview)
    XRecyclerView xrecyclerView;
    private FragmentCircleRecycleAdapter circleRecycleAdapter;
    private SharedPreferences sharedPreferences;
    private String userId;
    private String sessionId;
    private int page ;
    private final int COUNT=5;
    @Override
    protected int setViewID() {
        return R.layout.fragment_circle;
    }

    @Override
    protected void initButterKnife(View view) {
        ButterKnife.bind(this, view);
    }

    @Override
    protected void initView() {
        page=1;
        initRecycle();
        iPresenter = new IPresenterImpl(this);
        initSharedPreferences();

    }

    private void initSharedPreferences() {
        sharedPreferences = getActivity().getSharedPreferences("User", MODE_PRIVATE);
        userId = sharedPreferences.getString("userId", null);
        sessionId = sharedPreferences.getString("sessionId", null);
    }

    private void initRecycle() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        xrecyclerView.setLayoutManager(layoutManager);
        xrecyclerView.setPullRefreshEnabled(true);
        xrecyclerView.setLoadingMoreEnabled(true);
        circleRecycleAdapter = new FragmentCircleRecycleAdapter(getActivity());
        xrecyclerView.setAdapter(circleRecycleAdapter);
        xrecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
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
    }

    @Override
    protected void initData() {

        iPresenter.startRequest(HttpModel.GET, "circle/v1/findCircleList?page="+page+"&count="+COUNT, null, FindCircleListBean.class);
    }

    @Override
    public void getDataSuccess(Object data) {
        if (data instanceof FindCircleListBean) {
            FindCircleListBean data1 = (FindCircleListBean) data;
            List<FindCircleListBean.ResultBean> result = data1.getResult();
            if(page==1){
                circleRecycleAdapter.setmList(result);
            }else {
                circleRecycleAdapter.addmList(result);
            }
            page++;
            xrecyclerView.loadMoreComplete();
            xrecyclerView.refreshComplete();
        }
    }

    @Override
    public void getDataFail(String error) {
        if (error != null && error.length() > 0) {
            String error1 = error;
        }
    }
}
