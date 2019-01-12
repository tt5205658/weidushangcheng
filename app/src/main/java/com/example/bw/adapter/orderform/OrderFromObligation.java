package com.example.bw.adapter.orderform;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.bw.R;
import com.example.bw.bean.orderform.ObligationBean;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderFromObligation extends RecyclerView.Adapter<OrderFromObligation.ViewHodel> {


    private List<ObligationBean.OrderListBean> mList;
    private Context mContext;

    public OrderFromObligation(Context mContext) {
        this.mContext = mContext;
        mList = new ArrayList<>();
    }

    public void setmList(List<ObligationBean.OrderListBean> list) {
        mList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHodel onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
      //  View view = LayoutInflater.from(mContext).inflate(R.layout.item_orderfrom_obligation, viewGroup, false);
View view = View.inflate(mContext,R.layout.item_orderfrom_obligation,null);
        return new ViewHodel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodel viewHodel, int i) {
        List<ObligationBean.OrderListBean.DetailListBean> detailList = mList.get(i).getDetailList();
        double price = 0d;
        int num=0;
        for(int p =0;p<detailList.size();p++){
            price += detailList.get(p).getCommodityPrice()*detailList.get(p).getCommodityCount();
            num = detailList.get(p).getCommodityCount();
        }
        viewHodel.price.setText("共"+num+"件商品,需付款"+price+"元");
        viewHodel.ordernumber.setText(mList.get(i).getOrderId());
        viewHodel.orderTime.setText(mList.get(i).getOrderId().substring(0,8));
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        viewHodel.orderfromData.setLayoutManager(layoutManager);
        OrderFromObligationData fromObligationData = new OrderFromObligationData(mContext);
        fromObligationData.setmList(mList.get(i).getDetailList());
        viewHodel.orderfromData.setAdapter(fromObligationData);

        viewHodel.cancelbtton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderFromCallBack.deleteOrderFrom(mList.get(i).getOrderId()+"");
            }
        });

        viewHodel.paymentbtton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderFromCallBack.paymenOrderFrom(mList.get(i).getOrderId()+"");
            }
        });


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class ViewHodel extends RecyclerView.ViewHolder {

        @BindView(R.id.ordernumber)
        TextView ordernumber;
        @BindView(R.id.orderTime)
        TextView orderTime;
        @BindView(R.id.orderfrom_data)
        XRecyclerView orderfromData;
        @BindView(R.id.price)
        TextView price;
        @BindView(R.id.cancelbtton)
        Button cancelbtton;
        @BindView(R.id.paymentbtton)
        Button paymentbtton;
        public ViewHodel(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    OrderFromCallBack orderFromCallBack;
    public interface OrderFromCallBack {
        void  deleteOrderFrom(String id);
        void  paymenOrderFrom(String id);
    }
    public void setOrderFromCallBack(OrderFromCallBack callBack){
        orderFromCallBack = callBack;
    }
}
