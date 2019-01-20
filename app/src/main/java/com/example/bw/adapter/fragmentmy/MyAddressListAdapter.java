package com.example.bw.adapter.fragmentmy;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.bw.R;
import com.example.bw.bean.user.ReceiveAddressList;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyAddressListAdapter extends RecyclerView.Adapter<MyAddressListAdapter.ViewHodel> {


    private Context mContext;
    private List<ReceiveAddressList.ResultBean> mList;

    public MyAddressListAdapter(Context mContext) {
        this.mContext = mContext;
        mList = new ArrayList<>();
    }

    public void setmList(List<ReceiveAddressList.ResultBean> list) {
        mList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHodel onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       // View view = LayoutInflater.from(mContext).inflate(R.layout.item_myaddress, null);
        View view = View.inflate(mContext,R.layout.item_myaddress,null);
        return new ViewHodel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodel viewHodel, int i) {
        viewHodel.itemMyaddressUserName.setText(mList.get(i).getRealName());
        viewHodel.itemMyaddressUserPhone.setText(mList.get(i).getPhone());
        viewHodel.itemMyaddressUserAddress.setText(mList.get(i).getAddress());
        viewHodel.itemMyaddressDefaultaddress.setChecked(mList.get(i).isEnter());
        viewHodel.itemMyaddressDefaultaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(viewHodel.itemMyaddressDefaultaddress.isChecked()){
                    for (int p =0;p<mList.size();p++){
                        mList.get(p).setWhetherDefault(2);
                    }
                    mList.get(i).setWhetherDefault(1);
                }

                myAddressCallBack.defauAddress(mList.get(i).getId());
                notifyDataSetChanged();
            }
        });
        viewHodel.itemMyaddressUpdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myAddressCallBack.upDataButton(mList.get(i).getId()+"",
                        mList.get(i).getRealName(),
                        mList.get(i).getPhone(),
                        mList.get(i).getAddress(),
                        mList.get(i).getZipCode()
                        );

            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class ViewHodel extends RecyclerView.ViewHolder {

        @BindView(R.id.item_myaddress_userName)
        TextView itemMyaddressUserName;
        @BindView(R.id.item_myaddress_userPhone)
        TextView itemMyaddressUserPhone;
        @BindView(R.id.item_myaddress_userAddress)
        TextView itemMyaddressUserAddress;
        @BindView(R.id.item_myaddress_defaultaddress)
        CheckBox itemMyaddressDefaultaddress;
        @BindView(R.id.item_myaddress_updata)
        Button itemMyaddressUpdata;
        @BindView(R.id.item_myaddress_del)
        Button itemMyaddressDel;
        public ViewHodel(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    MyAddressCallBack myAddressCallBack;
    public interface MyAddressCallBack{
        void upDataButton(String id,String name,String phone,String address,String code);
        void defauAddress(int id);
    }
    public void setMyAddressCallBack(MyAddressCallBack callBack){
        myAddressCallBack=callBack;
    }
}
