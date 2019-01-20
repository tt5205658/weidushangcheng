package com.example.bw.activity.my;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.TextView;

import com.example.bw.R;
import com.example.bw.adapter.my.MyWalletDataAdapter;
import com.example.bw.base.baseactivity.BaseActivity;
import com.example.bw.bean.my.MyWalletData;
import com.example.bw.fragment.home.CommodityDetails;
import com.example.bw.presenter.IPresenterImpl;
import com.example.bw.utils.okhttputils.HttpModel;
import com.example.bw.view.IView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyWalletDataActivity extends BaseActivity implements IView {
    @BindView(R.id.ordernumber)
    TextView ordernumber;

    @BindView(R.id.orderfrom_data)
    XRecyclerView orderfromData;
    @BindView(R.id.expressCompName)
    TextView expressCompName;
    @BindView(R.id.expressSn)
    TextView expressSn;
    private MyWalletDataAdapter myWalletDataAdapter;

    @Override
    protected int setViewID() {
        return R.layout.activity_mywallet_data;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        IPresenterImpl iPresenter = new IPresenterImpl(this);
        String orderId = getIntent().getStringExtra("orderId");
        iPresenter.startRequest(HttpModel.GET, "order/verify/v1/findOrderInfo?orderId=" + orderId, null, MyWalletData.class);
    }

    @Override
    protected void initData() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        orderfromData.setLayoutManager(layoutManager);
        myWalletDataAdapter = new MyWalletDataAdapter(this);
        orderfromData.setAdapter(myWalletDataAdapter);
        myWalletDataAdapter.setCallbackData(new MyWalletDataAdapter.AppraiseCallbackData() {
            @Override
            public void callback(String id) {
                startActivity(new Intent(MyWalletDataActivity.this,CommodityDetails.class).putExtra("CommodityId",id));
            }
        });
    }

    @Override
    public void getDataSuccess(Object data) {
        if (data instanceof MyWalletData) {
            MyWalletData data1 = (MyWalletData) data;
            ordernumber.setText(data1.getOrderInfo().getOrderId());
            expressCompName.setText(data1.getOrderInfo().getExpressCompName());
            List<MyWalletData.OrderInfoBean.DetailListBean> detailList = data1.getOrderInfo().getDetailList();
            double price = 0d;
            int num=0;
            for(int p =0;p<detailList.size();p++){
                price += detailList.get(p).getCommodityPrice()*detailList.get(p).getCommodityCount();
                num = detailList.get(p).getCommodityCount();
            }
            expressSn.setText("共"+num+"件商品价值"+price+"元");
            myWalletDataAdapter.setmList(data1.getOrderInfo().getDetailList());

        }
    }
    public static String stampToDate(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }
    @Override
    public void getDataFail(String error) {

    }


}
