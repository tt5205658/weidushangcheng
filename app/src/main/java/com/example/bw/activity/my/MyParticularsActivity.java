package com.example.bw.activity.my;

import android.app.AlertDialog;
import android.app.admin.SystemUpdatePolicy;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.bw.R;
import com.example.bw.base.baseactivity.BaseActivity;
import com.example.bw.bean.user.UpdataPwdBean;
import com.example.bw.bean.user.UpdateNameBean;
import com.example.bw.bean.user.UserDataBean;
import com.example.bw.presenter.IPresenterImpl;
import com.example.bw.utils.okhttputils.HttpModel;
import com.example.bw.view.IView;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyParticularsActivity extends BaseActivity implements IView {
    @BindView(R.id.myParticulars_myPic)
    ImageView myParticularsMyPic;
    @BindView(R.id.myParticulars_myName)
    TextView myParticularsMyName;
    @BindView(R.id.myParticulars_myPwd)
    TextView myParticularsMyPwd;
    private IPresenterImpl iPresenter;
    private String path = Environment.getExternalStorageDirectory ()+"/image.png";

    @Override
    protected int setViewID() {
        return R.layout.myparticularsactivity;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        iPresenter = new IPresenterImpl(this);
        iPresenter.startRequest(HttpModel.GET,"user/verify/v1/getUserById",null,UserDataBean.class);
    }

    @Override
    protected void initData() {

    }



    @OnClick({R.id.myParticulars_myPic, R.id.myParticulars_myName, R.id.myParticulars_myPwd})
    public void onViewClicked(View view) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        switch (view.getId()) {
            case R.id.myParticulars_myPic:

                View popupView = LayoutInflater.from(this).inflate(R.layout.popupcamera,null);
                PopupWindow  popupWindow = new PopupWindow(popupView,200,ViewGroup.LayoutParams.WRAP_CONTENT,true);
                popupWindow.setTouchable(true);
                popupWindow.setFocusable(true);
                popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                popupWindow.showAsDropDown(myParticularsMyPic,100,12);
                 popupView.findViewById(R.id.popupCamera_startCamera).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        // 存到sdcard中
                        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                                Uri.fromFile(new File(path)));
                        //执行
                        startActivityForResult(intent, 100);
                        popupWindow.dismiss();
                    }
                });
                 popupView.findViewById(R.id.popupCamera_startPhoto).setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         //加载相册
                         Intent intent = new Intent(Intent.ACTION_PICK);
                         //设置图片格式
                         intent.setType("image/*");
                         //执行
                         startActivityForResult(intent, 300);
                         popupWindow.dismiss();
                     }
                 });
                popupView.findViewById(R.id.popupCamera_cancle).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });




                break;
            case R.id.myParticulars_myName:
                View alertView = View.inflate(MyParticularsActivity.this,R.layout.alertdialog_name,null);
                alertDialog.setView(alertView);
                alertDialog.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        EditText newName = alertView.findViewById(R.id.doalogName);

                        String trim = newName.getText().toString().trim();
                        if(!trim.isEmpty()){
                            Map<String,String>map = new HashMap<>();
                            map.put("nickName",trim);
                            iPresenter.startRequest(HttpModel.PUT,"user/verify/v1/modifyUserNick",map,UpdateNameBean.class);
                            dialog.dismiss();
                        }
                    }
                });
                alertDialog.setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialog.show();
                break;
            case R.id.myParticulars_myPwd:
                View alertPwdView = View.inflate(MyParticularsActivity.this,R.layout.alertdialog_pwd,null);
                alertDialog.setView(alertPwdView);
                alertDialog.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        EditText oldPwd = alertPwdView.findViewById(R.id.oldPwd);
                        EditText newPwd = alertPwdView.findViewById(R.id.newPwd);

                        String oldPwds = oldPwd.getText().toString().trim();
                        String newPwds = newPwd.getText().toString().trim();
                        if(!oldPwds.isEmpty()&&!newPwds.isEmpty()){
                            Map<String,String>map = new HashMap<>();
                            map.put("oldPwd",oldPwds);
                            map.put("newPwd",newPwds);
                            iPresenter.startRequest(HttpModel.PUT,"user/verify/v1/modifyUserPwd",map,UpdataPwdBean.class);
                            dialog.dismiss();
                        }
                    }
                });
                alertDialog.setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialog.show();
                break;
        }
    }

    @Override
    public void getDataSuccess(Object data) {
        if(data instanceof  UserDataBean){
            UserDataBean data1 = (UserDataBean) data;
            Glide.with(this).load(data1.getResult().getHeadPic()).into(myParticularsMyPic);
            myParticularsMyName.setText (data1.getResult().getNickName());
            myParticularsMyPwd.setText(data1.getResult().getPassword()+"");
        }
        if(data instanceof UpdateNameBean){
            UpdateNameBean data1 = (UpdateNameBean) data;
            if(data1.getMessage().equals("修改成功")){
                finish();
            }
        }
        if(data instanceof UpdataPwdBean){
            UpdataPwdBean data1 = (UpdataPwdBean) data;
            if(data1.getMessage().equals("修改成功")){
                finish();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //相机
        if(requestCode == 100 && resultCode == RESULT_OK){
            //调取裁剪功能
            Intent intent = new Intent("com.android.camera.action.CROP");
            //将图片设置给裁剪
            intent.setDataAndType(Uri.fromFile(new File(path)), "image/*");
            //设置是否支持裁剪
            intent.putExtra("CROP", true);
            //设置宽高比
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            //设置显示大小
            intent.putExtra("outputX", 50);
            intent.putExtra("outputY", 50);
            //将图片返回给data
            intent.putExtra("return-data", true);
            startActivityForResult(intent, 200);
        }
        //相册
        if(requestCode == 300 && resultCode == RESULT_OK){
            //获取相册路径
            Uri uri = data.getData();
            //调取裁剪功能
            Intent intent = new Intent("com.android.camera.action.CROP");
            //将图片设置给裁剪
            intent.setDataAndType(uri, "image/*");
            //设置是否支持裁剪
            intent.putExtra("CROP", true);
            //设置框高比
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            //设置显示大小
            intent.putExtra("outputX", 50);
            intent.putExtra("outputY", 50);
            //将图片返回给data
            intent.putExtra("return-data", true);
            startActivityForResult(intent, 200);
        }
        if(requestCode == 200 && resultCode == RESULT_OK){
            Bitmap bitmap = data.getParcelableExtra("data");
            //myProfileSimple.setImageURI();
            iPresenter.startRequest(HttpModel.IMAGE_POST,path,null,null);
        }
    }

    @Override
    public void getDataFail(String error) {

    }
}
