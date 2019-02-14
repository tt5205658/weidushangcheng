

package com.example.bw.adapter.orderform;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bw.R;
import com.example.bw.bean.orderform.AllBean;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AllAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context mContext;
    private List<AllBean.OrderListBean> mList;
    //待付款
    private final int TYPE_obligation=1;
    private final int TYPE_wait=2;
    private final int TYPE_remain=3;
    private final int TYPE_completed=9;
    private final int TYPE_none=-1;


    public AllAdapter(Context mContext) {
        this.mContext = mContext;
        mList = new ArrayList<>();
    }

    public void setmList(List<AllBean.OrderListBean> list) {
        mList = list;
        notifyDataSetChanged();
    }
    public void addmList(List<AllBean.OrderListBean> list) {

       if(list!=null){
           mList.addAll(list);
           notifyDataSetChanged();
       }

    }
    @Override
    public int getItemViewType(int position) {

        if(mList.get(position).getOrderStatus()==TYPE_obligation){
            return TYPE_obligation;
        }else if(mList.get(position).getOrderStatus()==TYPE_wait){
            return TYPE_wait;
        }else if(mList.get(position).getOrderStatus()==TYPE_remain){
            return TYPE_remain;
        }else if(mList.get(position).getOrderStatus()==TYPE_completed){
            return TYPE_completed;
        }else{
            return TYPE_none;
        }


    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        switch (i){
            case TYPE_obligation:
                //待付款
                View obligation=LayoutInflater.from(mContext).inflate(R.layout.item_orderfrom_obligation,viewGroup,false);
                return new ViewObligation(obligation);
            case TYPE_wait:
                //待收货
                View wait=LayoutInflater.from(mContext).inflate(R.layout.item_orderfrom_collect,viewGroup,false);
                return new ViewWait(wait);
            case TYPE_remain:
                //待评价
                View remain=LayoutInflater.from(mContext).inflate(R.layout.item_appraise,viewGroup,false);
                return new ViewRemain(remain);
            case TYPE_completed:
                //已完成
                View completed=LayoutInflater.from(mContext).inflate(R.layout.item_orderfrom_comolete,viewGroup,false);
                return new ViewCompleted(completed);
            default:
                return null;
        }
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        int itemViewType = getItemViewType(i);
       // final ListBean.Result result = list.get(i);
        switch (itemViewType){
            case TYPE_obligation:
                //待付款
                ViewObligation viewObligation= (ViewObligation) viewHolder;
                List<AllBean.OrderListBean.DetailListBean> detailList = mList.get(i).getDetailList();
                double price = 0d;
                int num=0;
                for(int p =0;p<detailList.size();p++){
                    price += detailList.get(p).getCommodityPrice()*detailList.get(p).getCommodityCount();
                    num = detailList.get(p).getCommodityCount();
                }
                viewObligation.price.setText("共"+num+"件商品,需付款"+price+"元");
                viewObligation.ordernumber.setText(mList.get(i).getOrderId());
                viewObligation.orderTime.setText(mList.get(i).getOrderId().substring(0,8));
                LinearLayoutManager viewObligationlayoutManager = new LinearLayoutManager(mContext);
                viewObligationlayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                viewObligation.orderfromData.setLayoutManager(viewObligationlayoutManager);
                OrderFromObligationData viewObligationfromObligationData = new OrderFromObligationData(mContext);
                viewObligationfromObligationData.setmList(mList.get(i).getDetailList());
                viewObligation.orderfromData.setAdapter(viewObligationfromObligationData);

                viewObligation.cancelbtton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       // orderFromCallBack.deleteOrderFrom(mList.get(i).getOrderId()+"");
                    }
                });

                viewObligation.paymentbtton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                      //  orderFromCallBack.paymenOrderFrom(mList.get(i).getOrderId()+"");
                    }
                });
                break;
            case TYPE_wait:
                //待收货
                ViewWait viewWait= (ViewWait) viewHolder;
                viewWait.expressCompName.setText(mList.get(i).getExpressCompName()+"");
                viewWait.expressSn.setText(mList.get(i).getExpressSn()+"");
                viewWait.ordernumber.setText(mList.get(i).getOrderId()+"");
                viewWait.orderTime.setText(mList.get(i).getOrderId().substring(0, 8));
                LinearLayoutManager viewWaitlayoutManager = new LinearLayoutManager(mContext);
                viewWaitlayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                viewWait.orderfromData.setLayoutManager(viewWaitlayoutManager);
                CollectOrderFromDataAdapter fromObligationData = new CollectOrderFromDataAdapter(mContext);
                fromObligationData.setmList(mList.get(i).getDetailList());
                viewWait.orderfromData.setAdapter(fromObligationData);
                break;
            case TYPE_remain:
                //待评价
                ViewRemain viewRemain= (ViewRemain) viewHolder;
                viewRemain.ordernumber.setText(mList.get(i).getOrderId() + "");
                viewRemain.orderTime.setText(mList.get(i).getOrderId().substring(0, 8));
                LinearLayoutManager viewRemainlayoutManager = new LinearLayoutManager(mContext);
                viewRemainlayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                viewRemain.orderfromData.setLayoutManager(viewRemainlayoutManager);
                AppraiseOrderFromDataAdapter viewRemainfromObligationData = new AppraiseOrderFromDataAdapter(mContext);
                viewRemainfromObligationData.setmList(mList.get(i).getDetailList());
                viewRemain.orderfromData.setAdapter(viewRemainfromObligationData);
                viewRemainfromObligationData.setCallbackData(new AppraiseOrderFromDataAdapter.AppraiseCallbackData() {
                    @Override
                    public void callback(String id) {
                      //  orderFromCallBack.gouappraise(id);
                    }
                });

                viewRemain.deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                      //  orderFromCallBack.paymenOrderFrom(mList.get(i).getOrderId() + "");
                    }
                });
                break;
            case TYPE_completed:
                //已完成
                ViewCompleted viewCompleted= (ViewCompleted) viewHolder;
                List<AllBean.OrderListBean.DetailListBean> detailList2 = mList.get(i).getDetailList();
                double price2 = 0d;

                for (int p = 0; p < detailList2.size(); p++) {
                    price2 += detailList2.get(p).getCommodityPrice() * detailList2.get(p).getCommodityCount();
                    num = detailList2.get(p).getCommodityCount();
                }

                viewCompleted.ordernumber.setText(mList.get(i).getOrderId() + "");
                viewCompleted.price.setText(price2+"");
                viewCompleted.orderTime.setText(mList.get(i).getOrderId().substring(0, 8));
                LinearLayoutManager viewCompletedlayoutManager = new LinearLayoutManager(mContext);
                viewCompletedlayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                viewCompleted.orderfromData.setLayoutManager(viewCompletedlayoutManager);
                ComleteOrderFromDataAdapter viewCompletedfromObligationData = new ComleteOrderFromDataAdapter(mContext);
                viewCompletedfromObligationData.setmList(mList.get(i).getDetailList());
                viewCompleted.orderfromData.setAdapter(viewCompletedfromObligationData);

                viewCompleted.deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                     //   orderFromCallBack.paymenOrderFrom(mList.get(i).getOrderId() + "");
                    }
                });
                break;
            default:
                break;
        }

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    //待付款
     class ObligationViewHodel extends RecyclerView.ViewHolder {


        public ObligationViewHodel(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    //待评价
    class AppraiseViewHolder extends RecyclerView.ViewHolder {


        public AppraiseViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    //已完成
    class CommpleteViewHolder extends RecyclerView.ViewHolder {

        public CommpleteViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
    public class ViewObligation extends RecyclerView.ViewHolder{
        //待付款控件
        @BindView(R.id.ordernumber)
        TextView ordernumber;
        @BindView(R.id.orderTime)
        TextView orderTime;
        @BindView(R.id.orderfrom_data)
        XRecyclerView orderfromData;
        @BindView(R.id.price)
        TextView price;
        @BindView(R.id.cancelbtton)
        Button cancelbtton;
        @BindView(R.id.paymentbtton)
        Button paymentbtton;

        public ViewObligation(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    public class ViewWait extends RecyclerView.ViewHolder{
        //待收货
        @BindView(R.id.ordernumber)
        TextView ordernumber;
        @BindView(R.id.orderTime)
        TextView orderTime;
        @BindView(R.id.orderfrom_data)
        XRecyclerView orderfromData;
        @BindView(R.id.expressCompName)
        TextView expressCompName;
        @BindView(R.id.expressSn)
        TextView expressSn;
        @BindView(R.id.paymentbtton)
        Button paymentbtton;

        public ViewWait(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public class ViewRemain extends RecyclerView.ViewHolder{
        //待pingjia
        @BindView(R.id.ordernumber)
        TextView ordernumber;
        @BindView(R.id.delete_button)
        ImageView deleteButton;
        @BindView(R.id.orderfrom_data)
        XRecyclerView orderfromData;
        @BindView(R.id.orderTime)
        TextView orderTime;

        public ViewRemain(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public class ViewCompleted extends RecyclerView.ViewHolder{
        //已完成
        @BindView(R.id.ordernumber)
        TextView ordernumber;
        @BindView(R.id.deleteButton)
        ImageView deleteButton;
        @BindView(R.id.orderfrom_data)
        XRecyclerView orderfromData;
        @BindView(R.id.price)
        TextView price;
        @BindView(R.id.orderTime)
        TextView orderTime;

        public ViewCompleted(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

}


