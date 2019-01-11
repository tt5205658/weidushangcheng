package com.example.bw.adapter.orderform;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.bw.R;
import com.example.bw.bean.shopping.QueryShoppingDataBean;
import com.example.bw.view.CustomView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateOrderFormAdapter extends RecyclerView.Adapter<CreateOrderFormAdapter.ViewHodel> {

    private Context mContext;
    private List<QueryShoppingDataBean.ResultBean> mList;

    public CreateOrderFormAdapter(Context mContext) {
        this.mContext = mContext;
        mList = new ArrayList<>();
    }

    public void setmList(List<QueryShoppingDataBean.ResultBean> list) {
        mList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHodel onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(mContext, R.layout.item_shoppingaddress, null);
        return new ViewHodel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodel viewHodel, int i) {
        viewHodel.shoppingPrice.setText("$"+mList.get(i).getPrice());
        viewHodel.shoppingTitle.setText(mList.get(i).getCommodityName());
        Glide.with(mContext).load(mList.get(i).getPic()).into(viewHodel.shoopingImage);
        viewHodel.addandlods.setEditText(mList.get(i).getCount());
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
        @BindView(R.id.addandlods)
        CustomView addandlods;

        public ViewHodel(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
