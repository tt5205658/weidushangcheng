package com.example.bw.adapter.orderform;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bw.R;
import com.example.bw.bean.orderform.AllBean;
import com.example.bw.bean.orderform.AppreaiseDataBean;
import com.example.bw.bean.orderform.ComleteBean;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CompleteOrderFromAdapter extends RecyclerView.Adapter<CompleteOrderFromAdapter.ViewHodel> {



    private List<AllBean.OrderListBean> mList;
    private Context mContext;

    public CompleteOrderFromAdapter(Context mContext) {
        this.mContext = mContext;
        mList = new ArrayList<>();
    }

    public void setmList(List<AllBean.OrderListBean> list) {
        mList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHodel onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //  View view = LayoutInflater.from(mContext).inflate(R.layout.item_orderfrom_obligation, viewGroup, false);
        View view = View.inflate(mContext, R.layout.item_orderfrom_comolete, null);
        return new ViewHodel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodel viewHodel, int i) {
        List<AllBean.OrderListBean.DetailListBean> detailList = mList.get(i).getDetailList();
        double price = 0d;
        int num = 0;
        for (int p = 0; p < detailList.size(); p++) {
            price += detailList.get(p).getCommodityPrice() * detailList.get(p).getCommodityCount();
            num = detailList.get(p).getCommodityCount();
        }

        viewHodel.ordernumber.setText(mList.get(i).getOrderId() + "");
        viewHodel.price.setText(price+"");
        viewHodel.orderTime.setText(mList.get(i).getOrderId().substring(0, 8));
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        viewHodel.orderfromData.setLayoutManager(layoutManager);
        ComleteOrderFromDataAdapter fromObligationData = new ComleteOrderFromDataAdapter(mContext);
        fromObligationData.setmList(mList.get(i).getDetailList());
        viewHodel.orderfromData.setAdapter(fromObligationData);

        viewHodel.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderFromCallBack.paymenOrderFrom(mList.get(i).getOrderId() + "");
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
        @BindView(R.id.deleteButton)
        ImageView deleteButton;
        @BindView(R.id.orderfrom_data)
        XRecyclerView orderfromData;
        @BindView(R.id.price)
        TextView price;
        @BindView(R.id.orderTime)
        TextView orderTime;

        public ViewHodel(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    OrderFromCallBack orderFromCallBack;

    public interface OrderFromCallBack {



        void paymenOrderFrom(String id);
    }

    public void setOrderFromCallBack(OrderFromCallBack callBack) {
        orderFromCallBack = callBack;
    }


}




