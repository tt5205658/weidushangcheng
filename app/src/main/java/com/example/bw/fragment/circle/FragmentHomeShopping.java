package com.example.bw.fragment.circle;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bw.R;
import com.example.bw.adapter.fragmenthome.FragmentHomeShoppingAdapter;
import com.example.bw.bean.home.HomeShoppingBean;
import com.example.bw.bean.home.HomeSwitchCommodityBean;
import com.example.bw.bean.home.HomeSwitchEventBean;
import com.example.bw.fragment.HomeFragment;
import com.example.bw.presenter.IPresenterImpl;
import com.example.bw.utils.okhttputils.HttpModel;
import com.example.bw.view.IView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FragmentHomeShopping extends Fragment implements IView {

    @BindView(R.id.fragment_home_fragmentshopping_recycle)
    RecyclerView fragmentHomeFragmentshoppingRecycle;
    Unbinder unbinder;
    private IPresenterImpl iPresenter;
    private FragmentHomeShoppingAdapter shoppingAdapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        iPresenter = new IPresenterImpl(this);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_home_fragmentshopping, null);
        unbinder = ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        return view;
    }
    @Subscribe(threadMode = ThreadMode.MAIN ,sticky = true)
    public void setNetWork(HomeSwitchEventBean eventBean){
        //1是跳转过来的值
        if(eventBean.getType()==1){
            iPresenter.startRequest(HttpModel.GET,"commodity/v1/findCommodityByCategory?categoryId="+eventBean.getContent()+"&page=1&count=5",null,HomeSwitchCommodityBean.class);
        }
        if(eventBean.getType()==2){
            iPresenter.startRequest(HttpModel.GET,"commodity/v1/findCommodityByKeyword?keyword="+eventBean.getContent()+"&page=1&count=5",null,HomeSwitchCommodityBean.class);
        }
        if(eventBean.getType()==3){
            iPresenter.startRequest(HttpModel.GET,"commodity/v1/findCommodityListByLabel?labelId="+eventBean.getContent()+"&page=1&count=5",null,HomeSwitchCommodityBean.class);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        shoppingAdapter = new FragmentHomeShoppingAdapter(getActivity());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        fragmentHomeFragmentshoppingRecycle.setLayoutManager(gridLayoutManager);
        fragmentHomeFragmentshoppingRecycle.setAdapter(shoppingAdapter);
    }

    @Override
    public void getDataSuccess(Object data) {
        if (data instanceof HomeSwitchCommodityBean) {
            HomeSwitchCommodityBean data1 = (HomeSwitchCommodityBean) data;
            if(data1.getResult().size()>0){
                shoppingAdapter.setMlssBeanList(data1.getResult());
            }else{
                Toast.makeText(getActivity(),"没有相关商品",Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public void getDataFail(String error) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(unbinder!=null){
            unbinder.unbind();
        }

    }
}
