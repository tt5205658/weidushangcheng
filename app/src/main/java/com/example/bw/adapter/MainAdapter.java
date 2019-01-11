/*
 * Copyright 2016 Yan Zhenjie
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.bw.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.bw.R;
import com.example.bw.bean.shopping.QueryShoppingDataBean;
import com.example.bw.view.CustomView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by YOLANDA on 2016/7/22.
 */
public class MainAdapter extends BaseAdapter<MainAdapter.ViewHolder> {

    private Context mContext;
    private List<QueryShoppingDataBean.ResultBean> mDataList;

    public MainAdapter(Context context) {
        super(context);
        mContext = context;
    }

    public void notifyDataSetChanged(List<QueryShoppingDataBean.ResultBean> dataList) {
        this.mDataList = dataList;
        super.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(getInflater().inflate(R.layout.item_menu_main, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.shoppingTitle.setText(mDataList.get(position).getCommodityName());
        holder.shoppingPrice.setText("$ "+mDataList.get(position).getPrice()+"");
        Glide.with(mContext).load(mDataList.get(position).getPic()).into(holder.shoopingImage);
        holder.addandlods.setEditText(mDataList.get(position).getCount());
        holder.checkboxSelect.setChecked(mDataList.get(position).isChecked());
        holder.checkboxSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.checkboxSelect.isChecked()){
                    mDataList.get(position).setChecked(true);
                    mainAdapterListener.setListener(mDataList);
                }else{
                    mDataList.get(position).setChecked(false);
                    mainAdapterListener.setListener(mDataList);
                }
            }
        });
        holder.addandlods.setCustomListener(new CustomView.CustomListener() {
            @Override
            public void jianjian(int count) {
                mDataList.get(position).setCount(count);
                mainAdapterListener.setListener(mDataList);
            }

            @Override
            public void shuruzhi(int count) {
                mDataList.get(position).setCount(count);
                mainAdapterListener.setListener(mDataList);
            }
        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.checkbox_select)
        CheckBox checkboxSelect;
        @BindView(R.id.shooping_image)
        ImageView shoopingImage;
        @BindView(R.id.shopping_title)
        TextView shoppingTitle;
        @BindView(R.id.shopping_price)
        TextView shoppingPrice;
        @BindView(R.id.addandlods)
        CustomView addandlods;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }


    }
    MainAdapterListener mainAdapterListener;
    public interface MainAdapterListener{
        void setListener(List<QueryShoppingDataBean.ResultBean>list);
    }
    public void setMainAdapterListener(MainAdapterListener listener){
        mainAdapterListener = listener;
    }

}
