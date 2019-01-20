package com.example.bw.adapter.my;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.bw.R;
import com.example.bw.bean.my.MyWalletBean;
import com.example.bw.bean.my.MyWalletData;
import com.example.bw.bean.orderform.AllBean;
import com.example.bw.bean.orderform.AppreaiseDataBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
public class MyWalletDataAdapter extends RecyclerView.Adapter<MyWalletDataAdapter.ViewHodel>{

        private List<MyWalletData.OrderInfoBean.DetailListBean> mList;
        private Context mContext;

        public MyWalletDataAdapter(Context mContext) {
            this.mContext = mContext;
            mList = new ArrayList<>();
        }

        public void setmList(List<MyWalletData.OrderInfoBean.DetailListBean> list) {
            this.mList = list;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ViewHodel onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_mywall_data, viewGroup, false);
            return new ViewHodel(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHodel viewHodel, int i) {

            String[] split = mList.get(i).getCommodityPic().split("\\,");
            Glide.with(mContext).load(split[0]).into(viewHodel.shoopingImage);
            viewHodel.shoppingTitle.setText(mList.get(i).getCommodityName());
            viewHodel.shoppingPrice.setText(mList.get(i).getCommodityPrice() + "");
            viewHodel.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    appraiseCallbackData.callback(mList.get(i).getCommodityId()+"");
                }
            });
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        static class ViewHodel extends RecyclerView.ViewHolder {
            @BindView(R.id.shooping_image)
            ImageView shoopingImage;
            @BindView(R.id.shopping_title)
            TextView shoppingTitle;
            @BindView(R.id.shopping_price)
            TextView shoppingPrice;


            public ViewHodel(@NonNull View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
        AppraiseCallbackData appraiseCallbackData;
        public interface AppraiseCallbackData{
            void callback(String id);
        }
        public void  setCallbackData(AppraiseCallbackData callbackData){
            appraiseCallbackData=callbackData;
        }
    }






