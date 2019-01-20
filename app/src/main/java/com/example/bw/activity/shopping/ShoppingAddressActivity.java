package com.example.bw.activity.shopping;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bw.R;
import com.example.bw.adapter.orderform.CreateOrderFormAdapter;
import com.example.bw.adapter.orderform.CreateOrderFormAddressAdapter;
import com.example.bw.base.baseactivity.BaseActivity;
import com.example.bw.bean.orderform.CreateOrderFormBean;
import com.example.bw.bean.orderform.CreateOrderFormScuuessBean;
import com.example.bw.bean.orderform.SkipHome;
import com.example.bw.bean.shopping.QueryShoppingDataBean;
import com.example.bw.bean.user.ReceiveAddressList;
import com.example.bw.presenter.IPresenterImpl;
import com.example.bw.utils.okhttputils.HttpModel;
import com.example.bw.view.IView;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShoppingAddressActivity extends BaseActivity implements IView {


    @BindView(R.id.recycle)
    RecyclerView recycle;
    @BindView(R.id.price)
    TextView price;
    @BindView(R.id.commint)
    Button commint;
    @BindView(R.id.item_myaddress_userName)
    TextView itemMyaddressUserName;
    @BindView(R.id.item_myaddress_userPhone)
    TextView itemMyaddressUserPhone;
    @BindView(R.id.item_myaddress_userAddress)
    TextView itemMyaddressUserAddress;
    @BindView(R.id.recycleaddresslist)
    RecyclerView recycleaddresslist;
    @BindView(R.id.address_list)
    CheckBox addressList;
    private List<QueryShoppingDataBean.ResultBean> orderFrom;
    private IPresenterImpl iPresenter;
    private CreateOrderFormAddressAdapter addressAdapter;

    @Override
    protected int setViewID() {
        return R.layout.shopping_address_activity;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        iPresenter = new IPresenterImpl(this);
        orderFrom = (List<QueryShoppingDataBean.ResultBean>) getIntent().getSerializableExtra("orderFrom");
        initRecycle();
        initFragment();
        initAddressList();
    }

    private void initAddressList() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleaddresslist.setLayoutManager(layoutManager);
        addressAdapter = new CreateOrderFormAddressAdapter(this);
        recycleaddresslist.setAdapter(addressAdapter);
        iPresenter.startRequest(HttpModel.GET, "user/verify/v1/receiveAddressList", null, ReceiveAddressList.class);
        addressAdapter.setMyAddressCallBack(new CreateOrderFormAddressAdapter.MyAddressCallBack() {
            @Override
            public void setAddersFrom(ReceiveAddressList.ResultBean resultBean) {
                id=resultBean.getId();
                itemMyaddressUserName.setText(resultBean.getRealName());
                itemMyaddressUserAddress.setText(resultBean.getAddress());
                itemMyaddressUserPhone.setText(resultBean.getPhone());
                Toast.makeText(ShoppingAddressActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
                recycleaddresslist.setVisibility(View.INVISIBLE);
                addressList.setChecked(false);
            }
        });
    }

    private void initFragment() {
       /* FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_home_fragmentlayout,homeShopping);
        fragmentTransaction.commit();*/
    }

    private void initRecycle() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycle.setLayoutManager(layoutManager);
        CreateOrderFormAdapter orderFormAdapter = new CreateOrderFormAdapter(this);

        recycle.setAdapter(orderFormAdapter);
        orderFormAdapter.setmList(orderFrom);
        double prices = 0;
        int num =0;
        for (int i=0;i<orderFrom.size();i++){
            prices+=orderFrom.get(i).getPrice()*orderFrom.get(i).getCount();

            num+=orderFrom.get(i).getCount();
        }
        price.setText("共"+num+"件商品,需要付款"+prices+"元");
    }


    @Override
    protected void initData() {

    }
private int id;

    @Override
    public void getDataSuccess(Object data) {
        if (data instanceof ReceiveAddressList) {
            ReceiveAddressList data1 = (ReceiveAddressList) data;
            List<ReceiveAddressList.ResultBean> result = data1.getResult();
            addressAdapter.setmList(result);
            for (int i = 0; i < result.size(); i++) {
                if (result.get(i).getWhetherDefault() == 1) {
                    itemMyaddressUserName.setText(result.get(i).getRealName());
                    itemMyaddressUserAddress.setText(result.get(i).getAddress());
                    itemMyaddressUserPhone.setText(result.get(i).getPhone());
                    id=result.get(i).getId();
                }
            }
        }else
        if(data instanceof CreateOrderFormScuuessBean){
            CreateOrderFormScuuessBean data1 = (CreateOrderFormScuuessBean) data;
            String message = data1.getMessage();
            Toast.makeText(ShoppingAddressActivity.this,message,Toast.LENGTH_SHORT).show();
            if(message.equals("创建订单成功")){
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                alertDialog.setMessage("创建订单成功,是否去付款?");
                alertDialog.setPositiveButton("去付款", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        EventBus.getDefault().postSticky(new SkipHome("跳转"));
                        finish();
                    }
                });
                alertDialog.setNegativeButton("回首页", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        finish();
                    }
                });
                alertDialog.show();
            }
        }
    }

    @Override
    public void getDataFail(String error) {
        Toast.makeText(ShoppingAddressActivity.this,error,Toast.LENGTH_SHORT).show();
    }




    @OnClick({R.id.address_list, R.id.commint})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.address_list:
                if(addressList.isChecked()){
                    recycleaddresslist.setVisibility(View.VISIBLE);
                }else{
                    recycleaddresslist.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.commint:
                double prices = 0;
                List< CreateOrderFormBean>createList = new ArrayList<>();

                for (int i=0;i<orderFrom.size();i++){
                    prices+=orderFrom.get(i).getPrice()*orderFrom.get(i).getCount();
                    createList.add(new CreateOrderFormBean(orderFrom.get(i).getCommodityId(),orderFrom.get(i).getCount()));
                }
                String s = new Gson().toJson(createList);
                Map<String,String> map=new HashMap<>();
                map.put("orderInfo",s);
                map.put("totalPrice",prices+"");
                map.put("addressId",id+"");

                iPresenter.startRequest(HttpModel.POST,"order/verify/v1/createOrder",map,CreateOrderFormScuuessBean.class);

                break;
        }
    }
}
