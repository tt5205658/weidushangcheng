package com.example.bw.adapter.fragmenthome;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bw.R;
import com.example.bw.bean.home.HomePopupOneBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentHomePopupOneAdapter extends RecyclerView.Adapter<FragmentHomePopupOneAdapter.ViewHodel> {
    private List<HomePopupOneBean.ResultBean>mList;
    private Context mContext;

    public FragmentHomePopupOneAdapter(Context mContext) {
        this.mContext = mContext;
        mList=new ArrayList<>();
    }
public void setmList(List<HomePopupOneBean.ResultBean>list){
        mList=list;
        notifyDataSetChanged();
}
    @NonNull
    @Override
    public ViewHodel onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_popup_one,viewGroup,false);
        return new ViewHodel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodel viewHodel, int i) {
        viewHodel.title.setText(mList.get(i).getName());
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
        @BindView(R.id.item_popup_one_title)
        TextView title;

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
