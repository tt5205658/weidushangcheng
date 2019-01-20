package com.example.bw.adapter.my;
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
import com.example.bw.bean.my.MycricleBean;
import com.example.bw.presenter.IPresenterImpl;
import com.example.bw.utils.okhttputils.HttpModel;
import com.example.bw.view.IView;
import com.example.bw.view.MultiImageView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static butterknife.ButterKnife.apply;

public class MyCricleAdapter extends RecyclerView.Adapter<MyCricleAdapter.ViewHodel>{

        private Context mContext;
        private List<MycricleBean.ResultBean> mList;

        private int whetherGreat;
        private boolean boo;

        public MyCricleAdapter(Context mContext) {
            this.mContext = mContext;
            mList=new ArrayList<>();
        }
        public void setmList(List<MycricleBean.ResultBean>list){
            mList.clear();
            if(list!=null){
                mList.addAll(list);
            }
            notifyDataSetChanged();

        }
        public void addmList(List<MycricleBean.ResultBean>list){

            if(list!=null){
                mList.addAll(list);
                notifyDataSetChanged();
            }

        }
        @NonNull
        @Override
        public ViewHodel onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = View.inflate(mContext, R.layout.item_fragment_circle, null);
            View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_activity_mycricle, viewGroup, false);
            return new ViewHodel(inflate);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHodel viewHodel, int i) {
            List<MycricleBean.ResultBean> mList = this.mList;
            viewHodel.itemFrgmentCircleTextcontent.setText(this.mList.get(i).getContent());
            viewHodel.itemFrgmentCircleTextname.setText(this.mList.get(i).getNickName());
            viewHodel.itemFrgmentCircleTextnum.setText(this.mList.get(i).getGreatNum()+"");

            viewHodel.itemFrgmentCircleTime.setText(stampToDate(mList.get(i).getCreateTime()+""));

            String image = mList.get(i).getImage();
            String[] split = image.split("\\,");
            List<String>sList=new ArrayList<>();
            for(int t=0;t<split.length;t++){
                sList.add(split[t]);
            }
            viewHodel.itemFrgmentCircleImagecontent.setList(sList);

            // Glide.with(mContext).load().into(viewHodel.itemFrgmentCircleImagecontent);
            RequestOptions requestOptions = RequestOptions.circleCropTransform();
            Glide.with(mContext).load(this.mList.get(i).getHeadPic()).apply(requestOptions).into(viewHodel.itemFrgmentCircleImageicon);
            viewHodel.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callBack.delete(mList.get(i).getId()+"");
                }
            });
        }

    public static String stampToDate(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

        @Override
        public int getItemCount() {
            return mList.size();
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
            MultiImageView itemFrgmentCircleImagecontent;
            @BindView(R.id.item_frgment_circle_textnum)
            TextView itemFrgmentCircleTextnum;
            @BindView(R.id.item_frgment_circle_imagezan)
            ImageView itemFrgmentCircleImagezan;
            @BindView(R.id.imagedelete)
            ImageView delete;
            public ViewHodel(@NonNull View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }

        CallBack callBack;
        public interface CallBack{
            void delete(String id);
        }
        public void setCallBack(CallBack back){
            callBack=back;
        }
    }


