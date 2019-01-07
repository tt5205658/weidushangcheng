package com.example.bw.adapter.fragmenthome;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.bw.R;
import com.example.bw.bean.home.HomeShoppingBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


    public class FragmentHomeQualityAdapter extends RecyclerView.Adapter<FragmentHomeQualityAdapter.ViewHodel> {



        private List<HomeShoppingBean.ResultBean.PzshBean.CommodityListBeanX> mlssBeanList;
        private Context mContext;

        public FragmentHomeQualityAdapter(Context mContext) {
            this.mContext = mContext;
            mlssBeanList = new ArrayList<>();
        }

        public void setMlssBeanList(List<HomeShoppingBean.ResultBean.PzshBean.CommodityListBeanX> list) {
            mlssBeanList = list;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public FragmentHomeQualityAdapter.ViewHodel onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = View.inflate(mContext, R.layout.item_fragment_home_quality, null);
            return new ViewHodel(view);
        }

        @Override
        public void onBindViewHolder(@NonNull FragmentHomeQualityAdapter.ViewHodel viewHodel, int i) {
            viewHodel.itemFragmentHomeFashionPrice.setText("$" + mlssBeanList.get(i).getPrice());
            viewHodel.itemFragmentHomeFashionTitlt.setText(mlssBeanList.get(i).getCommodityName());
            Glide.with(mContext).load(mlssBeanList.get(i).getMasterPic()).into(viewHodel.itemFragmentHomeFashionImage);
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
            public ViewHodel(@NonNull View itemView) {
                super(itemView);
                ButterKnife.bind(this,itemView);
            }
        }
    }


