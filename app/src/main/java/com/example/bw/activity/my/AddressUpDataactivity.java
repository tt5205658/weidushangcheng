package com.example.bw.activity.my;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bw.R;
import com.example.bw.base.baseactivity.BaseActivity;
import com.example.bw.bean.orderform.CollectSuccessBean;
import com.example.bw.presenter.IPresenterImpl;
import com.example.bw.utils.okhttputils.HttpModel;
import com.example.bw.view.IView;
import com.lljjcoder.citypickerview.widget.CityPicker;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddressUpDataactivity extends BaseActivity implements IView {
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.phone)
    EditText phone;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.addressData)
    EditText addressData;
    @BindView(R.id.youbian)
    EditText youbian;
    @BindView(R.id.addAddress)
    Button addAddress;
    @BindView(R.id.quxiao)
    Button quxiao;
    private IPresenterImpl iPresenter;
    private String ids;

    @Override
    protected int setViewID() {
        return R.layout.addressupdata;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        ids = getIntent().getStringExtra("id");
        String names = getIntent().getStringExtra("name");
        String phones = getIntent().getStringExtra("phone");
        String addresss = getIntent().getStringExtra("address");
        String codes = getIntent().getStringExtra("code");
        iPresenter = new IPresenterImpl(this);
        name.setText(names);
        phone.setText(phones);
        String[] split = addresss.split("\\ ");
        address.setText(split[0] + " " + split[1] + " " + split[2]+" "+split[4]);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 4; i < split.length; i++) {
            stringBuilder.append(split[i]);
        }
        addressData.setText(stringBuilder);
        youbian.setText(codes);
        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseArea(v);
            }
        });
    }

    @Override
    protected void initData() {

    }

    public void chooseArea(View view) {
        //判断输入法的隐藏状态
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
            selectAddress();//调用CityPicker选取区域
        }
    }

    private void selectAddress() {
        CityPicker cityPicker = new CityPicker.Builder(AddressUpDataactivity.this)
                .textSize(14)
                .title("地址选择")
                .titleBackgroundColor("#FFFFFF")
                .confirTextColor("#696969")
                .cancelTextColor("#696969")
                .province("北京省")
                .city("北京市")
                .district("朝阳区")
                .textColor(Color.parseColor("#000000"))
                .provinceCyclic(true)
                .cityCyclic(false)
                .districtCyclic(false)
                .visibleItemsCount(7)
                .itemPadding(10)
                .onlyShowProvinceAndCity(false)
                .build();
        cityPicker.show();
        //监听方法，获取选择结果
        cityPicker.setOnCityItemClickListener(new CityPicker.OnCityItemClickListener() {
            @Override
            public void onSelected(String... citySelected) {
                //省份
                String province = citySelected[0];
                //城市
                String city = citySelected[1];
                //区县（如果设定了两级联动，那么该项返回空）
                String district = citySelected[2];
                //邮编
                String code = citySelected[3];
                youbian.setText(code);
                //为TextView赋值
                address.setText(province.trim() + "  " + city.trim() + "  " + district.trim());
            }
        });
    }

    @Override
    public void getDataSuccess(Object data) {
        if(data instanceof CollectSuccessBean){
            CollectSuccessBean data1 = (CollectSuccessBean) data;
            Toast.makeText(AddressUpDataactivity.this,data1.getMessage(),Toast.LENGTH_SHORT).show();

            finish();
        }
    }

    @Override
    public void getDataFail(String error) {

    }



    @OnClick({R.id.addAddress, R.id.quxiao})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.addAddress:
                String userName = name.getText().toString().trim();
                String userPhone = phone.getText().toString().trim();
                String userAddress = address.getText().toString().trim();
                String userAddressData = addressData.getText().toString().trim();
                String userYoubian = youbian.getText().toString().trim();
                Map<String,String> map = new HashMap<>();
                map.put("id",ids);
                map.put("realName",userName);
                map.put("phone",userPhone);
                map.put("address",userAddress+" "+userAddressData);
                map.put("zipCode",userYoubian);
                iPresenter.startRequest(HttpModel.PUT,"user/verify/v1/changeReceiveAddress",map,CollectSuccessBean.class);
                break;
            case R.id.quxiao:
                finish();
                break;
        }
    }
}
