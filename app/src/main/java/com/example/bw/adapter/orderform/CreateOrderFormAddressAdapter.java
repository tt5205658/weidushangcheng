package com.example.bw.adapter.orderform;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
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

public class CreateOrderFormAddressAdapter extends RecyclerView.Adapter<CreateOrderFormAddressAdapter.ViewHodel> {
    private Context mContext;
    private List<ReceiveAddressList.ResultBean> mList;

    public CreateOrderFormAddressAdapter(Context mContext) {
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
        View view = View.inflate(mContext,R.layout.item_createorderfromaddress,null);
        return new ViewHodel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodel viewHodel, int i) {
        viewHodel.itemMyaddressUserName.setText(mList.get(i).getRealName());
        viewHodel.itemMyaddressUserPhone.setText(mList.get(i).getPhone());
        viewHodel.itemMyaddressUserAddress.setText(mList.get(i).getAddress());
        viewHodel.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReceiveAddressList.ResultBean addr = new ReceiveAddressList.ResultBean();
                addr.setWhetherDefault(mList.get(i).getWhetherDefault());
                addr.setAddress(mList.get(i).getAddress());
                addr.setId(mList.get(i).getId());
                addr.setCreateTime(mList.get(i).getCreateTime());
                addr.setPhone(mList.get(i).getPhone());
                addr.setRealName(mList.get(i).getRealName());
                addr.setUserId(mList.get(i).getUserId());
                addr.setZipCode(mList.get(i).getZipCode());
                myAddressCallBack.setAddersFrom(addr);
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
        public ViewHodel(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    MyAddressCallBack myAddressCallBack;
    public interface MyAddressCallBack{
        void setAddersFrom(ReceiveAddressList.ResultBean resultBean);
    }
    public void setMyAddressCallBack(MyAddressCallBack callBack){
        myAddressCallBack=callBack;
    }
}


