package com.example.bw.adapter.fragmenthome;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bw.R;
import com.example.bw.bean.home.HomePopupTwoBean;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentHomePopupTwoAdapter extends RecyclerView.Adapter<FragmentHomePopupTwoAdapter.ViewHodel> {
private List<HomePopupTwoBean.ResultBean>mList;
private Context mContext;

    public FragmentHomePopupTwoAdapter(Context mContext) {
        this.mContext = mContext;
        mList=new ArrayList<>();
    }
    public void setmList(List<HomePopupTwoBean.ResultBean>list){
        mList=list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHodel onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_popup_two,null);
        return new ViewHodel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodel viewHodel, int i) {
            viewHodel.tltle.setText(mList.get(i).getName());
            viewHodel.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callBakc.callBack(mList.get(i).getId()+"");
                }
            });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class ViewHodel extends RecyclerView.ViewHolder{
@BindView(R.id.item_popup_two_title)
TextView tltle;
        public ViewHodel(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    popupCallBakc callBakc;
    public interface popupCallBakc{
        void callBack(String code);
    }
    public void onCallBack(popupCallBakc bakc){
        callBakc=bakc;
    }
}
