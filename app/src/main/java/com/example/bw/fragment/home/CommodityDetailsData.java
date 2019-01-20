package com.example.bw.fragment.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.bw.R;
import com.example.bw.base.basefragment.BaseFragment;
import com.example.bw.bean.home.FindCommodityDetailsById;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CommodityDetailsData extends BaseFragment {
    @BindView(R.id.commoditydetails_data_webview)
    WebView commoditydetailsDataWebview;
    Unbinder unbinder;
    private FindCommodityDetailsById findCommodityDetailsById;

    @Override
    protected int setViewID() {
        return R.layout.commoditydetails_data;
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("Tangyucheng", "data----pause");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("Tangyucheng", "comment----resume");
    }

    public void setFindCommodityDetailsById(FindCommodityDetailsById data) {
        findCommodityDetailsById = data;
        FindCommodityDetailsById.ResultBean result = data.getResult();
        commoditydetailsDataWebview.loadUrl(result.getDetails());
        commoditydetailsDataWebview.getSettings().setUseWideViewPort(false);
        commoditydetailsDataWebview.getSettings().setLoadWithOverviewMode(true);
      //  commoditydetailsDataWebview.getSettings().setBuiltInZoomControls(true);
        //支持javascript
        commoditydetailsDataWebview.getSettings().setJavaScriptEnabled(true);
        // 设置可以支持缩放
        commoditydetailsDataWebview.getSettings().setSupportZoom(true);
        // 设置出现缩放工具
        commoditydetailsDataWebview.getSettings().setBuiltInZoomControls(true);
        //扩大比例的缩放
        commoditydetailsDataWebview.getSettings().setUseWideViewPort(true);

        //自适应屏幕
        commoditydetailsDataWebview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        commoditydetailsDataWebview.getSettings().setLoadWithOverviewMode(true);

        commoditydetailsDataWebview.loadDataWithBaseURL(null, result.getDetails(), "text/html", "utf-8", null);
    }

    @Override
    protected void initButterKnife(View view) {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
