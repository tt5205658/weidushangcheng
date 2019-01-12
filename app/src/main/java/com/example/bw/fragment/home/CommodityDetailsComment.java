package com.example.bw.fragment.home;

import android.util.Log;
import android.view.View;

import com.example.bw.R;
import com.example.bw.base.basefragment.BaseFragment;
import com.example.bw.bean.home.FindCommodityDetailsById;

import butterknife.ButterKnife;

public class CommodityDetailsComment extends BaseFragment {
    private FindCommodityDetailsById findCommodityDetailsById;
    @Override
    protected int setViewID() {
        return R.layout.commoditydetails_comment;
    }

    @Override
    protected void initButterKnife(View view) {
        ButterKnife.bind(this,view);
    }

    public void setFindCommodityDetailsById(FindCommodityDetailsById data){
        findCommodityDetailsById=data;

    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("Tangyucheng","comment----pause");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("Tangyucheng","comment----resume");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }
}
