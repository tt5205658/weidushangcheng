package com.example.bw.fragment.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.bw.R;
import com.example.bw.base.basefragment.BaseFragment;
import com.example.bw.bean.home.FindCommodityDetailsById;
import com.stx.xhb.xbanner.XBanner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class CommodityDetailsShopping extends BaseFragment {

    @BindView(R.id.commoditydetails_shopping_banner)
    XBanner commoditydetailsShoppingBanner;
    @BindView(R.id.commoditydetails_shopping_price)
    TextView commoditydetailsShoppingPrice;
    @BindView(R.id.commoditydetails_shopping_num)
    TextView commoditydetailsShoppingNum;
    @BindView(R.id.commoditydetails_shopping_title)
    TextView commoditydetailsShoppingTitle;
    @BindView(R.id.commoditydetails_shopping_zhongliang)
    TextView commoditydetailsShoppingZhongliang;
    Unbinder unbinder;
    private FindCommodityDetailsById findCommodityDetailsById;

    @Override
    protected int setViewID() {
        return R.layout.commoditydetails_shopping;
    }

    @Override
    protected void initButterKnife(View view) {
        ButterKnife.bind(this,view);
    }

    public void setFindCommodityDetailsById(FindCommodityDetailsById data) {
        findCommodityDetailsById = data;
        FindCommodityDetailsById.ResultBean result = data.getResult();
        String picture = result.getPicture();
        List<String >bannerList=new ArrayList<>();
        String[] split = picture.split("\\,");
        for (int i=0;i<split.length;i++){
            bannerList.add(split[i]);
        }
        initXBanner(bannerList);
        commoditydetailsShoppingNum.setText("一共售了"+result.getCommentNum()+"件");
        commoditydetailsShoppingPrice.setText("$"+result.getPrice());
        commoditydetailsShoppingTitle.setText(result.getCommodityName());
        commoditydetailsShoppingZhongliang.setText("商品质量    "+result.getWeight());
    }

    private void initXBanner(List<String>list) {
        commoditydetailsShoppingBanner.loadImage(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                Glide.with(getActivity()).load((String)model).into((ImageView)view);
            }
        });
        commoditydetailsShoppingBanner.setData(list,null);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("Tangyucheng", "shopping----pause");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("Tangyucheng", "shopping----pause");
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(unbinder!=null){
            unbinder.unbind();
        }

    }


}
