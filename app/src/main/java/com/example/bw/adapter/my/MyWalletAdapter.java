package com.example.bw.adapter.my;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bw.R;
import com.example.bw.bean.my.MyWalletBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyWalletAdapter extends RecyclerView.Adapter<MyWalletAdapter.ViewHoder> {

    private List<MyWalletBean.ResultBean.DetailListBean> mList;
    private Context mContext;

    public MyWalletAdapter(Context mContext) {
        this.mContext = mContext;
        mList = new ArrayList<>();

    }

    public void setmList(List<MyWalletBean.ResultBean.DetailListBean> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void addmList(List<MyWalletBean.ResultBean.DetailListBean> list) {
        if (list != null) {
            mList.addAll(list);
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public ViewHoder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_mywallet, viewGroup, false);
        return new ViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoder viewHoder, int i) {
        viewHoder.playprice.setText(mList.get(i).getAmount()+"");
        viewHoder.playtime.setText(stampToDate(mList.get(i).getConsumerTime()+""));
        viewHoder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myWalletCallBack.skip(mList.get(i).getOrderId()+"");
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class ViewHoder extends RecyclerView.ViewHolder {
        @BindView(R.id.playprice)
        TextView playprice;
        @BindView(R.id.playtime)
        TextView playtime;
        public ViewHoder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    private  String stampToDate(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }
    MyWalletCallBack myWalletCallBack;
    public interface MyWalletCallBack{
        void skip(String id);
    }
    public void setMyWalletCallBack (MyWalletCallBack callBack){
        myWalletCallBack=callBack;
    }
}
