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
import com.example.bw.bean.home.HomeSwitchCommodityBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentHomeShoppingAdapter extends RecyclerView.Adapter<FragmentHomeShoppingAdapter.ViewHodel> {



    private List<HomeSwitchCommodityBean.ResultBean> mlssBeanList;
    private Context mContext;

    public FragmentHomeShoppingAdapter(Context mContext) {
        this.mContext = mContext;
        mlssBeanList = new ArrayList<>();
    }

    public void setMlssBeanList(List<HomeSwitchCommodityBean.ResultBean> list) {
        mlssBeanList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHodel onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(mContext, R.layout.item_fragment_home_shopping, null);
        return new ViewHodel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodel viewHodel, int i) {
        viewHodel.itemFragmentHomeFashionPrice.setText("$" + mlssBeanList.get(i).getPrice()+".00");
        viewHodel.itemFragmentHomeFashionTitlt.setText(mlssBeanList.get(i).getCommodityName());
        viewHodel.itemFragmentHomeFashionNum.setText("已售"+mlssBeanList.get(i).getSaleNum() + "件");
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
        @BindView(R.id.item_fragment_home_fashion_num)
        TextView itemFragmentHomeFashionNum;
        public ViewHodel(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
    particularsCallBakc callBakc;
    public interface particularsCallBakc{
        void callBack(String code);
    }
    public void onCallBack(particularsCallBakc bakc){
        callBakc=bakc;
    }
}
