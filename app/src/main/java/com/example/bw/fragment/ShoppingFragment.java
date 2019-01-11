package com.example.bw.fragment;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bw.R;
import com.example.bw.activity.shopping.ShoppingAddressActivity;
import com.example.bw.adapter.MainAdapter;
import com.example.bw.base.basefragment.BaseFragment;
import com.example.bw.bean.orderform.CreateOrderFormBean;
import com.example.bw.bean.orderform.CreateOrderFormScuuessBean;
import com.example.bw.bean.shopping.QueryShoppingDataBean;
import com.example.bw.bean.shopping.SyncShoppingCart;
import com.example.bw.bean.shopping.SyncShoppingScuuessBean;
import com.example.bw.presenter.IPresenterImpl;
import com.example.bw.utils.okhttputils.HttpModel;
import com.example.bw.view.IView;
import com.google.gson.Gson;
import com.yanzhenjie.recyclerview.swipe.SwipeItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yanzhenjie.recyclerview.swipe.widget.DefaultItemDecoration;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ShoppingFragment extends BaseFragment implements IView {
    @BindView(R.id.swRecycle)
    SwipeMenuRecyclerView swRecycle;
    @BindView(R.id.checkBox_SelectAll)
    CheckBox checkBoxSelectAll;
    @BindView(R.id.text_price)
    TextView textPrice;
    @BindView(R.id.button_jiesuan)
    Button buttonJiesuan;
    Unbinder unbinder;
    private MainAdapter menuAdapter;
    private IPresenterImpl iPresenter;
    private List<QueryShoppingDataBean.ResultBean> result;

    @Override
    protected int setViewID() {
        return R.layout.fragment_shopping;
    }

    @Override
    protected void initButterKnife(View view) {
        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    protected void initView() {
        initSwRecycle();
        checkBoxSelectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBoxSelectAll.isChecked()){
                    for(int i=0;i<result.size();i++){
                        result.get(i).setChecked(true);
                    }
                }else{
                    for(int i=0;i<result.size();i++){
                        result.get(i).setChecked(false);
                    }
                }

                double num=0;
                double price=0;
                for (int i = 0;i<result.size();i++){
                    if(result.get(i).isChecked()){
                        num=result.get(i).getCount()*result.get(i).getPrice();
                        price+=num;
                    }
                }
                textPrice.setText("$"+price);
                menuAdapter.notifyDataSetChanged();
            }
        });
        iPresenter = new IPresenterImpl(this);
        iPresenter.startRequest(HttpModel.GET,"order/verify/v1/findShoppingCart",null,QueryShoppingDataBean.class);


    }

    private void initSwRecycle() {

        swRecycle.setLayoutManager(new LinearLayoutManager(getContext()));
        swRecycle.addItemDecoration(
                new DefaultItemDecoration(ContextCompat.getColor(getContext(), R.color.divider_color)));

        swRecycle.setSwipeItemClickListener(mItemClickListener);
        swRecycle.setSwipeMenuCreator(mSwipeMenuCreator);
        swRecycle.setSwipeMenuItemClickListener(mMenuItemClickListener);

        menuAdapter = new MainAdapter(getContext());
        swRecycle.setAdapter(menuAdapter);
        List<QueryShoppingDataBean.ResultBean> dataList = new ArrayList<>();

        menuAdapter.notifyDataSetChanged(dataList);


    }

    /**
     * RecyclerView的Item点击监听。
     */
    private SwipeItemClickListener mItemClickListener = new SwipeItemClickListener() {
        @Override
        public void onItemClick(View itemView, int position) {
            Toast.makeText(getContext(), "第" + position + "个", Toast.LENGTH_SHORT).show();
        }
    };

    /**w
     * RecyclerView的Item中的Menu点击监听。
     */
    private SwipeMenuItemClickListener mMenuItemClickListener = new SwipeMenuItemClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge, int position) {
            menuBridge.closeMenu();

            int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
            int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。

            if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {
                Toast.makeText(getContext(), "list第" + position + "; 右侧菜单第" + menuPosition, Toast.LENGTH_SHORT).show();
            } else if (direction == SwipeMenuRecyclerView.LEFT_DIRECTION) {
                Toast.makeText(getContext(), "list第" + position + "; 左侧菜单第" + menuPosition, Toast.LENGTH_SHORT).show();
            }
        }
    };

    /**
     * 菜单创建器，在Item要创建菜单的时候调用。
     */
    private SwipeMenuCreator mSwipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
            int width = getResources().getDimensionPixelSize(R.dimen.dp_70);

            // 1. MATCH_PARENT 自适应高度，保持和Item一样高;
            // 2. 指定具体的高，比如80;
            // 3. WRAP_CONTENT，自身高度，不推荐;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;

            SwipeMenuItem addItem = new SwipeMenuItem(getContext()).setBackground(R.drawable.selector_green)
                    .setImage(R.mipmap.ic_action_add)
                    .setWidth(width)
                    .setHeight(height);
            swipeLeftMenu.addMenuItem(addItem); // 添加菜单到左侧。

            SwipeMenuItem closeItem = new SwipeMenuItem(getContext()).setBackground(R.drawable.selector_green)
                    .setImage(R.mipmap.ic_action_close)
                    .setWidth(width)
                    .setHeight(height);
            swipeRightMenu.addMenuItem(closeItem); // 添加菜单到右侧。
        }
    };

    @Override
    protected void initData() {
        menuAdapter.setMainAdapterListener(new MainAdapter.MainAdapterListener() {
            @Override
            public void setListener(List<QueryShoppingDataBean.ResultBean> list) {

                result=list;
                int tagNUm=0;
                int num=0;
                int price=0;
                for (int i = 0;i<list.size();i++){
                    tagNUm += result.get(i).getCount();
                    if(list.get(i).isChecked()){
                        num+=list.get(i).getCount();
                        price+=list.get(i).getCount()*list.get(i).getPrice();;
                    }
                }
                if(tagNUm==num){
                    checkBoxSelectAll.setChecked(true);
                }else{
                    checkBoxSelectAll.setChecked(false);
                }
                textPrice.setText("$"+price);
                List<SyncShoppingCart> syncList = new ArrayList<>();
                for(int i=0;i<list.size();i++){
                    syncList.add(new SyncShoppingCart(list.get(i).getCommodityId(),list.get(i).getCount()));
                }
                Gson gson = new Gson();
                String s = gson.toJson(syncList);
                Map<String,String> map = new HashMap<>();
                map.put("data",s);
                iPresenter.startRequest(HttpModel.PUT,"order/verify/v1/syncShoppingCart",map,SyncShoppingScuuessBean .class);

            }

        });
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.checkBox_SelectAll, R.id.button_jiesuan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.checkBox_SelectAll:

                break;
            case R.id.button_jiesuan:

                ArrayList<QueryShoppingDataBean.ResultBean>orderFrom = new ArrayList<>();

                for (int i =0;i<result.size();i++){
                    if(result.get(i).isChecked()){
                        orderFrom.add(result.get(i));
                    }
                }
                Intent intent = new Intent(getActivity(),ShoppingAddressActivity.class);

                intent.putParcelableArrayListExtra("orderFrom", orderFrom);
                getActivity().startActivity(intent);

                break;
        }
    }

    @Override
    public void getDataSuccess(Object data) {
        if(data instanceof QueryShoppingDataBean){
            QueryShoppingDataBean data1 = (QueryShoppingDataBean) data;
            result = data1.getResult();
            menuAdapter.notifyDataSetChanged(result);
        }
    }

    @Override
    public void getDataFail(String error) {

    }
}
