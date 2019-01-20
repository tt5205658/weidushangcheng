package com.example.bw.adapter.my;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.bw.R;
import com.example.bw.bean.my.MyFootBean;
import com.example.bw.fragment.home.CommodityDetails;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyFootAdapter extends RecyclerView.Adapter<MyFootAdapter.ViewHodel> {

    private List<MyFootBean.ResultBean> mlssBeanList;
    private Context mContext;

    public MyFootAdapter(Context mContext) {
        this.mContext = mContext;
        mlssBeanList = new ArrayList<>();
    }

    public void setMlssBeanList(List<MyFootBean.ResultBean> list) {
        mlssBeanList.clear();
        mlssBeanList.addAll(list);
        notifyDataSetChanged();
    }
    public void addMlssBeanList(List<MyFootBean.ResultBean> list) {

        mlssBeanList.addAll(list);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHodel onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(mContext, R.layout.item_myfootprint, null);
        return new ViewHodel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodel viewHodel, int i) {
        viewHodel.itemFragmentHomeFashionPrice.setText("$" + mlssBeanList.get(i).getPrice() + ".00");
        viewHodel.itemFragmentHomeFashionTitlt.setText(mlssBeanList.get(i).getCommodityName());
        viewHodel.itemFragmentHomeFashionNum.setText("已浏览"+mlssBeanList.get(i).getBrowseNum()+"次");
        viewHodel.time.setText(stampToDate(mlssBeanList.get(i).getBrowseTime()+""));
        Glide.with(mContext).load(mlssBeanList.get(i).getMasterPic()).into(viewHodel.itemFragmentHomeFashionImage);
        viewHodel.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, CommodityDetails.class).putExtra("CommodityId", mlssBeanList.get(i).getCommodityId() + ""));
            }
        });

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
    public int getItemCount() {
        return mlssBeanList.size();
    }

    static class ViewHodel extends RecyclerView.ViewHolder {
        @BindView(R.id.item_fragment_home_fashion_image)
        ImageView itemFragmentHomeFashionImage;
        @BindView(R.id.item_fragment_home_fashion_titlt)
        TextView itemFragmentHomeFashionTitlt;
        @BindView(R.id.item_fragment_home_fashion_price)
        TextView itemFragmentHomeFashionPrice;
        @BindView(R.id.item_fragment_home_fashion_num)
        TextView itemFragmentHomeFashionNum;
        @BindView(R.id.time)
        TextView time;

        public ViewHodel(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    particularsCallBakc callBakc;

    public interface particularsCallBakc {
        void callBack(String code);
    }

    public void onCallBack(particularsCallBakc bakc) {
        callBakc = bakc;
    }
}


