package com.example.bw.fragment.home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bw.R;
import com.example.bw.base.baseactivity.BaseActivity;
import com.example.bw.bean.shopping.QueryShoppingDataBean;
import com.example.bw.bean.shopping.SyncShoppingCart;
import com.example.bw.bean.home.FindCommodityDetailsById;
import com.example.bw.bean.shopping.SyncShoppingScuuessBean;
import com.example.bw.presenter.IPresenterImpl;
import com.example.bw.utils.okhttputils.HttpModel;
import com.example.bw.view.IView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CommodityDetails extends BaseActivity implements IView {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.commoditydetails_head_shopping)
    TextView commoditydetailsHeadShopping;
    @BindView(R.id.commoditydetails_head_data)
    TextView commoditydetailsHeadData;
    @BindView(R.id.commoditydetails_head_pinglun)
    TextView commoditydetailsHeadPinglun;
    @BindView(R.id.commoditydetails_fragment_shopping)
    FrameLayout commoditydetailsFragmentShopping;
    @BindView(R.id.commoditydetails_fragment_data)
    FrameLayout commoditydetailsFragmentData;
    @BindView(R.id.commoditydetails_fragment_pinglun)
    FrameLayout commoditydetailsFragmentPinglun;
    @BindView(R.id.sc)
    ScrollView scrollView;
    private IPresenterImpl iPresenter;
    private CommodityDetailsComment commodityDetailsComment;
    private CommodityDetailsData commodityDetailsData;
    private CommodityDetailsShopping commodityDetailsShopping;
    private FindCommodityDetailsById data1;
    List<SyncShoppingCart>list;
    @Override
    protected int setViewID() {
        return R.layout.commoditydetails;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        iPresenter = new IPresenterImpl(this);

        Intent intent = getIntent();
        String commodityId = intent.getStringExtra("CommodityId");
        iPresenter.startRequest(HttpModel.GET, "commodity/v1/findCommodityDetailsById?commodityId=" + commodityId, null, FindCommodityDetailsById.class);


        initFragment();
    }

    private void initFragment() {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        commodityDetailsComment = new CommodityDetailsComment();
        fragmentTransaction.add(R.id.commoditydetails_fragment_pinglun, commodityDetailsComment);
        fragmentTransaction.commit();

        FragmentTransaction fragmentTransactionData = supportFragmentManager.beginTransaction();
        commodityDetailsData = new CommodityDetailsData();
        fragmentTransactionData.add(R.id.commoditydetails_fragment_data, commodityDetailsData);
        fragmentTransactionData.commit();

        FragmentTransaction fragmentTransactionShopping = supportFragmentManager.beginTransaction();
        commodityDetailsShopping = new CommodityDetailsShopping();
        fragmentTransactionShopping.add(R.id.commoditydetails_fragment_shopping, commodityDetailsShopping);
        fragmentTransactionShopping.commit();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initData() {
        scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                Log.i("Tangyucheng", "scrollX->" + scrollX + "      scrollY->" + scrollY + "      oldScrollX->" + oldScrollX + "     oldScrollY_>" + oldScrollY);

                if (scrollY < 1000) {
                    commoditydetailsHeadShopping.setTextColor(Color.parseColor("#FF0000"));
                    commoditydetailsHeadData.setTextColor(Color.parseColor("#FFFFFF"));
                    commoditydetailsHeadPinglun.setTextColor(Color.parseColor("#FFFFFF"));
                } else if (scrollY > 1000 && scrollY < 2500) {

                    commoditydetailsHeadShopping.setTextColor(Color.parseColor("#FFFFFF"));
                    commoditydetailsHeadData.setTextColor(Color.parseColor("#FF0000"));
                    commoditydetailsHeadPinglun.setTextColor(Color.parseColor("#FFFFFF"));
                } else if (scrollY > 2500) {
                    commoditydetailsHeadShopping.setTextColor(Color.parseColor("#FFFFFF"));
                    commoditydetailsHeadData.setTextColor(Color.parseColor("#FFFFFF"));
                    commoditydetailsHeadPinglun.setTextColor(Color.parseColor("#FF0000"));
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iPresenter.onDetach();
    }

    @OnClick({R.id.back, R.id.commoditydetails_head_shopping, R.id.commoditydetails_head_data, R.id.commoditydetails_head_pinglun, R.id.commoditydetails_buttom_add, R.id.commoditydetails_buttom_buy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                break;
            case R.id.commoditydetails_head_shopping:
                break;
            case R.id.commoditydetails_head_data:
                break;
            case R.id.commoditydetails_head_pinglun:
                break;
            case R.id.commoditydetails_buttom_add:

             //   iPresenter.startRequest(HttpModel.GET,"order/verify/v1/findShoppingCart",null,);
                iPresenter.startRequest(HttpModel.GET,"order/verify/v1/findShoppingCart",null,QueryShoppingDataBean.class);


                break;
            case R.id.commoditydetails_buttom_buy:
                break;
        }
    }

    @Override
    public void getDataSuccess(Object data) {
        if (data instanceof FindCommodityDetailsById) {
            data1 = (FindCommodityDetailsById) data;
            commodityDetailsShopping.setFindCommodityDetailsById(data1);
            commodityDetailsData.setFindCommodityDetailsById(data1);
            commodityDetailsComment.setFindCommodityDetailsById(data1);
        }else
        if(data instanceof QueryShoppingDataBean){
            QueryShoppingDataBean queryBean = (QueryShoppingDataBean) data;
            list = new ArrayList<>();
            List<QueryShoppingDataBean.ResultBean> result = queryBean.getResult();
            boolean b=true;
            for(int i=0;i<result.size();i++){
                if(result.get(i).getCommodityId()!=data1.getResult().getCommodityId()){

                    list.add(new SyncShoppingCart(result.get(i).getCommodityId(),result.get(i).getCount()));
                }else{
                    for (int a =0;a<list.size();a++){
                        int count = list.get(a).getCount();
                        list.get(a).setCount(count+1);
                    }
                    Toast.makeText(this,"已存在",Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            list.add(new SyncShoppingCart(data1.getResult().getCommodityId(),data1.getResult().getCommentNum()));
            Toast.makeText(this,"商品添加完成",Toast.LENGTH_SHORT).show();
            Gson gson = new Gson();
            String s = gson.toJson(list);
            Map<String,String>map = new HashMap<>();
            map.put("data",s);
            iPresenter.startRequest(HttpModel.PUT,"order/verify/v1/syncShoppingCart",map,SyncShoppingScuuessBean.class);
        }else if(data instanceof SyncShoppingScuuessBean){
            SyncShoppingScuuessBean syncSuccess = (SyncShoppingScuuessBean) data;
            if(syncSuccess.equals("同步成功")){
                Toast.makeText(CommodityDetails.this,"添加成功",Toast.LENGTH_SHORT).show();
            }

        }

    }

    @Override
    public void getDataFail(String error) {

    }


}
