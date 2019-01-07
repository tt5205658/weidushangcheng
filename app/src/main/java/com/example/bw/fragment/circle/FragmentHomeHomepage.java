package com.example.bw.fragment.circle;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.bw.R;
import com.example.bw.adapter.fragmenthome.FragmentHomeFashionAdapter;
import com.example.bw.adapter.fragmenthome.FragmentHomeHostAdapter;
import com.example.bw.adapter.fragmenthome.FragmentHomeQualityAdapter;
import com.example.bw.base.basefragment.BaseFragment;
import com.example.bw.bean.home.HomeBannerBean;
import com.example.bw.bean.home.HomeShoppingBean;
import com.example.bw.bean.home.HomeSwitchEventBean;
import com.example.bw.presenter.IPresenterImpl;
import com.example.bw.utils.okhttputils.HttpModel;
import com.example.bw.view.IView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.stx.xhb.xbanner.XBanner;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class FragmentHomeHomepage extends BaseFragment implements IView {


    @BindView(R.id.fragment_home_fragmenthome_xbanner_top)
    XBanner fragmentHomeFragmenthomeXbannerTop;
    @BindView(R.id.fragment_home_fragmenthome_recycle_hotrecommend)
    RecyclerView fragmentHomeFragmenthomeRecycleHotrecommend;
    @BindView(R.id.fragment_home_fragmenthome_recycle_fashion)
    XRecyclerView fragmentHomeFragmenthomeRecycleFashion;
    @BindView(R.id.fragment_home_fragmenthome_recycle_quality)
    XRecyclerView fragmentHomeFragmenthomeRecycleQuality;
    Unbinder unbinder;

    private Unbinder bind;
    private IPresenterImpl iPresenter;
    private FragmentHomeFashionAdapter fashionAdapter;
    private FragmentHomeQualityAdapter qualityAdapter;
    private FragmentHomeHostAdapter hostAdapter;
    private FragmentManager supportFragmentManager;
    private FragmentHomeShopping homeShopping;

    @Override
    protected int setViewID() {
        return R.layout.fragment_home_fragmenthome;
    }

    @Override
    protected void initButterKnife(View view) {
        bind = ButterKnife.bind(this, view);
    }

    @Override
    protected void initView() {
        supportFragmentManager = getActivity().getSupportFragmentManager();
        homeShopping = new FragmentHomeShopping();
        iPresenter = new IPresenterImpl(this);
        iPresenter.startRequest(HttpModel.GET, "commodity/v1/bannerShow", null, HomeBannerBean.class);
        iPresenter.startRequest(HttpModel.GET, "commodity/v1/commodityList", null, HomeShoppingBean.class);
    }

    @Override
    protected void initData() {
        initXBanner();
        initFashionAdapter();
        initHostAdapter();
        initQualityAdapter();
    }

    private void initQualityAdapter() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        fragmentHomeFragmenthomeRecycleQuality.setLoadingMoreEnabled(false);
        fragmentHomeFragmenthomeRecycleQuality.setPullRefreshEnabled(false);
        fragmentHomeFragmenthomeRecycleQuality.setLayoutManager(gridLayoutManager);
        qualityAdapter = new FragmentHomeQualityAdapter(getActivity());
        fragmentHomeFragmenthomeRecycleQuality.setAdapter(qualityAdapter);

    }

    private void initHostAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        /*fragmentHomeFragmenthomeRecycleHotrecommend.setLoadingMoreEnabled(false);
        fragmentHomeFragmenthomeRecycleHotrecommend.setPullRefreshEnabled(false);*/
        fragmentHomeFragmenthomeRecycleHotrecommend.setLayoutManager(linearLayoutManager);
        hostAdapter = new FragmentHomeHostAdapter(getActivity());
        fragmentHomeFragmenthomeRecycleHotrecommend.setAdapter(hostAdapter);
    }

    private void initFashionAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        fragmentHomeFragmenthomeRecycleFashion.setLoadingMoreEnabled(false);
        fragmentHomeFragmenthomeRecycleFashion.setPullRefreshEnabled(false);
        fragmentHomeFragmenthomeRecycleFashion.setLayoutManager(linearLayoutManager);
        fashionAdapter = new FragmentHomeFashionAdapter(getActivity());
        fragmentHomeFragmenthomeRecycleFashion.setAdapter(fashionAdapter);
    }

    private void initXBanner() {

        fragmentHomeFragmenthomeXbannerTop.loadImage(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                HomeBannerBean.ResultBean resultBean = (HomeBannerBean.ResultBean) model;
                Glide.with(getActivity()).load(resultBean.getImageUrl()).into((ImageView) view);
            }
        });
        fragmentHomeFragmenthomeXbannerTop.setOnItemClickListener(new XBanner.OnItemClickListener() {
            @Override
            public void onItemClick(XBanner banner, Object model, View view, int position) {
                HomeBannerBean.ResultBean resultBean = (HomeBannerBean.ResultBean) model;
               /* Intent intent = new Intent();
                intent.putExtra("jumpUrl",resultBean.getJumpUrl());*/
                Toast.makeText(getActivity(), "不知道跳不跳" + resultBean.getJumpUrl(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        fragmentHomeFragmenthomeXbannerTop.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        fragmentHomeFragmenthomeXbannerTop.stopAutoPlay();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }


    }

    @Override
    public void getDataSuccess(Object data) {
        if (data instanceof HomeBannerBean) {
            HomeBannerBean data1 = (HomeBannerBean) data;
            fragmentHomeFragmenthomeXbannerTop.setData(data1.getResult(), null);
        } else if (data instanceof HomeShoppingBean) {
            HomeShoppingBean data1 = (HomeShoppingBean) data;
            HomeShoppingBean.ResultBean result = data1.getResult();
            List<HomeShoppingBean.ResultBean.MlssBean.CommodityListBeanXX> mlssList = new ArrayList<>();
            List<HomeShoppingBean.ResultBean.PzshBean.CommodityListBeanX> pzshList = new ArrayList<>();
            List<HomeShoppingBean.ResultBean.RxxpBean.CommodityListBean> rxxpList = new ArrayList<>();
            for (int i = 0; i < result.getMlss().size(); i++) {
                List<HomeShoppingBean.ResultBean.MlssBean.CommodityListBeanXX> commodityList = result.getMlss().get(i).getCommodityList();
                mlssList.addAll(commodityList);
            }
            fashionAdapter.setMlssBeanList(mlssList);
            for (int i = 0; i < result.getPzsh().size(); i++) {
                List<HomeShoppingBean.ResultBean.PzshBean.CommodityListBeanX> commodityList = result.getPzsh().get(i).getCommodityList();
                pzshList.addAll(commodityList);
            }
            qualityAdapter.setMlssBeanList(pzshList);
            for (int i = 0; i < result.getMlss().size(); i++) {
                List<HomeShoppingBean.ResultBean.RxxpBean.CommodityListBean> commodityList = result.getRxxp().get(i).getCommodityList();
                rxxpList.addAll(commodityList);
            }
            hostAdapter.setMlssBeanList(rxxpList);

        }
    }

    @Override
    public void getDataFail(String error) {

    }



    @OnClick({R.id.rxtjimage, R.id.mlssimage, R.id.pzssimage})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rxtjimage:

                FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();

                fragmentTransaction.replace(R.id.fragment_home_fragmentlayout,homeShopping);
                fragmentTransaction.commit();
                EventBus.getDefault().postSticky(new HomeSwitchEventBean("1002",3));

                break;
            case R.id.mlssimage:

                FragmentTransaction fragmentTransaction1 = supportFragmentManager.beginTransaction();

                fragmentTransaction1.replace(R.id.fragment_home_fragmentlayout,homeShopping);
                fragmentTransaction1.commit();
                EventBus.getDefault().postSticky(new HomeSwitchEventBean("1003",3));
                break;
            case R.id.pzssimage:

                FragmentTransaction fragmentTransaction2 = supportFragmentManager.beginTransaction();

                fragmentTransaction2.replace(R.id.fragment_home_fragmentlayout,homeShopping);
                fragmentTransaction2.commit();
                EventBus.getDefault().postSticky(new HomeSwitchEventBean("1004",3));
                break;
        }
    }
}
