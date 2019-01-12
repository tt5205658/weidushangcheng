package com.example.bw.activity.orderform;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.bw.R;
import com.example.bw.activity.HomeViewpageActivity;
import com.example.bw.base.baseactivity.BaseActivity;
import com.example.bw.bean.orderform.PaySuccessBean;
import com.example.bw.bean.orderform.SkipFromOrderFrom;
import com.example.bw.presenter.IPresenterImpl;
import com.example.bw.utils.okhttputils.HttpModel;
import com.example.bw.view.IView;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PaymentOrderFromActivity extends BaseActivity implements IView {
    @BindView(R.id.remainingsum)
    CheckBox remainingsum;
    @BindView(R.id.weixin)
    CheckBox weixin;
    @BindView(R.id.zhifubao)
    CheckBox zhifubao;
    @BindView(R.id.enter_button)
    Button enterButton;
    @BindView(R.id.successhome)
    Button successhome;
    @BindView(R.id.successorderfrom)
    Button successorderfrom;
    @BindView(R.id.success_bg)
    ConstraintLayout successBg;
    @BindView(R.id.fail)
    Button fail;
    @BindView(R.id.fail_bg)
    ConstraintLayout failBg;
    private int payMethod;
    private IPresenterImpl iPresenter;
    private String orderId;

    @Override
    protected int setViewID() {
        return R.layout.activity_paymentorderfrom;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        payMethod=1;
        iPresenter = new IPresenterImpl(this);
        orderId = getIntent().getStringExtra("orderId");
        remainingsum.setChecked(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iPresenter.onDetach();
    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.remainingsum, R.id.weixin, R.id.zhifubao, R.id.enter_button,R.id.successhome, R.id.successorderfrom, R.id.fail})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case  R.id.fail:
                successBg.setVisibility(View.INVISIBLE);
                failBg.setVisibility(View.INVISIBLE);
                break;
            case  R.id.successorderfrom:
                EventBus.getDefault().post(new SkipFromOrderFrom(2));
                finish();
                break;
            case R.id.successhome:
                EventBus.getDefault().post(new SkipFromOrderFrom(1));
                finish();
                break;
            case R.id.remainingsum:
                if (remainingsum.isChecked()) {
                    payMethod = 1;
                    weixin.setChecked(false);
                    zhifubao.setChecked(false);
                } else {
                    payMethod = 4;
                }
                break;
            case R.id.weixin:
                if (weixin.isChecked()) {
                    payMethod = 3;
                    remainingsum.setChecked(false);
                    zhifubao.setChecked(false);
                } else {
                    payMethod = 4;
                }
                break;
            case R.id.zhifubao:
                if (zhifubao.isChecked()) {
                    payMethod = 2;
                    remainingsum.setChecked(false);
                    weixin.setChecked(false);
                } else {
                    payMethod = 4;
                }
                break;
            case R.id.enter_button:
                if (payMethod == 4) {
                    Toast.makeText(this, "请至少选择一种付款方式", Toast.LENGTH_SHORT).show();
                } else {
                    Map<String, String> map = new HashMap<>();
                    map.put("orderId", orderId);
                    map.put("payType", payMethod + "");
                    iPresenter.startRequest(HttpModel.POST, "order/verify/v1/pay", map, PaySuccessBean.class);

                }
                break;
        }
    }

    @Override
    public void getDataSuccess(Object data) {
        if (data instanceof PaySuccessBean) {
            PaySuccessBean data1 = (PaySuccessBean) data;
            if(data1.getMessage().equals("支付成功")){
                successBg.setVisibility(View.VISIBLE);
                failBg.setVisibility(View.INVISIBLE);
            }else{
                failBg.setVisibility(View.VISIBLE);
                successBg.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    public void getDataFail(String error) {
        String error1 = error;
    }




}
