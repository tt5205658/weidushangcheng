package com.example.bw.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.bumptech.glide.request.RequestOptions;
import com.example.bw.R;
import com.example.bw.bean.circle.AddCircleGreatBean;
import com.example.bw.bean.circle.CancelCircleGreatBean;
import com.example.bw.bean.circle.FindCircleListBean;
import com.example.bw.presenter.IPresenterImpl;
import com.example.bw.utils.okhttputils.HttpModel;
import com.example.bw.view.IView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static butterknife.ButterKnife.apply;

public class FragmentCircleRecycleAdapter extends RecyclerView.Adapter<FragmentCircleRecycleAdapter.ViewHodel> implements IView {

    private Context mContext;
    private List<FindCircleListBean.ResultBean> mList;
    private final IPresenterImpl iPresenter;
    private int whetherGreat;
    private boolean boo;

    public FragmentCircleRecycleAdapter(Context mContext) {
        this.mContext = mContext;
        iPresenter = new IPresenterImpl(this);
        mList=new ArrayList<>();
    }
public void setmList(List<FindCircleListBean.ResultBean>list){
        mList.clear();
        if(list!=null){
            mList.addAll(list);
        }
        notifyDataSetChanged();

}
public void addmList(List<FindCircleListBean.ResultBean>list){

        if(list!=null){
            mList.addAll(list);
            notifyDataSetChanged();
        }

}
    @NonNull
    @Override
    public ViewHodel onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(mContext, R.layout.item_fragment_circle, null);
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_fragment_circle, viewGroup, false);
        return new ViewHodel(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodel viewHodel, int i) {
        List<FindCircleListBean.ResultBean> mList = this.mList;
        viewHodel.itemFrgmentCircleTextcontent.setText(this.mList.get(i).getContent());
        viewHodel.itemFrgmentCircleTextname.setText(this.mList.get(i).getNickName());
        viewHodel.itemFrgmentCircleTextnum.setText(this.mList.get(i).getGreatNum()+"");
        viewHodel.itemFrgmentCircleTime.setText(this.mList.get(i).getCreateTime()+"");
        Glide.with(mContext).load(this.mList.get(i).getImage()).into(viewHodel.itemFrgmentCircleImagecontent);
       // Glide.with(mContext).load().into(viewHodel.itemFrgmentCircleImagecontent);
        RequestOptions requestOptions = RequestOptions.circleCropTransform();
        Glide.with(mContext).load(this.mList.get(i).getHeadPic()).apply(requestOptions).into(viewHodel.itemFrgmentCircleImageicon);

        if( this.mList.get(i).getWhetherGreat()==2){
            Glide.with(mContext).load(R.mipmap.common_btn_prise_n_xhdpi).into(viewHodel.itemFrgmentCircleImagezan);
        }else{
            Glide.with(mContext).load(R.mipmap.common_btn_prise_s_xxhdpi).into(viewHodel.itemFrgmentCircleImagezan);
        }

        viewHodel.itemFrgmentCircleImagezan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(FragmentCircleRecycleAdapter.this.mList.get(i).getWhetherGreat() ==2){
                    Map<String,String>map = new HashMap<>();
                    map.put("circleId", FragmentCircleRecycleAdapter.this.mList.get(i).getId()+"");
                    iPresenter.startRequest(HttpModel.POST,"circle/verify/v1/addCircleGreat",map,AddCircleGreatBean.class);
                }else{
                    Map<String,String>map = new HashMap<>();
                    map.put("circleId", FragmentCircleRecycleAdapter.this.mList.get(i).getId()+"");
                    iPresenter.startRequest(HttpModel.DELETE,"circle/verify/v1/cancelCircleGreat?circleId="+ FragmentCircleRecycleAdapter.this.mList.get(i).getId(),map,CancelCircleGreatBean.class);
                }
            }
        });
        setCallBackCircleGreat(new CallBackCircleGreat() {
            @Override
            public void cancelCircleGreatSuccess() {
                FragmentCircleRecycleAdapter.this.mList.get(i-1).setWhetherGreat(2);
                int greatNum = FragmentCircleRecycleAdapter.this.mList.get(i-1).getGreatNum();
                FragmentCircleRecycleAdapter.this.mList.get(i-1).setGreatNum(greatNum-1);
           //     Toast.makeText(mContext,"取消chenggong",Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();

            }

            @Override
            public void cancelCircleGreatFail() {
                Toast.makeText(mContext,"quxiao",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void addCircleGreatSuccess() {
                FragmentCircleRecycleAdapter.this.mList.get(i-1).setWhetherGreat(1);

                int greatNum = FragmentCircleRecycleAdapter.this.mList.get(i-1).getGreatNum();
                if(greatNum>0){
                    FragmentCircleRecycleAdapter.this.mList.get(i-1).setGreatNum(greatNum+1);
                }

              //  Toast.makeText(mContext,"添加chenggong",Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();

            }

            @Override
            public void addCircleGreatFail() {
                Toast.makeText(mContext,"chenggong",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public void getDataSuccess(Object data) {
        if(data instanceof AddCircleGreatBean){
            AddCircleGreatBean data1 = (AddCircleGreatBean) data;
            if(data1.getMessage().equals("点赞成功")){
                callBackCircleGreat.addCircleGreatSuccess();
            }else{
                callBackCircleGreat.addCircleGreatFail();
             }
            }else if(data instanceof CancelCircleGreatBean){
            CancelCircleGreatBean data1 = (CancelCircleGreatBean) data;
            if(data1.getMessage().equals("取消成功")){
                callBackCircleGreat.cancelCircleGreatSuccess();
            }else{
                callBackCircleGreat.cancelCircleGreatFail();
            }
        }

    }
    CallBackCircleGreat callBackCircleGreat;
    public interface CallBackCircleGreat {
        void cancelCircleGreatSuccess();
        void cancelCircleGreatFail();
        void addCircleGreatSuccess();
        void addCircleGreatFail();
    }
    public void setCallBackCircleGreat(CallBackCircleGreat back){
        callBackCircleGreat=back;
    }
    @Override
    public void getDataFail(String error) {
        Toast.makeText(mContext,"错误-->"+error,Toast.LENGTH_LONG).show();
    }

    static class ViewHodel extends RecyclerView.ViewHolder {
        @BindView(R.id.item_frgment_circle_imageicon)
        ImageView itemFrgmentCircleImageicon;
        @BindView(R.id.item_frgment_circle_textname)
        TextView itemFrgmentCircleTextname;
        @BindView(R.id.item_frgment_circle_time)
        TextView itemFrgmentCircleTime;
        @BindView(R.id.item_frgment_circle_textcontent)
        TextView itemFrgmentCircleTextcontent;
        @BindView(R.id.item_frgment_circle_imagecontent)
        ImageView itemFrgmentCircleImagecontent;
        @BindView(R.id.item_frgment_circle_textnum)
        TextView itemFrgmentCircleTextnum;
        @BindView(R.id.item_frgment_circle_imagezan)
        ImageView itemFrgmentCircleImagezan;
        public ViewHodel(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
